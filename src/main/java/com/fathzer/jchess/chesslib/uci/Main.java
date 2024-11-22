package com.fathzer.jchess.chesslib.uci;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Deque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fathzer.games.movelibrary.MoveLibrary;
import com.fathzer.games.perft.PerfTParser;
import com.fathzer.games.perft.PerfTTestData;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.lichess.DefaultOpenings;
import com.fathzer.jchess.uci.Engine;
import com.fathzer.jchess.uci.UCI;
import com.fathzer.jchess.uci.extended.ExtendedUCI;
import com.fathzer.jchess.uci.extended.SpeedTest;
import com.github.bhlangonijr.chesslib.move.Move;

public class Main extends ExtendedUCI {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		final String pathProperty = System.getProperty("openingsUrl");
		//FIXME According to the UCI protocol, process startup should be as quick as possible
		//So, reading the openings table should be done on "isready" command
		final MoveLibrary<Move, ChessLibMoveGenerator> openings = pathProperty==null ? null : readOpenings(pathProperty);
		try (UCI uci = new Main(new ChessLibEngine(openings))) {
			uci.run();
		}
	}
	
	private static URL toURL(String path) throws IOException {
		URL url;
		try {
			url = new URL(path);
		} catch (MalformedURLException e) {
			File file = new File(path);
			if (!file.exists()) {
				throw new FileNotFoundException();
			}
			url = file.toURI().toURL();
		}
		return url;
	}
	
	private static MoveLibrary<Move, ChessLibMoveGenerator> readOpenings(String url) {
		try {
			return readOpenings(url, toURL(url));
		} catch (IOException e) {
			LOGGER.error("Unable to load opening library at "+url, e);
			return null;
		}
	}

	private static MoveLibrary<Move, ChessLibMoveGenerator> readOpenings(String url, final URL location) throws IOException {
		final boolean compressed = location.getFile().endsWith(".gz");
		try (InputStream stream = location.openStream()) {
			final DefaultOpenings result = new DefaultOpenings(()->stream, compressed);
			LOGGER.info("Opening library read from {}", url);
			return result;
		}
	}

	public Main(Engine defaultEngine) {
		super(defaultEngine);
		addCommand(this::speedTest, "st");
	}
	
	@Override
	protected Collection<PerfTTestData> readTestData() {
		try (InputStream stream = Main.class.getResourceAsStream("/Perft.txt")) {
			return new PerfTParser().withStartPositionPrefix("position fen").withStartPositionCustomizer(s -> s+" 0 1").read(stream, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private void speedTest(Deque<String> args) {
		if (engine instanceof ChessLibEngine) {
			out("completed in "+new SpeedTest<>((ChessLibEngine)engine).run()+"ms");
		} else {
			debug("This engine does not support this command");
		}
	}
}

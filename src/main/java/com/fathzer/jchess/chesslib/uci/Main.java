package com.fathzer.jchess.chesslib.uci;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
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
		final DeferredReadBook<Move, ChessLibMoveGenerator> openings = pathProperty==null ? null : new DeferredReadBook<>(pathProperty, Main::readOpenings);
		try (UCI uci = new Main(new ChessLibEngine(openings))) {
			uci.run();
		}
	}
	
	public Main(Engine defaultEngine) {
		super(defaultEngine);
		addCommand(this::speedTest, "st");
	}
	
	@Override
	protected void doIsReady(Deque<String> tokens) {
		if (engine instanceof ChessLibEngine cle) {
			final DeferredReadBook<Move, ChessLibMoveGenerator> book = cle.getOwnBook();
			if (book!=null && book.isInitRequired()) {
				LOGGER.debug("Start reading opening library from {}", book.getUrl());
				try {
					book.init();
					LOGGER.debug("Opening library read from {}", book.getUrl());
				} catch (IOException e) {
					LOGGER.error("Unable to load opening library at {}", book.getUrl(), e);
					debug("An error occurred while reading opening book");
				}
			}
		}
		super.doIsReady(tokens);
	}
	
	static MoveLibrary<Move, ChessLibMoveGenerator> readOpenings(final URL location) throws IOException {
		final boolean compressed = location.getFile().endsWith(".gz");
		try (InputStream stream = location.openStream()) {
			return new DefaultOpenings(()->stream, compressed);
		}
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
		if (engine instanceof ChessLibEngine chesslibEngine) {
			out("completed in "+new SpeedTest<>(chesslibEngine, this::out).run()+"ms");
		} else {
			debug("This engine does not support this command");
		}
	}

	@Override
	protected void err(String tag, Throwable e) {
		LOGGER.error("An error occurred in {}", tag, e);
	}

	@Override
	protected void err(CharSequence message) {
		LOGGER.error("{}", message);
	}
}

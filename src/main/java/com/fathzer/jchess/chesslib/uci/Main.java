package com.fathzer.jchess.chesslib.uci;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Deque;

import com.fathzer.games.perft.PerfTParser;
import com.fathzer.games.perft.PerfTTestData;
import com.fathzer.jchess.uci.Engine;
import com.fathzer.jchess.uci.UCI;
import com.fathzer.jchess.uci.extended.ExtendedUCI;
import com.fathzer.jchess.uci.extended.SpeedTest;

public class Main extends ExtendedUCI {

	public static void main(String[] args) {
		try (UCI uci = new Main(new ChessLibEngine())) {
			uci.run();
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

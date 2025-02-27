package com.fathzer.jchess.chesslib;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fathzer.games.perft.PerfTBuilder;
import com.fathzer.games.perft.PerfTParser;
import com.fathzer.games.perft.PerfTResult;
import com.fathzer.games.perft.PerfTTestData;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

class PerfTTest {
	private static final Logger log = LoggerFactory.getLogger(PerfTTest.class);
	
	@DisabledIfSystemProperty(named="perftDepth",matches = "0")
	@Test
	void test() throws IOException {
		final int depth = Integer.getInteger("perftDepth", 1);
		if (depth!=1) {
			log.info("PerfT test depth is set to {}",depth);
		}
		readTests().stream().parallel().forEach(test -> {
			try {
				doTest(test, depth);
			} catch (Exception e) {
				fail("Exception on "+test.getStartPosition(),e);
			}
		});
	}
	
	@Test
	void showDivide() {
		final Board board = new Board();
//		board.loadFromFen("8/8/6b1/k3p2N/8/b1PB4/K6p/8 b - - 0 1");
		board.loadFromFen("r1b3r1/p2p1pk1/np6/4q1p1/N1P2RPp/1P1PP3/P1RK3P/1QN1B1n1 b - - 0 1");
		final PerfTBuilder<Move> perfT = new PerfTBuilder<>();
		final PerfTResult<Move> divide = perfT.build(new ChessLibMoveGenerator(board), 2).get();
		System.out.println("Leaves: "+ divide.getNbLeaves());
		System.out.println("Divide is "+divide.getDivides());
	}

	private void doTest(PerfTTestData test, int depth) {
		final Board board = new Board();
		board.loadFromFen(test.getStartPosition()+" 0 1");
		final PerfTBuilder<Move> perfT = new PerfTBuilder<>();
		if (test.getSize()>=depth) {
			final PerfTResult<Move> divide = perfT.build(new ChessLibMoveGenerator(board), depth).get();
			assertEquals(test.getExpectedLeaveCount(depth), divide.getNbLeaves(), "Error for "+test.getStartPosition()+". Divide is "+divide.getDivides());
		}
	}

	private List<PerfTTestData> readTests() throws IOException {
		try (InputStream stream = getClass().getResourceAsStream("/com/fathzer/jchess/perft/Perft.txt")) {
			return new PerfTParser().withStartPositionPrefix("position fen").read(stream, StandardCharsets.UTF_8);
		}
	}
}

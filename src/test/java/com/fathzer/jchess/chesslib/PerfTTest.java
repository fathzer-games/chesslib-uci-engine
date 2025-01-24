package com.fathzer.jchess.chesslib;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fathzer.games.MoveGenerator;
import com.fathzer.games.perft.PerfT;
import com.fathzer.games.perft.PerfTParser;
import com.fathzer.games.perft.PerfTResult;
import com.fathzer.games.perft.PerfTTestData;
import com.fathzer.games.util.PhysicalCores;
import com.fathzer.games.util.exec.ContextualizedExecutor;
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
		final Iterator<PerfTTestData> iterator = readTests().iterator();
		try (ContextualizedExecutor<MoveGenerator<Move>> exec =new ContextualizedExecutor<>(PhysicalCores.count())) {
			while (iterator.hasNext()) {
				final PerfTTestData test = iterator.next();
				try {
					doTest(exec, test, depth);
				} catch (Exception e) {
					fail("Exception on "+test.getStartPosition(),e);
				}
			}
		}
	}
	
//	@Test
	void showDivide() {
		try (ContextualizedExecutor<MoveGenerator<Move>> exec =new ContextualizedExecutor<>(PhysicalCores.count())) {
			final Board board = new Board();
			board.loadFromFen("8/8/6b1/k3p2N/8/b1PB4/K6p/8 b - - 0 1");
			final PerfT<Move> perfT = new PerfT<>(exec);
			final PerfTResult<Move> divide = perfT.divide(2, new ChessLibMoveGenerator(board));
			System.out.println("Leaves: "+ divide.getNbLeaves());
			System.out.println("Divide is "+divide.getDivides());
		}
	}

	private void doTest(ContextualizedExecutor<MoveGenerator<Move>> exec, PerfTTestData test, int depth) {
		final Board board = new Board();
		board.loadFromFen(test.getStartPosition()+" 0 1");
		final PerfT<Move> perfT = new PerfT<>(exec);
		if (test.getSize()>=depth) {
//			try {
				final PerfTResult<Move> divide = perfT.divide(depth, new ChessLibMoveGenerator(board));
				assertEquals(test.getCount(depth), divide.getNbLeaves(), "Error for "+test.getStartPosition()+". Divide is "+divide.getDivides());
//				if (count != test.getCount(depth)) {
//					System.out.println("Error for "+test.getFen()+" expected "+test.getCount(depth)+" got "+count);
//				} else {
//					System.out.println("Ok for "+test.getFen());
//				}
//			} catch (RuntimeException e) {
//				System.out.println("Exception for "+test.getFen());
//				throw e;
//			}
		}
	}

	private List<PerfTTestData> readTests() throws IOException {
		try (InputStream stream = getClass().getResourceAsStream("/com/fathzer/jchess/perft/Perft.txt")) {
			return new PerfTParser().withStartPositionPrefix("position fen").read(stream, StandardCharsets.UTF_8);
		}
	}
}

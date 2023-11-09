package com.fathzer.jchess.chesslib.ai.eval;

import static org.junit.jupiter.api.Assertions.*;

import static com.github.bhlangonijr.chesslib.Square.*;

import static com.fathzer.games.MoveGenerator.MoveConfidence.*;

import org.junit.jupiter.api.Test;

import com.fathzer.games.Color;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.eval.BasicEvaluator;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

class BasicEvaluatorTest {
	
	private static class ATest {
		private final BasicEvaluator eval = new BasicEvaluator();
		private final ChessLibMoveGenerator mvg;
		private int currentEval;
		private boolean noViewPoint;
		
		private ATest(String fen, Color viewPoint, int expectedEval) {
			Board internal = new Board();
			internal.loadFromFen(fen);
			eval.setViewPoint(viewPoint);
			this.noViewPoint = viewPoint==null;
			this.mvg = new ChessLibMoveGenerator(internal);
			mvg.setIncrementalEvaluator(eval);
			assertEquals(expectedEval, eval.rawEvaluate(mvg));
			currentEval = expectedEval;
		}
		
		private void test(Move move) {
			final int increment = eval.getIncrement(mvg, move, currentEval);
			mvg.makeMove(move, UNSAFE);
			int expectedEval = eval.rawEvaluate(mvg);
			if (expectedEval!=increment) {
				mvg.unmakeMove();
				System.out.println("Error for move "+move+" on "+mvg.getBoard().getFen());
			}
			assertEquals (expectedEval, increment);
			this.currentEval = increment;
		}
	}
	
	@Test
	void testBlack() {
		ATest test = new ATest("rn1qkb1r/1ppb1ppp/4pn2/pP1p4/3P1B2/4P3/P1P2PPP/RN1QKBNR w KQkq a6 0 6", Color.BLACK, 0);
		// En passant from white
		test.test(new Move(B5, A6));
		// No capture from black
		test.test(new Move(F8, D6));
		// Capture from white
		test.test(new Move(F4, D6));
		// Capture from black
		test.test(new Move(C7, D6));
		// No Capture from white
		test.test(new Move(B1,B3));
		// Castling from black
		test.test(new Move(E8, G8));
	}

	@Test
	void testWhite() {
		ATest test = new ATest("rn1qkb1r/1ppb1ppp/4pn2/pP1p4/3P1B2/4P3/P1P2PPP/RN1QKBNR w KQkq a6 0 6", Color.WHITE, 0);
		// En passant from white
		test.test(new Move(B5, A6));
		// No capture from black
		test.test(new Move(F8, D6));
		// Capture from white
		test.test(new Move(F4, D6));
		// Capture from black
		test.test(new Move(C7, D6));
		// No Capture from white
		test.test(new Move(B1,B3));
		// Castling from black
		test.test(new Move(E8, G8));
	}

	@Test
	void testCurrentPlayer() {
		ATest test = new ATest("rn1qkb1r/1ppb1ppp/4pn2/pP1p4/3P1B2/4P3/P1P2PPP/RN1QKBNR w KQkq a6 0 6", null, 0);
		// En passant from white
		test.test(new Move(B5, A6));
		// No capture from black
		test.test(new Move(F8, D6));
		// Capture from white
		test.test(new Move(F4, D6));
		// Capture from black
		test.test(new Move(C7, D6));
		// No Capture from white
		test.test(new Move(B1,B3));
		// Castling from black
		test.test(new Move(E8, G8));
	}
}

package com.fathzer.jchess.chesslib.ai.eval;

import static org.junit.jupiter.api.Assertions.*;

import static com.github.bhlangonijr.chesslib.Square.*;

import static com.fathzer.games.MoveGenerator.MoveConfidence.*;

import org.junit.jupiter.api.Test;

import com.fathzer.games.Color;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

class BasicEvaluatorTest {
	
	private static class ATest {
		private final BasicEvaluator eval;
		private final ChessLibMoveGenerator mvg;
		
		private ATest(String fen, Color viewPoint, int expectedEval) {
			Board internal = new Board();
			internal.loadFromFen(fen);
			this.mvg = new ChessLibMoveGenerator(internal);
			this.eval = new BasicEvaluator();
			this.eval.init(mvg);
			eval.setViewPoint(viewPoint);
			assertEquals(expectedEval, eval.evaluate(mvg));
		}
		
		private void test(Move move, int expectedEval) {
			eval.prepareMove(mvg, move);
			assertTrue(mvg.makeMove(move, UNSAFE));
			eval.commitMove();
			final int incEvaluation = eval.evaluate(mvg);
			if (expectedEval!=incEvaluation) {
				mvg.unmakeMove();
				eval.unmakeMove();
			}
			assertEquals (expectedEval, incEvaluation, "Error for move "+move+" on "+mvg.getBoard().getFen());
		}
		
		private int unmakeMove() {
			mvg.unmakeMove();
			eval.unmakeMove();
			return eval.evaluate(mvg);
		}
	}
	
	@Test
	void testBlack() {
		ATest test = new ATest("rn1qkb1r/1ppb1ppp/4pn2/pP1p4/3P1B2/4P3/P1P2PPP/RN1QKBNR w KQkq a6 0 6", Color.BLACK, 0);
		// En passant from white
		test.test(new Move(B5, A6), -100);
		// No capture from black
		test.test(new Move(F8, D6), -100);
		// Capture from white
		test.test(new Move(F4, D6), -400);
		// Capture from black
		test.test(new Move(C7, D6), -100);
		// No Capture from white
		test.test(new Move(B1,B3), -100);
		// Castling from black
		test.test(new Move(E8, G8), -100);
		// Unmake moves
		assertEquals(-100,test.unmakeMove());
		assertEquals(-100,test.unmakeMove());
		assertEquals(-400,test.unmakeMove());
		assertEquals(-100,test.unmakeMove());
		assertEquals(-100,test.unmakeMove());
		assertEquals(0,test.unmakeMove());
	}

	@Test
	void testWhite() {
		ATest test = new ATest("r2qkb1r/1ppb1ppp/4pn2/pP1p4/3P1B2/4P3/P1P2PPP/RN1QKBNR w KQkq a6 0 6", Color.WHITE, 300);
		// En passant from white
		test.test(new Move(B5, A6), 400);
		// No capture from black
		test.test(new Move(F8, D6), 400);
		// Capture from white
		test.test(new Move(F4, D6), 700);
		// Capture from black
		test.test(new Move(C7, D6), 400);
		// No Capture from white
		test.test(new Move(B1,B3), 400);
		// Castling from black
		test.test(new Move(E8, G8), 400);
	}

	@Test
	void testCurrentPlayer() {
		ATest test = new ATest("rn1qkb1r/1ppb1ppp/4pn2/pP1p4/3P1B2/4P3/P1P2PPP/RN1QKBNR w KQkq a6 0 6", null, 0);
		// En passant from white
		test.test(new Move(B5, A6), -100);
		// No capture from black
		test.test(new Move(F8, D6), 100);
		// Capture from white
		test.test(new Move(F4, D6), -400);
		// Capture from black
		test.test(new Move(C7, D6), 100);
		// No Capture from white
		test.test(new Move(B1,B3), -100);
		// Castling from black
		test.test(new Move(E8, G8), 100);
	}
}

package com.fathzer.jchess.chesslib.ai.eval;

import static org.junit.jupiter.api.Assertions.*;

import static com.github.bhlangonijr.chesslib.Square.*;

import static com.fathzer.games.MoveGenerator.MoveConfidence.*;

import org.junit.jupiter.api.Test;

import com.fathzer.games.MoveGenerator.MoveConfidence;
import com.fathzer.games.ai.evaluation.Evaluator;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

class BasicEvaluatorTest {
	
	private static class ATest {
		private final NaiveEvaluator eval;
		private final ChessLibMoveGenerator mvg;
		
		private ATest(String fen, int expectedEval) {
			this.mvg = new ChessLibMoveGenerator(new Board());
			mvg.getBoard().loadFromFen(fen);
			this.eval = new NaiveEvaluator();
			this.eval.init(mvg);
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
	}
	
	@Test
	void testCurrentPlayer() {
		ATest test = new ATest("rn1qkb1r/1ppb1ppp/4pn2/pP1p4/3P1B2/4P3/P1P2PPP/RN1QKBNR w KQkq a6 0 6", 0);
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
	
	@Test
	void testFork() {
		ChessLibMoveGenerator mvg = new ChessLibMoveGenerator(new Board());
		mvg.getBoard().loadFromFen("r2qkb1r/1ppb1ppp/4pn2/pP1p4/3P1B2/4P3/P1P2PPP/RN1QKBNR w KQkq a6 0 6");
		Evaluator<Move, ChessLibMoveGenerator> eval = new NaiveEvaluator();
		eval.init(mvg);
		assertEquals(300, eval.evaluate(mvg));
		
		ChessLibMoveGenerator mvg2 = (ChessLibMoveGenerator) mvg.fork();
		Evaluator<Move, ChessLibMoveGenerator> eval2 = eval.fork();
		assertEquals(300, eval2.evaluate(mvg2));
		// En passant from white
		Move move = new Move(B5, A6);
		eval2.prepareMove(mvg2, move);
		assertTrue(mvg2.makeMove(move, MoveConfidence.UNSAFE));
		eval2.commitMove();
		assertEquals(-400, eval2.evaluate(mvg2));
		assertEquals(300, eval.evaluate(mvg));
	}
}

package com.fathzer.jchess.chesslib.ai;

import static org.junit.jupiter.api.Assertions.*;

import static com.github.bhlangonijr.chesslib.Square.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.fathzer.games.Color;
import com.fathzer.games.ai.Negamax;
import com.fathzer.games.ai.SearchParameters;
import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.evaluation.Evaluation;
import com.fathzer.games.ai.evaluation.Evaluator;
import com.fathzer.games.ai.evaluation.Evaluation.Type;
import com.fathzer.games.ai.exec.ExecutionContext;
import com.fathzer.games.ai.exec.SingleThreadContext;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

class MinimaxEngineTest {
	static ChessLibMoveGenerator fromFEN(String fen) {
		Board board = new Board();
		board.loadFromFen(fen);
		return new ChessLibMoveGenerator(board);
	}

	@Test
	void blackPlayingTest() {
		final InternalEngine mme4 = new InternalEngine(new BasicEvaluator(), 3);
		mme4.setMoveComparatorSupplier(StrictMoveComparator::new);
		mme4.getDeepeningPolicy().setSize(Integer.MAX_VALUE);
		final List<EvaluatedMove<Move>> moves = mme4.getBestMoves(fromFEN("7k/5p1Q/5P1N/5PPK/6PP/8/8/8 b - - 6 5"));
//show(moves);
		assertEquals(1, moves.size());
		assertEquals(H8, moves.get(0).getContent().getFrom());
		assertEquals(H7, moves.get(0).getContent().getTo());
		assertEquals(-800, moves.get(0).getScore());
	}
	
	private void show(Collection<EvaluatedMove<Move>> moves) {
		System.out.println(moves);
	}
	
	@Test
	void test() {
		List<EvaluatedMove<Move>> moves;
		final InternalEngine mme4 = new InternalEngine(new BasicEvaluator(), 4);
		mme4.getDeepeningPolicy().setSize(Integer.MAX_VALUE);
		mme4.setMoveComparatorSupplier(StrictMoveComparator::new);
		
		// 3 possible Mats in 1 with whites
		moves = mme4.getBestMoves(fromFEN("7k/5p2/5PQN/5PPK/6PP/8/8/8 w - - 6 5"));
//show(moves);
		assertEquals(6, moves.size());
		{
			final Evaluation max = moves.get(0).getEvaluation();
			assertEquals(Type.WIN, max.getType());
			assertEquals(1, max.getCountToEnd());
			assertTrue(moves.get(3).getEvaluation().compareTo(max)<0);
			moves.stream().limit(3).forEach(m -> assertEquals(max, m.getEvaluation()));
		}
//fail("enough!");

		// Mat in 1 with blacks
		System.out.println("------------------");
		moves = mme4.getBestMoves(fromFEN("1R6/8/8/7R/k7/ppp1p3/r2bP3/1K6 b - - 6 5"));
//show(moves);
		assertEquals(7, moves.size());
		Evaluation max = moves.get(0).getEvaluation();
		assertEquals(Type.WIN, max.getType());
		assertEquals(1, max.getCountToEnd());
		Move mv = moves.get(0).getContent();
		assertEquals(C3, mv.getFrom());
		assertEquals(C2, mv.getTo());
		assertTrue(moves.get(1).getScore()<10000.0);
		
		// Check in 2
		System.out.println("------------------");
		moves = mme4.getBestMoves(fromFEN("8/8/8/8/1B6/NN6/pk1K4/8 w - - 0 1"));
//show(moves);
		max = moves.get(0).getEvaluation();
		assertEquals(Type.WIN, max.getType());
		assertEquals(2, max.getCountToEnd());
		assertTrue(moves.get(1).getScore()<max.getScore());
		mv = moves.get(0).getContent();
		assertEquals(B3, mv.getFrom());
		assertEquals(A1, mv.getTo());
		
		// Check in 2 with blacks
		System.out.println("------------------");
		moves = mme4.getBestMoves(fromFEN("8/4k1KP/6nn/6b1/8/8/8/8 b - - 0 1"));
//show(moves);
		max = moves.get(0).getEvaluation();
		assertEquals(Type.WIN, max.getType());
		assertEquals(2, max.getCountToEnd());
		assertTrue(moves.get(1).getScore()<max.getScore());
		assertEquals(G6, moves.get(0).getContent().getFrom());
		assertEquals(H8, moves.get(0).getContent().getTo());
		
		
		// Check in 3
		System.out.println("------------------");
		InternalEngine engine = new InternalEngine(new BasicEvaluator(), 6);
		engine.setMoveComparatorSupplier(StrictMoveComparator::new);
		engine.getDeepeningPolicy().setSize(3);
		engine.getDeepeningPolicy().setAccuracy(100);
		engine.setMoveComparatorSupplier(StrictMoveComparator::new);
		moves = engine.getBestMoves(fromFEN("r2k1r2/pp1b2pp/1b2Pn2/2p5/Q1B2Bq1/2P5/P5PP/3R1RK1 w - - 0 1"));
//show(moves);
assertEquals(19, moves.size());
		mv = moves.get(0).getContent();
		assertEquals(D1, mv.getFrom());
		assertEquals(D7, mv.getTo());
	}
	
	@Test
	void moreTests() {
		final ChessLibMoveGenerator board = fromFEN("8/8/8/3kr3/8/8/5PPP/7K w - - 0 1");
		final Evaluator<ChessLibMoveGenerator> basicEvaluator = new BasicEvaluator();
		basicEvaluator.setViewPoint(Color.WHITE);
		try (ExecutionContext<Move, ChessLibMoveGenerator> exec = new SingleThreadContext<>(board)) {
			Negamax<Move, ChessLibMoveGenerator> ai = new Negamax<>(exec, basicEvaluator);
			List<Move> l = new ArrayList<>();
			l.add(new Move(H1, G1));
			l.add(new Move(F2, F3));
			l.add(new Move(F2, F4));
			final SearchParameters params = new SearchParameters(4, Integer.MAX_VALUE, 0);
			final List<EvaluatedMove<Move>> eval = ai.getBestMoves(l, params).getCut();
			assertEquals(3, eval.size());
			for (EvaluatedMove<Move> e : eval) {
				assertEquals(Type.LOOSE, e.getEvaluation().getType());
				assertEquals(1, e.getEvaluation().getCountToEnd());
			}
		}
	}
	
	@Test
	@Disabled
	void iterativeTest() {
		//TODO This test is disabled, it tests mat in 1 should not be returned when best moves are mat in 3
		// when ai is called with a reasonable non null accuracy
		// Currently, the only way to achieve this is to have a custom win/loose evaluation with a gap higher than the accuracy
		// I should think more about it...
		InternalEngine engine = new InternalEngine(new BasicEvaluator(), 8);
		engine.setParallelism(4);
		engine.getDeepeningPolicy().setSize(1);
		engine.getDeepeningPolicy().setAccuracy(300);
		engine.getDeepeningPolicy().setMaxTime(15000);
		// Tests that loose in 1 are not in the best moves (was a bug in fist iterative engine version)
		final List<EvaluatedMove<Move>> moves = engine.getBestMoves(fromFEN("4n2r/2k1Q2p/5B2/2N5/2B2R2/1P6/3PKPP1/6q1 b - - 2 46"));
		assertEquals(2, moves.size());
		assertEquals(3, moves.get(0).getEvaluation().getCountToEnd());
		assertEquals(3, moves.get(1).getEvaluation().getCountToEnd());
	}
	
	@Test
	void iterativeTest2() {
		InternalEngine engine = new InternalEngine(new BasicEvaluator(), 4);
		engine.setParallelism(4);
		engine.getDeepeningPolicy().setSize(1);
		engine.getDeepeningPolicy().setAccuracy(100);
		engine.getDeepeningPolicy().setMaxTime(15000);
		// Tests that loosing move is not in the best moves (was a bug in fist iterative engine version)
		final List<EvaluatedMove<Move>> moves = engine.getBestMoves(fromFEN("3bkrnr/p2ppppp/7q/2p5/8/2P5/PP1PPPPP/RNBQKBNR b KQk - 0 1"));
		for (EvaluatedMove<Move> ev : moves) {
			assertEquals(Type.EVAL, ev.getEvaluation().getType());
		}
	}
	
	@Test
	@Disabled
	void bug20230813() {
		// Not a bug, just a problem with evaluation function
		ChessLibMoveGenerator board = fromFEN("8/8/8/4p1k1/3bK3/8/7p/8 b - - 0 1");
		InternalEngine engine = new InternalEngine(new BasicEvaluator(), 4);
		engine.getDeepeningPolicy().setSize(Integer.MAX_VALUE);
		System.out.println(engine.getBestMoves(board));
		System.out.println(engine.apply(board));
	}

	@Test
	@Disabled
	void bug20230821() {
		// Not a bug, just a problem with evaluation function
		InternalEngine engine = new InternalEngine(new BasicEvaluator(), 7);
		System.out.println(engine.apply(fromFEN("8/6k1/6p1/1N6/6K1/R7/4B3/8 w - - 21 76")));
	}
}

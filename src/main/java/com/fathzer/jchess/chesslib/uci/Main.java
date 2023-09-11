package com.fathzer.jchess.chesslib.uci;

import java.util.List;

import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.evaluation.Evaluation;
import com.fathzer.games.ai.evaluation.Evaluation.Type;
import com.fathzer.jchess.chesslib.ChessLibEngine;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.BasicEvaluator;
import com.fathzer.jchess.chesslib.ai.InternalEngine;
import com.fathzer.jchess.uci.Engine;
import com.fathzer.jchess.uci.UCI;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

public class Main extends UCI {

	public static void main(String[] args) {
		new Main(new ChessLibEngine()).run();
	}

	public Main(Engine defaultEngine) {
		super(defaultEngine);
		addCommand(this::speedTest, "st");
	}

//TODO Commonalize this with JChess-core	
	private static class MovesAndMore {
		private final InternalEngine engine;
		private String fen;
		private List<EvaluatedMove<Move>> moves;
		
		MovesAndMore(InternalEngine engine, String fen) {
			this.engine = engine;
			fill(fen);
		}
		
		void fill(String fen) {
			Board board = new Board();
			board.loadFromFen(fen);
			moves = engine.getBestMoves(new ChessLibMoveGenerator(board));
		}

		private void assertEquals(Object expected, Object actual) {
			if (!expected.equals(actual)) {
				show();
				throw new IllegalArgumentException("Expecting "+expected+" but is "+actual);
			}
		}

		private void assertTrue(boolean value) {
			if (!value) {
				show();
				throw new IllegalArgumentException("Expecting true here");
			}
		}
		
		private void show() {
			System.out.println(fen);
			System.out.println(moves);
		}
	}
	
	private void speedTest(String[] args) {
		final long start = System.currentTimeMillis();
		final InternalEngine engine = new InternalEngine(new BasicEvaluator(), 8);
		engine.getSearchParams().setSize(Integer.MAX_VALUE);
		if (args.length!=0) {
			engine.setParallelism(Integer.parseInt(args[0]));
		}
		
		// 3 possible Mats in 1 with whites
		final MovesAndMore mv = new MovesAndMore(engine, "7k/5p2/5PQN/5PPK/6PP/8/8/8 w - - 6 5");
		mv.assertEquals(6, mv.moves.size());
		{
			final Evaluation max = mv.moves.get(0).getEvaluation();
			mv.assertEquals(Type.WIN, max.getType());
			mv.assertEquals(1, max.getCountToEnd());
			mv.assertTrue(mv.moves.get(3).getEvaluation().compareTo(max)<0);
			mv.moves.stream().limit(3).forEach(m -> mv.assertEquals(max, m.getEvaluation()));
		}

		// Mat in 1 with blacks
		mv.fill("1R6/8/8/7R/k7/ppp1p3/r2bP3/1K6 b - - 6 5");
		mv.assertEquals(7, mv.moves.size());
		Evaluation max = mv.moves.get(0).getEvaluation();
		mv.assertEquals(Type.WIN, max.getType());
		mv.assertEquals(1, max.getCountToEnd());
		Move m = mv.moves.get(0).getContent();
		mv.assertEquals("c3", m.getFrom().toString().toLowerCase());
		mv.assertEquals("c2", m.getTo().toString().toLowerCase());
		max = mv.moves.get(1).getEvaluation();
		//TODO iterative engine fails to find the second best move in tree, probably because of deepening interruption by first mat
		// Make a test when it will be fixed with a second move that is a MAT in 3 move (see commented code). 
//		mv.assertEquals(Type.WIN, max.getType());
//		mv.assertEquals(3, max.getCountToEnd());
//		mv.assertEquals(Type.EVAL, mv.moves.get(2).getEvaluation().getType());
		
		// Check in 2
		mv.fill("8/8/8/8/1B6/NN6/pk1K4/8 w - - 0 1");
		max = mv.moves.get(0).getEvaluation();
		mv.assertEquals(Type.WIN, max.getType());
		mv.assertEquals(2, max.getCountToEnd());
		mv.assertTrue(mv.moves.get(1).getScore()<max.getScore());
		m = mv.moves.get(0).getContent();
		mv.assertEquals("b3", m.getFrom().toString().toLowerCase());
		mv.assertEquals("a1", m.getTo().toString().toLowerCase());
		
		// Check in 2 with blacks
		mv.fill("8/4k1KP/6nn/6b1/8/8/8/8 b - - 0 1");
		max = mv.moves.get(0).getEvaluation();
		mv.assertEquals(Type.WIN, max.getType());
		mv.assertEquals(2, max.getCountToEnd());
		mv.assertTrue(mv.moves.get(1).getScore()<max.getScore());
		mv.assertEquals("g6", mv.moves.get(0).getContent().getFrom().toString().toLowerCase());
		mv.assertEquals("h8", mv.moves.get(0).getContent().getTo().toString().toLowerCase());
		
		// Check in 3
		engine.getSearchParams().setSize(3);
		engine.getSearchParams().setAccuracy(100);
		mv.fill("r2k1r2/pp1b2pp/1b2Pn2/2p5/Q1B2Bq1/2P5/P5PP/3R1RK1 w - - 0 1");
		mv.assertEquals(19, mv.moves.size());
		m = mv.moves.get(0).getContent();
		mv.assertEquals("d1", m.getFrom().toString().toLowerCase());
		mv.assertEquals("d7", m.getTo().toString().toLowerCase());
		
		// Check in 4
		engine.getSearchParams().setSize(1);
		engine.getSearchParams().setAccuracy(0);
		mv.fill("8/4k3/8/R7/8/8/8/4K2R w K - 0 1");
		mv.assertEquals(2, mv.moves.size());
		mv.assertEquals(Evaluation.Type.WIN, mv.moves.get(0).getEvaluation().getType());
		mv.assertEquals(4, mv.moves.get(0).getEvaluation().getCountToEnd());
		mv.assertEquals(Evaluation.Type.WIN, mv.moves.get(1).getEvaluation().getType());
		mv.assertEquals(4, mv.moves.get(1).getEvaluation().getCountToEnd());

		out("completed in "+(System.currentTimeMillis()-start)+"ms");
	}
}

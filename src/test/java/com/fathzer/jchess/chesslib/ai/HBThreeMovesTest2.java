package com.fathzer.jchess.chesslib.ai;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2SimplifiedEvaluator;
import com.fathzer.jchess.chesslib.uci.ChessLibEngine;
import com.github.bhlangonijr.chesslib.move.Move;

class HBThreeMovesTest2 {

	@Test
	void test() {
		final int depth = 6;
		final int bestMoveCount = 3;
//		final String fen = "r2k1r2/pp1b2pp/1b2Pn2/2p5/Q1B2Bq1/2P5/P5PP/3R1RK1 w - - 0 1"; // pions doublés dans le camp noir
		final String fen = "r2k1r2/pp1b2p1/1b2Pnp1/2p5/Q1B2Bq1/2P5/P5PP/3R1RK1 w - - 0 1";
		final IterativeDeepeningEngine<Move, ChessLibMoveGenerator> engine = ChessLibEngine.buildEngine(Hb2SimplifiedEvaluator::new, depth);
		engine.getDeepeningPolicy().setSize(bestMoveCount);
		final ChessLibMoveGenerator board = MinimaxEngineTest.fromFEN(fen, BasicMoveComparator::new);
		final List<EvaluatedMove<Move>> moves = engine.getBestMoves(board).getAccurateMoves();
		System.out.println(moves);
		for (int i=0;i<bestMoveCount;i++) {
			EvaluatedMove<Move> move = moves.get(i);
			List<Move> principalVariation = engine.getTranspositionTable().collectPV(board, move.getMove(), depth);
			System.out.println(move+" -> "+principalVariation);
		}
	}

}
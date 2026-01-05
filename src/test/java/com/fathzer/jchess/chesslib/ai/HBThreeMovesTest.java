package com.fathzer.jchess.chesslib.ai;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.eval.SimplifiedEvaluator;
import com.fathzer.jchess.chesslib.uci.ChessLibEngine;
import com.github.bhlangonijr.chesslib.move.Move;

class HBThreeMovesTest {

	@Test
	void test() {
		final int depth = 1;
		final int bestMoveCount = 10;
//		final String fen = "5k2/p1p2ppp/4b3/1p2p1P1/1P2P3/3P1B2/Pb2NPKP/8 w - - 0 1";
//		final String fen = "r2k1r2/pp1b2pp/1b2Pn2/2p5/Q1B2Bq1/2P5/P5PP/3R1RK1 w - - 0 1";
		final String fen = "8/3k3p/2R5/3PP3/8/1p2K3/7r/8 w - - 0 1";
		
		final IterativeDeepeningEngine<Move, ChessLibMoveGenerator> engine = ChessLibEngine.buildEngine(SimplifiedEvaluator::new, depth);
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
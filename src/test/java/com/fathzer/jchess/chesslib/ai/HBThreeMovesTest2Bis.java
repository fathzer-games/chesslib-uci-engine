package com.fathzer.jchess.chesslib.ai;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.eval.hb.HbPestoEvaluator;
import com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2SimplifiedEvaluator;
import com.fathzer.jchess.chesslib.uci.ChessLibEngine;
import com.github.bhlangonijr.chesslib.move.Move;

class HBThreeMovesTest2Bis {

	@Test
	void test() {
		final int depth = 12;
		final int bestMoveCount = 3;
		final String fen = "5k2/p1p2ppp/4b3/1p2p1P1/1P2P3/3P1B2/Pb2NPKP/8 w - - 0 1";
//		final String fen = "r2k1r2/pp1b2pp/1b2Pn2/2p5/Q1B2Bq1/2P5/P5PP/3R1RK1 w - - 0 1"; // pas de pions doublés
//		final String fen = "5k2/p1p2ppp/4b3/4p1P1/1P2P3/p2P1B2/1b2NP1P/6K1 w - - 0 1";
	
		
//		final String fen = "r2k1r2/pp1b2p1/1b2Pnp1/2p5/Q1B2Bq1/2P5/P5PP/3R1RK1 w - - 0 1";
//		final IterativeDeepeningEngine<Move, ChessLibMoveGenerator> engine = ChessLibEngine.buildEngine(Hb2SimplifiedEvaluator::new, depth);
		final IterativeDeepeningEngine<Move, ChessLibMoveGenerator> engine = ChessLibEngine.buildEngine(HbPestoEvaluator::new, depth);
		
		engine.getDeepeningPolicy().setSize(bestMoveCount);
		engine.getDeepeningPolicy().setDeepenOnForced(true);
		final List<EvaluatedMove<Move>> moves = engine.getBestMoves(MinimaxEngineTest.fromFEN(fen, BasicMoveComparator::new)).getBestMoves();
		System.out.println(moves);
		for (EvaluatedMove<Move> move : moves) {
//			EvaluatedMove<Move> move = moves.get(i);
			List<Move> principalVariation = move.getPrincipalVariation();
			System.out.println(move+" -> "+principalVariation);
		}
	}

}

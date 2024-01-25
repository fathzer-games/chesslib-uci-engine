package com.fathzer.jchess.chesslib.ai.eval;

import com.fathzer.games.ai.evaluation.StaticEvaluator;
import com.fathzer.games.ai.evaluation.ZeroSumEvaluator;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.move.Move;

public class MyTinyEvaluator implements StaticEvaluator<Move, ChessLibMoveGenerator>, ZeroSumEvaluator<Move, ChessLibMoveGenerator> {

	@Override
	public int evaluateAsWhite(ChessLibMoveGenerator board) {
		// TODO Auto-generated method stub
		return 0;
	}

}

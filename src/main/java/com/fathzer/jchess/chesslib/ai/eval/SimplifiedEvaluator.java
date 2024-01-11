package com.fathzer.jchess.chesslib.ai.eval;

import com.fathzer.chess.utils.evaluators.simplified.AbstractSimplifiedEvaluator;
import com.fathzer.jchess.chesslib.ChessLibAdapter;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.move.Move;

public class SimplifiedEvaluator extends AbstractSimplifiedEvaluator<Move, ChessLibMoveGenerator> implements ChessLibAdapter {
	public SimplifiedEvaluator() {
		super();
	}
}

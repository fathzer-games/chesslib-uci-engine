package com.fathzer.jchess.chesslib.ai.eval;

import com.fathzer.chess.utils.evaluators.AbstractNaiveEvaluator;
import com.fathzer.jchess.chesslib.ChessLibAdapter;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.move.Move;

public class NaiveEvaluator extends AbstractNaiveEvaluator<Move, ChessLibMoveGenerator> implements ChessLibAdapter {
	public NaiveEvaluator() {
		super();
	}

	private NaiveEvaluator(int score) {
		super(score);
	}

	@Override
	public NaiveEvaluator fork(int score) {
		final NaiveEvaluator result = new NaiveEvaluator(score);
		result.viewPoint = this.viewPoint;
		return result;
	}
}

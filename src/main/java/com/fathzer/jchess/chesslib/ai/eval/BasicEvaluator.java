package com.fathzer.jchess.chesslib.ai.eval;

import com.fathzer.chess.utils.evaluators.NaiveEvaluator;
import com.fathzer.jchess.chesslib.ChessLibAdapter;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.move.Move;

public class BasicEvaluator extends NaiveEvaluator<Move, ChessLibMoveGenerator> implements ChessLibAdapter {
	public BasicEvaluator() {
		super();
	}

	private BasicEvaluator(int score) {
		super(score);
	}

	@Override
	public BasicEvaluator fork(int score) {
		return new BasicEvaluator(score);
	}
}

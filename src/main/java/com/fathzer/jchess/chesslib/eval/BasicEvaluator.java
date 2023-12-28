package com.fathzer.jchess.chesslib.eval;

import static com.fathzer.games.Color.*;

import com.fathzer.chess.utils.evaluators.NaiveEvaluator;
import com.fathzer.games.Color;
import com.fathzer.jchess.chesslib.ChessLibAdapter;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.move.Move;

public class BasicEvaluator extends NaiveEvaluator<Move, ChessLibMoveGenerator> implements ChessLibAdapter {
	public BasicEvaluator(ChessLibMoveGenerator board) {
		super(board);
	}

	private BasicEvaluator(int score) {
		super(score);
	}
	
	@Override
	public void setViewPoint(Color viewPoint) {
		if (viewPoint==null) {
			this.viewPoint = 0;
		} else {
			this.viewPoint = viewPoint==WHITE ? 1 : -1;
		}
	}

	@Override
	public BasicEvaluator fork(int score) {
		return new BasicEvaluator(score);
	}
}

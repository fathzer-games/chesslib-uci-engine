package com.fathzer.jchess.chesslib.ai.eval;

import com.fathzer.chess.utils.evaluators.AbstractNaiveEvaluator;
import com.fathzer.jchess.chesslib.BasicMoveDecoder;
import com.fathzer.jchess.chesslib.ChessLibExplorerBuilder;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.move.Move;

public class NaiveEvaluator extends AbstractNaiveEvaluator<Move, ChessLibMoveGenerator> implements ChessLibExplorerBuilder {
	public NaiveEvaluator() {
		super();
	}

	private NaiveEvaluator(int score) {
		super(score);
	}

	@Override
	public NaiveEvaluator fork(int score) {
		return new NaiveEvaluator(score);
	}

	@Override
	protected int getCapturedType(ChessLibMoveGenerator board, Move move) {
		return BasicMoveDecoder.getCapturedType(board, move);
	}

	@Override
	protected int getPromotionType(ChessLibMoveGenerator board, Move move) {
		return BasicMoveDecoder.getPromotionType(board, move);
	}
}

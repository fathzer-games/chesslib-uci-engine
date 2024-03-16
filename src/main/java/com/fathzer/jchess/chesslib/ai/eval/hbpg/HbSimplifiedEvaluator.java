package com.fathzer.jchess.chesslib.ai.eval.hbpg;

import com.fathzer.chess.utils.adapters.MoveData;
import com.fathzer.jchess.chesslib.ChessLibExplorerBuilder;
import com.fathzer.jchess.chesslib.ChessLibMoveData;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.move.Move;

public class HbSimplifiedEvaluator extends HbAbstractIncrementalSimplifiedEvaluator<Move, ChessLibMoveGenerator> implements ChessLibExplorerBuilder {
	private HbSimplifiedEvaluator(HbIncrementalState state) {
		super(state);
	}

	public HbSimplifiedEvaluator() {
		super();
	}

	@Override
	public MoveData<Move, ChessLibMoveGenerator> get() {
		return new ChessLibMoveData();
	}

	@Override
	protected HbAbstractIncrementalSimplifiedEvaluator<Move, ChessLibMoveGenerator> fork(HbIncrementalState state) {
		return new HbSimplifiedEvaluator(state);
	}
}

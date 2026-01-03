package com.fathzer.jchess.chesslib.ai.eval.hb;

import com.fathzer.chess.utils.adapters.MoveData;
import com.fathzer.jchess.chesslib.ChessLibExplorerBuilder;
import com.fathzer.jchess.chesslib.ChessLibMoveData;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.move.Move;

public class HbPestoEvaluator extends HbAbstractIncrementalPestoEvaluator<Move, ChessLibMoveGenerator> implements ChessLibExplorerBuilder {
	private HbPestoEvaluator(HbPestoState state) {
		super(state);
	}

	public HbPestoEvaluator() {
		super();
	}

	@Override
	public MoveData<Move, ChessLibMoveGenerator> get() {
		return new ChessLibMoveData();
	}

	@Override
	protected HbAbstractIncrementalPestoEvaluator<Move, ChessLibMoveGenerator> fork(HbPestoState state) {
		return new HbPestoEvaluator(state);
	}
}

package com.fathzer.jchess.chesslib.ai.eval.hbpg2;

import com.fathzer.chess.utils.adapters.MoveData;
import com.fathzer.jchess.chesslib.ChessLibExplorerBuilder;
import com.fathzer.jchess.chesslib.ChessLibMoveData;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.move.Move;

public class Hb2SimplifiedEvaluator extends Hb2AbstractIncrementalSimplifiedEvaluator<Move, ChessLibMoveGenerator> implements ChessLibExplorerBuilder {
	private Hb2SimplifiedEvaluator(Hb2IncrementalState state) {
		super(state);
	}

	public Hb2SimplifiedEvaluator() {
		super();
	}

	@Override
	public MoveData<Move, ChessLibMoveGenerator> get() {
		return new ChessLibMoveData();
	}

	@Override
	protected Hb2AbstractIncrementalSimplifiedEvaluator<Move, ChessLibMoveGenerator> fork(Hb2IncrementalState state) {
		return new Hb2SimplifiedEvaluator(state);
	}
}

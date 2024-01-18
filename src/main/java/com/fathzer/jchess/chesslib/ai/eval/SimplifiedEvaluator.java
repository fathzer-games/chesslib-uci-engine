package com.fathzer.jchess.chesslib.ai.eval;

import com.fathzer.chess.utils.adapters.MoveData;
import com.fathzer.chess.utils.evaluators.simplified.AbstractIncrementalSimplifiedEvaluator;
import com.fathzer.chess.utils.evaluators.simplified.IncrementalState;
import com.fathzer.jchess.chesslib.ChessLibExplorerBuilder;
import com.fathzer.jchess.chesslib.ChessLibMoveData;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.move.Move;

public class SimplifiedEvaluator extends AbstractIncrementalSimplifiedEvaluator<Move, ChessLibMoveGenerator> implements ChessLibExplorerBuilder {
	private SimplifiedEvaluator(IncrementalState state) {
		super(state);
	}

	public SimplifiedEvaluator() {
		super();
	}

	@Override
	public MoveData<Move, ChessLibMoveGenerator> get() {
		return new ChessLibMoveData();
	}

	@Override
	protected AbstractIncrementalSimplifiedEvaluator<Move, ChessLibMoveGenerator> fork(IncrementalState state) {
		return new SimplifiedEvaluator(state);
	}
}

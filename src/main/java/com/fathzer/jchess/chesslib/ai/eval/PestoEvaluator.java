package com.fathzer.jchess.chesslib.ai.eval;

import com.fathzer.chess.utils.adapters.MoveData;
import com.fathzer.chess.utils.evaluators.pesto.AbstractIncrementalPestoEvaluator;
import com.fathzer.chess.utils.evaluators.pesto.PestoState;
import com.fathzer.jchess.chesslib.ChessLibExplorerBuilder;
import com.fathzer.jchess.chesslib.ChessLibMoveData;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.move.Move;

public class PestoEvaluator extends AbstractIncrementalPestoEvaluator<Move, ChessLibMoveGenerator> implements ChessLibExplorerBuilder {
	private PestoEvaluator(PestoState state) {
		super(state);
	}

	public PestoEvaluator() {
		super();
	}

	@Override
	public MoveData<Move, ChessLibMoveGenerator> get() {
		return new ChessLibMoveData();
	}

	@Override
	protected AbstractIncrementalPestoEvaluator<Move, ChessLibMoveGenerator> fork(PestoState state) {
		return new PestoEvaluator(state);
	}
}

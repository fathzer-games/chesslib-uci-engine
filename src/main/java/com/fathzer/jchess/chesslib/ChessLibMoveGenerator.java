package com.fathzer.jchess.chesslib;

import java.util.Comparator;
import java.util.List;

import com.fathzer.games.MoveGenerator;
import com.fathzer.games.HashProvider;
import com.fathzer.games.Status;
import com.fathzer.jchess.chesslib.eval.AbstractEvaluationStack;
import com.fathzer.jchess.chesslib.eval.IncrementalEvaluator;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;

public class ChessLibMoveGenerator implements MoveGenerator<Move>, HashProvider {
	private final Board board;
	private Comparator<Move> comparator;
	private AbstractEvaluationStack<Move, ChessLibMoveGenerator, ?> evaluationStack;
	
	public ChessLibMoveGenerator(Board board) {
		this.board = board.clone();
	}
	
	public void setIncrementalEvaluator(IncrementalEvaluator<Move, ChessLibMoveGenerator, ?> evaluator) {
		this.evaluationStack = new AbstractEvaluationStack<>(this, evaluator);
	}
	
	public AbstractEvaluationStack<Move, ChessLibMoveGenerator, ?> getEvaluationStack() {
		return evaluationStack;
	}

	@Override
	public boolean makeMove(Move move, MoveConfidence confidence) {
		if (evaluationStack!=null) {
			evaluationStack.prepareMove(move);
		}
		final boolean isMoveValid = internalMakeMove(move, confidence);
		if (isMoveValid && evaluationStack!=null) {
			evaluationStack.commitMove(move);
		}
		return isMoveValid;
	}

	private boolean internalMakeMove(Move move, MoveConfidence confidence) {
		try {
			return board.doMove(move, MoveConfidence.UNSAFE==confidence);
		} catch (RuntimeException e) {
			// Can throw an exception if no piece is at move from cell
			return false;
		}
	}
	
	@Override
	public void unmakeMove() {
		board.undoMove();
		if (evaluationStack!=null) {
			evaluationStack.unmakeMove();
		}
	}
	
	@Override
	public List<Move> getMoves(boolean quiesce) {
		final List<Move> moves = quiesce ? board.pseudoLegalCaptures() : board.pseudoLegalMoves();
		if (comparator!=null) {
			moves.sort(comparator);
		}
		return moves;
	}
	
	@Override
	public List<Move> getLegalMoves() {
		return board.legalMoves();
	}

	@Override
	public long getHashKey() {
		return board.getZobristKey();
	}
	
	public Board getBoard() {
		return this.board; 
	}

	public Comparator<Move> getMoveComparator() {
		return this.comparator;
	}
	
	public void setMoveComparator(Comparator<Move> comparator) {
		this.comparator = comparator;
	}

	@Override
	public Status getContextualStatus() {
		return board.getHalfMoveCounter()>50 || board.isInsufficientMaterial() || board.isRepetition() ? Status.DRAW : Status.PLAYING;
	}

	@Override
	public Status getEndGameStatus() {
		if (board.isKingAttacked()) {
			return board.getSideToMove()==Side.BLACK ? Status.WHITE_WON : Status.BLACK_WON;
		} else {
			return Status.DRAW;
		}
	}
}

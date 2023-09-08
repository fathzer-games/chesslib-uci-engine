package com.fathzer.jchess.chesslib;

import java.util.Comparator;
import java.util.List;

import com.fathzer.games.MoveGenerator;
import com.fathzer.games.HashProvider;
import com.fathzer.games.Status;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;

public class ChessLibMoveGenerator implements MoveGenerator<Move>, HashProvider {
	private final Board board;
	private Comparator<Move> comparator;
	
	public ChessLibMoveGenerator(Board board) {
		this.board = board.clone();
	}
	
	@Override
	public boolean makeMove(Move move) {
		return board.doMove(move);
	}
	
	@Override
	public void unmakeMove() {
		board.undoMove();
	}
	
	@Override
	public List<Move> getMoves() {
		final List<Move> moves = board.pseudoLegalMoves();
		if (comparator!=null) {
			moves.sort(comparator);
		}
		return moves;
	}

	@Override
	public Status getStatus() {
		if (board.isMated()) {
			return Side.BLACK.equals(board.getSideToMove()) ? Status.WHITE_WON : Status.BLACK_WON;
		}
		return board.isDraw() ? Status.DRAW : Status.PLAYING;
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
}

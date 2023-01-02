package com.fathzer.jchess.chesslib;

import java.util.Iterator;
import java.util.List;

import com.fathzer.games.GameState;
import com.fathzer.games.ZobristProvider;
import com.fathzer.games.Status;
import com.fathzer.jchess.uci.UCIMove;
import com.fathzer.jchess.uci.UCIMoveGenerator;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;

public class ChessLibMoveGenerator implements UCIMoveGenerator<Move>, ZobristProvider {
	private Board board;
	
	public ChessLibMoveGenerator(Board board) {
		this.board = board.clone();
	}
	
	@Override
	public void makeMove(Move move) {
		board.doMove(move);
	}
	
	@Override
	public void unmakeMove() {
		board.undoMove();
	}
	
	@Override
	public GameState<Move> getState() {
		final List<Move> moves = board.legalMoves();
		final Status status;
		if (moves.isEmpty()) {
			final boolean isCheck = board.isKingAttacked();
			if (isCheck) {
				status = Side.BLACK.equals(board.getSideToMove()) ? Status.WHITE_WON : Status.BLACK_WON;
			} else {
				status = Status.DRAW;
			}
		} else {
			status = Status.PLAYING;
		}
		return new GameState<Move>() {
			@Override
			public Status getStatus() {
				return status;
			}

			@Override
			public int size() {
				return moves.size();
			}

			@Override
			public Move get(int index) {
				return moves.get(index);
			}

			@Override
			public Iterator<Move> iterator() {
				return moves.iterator();
			}
		}; 
	}
	
	@Override
	public UCIMove toUCI(Move move) {
		final String fenSymbol = Piece.NONE.equals(move.getPromotion()) ? null : move.getPromotion().getFenSymbol().toLowerCase();
		return new UCIMove(move.getFrom().name().toLowerCase(), move.getTo().name().toLowerCase(), fenSymbol);
	}
	
	@Override
	public long getZobristKey() {
		return board.getZobristKey();
	}
}

package com.fathzer.jchess.chesslib.eval;

import static com.fathzer.games.Color.*;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import com.fathzer.games.Color;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

public class BasicEvaluator implements IncrementalEvaluator<Move, ChessLibMoveGenerator, BasicEvalState> {
	public static final Map<PieceType, Integer> PIECE_VALUE;
	private Side viewPoint;
	
	static {
		Map<PieceType, Integer> map = new EnumMap<>(PieceType.class);
		map.put(PieceType.QUEEN, 9);
		map.put(PieceType.ROOK, 5);
		map.put(PieceType.BISHOP, 3);
		map.put(PieceType.KNIGHT, 3);
		map.put(PieceType.PAWN, 1);
		map.put(PieceType.KING, 1000);
		PIECE_VALUE = Collections.unmodifiableMap(map);
	}
	
	@Override
	public void setViewPoint(Color viewPoint) {
		if (viewPoint==null) {
			this.viewPoint = null;
		} else {
			this.viewPoint = viewPoint==WHITE ? Side.WHITE : Side.BLACK;
		}
	}
	
	public boolean hasViewPoint() {
		return viewPoint!=null;
	}

	@Override
	public int evaluate(ChessLibMoveGenerator board) {
		if (board.getEvaluationStack()==null) {
			throw new IllegalStateException("increment eval not installed");
		}
		return board.getEvaluationStack().getCurrentEvaluation();
	}

	@Override
	public int rawEvaluate(ChessLibMoveGenerator board) {
		int points = 100*getPoints(board.getBoard());
		if (Side.BLACK==viewPoint || (viewPoint==null && Side.BLACK==board.getBoard().getSideToMove())) {
			points = -points;
		}
		return points;
	}

	public int getPoints(Board board) {
		int points = 0;
		for (Piece p : board.boardToArray()) {
			if (p!=Piece.NONE) {
				int inc = PIECE_VALUE.get(p.getPieceType());
				if (p.getPieceSide()==Side.WHITE) {
					points += inc;
				} else {
					points -= inc;
				}
			}
		}
		return points;
	}
	
	@Override
	public int getIncrement(ChessLibMoveGenerator board, Move move, int previous) {
		if (viewPoint==null) {
			previous = - previous;
		}
		final Board internal = board.getBoard();
		if (!internal.getContext().isCastleMove(move)) {
			int inc = 0;
	        Piece movingPiece = internal.getPiece(move.getFrom());
	        Piece capturedPiece = internal.getPiece(move.getTo());
	        if (!Square.NONE.equals(internal.getEnPassantTarget()) &&
	                PieceType.PAWN.equals(movingPiece.getPieceType()) &&
	                !move.getTo().getFile().equals(move.getFrom().getFile()) &&
	                Piece.NONE.equals(capturedPiece)) {
	            capturedPiece = internal.getPiece(internal.getEnPassantTarget());
	        }
	        if (capturedPiece!=Piece.NONE) {
	        	inc = PIECE_VALUE.get(capturedPiece.getPieceType());
	        }
	        if (move.getPromotion()!=Piece.NONE) {
	        	inc = inc + PIECE_VALUE.get(move.getPromotion().getPieceType())-1;
	        }
			if (inc!=0) {
				inc = inc * 100;
				if (board.getBoard().getSideToMove()!=viewPoint) {
					inc = -inc;
				}
				previous += inc;
			}
		}
		return previous;
	}

	@Override
	public BasicEvalState newState(int score) {
		return new BasicEvalState(score);
	}
}

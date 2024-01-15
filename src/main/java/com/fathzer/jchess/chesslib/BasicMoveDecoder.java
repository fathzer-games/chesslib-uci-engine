package com.fathzer.jchess.chesslib;

import static com.fathzer.chess.utils.Pieces.PAWN;

import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

public final class BasicMoveDecoder {
	private BasicMoveDecoder() {
		super();
	}
	
	public static int getMovingPiece(ChessLibMoveGenerator board, Move move) {
		return toPiece(board.getBoard().getPiece(move.getFrom()));
	}
	
	public static int getCapturedType(ChessLibMoveGenerator board, Move move) {
		final Piece moving = board.getBoard().getPiece(move.getFrom());
		if (Square.NONE!=board.getBoard().getEnPassantTarget() && PieceType.PAWN==moving.getPieceType() &&
        move.getTo().getFile()!=move.getFrom().getFile()) {
			return PAWN; // A pawn is captured
		} else {
			final Piece piece = board.getBoard().getPiece(move.getTo());
			// Be aware of castling in chess 960 where we can consider the king captures its own rook!
			return piece.getPieceSide()!=moving.getPieceSide() ? fromPieceType(piece.getPieceType()) : 0;
		}
	}
	
	public static int getPromotionType(ChessLibMoveGenerator board, Move move) {
		return fromPieceType(move.getPromotion().getPieceType());
	}
	
	private static int fromPieceType(PieceType type) {
		return type==null ? 0 : type.ordinal()+1;
	}
	
	private static int toPiece(Piece piece) {
		final int p = fromPieceType(piece.getPieceType());
		return piece.getPieceSide()==Side.WHITE ? p : -p;
	}

}

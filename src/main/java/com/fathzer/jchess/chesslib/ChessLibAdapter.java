package com.fathzer.jchess.chesslib;

import static com.fathzer.chess.utils.Pieces.*;

import com.fathzer.chess.utils.adapters.MoveAdapter;
import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.chess.utils.adapters.BoardExplorerBuilder;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

public interface ChessLibAdapter extends BoardExplorerBuilder<ChessLibMoveGenerator>, MoveAdapter<Move, ChessLibMoveGenerator> {

	@Override
	default BoardExplorer getExplorer(ChessLibMoveGenerator board) {
		return new ChessLibBoardExplorer(board.getBoard());
	}

	@Override
	default int getMovingPiece(ChessLibMoveGenerator board, Move move) {
		return toPiece(board.getBoard().getPiece(move.getFrom()));
	}
	
	@Override
	default int getCapturedType(ChessLibMoveGenerator board, Move move) {
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
	
	@Override
	default int getPromotionType(ChessLibMoveGenerator board, Move move) {
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

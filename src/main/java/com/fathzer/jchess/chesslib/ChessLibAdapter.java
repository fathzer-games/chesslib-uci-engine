package com.fathzer.jchess.chesslib;

import static com.github.bhlangonijr.chesslib.PieceType.PAWN;

import java.util.stream.Stream;

import com.fathzer.chess.utils.adapters.ColorAdapter;
import com.fathzer.chess.utils.adapters.MoveAdapter;
import com.fathzer.chess.utils.adapters.PieceEvaluator;
import com.fathzer.chess.utils.adapters.PieceStreamer;
import com.fathzer.jchess.chesslib.ai.PieceValues;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

public interface ChessLibAdapter extends ColorAdapter<ChessLibMoveGenerator, Side, Piece>, PieceStreamer<ChessLibMoveGenerator, Piece>, PieceEvaluator<Piece>, MoveAdapter<Move, ChessLibMoveGenerator, Piece> {
	@Override
	default boolean isWhite(Side color) {
		return color==Side.WHITE;
	}

	@Override
	default Side getSideToMove(ChessLibMoveGenerator board) {
		return board.getBoard().getSideToMove();
	}

	@Override
	default Stream<Piece> getPieces(ChessLibMoveGenerator board) {
		return BoardExplorerBuilder.getPieces(board.getBoard());
	}

	@Override
	default Side getColor(Piece piece) {
		return piece.getPieceSide();
	}

	@Override
	default int getValue(Piece piece) {
		return PieceValues.get(piece.getPieceType());
	}

	@Override
	default Piece getMoving(ChessLibMoveGenerator board, Move move) {
		return board.getBoard().getPiece(move.getFrom());
	}

	@Override
	default Piece getCaptured(ChessLibMoveGenerator board, Move move) {
		final Board b = board.getBoard();
		final Piece moving = b.getPiece(move.getFrom());
		if (Square.NONE!=b.getEnPassantTarget() && PAWN==moving.getPieceType() &&
        move.getTo().getFile()!=move.getFrom().getFile()) {
			return moving; // Color of piece doesn't matter
		} else {
			final Piece piece = board.getBoard().getPiece(move.getTo());
			return piece.getPieceSide()!=moving.getPieceSide() ? piece : Piece.NONE;
		}
	}
	
	@Override
	default Piece getPromotion(ChessLibMoveGenerator board, Move move) {
		return move.getPromotion();
	}
	
	@Override
	default boolean isNone(Piece piece) {
		return piece==Piece.NONE;
	}
}

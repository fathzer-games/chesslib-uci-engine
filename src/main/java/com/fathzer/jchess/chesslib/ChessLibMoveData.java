package com.fathzer.jchess.chesslib;

import static com.github.bhlangonijr.chesslib.CastleRight.KING_SIDE;
import static com.github.bhlangonijr.chesslib.CastleRight.QUEEN_SIDE;
import static com.github.bhlangonijr.chesslib.PieceType.KING;
import static com.github.bhlangonijr.chesslib.PieceType.PAWN;

import com.fathzer.chess.utils.adapters.MoveData;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.game.GameContext;
import com.github.bhlangonijr.chesslib.move.Move;

public class ChessLibMoveData implements MoveData<Move, ChessLibMoveGenerator> {
	private Square movingIndex;
	private Piece movingPiece;
	private Square movingDestination;
	private Square capturedIndex;
	private PieceType captured;
	private PieceType promotion;
	private Square castlingRookIndex;
	private Square castlingRookDestinationIndex;

	@Override
	public int getMovingIndex() {
		return getIndex(movingIndex);
	}

	@Override
	public int getMovingPiece() {
		return ChessLibBoardExplorer.toPiece(movingPiece);
	}

	@Override
	public int getMovingDestination() {
		return getIndex(movingDestination);
	}

	@Override
	public int getCapturedType() {
		return ChessLibBoardExplorer.fromPieceType(captured);
	}

	@Override
	public int getCapturedIndex() {
		return getIndex(capturedIndex);
	}

	@Override
	public int getPromotionType() {
		return ChessLibBoardExplorer.fromPieceType(promotion);
	}

	@Override
	public int getCastlingRookIndex() {
		return castlingRookIndex==null ? -1 : getIndex(castlingRookIndex);
	}

	@Override
	public int getCastlingRookDestinationIndex() {
		return getIndex(castlingRookDestinationIndex);
	}

	@Override
	public boolean update(Move move, ChessLibMoveGenerator board) {
		this.movingIndex = move.getFrom();
		this.movingPiece = board.getBoard().getPiece(movingIndex);
		PieceType movingType = movingPiece.getPieceType();
		if (movingType==null) {
			return false;
		} else if (movingType==KING) {
			this.promotion = null;
			final GameContext context = board.getBoard().getContext();
			if (context.isCastleMove(move)) {
				final Side side = board.getBoard().getSideToMove();
				this.captured = null;
				this.movingDestination = move.getTo();
				final Move rookMove = context.getRookCastleMove(side, context.isKingSideCastle(move) ? KING_SIDE :
                    QUEEN_SIDE);
				this.castlingRookIndex = rookMove.getFrom();
				this.castlingRookDestinationIndex = rookMove.getTo();
			} else {
				this.castlingRookIndex = null;
				this.movingDestination = move.getTo();
				this.captured = board.getBoard().getPiece(movingDestination).getPieceType();
				if (this.captured!=null) {
					this.capturedIndex = this.movingDestination;
				}
			}
		} else {
			// Not a king move => no castling
			this.castlingRookIndex=null;
			this.movingDestination = move.getTo();
			if (movingType==PAWN && movingDestination==board.getBoard().getEnPassant()) {
				this.captured = PAWN;
				this.capturedIndex = board.getBoard().getEnPassantTarget();
				this.promotion = null;
			} else {
				this.promotion = move.getPromotion().getPieceType();
				this.captured = board.getBoard().getPiece(movingDestination).getPieceType();
				if (this.captured!=null) {
					this.capturedIndex = this.movingDestination;
				}
			}
		}
		return true;
	}
	
	int getIndex(Square sq) {
		return 8*(7-sq.getRank().ordinal())+sq.getFile().ordinal();
	}
}

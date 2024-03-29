package com.fathzer.jchess.chesslib.ai.eval.hbpg2;

import static com.fathzer.chess.utils.Pieces.KING;
import static com.fathzer.chess.utils.Pieces.PAWN;
import static com.fathzer.chess.utils.Pieces.ROOK;
import static com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2SimplifiedEvaluatorBase.getPositionValue;
import static com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2SimplifiedEvaluatorBase.getRawValue;

import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.chess.utils.adapters.MoveData;

/** The current state of a {@link Hb2AbstractIncrementalSimplifiedEvaluator}
 */
public class Hb2IncrementalState extends Hb2BasicState {
	Hb2IncrementalState() {
		super();
	}
	
	Hb2IncrementalState(BoardExplorer exp) {
		super(exp);
	}

	void update(MoveData<?,?> move) {
		points += getIncrement(move);
	}

	private int getIncrement(MoveData<?,?> move) {
		final boolean isBlack = move.getMovingPiece()<0;
		int moving = Math.abs(move.getMovingPiece());
		final int movingIndex = move.getMovingIndex();
		int inc;
		if (moving==KING) {
			// The position value of kings is not evaluated incrementally
			int rookIndex = move.getCastlingRookIndex();
			if (rookIndex>=0) {
				// It's a castling move, update rook positions values
				inc =  getPositionValue(ROOK, isBlack, move.getCastlingRookDestinationIndex()) - getPositionValue(ROOK, isBlack, rookIndex);
			} else {
				inc = doCapture(isBlack, move);
			}
			// Update king's position
			if (isBlack) {
				this.blackKingIndex = move.getMovingDestination();
			} else {
				this.whiteKingIndex = move.getMovingDestination();
			}
		} else {
			// Remove the position value of the moving piece
			inc = - getPositionValue(moving, isBlack, movingIndex);
			final int promoType = move.getPromotionType();
			if (promoType!=0) {
				// If promotion, add raw value points, update phase
				inc += getRawValue(promoType)-getRawValue(PAWN);
				moving = promoType;
				add(isBlack ? -promoType : promoType);
			}
			inc += doCapture(isBlack, move);
			// Adds the position value of the 
			inc += getPositionValue(moving, isBlack, move.getMovingDestination());
		}
		return isBlack ? -inc : +inc;
	}
	
	private int doCapture(boolean isBlack, MoveData<?,?> move) {
		int captured = move.getCapturedType();
		if (captured!=0) {
			// A piece was captured
			// Update the phase detector
			remove(isBlack ? captured : -captured);
			// Then add its raw value and its position value
			return getRawValue(captured) + getPositionValue(captured, !isBlack, move.getCapturedIndex());
		} else {
			return 0;
		}
	}
}
package com.fathzer.jchess.chesslib.ai.eval.hbpg;

import static com.fathzer.chess.utils.Pieces.KING;

import com.fathzer.chess.utils.adapters.BoardExplorer;

/** The state of the evaluator.
 */
class HbBasicState extends HbFastPhaseDetector {
	int points;
	int whiteKingIndex;
	int blackKingIndex;
	
	HbBasicState() {
		super();
	}
	
	void copyTo(HbBasicState other) {
		super.copyTo(other);
		other.points = points;
		other.blackKingIndex = blackKingIndex;
		other.whiteKingIndex = whiteKingIndex;
	}
	
	HbBasicState(BoardExplorer explorer) {
		this.points = 0;
		do {
			final int p = explorer.getPiece();
			add(p);
			final int kind = Math.abs(p);
			final int index = explorer.getIndex();
			final boolean isBlack = p<0;
			if (kind!=KING) {
				int inc = HbSimplifiedEvaluatorBase.getRawValue(kind);
				inc += HbSimplifiedEvaluatorBase.getPositionValue(kind, isBlack, index);
				if (isBlack) {
					points -= inc;
				} else {
					points += inc;
				}
			} else if (isBlack) {
				this.blackKingIndex = index;
			} else {
				this.whiteKingIndex = index;
			}
		} while (explorer.next());
	}

	int evaluateAsWhite() {
		return points + HbSimplifiedEvaluatorBase.getKingPositionsValue(whiteKingIndex, blackKingIndex, getPhase());
	}
}
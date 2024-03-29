package com.fathzer.jchess.chesslib.ai.eval.hbpg2;

import static com.fathzer.chess.utils.Pieces.KING;

import com.fathzer.chess.utils.adapters.BoardExplorer;

/** The state of the evaluator.
 */
class Hb2BasicState extends Hb2FastPhaseDetector {
	int points;
	int whiteKingIndex;
	int blackKingIndex;
	int computedPhase;
	
	
	Hb2BasicState() {
		super();
	}
	
	void copyTo(Hb2BasicState other) {
		super.copyTo(other);
		other.points = points;
		other.blackKingIndex = blackKingIndex;
		other.whiteKingIndex = whiteKingIndex;
		other.computedPhase = computedPhase;
	}
	
	Hb2BasicState(BoardExplorer explorer) {
		
		this.points = 0;
		this.computedPhase = 0;
		do {
			final int p = explorer.getPiece();
			add(p);
			final int kind = Math.abs(p);
			final int index = explorer.getIndex();
			final boolean isBlack = p<0;
			if (kind!=KING) {
				int inc = Hb2SimplifiedEvaluatorBase.getRawValue(kind);
				inc += Hb2SimplifiedEvaluatorBase.getPositionValue(kind, isBlack, index);
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
			
			computedPhase += Hb2Phase.getPhaseValue(kind);
		} while (explorer.next());
	}


	int evaluateAsWhite() {
		// for the time being (29/03/2024) points are the same in Mg and Eg except for the king preferred squares
		// but it's gonna change very soon
		int phase = getPhaseForTaperedEval(computedPhase);
		int evalMg = points + Hb2SimplifiedEvaluatorBase.getKingPositionsValueMg(whiteKingIndex, blackKingIndex);
		int evalEg = points + Hb2SimplifiedEvaluatorBase.getKingPositionsValueEg(whiteKingIndex, blackKingIndex);
		return ((evalMg * phase + evalEg * (Hb2Phase.NB_INCR_PHASE-phase)) / Hb2Phase.NB_INCR_PHASE);
	}
}
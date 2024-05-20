package com.fathzer.jchess.chesslib.ai.eval.hbpg2;

import static com.fathzer.chess.utils.Pieces.KING;

import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.github.bhlangonijr.chesslib.Board;

/** The state of the evaluator.
 */
class Hb2BasicState extends Hb2FastPhaseDetector {
	int pointsMg;
	int pointsEg;
	int whiteKingIndex;
	int blackKingIndex;
	int computedPhase;
	Board board;
	
	
	Hb2BasicState() {
		super();
	}
	
	void copyTo(Hb2BasicState other) {
		super.copyTo(other);
		other.pointsMg = pointsMg;
		other.blackKingIndex = blackKingIndex;
		other.whiteKingIndex = whiteKingIndex;
		other.computedPhase = computedPhase;
		other.board = board;
		
	}
	
	Hb2BasicState(BoardExplorer explorer, Board board) {
		this.board = board;
		this.pointsMg = 0;
		this.computedPhase = 0;
		do {
			final int p = explorer.getPiece();
//			add(p);
			final int kind = Math.abs(p);
			final int index = explorer.getIndex();
			final boolean isBlack = p<0;
			if (kind!=KING) {
				int incMg = Hb2SimplifiedEvaluatorBase.getRawValueMg(kind);
				int incEg = Hb2SimplifiedEvaluatorBase.getRawValueMg(kind);
				if (isBlack) {
					pointsMg -= incMg;
					pointsEg -= incEg;
				} else {
					pointsMg += incMg;
					pointsEg += incEg;
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
		// pointsMg = material only! The white material minus the black material in the middlegame.
		// pointsEg = material only! The white material minus the black material in the endgame.
		int phase = getPhaseForTaperedEval(computedPhase);
		int pointsPosMg = Hb2SimplifiedEvaluatorBase.getPositionValueMg(board);
		int pointsPosEg = Hb2SimplifiedEvaluatorBase.getPositionValueEg(board);
		int evalMg = pointsMg + pointsPosMg + Hb2SimplifiedEvaluatorBase.getKingPositionsValueMg(whiteKingIndex, blackKingIndex);
		int evalEg = pointsEg + pointsPosEg+ Hb2SimplifiedEvaluatorBase.getKingPositionsValueEg(whiteKingIndex, blackKingIndex);
		return ((evalMg * phase + evalEg * (Hb2Phase.NB_INCR_PHASE-phase)) / Hb2Phase.NB_INCR_PHASE);
	}
}
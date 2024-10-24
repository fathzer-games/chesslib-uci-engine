package com.fathzer.jchess.chesslib.ai.eval.hbpg2;

import static com.fathzer.chess.utils.Pieces.KING;

import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.github.bhlangonijr.chesslib.Board;

/** The elementary state of the evaluator.
 */
 
class Hb2ElementaryBasicState {
	
	int pointsMg;
	int pointsEg;
	int pointsPosMg;
	int pointsPosEg;
	int whiteKingIndex;
	int blackKingIndex;
	int computedPhase;
	Board board;
	
	
	Hb2ElementaryBasicState() {
		super();
	}
	
	public void copyTo(Hb2ElementaryBasicState other) {
		
		other.pointsMg = pointsMg;
		other.pointsEg= pointsEg;
		other.pointsPosMg = pointsPosMg;
		other.pointsPosEg= pointsPosEg;
		other.blackKingIndex = blackKingIndex;
		other.whiteKingIndex = whiteKingIndex;
		other.computedPhase = computedPhase;
		other.board = board;
		
	}
	
	Hb2ElementaryBasicState(BoardExplorer explorer, Board board) {
		this.board = board;
		this.pointsMg = 0;
		this.pointsEg = 0;
		this.pointsPosMg = 0;
		this.pointsPosEg = 0;
		this.computedPhase = 0;
		do {
			final int p = explorer.getPiece();

			final int kind = Math.abs(p);
			final int index = explorer.getIndex();
			final boolean isBlack = p<0;
			if (kind!=KING) {
				int incMg = Hb2SimplifiedEvaluatorBase.getRawValueMg(kind);
				int incEg = Hb2SimplifiedEvaluatorBase.getRawValueEg(kind);
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
			
			
			
			if (kind!=KING) {
				int incPosMg = Hb2SimplifiedEvaluatorBase.getPositionValueMg(kind, isBlack, index);
				int incPosEg = Hb2SimplifiedEvaluatorBase.getPositionValueEg(kind, isBlack, index);
				if (isBlack) {
					pointsPosMg -= incPosMg;
					pointsPosEg -= incPosEg;
				} else {
					pointsPosMg += incPosMg;
					pointsPosEg += incPosEg;
				}
			}
			computedPhase += Hb2Phase.getPhaseValue(kind);
		} while (explorer.next());
	}



	
//	int getPhaseForTaperedEval(int computedPhaseValue) {	
//		return (Math.min(computedPhaseValue, Hb2Phase.PHASE_UPPER_BOUND));
//	}
	
	
//	int evaluateAsWhite() {
//
//		// pointsMg = material only! The white material minus the black material in the middlegame.
//		// pointsEg = material only! The white material minus the black material in the endgame.
////		int phase = Hb2Phase.getPhaseForTaperedEval(computedPhase);
//		// gets the borned phase: necessary for the tapered evaluation
//		int phase= (computedPhase > Hb2Phase.PHASE_UPPER_BOUND?Hb2Phase.PHASE_UPPER_BOUND:computedPhase);
////		int pointsPosMg = Hb2SimplifiedEvaluatorBase.getPositionValueMg(board);
////		int pointsPosEg = Hb2SimplifiedEvaluatorBase.getPositionValueEg(board);
//		int evalMg = pointsMg + pointsPosMg + Hb2SimplifiedEvaluatorBase.getKingPositionsValueMg(whiteKingIndex, blackKingIndex);
//		int evalEg = pointsEg + pointsPosEg+ Hb2SimplifiedEvaluatorBase.getKingPositionsValueEg(whiteKingIndex, blackKingIndex);
//		return ((evalMg * phase + evalEg * (Hb2Phase.NB_INCR_PHASE-phase)) / Hb2Phase.NB_INCR_PHASE);
//	}



}
package com.fathzer.jchess.chesslib.ai.eval.hbpg2;

/** A class that tracks the current phase.
 */
class Hb2FastPhaseDetector {

//	private long state;

	
	void add(int piece) {
//		state += PIECE_KIND_TO_VALUES[piece + KING];
	}
	
	void remove(int piece) {
//		state -= PIECE_KIND_TO_VALUES[piece + KING];
	}
	
	int getPhaseForTaperedEval(int computedPhaseValue) {	
		return (Math.min(computedPhaseValue, Hb2Phase.PHASE_UPPER_BOUND));
	}
	
	
	


	void copyTo(Hb2FastPhaseDetector other) {
//		other.state = state;
	}
}
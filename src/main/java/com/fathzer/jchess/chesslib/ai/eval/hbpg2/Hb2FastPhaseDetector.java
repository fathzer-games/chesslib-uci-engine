package com.fathzer.jchess.chesslib.ai.eval.hbpg2;

/** A class that tracks the current phase.
 */
class Hb2FastPhaseDetector {

//	private long state;

	
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
//	void add(int piece) {
////		state += PIECE_KIND_TO_VALUES[piece + KING];
//	}
//	
//	void remove(int piece) {
////		state -= PIECE_KIND_TO_VALUES[piece + KING];
//	}
=======
	void add(int piece) {
//		state += PIECE_KIND_TO_VALUES[piece + KING];
	}
	
	void remove(int piece) {
//		state -= PIECE_KIND_TO_VALUES[piece + KING];
	}
>>>>>>> d32a00b Hb2MyFirstEvaluator: the beginning. Compared to the SimplifiedEvaluator the evaluation function is tapered (scale = 64)
=======
	void add(int piece) {
//		state += PIECE_KIND_TO_VALUES[piece + KING];
	}
	
	void remove(int piece) {
//		state -= PIECE_KIND_TO_VALUES[piece + KING];
	}
>>>>>>> 1477ae6 Hb2MyFirstEvaluator: the beginning. Compared to the SimplifiedEvaluator the evaluation function is tapered (scale = 64)
=======
//	void add(int piece) {
////		state += PIECE_KIND_TO_VALUES[piece + KING];
//	}
//	
//	void remove(int piece) {
////		state -= PIECE_KIND_TO_VALUES[piece + KING];
//	}
>>>>>>> 8fcce8e Valeurs différentes du matériel en final rendues possibles
	
	int getPhaseForTaperedEval(int computedPhaseValue) {	
		return (Math.min(computedPhaseValue, Hb2Phase.PHASE_UPPER_BOUND));
	}
	
	
	


	void copyTo(Hb2FastPhaseDetector other) {
//		other.state = state;
	}
}
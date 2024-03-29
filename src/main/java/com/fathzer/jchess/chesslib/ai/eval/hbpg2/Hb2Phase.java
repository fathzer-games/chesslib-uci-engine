package com.fathzer.jchess.chesslib.ai.eval.hbpg2;

public class Hb2Phase {
	
	// Beware, what follows  has nothing to do with com.fathzer.chess.utils.Pieces.VALUES, since the Queen is evaluated 9 instead of 10, in Pieces.VALUES
	// Pieces.VALUE is useful only for comparing / ordering moves, by the way
	protected static final int[] PHASE_VALUES = new int[] {0,1,3,3,5,10,0};
	
	// The game phase will be an int value  in the [0,64] interval
	public static final int NB_INCR_PHASE = 64; // Pawns do not count. 3 for B and N, 5 for the R,  10 for the Q. White has therefore 32, and black likewise. Total = 64.
	
	// Very useful for the tapered evaluation
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
	// Ethereal did not even think about it. But many other engines do.
=======
	// Ethereal did not even think about it. But many others engines do.
>>>>>>> d32a00b Hb2MyFirstEvaluator: the beginning. Compared to the SimplifiedEvaluator the evaluation function is tapered (scale = 64)
=======
	// Ethereal did not even think about it. But many others engines do.
>>>>>>> 1477ae6 Hb2MyFirstEvaluator: the beginning. Compared to the SimplifiedEvaluator the evaluation function is tapered (scale = 64)
	// For example, If white or black or both have 2 queens for example, without an upper  bound, the phase could exceed NB_INCR_PHASE, which would ruin the tapered evaluation
	public static final int PHASE_UPPER_BOUND = NB_INCR_PHASE;
	
	
	private int phase;
	
	public Hb2Phase(int computedPhaseValue) {
		this.phase = computedPhaseValue;
	}


	public static int getPhaseValue(int indexPieceType) {
		return(PHASE_VALUES[indexPieceType]);
	}


	public int getPhase() {
		return phase;
	}


	public void setPhase(int phase) {
		this.phase = phase;
	}
	
	

}

package com.fathzer.jchess.chesslib.ai.eval.hb;

/** The state of a simplified evaluator.
 */
public class HbPestoState {
	int mgPoints;
	int egPoints;
	int phasePoints;
	
	
	HbPestoState() {
		super();
	}
	
	void copyTo(HbPestoState other) {
		other.mgPoints = mgPoints;
		other.egPoints = egPoints;
		other.phasePoints = phasePoints;
	}
	
	void clear() {
		mgPoints = 0;
		egPoints = 0;
		phasePoints = 0;
	}
}
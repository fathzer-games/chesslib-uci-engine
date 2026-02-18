package com.fathzer.jchess.chesslib.ai.eval.hb;



/** The state of a simplified evaluator.
 */
public class HbPestoState {
	
	public class HbComplementsPestoState {
		
		public int mgPassedPawnsBonus;
		public int egPassedPawnsBonus;
		public HbComplementsPestoState() {
			super();
			mgPassedPawnsBonus = 1;
		}
		
		
	}
	int mgPoints;
	int egPoints;
	public HbComplementsPestoState hbComplPs;
	
	int phasePoints;
	
	
	
	public HbPestoState() {
		super();
		hbComplPs = new HbComplementsPestoState();
	}
	
	public int getMgPoints() {
		return mgPoints;
	}

	public void setMgPoints(int mgPoints) {
		this.mgPoints = mgPoints;
	}

	public int getEgPoints() {
		return egPoints;
	}

	public void setEgPoints(int egPoints) {
		this.egPoints = egPoints;
	}

	public HbComplementsPestoState getHnComplPs() {
		return hbComplPs;
	}

	public void setHnComplPs(HbComplementsPestoState hnComplPs) {
		this.hbComplPs = hnComplPs;
	}

	public int getPhasePoints() {
		return phasePoints;
	}

	public void setPhasePoints(int phasePoints) {
		this.phasePoints = phasePoints;
	}

	public void copyTo(HbPestoState other) {
		other.mgPoints = mgPoints;
		other.egPoints = egPoints;
		other.phasePoints = phasePoints;
		other.hbComplPs.mgPassedPawnsBonus = hbComplPs.mgPassedPawnsBonus;
		other.hbComplPs.egPassedPawnsBonus = hbComplPs.egPassedPawnsBonus;
	}
	
	void clear() {
		mgPoints = 0;
		egPoints = 0;
		phasePoints = 0;
		hbComplPs.mgPassedPawnsBonus =0;
		hbComplPs.egPassedPawnsBonus =0;
				
	}
	
	
}
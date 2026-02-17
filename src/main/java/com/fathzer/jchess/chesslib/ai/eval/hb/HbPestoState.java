package com.fathzer.jchess.chesslib.ai.eval.hb;



/** The state of a simplified evaluator.
 */
public class HbPestoState {
	
	class HpComplementsPestoState {
		
		int mgPassedPawnsBonus;
		int egPassedPawnsBonus;
		public HpComplementsPestoState() {
			super();
			mgPassedPawnsBonus = 1;
		}
		
		
	}
	int mgPoints;
	int egPoints;
	HpComplementsPestoState hnComplPs;
	
	int phasePoints;
	
	
	
	HbPestoState() {
		super();
		hnComplPs = new HpComplementsPestoState();
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

	public HpComplementsPestoState getHnComplPs() {
		return hnComplPs;
	}

	public void setHnComplPs(HpComplementsPestoState hnComplPs) {
		this.hnComplPs = hnComplPs;
	}

	public int getPhasePoints() {
		return phasePoints;
	}

	public void setPhasePoints(int phasePoints) {
		this.phasePoints = phasePoints;
	}

	void copyTo(HbPestoState other) {
		other.mgPoints = mgPoints;
		other.egPoints = egPoints;
		other.phasePoints = phasePoints;
		other.hnComplPs.mgPassedPawnsBonus = hnComplPs.mgPassedPawnsBonus;
		other.hnComplPs.egPassedPawnsBonus = hnComplPs.egPassedPawnsBonus;
	}
	
	void clear() {
		mgPoints = 0;
		egPoints = 0;
		phasePoints = 0;
	}
	
	public static void main(String[] args) {
		HbPestoState lHbPestoState = new HbPestoState();
		System.out.println(lHbPestoState.hnComplPs.mgPassedPawnsBonus);
		System.out.println(lHbPestoState.hnComplPs.egPassedPawnsBonus);
		(lHbPestoState.hnComplPs.mgPassedPawnsBonus)++;
		System.out.println(lHbPestoState.hnComplPs.mgPassedPawnsBonus);
		System.out.println("STALINE");
		HbPestoState HbPestoState2 = new HbPestoState();
		lHbPestoState.copyTo(HbPestoState2);
		System.out.println(HbPestoState2.hnComplPs.mgPassedPawnsBonus);
		System.out.println(HbPestoState2.hnComplPs.egPassedPawnsBonus);

	}
}
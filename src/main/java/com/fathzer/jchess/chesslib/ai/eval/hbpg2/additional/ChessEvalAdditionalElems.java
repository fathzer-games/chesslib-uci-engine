package com.fathzer.jchess.chesslib.ai.eval.hbpg2.additional;


import com.fathzer.chess.utils.adapters.MoveData;
import com.github.bhlangonijr.chesslib.Board;


/** This class contains all the chess evaluation stuff that is not in the elementary state of the evaluator.
 */
 
public class ChessEvalAdditionalElems {
	
	private PawnsStrucEval pawnsStructEval;
	private MobilityEval mobilityEval;
	
	public ChessEvalAdditionalElems(ChessEvalAdditionalElems ceae) {
		pawnsStructEval = new PawnsStrucEval(ceae.pawnsStructEval);
		mobilityEval = new MobilityEval(ceae.mobilityEval);
	}
	
	
	public ChessEvalAdditionalElems(Board board) {
		pawnsStructEval = new PawnsStrucEval( board);
		mobilityEval = new MobilityEval( board);
	}
	
//	public void copyTo(ChessEvalAdditionalElems other) {
//		pawnsStructEval.copyTo(other.pawnsStructEval);
//		mobilityEval.copyTo(other.mobilityEval);
//		
//	}
	
	public void updateEvalAdditionalElems(MoveData<?,?> move, Board board) {
		pawnsStructEval.updatePawnsStructEval(move, board);
		mobilityEval.computeMobility(move, board);
	}

	public PawnsStrucEval getPawnsStructEval() {
		return pawnsStructEval;
	}

	public void setPawnsStructEval(PawnsStrucEval pawnsStructEval) {
		this.pawnsStructEval = pawnsStructEval;
	}
	
	public int getContribMg() {
		int contrib = 0;
		contrib+= pawnsStructEval.getContribMg();
		contrib+= mobilityEval.getContribMg();
		return(contrib);
		
	}
	
	public int getContribEg() {
		int contrib = 0;
		contrib+= pawnsStructEval.getContribEg();
		contrib+= mobilityEval.getContribEg();
		return(contrib);
		
	}
	
	

}

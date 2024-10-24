package com.fathzer.jchess.chesslib.ai.eval.hbpg2.additional;

import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.github.bhlangonijr.chesslib.Board;


/** This class contains all the chess evaluation stuff that is not in the elementary state of the evaluator.
 */
 
public class ChessEvalAdditionalElems {
	
	private PawnsStrucEval pawnsStructEval;
	
	public ChessEvalAdditionalElems(ChessEvalAdditionalElems ceae) {
		pawnsStructEval = new PawnsStrucEval(ceae.pawnsStructEval);
	}
	
	
	public ChessEvalAdditionalElems(BoardExplorer explorer, Board board) {
		pawnsStructEval = new PawnsStrucEval(explorer, board);
	}
	
	public void copyTo(ChessEvalAdditionalElems other) {
		pawnsStructEval.copyTo(other.pawnsStructEval);
		
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
		return(contrib);
		
	}
	
	public int getContribEg() {
		int contrib = 0;
		contrib+= pawnsStructEval.getContribEg();
		return(contrib);
		
	}
	
	

}

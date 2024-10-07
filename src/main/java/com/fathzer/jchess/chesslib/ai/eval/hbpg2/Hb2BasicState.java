package com.fathzer.jchess.chesslib.ai.eval.hbpg2;



import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.github.bhlangonijr.chesslib.Board;

/** The state of the evaluator.
 */
class Hb2BasicState extends Hb2ElementaryBasicState {
	
	
	Hb2BasicState() {
		super();
	}
	
	void copyTo(Hb2BasicState other) {
		super.copyTo(other);
//		other.pointsMg = pointsMg;
//		other.pointsEg= pointsEg;
//		other.pointsPosMg = pointsPosMg;
//		other.pointsPosEg= pointsPosEg;
//		other.blackKingIndex = blackKingIndex;
//		other.whiteKingIndex = whiteKingIndex;
//		other.computedPhase = computedPhase;
//		other.board = board;
		
	}
	

	
	Hb2BasicState(BoardExplorer explorer, Board board) {
		super(explorer, board);
	
	}



}
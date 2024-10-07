package com.fathzer.jchess.chesslib.ai.eval.hbpg2;



import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.jchess.chesslib.ai.eval.hbpg2.additional.ChessEvalAdditionalElems;
import com.github.bhlangonijr.chesslib.Board;

/** The state of the evaluator.
 */
class Hb2BasicState extends Hb2ElementaryBasicState {
	private ChessEvalAdditionalElems chessEvalAdditionalElems;
	
	Hb2BasicState() {
		super();
	}
	
	public void copyTo(Hb2BasicState other) {
		super.copyTo(other);
		chessEvalAdditionalElems.copyTo(other.chessEvalAdditionalElems);
		
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
		chessEvalAdditionalElems = new ChessEvalAdditionalElems(explorer, board);
		
	
	}

	public ChessEvalAdditionalElems getChessEvalAdditionalElems() {
		return chessEvalAdditionalElems;
	}

	public void setChessEvalAdditionalElems(ChessEvalAdditionalElems chessEvalAdditionalElems) {
		this.chessEvalAdditionalElems = chessEvalAdditionalElems;
	}



}
package com.fathzer.jchess.chesslib.ai.eval.hbpg2;



import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.jchess.chesslib.ChessLibBoardExplorer;
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
		other.chessEvalAdditionalElems = new ChessEvalAdditionalElems(this.chessEvalAdditionalElems);
		
		
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
		chessEvalAdditionalElems = new ChessEvalAdditionalElems(new ChessLibBoardExplorer(board), board);
		
	
	}

	public ChessEvalAdditionalElems getChessEvalAdditionalElems() {
		return chessEvalAdditionalElems;
	}

	public void setChessEvalAdditionalElems(ChessEvalAdditionalElems chessEvalAdditionalElems) {
		this.chessEvalAdditionalElems = chessEvalAdditionalElems;
	}

	
	int evaluateAsWhite() {

		// pointsMg = material only! The white material minus the black material in the middlegame.
		// pointsEg = material only! The white material minus the black material in the endgame.
//		int phase = Hb2Phase.getPhaseForTaperedEval(computedPhase);
		// gets the borned phase: necessary for the tapered evaluation
		int phase= (computedPhase > Hb2Phase.PHASE_UPPER_BOUND?Hb2Phase.PHASE_UPPER_BOUND:computedPhase);
//		int pointsPosMg = Hb2SimplifiedEvaluatorBase.getPositionValueMg(board);
//		int pointsPosEg = Hb2SimplifiedEvaluatorBase.getPositionValueEg(board);
	
		int evalMg = pointsMg + pointsPosMg + Hb2SimplifiedEvaluatorBase.getKingPositionsValueMg(whiteKingIndex, blackKingIndex) + chessEvalAdditionalElems.getContribMg();
		int evalEg = pointsEg + pointsPosEg+ Hb2SimplifiedEvaluatorBase.getKingPositionsValueEg(whiteKingIndex, blackKingIndex) + chessEvalAdditionalElems.getContribEg();
		
		return ((evalMg * phase + evalEg * (Hb2Phase.NB_INCR_PHASE-phase)) / Hb2Phase.NB_INCR_PHASE);
	}


}
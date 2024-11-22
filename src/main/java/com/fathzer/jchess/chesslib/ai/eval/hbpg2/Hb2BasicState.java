package com.fathzer.jchess.chesslib.ai.eval.hbpg2;



import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.jchess.chesslib.ChessLibBoardExplorer;
import com.fathzer.jchess.chesslib.ai.eval.hbpg2.additional.ChessEvalAdditionalElems;
import com.github.bhlangonijr.chesslib.Board;

/** The state of the evaluator.
 */
class Hb2BasicState extends Hb2ElementaryBasicState {
	protected ChessEvalAdditionalElems chessEvalAdditionalElems;
	
	Hb2BasicState() {
		super();
	}
	
	public void copyTo(Hb2BasicState other) {
		super.copyTo(other);
		other.chessEvalAdditionalElems = new ChessEvalAdditionalElems(this.chessEvalAdditionalElems);
		
		

		
	}
	

	
	Hb2BasicState(BoardExplorer explorer, Board board) {
		super(explorer, board);
		chessEvalAdditionalElems = new ChessEvalAdditionalElems( board);
		
	
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
		int posKingMg = Hb2SimplifiedEvaluatorBase.getKingPositionsValueMg(whiteKingIndex, blackKingIndex);
		int posKingEg = Hb2SimplifiedEvaluatorBase.getKingPositionsValueEg(whiteKingIndex, blackKingIndex);
		int evalMg = pointsMg + pointsPosMg + posKingMg + chessEvalAdditionalElems.getContribMg();
		int evalEg = pointsEg + pointsPosEg+ posKingEg + chessEvalAdditionalElems.getContribEg();
		
		return ((evalMg * phase + evalEg * (Hb2Phase.NB_INCR_PHASE-phase)) / Hb2Phase.NB_INCR_PHASE);
	}


}
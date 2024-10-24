package com.fathzer.jchess.chesslib.ai.eval.hbpg2;



import com.fathzer.chess.utils.adapters.BoardExplorer;
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
=======
=======
import com.fathzer.jchess.chesslib.ChessLibBoardExplorer;
>>>>>>> 5ae67a7 Doubled pawns are hit with a penalty
import com.fathzer.jchess.chesslib.ai.eval.hbpg2.additional.ChessEvalAdditionalElems;
>>>>>>> b73e44a Evaluation de la structure de pions: calcul du nombre de pions noirs par colonne, du nombre de pions blancs par colonne. Ca servira pour les pions doublés, les pions passés, etc...
import com.github.bhlangonijr.chesslib.Board;

/** The state of the evaluator.
 */
<<<<<<< Upstream, based on origin/main
class Hb2BasicState extends Hb2FastPhaseDetector {
	int pointsMg;
	int pointsEg;
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
	int pointsPosMg;
	int pointsPosEg;
	int whiteKingIndex;
	int blackKingIndex;
	int computedPhase;
	Board board;
=======
class Hb2BasicState extends Hb2ElementaryBasicState {
<<<<<<< Upstream, based on origin/main
>>>>>>> f67222a nettoyage, refactoring
	
=======
	private ChessEvalAdditionalElems chessEvalAdditionalElems;
>>>>>>> b73e44a Evaluation de la structure de pions: calcul du nombre de pions noirs par colonne, du nombre de pions blancs par colonne. Ca servira pour les pions doublés, les pions passés, etc...
	
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


<<<<<<< Upstream, based on origin/main
		// pointsMg = material only! The white material minus the black material in the middlegame.
		// pointsEg = material only! The white material minus the black material in the endgame.
		int phase = getPhaseForTaperedEval(computedPhase);
//		int pointsPosMg = Hb2SimplifiedEvaluatorBase.getPositionValueMg(board);
//		int pointsPosEg = Hb2SimplifiedEvaluatorBase.getPositionValueEg(board);
		int evalMg = pointsMg + pointsPosMg + Hb2SimplifiedEvaluatorBase.getKingPositionsValueMg(whiteKingIndex, blackKingIndex);
		int evalEg = pointsEg + pointsPosEg+ Hb2SimplifiedEvaluatorBase.getKingPositionsValueEg(whiteKingIndex, blackKingIndex);
=======
=======
import com.github.bhlangonijr.chesslib.Board;
>>>>>>> 9522cc2 Valeurs différentes pour les positions des pions en Mg et Eg. L'incrémental concerne désormais seulement le matériel

/** The state of the evaluator.
 */
class Hb2BasicState extends Hb2FastPhaseDetector {
	int points;
=======
>>>>>>> 8fcce8e Valeurs différentes du matériel en final rendues possibles
=======
	int pointsPosMg;
	int pointsPosEg;
>>>>>>> 60c3451 Hb2SimplifiedEvaluator: incrémental achevé et propre pour position des pièces et matériel. Avec tapered eval
	int whiteKingIndex;
	int blackKingIndex;
	int computedPhase;
	Board board;
	
	
	Hb2BasicState() {
		super();
	}
	
	void copyTo(Hb2BasicState other) {
		super.copyTo(other);
		other.pointsMg = pointsMg;
		other.pointsEg= pointsEg;
		other.pointsPosMg = pointsPosMg;
		other.pointsPosEg= pointsPosEg;
		other.blackKingIndex = blackKingIndex;
		other.whiteKingIndex = whiteKingIndex;
		other.computedPhase = computedPhase;
		other.board = board;
		
	}
	

	
	Hb2BasicState(BoardExplorer explorer, Board board) {
		this.board = board;
		this.pointsMg = 0;
		this.pointsEg = 0;
		this.pointsPosMg = 0;
		this.pointsPosEg = 0;
		this.computedPhase = 0;
		do {
			final int p = explorer.getPiece();
//			add(p);
			final int kind = Math.abs(p);
			final int index = explorer.getIndex();
			final boolean isBlack = p<0;
			if (kind!=KING) {
				int incMg = Hb2SimplifiedEvaluatorBase.getRawValueMg(kind);
				int incEg = Hb2SimplifiedEvaluatorBase.getRawValueEg(kind);
				if (isBlack) {
					pointsMg -= incMg;
					pointsEg -= incEg;
				} else {
					pointsMg += incMg;
					pointsEg += incEg;
				}
			} else if (isBlack) {
				this.blackKingIndex = index;
			} else {
				this.whiteKingIndex = index;
			}
			
			
			
			if (kind!=KING) {
				int incPosMg = Hb2SimplifiedEvaluatorBase.getPositionValueMg(kind, isBlack, index);
				int incPosEg = Hb2SimplifiedEvaluatorBase.getPositionValueEg(kind, isBlack, index);
				if (isBlack) {
					pointsPosMg -= incPosMg;
					pointsPosEg -= incPosEg;
				} else {
					pointsPosMg += incPosMg;
					pointsPosEg += incPosEg;
				}
			}
			computedPhase += Hb2Phase.getPhaseValue(kind);
		} while (explorer.next());
	}


	int evaluateAsWhite() {

		// pointsMg = material only! The white material minus the black material in the middlegame.
		// pointsEg = material only! The white material minus the black material in the endgame.
		int phase = getPhaseForTaperedEval(computedPhase);
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
		int evalMg = points + Hb2SimplifiedEvaluatorBase.getKingPositionsValueMg(whiteKingIndex, blackKingIndex);
		int evalEg = points + Hb2SimplifiedEvaluatorBase.getKingPositionsValueEg(whiteKingIndex, blackKingIndex);
>>>>>>> d32a00b Hb2MyFirstEvaluator: the beginning. Compared to the SimplifiedEvaluator the evaluation function is tapered (scale = 64)
=======
		int pointsPosMg = Hb2SimplifiedEvaluatorBase.getPositionValueMg(board);
		int pointsPosEg = Hb2SimplifiedEvaluatorBase.getPositionValueEg(board);
<<<<<<< Upstream, based on origin/main
		int evalMg = points + pointsPosMg + Hb2SimplifiedEvaluatorBase.getKingPositionsValueMg(whiteKingIndex, blackKingIndex);
		int evalEg = points + pointsPosEg+ Hb2SimplifiedEvaluatorBase.getKingPositionsValueEg(whiteKingIndex, blackKingIndex);
>>>>>>> 9522cc2 Valeurs différentes pour les positions des pions en Mg et Eg. L'incrémental concerne désormais seulement le matériel
=======
=======
import com.github.bhlangonijr.chesslib.Board;
>>>>>>> 7fec468 Valeurs différentes pour les positions des pions en Mg et Eg. L'incrémental concerne désormais seulement le matériel

/** The state of the evaluator.
 */
class Hb2BasicState extends Hb2FastPhaseDetector {
	int points;
	int whiteKingIndex;
	int blackKingIndex;
	int computedPhase;
	Board board;
	
	
	Hb2BasicState() {
		super();
	}
	
	void copyTo(Hb2BasicState other) {
		super.copyTo(other);
		other.points = points;
		other.blackKingIndex = blackKingIndex;
		other.whiteKingIndex = whiteKingIndex;
		other.computedPhase = computedPhase;
		other.board = board;
		
	}
	
	Hb2BasicState(BoardExplorer explorer, Board board) {
		this.board = board;
		this.points = 0;
		this.computedPhase = 0;
		do {
			final int p = explorer.getPiece();
			add(p);
			final int kind = Math.abs(p);
			final int index = explorer.getIndex();
			final boolean isBlack = p<0;
			if (kind!=KING) {
				int inc = Hb2SimplifiedEvaluatorBase.getRawValue(kind);
//				inc += Hb2SimplifiedEvaluatorBase.getPositionValue(kind, isBlack, index);
				if (isBlack) {
					points -= inc;
				} else {
					points += inc;
				}
			} else if (isBlack) {
				this.blackKingIndex = index;
			} else {
				this.whiteKingIndex = index;
			}
			
			computedPhase += Hb2Phase.getPhaseValue(kind);
		} while (explorer.next());
	}


	int evaluateAsWhite() {
		// for the time being (29/03/2024) points are the same in Mg and Eg except for the king preferred squares
		// but it's gonna change very soon
		// points = material only, from now on.
		int phase = getPhaseForTaperedEval(computedPhase);
<<<<<<< Upstream, based on origin/main
		int evalMg = points + Hb2SimplifiedEvaluatorBase.getKingPositionsValueMg(whiteKingIndex, blackKingIndex);
		int evalEg = points + Hb2SimplifiedEvaluatorBase.getKingPositionsValueEg(whiteKingIndex, blackKingIndex);
>>>>>>> 1477ae6 Hb2MyFirstEvaluator: the beginning. Compared to the SimplifiedEvaluator the evaluation function is tapered (scale = 64)
=======
		int pointsPosMg = Hb2SimplifiedEvaluatorBase.getPositionValueMg(board);
		int pointsPosEg = Hb2SimplifiedEvaluatorBase.getPositionValueEg(board);
		int evalMg = points + pointsPosMg + Hb2SimplifiedEvaluatorBase.getKingPositionsValueMg(whiteKingIndex, blackKingIndex);
		int evalEg = points + pointsPosEg+ Hb2SimplifiedEvaluatorBase.getKingPositionsValueEg(whiteKingIndex, blackKingIndex);
>>>>>>> 7fec468 Valeurs différentes pour les positions des pions en Mg et Eg. L'incrémental concerne désormais seulement le matériel
=======
=======
//		int pointsPosMg = Hb2SimplifiedEvaluatorBase.getPositionValueMg(board);
//		int pointsPosEg = Hb2SimplifiedEvaluatorBase.getPositionValueEg(board);
>>>>>>> 60c3451 Hb2SimplifiedEvaluator: incrémental achevé et propre pour position des pièces et matériel. Avec tapered eval
		int evalMg = pointsMg + pointsPosMg + Hb2SimplifiedEvaluatorBase.getKingPositionsValueMg(whiteKingIndex, blackKingIndex);
		int evalEg = pointsEg + pointsPosEg+ Hb2SimplifiedEvaluatorBase.getKingPositionsValueEg(whiteKingIndex, blackKingIndex);
>>>>>>> 8fcce8e Valeurs différentes du matériel en final rendues possibles
		return ((evalMg * phase + evalEg * (Hb2Phase.NB_INCR_PHASE-phase)) / Hb2Phase.NB_INCR_PHASE);
	}
=======
>>>>>>> f67222a nettoyage, refactoring
}
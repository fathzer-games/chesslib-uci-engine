package com.fathzer.jchess.chesslib.ai.eval.hbpg2;

import static com.fathzer.chess.utils.Pieces.KING;
import static com.fathzer.chess.utils.Pieces.PAWN;
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
import static com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2SimplifiedEvaluatorBase.getRawValueMg;
import static com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2SimplifiedEvaluatorBase.getRawValueEg;

import com.fathzer.chess.utils.Pieces;
<<<<<<< Upstream, based on origin/main
import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.chess.utils.adapters.MoveData;
import com.github.bhlangonijr.chesslib.Board;

/** The current state of a {@link Hb2AbstractIncrementalSimplifiedEvaluator}
 */
public class Hb2IncrementalState extends Hb2BasicState {
	Hb2IncrementalState() {
		super();
	}
	
	Hb2IncrementalState(BoardExplorer exp, Board board) {
		super(exp, board);
	}

	
	
	void update(MoveData<?,?> move) {
		pointsMg += getIncrementMg(move);
		pointsEg += getIncrementEg(move);
		pointsPosMg += getIncrementPosMg(move);
		pointsPosEg += getIncrementPosEg(move);
		chessEvalAdditionalElems.upadateEvalAdditionalElems(move);
		updatePhase(move);
	}
	
	//TODO STALINE à coder
	void update(MoveData<?,?> move, Board board) {
		pointsMg += getIncrementMg(move);
		pointsEg += getIncrementEg(move);
		pointsPosMg += getIncrementPosMg(move);
		pointsPosEg += getIncrementPosEg(move);
		chessEvalAdditionalElems.upadateEvalAdditionalElems(move);
		updatePhase(move);
	}
	
	
	
	private void updatePhase(MoveData<?,?> move) {
		final int promoType = move.getPromotionType();
		if (promoType!=0) {
			// If promotion, add raw value points, update phase
			computedPhase += Hb2Phase.getPhaseValue(promoType);
		
		}
		int captured = move.getCapturedType();
		if (captured!=0) {
			// A piece was captured
			// Update the phase detector
//			remove(isBlack ? captured : -captured);
			// Then add its raw value and its position value
//			return getRawValue(captured) + getPositionValue(captured, !isBlack, move.getCapturedIndex());
			computedPhase -= Hb2Phase.getPhaseValue(captured);
		}
	}

	private int getIncrementMg(MoveData<?,?> move) {
		final boolean isBlack = move.getMovingPiece()<0;
		int moving = Math.abs(move.getMovingPiece());
		final int movingIndex = move.getMovingIndex();
//		int inc;
		int inc = 0;
		if (moving==KING) {
			// The position value of kings is not evaluated incrementally
			int rookIndex = move.getCastlingRookIndex();
			if (rookIndex>=0) {
				// It's a castling move, update rook positions values
//				inc =  getPositionValue(ROOK, isBlack, move.getCastlingRookDestinationIndex()) - getPositionValue(ROOK, isBlack, rookIndex);
			} else {
				inc += doCaptureMg(isBlack, move);
			}
			// Update king's position
			if (isBlack) {
				this.blackKingIndex = move.getMovingDestination();
			} else {
				this.whiteKingIndex = move.getMovingDestination();
			}
		} else {
			// Remove the position value of the moving piece
//			inc = - getPositionValue(moving, isBlack, movingIndex);
			final int promoType = move.getPromotionType();
			if (promoType!=0) {
				// If promotion, add raw value points, update phase
				inc += getRawValueMg(promoType)-getRawValueMg(PAWN);
				moving = promoType;
//				add(isBlack ? -promoType : promoType);
			}
			inc += doCaptureMg(isBlack, move);
			// Adds the position value of the 
//			inc += getPositionValue(moving, isBlack, move.getMovingDestination());
		}
		return isBlack ? -inc : +inc;
	}
	
	private int getIncrementEg(MoveData<?,?> move) {
		final boolean isBlack = move.getMovingPiece()<0;
		int moving = Math.abs(move.getMovingPiece());
		final int movingIndex = move.getMovingIndex();
//		int inc;
		int inc = 0;
		if (moving==KING) {
			// The position value of kings is not evaluated incrementally
			int rookIndex = move.getCastlingRookIndex();
			if (rookIndex>=0) {
				// It's a castling move, update rook positions values
//				inc =  getPositionValue(ROOK, isBlack, move.getCastlingRookDestinationIndex()) - getPositionValue(ROOK, isBlack, rookIndex);
			} else {
				inc += doCaptureMg(isBlack, move);
			}
			// Update king's position
			if (isBlack) {
				this.blackKingIndex = move.getMovingDestination();
			} else {
				this.whiteKingIndex = move.getMovingDestination();
			}
		} else {
			// Remove the position value of the moving piece
//			inc = - getPositionValue(moving, isBlack, movingIndex);
			final int promoType = move.getPromotionType();
			if (promoType!=0) {
				// If promotion, add raw value points, update phase
				inc += getRawValueEg(promoType)-getRawValueEg(PAWN);
				moving = promoType;
//				add(isBlack ? -promoType : promoType);
			}
			inc += doCaptureEg(isBlack, move);
			// Adds the position value of the 
//			inc += getPositionValue(moving, isBlack, move.getMovingDestination());
		}
		return isBlack ? -inc : +inc;
	}
	
	private int doCaptureMg(boolean isBlack, MoveData<?,?> move) {
		int captured = move.getCapturedType();
		if (captured!=0) {
			// A piece was captured
			// Update the phase detector
//			remove(isBlack ? captured : -captured);
			// Then add its raw value and its position value
//			return getRawValue(captured) + getPositionValue(captured, !isBlack, move.getCapturedIndex());
			return getRawValueMg(captured);
		} else {
			return 0;
		}
	}
	
	private int doCaptureEg(boolean isBlack, MoveData<?,?> move) {
		int captured = move.getCapturedType();
		if (captured!=0) {
			// A piece was captured
			// Update the phase detector
//			remove(isBlack ? captured : -captured);
			// Then add its raw value and its position value
//			return getRawValue(captured) + getPositionValue(captured, !isBlack, move.getCapturedIndex());
			return getRawValueEg(captured);
		} else {
			return 0;
		}
	}
	
	private int getIncrementPosMg(MoveData<?,?> move) {
		final boolean isBlack = move.getMovingPiece()<0;
		int moving = Math.abs(move.getMovingPiece());
		final int movingIndex = move.getMovingIndex();
//		int inc;
		int inc = 0;
		if (moving==KING) {
			// The position value of kings is not evaluated incrementally
			int rookIndex = move.getCastlingRookIndex();
			if (rookIndex>=0) {
				// It's a castling move, update rook positions values
				inc += Hb2SimplifiedEvaluatorBase.getPositionValueMg(Pieces.ROOK, isBlack, move.getCastlingRookDestinationIndex()) - Hb2SimplifiedEvaluatorBase.getPositionValueMg(Pieces.ROOK, isBlack, rookIndex);
//				inc =  getPositionValue(ROOK, isBlack, move.getCastlingRookDestinationIndex()) - getPositionValue(ROOK, isBlack, rookIndex);
			} else {
				inc = doCapturPosMg(isBlack, move);
			}
			// Update king's position
			if (isBlack) {
				this.blackKingIndex = move.getMovingDestination();
			} else {
				this.whiteKingIndex = move.getMovingDestination();
			}
		} else {
			// Remove the position value of the moving piece
//			inc = - getPositionValue(moving, isBlack, movingIndex);
			inc -= Hb2SimplifiedEvaluatorBase.getPositionValueMg(moving, isBlack, movingIndex);
			final int promoType = move.getPromotionType();
			if (promoType!=0) {
				// If promotion, add raw value points, update phase
//				inc += getRawValueMg(promoType)-getRawValueMg(PAWN);
				moving = promoType;
//				add(isBlack ? -promoType : promoType);
			}
			inc += doCapturPosMg(isBlack, move);
			// Adds the position value of the moving piece at its destination square
			inc += Hb2SimplifiedEvaluatorBase.getPositionValueMg(moving, isBlack, move.getMovingDestination());
//			inc += getPositionValue(moving, isBlack, move.getMovingDestination());
		}
		return isBlack ? -inc : +inc;
	}
	

	private int doCapturPosMg(boolean isBlack, MoveData<?,?> move) {
		int captured = move.getCapturedType();
		if (captured!=0) {
			// A piece was captured
			// Update the phase detector
//			remove(isBlack ? captured : -captured);
			// Then add its raw value and its position value
//			return getRawValue(captured) + getPositionValue(captured, !isBlack, move.getCapturedIndex());
			
			return Hb2SimplifiedEvaluatorBase.getPositionValueMg(captured, !isBlack, move.getCapturedIndex());
			
		} else {
			return 0;
		}
	}
	
	private int getIncrementPosEg(MoveData<?,?> move) {
		final boolean isBlack = move.getMovingPiece()<0;
		int moving = Math.abs(move.getMovingPiece());
		final int movingIndex = move.getMovingIndex();
//		int inc;
		int inc = 0;
		if (moving==KING) {
			// The position value of kings is not evaluated incrementally
			int rookIndex = move.getCastlingRookIndex();
			if (rookIndex>=0) {
				// It's a castling move, update rook positions values
				inc += Hb2SimplifiedEvaluatorBase.getPositionValueEg(Pieces.ROOK, isBlack, move.getCastlingRookDestinationIndex()) - Hb2SimplifiedEvaluatorBase.getPositionValueEg(Pieces.ROOK, isBlack, rookIndex);
//				inc =  getPositionValue(ROOK, isBlack, move.getCastlingRookDestinationIndex()) - getPositionValue(ROOK, isBlack, rookIndex);
			} else {
				inc = doCapturPosEg(isBlack, move);
			}
			// Update king's position
			if (isBlack) {
				this.blackKingIndex = move.getMovingDestination();
			} else {
				this.whiteKingIndex = move.getMovingDestination();
			}
		} else {
			// Remove the position value of the moving piece
//			inc = - getPositionValue(moving, isBlack, movingIndex);
			inc -= Hb2SimplifiedEvaluatorBase.getPositionValueEg(moving, isBlack, movingIndex);
			final int promoType = move.getPromotionType();
			if (promoType!=0) {
				// If promotion, add raw value points, update phase
//				inc += getRawValueMg(promoType)-getRawValueMg(PAWN);
				moving = promoType;
//				add(isBlack ? -promoType : promoType);
			}
			inc += doCapturPosEg(isBlack, move);
			// Adds the position value of the moving piece at its destination square
			inc += Hb2SimplifiedEvaluatorBase.getPositionValueEg(moving, isBlack, move.getMovingDestination());
//			inc += getPositionValue(moving, isBlack, move.getMovingDestination());
		}
		return isBlack ? -inc : +inc;
	}
	

	private int doCapturPosEg(boolean isBlack, MoveData<?,?> move) {
		int captured = move.getCapturedType();
		if (captured!=0) {
			// A piece was captured
			// Update the phase detector
//			remove(isBlack ? captured : -captured);
			// Then add its raw value and its position value
//			return getRawValue(captured) + getPositionValue(captured, !isBlack, move.getCapturedIndex());
			
			return Hb2SimplifiedEvaluatorBase.getPositionValueEg(captured, !isBlack, move.getCapturedIndex());
			
=======
import static com.fathzer.chess.utils.Pieces.ROOK;
import static com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2SimplifiedEvaluatorBase.getPositionValue;
=======
//import static com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2SimplifiedEvaluatorBase.getPositionValue;
>>>>>>> 9522cc2 Valeurs différentes pour les positions des pions en Mg et Eg. L'incrémental concerne désormais seulement le matériel
import static com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2SimplifiedEvaluatorBase.getRawValue;
=======
import static com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2SimplifiedEvaluatorBase.getRawValueMg;
import static com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2SimplifiedEvaluatorBase.getRawValueEg;
>>>>>>> 8fcce8e Valeurs différentes du matériel en final rendues possibles

=======
>>>>>>> 60c3451 Hb2SimplifiedEvaluator: incrémental achevé et propre pour position des pièces et matériel. Avec tapered eval
import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.chess.utils.adapters.MoveData;
import com.github.bhlangonijr.chesslib.Board;

/** The current state of a {@link Hb2AbstractIncrementalSimplifiedEvaluator}
 */
public class Hb2IncrementalState extends Hb2BasicState {
	Hb2IncrementalState() {
		super();
	}
	
	Hb2IncrementalState(BoardExplorer exp, Board board) {
		super(exp, board);
	}

	
	
	void update(MoveData<?,?> move) {
		pointsMg += getIncrementMg(move);
		pointsEg += getIncrementEg(move);
		pointsPosMg += getIncrementPosMg(move);
		pointsPosEg += getIncrementPosEg(move);
		updatePhase(move);
	}
	
	private void updatePhase(MoveData<?,?> move) {
		final int promoType = move.getPromotionType();
		if (promoType!=0) {
			// If promotion, add raw value points, update phase
			computedPhase += Hb2Phase.getPhaseValue(promoType);
		
		}
		int captured = move.getCapturedType();
		if (captured!=0) {
			// A piece was captured
			// Update the phase detector
//			remove(isBlack ? captured : -captured);
			// Then add its raw value and its position value
//			return getRawValue(captured) + getPositionValue(captured, !isBlack, move.getCapturedIndex());
			computedPhase -= Hb2Phase.getPhaseValue(captured);
		}
	}

	private int getIncrementMg(MoveData<?,?> move) {
		final boolean isBlack = move.getMovingPiece()<0;
		int moving = Math.abs(move.getMovingPiece());
		final int movingIndex = move.getMovingIndex();
//		int inc;
		int inc = 0;
		if (moving==KING) {
			// The position value of kings is not evaluated incrementally
			int rookIndex = move.getCastlingRookIndex();
			if (rookIndex>=0) {
				// It's a castling move, update rook positions values
//				inc =  getPositionValue(ROOK, isBlack, move.getCastlingRookDestinationIndex()) - getPositionValue(ROOK, isBlack, rookIndex);
			} else {
				inc += doCaptureMg(isBlack, move);
			}
			// Update king's position
			if (isBlack) {
				this.blackKingIndex = move.getMovingDestination();
			} else {
				this.whiteKingIndex = move.getMovingDestination();
			}
		} else {
			// Remove the position value of the moving piece
//			inc = - getPositionValue(moving, isBlack, movingIndex);
			final int promoType = move.getPromotionType();
			if (promoType!=0) {
				// If promotion, add raw value points, update phase
				inc += getRawValueMg(promoType)-getRawValueMg(PAWN);
				moving = promoType;
//				add(isBlack ? -promoType : promoType);
			}
			inc += doCaptureMg(isBlack, move);
			// Adds the position value of the 
//			inc += getPositionValue(moving, isBlack, move.getMovingDestination());
		}
		return isBlack ? -inc : +inc;
	}
	
	private int getIncrementEg(MoveData<?,?> move) {
		final boolean isBlack = move.getMovingPiece()<0;
		int moving = Math.abs(move.getMovingPiece());
		final int movingIndex = move.getMovingIndex();
//		int inc;
		int inc = 0;
		if (moving==KING) {
			// The position value of kings is not evaluated incrementally
			int rookIndex = move.getCastlingRookIndex();
			if (rookIndex>=0) {
				// It's a castling move, update rook positions values
//				inc =  getPositionValue(ROOK, isBlack, move.getCastlingRookDestinationIndex()) - getPositionValue(ROOK, isBlack, rookIndex);
			} else {
				inc += doCaptureMg(isBlack, move);
			}
			// Update king's position
			if (isBlack) {
				this.blackKingIndex = move.getMovingDestination();
			} else {
				this.whiteKingIndex = move.getMovingDestination();
			}
		} else {
			// Remove the position value of the moving piece
//			inc = - getPositionValue(moving, isBlack, movingIndex);
			final int promoType = move.getPromotionType();
			if (promoType!=0) {
				// If promotion, add raw value points, update phase
				inc += getRawValueEg(promoType)-getRawValueEg(PAWN);
				moving = promoType;
//				add(isBlack ? -promoType : promoType);
			}
			inc += doCaptureEg(isBlack, move);
			// Adds the position value of the 
//			inc += getPositionValue(moving, isBlack, move.getMovingDestination());
		}
		return isBlack ? -inc : +inc;
	}
	
	private int doCaptureMg(boolean isBlack, MoveData<?,?> move) {
		int captured = move.getCapturedType();
		if (captured!=0) {
			// A piece was captured
			// Update the phase detector
//			remove(isBlack ? captured : -captured);
			// Then add its raw value and its position value
<<<<<<< Upstream, based on origin/main
			return getRawValue(captured) + getPositionValue(captured, !isBlack, move.getCapturedIndex());
>>>>>>> d32a00b Hb2MyFirstEvaluator: the beginning. Compared to the SimplifiedEvaluator the evaluation function is tapered (scale = 64)
=======
//			return getRawValue(captured) + getPositionValue(captured, !isBlack, move.getCapturedIndex());
<<<<<<< Upstream, based on origin/main
			return getRawValue(captured);
>>>>>>> 9522cc2 Valeurs différentes pour les positions des pions en Mg et Eg. L'incrémental concerne désormais seulement le matériel
=======
import static com.fathzer.chess.utils.Pieces.ROOK;
import static com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2SimplifiedEvaluatorBase.getPositionValue;
=======
//import static com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2SimplifiedEvaluatorBase.getPositionValue;
>>>>>>> 7fec468 Valeurs différentes pour les positions des pions en Mg et Eg. L'incrémental concerne désormais seulement le matériel
import static com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2SimplifiedEvaluatorBase.getRawValue;

import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.chess.utils.adapters.MoveData;
import com.github.bhlangonijr.chesslib.Board;

/** The current state of a {@link Hb2AbstractIncrementalSimplifiedEvaluator}
 */
public class Hb2IncrementalState extends Hb2BasicState {
	Hb2IncrementalState() {
		super();
	}
	
	Hb2IncrementalState(BoardExplorer exp, Board board) {
		super(exp, board);
	}

	void update(MoveData<?,?> move) {
		points += getIncrement(move);
	}

	private int getIncrement(MoveData<?,?> move) {
		final boolean isBlack = move.getMovingPiece()<0;
		int moving = Math.abs(move.getMovingPiece());
		final int movingIndex = move.getMovingIndex();
//		int inc;
		int inc = 0;
		if (moving==KING) {
			// The position value of kings is not evaluated incrementally
			int rookIndex = move.getCastlingRookIndex();
			if (rookIndex>=0) {
				// It's a castling move, update rook positions values
//				inc =  getPositionValue(ROOK, isBlack, move.getCastlingRookDestinationIndex()) - getPositionValue(ROOK, isBlack, rookIndex);
			} else {
				inc = doCapture(isBlack, move);
			}
			// Update king's position
			if (isBlack) {
				this.blackKingIndex = move.getMovingDestination();
			} else {
				this.whiteKingIndex = move.getMovingDestination();
			}
		} else {
			// Remove the position value of the moving piece
//			inc = - getPositionValue(moving, isBlack, movingIndex);
			final int promoType = move.getPromotionType();
			if (promoType!=0) {
				// If promotion, add raw value points, update phase
				inc += getRawValue(promoType)-getRawValue(PAWN);
				moving = promoType;
				add(isBlack ? -promoType : promoType);
			}
			inc += doCapture(isBlack, move);
			// Adds the position value of the 
//			inc += getPositionValue(moving, isBlack, move.getMovingDestination());
		}
		return isBlack ? -inc : +inc;
	}
	
	private int doCapture(boolean isBlack, MoveData<?,?> move) {
		int captured = move.getCapturedType();
		if (captured!=0) {
			// A piece was captured
			// Update the phase detector
			remove(isBlack ? captured : -captured);
			// Then add its raw value and its position value
<<<<<<< Upstream, based on origin/main
			return getRawValue(captured) + getPositionValue(captured, !isBlack, move.getCapturedIndex());
>>>>>>> 1477ae6 Hb2MyFirstEvaluator: the beginning. Compared to the SimplifiedEvaluator the evaluation function is tapered (scale = 64)
=======
//			return getRawValue(captured) + getPositionValue(captured, !isBlack, move.getCapturedIndex());
			return getRawValue(captured);
>>>>>>> 7fec468 Valeurs différentes pour les positions des pions en Mg et Eg. L'incrémental concerne désormais seulement le matériel
=======
			return getRawValueMg(captured);
		} else {
			return 0;
		}
	}
	
	private int doCaptureEg(boolean isBlack, MoveData<?,?> move) {
		int captured = move.getCapturedType();
		if (captured!=0) {
			// A piece was captured
			// Update the phase detector
//			remove(isBlack ? captured : -captured);
			// Then add its raw value and its position value
//			return getRawValue(captured) + getPositionValue(captured, !isBlack, move.getCapturedIndex());
			return getRawValueEg(captured);
>>>>>>> 8fcce8e Valeurs différentes du matériel en final rendues possibles
		} else {
			return 0;
		}
	}
	
	private int getIncrementPosMg(MoveData<?,?> move) {
		final boolean isBlack = move.getMovingPiece()<0;
		int moving = Math.abs(move.getMovingPiece());
		final int movingIndex = move.getMovingIndex();
//		int inc;
		int inc = 0;
		if (moving==KING) {
			// The position value of kings is not evaluated incrementally
			int rookIndex = move.getCastlingRookIndex();
			if (rookIndex>=0) {
				// It's a castling move, update rook positions values
				inc += Hb2SimplifiedEvaluatorBase.getPositionValueMg(Pieces.ROOK, isBlack, move.getCastlingRookDestinationIndex()) - Hb2SimplifiedEvaluatorBase.getPositionValueMg(Pieces.ROOK, isBlack, rookIndex);
//				inc =  getPositionValue(ROOK, isBlack, move.getCastlingRookDestinationIndex()) - getPositionValue(ROOK, isBlack, rookIndex);
			} else {
				inc = doCapturPosMg(isBlack, move);
			}
			// Update king's position
			if (isBlack) {
				this.blackKingIndex = move.getMovingDestination();
			} else {
				this.whiteKingIndex = move.getMovingDestination();
			}
		} else {
			// Remove the position value of the moving piece
//			inc = - getPositionValue(moving, isBlack, movingIndex);
			inc -= Hb2SimplifiedEvaluatorBase.getPositionValueMg(moving, isBlack, movingIndex);
			final int promoType = move.getPromotionType();
			if (promoType!=0) {
				// If promotion, add raw value points, update phase
//				inc += getRawValueMg(promoType)-getRawValueMg(PAWN);
				moving = promoType;
//				add(isBlack ? -promoType : promoType);
			}
			inc += doCapturPosMg(isBlack, move);
			// Adds the position value of the moving piece at its destination square
			inc += Hb2SimplifiedEvaluatorBase.getPositionValueMg(moving, isBlack, move.getMovingDestination());
//			inc += getPositionValue(moving, isBlack, move.getMovingDestination());
		}
		return isBlack ? -inc : +inc;
	}
	

	private int doCapturPosMg(boolean isBlack, MoveData<?,?> move) {
		int captured = move.getCapturedType();
		if (captured!=0) {
			// A piece was captured
			// Update the phase detector
//			remove(isBlack ? captured : -captured);
			// Then add its raw value and its position value
//			return getRawValue(captured) + getPositionValue(captured, !isBlack, move.getCapturedIndex());
			
			return Hb2SimplifiedEvaluatorBase.getPositionValueMg(captured, !isBlack, move.getCapturedIndex());
			
		} else {
			return 0;
		}
	}
	
	private int getIncrementPosEg(MoveData<?,?> move) {
		final boolean isBlack = move.getMovingPiece()<0;
		int moving = Math.abs(move.getMovingPiece());
		final int movingIndex = move.getMovingIndex();
//		int inc;
		int inc = 0;
		if (moving==KING) {
			// The position value of kings is not evaluated incrementally
			int rookIndex = move.getCastlingRookIndex();
			if (rookIndex>=0) {
				// It's a castling move, update rook positions values
				inc += Hb2SimplifiedEvaluatorBase.getPositionValueEg(Pieces.ROOK, isBlack, move.getCastlingRookDestinationIndex()) - Hb2SimplifiedEvaluatorBase.getPositionValueEg(Pieces.ROOK, isBlack, rookIndex);
//				inc =  getPositionValue(ROOK, isBlack, move.getCastlingRookDestinationIndex()) - getPositionValue(ROOK, isBlack, rookIndex);
			} else {
				inc = doCapturPosEg(isBlack, move);
			}
			// Update king's position
			if (isBlack) {
				this.blackKingIndex = move.getMovingDestination();
			} else {
				this.whiteKingIndex = move.getMovingDestination();
			}
		} else {
			// Remove the position value of the moving piece
//			inc = - getPositionValue(moving, isBlack, movingIndex);
			inc -= Hb2SimplifiedEvaluatorBase.getPositionValueEg(moving, isBlack, movingIndex);
			final int promoType = move.getPromotionType();
			if (promoType!=0) {
				// If promotion, add raw value points, update phase
//				inc += getRawValueMg(promoType)-getRawValueMg(PAWN);
				moving = promoType;
//				add(isBlack ? -promoType : promoType);
			}
			inc += doCapturPosEg(isBlack, move);
			// Adds the position value of the moving piece at its destination square
			inc += Hb2SimplifiedEvaluatorBase.getPositionValueEg(moving, isBlack, move.getMovingDestination());
//			inc += getPositionValue(moving, isBlack, move.getMovingDestination());
		}
		return isBlack ? -inc : +inc;
	}
	

	private int doCapturPosEg(boolean isBlack, MoveData<?,?> move) {
		int captured = move.getCapturedType();
		if (captured!=0) {
			// A piece was captured
			// Update the phase detector
//			remove(isBlack ? captured : -captured);
			// Then add its raw value and its position value
//			return getRawValue(captured) + getPositionValue(captured, !isBlack, move.getCapturedIndex());
			
			return Hb2SimplifiedEvaluatorBase.getPositionValueEg(captured, !isBlack, move.getCapturedIndex());
			
		} else {
			return 0;
		}
	}
}
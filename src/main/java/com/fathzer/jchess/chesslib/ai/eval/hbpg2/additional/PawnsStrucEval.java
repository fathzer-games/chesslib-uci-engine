package com.fathzer.jchess.chesslib.ai.eval.hbpg2.additional;

import static com.fathzer.chess.utils.Pieces.PAWN;

import java.util.Arrays;

import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.chess.utils.adapters.MoveData;
import com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2ChessConstants;

import com.github.bhlangonijr.chesslib.Board;


public class PawnsStrucEval {
	private Board board;
	
	private int[] tabNbWhitePawnsByCol;
	private int[] tabNbBlackPawnsByCol;
	
	
	public PawnsStrucEval() {
		tabNbWhitePawnsByCol = new int[Hb2ChessConstants.NB_COLS];
		tabNbBlackPawnsByCol = new int[Hb2ChessConstants.NB_COLS];
	}
	
	public PawnsStrucEval(PawnsStrucEval pse) {
		// Since it's an array of integers, the copy is not a shallow copy
		tabNbWhitePawnsByCol = Arrays.copyOf(pse.tabNbWhitePawnsByCol, Hb2ChessConstants.NB_COLS);
		// Since it's an array of integers, the copy is not a shallow copy
		tabNbBlackPawnsByCol = Arrays.copyOf(pse.tabNbBlackPawnsByCol, Hb2ChessConstants.NB_COLS);
	
	}
	
	public PawnsStrucEval(BoardExplorer explorer, Board board) {
		this();
		
		
		this.board = board;
		for (int i= 0; i < Hb2ChessConstants.NB_COLS; i++) {
			tabNbWhitePawnsByCol[i] = 0;
			tabNbBlackPawnsByCol[i] = 0;
		}

		do {
			final int p = explorer.getPiece();
			final int kind = Math.abs(p);
			final int index = explorer.getIndex();
			final boolean isPieceBlack = p<0;
			if (kind==PAWN) {
				
				final int columnPawn = index%Hb2ChessConstants.NB_COLS;
				
				if (isPieceBlack) {
					tabNbBlackPawnsByCol[columnPawn]++;
				} else {
					tabNbWhitePawnsByCol[columnPawn]++;
				}
				
				
			} 
		} while (explorer.next());
	

	}
	
	void copyTo(PawnsStrucEval other) {
		other.board = board;
		other.tabNbBlackPawnsByCol = tabNbBlackPawnsByCol.clone(); // not a shallow copy!!!! For integers are of primitive type...
		other.tabNbWhitePawnsByCol = tabNbWhitePawnsByCol.clone(); // not a shallow copy!!!!
	}
	
	public void modifyNumberOfBlackPawnsofColumn(int column, int nbPawns ) {
		tabNbBlackPawnsByCol[column] += nbPawns;
	}
	
	public void modifyNumberOfWhitePawnsofColumn(int column, int nbPawns ) {
		tabNbWhitePawnsByCol[column] += nbPawns;
	}
	public void updatePawnsStructEval(MoveData<?,?> move) {
		 updateDoubledPawns(move);
	}
	
	
	private void updateDoubledPawns(MoveData<?,?> move) {
		int kind = Math.abs(move.getMovingPiece());
		if (kind != PAWN) {
			return;
		}
		boolean isBlack = (move.getMovingPiece()<0?true:false);
		int columnPawn = move.getMovingIndex()%Hb2ChessConstants.NB_COLS;
		final int promoType = move.getPromotionType();
		if (promoType!=0) {
			// If promotion, then the pawn disappears.
			
			
			
			// Get the pawn's column and decrement its number of pawns of its color
			if (isBlack) {
				modifyNumberOfBlackPawnsofColumn(columnPawn, (-1));
			} else {
				modifyNumberOfWhitePawnsofColumn(columnPawn, (-1));
			}
			return; // promotion. so we stop here: no chance of a pawn being captured...
		
		}
		
		int captured = move.getCapturedType();
		if (captured!=0) {
			// Well the pawn with change columns...whatever it captures
			int destinationColumnOfThePawn = move.getMovingDestination()%Hb2ChessConstants.NB_COLS;
			if (isBlack) {
				modifyNumberOfBlackPawnsofColumn(columnPawn, (-1));
				modifyNumberOfBlackPawnsofColumn(destinationColumnOfThePawn, 1);
			} else {
				modifyNumberOfWhitePawnsofColumn(columnPawn, (-1));
				modifyNumberOfWhitePawnsofColumn(destinationColumnOfThePawn, 1);
			}
			// If a piece was captured, we don't care; whereas if a pawn is captured, it'as another story...
			if (Math.abs(captured)== PAWN) {
				if (isBlack) {
					// Black pawns are eating white pawns
					modifyNumberOfWhitePawnsofColumn(destinationColumnOfThePawn, (-1));
					
				} else {
					// White pawns are eating black pawns
					modifyNumberOfBlackPawnsofColumn(destinationColumnOfThePawn, (-1));
					
				}
			}

		}
	}
	
	public int getContribMg() {
		
		// white doubled pawns
		int malusW = 0;
		for (int i = 0; i < Hb2ChessConstants.NB_COLS; i++) {
			if (tabNbWhitePawnsByCol[i] > 1) {
				malusW += tabNbWhitePawnsByCol[i] * Hb2ChessConstants.MALUS_DOUBLED_PAWNS_MG;
			}
		}
		
		// black doubled pawns
		int malusB = 0;
		for (int i = 0; i < Hb2ChessConstants.NB_COLS; i++) {
			if (tabNbBlackPawnsByCol[i] > 1) {
				malusB += tabNbBlackPawnsByCol[i] * Hb2ChessConstants.MALUS_DOUBLED_PAWNS_MG;
			}
		}
		return(malusW - malusB);
	}
	
	
	
	
	public int getContribEg() {
		
		// white doubled pawns
		int malusW = 0;
		for (int i = 0; i < Hb2ChessConstants.NB_COLS; i++) {
			if (tabNbWhitePawnsByCol[i] > 1) {
				malusW += Hb2ChessConstants.NB_MAX_PAWNS_TAKEN_INTO_ACCOUNT_FOR_MALUS_DOUBLED_PAWNS * Hb2ChessConstants.MALUS_DOUBLED_PAWNS_EG;
			}
		}
		
		// black doubled pawns
		int malusB = 0;
		for (int i = 0; i < Hb2ChessConstants.NB_COLS; i++) {
			if (tabNbBlackPawnsByCol[i] > 1) {
				malusB += Hb2ChessConstants.NB_MAX_PAWNS_TAKEN_INTO_ACCOUNT_FOR_MALUS_DOUBLED_PAWNS * Hb2ChessConstants.MALUS_DOUBLED_PAWNS_EG;
			}
		}
		return(malusW - malusB);
		
	}
	
	
	
}

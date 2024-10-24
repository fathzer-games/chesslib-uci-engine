package com.fathzer.jchess.chesslib.ai.eval.hbpg2.additional;

import static com.fathzer.chess.utils.Pieces.PAWN;

import java.util.Arrays;

import com.fathzer.chess.utils.adapters.BoardExplorer;
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
		other.tabNbBlackPawnsByCol = tabNbBlackPawnsByCol.clone(); // not a shallow copy!!!!
		other.tabNbWhitePawnsByCol = tabNbWhitePawnsByCol.clone(); // not a shallow copy!!!!
	}
	
	public int getContribMg() {
		
		// white doubled pawns
		int malusW = 0;
		for (int i = 0; i < Hb2ChessConstants.NB_COLS; i++) {
			if (tabNbWhitePawnsByCol[i] > 1) {
				malusW += tabNbWhitePawnsByCol[i] * Hb2ChessConstants.MALUS_DOUBLED_PAWN_MG;
			}
		}
		
		// black doubled pawns
		int malusB = 0;
		for (int i = 0; i < Hb2ChessConstants.NB_COLS; i++) {
			if (tabNbBlackPawnsByCol[i] > 1) {
				malusB += tabNbBlackPawnsByCol[i] * Hb2ChessConstants.MALUS_DOUBLED_PAWN_MG;
			}
		}
		return(malusW - malusB);
	}
	
	
	
	
	public int getContribEg() {
		
		// white doubled pawns
		int malusW = 0;
		for (int i = 0; i < Hb2ChessConstants.NB_COLS; i++) {
			if (tabNbWhitePawnsByCol[i] > 1) {
				malusW += tabNbWhitePawnsByCol[i] * Hb2ChessConstants.MALUS_DOUBLED_PAWN_EG;
			}
		}
		
		// black doubled pawns
		int malusB = 0;
		for (int i = 0; i < Hb2ChessConstants.NB_COLS; i++) {
			if (tabNbBlackPawnsByCol[i] > 1) {
				malusB += tabNbBlackPawnsByCol[i] * Hb2ChessConstants.MALUS_DOUBLED_PAWN_EG;
			}
		}
		return(malusW - malusB);
		
	}
	
	
	
}

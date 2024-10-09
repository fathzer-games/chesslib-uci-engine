package com.fathzer.jchess.chesslib.ai.eval.hbpg2.additional;

import static com.fathzer.chess.utils.Pieces.PAWN;

import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2ChessConstants;

import com.github.bhlangonijr.chesslib.Board;


public class PawnsStrucEval {
	private Board board;
	
	private int[] tabNbWhitePawnsByCol = new int[Hb2ChessConstants.NB_COLS];
	private int[] tabNbBlackPawnsByCol = new int[Hb2ChessConstants.NB_COLS];
	
	public PawnsStrucEval(BoardExplorer explorer, Board board) {
		super();
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
	
	
}

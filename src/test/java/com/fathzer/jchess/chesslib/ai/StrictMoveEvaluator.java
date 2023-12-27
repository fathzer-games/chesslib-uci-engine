package com.fathzer.jchess.chesslib.ai;

import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

public class StrictMoveEvaluator extends BasicMoveComparator {

	public StrictMoveEvaluator(ChessLibMoveGenerator board) {
		super(board);
	}

	@Override
	public int compare(Move m1, Move m2) {
		if (m1.equals(m2)) {
			return 0;
		}
		int cmp = super.compare(m1, m2);
		if (cmp==0) {
			cmp = getIndex(m1.getFrom()) - getIndex(m2.getFrom());
			if (cmp==0) {
				cmp = getIndex(m1.getTo()) - getIndex(m2.getTo());
				if (cmp==0) {
					// pawn promotions to Bishop or knight
					return m1.getPromotion().getPieceType()==PieceType.KNIGHT ? 1 : -1;
				}
			}
		}
		return cmp;
	}
	
	public static int getIndex(Square square) {
		return (7-square.getRank().ordinal())*8+square.getFile().ordinal();
	}

}

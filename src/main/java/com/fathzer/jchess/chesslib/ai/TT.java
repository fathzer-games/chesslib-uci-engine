package com.fathzer.jchess.chesslib.ai;

import com.fathzer.games.ai.transposition.OneLongEntryTranspositionTable;
import com.fathzer.games.ai.transposition.SizeUnit;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

class TT extends OneLongEntryTranspositionTable<Move> {
	// Move is encoded as an int:
	// 12 bits for source, 12 bits for destination
	// 8 bits for promotion
	
	private static final int FROM_MASK = 0xfff;
	private static final int TO_MASK = 0xfff000;
	private static final int TO_OFFSET = 12;
	private static final int PROMOTION_OFFSET = 24;
	
	public TT(int size, SizeUnit unit) {
		super(size, unit);
	}

	@Override
	protected int toInt(Move move) {
		if (move==null) {
			return 0;
		}
		int result = move.getFrom().ordinal() | (move.getTo().ordinal()<<TO_OFFSET);
		if (move.getPromotion()!=null) {
			result |= ((move.getPromotion().ordinal())<<PROMOTION_OFFSET);
		}
		return result;
	}

	@Override
	protected Move toMove(int value) {
		if (value==0) {
			return null;
		}
		final int promotionIndex = value >> PROMOTION_OFFSET;
		final Square from = Square.squareAt(value & FROM_MASK);
		final Square to = Square.squareAt((value & TO_MASK) >> TO_OFFSET);
		return promotionIndex==0 ? new Move(from, to) : new Move(from, to, Piece.allPieces[promotionIndex]);
	}
}
package com.fathzer.jchess.chesslib.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fathzer.games.ai.transposition.SizeUnit;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

class TranspositionTableTest {
	@Test
	void test() {
		TT tt = new TT(1, SizeUnit.KB);
		Move mv = new Move(Square.D7,Square.D8, Piece.WHITE_QUEEN);
		Move other = tt.toMove(tt.toInt(mv));
		assertEquals(Square.D7, other.getFrom());
		assertEquals(Square.D8, other.getTo());
		assertEquals(Piece.WHITE_QUEEN, other.getPromotion());
		
		mv = new Move(Square.squareAt(1),Square.squareAt(0));
		other = tt.toMove(tt.toInt(mv));
		assertEquals(1, other.getFrom().ordinal());
		assertEquals(0, other.getTo().ordinal());
		assertEquals(Piece.NONE, other.getPromotion());
	}
}

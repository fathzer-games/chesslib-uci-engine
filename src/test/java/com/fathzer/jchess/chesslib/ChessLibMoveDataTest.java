package com.fathzer.jchess.chesslib;

import static org.junit.jupiter.api.Assertions.*;

import static com.github.bhlangonijr.chesslib.Square.*;

import static com.fathzer.chess.utils.Pieces.*;

import org.junit.jupiter.api.Test;

import com.fathzer.chess.test.utils.FENUtils;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.move.Move;

class ChessLibMoveDataTest {
	@Test
	void test() {
		final ChessLibMoveData mv = new ChessLibMoveData();
		assertEquals(0, mv.getIndex(A8));
		assertEquals(1, mv.getIndex(B8));
		assertEquals(8, mv.getIndex(A7));
		assertEquals(63, mv.getIndex(H1));

		ChessLibMoveGenerator mg;
		
		mg = FENUtils.from("r2qkb1r/1Ppb1ppp/4pn2/p2p4/3P1B2/4P3/P1P2PPP/RN1QK2R w KQkq - 0 6");
		// No capture, no promotion, no castling
		assertTrue(mv.update(new Move(F4, G5), mg));
		assertEquals(BISHOP, mv.getMovingPiece());
		assertEquals(0, mv.getCapturedType());
		assertEquals(0, mv.getPromotionType());
		assertEquals(-1, mv.getCastlingRookIndex());

		// Capture and promotion
		assertTrue(mv.update(new Move(B7, A8, Piece.BLACK_KNIGHT), mg));
		assertEquals(PAWN, mv.getMovingPiece());
		assertEquals(9, mv.getMovingIndex());
		assertEquals(0, mv.getMovingDestination());
		assertEquals(0, mv.getCapturedIndex());
		assertEquals(ROOK, mv.getCapturedType());
		assertEquals(KNIGHT, mv.getPromotionType());
		assertEquals(-1, mv.getCastlingRookIndex());
		
		// Castling
		assertTrue(mv.update(new Move(E1, G1), mg));
		assertEquals(KING, mv.getMovingPiece());
		assertEquals(60, mv.getMovingIndex());
		assertEquals(62, mv.getMovingDestination());
		assertEquals(0, mv.getCapturedIndex());
		assertEquals(0, mv.getCapturedType());
		assertEquals(0, mv.getPromotionType());
		assertEquals(63, mv.getCastlingRookIndex());
		assertEquals(61, mv.getCastlingRookDestinationIndex());
		
		// Promotion with no capture
		assertTrue(mv.update(new Move(B7, B8, Piece.BLACK_QUEEN), mg));
		assertEquals(PAWN, mv.getMovingPiece());
		assertEquals(9, mv.getMovingIndex());
		assertEquals(1, mv.getMovingDestination());
		assertEquals(0, mv.getCapturedType());
		assertEquals(QUEEN, mv.getPromotionType());
		assertEquals(-1, mv.getCastlingRookIndex());
		
		mg = FENUtils.from("rn1qkb1r/1ppb1ppp/4pn2/pP1p4/3P1B2/4P3/P1P2PPP/RN1QKBNR w KQkq a6 0 6");
		// Illegal move (no moving piece)
		assertFalse(mv.update(new Move(B2, B3), mg));
		
		// En passant
		assertTrue (mv.update(new Move(B5, A6), mg));
		assertEquals(PAWN, mv.getMovingPiece());
		assertEquals(25, mv.getMovingIndex());
		assertEquals(16, mv.getMovingDestination());
		assertEquals(24, mv.getCapturedIndex());
		assertEquals(PAWN, mv.getCapturedType());
		assertEquals(0, mv.getPromotionType());
		assertEquals(-1, mv.getCastlingRookIndex());
	}
}

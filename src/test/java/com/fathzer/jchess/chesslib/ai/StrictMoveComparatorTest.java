package com.fathzer.jchess.chesslib.ai;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.fathzer.jchess.chesslib.ai.MinimaxEngineTest.fromFEN;
import static com.github.bhlangonijr.chesslib.Square.*;

import org.junit.jupiter.api.Test;

import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

class StrictMoveComparatorTest {

	@Test
	void test() {
		assertEquals(0,StrictMoveComparator.getIndex(Square.A8));
		StrictMoveComparator c = new StrictMoveComparator(new ChessLibMoveGenerator(new Board()));
		
		// Ensure first is A8
		assertTrue(c.compare(new Move(A8,A6),new Move(A7,A6))<0);
		assertTrue(c.compare(new Move(A8,A6),new Move(B8,A6))<0);
		
		// Ensure Last is H1
		assertTrue(c.compare(new Move(H1,H6),new Move(H2,H6))>0);
		assertTrue(c.compare(new Move(H1,H6),new Move(G8,H6))>0);
	}

	@Test
	void test2() {
		ChessLibMoveGenerator board = fromFEN("5B2/8/7p/8/8/NN6/pk1K4/8 b - - 0 1");
		final StrictMoveComparator cmp = new StrictMoveComparator(board);
		
		List<Move> moves = board.getMoves();
		moves.sort(cmp);
		
		final Move queenPromo = new Move(Square.A2, Square.A1, Piece.BLACK_QUEEN);
		final Move rookPromo = new Move(Square.A2, Square.A1, Piece.BLACK_ROOK);
		final Move bishopPromo = new Move(Square.A2, Square.A1, Piece.BLACK_BISHOP);
		final Move knightPromo = new Move(Square.A2, Square.A1, Piece.BLACK_KNIGHT);
		final Move knightCaught = new Move(Square.B2, Square.B3);
		final Move pawnAdvance = new Move(Square.H6, Square.H5);

		
		final List<Move> expected = Arrays.asList(queenPromo, rookPromo, knightCaught, bishopPromo, knightPromo, pawnAdvance);
		// Warning, move generator can return pseudo legal moves that should be ignored in sorting comparison
		// in order to not have to rewrite test when generator changes from pseudo legals to strictly legal moves
		moves = moves.stream().filter(expected::contains).collect(Collectors.toList());
		assertEquals(expected, moves);
		
		assertTrue(cmp.compare(bishopPromo,rookPromo)>0);
		assertTrue(cmp.compare(knightPromo, bishopPromo)>0);
	}

}

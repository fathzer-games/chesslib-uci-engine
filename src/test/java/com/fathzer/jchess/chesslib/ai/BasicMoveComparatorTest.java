package com.fathzer.jchess.chesslib.ai;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import static com.fathzer.jchess.chesslib.ai.MinimaxEngineTest.fromFEN;

import org.junit.jupiter.api.Test;

import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

class BasicMoveComparatorTest {

	@Test
	void test() {
		final ChessLibMoveGenerator board = fromFEN("Q2n4/4P3/8/5P2/8/qK3p1k/1P6/8 w - - 0 1");
		final BasicMoveComparator cmp = new BasicMoveComparator(board);
		final Move queenPawnCatch = new Move(Square.A8, Square.F3);
		final Move queenQueenCatch = new Move(Square.A8, Square.A3);
		final Move kingCatch = new Move(Square.B3, Square.A3);
		final Move pawnMove = new Move(Square.F5, Square.F6);
		final Move pawnPromo = new Move(Square.E7, Square.E8, Piece.WHITE_QUEEN);
		final Move pawnCatchPromo = new Move(Square.E7, Square.D8, Piece.WHITE_QUEEN);
		
		Arrays.stream(new Move[] {pawnMove, queenQueenCatch, queenPawnCatch, kingCatch, pawnCatchPromo, pawnPromo}).map(m -> m.toString()+":"+cmp.getMoveValue(m)).forEach(System.out::println);
		final List<Move> sorted = Arrays.asList(queenPawnCatch, pawnPromo, kingCatch, pawnMove, queenQueenCatch, pawnCatchPromo);
		sorted.sort(cmp);
		assertEquals(Arrays.asList(pawnCatchPromo, queenQueenCatch, kingCatch, pawnPromo, queenPawnCatch, pawnMove), sorted);
		assertTrue(cmp.compare(kingCatch, queenQueenCatch)>0);
		assertTrue(cmp.compare(pawnMove, queenQueenCatch)>0);
		assertTrue(cmp.compare(pawnMove, kingCatch)>0);
		assertTrue(cmp.compare(queenPawnCatch, kingCatch)>0);
	}

}

package com.fathzer.jchess.chesslib.ai;

import static com.fathzer.jchess.chesslib.ai.MinimaxEngineTest.fromFEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.fathzer.games.util.MoveList;
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
		
		Move[] moves = new Move[] {pawnMove, queenQueenCatch, queenPawnCatch, kingCatch, pawnCatchPromo, pawnPromo};
//		Arrays.stream(moves).map(m -> m.toString()+":"+cmp.applyAsInt(m)).forEach(System.out::println);
		final MoveList<Move> sorted = new MoveList<>();
		sorted.setComparator(cmp);
		sorted.addAll(Arrays.asList(moves));
		sorted.sort();
		assertEquals(Arrays.asList(pawnCatchPromo, queenQueenCatch, kingCatch, pawnPromo, queenPawnCatch, pawnMove), sorted);
		assertFalse(cmp.test(pawnMove));
		assertTrue(cmp.compare(kingCatch, queenQueenCatch)>0);
		assertTrue(cmp.compare(queenPawnCatch, kingCatch)>0);
	}

}

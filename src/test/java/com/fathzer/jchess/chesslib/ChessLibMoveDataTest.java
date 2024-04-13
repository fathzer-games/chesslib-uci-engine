package com.fathzer.jchess.chesslib;

import static com.github.bhlangonijr.chesslib.Square.A7;
import static com.github.bhlangonijr.chesslib.Square.A8;
import static com.github.bhlangonijr.chesslib.Square.B8;
import static com.github.bhlangonijr.chesslib.Square.H1;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fathzer.chess.test.utils.FENUtils;
import com.fathzer.chess.utils.adapters.MoveData;
import com.fathzer.chess.utils.test.AbstractMoveDataTest;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.move.Move;

class ChessLibMoveDataTest extends AbstractMoveDataTest<Move, ChessLibMoveGenerator> {
	@Override
	protected MoveData<Move, ChessLibMoveGenerator> buildMoveData() {
		return new ChessLibMoveData();
	}
	
	@Override
	protected ChessLibMoveGenerator toBoard(String fen) {
		return FENUtils.from(fen);
	}

	@Override
	protected Move toMove(int from, int to, int promotionType, ChessLibMoveGenerator board) {
		Piece promotion;
		if (promotionType == 0) {
			promotion = Piece.NONE;
		} else {
			promotion = Piece.values()[board.isWhiteToMove() ? promotionType-1 : promotionType+5];
		}
		return new Move(ChessLibBoardExplorer.SQUARES[from], ChessLibBoardExplorer.SQUARES[to], promotion);
	}

	@Test
	void testSquareIndex() {
		final ChessLibMoveData mv = new ChessLibMoveData();
		assertEquals(0, mv.getIndex(A8));
		assertEquals(1, mv.getIndex(B8));
		assertEquals(8, mv.getIndex(A7));
		assertEquals(63, mv.getIndex(H1));
	}
}

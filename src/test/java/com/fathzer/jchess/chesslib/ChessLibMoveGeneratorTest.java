package com.fathzer.jchess.chesslib;

import static org.junit.jupiter.api.Assertions.*;

import static com.github.bhlangonijr.chesslib.Square.*;

import static com.fathzer.games.MoveGenerator.MoveConfidence.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.move.Move;

class ChessLibMoveGeneratorTest {

	@Test
	void testInvalidMovesWithUnsafe() {
		Board internal = new Board();
		internal.loadFromFen("rn1qk2r/1ppb1ppp/P2Bpn2/3p4/3P4/4P3/P1P2PPP/RN1QKBNR b KQkq - 0 7");
		ChessLibMoveGenerator mvg = new ChessLibMoveGenerator(internal);
		List<Move> movesList = mvg.getBoard().pseudoLegalMoves();
		
		// Castling with free cells in check
		Move move = new Move(E8, G8);
		assertFalse(mvg.makeMove(move, UNSAFE));
		
		// Move an non existing piece
		move = new Move(H6, H5);
		assertFalse(mvg.makeMove(move, UNSAFE));
		
		// Move a piece of the wrong color
		move = new Move(D1, D3);
		assertFalse(mvg.makeMove(move, UNSAFE));
		
		// Move a piece through another piece 
		move = new Move(D6, B8);
		assertFalse(mvg.makeMove(move, UNSAFE));
		
		// Piece takes own piece
		move = new Move(F6, D5);
		assertFalse(mvg.makeMove(move, UNSAFE));
		
		// Piece goes to unreachable cell
		move = new Move(D5, A5);
		assertFalse(movesList.contains(move));
//		assertFalse(mvg.makeMove(move, UNSAFE));

		// Pawn takes no piece
		move = new Move(H7, G6);
		assertFalse(movesList.contains(move));
//		assertFalse(mvg.makeMove(move, UNSAFE));
		
		// Pawn goes on opponent piece
		move = new Move(D5, D4);
		assertFalse(movesList.contains(move));
//		assertFalse(mvg.makeMove(move, UNSAFE));

		// Pawn goes on own piece
		move = new Move(F7, F6);
		assertFalse(mvg.makeMove(move, UNSAFE));
		
		// Imaginary promotion
		move = new Move(G7, G6, Piece.BLACK_QUEEN);
		assertFalse(mvg.makeMove(move, UNSAFE));

		
		internal = new Board();
		internal.loadFromFen("3rk2r/PR3p1p/4pn2/3p2pP/3P2bb/4P2N/P1P2PP1/1N2K2R w k - 0 7");
		mvg = new ChessLibMoveGenerator(internal);
		movesList = mvg.getBoard().pseudoLegalMoves();

		// Promotion to a pawn
		move = new Move(A7, A8, Piece.WHITE_PAWN);
		assertFalse(movesList.contains(move));
//		assertFalse(mvg.makeMove(move, UNSAFE)); //FIXME

		// Promotion of wrong color
		move = new Move(A7, A8, Piece.BLACK_QUEEN);
		assertFalse(movesList.contains(move));
//		assertFalse(mvg.makeMove(move, UNSAFE)); //FIXME
		
		// Promotion of a piece that is not a pawn
		move = new Move(B7, B8, Piece.WHITE_QUEEN);
		assertFalse(mvg.makeMove(move, UNSAFE));
		
		// imaginary en-passant
		move = new Move(H5, G6);
//		assertFalse(mvg.makeMove(move, UNSAFE));

		// Pinned piece
		move = new Move(F2, F3);
		assertFalse(mvg.makeMove(move, UNSAFE));
		
		// King goes to in check cell
		move = new Move(E1, E2);
		assertFalse(mvg.makeMove(move, UNSAFE));

		// Illegal castling because castling is not available
		move = new Move(E1, G1);
		assertFalse(mvg.makeMove(move, UNSAFE));

		internal = new Board();
		internal.loadFromFen("3rk2r/PR3p1p/4pn2/3p2pP/1b1P2b1/4P2N/P1P2PP1/1N2K2R w Kk - 0 7");
		mvg = new ChessLibMoveGenerator(internal);
		movesList = mvg.getBoard().pseudoLegalMoves();
		
		// Illegal castling because king is in check
		move = new Move(E1, G1);
//		assertFalse(mvg.makeMove(move, UNSAFE));
	}
	
	@Test
	void testValidMovesWithUnsafe() {
		Board internal = new Board();
		internal.loadFromFen("rn1qk2r/1ppb1ppp/P2Bpn2/3p4/3P4/4P3/P1P2PPP/RN1QKBNR b KQkq - 0 7");
		ChessLibMoveGenerator mvg = new ChessLibMoveGenerator(internal);
		
		// Pawn catch
		assertTrue(mvg.makeMove(new Move(C7,D6), UNSAFE));
		mvg.unmakeMove();
		
		//TODO
		
	}
}

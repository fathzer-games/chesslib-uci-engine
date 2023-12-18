package com.fathzer.jchess.chesslib;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;

public final class BoardExplorerBuilder {
	private static final List<Square> BOARD_SQUARES;
	
	private BoardExplorerBuilder() {
		// Prevent intantiation
	}
	
	static {
		BOARD_SQUARES = new ArrayList<>();
		for (Square q : Square.values()) {
			if (q!=Square.NONE) {
				BOARD_SQUARES.add(q);
			}
		}
	}
	
	public static Stream<Piece> getPieces(Board board) {
		return BOARD_SQUARES.stream().map(board::getPiece).filter(p->p!=Piece.NONE);
	}
}

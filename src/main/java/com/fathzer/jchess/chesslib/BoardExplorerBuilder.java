package com.fathzer.jchess.chesslib;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;

import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.Side;

abstract class BoardExplorerBuilder {
	private static final List<Square> BOARD_SQUARES;
	
	private BoardExplorerBuilder() {
		// Prevent instantiation
	}
	
	static {
		BOARD_SQUARES = new ArrayList<>();
		for (Square q : Square.values()) {
			if (q!=Square.NONE) {
				BOARD_SQUARES.add(q);
			}
		}
	}
	
	public static Stream<Integer> getPieces(Board board) {
		return BOARD_SQUARES.stream().map(board::getPiece).filter(p->p!=Piece.NONE).map(BoardExplorerBuilder::toPiece);
	}

	static int fromPieceType(PieceType type) {
		return type==null ? 0 : type.ordinal()+1;
	}
	
	static int toPiece(Piece piece) {
		final int index = fromPieceType(piece.getPieceType());
		return piece.getPieceSide()==Side.WHITE ? index : -index;
	}
}
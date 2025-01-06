package com.fathzer.jchess.chesslib;

import static com.github.bhlangonijr.chesslib.Square.*;

import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;

class ChessLibBoardExplorer implements BoardExplorer {
	static final Square[] SQUARES;
	
	static {
		SQUARES = new Square[] {
			A8, B8, C8, D8, E8, F8, G8, H8,
			A7, B7, C7, D7, E7, F7, G7, H7,
			A6, B6, C6, D6, E6, F6, G6, H6,
			A5, B5, C5, D5, E5, F5, G5, H5,
			A4, B4, C4, D4, E4, F4, G4, H4,
			A3, B3, C3, D3, E3, F3, G3, H3,
			A2, B2, C2, D2, E2, F2, G2, H2,
			A1, B1, C1, D1, E1, F1, G1, H1,
		};
	}
	
	private final Board board;
	private int index;
	private int piece;
	
	ChessLibBoardExplorer(Board board) {
		this.board = board;
		this.index = -1;
		next();
	}
	
	@Override
	public boolean next() {
		index++;
		if (index>=SQUARES.length) {
			return false;
		}
		piece = toPiece(board.getPiece(SQUARES[index]));
		return piece != 0 || next();
	}

	static int fromPieceType(PieceType type) {
		return type==null ? 0 : type.ordinal()+1;
	}
	
	static int toPiece(Piece piece) {
		final int index = fromPieceType(piece.getPieceType());
		return piece.getPieceSide()==Side.WHITE ? index : -index;
	}

	public int getIndex() {
		return index;
	}

	public int getPiece() {
		return piece;
	}
}

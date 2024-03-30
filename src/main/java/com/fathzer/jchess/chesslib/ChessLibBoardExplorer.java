package com.fathzer.jchess.chesslib;

import static com.github.bhlangonijr.chesslib.Square.A1;
import static com.github.bhlangonijr.chesslib.Square.A2;
import static com.github.bhlangonijr.chesslib.Square.A3;
import static com.github.bhlangonijr.chesslib.Square.A4;
import static com.github.bhlangonijr.chesslib.Square.A5;
import static com.github.bhlangonijr.chesslib.Square.A6;
import static com.github.bhlangonijr.chesslib.Square.A7;
import static com.github.bhlangonijr.chesslib.Square.A8;
import static com.github.bhlangonijr.chesslib.Square.B1;
import static com.github.bhlangonijr.chesslib.Square.B2;
import static com.github.bhlangonijr.chesslib.Square.B3;
import static com.github.bhlangonijr.chesslib.Square.B4;
import static com.github.bhlangonijr.chesslib.Square.B5;
import static com.github.bhlangonijr.chesslib.Square.B6;
import static com.github.bhlangonijr.chesslib.Square.B7;
import static com.github.bhlangonijr.chesslib.Square.B8;
import static com.github.bhlangonijr.chesslib.Square.C1;
import static com.github.bhlangonijr.chesslib.Square.C2;
import static com.github.bhlangonijr.chesslib.Square.C3;
import static com.github.bhlangonijr.chesslib.Square.C4;
import static com.github.bhlangonijr.chesslib.Square.C5;
import static com.github.bhlangonijr.chesslib.Square.C6;
import static com.github.bhlangonijr.chesslib.Square.C7;
import static com.github.bhlangonijr.chesslib.Square.C8;
import static com.github.bhlangonijr.chesslib.Square.D1;
import static com.github.bhlangonijr.chesslib.Square.D2;
import static com.github.bhlangonijr.chesslib.Square.D3;
import static com.github.bhlangonijr.chesslib.Square.D4;
import static com.github.bhlangonijr.chesslib.Square.D5;
import static com.github.bhlangonijr.chesslib.Square.D6;
import static com.github.bhlangonijr.chesslib.Square.D7;
import static com.github.bhlangonijr.chesslib.Square.D8;
import static com.github.bhlangonijr.chesslib.Square.E1;
import static com.github.bhlangonijr.chesslib.Square.E2;
import static com.github.bhlangonijr.chesslib.Square.E3;
import static com.github.bhlangonijr.chesslib.Square.E4;
import static com.github.bhlangonijr.chesslib.Square.E5;
import static com.github.bhlangonijr.chesslib.Square.E6;
import static com.github.bhlangonijr.chesslib.Square.E7;
import static com.github.bhlangonijr.chesslib.Square.E8;
import static com.github.bhlangonijr.chesslib.Square.F1;
import static com.github.bhlangonijr.chesslib.Square.F2;
import static com.github.bhlangonijr.chesslib.Square.F3;
import static com.github.bhlangonijr.chesslib.Square.F4;
import static com.github.bhlangonijr.chesslib.Square.F5;
import static com.github.bhlangonijr.chesslib.Square.F6;
import static com.github.bhlangonijr.chesslib.Square.F7;
import static com.github.bhlangonijr.chesslib.Square.F8;
import static com.github.bhlangonijr.chesslib.Square.G1;
import static com.github.bhlangonijr.chesslib.Square.G2;
import static com.github.bhlangonijr.chesslib.Square.G3;
import static com.github.bhlangonijr.chesslib.Square.G4;
import static com.github.bhlangonijr.chesslib.Square.G5;
import static com.github.bhlangonijr.chesslib.Square.G6;
import static com.github.bhlangonijr.chesslib.Square.G7;
import static com.github.bhlangonijr.chesslib.Square.G8;
import static com.github.bhlangonijr.chesslib.Square.H1;
import static com.github.bhlangonijr.chesslib.Square.H2;
import static com.github.bhlangonijr.chesslib.Square.H3;
import static com.github.bhlangonijr.chesslib.Square.H4;
import static com.github.bhlangonijr.chesslib.Square.H5;
import static com.github.bhlangonijr.chesslib.Square.H6;
import static com.github.bhlangonijr.chesslib.Square.H7;
import static com.github.bhlangonijr.chesslib.Square.H8;

import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;

public class ChessLibBoardExplorer implements BoardExplorer {
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
	
	public ChessLibBoardExplorer(Board board) {
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

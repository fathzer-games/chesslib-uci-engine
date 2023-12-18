package com.fathzer.jchess.chesslib.ai;

import java.util.EnumMap;
import java.util.Map;

import com.github.bhlangonijr.chesslib.PieceType;

public abstract class PieceValues {
	private static final Map<PieceType, Integer> PIECE_VALUE;
	
	static {
		PIECE_VALUE = new EnumMap<>(PieceType.class);
		PIECE_VALUE.put(PieceType.QUEEN, 9);
		PIECE_VALUE.put(PieceType.ROOK, 5);
		PIECE_VALUE.put(PieceType.BISHOP, 3);
		PIECE_VALUE.put(PieceType.KNIGHT, 3);
		PIECE_VALUE.put(PieceType.PAWN, 1);
		PIECE_VALUE.put(PieceType.KING, 10);
	}
	
	private PieceValues() {
		// Prevents subclasses
	}

	public static int get(PieceType type) {
		return PIECE_VALUE.get(type);
	}
}

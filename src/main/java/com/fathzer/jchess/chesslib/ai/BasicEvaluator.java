package com.fathzer.jchess.chesslib.ai;

import static com.fathzer.games.Color.*;

import java.util.EnumMap;
import java.util.Map;

import com.fathzer.games.Color;
import com.fathzer.games.ai.evaluation.Evaluator;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.Side;

public class BasicEvaluator implements Evaluator<Board> {
	public static final Map<PieceType, Integer> PIECE_VALUE;
	private Color viewPoint;
	
	static {
		PIECE_VALUE = new EnumMap<>(PieceType.class);
		PIECE_VALUE.put(PieceType.QUEEN, 9);
		PIECE_VALUE.put(PieceType.ROOK, 5);
		PIECE_VALUE.put(PieceType.BISHOP, 3);
		PIECE_VALUE.put(PieceType.KNIGHT, 3);
		PIECE_VALUE.put(PieceType.PAWN, 1);
	}
	
	@Override
	public void setViewPoint(Color viewPoint) {
		this.viewPoint = viewPoint;
	}

	@Override
	public int evaluate(Board board) {
		int points = 100*getPoints(board);
		if (BLACK==viewPoint || (viewPoint==null && Side.BLACK==board.getSideToMove())) {
			points = -points;
		}
		return points;
	}

	public int getPoints(Board board) {
		int points = 0;
		for (Piece p : board.boardToArray()) {
			int inc = PIECE_VALUE.get(p.getPieceType());
			if (p.getPieceSide()==Side.WHITE) {
				points += inc;
			} else {
				points -= inc;
			}
		}
		return points;
	}
}

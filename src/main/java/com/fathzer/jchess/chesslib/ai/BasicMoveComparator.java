package com.fathzer.jchess.chesslib.ai;

import java.util.Comparator;

import static com.github.bhlangonijr.chesslib.PieceType.*;
import static com.fathzer.jchess.chesslib.ai.BasicEvaluator.PIECE_VALUE;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.move.Move;

/** A move comparator that considers a catch is better than other moves and taking a high value piece with a small value piece is better than the opposite.
 */
public class BasicMoveComparator implements Comparator<Move> {
	private Board board;
	
	public BasicMoveComparator(Board board) {
		this.board = board;
	}

	@Override
	public int compare(Move m1, Move m2) {
		// Important sort from higher to lower scores
		return getValue(m2) - getValue(m1);
	}

	public int getValue(Move m) {
		final PieceType promotion = m.getPromotion().getPieceType();
		int value = promotion==NONE ? 0 : PIECE_VALUE.get(m.getPromotion().getPieceType());
		final PieceType caught = board.getPiece(m.getTo()).getPieceType();
		if (caught==NONE) {
			return value;
		} else {
			value += 64 + PIECE_VALUE.get(caught);
			final PieceType catching = board.getPiece(m.getFrom()).getPieceType();
			return value - (catching==PieceType.KING ? 10 : PIECE_VALUE.get(catching));
		}
	}
}

package com.fathzer.jchess.chesslib.ai;

import static com.fathzer.jchess.chesslib.eval.BasicEvaluator.PIECE_VALUE;

import java.util.Comparator;

import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.move.Move;

/** A move comparator that considers a catch is better than other moves and taking a high value piece with a small value piece is better than the opposite.
 */
public class BasicMoveComparator implements Comparator<Move> {
	private ChessLibMoveGenerator board;
	
	public BasicMoveComparator(ChessLibMoveGenerator board) {
		this.board = board;
	}

	@Override
	public int compare(Move m1, Move m2) {
		// Important sort from higher to lower scores
		return getValue(m2) - getValue(m1);
	}

	public int getValue(Move m) {
		final PieceType promotion = m.getPromotion().getPieceType();
		int value = promotion==null ? 0 : (PIECE_VALUE.get(promotion)-1)*16;
		final PieceType caught = board.getBoard().getPiece(m.getTo()).getPieceType();
		if (caught==null) {
			return value;
		} else {
			value += PIECE_VALUE.get(caught)*16;
			final PieceType catching = board.getBoard().getPiece(m.getFrom()).getPieceType();
			return value - (catching==PieceType.KING ? 10 : PIECE_VALUE.get(catching));
		}
	}
}

package com.fathzer.jchess.chesslib.ai;

import com.fathzer.chess.utils.AbstractDefaultMoveComparator;
import com.fathzer.jchess.chesslib.BasicMoveDecoder;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.move.Move;

/** A move comparator that considers a catch is better than other moves and taking a high value piece with a small value piece is better than the opposite.
 */
public class BasicMoveComparator extends AbstractDefaultMoveComparator<Move, ChessLibMoveGenerator> {

	public BasicMoveComparator(ChessLibMoveGenerator board) {
		super(board);
	}

	@Override
	public int getMovingPiece(ChessLibMoveGenerator board, Move move) {
		return BasicMoveDecoder.getMovingPiece(board, move);
	}

	@Override
	public int getCapturedType(ChessLibMoveGenerator board, Move move) {
		return BasicMoveDecoder.getCapturedType(board, move);
	}

	@Override
	public int getPromotionType(ChessLibMoveGenerator board, Move move) {
		return BasicMoveDecoder.getPromotionType(board, move);
	}
}

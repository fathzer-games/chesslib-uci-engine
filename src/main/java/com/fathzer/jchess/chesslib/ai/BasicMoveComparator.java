package com.fathzer.jchess.chesslib.ai;

import com.fathzer.chess.utils.DefaultMoveComparator;
import com.fathzer.jchess.chesslib.ChessLibAdapter;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.move.Move;

/** A move comparator that considers a catch is better than other moves and taking a high value piece with a small value piece is better than the opposite.
 */
public class BasicMoveComparator extends DefaultMoveComparator<Move, ChessLibMoveGenerator> implements ChessLibAdapter {

	public BasicMoveComparator(ChessLibMoveGenerator board) {
		super(board);
	}
}

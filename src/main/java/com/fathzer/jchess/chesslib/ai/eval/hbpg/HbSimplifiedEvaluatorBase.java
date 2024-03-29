package com.fathzer.jchess.chesslib.ai.eval.hbpg;

import com.fathzer.chess.utils.Pieces;
import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.chess.utils.adapters.BoardExplorerBuilder;
import com.fathzer.games.MoveGenerator;

abstract class HbSimplifiedEvaluatorBase<M, B extends MoveGenerator<M>> implements BoardExplorerBuilder<B> {
	private static final int[] PIECE_VALUES = {0, 100, 320, 330, 500, 900, 20000};
	private static final int[] KING_MID_GAME_EVAL = new int[] {
			-30,-40,-40,-50,-50,-40,-40,-30,
			-30,-40,-40,-50,-50,-40,-40,-30,
			-30,-40,-40,-50,-50,-40,-40,-30,
			-30,-40,-40,-50,-50,-40,-40,-30,
			-20,-30,-30,-40,-40,-30,-30,-20,
			-10,-20,-20,-20,-20,-20,-20,-10,
			 20, 20,  0,  0,  0,  0, 20, 20,
			 20, 30, 10,  0,  0, 10, 30, 20};

	private static final int[] KING_END_GAME_EVAL = new int[] {
			-50,-40,-30,-20,-20,-30,-40,-50,
			-30,-20,-10,  0,  0,-10,-20,-30,
			-30,-10, 20, 30, 30, 20,-10,-30,
			-30,-10, 30, 40, 40, 30,-10,-30,
			-30,-10, 30, 40, 40, 30,-10,-30,
			-30,-10, 20, 30, 30, 20,-10,-30,
			-30,-30,  0,  0,  0,  0,-30,-30,
			-50,-30,-30,-30,-30,-30,-30,-50};
	
	private static final int [][] PIECE_POSITION_VALUES = new int[][] {
		// Just to have index equals to piece type codes
		new int[0],
		// PAWN
		new int[] {
			0,  0,  0,  0,  0,  0,  0,  0,
			50, 50, 50, 50, 50, 50, 50, 50,
			10, 10, 20, 30, 30, 20, 10, 10,
			 5,  5, 10, 25, 25, 10,  5,  5,
			 0,  0,  0, 20, 20,  0,  0,  0,
			 5, -5,-10,  0,  0,-10, -5,  5,
			 5, 10, 10,-20,-20, 10, 10,  5,
			 0,  0,  0,  0,  0,  0,  0,  0},
		// KNIGHT
		new int[] {
			-50,-40,-30,-30,-30,-30,-40,-50,
			-40,-20,  0,  0,  0,  0,-20,-40,
			-30,  0, 10, 15, 15, 10,  0,-30,
			-30,  5, 15, 20, 20, 15,  5,-30,
			-30,  0, 15, 20, 20, 15,  0,-30,
			-30,  5, 10, 15, 15, 10,  5,-30,
			-40,-20,  0,  5,  5,  0,-20,-40,
			-50,-40,-30,-30,-30,-30,-40,-50},
		// BISHOP
		new int[] {
			-20,-10,-10,-10,-10,-10,-10,-20,
			-10,  0,  0,  0,  0,  0,  0,-10,
			-10,  0,  5, 10, 10,  5,  0,-10,
			-10,  5,  5, 10, 10,  5,  5,-10,
			-10,  0, 10, 10, 10, 10,  0,-10,
			-10, 10, 10, 10, 10, 10, 10,-10,
			-10,  5,  0,  0,  0,  0,  5,-10,
			-20,-10,-10,-10,-10,-10,-10,-20},
		// ROOK
		new int[] {
			  0,  0,  0,  0,  0,  0,  0,  0,
			  5, 10, 10, 10, 10, 10, 10,  5,
			 -5,  0,  0,  0,  0,  0,  0, -5,
			 -5,  0,  0,  0,  0,  0,  0, -5,
			 -5,  0,  0,  0,  0,  0,  0, -5,
			 -5,  0,  0,  0,  0,  0,  0, -5,
			 -5,  0,  0,  0,  0,  0,  0, -5,
			  0,  0,  0,  5,  5,  0,  0,  0},
		// QUEEN
		new int[] {
			-20,-10,-10, -5, -5,-10,-10,-20,
			-10,  0,  0,  0,  0,  0,  0,-10,
			-10,  0,  5,  5,  5,  5,  0,-10,
			 -5,  0,  5,  5,  5,  5,  0, -5,
			  0,  0,  5,  5,  5,  5,  0, -5,
			-10,  5,  5,  5,  5,  5,  0,-10,
			-10,  0,  5,  0,  0,  0,  0,-10,
			-20,-10,-10, -5, -5,-10,-10,-20
	}};
	
	HbSimplifiedEvaluatorBase() {
		super();
	}
	
	static int getPositionValue(int type, boolean black, int index) {
		return getPositionValue(PIECE_POSITION_VALUES[type], index, black);
	}
	
	static int getRawValue(int type) {
		return PIECE_VALUES[type];
	}
	
	static int getKingPositionsValue(int whiteIndex, int blackIndex, HbPhase phase) {
		final int[] kingMap = phase==HbPhase.MIDDLE_GAME ? KING_MID_GAME_EVAL : KING_END_GAME_EVAL;
		return getPositionValue(kingMap, whiteIndex, false) - getPositionValue(kingMap, blackIndex, true);
	}
	
	private static int getPositionValue(int[] positionMap, int index, boolean black) {
		if (black) {
			final int row = 7 - index/8;
			final int col = index%8;
			index = row*8 + col;
		}
		return positionMap[index];
	}
	
	/** Gets the position value associated with a type of piece and an index.
	 * @param type The piece type as define in {@link Pieces}
	 * @param index The index of the piece on the board as defined in {@link BoardExplorer}
	 * @return an integer
	 */
	static int getPositionValue(int type, int index) {
		return PIECE_POSITION_VALUES[type][index];
	}
}

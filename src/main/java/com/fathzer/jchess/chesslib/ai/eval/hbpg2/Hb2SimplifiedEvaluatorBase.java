package com.fathzer.jchess.chesslib.ai.eval.hbpg2;

<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
import static com.fathzer.chess.utils.Pieces.KING;

import com.fathzer.chess.utils.Pieces;
import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.chess.utils.adapters.BoardExplorerBuilder;
import com.fathzer.games.MoveGenerator;
import com.fathzer.jchess.chesslib.ChessLibBoardExplorer;
import com.github.bhlangonijr.chesslib.Board;


abstract class Hb2SimplifiedEvaluatorBase<M, B extends MoveGenerator<M>> implements BoardExplorerBuilder<B> {
	
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
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
	
	
	private static final int[] PIECE_VALUES_MG = {0, 100, 320, 330, 500, 900, 20000};
	
	private static final int[] PIECE_VALUES_EG = {0, 100, 320, 330, 500, 900, 20000};
	
	
//	private static final int[] KING_MID_GAME_EVAL = new int[] {
//			-30,-40,-40,-50,-50,-40,-40,-30,
//			-30,-40,-40,-50,-50,-40,-40,-30,
//			-30,-40,-40,-50,-50,-40,-40,-30,
//			-30,-40,-40,-50,-50,-40,-40,-30,
//			-20,-30,-30,-40,-40,-30,-30,-20,
//			-10,-20,-20,-20,-20,-20,-20,-10,
//			 20, 20,  0,  0,  0,  0, 20, 20,
//			 20, 30, 10,  0,  0, 10, 30, 20};
//
//	private static final int[] KING_END_GAME_EVAL = new int[] {
//			-50,-40,-30,-20,-20,-30,-40,-50,
//			-30,-20,-10,  0,  0,-10,-20,-30,
//			-30,-10, 20, 30, 30, 20,-10,-30,
//			-30,-10, 30, 40, 40, 30,-10,-30,
//			-30,-10, 30, 40, 40, 30,-10,-30,
//			-30,-10, 20, 30, 30, 20,-10,-30,
//			-30,-30,  0,  0,  0,  0,-30,-30,
//			-50,-30,-30,-30,-30,-30,-30,-50};
//	

	
	private static final int [][] PIECE_POSITION_VALUES_MG = new int[][] {
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
	
	private static final int [][] PIECE_POSITION_VALUES_EG= new int[][] {
		// Just to have index equals to piece type codes
		new int[0],
		// PAWN
		new int[] {
			0,  0,  0,  0,  0,  0,  0,  0,
			90, 90, 90, 90, 90, 90, 90, 90,
            30, 30, 40, 60, 60, 40, 30, 30,
            10, 10, 20, 40, 40, 20, 10, 10,
			 0,  0,  0, 20, 20,  0,  0,  0,
			 5,  0,  0,  0,  0,  0,  0,  5,
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
	
	Hb2SimplifiedEvaluatorBase() {
		super();
	}
	
	
	static int getPositionValueMg(int type, boolean black, int index) {
//		return getPositionValue(PIECE_POSITION_VALUES_MG[type], index, black);
		return getPositionValue(PIECE_POSITION_VALUES[type], index, black);
	}
	
	static int getPositionValueEg(int type, boolean black, int index) {
//		return getPositionValue(PIECE_POSITION_VALUES_EG[type], index, black);
		return getPositionValue(PIECE_POSITION_VALUES[type], index, black);
	}
	
	static int getRawValueMg(int type) {
		return PIECE_VALUES_MG[type];
		
	}
	
	static int getRawValueEg(int type) {
		return PIECE_VALUES_EG[type];
	}	
	
	static int getKingPositionsValueMg(int whiteIndex, int blackIndex) {
		
		return getPositionValue(KING_MID_GAME_EVAL, whiteIndex, false) - getPositionValue(KING_MID_GAME_EVAL, blackIndex, true);
	}
	
	static int getKingPositionsValueEg(int whiteIndex, int blackIndex) {
		
		return getPositionValue(KING_END_GAME_EVAL, whiteIndex, false) - getPositionValue(KING_END_GAME_EVAL, blackIndex, true);
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
//	static int getPositionValue(int type, int index) {
//		return PIECE_POSITION_VALUES[type][index];
//	}
	
	static int getPositionValueMg(Board board) {
//		BoardExplorer explorer = getExplorer(board);
		int pointsPosMg = 0;
		BoardExplorer explorer = new ChessLibBoardExplorer(board);
		do {
			final int p = explorer.getPiece();
			
			final int kind = Math.abs(p);
			final int index = explorer.getIndex();
			final boolean isBlack = p<0;
			if (kind!=KING) {
				
				int inc = getPositionValueMg(kind, isBlack, index);
				if (isBlack) {
					pointsPosMg -= inc;
				} else {
					pointsPosMg += inc;
				}
			} 
			
			
		} while (explorer.next());
		
		return (pointsPosMg);
	
	}
	
	static int getPositionValueEg(Board board) {
//		BoardExplorer explorer = getExplorer(board);
		int pointsPosEg = 0;
		BoardExplorer explorer = new ChessLibBoardExplorer(board);
		do {
			final int p = explorer.getPiece();
			
			final int kind = Math.abs(p);
			final int index = explorer.getIndex();
			final boolean isBlack = p<0;
			if (kind!=KING) {
				
				int inc = getPositionValueEg(kind, isBlack, index);
				if (isBlack) {
					pointsPosEg -= inc;
				} else {
					pointsPosEg += inc;
				}
			} 
			
			
		} while (explorer.next());
		
		return (pointsPosEg);
	
=======
=======
import static com.fathzer.chess.utils.Pieces.KING;

>>>>>>> 9522cc2 Valeurs différentes pour les positions des pions en Mg et Eg. L'incrémental concerne désormais seulement le matériel
import com.fathzer.chess.utils.Pieces;
import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.chess.utils.adapters.BoardExplorerBuilder;
import com.fathzer.games.MoveGenerator;
import com.fathzer.jchess.chesslib.ChessLibBoardExplorer;
import com.github.bhlangonijr.chesslib.Board;


abstract class Hb2SimplifiedEvaluatorBase<M, B extends MoveGenerator<M>> implements BoardExplorerBuilder<B> {
	private static final int[] PIECE_VALUES = {0, 100, 320, 330, 500, 900, 20000};
=======
	private static final int[] PIECE_VALUES_MG = {0, 100, 320, 330, 500, 900, 20000};
	
	private static final int[] PIECE_VALUES_EG = {0, 100, 320, 330, 500, 900, 20000};
	
	
>>>>>>> 8fcce8e Valeurs différentes du matériel en final rendues possibles
=======
>>>>>>> 60c3451 Hb2SimplifiedEvaluator: incrémental achevé et propre pour position des pièces et matériel. Avec tapered eval
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
	
	
	private static final int[] PIECE_VALUES_MG = {0, 100, 320, 330, 500, 900, 20000};
	
	private static final int[] PIECE_VALUES_EG = {0, 100, 320, 330, 500, 900, 20000};
	
	
//	private static final int[] KING_MID_GAME_EVAL = new int[] {
//			-30,-40,-40,-50,-50,-40,-40,-30,
//			-30,-40,-40,-50,-50,-40,-40,-30,
//			-30,-40,-40,-50,-50,-40,-40,-30,
//			-30,-40,-40,-50,-50,-40,-40,-30,
//			-20,-30,-30,-40,-40,-30,-30,-20,
//			-10,-20,-20,-20,-20,-20,-20,-10,
//			 20, 20,  0,  0,  0,  0, 20, 20,
//			 20, 30, 10,  0,  0, 10, 30, 20};
//
//	private static final int[] KING_END_GAME_EVAL = new int[] {
//			-50,-40,-30,-20,-20,-30,-40,-50,
//			-30,-20,-10,  0,  0,-10,-20,-30,
//			-30,-10, 20, 30, 30, 20,-10,-30,
//			-30,-10, 30, 40, 40, 30,-10,-30,
//			-30,-10, 30, 40, 40, 30,-10,-30,
//			-30,-10, 20, 30, 30, 20,-10,-30,
//			-30,-30,  0,  0,  0,  0,-30,-30,
//			-50,-30,-30,-30,-30,-30,-30,-50};
//	

	
	private static final int [][] PIECE_POSITION_VALUES_MG = new int[][] {
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
	
	private static final int [][] PIECE_POSITION_VALUES_EG= new int[][] {
		// Just to have index equals to piece type codes
		new int[0],
		// PAWN
		new int[] {
			0,  0,  0,  0,  0,  0,  0,  0,
			90, 90, 90, 90, 90, 90, 90, 90,
            30, 30, 40, 60, 60, 40, 30, 30,
            10, 10, 20, 40, 40, 20, 10, 10,
			 0,  0,  0, 20, 20,  0,  0,  0,
			 5,  0,  0,  0,  0,  0,  0,  5,
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
	
	Hb2SimplifiedEvaluatorBase() {
		super();
	}
	
	
	static int getPositionValueMg(int type, boolean black, int index) {
//		return getPositionValue(PIECE_POSITION_VALUES_MG[type], index, black);
		return getPositionValue(PIECE_POSITION_VALUES[type], index, black);
	}
	
	static int getPositionValueEg(int type, boolean black, int index) {
//		return getPositionValue(PIECE_POSITION_VALUES_EG[type], index, black);
		return getPositionValue(PIECE_POSITION_VALUES[type], index, black);
	}
	
	static int getRawValueMg(int type) {
		return PIECE_VALUES_MG[type];
		
	}
	
	static int getRawValueEg(int type) {
		return PIECE_VALUES_EG[type];
	}	
	
	static int getKingPositionsValueMg(int whiteIndex, int blackIndex) {
		
		return getPositionValue(KING_MID_GAME_EVAL, whiteIndex, false) - getPositionValue(KING_MID_GAME_EVAL, blackIndex, true);
	}
	
	static int getKingPositionsValueEg(int whiteIndex, int blackIndex) {
		
		return getPositionValue(KING_END_GAME_EVAL, whiteIndex, false) - getPositionValue(KING_END_GAME_EVAL, blackIndex, true);
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
<<<<<<< Upstream, based on origin/main
	static int getPositionValue(int type, int index) {
		return PIECE_POSITION_VALUES[type][index];
>>>>>>> d32a00b Hb2MyFirstEvaluator: the beginning. Compared to the SimplifiedEvaluator the evaluation function is tapered (scale = 64)
=======
//	static int getPositionValue(int type, int index) {
//		return PIECE_POSITION_VALUES[type][index];
//	}
	
	static int getPositionValueMg(Board board) {
//		BoardExplorer explorer = getExplorer(board);
		int pointsPosMg = 0;
		BoardExplorer explorer = new ChessLibBoardExplorer(board);
		do {
			final int p = explorer.getPiece();
			
			final int kind = Math.abs(p);
			final int index = explorer.getIndex();
			final boolean isBlack = p<0;
			if (kind!=KING) {
				
				int inc = getPositionValueMg(kind, isBlack, index);
				if (isBlack) {
					pointsPosMg -= inc;
				} else {
					pointsPosMg += inc;
				}
			} 
			
			
		} while (explorer.next());
		
		return (pointsPosMg);
	
	}
	
	static int getPositionValueEg(Board board) {
//		BoardExplorer explorer = getExplorer(board);
		int pointsPosEg = 0;
		BoardExplorer explorer = new ChessLibBoardExplorer(board);
		do {
			final int p = explorer.getPiece();
			
			final int kind = Math.abs(p);
			final int index = explorer.getIndex();
			final boolean isBlack = p<0;
			if (kind!=KING) {
				
				int inc = getPositionValueEg(kind, isBlack, index);
				if (isBlack) {
					pointsPosEg -= inc;
				} else {
					pointsPosEg += inc;
				}
			} 
			
			
		} while (explorer.next());
		
		return (pointsPosEg);
	
>>>>>>> 9522cc2 Valeurs différentes pour les positions des pions en Mg et Eg. L'incrémental concerne désormais seulement le matériel
=======
=======
import static com.fathzer.chess.utils.Pieces.KING;

>>>>>>> 7fec468 Valeurs différentes pour les positions des pions en Mg et Eg. L'incrémental concerne désormais seulement le matériel
import com.fathzer.chess.utils.Pieces;
import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.chess.utils.adapters.BoardExplorerBuilder;
import com.fathzer.games.MoveGenerator;
import com.fathzer.jchess.chesslib.ChessLibBoardExplorer;
import com.github.bhlangonijr.chesslib.Board;


abstract class Hb2SimplifiedEvaluatorBase<M, B extends MoveGenerator<M>> implements BoardExplorerBuilder<B> {
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
	
//	private static final int [][] PIECE_POSITION_VALUES = new int[][] {
//		// Just to have index equals to piece type codes
//		new int[0],
//		// PAWN
//		new int[] {
//			0,  0,  0,  0,  0,  0,  0,  0,
//			50, 50, 50, 50, 50, 50, 50, 50,
//			10, 10, 20, 30, 30, 20, 10, 10,
//			 5,  5, 10, 25, 25, 10,  5,  5,
//			 0,  0,  0, 20, 20,  0,  0,  0,
//			 5, -5,-10,  0,  0,-10, -5,  5,
//			 5, 10, 10,-20,-20, 10, 10,  5,
//			 0,  0,  0,  0,  0,  0,  0,  0},
//		// KNIGHT
//		new int[] {
//			-50,-40,-30,-30,-30,-30,-40,-50,
//			-40,-20,  0,  0,  0,  0,-20,-40,
//			-30,  0, 10, 15, 15, 10,  0,-30,
//			-30,  5, 15, 20, 20, 15,  5,-30,
//			-30,  0, 15, 20, 20, 15,  0,-30,
//			-30,  5, 10, 15, 15, 10,  5,-30,
//			-40,-20,  0,  5,  5,  0,-20,-40,
//			-50,-40,-30,-30,-30,-30,-40,-50},
//		// BISHOP
//		new int[] {
//			-20,-10,-10,-10,-10,-10,-10,-20,
//			-10,  0,  0,  0,  0,  0,  0,-10,
//			-10,  0,  5, 10, 10,  5,  0,-10,
//			-10,  5,  5, 10, 10,  5,  5,-10,
//			-10,  0, 10, 10, 10, 10,  0,-10,
//			-10, 10, 10, 10, 10, 10, 10,-10,
//			-10,  5,  0,  0,  0,  0,  5,-10,
//			-20,-10,-10,-10,-10,-10,-10,-20},
//		// ROOK
//		new int[] {
//			  0,  0,  0,  0,  0,  0,  0,  0,
//			  5, 10, 10, 10, 10, 10, 10,  5,
//			 -5,  0,  0,  0,  0,  0,  0, -5,
//			 -5,  0,  0,  0,  0,  0,  0, -5,
//			 -5,  0,  0,  0,  0,  0,  0, -5,
//			 -5,  0,  0,  0,  0,  0,  0, -5,
//			 -5,  0,  0,  0,  0,  0,  0, -5,
//			  0,  0,  0,  5,  5,  0,  0,  0},
//		// QUEEN
//		new int[] {
//			-20,-10,-10, -5, -5,-10,-10,-20,
//			-10,  0,  0,  0,  0,  0,  0,-10,
//			-10,  0,  5,  5,  5,  5,  0,-10,
//			 -5,  0,  5,  5,  5,  5,  0, -5,
//			  0,  0,  5,  5,  5,  5,  0, -5,
//			-10,  5,  5,  5,  5,  5,  0,-10,
//			-10,  0,  5,  0,  0,  0,  0,-10,
//			-20,-10,-10, -5, -5,-10,-10,-20
//	}};
	
	private static final int [][] PIECE_POSITION_VALUES_MG = new int[][] {
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
	
	private static final int [][] PIECE_POSITION_VALUES_EG= new int[][] {
		// Just to have index equals to piece type codes
		new int[0],
		// PAWN
		new int[] {
			0,  0,  0,  0,  0,  0,  0,  0,
			90, 90, 90, 90, 90, 90, 90, 90,
            30, 30, 40, 60, 60, 40, 30, 30,
            10, 10, 20, 40, 40, 20, 10, 10,
			 0,  0,  0, 20, 20,  0,  0,  0,
			 5,  0,  0,  0,  0,  0,  0,  5,
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
	
	Hb2SimplifiedEvaluatorBase() {
		super();
	}
	
//	static int getPositionValue(int type, boolean black, int index) {
//		return getPositionValue(PIECE_POSITION_VALUES[type], index, black);
//	}
//	
	static int getPositionValueMg(int type, boolean black, int index) {
		return getPositionValue(PIECE_POSITION_VALUES_MG[type], index, black);
	}
	
	static int getPositionValueEg(int type, boolean black, int index) {
		return getPositionValue(PIECE_POSITION_VALUES_EG[type], index, black);
	}
	
	static int getRawValue(int type) {
		return PIECE_VALUES[type];
	}
	
	
	
	static int getKingPositionsValueMg(int whiteIndex, int blackIndex) {
		
		return getPositionValue(KING_MID_GAME_EVAL, whiteIndex, false) - getPositionValue(KING_MID_GAME_EVAL, blackIndex, true);
	}
	
	static int getKingPositionsValueEg(int whiteIndex, int blackIndex) {
		
		return getPositionValue(KING_END_GAME_EVAL, whiteIndex, false) - getPositionValue(KING_END_GAME_EVAL, blackIndex, true);
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
<<<<<<< Upstream, based on origin/main
	static int getPositionValue(int type, int index) {
		return PIECE_POSITION_VALUES[type][index];
>>>>>>> 1477ae6 Hb2MyFirstEvaluator: the beginning. Compared to the SimplifiedEvaluator the evaluation function is tapered (scale = 64)
=======
//	static int getPositionValue(int type, int index) {
//		return PIECE_POSITION_VALUES[type][index];
//	}
	
	static int getPositionValueMg(Board board) {
//		BoardExplorer explorer = getExplorer(board);
		int pointsPosMg = 0;
		BoardExplorer explorer = new ChessLibBoardExplorer(board);
		do {
			final int p = explorer.getPiece();
			
			final int kind = Math.abs(p);
			final int index = explorer.getIndex();
			final boolean isBlack = p<0;
			if (kind!=KING) {
				
				int inc = getPositionValueMg(kind, isBlack, index);
				if (isBlack) {
					pointsPosMg -= inc;
				} else {
					pointsPosMg += inc;
				}
			} 
			
			
		} while (explorer.next());
		
		return (pointsPosMg);
	
	}
	
	static int getPositionValueEg(Board board) {
//		BoardExplorer explorer = getExplorer(board);
		int pointsPosEg = 0;
		BoardExplorer explorer = new ChessLibBoardExplorer(board);
		do {
			final int p = explorer.getPiece();
			
			final int kind = Math.abs(p);
			final int index = explorer.getIndex();
			final boolean isBlack = p<0;
			if (kind!=KING) {
				
				int inc = getPositionValueEg(kind, isBlack, index);
				if (isBlack) {
					pointsPosEg -= inc;
				} else {
					pointsPosEg += inc;
				}
			} 
			
			
		} while (explorer.next());
		
		return (pointsPosEg);
	
>>>>>>> 7fec468 Valeurs différentes pour les positions des pions en Mg et Eg. L'incrémental concerne désormais seulement le matériel
	}
}

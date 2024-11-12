package com.fathzer.jchess.chesslib.hbpg2.test;

import static com.fathzer.chess.utils.Pieces.PAWN;

import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.jchess.chesslib.ChessLibBoardExplorer;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2ChessConstants;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Rank;
import com.github.bhlangonijr.chesslib.Square;

public class TestPasserPawns {
	
	public static int getIndex(Square square) {
		return (7-square.getRank().ordinal())*8+square.getFile().ordinal();
	}
	
	static ChessLibMoveGenerator fromFEN(String fen) {
		final Board board = new Board();
		board.loadFromFen(fen);
		return new ChessLibMoveGenerator(board);
	}
	
	static void printDetailsAboutPawn(Square pawnSq,  boolean isBlack,Board board) {
		
		System.out.println();
		System.out.println();
		System.out.println("####################");
		if (isBlack) {
			System.out.println("BLACK PAWN DETECTED ON THE "+ pawnSq.name()+" SQUARE");
		} else {
			System.out.println("WHITE PAWN DETECTED ON THE "+ pawnSq.name()+" SQUARE");
		}
		int indexCazz = getIndex(pawnSq);
		System.out.println("Index JMA case "+ pawnSq.value()+":"+ indexCazz);
		Square[] tabAdjSquares = pawnSq.getSideSquares();
		System.out.println("Cases adjacentes de la case "+ pawnSq.value()+":");
		for (int i = 0; i < tabAdjSquares.length; i++) {
			System.out.println(tabAdjSquares[i].value());
		
		}
		
		
		int columnPawn = indexCazz%Hb2ChessConstants.NB_FILES;
		int rankPawn =Hb2ChessConstants.INDEX_MAX_RANK-pawnSq.getRank().ordinal();
		System.out.println("rankPawn_JMA:"+ rankPawn);
		System.out.println("columnPawn_JMA:"+ columnPawn);
		
		if (isBlack ) {
			if ( pawnSq.getRank() == Rank.RANK_2) {
				// A black pawn on the 2nd rank is a passer, by nature
				System.out.println("THE BLACK " + pawnSq.name()+ " PAWN IS A PASSED PAWN!");
			}
			
		} else {
			if ( pawnSq.getRank() == Rank.RANK_7) {
				// A white pawn on the 7th rank is a passer, by nature
				System.out.println("THE WHITE " + pawnSq.name()+ " PAWN IS A PASSED PAWN!");
			}
		}
		
		
		
//		Piece enemyPawn = (isBlack?Piece.WHITE_PAWN:Piece.BLACK_PAWN);
//		long bitboardEnemyPawns = board.getBitboard(enemyPawn);
		
		 
		System.out.println("####################");
		System.out.println();
		System.out.println();
		
	}
	
	static void deallWithSquareOccupiedByPawn(Square pawnSq, boolean isBlack, Board board) {
	
		printDetailsAboutPawn(pawnSq, isBlack, board);
		
	}

	public static void main(String[] args) {
		Board internal = new Board();
		internal.loadFromFen("6k1/8/1Pp5/8/8/3P4/1K6/8 w - - 0 1"); // cf /Volumes/PORSCHE/ECHECS_DVT/PROG_JMA/PIONS_PASSES/fen.gif
		ChessLibMoveGenerator mvg = new ChessLibMoveGenerator(internal);
		
		BoardExplorer explorer = new ChessLibBoardExplorer(mvg.getBoard());
		do {
			final int p = explorer.getPiece();
			
			final int kind = Math.abs(p);
			final int index = explorer.getIndex();
			final boolean isBlack = p<0;
			if (kind == PAWN) {
				int row = Hb2ChessConstants.INDEX_MAX_RANK - index/Hb2ChessConstants.NB_RANKS;
				int col = index%Hb2ChessConstants.NB_FILES;
				Square sqp = Square.squareAt((8*row)+col);
				
				deallWithSquareOccupiedByPawn(sqp, isBlack, mvg.getBoard());
//				
//				int inc = getPositionValueEg(kind, isBlack, index);
//				if (isBlack) {
//					pointsPosEg -= inc;
//				} else {
//					pointsPosEg += inc;
//				}
			} 
			
			
		} while (explorer.next());
		
	}

}

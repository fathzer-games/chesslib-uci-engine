package com.fathzer.jchess.chesslib.hbpg2.test;

import static com.fathzer.chess.utils.Pieces.PAWN;

import java.util.List;

import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.jchess.chesslib.ChessLibBoardExplorer;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2ChessConstants;
import com.github.bhlangonijr.chesslib.Bitboard;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.File;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Rank;
import com.github.bhlangonijr.chesslib.Square;

public class VeryDirtyTestPasserPawns {
	
	public static int getIndex(Square square) {
		return (Hb2ChessConstants.INDEX_MAX_RANK-square.getRank().ordinal())*Hb2ChessConstants.NB_RANKS+square.getFile().ordinal();
	}
	
	static int getRgSquare (int rank, int file) {
		return((Hb2ChessConstants.NB_RANKS*rank)+file);
	}
	
	static ChessLibMoveGenerator fromFEN(String fen) {
		final Board board = new Board();
		board.loadFromFen(fen);
		return new ChessLibMoveGenerator(board);
	}
	
	static void printDetailsAboutPawn(Square pawnSq,  boolean isBlack,Board board) {
		
//		/// STALINE
//				if (pawnSq.name().equalsIgnoreCase("E2") == false) {
//					return;
//				}
		
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
				System.out.println("####################");
				System.out.println();
				System.out.println();
				return;
			}
			
		} else {
			if ( pawnSq.getRank() == Rank.RANK_7) {
				// A white pawn on the 7th rank is a passer, by nature
				System.out.println("THE WHITE " + pawnSq.name()+ " PAWN IS A PASSED PAWN!");
				System.out.println("####################");
				System.out.println();
				System.out.println();
				return;
			}
		}
		
		
		
		Piece enemyPawn = (isBlack?Piece.WHITE_PAWN:Piece.BLACK_PAWN);
		long bitboardEnemyPawns = board.getBitboard(enemyPawn);
		if (bitboardEnemyPawns == 0L) {
			System.out.println("THE "+ (isBlack?"BLACK ":"WHITE ") + pawnSq.name()+ " PAWN IS A PASSED PAWN!");
		}
		
		
		if (!isBlack) {
			
			// Bon, on part de la rangée devant le pion blanc et on va jusqu'à la 7ème rangée, pour la colonne du pion et ses colonnes adjacentes (au max 2)
			// On regarde s'il y a un pion noir ennemi dans cette zone. Si c'est le cas, alors le pion n'est pas passé. Si cette zone est vide de pions noirs, alors 
			// le pion blanc est passé
			 int rankDebut = pawnSq.getRank().ordinal()+1;
			 int rankFin = Rank.RANK_7.ordinal();
			 int fileDebut = pawnSq.getFile().ordinal();
			 int fileFin = pawnSq.getFile().ordinal();
			 if (columnPawn == 0) {
				 fileFin = File.FILE_B.ordinal(); 
			 } else if (columnPawn == 7) {
				 fileDebut = File.FILE_G.ordinal(); 
			 }	else {
				 fileDebut = pawnSq.getFile().ordinal() - 1;
				 fileFin = pawnSq.getFile().ordinal() +1;
			 }
			 //getRgSquare

//			 int rgDebutSq = (Hb2ChessConstants.NB_RANKS*rankDebut)+fileDebut;
//			 int rgFinSq = (Hb2ChessConstants.NB_RANKS*rankFin)+fileFin;
			 boolean areEnemyPawnInZone = false;
			 for (int rankk = rankDebut; rankk <= rankFin; rankk++) {
				 int rgSqDebutRank  = getRgSquare(rankk, fileDebut);
				 int rgSqFintRank  = getRgSquare(rankk, fileFin);
				 long pionsAdversesDansZoneForRank = Bitboard.bitsBetween(bitboardEnemyPawns, rgSqDebutRank, rgSqFintRank);
				 if (pionsAdversesDansZoneForRank != 0L) {
					 areEnemyPawnInZone = true;
					 break;
				 }
				 
			 }
			 
//			 Square sqDebut = Square.squareAt((Hb2ChessConstants.NB_RANKS*rankDebut)+fileDebut);
//			 Square sqFin = Square.squareAt((Hb2ChessConstants.NB_RANKS*rankFin)+fileFin);
//			long ALL_BITS_EQUAL_ONE_BITBOARD = 0xFFFFFFFFFFFFFFFFL;
//			 long pionsAdversesDansZone = Bitboard.bitsBetween(ALL_BITS_EQUAL_ONE_BITBOARD, sqDebut.ordinal(), sqFin.ordinal());
////			 long pionsAdversesDansZone = Bitboard.bitsBetween(ALL_BITS_EQUAL_ONE_BITBOARD, rgDebutSq, rgFinSq);
//			 List<Square> lstKazz = Bitboard.bbToSquareList(pionsAdversesDansZone);
//			 for (Square kaaaaz : lstKazz) {
//				 System.out.println(kaaaaz.value());
//			 }
			 if (areEnemyPawnInZone == false) {
				 System.out.println("THE WHITE " + pawnSq.name()+ " PAWN IS A PASSED PAWN!");
			 }

			 		
			
			
		} else {
			
			// Bon, on part de la rangée devant le pion noir (du point de vue noir) et on va jusqu'à la 2ème rangée, pour la colonne du pion et ses colonnes adjacentes (au max 2)
			// On regarde s'il y a un pion blanc ennemi dans cette zone. Si c'est le cas, alors le pion n'est pas passé. Si cette zone est vide de pions blancs, alors 
			// le pion noir est passé
			 int rankDebut = Rank.RANK_2.ordinal(); 
			 int rankFin = pawnSq.getRank().ordinal()-1;
			 int fileDebut = pawnSq.getFile().ordinal();
			 int fileFin = pawnSq.getFile().ordinal();
			 if (columnPawn == 0) {
				 fileFin = File.FILE_B.ordinal(); 
			 } else if (columnPawn == 7) {
				 fileDebut = File.FILE_G.ordinal(); 
			 }	else {
				 fileDebut = pawnSq.getFile().ordinal() - 1;
				 fileFin = pawnSq.getFile().ordinal() +1;
			 }
			 
//			 Square sqDebut = Square.squareAt((Hb2ChessConstants.NB_RANKS*rankDebut)+fileDebut);
//			 Square sqFin = Square.squareAt((Hb2ChessConstants.NB_RANKS*rankFin)+fileFin);
			 
			 boolean areEnemyPawnInZone = false;
			 for (int rankk = rankDebut; rankk <= rankFin; rankk++) {
				 int rgSqDebutRank  = getRgSquare(rankk, fileDebut);
				 int rgSqFintRank  = getRgSquare(rankk, fileFin);
				 long pionsAdversesDansZoneForRank = Bitboard.bitsBetween(bitboardEnemyPawns, rgSqDebutRank, rgSqFintRank);
				 if (pionsAdversesDansZoneForRank != 0L) {
					 areEnemyPawnInZone = true;
					 break;
				 }
				 
			 }
	
			 
			 if (areEnemyPawnInZone == false) {
				 System.out.println("THE BLACK " + pawnSq.name()+ " PAWN IS A PASSED PAWN!");
			 }
			
			
		}

		System.out.println("####################");
		System.out.println();
		System.out.println();
		
	}
	
	static void deallWithSquareOccupiedByPawn(Square pawnSq, boolean isBlack, Board board) {
	
		printDetailsAboutPawn(pawnSq, isBlack, board);
		
	}

	
	
	static void testFen1() {

		
		testFen("6k1/8/1Pp5/8/8/3P4/1K6/8 w - - 0 1");
		
		
	}
		
	
		
	static void testFen2() {
		testFen("6k1/8/1Pp5/8/8/3P3p/1K2Pp2/8 w - - 0 1");
			
			
		}
	static void testFen3() {
		testFen("6k1/8/1Pp5/8/8/7p/1K1PPp2/8 w - - 0 1");
			
			
		}
	
	
	static void testFen(String fen) {
		
		System.out.println();
		System.out.println("FEN en test pour les pions passés:" + fen);
		Board internal = new Board();
		internal.loadFromFen(fen); 
		ChessLibMoveGenerator mvg = new ChessLibMoveGenerator(internal);
		
		System.out.println(internal.toStringFromWhiteViewPoint());
		
		BoardExplorer explorer = new ChessLibBoardExplorer(mvg.getBoard());
		do {
			final int p = explorer.getPiece();
			
			final int kind = Math.abs(p);
			final int index = explorer.getIndex();
			final boolean isBlack = p<0;
			if (kind == PAWN) {
				int row = Hb2ChessConstants.INDEX_MAX_RANK - index/Hb2ChessConstants.NB_RANKS;
				int col = index%Hb2ChessConstants.NB_FILES;
				Square sqp = Square.squareAt((Hb2ChessConstants.NB_RANKS*row)+col);
				
				deallWithSquareOccupiedByPawn(sqp, isBlack, mvg.getBoard());

			}
			
		} while (explorer.next());
		
		System.out.println();
		System.out.println();
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println();
		System.out.println();
		
	}
	
	public static void main(String[] args) {
//		testFen1(); // cf /Volumes/PORSCHE/ECHECS_DVT/PROG_JMA/PIONS_PASSES/fen1.gif
		testFen2(); // cf /Volumes/PORSCHE/ECHECS_DVT/PROG_JMA/PIONS_PASSES/fen2.gif
//		testFen3(); // cf /Volumes/PORSCHE/ECHECS_DVT/PROG_JMA/PIONS_PASSES/fen3.gif
	}


}

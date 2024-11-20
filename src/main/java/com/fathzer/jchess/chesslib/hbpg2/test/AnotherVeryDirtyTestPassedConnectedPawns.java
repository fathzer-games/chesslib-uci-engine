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



public class AnotherVeryDirtyTestPassedConnectedPawns {
	
    public static final long h_file                                 = 0x8080808080808080L;
    public static final long g_file                                 = h_file >>> 1;
    public static final long f_file                                 = h_file >>> 2;
    public static final long e_file                                 = h_file >>> 3;
    public static final long d_file                                 = h_file >>> 4;
    public static final long c_file                                 = h_file >>> 5;
    public static final long b_file                                 = h_file >>> 6;
    public static final long a_file                                 = h_file >>> 7;

    public static final long rank_1                                 = 0x00000000000000FFL;
    public static final long rank_2                                 = rank_1 << 8;
    public static final long rank_3                                 = rank_1 << 16;
    public static final long rank_4                                 = rank_1 << 24;
    public static final long rank_5                                 = rank_1 << 32;
    public static final long rank_6                                 = rank_1 << 40;
    public static final long rank_7                                 = rank_1 << 48;
    public static final long rank_8                                 = rank_1 << 56;

  
    public static final long not_a_file                             = ~a_file;
    public static final long not_h_file                             = ~h_file;
    public static final long not_rank_1                             = ~rank_1;
    public static final long not_rank_8                             = ~rank_8;
	
	   public static final long[] whitePassedPawnMask = new long[] {
	            0x0303030303030300L, 0x0707070707070700L, 0x0e0e0e0e0e0e0e00L, 0x1c1c1c1c1c1c1c00L,
	            0x3838383838383800L, 0x7070707070707000L, 0xe0e0e0e0e0e0e000L, 0xc0c0c0c0c0c0c000L,
	            0x0303030303030000L, 0x0707070707070000L, 0x0e0e0e0e0e0e0000L, 0x1c1c1c1c1c1c0000L,
	            0x3838383838380000L, 0x7070707070700000L, 0xe0e0e0e0e0e00000L, 0xc0c0c0c0c0c00000L,
	            0x0303030303000000L, 0x0707070707000000L, 0x0e0e0e0e0e000000L, 0x1c1c1c1c1c000000L,
	            0x3838383838000000L, 0x7070707070000000L, 0xe0e0e0e0e0000000L, 0xc0c0c0c0c0000000L,
	            0x0303030300000000L, 0x0707070700000000L, 0x0e0e0e0e00000000L, 0x1c1c1c1c00000000L,
	            0x3838383800000000L, 0x7070707000000000L, 0xe0e0e0e000000000L, 0xc0c0c0c000000000L,
	            0x0303030000000000L, 0x0707070000000000L, 0x0e0e0e0000000000L, 0x1c1c1c0000000000L,
	            0x3838380000000000L, 0x7070700000000000L, 0xe0e0e00000000000L, 0xc0c0c00000000000L,
	            0x0303000000000000L, 0x0707000000000000L, 0x0e0e000000000000L, 0x1c1c000000000000L,
	            0x3838000000000000L, 0x7070000000000000L, 0xe0e0000000000000L, 0xc0c0000000000000L,
	            0x0300000000000000L, 0x0700000000000000L, 0x0e00000000000000L, 0x1c00000000000000L,
	            0x3800000000000000L, 0x7000000000000000L, 0xe000000000000000L, 0xc000000000000000L,
	            0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
	            0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L
	    };

	    public static final long[] blackPassedPawnMask = new long[] {

	            0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
	            0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
	            0x0000000000000003L, 0x0000000000000007L, 0x000000000000000eL, 0x000000000000001cL,
	            0x0000000000000038L, 0x0000000000000070L, 0x00000000000000e0L, 0x00000000000000c0L,
	            0x0000000000000303L, 0x0000000000000707L, 0x0000000000000e0eL, 0x0000000000001c1cL,
	            0x0000000000003838L, 0x0000000000007070L, 0x000000000000e0e0L, 0x000000000000c0c0L,
	            0x0000000000030303L, 0x0000000000070707L, 0x00000000000e0e0eL, 0x00000000001c1c1cL,
	            0x0000000000383838L, 0x0000000000707070L, 0x0000000000e0e0e0L, 0x0000000000c0c0c0L,
	            0x0000000003030303L, 0x0000000007070707L, 0x000000000e0e0e0eL, 0x000000001c1c1c1cL,
	            0x0000000038383838L, 0x0000000070707070L, 0x00000000e0e0e0e0L, 0x00000000c0c0c0c0L,
	            0x0000000303030303L, 0x0000000707070707L, 0x0000000e0e0e0e0eL, 0x0000001c1c1c1c1cL,
	            0x0000003838383838L, 0x0000007070707070L, 0x000000e0e0e0e0e0L, 0x000000c0c0c0c0c0L,
	            0x0000030303030303L, 0x0000070707070707L, 0x00000e0e0e0e0e0eL, 0x00001c1c1c1c1c1cL,
	            0x0000383838383838L, 0x0000707070707070L, 0x0000e0e0e0e0e0e0L, 0x0000c0c0c0c0c0c0L,
	            0x0003030303030303L, 0x0007070707070707L, 0x000e0e0e0e0e0e0eL, 0x001c1c1c1c1c1c1cL,
	            0x0038383838383838L, 0x0070707070707070L, 0x00e0e0e0e0e0e0e0L, 0x00c0c0c0c0c0c0c0L,
	    };
	    
	    public static final long shiftWest(long b) {
	        return (b >>> 1) & not_h_file;
	    }

	    public static final long shiftEast(long b) {
	        return (b << 1) & not_a_file;
	    }

	    public static final long shiftSouth(long b) {
	        return b >>> 8;
	    }

	    public static final long shiftNorth(long b) {
	        return b << 8;
	    }

	    public static final long shiftNorthEast(long b) {
	        return (b << 9) & not_a_file;
	    }

	    public static final long shiftSouthEast(long b) {
	        return (b >>> 7) & not_a_file;
	    }

	    public static final long shiftSouthWest(long b) {
	        return (b >>> 9) & not_h_file;
	    }

	    public static final long shiftNorthWest(long b) {
	        return (b << 7) & not_h_file;
	    }

	
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
	
	
	public static void printLstSquare(List<Square> lstSq) {
		for (Square sq : lstSq) {
		      System.out.print(sq.value());
		      System.out.print(" ");
		}
		System.out.println();
	}
	
	static void newPrintDetailsAboutPawn(Square pawnSq,  boolean isBlack,Board board) {
		
//		/// STALINE
//				if (pawnSq.name().equalsIgnoreCase("H3") == false) {
//					return;
//				}
//				long toto = 	pawnSq.ordinal();
//				List<Square> lstSquaretoto = Bitboard.bbToSquareList(toto);
//				printLstSquare(lstSquaretoto);
//			 toto = 	(1L << pawnSq.ordinal());
//			lstSquaretoto = Bitboard.bbToSquareList(toto);
//			printLstSquare(lstSquaretoto);
			 
		long bitBoardWhitePawns = board.getBitboard(Piece.WHITE_PAWN);
		long  connectedPawnsEastWhite = shiftNorthEast(bitBoardWhitePawns) & bitBoardWhitePawns;
		long  connectedPawnsWestWhite = shiftNorthWest(bitBoardWhitePawns) & bitBoardWhitePawns;
		
		List<Square> lstSquare = Bitboard.bbToSquareList(connectedPawnsEastWhite);
		printLstSquare(lstSquare);
		lstSquare = Bitboard.bbToSquareList(connectedPawnsWestWhite);
		printLstSquare(lstSquare);
		
		long bitBoardBlackPawns = board.getBitboard(Piece.BLACK_PAWN);
		long  connectedPawnsEastBlack = shiftSouthEast(bitBoardBlackPawns) & bitBoardBlackPawns;
		long  connectedPawnsWestBlack = shiftSouthWest(bitBoardBlackPawns) & bitBoardBlackPawns;
		List<Square> lstSquareBP = Bitboard.bbToSquareList(connectedPawnsEastBlack);
		printLstSquare(lstSquareBP);
		lstSquare = Bitboard.bbToSquareList(connectedPawnsWestBlack);
		printLstSquare(lstSquare);
		
		
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
		
//		if (isBlack ) {
//			if ( pawnSq.getRank() == Rank.RANK_2) {
//				// A black pawn on the 2nd rank is a passer, by nature
//				System.out.println("THE BLACK " + pawnSq.name()+ " PAWN IS A PASSED PAWN!");
//				System.out.println("####################");
//				System.out.println();
//				System.out.println();
//				return;
//			}
//			
//		} else {
//			if ( pawnSq.getRank() == Rank.RANK_7) {
//				// A white pawn on the 7th rank is a passer, by nature
//				System.out.println("THE WHITE " + pawnSq.name()+ " PAWN IS A PASSED PAWN!");
//				System.out.println("####################");
//				System.out.println();
//				System.out.println();
//				return;
//			}
//		}
//		
		
		
		Piece enemyPawn = (isBlack?Piece.WHITE_PAWN:Piece.BLACK_PAWN);
		long bitboardEnemyPawns = board.getBitboard(enemyPawn);
//		if (bitboardEnemyPawns == 0L) {
//			System.out.println("THE "+ (isBlack?"BLACK ":"WHITE ") + pawnSq.name()+ " PAWN IS A PASSED PAWN!");
//		}
		long testPassedPawn = 0L;
		if (!isBlack) {
			testPassedPawn = whitePassedPawnMask[pawnSq.ordinal()] & bitboardEnemyPawns;
		} else {
			testPassedPawn = blackPassedPawnMask[pawnSq.ordinal()] & bitboardEnemyPawns;
		}
		if (testPassedPawn == 0L) {
			if (!isBlack) {
				
				 System.out.println("THE WHITE " + pawnSq.name()+ " PAWN IS A PASSED PAWN!");
				
			} else {
				 System.out.println("THE BLACK " + pawnSq.name()+ " PAWN IS A PASSED PAWN!");
			}
			
		}
		
	
//	long bitBoardWhitePawns = board.getBitboard(Piece.WHITE_PAWN);
//	long  connectedPawnsEastWhite = shiftNorthEast(bitBoardWhitePawns) & bitBoardWhitePawns;
//	long  connectedPawnsWestWhite = shiftNorthWest(bitBoardWhitePawns) & bitBoardWhitePawns;
//	
//	long bitBoardBlackPawns = board.getBitboard(Piece.BLACK_PAWN);
//	long  connectedPawnsEastBlack = shiftSouthEast(bitBoardBlackPawns) & bitBoardBlackPawns;
//	long  connectedPawnsWestBlack = shiftSouthWest(bitBoardBlackPawns) & bitBoardBlackPawns;
		if (!isBlack) {
			
			 boolean connectedWhitePawn = ( (1L << pawnSq.ordinal()) & (connectedPawnsEastWhite | connectedPawnsWestWhite)) != 0;
			 if (connectedWhitePawn) {
				 System.out.println("THE WHITE " + pawnSq.name()+ " PAWN IS A CONNECTED PAWN!");
			 }
			
		} else {
			boolean connectedBlackPawn = ((1L << pawnSq.ordinal()) & (connectedPawnsEastBlack | connectedPawnsWestBlack)) != 0;
			 if (connectedBlackPawn) {
				 System.out.println("THE BLACK " + pawnSq.name()+ " PAWN IS A CONNECTED PAWN!");
			 }
			
		}
		
	
		
//		if (!isBlack) {
//			
//			// Bon, on part de la rangée devant le pion blanc et on va jusqu'à la 7ème rangée, pour la colonne du pion et ses colonnes adjacentes (au max 2)
//			// On regarde s'il y a un pion noir ennemi dans cette zone. Si c'est le cas, alors le pion n'est pas passé. Si cette zone est vide de pions noirs, alors 
//			// le pion blanc est passé
//			 int rankDebut = pawnSq.getRank().ordinal()+1;
//			 int rankFin = Rank.RANK_7.ordinal();
//			 int fileDebut = pawnSq.getFile().ordinal();
//			 int fileFin = pawnSq.getFile().ordinal();
//			 if (columnPawn == 0) {
//				 fileFin = File.FILE_B.ordinal(); 
//			 } else if (columnPawn == 7) {
//				 fileDebut = File.FILE_G.ordinal(); 
//			 }	else {
//				 fileDebut = pawnSq.getFile().ordinal() - 1;
//				 fileFin = pawnSq.getFile().ordinal() +1;
//			 }
//			 //getRgSquare
//
////			 int rgDebutSq = (Hb2ChessConstants.NB_RANKS*rankDebut)+fileDebut;
////			 int rgFinSq = (Hb2ChessConstants.NB_RANKS*rankFin)+fileFin;
//			 boolean areEnemyPawnInZone = false;
//			 for (int rankk = rankDebut; rankk <= rankFin; rankk++) {
//				 int rgSqDebutRank  = getRgSquare(rankk, fileDebut);
//				 int rgSqFintRank  = getRgSquare(rankk, fileFin);
//				 long pionsAdversesDansZoneForRank = Bitboard.bitsBetween(bitboardEnemyPawns, rgSqDebutRank, rgSqFintRank);
//				 if (pionsAdversesDansZoneForRank != 0L) {
//					 areEnemyPawnInZone = true;
//					 break;
//				 }
//				 
//			 }
//			 
////			 Square sqDebut = Square.squareAt((Hb2ChessConstants.NB_RANKS*rankDebut)+fileDebut);
////			 Square sqFin = Square.squareAt((Hb2ChessConstants.NB_RANKS*rankFin)+fileFin);
////			long ALL_BITS_EQUAL_ONE_BITBOARD = 0xFFFFFFFFFFFFFFFFL;
////			 long pionsAdversesDansZone = Bitboard.bitsBetween(ALL_BITS_EQUAL_ONE_BITBOARD, sqDebut.ordinal(), sqFin.ordinal());
//////			 long pionsAdversesDansZone = Bitboard.bitsBetween(ALL_BITS_EQUAL_ONE_BITBOARD, rgDebutSq, rgFinSq);
////			 List<Square> lstKazz = Bitboard.bbToSquareList(pionsAdversesDansZone);
////			 for (Square kaaaaz : lstKazz) {
////				 System.out.println(kaaaaz.value());
////			 }
//			 if (areEnemyPawnInZone == false) {
//				 System.out.println("THE WHITE " + pawnSq.name()+ " PAWN IS A PASSED PAWN!");
//			 }
//
//			 		
//			
//			
//		} else {
//			
//			// Bon, on part de la rangée devant le pion noir (du point de vue noir) et on va jusqu'à la 2ème rangée, pour la colonne du pion et ses colonnes adjacentes (au max 2)
//			// On regarde s'il y a un pion blanc ennemi dans cette zone. Si c'est le cas, alors le pion n'est pas passé. Si cette zone est vide de pions blancs, alors 
//			// le pion noir est passé
//			 int rankDebut = Rank.RANK_2.ordinal(); 
//			 int rankFin = pawnSq.getRank().ordinal()-1;
//			 int fileDebut = pawnSq.getFile().ordinal();
//			 int fileFin = pawnSq.getFile().ordinal();
//			 if (columnPawn == 0) {
//				 fileFin = File.FILE_B.ordinal(); 
//			 } else if (columnPawn == 7) {
//				 fileDebut = File.FILE_G.ordinal(); 
//			 }	else {
//				 fileDebut = pawnSq.getFile().ordinal() - 1;
//				 fileFin = pawnSq.getFile().ordinal() +1;
//			 }
//			 
////			 Square sqDebut = Square.squareAt((Hb2ChessConstants.NB_RANKS*rankDebut)+fileDebut);
////			 Square sqFin = Square.squareAt((Hb2ChessConstants.NB_RANKS*rankFin)+fileFin);
//			 
//			 boolean areEnemyPawnInZone = false;
//			 for (int rankk = rankDebut; rankk <= rankFin; rankk++) {
//				 int rgSqDebutRank  = getRgSquare(rankk, fileDebut);
//				 int rgSqFintRank  = getRgSquare(rankk, fileFin);
//				 long pionsAdversesDansZoneForRank = Bitboard.bitsBetween(bitboardEnemyPawns, rgSqDebutRank, rgSqFintRank);
//				 if (pionsAdversesDansZoneForRank != 0L) {
//					 areEnemyPawnInZone = true;
//					 break;
//				 }
//				 
//			 }
//	
//			 
//			 if (areEnemyPawnInZone == false) {
//				 System.out.println("THE BLACK " + pawnSq.name()+ " PAWN IS A PASSED PAWN!");
//			 }
//			
//			
//		}

		System.out.println("####################");
		System.out.println();
		System.out.println();
		
	}
	
	static void newDeallWithSquareOccupiedByPawn(Square pawnSq, boolean isBlack, Board board) {
	
		newPrintDetailsAboutPawn(pawnSq, isBlack, board);
		
	}

	
	
	static void testFen1() {

		
		testFen("6k1/8/1Pp5/8/8/3P4/1K6/8 w - - 0 1");
		
		
	}
		
	
		
	static void testFen2() {
//		testFen("6k1/8/1Pp5/8/8/3P3p/1K2Pp2/8 w - - 0 1");
		testFen("6k1/8/1Pp5/8/6p1/3P3p/1K2Pp2/8 w - - 0 1");
		
			
			
		}
	static void testFen3() {
		testFen("6k1/8/1Pp5/8/8/7p/1K1PPp2/8 w - - 0 1");
			
			
		}
	
	
	static void testFen(String fen) {
		
//		long bbBlackPawnsOccupancy = 0L;
//		long bbWhitePawnsOccupancy = 0L;
		System.out.println();
		System.out.println("FEN en test pour les pions passés:" + fen);
		Board internal = new Board();
		internal.loadFromFen(fen); 
		ChessLibMoveGenerator mvg = new ChessLibMoveGenerator(internal);
		
		System.out.println(internal.toStringFromWhiteViewPoint());
		
//		BoardExplorer explorer0 = new ChessLibBoardExplorer(mvg.getBoard());
//		do {
//			final int p = explorer0.getPiece();
//			
//			final int kind = Math.abs(p);
//			final int index = explorer0.getIndex();
//			final boolean isBlack = p<0;
//			if (kind == PAWN) {
//				int row = Hb2ChessConstants.INDEX_MAX_RANK - index/Hb2ChessConstants.NB_RANKS;
//				int col = index%Hb2ChessConstants.NB_FILES;
//				Square sqp = Square.squareAt((Hb2ChessConstants.NB_RANKS*row)+col);
//				long bbPawn = Bitboard.getBbtable(sqp);
//				if (isBlack) {
//					bbBlackPawnsOccupancy = bbBlackPawnsOccupancy | bbPawn;
//				} else {
//					bbWhitePawnsOccupancy = bbWhitePawnsOccupancy | bbPawn;
//				}
//				
//
//			}
//			
//		} while (explorer0.next());
//		
//		List<Square> lstSquareWhitePawns = Bitboard.bbToSquareList(bbWhitePawnsOccupancy);
//		BitboardTries.printLstSquare(lstSquareWhitePawns);
//		
//		List<Square> lstSquareBlackPawns = Bitboard.bbToSquareList(bbBlackPawnsOccupancy);
//		BitboardTries.printLstSquare(lstSquareBlackPawns);
		
		
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
				
				newDeallWithSquareOccupiedByPawn(sqp, isBlack, mvg.getBoard());

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

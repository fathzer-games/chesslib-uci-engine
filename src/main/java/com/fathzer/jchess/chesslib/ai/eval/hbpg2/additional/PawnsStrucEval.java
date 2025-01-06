package com.fathzer.jchess.chesslib.ai.eval.hbpg2.additional;

import static com.fathzer.chess.utils.Pieces.PAWN;

import java.util.Arrays;

import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.chess.utils.adapters.MoveData;
import com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2ChessConstants;
import com.github.bhlangonijr.chesslib.Bitboard;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.File;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Rank;
import com.github.bhlangonijr.chesslib.Side;


public class PawnsStrucEval {
	
	
	private static final int MALUS_DOUBLED_PAWNS_MG = -20;
	private static final int MALUS_DOUBLED_PAWNS_EG = -40;
	
	private static final int PASSED_PAWN_BONUS_LESS_THAN_5TH_RANK = 13;
	private static final int PASSED_PAWN_BONUS = 25;
	private static final int PASSED_PAWN_BONUS_6_TH_RANK = 27;
	private static final int PASSED_PAWN_BONUS_7_TH_RANK = 30;
	
	private static final int PASSED_PAWN_BONUS_LESS_THAN_5TH_RANK_EG = 14;
	private static final int PASSED_PAWN_BONUS_EG = 27;
	private static final int PASSED_PAWN_BONUS_6_TH_RANK_EG = 30;
	private static final int PASSED_PAWN_BONUS_7_TH_RANK_EG = 33;
	
	// Here the ranks are relative to the side. For black, the seventh rank is the 2nd rank
	private static final int[] TAB_PASSED_PAWN_BONUS_BY_RANK_MG =
		{0,PASSED_PAWN_BONUS_LESS_THAN_5TH_RANK,
				PASSED_PAWN_BONUS_LESS_THAN_5TH_RANK,
				PASSED_PAWN_BONUS_LESS_THAN_5TH_RANK,
				PASSED_PAWN_BONUS,PASSED_PAWN_BONUS_6_TH_RANK,PASSED_PAWN_BONUS_7_TH_RANK,
				0};
	
	private static final int[] TAB_PASSED_PAWN_BONUS_BY_RANK_EG =
		{0,PASSED_PAWN_BONUS_LESS_THAN_5TH_RANK_EG,
				PASSED_PAWN_BONUS_LESS_THAN_5TH_RANK_EG,
				PASSED_PAWN_BONUS_LESS_THAN_5TH_RANK_EG,
				PASSED_PAWN_BONUS,PASSED_PAWN_BONUS_6_TH_RANK_EG,PASSED_PAWN_BONUS_7_TH_RANK_EG,
				0};
	
	private Board board;
	
	private int[] tabNbWhitePawnsByCol;
	private int[] tabNbBlackPawnsByCol;
	private int bonusWhitePassedPawnsMg;
	private int bonusWhitePassedPawnsEg;
	private int bonusBlackPassedPawnsMg;
	private int bonusBlackPassedPawnsEg;
	
	public PawnsStrucEval() {
		tabNbWhitePawnsByCol = new int[Hb2ChessConstants.NB_FILES];
		tabNbBlackPawnsByCol = new int[Hb2ChessConstants.NB_FILES];
		bonusWhitePassedPawnsEg = 0;
		bonusBlackPassedPawnsMg = 0;
		bonusBlackPassedPawnsMg = 0;
		bonusBlackPassedPawnsEg = 0;
		
	}
	
	public PawnsStrucEval(PawnsStrucEval pse) {
		// Since it's an array of integers, the copy is not a shallow copy
		tabNbWhitePawnsByCol = Arrays.copyOf(pse.tabNbWhitePawnsByCol, Hb2ChessConstants.NB_FILES);
		// Since it's an array of integers, the copy is not a shallow copy
		tabNbBlackPawnsByCol = Arrays.copyOf(pse.tabNbBlackPawnsByCol, Hb2ChessConstants.NB_FILES);
	
	}
	
	public PawnsStrucEval(BoardExplorer explorer, Board board) {
		this();
		
		long bitboardWhitePawns = board.getBitboard(Piece.WHITE_PAWN);
		long bitboardBlackPawns = board.getBitboard(Piece.BLACK_PAWN);
		
		this.board = board;
		for (int i= 0; i < Hb2ChessConstants.NB_FILES; i++) {
			tabNbWhitePawnsByCol[i] = 0;
			tabNbBlackPawnsByCol[i] = 0;
		}

		do {
			final int p = explorer.getPiece();
			final int kind = Math.abs(p);
			final int index = explorer.getIndex();
			final boolean isPieceBlack = p<0;
			if (kind==PAWN) {
				
				final int columnPawn = index%Hb2ChessConstants.NB_FILES;
				int rawPawn = Hb2ChessConstants.INDEX_MAX_RANK - index/Hb2ChessConstants.NB_RANKS;
			
				
				if (isPieceBlack) {
					tabNbBlackPawnsByCol[columnPawn]++;
				} else {
					tabNbWhitePawnsByCol[columnPawn]++;
				}
				
				boolean isPassedPawn = isPawnPassed(rawPawn, columnPawn,  isPieceBlack,  bitboardWhitePawns,  bitboardBlackPawns);
				if (isPassedPawn) {
					
					if (isPieceBlack) {
						bonusBlackPassedPawnsMg += TAB_PASSED_PAWN_BONUS_BY_RANK_MG[Hb2ChessConstants.INDEX_MAX_RANK - rawPawn];
						bonusBlackPassedPawnsEg += TAB_PASSED_PAWN_BONUS_BY_RANK_EG[Hb2ChessConstants.INDEX_MAX_RANK - rawPawn];
						
					} else {
						bonusWhitePassedPawnsMg += TAB_PASSED_PAWN_BONUS_BY_RANK_MG[rawPawn];
						bonusWhitePassedPawnsEg += TAB_PASSED_PAWN_BONUS_BY_RANK_EG[rawPawn];
					}
				}
				
				
			} 
		} while (explorer.next());
	

	}
	
	static int getRgSquare (int rank, int file) {
		return((Hb2ChessConstants.NB_RANKS*rank)+file);
	}
	
	static boolean areEnemyPawnInZone(int rankDebut,int rankFin, int fileDebut, int  fileFin, long bitboardEnemyPawns) {
		
	
		 for (int rankk = rankDebut; rankk <= rankFin; rankk++) {
			 int rgSqDebutRank  = getRgSquare(rankk, fileDebut);
			 int rgSqFintRank  = getRgSquare(rankk, fileFin);
			 long pionsAdversesDansZoneForRank = Bitboard.bitsBetween(bitboardEnemyPawns, rgSqDebutRank, rgSqFintRank);
			 if (pionsAdversesDansZoneForRank != 0L) {
				
				 return(false);
			 }
			 
		 }

		 return(true);
		
		
		
	}
	
	static boolean isWhitePawnPassed(int rawPawn, int colPawn, long bitboardBlackPawns) {
		
		if (bitboardBlackPawns == 0L) {
			return(true);
		}
		// Bon, on part de la rangée devant le pion blanc et on va jusqu'à la 7ème rangée, pour la colonne du pion et ses colonnes adjacentes (au max 2)
				// On regarde s'il y a un pion noir ennemi dans cette zone. Si c'est le cas, alors le pion n'est pas passé. Si cette zone est vide de pions noirs, alors 
				// le pion blanc est passé
				 int rankDebut = rawPawn +1;
				 int rankFin = Rank.RANK_7.ordinal();
				 int fileDebut = colPawn;
				 int fileFin = colPawn;
				 if (colPawn == 0) {
					 fileFin = File.FILE_B.ordinal(); 
				 } else if (colPawn == Hb2ChessConstants.INDEX_MAX_FILE) {
					 fileDebut = File.FILE_G.ordinal(); 
				 }	else {
					 fileDebut = colPawn - 1;
					 fileFin = colPawn +1;
				 }
				 
				boolean  areEnemyPawnInZoneToBeExplored = areEnemyPawnInZone( rankDebut, rankFin,  fileDebut,   fileFin,bitboardBlackPawns);
				 return ( areEnemyPawnInZoneToBeExplored);
			
		
	}
	
	
	static boolean isBlackPawnPassed(int rawPawn, int colPawn, long bitboardWhitePawns) {
		
		if (bitboardWhitePawns == 0L) {
			return(true);
		}
		// Bon, on part de la rangée devant le pion noir (du point de vue noir) et on va jusqu'à la 2ème rangée, pour la colonne du pion et ses colonnes adjacentes (au max 2)
		// On regarde s'il y a un pion blanc ennemi dans cette zone. Si c'est le cas, alors le pion n'est pas passé. Si cette zone est vide de pions blancs, alors 
		// le pion noir est passé
		 int rankDebut = Rank.RANK_2.ordinal(); 
		 int rankFin = rawPawn-1;
		 int fileDebut =colPawn;
		 int fileFin = colPawn;
		 if (colPawn == 0) {
			 fileFin = File.FILE_B.ordinal(); 
		 } else if (colPawn == Hb2ChessConstants.INDEX_MAX_FILE) {
			 fileDebut = File.FILE_G.ordinal(); 
		 }	else {
			 fileDebut = colPawn - 1;
			 fileFin = colPawn +1;
		 }
				 
				boolean  areEnemyPawnInZoneToBeExplored = areEnemyPawnInZone( rankDebut, rankFin,  fileDebut,   fileFin,bitboardWhitePawns);
				 return ( areEnemyPawnInZoneToBeExplored);
			
		
	}
	
	static boolean isPawnPassed(int rawPawn, int colPawn , boolean isBlack, long bitboardWhitePawns, long bitboardBlackPawns) {
		
		
		 
		
		

		
		if (!isBlack) {
		
			return(isWhitePawnPassed( rawPawn,  colPawn,  bitboardBlackPawns));
		} else {
			return(isBlackPawnPassed( rawPawn,  colPawn,  bitboardWhitePawns));
		}
		
	}
	
	void copyTo(PawnsStrucEval other) {
		other.board = board;
		other.tabNbBlackPawnsByCol = tabNbBlackPawnsByCol.clone(); // not a shallow copy!!!! For integers are of primitive type...
		other.tabNbWhitePawnsByCol = tabNbWhitePawnsByCol.clone(); // not a shallow copy!!!!
	}
	
	public void modifyNumberOfBlackPawnsofColumn(int column, int nbPawns ) {
		tabNbBlackPawnsByCol[column] += nbPawns;
	}
	
	public void modifyNumberOfWhitePawnsofColumn(int column, int nbPawns ) {
		tabNbWhitePawnsByCol[column] += nbPawns;
	}
	public void updatePawnsStructEval(MoveData<?,?> move) {
		 updateDoubledPawns(move);
	}
	
	
	private void updateDoubledPawns(MoveData<?,?> move) {
		int kind = Math.abs(move.getMovingPiece());
		if (kind != PAWN) {
			return;
		}
		boolean isBlack = (move.getMovingPiece()<0?true:false);
		int columnPawn = move.getMovingIndex()%Hb2ChessConstants.NB_FILES;
		final int promoType = move.getPromotionType();
		if (promoType!=0) {
			// If promotion, then the pawn disappears.
			
			
			
			// Get the pawn's column and decrement its number of pawns of its color
			if (isBlack) {
				modifyNumberOfBlackPawnsofColumn(columnPawn, (-1));
			} else {
				modifyNumberOfWhitePawnsofColumn(columnPawn, (-1));
			}
			return; // promotion. so we stop here: no chance of a pawn being captured...
		
		}
		
		int captured = move.getCapturedType();
		if (captured!=0) {
			// Well the pawn with change columns...whatever it captures
			int destinationColumnOfThePawn = move.getMovingDestination()%Hb2ChessConstants.NB_FILES;
			if (isBlack) {
				modifyNumberOfBlackPawnsofColumn(columnPawn, (-1));
				modifyNumberOfBlackPawnsofColumn(destinationColumnOfThePawn, 1);
			} else {
				modifyNumberOfWhitePawnsofColumn(columnPawn, (-1));
				modifyNumberOfWhitePawnsofColumn(destinationColumnOfThePawn, 1);
			}
			// If a piece was captured, we don't care; whereas if a pawn is captured, it'as another story...
			if (Math.abs(captured)== PAWN) {
				if (isBlack) {
					// Black pawns are eating white pawns
					modifyNumberOfWhitePawnsofColumn(destinationColumnOfThePawn, (-1));
					
				} else {
					// White pawns are eating black pawns
					modifyNumberOfBlackPawnsofColumn(destinationColumnOfThePawn, (-1));
					
				}
			}

		}
	}
	
	private void updatePasseddPawns(MoveData<?,?> move) {
		//TODO STALINE A CODER!!
		int kind = Math.abs(move.getMovingPiece());
		if (kind != PAWN) {
			return;
		}
		boolean isBlack = (move.getMovingPiece()<0?true:false);
		int columnPawn = move.getMovingIndex()%Hb2ChessConstants.NB_FILES;
		final int promoType = move.getPromotionType();
		if (promoType!=0) {
			// If promotion, then the pawn disappears.
			
			
			
			// Get the pawn's column and decrement its number of pawns of its color
			if (isBlack) {
				modifyNumberOfBlackPawnsofColumn(columnPawn, (-1));
			} else {
				modifyNumberOfWhitePawnsofColumn(columnPawn, (-1));
			}
			return; // promotion. so we stop here: no chance of a pawn being captured...
		
		}
		
		int captured = move.getCapturedType();
		if (captured!=0) {
			// Well the pawn with change columns...whatever it captures
			int destinationColumnOfThePawn = move.getMovingDestination()%Hb2ChessConstants.NB_FILES;
			if (isBlack) {
				modifyNumberOfBlackPawnsofColumn(columnPawn, (-1));
				modifyNumberOfBlackPawnsofColumn(destinationColumnOfThePawn, 1);
			} else {
				modifyNumberOfWhitePawnsofColumn(columnPawn, (-1));
				modifyNumberOfWhitePawnsofColumn(destinationColumnOfThePawn, 1);
			}
			// If a piece was captured, we don't care; whereas if a pawn is captured, it'as another story...
			if (Math.abs(captured)== PAWN) {
				if (isBlack) {
					// Black pawns are eating white pawns
					modifyNumberOfWhitePawnsofColumn(destinationColumnOfThePawn, (-1));
					
				} else {
					// White pawns are eating black pawns
					modifyNumberOfBlackPawnsofColumn(destinationColumnOfThePawn, (-1));
					
				}
			}

		}
	}
	
	public int getContribDoubledPawnsMg() {
		
		// white doubled pawns
		int malusW = 0;
		for (int i = 0; i < Hb2ChessConstants.NB_FILES; i++) {
			if (tabNbWhitePawnsByCol[i] > 1) {
				malusW += tabNbWhitePawnsByCol[i] * MALUS_DOUBLED_PAWNS_MG;
			}
		}
		
		// black doubled pawns
		int malusB = 0;
		for (int i = 0; i < Hb2ChessConstants.NB_FILES; i++) {
			if (tabNbBlackPawnsByCol[i] > 1) {
				malusB += tabNbBlackPawnsByCol[i] * MALUS_DOUBLED_PAWNS_MG;
			}
		}
		return(malusW - malusB);
		
	}
	
	public int getContribPassedPawnsMg() {
		return(bonusWhitePassedPawnsMg - bonusBlackPassedPawnsMg);
		
	
	}
	
	
	public int getContribMg() {
		
		int ctrbDoubledPawns = getContribDoubledPawnsMg();
		int ctrbPassedPawns = getContribPassedPawnsMg();
		
		return(ctrbDoubledPawns+ctrbPassedPawns);
	
	
	}
	
	

	public int getContribDoubledPawnsEg() {
		
		// white doubled pawns
				int malusW = 0;
				for (int i = 0; i < Hb2ChessConstants.NB_FILES; i++) {
					if (tabNbWhitePawnsByCol[i] > 1) {
						malusW += Hb2ChessConstants.NB_MAX_PAWNS_TAKEN_INTO_ACCOUNT_FOR_MALUS_DOUBLED_PAWNS * MALUS_DOUBLED_PAWNS_EG;
					}
				}
				
				// black doubled pawns
				int malusB = 0;
				for (int i = 0; i < Hb2ChessConstants.NB_FILES; i++) {
					if (tabNbBlackPawnsByCol[i] > 1) {
						malusB += Hb2ChessConstants.NB_MAX_PAWNS_TAKEN_INTO_ACCOUNT_FOR_MALUS_DOUBLED_PAWNS * MALUS_DOUBLED_PAWNS_EG;
					}
				}
				return(malusW - malusB);
		
	}
	
	public int getContribPassedPawnsEg() {
		return(bonusWhitePassedPawnsEg - bonusBlackPassedPawnsEg);
		
	
	}
	
	public int getContribEg() {
		
		int ctrbDoubledPawns = getContribDoubledPawnsEg();
		int ctrbPassedPawns = getContribPassedPawnsEg();
		
		return(ctrbDoubledPawns+ctrbPassedPawns);
		
	}
	
	
	
}

package com.fathzer.jchess.chesslib.ai.eval.hbpg2;

public abstract class Hb2ChessConstants {
	public static final int NB_RAWS = 8;
	public static final int NB_COLS = 8;
	
	public static final int MALUS_DOUBLED_PAWNS_MG = -20;
	public static final int MALUS_DOUBLED_PAWNS_EG = -40;
	
	public static final int NB_MAX_PAWNS_TAKEN_INTO_ACCOUNT_FOR_MALUS_DOUBLED_PAWNS = 2; // Beyond, it could be counterproductive...according to many famous chess engines specs

}

package com.fathzer.jchess.chesslib.ai.eval.hbpg2.additional;


import java.util.List;


import com.fathzer.chess.utils.adapters.MoveData;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;

//Config/EvaluationConfig.cs:    public int MgKnightMobilityScale = 77;
//Config/EvaluationConfig.cs:    public int EgKnightMobilityScale = 39;
//Config/EvaluationConfig.cs:    public int MgBishopMobilityScale = 62;
//Config/EvaluationConfig.cs:    public int EgBishopMobilityScale = 185;
//Config/EvaluationConfig.cs:    public int MgRookMobilityScale = 85;
//Config/EvaluationConfig.cs:    public int EgRookMobilityScale = 154;
//Config/EvaluationConfig.cs:    public int MgQueenMobilityScale = 87;
//Config/EvaluationConfig.cs:    public int EgQueenMobilityScale = 78;

public class MobilityEval {
	
	private final static int MOBILITY_MULTIPLIER = 5;
	private final static double MOBILITY_RATIO_FACTOR = 10.0d;
	private final static int MOBILITY_MG_EG_RATIO_FACTOR_NUMERATOR = 110;
	private final static int MOBILITY_MG_EG_RATIO_FACTOR_DENOMINATOR = 100;
	
	
	private static int mobility(Board board, Side sidePointOfView) {
		return MOBILITY_MULTIPLIER * mobilityRatio(board, sidePointOfView);
	}

	private static int mobilityRatio(Board board, Side sidePointOfView) {
		if (board.getSideToMove() == sidePointOfView) {
			List<Move> lstMov = board.legalMoves();
			if (board.doNullMove()) {
				List<Move> lstOpponenMov = board.legalMoves();
				
				board.undoMove();
				return (int) ((lstMov.size() * MOBILITY_RATIO_FACTOR) / lstOpponenMov.size());
			} 
			
		} else {
			
			List<Move> lstOpponenMov = board.legalMoves();
			if (board.doNullMove()) {
				List<Move> lstMov = board.legalMoves();
				
				board.undoMove();
				return (int) ((lstMov.size() * MOBILITY_RATIO_FACTOR) / lstOpponenMov.size());
			} 			
			
		}
		// If we reach this point it's Stalingrad german side...
		return(0); // no mobility in the evaluation function in case of error: better than nothing
	}
	
	private int bonusWhiteMobilityMg;
	private int bonusWhiteMobilityEg;
	private int bonusBlackMobilityMg;
	private int bonusBlackMobilityEg;
	
	public MobilityEval() {
		
		bonusWhiteMobilityMg = 0;
		bonusWhiteMobilityEg = 0;
		bonusBlackMobilityMg = 0;
		bonusBlackMobilityEg = 0;
		
	}
	
	public MobilityEval(MobilityEval mbe) {
		
		
	
		
		bonusWhiteMobilityMg = mbe.bonusWhiteMobilityMg;
		bonusWhiteMobilityEg = mbe.bonusWhiteMobilityEg;
		bonusBlackMobilityMg = mbe.bonusBlackMobilityMg;
		bonusBlackMobilityEg = mbe.bonusBlackMobilityEg;
	
	}
	
	public MobilityEval( Board board) {
		this();
		bonusWhiteMobilityMg = mobility(board, Side.WHITE);
		bonusWhiteMobilityEg = bonusWhiteMobilityMg * MOBILITY_MG_EG_RATIO_FACTOR_NUMERATOR / MOBILITY_MG_EG_RATIO_FACTOR_DENOMINATOR;
		bonusBlackMobilityMg = mobility(board, Side.BLACK);
		bonusBlackMobilityEg = bonusWhiteMobilityMg * MOBILITY_MG_EG_RATIO_FACTOR_NUMERATOR / MOBILITY_MG_EG_RATIO_FACTOR_DENOMINATOR;
		
		
	}
	
	public int getContribMg() {
	
		
		return(bonusWhiteMobilityMg - bonusBlackMobilityMg);
	
	
	}
	
	


	
	
	public int getContribEg() {
	
		
		return(bonusWhiteMobilityEg - bonusBlackMobilityEg);
		
	}
	

	
	public void computeMobility(MoveData<?,?> move, Board board) {
		bonusWhiteMobilityMg = mobility(board, Side.WHITE);
		bonusWhiteMobilityEg = bonusWhiteMobilityMg * MOBILITY_MG_EG_RATIO_FACTOR_NUMERATOR / MOBILITY_MG_EG_RATIO_FACTOR_DENOMINATOR;
		bonusBlackMobilityMg = mobility(board, Side.BLACK);
		bonusBlackMobilityEg = bonusWhiteMobilityMg * MOBILITY_MG_EG_RATIO_FACTOR_NUMERATOR / MOBILITY_MG_EG_RATIO_FACTOR_DENOMINATOR;
		
		
	}
	

}

package com.fathzer.jchess.chesslib.eval;

import static com.fathzer.games.Color.*;
import static com.github.bhlangonijr.chesslib.PieceType.*;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import com.fathzer.games.Color;
import com.fathzer.games.ai.evaluation.Evaluator;
import com.fathzer.games.util.Stack;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

public class BasicEvaluator implements Evaluator<Move, ChessLibMoveGenerator> {
	public static final Map<PieceType, Integer> PIECE_VALUE;
	
	private final Stack<Integer> scores;
	private int toCommit; 
	private Side viewPoint;
	
	static {
		Map<PieceType, Integer> map = new EnumMap<>(PieceType.class);
		map.put(PieceType.QUEEN, 9);
		map.put(PieceType.ROOK, 5);
		map.put(PieceType.BISHOP, 3);
		map.put(PieceType.KNIGHT, 3);
		map.put(PieceType.PAWN, 1);
		map.put(PieceType.KING, 1000);
		PIECE_VALUE = Collections.unmodifiableMap(map);
	}
	
	public BasicEvaluator(ChessLibMoveGenerator board) {
		this(getPoints(board.getBoard()));
	}
	
	private BasicEvaluator(int score) {
		this.scores = new Stack<>(null);
		scores.set(score);
	}
	
	@Override
	public void setViewPoint(Color viewPoint) {
		if (viewPoint==null) {
			this.viewPoint = null;
		} else {
			this.viewPoint = viewPoint==WHITE ? Side.WHITE : Side.BLACK;
		}
	}
	
	public boolean hasViewPoint() {
		return viewPoint!=null;
	}

	@Override
	public int evaluate(ChessLibMoveGenerator board) {
		int points = 100*scores.get();
		if (Side.BLACK==viewPoint || (viewPoint==null && Side.BLACK==board.getBoard().getSideToMove())) {
			points = -points;
		}
		return points;
	}

	public static int getPoints(Board board) {
		int points = 0;
		for (Piece p : board.boardToArray()) {
			if (p!=Piece.NONE) {
				int inc = PIECE_VALUE.get(p.getPieceType());
				if (p.getPieceSide()==Side.WHITE) {
					points += inc;
				} else {
					points -= inc;
				}
			}
		}
		return points;
	}
	
	@Override
	public void prepareMove(ChessLibMoveGenerator board, Move move) {
		final Board internal = board.getBoard();
		int increment = 0;
		if (!internal.getContext().isCastleMove(move)) {
	        Piece movingPiece = internal.getPiece(move.getFrom());
	        Piece capturedPiece = internal.getPiece(move.getTo());
	        if (!Square.NONE.equals(internal.getEnPassantTarget()) &&
	                PAWN.equals(movingPiece.getPieceType()) &&
	                !move.getTo().getFile().equals(move.getFrom().getFile()) &&
	                Piece.NONE.equals(capturedPiece)) {
	            increment = PIECE_VALUE.get(PAWN);
	        } else {
		        if (capturedPiece!=Piece.NONE) {
		        	increment = PIECE_VALUE.get(capturedPiece.getPieceType());
		        }
		        if (move.getPromotion()!=Piece.NONE) {
		        	increment = increment + PIECE_VALUE.get(move.getPromotion().getPieceType())-1;
		        }
	        }
			if (board.getBoard().getSideToMove()!=Side.WHITE) {
				increment = -increment;
			}
		}
		toCommit = scores.get()+increment;
	}
	
	@Override
	public void commitMove() {
		scores.next();
		scores.set(toCommit);
	}

	@Override
	public void unmakeMove() {
		scores.previous();
	}

	@Override
	public Evaluator<Move, ChessLibMoveGenerator> fork() {
		return new BasicEvaluator(scores.get());
	}
}

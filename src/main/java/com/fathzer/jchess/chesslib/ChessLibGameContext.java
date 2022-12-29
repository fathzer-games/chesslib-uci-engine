package com.fathzer.jchess.chesslib;

import java.util.Iterator;
import java.util.List;
import java.util.function.ToIntFunction;

import com.fathzer.games.GameState;
import com.fathzer.games.Status;
import com.fathzer.games.ai.GameContext;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;

public class ChessLibGameContext implements GameContext<Move> {
	private ToIntFunction<Board> evaluator;
	private Board board;
	
	public ChessLibGameContext(ToIntFunction<Board> evaluator, Board board) {
		this.evaluator = evaluator;
		this.board = board.clone();
	}
	
	@Override
	public void makeMove(Move move) {
		board.doMove(move);
	}
	
	@Override
	public void unmakeMove() {
//System.out.println("Unmake move "+move+" on thread "+Thread.currentThread()+" at depth "+currentDepth);
		board.undoMove();
	}
	
	@Override
	public GameState<Move> getState() {
		final List<Move> moves = board.legalMoves();
		final Status status;
		if (moves.isEmpty()) {
			final boolean isCheck = board.isKingAttacked();
			if (isCheck) {
				status = Side.BLACK.equals(board.getSideToMove()) ? Status.WHITE_WON : Status.BLACK_WON;
			} else {
				status = Status.DRAW;
			}
		} else {
			status = Status.PLAYING;
		}
		return new GameState<Move>() {
			@Override
			public Status getStatus() {
				return status;
			}

			@Override
			public int size() {
				return moves.size();
			}

			@Override
			public Move get(int index) {
				return moves.get(index);
			}

			@Override
			public Iterator<Move> iterator() {
				return moves.iterator();
			}
		}; 
	}

	@Override
	public int evaluate() {
		return -evaluator.applyAsInt(board);
	}
}

package com.fathzer.jchess.chesslib;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;

import com.fathzer.games.MoveGenerator;
import com.fathzer.games.HashProvider;
import com.fathzer.games.Status;
import com.fathzer.jchess.chesslib.ai.BasicMoveComparator;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;

public class ChessLibMoveGenerator implements MoveGenerator<Move>, HashProvider {
	private final Board board;
	private Function<ChessLibMoveGenerator, Comparator<Move>> moveComparatorBuilder;
	private Comparator<Move> comparator;
	
	public ChessLibMoveGenerator(Board board) {
		this.board = board;
	}
	
	@Override
	public boolean makeMove(Move move, MoveConfidence confidence) {
		try {
			return board.doMove(move, MoveConfidence.UNSAFE==confidence);
		} catch (RuntimeException e) {
			// Can throw an exception if no piece is at move from cell
			return false;
		}
	}
	
	@Override
	public void unmakeMove() {
		board.undoMove();
	}
	
	@Override
	public List<Move> getMoves(boolean quiesce) {
		final List<Move> moves = quiesce ? board.pseudoLegalCaptures() : board.pseudoLegalMoves();
		if (comparator!=null) {
			sort(moves);
		}
		return moves;
	}
	
	private void sort(List<Move> moves) {
		final List<Move> toBeSorted = new ArrayList<>();
		final BasicMoveComparator cmp = (BasicMoveComparator) comparator; //TODO
		ListIterator<Move> iter = moves.listIterator();
		while (iter.hasNext()) {
			Move m = iter.next();
			if (cmp.getMoveValue(m)!=0) {
				toBeSorted.add(m);
				iter.remove();
			}
		}
		toBeSorted.sort(cmp);
		moves.addAll(0, toBeSorted);
	}
	
	@Override
	public List<Move> getLegalMoves() {
		return board.legalMoves();
	}

	@Override
	public long getHashKey() {
		return board.getZobristKey();
	}
	
	public Board getBoard() {
		return this.board; 
	}

	public void setMoveComparatorBuilder(Function<ChessLibMoveGenerator, Comparator<Move>> moveComparatorBuilder) {
		this.moveComparatorBuilder = moveComparatorBuilder;
		if (moveComparatorBuilder==null) {
			comparator = null;
		} else {
			comparator = moveComparatorBuilder.apply(this);
		}
	}

	@Override
	public Status getContextualStatus() {
		return board.getHalfMoveCounter()>=100 || board.isInsufficientMaterial() || board.isRepetition() ? Status.DRAW : Status.PLAYING;
	}

	@Override
	public Status getEndGameStatus() {
		if (board.isKingAttacked()) {
			return board.getSideToMove()==Side.BLACK ? Status.WHITE_WON : Status.BLACK_WON;
		} else {
			return Status.DRAW;
		}
	}

	@Override
	public MoveGenerator<Move> fork() {
		final ChessLibMoveGenerator result = new ChessLibMoveGenerator(board.clone());
		result.setMoveComparatorBuilder(moveComparatorBuilder);
		return result;
	}
}

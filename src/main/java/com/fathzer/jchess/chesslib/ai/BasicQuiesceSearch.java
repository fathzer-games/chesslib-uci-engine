package com.fathzer.jchess.chesslib.ai;

import java.util.List;

import com.fathzer.chess.utils.evaluators.quiesce.AbstractBasicQuiesceEvaluator;
import com.fathzer.games.ai.SearchContext;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

public class BasicQuiesceSearch extends AbstractBasicQuiesceEvaluator<Move, ChessLibMoveGenerator> {
	@Override
	protected List<Move> getMoves(SearchContext<Move, ChessLibMoveGenerator> context, int quiesceDepth) {
		final ChessLibMoveGenerator gamePosition = context.getGamePosition();
		final Board board = gamePosition.getBoard();
		final List<Move> moves = board.isKingAttacked() ? board.pseudoLegalMoves() : board.pseudoLegalCaptures();
		moves.sort(new BasicMoveComparator(gamePosition));
		return moves;
	}

	@Override
	protected boolean isCheck(SearchContext<Move, ChessLibMoveGenerator> context) {
		return context.getGamePosition().getBoard().isKingAttacked();
	}
}

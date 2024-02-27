package com.fathzer.jchess.chesslib.ai;

import java.util.List;

import com.fathzer.chess.utils.evaluators.quiesce.AbstractBasicQuiesceEvaluator;
import com.fathzer.games.ai.SearchContext;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.move.Move;

public class BasicQuiesceSearch extends AbstractBasicQuiesceEvaluator<Move, ChessLibMoveGenerator> {
	@Override
	protected List<Move> getMoves(SearchContext<Move, ChessLibMoveGenerator> context, int quiesceDepth) {
		final ChessLibMoveGenerator gamePosition = context.getGamePosition();
		final List<Move> moves = gamePosition.getBoard().pseudoLegalCaptures();
		moves.sort(new BasicMoveComparator(gamePosition));
		return moves;
	}

}

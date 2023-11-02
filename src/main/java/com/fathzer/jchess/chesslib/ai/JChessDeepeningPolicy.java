package com.fathzer.jchess.chesslib.ai;

import java.util.List;

import com.fathzer.games.ai.SearchResult;
import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.iterativedeepening.DeepeningPolicy;

final class ChessLibDeepeningPolicy extends DeepeningPolicy {
	
	protected ChessLibDeepeningPolicy(long maxTimeMs) {
		super(maxTimeMs);
	}
	
	@Override
	public int getNextDepth(int currentDepth) {
		return currentDepth < 5 ? currentDepth+2 : currentDepth+1;
	}

	@Override
	public boolean isEnoughTimeToDeepen(int depth) {
		return depth<5 || getSpent()<getMaxTime()/3;
	}

	@Override
	public <M> void mergeInterrupted(SearchResult<M> bestMoves, int bestMovesDepth, List<EvaluatedMove<M>> partialList, int interruptionDepth) {
		if ((interruptionDepth - bestMovesDepth)%2==0) {
			//TODO Remove when quiesce will be implemented
			// Do not merge results if depth are optimistic and pessimistic. 
			super.mergeInterrupted(bestMoves, bestMovesDepth, partialList, interruptionDepth);
		}
	}
}
package com.fathzer.jchess.chesslib.ai;

import java.util.List;

import com.fathzer.games.ai.SearchResult;
import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.iterativedeepening.DeepeningPolicy;

public final class ChessLibDeepeningPolicy extends DeepeningPolicy {
	
	public ChessLibDeepeningPolicy(int maxDepth) {
		super(maxDepth);
	}
	
	@Override
	public int getNextDepth(int currentDepth) {
		int candidate = currentDepth < 5 ? currentDepth+2 : currentDepth+1;
		return candidate>this.getDepth() ? this.getDepth() : candidate;
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
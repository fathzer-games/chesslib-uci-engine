package com.fathzer.jchess.chesslib.ai;

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
		final long spent = getSpent();
		return spent<getMaxTime() && (depth<5 || spent<getMaxTime()/3);
	}
}
package com.fathzer.jchess.chesslib.eval;

public class BasicEvalState implements EvaluationState {
	private int score;

	public BasicEvalState(int score) {
		super();
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return Integer.toString(score);
	}
}
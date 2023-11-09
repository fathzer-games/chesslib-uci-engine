package com.fathzer.jchess.chesslib.eval;

public interface EvaluationState {
	int getScore();
	void setScore(int score); //TODO Probably not the right interface for managing incremental eval with phases
}

package com.fathzer.jchess.chesslib.eval;

import com.fathzer.games.MoveGenerator;
import com.fathzer.games.ai.evaluation.Evaluator;

public interface IncrementalEvaluator<M, B extends MoveGenerator<M>, S extends EvaluationState> extends Evaluator<B> {
	int rawEvaluate(B board);
	S newState(int score);
	int getIncrement(B board, M move, int currentScore);
}

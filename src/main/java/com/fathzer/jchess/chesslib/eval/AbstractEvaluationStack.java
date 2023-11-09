package com.fathzer.jchess.chesslib.eval;

import java.util.ArrayList;
import java.util.List;

import com.fathzer.games.MoveGenerator;

public class AbstractEvaluationStack<M, B extends MoveGenerator<M>, S extends EvaluationState> {
	final B mg;
	private final IncrementalEvaluator<M, B, S> evaluator;
	private final List<S> evalStates;
	private S currentState;
	private int currentStateIndex;

	public AbstractEvaluationStack(B mg, IncrementalEvaluator<M, B, S> evaluator) {
		this.mg = mg;
		this.evaluator = evaluator;
		evalStates = new ArrayList<>();
		this.currentState = evaluator.newState(this.evaluator.rawEvaluate(mg));
		evalStates.add(this.currentState);
		this.currentStateIndex = 0;
	}
	
	public int getCurrentEvaluation() {
		return currentState.getScore();
	}

	public void prepareMove(M move) {
//		if (evaluator.rawEvaluate(mg)!=currentState.getScore()) {
//			throw new IllegalStateException("A very bad situation");
//		}
		getNext().setScore(evaluator.getIncrement(mg, move, currentState.getScore()));
	}
	
	public void commitMove(M move) {
		currentStateIndex++;
		currentState = evalStates.get(currentStateIndex);
		//TODO Remove when fixed
//		final int raw = evaluator.rawEvaluate(this.mg);
//		if (getCurrentEvaluation()!=raw) {
//			throw new IllegalStateException("Something is wrong in make move: "+raw+"!="+getCurrentEvaluation());
//		}
		//End of TODO
	}
	
	private S getNext() {
		final S state;
		if (currentStateIndex+1>=evalStates.size()) {
			state = evaluator.newState(0);
			evalStates.add(state);
		} else {
			state = evalStates.get(currentStateIndex+1);
		}
		return state;
	}
	
	public void unmakeMove() {
		currentStateIndex--;
		currentState = evalStates.get(currentStateIndex);
	}
}

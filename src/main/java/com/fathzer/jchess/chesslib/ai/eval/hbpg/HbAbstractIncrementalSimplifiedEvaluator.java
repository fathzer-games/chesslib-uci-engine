package com.fathzer.jchess.chesslib.ai.eval.hbpg;

import java.util.function.Supplier;

import com.fathzer.chess.utils.adapters.MoveData;
import com.fathzer.games.MoveGenerator;
import com.fathzer.games.ai.evaluation.Evaluator;
import com.fathzer.games.ai.evaluation.ZeroSumEvaluator;
import com.fathzer.games.util.Stack;

/** An incremental implementation of the simplified evaluator described at <a href="https://www.chessprogramming.org/Simplified_Evaluation_Function">https://www.chessprogramming.org/Simplified_Evaluation_Function</a>
 * <br>This only works with 8*8 games and exactly one king per Color.
 */
public abstract class HbAbstractIncrementalSimplifiedEvaluator<M, B extends MoveGenerator<M>> extends HbSimplifiedEvaluatorBase<M, B> implements ZeroSumEvaluator<M,B>, Supplier<MoveData<M,B>> {
	private final Stack<HbIncrementalState> states;
	private HbIncrementalState toCommit;
	private MoveData<M, B> moveData;
	
	/** Default constructor
	 */
	protected HbAbstractIncrementalSimplifiedEvaluator() {
		this.states = new Stack<>(HbIncrementalState::new);
		this.moveData = get();
	}
	
	/** Constructor.
	 * @param state The state to initialize the evaluator
	 */
	protected HbAbstractIncrementalSimplifiedEvaluator(HbIncrementalState state) {
		this();
		HbIncrementalState other = new HbIncrementalState();
		state.copyTo(other);
		states.set(state);
	}


	@Override
	public Evaluator<M, B> fork() {
		return fork(states.get());
	}
	
	/** Creates a new instance initialized with current state that will become the initial state of created instance.
	 * @param state The initial state.
	 * @return a new evaluator of the same class as this, this the same view point, and initialized with the state.
	 */
	protected abstract HbAbstractIncrementalSimplifiedEvaluator<M, B> fork(HbIncrementalState state);

	@Override
	public void init(B board) {
		states.clear();
		states.set(new HbIncrementalState(getExplorer(board)));
	}

	@Override
	public void prepareMove(B board, M move) {
		if (moveData.update(move, board)) {
			buildToCommit();
			toCommit.update(moveData);
		}
	}
	
	private void buildToCommit() {
		final HbIncrementalState current = states.get();
		states.next();
		toCommit = states.get();
		states.previous();
		current.copyTo(toCommit);
	}

	@Override
	public void commitMove() {
		states.next();
		states.set(toCommit);
	}

	@Override
	public void unmakeMove() {
		states.previous();
	}

	@Override
	public int evaluateAsWhite(B board) {
		return states.get().evaluateAsWhite();
	}
	
	HbIncrementalState getState() {
		return states.get();
	}
}

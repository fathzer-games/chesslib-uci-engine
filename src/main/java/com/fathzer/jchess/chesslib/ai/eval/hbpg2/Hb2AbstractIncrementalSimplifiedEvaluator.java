package com.fathzer.jchess.chesslib.ai.eval.hbpg2;

import java.util.function.Supplier;

import com.fathzer.chess.utils.adapters.MoveData;
import com.fathzer.games.MoveGenerator;
import com.fathzer.games.ai.evaluation.Evaluator;
import com.fathzer.games.ai.evaluation.ZeroSumEvaluator;
import com.fathzer.games.util.Stack;
<<<<<<< Upstream, based on origin/main
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;

/** An incremental implementation of the simplified evaluator described at <a href="https://www.chessprogramming.org/Simplified_Evaluation_Function">https://www.chessprogramming.org/Simplified_Evaluation_Function</a>
 * <br>This only works with 8*8 games and exactly one king per Color.
 */
public abstract class Hb2AbstractIncrementalSimplifiedEvaluator<M, B extends MoveGenerator<M>> extends Hb2SimplifiedEvaluatorBase<M, B> implements ZeroSumEvaluator<M,B>, Supplier<MoveData<M,B>> {
	private final Stack<Hb2IncrementalState> states;
	private Hb2IncrementalState toCommit;
	private MoveData<M, B> moveData;
	
	/** Default constructor
	 */
	protected Hb2AbstractIncrementalSimplifiedEvaluator() {
		this.states = new Stack<>(Hb2IncrementalState::new);
		this.moveData = get();
	}
	
	/** Constructor.
	 * @param state The state to initialize the evaluator
	 */
	protected Hb2AbstractIncrementalSimplifiedEvaluator(Hb2IncrementalState state) {
		this();
		Hb2IncrementalState other = new Hb2IncrementalState();
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
	protected abstract Hb2AbstractIncrementalSimplifiedEvaluator<M, B> fork(Hb2IncrementalState state);

	@Override
	public void init(B board) {
		states.clear();
		states.set(new Hb2IncrementalState(getExplorer(board), ((ChessLibMoveGenerator)board).getBoard()));
		
=======

/** An incremental implementation of the simplified evaluator described at <a href="https://www.chessprogramming.org/Simplified_Evaluation_Function">https://www.chessprogramming.org/Simplified_Evaluation_Function</a>
 * <br>This only works with 8*8 games and exactly one king per Color.
 */
public abstract class Hb2AbstractIncrementalSimplifiedEvaluator<M, B extends MoveGenerator<M>> extends Hb2SimplifiedEvaluatorBase<M, B> implements ZeroSumEvaluator<M,B>, Supplier<MoveData<M,B>> {
	private final Stack<Hb2IncrementalState> states;
	private Hb2IncrementalState toCommit;
	private MoveData<M, B> moveData;
	
	/** Default constructor
	 */
	protected Hb2AbstractIncrementalSimplifiedEvaluator() {
		this.states = new Stack<>(Hb2IncrementalState::new);
		this.moveData = get();
	}
	
	/** Constructor.
	 * @param state The state to initialize the evaluator
	 */
	protected Hb2AbstractIncrementalSimplifiedEvaluator(Hb2IncrementalState state) {
		this();
		Hb2IncrementalState other = new Hb2IncrementalState();
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
	protected abstract Hb2AbstractIncrementalSimplifiedEvaluator<M, B> fork(Hb2IncrementalState state);

	@Override
	public void init(B board) {
		states.clear();
		states.set(new Hb2IncrementalState(getExplorer(board)));
>>>>>>> d32a00b Hb2MyFirstEvaluator: the beginning. Compared to the SimplifiedEvaluator the evaluation function is tapered (scale = 64)
	}

	@Override
	public void prepareMove(B board, M move) {
		if (moveData.update(move, board)) {
			buildToCommit();
			toCommit.update(moveData);
		}
	}
	
	private void buildToCommit() {
		final Hb2IncrementalState current = states.get();
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
	
	Hb2IncrementalState getState() {
		return states.get();
	}
}

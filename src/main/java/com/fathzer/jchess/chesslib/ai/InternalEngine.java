package com.fathzer.jchess.chesslib.ai;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fathzer.games.Color;
import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.evaluation.Evaluator;
import com.fathzer.games.ai.exec.ExecutionContext;
import com.fathzer.games.ai.exec.MultiThreadsContext;
import com.fathzer.games.ai.exec.SingleThreadContext;
import com.fathzer.games.ai.iterativedeepening.FirstBestMoveSelector;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningSearch;
import com.fathzer.games.ai.moveSelector.RandomMoveSelector;
import com.fathzer.games.ai.moveSelector.StaticMoveSelector;
import com.fathzer.games.ai.transposition.SizeUnit;
import com.fathzer.games.util.ContextualizedExecutor;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;

public class InternalEngine extends IterativeDeepeningEngine<Move, ChessLibMoveGenerator> {
	private static final Logger log = LoggerFactory.getLogger(InternalEngine.class);
	
	private Function<ChessLibMoveGenerator, Comparator<Move>> moveComparatorSupplier;
	
	public InternalEngine(Evaluator<ChessLibMoveGenerator> evaluator , int maxDepth) {
		super(evaluator, maxDepth, new TT(16, SizeUnit.MB));
		setDeepeningPolicyBuilder(() -> new ChessLibDeepeningPolicy(getMaxTime()));
		moveComparatorSupplier = BasicMoveComparator::new;
		setLogger(new DefaultLogger(this));
	}
	
	public void setMoveComparatorSupplier(Function<ChessLibMoveGenerator, Comparator<Move>> moveComparatorSupplier) {
		this.moveComparatorSupplier = moveComparatorSupplier;
	}

	@Override
	protected ExecutionContext<Move, ChessLibMoveGenerator> buildExecutionContext(ChessLibMoveGenerator board) {
		board.setMoveComparator(moveComparatorSupplier.apply(board));
		if (getParallelism()==1) {
			return new SingleThreadContext<>(board);
		} else {
			final Supplier<ChessLibMoveGenerator> supplier = () -> {
				final ChessLibMoveGenerator mg = new ChessLibMoveGenerator(board.getBoard());
				mg.setMoveComparator(moveComparatorSupplier.apply(mg));
				return mg;
			};
			return new MultiThreadsContext<>(supplier, new ContextualizedExecutor<>(getParallelism()));
		}
	}

	@Override
	protected void setViewPoint(Evaluator<ChessLibMoveGenerator> evaluator, ChessLibMoveGenerator board) {
		evaluator.setViewPoint(board.getBoard().getSideToMove()==Side.BLACK ? Color.BLACK : Color.WHITE);
	}

	@Override
	public Move apply(ChessLibMoveGenerator board) {
		final BasicMoveComparator c = new BasicMoveComparator(board); 
		super.setMoveSelector(new FirstBestMoveSelector<Move>().setNext(new StaticMoveSelector<Move,IterativeDeepeningSearch<Move>>(c::getValue).setNext(new RandomMoveSelector<>())));
		final IterativeDeepeningSearch<Move> search = search(board);
		final List<EvaluatedMove<Move>> bestMoves = this.getMoveSelector().select(search, search.getBestMoves());
		final EvaluatedMove<Move> evaluatedMove = bestMoves.get(0);
		Move move = evaluatedMove.getContent();
		log.info("Move choosen :{}", move);
		final List<Move> pv = evaluatedMove.getPrincipalVariation();
		log.info("pv: {}", pv);
		return move;
	}
	
	@Override
	protected IterativeDeepeningSearch<Move> search(ChessLibMoveGenerator board) {
		log.info("--- Start evaluation for {} with size={}, accuracy={}, maxDepth={}---", board.getBoard().getFen(), getSearchParams().getSize(), getSearchParams().getAccuracy(), getSearchParams().getDepth());
		IterativeDeepeningSearch<Move> search = super.search(board);
		log.info("--- End of iterative evaluation returns: {}", search.getBestMoves());
		return search;
	}
}

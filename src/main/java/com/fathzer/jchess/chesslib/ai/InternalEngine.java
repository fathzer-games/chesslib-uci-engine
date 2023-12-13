package com.fathzer.jchess.chesslib.ai;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fathzer.games.Color;
import com.fathzer.games.ai.SearchContext;
import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.evaluation.Evaluator;
import com.fathzer.games.ai.iterativedeepening.FirstBestMoveSelector;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningSearch;
import com.fathzer.games.ai.moveSelector.RandomMoveSelector;
import com.fathzer.games.ai.moveSelector.StaticMoveSelector;
import com.fathzer.games.ai.transposition.SizeUnit;
import com.fathzer.games.util.exec.ContextualizedExecutor;
import com.fathzer.games.util.exec.ExecutionContext;
import com.fathzer.games.util.exec.MultiThreadsContext;
import com.fathzer.games.util.exec.SingleThreadContext;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;

public class InternalEngine extends IterativeDeepeningEngine<Move, ChessLibMoveGenerator> {
	private static final Logger log = LoggerFactory.getLogger(InternalEngine.class);
	
	private Function<ChessLibMoveGenerator, Comparator<Move>> moveComparatorSupplier;
	private Function<ChessLibMoveGenerator, Evaluator<Move, ChessLibMoveGenerator>> evaluatorBuilder;
	
	public InternalEngine(Function<ChessLibMoveGenerator, Evaluator<Move, ChessLibMoveGenerator>> evaluatorBuilder , int maxDepth) {
		super(maxDepth, new TT(16, SizeUnit.MB));
		setDeepeningPolicy(new ChessLibDeepeningPolicy(maxDepth));
		moveComparatorSupplier = BasicMoveComparator::new;
		this.evaluatorBuilder = evaluatorBuilder;
		setLogger(new DefaultLogger(this));
	}
	
	public void setMoveComparatorSupplier(Function<ChessLibMoveGenerator, Comparator<Move>> moveComparatorSupplier) {
		this.moveComparatorSupplier = moveComparatorSupplier;
	}

	@Override
	protected ExecutionContext<SearchContext<Move, ChessLibMoveGenerator>> buildExecutionContext(ChessLibMoveGenerator board) {
		board.setMoveComparatorBuilder(moveComparatorSupplier);
		final ChessLibMoveGenerator b = (ChessLibMoveGenerator) board.fork();
		final Evaluator<Move, ChessLibMoveGenerator> evaluator = evaluatorBuilder.apply(b);
		evaluator.setViewPoint(b.getBoard().getSideToMove()==Side.WHITE ? Color.WHITE:Color.BLACK);
		final SearchContext<Move, ChessLibMoveGenerator> context = new SearchContext<>(b, evaluator);
		if (getParallelism()==1) {
			return new SingleThreadContext<>(context);
		} else {
			final ContextualizedExecutor<SearchContext<Move, ChessLibMoveGenerator>> contextualizedExecutor = new ContextualizedExecutor<>(getParallelism());
			return new MultiThreadsContext<>(context, contextualizedExecutor);
		}
	}

	@Override
	public Move apply(ChessLibMoveGenerator board) {
		final BasicMoveComparator c = new BasicMoveComparator(board); 
		super.setMoveSelector(new FirstBestMoveSelector<Move>().setNext(new StaticMoveSelector<Move,IterativeDeepeningSearch<Move>>(c::getValue).setNext(new RandomMoveSelector<>())));
		final IterativeDeepeningSearch<Move> search = search(board);
		final List<EvaluatedMove<Move>> bestMoves = this.getMoveSelector().select(search, search.getBestMoves());
		final EvaluatedMove<Move> evaluatedMove = bestMoves.get(0);
		Move move = evaluatedMove.getContent();
		log.info("Move chosen :{}", move);
		final List<Move> pv = evaluatedMove.getPrincipalVariation();
		log.info("pv: {}", pv);
		return move;
	}
	
	@Override
	protected IterativeDeepeningSearch<Move> search(ChessLibMoveGenerator board) {
		log.info("--- Start evaluation for {} with size={}, accuracy={}, maxDepth={}---", board.getBoard().getFen(), getDeepeningPolicy().getSize(), getDeepeningPolicy().getAccuracy(), getDeepeningPolicy().getDepth());
		IterativeDeepeningSearch<Move> search = super.search(board);
		log.info("--- End of iterative evaluation returns: {}", search.getBestMoves());
		return search;
	}
}

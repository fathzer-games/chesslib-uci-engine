package com.fathzer.jchess.chesslib.ai;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fathzer.games.Color;
import com.fathzer.games.ai.Negamax;
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
import com.fathzer.games.util.ContextualizedExecutor;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;

public class InternalEngine extends IterativeDeepeningEngine<Move, ChessLibMoveGenerator> {
	private static final Logger log = LoggerFactory.getLogger(InternalEngine.class); 
	
	public InternalEngine(Evaluator<ChessLibMoveGenerator> evaluator, int maxDepth) {
		super(evaluator, maxDepth, new TT(512));
		setDeepeningPolicyBuilder(() -> new ChessLibDeepeningPolicy(getMaxTime()));
	}
	
	@Override
	protected ExecutionContext<Move, ChessLibMoveGenerator> buildExecutionContext(ChessLibMoveGenerator board) {
		if (getParallelism()==1) {
			return new SingleThreadContext<>(board);
		} else {
			final Supplier<ChessLibMoveGenerator> supplier = () -> {
				return new ChessLibMoveGenerator(board.getBoard());
			};
			return new MultiThreadsContext<>(supplier, new ContextualizedExecutor<>(getParallelism()));
		}
	}

	@Override
	protected Negamax<Move, ChessLibMoveGenerator> buildNegaMax(ExecutionContext<Move, ChessLibMoveGenerator> context, Evaluator<ChessLibMoveGenerator> evaluator) {
		return new Negamax<>(context, evaluator) {
			@Override
			public List<Move> sort(List<Move> moves) {
				final ChessLibMoveGenerator b = getGamePosition();
				final Comparator<Move> cmp = new BasicMoveComparator(b);
				moves.sort(cmp);
				return moves;
			}
		};
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
		log.info("Move choosen :{}", move.toString());
		final List<Move> pv = evaluatedMove.getPrincipalVariation();
		log.info("pv: {}", pv);
		return move;
	}
	
	@Override
	protected IterativeDeepeningSearch<Move> search(ChessLibMoveGenerator board) {
		log.info("--- Start evaluation for {} with size={}, accuracy={}, maxDepth={}---", board.getBoard().getFen(), getSearchParams().getSize(), getSearchParams().getAccuracy(), getSearchParams().getDepth());
		IterativeDeepeningSearch<Move> search = super.search(board);
		log.info("--- End of iterative evaluation returns: {}", toString(search.getBestMoves()));
		return search;
	}

	public static String toString(Collection<EvaluatedMove<Move>> moves) {
		return moves.stream().map(em -> em.getContent().toString()).collect(Collectors.joining(", ", "[", "]"));
	}
/*
	public static String toString(EvaluatedMove<Move> ev, CoordinatesSystem cs) {
		final Type type = ev.getEvaluation().getType();
		String value;
		if (type==Type.EVAL) {
			value = Integer.toString(ev.getScore());
		} else {
			value="M"+(ev.getEvaluation().getType()==Type.LOOSE?"-":"+")+ev.getEvaluation().getCountToEnd();
		}
		return ev.getContent().toString(cs)+"("+value+")";
	}

	private class DefaultEventLogger implements EventLogger<Move> {
		private CoordinatesSystem cs;

		public DefaultEventLogger(CoordinatesSystem cs) {
			super();
			this.cs = cs;
		}
		@Override
		public void logSearch(int depth, SearchStatistics stat, SearchResult<Move> bestMoves) {
			final long duration = stat.getDurationMs();
			final List<EvaluatedMove<Move>> cut = bestMoves.getCut();
			log.info("{} move generations, {} moves generated, {} moves played, {} evaluations for {} moves at depth {} by {} threads in {}ms -> {}",
					stat.getMoveGenerationCount(), stat.getGeneratedMoveCount(), stat.getMovePlayedCount(), stat.getEvaluationCount(), bestMoves.getList().size(),
					depth, getParallelism(), duration, cut.isEmpty()?null:cut.get(0).getEvaluation());
			log.info("Search at depth {} returns: {}", depth, JChessEngine.toString(bestMoves.getCut(),cs));
		}

		@Override
		public void logTimeOut(int depth) {
			log.info("Search interrupted by timeout at depth {}",depth);
		}

		@Override
		public void logEndedByPolicy(int depth) {
			log.info("Search ended by deepening policy at depth {}", depth);
		}
	}

	protected EventLogger<Move> getLogger(Board<Move> board) {
		return new DefaultEventLogger(board.getCoordinatesSystem());
	}*/
}

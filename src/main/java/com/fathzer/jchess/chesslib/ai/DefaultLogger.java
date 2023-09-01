package com.fathzer.jchess.chesslib.ai;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fathzer.games.ai.SearchResult;
import com.fathzer.games.ai.SearchStatistics;
import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine.EventLogger;
import com.github.bhlangonijr.chesslib.move.Move;

class DefaultLogger implements EventLogger<Move> {
	private static final Logger log = LoggerFactory.getLogger(DefaultLogger.class);
	private final int parallelism;

	DefaultLogger(int parallelism) {
		super();
		this.parallelism = parallelism;
	}

	@Override
	public void logSearch(int depth, SearchStatistics stat, SearchResult<Move> bestMoves) {
		final long duration = stat.getDurationMs();
		final List<EvaluatedMove<Move>> cut = bestMoves.getCut();
		log.info("{} move generations, {} moves generated, {} moves played, {} evaluations for {} moves at depth {} by {} threads in {}ms -> {}",
				stat.getMoveGenerationCount(), stat.getGeneratedMoveCount(), stat.getMovePlayedCount(), stat.getEvaluationCount(), bestMoves.getList().size(),
				depth, parallelism, duration, cut.isEmpty()?null:cut.get(0).getEvaluation());
		log.info("Search at depth {} returns: {}", depth, bestMoves.getCut());
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


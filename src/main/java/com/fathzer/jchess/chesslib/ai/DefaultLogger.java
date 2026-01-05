package com.fathzer.jchess.chesslib.ai;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fathzer.games.ai.SearchResult;
import com.fathzer.games.ai.SearchStatistics;
import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine.EngineEventLogger;
import com.fathzer.games.ai.iterativedeepening.SearchHistory;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.move.Move;

public class DefaultLogger implements EngineEventLogger<Move, ChessLibMoveGenerator> {
	private static final Logger log = LoggerFactory.getLogger(DefaultLogger.class);
	private final IterativeDeepeningEngine<Move, ChessLibMoveGenerator> engine;

	public DefaultLogger(IterativeDeepeningEngine<Move, ChessLibMoveGenerator> engine) {
		super();
		this.engine = engine;
	}

	@Override
	public void logSearchAtDepth(int depth, SearchStatistics stat, SearchResult<Move> bestMoves) {
		final long duration = stat.getDurationMs();
		final List<EvaluatedMove<Move>> cut = bestMoves.getCut();
		log.info("{} move generations, {} moves generated, {} moves played, {} evaluations for {} moves at depth {} by {} threads in {}ms -> {}",
				stat.getMoveGenerationCount(), stat.getGeneratedMoveCount(), stat.getMovePlayedCount(), stat.getEvaluationCount(), bestMoves.getList().size(),
				depth, engine.getParallelism(), duration, cut.isEmpty()?null:cut.get(0).getEvaluation());
		log.info("Search at depth {} returns: {}", depth, bestMoves.getCut());
	}

	
	@Override
	public void logSearchStart(ChessLibMoveGenerator board, IterativeDeepeningEngine<Move, ChessLibMoveGenerator> engine) {
		log.info("--- Start evaluation for {} with size={}, accuracy={}, maxDepth={}---", board.getBoard().getFen(), engine.getDeepeningPolicy().getSize(), engine.getDeepeningPolicy().getAccuracy(), engine.getDeepeningPolicy().getDepth());
	}

	@Override
	public void logTimeOut(int depth) {
		log.info("Search interrupted by timeout at depth {}",depth);
	}

	@Override
	public void logEndedByPolicy(int depth) {
		log.info("Search ended by deepening policy at depth {}", depth);
	}
	
	@Override
	public void logSearchEnd(ChessLibMoveGenerator board, SearchHistory<Move> result) {
		log.info("--- End of iterative evaluation returns: {}", result.getAccurateMoves());
		if (result.isEmpty()) {
			log.info("No valid move found");
		} else {
			//TODO Not the right place, the engine could return another move, especially when there is tie moves
			EvaluatedMove<Move> evaluatedMove = result.getAccurateMoves().get(0);
			Move move = evaluatedMove.getMove();
			log.info("Move chosen :{}", move);
			log.info("pv: {}", engine.getTranspositionTable().collectPV(board, move, result.getLastDepth()));
		}
	}
}


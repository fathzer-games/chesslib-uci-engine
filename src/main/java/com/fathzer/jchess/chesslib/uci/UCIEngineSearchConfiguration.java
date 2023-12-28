package com.fathzer.jchess.chesslib.uci;

import com.fathzer.games.Color;
import com.fathzer.games.ai.time.BasicTimeManager;
import com.fathzer.games.clock.CountDownState;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.InternalEngine;
import com.fathzer.jchess.chesslib.time.RemainingMoveOracle;
import com.fathzer.jchess.uci.parameters.GoParameters;
import com.fathzer.jchess.uci.parameters.GoParameters.PlayerClockData;
import com.fathzer.jchess.uci.parameters.GoParameters.TimeOptions;
import com.github.bhlangonijr.chesslib.Side;

/** A class that configures the engine before executing the go command
 */
public class UCIEngineSearchConfiguration {
	private static final BasicTimeManager<ChessLibMoveGenerator> TIME_MANAGER = new BasicTimeManager<>(RemainingMoveOracle.INSTANCE);

	public static class EngineConfiguration {
		private long maxTime;
		private int depth;
		
		private EngineConfiguration(InternalEngine engine) {
			maxTime = engine.getDeepeningPolicy().getMaxTime();
			depth = engine.getDeepeningPolicy().getDepth();
		}
	}
	
	public EngineConfiguration configure(InternalEngine engine, GoParameters options, ChessLibMoveGenerator board) {
		final EngineConfiguration result = new EngineConfiguration(engine);
		final TimeOptions timeOptions = options.getTimeOptions();
		if (options.isPonder() || !options.getMoveToSearch().isEmpty() || options.getMate()>0 || options.getNodes()>0 || timeOptions.isInfinite()) {
			//TODO some options are not supported
		}
		if (options.getDepth()>0) {
			engine.getDeepeningPolicy().setDepth(options.getDepth());
		}
		if (timeOptions.getMoveTimeMs()>0) {
			engine.getDeepeningPolicy().setMaxTime(timeOptions.getMoveTimeMs());
		} else {
			final Color c = board.getBoard().getSideToMove()==Side.WHITE ? Color.WHITE : Color.BLACK;
			final PlayerClockData engineClock = c==Color.WHITE ? timeOptions.getWhiteClock() : timeOptions.getBlackClock();
			if (engineClock.getRemainingMs()>0) {
				engine.getDeepeningPolicy().setMaxTime(getMaxTime(board, engineClock.getRemainingMs(), engineClock.getIncrementMs(), timeOptions.getMovesToGo()));
			}
		}
		return result;
	}

	public void set(InternalEngine engine, EngineConfiguration c) {
		engine.getDeepeningPolicy().setMaxTime(c.maxTime);
		engine.getDeepeningPolicy().setDepth(c.depth);
	}
	
	public long getMaxTime(ChessLibMoveGenerator board, long remainingMs, long incrementMs, int movesToGo) {
		return TIME_MANAGER.getMaxTime(board, new CountDownState(remainingMs, incrementMs, movesToGo));
	}
}

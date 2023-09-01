package com.fathzer.jchess.chesslib.ai;

import static com.fathzer.jchess.chesslib.ai.MinimaxEngineTest.fromFEN;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.function.IntUnaryOperator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fathzer.games.ai.AlphaBetaState;
import com.fathzer.games.ai.Negamax;
import com.fathzer.games.ai.SearchParameters;
import com.fathzer.games.ai.SearchResult;
import com.fathzer.games.ai.SearchStatistics;
import com.fathzer.games.ai.evaluation.Evaluation;
import com.fathzer.games.ai.exec.ExecutionContext;
import com.fathzer.games.ai.exec.SingleThreadContext;
import com.fathzer.games.ai.experimental.KeyBasedNegaMaxSpyFilter;
import com.fathzer.games.ai.experimental.Negamax3;
import com.fathzer.games.ai.experimental.Spy;
import com.fathzer.games.ai.experimental.TreeSearchStateStack;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine.EventLogger;
import com.fathzer.games.ai.transposition.BasicPolicy;
import com.fathzer.games.ai.transposition.EntryType;
import com.fathzer.games.ai.transposition.OneLongEntryTranspositionTable;
import com.fathzer.games.ai.transposition.SizeUnit;
import com.fathzer.games.ai.transposition.TranspositionTable;
import com.fathzer.games.ai.transposition.TranspositionTableEntry;
import com.fathzer.games.ai.transposition.TranspositionTablePolicy;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

class DifTest {
//	@Test
	void dif() {
		ExecutionContext<Move, ChessLibMoveGenerator> context = new SingleThreadContext<Move, ChessLibMoveGenerator>(fromFEN("r2k1r2/pp1b2pp/1b2Pn2/2p5/Q1B2Bq1/2P5/P5PP/3R1RK1 w - - 0 1"));
		Negamax<Move, ChessLibMoveGenerator> ngmx = new Negamax<>(context, new BasicEvaluator()) {
			@Override
			public List<Move> sort(List<Move> moves) {
				final Comparator<Move> cmp = new StrictMoveComparator(getGamePosition());
				moves.sort(cmp);
				return moves;
			}
			@Override
			protected SearchResult<Move> getBestMoves(List<Move> moves, SearchParameters params, BiFunction<Move,Integer, Integer> rootEvaluator) {
				System.out.println(moves);
				return super.getBestMoves(moves, params, rootEvaluator);
			}
		};
		SearchParameters params = new SearchParameters(2, 3, 100);
		SearchResult<Move> bestMoves = ngmx.getBestMoves(params);
		System.out.println(ngmx.getStatistics());
	}

//	@Test
	void dif2() {
		String fen = "8/8/8/8/1B6/NN6/pk1K4/8 w - - 0 1";
		ExecutionContext<Move, ChessLibMoveGenerator> context = new SingleThreadContext<Move, ChessLibMoveGenerator>(fromFEN(fen));
		Negamax<Move, ChessLibMoveGenerator> ngmx = new Negamax<>(context, new BasicEvaluator()) {
			@Override
			public List<Move> sort(List<Move> moves) {
				final Comparator<Move> cmp = new StrictMoveComparator(getGamePosition());
				moves.sort(cmp);
				return moves;
			}
			@Override
			protected SearchResult<Move> getBestMoves(List<Move> moves, SearchParameters params, BiFunction<Move,Integer, Integer> rootEvaluator) {
				System.out.println(moves);
				return super.getBestMoves(moves, params, rootEvaluator);
			}
		};
		SearchParameters params = new SearchParameters(2, Integer.MAX_VALUE, 0);
		SearchResult<Move> bestMoves = ngmx.getBestMoves(params);
		System.out.println(bestMoves.getList());
	}
	
//	@Test
	void dif3() {
		final InternalEngine engine = new InternalEngine(new BasicEvaluator(), 4);
		engine.setMoveComparatorSupplier(StrictMoveComparator::new);
		engine.getSearchParams().setSize(Integer.MAX_VALUE);
		engine.setTranspositionTable(null);
		String fen = "8/8/8/8/1B6/NN6/pk1K4/8 w - - 0 1";
		engine.getBestMoves(fromFEN(fen));
	}

	@BeforeAll
	static void whoIAm() {
		System.out.println("ChessLib");
	}

	@Test
	void dif4() {
//		String fen = "r2k1r2/pp1b2pp/1b2Pn2/2p5/Q1B2Bq1/2P5/P5PP/3R1RK1 w - - 0 1";
//		int depth = 6;
		String fen = "r2k1r2/pp1R2pp/1b2Pn2/2p5/Q1B2Bq1/2P5/P5PP/5RK1 b - - 0 1";
		int depth = 5;
		final TT tt = new TT(1, SizeUnit.MB);
		tt.setPolicy(new MyPolicy<>());
		ExecutionContext<Move, ChessLibMoveGenerator> context = new SingleThreadContext<Move, ChessLibMoveGenerator>(fromFEN(fen));
		Negamax<Move, ChessLibMoveGenerator> ngmx = negamax(context, tt);
		SearchParameters params = new SearchParameters(depth, 3, 100);
		SearchResult<Move> bestMoves = ngmx.getBestMoves(Collections.singletonList(new Move(Square.F6,Square.D7)), params);
//		SearchResult<Move> bestMoves = ngmx.getBestMoves(params);
//		System.out.println(bestMoves.getList());
//		System.out.println(ngmx.getStatistics());
	}

	private Negamax<Move, ChessLibMoveGenerator> negamax(ExecutionContext<Move, ChessLibMoveGenerator> context, final TT tt) {
		Negamax<Move, ChessLibMoveGenerator> ngmx = new Negamax<>(context, new BasicEvaluator()) {
			@Override
			public List<Move> sort(List<Move> moves) {
				final Comparator<Move> cmp = new StrictMoveComparator(getGamePosition());
				moves.sort(cmp);
				return moves;
			}
			@Override
			protected SearchResult<Move> getBestMoves(List<Move> moves, SearchParameters params, BiFunction<Move,Integer, Integer> rootEvaluator) {
				System.out.println(moves);
				return super.getBestMoves(moves, params, rootEvaluator);
			}
			@Override
			protected synchronized void logAddToResult(Move move, Evaluation evaluation) {
				// TODO Auto-generated method stub
				System.out.println(move.toString()+":"+evaluation+" -> "+getStatistics());
				show(tt);
			}
		};
		ngmx.setTranspositonTable(tt);
//		final Spy<Move, ChessLibMoveGenerator> spy = new NegaMaxSpy(-230155418954045773L);
//		ngmx.setSpy(spy);
		return ngmx;
	}

//	@Test
	void dif5() {
		InternalEngine engine = new InternalEngine(new BasicEvaluator(), 6);
		engine.setMoveComparatorSupplier(StrictMoveComparator::new);
		engine.getSearchParams().setSize(3);
		engine.getSearchParams().setAccuracy(100);
		engine.setMoveComparatorSupplier(StrictMoveComparator::new);
		final TranspositionTable<Move> table = engine.getTranspositionTable();
		engine.setLogger(new MyLogger<>(table));
		final ChessLibMoveGenerator board = fromFEN("r2k1r2/pp1b2pp/1b2Pn2/2p5/Q1B2Bq1/2P5/P5PP/3R1RK1 w - - 0 1");
		engine.getBestMoves(board);
		show(table);
	}

	protected <M> void show(final TranspositionTable<M> table) {
		final Iterator<TranspositionTableEntry<M>> entries = table.getEntries();
		int count = 0;
		while (entries.hasNext()) {
			count++;
			entries.next();
		}
		final int size = table.getSize();
		System.out.println ("  Number of entries: "+count+"/"+size+" ("+(size/count)+") "+table.getPolicy());
	}
	
	private static class MyPolicy<M> extends BasicPolicy<M> {
		private AtomicLong callCount = new AtomicLong();
		private AtomicLong rejected = new AtomicLong();
		private AtomicLong toFree = new AtomicLong();
		private AtomicLong eraseCount = new AtomicLong();
		private AtomicLong updateCount = new AtomicLong();

		@Override
		protected boolean shouldReplace(TranspositionTableEntry<M> entry, long newKey, int newDepth, EntryType newType) {
			callCount.incrementAndGet();
			final boolean wasValid = entry.isValid();
			final long oldKey = entry.getKey();
			final boolean ok = super.shouldReplace(entry, newKey, newDepth, newType);
			if (ok) {
				if (wasValid) {
					if (oldKey==newKey) {
						updateCount.incrementAndGet();
					} else {
						eraseCount.incrementAndGet();
					}
				} else {
					toFree.incrementAndGet();
				}
			} else {
				rejected.incrementAndGet();
			}
			return ok;
		}

		@Override
		public String toString() {
			return "Table updates [callCount=" + callCount + ", rejected=" + rejected + ", toFree=" + toFree
					+ ", eraseCount=" + eraseCount + ", updateCount=" + updateCount + "]";
		}
	}
	
	private class MyLogger<M> implements IterativeDeepeningEngine.EventLogger<M> {
		private final TranspositionTable<M> table;
		
		public MyLogger(TranspositionTable<M> table) {
			super();
			this.table = table;
		}
		
		@Override
		public void logSearch(int depth, SearchStatistics stats, SearchResult<M> results) {
			show(table);
		}
	}
}

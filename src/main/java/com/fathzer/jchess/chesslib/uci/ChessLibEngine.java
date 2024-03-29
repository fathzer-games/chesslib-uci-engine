package com.fathzer.jchess.chesslib.uci;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.fathzer.games.ai.Negamax;
import com.fathzer.games.ai.SearchContext;
import com.fathzer.games.ai.evaluation.Evaluator;
import com.fathzer.games.ai.iterativedeepening.FirstBestMoveSelector;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningSearch;
import com.fathzer.games.ai.moveselector.RandomMoveSelector;
import com.fathzer.games.ai.moveselector.StaticMoveSelector;
import com.fathzer.games.ai.time.BasicTimeManager;
import com.fathzer.games.ai.transposition.SizeUnit;
import com.fathzer.games.ai.transposition.TranspositionTable;
import com.fathzer.games.movelibrary.MoveLibrary;
import com.fathzer.games.perft.TestableMoveGeneratorBuilder;
import com.fathzer.games.util.PhysicalCores;
import com.fathzer.games.util.exec.ExecutionContext;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.BasicMoveComparator;
import com.fathzer.jchess.chesslib.ai.BasicQuiesceSearch;
import com.fathzer.jchess.chesslib.ai.ChessLibDeepeningPolicy;
import com.fathzer.jchess.chesslib.ai.DefaultLogger;
import com.fathzer.jchess.chesslib.ai.TT;
import com.fathzer.jchess.chesslib.ai.eval.NaiveEvaluator;
import com.fathzer.jchess.chesslib.ai.eval.SimplifiedEvaluator;
import com.fathzer.jchess.chesslib.time.RemainingMoveOracle;
import com.fathzer.jchess.uci.UCIMove;
import com.fathzer.jchess.uci.extended.Displayable;
import com.fathzer.jchess.uci.helper.AbstractEngine;
import com.fathzer.jchess.uci.helper.EvaluatorConfiguration;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

public class ChessLibEngine extends AbstractEngine<Move, ChessLibMoveGenerator> implements TestableMoveGeneratorBuilder<Move, ChessLibMoveGenerator>, Displayable {
	private static final List<EvaluatorConfiguration<Move, ChessLibMoveGenerator>> EVALUATORS = Arrays.asList(new EvaluatorConfiguration<>("simplified",SimplifiedEvaluator::new),new EvaluatorConfiguration<>("naive",NaiveEvaluator::new));
	
	private final MoveLibrary<Move, ChessLibMoveGenerator> ownBook;

	public ChessLibEngine() {
		this (null);
	}

	public ChessLibEngine(MoveLibrary<Move, ChessLibMoveGenerator> ownBook) {
		super (buildEngine(EVALUATORS.get(0).getBuilder(), 20), new BasicTimeManager<>(RemainingMoveOracle.INSTANCE));
		setEvaluators(EVALUATORS);
		this.ownBook = ownBook;
	}
	
	@Override
	public String getId() {
		return "ChessLib";
	}
	
	@Override
	public String getAuthor() {
		return "Jean-Marc Astesana (Fathzer), Move generator is from Ben-Hur Carlos Vieira Langoni Junior";
	}

	@Override
	public boolean hasOwnBook() {
		return ownBook!=null;
	}

	@Override
	public void setOwnBook(boolean activate) {
		engine.setOpenings(activate?ownBook:null);
	}

	@Override
	public void setStartPosition(String fen) {
		board = fromFEN(fen);
		board.setMoveComparatorBuilder(BasicMoveComparator::new);
	}
	
	@Override
	public UCIMove toUCI(Move move) {
		final String fenSymbol = Piece.NONE.equals(move.getPromotion()) ? null : move.getPromotion().getFenSymbol().toLowerCase();
		return new UCIMove(move.getFrom().name().toLowerCase(), move.getTo().name().toLowerCase(), fenSymbol);
	}
	
	@Override
	protected Move toMove(UCIMove move) {
		return fromUCI(move, board);
	}
	
	public static Move fromUCI(UCIMove move, ChessLibMoveGenerator board) {
		Piece p = null;
		final String promotion = move.getPromotion();
		if (promotion!=null) {
			// Warning the promotion code is always in lowercase in UCI
			final String notation = board.isWhiteToMove() ? promotion.toUpperCase() : promotion;
			p = Piece.fromFenSymbol(notation);
		} else {
			p = Piece.NONE;
		}
		return new Move(Square.fromValue(move.getFrom().toUpperCase()), Square.fromValue(move.getTo().toUpperCase()), p);
	}

	@Override
	public String getBoardAsString() {
		return board.getBoard().toString();
	}

	@Override
	public String getFEN() {
		return board.getBoard().getFen();
	}

	@Override
	public ChessLibMoveGenerator fromFEN(String fen) {
		final Board internalBoard = new Board();
		internalBoard.loadFromFen(fen);
		return new ChessLibMoveGenerator(internalBoard);
	}
	
	public static IterativeDeepeningEngine<Move, ChessLibMoveGenerator> buildEngine(Supplier<Evaluator<Move, ChessLibMoveGenerator>> evaluatorBuilder, int maxDepth) {
		final IterativeDeepeningEngine<Move, ChessLibMoveGenerator> engine = new IterativeDeepeningEngine<>(new ChessLibDeepeningPolicy(maxDepth), new TT(16, SizeUnit.MB), evaluatorBuilder) {
			@Override
			protected Negamax<Move, ChessLibMoveGenerator> buildNegaMax(ExecutionContext<SearchContext<Move, ChessLibMoveGenerator>> context) {
				final Negamax<Move, ChessLibMoveGenerator> negaMax = super.buildNegaMax(context);
				negaMax.setQuiesceEvaluator(new BasicQuiesceSearch());
				return negaMax;
			}
			
		};
		engine.setMoveSelectorBuilder(b -> {
			final BasicMoveComparator c = new BasicMoveComparator(b);
			final RandomMoveSelector<Move, IterativeDeepeningSearch<Move>> rnd = new RandomMoveSelector<>();
			final StaticMoveSelector<Move, IterativeDeepeningSearch<Move>> stmv = new StaticMoveSelector<>(c::evaluate);
			return new FirstBestMoveSelector<Move>().setNext(stmv.setNext(rnd));
		});
		engine.setLogger(new DefaultLogger(engine));
		engine.setParallelism(PhysicalCores.count()>1 ? 2 : 1);
		engine.getDeepeningPolicy().setMaxTime(60000);
		return engine;
	}

	@Override
	protected TranspositionTable<Move> buildTranspositionTable(int sizeInMB) {
		return new TT(sizeInMB, SizeUnit.MB);
	}
}

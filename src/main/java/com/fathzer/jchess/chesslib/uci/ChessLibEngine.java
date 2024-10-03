package com.fathzer.jchess.chesslib.uci;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.fathzer.games.ai.Negamax;
import com.fathzer.games.ai.SearchContext;
import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.evaluation.Evaluator;
import com.fathzer.games.ai.iterativedeepening.FirstBestMoveSelector;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine;
import com.fathzer.games.ai.iterativedeepening.SearchHistory;
import com.fathzer.games.ai.moveselector.MoveSelector;
import com.fathzer.games.ai.moveselector.RandomMoveSelector;
import com.fathzer.games.ai.moveselector.StaticMoveSelector;
import com.fathzer.games.ai.time.BasicTimeManager;
import com.fathzer.games.ai.transposition.SizeUnit;
import com.fathzer.games.ai.transposition.TranspositionTable;
import com.fathzer.games.perft.FromPositionMoveGeneratorBuilder;
import com.fathzer.games.util.PhysicalCores;
import com.fathzer.games.util.exec.ExecutionContext;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.BasicMoveComparator;
import com.fathzer.jchess.chesslib.ai.BasicQuiesceSearch;
import com.fathzer.jchess.chesslib.ai.ChessLibDeepeningPolicy;
import com.fathzer.jchess.chesslib.ai.DefaultLogger;
import com.fathzer.jchess.chesslib.ai.TT;
import com.fathzer.jchess.chesslib.ai.eval.MyTinyEvaluator;
import com.fathzer.jchess.chesslib.ai.eval.NaiveEvaluator;
import com.fathzer.jchess.chesslib.ai.eval.PestoEvaluator;
import com.fathzer.jchess.chesslib.ai.eval.SimplifiedEvaluator;
import com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2MyFirstEvaluator;
import com.fathzer.jchess.chesslib.ai.eval.hbpg2.Hb2SimplifiedEvaluator;
import com.fathzer.jchess.chesslib.time.RemainingMoveOracle;
import com.fathzer.jchess.uci.UCIMove;
import com.fathzer.jchess.uci.extended.Displayable;
import com.fathzer.jchess.uci.helper.AbstractEngine;
import com.fathzer.jchess.uci.helper.DeferredReadMoveLibrary;
import com.fathzer.jchess.uci.helper.EvaluatorConfiguration;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

<<<<<<< Upstream, based on origin/main
public class ChessLibEngine extends AbstractEngine<Move, ChessLibMoveGenerator> implements FromPositionMoveGeneratorBuilder<Move, ChessLibMoveGenerator>, Displayable {
=======
public class ChessLibEngine extends AbstractEngine<Move, ChessLibMoveGenerator> implements TestableMoveGeneratorBuilder<Move, ChessLibMoveGenerator>, Displayable {
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
	private static final List<EvaluatorConfiguration<Move, ChessLibMoveGenerator>> EVALUATORS = Arrays.asList(
			new EvaluatorConfiguration<>("hb",MyTinyEvaluator::new),
			new EvaluatorConfiguration<>("pesto",PestoEvaluator::new),
			new EvaluatorConfiguration<>("simplified",SimplifiedEvaluator::new),
			new EvaluatorConfiguration<>("naive",NaiveEvaluator::new)
		);
=======
>>>>>>> 8600cfb squelette d'évaluateur
	private static final List<EvaluatorConfiguration<Move, ChessLibMoveGenerator>> EVALUATORS = 
			Arrays.asList(
					new EvaluatorConfiguration<>("hbfirst2",Hb2MyFirstEvaluator::new),
					new EvaluatorConfiguration<>("hbtiny",MyTinyEvaluator::new),
<<<<<<< Upstream, based on origin/main
					new EvaluatorConfiguration<>("pesto",PestoEvaluator::new),
=======
					new EvaluatorConfiguration<>("hbSimplified",Hb2SimplifiedEvaluator::new),				
>>>>>>> bda7176 Hb2SimplifiedEvaluator: incrémental achevé et propre pour position des pièces et matériel. Avec tapered eval
					new EvaluatorConfiguration<>("simplified",SimplifiedEvaluator::new),
					new EvaluatorConfiguration<>("naive",NaiveEvaluator::new)
					);
<<<<<<< Upstream, based on origin/main
=======
>>>>>>> d32a00b Hb2MyFirstEvaluator: the beginning. Compared to the SimplifiedEvaluator the evaluation function is tapered (scale = 64)
=======
	private static final List<EvaluatorConfiguration<Move, ChessLibMoveGenerator>> EVALUATORS = 
			Arrays.asList(new EvaluatorConfiguration<>("hb",MyTinyEvaluator::new),new EvaluatorConfiguration<>("simplified",SimplifiedEvaluator::new),new EvaluatorConfiguration<>("naive",NaiveEvaluator::new));
>>>>>>> f86bfc5 squelette d'évaluateur
<<<<<<< Upstream, based on origin/main
>>>>>>> 8600cfb squelette d'évaluateur
=======
=======
	private static final List<EvaluatorConfiguration<Move, ChessLibMoveGenerator>> EVALUATORS = 
			Arrays.asList(new EvaluatorConfiguration<>("hb",MyTinyEvaluator::new),new EvaluatorConfiguration<>("simplified",SimplifiedEvaluator::new),new EvaluatorConfiguration<>("naive",NaiveEvaluator::new));
>>>>>>> e44b9b1 squelette d'évaluateur
<<<<<<< Upstream, based on origin/main
>>>>>>> 270a4e0 squelette d'évaluateur
=======
=======
	private static final List<EvaluatorConfiguration<Move, ChessLibMoveGenerator>> EVALUATORS = 
			Arrays.asList(new EvaluatorConfiguration<>("hb",MyTinyEvaluator::new),new EvaluatorConfiguration<>("simplified",SimplifiedEvaluator::new),new EvaluatorConfiguration<>("naive",NaiveEvaluator::new));
>>>>>>> bf384dd squelette d'évaluateur
>>>>>>> 9c3d5f6 squelette d'évaluateur
	
	private final DeferredReadMoveLibrary<Move, ChessLibMoveGenerator> ownBook;

	public ChessLibEngine() {
		this (null);
	}

	public ChessLibEngine(DeferredReadMoveLibrary<Move, ChessLibMoveGenerator> ownBook) {
		super (buildEngine(EVALUATORS.get(0).evaluatorBuilder(), 20), new BasicTimeManager<>(RemainingMoveOracle.INSTANCE));
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
	
	DeferredReadMoveLibrary<Move, ChessLibMoveGenerator> getOwnBook() {
		return ownBook;
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
		board = fromPosition(fen);
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
		return board==null ? null : board.getBoard().getFen();
	}

	@Override
	public ChessLibMoveGenerator fromPosition(String fen) {
		final Board internalBoard = new Board();
		internalBoard.loadFromFen(fen);
		return new ChessLibMoveGenerator(internalBoard);
	}
	
	public static IterativeDeepeningEngine<Move, ChessLibMoveGenerator> buildEngine(Supplier<Evaluator<Move, ChessLibMoveGenerator>> evaluatorBuilder, int maxDepth) {
		final IterativeDeepeningEngine<Move, ChessLibMoveGenerator> engine = new IterativeDeepeningEngine<>(new ChessLibDeepeningPolicy(maxDepth), new TT(16, SizeUnit.MB), evaluatorBuilder) {
			@Override
			protected Negamax<Move, ChessLibMoveGenerator> buildAI(ExecutionContext<SearchContext<Move, ChessLibMoveGenerator>> context) {
				final Negamax<Move, ChessLibMoveGenerator> negaMax = (Negamax<Move, ChessLibMoveGenerator>) super.buildAI(context);
				negaMax.setQuiesceEvaluator(new BasicQuiesceSearch());
				return negaMax;
			}
			
		};
		engine.setLogger(new DefaultLogger(engine));
		engine.setParallelism(PhysicalCores.count()>1 ? 2 : 1);
		engine.getDeepeningPolicy().setMaxTime(60000);
		engine.getDeepeningPolicy().setDeepenOnForced(false);
		return engine;
	}

	@Override
	protected EvaluatedMove<Move> getSelected(ChessLibMoveGenerator b, SearchHistory<Move> history) {
		final BasicMoveComparator c = new BasicMoveComparator(b);
		final MoveSelector<Move, SearchHistory<Move>> stmv = new StaticMoveSelector<>(c::evaluate);
		final MoveSelector<Move, SearchHistory<Move>> selector = new FirstBestMoveSelector<>();
		selector.setNext(stmv.setNext(new RandomMoveSelector<>()));
		return history.getBestMove(selector);
	}

	@Override
	protected TranspositionTable<Move, ChessLibMoveGenerator> buildTranspositionTable(int sizeInMB) {
		return new TT(sizeInMB, SizeUnit.MB);
	}
}

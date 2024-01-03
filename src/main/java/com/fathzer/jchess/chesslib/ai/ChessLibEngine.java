package com.fathzer.jchess.chesslib.ai;

import java.util.function.Supplier;

import com.fathzer.games.ai.evaluation.Evaluator;
import com.fathzer.games.ai.iterativedeepening.FirstBestMoveSelector;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningSearch;
import com.fathzer.games.ai.moveselector.RandomMoveSelector;
import com.fathzer.games.ai.moveselector.StaticMoveSelector;
import com.fathzer.games.ai.time.BasicTimeManager;
import com.fathzer.games.ai.time.TimeManager;
import com.fathzer.games.ai.transposition.SizeUnit;
import com.fathzer.games.perft.TestableMoveGeneratorBuilder;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.eval.BasicEvaluator;
import com.fathzer.jchess.chesslib.time.RemainingMoveOracle;
import com.fathzer.jchess.uci.UCIMove;
import com.fathzer.jchess.uci.helper.AbstractEngine;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

public class ChessLibEngine extends AbstractEngine<Move, ChessLibMoveGenerator> implements TestableMoveGeneratorBuilder<Move, ChessLibMoveGenerator> {
	public static final ChessLibEngine INSTANCE = new ChessLibEngine();
	
	@Override
	protected TimeManager<ChessLibMoveGenerator> buildTimeManager() {
		return new BasicTimeManager<>(RemainingMoveOracle.INSTANCE);
	}

	@Override
	protected IterativeDeepeningEngine<Move, ChessLibMoveGenerator> buildEngine() {
		return buildEngine(BasicEvaluator::new, 8);
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
		return board==null ? "no position defined" : board.toString();
	}

	@Override
	public String getFEN() {
		return board==null ? null : board.getBoard().getFen();
	}

	@Override
	public ChessLibMoveGenerator fromFEN(String fen) {
		final Board internalBoard = new Board();
		internalBoard.loadFromFen(fen);
		return new ChessLibMoveGenerator(internalBoard);
	}
	
	public static IterativeDeepeningEngine<Move, ChessLibMoveGenerator> buildEngine(Supplier<Evaluator<Move, ChessLibMoveGenerator>> evaluatorBuilder, int maxDepth) {
		final IterativeDeepeningEngine<Move, ChessLibMoveGenerator> engine = new IterativeDeepeningEngine<>(new ChessLibDeepeningPolicy(maxDepth), new TT(16, SizeUnit.MB), evaluatorBuilder);
		engine.setMoveSelectorBuilder(b -> {
			final BasicMoveComparator c = new BasicMoveComparator(b);
			final RandomMoveSelector<Move, IterativeDeepeningSearch<Move>> rnd = new RandomMoveSelector<>();
			final StaticMoveSelector<Move, IterativeDeepeningSearch<Move>> stmv = new StaticMoveSelector<>(c::evaluate);
			return new FirstBestMoveSelector<Move>().setNext(stmv.setNext(rnd));
		});
		engine.setLogger(new DefaultLogger(engine));
		return engine;
	}
}

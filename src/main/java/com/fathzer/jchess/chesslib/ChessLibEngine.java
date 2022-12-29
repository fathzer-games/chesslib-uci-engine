package com.fathzer.jchess.chesslib;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.fathzer.games.ai.GameContext;
import com.fathzer.games.perft.PerfT;
import com.fathzer.games.perft.Divide;
import com.fathzer.jchess.uci.Engine;
import com.fathzer.jchess.uci.LongRunningTask;
import com.fathzer.jchess.uci.UCIMove;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

public class ChessLibEngine implements Engine {
	public static final Engine INSTANCE = new ChessLibEngine();
	private Board board;
	
	@Override
	public String getId() {
		return "ChessLib";
	}
	
	@Override
	public String getAuthor() {
		return "Jean-Marc Astesana (Fathzer)";
	}
	
	private Supplier<GameContext<Move>> getContextBuilder() {
		return ()-> new ChessLibGameContext(b->0, (com.github.bhlangonijr.chesslib.Board)board);
	}
	
	@Override
	public void move(UCIMove move) {
		Piece p = null;
		final String promotion = move.getPromotion();
		if (promotion!=null) {
			// Warning the promotion code is always in lowercase
			final String notation = Side.WHITE.equals(board.getSideToMove()) ? promotion.toUpperCase() : promotion;
			p = Piece.fromFenSymbol(notation);
		} else {
			p = Piece.NONE;
		}
		board.doMove(new Move(Square.fromValue(move.getFrom().toUpperCase()), Square.fromValue(move.getTo().toUpperCase()), p));
	}

	@Override
	public void setFEN(String fen) {
		board = new Board();
		board.loadFromFen(fen);
	}
	
	@Override
	public LongRunningTask<UCIMove> go() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public LongRunningTask<Collection<Divide<UCIMove>>> divide(int depth, int parallelism) {
		final PerfT<Move> perft = new PerfT<>(getContextBuilder());
		perft.setParallelism(parallelism);
		return new LongRunningTask<>() {
			@Override
			public Collection<Divide<UCIMove>> get() {
				final Function<Divide<Move>, Divide<UCIMove>> mapper = d -> new Divide<>(toMove(d.getMove()), d.getCount());
				return perft.divide(depth).stream().map(mapper).collect(Collectors.toList());
			}

			@Override
			public void stop() {
				super.stop();
				perft.interrupt();
			}
		};
	}
	
	private UCIMove toMove(Move move) {
		final String fenSymbol = Piece.NONE.equals(move.getPromotion()) ? null : move.getPromotion().getFenSymbol().toLowerCase();
		return new UCIMove(move.getFrom().name().toLowerCase(), move.getTo().name().toLowerCase(), fenSymbol);
	}

	@Override
	public String getBoardAsString() {
		return board==null ? "no position defined" : board.toString();
	}

	@Override
	public String getFEN() {
		return board==null ? null : board.getFen();
	}
}

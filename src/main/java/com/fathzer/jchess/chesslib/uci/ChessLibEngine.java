package com.fathzer.jchess.chesslib.uci;

import com.fathzer.games.MoveGenerator;
import com.fathzer.games.perft.TestableMoveGeneratorSupplier;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.InternalEngine;
import com.fathzer.jchess.chesslib.eval.BasicEvaluator;
import com.fathzer.jchess.chesslib.eval.IncrementalEvaluator;
import com.fathzer.jchess.uci.BestMoveReply;
import com.fathzer.jchess.uci.Engine;
import com.fathzer.jchess.uci.GoOptions;
import com.fathzer.jchess.uci.LongRunningTask;
import com.fathzer.jchess.uci.MoveGeneratorSupplier;
import com.fathzer.jchess.uci.MoveToUCIConverter;
import com.fathzer.jchess.uci.UCIMove;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

public class ChessLibEngine implements Engine, TestableMoveGeneratorSupplier<Move>, MoveGeneratorSupplier<Move>, MoveToUCIConverter<Move> {
	public static final Engine INSTANCE = new ChessLibEngine();
	private Board board;
	private InternalEngine engine = new InternalEngine(new BasicEvaluator(), 8);
	
	@Override
	public String getId() {
		return "ChessLib";
	}
	
	@Override
	public String getAuthor() {
		return "Jean-Marc Astesana (Fathzer), Move generator is from Ben-Hur Carlos Vieira Langoni Junior";
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
	public void setStartPosition(String fen) {
		board = new Board();
		board.loadFromFen(fen);
	}
	
	@Override
	public LongRunningTask<BestMoveReply> go(GoOptions options) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public MoveGenerator<Move> get() {
		final ChessLibMoveGenerator result = new ChessLibMoveGenerator(board);
		result.setIncrementalEvaluator((IncrementalEvaluator<Move, ChessLibMoveGenerator, ?>) engine.getEvaluator());
		return result;
	}
	
	@Override
	public String toUCI(Move move) {
		final String fenSymbol = Piece.NONE.equals(move.getPromotion()) ? null : move.getPromotion().getFenSymbol().toLowerCase();
		return new UCIMove(move.getFrom().name().toLowerCase(), move.getTo().name().toLowerCase(), fenSymbol).toString();
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

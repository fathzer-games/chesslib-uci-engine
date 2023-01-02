package com.fathzer.jchess.chesslib;

import com.fathzer.jchess.uci.Engine;
import com.fathzer.jchess.uci.LongRunningTask;
import com.fathzer.jchess.uci.UCIMoveGeneratorProvider;
import com.fathzer.jchess.uci.UCIMove;
import com.fathzer.jchess.uci.UCIMoveGenerator;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

public class ChessLibEngine implements Engine, UCIMoveGeneratorProvider<Move> {
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
	public UCIMoveGenerator<Move> getMoveGenerator() {
		return new ChessLibMoveGenerator((com.github.bhlangonijr.chesslib.Board)board);
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

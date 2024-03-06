package com.fathzer.jchess.lichess;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.uci.ChessLibEngine;
import com.fathzer.jchess.uci.UCIMove;
import com.github.bhlangonijr.chesslib.move.Move;

public class DefaultOpenings extends AbstractDefaultOpenings<Move, ChessLibMoveGenerator> {
	
	public DefaultOpenings(Supplier<InputStream> stream, boolean zipped) throws IOException {
		super(stream, zipped);
	}
	
	@Override
	protected Move fromUCI(ChessLibMoveGenerator board, String move) {
		return ChessLibEngine.fromUCI(UCIMove.from(move), board);
	}

	@Override
	protected String toXFEN(ChessLibMoveGenerator board) {
		return board.getBoard().getFen(true, true);
	}
}

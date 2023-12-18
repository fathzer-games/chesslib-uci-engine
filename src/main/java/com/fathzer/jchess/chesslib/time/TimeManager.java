package com.fathzer.jchess.chesslib.time;

import com.fathzer.chess.utils.VuckovicSolakOracle;
import com.fathzer.games.ai.time.RemainingMoveCountPredictor;
import com.fathzer.jchess.chesslib.ChessLibAdapter;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;

/** A {@link RemainingMoveCountPredictor} that uses the function described in chapter 4 of <a href="http://facta.junis.ni.ac.rs/acar/acar200901/acar2009-07.pdf">Vuckovic and Solak paper</a>.
 */
public class TimeManager extends VuckovicSolakOracle<ChessLibMoveGenerator, Piece> implements ChessLibAdapter {
	public static final TimeManager INSTANCE = new TimeManager();

	@Override
	protected boolean isNotKing(Piece piece) {
		return piece.getPieceType()!=PieceType.KING;
	}
}

package com.fathzer.jchess.chesslib.time;

import com.fathzer.chess.utils.AbstractVuckovicSolakOracle;
import com.fathzer.games.ai.time.RemainingMoveCountPredictor;
import com.fathzer.jchess.chesslib.ChessLibExplorerBuilder;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;

/** A {@link RemainingMoveCountPredictor} that uses the function described in chapter 4 of <a href="http://facta.junis.ni.ac.rs/acar/acar200901/acar2009-07.pdf">Vuckovic and Solak paper</a>.
 */
public class RemainingMoveOracle extends AbstractVuckovicSolakOracle<ChessLibMoveGenerator> implements ChessLibExplorerBuilder {
	public static final RemainingMoveOracle INSTANCE = new RemainingMoveOracle();

	private RemainingMoveOracle() {
		super();
	}
}

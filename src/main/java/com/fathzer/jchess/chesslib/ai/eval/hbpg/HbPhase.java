package com.fathzer.jchess.chesslib.ai.eval.hbpg;

/** The phases of a game.
 * <br>There's only two phases in the simplified evaluator, and not three as we could expect.
 * The idea is, in the opening phase, the evaluation function should be replaced by an opening library, the middle game starts when the position
 * is no more in the library.
 */
enum HbPhase {
	/** Middle of the game.*/
	MIDDLE_GAME,
	/** End of the game */
	END_GAME
}

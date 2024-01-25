<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
package com.fathzer.jchess.chesslib.ai;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.eval.SimplifiedEvaluator;
import com.fathzer.jchess.chesslib.uci.ChessLibEngine;
import com.github.bhlangonijr.chesslib.move.Move;

class HBThreeMovesTest {

	@Test
	void test() {
		final int depth = 6;
		final int bestMoveCount = 3;
		final String fen = "r2k1r2/pp1b2pp/1b2Pn2/2p5/Q1B2Bq1/2P5/P5PP/3R1RK1 w - - 0 1";
		final IterativeDeepeningEngine<Move, ChessLibMoveGenerator> engine = ChessLibEngine.buildEngine(SimplifiedEvaluator::new, depth);
		engine.getDeepeningPolicy().setSize(bestMoveCount);
		final List<EvaluatedMove<Move>> moves = engine.getBestMoves(MinimaxEngineTest.fromFEN(fen, BasicMoveComparator::new));
		System.out.println(moves);
		for (int i=0;i<3;i++) {
			EvaluatedMove<Move> move = moves.get(i);
			List<Move> principalVariation = move.getPrincipalVariation();
			System.out.println(move+" -> "+principalVariation);
		}
	}

}
=======
package com.fathzer.jchess.chesslib.ai;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.eval.SimplifiedEvaluator;
import com.fathzer.jchess.chesslib.uci.ChessLibEngine;
import com.github.bhlangonijr.chesslib.move.Move;

class HBThreeMovesTest {

	@Test
	void test() {
		final int depth = 6;
		final int bestMoveCount = 3;
		final String fen = "r2k1r2/pp1b2pp/1b2Pn2/2p5/Q1B2Bq1/2P5/P5PP/3R1RK1 w - - 0 1";
		final IterativeDeepeningEngine<Move, ChessLibMoveGenerator> engine = ChessLibEngine.buildEngine(SimplifiedEvaluator::new, depth);
		engine.getDeepeningPolicy().setSize(bestMoveCount);
		final List<EvaluatedMove<Move>> moves = engine.getBestMoves(MinimaxEngineTest.fromFEN(fen, BasicMoveComparator::new));
		System.out.println(moves);
		for (int i=0;i<3;i++) {
			EvaluatedMove<Move> move = moves.get(i);
			List<Move> principalVariation = move.getPrincipalVariation();
			System.out.println(move+" -> "+principalVariation);
		}
	}

}
>>>>>>> d32a00b Hb2MyFirstEvaluator: the beginning. Compared to the SimplifiedEvaluator the evaluation function is tapered (scale = 64)
=======
package com.fathzer.jchess.chesslib.ai;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.eval.SimplifiedEvaluator;
import com.fathzer.jchess.chesslib.uci.ChessLibEngine;
import com.github.bhlangonijr.chesslib.move.Move;

class HBThreeMovesTest {

	@Test
	void test() {
		final int depth = 6;
		final int bestMoveCount = 3;
		final String fen = "r2k1r2/pp1b2pp/1b2Pn2/2p5/Q1B2Bq1/2P5/P5PP/3R1RK1 w - - 0 1";
		final IterativeDeepeningEngine<Move, ChessLibMoveGenerator> engine = ChessLibEngine.buildEngine(SimplifiedEvaluator::new, depth);
		engine.getDeepeningPolicy().setSize(bestMoveCount);
		final List<EvaluatedMove<Move>> moves = engine.getBestMoves(MinimaxEngineTest.fromFEN(fen, BasicMoveComparator::new));
		System.out.println(moves);
		for (int i=0;i<3;i++) {
			EvaluatedMove<Move> move = moves.get(i);
			List<Move> principalVariation = move.getPrincipalVariation();
			System.out.println(move+" -> "+principalVariation);
		}
	}

}
>>>>>>> 2a0a7bc An example of calling engine to have three best moves with their pv
=======
package com.fathzer.jchess.chesslib.ai;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.eval.SimplifiedEvaluator;
import com.fathzer.jchess.chesslib.uci.ChessLibEngine;
import com.github.bhlangonijr.chesslib.move.Move;

class HBThreeMovesTest {

	@Test
	void test() {
		final int depth = 6;
		final int bestMoveCount = 3;
		final String fen = "r2k1r2/pp1b2pp/1b2Pn2/2p5/Q1B2Bq1/2P5/P5PP/3R1RK1 w - - 0 1";
		final IterativeDeepeningEngine<Move, ChessLibMoveGenerator> engine = ChessLibEngine.buildEngine(SimplifiedEvaluator::new, depth);
		engine.getDeepeningPolicy().setSize(bestMoveCount);
		final List<EvaluatedMove<Move>> moves = engine.getBestMoves(MinimaxEngineTest.fromFEN(fen, BasicMoveComparator::new));
		System.out.println(moves);
		for (int i=0;i<3;i++) {
			EvaluatedMove<Move> move = moves.get(i);
			List<Move> principalVariation = move.getPrincipalVariation();
			System.out.println(move+" -> "+principalVariation);
		}
	}

}
>>>>>>> 949a180 An example of calling engine to have three best moves with their pv
=======
package com.fathzer.jchess.chesslib.ai;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.eval.SimplifiedEvaluator;
import com.fathzer.jchess.chesslib.uci.ChessLibEngine;
import com.github.bhlangonijr.chesslib.move.Move;

class HBThreeMovesTest {

	@Test
	void test() {
		final int depth = 6;
		final int bestMoveCount = 3;
		final String fen = "r2k1r2/pp1b2pp/1b2Pn2/2p5/Q1B2Bq1/2P5/P5PP/3R1RK1 w - - 0 1";
		final IterativeDeepeningEngine<Move, ChessLibMoveGenerator> engine = ChessLibEngine.buildEngine(SimplifiedEvaluator::new, depth);
		engine.getDeepeningPolicy().setSize(bestMoveCount);
		final List<EvaluatedMove<Move>> moves = engine.getBestMoves(MinimaxEngineTest.fromFEN(fen, BasicMoveComparator::new));
		System.out.println(moves);
		for (int i=0;i<3;i++) {
			EvaluatedMove<Move> move = moves.get(i);
			List<Move> principalVariation = move.getPrincipalVariation();
			System.out.println(move+" -> "+principalVariation);
		}
	}

}
>>>>>>> 4c3d9b3 An example of calling engine to have three best moves with their pv
=======
package com.fathzer.jchess.chesslib.ai;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.eval.SimplifiedEvaluator;
import com.fathzer.jchess.chesslib.uci.ChessLibEngine;
import com.github.bhlangonijr.chesslib.move.Move;

class HBThreeMovesTest {

	@Test
	void test() {
		final int depth = 6;
		final int bestMoveCount = 3;
		final String fen = "r2k1r2/pp1b2pp/1b2Pn2/2p5/Q1B2Bq1/2P5/P5PP/3R1RK1 w - - 0 1";
		final IterativeDeepeningEngine<Move, ChessLibMoveGenerator> engine = ChessLibEngine.buildEngine(SimplifiedEvaluator::new, depth);
		engine.getDeepeningPolicy().setSize(bestMoveCount);
		final List<EvaluatedMove<Move>> moves = engine.getBestMoves(MinimaxEngineTest.fromFEN(fen, BasicMoveComparator::new));
		System.out.println(moves);
		for (int i=0;i<3;i++) {
			EvaluatedMove<Move> move = moves.get(i);
			List<Move> principalVariation = move.getPrincipalVariation();
			System.out.println(move+" -> "+principalVariation);
		}
	}

}
>>>>>>> 1f95cd2 An example of calling engine to have three best moves with their pv
=======
package com.fathzer.jchess.chesslib.ai;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.iterativedeepening.IterativeDeepeningEngine;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.eval.SimplifiedEvaluator;
import com.fathzer.jchess.chesslib.uci.ChessLibEngine;
import com.github.bhlangonijr.chesslib.move.Move;

class HBThreeMovesTest {

	@Test
	void test() {
		final int depth = 6;
		final int bestMoveCount = 3;
		final String fen = "r2k1r2/pp1b2pp/1b2Pn2/2p5/Q1B2Bq1/2P5/P5PP/3R1RK1 w - - 0 1";
		final IterativeDeepeningEngine<Move, ChessLibMoveGenerator> engine = ChessLibEngine.buildEngine(SimplifiedEvaluator::new, depth);
		engine.getDeepeningPolicy().setSize(bestMoveCount);
		final List<EvaluatedMove<Move>> moves = engine.getBestMoves(MinimaxEngineTest.fromFEN(fen, BasicMoveComparator::new));
		System.out.println(moves);
		for (int i=0;i<3;i++) {
			EvaluatedMove<Move> move = moves.get(i);
			List<Move> principalVariation = move.getPrincipalVariation();
			System.out.println(move+" -> "+principalVariation);
		}
	}

}
>>>>>>> cb51aff An example of calling engine to have three best moves with their pv

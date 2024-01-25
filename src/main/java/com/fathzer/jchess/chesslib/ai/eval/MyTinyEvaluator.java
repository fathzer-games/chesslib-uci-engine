<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
package com.fathzer.jchess.chesslib.ai.eval;

import com.fathzer.games.ai.evaluation.StaticEvaluator;
import com.fathzer.games.ai.evaluation.ZeroSumEvaluator;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
<<<<<<< Upstream, based on origin/main
<<<<<<< Upstream, based on origin/main
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

public class MyTinyEvaluator implements StaticEvaluator<Move, ChessLibMoveGenerator>, ZeroSumEvaluator<Move, ChessLibMoveGenerator> {
	private final SimplifiedEvaluator ev = new SimplifiedEvaluator();

	@Override
	public int evaluateAsWhite(ChessLibMoveGenerator board) {
		// Calculer l'évaluation de base
		int baseEvaluation;
//		synchronized (this) { // Alternative à la méthode fork
			ev.init(board);
			baseEvaluation = ev.evaluateAsWhite(board);
//		}
		// Tu peux ajouter ce qui concerne les chaines de pions, mauvais/bon fous, etc à l'évaluation de base ...
		return baseEvaluation;
	}
	
	@Override
	public StaticEvaluator<Move, ChessLibMoveGenerator> fork() {
		// Attention : SimplifiedEvaluator n'est pas thread safe, ce qui est généralement le cas des évaluateurs statiques (d'où l'implémentation par défaut de fork qui renvoie this).
		// Pour éviter les pb de threading, soit on crée un évaluateur par thread comme ici, soit l'usage du SimplifiedEvaluator doit être protégé par un synchronized (pas performant). 
		return new MyTinyEvaluator();
	}


	public static void main(String[] args) {
		// Exemple pour créer un ChessLibMoveGenerator à partir d'un FEN et l'évaluer
		ChessLibMoveGenerator mvg = new ChessLibMoveGenerator(new Board());
		mvg.getBoard().loadFromFen("8/3b3p/p3P1p1/3K4/5P1P/2k5/8/8 b - - 0 56");
		System.out.println(new MyTinyEvaluator().evaluate(mvg));
	}
=======
=======
import com.github.bhlangonijr.chesslib.Board;
>>>>>>> b2e1bf7 MyTinyEvaluator => example of static reuse of SimplifiedEvaluator
import com.github.bhlangonijr.chesslib.move.Move;

public class MyTinyEvaluator implements StaticEvaluator<Move, ChessLibMoveGenerator>, ZeroSumEvaluator<Move, ChessLibMoveGenerator> {
	private final SimplifiedEvaluator ev = new SimplifiedEvaluator();

	@Override
	public int evaluateAsWhite(ChessLibMoveGenerator board) {
		// Calculer l'évaluation de base
		ev.init(board);
		int baseEvaluation = ev.evaluate(board);
		// Tu peux ajouter ce qui concerne les chaines de pions, mauvais/bon fous, etc à l'évaluation de base ...
		return baseEvaluation;
	}

<<<<<<< Upstream, based on origin/main
>>>>>>> f86bfc5 squelette d'évaluateur
=======
	public static void main(String[] args) {
		// Exemple pour créer un ChessLibMoveGenerator à partir d'un FEN et l'évaluer
		ChessLibMoveGenerator mvg = new ChessLibMoveGenerator(new Board());
		mvg.getBoard().loadFromFen("8/3b3p/p3P1p1/3K4/5P1P/2k5/8/8 b - - 0 56");
		System.out.println(new MyTinyEvaluator().evaluate(mvg));
	}
>>>>>>> b2e1bf7 MyTinyEvaluator => example of static reuse of SimplifiedEvaluator
}
=======
package com.fathzer.jchess.chesslib.ai.eval;

import com.fathzer.games.ai.evaluation.StaticEvaluator;
import com.fathzer.games.ai.evaluation.ZeroSumEvaluator;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

public class MyTinyEvaluator implements StaticEvaluator<Move, ChessLibMoveGenerator>, ZeroSumEvaluator<Move, ChessLibMoveGenerator> {
	private final SimplifiedEvaluator ev = new SimplifiedEvaluator();

	@Override
	public int evaluateAsWhite(ChessLibMoveGenerator board) {
		// Calculer l'évaluation de base
		int baseEvaluation;
//		synchronized (this) { // Alternative à la méthode fork
			ev.init(board);
			baseEvaluation = ev.evaluateAsWhite(board);
//		}
		// Tu peux ajouter ce qui concerne les chaines de pions, mauvais/bon fous, etc à l'évaluation de base ...
		return baseEvaluation;
	}
	
	@Override
	public StaticEvaluator<Move, ChessLibMoveGenerator> fork() {
		// Attention : SimplifiedEvaluator n'est pas thread safe, ce qui est généralement le cas des évaluateurs statiques (d'où l'implémentation par défaut de fork qui renvoie this).
		// Pour éviter les pb de threading, soit on crée un évaluateur par thread comme ici, soit l'usage du SimplifiedEvaluator doit être protégé par un synchronized (pas performant). 
		return new MyTinyEvaluator();
	}


	public static void main(String[] args) {
		// Exemple pour créer un ChessLibMoveGenerator à partir d'un FEN et l'évaluer
		ChessLibMoveGenerator mvg = new ChessLibMoveGenerator(new Board());
		mvg.getBoard().loadFromFen("8/3b3p/p3P1p1/3K4/5P1P/2k5/8/8 b - - 0 56");
		System.out.println(new MyTinyEvaluator().evaluate(mvg));
	}
}
>>>>>>> 2320dd0 Fixes MyTinyEvaluator hang + search deepen on forced moves
=======
package com.fathzer.jchess.chesslib.ai.eval;

import com.fathzer.games.ai.evaluation.StaticEvaluator;
import com.fathzer.games.ai.evaluation.ZeroSumEvaluator;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.move.Move;

public class MyTinyEvaluator implements StaticEvaluator<Move, ChessLibMoveGenerator>, ZeroSumEvaluator<Move, ChessLibMoveGenerator> {

	@Override
	public int evaluateAsWhite(ChessLibMoveGenerator board) {
		// TODO Auto-generated method stub
		return 0;
	}

}
>>>>>>> e44b9b1 squelette d'évaluateur

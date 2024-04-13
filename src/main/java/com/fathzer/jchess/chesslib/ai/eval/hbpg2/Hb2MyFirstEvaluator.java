package com.fathzer.jchess.chesslib.ai.eval.hbpg2;

import com.fathzer.games.ai.evaluation.StaticEvaluator;
import com.fathzer.games.ai.evaluation.ZeroSumEvaluator;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.chesslib.ai.eval.NaiveEvaluator;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;


public class Hb2MyFirstEvaluator implements StaticEvaluator<Move, ChessLibMoveGenerator>, ZeroSumEvaluator<Move, ChessLibMoveGenerator> {
	private final Hb2SimplifiedEvaluator ev = new Hb2SimplifiedEvaluator();
	private final NaiveEvaluator enNaive = new NaiveEvaluator();
	@Override
	public int evaluateAsWhite(ChessLibMoveGenerator board) {
		// Calculer l'évaluation de base
		int baseEvaluation;
//		synchronized (this) { // Alternative à la méthode fork
			ev.init(board); // On n'utilise pas le côté incréemantal
			baseEvaluation = ev.evaluateAsWhite(board);
//		}
		// Tu peux ajouter ce qui concerne les chaines de pions, mauvais/bon fous, etc à l'évaluation de base ...
		return baseEvaluation;
	}
	
	@Override
	public StaticEvaluator<Move, ChessLibMoveGenerator> fork() {
		// Attention : SimplifiedEvaluator n'est pas thread safe, ce qui est généralement le cas des évaluateurs statiques (d'où l'implémentation par défaut de fork qui renvoie this).
		// Pour éviter les pb de threading, soit on crée un évaluateur par thread comme ici, soit l'usage du SimplifiedEvaluator doit être protégé par un synchronized (pas performant). 
		return new Hb2MyFirstEvaluator();
	}


	public static void main(String[] args) {
		// Exemple pour créer un ChessLibMoveGenerator à partir d'un FEN et l'évaluer
		ChessLibMoveGenerator mvg = new ChessLibMoveGenerator(new Board());
		mvg.getBoard().loadFromFen("8/3b3p/p3P1p1/3K4/5P1P/2k5/8/8 b - - 0 56");
		System.out.println(new Hb2MyFirstEvaluator().evaluate(mvg));
	}
}
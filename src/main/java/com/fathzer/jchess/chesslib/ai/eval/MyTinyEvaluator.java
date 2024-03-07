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
		ev.init(board);
		int baseEvaluation = ev.evaluate(board);
		// Tu peux ajouter ce qui concerne les chaines de pions, mauvais/bon fous, etc à l'évaluation de base ...
		return baseEvaluation;
	}

	public static void main(String[] args) {
		// Exemple pour créer un ChessLibMoveGenerator à partir d'un FEN et l'évaluer
		ChessLibMoveGenerator mvg = new ChessLibMoveGenerator(new Board());
		mvg.getBoard().loadFromFen("8/3b3p/p3P1p1/3K4/5P1P/2k5/8/8 b - - 0 56");
		System.out.println(new MyTinyEvaluator().evaluate(mvg));
	}
}

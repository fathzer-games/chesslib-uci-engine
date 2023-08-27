package com.fathzer.jchess.chesslib.uci;

import com.fathzer.jchess.chesslib.ChessLibEngine;
import com.fathzer.jchess.uci.UCI;

public class Main {

	public static void main(String[] args) {
		new UCI(new ChessLibEngine()).run();
	}

}

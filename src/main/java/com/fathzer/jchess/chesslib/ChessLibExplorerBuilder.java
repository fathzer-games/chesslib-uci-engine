package com.fathzer.jchess.chesslib;

import com.fathzer.chess.utils.adapters.BoardExplorer;
import com.fathzer.chess.utils.adapters.BoardExplorerBuilder;

public interface ChessLibExplorerBuilder extends BoardExplorerBuilder<ChessLibMoveGenerator> {

	@Override
	default BoardExplorer getExplorer(ChessLibMoveGenerator board) {
		return new ChessLibBoardExplorer(board.getBoard());
	}

}

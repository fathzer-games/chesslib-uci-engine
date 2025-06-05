package com.fathzer.chess.test.utils;

import java.util.List;

import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.chess.utils.model.IBoard;
import com.fathzer.games.MoveGenerator.MoveConfidence;
import com.github.bhlangonijr.chesslib.move.Move;

public class ChessLibBoard implements IBoard<Move>{
    private final ChessLibMoveGenerator board;

    public ChessLibBoard(ChessLibMoveGenerator board) {
        this.board = board;
    }

    @Override
    public List<Move> getMoves() {
        return board.getMoves();
    }

    @Override
    public boolean makeMove(Move mv) {
        return board.makeMove(mv, MoveConfidence.LEGAL);
    }

    @Override
    public void unmakeMove() {
        board.unmakeMove();
    }

    public ChessLibMoveGenerator getBoard() {
        return board;
    }
}
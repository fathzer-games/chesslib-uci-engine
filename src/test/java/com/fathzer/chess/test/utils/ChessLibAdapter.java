package com.fathzer.chess.test.utils;

import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.chess.utils.model.TestAdapter;
import com.fathzer.chess.utils.model.Variant;
import com.github.bhlangonijr.chesslib.move.Move;

public class ChessLibAdapter implements TestAdapter<ChessLibBoard, Move> {

    @Override
    public ChessLibBoard fenToBoard(String fen, Variant variant) {
        final ChessLibMoveGenerator from = FENUtils.from(fen);
        return new ChessLibBoard(from);
    }
}
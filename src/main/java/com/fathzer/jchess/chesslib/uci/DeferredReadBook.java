package com.fathzer.jchess.chesslib.uci;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.movelibrary.MoveLibrary;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.fathzer.jchess.lichess.DefaultOpenings;
import com.github.bhlangonijr.chesslib.move.Move;

class DeferredReadBook implements MoveLibrary<Move, ChessLibMoveGenerator> {
	private final String url;
	private MoveLibrary<Move, ChessLibMoveGenerator> internal;

	DeferredReadBook(String url) {
		this.url = url;
	}

	@Override
	public Optional<EvaluatedMove<Move>> apply(ChessLibMoveGenerator board) {
		if (internal==null) {
			return Optional.empty();
		}
		return internal.apply(board);
	}
	
	boolean isInitRequired() {
		return internal==null;
	}
	
	void init() throws IOException {
		if (isInitRequired()) {
			internal = readOpenings(toURL(this.url));
		}
	}
	
	private static URL toURL(String path) throws IOException {
		URL url;
		try {
			url = new URL(path);
		} catch (MalformedURLException e) {
			File file = new File(path);
			if (!file.exists()) {
				throw new FileNotFoundException();
			}
			url = file.toURI().toURL();
		}
		return url;
	}
	
	private static MoveLibrary<Move, ChessLibMoveGenerator> readOpenings(final URL location) throws IOException {
		final boolean compressed = location.getFile().endsWith(".gz");
		try (InputStream stream = location.openStream()) {
			return new DefaultOpenings(()->stream, compressed);
		}
	}

	String getUrl() {
		return url;
	}
}

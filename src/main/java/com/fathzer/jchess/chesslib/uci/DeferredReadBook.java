package com.fathzer.jchess.chesslib.uci;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import com.fathzer.games.MoveGenerator;
import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.movelibrary.MoveLibrary;

class DeferredReadBook<M, B extends MoveGenerator<M>> implements MoveLibrary<M, B> {
	@FunctionalInterface
	static interface IOReader<T> {
		T read(URL url) throws IOException;
	}
	
	private final String url;
	private IOReader<MoveLibrary<M, B>> reader;
	private MoveLibrary<M, B> internal;

	DeferredReadBook(String url, IOReader<MoveLibrary<M,B>> reader) {
		this.url = url;
		this.reader = reader;
	}

	@Override
	public Optional<EvaluatedMove<M>> apply(B board) {
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
			internal = reader.read(toURL(this.url));
		}
	}
	
	static URL toURL(String path) throws IOException {
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

	String getUrl() {
		return url;
	}
}

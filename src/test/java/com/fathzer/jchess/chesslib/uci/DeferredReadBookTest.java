package com.fathzer.jchess.chesslib.uci;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URI;

import org.junit.jupiter.api.Test;

import com.fathzer.chess.test.utils.FENUtils;
import com.fathzer.jchess.chesslib.ChessLibMoveGenerator;
import com.github.bhlangonijr.chesslib.move.Move;

class DeferredReadBookTest {
	

	@Test
	void test() throws Exception {
		String httpsURL = "https://myApp.org/file.gz";
		assertEquals(URI.create(httpsURL).toURL(), DeferredReadBook.toURL(httpsURL));
		
		assertThrows(IOException.class, () -> DeferredReadBook.toURL("httpx://myApp.org/file.gz"));
		assertThrows(IOException.class, () -> DeferredReadBook.toURL("src/test/resources/unknownFile.json"));
		
		final String path = "src/test/resources/openings.json";
		final DeferredReadBook<Move, ChessLibMoveGenerator> rb = new DeferredReadBook<>(path, Main::readOpenings);
		assertTrue(rb.isInitRequired());
		final String fen = "rn1qkb1r/pp3ppp/2p1pnb1/3p4/2PP3N/2N2PP1/PP2P2P/R1BQKB1R w KQkq -";
		assertTrue(rb.apply(FENUtils.from(fen)).isEmpty());
		rb.init();
		assertFalse(rb.isInitRequired());
		assertFalse(rb.apply(FENUtils.from(fen)).isEmpty());
		// A second init call should no throw any exception
		rb.init();
	}
}

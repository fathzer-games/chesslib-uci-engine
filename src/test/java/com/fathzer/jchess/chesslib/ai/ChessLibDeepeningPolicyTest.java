package com.fathzer.jchess.chesslib.ai;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.*;

import org.junit.jupiter.api.Test;

import com.fathzer.games.ai.iterativedeepening.DeepeningPolicy;

class ChessLibDeepeningPolicyTest {
	@Test
	void test() {
		final DeepeningPolicy policy = new ChessLibDeepeningPolicy(10);
		assertEquals(10, policy.getDepth());
		assertEquals(2, policy.getStartDepth());
		assertEquals(4, policy.getNextDepth(2));
		assertEquals(6, policy.getNextDepth(4));
		assertEquals(7, policy.getNextDepth(6));
		assertEquals(10, policy.getNextDepth(10));
	}

	@Test
	void bug20241127() {
		// isEnoughTimeToDeepen returned true when time was spent and depth was < 5
		final DeepeningPolicy policy = new ChessLibDeepeningPolicy(10);
		policy.setMaxTime(1);
		assertThrows(IllegalStateException.class, () -> policy.getSpent());
		policy.start();
		await().atLeast(10, TimeUnit.MILLISECONDS);
		assertFalse(policy.isEnoughTimeToDeepen(4));
		
		policy.setMaxTime(10000);
		policy.start();
	}
	

}

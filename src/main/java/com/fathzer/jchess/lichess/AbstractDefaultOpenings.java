package com.fathzer.jchess.lichess;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fathzer.games.MoveGenerator;
<<<<<<< Upstream, based on origin/main
import com.fathzer.games.ai.evaluation.EvaluatedMove;
import com.fathzer.games.ai.evaluation.Evaluation;
=======
import com.fathzer.games.movelibrary.AbstractMoveLibrary;
>>>>>>> fec2d1d Bon, commit du Hb2MyFirstEvaluator, il a gagné contre le SimplifiedEvaluator

public abstract class AbstractDefaultOpenings<M, B extends MoveGenerator<M>> extends AbstractMoveLibrary<JSONObject, M, B> {
	private final JSONObject db;
	
	protected AbstractDefaultOpenings(Supplier<InputStream> stream, boolean zipped) throws IOException {
		db = readJSON(stream, zipped);
		this.setMoveSelector(weightedMoveSelector());
	}
	
	private JSONObject readJSON(Supplier<InputStream> stream, boolean zipped) throws IOException {
		try (InputStream in = zipped ? new GZIPInputStream(stream.get()) : stream.get()) {
			return new JSONObject(new JSONTokener(in));
		}
	}
	
	private String toReducedXFen(B board) {
		final String fen = toXFEN(board);
		int index = fen.lastIndexOf(' ', fen.lastIndexOf(' ')-1);
		return fen.substring(0, index);
	}
	
	@Override
	protected List<JSONObject> getRecords(B board) {
		final String fen = toReducedXFen(board);
		final JSONObject theRecord = db.optJSONObject(fen);
		final JSONArray moves = theRecord==null ? null : theRecord.optJSONArray("moves");
		if (moves==null) {
			return Collections.emptyList();
		}
		final List<JSONObject> result = new LinkedList<>();
		for (int i=0;i<moves.length();i++) {
			result.add(moves.getJSONObject(i));
		}
		return result;
	}

	@Override
	protected EvaluatedMove<M> toEvaluatedMove(B board, JSONObject move) {
		return new EvaluatedMove<>(fromUCI(board, getCoord(move)), Evaluation.UNKNOWN);
	}

	@Override
	protected long getWeight(JSONObject move) {
		return move.getLong("count");
	}
		
	protected String getCoord(JSONObject move) {
		return move.getString("coord");
	}
		
	protected abstract String toXFEN(B board);
	protected abstract M fromUCI(B board, String move);
}

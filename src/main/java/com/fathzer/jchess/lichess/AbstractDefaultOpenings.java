package com.fathzer.jchess.lichess;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fathzer.games.MoveGenerator;

public abstract class AbstractDefaultOpenings<M, B extends MoveGenerator<M>> implements Function<B, M> {
	public enum SelectionPolicy { BEST, PROBABLE, RANDOM }
	
	private static final Random RND = new Random();

	private final JSONObject db;
	private SelectionPolicy selectionPolicy;
	
	protected AbstractDefaultOpenings(Supplier<InputStream> stream, boolean zipped) throws IOException {
		db = readJSON(stream, zipped);
		this.selectionPolicy = SelectionPolicy.PROBABLE;
	}
	
	private JSONObject readJSON(Supplier<InputStream> stream, boolean zipped) throws IOException {
		try (InputStream in = zipped ? new GZIPInputStream(stream.get()) : stream.get()) {
			return new JSONObject(new JSONTokener(in));
		}
	}
	
	private String toReducedFen(B board) {
		final String fen = toXFEN(board);
		int index = fen.lastIndexOf(' ', fen.lastIndexOf(' ')-1);
		return fen.substring(0, index);
	}

	@Override
	public M apply(B board) {
		final String fen = toReducedFen(board);
		final JSONObject theRecord = db.optJSONObject(fen);
		if (theRecord==null) {
			return null;
		}
		final JSONArray moves = theRecord.optJSONArray("moves");
		if (moves.length()==0) {
			return null;
		}
		int index;
		if (selectionPolicy==SelectionPolicy.RANDOM) {
			index = RND.nextInt(moves.length());
		} else if (selectionPolicy==SelectionPolicy.BEST) {
			index = 0;
		} else {
			int count = 0;
			for (int i=0;i<moves.length();i++) {
				final JSONObject move = moves.getJSONObject(i);
				count += getCount(move);
			}
			final int value = RND.nextInt(count);
			count = 0;
			index = moves.length()-1;
			for (int i=0;i<moves.length();i++) {
				final JSONObject move = moves.getJSONObject(i);
				count += getCount(move);
				if (value<count) {
					index = i;
					break;
				}
			}
		}
		final String move = getCoord(moves.getJSONObject(index));
		return fromUCI(board, move);
	}
	
	protected int getCount(JSONObject move) {
		return move.getInt("count");
	}
		
	protected String getCoord(JSONObject move) {
		return move.getString("coord");
	}
		
	protected abstract M fromUCI(B board, String move);
	protected abstract String toXFEN(B board);
}

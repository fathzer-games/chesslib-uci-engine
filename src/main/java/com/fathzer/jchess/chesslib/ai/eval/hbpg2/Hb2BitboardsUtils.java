package com.fathzer.jchess.chesslib.ai.eval.hbpg2;

public class Hb2BitboardsUtils {

	public static final long h_file = 0x8080808080808080L;
	public static final long g_file = h_file >>> 1;
	public static final long f_file = h_file >>> 2;
	public static final long e_file = h_file >>> 3;
	public static final long d_file = h_file >>> 4;
	public static final long c_file = h_file >>> 5;
	public static final long b_file = h_file >>> 6;
	public static final long a_file = h_file >>> 7;

	public static final long rank_1 = 0x00000000000000FFL;
	public static final long rank_2 = rank_1 << 8;
	public static final long rank_3 = rank_1 << 16;
	public static final long rank_4 = rank_1 << 24;
	public static final long rank_5 = rank_1 << 32;
	public static final long rank_6 = rank_1 << 40;
	public static final long rank_7 = rank_1 << 48;
	public static final long rank_8 = rank_1 << 56;

	public static final long not_a_file = ~a_file;
	public static final long not_h_file = ~h_file;
	public static final long not_rank_1 = ~rank_1;
	public static final long not_rank_8 = ~rank_8;

	public static final long[] whitePassedPawnMask = new long[] { 0x0303030303030300L, 0x0707070707070700L,
			0x0e0e0e0e0e0e0e00L, 0x1c1c1c1c1c1c1c00L, 0x3838383838383800L, 0x7070707070707000L, 0xe0e0e0e0e0e0e000L,
			0xc0c0c0c0c0c0c000L, 0x0303030303030000L, 0x0707070707070000L, 0x0e0e0e0e0e0e0000L, 0x1c1c1c1c1c1c0000L,
			0x3838383838380000L, 0x7070707070700000L, 0xe0e0e0e0e0e00000L, 0xc0c0c0c0c0c00000L, 0x0303030303000000L,
			0x0707070707000000L, 0x0e0e0e0e0e000000L, 0x1c1c1c1c1c000000L, 0x3838383838000000L, 0x7070707070000000L,
			0xe0e0e0e0e0000000L, 0xc0c0c0c0c0000000L, 0x0303030300000000L, 0x0707070700000000L, 0x0e0e0e0e00000000L,
			0x1c1c1c1c00000000L, 0x3838383800000000L, 0x7070707000000000L, 0xe0e0e0e000000000L, 0xc0c0c0c000000000L,
			0x0303030000000000L, 0x0707070000000000L, 0x0e0e0e0000000000L, 0x1c1c1c0000000000L, 0x3838380000000000L,
			0x7070700000000000L, 0xe0e0e00000000000L, 0xc0c0c00000000000L, 0x0303000000000000L, 0x0707000000000000L,
			0x0e0e000000000000L, 0x1c1c000000000000L, 0x3838000000000000L, 0x7070000000000000L, 0xe0e0000000000000L,
			0xc0c0000000000000L, 0x0300000000000000L, 0x0700000000000000L, 0x0e00000000000000L, 0x1c00000000000000L,
			0x3800000000000000L, 0x7000000000000000L, 0xe000000000000000L, 0xc000000000000000L, 0x0000000000000000L,
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
			0x0000000000000000L, 0x0000000000000000L };

	public static final long[] blackPassedPawnMask = new long[] {

			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000003L, 0x0000000000000007L,
			0x000000000000000eL, 0x000000000000001cL, 0x0000000000000038L, 0x0000000000000070L, 0x00000000000000e0L,
			0x00000000000000c0L, 0x0000000000000303L, 0x0000000000000707L, 0x0000000000000e0eL, 0x0000000000001c1cL,
			0x0000000000003838L, 0x0000000000007070L, 0x000000000000e0e0L, 0x000000000000c0c0L, 0x0000000000030303L,
			0x0000000000070707L, 0x00000000000e0e0eL, 0x00000000001c1c1cL, 0x0000000000383838L, 0x0000000000707070L,
			0x0000000000e0e0e0L, 0x0000000000c0c0c0L, 0x0000000003030303L, 0x0000000007070707L, 0x000000000e0e0e0eL,
			0x000000001c1c1c1cL, 0x0000000038383838L, 0x0000000070707070L, 0x00000000e0e0e0e0L, 0x00000000c0c0c0c0L,
			0x0000000303030303L, 0x0000000707070707L, 0x0000000e0e0e0e0eL, 0x0000001c1c1c1c1cL, 0x0000003838383838L,
			0x0000007070707070L, 0x000000e0e0e0e0e0L, 0x000000c0c0c0c0c0L, 0x0000030303030303L, 0x0000070707070707L,
			0x00000e0e0e0e0e0eL, 0x00001c1c1c1c1c1cL, 0x0000383838383838L, 0x0000707070707070L, 0x0000e0e0e0e0e0e0L,
			0x0000c0c0c0c0c0c0L, 0x0003030303030303L, 0x0007070707070707L, 0x000e0e0e0e0e0e0eL, 0x001c1c1c1c1c1c1cL,
			0x0038383838383838L, 0x0070707070707070L, 0x00e0e0e0e0e0e0e0L, 0x00c0c0c0c0c0c0c0L, };

	public static final long shiftWest(long b) {
		return (b >>> 1) & not_h_file;
	}

	public static final long shiftEast(long b) {
		return (b << 1) & not_a_file;
	}

	public static final long shiftSouth(long b) {
		return b >>> 8;
	}

	public static final long shiftNorth(long b) {
		return b << 8;
	}

	public static final long shiftNorthEast(long b) {
		return (b << 9) & not_a_file;
	}

	public static final long shiftSouthEast(long b) {
		return (b >>> 7) & not_a_file;
	}

	public static final long shiftSouthWest(long b) {
		return (b >>> 9) & not_h_file;
	}

	public static final long shiftNorthWest(long b) {
		return (b << 7) & not_h_file;
	}

}

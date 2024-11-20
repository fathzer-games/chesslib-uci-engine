package com.fathzer.jchess.chesslib.hbpg2.test;

import java.util.List;

import com.github.bhlangonijr.chesslib.Bitboard;
import com.github.bhlangonijr.chesslib.Square;

public class BitboardTries {
	
	public static void printLongAsBinary(long l) {
		for(int i = 0; i < Long.numberOfLeadingZeros((long)l); i++) {
		      System.out.print('0');
		}
		System.out.println(Long.toBinaryString((long)l));
	}
	
	public static void printLstSquare(List<Square> lstSq) {
		for (Square sq : lstSq) {
		      System.out.print(sq.value());
		      System.out.print(" ");
		}
		System.out.println();
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Square> lstSquare = Bitboard.bbToSquareList(256L);
		printLstSquare(lstSquare);
		List<Square> lstSquare2 = Bitboard.bbToSquareList(1L);
		printLstSquare(lstSquare2);
		List<Square> lstSquare3 = Bitboard.bbToSquareList(1099511627776L);
		printLstSquare(lstSquare3);
		List<Square> lstSquare4 = Bitboard.bbToSquareList(512L);
		printLstSquare(lstSquare4);

	}

}

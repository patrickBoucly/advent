package a2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class A2016Day15 extends A2016 {
	String input = "10001001100000001";

	public A2016Day15(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day15 d = new A2016Day15(16);
		long startTime = System.currentTimeMillis();
		// System.out.println(d.s1(false));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s3(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public String s3(boolean b) {
		String input = "10001001100000001";
		int k = input.length();
		int size = 35651584;
		String next = "a0b";
		for (int i = 0; i < 10; i++) {
			next = getNext(next, "a", "b");

		}
		String c = next;
		int g = getSize(next);

		next = "c0d";
		while (getSize2(next, g) < size) {
			next = getNext(next, "c", "d");

		}
		String lastTooLong = next;
		while (getSize2(next, g) > size) {
			lastTooLong = next;
			next = next.substring(0, next.length() - 1);
		}

		lastTooLong = lastTooLong.substring(0, lastTooLong.length() - 1) + StringUtils.reverse(c);

		next = lastTooLong;
		while (getSize3(next, g) > size) {
			lastTooLong = next;
			next = next.substring(0, next.length() - 1);

		}

		lastTooLong = lastTooLong.substring(0, lastTooLong.length() - 1) + input;
		String finalDragonCurve = lastTooLong.substring(0, lastTooLong.length() - 7);
		return checksum2(finalDragonCurve, input, c);
	}

	private String checksum2(String dragonCurved, String input, String c) {
		if (dragonCurved.length() % 2 != 0) {
			return dragonCurved;
		} else {
			dragonCurved = aChecksum2(dragonCurved, input, c);
			return checksum2(dragonCurved, input, c);
		}
	}

	private String aChecksum2(String dragonCurvedAlpha, String input, String c) {
		String res = "";
		String dragonCurved=dragonCurvedAlpha;
		if (dragonCurvedAlpha.substring(0, 1).equals("c")) {
		 dragonCurved = sansCD(dragonCurvedAlpha, input, c);
		}
		for (int i = 0; i < dragonCurved.length() - 1; i = i + 2) {
			String un = dragonCurved.substring(i, i + 1);
			String deux = dragonCurved.substring(i + 1, i + 2);
			if (un.equals("a")) {
				res += aChecksum(input + deux);
			} else if (un.equals("b")) {
				res += aChecksum(StringUtils.reverse(input) + deux);
			} else if (un.equals(deux)) {
				res += "1";
			} else {
				res += "0";
			}
		}
		return res;
	}

	private String sansCD(String dragonCurved, String input, String contenuDeC) {
		String sansCD = "";
		for (int i = 0; i < dragonCurved.length(); i = i + 2) {
			if (dragonCurved.substring(i, i + 1).equals("c")) {
				sansCD += contenuDeC;
			} else if (dragonCurved.substring(i, i + 1).equals("d")) {
				sansCD += StringUtils.reverse(contenuDeC);
			} else {
				sansCD += dragonCurved.substring(i, i + 1);
			}
		}
		return sansCD;
	}

	private static int getSize3(String next, int g) {
		int res = 0;
		for (int i = 0; i < next.length(); i++) {
			String l = next.substring(i, i + 1);
			if (l.equals("0") || l.equals("1")) {
				res += 1;
			} else if (l.equals("c") || l.equals("d")) {
				res += g;
			} else {
				res += 17;
			}
		}
		return res;
	}

	private static int getSize2(String next, int g) {
		int res = 0;
		for (int i = 0; i < next.length(); i++) {
			if (next.substring(i, i + 1).equals("0")) {
				res += 1;
			} else {
				res += g;
			}
		}
		return res;
	}

	private static int getSize(String next) {
		int res = 0;
		for (int i = 0; i < next.length(); i++) {
			if (next.substring(i, i + 1).equals("0")) {
				res += 1;
			} else {
				res += 17;
			}
		}
		return res;
	}

	public static String getNext(String next, String l, String lb) {
		String res = next + "0";

		for (int i = 0; i < next.length(); i++) {
			if (next.substring(i, i + 1).equals(l)) {
				res += lb;
			} else if (next.substring(i, i + 1).equals("0")) {
				res += "0";
			} else {
				res += l;
			}
		}
		return res;

	}

	public String s1(boolean b) {
		if (b) {
			return checksum(dragonCurve(input, 272));
		}
		return checksum(dragonCurve("10000", 20));
	}

	private String checksum(String dragonCurved) {
		if (dragonCurved.length() % 2 != 0) {
			return dragonCurved;
		} else {
			dragonCurved = aChecksum(dragonCurved);
			return checksum(dragonCurved);
		}
	}

	private String aChecksum(String dragonCurved) {
		String res = "";
		for (int i = 0; i < dragonCurved.length(); i = i + 2) {
			if (dragonCurved.substring(i, i + 1).equals(dragonCurved.substring(i + 1, i + 2))) {
				res += "1";
			} else {
				res += "0";
			}
		}
		return res;
	}

	private String dragonCurve(String a, int discSize) {
		String pattern = a + "0" + aDragonCurved(a);
		int l = pattern.length();
		int nbIter = 0;
		String res = pattern;
		boolean fini = false;
		while (!fini) {
			if (l >= discSize) {
				for (int i = 0; i < nbIter; i++) {
					pattern += "0" + aDragonCurved(pattern);
				}
				return pattern.substring(0, discSize);
			}
			nbIter++;
			l = 2 * l + 1;
		}
		return "";
	}

	private String aDragonCurved(String a) {
		String b = a;
		String c = StringUtils.reverse(b);
		b = "";
		for (int i = 0; i < c.length(); i++) {
			if (c.substring(i, i + 1).equals("0")) {
				b += "1";
			} else {
				b += "0";
			}
		}
		return b;
	}

	public String s2(boolean b) {
		// String dc=dragonCurve("10001001100000001",35651584);

		for (int i = 0; i < 10000; i++) {
			int j = 17 * ((int) Math.pow(2, i));
			System.out.println(dragonCurve(input, j));
		}
		return "";
	}

	public static List<Long> getDuration() {
		A2016Day15 d = new A2016Day15(16);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		// d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1, 3755797L);
	}

}

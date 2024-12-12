package a2015;

import java.util.Arrays;
import java.util.List;

public class A2015Day8 extends A2015 {
	public A2015Day8(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2015Day8 d = new A2015Day8(8);
		long startTime = System.currentTimeMillis();
		//System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public long s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		int res = 0;
		for (String s : inputL) {
			int tailleM=tailleEnMemoire(s.substring(1, s.length() - 1));
			res += (s.length() - tailleM);
		}
		return res;
	}

	private int tailleEnMemoire(String s) {

		if (s.isEmpty() || s.length() == 0 || s.equals("\"\"")) {
			return 0;
		}
		int res = 0;
		int j = 0;
		if (s.length() == 1) {
			return res + 1;
		}
		if (!s.contains("\\")) {
			return s.length();
		} else {
			int pos = s.indexOf("\\");
			if (pos != 0) {
				return pos + tailleEnMemoire(s.substring(pos));
			} else {

				String next1 = s.substring(j + 1, j + 2);
				if (!next1.equals("x") && !next1.equals("\\")) {
					res += tailleEnMemoire(s.substring(1));
				}else if(next1.equals("\\")) {
					res+= tailleEnMemoire(s.substring(2))+1;
				}else {
					if (isHexadecimal(s.substring(j + 2, j + 4))) {
						res += tailleEnMemoire(s.substring(4)) + 1;
					} else {
						res += tailleEnMemoire(s.substring(4)) + 4;
					}
				} 
			}
		}

		return res;
	}

	
	public static boolean isHexadecimal(String input) {
		// Vérifie si la chaîne est de longueur 2 et correspond à un hexadécimal
		return input != null && input.matches("^[0-9a-fA-F]{2}$");
	}

	public long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		int res = 0;
		for (String s : inputL) {
			//System.out.println(s);
			//System.out.println(s.length());
			int tailleE=tailleEncodage(s.substring(1, s.length() - 1));
			//System.out.println(tailleE+6);
			res += (tailleE+6-s.length());
		}
		return res;

	}

	private int tailleEncodage(String s) {
		StringBuilder st=new StringBuilder();
		if (s.isEmpty() || s.length() == 0 ) {
			return 0;
		}
		int res = 0;
		int j = 0;
		if (s.length() == 1) {
			return res + 1;
		}
		if (!s.contains("\\")) {
			return s.length();
		} else {
			int pos = s.indexOf("\\");
			if (pos != 0) {
				return pos + tailleEncodage(s.substring(pos));
			} else {

				String next1 = s.substring(j + 1, j + 2);
				if (!next1.equals("x")) {
					res+= tailleEncodage(s.substring(2))+4;
				}else {
					if (isHexadecimal(s.substring(j + 2, j + 4))) {
						res += tailleEncodage(s.substring(4)) + 5;
					} else {
						res += tailleEncodage(s.substring(4)) + 4;
					}
				} 
			}
		}

		return res;
	}

	public static List<Long> getDuration() {
		A2015Day8 d = new A2015Day8(8);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1, endTime - startTime);
	}

}

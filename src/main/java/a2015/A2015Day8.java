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
		System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		// System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public long s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		int res = 0;
		for (String s : inputL) {
			System.out.println(s);
			System.out.println(s.length());
			System.out.println(taillEnMemoire(s));
			res += (s.length() - taillEnMemoire(s));
		}
		return res;
	}

	private int taillEnMemoire(String s) {
		s = s.substring(1, s.length() - 1);
		if (s.isEmpty() || s.length() == 0) {
			return 0;
		}
		int res = 0;
		int j = 0;
		while (s.length() > 0) {
			if (!s.substring(0, 1).equals("\\")) {
				res = taillEnMemoire(s.substring(1)) + 1;
			} else {
				if (s.length() == 1) {
					return res+1;
				} else {
					String next1 = s.substring(j + 1, j + 2);
					if (next1.equals("\\") || next1.equals("\"")) {
						res = taillEnMemoire(s.substring(2)) + 1;
					}else if(next1.equals("x")) {
						if (s.length() == 2) {
							res = taillEnMemoire(s.substring(3)) + 2;
						}else if(s.length() == 3) {
							res = taillEnMemoire(s.substring(4)) + 3;
						}else {
							if(isHexadecimal(s.substring(j + 1, j + 2))) {
								res = taillEnMemoire(s.substring(4)) + 1;
							}else {
								res = taillEnMemoire(s.substring(4)) + 4; 
							}
						}
					}else {
						res = taillEnMemoire(s.substring(2)) + 2;
					}
				}
			}
		}
		while (j < s.length()) {
			if (!s.substring(j, j + 1).equals("\\")) {
				res++;
				j++;
			} else {
				if (j == s.length() - 1) {
					j++;
				} else {
					String next1 = s.substring(j + 1, j + 2);
					if (next1.equals("\\")) {
						res++;
						j += j + 2;
					} else if (next1.equals("\"")) {
						res++;
						j += j + 2;
					} else {
						if (j + 1 == s.length() - 1) {
							j++;
						} else {
							String next2 = s.substring(j + 2, j + 3);
							if (next2.equals("\\")) {
								res++;
								j += j + 2;
							} else if (next2.equals("\"")) {
								res++;
								j += j + 2;
							}
						}
					}
				}
			}
		}
		return s.length() - res;
	}

	private int compterHexa(String s) {
		String[] sp = s.split("\\\\x");
		int res = 0;
		if (sp.length == 1) {
			return 0;
		}
		if (sp.length == 2) {
			if (s.length() == sp[1].length()) {
				return 0;
			} else {
				if (isHexadecimal(sp[1].substring(0, 2))) {
					return 1;
				}
				return 0;
			}
		}
		for (int j = 0; j < sp.length; j++) {
			String p = sp[j];
			if (p.length() == 1) {
				if (isHexadecimal(p.substring(0, 1))) {
					res++;
				}
			}
			if (p.length() > 2) {
				if (isHexadecimal(p.substring(0, 2))) {
					res++;
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
		int res = (int) s1(b);

		return 0L;

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

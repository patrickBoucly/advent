package a2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A2016Day9 extends A2016 {
	public A2016Day9(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day9 d = new A2016Day9(9);
		long startTime = System.currentTimeMillis();
		System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public Long s2(boolean b) {
		List<ChaineRep> chaines = new ArrayList<>();
		List<ChaineRep> nextChaines = new ArrayList<>();
		chaines.add(new ChaineRep(1, getInput(b).trim()));
		Long l = 0L;

		for (ChaineRep cr : chaines) {
			nextChaines.addAll(decode2(cr));
		}
		chaines = new ArrayList<>();
		for (ChaineRep cr : nextChaines) {
			if (cr.chaine.contains("(")) {
				chaines.add(cr);
			} else {
				l += cr.rep * cr.chaine.length();
			}
		}
		for (ChaineRep cr : chaines) {
			l += compteUneChaine(cr);
		}
		chaines = new ArrayList<>(nextChaines);

		return l;
	}

	private Long compteUneChaine(ChaineRep macr) {
		List<ChaineRep> chaines = new ArrayList<>();
		List<ChaineRep> nextChaines = new ArrayList<>();
		chaines.add(macr);
		Long l = 0L;

		for (ChaineRep cr : chaines) {
			nextChaines.addAll(decode2(cr));
		}
		chaines = new ArrayList<>();
		for (ChaineRep cr : nextChaines) {
			if (cr.chaine.contains("(")) {
				chaines.add(cr);
			} else {
				l += cr.rep * cr.chaine.length();
			}
		}
		for (ChaineRep cr : chaines) {
			l += compteUneChaine(cr);
		}
		return l;

	}

	private List<ChaineRep> decode2(ChaineRep cr) {
		List<ChaineRep> l1 = new ArrayList<>();
		int pos = 0;
		while (pos < cr.chaine.length()) {
			if (cr.chaine.substring(pos, pos + 1).equals("(")) {
				String ext = cr.chaine.substring(pos + 1, pos + cr.chaine.substring(pos).indexOf(")"));
				int r = Integer.valueOf(ext.split("x")[1]);
				int l = Integer.valueOf(ext.split("x")[0]);
				l1.add(new ChaineRep(r * cr.rep,
						cr.chaine.substring(pos + ext.length() + 2, pos + ext.length() + 2 + l)));
				pos += ext.length() + 2 + l;
			} else {
				l1.add(new ChaineRep(1, "a"));
				pos++;
			}

		}
		return l1;
	}

	public int s1(boolean b) {
		String input = getInput(b).trim();
		String dec = decode(input);
		return dec.length();
	}

	public class ChaineRep {
		int rep;
		String chaine;

		public ChaineRep(int rep, String chaine) {
			super();
			this.rep = rep;
			this.chaine = chaine;
		}

		public int getRep() {
			return rep;
		}

		public void setRep(int rep) {
			this.rep = rep;
		}

		public String getChaine() {
			return chaine;
		}

		public void setChaine(String chaine) {
			this.chaine = chaine;
		}

		@Override
		public String toString() {
			return "" + rep + "," + chaine;
		}

	}

	private String decode(String input) {
		StringBuilder res = new StringBuilder();
		int pos = 0;
		while (pos < input.length()) {
			if (input.substring(pos, pos + 1).equals("(")) {
				String ext = input.substring(pos + 1, pos + input.substring(pos).indexOf(")"));
				int r = Integer.valueOf(ext.split("x")[1]);
				int l = Integer.valueOf(ext.split("x")[0]);
				for (int j = 1; j <= r; j++) {
					res.append(input.substring(pos + ext.length() + 2, pos + ext.length() + 2 + l));
				}
				pos += ext.length() + 2 + l;
			} else {
				res.append(input.substring(pos, pos + 1));
				pos++;
			}
			// System.out.println(res);
		}
		return res.toString();
	}

	public static List<Long> getDuration() {
		A2016Day9 d = new A2016Day9(9);
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

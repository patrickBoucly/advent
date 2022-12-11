package a2022;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class A2022Day06 extends A2022 {

	public A2022Day06(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day06 d = new A2022Day06(6);
		System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public int s2(boolean b) {
		String input = getInput(b);
		return getMarqueurPosition(input,14);
	}

	public int s1(boolean b) {
		String input = getInput(b);
		return getMarqueurPosition(input,4);
	}

	private int getMarqueurPosition(String input,int nbLettre) {
		for (int i = 0; i < input.length() - nbLettre; i++) {
			String nLettre=input.substring(i, i+nbLettre);
			if(toutesLesLettresSontDifferentes(nLettre)) {
				return i+nbLettre;
			}
		}
		return 0;
	}

	private boolean toutesLesLettresSontDifferentes(String mot) {
		HashMap<String, Integer> lettres =decompteDesLettresDunMot(mot);
		return lettres.values().stream().noneMatch(v->v>1);
	}
	
	private HashMap<String, Integer> decompteDesLettresDunMot(String mot) {
		HashMap<String, Integer> lettreDuMot = new HashMap<>();
		for (int pos = 0; pos < mot.length() ; pos++) {
			String l = mot.substring(pos, pos + 1);
			if (lettreDuMot.containsKey(l)) {
				lettreDuMot.put(l, lettreDuMot.get(l) + 1);
			} else {
				lettreDuMot.put(l, 1);
			}
		}
		return lettreDuMot;
	}

	public static List<Long> getDuration() {
		A2022Day06 d = new A2022Day06(6);
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

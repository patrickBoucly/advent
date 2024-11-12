package a2015;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class A2015Day5 extends A2015 {
	public A2015Day5(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2015Day5 d = new A2015Day5(5);
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

	public static List<String> voyelles = List.of("a", "e", "i", "o", "u");
	public static List<String> interdites = List.of("ab", "cd", "pq", "xy");

	public long s1(boolean b) {
		Stream<String> inputL = Arrays.asList(getInput(b).split("\n")).stream();
		return inputL.filter(this::estGentille).count();
	}

	public long s2(boolean b) {
		return Arrays.asList(getInput(b).split("\n")).stream().filter(this::estGentilleB).count();
	}

	public boolean estGentilleB(String chaine) {
		return estGentilleCond1B(chaine) && estGentilleCond2B(chaine);
	}

	private boolean estGentilleCond2B(String chaine) {
		for (int i = 0; i < chaine.length() - 2; i++) {
			if (chaine.substring(i, i + 1).equals(chaine.substring(i + 2, i + 3))) {
				return true;
			}
		}
		return false;
	}

	private boolean estGentilleCond1B(String chaine) {
		for (int i = 0; i < chaine.length() - 2; i++) {
			String test = chaine.substring(i, i + 2);
			for (int j = i + 2; j < chaine.length() - 1; j++) {
				if (chaine.substring(j, j + 2).equals(test)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean estGentille(String chaine) {
		HashMap<String, Integer> lettres = getLettres(chaine);
		return estGentilleCond1(lettres) && estGentilleCond2(chaine) && estGentilleCond3(chaine);
	}

	private boolean estGentilleCond3(String chaine) {
		return interdites.stream().noneMatch(chaine::contains);
	}

	private boolean estGentilleCond2(String chaine) {
		for (int i = 0; i < chaine.length() - 1; i++) {
			if (chaine.substring(i, i + 1).equals(chaine.substring(i + 1, i + 2))) {
				return true;
			}
		}
		return false;
	}

	private boolean estGentilleCond1(HashMap<String, Integer> lettres) {
		int nbV = 0;
		for (String l : lettres.keySet()) {
			if (voyelles.contains(l)) {
				nbV += lettres.get(l);
			}
		}
		return nbV > 2;
	}

	private HashMap<String, Integer> getLettres(String mot) {
		HashMap<String, Integer> lettreDuMot = new HashMap<>();
		for (int pos = 0; pos < mot.length(); pos++) {
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
		A2015Day5 d = new A2015Day5(5);
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

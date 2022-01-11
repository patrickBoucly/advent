package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class A2017Day4 extends A2017 {

	public A2017Day4(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day4 d = new A2017Day4(4);
		long startTime = System.currentTimeMillis();
		// System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public int s1(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		int res = 0;
		for (String l : lignes) {
			res += analyse(l);
		}
		return res;
	}

	private int analyse(String l) {
		String[] sp = l.split(" ");
		List<String> connue = new ArrayList<>();
		for (String s : sp) {
			if (connue.contains(s.trim())) {
				return 0;
			} else {
				connue.add(s.trim());
			}
		}
		return 1;
	}

	public int s2(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		int res = 0;
		for (String l : lignes) {
			res += analyse2(l);
		}
		return res;
	}

	private int analyse2(String l) {
		System.out.println(l);
		String[] sp = l.split(" ");
		List<Dic> dicos = new ArrayList<>();
		for (String s : sp) {
			dicos.add(new Dic(getLettres(s),s));
		}
		for (int p = 0; p < dicos.size(); p++) {
			for (int q = 0; q < dicos.size(); q++) {
				if (p != q) {
					//System.out.println(dicos.get(p).mot);
					//System.out.println(dicos.get(q).mot);
					if(dicos.get(p).equals(dicos.get(q))) {
						System.out.println("not valid");
						
						return 0;
					}
				}
			}
		}
		System.out.println("valid");
		return 1;
	}

	public static class Dic {
		HashMap<String, Integer> lettreDuMot;
		String mot;
		public HashMap<String, Integer> getLettreDuMot() {
			return lettreDuMot;
		}

		public Dic(HashMap<String, Integer> lettreDuMot, String mot) {
			super();
			this.lettreDuMot = lettreDuMot;
			this.mot = mot;
		}

		public String getMot() {
			return mot;
		}

		public void setMot(String mot) {
			this.mot = mot;
		}

		public void setLettreDuMot(HashMap<String, Integer> lettreDuMot) {
			this.lettreDuMot = lettreDuMot;
		}

		public Dic(HashMap<String, Integer> lettreDuMot) {
			super();
			this.lettreDuMot = lettreDuMot;
		}

		@Override
		public int hashCode() {
			return Objects.hash(lettreDuMot);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Dic other = (Dic) obj;
			for(String s:lettreDuMot.keySet()) {
				if(!other.lettreDuMot.containsKey(s)) {
					return false;
				} else {
					if(lettreDuMot.get(s) != other.lettreDuMot.get(s)) {
						return false;
					}
				}
			}
			for(String s:other.lettreDuMot.keySet()) {
				if(!lettreDuMot.containsKey(s)) {
					return false;
				} else {
					if(lettreDuMot.get(s) != other.lettreDuMot.get(s)) {
						return false;
					}
				}
			}
			return true;
		}

		@Override
		public String toString() {
			return "Dic [lettreDuMot=" + lettreDuMot + ", mot=" + mot + "]";
		}

		

	}

	private HashMap<String, Integer> getLettres(String mot) {
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
		A2017Day4 d = new A2017Day4(1);
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

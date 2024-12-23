package a2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import outils.MesOutils;

public class A2024Day19 extends A2024 {

	public A2024Day19(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day19 d = new A2024Day19(19);
		// System.out.println(d.s1(true));

		long startTime = System.currentTimeMillis();
		// d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	public long s1(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		Game g = getGame(input);
		return g.getRes1();
	}

	private Game getGame(List<String> input) {
		Set<String> designs = new HashSet<>();
		Set<String> towel = new HashSet<>();
		Set<Integer> cover = new HashSet<>();
		cover.add(0);
		String[] s = input.get(0).trim().split(",");
		for (String st : s) {
			towel.add(st.trim());
		}
		for (int i = 2; i < input.size(); i++) {
			designs.add(input.get(i).trim());
		}
		Set<Scenario> scenar = new HashSet<>();
		return new Game(designs, towel, cover,
				MesOutils.getMaxIntegerFromList(towel.stream().map(t -> t.length()).toList()), scenar);
	}

	public long s2(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		Game g = getGame(input);
		return g.getRes2();
	}

	@Data
	@AllArgsConstructor
	private static class Game {
		public long getRes1() {
			Integer res = 0;
			for (String d : designs) {
				if (isOk(d)) {
					// System.out.println("ok :"+d);
					res++;
				}
			}
			return res;
		}

		public long getRes2() {
			long res = 0;
			for (String d : designs) {
				if (isOk(d)) {
					res += getArrangement(d);
				}
			}
			return res;
		}

		private long getArrangement(String d) {
			long res = 0l;
			Map<Integer, Long> posQtt = new HashMap<>();
			List<String> match = new ArrayList<>();
			for (String towel : towels) {
				if (d.substring(0, towel.length()).equals(towel)) {
					match.add(towel);
					posQtt.put(towel.length(), 1L);
				}
			}
			while (!posQtt.isEmpty()) {
				Map<Integer, Long> nextPosQtt = new HashMap<>();
				for (Entry<Integer, Long> e : posQtt.entrySet()) {
					if (e.getKey().equals(d.length())) {
						if (nextPosQtt.containsKey(d.length())) {
							nextPosQtt.put(d.length(), nextPosQtt.get(d.length()) + e.getValue());
						} else {
							nextPosQtt.put(d.length(), e.getValue());
						}
					} else {
						for (String nextTowal : towels) {
							if (e.getKey() + nextTowal.length() == d.length()) {
								if (d.substring(e.getKey()).equals(nextTowal)) {
									if (nextPosQtt.containsKey(d.length())) {
										nextPosQtt.put(d.length(), nextPosQtt.get(d.length()) + e.getValue());
									} else {
										nextPosQtt.put(d.length(), e.getValue());
									}
								}
							} else if (e.getKey() + nextTowal.length() < d.length()) {
								if (d.substring(e.getKey(), e.getKey() + nextTowal.length()).equals(nextTowal)) {
									if (nextPosQtt.containsKey(e.getKey() + nextTowal.length())) {
										nextPosQtt.put(e.getKey() + nextTowal.length(),
												nextPosQtt.get(e.getKey() + nextTowal.length()) + e.getValue());
									} else {
										nextPosQtt.put(e.getKey() + nextTowal.length(), e.getValue());
									}
								}
							}
						}
					}
				}
				if (nextPosQtt.keySet().size() == 1) {
					for (Entry<Integer, Long> e : nextPosQtt.entrySet()) {
						if(e.getKey().equals(d.length()))
						return e.getValue();
					}
				}
				
				posQtt = new HashMap<>(nextPosQtt);
			}

			return res;
		}

		private boolean isOk(String d) {
			cover = new HashSet<>();
			cover.add(0);
			Integer maxCover = 0;
			List<Integer> starts = new ArrayList<>();
			int coverL = cover.size();
			int newCoverL = cover.size() + 1;

			while (newCoverL > coverL) {
				maxCover = MesOutils.getMaxIntegerFromList(cover.stream().toList());
				coverL = cover.size();
				for (Integer j : cover) {
					if (j >= maxCover - maxLength) {
						starts.add(j);
					}
				}
				for (Integer start : starts) {
					if (start.equals(d.length())) {
						return true;
					}
					for (String towel : towels) {
						// System.out.println(towel);
						if (start + towel.length() == d.length()) {
							if (d.substring(start).equals(towel)) {
								cover.add(start + towel.length());
							}
						} else if (start + towel.length() < d.length()) {
							if (d.substring(start, start + towel.length()).equals(towel)) {
								cover.add(start + towel.length());
							}
						}
					}
				}
				newCoverL = cover.size();
			}
			return false;
		}

		Set<String> designs;
		Set<String> towels;
		Set<Integer> cover;
		Integer maxLength;
		Set<Scenario> scenar;
	}

	@Data
	@AllArgsConstructor
	private static class Scenario {
		int occupe;
		int qtt;
		boolean actif;
		boolean succed;
	}

	public static List<Long> getDuration() {
		A2024Day19 d = new A2024Day19(16);
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

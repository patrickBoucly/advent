package a2017;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2017Day11 extends A2017 {

	public A2017Day11(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day11 d = new A2017Day11(11);
		// d.s1(true);
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

	public long s1(boolean b) {
		HashMap<String, Long> hashMoves = getHashMove(b);
		return compteStep(hashMoves);
	}

	private HashMap<String, Long> getHashMove(boolean b) {
		List<String> moves = Arrays.asList(getInput(b).split(",")).stream().map(String::trim)
				.collect(Collectors.toList());
		HashMap<String, Long> hashMoves = new HashMap<>();
		hashMoves.put("n", moves.stream().filter(s -> s.equals("n")).count());
		hashMoves.put("s", moves.stream().filter(s -> s.equals("s")).count());
		hashMoves.put("ne", moves.stream().filter(s -> s.equals("ne")).count());
		hashMoves.put("nw", moves.stream().filter(s -> s.equals("nw")).count());
		hashMoves.put("se", moves.stream().filter(s -> s.equals("se")).count());
		hashMoves.put("sw", moves.stream().filter(s -> s.equals("sw")).count());
		return hashMoves;
	}

	private long compteStep(HashMap<String, Long> m) {
		HashMap<String, Long> mc = new HashMap<>(m);
		if (mc.values().stream().filter(v -> v > 0).count() <= 2) {
			return mc.values().stream().reduce(0L, (a, b) -> a + b);
		}
		while (mc.values().stream().filter(v -> v > 0).count() > 2) {
			mc = clean(mc);
		}

		return mc.values().stream().filter(v -> v > 0).reduce(0L, (a, b) -> a + b);
	}

	private HashMap<String, Long> clean(HashMap<String, Long> m) {
		if (m.get("n") > 0 && m.get("s") > 0) {
			if (m.get("n") >= m.get("s")) {
				m.put("n", m.get("n") - m.get("s"));
				m.put("s", 0L);
			} else {
				m.put("s", m.get("s") - m.get("n"));
				m.put("n", 0L);
			}
		}
		if (m.get("nw") > 0 && m.get("se") > 0) {
			if (m.get("nw") >= m.get("se")) {
				m.put("nw", m.get("nw") - m.get("se"));
				m.put("se", 0L);
			} else {
				m.put("se", m.get("se") - m.get("nw"));
				m.put("nw", 0L);
			}
		}
		if (m.get("ne") > 0 && m.get("sw") > 0) {
			if (m.get("ne") >= m.get("sw")) {
				m.put("ne", m.get("ne") - m.get("sw"));
				m.put("sw", 0L);
			} else {
				m.put("sw", m.get("sw") - m.get("ne"));
				m.put("ne", 0L);
			}
		}

		// 1
		if (m.get("ne") > 0 && m.get("se") > 0 && m.get("s") > 0) {
			if (m.get("ne") >= m.get("s")) {
				m.put("se", m.get("se") + m.get("s"));
				m.put("ne", m.get("ne") - m.get("s"));
				m.put("s", 0L);
			} else {
				m.put("se", m.get("se") + m.get("ne"));
				m.put("s", m.get("s") - m.get("ne"));
				m.put("ne", 0L);
			}
		}
		// 2
		if (m.get("n") > 0 && m.get("ne") > 0 && m.get("se") > 0) {
			if (m.get("n") >= m.get("se")) {
				m.put("ne", m.get("ne") + m.get("se"));
				m.put("n", m.get("n") - m.get("se"));
				m.put("se", 0L);
			} else {
				m.put("ne", m.get("ne") + m.get("n"));
				m.put("se", m.get("se") - m.get("n"));
				m.put("n", 0L);
			}
		}
		// 3
		if (m.get("nw") > 0 && m.get("n") > 0 && m.get("ne") > 0) {
			if (m.get("nw") >= m.get("ne")) {
				m.put("n", m.get("n") + m.get("ne"));
				m.put("nw", m.get("nw") - m.get("ne"));
				m.put("ne", 0L);
			} else {
				m.put("n", m.get("n") + m.get("nw"));
				m.put("ne", m.get("ne") - m.get("nw"));
				m.put("nw", 0L);
			}
		}
		// 4
		if (m.get("sw") > 0 && m.get("nw") > 0 && m.get("n") > 0) {
			if (m.get("sw") >= m.get("n")) {
				m.put("nw", m.get("nw") + m.get("n"));
				m.put("sw", m.get("sw") - m.get("n"));
				m.put("n", 0L);
			} else {
				m.put("nw", m.get("nw") + m.get("sw"));
				m.put("n", m.get("n") - m.get("sw"));
				m.put("sw", 0L);
			}
		}
		// 5
		if (m.get("nw") > 0 && m.get("sw") > 0 && m.get("nw") > 0) {
			if (m.get("nw") >= m.get("nw")) {
				m.put("sw", m.get("sw") + m.get("nw"));
				m.put("nw", m.get("nw") - m.get("nw"));
				m.put("nw", 0L);
			} else {
				m.put("sw", m.get("sw") + m.get("nw"));
				m.put("nw", m.get("nw") - m.get("nw"));
				m.put("nw", 0L);
			}
		}
		// 6
		if (m.get("sw") > 0 && m.get("sw") > 0 && m.get("sw") > 0) {
			if (m.get("sw") >= m.get("sw")) {
				m.put("sw", m.get("sw") + m.get("sw"));
				m.put("sw", m.get("sw") - m.get("sw"));
				m.put("sw", 0L);
			} else {
				m.put("sw", m.get("sw") + m.get("sw"));
				m.put("sw", m.get("sw") - m.get("sw"));
				m.put("sw", 0L);
			}
		}
		return m;
	}

	public long s2(boolean b) {
		HashMap<Integer, Long> stepDist = new HashMap<>();
		List<String> moves = Arrays.asList(getInput(b).split(",")).stream().map(String::trim)
				.collect(Collectors.toList());
		HashMap<String, Long> hashMoves = new HashMap<>();
		hashMoves.put("n", 0L);
		hashMoves.put("s", 0L);
		hashMoves.put("ne", 0L);
		hashMoves.put("nw", 0L);
		hashMoves.put("se", 0L);
		hashMoves.put("sw", 0L);
		int step = 0;
		for (String move : moves) {
			if (move.equals("n")) {
				hashMoves.put("n", hashMoves.get("n") + 1);
			} else if (move.equals("ne")) {
				hashMoves.put("ne", hashMoves.get("ne") + 1);
			} else if (move.equals("nw")) {
				hashMoves.put("nw", hashMoves.get("nw") + 1);
			} else if (move.equals("s")) {
				hashMoves.put("s", hashMoves.get("s") + 1);
			} else if (move.equals("se")) {
				hashMoves.put("se", hashMoves.get("se") + 1);
			} else if (move.equals("sw")) {
				hashMoves.put("sw", hashMoves.get("sw") + 1);
			}
			stepDist.put(step++, compteStep(hashMoves));
		}
		return MesOutils.getMaxLongFromList(stepDist.values().stream().collect(Collectors.toList()));
	}

	public static List<Long> getDuration() {
		A2017Day11 d = new A2017Day11(1);
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

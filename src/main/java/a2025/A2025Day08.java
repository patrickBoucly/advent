package a2025;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class A2025Day08 extends A2025 {
	public A2025Day08(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2025Day08 d = new A2025Day08(8);
		// System.out.println(d.s1(false));

		long startTime = System.currentTimeMillis();
	//	System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		 System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		// System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds,
		// run 2 took "
		// + (endTime - startTime) + " milliseconds");
	}

	public Integer s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Game g = new Game();
		g.initFromInput(inputL, b);
		return g.solve1();
	}

	public Long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Game g = new Game();
		g.initFromInput(inputL, b);
		return g.solve2();
	}

	@Data
	@NoArgsConstructor
	public static class Game {
		Set<JunctionBox> jbs;
		Set<Circuit> circuits;
		Map<Pair<JunctionBox, JunctionBox>, Long> connexions;
		Long maxCon = 1000L;
		JunctionBox lastj1=null;
		JunctionBox lastj2=null;
		public Integer solve1() {
			LinkedHashMap<Pair<JunctionBox, JunctionBox>, Long> sorted = connexions.entrySet().stream()
					.sorted(Map.Entry.comparingByValue()).limit(maxCon)
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
			for (Entry<Pair<JunctionBox, JunctionBox>, Long> entry : sorted.entrySet()) {
				Circuit c1 = getCircuit(circuits, entry.getKey().getLeft());
				Circuit c2 = getCircuit(circuits, entry.getKey().getRight());
				if (!c1.jbs.contains(entry.getKey().getRight())) {
					Set<JunctionBox> njbs = new HashSet<>();
					njbs.addAll(c1.jbs);
					njbs.addAll(c2.jbs);
					Circuit c3 = new Circuit(njbs);
					circuits.remove(c1);
					circuits.remove(c2);
					circuits.add(c3);
				}
			}
			Map<Circuit, Integer> circSize = new LinkedHashMap<>();
			for (Circuit c : circuits) {
				circSize.put(c, c.jbs.size());
			}
			List<Integer> sortedValues = circSize.values().stream().distinct().sorted(Comparator.reverseOrder())
					.toList();

			return sortedValues.get(0) * sortedValues.get(1) * sortedValues.get(2);
		}

		private Circuit getCircuit(Set<Circuit> cir, JunctionBox junctionBox) {
			for (Circuit c : cir) {
				if (c.jbs.contains(junctionBox)) {
					return c;
				}
			}
			return null;
		}

		public void initFromInput(List<String> inputL, boolean b) {
			if (!b) {
				maxCon = 10L;
			}
			jbs = new HashSet<>();
			circuits = new HashSet<>();
			for (int i = 0; i < inputL.size(); i++) {
				String[] s = inputL.get(i).split(",");
				JunctionBox j = new JunctionBox(Long.parseLong(s[0]), Long.parseLong(s[1]), Long.parseLong(s[2]),
						i + 1);
				jbs.add(j);
				Set<JunctionBox> cjbs = new HashSet<>();
				cjbs.add(j);
				Circuit c = new Circuit(cjbs);
				circuits.add(c);
			}
			connexions = new HashMap<>();
			for (JunctionBox j1 : jbs) {
				for (JunctionBox j2 : jbs) {
					if (j1.id < j2.id) {

						connexions.put(Pair.of(j1, j2), distC(j1, j2));
					}
				}
			}

		}

		private Long distC(JunctionBox j1, JunctionBox j2) {
			return (j2.z - j1.z) * (j2.z - j1.z) + (j2.y - j1.y) * (j2.y - j1.y) + (j2.x - j1.x) * (j2.x - j1.x);
		}

		public long solve2() {
			LinkedHashMap<Pair<JunctionBox, JunctionBox>, Long> sorted = connexions.entrySet().stream()
					.sorted(Map.Entry.comparingByValue())
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
			while (circuits.size() > 1) {
				addOneLink(sorted);
			}
			System.out.println(lastj1);
			System.out.println(lastj2);
			System.out.println(circuits);
			return lastj1.x*lastj2.x;
		}

		private void addOneLink(LinkedHashMap<Pair<JunctionBox, JunctionBox>, Long> sorted) {
			for (Entry<Pair<JunctionBox, JunctionBox>, Long> entry : sorted.entrySet()) {
				lastj1=entry.getKey().getLeft();
				lastj2=entry.getKey().getRight();
				Circuit c1 = getCircuit(circuits, lastj1);
				Circuit c2 = getCircuit(circuits, lastj2);
				if (!c1.jbs.contains(entry.getKey().getRight())) {
					Set<JunctionBox> njbs = new HashSet<>();
					njbs.addAll(c1.jbs);
					njbs.addAll(c2.jbs);
					Circuit c3 = new Circuit(njbs);
					circuits.remove(c1);
					circuits.remove(c2);
					circuits.add(c3);
					break;
				}
				
			}
			
		}
	}

	@Data
	@AllArgsConstructor
	public static class JunctionBox {
		Long x;
		Long y;
		Long z;
		int id;
	}

	@Data
	@AllArgsConstructor
	public static class Connexion {
		Map<Pair<JunctionBox, JunctionBox>, Long> connexions;
	}

	@Data
	@AllArgsConstructor
	public static class Circuit {
		Set<JunctionBox> jbs;

	}

	public static List<Long> getDuration() {
		A2025Day08 d = new A2025Day08(8);
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
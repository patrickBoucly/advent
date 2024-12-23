package a2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import a2024.A2024Day16.Game;
import a2024.A2024Day16.Maps;
import a2024.A2024Day16.Position;
import a2024.A2024Day16.State;

public class A2024Day20 extends A2024 {

	public A2024Day20(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day20 d = new A2024Day20(20);
		System.out.println(d.s1(true));
		// too high 104512
		long startTime = System.currentTimeMillis();
		// d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		// System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	public long s1(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		Game g = getGame(input, b, (int) input.size());
		Map<Long, Long> saveSecCheatWays = new HashMap<>();
		Long refTime = g.res201();
		int cpt = 0;
		List<Position> candidat = g.map.mapPos.stream().filter(p -> p.contenu.equals("#") && g.map.ouvreUnChemin(p))
				.toList();
		System.out.println(candidat.size());
		for (Position p : candidat) {
			if (p.contenu.equals("#")) {
				cpt++;
				Game g2 = getGame(input, b, (int) input.size());
				for (Position q : g2.map.mapPos) {
					if (p.x == q.x && p.y == q.y) {
						q.contenu = ".";
					}
				}
				Long g2Time = g2.res201();
				if (saveSecCheatWays.containsKey(refTime - g2Time)) {
					saveSecCheatWays.put(refTime - g2Time, 1 + saveSecCheatWays.get(refTime - g2Time));
				} else {
					saveSecCheatWays.put(refTime - g2Time, 1L);
				}
				if(cpt%100==0) {
					System.out.println(cpt);
				}
			}
		}
		Long res = 0L;
		System.out.println(saveSecCheatWays);
		for (Entry<Long, Long> e : saveSecCheatWays.entrySet()) {
			if (b) {
				if (e.getKey() > 100) {
					res += e.getValue()*e.getKey();
				}
			}else {
				res += e.getValue()*e.getKey();
			}
		}
		return res;
	}

	public long s2(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();

		return 0l;
	}

	private Game getGame(List<String> input, boolean b, int l) {
		Set<Position> mapPos = new HashSet<>();
		Set<State> states = new HashSet<>();
		State initialState = null;
		int max = 140;
		int nbByte = l;
		if (!b) {
			max = 15;
		}
		for (int i = 0; i < max; i++) {
			for (int j = 0; j < max; j++) {
				Position p = null;
				String m = input.get(j).substring(i, i + 1);
				if (m.equals("S")) {
					p = new Position(i, j, "S");
					List<Position> it = new ArrayList<>();
					it.add(p);
					initialState = new State(p, null, 0L, it);
					states.add(initialState);
				} else {
					p = new Position(i, j, m);
				}
				mapPos.add(p);
			}

		}
		Maps map = new Maps(mapPos);
		return new Game(map, states, initialState, 0L);
	}

	public static List<Long> getDuration() {
		A2024Day20 d = new A2024Day20(20);
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

package a2025;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import outils.MesOutils;

public class A2025Day07 extends A2025 {
	public A2025Day07(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2025Day07 d = new A2025Day07(7);
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

	public Integer s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Game g = new Game();
		g.initFromInput(inputL);
		// System.out.println(g);
		return g.solve1();
	}

	public Long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Game g = new Game();
		g.initFromInput(inputL);
		return g.solve2();
	}

	@Data
	@NoArgsConstructor
	public static class Game {
		List<String> inputL;
		Point s;
		Set<Point> obstacles;
		Set<Point> tachyon;
		Map<Point, Long> tachyonsFreq;
		Set<Point> touchObstacles;
		Long timeline;

		public void initFromInput(List<String> inputL) {
			this.inputL = inputL;
			obstacles = new HashSet<>();
			tachyon = new HashSet<>();
			for (int i = 0; i < inputL.size() - 1; i++) {
				for (int j = 0; j < inputL.get(0).length() - 1; j++) {
					if (inputL.get(i).substring(j, j + 1).equals("S")) {
						s = new Point(j, i, "S");
						tachyon.add(s);
					}
					if (inputL.get(i).substring(j, j + 1).equals("^")) {
						obstacles.add(new Point(j, i, "^"));
					}

				}
			}
		}

		public Integer solve1() {
			touchObstacles = new HashSet<>();
			for (int i = 0; i < inputL.size() - 1; i++) {
				goDown();
			}
			return touchObstacles.size();
		}

		private void goDown() {
			Set<Point> nextTachyon = new HashSet<>();
			for (Point t : tachyon) {
				Point down = new Point(t.x, t.y + 1, "?");
				if (obstacles.contains(down)) {
					Point downL = new Point(t.x - 1, t.y + 1, "?");
					Point downR = new Point(t.x + 1, t.y + 1, "?");
					nextTachyon.add(downL);
					nextTachyon.add(downR);
					touchObstacles.add(down);
				} else {
					nextTachyon.add(down);
				}
			}
			tachyon = nextTachyon;

		}

		public Long solve2() {
			timeline = 0L;
			touchObstacles = new HashSet<>();
			tachyonsFreq = new HashMap<>();
			tachyonsFreq.put(s, 1L);
			for (int i = 0; i < inputL.size() - 1; i++) {
				goDown2(i);
			}
			for (Entry<Point, Long> e : tachyonsFreq.entrySet()) {
				if (e.getKey().y == inputL.size() - 2) {
					timeline += e.getValue();
				}

			}
			return timeline;
		}

		private void goDown2(Integer i) {
			for (Point t : tachyonsFreq.keySet().stream().filter(p -> p.y == i).toList()) {
				Point down = new Point(t.x, t.y + 1, "?");
				if (obstacles.contains(down)) {
					Point downL = new Point(t.x - 1, t.y + 1, "?");
					Point downR = new Point(t.x + 1, t.y + 1, "?");
					if (tachyonsFreq.containsKey(downL)) {
						tachyonsFreq.put(downL, tachyonsFreq.get(downL) + tachyonsFreq.get(t));
					} else {
						tachyonsFreq.put(downL, tachyonsFreq.get(t));
					}
					if (tachyonsFreq.containsKey(downR)) {
						tachyonsFreq.put(downR, tachyonsFreq.get(downR) + tachyonsFreq.get(t));
					} else {
						tachyonsFreq.put(downR, tachyonsFreq.get(t));
					}

					touchObstacles.add(down);
				} else {
					if (tachyonsFreq.containsKey(down)) {
						tachyonsFreq.put(down, tachyonsFreq.get(down) + tachyonsFreq.get(t));
					} else {
						tachyonsFreq.put(down, tachyonsFreq.get(t));
					}
				}
			}

		}

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			Set<Point> pts = new HashSet<>();
			pts.addAll(obstacles);
			pts.addAll(tachyon);

			int imax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getX).collect(Collectors.toList()));
			int jmax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getY).collect(Collectors.toList()));
			for (int j = 0; j <= jmax; j++) {
				for (int i = 0; i <= imax; i++) {
					Point.getPoint(pts, i, j).ifPresentOrElse(pt -> res.append(pt.contenu), () -> res.append("."));

				}
				res.append("\n");
			}
			return res.toString();
		}
	}

	@Data
	@AllArgsConstructor
	public static class Point {
		int x;
		int y;
		String contenu;

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			return x == other.x && y == other.y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		public static Optional<Point> getPoint(Set<Point> pts, int i, int j) {
			Point p = new Point(i, j, "?");
			for (Point pt : pts) {
				if (pt.equals(p)) {
					return Optional.ofNullable(pt);
				}
			}
			return Optional.empty();
		}
	}

	public static List<Long> getDuration() {
		A2025Day07 d = new A2025Day07(7);
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
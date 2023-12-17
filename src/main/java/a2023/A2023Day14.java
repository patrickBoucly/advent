package a2023;

import java.util.ArrayList;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import outils.MesOutils;

public class A2023Day14 extends A2023 {

	public A2023Day14(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day14 d = new A2023Day14(14);
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

	public Long s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		TheGame tg = getTheGame(inputL);
		tg.moveNorth();
		return tg.sum1();
	}

	private TheGame getTheGame(List<String> inputL) {
		TheGame tg = new TheGame();
		Set<Point> points = new HashSet<>();
		for (int l = 0; l < inputL.size(); l++) {
			for (int c = 0; c < inputL.get(0).length() - 1; c++) {
				Point p = new Point();
				p.setX(c);
				p.setY(l);
				p.setInfo(inputL.get(l).substring(c, c + 1));

				points.add(p);
			}
		}
		tg.setPoints(points);
		return tg;
	}

	public Long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		TheGame tg = getTheGame(inputL);
		Map<TheGame, List<Integer>> res = new HashMap<>();
		boolean cont = true;
		int i = 1;
		while (cont) {
			tg.makeNewCycle();
			TheGame tg2 = new TheGame(tg);
			if (res.containsKey(tg2)) {
				List<Integer> l = new ArrayList<>(res.get(tg2));

				l.add(i);
				res.put(tg2, l);
				if (l.size() == 2) {
					cont = false;
				}
			} else {
				res.put(tg2, Arrays.asList(i));
			}
			tg = new TheGame(tg2);
			i++;
		}
		int modulo = 1;
		int indiceDeuxVal=0;
		for (Entry<TheGame, List<Integer>> e : res.entrySet()) {
			if (e.getValue().size() > 1) {
				indiceDeuxVal=e.getValue().get(0);
				modulo = e.getValue().get(1) - e.getValue().get(0);
			}
		}
		

		for (Entry<TheGame, List<Integer>> e : res.entrySet()) {
			if (e.getValue().get(0)>indiceDeuxVal) {
				if (1000000000 % modulo == e.getValue().get(0) % modulo) {
					return e.getKey().sum1();
				}
			}
		}
		return 0L;

	}

	public static Optional<Point> getPoint(Set<Point> pts, int x, int y) {
		Point p = null;
		for (Point i : pts) {
			if (x == i.x && y == i.y) {
				return Optional.ofNullable(i);
			}
		}
		return Optional.ofNullable(p);
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class TheGame {
		Set<Point> points;

		@Override

		public String toString() {
			StringBuilder res = new StringBuilder();
			int imax = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getX).collect(Collectors.toList()));
			int jmax = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getY).collect(Collectors.toList()));
			for (int j = 0; j <= jmax; j++) {
				for (int i = 0; i <= imax; i++) {
					getPoint(points, i, j).ifPresentOrElse(pt -> res.append(pt.info), () -> res.append("."));
				}
				res.append("\n");
			}
			return res.toString();
		}

		public void makeNewCycle() {
			moveNorth();
			moveWest();
			moveSouth();
			moveEast();
		}

		public void moveEast() {
			// ctrl décroissant
			int nbcol = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getX).collect(Collectors.toList()));
			int nbLines = MesOutils
					.getMaxIntegerFromList(points.stream().map(Point::getY).collect(Collectors.toList()));

			int lastFreeIndex = nbcol;
			for (int l = 0; l <= nbLines; l++) {
				lastFreeIndex = nbcol;
				for (int c = nbcol; c >= 0; c--) {
					Optional<Point> p = getPoint(points, c, l);
					if (p.isEmpty()) {
						throw new RuntimeException();
					}
					if (p.get().info.equals("#")) {
						lastFreeIndex = p.get().x - 1;
					}
					if (p.get().info.equals(".")) {

					}
					if (p.get().info.equals("O")) {
						p.get().info = ".";
						Optional<Point> p2 = getPoint(points, lastFreeIndex, l);
						if (p2.isEmpty()) {
							throw new RuntimeException();
						}
						p2.get().info = "O";
						lastFreeIndex--;
					}
				}
			}
		}

		public void moveSouth() {
			int nbcol = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getX).collect(Collectors.toList()));
			int nbLines = MesOutils
					.getMaxIntegerFromList(points.stream().map(Point::getY).collect(Collectors.toList()));

			int lastFreeIndex = 0;
			for (int c = 0; c <= nbcol; c++) {
				lastFreeIndex = nbLines;
				for (int l = nbLines; l >= 0; l--) {
					Optional<Point> p = getPoint(points, c, l);
					if (p.isEmpty()) {
						throw new RuntimeException();
					}
					if (p.get().info.equals("#")) {
						lastFreeIndex = p.get().y - 1;
					}
					if (p.get().info.equals(".")) {

					}
					if (p.get().info.equals("O")) {
						p.get().info = ".";
						Optional<Point> p2 = getPoint(points, c, lastFreeIndex);
						if (p2.isEmpty()) {
							throw new RuntimeException();
						}
						p2.get().info = "O";
						lastFreeIndex--;
					}
				}
			}
		}

		public void moveWest() {
			// ctrl croissant
			int nbcol = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getX).collect(Collectors.toList()));
			int nbLines = MesOutils
					.getMaxIntegerFromList(points.stream().map(Point::getY).collect(Collectors.toList()));

			int lastFreeIndex = 0;
			for (int l = 0; l <= nbLines; l++) {
				lastFreeIndex = 0;
				for (int c = 0; c <= nbcol; c++) {
					Optional<Point> p = getPoint(points, c, l);
					if (p.isEmpty()) {
						throw new RuntimeException();
					}
					if (p.get().info.equals("#")) {
						lastFreeIndex = p.get().x + 1;
					}
					if (p.get().info.equals(".")) {

					}
					if (p.get().info.equals("O")) {
						p.get().info = ".";
						Optional<Point> p2 = getPoint(points, lastFreeIndex, l);
						if (p2.isEmpty()) {
							throw new RuntimeException();
						}
						p2.get().info = "O";
						lastFreeIndex++;
					}
				}
			}

		}

		public Long sum1() {
			Long sum = 0L;
			int nbLines = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getY).toList());
			for (Point p : points) {
				if (p.info.equals("O")) {
					sum += nbLines + 1 - p.y;
				}
			}
			return sum;
		}

		public void moveNorth() {
			int nbcol = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getX).collect(Collectors.toList()));
			int nbLines = MesOutils
					.getMaxIntegerFromList(points.stream().map(Point::getY).collect(Collectors.toList()));

			int lastFreeIndex = 0;
			for (int c = 0; c <= nbcol; c++) {
				lastFreeIndex = 0;
				for (int l = 0; l <= nbLines; l++) {
					Optional<Point> p = getPoint(points, c, l);
					if (p.isEmpty()) {
						throw new RuntimeException();
					}
					if (p.get().info.equals("#")) {
						lastFreeIndex = p.get().y + 1;
					}
					if (p.get().info.equals(".")) {

					}
					if (p.get().info.equals("O")) {
						p.get().info = ".";
						Optional<Point> p2 = getPoint(points, c, lastFreeIndex);
						if (p2.isEmpty()) {
							throw new RuntimeException();
						}
						p2.get().info = "O";
						lastFreeIndex++;
					}
				}
			}
		}

		@Override
		public int hashCode() {
			return Objects.hash(points);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TheGame other = (TheGame) obj;
			return Objects.equals(points, other.points);
		}

		public TheGame(TheGame tg2) {
			Set<Point> np = new HashSet<A2023Day14.Point>();
			for (Point p : tg2.points) {
				np.add(new Point(p.x, p.y, p.info));
			}
			setPoints(np);
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	private static class Point {
		int x;
		int y;
		String info;

		@Override
		public int hashCode() {
			return Objects.hash(info, x, y);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			return Objects.equals(info, other.info) && x == other.x && y == other.y;
		}

	}

	public static List<Long> getDuration() {
		A2023Day14 d = new A2023Day14(1);
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

package a2024;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import outils.MesOutils;

public class A2024Day4 extends A2024 {

	public A2024Day4(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day4 d = new A2024Day4(4);
		//System.out.println(d.s1(true));
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
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim()).toList();
		Set<Point> pts = getPoints(input);
		return cptXmas1(pts);
	}

	private long cptXmas1(Set<Point> pts) {
		int res = cptXmas1H(pts) + cptXmas1V(pts) + cptXmasD(pts);
		return res;
	}

	private int cptXmasD(Set<Point> pts) {
		int res = 0;
		int imax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getX).toList());
		int jmax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getY).toList());
		for (int i = 0; i <= imax; i++) {
			for (int j = 0; j <= jmax; j++) {
				if (getPoint(pts, i + 3, j + 3).isPresent()) {
					if (getPoint(pts, i, j).get().letter.equals("X")
							&& getPoint(pts, i + 1, j + 1).get().letter.equals("M")
							&& getPoint(pts, i + 2, j + 2).get().letter.equals("A")
							&& getPoint(pts, i + 3, j + 3).get().letter.equals("S")) {
						// System.out.println("partie i + 3, j + 3 " + i + "," + j);
						res++;
					}
				}
				if (getPoint(pts, i + 3, j - 3).isPresent()) {
					if (getPoint(pts, i, j).get().letter.equals("X")
							&& getPoint(pts, i + 1, j - 1).get().letter.equals("M")
							&& getPoint(pts, i + 2, j - 2).get().letter.equals("A")
							&& getPoint(pts, i + 3, j - 3).get().letter.equals("S")) {
						res++;
						// System.out.println("partie i + 3, j - 3 " + i + "," + j);
					}
				}
				if (getPoint(pts, i - 3, j + 3).isPresent()) {
					if (getPoint(pts, i, j).get().letter.equals("X")
							&& getPoint(pts, i - 1, j + 1).get().letter.equals("M")
							&& getPoint(pts, i - 2, j + 2).get().letter.equals("A")
							&& getPoint(pts, i - 3, j + 3).get().letter.equals("S")) {
						res++;
						// System.out.println("partie i - 3, j + 3 " + i + "," + j);
					}
				}
				if (getPoint(pts, i - 3, j - 3).isPresent()) {
					if (getPoint(pts, i, j).get().letter.equals("X")
							&& getPoint(pts, i - 1, j - 1).get().letter.equals("M")
							&& getPoint(pts, i - 2, j - 2).get().letter.equals("A")
							&& getPoint(pts, i - 3, j - 3).get().letter.equals("S")) {
						res++;
						// System.out.println("partie i - 3, j - 3 " + i + "," + j);
					}
				}
			}
		}
		return res;
	}

	private int cptXmas1V(Set<Point> pts) {
		int res = 0;
		int imax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getX).toList());
		int jmax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getY).toList());
		for (int i = 0; i <= imax; i++) {
			int j = 0;
			while (j < jmax) {
				if (getPoint(pts, i, j).isPresent() && getPoint(pts, i, j + 3).isPresent()) {
					if (getPoint(pts, i, j).get().letter.equals("X") && getPoint(pts, i, j + 1).get().letter.equals("M")
							&& getPoint(pts, i, j + 2).get().letter.equals("A")
							&& getPoint(pts, i, j + 3).get().letter.equals("S")) {
						res++;

						j = j + 3;
					}
				}
				j = j + 1;
			}
		}
		for (int i = 0; i <= imax; i++) {
			int j = 0;
			while (j < jmax) {
				if (getPoint(pts, i, j).isPresent() && getPoint(pts, i, j + 3).isPresent()) {
					if (getPoint(pts, i, j).get().letter.equals("S") && getPoint(pts, i, j + 1).get().letter.equals("A")
							&& getPoint(pts, i, j + 2).get().letter.equals("M")
							&& getPoint(pts, i, j + 3).get().letter.equals("X")) {
						res++;
						j = j + 3;

					}
				}
				j = j + 1;
			}
		}
		System.out.println("V :" + res);
		return res;
	}

	private int cptXmas1H(Set<Point> pts) {
		int res = 0;
		int imax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getX).toList());
		int jmax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getY).toList());
		for (int j = 0; j <= jmax; j++) {
			int i = 0;
			while (i < imax) {
				if (getPoint(pts, i, j).isPresent() && getPoint(pts, i + 3, j).isPresent()) {
					if (getPoint(pts, i, j).get().letter.equals("X") && getPoint(pts, i + 1, j).get().letter.equals("M")
							&& getPoint(pts, i + 2, j).get().letter.equals("A")
							&& getPoint(pts, i + 3, j).get().letter.equals("S")) {
						res++;
						i = i + 3;
					}
				}
				i = i + 1;
			}
		}
		for (int j = 0; j <= jmax; j++) {
			int i = 0;
			while (i < imax) {
				if (getPoint(pts, i, j).isPresent() && getPoint(pts, i + 3, j).isPresent()) {
					if (getPoint(pts, i, j).get().letter.equals("S") && getPoint(pts, i + 1, j).get().letter.equals("A")
							&& getPoint(pts, i + 2, j).get().letter.equals("M")
							&& getPoint(pts, i + 3, j).get().letter.equals("X")) {
						res++;

						i = i + 3;
					}
				}
				i = i + 1;
			}
		}
		System.out.println("H :" + res);
		return res;
	}

	public int s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim()).toList();
		Set<Point> pts = getPoints(input);
		return cptMAS(pts);

	}

	private int cptMAS(Set<Point> pts) {
		int res = 0;
		int imax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getX).toList());
		int jmax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getY).toList());
		for (int i = 0; i <= imax; i++) {
			for (int j = 0; j <= jmax; j++) {
				if (getPoint(pts, i, j).get().letter.equals("A")) {
					if (getPoint(pts, i + 1, j + 1).isPresent() && getPoint(pts, i - 1, j + 1).isPresent()
							&& getPoint(pts, i + 1, j - 1).isPresent() && getPoint(pts, i - 1, j - 1).isPresent()) {

						if (getPoint(pts, i + 1, j + 1).get().letter.equals("M")
								&& getPoint(pts, i - 1, j - 1).get().letter.equals("S")
								&& getPoint(pts, i + 1, j - 1).get().letter.equals("M")
								&& getPoint(pts, i - 1, j + 1).get().letter.equals("S")) {
							res++;
						}
						if (getPoint(pts, i + 1, j + 1).get().letter.equals("S")
								&& getPoint(pts, i - 1, j - 1).get().letter.equals("M")
								&& getPoint(pts, i + 1, j - 1).get().letter.equals("S")
								&& getPoint(pts, i - 1, j + 1).get().letter.equals("M")) {
							res++;
						}
						if (getPoint(pts, i + 1, j + 1).get().letter.equals("M")
								&& getPoint(pts, i - 1, j - 1).get().letter.equals("S")
								&& getPoint(pts, i + 1, j - 1).get().letter.equals("S")
								&& getPoint(pts, i - 1, j + 1).get().letter.equals("M")) {
							res++;
						}
						if (getPoint(pts, i + 1, j + 1).get().letter.equals("S")
								&& getPoint(pts, i - 1, j - 1).get().letter.equals("M")
								&& getPoint(pts, i + 1, j - 1).get().letter.equals("M")
								&& getPoint(pts, i - 1, j + 1).get().letter.equals("S")) {
							res++;
						}
					}
				}
			}
		}
		return res;
	}

	private Set<Point> getPoints(List<String> input) {
		Set<Point> pts = new HashSet<>();
		int j = 0;
		for (String s : input) {
			for (int i = 0; i < s.length(); i++) {
				pts.add(new Point(i, j, s.substring(i, i + 1)));
			}
			j++;
		}
		return pts;
	}

	@Data
	@AllArgsConstructor
	private static class Point {
		int x;
		int y;
		String letter;

		public static Optional<Point> getPoint(Set<Point> pts, int x, int y) {
			Point p = null;
			for (Point i : pts) {
				if (x == i.x && y == i.y) {
					p = new Point(x, y, i.letter);
				}
			}
			return Optional.ofNullable(p);
		}

	}

	public static Optional<Point> getPoint(Set<Point> pts, int x, int y) {
		Point p = null;
		for (Point i : pts) {
			if (x == i.x && y == i.y) {
				p = new Point(x, y, i.letter);
			}
		}
		return Optional.ofNullable(p);
	}

	public static List<Long> getDuration() {
		A2024Day4 d = new A2024Day4(4);
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

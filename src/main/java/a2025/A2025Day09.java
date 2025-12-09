package a2025;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import outils.MesOutils;

public class A2025Day09 extends A2025 {
	public A2025Day09(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2025Day09 d = new A2025Day09(9);
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

	public Long s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Game g = new Game(getPointsFromInput(inputL));
		return g.solve1();
	}

	public Long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Game g = new Game(getPointsFromInput(inputL));
		return g.solve2();
	}

	private Set<Point> getPointsFromInput(List<String> inputL) {
		Set<Point> points = new HashSet<>();
		int j = 0;
		for (String l : inputL) {
			String[] s = l.split(",");
			points.add(new Point(Long.parseLong(s[1]), Long.parseLong(s[0]), "#"));
		}
		return points;
	}

	@Data
	@AllArgsConstructor
	public static class Game {
		Set<Point> pts;

		private long manat(Point p1, Point p2) {
			return (Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y));

		}

		public Long solve1() {
			Long maxManat = 0L;
			Point m1 = null;
			Point m2 = null;
			for (Point p1 : pts) {
				for (Point p2 : pts) {
					Long d = manat(p1, p2);
					if (d > maxManat) {
						maxManat = d;
						m1 = p1;
						m2 = p2;
					}
				}
			}
			return aire(m1, m2);
		}

		private Long aire(Point m1, Point m2) {
			return (Math.abs(m1.y - m2.y) + 1) * (Math.abs(m1.x - m2.x) + 1);
		}

		public Long solve2() {
			afficher();
			Long maxManat = 0L;
			Point m1 = null;
			Point m2 = null;
			for (Point p1 : pts) {
				for (Point p2 : pts) {
					if (coupleAcceptable(p1, p2)) {
						Long d = manat(p1, p2);
						if (d > maxManat) {
							maxManat = d;
							m1 = p1;
							m2 = p2;
						}
					}
				}
			}
			return aire(m1, m2);
		}

		private boolean coupleAcceptable(Point p1, Point p2) {
			boolean a = true;
			for (Point p : pts) {
				if (!p.equals(p1) && !p.equals(p2)) {

				}
			}
			return a;
		}

		public String afficher() {

			StringBuilder res = new StringBuilder();
			long imax = MesOutils.getMaxLongFromList(pts.stream().map(Point::getY).toList());
			long imin = MesOutils.getMinLongFromList(pts.stream().map(Point::getY).toList());
			long jmax = MesOutils.getMaxLongFromList(pts.stream().map(Point::getX).toList());
			long jmin = MesOutils.getMinLongFromList(pts.stream().map(Point::getX).toList());
			for (long j = jmin; j <= jmax; j++) {
				for (long i = imin; i <= imax; i++) {
					Point.getPoint(pts, j, i).ifPresentOrElse(pt -> res.append("#"), () -> res.append("."));

				}
				res.append("\n");
			}
			System.out.println(res);
			return res.toString();
		}

	}

	@Data
	@AllArgsConstructor
	public static class Point {
		Long x;
		Long y;
		String cont;
		public static Optional<Point> getPoint(Set<Point> pts, Long i, Long j) {
			Point p = new Point(i, j, "?");
			for (Point pt : pts) {
				if (pt.equals(p)) {
					return Optional.ofNullable(pt);
				}
			}
			return Optional.empty();
		}

		public static Optional<Point> getPoint(Set<Point> pts, int i, int j) {
			Point p = new Point(Long.parseLong(String.valueOf(i)), Long.parseLong(String.valueOf(j)), "?");
			for (Point pt : pts) {
				if (pt.equals(p)) {
					return Optional.ofNullable(pt);
				}
			}
			return Optional.empty();
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
			return Objects.equals(x, other.x) && Objects.equals(y, other.y);
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
		

	}

	public static List<Long> getDuration() {
		A2025Day09 d = new A2025Day09(9);
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
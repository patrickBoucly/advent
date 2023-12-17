package a2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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

public class A2023Day11 extends A2023 {

	public A2023Day11(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day11 d = new A2023Day11(11);
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

	public Long s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		Universe univ = getUniverseFromInput(inputL);
		univ.expanse(1L);
		return univ.sumManathan();
	}

	private Universe getUniverseFromInput(List<String> inputL) {
		Set<Point> points = new HashSet<>();
		for (Long j = 0L; j < inputL.size(); j++) {
			for (Long i = 0L; i < inputL.get(0).length() - 1; i++) {
				Point p = new Point();
				p.setInfo(inputL.get(j.intValue()).substring(i.intValue(), i.intValue() + 1));
				p.setX(i);
				p.setY(j);
				p.setXExp(i);
				p.setYExp(j);
				points.add(p);
			}
		}
		Universe u = new Universe();
		u.setPoints(points);
		return u;
	}

	public Long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		Universe univ = getUniverseFromInput(inputL);
		Long speed=1000000L;
		if(!b) {
		//	speed=100L;
		}
		univ.expanse(speed-1);
		
		return univ.sumManathan();

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	private static class Universe {
		Set<Point> points;

		public Long sumManathan() {
			Long sumManathan = 0L;
			List<Point> galaxies = points.stream().filter(p -> p.info.equals("#")).toList();
			for (Point p1 : galaxies) {
				for (Point p2 : galaxies) {
					if (!p1.equals(p2)) {
						sumManathan += manDist(p1, p2);
					}

				}
			}
			return sumManathan / 2;
		}

		public void expanse(Long speed) {
			Set<Point> expansePoint = new HashSet<>();
			Long jmax = MesOutils.getMaxLongFromList(points.stream().map(p -> p.getX()).toList());
			Long imax = MesOutils.getMaxLongFromList(points.stream().map(p -> p.getY()).toList());
			List<Integer> ligneVide = new ArrayList<>();
			List<Integer> colVide = new ArrayList<>();
			for (int j = 0; j < jmax; j++) {
				if (pasDeGalaxieACetteLigne(j)) {
					ligneVide.add(j);
				}
			}
			for (int i = 0; i < imax; i++) {
				if (pasDeGalaxieACetteColonne(i)) {
					colVide.add(i);
				}
			}
			for (Point p : points.stream().filter(q -> q.info.equals("#")).toList()) {
				Point p2 = new Point();
				p2.setInfo("#");
				p2.setX(p.getX() + speed * colVide.stream().filter(e -> e < p.getX()).toList().size());
				p2.setY(p.getY() + speed * ligneVide.stream().filter(e -> e < p.getY()).toList().size());
				expansePoint.add(p2);
			}
			setPoints(expansePoint);

		}

		private List<Point> recupererPointDeLAColonne(int i) {
			return points.stream().filter(p -> p.x == i).toList();
		}

		private boolean pasDeGalaxieACetteColonne(int i) {
			return points.stream().filter(p -> p.x == i && p.info.equals("#")).count() == 0L;
		}

		private List<Point> recupererPointDeLALigne(int j) {
			return points.stream().filter(p -> p.y == j).toList();
		}

		private boolean pasDeGalaxieACetteLigne(int j) {
			return points.stream().filter(p -> p.y == j && p.info.equals("#")).count() == 0L;
		}

		private Long manDist(Point p1, Point p2) {
			return (Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y));
		}

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			Long imax = MesOutils.getMaxLongFromList(points.stream().map(Point::getX).collect(Collectors.toList()));
			Long jmax = MesOutils.getMaxLongFromList(points.stream().map(Point::getY).collect(Collectors.toList()));
			for (int j = 0; j <= jmax; j++) {
				for (int i = 0; i <= imax; i++) {
					getPoint(points, i, j).ifPresentOrElse(pt -> res.append(pt.info), () -> res.append("."));

				}
				res.append("\n");
			}
			return res.toString();
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

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	private static class Point {
		Long x;
		Long y;
		Long xExp;
		Long yExp;
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
		A2023Day11 d = new A2023Day11(1);
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

package aocmaven.a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class A2021Day9 extends A2021 {

	public A2021Day9(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day9 d = new A2021Day9(9);
		System.out.println(d.s1(true));
		System.out.println(d.s2(true));
		}

	public int s2(boolean c) {
		List<Point> points = getPoints(c);
		setDanger(points);
		List<Point> mini = points.stream().filter(p -> p.danger != -1).collect(Collectors.toList());
		List<Integer> bassinSize = new ArrayList<>();
		for (Point p : mini) {
			List<Point> bassin = getBassin(points, p);
			bassinSize.add(bassin.size());
		}
		bassinSize = bassinSize.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
		return (bassinSize.get(0) * bassinSize.get(1) * bassinSize.get(2));
	}

	private List<Point> getBassin(List<Point> points, Point p) {
		List<Point> bassin = new ArrayList<>();
		bassin.add(p);
		List<Point> newBassin = new ArrayList<>(bassin);
		boolean start = true;
		while (start || bassin.size() < newBassin.size()) {
			bassin = new ArrayList<>(newBassin);
			start = false;
			for (Point b : bassin) {
				List<Point> adj = getAdj(points, b);
				for (Point a : adj) {
					if (a.alt > b.alt && a.alt < 9 && !newBassin.contains(a)) {
						newBassin.add(a);
					}
				}
			}

		}
		return newBassin;
	}

	public int s1(boolean c) {
		List<Point> points = getPoints(c);
		setDanger(points);

		int res = 0;
		for (Point p : points) {
			if (p.danger != -1) {
				res += p.danger;
			}
		}
		return (res);
	}

	public List<Point> getAdj(List<Point> points, Point p) {
		List<Point> adj = new ArrayList<>();
		Point.getPoint(points, p.x - 1, p.y).ifPresent(adj::add);
		Point.getPoint(points, p.x, p.y - 1).ifPresent(adj::add);
		Point.getPoint(points, p.x + 1, p.y).ifPresent(adj::add);
		Point.getPoint(points, p.x, p.y + 1).ifPresent(adj::add);
		return adj;
	}

	public boolean minLocal(List<Point> adj, Point p) {
		return adj.stream().allMatch(x -> x.alt > p.alt);
	}

	private List<Point> setDanger(List<Point> points) {
		return points.stream().filter(x -> minLocal(getAdj(points, x), x)).map(Point::dangereux)
				.collect(Collectors.toList());
	}

	private List<Point> getPoints(boolean c) {
		List<String> lignes = Arrays.asList(getInput(c).split("\n")).stream().map(String::trim).collect(Collectors.toList());
		List<Point> points = new ArrayList<>();
		int imax = lignes.get(0).length();
		int id = 0;
		int jmax = lignes.size();
		for (int j = 0; j < jmax; j++) {
			for (int i = 0; i < imax; i++) {
				points.add(new Point(id, i, j, Integer.parseInt(lignes.get(j).substring(i, i + 1))));
				id++;
			}
		}

		return points;
	}

	public static class Point {
		int id;
		int x;
		int y;
		int alt;
		int danger;

		@Override
		public int hashCode() {
			return Objects.hash(id);
		}

		public Point dangereux() {
			setDanger(getAlt() + 1);
			return this;
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
			return id == other.id;
		}

		public static Optional<Point> getPoint(List<Point> points, int abs, int ord) {
			Point p = null;
			for (Point i : points) {
				if (abs == i.x && ord == i.y) {
					p = new Point(i.id, abs, ord, i.alt);
					if (i.danger != -1) {
						p.setDanger(i.danger);
						return Optional.ofNullable(p);
					}
				}
			}
			return Optional.ofNullable(p);
		}

		public Point(int id, int x, int y, int alt) {
			super();
			this.id = id;
			this.x = x;
			this.y = y;
			this.alt = alt;
			this.danger = -1;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getAlt() {
			return alt;
		}

		public void setAlt(int alt) {
			this.alt = alt;
		}

		public int getDanger() {
			return danger;
		}

		public void setDanger(int danger) {
			this.danger = danger;
		}

		@Override
		public String toString() {
			return "Point [id=" + id + ", x=" + x + ", y=" + y + ", alt=" + alt + ", danger=" + danger + "]";
		}
	}
	public static List<Long> getDuration() {
		A2021Day9 d = new A2021Day9(9);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1,endTime - startTime);
	}

}

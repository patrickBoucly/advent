package a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import outils.MesOutils;
import outils.UniformCostSearch;

public class A2021Day15 extends A2021 {

	public A2021Day15(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day15 d = new A2021Day15(15);
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

	private int s1(boolean b) {
		return s(b, 1);
	}

	private int s2(boolean b) {
		return s(b, 5);
	}

	private int s(boolean b, int rep) {
		UniformCostSearch.Graph graph = new UniformCostSearch.Graph();
		List<Point> p1 = getPoints(true);
		List<Point> points = getAllPoints(p1, rep);
		points.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
		for (int i = 0; i < points.size(); i++) {
			graph.addNode(i);
		}
		int imax = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getX).collect(Collectors.toList()));
		points.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
		for (int j = 0; j <= imax; j++) {
			for (int i = 0; i < imax; i++) {
				Point mpt = points.get(i+1+j*(imax+1));// Point.getPoint(i + 1, j, points);
				graph.addEdge((imax + 1) * j + i, (imax + 1) * j + i + 1, mpt.getValue());
				mpt = points.get(i+j*(imax+1)); //Point.getPoint(i, j, points);
				graph.addEdge((imax + 1) * j + i + 1, (imax + 1) * j + i, mpt.getValue());
			}
		}
		//points.sort(Comparator.comparing(Point::getX).thenComparing(Comparator.comparing(Point::getY)));
		for (int i = 0; i <= imax; i++) {
			for (int j = 0; j < imax; j++) {
				Point mpt =  points.get(i+(j+1)*(imax+1))   ;//Point.getPoint(i, j + 1, points);
				graph.addEdge((imax + 1) * j + i, (imax + 1) * (j + 1) + i, mpt.getValue());
				mpt =points.get(i+(j)*(imax+1))   ;// Point.getPoint(i, j, points);
				graph.addEdge((imax + 1) * (j + 1) + i, (imax + 1) * j + i, mpt.getValue());
			}
		}
		return graph.uniformSearch(0, points.size() - 1);

	}

	public List<Point> getAllPoints(List<Point> p1, int rep) {
		int size = MesOutils.getMaxIntegerFromList(p1.stream().map(Point::getX).collect(Collectors.toList())) + 1;
		List<Point> allPoints = new ArrayList<>();
		for (int i = 0; i < rep; i++) {
			for (int j = 0; j < rep; j++) {
				for (Point p : p1) {
					Point np = new Point(p.x + i * size, p.y + j * size, newRisk(i, j, p.value));
					allPoints.add(np);
				}
			}
		}
		return allPoints;
	}

	public int newRisk(int i, int j, int value) {
		int res = value;
		int dec = i + j;
		if (dec == 0) {
			return res;
		}
		for (int k = 1; k <= dec; k++) {
			if (res == 9) {
				res = 1;
			} else {
				res++;
			}
		}
		return res;
	}

	public List<Point> getPoints(boolean c) {
		List<Point> points = new ArrayList<>();
		List<String> lignes = Arrays.asList(getInput(c).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		for (int i = 0; i < lignes.size(); i++) {
			for (int j = 0; j < lignes.size(); j++) {
				points.add(new Point(i, j, Integer.parseInt(lignes.get(j).substring(i, i + 1))));
			}
		}
		return points;
	}

	public static class Point {
		int x;
		int y;
		int value;

		@Override
		public String toString() {
			return "[" + value + "]";
		}

		public Point(int x, int y, int value) {
			super();
			this.x = x;
			this.y = y;
			this.value = value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
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
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
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

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public static Point getPoint(int i, int j, List<Point> pts) {
			Point p = new Point(i, j, 0);
			for (Point pt : pts) {
				if (pt.equals(p)) {
					return pt;
				}
			}
			return null;
		}
	}

	public static List<Long> getDuration() {
		A2021Day15 d = new A2021Day15(15);
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

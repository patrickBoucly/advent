package a2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import outils.MesOutils;
import outils.UniformCostSearch;
import outils.UniformCostSearch.Graph;

public class A2022Day12 extends A2022 {

	public A2022Day12(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day12 d = new A2022Day12(12);
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

	public int s2(boolean b) {
		UniformCostSearch.Graph graph = new UniformCostSearch.Graph();
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		int min=31;
		if(b) {
			min=339;
		}
		int imax = input.get(0).length();
		int jmax = input.size();
		List<Point> points = new ArrayList<>();
		for (int i = 0; i < imax; i++) {
			for (int j = 0; j < jmax; j++) {
				points.add(new Point(i, j, input.get(j).substring(i, i + 1)));
			}
		}
		points.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
		List<Integer> startAndStop = init(graph, b, points,jmax,imax);
		for(Point pt:points) {
			if(pt.altS.equals("a")) {
				int dist= graph.uniformSearch(Point.getRang(pt, points), startAndStop.get(1));
				if(dist<min) {
					min=dist;
				}
			}
			
		}
		return min;
	}

	public int s1(boolean b) {
		UniformCostSearch.Graph graph = new UniformCostSearch.Graph();
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		int imax = input.get(0).length();
		int jmax = input.size();
		List<Point> points = new ArrayList<>();
		for (int i = 0; i < imax; i++) {
			for (int j = 0; j < jmax; j++) {
				points.add(new Point(i, j, input.get(j).substring(i, i + 1)));
			}
		}
		points.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
		List<Integer> startAndStop = init(graph, b, points,jmax,imax);
		return graph.uniformSearch(startAndStop.get(0), startAndStop.get(1));
	}

	private List<Integer> init(Graph graph, boolean b, List<Point> points, int jmax, int imax) {
		
		List<Integer> startAndEnd = new ArrayList<>();
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).altS.equals("S")) {
				startAndEnd.add(-i);

			}
			if (points.get(i).altS.equals("E")) {
				startAndEnd.add(i);
			}
			graph.addNode(i);
		}
		startAndEnd.sort(null);
		for (int j = 0; j < jmax - 1; j++) {
			for (int i = 0; i < imax - 1; i++) {
				Point mpt = Point.getPoint(i, j, points);
				Point mptd = Point.getPoint(i + 1, j, points);
				Point mptb = Point.getPoint(i, j + 1, points);
				if (!mpt.altS.equals("Z") && !mptd.altS.equals("Z")) {
					if (mptd.getValue() - mpt.getValue() < 2) {
						
						graph.addEdge(Point.getRang(mpt, points), Point.getRang(mptd, points), 1);
					}
					if (mpt.getValue() - mptd.getValue() < 2) {
						
						graph.addEdge(Point.getRang(mptd, points), Point.getRang(mpt, points), 1);
					}
				}
				if (!mpt.altS.equals("Z") && !mptb.altS.equals("Z")) {
					if (mptb.getValue() - mpt.getValue() < 2) {
						
						graph.addEdge(Point.getRang(mpt, points), Point.getRang(mptb, points), 1);
					}
					if (mpt.getValue() - mptb.getValue() < 2) {
						
						graph.addEdge(Point.getRang(mptb, points), Point.getRang(mpt, points), 1);
					}
				}

			}
		}
		return List.of(Math.abs(startAndEnd.get(0)), startAndEnd.get(1));
	}

	public static class Point {
		int x;
		int y;
		String altS;

		

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + ", altS=" + altS + "]";
		}

		public Point(int x, int y, String altS) {
			super();
			this.x = x;
			this.y = y;
			this.altS = altS;
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

		public Integer getValue() {
			if ("Z".equals(altS)) {
				return 2000;
			}
			if ("S".equals(altS)) {
				return 1;
			}
			if ("E".equals(altS)) {
				return 26;
			}
			
			String ordre = "SabcdefghijklmnopqrstuvwxyzE";
			for (int i = 0; i < ordre.length(); i++) {

				if (ordre.substring(i, i + 1).equals(altS)) {
					return i;
				}

			}
			return -100000000;
		}

		public static Point getPoint(int i, int j, List<Point> pts) {
			Point p = new Point(i, j, "");
			for (Point pt : pts) {
				if (pt.equals(p)) {
					return pt;
				}
			}
			return null;
		}

		public static Point getPoint(String lettre, List<Point> pts) {
			for (Point pt : pts) {
				if (pt.altS.equals(lettre)) {
					return pt;
				}
			}
			return null;
		}

		public static int getRang(Point p, List<Point> pts) {
			int imax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getX).collect(Collectors.toList()));
			return p.x + p.y * (imax + 1);
		}
	}

	public static List<Long> getDuration() {
		A2022Day12 d = new A2022Day12(12);
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

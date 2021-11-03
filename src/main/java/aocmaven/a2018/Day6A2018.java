package aocmaven.a2018;

import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Data;
import lombok.Value;

public class Day6A2018 {

	public final static Integer DISTMAX = 10000;

	public static void main(String[] args) {
		// s1();
		s2();

	}

	private static void s2() {
		String message = getMessage(
				"C:\\Users\\z11r88\\eclipse-workspace2\\advent_of_code\\src\\advent_of_code\\main\\resources\\a2018\\input6");
		List<Point> region = new ArrayList<>();
		List<Point> points = new ArrayList<>();
		int id = 1;
		int xMax = -1;
		int yMax = -1;
		int xMin = 1000;
		int yMin = 1000;
		for (String s : message.split("\n")) {
			String[] s1 = s.split(",");
			int x = Integer.parseInt(s1[0].trim());
			int y = Integer.parseInt(s1[1].trim());
			Point p = new Point(id, x, y);
			points.add(p);
			if (x > xMax) {
				xMax = x;
			}
			if (y > yMax) {
				yMax = y;
			}
			if (x < xMin) {
				xMin = x;
			}
			if (y < yMin) {
				yMin = y;
			}
			id++;
		}
		List<Point> tableau = new ArrayList<>();
		for (int i = xMin; i < xMax; i++) {
			for (int j = yMin; j < yMax; j++) {
				tableau.add(new Point(0, i, j));
			}
		}

		for (Point p : tableau) {
			Integer distP = 0;
			boolean fini = false;
			while (!fini) {
				for (int i = 0; i < points.size(); i++) {
					if (!fini) {
						distP += distance(p.x, p.y, points.get(i));
						if (distP > DISTMAX) {
							fini = true;
							break;
						}
						if(i==points.size()-1) {
							fini=true;
						}
					}
				}

			}
			if (distP < DISTMAX) {
				region.add(p);
			}
		}
		System.out.println(region.size());

	}

	private static void s1() {
		String message = getMessage(
				"C:\\Users\\z11r88\\eclipse-workspace2\\advent_of_code\\src\\advent_of_code\\main\\resources\\a2018\\input6");
		List<Point> points = new ArrayList<>();
		int id = 1;
		int xMax = -1;
		int yMax = -1;
		int xMin = 1000;
		int yMin = 1000;
		for (String s : message.split("\n")) {
			String[] s1 = s.split(",");
			int x = Integer.parseInt(s1[0].trim());
			int y = Integer.parseInt(s1[1].trim());
			Point p = new Point(id, x, y);
			System.out.println(p);
			points.add(p);
			if (x > xMax) {
				xMax = x;
			}
			if (y > yMax) {
				yMax = y;
			}
			if (x < xMin) {
				xMin = x;
			}
			if (y < yMin) {
				yMin = y;
			}
			id++;
		}
		List<Point> tableau = new ArrayList<>();
		for (int i = xMin - 3000; i < xMax + 3000; i++) {
			for (int j = yMin - 3000; j < yMax + 3000; j++) {
				tableau.add(new Point(affecter(i, j, points), i, j));

			}
		}
		HashMap<Integer, Integer> comptages = new HashMap<>();
		for (Point p : tableau) {
			if (comptages.containsKey(p.id)) {
				comptages.put(p.id, comptages.get(p.id) + 1);
			} else {
				comptages.put(p.id, 1);
			}
		}
		int maxSize = 0;
		for (Integer i : comptages.keySet()) {
			System.out.println(i + " : " + comptages.get(i));
			if (comptages.get(i) > maxSize) {
				maxSize = comptages.get(i);
			}

		}
		// imprimer(tableau,xMin,xMax,yMin,yMax);
		System.out.println(maxSize);
	}

	private static void imprimer(List<Point> tableau, int xMin, int xMax, int yMin, int yMax) {
		StringBuilder ligne = new StringBuilder();
		for (int j = yMin - 10; j < yMax + 10; j++) {
			ligne = new StringBuilder();
			for (int i = xMin - 10; i < xMax + 10; i++) {
				for (Point p : tableau) {
					if (p.x == i && p.y == j) {
						ligne.append(+p.id + " ");
					}
				}

			}
			System.out.println(ligne);
			System.out.println("     ");
		}

	}

	private static int affecter(int i, int j, List<Point> points) {
		int dist = 1000000;
		int cetteDist = 1000000;
		List<Integer> distances = new ArrayList<>();
		int idRes = -1;
		for (Point p : points) {
			cetteDist = distance(i, j, p);
			if (cetteDist <= dist) {
				dist = cetteDist;
				distances.add(dist);
				idRes = p.id;
			}
		}
		int nbMin = 0;
		for (Integer d : distances) {
			if (d == dist) {
				nbMin++;
			}
		}
		if (nbMin > 1) {
			idRes = 0;
		}
		return idRes;
	}

	private static int distance(int i, int j, Point p) {
		int res = Math.abs(p.x - i) + Math.abs(p.y - j);
		return res;
	}

	@Data
	@Value
	public static class Point {
		int id;
		int x;
		int y;

		public Point(int id, int x, int y) {
			super();
			this.id = id;
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Point [id= " + id + ", x=" + x + ", y=" + y + "]";
		}

	}

	private static String getMessage(String monFic) {
		String line = "";
		try {
			line = readFileToString(new File(monFic));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return line;
	}

}

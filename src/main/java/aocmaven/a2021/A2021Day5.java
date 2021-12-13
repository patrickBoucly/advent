package aocmaven.a2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class A2021Day5 extends A2021 {

	public A2021Day5(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day5 d = new A2021Day5(5);
		System.out.println(d.s1(true));
		System.out.println(d.s2(true));

	}

	public int s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());

		List<Wind> winds = getWinds(input);
		Map<String, Integer> cpt = getMapPointsHVD(winds);
		int res = 0;
		for (String key : cpt.keySet()) {
			
			if (cpt.get(key) > 1) {
				res++;
				
			}
		}
		return res;
	}

	private static Map<String, Integer> getMapPointsHVD(List<Wind> winds) {
		Map<String, Integer> cpt = new HashMap<>();
		int min = 0;
		int max = 0;

		for (Wind w : winds) {
			if (w.oneEnd.x == w.otherEnd.x) {
				if (w.oneEnd.y < w.otherEnd.y) {
					min = w.oneEnd.y;
					max = w.otherEnd.y;

				} else {
					max = w.oneEnd.y;
					min = w.otherEnd.y;
				}
				for (int i = min; i <= max; i++) {
					String key = String.valueOf(w.oneEnd.x) + "_" + String.valueOf(i);
					if (cpt.containsKey(key)) {
						cpt.put(key, cpt.get(key) + 1);
					} else {
						cpt.put(key, 1);
					}
				}
			} else if (w.oneEnd.y == w.otherEnd.y) {
				if (w.oneEnd.x < w.otherEnd.x) {
					min = w.oneEnd.x;
					max = w.otherEnd.x;

				} else {
					max = w.oneEnd.x;
					min = w.otherEnd.x;
				}
				for (int i = min; i <= max; i++) {
					String key = String.valueOf(i) + "_" + String.valueOf(w.oneEnd.y);
					if (cpt.containsKey(key)) {
						cpt.put(key, cpt.get(key) + 1);
					} else {
						cpt.put(key, 1);
					}
				}
			} else if (w.isDiag()) {
				Point xmin = new Point();

				if (w.oneEnd.x < w.otherEnd.x) {
					min = w.oneEnd.x;
					xmin = w.oneEnd;

				} else {
					xmin = w.otherEnd;
					min = w.otherEnd.x;
				}
			
				for (int k = 0; k <= Math.abs(w.oneEnd.y - w.otherEnd.y); k++) {
					String key = String.valueOf(k + xmin.x) + "_" + String.valueOf(k * w.getDir() + xmin.y);
					if (cpt.containsKey(key)) {
						cpt.put(key, cpt.get(key) + 1);
					} else {
						cpt.put(key, 1);
					}
				}
			}
		}
		return cpt;
	}

	public int s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());

		List<Wind> winds = getWinds(input);
		Map<String, Integer> cpt = getMapPointsHV(winds);
		int res = 0;
		for (String key : cpt.keySet()) {
			if (cpt.get(key) > 1) {
				res++;
			}
		}
		return res;
	}

	private static Map<String, Integer> getMapPointsHV(List<Wind> winds) {
		Map<String, Integer> cpt = new HashMap<>();
		int min = 0;
		int max = 0;

		for (Wind w : winds) {
			if (w.oneEnd.x == w.otherEnd.x) {
				if (w.oneEnd.y < w.otherEnd.y) {
					min = w.oneEnd.y;
					max = w.otherEnd.y;

				} else {
					max = w.oneEnd.y;
					min = w.otherEnd.y;
				}
				for (int i = min; i <= max; i++) {
					String key = String.valueOf(w.oneEnd.x) + "_" + String.valueOf(i);
					if (cpt.containsKey(key)) {
						cpt.put(key, cpt.get(key) + 1);
					} else {
						cpt.put(key, 1);
					}
				}
			} else if (w.oneEnd.y == w.otherEnd.y) {
				if (w.oneEnd.x < w.otherEnd.x) {
					min = w.oneEnd.x;
					max = w.otherEnd.x;

				} else {
					max = w.oneEnd.x;
					min = w.otherEnd.x;
				}
				for (int i = min; i <= max; i++) {
					String key = String.valueOf(i) + "_" + String.valueOf(w.oneEnd.y);

					if (cpt.containsKey(key)) {
						cpt.put(key, cpt.get(key) + 1);
					} else {
						cpt.put(key, 1);
					}
				}
			}
		}
		return cpt;
	}

	private static List<Wind> getWinds(List<String> input) {
		List<Wind> winds = new ArrayList<>();

		for (String s : input) {
			List<String> coordonnes = new ArrayList<>();
			List<String> pointS = Arrays.asList(s.split("->"));
			for (String ps : pointS) {
				coordonnes.addAll(Arrays.asList(ps.trim().split(",")));
			}
			Point oneEnd = new Point(Integer.parseInt(coordonnes.get(0)), Integer.parseInt(coordonnes.get(1)), 0);
			Point otherEnd = new Point(Integer.parseInt(coordonnes.get(2)), Integer.parseInt(coordonnes.get(3)), 0);
			winds.add(new Wind(oneEnd, otherEnd));
		}
		return winds;
	}

	public static class Wind {
		Point oneEnd;
		Point otherEnd;

		public Wind(Point oneEnd, Point otherEnd) {
			super();
			this.oneEnd = oneEnd;
			this.otherEnd = otherEnd;
		}

		public int getDir() {
			int res = 0;
			if (oneEnd.y - otherEnd.y == oneEnd.x - otherEnd.x) {
				return 1;
			}
			if (oneEnd.y - otherEnd.y == -oneEnd.x + otherEnd.x) {
				return -1;
			}
			return res;
		}

		public boolean isDiag() {

			return (getDir() * getDir()) == 1;
		}

		public Point getOneEnd() {
			return oneEnd;
		}

		public void setOneEnd(Point oneEnd) {
			this.oneEnd = oneEnd;
		}

		public Point getOtherEnd() {
			return otherEnd;
		}

		public void setOtherEnd(Point otherEnd) {
			this.otherEnd = otherEnd;
		}

	}

	public static class Point {
		int x;
		int y;
		int nbNuage;

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

		public int getNbNuage() {
			return nbNuage;
		}

		public void setNbNuage(int nbNuage) {
			this.nbNuage = nbNuage;
		}

		public Point(int x, int y, int nbNuage) {
			super();
			this.x = x;
			this.y = y;
			this.nbNuage = nbNuage;
		}

		public Point() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + ", nbNuage=" + nbNuage + "]";
		}

	}


	public static String getDuration() {
		A2021Day5 d = new A2021Day5(5);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1=endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return "Day "+ d.day+" run 1 took "+timeS1+" milliseconds, run 2 took " + (endTime - startTime) + " milliseconds";
		
	}
}

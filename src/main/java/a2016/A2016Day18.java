package a2016;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2016Day18 extends A2016 {

	public A2016Day18(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day18 d = new A2016Day18(18);
		long startTime = System.currentTimeMillis();
		//System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public long s1(boolean b) {
		String input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(String::trim).findFirst().get();
		int nbRow = 10;
		if(b) {
			nbRow = 40;
		}
		Set<Point> l0 = new HashSet<>();
		for (int i = 0; i < input.length(); i++) {
			l0.add(new Point(i, 0, input.substring(i, i + 1).equals("^")));
		}
		Game g = new Game(l0);
		System.out.println(g);
		g.remplir(nbRow);
		System.out.println(g);
		return g.pts.stream().filter(p->!p.isTrap).count();
	}

	public long s2(boolean b) {
		String input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(String::trim).findFirst().get();
		int nbRow = 10;
		if(b) {
			nbRow = 400000;
		}
		Set<Point> l0 = new HashSet<>();
		for (int i = 0; i < input.length(); i++) {
			l0.add(new Point(i, 0, input.substring(i, i + 1).equals("^")));
		}
		Long nbSafe=l0.stream().filter(p->!p.isTrap).count();
		Game g=new Game(l0);
		Set<Point> nextLine=g.getNextLigne(l0);
		nbSafe+=nextLine.stream().filter(p->!p.isTrap).count();
		for(int j=3;j<=nbRow;j++) {
			nextLine=g.getNextLigne(nextLine);
			nbSafe+=nextLine.stream().filter(p->!p.isTrap).count();
		}
		return nbSafe;
	}
	
	

	public static class Game {

		Set<Point> pts;

		public Set<Point> getPts() {
			return pts;
		}

		public void remplir(int nbRow) {
			int j = 0;
			int imax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getX).collect(Collectors.toList()));
			while (j < nbRow-1) {
				j++;
				for (int i = 0; i < imax+1; i++) {
					boolean leftIsTrap = getLeftIsTrap(i, j);
					boolean centerIsTrap = getCenterIsTrap(i, j);
					boolean rigthIsTrap = getRightIsTrap(i, j);
					boolean isTrap = false;
					if (leftIsTrap && centerIsTrap && !rigthIsTrap) {
						isTrap = true;
					}
					if (!leftIsTrap && centerIsTrap && rigthIsTrap) {
						isTrap = true;
					}
					if (leftIsTrap && !centerIsTrap && !rigthIsTrap) {
						isTrap = true;
					}
					if (!leftIsTrap && !centerIsTrap && rigthIsTrap) {
						isTrap = true;
					}
					pts.add(new Point(i, j, isTrap));
				}
			}

		}
		public Set<Point> getNextLigne(Set<Point> pts){
			Set<Point> nextLigne=new HashSet<>();
			int imax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getX).collect(Collectors.toList()));
			int j = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getY).collect(Collectors.toList()))+1;
			for (int i = 0; i < imax+1; i++) {
				boolean leftIsTrap = getLeftIsTrap(i, j,pts);
				boolean centerIsTrap = getCenterIsTrap(i, j,pts);
				boolean rigthIsTrap = getRightIsTrap(i, j,pts);
				boolean isTrap = false;
				if (leftIsTrap && centerIsTrap && !rigthIsTrap) {
					isTrap = true;
				}
				if (!leftIsTrap && centerIsTrap && rigthIsTrap) {
					isTrap = true;
				}
				if (leftIsTrap && !centerIsTrap && !rigthIsTrap) {
					isTrap = true;
				}
				if (!leftIsTrap && !centerIsTrap && rigthIsTrap) {
					isTrap = true;
				}
				nextLigne.add(new Point(i, j, isTrap));
			}
			return nextLigne;
			
		}

		private boolean getRightIsTrap(int i, int j, Set<Point> pts2) {
			Optional<Point> r = Point.getPoint(pts2, i + 1, j - 1);
			if (r.isEmpty()) {
				return false;
			}
			return r.get().isTrap;
		}

		private boolean getCenterIsTrap(int i, int j, Set<Point> pts2) {
			Optional<Point> r = Point.getPoint(pts2, i , j - 1);
			if (r.isEmpty()) {
				return false;
			}
			return r.get().isTrap;
		}

		private boolean getLeftIsTrap(int i, int j, Set<Point> pts2) {
			Optional<Point> r = Point.getPoint(pts2, i - 1, j - 1);
			if (r.isEmpty()) {
				return false;
			}
			return r.get().isTrap;
		}

		private boolean getRightIsTrap(int i, int j) {
			Optional<Point> r = Point.getPoint(pts, i + 1, j - 1);
			if (r.isEmpty()) {
				return false;
			}
			return r.get().isTrap;
		}

		private boolean getCenterIsTrap(int i, int j) {
			Optional<Point> r = Point.getPoint(pts, i , j - 1);
			if (r.isEmpty()) {
				return false;
			}
			return r.get().isTrap;
		}

		private boolean getLeftIsTrap(int i, int j) {
			Optional<Point> r = Point.getPoint(pts, i - 1, j - 1);
			if (r.isEmpty()) {
				return false;
			}
			return r.get().isTrap;
		}

		public void setPts(Set<Point> pts) {
			this.pts = pts;
		}

		public Game(Set<Point> pts) {
			super();
			this.pts = pts;
		}

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			// pts.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
			int imax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getX).collect(Collectors.toList()));
			int jmax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getY).collect(Collectors.toList()));
			for (int j = 0; j <= jmax; j++) {
				for (int i = 0; i <= imax; i++) {
					Point.getPoint(pts, i, j).ifPresentOrElse(pt -> res.append(pt.isTrap ? "^" : "."),
							() -> res.append("."));
				}
				res.append("\n");
			}
			return res.toString();
		}
	}

	private static class Point {
		int x;
		int y;
		Boolean isTrap;

		public Point(int x, int y, Boolean isTrap) {
			super();
			this.x = x;
			this.y = y;
			this.isTrap = isTrap;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
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
			return x == other.x && y == other.y;
		}

		@Override
		public String toString() {
			return isTrap ? "^" : ".";
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

		public Boolean getIsTrap() {
			return isTrap;
		}

		public void setIsTrap(Boolean isTrap) {
			this.isTrap = isTrap;
		}

		public static Optional<Point> getPoint(Set<Point> pts, int x, int y) {
			Point p = null;
			for (Point i : pts) {
				if (x == i.x && y == i.y) {
					p = new Point(x, y, i.isTrap);
				}
			}
			return Optional.ofNullable(p);
		}

	}

	public static List<Long> getDuration() {
		A2016Day18 d = new A2016Day18(16);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1, 3755797L);
	}

}

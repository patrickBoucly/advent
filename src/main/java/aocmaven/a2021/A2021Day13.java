package aocmaven.a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2021Day13 extends A2021 {

	public A2021Day13(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day13 d = new A2021Day13(13);
		// d.s1(true);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1=endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		System.out.println("Day "+ d.day+" run 1 took "+timeS1+" milliseconds, run 2 took " + (endTime - startTime) + " milliseconds");
		
	}

	private String s2(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		Set<Point> pts = new HashSet<>();
		List<Operation> pliages = new ArrayList<>();
		for (String l : lignes) {
			if (!l.equals("")) {
				if (l.substring(0, 1).equals("f")) {
					String[] sp = l.split("=");
					pliages.add(new Operation(l.substring(11, 12), Integer.parseInt(sp[1])));

				} else {
					String[] sp = l.split(",");
					pts.add(new Point(Integer.parseInt(sp[0]), Integer.parseInt(sp[1])));
				}
			}
		}
		for(Operation o:pliages) {
		pts = plier(pts, o);
		}
		int xmin=MesOutils.getMinIntegerFromList(pts.stream().map(Point::getX).collect(Collectors.toList()));
		int xmax=MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getX).collect(Collectors.toList()));
		int ymin=MesOutils.getMinIntegerFromList(pts.stream().map(Point::getY).collect(Collectors.toList()));
		int ymax=MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getY).collect(Collectors.toList()));
		String line="";
		for(int y=ymin;y<=ymax;y++) {
			for(int x=xmin;x<=xmax;x++) {
				if(pts.contains(new Point(x, y))) {
					line+="#";
				} else {
					line+=" ";
				}
			}
			line+="\n";
			
		}
		return line;
	}

	private int s1(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		Set<Point> pts = new HashSet<>();
		List<Operation> pliages = new ArrayList<>();
		for (String l : lignes) {
			if (!l.equals("")) {
				if (l.substring(0, 1).equals("f")) {
					String[] sp = l.split("=");
					pliages.add(new Operation(l.substring(11, 12), Integer.parseInt(sp[1])));

				} else {
					String[] sp = l.split(",");
					pts.add(new Point(Integer.parseInt(sp[0]), Integer.parseInt(sp[1])));
				}
			}
		}
		pts = plier(pts, pliages.get(0));
		return pts.size();

	}

	private Set<Point> plier(Set<Point> pts, Operation o) {
		Set<Point> newPts = new HashSet<>();
		for (Point p : pts) {
			if (o.type.equals("x")) {
				if (p.x < o.pos) {
					newPts.add(p);
				} else if (p.x > o.pos) {
					newPts.add(new Point(2*o.pos-p.x, p.y));

				}

			} else {
				if (p.y < o.pos) {
					newPts.add(new Point(p.x, p.y));
				} else if (p.y > o.pos) {
					newPts.add(new Point(p.x,2*o.pos-p.y));
				}
			}
		}
		return newPts;
	}

	public static class Operation {
		String type;
		int pos;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getPos() {
			return pos;
		}

		public void setPos(int pos) {
			this.pos = pos;
		}

		public Operation(String type, int pos) {
			super();
			this.type = type;
			this.pos = pos;
		}

		@Override
		public String toString() {
			return "Operation [type=" + type + ", pos=" + pos + "]";
		}

	}

	public static class Point {
		int x;
		int y;

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

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}

	}
	public static String getDuration() {
		A2021Day13 d = new A2021Day13(13);
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

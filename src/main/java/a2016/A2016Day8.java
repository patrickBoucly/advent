package a2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2016Day8 extends A2016 {
	public A2016Day8(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day8 d = new A2016Day8(8);
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

	public String s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim())
				.collect(Collectors.toList());
		Game g=new Game(getInsts(input),getEmptyGame());
		for (Inst i : g.insts) {
			g.setPts(apply(g.pts, i));
		}
		return g.toString();
	}

	public Long s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim())
				.collect(Collectors.toList());
		Game g=new Game(getInsts(input),getEmptyGame());
		for (Inst i : g.insts) {
			g.setPts(apply(g.pts, i));
		}
		return g.pts.stream().filter(p -> p.isOn).count();
	}

	public Set<Point> apply(Set<Point> pts, Inst i) {
		Set<Point> np=new HashSet<>();
		if (i.type.equals("rect")) {
			for (Point p : pts) {
				if (p.x < i.a && p.y < i.b) {
					np.add(new Point(p.x,p.y,true));
				} else {
					np.add(new Point(p.x,p.y,p.isOn));
				}
			}
		} else if (i.type.equals("row")) {
			np=decRow(pts,i.a,i.b);
		} else {
			np=decCol(pts,i.a,i.b);
		}
		return np;
	}

	private Set<Point> decCol(Set<Point> pts, int a, int b) {
		Set<Point> np=new HashSet<>();
		for(Point p:pts) {
			if(p.x==a) {
				np.add(new Point(p.x,(p.y+b )% 6,p.isOn));
			}else {
				np.add(new Point(p.x,p.y,p.isOn));
			}
		}
		return np;
	}

	private Set<Point> decRow(Set<Point> pts, int a, int b) {
		Set<Point> np=new HashSet<>();
		for(Point p:pts) {
			if(p.y==a) {
				np.add(new Point((p.x+b) % 50,p.y,p.isOn));
			}else {
				np.add(new Point(p.x,p.y,p.isOn));
			}
		}
		return np;
	}

	public static Optional<Point> getPoint(Set<Point> pts, int x, int y) {
		Point p = null;
		for (Point i : pts) {
			if (x == i.x && y == i.y) {
				p = new Point(x, y, i.isOn);
			}
		}
		return Optional.ofNullable(p);
	}

	public Set<Point> getEmptyGame() {
		Set<Point> pts = new HashSet<>();
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 6; j++) {
				pts.add(new Point(i, j, false));
			}
		}
		return pts;
	}

	public List<Inst> getInsts(List<String> input) {
		List<Inst> insts = new ArrayList<>();
		for (int i = 0; i < input.size() - 1; i++) {
			String line = input.get(i);
			String type = "";
			int a = 0;
			int c = 0;
			if (line.contains("rect")) {
				type = "rect";
				String chiffres = line.split(" ")[1];
				a = Integer.valueOf(chiffres.split("x")[0]);
				c = Integer.valueOf(chiffres.split("x")[1]);
			} else {
				if (line.contains("row")) {
					type = "row";

				} else {
					type = "col";
				}
				String[] sp = line.split(" ");
				a = Integer.valueOf(sp[2].split("=")[1]);
				c = Integer.valueOf(sp[4]);
			}
			insts.add(new Inst(type, a, c));
		}
		return insts;
	}

	public class Inst {
		String type;
		int a;
		int b;

		public Inst(String type, int a, int b) {
			super();
			this.type = type;
			this.a = a;
			this.b = b;
		}

		@Override
		public String toString() {
			return "Inst [type=" + type + ", a=" + a + ", b=" + b + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(a, b, type);
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
			Inst other = (Inst) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return a == other.a && b == other.b && Objects.equals(type, other.type);
		}

		public A2016Day8 getEnclosingInstance() {
			return A2016Day8.this;
		}

	}

	public static class Game {
		List<Inst> insts;
		Set<Point> pts;

		public List<Inst> getInsts() {
			return insts;
		}

		public void setInsts(List<Inst> insts) {
			this.insts = insts;
		}

		public Set<Point> getPts() {
			return pts;
		}

		public void setPts(Set<Point> pts) {
			this.pts = pts;
		}

		public Game(List<Inst> insts, Set<Point> pts) {
			super();
			this.insts = insts;
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
					Point.getPoint(pts, i, j).ifPresentOrElse(pt -> res.append(pt.isOn ? "#" : "."), () -> res.append("."));

				}
				res.append("\n");
			}
			return res.toString();
		}
	}

	private static class Point {
		int x;
		int y;
		Boolean isOn;

		public Point(int x, int y, Boolean pixel) {
			super();
			this.x = x;
			this.y = y;
			this.isOn = pixel;
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
			return isOn ? "#" : ".";
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

		public Boolean getIsOn() {
			return isOn;
		}

		public void setIsOn(Boolean isOn) {
			this.isOn = isOn;
		}

		public static Optional<Point> getPoint(Set<Point> pts, int x, int y) {
			Point p = null;
			for (Point i : pts) {
				if (x == i.x && y == i.y) {
					p = new Point(x, y, i.isOn);
				}
			}
			return Optional.ofNullable(p);
		}

	}
	public static List<Long> getDuration() {
		A2016Day8 d = new A2016Day8(8);
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

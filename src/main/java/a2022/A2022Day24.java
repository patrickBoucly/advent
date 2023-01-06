package a2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class A2022Day24 extends A2022 {

	public A2022Day24(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day24 d = new A2022Day24(24);

		long startTime = System.currentTimeMillis();
		System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		// System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public String s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		return null;
	}

	public int s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		Situation s0 = getSituation(input);
		List<Situation> situs = new ArrayList<>();
		List<Situation> nextSitu = new ArrayList<>();
		situs.add(s0);
		while (situs.stream().noneMatch(Situation::isOver)) {
			nextSitu = getNextSitu(situs);
			situs = new ArrayList<>(nextSitu);
		}
		return situs.stream().filter(Situation::isOver).findAny().get().time;
	}

	private List<Situation> getNextSitu(List<Situation> situs) {
		List<Situation> nextSitu = new ArrayList<>();
		for (Situation s : situs) {
			Situation ns=getNextSituSansPos(s);
			if (ns.p.equals(new Position(ns.v.xmax - 1, ns.v.ymax - 1))) {
				ns.setP(new Position(ns.v.xmax - 1, ns.v.ymax));
				nextSitu = new ArrayList<>();
				nextSitu.add(ns);
				return nextSitu;
			}
			if (ns.v.blizzards.stream().filter(bl -> bl.i == ns.p.x && bl.j == ns.p.y).count() < 1L) {
				nextSitu.add(new Situation(ns.v, new Position(ns.p.x, ns.p.y), ns.time));
			}
			Position pn = new Position(ns.p.x, ns.p.y - 1);
			Position ps = new Position(ns.p.x, ns.p.y + 1);
			Position pe = new Position(ns.p.x - 1, ns.p.y);
			Position po = new Position(ns.p.x + 1, ns.p.y);
			List<Position> pos = List.of(pn, ps, pe, po);
			for (Position position : pos) {
				if (position.x > 0 && position.y > 0 && position.x < ns.v.xmax && position.y < ns.v.ymax) {
					if (ns.v.blizzards.stream().filter(bl -> bl.i == position.x && bl.j == position.y).count() < 1L) {
						nextSitu.add(new Situation(ns.v, position, ns.time));
					}
				}
			}
		}
		List<Situation> nextSituC = new ArrayList<>();
		for (Situation st : nextSitu) {
			if (!(st.v.blizzards.stream().filter(bl -> bl.i == st.p.x && bl.j == st.p.y).count() > 0L)) {
				nextSituC.add(st);
			}
		}
		return nextSituC;
	}

	private Situation getNextSituSansPos(Situation s) {
		Situation ns =new Situation();
		ns.setP(new Position(s.p.x,s.p.y));
		ns.setTime(s.getTime()+1);
		ns.setV(s.v.moveB());
		return ns;
	}

	private Situation getSituation(List<String> input) {
		int j = 0;
		int xmax = 0;
		int ymax = 0;
		int idb = 0;
		Set<Blizzard> blizzards = new HashSet<>();
		for (String s : input) {
			xmax = s.length();
			for (int i = 0; i < s.length(); i++) {

				String info = s.substring(i, i + 1);
				if (!(info.equals(".") || info.equals("#"))) {
					blizzards.add(new Blizzard(idb, i, j, info));
					idb++;
				}

			}
			j++;
		}
		ymax = j;
		Valley v = new Valley(blizzards, xmax - 1, ymax - 1);
		return new Situation(v, new Position(1, 0), 0);
	}

	private class Situation {
		Valley v;
		Position p;
		int time;

		public boolean isOver() {
			return (p.x == v.xmax - 1 && p.y == v.ymax);
		}

		public Valley getV() {
			return v;
		}

		public void setV(Valley v) {
			this.v = v;
		}

		public Position getP() {
			return p;
		}

		public void setP(Position p) {
			this.p = p;
		}

		public int getTime() {
			return time;
		}

		public void setTime(int time) {
			this.time = time;
		}

		public Situation(Valley v, Position p, int time) {
			super();
			this.v = v;
			this.p = p;
			this.time = time;
		}

		public Situation() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			res.append("Time :" + time);
			res.append("\n");

			int imax = v.xmax;
			int jmax = v.ymax;
			for (int j = 0; j <= jmax; j++) {

				for (int i = 0; i <= imax; i++) {
					if (i == p.x && j == p.y) {
						res.append("E");
					} else if (i == 1 && j == 0) {
						res.append(".");
					} else if (i == v.xmax - 1 && j == v.ymax) {
						res.append(".");
					} else if (i == 0 || j == 0 || i == v.xmax || j == v.ymax) {
						res.append("#");
					} else {
						long cptBliz = compteBlizzard(v.blizzards, i, j);
						if (cptBliz == 0L) {
							res.append(".");
						} else if (cptBliz == 1L) {
							res.append(getDir(v.blizzards, i, j));
						} else {
							res.append(cptBliz);
						}
					}
				}
				res.append("\n");
			}
			return res.toString();
		}

		private String getDir(Set<Blizzard> blizzards, int i, int j) {
			return v.blizzards.stream().filter(bl -> bl.i == i && bl.j == j).findFirst().get().dir;
		}

		private long compteBlizzard(Set<Blizzard> blizzards, int i, int j) {
			return v.blizzards.stream().filter(bl -> bl.i == i && bl.j == j).count();
		}
	}

	private class Position {
		int x;
		int y;

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

		public Position(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(x, y);
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
			Position other = (Position) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return x == other.x && y == other.y;
		}

		private A2022Day24 getEnclosingInstance() {
			return A2022Day24.this;
		}

	}

	private class Valley {
		Set<Blizzard> blizzards;
		int xmax;
		int ymax;

		public Set<Blizzard> getBlizzards() {
			return blizzards;
		}

		public Valley moveB() {
			Valley nv=new Valley();
			nv.setXmax(xmax);
			nv.setYmax(ymax);
			Set<Blizzard> nbs=new HashSet<>();
			for(Blizzard b:blizzards) {
				nbs.add(new Blizzard(b.id, b.i, b.j, b.dir));
			}
			nv.setBlizzards(nbs);
			nv.move();
			return nv;
		}

		public void setBlizzards(Set<Blizzard> blizzards) {
			this.blizzards = blizzards;
		}

		public Valley(Set<Blizzard> blizzards, int xmax, int ymax) {
			super();
			this.blizzards = blizzards;
			this.xmax = xmax;
			this.ymax = ymax;
		}

		public Valley(Valley v) {
			this.xmax = v.xmax;
			this.ymax = v.ymax;
			this.blizzards =new HashSet<>(v.blizzards);
		}

		public Valley() {
			// TODO Auto-generated constructor stub
		}

		public int getXmax() {
			return xmax;
		}

		public void setXmax(int xmax) {
			this.xmax = xmax;
		}

		public int getYmax() {
			return ymax;
		}

		public void setYmax(int ymax) {
			this.ymax = ymax;
		}

		public void move() {
			for (Blizzard b : blizzards) {
				if (b.dir.equals("^")) {
					if (b.j == 1) {
						b.setJ(ymax - 1);
					} else {
						b.setJ(b.j - 1);
					}
				}
				if (b.dir.equals("v")) {
					if (b.j == ymax - 1) {
						b.setJ(1);
					} else {
						b.setJ(b.j + 1);
					}
				}
				if (b.dir.equals("<")) {
					if (b.i == 1) {
						b.setI(xmax - 1);
					} else {
						b.setI(b.i - 1);
					}
				}
				if (b.dir.equals(">")) {
					if (b.i == xmax - 1) {
						b.setI(1);
					} else {
						b.setI(b.i + 1);
					}
				}
			}
		}

	}

	private class Blizzard {
		int id;
		int i;
		int j;
		String dir;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

		public int getJ() {
			return j;
		}

		public void setJ(int j) {
			this.j = j;
		}

		public String getDir() {
			return dir;
		}

		public void setDir(String dir) {
			this.dir = dir;
		}

		public Blizzard(int id, int i, int j, String dir) {
			super();
			this.id = id;
			this.i = i;
			this.j = j;
			this.dir = dir;
		}

		@Override
		public String toString() {
			return "Blizzard [i=" + i + ", j=" + j + ", dir=" + dir + "]";
		}

	}

	public static List<Long> getDuration() {
		A2022Day24 d = new A2022Day24(24);
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

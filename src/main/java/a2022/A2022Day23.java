package a2022;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2022Day23 extends A2022 {

	public A2022Day23(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day23 d = new A2022Day23(23);

		long startTime = System.currentTimeMillis();
		// System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public int s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		Set<Point> pts = getPoints(input);
		Field f = new Field(pts);
		int dec = 0;
		int round = 0;
		System.out.println(pts.stream().filter(q -> q.elfPresent).toList().size());
		while (!f.fini()) {
			f = f.nextPos(dec);
			round++;
			if(round%10==0) {
				System.out.println("End of Round "+round+" cptIso: "+f.cptIso());
			}
			//System.out.println(f);
			//System.out.println("Round "+round +" nbMvt : "+ f.nbMvt);
			dec++;
		}
		round++;
		//run 2 took 2748251 milliseconds
		return round;
	}

	public int s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		Set<Point> pts = getPoints(input);
		Field f = new Field(pts);
		System.out.println(f);
		int dec = 0;
		int round = 0;
		while (round < 10) {
			f = f.nextPos(dec);
			round++;
			dec++;
			//System.out.println("End of Round "+round);
			//System.out.println(f);
		}
		
		return f.countEmpty();
	}

	public Optional<Point> getPoint(Set<Point> pts, int x, int y) {
		Point p = null;
		for (Point i : pts) {
			if (x == i.p.x && y == i.p.y) {
				p = new Point(new Position(x, y), i.elfPresent);
				return Optional.ofNullable(p);
			}
		}
		return Optional.ofNullable(p);
	}

	private Set<Point> getPoints(List<String> input) {
		Set<Point> pts = new HashSet<A2022Day23.Point>();
		int j = 0;
		for (String s : input) {
			for (int i = 0; i < s.length(); i++) {
				pts.add(new Point(new Position(i, j), s.substring(i, i + 1)));
			}
			j++;
		}
		return pts;
	}

	private class Field {
		Set<Point> pts;
		int nbMvt=1;
		public Set<Point> getPts() {
			return pts;
		}
		public Integer cptIso() {
			List<Point> ptElf=pts.stream().filter(q -> q.elfPresent).toList();
			int nbElf=ptElf.size();
			int cptIso=0;
			for(Point p:ptElf) {
				if(p.nordOk(pts) && p.sudOk(pts)&& p.estOk(pts)&& p.ouestOk(pts)) {
					cptIso++;
				}
			}
			return cptIso;
			
		}

		public boolean fini() {
			boolean res=true;
			for(Point p:pts) {
				if(!p.nordOk(pts)||!p.sudOk(pts)||!p.estOk(pts)||!p.ouestOk(pts)) {
					return false;
				}
			}
			return res;
		}

		public int countEmpty() {
			int cpt = 0;
			int imax = MesOutils.getMaxIntegerFromList(pts.stream().map(p -> p.getP().x).toList());
			int imin = MesOutils.getMinIntegerFromList(pts.stream().map(p -> p.getP().x).toList());
			int jmax = MesOutils.getMaxIntegerFromList(pts.stream().map(p -> p.getP().y).toList());
			int jmin = MesOutils.getMinIntegerFromList(pts.stream().map(p -> p.getP().y).toList());
			for (int j = jmin; j <= jmax; j++) {
				for (int i = imin; i <= imax; i++) {
					Optional<Point> pij=getPoint(pts, i, j);
					if(pij.isPresent()) {
						if(!pij.get().elfPresent) {
							cpt++;
						}
					} else {
						cpt++;
					}
				}

			}
			return cpt;
		}

		public Field nextPos(int dec) {
			Field nf = new Field();
			int nbMvt=0;
			Set<Point> nfp = new HashSet<>();
			for (Point pt : pts.stream().filter(q -> q.elfPresent).toList()) {
				pt.setNextWP(dec, pts);
			}
			for (Point pt : pts.stream().filter(q -> q.elfPresent).toList()) {
				Position nextWP = pt.getNextPosWanted();
				List<Point> ptElf=pts.stream().filter(q -> q.elfPresent).toList();
				if (ptElf.stream().filter(q -> q.getNextPosWanted().equals(nextWP))
						.count() < 2) {
					nfp.add(new Point(new Position(nextWP), true));
					nbMvt++;
				} else {
					nfp.add(new Point(new Position(pt.p), true));
				}
			}
			nf.setPts(nfp);
			nf.nbMvt=nbMvt;
			return nf;
		}

		public void setPts(Set<Point> pts) {
			this.pts = pts;
		}

		public Field(Set<Point> pts) {
			super();
			this.pts = pts;
		}

		public Field() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			int imax = MesOutils.getMaxIntegerFromList(pts.stream().map(p -> p.getP().x).toList());
			int imin =Math.min(0,MesOutils.getMinIntegerFromList(pts.stream().map(p -> p.getP().x).toList()));
			int jmax = MesOutils.getMaxIntegerFromList(pts.stream().map(p -> p.getP().y).toList());
			int jmin = Math.min(0,MesOutils.getMinIntegerFromList(pts.stream().map(p -> p.getP().y).toList()));
			for (int j = jmin; j <= jmax; j++) {
				for (int i = imin; i <= imax; i++) {
					if(i==0 && j==0) {
						getPoint(pts, i, j).ifPresentOrElse(pt -> res.append(pt.elfPresent ? "*" : "0"),
								() -> res.append("0"));
					} else {
					getPoint(pts, i, j).ifPresentOrElse(pt -> res.append(pt.elfPresent ? "#" : "."),
							() -> res.append("."));
					}
				}
				res.append("\n");
			}
			return res.toString();
		}

	}

	private class Point {
		public Point(Position position, String info) {
			this.p = position;
			this.elfPresent = info.equals("#") ? true : false;
		}

		public void setNextWP(int dec, Set<Point> pts) {
			if (noElfAround(pts)) {
				setNextPosWanted(new Position(p.x, p.y));
			} else {
				int test=0;
				while (nextPosWanted == null && test <5) {
					if (dec % 4 == 0) {
						if (nordOk(pts)) {
							setNextPosWanted(new Position(p.x, p.y - 1));
						}
					} else if (dec % 4 == 1) {
						if (sudOk(pts)) {
							setNextPosWanted(new Position(p.x, p.y + 1));
						}
					} else if (dec % 4 == 2) {
						if (ouestOk(pts)) {
							setNextPosWanted(new Position(p.x - 1, p.y));
						}
					} else if (dec % 4 == 3) {
						if (estOk(pts)) {
							setNextPosWanted(new Position(p.x + 1, p.y));
						}
					}
					dec++;
					test++;
				}
				
			}
			if(nextPosWanted==null) {
				setNextPosWanted(new Position(p.x, p.y));
			}
		}

		private boolean noElfAround(Set<Point> pts) {
			return estOk(pts)&&nordOk(pts)&&sudOk(pts)&&ouestOk(pts);
		}

		private boolean estOk(Set<Point> pts) {
			Optional<Point> e = getPoint(pts, p.x + 1, p.y);
			Optional<Point> ne = getPoint(pts, p.x + 1, p.y - 1);
			Optional<Point> se = getPoint(pts, p.x + 1, p.y + 1);
			if (!((ne.isPresent() && ne.get().elfPresent) || (e.isPresent() && e.get().elfPresent)
					|| (se.isPresent() && se.get().elfPresent))) {
				return true;
			}
			return false;
		}

		private boolean ouestOk(Set<Point> pts) {
			Optional<Point> o = getPoint(pts, p.x - 1, p.y);
			Optional<Point> no = getPoint(pts, p.x - 1, p.y - 1);
			Optional<Point> so = getPoint(pts, p.x - 1, p.y + 1);
			if (!((so.isPresent() && so.get().elfPresent) || (o.isPresent() && o.get().elfPresent)
					|| (no.isPresent() && no.get().elfPresent))) {
				return true;
			}
			return false;
		}

		private boolean sudOk(Set<Point> pts) {
			Optional<Point> s = getPoint(pts, p.x, p.y + 1);
			Optional<Point> sw = getPoint(pts, p.x + 1, p.y + 1);
			Optional<Point> se = getPoint(pts, p.x - 1, p.y + 1);
			if (!((se.isPresent() && se.get().elfPresent) || (s.isPresent() && s.get().elfPresent)
					|| (sw.isPresent() && sw.get().elfPresent))) {
				return true;
			}
			return false;
		}

		private boolean nordOk(Set<Point> pts) {
			Optional<Point> n = getPoint(pts, p.x, p.y - 1);
			Optional<Point> nw = getPoint(pts, p.x + 1, p.y - 1);
			Optional<Point> ne = getPoint(pts, p.x - 1, p.y - 1);
			if (!((ne.isPresent() && ne.get().elfPresent) || (n.isPresent() && n.get().elfPresent)
					|| (nw.isPresent() && nw.get().elfPresent))) {
				return true;
			}
			return false;
		}

		public Point(Position position, boolean elfPresent) {
			this.p = position;
			this.elfPresent = elfPresent;
		}

		Position p;
		boolean elfPresent;
		private Position nextPosWanted;

		public Position getP() {
			return p;
		}


		public Position getNextPosWanted() {
			return nextPosWanted;
		}

		public void setNextPosWanted(Position nextPosWanted) {
			this.nextPosWanted = nextPosWanted;
		}

	}

	private class Position {
		int x;
		int y;

		@Override
		public String toString() {
			return "Position [x=" + x + ", y=" + y + "]";
		}

		public Position(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public Position(Position nextWP) {
			super();
			this.x = nextWP.x;
			this.y = nextWP.y;
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

		private A2022Day23 getEnclosingInstance() {
			return A2022Day23.this;
		}

	}

	public static List<Long> getDuration() {
		A2022Day23 d = new A2022Day23(23);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1,(long) 2748251);
	}

}

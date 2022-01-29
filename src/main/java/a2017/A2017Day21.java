package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class A2017Day21 extends A2017 {

	public A2017Day21(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day21 d = new A2017Day21(21);
		// d.s1(true);
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

	public long s1(boolean b) {

		List<Rule> rules = getRules(b);

		Grid init = getInitGrid();
		for (int i = 0; i < 5; i++) {
			init = iter(init, rules);
			System.out.println("nbIter : " + i + " nb # : " + init.pts.stream().filter(p -> p.v.equals("#")).count());
		}
		return init.pts.stream().filter(p -> p.v.equals("#")).count();
	}

	private Grid iter(Grid g, List<Rule> rules) {
		if (g.size % 2 == 0) {
			List<Grid> splitted2x2 = split2x2(g);
			List<Grid> converted3x3 = new ArrayList<>();
			for (Grid s : splitted2x2) {
				converted3x3.add(getCorres(s, rules));
				for (Rule r : rules) {
					if (r.match(s)) {
						converted3x3.add(r.go);
					}
				}
			}
			int gridFusionSize = (int) Math.sqrt(converted3x3.size());
			List<Point> ptsF = new ArrayList<>();
			int incX = 0;
			int incY = 0;
			int cpt = 0;
			for (Grid gt : converted3x3) {
				List<Point> ptsGt = new ArrayList<>(gt.pts);
				for (Point p : ptsGt) {
					ptsF.add(new Point(p.x + 3 * incX, p.y + 3 * incY, p.v));
				}
				cpt++;
				if (cpt % gridFusionSize == 0) {
					incY++;
					incX = 0;
				} else {
					incX++;
				}
			}

			return new Grid(ptsF);
		} else if (g.size % 3 == 0) {
			List<Grid> splitted3x3 = split3x3(g);
			List<Grid> converted4x4 = new ArrayList<>();
			for (Grid s : splitted3x3) {
				for (Rule r : rules) {
					if (r.match(s)) {
						converted4x4.add(r.go);
					}
				}
			}

			int gridFusionSize = (int) Math.sqrt(converted4x4.size());
			List<Point> ptsF = new ArrayList<>();
			int incX = 0;
			int incY = 0;
			int cpt = 0;
			for (Grid gt : converted4x4) {
				List<Point> ptsGt = new ArrayList<>(gt.pts);
				for (Point p : ptsGt) {
					ptsF.add(new Point(p.x + 4 * incX, p.y + 4 * incY, p.v));
				}
				cpt++;
				if (cpt % gridFusionSize == 0) {
					incY++;
					incX = 0;
				} else {
					incX++;
				}
			}

			return new Grid(ptsF);
		} else {
			return null;
		}
	}

	private Grid getCorres(Grid s, List<Rule> rules) {
		for (Rule r : rules) {
			for (Grid gx : s.get8Grid()) {
				if (gx.equals(s)) {
					return r.go;
				}
			}
		}
		return null;
	}

	private List<Grid> split3x3(Grid g) {
		List<Grid> split3x3 = new ArrayList<>();
		int nbBlocParCol = g.size / 3;
		for (int j = 0; j < nbBlocParCol; j++) {
			for (int i = 0; i < nbBlocParCol; i++) {
				List<Point> p = new ArrayList<>();
				p.add(g.get(0 + 3 * i, 0 + 3 * j));
				p.add(g.get(0 + 3 * i, 1 + 3 * j));
				p.add(g.get(0 + 3 * i, 2 + 3 * j));
				p.add(g.get(1 + 3 * i, 0 + 3 * j));
				p.add(g.get(1 + 3 * i, 1 + 3 * j));
				p.add(g.get(1 + 3 * i, 2 + 3 * j));
				p.add(g.get(2 + 3 * i, 0 + 3 * j));
				p.add(g.get(2 + 3 * i, 1 + 3 * j));
				p.add(g.get(2 + 3 * i, 2 + 3 * j));
				p.sort(Comparator.comparing(Point::getX).thenComparingInt(Point::getY));
				split3x3.add(new Grid(p));
			}
		}
		return split3x3;
	}

	private List<Grid> split2x2(Grid g) {
		List<Grid> split2x2 = new ArrayList<>();
		g.pts.sort(Comparator.comparing(Point::getX).thenComparingInt(Point::getY));
		int dec = g.pts.get(0).x;
		for (Point p : g.pts) {
			p.x -= dec;
			p.y -= dec;

		}
		int nbBlocParCol = g.size / 2;
		for (int j = 0; j < nbBlocParCol; j++) {
			for (int i = 0; i < nbBlocParCol; i++) {
				List<Point> p = new ArrayList<>();
				p.add(g.get(0 + 2 * i, 0 + 2 * j));
				p.add(g.get(0 + 2 * i, 1 + 2 * j));
				p.add(g.get(1 + 2 * i, 0 + 2 * j));
				p.add(g.get(1 + 2 * i, 1 + 2 * j));
				split2x2.add(new Grid(p));
			}
		}
		return split2x2;
	}

	private Grid getInitGrid() {
		List<Point> pts = new ArrayList<>();
		pts.add(new Point(0, 0, "."));
		pts.add(new Point(0, 1, "#"));
		pts.add(new Point(0, 2, "."));
		pts.add(new Point(1, 0, "."));
		pts.add(new Point(1, 1, "."));
		pts.add(new Point(1, 2, "#"));
		pts.add(new Point(2, 0, "#"));
		pts.add(new Point(2, 1, "#"));
		pts.add(new Point(2, 2, "#"));
		return new Grid(pts);
	}

	private List<Rule> getRules(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());

		List<Rule> rules = new ArrayList<>();
		for (String l : lignes) {
			String[] sp = l.split("=>");
			String[] spg = sp[0].split("/");
			String[] spd = sp[1].split("/");
			List<Point> ptsGi = new ArrayList<>();
			List<Point> ptsGo = new ArrayList<>();
			int cptG = 0;
			for (String s : spg) {
				for (int i = 0; i < s.trim().length(); i++) {
					if (StringUtils.isNotEmpty(s)) {
						ptsGi.add(new Point(cptG, i, s.trim().substring(i, i + 1)));
					}
				}
				cptG++;
			}
			int cptD = 0;
			for (String s : spd) {
				if (StringUtils.isNotEmpty(s)) {
					for (int i = 0; i < s.trim().length(); i++) {
						ptsGo.add(new Point(cptD, i, s.trim().substring(i, i + 1)));
					}
				}
				cptD++;
			}
			rules.add(new Rule(new Grid(ptsGi), new Grid(ptsGo)));

		}
		return rules.stream().filter(r -> r != null).collect(Collectors.toList());
	}

	public long s2(boolean b) {

		List<Rule> rules = getRules(b);

		Grid init = getInitGrid();
		Map<Grid, Integer> m = new HashMap<>();
		// m.put(init, 1);
		List<Grid> ag = new ArrayList<>();
		for (Rule r : rules) {
			ag.addAll(r.gi.get8Grid());
		}
		for (Grid g : ag) {
			m.put(g, 0);
		}
		System.out.println(m.get(init));
		m.put(init, m.get(init) + 1);

		for (int j = 0; j < 18; j++) {
			m = iterM(m, rules);
			long res = 0L;
			for (Entry<Grid, Integer> i : m.entrySet()) {
				res += i.getValue() * i.getKey().pts.stream().filter(p -> p.v.equals("#")).count();
			}
			System.out.println(res);
		}
		long res = 0L;
		for (Entry<Grid, Integer> i : m.entrySet()) {
			res += i.getValue() * i.getKey().pts.stream().filter(p -> p.v.equals("#")).count();
		}
		return res;
	}

	private Map<Grid, Integer> iterM(Map<Grid, Integer> m, List<Rule> rules) {
		Map<Grid, Integer> res = new HashMap<>();
		for (Entry<Grid, Integer> i : m.entrySet()) {
			if (m.get(i.getKey()) > 0) {
				Grid g = i.getKey();
				if (g.size == 2) {
					Map<Grid, Integer> tmpres = new HashMap<>();
					
				
						if(tmpres.containsKey(getCorres(g, rules))) {
							tmpres.put(getCorres(g, rules),tmpres.get(getCorres(g, rules))+1);
						}else {
							tmpres.put(getCorres(g, rules),1);
						}
					
					for(Entry<Grid,Integer> j:tmpres.entrySet()) {
						if(res.containsKey(j.getKey())) {
							res.put(j.getKey(),res.get(j.getKey())+ j.getValue()*m.get(g));
						} else {
						res.put(j.getKey(), j.getValue()*m.get(g));
						}
					}
				} else if (g.size == 3) {
					Grid x = null;
					for (Rule r : rules) {
						if (r.match(g)) {
							x = r.go;
						}
					}
					List<Grid> g2from4 = new ArrayList<>();
					g2from4.add(new Grid(Arrays.asList(x.pts.get(0), x.pts.get(1), x.pts.get(4), x.pts.get(5))));
					g2from4.add(new Grid(Arrays.asList(x.pts.get(2), x.pts.get(3), x.pts.get(6), x.pts.get(7))));
					g2from4.add(new Grid(Arrays.asList(x.pts.get(8), x.pts.get(9), x.pts.get(12), x.pts.get(13))));
					g2from4.add(new Grid(Arrays.asList(x.pts.get(10), x.pts.get(11), x.pts.get(14), x.pts.get(15))));
					Map<Grid, Integer> tmpres = new HashMap<>();
					for (Grid g2 : g2from4) {
						System.out.println(g2);
						if(tmpres.containsKey(g2)) {
							tmpres.put(g2,tmpres.get(g2)+1);
						}else {
							tmpres.put(g2,1);
						}
					}
					for(Entry<Grid,Integer> j:tmpres.entrySet()) {
						if(res.containsKey(j.getKey())) {
							res.put(j.getKey(),res.get(j.getKey())+ j.getValue()*m.get(g));
						} else {
						res.put(j.getKey(), j.getValue()*m.get(g));
						}
					}
					System.out.println(res);
				}
			}
		}
		return res;
	}

	public static class Point {
		int x;
		int y;
		String v;

		public Point(int x, int y, String v) {
			super();
			this.x = x;
			this.y = y;
			this.v = v;
		}

		@Override
		public String toString() {
			return v;
		}

		@Override
		public int hashCode() {
			return Objects.hash(v, x, y);
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
			return Objects.equals(v, other.v) && x == other.x && y == other.y;
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

		public String getV() {
			return v;
		}

		public void setV(String v) {
			this.v = v;
		}

	}

	public static class Grid {
		List<Point> pts;
		int size;

		public Grid(List<Point> pts) {
			super();
			this.pts = pts;
			this.size = (int) Math.sqrt(pts.size());
			;
		}

		public Point get(int i, int j) {
			for (Point p : pts) {
				if (p.x == i && p.y == j) {
					return new Point(i, j, p.v);
				}
			}

			return null;
		}

		@Override
		public int hashCode() {
			return Objects.hash(pts, size);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Grid other = (Grid) obj;
			return Objects.equals(pts, other.pts) && size == other.size;
		}

		public List<Point> getPts() {
			return pts;
		}

		public void setPts(List<Point> pts) {
			this.pts = pts;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		@Override
		public String toString() {
			String res = "";
			for (int i = 0; i < pts.size(); i++) {
				if (i != 0 && i % size == 0) {
					res += "\n";
				}
				res += pts.get(i);

			}
			return res;
		}

		public List<Grid> get8Grid() {
			List<Grid> res = new ArrayList<>();
			Grid g = new Grid(new ArrayList<>(pts));
			res.add(g);
			Grid flip = flip(g);
			res.add(flip);
			res.addAll(rotate(g));
			res.addAll(rotate(flip));
			return res;
		}

		private List<Grid> rotate(Grid grid) {
			List<Grid> res = new ArrayList<>();
			Grid g = new Grid(new ArrayList<>(grid.pts));
			res.add(r9(g));
			res.add(r9(r9(g)));
			res.add(r9(r9(r9(g))));
			return res;
		}

		private Grid r9(Grid g) {
			ArrayList<Point> resPts = new ArrayList<>();
			if (g.size == 2) {
				resPts.add(new Point(0, 0, g.pts.get(2).v));
				resPts.add(new Point(0, 1, g.pts.get(0).v));
				resPts.add(new Point(1, 0, g.pts.get(3).v));
				resPts.add(new Point(1, 1, g.pts.get(1).v));
				return new Grid(resPts);
			} else if (g.size == 3) {
				resPts.add(new Point(0, 0, g.pts.get(2).v));
				resPts.add(new Point(0, 1, g.pts.get(5).v));
				resPts.add(new Point(0, 2, g.pts.get(8).v));
				resPts.add(new Point(1, 0, g.pts.get(1).v));
				resPts.add(new Point(1, 1, g.pts.get(4).v));
				resPts.add(new Point(1, 2, g.pts.get(7).v));
				resPts.add(new Point(2, 0, g.pts.get(0).v));
				resPts.add(new Point(2, 1, g.pts.get(3).v));
				resPts.add(new Point(2, 2, g.pts.get(6).v));
				return new Grid(resPts);
			} else {
				return null;
			}
		}

		private Grid flip(Grid g) {
			ArrayList<Point> resPts = new ArrayList<>();
			if (g.size == 2) {
				resPts.add(new Point(0, 0, g.pts.get(2).v));
				resPts.add(new Point(0, 1, g.pts.get(3).v));
				resPts.add(new Point(1, 0, g.pts.get(0).v));
				resPts.add(new Point(1, 1, g.pts.get(1).v));
				return new Grid(resPts);
			} else if (g.size == 3) {
				resPts.add(new Point(0, 0, g.pts.get(6).v));
				resPts.add(new Point(0, 1, g.pts.get(7).v));
				resPts.add(new Point(0, 2, g.pts.get(8).v));
				resPts.add(new Point(1, 0, g.pts.get(3).v));
				resPts.add(new Point(1, 1, g.pts.get(4).v));
				resPts.add(new Point(1, 2, g.pts.get(5).v));
				resPts.add(new Point(2, 0, g.pts.get(0).v));
				resPts.add(new Point(2, 1, g.pts.get(1).v));
				resPts.add(new Point(2, 2, g.pts.get(2).v));
				return new Grid(resPts);
			} else {
				return null;
			}
		}

	}

	public static class Rule {
		Grid gi;
		Grid go;

		public Rule(Grid gi, Grid go) {
			super();
			this.gi = gi;
			this.go = go;
		}

		public boolean match(Grid g) {
			List<Grid> g8 = g.get8Grid();
			return g8.contains(gi);
		}

		public Grid getGi() {
			return gi;
		}

		public void setGi(Grid gi) {
			this.gi = gi;
		}

		public Grid getGo() {
			return go;
		}

		public void setGo(Grid go) {
			this.go = go;
		}

		@Override
		public String toString() {
			return print(gi) + "=>" + print(go);
		}

		private String print(Grid gx) {
			String res = "";
			for (int j = 0; j < gx.pts.size(); j++) {
				if (j != 0 && j % gx.size == 0) {
					res += "/";
				}
				res += gx.pts.get(j).v;

			}
			return res;
		}

	}

	public static List<Long> getDuration() {
		A2017Day21 d = new A2017Day21(1);
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

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

public class A2021Day4 extends A2021 {

	public A2021Day4(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day4 d = new A2021Day4(4);
		System.out.println(d.s1(true));
		System.out.println(d.s2(true));

	}

	public int s1(boolean b) {
		Map g = res1(b);
		Integer t = 0;

		Set<Integer> s = g.keySet();
		for (Integer i : s) {
			t = i;
		}
		Grid grid = (Grid) g.get(t);

		int res = 0;
		for (Case c : grid.cases) {
			if (!c.marked) {
				res += c.value;
			}
		}
		return res * t;

	}

	public int s2(boolean b) {
		Map g = res2(b);
		Integer t = 0;

		Set<Integer> s = g.keySet();
		for (Integer i : s) {
			t = i;
		}
		Grid grid = (Grid) g.get(t);

		int res = 0;
		for (Case c : grid.cases) {
			if (!c.marked) {
				res += c.value;
			}
		}
		return res * t;

	}

	public Map<Integer, Grid> res2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());

		int idres = 0;
		int tres = 0;
		Grid gres = new Grid();

		List<Integer> tirage = getTirage(input);
		List<Grid> grilles = getGrilles(input);
		List<Integer> ordreGagnant = new ArrayList<>();
		for (Integer t : tirage) {
			for (Grid g : grilles) {

				for (Case c : g.cases) {
					if (!ordreGagnant.contains(g.id)) {
						if (c.value == t) {
							c.setMarked(true);
							
						}
						if (g.gagne()) {
							if (!ordreGagnant.contains(g.id)) {
								ordreGagnant.add(g.id);
								tres = t;
								idres = g.id;
								gres = new Grid(g.cases, g.id);
							}

						}
					}
				}
			}
		}

		Map res = new HashMap<>();
		res.put(tres, gres);
		return res;

	}

	public Map<Integer, Grid> res1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());

		List<Integer> tirage = getTirage(input);
		List<Grid> grilles = getGrilles(input);

		for (Integer t : tirage) {

			for (Grid g : grilles) {

				for (Case c : g.cases) {
					if (c.value == t) {
						c.setMarked(true);
						}
					if (g.gagne()) {
						Map res = new HashMap<>();
						res.put(t, g);
						return res;
					}
				}
			}
		}
		return null;

	}

	private static List<Grid> getGrilles(List<String> input) {
		List<Grid> grilles = new ArrayList<>();
		int id = 0;
		int l = 2;
		Grid g = new Grid();
		int abs = 1;
		int ord = 1;
		while (l < input.size()) {

			if (input.get(l).equals("")) {
				id++;
				g.setId(id);
				grilles.add(g);
				g = new Grid();
				abs = 1;
				ord = 1;
				l++;
			} else {
				String[] line = input.get(l).split(" ");

				for (String s : line) {
					if (!s.equals("")) {
						Case c = new Case(false, abs, ord, Integer.parseInt(s));

						g.add(c);
						abs++;
					}
				}
				l++;
				abs = 1;
				ord++;
			}

		}
		id++;
		grilles.add(g);
		return grilles;
	}

	private static List<Integer> getTirage(List<String> input) {
		List<Integer> tirage = new ArrayList<>();
		String[] a = input.get(0).split(",");
		for (String s : a) {
			tirage.add(Integer.parseInt(s));
		}
		return tirage;
	}

	public static class Grid {
		List<Case> cases = new ArrayList<>();
		int id;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Grid(List<Case> cases, int id) {
			super();
			this.cases = cases;
			this.id = id;
		}

		public boolean gagne() {
			boolean res = false;
			for (int i = 1; i < 6; i++) {

				if (getCase(i, 1).marked && getCase(i, 2).marked && getCase(i, 3).marked && getCase(i, 4).marked
						&& getCase(i, 5).marked) {
					return true;
				}
				if (getCase(1, i).marked && getCase(2, i).marked && getCase(3, i).marked && getCase(4, i).marked
						&& getCase(5, i).marked) {
					return true;
				}
			}
			return res;
		}

		public int compteMaxAligne() {
			int res = 0;
			int cpt = 0;
			for (int i = 1; i < 6; i++) {
				for (int j = 1; j < 6; j++) {
					if (getCase(i, j).marked) {
						cpt++;
					}
				}
				if (cpt > res) {
					res = cpt;
				}
				cpt = 0;
			}
			for (int i = 1; i < 6; i++) {
				for (int j = 1; j < 6; j++) {
					if (getCase(j, i).marked) {
						cpt++;
					}
				}
				if (cpt > res) {
					res = cpt;
				}
				cpt = 0;
			}
			return res;
		}

		public void add(Case case1) {
			List<Case> cases = getCases();
			cases.add(case1);
			this.setCases(cases);

		}

		public Case getCase(int i, int j) {
			for (Case c : this.cases) {
				if (c.abs == i && c.ord == j) {
					return c;
				}
			}
			return null;
		}

		public Grid() {
			// TODO Auto-generated constructor stub
		}

		public List<Case> getCases() {
			return cases;
		}

		public void setCases(List<Case> cases) {
			this.cases = cases;
		}

		@Override
		public String toString() {
			String res = "";
			for (Case c : cases) {
				if (c.marked) {
					res += " [" + c.value + "] ";
				} else {
					res += "  " + c.value + "  ";
				}
				if (c.abs == 5) {
					res += "\n ";
				}
			}
			return res;
		}

	}

	public static class Case {
		boolean marked;
		int abs;
		int ord;
		int value;

		public boolean isMarked() {
			return marked;
		}

		public void setMarked(boolean marked) {
			this.marked = marked;
		}

		public int getAbs() {
			return abs;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public void setAbs(int abs) {
			this.abs = abs;
		}

		public int getOrd() {
			return ord;
		}

		public void setOrd(int ord) {
			this.ord = ord;
		}

		public Case(boolean marked, int abs, int ord, int value) {
			super();
			this.marked = marked;
			this.abs = abs;
			this.ord = ord;
			this.value = value;
		}

		@Override
		public String toString() {
			return "Case [marked=" + marked + ", abs=" + abs + ", ord=" + ord + ", value=" + value + "]";
		}

	}
	public static List<Long> getDuration() {
		A2021Day4 d = new A2021Day4(4);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1,endTime - startTime);
	}
	
}

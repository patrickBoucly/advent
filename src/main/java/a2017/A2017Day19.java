package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class A2017Day19 extends A2017 {

	public A2017Day19(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day19 d = new A2017Day19(19);
		// d.s1(true);
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

	public String s1(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Case> l = new ArrayList<>();
		int i = 0;
		for (String li : lignes) {
			for (int j = 0; j < li.length(); j++) {
				String carac = li.substring(j, j + 1);
				if (!carac.equals(".")) {
					l.add(new Case(i, j, li.substring(j, j + 1)));
				}
			}
			i++;
		}
		Game g = new Game(l);
		g.sol1();
		return g.message;
	}

	public int s2(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Case> l = new ArrayList<>();
		int i = 0;
		for (String li : lignes) {
			for (int j = 0; j < li.length(); j++) {
				String carac = li.substring(j, j + 1);
				if (!carac.equals(".")) {
					l.add(new Case(i, j, li.substring(j, j + 1)));
				}
			}
			i++;
		}
		Game g = new Game(l);
		if(b) {
			g.sol1();
		}else {
			g.sol1_s();
		}
		
		return g.step;

	}

	public static class Game {
		List<Case> l;
		String message;
		Case curCase;
		String dir;
		int step=1;

		public List<Case> getL() {
			return l;
		}

		public void sol1() {
			while (!curCase.s.equals("L")) {
				System.out.println(curCase+" "+dir);
				move();
				step++;
			}

		}
		public void sol1_s() {
			System.out.println(step);
			while (!curCase.s.equals("F")) {
				System.out.println(curCase+" "+dir);
				move();
				step++;
				System.out.println(step);
			}

		}

		private void move() {
			Case nextCar = null;
			if (dir.equals("S")) {
				nextCar = g(l, curCase.i + 1, curCase.j);
			} else if (dir.equals("N")) {
				nextCar = g(l, curCase.i - 1, curCase.j);
			} else if (dir.equals("E")) {
				nextCar = g(l, curCase.i, curCase.j + 1);
			} else if (dir.equals("W")) {
				nextCar = g(l, curCase.i, curCase.j - 1);
			}
			if (Character.isLetter(nextCar.s.charAt(0))) {
				message += nextCar.s;
			}
			if (nextCar.s.equals("+")) {
				
				if (dir.equals("S") || dir.equals("N")) {
					if (gc(l, nextCar.i, curCase.j - 1)) {
						Case nextCarg = g(l, nextCar.i, nextCar.j - 1);
						if (nextCarg.s.equals("-")||Character.isLetter(nextCarg.s.charAt(0))) {
							dir = "W";
						}
					} else {
						dir = "E";
					}

				} else if (dir.equals("E") || dir.equals("W")) {
					if (gc(l, nextCar.i + 1, nextCar.j)) {
						Case nextCarg = g(l, nextCar.i + 1, nextCar.j);
						if (nextCarg.s.equals("|")||Character.isLetter(nextCarg.s.charAt(0))) {
							dir = "S";
						}
					} else {
						dir = "N";
					}
				}
			}
			curCase = nextCar;

		}

		private boolean gc(List<Case> l, int i, int j) {
			if (g(l, i, j) == null) {
				return false;
			}
			return true;
		}

		private Case g(List<Case> l2, int i, int j) {
			Optional<Case> nc = l.stream().filter(c -> c.i == i && c.j == j).findAny();
			if (nc.isPresent()) {
				return nc.get();
			}
			return null;
		}

		public void setL(List<Case> l) {
			this.l = l;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getDir() {
			return dir;
		}

		public void setDir(String dir) {
			this.dir = dir;
		}

		public Game(List<Case> l) {
			super();
			this.l = l;
			this.message = "";
			this.dir = "S";
			this.curCase = l.stream().filter(c -> c.i == 0).findFirst().get();
		}

		public Case getCurCase() {
			return curCase;
		}

		public void setCurCase(Case curCase) {
			this.curCase = curCase;
		}

	}

	public static class Case {
		int i;
		int j;
		String s;

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

		public String getS() {
			return s;
		}

		public void setS(String s) {
			this.s = s;
		}

		public Case(int i, int j, String s) {
			super();
			this.i = i;
			this.j = j;
			this.s = s;
		}

		@Override
		public String toString() {
			return "Case [i=" + i + ", j=" + j + ", s=" + s + "]";
		}

	}

	public static List<Long> getDuration() {
		A2017Day19 d = new A2017Day19(1);
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

package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class A2017Day3 extends A2017 {

	public A2017Day3(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day3 d = new A2017Day3(3);
		System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public int s1(boolean z) {
		int input = 347991;
		List<Position> ln = getLN(input);
		return getVal(ln, input);
	}

	private int getVal(List<Position> ln, int input) {
		for (Position p : ln) {
			if (p.val == input) {
				return p.dist();
			}
		}
		return 0;
	}

	private List<Position> getLN(int input) {
		List<Position> ln = new ArrayList<>();
		ln.add(new Position(0, 0, 1));
		String dir = "E";
		int i = 0;
		int j = 0;
		int derval = 1;
		int qttDep = 1;
		int cptChangeqtt = 1;
		while (ln.get(ln.size() - 1).val < input) {
			Collection<? extends Position> nextPos = nextPos(i, j, dir, qttDep, derval);
			ln.addAll(nextPos);
			Position lastP = ln.get(ln.size() - 1);
			i = lastP.i;
			j = lastP.j;
			derval = lastP.val;
			cptChangeqtt++;
			if (cptChangeqtt % 2 == 1) {
				qttDep++;
			}
			dir = nextDir(dir);
		}
		return ln;
	}

	private String nextDir(String dir) {
		if (dir.equals("E")) {
			return "N";
		} else if (dir.equals("N")) {
			return "W";
		} else if (dir.equals("W")) {
			return "S";
		} else {
			return "E";
		}
	}

	private Collection<? extends Position> nextPos(int i, int j, String dir, int qttD, int derval) {
		List<Position> lp = new ArrayList<>();
		for (int k = 1; k <= qttD; k++) {
			derval++;
			if (dir.equals("E")) {
				i++;
			} else if (dir.equals("N")) {
				j++;
			} else if (dir.equals("W")) {
				i--;
			} else {
				j--;
			}
			lp.add(new Position(i, j, derval));

		}
		return lp;
	}

	public int s2(boolean b) {
		int input = 347991;
		List<Position> ln = getLN2(input);
		int res = 0;
		boolean a = false;
		for(int pos=0;pos<ln.size();pos++) {
			if(ln.get(pos).val> input) {
				return ln.get(pos).val;
			}
		}
		return res;
	}

	private List<Position> getLN2(int input) {
		List<Position> ln = new ArrayList<>();
		int sizeStep_1=ln.size();
		ln.add(new Position(0, 0, 1));
		String dir = "E";
		int i = 0;
		int j = 0;
		int qttDep = 1;
		int cptChangeqtt = 1;
		while (ln.size()<500) {
			Collection<? extends Position> nextPos = nextPos2(i, j, dir, qttDep, ln, input);
			ln = new ArrayList<>(nextPos);
			Position lastP = ln.get(ln.size() - 1);
			i = lastP.i;
			j = lastP.j;
			cptChangeqtt++;
			if (cptChangeqtt % 2 == 1) {
				qttDep++;
			}
			dir = nextDir(dir);
		}
		System.out.println(ln.stream().map(Position::getVal).collect(Collectors.toList()));
		return ln;
	}

	private Collection<? extends Position> nextPos2(int i, int j, String dir, int qttD, List<Position> ln, int input) {
		// List<Position> lp = new ArrayList<>();
		boolean add = true;
		for (int k = 1; k <= qttD; k++) {
			if (dir.equals("E")) {
				i++;
			} else if (dir.equals("N")) {
				j++;
			} else if (dir.equals("W")) {
				i--;
			} else {
				j--;
			}
			int val = getValeur(i, j, ln);
			if (val > 2*input) {
				add = false;
			}
			if (add) {
				ln.add(new Position(i, j, val));
			}
		}
		return ln;
	}

	private int getValeur(int i, int j, List<Position> ln) {
		int res = 0;
		for (Position p : ln.stream().filter(p -> distR1(p, i, j)).collect(Collectors.toList())) {
			res += p.val;
		}
		return res;
	}

	private boolean distR1(Position p, int i, int j) {
		return Math.abs(p.i - i) < 2 && Math.abs(p.j - j) < 2;
	}

	public static class Position {
		int i;
		int j;
		int val;

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

		public int getVal() {
			return val;
		}

		public void setVal(int val) {
			this.val = val;
		}

		public Position(int i, int j, int val) {
			super();
			this.i = i;
			this.j = j;
			this.val = val;
		}

		@Override
		public String toString() {
			return "Position [i=" + i + ", j=" + j + ", val=" + val + "]";
		}

		@Override
		public int hashCode() {
			return Objects.hash(i, j);
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
			return i == other.i && j == other.j;
		}

		public int dist() {
			return Math.abs(i) + Math.abs(j);
		}

	}

	public static List<Long> getDuration() {
		A2017Day3 d = new A2017Day3(1);
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

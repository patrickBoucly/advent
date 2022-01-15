package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2017Day13 extends A2017 {

	public A2017Day13(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day13 d = new A2017Day13(13);
		// d.s1(true);
		long startTime = System.currentTimeMillis();
		//System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(false));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public int s1(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Scanner> scans = new ArrayList<>();

		for (String l : lignes) {
			String[] sp = l.split(":");
			scans.add(new Scanner(Integer.parseInt(sp[0].trim()), Integer.parseInt(sp[1].trim())));
		}
		System.out.println(scans);
		int ps = 0;
		int maxS = MesOutils.getMaxIntegerFromList(scans.stream().map(Scanner::getDepth).collect(Collectors.toList()));
		int cost = 0;
		for (int k = 0; k <= maxS; k++) {
			Scanner s=getScanP(scans,ps);
			if (s !=null && s.pos == 1) {
				cost += s.depth * s.range;
			}
			ps++;
			move(scans);
		}
		return cost;

	}

	private Scanner getScanP(List<Scanner> scans, int ps) {
		for(Scanner s:scans) {
			if(s.depth==ps) {
				return s;
			}
		}
		return null;
	}

	private void move(List<Scanner> scans) {
		for (Scanner s : scans) {
			if (s.descend) {
				if (s.pos == s.range) {
					s.descend = false;
					s.pos = s.pos - 1;
				} else {
					s.pos++;
				}
			} else {
				if (s.pos == 1) {
					s.descend = true;
					s.pos = s.pos + 1;
				} else {
					s.pos--;
				}
			}
		}

	}

	public int s2(boolean b) {
		int cost = 0;
		for(int w=0;w>=-10000;w--) {
			cost=getCost(getScans(b),w);
			System.out.println(w+" "+cost);
			if(cost==0) {
				return w;
			}
		}
		return cost;
	}

	private List<Scanner> getScans(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Scanner> scans =new ArrayList<>();

		for (String l : lignes) {
			String[] sp = l.split(":");
			scans.add(new Scanner(Integer.parseInt(sp[0].trim()), Integer.parseInt(sp[1].trim())));
		}
		return scans;
	}

	private int getCost(List<Scanner> scans, int w) {
		int maxS = MesOutils.getMaxIntegerFromList(scans.stream().map(Scanner::getDepth).collect(Collectors.toList()));
		int cost=0;
		while(w<=maxS) {
		
			Scanner s=getScanP(scans,w);
			if (s !=null && s.pos == 1) {
				cost += s.depth * s.range;
			}
			move(scans);
			w++;
		}
		return cost;
	}

	public static class Scanner {
		int depth;
		int range;
		int pos;
		boolean descend;

		public int getDepth() {
			return depth;
		}

		public void setDepth(int depth) {
			this.depth = depth;
		}

		public int getRange() {
			return range;
		}

		public void setRange(int range) {
			this.range = range;
		}

		public int getPos() {
			return pos;
		}

		public void setPos(int pos) {
			this.pos = pos;
		}

		public boolean isDescend() {
			return descend;
		}

		public void setDescend(boolean descend) {
			this.descend = descend;
		}

		public Scanner(int depth, int range) {
			super();
			this.depth = depth;
			this.range = range;
			this.pos = 1;
			this.descend = true;
		}

		@Override
		public String toString() {
			return "Scanner [depth=" + depth + ", range=" + range + ", pos=" + pos + ", descend=" + descend + "]";
		}

	}

	public static List<Long> getDuration() {
		A2017Day13 d = new A2017Day13(1);
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

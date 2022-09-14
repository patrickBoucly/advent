package a2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A2016Day7 extends A2016 {
	public A2016Day7(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day7 d = new A2016Day7(7);
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

	private Long s1(boolean b) {
		return Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim()).map(s -> new IP(s))
				.filter(IP::isTLS).count();
	}

	private Long s2(boolean b) {
		return Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim()).map(s -> new IP(s))
				.filter(IP::isSSL).count();
	}

	private class IP {
		String ip;

		public IP(String ip) {
			super();
			this.ip = ip;
		}

		public boolean isTLS() {
			String ip = this.ip;
			List<String> inB = new ArrayList<>();
			List<String> outB = new ArrayList<>();
			String[] splitted = ip.split("\\[|\\]");
			boolean abbaDansOut = false;
			boolean abbaDansIn = false;
			for (int i = 0; i < splitted.length; i++) {
				if (i % 2 == 0) {
					outB.add(splitted[i]);
				} else {
					inB.add(splitted[i]);
				}
			}
			for (String s : splitted) {
				if (contient4Ident(s)) {
					return false;
				}
			}
			for (String s : outB) {
				if (contientAbba(s)) {
					abbaDansOut = true;
				}
			}
			for (String s : inB) {
				if (contientAbba(s)) {
					abbaDansIn = true;
				}
			}
			if (!abbaDansOut | abbaDansIn) {
				return false;
			}
			return true;
		}

		public boolean isSSL() {
			String ip = this.ip;
			List<String> inB = new ArrayList<>();
			List<String> outB = new ArrayList<>();
			String[] splitted = ip.split("\\[|\\]");
			boolean abbaDansOut = false;
			boolean abbaDansIn = false;
			for (int i = 0; i < splitted.length; i++) {
				if (i % 2 == 0) {
					outB.add(splitted[i]);
				} else {
					inB.add(splitted[i]);
				}
			}
			List<String> triOut = getTriplet(outB);
			List<String> triIn = getTriplet(inB);
			for (String in : triIn) {
				for (String out : triOut) {
					if (triSym(in, out)) {
						return true;
					}
				}
			}
			if (!abbaDansOut | abbaDansIn) {
				return false;
			}
			return true;
		}

		private boolean triSym(String in, String out) {
			String i1 = in.substring(0, 1);
			String i2 = in.substring(1, 2);
			String i3 = in.substring(2, 3);
			String o1 = out.substring(0, 1);
			String o2 = out.substring(1, 2);
			String o3 = out.substring(2, 3);
			return !i1.equals(i2) && i1.equals(o2) && i2.equals(o3) && i3.equals(o2) && o1.equals(o3) && i1.equals(i3);
		}

		private List<String> getTriplet(List<String> l) {
			List<String> triplets = new ArrayList<>();
			for (String s : l) {
				for (int i = 0; i <= s.length() - 3; i++) {
					triplets.add(s.substring(i, i + 3));
				}
			}
			return triplets;
		}

		private boolean contientAbba(String s) {
			for (int i = 0; i <= s.length() - 4; i++) {
				String sub = s.substring(i, i + 4);
				String l1 = sub.substring(0, 1);
				String l2 = sub.substring(1, 2);
				String l3 = sub.substring(2, 3);
				String l4 = sub.substring(3);
				if (l1.equals(l4) && l2.equals(l3)) {
					return true;
				}
			}
			return false;
		}

		private boolean contient4Ident(String s) {
			for (int i = 0; i <= s.length() - 4; i++) {
				String sub = s.substring(i, i + 4);
				String l1 = sub.substring(0, 1);
				String l2 = sub.substring(1, 2);
				String l3 = sub.substring(2, 3);
				String l4 = sub.substring(3);
				if (l1.equals(l4) && l2.equals(l3) && l1.equals(l2)) {
					return true;
				}
			}
			return false;
		}

	}

	public static List<Long> getDuration() {
		A2016Day7 d = new A2016Day7(7);
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

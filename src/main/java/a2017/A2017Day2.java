package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2017Day2 extends A2017 {

	public A2017Day2(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day2 d = new A2017Day2(2);
		// System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public int s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		int res = 0;
		for (String l : inputL) {
			String[] sp = l.split("\t");
			res += calLigne(sp);
		}
		return res;
	}

	private int calLigne(String[] sp) {
		List<Integer> nbs = new ArrayList<>();
		for (String s : sp) {
			nbs.add(Integer.parseInt(s.trim()));
		}
		return MesOutils.getMaxIntegerFromList(nbs) - MesOutils.getMinIntegerFromList(nbs);
	}

	public int s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		int res = 0;
		for (String l : inputL) {
			String[] sp = l.split("\t");
			res += calLigne2(sp);
		}
		return res;
	}

	private int calLigne2(String[] sp) {
		List<Integer> nbs = new ArrayList<>();
		for (String s : sp) {
			nbs.add(Integer.parseInt(s.trim()));
		}
		for (Integer nb1 : nbs) {
			for (Integer nb2 : nbs) {
				if (nb1 != nb2) {
					if (nb1 % nb2 == 0) {
						return nb1 / nb2;
					}
					if (nb2 % nb1 == 0) {
						return nb2 / nb1;
					}
				}
			}
		}
		return 0;
	}

	public static List<Long> getDuration() {
		A2017Day2 d = new A2017Day2(1);
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

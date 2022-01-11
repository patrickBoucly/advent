package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class A2017Day6 extends A2017 {

	public A2017Day6(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day6 d = new A2017Day6(6);
		// d.s1(true);
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

	public int s1(boolean b) {
		List<Integer> inst = Arrays.asList(getInput(b).split("\t")).stream().map(String::trim).map(Integer::parseInt)
				.collect(Collectors.toList());
		return getRes(inst);
	}

	private int getRes(List<Integer> inst) {
		HashMap<Integer, List<Integer>> histo = new HashMap<>();
		histo.put(0, inst);
		int nbOp = 1;
		while (true) {
			List<Integer> newRepart = getNR(inst, nbOp);
			if (histo.containsValue(newRepart)) {
				return nbOp;
			} else {
				histo.put(nbOp, newRepart);
				inst = newRepart;
				nbOp++;
			}
		}
	}

	private List<Integer> getNR(List<Integer> inst, int nbOp) {
		List<Integer>l=new ArrayList<>(inst);
		int valMax = -1;
		int posMax = -1;
		for(int i=0;i<l.size();i++) {
			if(l.get(i)>valMax) {
				valMax=l.get(i);
				posMax=i;
			}
		}
		l.set(posMax, 0);
		for(int j=0;j<valMax;j++) {
			int posAAlim=(posMax+1+j)%l.size();
			l.set(posAAlim, l.get(posAAlim)+1);
		}
		return l;
	}

	public int s2(boolean b) {
		List<Integer> inst = Arrays.asList(getInput(b).split("\t")).stream().map(String::trim).map(Integer::parseInt)
				.collect(Collectors.toList());
		return getRes2(inst);
	}

	private int getRes2(List<Integer> inst) {
		HashMap<Integer, List<Integer>> histo = new HashMap<>();
		histo.put(0, inst);
		int nbOp = 1;
		while (true) {
			List<Integer> newRepart = getNR(inst, nbOp);
			if (histo.containsValue(newRepart)) {
				for(Integer i:histo.keySet()) {
					if(histo.get(i).equals(newRepart)){
						return nbOp-i;
					}
				}
				
			} else {
				histo.put(nbOp, newRepart);
				inst = newRepart;
				nbOp++;
			}
		}
	}

	public static List<Long> getDuration() {
		A2017Day6 d = new A2017Day6(1);
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

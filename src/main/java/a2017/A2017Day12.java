package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class A2017Day12 extends A2017 {

	public A2017Day12(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day12 d = new A2017Day12(12);
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
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		HashMap<Integer, List<Integer>> prgs = new HashMap<>();
		for (String s : lignes) {
			String[] sp = s.split("<->");
			List<Integer> l = new ArrayList<>();
			String[] sp2 = sp[1].trim().split(",");
			for (String li : sp2) {
				l.add(Integer.parseInt(li.trim()));
			}
			prgs.put(Integer.parseInt(sp[0].trim()), l);
		}
		Set<Integer> lieePg0 = new HashSet<>();
		lieePg0.add(0);
		lieePg0.addAll(prgs.get(0));
		boolean start = true;
		Set<Integer> nlieePg0 = new HashSet<>(lieePg0);
		while (nlieePg0.size() > lieePg0.size() || start) {
			start = false;
			lieePg0 = new HashSet<>(nlieePg0);
			for (Integer i : lieePg0) {
				nlieePg0.addAll(prgs.get(i));
			}
		}
		return lieePg0.size();

	}

	public int s2(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		HashMap<Integer, List<Integer>> prgs = new HashMap<>();
		for (String s : lignes) {
			String[] sp = s.split("<->");
			List<Integer> l = new ArrayList<>();
			String[] sp2 = sp[1].trim().split(",");
			for (String li : sp2) {
				l.add(Integer.parseInt(li.trim()));
			}
			prgs.put(Integer.parseInt(sp[0].trim()), l);
		}
		Set<Integer> lieePg0 = new HashSet<>();
		lieePg0.add(0);
		lieePg0.addAll(prgs.get(0));
		boolean start = true;
		Set<Integer> nlieePg0 = new HashSet<>(lieePg0);
		while (nlieePg0.size() > lieePg0.size() || start) {
			start = false;
			lieePg0 = new HashSet<>(nlieePg0);
			for (Integer i : lieePg0) {
				nlieePg0.addAll(prgs.get(i));
			}
		}
		HashMap<Integer,Set<Integer>> grps=new HashMap<>();
		grps.put(0, nlieePg0);
		while(grps.values().stream().map(x->x.size()).reduce(0,(a,c)->a+c)<prgs.size()) {
			grps=ng(prgs,grps);
			//System.out.println(grps);
		}
		return grps.keySet().size();
	}

	private HashMap<Integer, Set<Integer>> ng(HashMap<Integer, List<Integer>> prgs, HashMap<Integer, Set<Integer>> grps) {
		Set<Integer> ds1grp = new HashSet<>();
		for(Integer i:grps.keySet()) {
			ds1grp.addAll(grps.get(i));
		}
		boolean t=false;
		int ngNum=-1;
		List<Integer> knowPG=new ArrayList<>();
		for(Set<Integer> k:grps.values().stream().collect(Collectors.toList())) {
			for(Integer w:k) {
				knowPG.add(w);
			}
		}
		while(!t) {
			for(Integer j:prgs.keySet()) {
				if(!knowPG.contains(j)) {
					ngNum=j;
					t=true;
				}
			}
		}
		Set<Integer> lieePgj = new HashSet<>();
		lieePgj.add(ngNum);
		lieePgj.addAll(prgs.get(ngNum));
		boolean start = true;
		Set<Integer> nlieePgj = new HashSet<>(lieePgj);
		while (nlieePgj.size() > lieePgj.size() || start) {
			start = false;
			lieePgj = new HashSet<>(nlieePgj);
			for (Integer i : lieePgj) {
				nlieePgj.addAll(prgs.get(i));
			}
		}
		grps.put(ngNum, lieePgj);
		return grps;
	}

	public static List<Long> getDuration() {
		A2017Day12 d = new A2017Day12(1);
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

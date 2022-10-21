package a2016;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A2016Day16 extends A2016 {
	public A2016Day16(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day16 d = new A2016Day16(15);
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

	public Long s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Disc> discs = getDiscs(input);
		Long test=-1L;
		boolean win=false;
		while(!win) {
			test++;
			win=tester(test,discs);
			
		}
		return test;
	}

	private List<Disc> getDiscs(List<String> input) {
		List<Disc> discs = new ArrayList<>();
		for (String s : input) {
			String[] split = s.split(" ");
			discs.add(
					new Disc(Integer.valueOf(split[3]), Integer.valueOf(split[11].substring(0, split[11].length() - 1)),
							Integer.valueOf(split[1].substring(1))));
		}
		return discs;
	}
	private boolean tester(Long test, List<Disc> discs) {
		for(Disc d:discs) {
			if(((test+d.id+d.curPos)%d.nbPos!=0)) {
				return false;
			}
		}
		return true;
	}


	public Long s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Disc> discs = getDiscs(input);
		discs.add(new Disc(11,0,discs.size()+1));
		Long test=-1L;
		boolean win=false;
		while(!win) {
			test++;
			win=tester(test,discs);
			
		}
		return test;
	}

	private static class Disc{
		int nbPos;
		int curPos;
		int id;
		public Disc(int nbPos, int curPos, int id) {
			super();
			this.nbPos = nbPos;
			this.curPos = curPos;
			this.id = id;
		}
		@Override
		public String toString() {
			return "Disc [nbPos=" + nbPos + ", curPos=" + curPos + ", id=" + id + "]";
		}
		
	}


	public static List<Long> getDuration() {
		A2016Day16 d = new A2016Day16(15);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		//d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1, 3755797L);
	}

}

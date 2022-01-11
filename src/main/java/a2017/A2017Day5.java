package a2017;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class A2017Day5 extends A2017 {

	public A2017Day5(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day5 d = new A2017Day5(5);
		// d.s1(true);
		long startTime = System.currentTimeMillis();
		//System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1=endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day "+ d.day+" run 1 took "+timeS1+" milliseconds, run 2 took " + (endTime - startTime) + " milliseconds");
		
	}

	public int s1(boolean b) {
		List<Integer> inst = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).map(Integer::parseInt).collect(Collectors.toList());
		int curpos=0;
		int cpt=0;
		while(curpos<inst.size()) {
			int oldPos=curpos;
			curpos+=inst.get(curpos);
			inst.set(oldPos, inst.get(oldPos)+1);
			cpt++;
		}
		return cpt;
	}
	public int s2(boolean b) {
		List<Integer> inst = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).map(Integer::parseInt).collect(Collectors.toList());
		int curpos=0;
		int cpt=0;
		while(curpos<inst.size()) {
			int oldPos=curpos;
			int offset=inst.get(curpos);
			curpos+=offset;
			int dif =(offset>2) ? -1:1;
			inst.set(oldPos, inst.get(oldPos)+dif);
			cpt++;
		}
		return cpt;
	}



	public static List<Long> getDuration() {
		A2017Day5 d = new A2017Day5(1);
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

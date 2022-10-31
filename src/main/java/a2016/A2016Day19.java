package a2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2016Day19 extends A2016 {

	public A2016Day19(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day19 d = new A2016Day19(19);
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

	private Integer s2(boolean b) {
		int size = 3014387;
		ArrayList<Integer> l = new ArrayList<>();
		for (int i = 0; i <= size - 1; i=i+1) {
			l.add(i + 1);
		}
		size=l.size();
		int curPos = 0;
		int posEnFace=0;
		int cptRemove=0;
		int curVal=1;
		while (l.stream().count() != 1L) {
			posEnFace=((size)/2+curPos)%l.size();
			l.remove(posEnFace);
			size=l.size();
			curPos = (l.indexOf(curVal)+1)%l.size();
			curVal=l.get(curPos);
			cptRemove++;
			if(cptRemove%100000==0) {
			System.out.println(size);
			}
		}
		System.out.println(l);
		return l.stream().filter(e -> !e.equals(0)).findFirst().get();
	}

	public long s1(boolean b) {
		int size = 3014387;
		ArrayList<Integer> l = new ArrayList<>();
		for (int i = 0; i <= size - 1; i=i+2) {
			l.add(i + 1);
		}
		size=l.size();
		int curPos = l.size()-1;
		int cptRemove=0;
		while (l.stream().count() != 1L) {
			curPos = (curPos+1)%size;
			l.remove(curPos);
			size=l.size();
			cptRemove++;
			if(cptRemove%10000==0) {
			System.out.println(size);
			}
		}

		return l.stream().filter(e -> !e.equals(0)).findFirst().get();
	}

	

	public static List<Long> getDuration() {
		A2016Day19 d = new A2016Day19(19);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(3755797L,10028548L);
	}

}

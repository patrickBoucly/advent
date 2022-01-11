package a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A2021Day1 extends A2021 {

	public A2021Day1(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day1 d = new A2021Day1(1);
		// d.s1(true);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1=endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		System.out.println("Day "+ d.day+" run 1 took "+timeS1+" milliseconds, run 2 took " + (endTime - startTime) + " milliseconds");
		
	}

	public int s1(boolean b) {
		List<Integer> inputL=Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).map(Integer::parseInt).collect(Collectors.toList());
		return getNbIncrease(inputL);
	}

	private static int getNbIncrease(List<Integer> inputL) {
		int res=0;
		for(int i=1;i<inputL.size();i++) {
			if(inputL.get(i-1)<inputL.get(i)){
				res++;
			}
		}
		return res;
	}
	
	public int s2(boolean b) {
		List<Integer> inputL=Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).map(Integer::parseInt).collect(Collectors.toList());
		List<Integer> sommeDe3= new ArrayList<>();
		for(int i=0;i<inputL.size()-2;i++) {
			sommeDe3.add(inputL.get(i)+inputL.get(i+1)+inputL.get(i+2));
		}
		return getNbIncrease(sommeDe3);
		
	}
	public static List<Long> getDuration() {
		A2021Day1 d = new A2021Day1(1);
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

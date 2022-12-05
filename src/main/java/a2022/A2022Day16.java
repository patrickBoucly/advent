package a2022;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A2022Day16 extends A2022 {

	public A2022Day16(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day16 d = new A2022Day16(16);
		System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1=endTime - startTime;
		startTime = System.currentTimeMillis();
		//System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day "+ d.day+" run 1 took "+timeS1+" milliseconds, run 2 took " + (endTime - startTime) + " milliseconds");
		
	}

	public String s2(boolean b) {
		List<String> input=Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		return null;
	}

	public String s1(boolean b) {
		List<String> input=Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		return null;
	}

	public static List<Long> getDuration() {
		A2022Day16 d = new A2022Day16(16);
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

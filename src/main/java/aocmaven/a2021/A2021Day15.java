package aocmaven.a2021;

import java.util.Arrays;
import java.util.List;

public class A2021Day15 extends A2021 {

	public A2021Day15(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day15 d = new A2021Day15(15);
		// d.s1(true);
		long startTime = System.currentTimeMillis();
		// d.s1(false);
		System.out.println(d.s1(false));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();

	//	System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	private long s2(boolean b) {
		return 0L;
	}
	private long s1(boolean b) {
		return 0L;
	}

	
	public static List<Long> getDuration() {
		A2021Day15 d = new A2021Day15(15);
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

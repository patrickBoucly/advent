package a2017;

import java.util.Arrays;
import java.util.List;


public class A2017Day21 extends A2017 {

	public A2017Day21(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day21 d = new A2017Day21(1);
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
		return 0;
	}
	public int s2(boolean b) {
		return 0;
	}



	public static List<Long> getDuration() {
		A2017Day21 d = new A2017Day21(1);
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

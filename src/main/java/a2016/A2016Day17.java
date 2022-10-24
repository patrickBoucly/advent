package a2016;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class A2016Day17 extends A2016 {
	String input = "10001001100000001";

	public A2016Day17(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day17 d = new A2016Day17(17);
		long startTime = System.currentTimeMillis();
		System.out.println(d.s1(false));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}



	private char[] s1(boolean b) {
		String input="ioramepc";
		if(!b) {
			input="ihgpwlah";
		}
		return null;
	}

	public String s2(boolean b) {
		
	
		return "";
	}

	public static List<Long> getDuration() {
		A2016Day17 d = new A2016Day17(17);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		// d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1, 3755797L);
	}

}

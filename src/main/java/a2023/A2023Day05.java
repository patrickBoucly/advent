package a2023;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A2023Day05 extends A2023 {

	public A2023Day05(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day05 d = new A2023Day05(5);
		System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public int s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		System.out.println(inputL);

		return 0;
	}

	public int s2(boolean b) {
		return 0;

	}

	public static List<Long> getDuration() {
		A2023Day05 d = new A2023Day05(5);
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

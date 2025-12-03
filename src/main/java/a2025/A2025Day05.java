package a2025;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import lombok.Data;

public class A2025Day05 extends A2025 {
	public A2025Day05(int day) {
		super(day);
	}
	public static void main(String[] args0) {
		A2025Day05 d = new A2025Day05(3);
		System.out.println(d.s1(false));
		//System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		//d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		//System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
	//	System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
		//		+ (endTime - startTime) + " milliseconds");
	}

	public Integer s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Game g = new Game(inputL);
		return g.solve1();
	}

	public Long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Game g = new Game(inputL);
		return g.solve2();
	}
	@Data
	@AllArgsConstructor
	public static class Game {
		List<String> inputL;
		public Integer solve1() {
			int r = 0;			
			return r;
		}
		public Long solve2() {
			Long r = 0L;
			return r;
		}
	}

	public static List<Long> getDuration() {
		A2025Day05 d = new A2025Day05(3);
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
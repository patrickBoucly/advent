package a2025;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

public class A2025Day01 extends A2025 {

	public A2025Day01(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2025Day01 d = new A2025Day01(1);
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
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(s -> s.trim()).toList();
		Game g = new Game();
		g.setInstFromInput(inputL);
		return g.solve1();
	}

	public int s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(s -> s.trim()).toList();
		Game g = new Game();
		g.setInstFromInput(inputL);
		return g.solve2();

	}

	public static class Game {
		List<Instruction> ins;

		public void setInstFromInput(List<String> input) {
			ins = new LinkedList<>();
			for (String l : input) {
				boolean c = l.substring(0, 1).equals("R");
				int value = Integer.parseInt(l.substring(1));
				Instruction i = new Instruction(c, value);
				ins.add(i);
			}
		}

		public Integer solve1() {
			int pos = 50;
			int res = 0;
			for (Instruction i : ins) {
				if (i.croissant) {
					pos += i.valeur;

				} else {
					pos -= i.valeur;
				}
				if (pos % 100 == 0) {
					res++;
				}
			}
			return res;
		}
		public Integer solve2() {
			int pos = 50;
			int res = 0;
			for (Instruction i : ins) {
				if (i.croissant) {
					for(int j=0;j<i.valeur;j++) {
						pos += 1;
						if (pos % 100 == 0) {
							res++;
						}
					}
					

				} else {
					for(int j=0;j<i.valeur;j++) {
						pos -= 1;
						if (pos % 100 == 0) {
							res++;
						}
					}
				}				
			}
			return res;
		}
	}

	@Data
	@AllArgsConstructor
	public static class Instruction {
		boolean croissant;
		int valeur;

	}

	public static List<Long> getDuration() {
		A2025Day01 d = new A2025Day01(1);
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

package a2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

public class A2016Day21 extends A2016 {

	public A2016Day21(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day21 d = new A2016Day21(21);
		long startTime = System.currentTimeMillis();
		System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	private String s2(boolean b) {
		List<String> inst = Arrays.asList(getInput(b).trim().split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Inst> insts = inst.stream().map(Inst::new).collect(Collectors.toList());
		System.out.println(insts);
		String fin = "fbgdceah";
		
	
		while (true) {
			List<String> input=Arrays.asList("a","b","c","d","e","f","g","h");
			Collections.shuffle(input);
			String inputf=input.stream().collect(Collectors.joining(""));
			for (Inst i : insts) {
				
				inputf = i.apply(inputf);
				
				
				
			}
			if(inputf.equals(fin)) {
				return input.stream().collect(Collectors.joining(""));
			}
		}


	}

	public String s1(boolean b) {
		List<String> inst = Arrays.asList(getInput(b).trim().split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Inst> insts = inst.stream().map(Inst::new).collect(Collectors.toList());
		System.out.println(insts);
		String input = "abcde";
		if (b) {
			input = "gbcdhafe";
		}
		System.out.println(insts);
		for (Inst i : insts) {
			input = i.apply(input);
			System.out.println(input);
		}
		return input;
	}

	private class Inst {
		String type;
		String p1;
		String p2;

		public Inst(String line) {
			super();
			String[] s = line.split(" ");
			if (s[0].equals("swap")) {
				if (s[1].equals("position")) {
					type = "swapPosition";
					p1 = s[2];
					p2 = s[5];
				} else {
					type = "swapLetter";
					p1 = s[2];
					p2 = s[5];
				}
			}
			if (s[0].equals("rotate")) {
				if (s[1].equals("left")) {
					type = "rotateLeft";
					p1 = s[2];
					p2 = null;
				} else if (s[1].equals("right")) {
					type = "rotateRight";
					p1 = s[2];
					p2 = null;
				} else {
					type = "rotateBased";
					p1 = s[6];
					p2 = null;
				}
			}

			if (s[0].equals("reverse")) {
				type = "reverse";
				p1 = s[2];
				p2 = s[4];

			}
			if (s[0].equals("move")) {
				type = "move";
				p1 = s[2];
				p2 = s[5];
			}
		}

		public String apply(String input) {
			if (type.equals("swapPosition")) {
				return applySwapPosition(input);
			}
			if (type.equals("swapLetter")) {
				return applySwapLetter(input);
			}
			if (type.equals("rotateLeft")) {
				return applyRotateLeft(input);
			}
			if (type.equals("rotateRight")) {
				return applyRotateRight(input);
			}
			if (type.equals("rotateBased")) {
				return applyRotateBased(input);
			}
			if (type.equals("reverse")) {
				return applyReverse(input);
			}
			if (type.equals("move")) {
				return applyMove(input);
			}
			return null;
		}
		/*
		 * move position X to position Y means that the letter which is at index X
		 * should be removed from the string, then inserted such that it ends up at
		 * index Y.
		 */

		private String applyMove(String input) {
			List<String> chars = Stream.of(input.split("(?<=\\G.{1})")).collect(Collectors.toList());
			String letterPosX = chars.get(Integer.parseInt(p1));
			chars.remove(Integer.parseInt(p1));
			chars.add(Integer.parseInt(p2), letterPosX);
			return chars.stream().collect(Collectors.joining(""));
		}

		private String applyReverse(String input) {
			return input.substring(0, Integer.parseInt(p1))
					+ StringUtils.reverse(input.substring(Integer.parseInt(p1), Integer.parseInt(p2) + 1))
					+ input.substring(Integer.parseInt(p2) + 1);
		}

		private String applyRotateBased(String input) {
			int dec = input.indexOf(p1) > 3 ? input.indexOf(p1) + 2 : input.indexOf(p1) + 1;
			return StringUtils.rotate(input, dec);
		}

		private String applyRotateRight(String input) {
			return StringUtils.rotate(input, Integer.parseInt(p1));
		}

		private String applyRotateLeft(String input) {
			return StringUtils.rotate(input, -Integer.parseInt(p1));
		}

		private String applySwapLetter(String input) {
			List<String> chars = Stream.of(input.split("(?<=\\G.{1})")).collect(Collectors.toList());
			int posLetterPosX = chars.indexOf(p1);
			int posLetterPosY = chars.indexOf(p2);
			chars.set(posLetterPosX, p2);
			chars.set(posLetterPosY, p1);
			return chars.stream().collect(Collectors.joining(""));
		}

		private String applySwapPosition(String input) {
			List<String> chars = Stream.of(input.split("(?<=\\G.{1})")).collect(Collectors.toList());
			String letterPosX = chars.get(Integer.parseInt(p1));
			String letterPosY = chars.get(Integer.parseInt(p2));
			chars.set(Integer.parseInt(p1), letterPosY);
			chars.set(Integer.parseInt(p2), letterPosX);
			return chars.stream().collect(Collectors.joining(""));
		}

		@Override
		public String toString() {
			if (p2 != null) {
				return "Inst [type=" + type + ", p1=" + p1 + ", p2=" + p2 + "]";
			}
			return "Inst [type=" + type + ", p1=" + p1 + "]";
		}

	}

	public static List<Long> getDuration() {
		A2016Day21 d = new A2016Day21(21);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(3755797L, 10028548L);
	}

}

package aocmaven.a2018;

import static org.apache.commons.io.FileUtils.readFileToString;
import java.util.stream.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Day2A2018 {

	public static void main(String[] args) {
	//	s1();
		s2();

	}

	private static void s2() {
		String message = getMessage(
				"C:\\Users\\z11r88\\eclipse-workspace2\\advent_of_code\\src\\advent_of_code\\main\\resources\\a2018\\input2");
		Map<String, Map<String, Long>> letterAndCount = new HashMap<>();
		
		for (String s : message.split("\n")) {
			Map<String, Long> charCount = IntStream.range(0, s.length()).mapToObj(i -> s.substring(i, i + 1))
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
			letterAndCount.put(s, charCount);
		}
		boolean trouver=false;
		for(String s1:letterAndCount.keySet()) {
			for(String s2:letterAndCount.keySet()) {
				int diff=0;
				if(!s1.equals(s2)) {
					for(int i=0;i<s1.length();i++) {
						if(!s1.subSequence(i, i+1).equals(s2.subSequence(i, i+1))){
							diff++;
						}
					}
				}
				if(diff==1) {
					System.out.println(s1);
					System.out.println(s2);
			}
				//kbqwtcvzhmhpoelrnaxydifyb
			}
		}

	}

	private static void s1() {
		String message = getMessage(
				"C:\\Users\\z11r88\\eclipse-workspace2\\advent_of_code\\src\\advent_of_code\\main\\resources\\a2018\\input2");

		Map<Integer, Integer> res = new HashMap<>();
		res.put(2, 0);
		res.put(3, 0);

		for (String s : message.split("\n")) {
			boolean cpt2 = false;
			boolean cpt3 = false;
			Map<String, Long> charCount = IntStream.range(0, s.length()).mapToObj(i -> s.substring(i, i + 1))
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
			for (String lettre : charCount.keySet()) {
				if (charCount.get(lettre) == 2 && !cpt2) {
					res.put(2, res.get(2) + 1);
					cpt2 = true;
				} else if (charCount.get(lettre) == 3 && !cpt3) {
					res.put(3, res.get(3) + 1);
					cpt3 = true;
				}
			}
		}
		Integer sol = res.get(3) * res.get(2);
		System.out.println(sol);

	}

	private static String getMessage(String monFic) {
		String line = "";
		try {
			line = readFileToString(new File(monFic));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return line;
	}
}

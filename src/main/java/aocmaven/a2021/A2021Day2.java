package aocmaven.a2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A2021Day2 extends A2021 {

	public A2021Day2(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day2 d = new A2021Day2(2);
		System.out.println(d.s1(true));
		 System.out.println( d.s2(true));
		
	}

	public int s1(boolean b) {
		List<String> input=Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());

		int f = 0;
		int a = 0;
		for (String s : input) {
			if (s.substring(0, 1).equals("f")) {
				f += Integer.parseInt(((String)((String[]) s.split(" "))[1]).trim());
			} else if (s.substring(0, 1).equals("d")) {
				a += Integer.parseInt(((String)((String[]) s.split(" "))[1]).trim());
			} else {
				a -= Integer.parseInt(((String)((String[]) s.split(" "))[1]).trim());
			}
		}
		int resStreamFunction =sumLetter("f",b)*(sumLetter("d",b)-sumLetter("u",b));
		return resStreamFunction;
	}
	
	public int sumLetter(String letter,boolean b) {
		return Arrays.asList(getInput(b).split("\n")).stream().filter(s -> s.substring(0, 1).equals(letter))
				.map(s -> ((String[]) s.split(" "))[1]).map(String::trim).map(Integer::parseInt)
				.collect(Collectors.summingInt(Integer::intValue));
	}

	public int s2(boolean b) {
		List<String> input=Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		int f = 0;
		int a = 0;
		int v = 0;
		for (String s : input) {
			if (s.substring(0, 1).equals("f")) {
				f += Integer.parseInt(((String) ((String[]) s.split(" "))[1]).trim());
				a += v * Integer.parseInt(((String)((String[]) s.split(" "))[1]).trim());
			} else if (s.substring(0, 1).equals("d")) {
				v += Integer.parseInt(((String)((String[]) s.split(" "))[1]).trim());
			} else {
				v -= Integer.parseInt(((String)((String[]) s.split(" "))[1]).trim());
			}
		}
		 return (f * a);
		
	}
	public static List<Long> getDuration() {
		A2021Day2 d = new A2021Day2(2);
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

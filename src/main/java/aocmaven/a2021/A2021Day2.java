package aocmaven.a2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A2021Day2 {

	public static void main(String[] args0) {
		 s1();
		//s2();
	}

	private static void s1() {
		List<String> input;
		input = Arrays.asList(read().split("\n"));
		int f = 0;
		int a = 0;
		for (String s : input) {
			if (s.substring(0, 1).equals("f")) {
				f += Integer.parseInt(((String[]) s.split(" "))[1]);
			} else if (s.substring(0, 1).equals("d")) {
				a += Integer.parseInt(((String[]) s.split(" "))[1]);
			} else {
				a -= Integer.parseInt(((String[]) s.split(" "))[1]);
			}
		}
		System.out.println(f * a);
		int resStream = Arrays.asList(read().split("\n")).stream().filter(s -> s.substring(0, 1).equals("f"))
				.map(s -> ((String[]) s.split(" "))[1]).map(Integer::parseInt)
				.collect(Collectors.summingInt(Integer::intValue))
				* (Arrays.asList(read().split("\n")).stream().filter(s -> s.substring(0, 1).equals("d"))
						.map(s -> ((String[]) s.split(" "))[1]).map(Integer::parseInt)
						.collect(Collectors.summingInt(Integer::intValue))
						- Arrays.asList(read().split("\n")).stream().filter(s -> s.substring(0, 1).equals("u"))
								.map(s -> ((String[]) s.split(" "))[1]).map(Integer::parseInt)
								.collect(Collectors.summingInt(Integer::intValue)));
		System.out.println(resStream);
		int resStreamFunction =sumLetter("f")*(sumLetter("d")-sumLetter("u"));
		System.out.println(resStreamFunction);
	}
	
	public static int sumLetter(String letter) {
		return Arrays.asList(read().split("\n")).stream().filter(s -> s.substring(0, 1).equals(letter))
				.map(s -> ((String[]) s.split(" "))[1]).map(Integer::parseInt)
				.collect(Collectors.summingInt(Integer::intValue));
	}

	private static void s2() {
		List<String> input;
		input = Arrays.asList(read().split("\n"));
		int f = 0;
		int a = 0;
		int v = 0;
		for (String s : input) {
			if (s.substring(0, 1).equals("f")) {
				f += Integer.parseInt(((String[]) s.split(" "))[1]);
				a += v * Integer.parseInt(((String[]) s.split(" "))[1]);
			} else if (s.substring(0, 1).equals("d")) {
				v += Integer.parseInt(((String[]) s.split(" "))[1]);
			} else {
				v -= Integer.parseInt(((String[]) s.split(" "))[1]);
			}
		}
		System.out.println(f * a);
		
	}

	private static String read() {
		Path path = Paths.get(
				"C:\\git_repositories\\advent\\src\\main\\resources\\src\\advent_of_code\\main\\resources\\a2021\\input2");
		String content = "";
		try {
			content = Files.readString(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;

	}
}

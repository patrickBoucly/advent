package a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A2021Day3 extends A2021 {

	public A2021Day3(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day3 d = new A2021Day3(3);
		System.out.println(d.s1(true));
		System.out.println(d.s2(true));
	}

	public int s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).collect(Collectors.toList());
		return Integer.parseInt(getGamma(input), 2) * Integer.parseInt(getEpsilon(input), 2);
	}

	private static String getEpsilon(List<String> input) {
		String epsilon = "";
		for (int pos = 0; pos < input.get(0).length(); pos++) {
			int nb0 = 0;
			int nb1 = 0;
			for (String s : input) {
				if (s.substring(pos, pos + 1).equals("1")) {
					nb1++;
				} else {
					nb0++;
				}
			}
			if (nb1 < nb0) {
				epsilon += "1";
			} else {
				epsilon += "0";
			}
		}
		return epsilon;
	}

	private static String getGamma(List<String> input) {
		String gamma = "";
		for (int pos = 0; pos < input.get(0).length(); pos++) {
			int nb0 = 0;
			int nb1 = 0;
			for (String s : input) {
				if (s.substring(pos, pos + 1).equals("1")) {
					nb1++;
				} else {
					nb0++;
				}
			}
			if (nb1 > nb0) {
				gamma += "1";
			} else {
				gamma += "0";
			}
		}
		return gamma;
	}

	private static String getPlusCourante(List<String> input, Integer pos) {
		String plusCourante = "";

		int nb0 = 0;
		int nb1 = 0;
		for (String s : input) {
			if (s.substring(pos, pos + 1).equals("1")) {
				nb1++;
			} else {
				nb0++;
			}
		}
		if (nb1 >= nb0) {
			plusCourante += "1";
		} else {
			plusCourante += "0";
		}

		return plusCourante;
	}

	private static String getMoinsCourante(List<String> input, Integer pos) {
		String moinsCourante = "";

		int nb0 = 0;
		int nb1 = 0;
		for (String s : input) {
			if (s.substring(pos, pos + 1).equals("1")) {
				nb1++;
			} else {
				nb0++;
			}
		}
		if (nb1 < nb0) {
			moinsCourante += "1";
		} else {
			moinsCourante += "0";
		}

		return moinsCourante;
	}

	public int s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		return Integer.parseInt(getGenerateur(input).trim(), 2) * Integer.parseInt(getEpurateur(input).trim(), 2);
	}

	private static String getEpurateur(List<String> input) {
		List<String> codeConserves;
		int pos = 0;
		while (input.size() > 1) {
			codeConserves = new ArrayList<>();
			String conserver = getMoinsCourante(input, pos);
			for (String s : input) {
				if (s.substring(pos, pos + 1).equals(conserver)) {
					codeConserves.add(s);
				}
			}
			input = new ArrayList<>(codeConserves);
			pos++;
		}
		return input.get(0);
	}

	private static String getGenerateur(List<String> input) {
		List<String> codeConserves;
		int pos = 0;
		while (input.size() > 1) {
			codeConserves = new ArrayList<>();
			String conserver = getPlusCourante(input, pos);
			for (String s : input) {
				if (s.substring(pos, pos + 1).equals(conserver)) {
					codeConserves.add(s);
				}
			}
			input = new ArrayList<>(codeConserves);
			pos++;
		}
		return input.get(0);
	}
	public static List<Long> getDuration() {
		A2021Day3 d = new A2021Day3(3);
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

package aocmaven.a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class A2021Day8 extends A2021 {

	public A2021Day8(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day8 d = new A2021Day8(8);
		d.s1(true);
		// d.s2(true);

	}

	private void s1(boolean c) {
		System.out.println(getInfos(c).stream().map(Info::getOutput).mapToLong(out->out.stream().filter(s ->Arrays.asList(2, 3, 4, 7).contains(s.length())).count()).reduce((long) 0, (a, b) -> a + b));
	}

	private void s2(boolean c) {
		List<Info> infos = getInfos(c);
		int res = 0;
		for (Info i : infos) {
			int decode = getDecodeOutput(i.getInput(), i.getOutput());
			System.out.println(decode);
			res += decode;
		}

		System.out.println(res);

	}

	private int getDecodeOutput(List<String> t, List<String> to) {
		String sept = t.stream().filter(s -> s.length() == 3).findFirst().get();
		String un = t.stream().filter(s -> s.length() == 2).findFirst().get();
		String quatre = t.stream().filter(s -> s.length() == 4).findFirst().get();
		Map<String, String> corespOldLnewL = new HashMap<>();
		String al = "abcdefg";
		for (int pos = 0; pos < sept.length(); pos++) {
			if (!un.contains(sept.substring(pos, pos + 1))) {
				corespOldLnewL.put(sept.substring(pos, pos + 1), "a");
			}
		}
		String elementCommunCpt6 = "";
		elementCommunCpt6 = getElementCommunCpt6(t, al, elementCommunCpt6);
		if (elementCommunCpt6.contains(un.substring(0, 1))) {
			corespOldLnewL.put(un.substring(0, 1), "f");
			corespOldLnewL.put(un.substring(0, 1), "c");
		} else {
			corespOldLnewL.put(un.substring(0, 1), "c");
			corespOldLnewL.put(un.substring(0, 1), "f");
		}
		for (int pos = 0; pos < quatre.length(); pos++) {
			if (!elementCommunCpt6.contains(quatre.substring(pos, pos + 1))
					&& !quatre.substring(pos, pos + 1).equals(corespOldLnewL.get("c"))) {
				corespOldLnewL.put(quatre.substring(pos, pos + 1), "d");
			}
		}
		for (int l = 0; l < 7; l++) {
			int cpt = 0;
			for (String s : t) {
				if (s.contains(al.substring(l, l + 1))) {
					cpt++;
				}
			}
			if (cpt == 4) {
				corespOldLnewL.put(al.substring(l, l + 1), "e");

			}
			if (cpt == 7 && !al.substring(l, l + 1).equals(corespOldLnewL.get("d"))) {
				corespOldLnewL.put(al.substring(l, l + 1), "g");
			}
			if (cpt == 6) {
				corespOldLnewL.put(al.substring(l, l + 1), "b");
			}
		}
		Map<String, Integer> corespnewLNumber = getCorespnewLNumber();
		for (String s : t) {
			String cleanMessage = "";
			for (int pos = 0; pos < s.length(); pos++) {
				String letter = s.substring(pos, pos + 1);
				cleanMessage += corespOldLnewL.get(letter);
			}

			cleanMessage = sortString(cleanMessage);
			System.out.println(corespnewLNumber.get(cleanMessage));
		}
		String numberDecode = "";
		for (String s : to) {
			String cleanMessage = "";
			for (int pos = 0; pos < s.length(); pos++) {
				String letter = s.substring(pos, pos + 1);
				cleanMessage += corespOldLnewL.get(letter);
			}
			numberDecode += corespnewLNumber.get(sortString(cleanMessage));
		}
		return Integer.parseInt(numberDecode);
	}

	private Map<String, Integer> getCorespnewLNumber() {
		Map<String, Integer> corespnewLNumber = new HashMap<>();
		corespnewLNumber.put("abcefg", 0);
		corespnewLNumber.put("cf", 1);
		corespnewLNumber.put("acdeg", 2);
		corespnewLNumber.put("acdfg", 3);
		corespnewLNumber.put("bcdf", 4);
		corespnewLNumber.put("abdfg", 5);
		corespnewLNumber.put("abdefg", 6);
		corespnewLNumber.put("acf", 7);
		corespnewLNumber.put("abcdefg", 8);
		corespnewLNumber.put("abcdfg", 9);
		return corespnewLNumber;
	}

	private String getElementCommunCpt6(List<String> t, String al, String elementCommunCpt6) {
		for (int l = 0; l < 7; l++) {
			if (isInCpt6(t, al, l)) {
				elementCommunCpt6 += al.substring(l, l + 1);
			}
		}
		return elementCommunCpt6;
	}

	public static String sortString(String inputString) {
		// Converting input string to character array
		char tempArray[] = inputString.toCharArray();

		// Sorting temp array using
		Arrays.sort(tempArray);

		// Returning new sorted string
		return new String(tempArray);
	}

	private boolean isInCpt6(List<String> t, String al, int l) {
		List<String> les6 = t.stream().filter(s -> s.length() == 6).collect(Collectors.toList());
		int cpt = 0;
		for (String e : les6) {
			if (e.contains(al.substring(l, l + 1))) {
				cpt++;
			}
		}
		return cpt == les6.size();
	}

	private List<Info> getInfos(boolean c) {
		List<String> infosString = Arrays.asList(getInput(c).split("\n")).stream().collect(Collectors.toList());

		List<Info> infos = new ArrayList<>();
		for (String s : infosString) {
			String[] io = s.split("@", 2);
			String i = io[0];
			String o = io[1];
			o = o.trim();
			List<String> is = Arrays.asList(i.split(" "));
			List<String> os = Arrays.asList(o.split(" "));
			infos.add(new Info(is, os));
		}
		return infos;
	}

	public static class Info {
		List<String> input;
		List<String> output;

		public List<String> getInput() {
			return input;
		}

		public void setInput(List<String> input) {
			this.input = input;
		}

		public List<String> getOutput() {
			return output;
		}

		public void setOutput(List<String> output) {
			this.output = output;
		}

		public Info(List<String> input, List<String> output) {
			super();
			this.input = input;
			this.output = output;
		}

		@Override
		public String toString() {
			return "Info [input=" + input + ", output=" + output + "]";
		}
	}
}

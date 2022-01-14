package a2017;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class A2017Day9 extends A2017 {

	public A2017Day9(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day9 d = new A2017Day9(9);
		// d.s1(true);
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

	public int s1(boolean b) {
		String ch = getInput(b);
		String ch2 = "{{<!>},{<!>},{<!>},{<a>}}";
		String cleanest = ch;
		boolean start = true;
		System.out.println(ch);

		while (!cleanest.equals(ch) || start) {
			start = false;
			ch = cleanest;

			cleanest = clean(ch);

		}
		System.out.println(cleanest);
		return score(cleanest);
//10656
	}

	private int score(String c) {
		int tot = 0;
		int level = 0;
		for (int pos = 0; pos < c.length(); pos++) {
			if (c.substring(pos, pos + 1).equals("{")) {
				level++;
			} else if (c.substring(pos, pos + 1).equals("}")) {
				tot += level;
				level--;
			}
		}
		return tot;
	}

	private String clean(String ch) {
		if (ch.contains("!")) {
			return sansExcl(ch);
		}
		int posDebG = -1;
		for (int p = 0; p < ch.length(); p++) {
			if (ch.substring(p, p + 1).equals("<") && posDebG == -1) {
				posDebG = p;
			}
		}
		int posFinG = -1;
		for (int q = posDebG + 1; q < ch.length(); q++) {
			if (ch.substring(q, q + 1).equals(">") && posFinG == -1) {
				posFinG = q;
			}
		}
		if (posDebG == -1 && posFinG == -1) {
			return ch;
		}
		return ch.substring(0, posDebG) + ch.substring(posFinG + 1);
	}

	private String sansExcl(String ch) {
		int posDebG = -1;
		for (int p = 0; p < ch.length(); p++) {
			if (ch.substring(p, p + 1).equals("!") && posDebG == -1) {
				posDebG = p;
			}
		}
		return ch.substring(0, posDebG) + ch.substring(posDebG + 2);
	}

	public int s2(boolean b) {
		String ch = getInput(b);
		String cleanest = ch;
		boolean start = true;
		System.out.println(ch);

		while (cleanest.contains("!")) {
			start = false;
			ch = cleanest;
			cleanest = sansExcl(ch);
		}
		System.out.println(cleanest);
		return score2(cleanest);
	}

	private int score2(String ch) {
		int tot=0;
		HashMap<String,Integer> nextStep=new HashMap<>();
		while(ch.contains("<")) {
			nextStep=nextStep(ch);
			ch=nextStep.keySet().stream().findFirst().get();
			tot+=nextStep.get(ch);
		}
		return tot;
	}

	private HashMap<String, Integer> nextStep(String ch) {
		int posDebG = -1;
		for (int p = 0; p < ch.length(); p++) {
			if (ch.substring(p, p + 1).equals("<") && posDebG == -1) {
				posDebG = p;
			}
		}
		int posFinG = -1;
		for (int q = posDebG + 1; q < ch.length(); q++) {
			if (ch.substring(q, q + 1).equals(">") && posFinG == -1) {
				posFinG = q;
			}
		}
		HashMap<String,Integer> res=new HashMap<>();
		res.put(ch.substring(0, posDebG) + ch.substring(posFinG + 1),posFinG-posDebG-1);
		return res;
	}

	public static List<Long> getDuration() {
		A2017Day9 d = new A2017Day9(1);
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

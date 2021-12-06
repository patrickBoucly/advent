package aocmaven.a2021;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class A2021Day7 extends  A2021 {


	public A2021Day7(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day7 d = new A2021Day7(6);
		d.s(false,18);
		d.s(false, 80);
		d.s(false, 256);
		d.s(true, 18);
		d.s(true, 80);
		d.s(true, 256);
	}

	private void s(boolean b, int nbDay) {
		String champs = (b ? "full input" : "sample data");
		List<String> input = Arrays.asList(getInput(b).split(","));
		List<Integer> inputNum = input.stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
		Map<Integer, BigInteger> comptage = new HashMap<>();
		for (Integer i : inputNum) {
			if (comptage.containsKey(i)) {
				comptage.put(i, comptage.get(i).add(BigInteger.ONE));
			} else {
				comptage.put(i, BigInteger.ONE);
			}
		}
		for (int k = 0; k <= 8; k++) {
			if (!comptage.containsKey(k)) {
				comptage.put(k, BigInteger.ZERO);
			}
		}
		Map<Integer, BigInteger> nextComptage = new HashMap<>();
		int day = 0;
		while (day < nbDay) {
			nextComptage = new HashMap<>();
			for (int k = 0; k <= 8; k++) {
				if (k == 6) {
					nextComptage.put(6, comptage.get(7).add(comptage.get(0)));
				} else if (k == 8) {
					nextComptage.put(8, comptage.get(0));
				} else {
					nextComptage.put(k, comptage.get(k + 1));
				}
			}
			comptage = new HashMap<>(nextComptage);
			day++;
		}
		BigInteger tot = BigInteger.ZERO;
		for (Integer i : nextComptage.keySet()) {
			tot = tot.add(nextComptage.get(i));
		}
		System.out.println("number of fish after " + nbDay + " days on " + champs + " : " + tot);
	}

	
}

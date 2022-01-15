package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.print.attribute.HashAttributeSet;

public class A2017Day16 extends A2017 {

	public A2017Day16(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day16 d = new A2017Day16(16);
		// d.s1(true);
		long startTime = System.currentTimeMillis();
		// System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public String s1(boolean b) {
		List<String> inst = Arrays.asList(getInput(b).split(",")).stream().map(String::trim)
				.collect(Collectors.toList());
		HashMap<String, Integer> pg = new HashMap<>();
		pg.put("a", 0);
		pg.put("b", 1);
		pg.put("c", 2);
		pg.put("d", 3);
		pg.put("e", 4);
		pg.put("f", 5);
		pg.put("g", 6);
		pg.put("h", 7);
		pg.put("i", 8);
		pg.put("j", 9);
		pg.put("k", 10);
		pg.put("l", 11);
		pg.put("m", 12);
		pg.put("n", 13);
		pg.put("o", 14);
		pg.put("p", 15);
		for (String ins : inst) {
			pg = apply(pg, ins);
		}
		String res = "";
		for (int j = 0; j <= 15; j++) {
			for (String k : pg.keySet()) {
				if (pg.get(k) == j) {
					res += k;
				}
			}
		}
		return res;
	}

	private HashMap<String, Integer> apply(HashMap<String, Integer> pg, String ins) {
		if (ins.substring(0, 1).equals("s")) {
			return spin(pg, Integer.parseInt(ins.substring(1)));
		}
		if (ins.substring(0, 1).equals("x")) {
			String a = ins.substring(1);
			String[] sp = a.split("/");
			int p1 = Integer.parseInt(sp[0]);
			int p2 = Integer.parseInt(sp[1]);
			String l1 = "";
			String l2 = "";
			for (int j = 0; j <= 15; j++) {
				for (String k : pg.keySet()) {
					if (pg.get(k) == p1) {
						l1 = k;
					}
					if (pg.get(k) == p2) {
						l2 = k;
					}
				}
			}
			pg.put(l1, p2);
			pg.put(l2, p1);
			return pg;
		}
		if (ins.substring(0, 1).equals("p")) {
			String a = ins.substring(1);
			String[] sp = a.split("/");
			String l1 = sp[0];
			String l2 = sp[1];
			int p1 = pg.get(l1);
			int p2 = pg.get(l2);
			pg.put(l1, p2);
			pg.put(l2, p1);

			return pg;
		}
		return null;
	}

	private HashMap<String, Integer> spin(HashMap<String, Integer> pg, int dec) {
		for (String k : pg.keySet()) {
			pg.put(k, (pg.get(k) + dec) % 16);
		}
		return pg;
	}

	public String s2(boolean b) {
		HashMap<String, Integer> pg = getInit();
		HashMap<String, Integer> pgCtrl = getInit();
	//	List<String> inst = Arrays.asList("pa/d", "po/b", "pe/c", "pc/i", "pf/m", "pg/l", "ph/b", "pc/n", "pj/p",
	//			"pk/j", "pg/c", "pf/h", "pg/f", "pg/k");
		List<String> inst = Arrays.asList(getInput(b).split(",")).stream().map(String::trim)
				.collect(Collectors.toList());

		for (int k = 1; k <= 10; k++) {
			
			for (String ins : inst) {
				pg = apply(pg, ins);
			}
			
		}
		String res = "";
		for (int j = 0; j <= 15; j++) {
			for (String k : pg.keySet()) {
				if (pg.get(k) == j) {
					res += k;
				}
			}
		}
		// aeodkghcjfnblpim
		// alhdbjfgoiempkcn
		// alfdemcjipbognkh
		return res;
	}

	private HashMap<String, Integer> getInit() {
		HashMap<String, Integer> pg = new HashMap<>();
		pg.put("a", 0);
		pg.put("b", 1);
		pg.put("c", 2);
		pg.put("d", 3);
		pg.put("e", 4);
		pg.put("f", 5);
		pg.put("g", 6);
		pg.put("h", 7);
		pg.put("i", 8);
		pg.put("j", 9);
		pg.put("k", 10);
		pg.put("l", 11);
		pg.put("m", 12);
		pg.put("n", 13);
		pg.put("o", 14);
		pg.put("p", 15);
		return pg;
	}

	public static List<Long> getDuration() {
		A2017Day16 d = new A2017Day16(1);
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

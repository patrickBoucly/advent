package a2022;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import outils.CircularLinkedList;

public class A2022Day20 extends A2022 {

	public A2022Day20(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day20 d = new A2022Day20(20);

		long startTime = System.currentTimeMillis();
		//System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public long s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().toList();
		Long cle = 811589153L;
		List<Score> list = new LinkedList<>();
		List<Score> listInitiale = new LinkedList<>();
		int id = 0;
		for (String s : input) {
			id++;
			list.add(new Score(id, cle * Long.parseLong(s.trim())));
			listInitiale.add(new Score(id, cle * Long.parseLong(s.trim())));
		}
		int n = listInitiale.size();
		System.out.println("0 " +list);
		for (int k = 1; k <= 10; k++) {
			for (int i = 0; i < n; i++) {
				Score s = listInitiale.get(i);
				Long val=s.value;
				Long valFloor = (long) Math.floorMod(s.value,n-1);
				int valIndex = getPosition(list, s.id);
				list.removeIf(score -> score.id == s.id);
				Long dec = Math.floorDiv(valIndex + valFloor, n);
				int newPosition = Math.floorMod((valIndex + valFloor + dec + listInitiale.size()), listInitiale.size());
				list.add(newPosition, new Score(s.id, val));
			}
			System.out.println(k+" "+list);
		}
		System.out.println(list);
		Long n1 = getNumberNthAfter0(list, 1000);
		Long n2 = getNumberNthAfter0(list, 2000);
		Long n3 = getNumberNthAfter0(list, 3000);
		return n1 + n2 + n3;
	}

	public long s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		CircularLinkedList circleList = new CircularLinkedList();
		List<Score> list = new LinkedList<>();
		List<Score> listInitiale = new LinkedList<>();
		int id = 0;
		for (String s : input) {
			id++;
			list.add(new Score(id, Long.parseLong(s.trim())));
			listInitiale.add(new Score(id, Long.parseLong(s.trim())));
		}
		int n = listInitiale.size();
		System.out.println(list);
		for (int i = 0; i < n; i++) {
			Score s = listInitiale.get(i);
			Long val = s.value;
			int valIndex = getPosition(list, s.id);
			list.removeIf(score -> score.id == s.id);
			long dec =  Math.floorDiv(valIndex + val, n);
			
			int newPosition = Math.floorMod((valIndex + val + dec), listInitiale.size());
			list.add(newPosition, new Score(s.id, val));
			if (i % 100 == 0) {
				System.out.println(i);
			}
			// too high 4789
		}
		System.out.println(list);
		Long n1 = getNumberNthAfter0(list, 1000);
		Long n2 = getNumberNthAfter0(list, 2000);
		Long n3 = getNumberNthAfter0(list, 3000);
		return n1 + n2 + n3;
	}

	private int getPosition(List<Score> list, int id) {
		for (int j = 0; j < list.size(); j++) {
			if (list.get(j).id == id) {
				return j;
			}
		}
		return 0;
	}

	private Long getNumberNthAfter0(List<Score> list, int i) {
		int pos0 = 0;
		for (int j = 0; j < list.size(); j++) {
			if (list.get(j).value == 0) {
				pos0 = j;
			}
		}
		return list.get(Math.floorMod((pos0 + i), list.size())).value;
	}

	public class Score {
		int id;
		Long value;

		public Score(int id, Long value) {
			super();
			this.id = id;
			this.value = value;
		}

		@Override
		public String toString() {
			return "" + value;
		}

	}

	public static List<Long> getDuration() {
		A2022Day20 d = new A2022Day20(20);
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

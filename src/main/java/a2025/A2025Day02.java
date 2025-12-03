package a2025;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.List;

import lombok.AllArgsConstructor;

import lombok.Data;

public class A2025Day02 extends A2025 {

	public A2025Day02(int day) {

		super(day);

	}

	public static void main(String[] args0) {

		A2025Day02 d = new A2025Day02(2);
		// System.out.println(d.s1(false));
		// System.out.println(d.s1(true));

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

	public Long s1(boolean b) {

		List<String> inputL = Arrays.asList(getInput(b).split(",")).stream().map(String::trim).toList();

		Game g = new Game();

		g.setIdsFromInput(inputL);

		return g.solve1();

	}

	public Long s2(boolean b) {

		List<String> inputL = Arrays.asList(getInput(b).split(",")).stream().map(String::trim).toList();

		Game g = new Game();

		g.setIdsFromInput(inputL);

		return g.solve2();

	}

	public static class Game {
		List<Id> ids;

		public void setIdsFromInput(List<String> input) {
			ids = new ArrayList<>();
			for (String l : input) {
				String[] s = l.split("-");
				ids.add(new Id(Long.parseLong(s[0]), Long.parseLong(s[1])));
			}
		}

		public Long solve2() {
			Long r = 0L;
			for (Id i : ids) {
				r += getValueForId2(i);
			}
			return r;
		}

		private Long getValueForId2(Id i) {
			Long r = 0L;
			if (i.first < 10) {
				i.first = 10L;
			}
			if (i.last < 10) {
				i.last = 10L;
			}
			Long curVal = i.first;

			while (curVal <= i.last) {
				String curValS = String.valueOf(curVal);
				if (isInvalid(curValS)) {
					r += curVal;
				}
				curVal++;
			}
			return r;
		}

		private boolean isInvalid(String curValS) {
			for (int j = 1; j <= curValS.length(); j++) {
				if (patternSizeN(curValS, j)) {
					return true;
				}
			}
			return false;
		}

		private boolean patternSizeN(String curValS, Integer n) {
			if (curValS.length() < 2 * n || curValS.length() % n != 0) {
				return false;
			}
			String p = curValS.substring(0, n);
			int j = 0;
			while (j < curValS.length() - n) {
				if (!curValS.substring(j + n, j + (2 * n)).equals(p)) {
					return false;
				}
				j += n;
			}
			return true;
		}

		public Long solve1() {
			Long r = 0L;
			for (Id i : ids) {
				r += getValueForId(i);
			}
			return r;
		}

		public Long getValueForId(Id i) {
			Long r = 0L;
			if (i.first < 10) {
				i.first = 10L;
			}
			if (i.last < 10) {
				i.last = 10L;
			}
			Long curVal = i.first;

			while (curVal <= i.last) {
				String curValS = String.valueOf(curVal);
				if (curValS.length() % 2 == 0) {
					Long a = Long.parseLong(curValS.substring(0, (curValS.length()) / 2));
					Long c = Long.parseLong(curValS.substring(curValS.length() / 2));
					if (a.equals(c)) {
						r += curVal;
					}
				}
				curVal++;
			}
			return r;
		}

	}

	@Data
	@AllArgsConstructor
	public static class Id {
		Long first;
		Long last;
	}

	public static List<Long> getDuration() {
		A2025Day02 d = new A2025Day02(2);
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
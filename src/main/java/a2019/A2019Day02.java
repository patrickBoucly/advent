package a2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

public class A2019Day02 extends A2019 {

	public A2019Day02(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2019Day02 d = new A2019Day02(2);
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

	public Integer s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split(",")).stream().map(String::trim).toList();
		Game g = new Game();
		return g.solve1(inputL);
	}

	public Integer s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split(",")).stream().map(String::trim).toList();
		Game g = new Game();
		return g.solve2(inputL);
	}

	@Data
	@NoArgsConstructor
	public static class Game {
		public void initFromInput(List<String> inputL) {

		}

		public Integer solve2(List<String> inputL) {
			for (int n = 0; n < 100; n++) {
				for (int v = 0; v < 100; v++) {
					Integer res = calculLiNomVerbe(new ArrayList<>(inputL.stream().map(Integer::parseInt).toList()), n,
							v);
					if (res == 19690720) {
						return 100 * n + v;
					}

				}
			}
			return 0;
		}

		public Integer solve1(List<String> inputL) {
			List<Integer> li = new ArrayList<>(inputL.stream().map(Integer::parseInt).toList());
			return calculLiNomVerbe(new ArrayList<>(li), 12, 1);
		}

		private Integer calculLiNomVerbe(List<Integer> a, int n, int v) {
			int i = 0;
			a.set(1, n);
			a.set(2, v);
			while (i < a.size()) {
				if (a.get(i).equals(1)) {
					applyAdd(a, i);
					i += 4;
				} else if (a.get(i).equals(2)) {
					applyMult(a, i);
					i += 4;
				} else {
					i += 1;
					break;
				}

			}
			return a.get(0);
		}

		private void applyMult(List<Integer> li, int i) {
			li.set(li.get(i + 3), li.get(li.get(i + 1)) * li.get(li.get(i + 2)));
		}

		private void applyAdd(List<Integer> li, int i) {
			li.set(li.get(i + 3), li.get(li.get(i + 1)) + li.get(li.get(i + 2)));
		}

	}

	public static List<Long> getDuration() {
		A2019Day02 d = new A2019Day02(2);
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

package a2024;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

public class A2024Day14 extends A2024 {

	public A2024Day14(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day14 d = new A2024Day14(14);
		// System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		// d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	public long s1(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		Game g = getGame(input, b);
		for (Long d = 0L; d < g.duration; d++) {
			for (Robot r : g.robots) {
				r.move();
			}
		}
		return g.countRes1();
	}

	private Game getGame(List<String> input, boolean b) {
		Set<Robot> robots = new HashSet<>();
		int wide = b ? 101 : 11;
		int tall = b ? 103 : 7;
		Long duration = 100L;
		for (int i = 0; i < input.size(); i++) {
			int posEg1 = input.get(i).indexOf("=");
			int posEg2 = input.get(i).lastIndexOf("=");
			int posVir1 = input.get(i).indexOf(",");
			int posVir2 = input.get(i).lastIndexOf(",");
			Integer px = Integer.parseInt(input.get(i).substring(posEg1 + 1, posVir1));
			Integer py = Integer.parseInt(input.get(i).substring(posVir1 + 1, posEg2 - 2));
			Integer vx = Integer.parseInt(input.get(i).substring(posEg2 + 1, posVir2));
			Integer vy = Integer.parseInt(input.get(i).substring(posVir2 + 1));
			robots.add(new Robot(px, py, vx, vy, wide, tall));
		}
		return new Game(robots, wide, tall, duration, 0L, 0L);
	}

	public Long s2(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		Game g = getGame(input, b);
		for (Long d = 1L; d < 1000000L; d++) {
			for (Robot r : g.robots) {
				r.move();
			}
			if (g.countRes2()) {
				System.out.println(g);
				System.out.println(d+"!!");
				return d;
			}
			if (d % 100000L == 0) {
				System.out.println("cpt " + d);
			}
		}
		return -1L;

	}

	@Data
	@AllArgsConstructor
	private static class Game {
		public long countRes1() {
			int sepX = (wide - 1) / 2;
			int sepY = (tall - 1) / 2;
			Long q1 = robots.stream().filter(r -> r.px < sepX && r.py < sepY).count();
			Long q2 = robots.stream().filter(r -> r.px < sepX && r.py > sepY).count();
			Long q3 = robots.stream().filter(r -> r.px > sepX && r.py < sepY).count();
			Long q4 = robots.stream().filter(r -> r.px > sepX && r.py > sepY).count();
			return q1 * q2 * q3 * q4;
		}

		public boolean countRes2() {
			for (Robot r1 : robots) {
				for (Robot r2 : robots) {
					if (!r1.equals(r2)) {
						if (r1.px.equals(r2.px) && r1.py.equals(r2.py)) {
							return false;
						}
					}
				}
			}
			return true;
		}

		private boolean robotSym(List<Robot> rigth, Robot r) {
			int sepX = (wide - 1) / 2;
			Integer symA = sepX + (sepX - r.px);
			for (Robot s : rigth) {
				if (s.px.equals(symA) && s.py.equals(r.py)) {
					return true;
				}
			}
			return false;
		}

		Set<Robot> robots;
		int wide;
		int tall;
		Long duration;
		Long res1;
		Long res2;

		@Override
		public String toString() {
			String res = "";
			for (int j = 0; j < tall; j++) {
				for (int i = 0; i < wide; i++) {

					long nb = nbRobot(i, j);
					System.out.println(nb);
					if (nb * 1 == 0) {
						res += ".";
					} else {
						res += nb;
					}
				}
				res += "\n";
			}
			return res;
		}

		private long nbRobot(int i, int j) {
			return robots.stream().filter(r -> r.px - i == 0 && r.py - j == 0).count();
		}

	}

	@Data
	@AllArgsConstructor
	private static class Robot {
		Integer px;
		Integer py;
		Integer vx;
		Integer vy;
		int wide;
		int tall;

		public void move() {
			Integer npx = (px + vx) % wide;
			Integer npy = (py + vy) % tall;
			while (npx < 0) {
				npx += wide;
			}
			while (npy < 0) {
				npy += tall;
			}
			setPx(npx);
			setPy(npy);
		}
	}

	public static List<Long> getDuration() {
		A2024Day14 d = new A2024Day14(13);
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

package a2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2022Day10 extends A2022 {

	public A2022Day10(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day10 d = new A2022Day10(10);
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

	public String s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		StringBuilder res = new StringBuilder();
		Long x = 1L;
		boolean onProcess = false;
		Integer cycle = 1;
		int j = -1;
		String inst = "";
		Long nextOp = 0L;
		int posLigne = 0;
		int numLigne = -1;
		List<Point> pts = new ArrayList<>();
		Game g = new Game(pts);
		while (cycle < 241) {
			if ((posLigne) % 40 == 0) {
				numLigne++;
				posLigne = 0;
				res.append("\n");
			}
			if (!onProcess) {

				j++;
				inst = input.get(j).trim();
			}
			if (inst.equals("noop")) {
				if (Math.abs(posLigne - x) < 2) {
					pts.add(new Point(posLigne, numLigne, true));
				} else {
					pts.add(new Point(posLigne, numLigne, false));
				}
				posLigne++;

			} else if (!onProcess) {
				if (Math.abs(posLigne - x) < 2) {
					pts.add(new Point(posLigne, numLigne, true));
				} else {
					pts.add(new Point(posLigne, numLigne, false));
				}
				posLigne++;
				System.out.println("Start cycle   " + cycle + ": begin executing " + inst);
				System.out.println(
						"During cycle  " + cycle + ": CRT draws pixel in position " + Integer.valueOf(cycle - 1));
				nextOp = Long.valueOf(inst.split(" ")[1].trim());
				onProcess = true;
			} else {
				if (Math.abs(posLigne - x) < 2) {
					pts.add(new Point(posLigne, numLigne, true));
				} else {
					pts.add(new Point(posLigne, numLigne, false));
				}
				posLigne++;
				x += nextOp;
				System.out.println(
						"During cycle  " + cycle + ": CRT draws pixel in position " + Integer.valueOf(cycle - 1));
				System.out.println("End of cycle   " + cycle + ": finish executing addx " + nextOp
						+ "(Register X is now " + x + ")");
				onProcess = false;
				

			}
			// System.out.println(res);
			cycle++;

		}
		g.setPts(new HashSet<>(pts));
		return g.toString();
	}

	public Long s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		Long res = 0L;
		Long x = 1L;
		boolean onProcess = false;
		Integer cycle = 1;
		int j = -1;
		String inst = "";
		Long nextOp = 0L;
		while (cycle < 241) {
			System.out.println(cycle);
			System.out.println(j);
			if ((cycle - 20) % 40 == 0) {
				res += x * cycle;
			}
			if (!onProcess) {
				j++;
				inst = input.get(j).trim();
			}
			if (inst.equals("noop")) {
				cycle++;
			} else if (!onProcess) {
				cycle++;
				nextOp = Long.valueOf(inst.split(" ")[1].trim());
				onProcess = true;
			} else {
				x += nextOp;
				onProcess = false;
				cycle++;
			}
		}

		return res;
	}

	public static class Game {
		Set<Point> pts;

		public Game(List<Point> pts2) {
			// TODO Auto-generated constructor stub
		}

		public Set<Point> getPts() {
			return pts;
		}

		public void setPts(Set<Point> pts) {
			this.pts = pts;
		}

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			// pts.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
			int imax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getX).collect(Collectors.toList()));
			int jmax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getY).collect(Collectors.toList()));
			for (int j = 0; j <= jmax; j++) {
				for (int i = 0; i <= imax; i++) {
					Point.getPoint(pts, i, j).ifPresentOrElse(pt -> res.append(pt.isOn ? "#" : "."),
							() -> res.append("."));

				}
				res.append("\n");
			}
			return res.toString();
		}
	}

	private static class Point {
		int x;
		int y;
		Boolean isOn;

		public Point(int x, int y, Boolean pixel) {
			super();
			this.x = x;
			this.y = y;
			this.isOn = pixel;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			return x == other.x && y == other.y;
		}

		@Override
		public String toString() {
			return isOn ? "#" : ".";
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public Boolean getIsOn() {
			return isOn;
		}

		public void setIsOn(Boolean isOn) {
			this.isOn = isOn;
		}

		public static Optional<Point> getPoint(Set<Point> pts, int x, int y) {
			Point p = null;
			for (Point i : pts) {
				if (x == i.x && y == i.y) {
					p = new Point(x, y, i.isOn);
				}
			}
			return Optional.ofNullable(p);
		}
	}

	public static List<Long> getDuration() {
		A2022Day10 d = new A2022Day10(10);
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

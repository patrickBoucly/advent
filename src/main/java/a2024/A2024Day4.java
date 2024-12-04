package a2024;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import outils.MesOutils;

public class A2024Day4 extends A2024 {

	public A2024Day4(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day4 d = new A2024Day4(4);
		System.out.println(d.s1(false));
		long startTime = System.currentTimeMillis();
		//d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	public long s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim())
				.collect(Collectors.toList());
		
		
		int res=0;
		

		return res;
	}

	public int s2(boolean b) {
		String inputL = getInput(b);
		
		int res=0;
	

		return res;

	}
	
	private Set<Point> getPoints(List<String> input) {
		Set<Point> pts = new HashSet<Point>();
		int j = 0;
		for (String s : input) {
			for (int i = 0; i < s.length(); i++) {
				pts.add(new Point(new Position(i, j), s.substring(i, i + 1)));
			}
			j++;
		}
		return pts;
	}
	
	@Data
	@AllArgsConstructor
	private static class Point {
		int x;
		int y;
		String letter;

	
		public static Optional<Point> getPoint(Set<Point> pts, int x, int y) {
			Point p = null;
			for (Point i : pts) {
				if (x == i.x && y == i.y) {
					p = new Point(x, y, i.letter);
				}
			}
			return Optional.ofNullable(p);
		}

	}
	@Data
	@AllArgsConstructor
	public static class Game {
		Set<Point> pts;
		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			// pts.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
			int imax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getX).toList());
			int jmax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getY).toList());
			for (int j = 0; j <= jmax; j++) {
				for (int i = 0; i <= imax; i++) {
					Point.getPoint(pts, i, j).ifPresentOrElse(pt -> res.append(pt.letter), () -> res.append("."));

				}
				res.append("\n");
			}
			return res.toString();
		}
	}

	public static List<Long> getDuration() {
		A2024Day4 d = new A2024Day4(4);
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

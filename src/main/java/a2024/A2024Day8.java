package a2024;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

public class A2024Day8 extends A2024 {

	public A2024Day8(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day8 d = new A2024Day8(8);
		System.out.println(d.s1(true));
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
		Grille g = getGrille(input);
		g.solve1();
		return g.positions.stream().filter(p -> p.antinode).count();
	}

	private Grille getGrille(List<String> input) {
		Set<Position> positions = new HashSet<>();
		Set<Antenne> antennes = new HashSet<>();
		int j = 0;
		for (String s : input) {
			for (int i = 0; i < s.length(); i++) {
				String a = s.substring(i, i + 1);
				positions.add(new Position(i, j, false));
				if (!a.equals(".")) {
					antennes.add(new Antenne(i, j, a));
				}
			}
			j++;
		}
		return new Grille(positions, antennes);
	}

	public Long s2(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		Grille g = getGrille(input);
		g.solve2();
		return g.positions.stream().filter(p -> p.antinode).count();
	}

	@Data
	@AllArgsConstructor
	private static class Grille {
		public void solve1() {
			List<String> names = antennes.stream().map(a -> a.name).distinct().toList();
			for (String n : names) {
				List<Antenne> ant = antennes.stream().filter(a -> a.name.equals(n)).distinct().toList();
				for (Antenne a1 : ant) {
					for (Antenne autre : ant) {
						if (!a1.equals(autre)) {
							Optional<Position> p = getPosition(positions, 2 * autre.a - a1.a, 2 * autre.o - a1.o);
							if (p.isPresent()) {
								p.get().antinode = true;
							}
						}

					}
				}
			}

		}

		public void solve2() {
			List<String> names = antennes.stream().map(a -> a.name).distinct().toList();
			for (String n : names) {
				List<Antenne> ant = antennes.stream().filter(a -> a.name.equals(n)).distinct().toList();
				if(ant.size()>1) {
					for(Antenne a1 : ant) {
						getPosition(positions, a1.a,a1.o).get().antinode=true;
					}
				}
				for (Antenne a1 : ant) {
					for (Antenne autre : ant) {
						if (!a1.equals(autre)) {
							boolean go = true;
							int j=0;
							while (go) {
								int newA=2 * autre.a - a1.a + j*(autre.a-a1.a);
								int newO=2 * autre.o - a1.o+ j*(autre.o-a1.o);
								Optional<Position> p = getPosition(positions, newA,newO);
								if (p.isPresent()) {
									p.get().antinode = true;
									j++;
								}else {
									go=false;
								}
							}
						}

					}
				}
			}

		}

		Set<Position> positions;
		Set<Antenne> antennes;
	}

	public static Optional<Position> getPosition(Set<Position> pts, int x, int y) {
		Position p = null;
		for (Position i : pts) {
			if (x == i.a && y == i.o) {
				return Optional.ofNullable(i);
			}
		}
		return Optional.ofNullable(p);
	}

	@Data
	@AllArgsConstructor
	private static class Position {
		Integer a;
		Integer o;
		boolean antinode = false;

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
			return Objects.equals(a, other.a) && Objects.equals(o, other.o);
		}

		@Override
		public int hashCode() {
			return Objects.hash(a, o);
		}
	}

	@Data
	@AllArgsConstructor
	private static class Antenne {
		Integer a;
		Integer o;
		String name;
	}

	public static List<Long> getDuration() {
		A2024Day8 d = new A2024Day8(8);
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

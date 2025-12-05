package a2025;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import outils.MesOutils;

public class A2025Day04 extends A2025 {
	public A2025Day04(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2025Day04 d = new A2025Day04(4);
		//System.out.println(d.s1(true));
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
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Game g = new Game(getPointsFromInput(inputL));
		return g.solve1();
	}

	public long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();

		Game g = new Game(getPointsFromInput(inputL));
		return g.solve2();
	}

	private Set<Point> getPointsFromInput(List<String> inputL) {
		Set<Point> points = new HashSet<>();
		int j = 0;
		for (String l : inputL) {
			points.addAll(getPointsFromLigne(l, j));
			j++;
		}
		return points;
	}

	private Set<Point> getPointsFromLigne(String l, int j) {
		Set<Point> points = new HashSet<>();
		for (int i = 0; i < l.length(); i++) {
			if (l.substring(i, i + 1).equals("@")) {
				points.add(new Point(i, j, l.substring(i, i + 1),false));
			}
		}
		return points;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Game {
		Set<Point> points;

		public Long solve1() {
			Long r=0L;
			for(Point p:points) {
				if(getVoisinnage(p).size()<4) {
					r++;
				}
			}
			return r;
		}

		public long solve2() {
			boolean cont=true;
			while(cont) {
				cont=false;
				for(Point p:points.stream().filter(t->!t.supprimable).toList()) {
					if(getVoisinnageSup(p).size()<4) {
						p.supprimable=true;
						cont=true;
					}
				}
				
			}
			
			return points.stream().filter(t->t.supprimable).count();
		}
		private Set<Point> getVoisinnageSup(Point c) {
			Set<Point> voisinage=new HashSet<>();
			Optional<Point> gauche=getPoint(points,c.x-1,c.y);
			Optional<Point> droite=getPoint(points,c.x+1,c.y);
			if(gauche.isPresent() && !gauche.get().supprimable ) {
				voisinage.add(gauche.get());
			}
			if(droite.isPresent()&& !droite.get().supprimable) {
				voisinage.add(droite.get());
			}
			for(int k=c.x-1;k<=c.x+1;k++) {
				Optional<Point> dessus=getPoint(points,k,c.y-1);
				Optional<Point> dessous=getPoint(points,k,c.y+1);
				if(dessus.isPresent() && !dessus.get().supprimable) {
					voisinage.add(dessus.get());
				}
				if(dessous.isPresent() &&  !dessous.get().supprimable ) {
					voisinage.add(dessous.get());
				}
			}
			return voisinage;
		}

		private Set<Point> getVoisinnage(Point c) {
			Set<Point> voisinage=new HashSet<>();
			Optional<Point> gauche=getPoint(points,c.x-1,c.y);
			Optional<Point> droite=getPoint(points,c.x+1,c.y);
			if(gauche.isPresent()) {
				voisinage.add(gauche.get());
			}
			if(droite.isPresent()) {
				voisinage.add(droite.get());
			}
			for(int k=c.x-1;k<=c.x+1;k++) {
				Optional<Point> dessus=getPoint(points,k,c.y-1);
				Optional<Point> dessous=getPoint(points,k,c.y+1);
				if(dessus.isPresent()) {
					voisinage.add(dessus.get());
				}
				if(dessous.isPresent()) {
					voisinage.add(dessous.get());
				}
			}
			return voisinage;
		}

	}
	
	
	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	private static class Point {
		int x;
		int y;
		String contenu;
		boolean supprimable;
	}

	public static Optional<Point> getPoint(Set<Point> pts, int x, int y) {
		Point p = null;
		for (Point i : pts) {
			if (x == i.x && y == i.y) {
				p = new Point(x, y, i.contenu,i.supprimable);
			}
		}
		return Optional.ofNullable(p);
	}

	public static List<Long> getDuration() {
		A2025Day04 d = new A2025Day04(4);
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
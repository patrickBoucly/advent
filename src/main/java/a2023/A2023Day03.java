package a2023;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import a2023.A2023Day03.Chiffre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class A2023Day03 extends A2023 {
	private static List<String> CHIFFR = List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
	private static List<String> CHIFFROREMPTY = List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9",".");

	public A2023Day03(int day) {
		super(day);
	}
	public int id=0;
	public static void main(String[] args0) {
		A2023Day03 d = new A2023Day03(3);
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

	public int s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		TheGame tg = new TheGame();
		tg.setPoints(getPointsFromInput(inputL));
		tg.setChiffres(getChiffresFromInput(inputL));
		tg.validerChiffres();
		return tg.getChiffres().stream().filter(c -> !c.isEstValide()).map(Chiffre::getValue).reduce(0, Integer::sum);
	}

	private Set<Chiffre> getChiffresFromInput(List<String> inputL) {
		Set<Chiffre> chiffres = new HashSet<>();
		int j = 0;
		for (String l : inputL) {
			chiffres.addAll(getchiffresFromLigne(l, j));
			j++;
		}
		return chiffres;
	}

	private Set<Chiffre> getchiffresFromLigne(String l, int j) {
		Set<Chiffre> chiffres = new HashSet<>();
		Chiffre leChiffreEnCours = new Chiffre();
		int i = 0;
		while (i < l.length()) {
			String s = l.substring(i, i + 1);
			if (CHIFFR.contains(s)) {
				leChiffreEnCours = new Chiffre();
				leChiffreEnCours.setId(id++);
				int offset = 0;
				String next = l.substring(i + offset, i + 1 + offset);
				while (CHIFFR.contains(next) && i + offset < l.length()) {
					offset++;
					if (i + offset < l.length()) {
						next = l.substring(i + offset, i + 1 + offset);
					} else {
						next = l.substring(i + offset);
					}
				}
				leChiffreEnCours.setDebAbs(i);
				leChiffreEnCours.setFinAbs(i + offset - 1);
				leChiffreEnCours.setOrdonnee(j);
				leChiffreEnCours.setValue(Integer.parseInt(l.substring(i, i + offset)));
				chiffres.add(leChiffreEnCours);
				i += offset;
			} else {
				i++;
			}
		}
		return chiffres;
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
			points.add(new Point(i, j, l.substring(i, i + 1)));
		}
		return points;
	}

	public long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		TheGame tg = new TheGame();
		tg.setPoints(getPointsFromInput(inputL));
		tg.setChiffres(getChiffresFromInput(inputL));
		tg.validerChiffres();
		return tg.sumGear();
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class TheGame {
		public void validerChiffres() {
			for(Chiffre c:chiffres) {
				validerUnChiffre(c);
			}

		}

		public long sumGear() {
			long res=0;
			List<Point> eng=points.stream().filter(f->f.contenu.equals("*")).toList();
			int cpt=0;
			for(Point e:eng) {
				cpt++;
				System.out.println(cpt);
				res+=getSumEng(e);
			}
			return res;
		}

		private long getSumEng(Point e) {
			List<Chiffre> candidats=chiffres.stream().filter(ch->Math.abs(ch.ordonnee-e.y)<3 && Math.abs((ch.debAbs+ch.finAbs)/2-e.x)<8).toList();
				for(Chiffre c:candidats) {
					if(pointDansList(getVoisinnage(c),e)) {
						for(Chiffre c2:candidats.stream().filter(ch->Math.abs(ch.ordonnee-c.ordonnee)<3).toList()) {
							if(pointDansList(getVoisinnage(c2),e)) {
								if(!c.equals(c2)) {
									System.out.println(""+c2.value+"*"+c.value);
									return c.getValue()*c2.getValue();
								}
							}
						}
					}
				}
				
			return 0;
		}

		private boolean pointDansList(Set<Point> voisinnage, Point p) {
			return voisinnage.stream().anyMatch(v->v.x==p.x && v.y==p.y);

		}

		private void validerUnChiffre(Chiffre c) {
			Set<Point> voisinage=getVoisinnage(c);
			int voisins=voisinage.size();
			int vOk=0;
			for(Point p:voisinage) {
				if(CHIFFROREMPTY.contains(p.contenu)) {
					vOk++;
				}
			}
			if(vOk==voisins) {
				c.setEstValide(true);
			}
		}

		private Set<Point> getVoisinnage(Chiffre c) {
			Set<Point> voisinage=new HashSet<>();
			Optional<Point> gauche=getPoint(points,c.debAbs-1,c.ordonnee);
			Optional<Point> droite=getPoint(points,c.finAbs+1,c.ordonnee);
			if(gauche.isPresent()) {
				voisinage.add(gauche.get());
			}
			if(droite.isPresent()) {
				voisinage.add(droite.get());
			}
			for(int k=c.debAbs-1;k<=c.finAbs+1;k++) {
				Optional<Point> dessus=getPoint(points,k,c.ordonnee-1);
				Optional<Point> dessous=getPoint(points,k,c.ordonnee+1);
				if(dessus.isPresent()) {
					voisinage.add(dessus.get());
				}
				if(dessous.isPresent()) {
					voisinage.add(dessous.get());
				}
			}
			return voisinage;
		}

		Set<Point> points;
		Set<Chiffre> chiffres;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Chiffre {
		int value;
		int ordonnee;
		int debAbs;
		int finAbs;
		boolean estValide = false;
		int id;
		@Override
		public int hashCode() {
			return Objects.hash(id);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Chiffre other = (Chiffre) obj;
			return id == other.id;
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
	}

	public static Optional<Point> getPoint(Set<Point> pts, int x, int y) {
		Point p = null;
		for (Point i : pts) {
			if (x == i.x && y == i.y) {
				p = new Point(x, y, i.contenu);
			}
		}
		return Optional.ofNullable(p);
	}

	public static List<Long> getDuration() {
		A2023Day03 d = new A2023Day03(3);
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

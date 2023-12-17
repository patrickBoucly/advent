package a2023;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import outils.MesOutils;

public class A2023Day13 extends A2023 {

	public A2023Day13(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day13 d = new A2023Day13(13);
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

	public int s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		TheGame tg = getTheGame(inputL);
		tg.resoudresGrilles();
		return tg.grilles.stream().map(Grille::getResultat).reduce(Integer::sum).get();
	}

	public int s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		TheGame tg = getTheGame(inputL);
		tg.resoudresGrilles2();
		return tg.grilles.stream().map(Grille::getResultat).reduce(Integer::sum).get();

	}

	private TheGame getTheGame(List<String> inputL) {
		Set<Grille> grilles = new HashSet<>();
		Grille grille = new Grille();
		Set<Point> pts = new HashSet<>();
		grille.setPoints(pts);
		TheGame tg = new TheGame();
		int lmax = inputL.size();
		int cmax = 0;
		int idGrille = 0;
		int numLigne = -1;
		for (int l = 0; l < lmax; l++) {
			numLigne++;
			cmax = inputL.get(l).length() - 1;
			if (inputL.get(l).length() < 2) {
				grille.setPoints(pts);
				grilles.add(grille);
				idGrille++;
				grille = new Grille();
				grille.id = idGrille;
				pts = new HashSet<>();
				grille.setPoints(pts);
				numLigne = -1;
			} else {
				for (int c = 0; c <= cmax; c++) {
					Point p = new Point();
					p.setX(c);
					p.setY(numLigne);
					String info = inputL.get(l).substring(c, c + 1);
					p.setInfo(info);
					pts.add(p);
				}
			}
		}
		grille.setPoints(pts);
		grilles.add(grille);
		tg.setGrilles(grilles);
		return tg;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class TheGame {
		public void resoudresGrilles() {
			// grilles.sort(Comparator.comparing(Grille::getId));
			for (Grille g : grilles) {

				resoudreGrillePairSymVer(g);

				resoudreGrilleImpairSymHor(g);
				

			}

		}

		public void resoudresGrilles2() {
			for (Grille g : grilles) {
				
					resoudreGrillePairSymVer(g);
					resoudreGrilleImpairSymHor(g);
					g.resultat = 0;
					getPointToChange(g);
			}

		}

		private Point getPointToChange(Grille g) {
			for (Point p : g.points) {
				p.setInfo(p.info.equals("#") ? "." : "#");

				resoudreGrillePairSymVer(g);
				if (g.resultat != 0) {
					return p;
				}
				resoudreGrilleImpairSymHor(g);
				if (g.resultat != 0) {
					return p;
				}
				if (g.resultat != 0) {
					return p;
				}
				p.setInfo(p.info.equals("#") ? "." : "#");
			}
			return null;
		}

		private void resoudreGrilleImpairSymHor(Grille g) {
			int linmax = MesOutils
					.getMaxIntegerFromList(g.points.stream().map(Point::getY).collect(Collectors.toList()));
			for (int numLin = 0; numLin < linmax; numLin++) {
				if (symHor(g, numLin)) {
					int add = (numLin + 1) * 100;
					
					if (g.type.equals("o")) {
						g.type = "h";
					} else {
						g.type = "v";
					}
					g.resultat += add;
					break;
				}
			}

		}

		private boolean symHor(Grille g, int numLin) {

			for (Point p : g.points) {
				Optional<Point> pSymHor = getPointSymHor(g, p, numLin);
				if (pSymHor.isPresent()) {
					if (!pSymHor.get().info.equals(p.info)) {
						return false;
					}
				}
			}
			return true;

		}

		private Optional<Point> getPointSymHor(Grille g, Point p, int numLin) {
			return getPoint(g.points, p.x, 2 * numLin - p.y + 1);
		}

		private void resoudreGrillePairSymVer(Grille g) {
			int colmax = MesOutils
					.getMaxIntegerFromList(g.points.stream().map(Point::getX).collect(Collectors.toList()));
			for (int numCol = 0; numCol < colmax; numCol++) {
				if (symVer(g, numCol)) {
					int add = numCol + 1;
					if (g.type.equals("o")) {
						g.type = "v";
					} else {
						g.type = "h";
					}
					g.resultat += add;
					break;
				}
			}

		}

		private boolean symVer(Grille g, int numCol) {
			for (Point p : g.points) {
				Optional<Point> pSymVer = getPointSymVer(g, p, numCol);
				if (pSymVer.isPresent()) {
					if (!pSymVer.get().info.equals(p.info)) {
						return false;
					}
				}
			}
			return true;
		}

		private Optional<Point> getPointSymVer(Grille g, Point p, int numCol) {
			return getPoint(g.points, 2 * numCol - p.x + 1, p.y);
		}

		Set<Grille> grilles;

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			for (Grille g : grilles) {
				res.append(g).append("\n");
			}
			return res.toString();
		}

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Grille {
		String type = "o";
		Set<Point> points;
		int id;
		int resultat = 0;

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			res.append("Grille n°" + id).append("\n");
			// pts.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
			int imax = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getX).collect(Collectors.toList()));
			int jmax = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getY).collect(Collectors.toList()));
			for (int j = 0; j <= jmax; j++) {
				for (int i = 0; i <= imax; i++) {
					getPoint(points, i, j).ifPresentOrElse(pt -> res.append(pt.info), () -> res.append("."));
				}
				res.append("\n");
			}
			return res.toString();
		}

	}

	public static Optional<Point> getPoint(Set<Point> pts, int x, int y) {
		Point p = null;
		for (Point i : pts) {
			if (x == i.x && y == i.y) {
				return Optional.ofNullable(i);
			}
		}
		return Optional.ofNullable(p);
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	private static class Point {
		int x;
		int y;
		String info;

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

	}

	public static List<Long> getDuration() {
		A2023Day13 d = new A2023Day13(1);
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

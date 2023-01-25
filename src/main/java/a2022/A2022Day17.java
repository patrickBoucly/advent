package a2022;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Getter;
import lombok.Setter;
import outils.MesOutils;
@Getter
@Setter
public class A2022Day17 extends A2022 {

	public A2022Day17(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day17 d = new A2022Day17(17);

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

	public long s2(boolean b) {
		if (!b) {
			Game g = getGame(b, null);
			Long totalRock = 1000000000000L;
			int taillePattern = 7 * 20;
			long nbPattern = (totalRock - 20) / taillePattern;
			Long reste = (totalRock - 20) - nbPattern * taillePattern;

			Long res = 0L;
			int dec = 20 + reste.intValue();
			res += (s1(b, taillePattern + 20) - s1(b, 20)) * nbPattern + s1(b, dec);

			return res;
		}
		Game g = getGame(b, null);
		Long totalRock = 1000000000000L;
		int taillePattern = 1705;
		int deb=1870;
		//s1(b,1200);
		long nbPattern = (totalRock - deb) / taillePattern;
		Long reste = (totalRock - deb) - nbPattern * taillePattern;
		Long res = 0L;
		int dec = deb + reste.intValue();
		res += (s1(b, taillePattern + deb) - s1(b, deb)) * nbPattern +  s1(b, dec);
		return res;
//too low 1498113207559
	}

	private int s1(boolean b, int nbRockMax) {
		Game g = getGame(b, null);
		g.setPieces(new HashSet<>());
		int nbRocks = 0;
		while (nbRocks < nbRockMax + 1) {
			String type = String.valueOf(nbRocks % 5);
			int sup = type.equals("1") ? 1 : 0;
			int ymax = MesOutils.getMaxIntegerFromList(g.getPlateau().stream()
					.filter(p -> p.x > 0 && p.x < 8 && !p.element.equals(".")).map(Point::getY).toList());
			if (nbRocks == nbRockMax) {
				return ymax;
			}

			//System.out.println(nbRocks + " " + ymax);

			if (nbRocks == 43618) {
			//	System.out.println(nbRocks + " " + ymax);
			}
			int bordMax = MesOutils.getMaxIntegerFromList(
					g.plateau.stream().filter(p -> p.x == 0 && p.element.equals("b")).map(Point::getY).toList());
			if (bordMax < ymax + 7) {
				for (int j = bordMax + 1; j < bordMax + 10; j++) {
					g.plateau.add(new Point(0, j, "b"));
					g.plateau.add(new Point(8, j, "b"));
				}
			}
			Piece p = new Piece(type, new Point(3, ymax + 4 + sup, "r"));
			g.pieces.add(p);
			g.faireTomberPiece(p);
			// System.out.println(g);
			nbRocks++;

		}
		System.out.println(g);
		return MesOutils.getMaxIntegerFromList(
				g.plateau.stream().filter(p -> p.x > 0 && p.x < 8 && !p.element.equals(".")).map(Point::getY).toList());
	}

	public static BigInteger lcm(BigInteger number1, BigInteger number2) {
		BigInteger gcd = number1.gcd(number2);
		BigInteger absProduct = number1.multiply(number2).abs();
		return absProduct.divide(gcd);
	}

	public Integer s1(boolean b) {
		return s1(b, 2023);
	}

	private Game getGame(boolean b, BigInteger windSize) {
		String winds = getInput(b);
		int tmax = 10;
		Set<Point> bord = new HashSet<>();
		for (int i = 0; i < 9; i++) {
			bord.add(new Point(i, 0, "b"));
		}
		for (int j = 0; j < tmax; j++) {
			bord.add(new Point(0, j, "b"));
			bord.add(new Point(8, j, "b"));
		}
		for (int j = 0; j < tmax; j++) {
			bord.add(new Point(1, j, "."));
			bord.add(new Point(7, j, "."));
		}
		if (windSize == null) {
			return new Game(bord, winds, 0);
		}
		return new Game(bord, winds.substring(tmax, windSize.intValue()), 0);
	}
	@Getter
	@Setter
	private class Game {
		Set<Point> plateau;
		Set<Piece> pieces;
		String winds;
		int wp;

		private Set<Point> changeElement(int x, int y, String e, Set<Point> newPlateau) {
			newPlateau.remove(new Point(x, y, ""));
			newPlateau.add(new Point(x, y, e));
			return newPlateau;
		}

		private Point getPts(int x, int y) {
			for (Point pt : plateau) {
				if (x == pt.x && y == pt.y) {
					return pt;
				}
			}
			return null;

		}

		public void faireTomberPiece(Piece p) {
			for (Point pt : p.points) {
				plateau.add(pt);
			}
			boolean descend = true;

			while (descend) {
				wp = wp % winds.length();
				String vent = "";
				if (wp == winds.length()) {
					vent = winds.substring(wp);
				} else {
					vent = winds.substring(wp, wp + 1);
				}
				Piece pj = jetIfPossible(p, vent);
				wp++;
				Pair<Boolean, Piece> fall = fallIfPossible(pj);
				descend = fall.getLeft();
				p = fall.getRight();
			}
		}

		private Pair<Boolean, Piece> fallIfPossible(Piece p) {
			Piece np = new Piece();
			np.setPoints(new ArrayList<>());
			np.setType(p.type);
			boolean canMove = true;
			Set<Point> newPlateau = new HashSet<>();
			for (Point pt : plateau) {
				if (!p.points.contains(pt)) {
					newPlateau.add(new Point(pt.x, pt.y, pt.element));
				}
			}
			for (Point pt : p.points) {
				Point ptb = Point.getPoint(plateau, pieces, pt.x, pt.y - 1).get();
				if (!(ptb.element.equals("r") || ptb.element.equals("."))) {
					canMove = false;
				}
			}
			if (canMove) {
				for (Point pt : p.points) {
					Point npb1 = new Point(pt.x, pt.y - 1, pt.element);
					Point npb2 = new Point(pt.x, pt.y - 1, pt.element);
					Point oldP = new Point(pt.x, pt.y - 1, pt.element);
					newPlateau.remove(oldP);
					newPlateau.add(npb1);
					np.points.add(npb2);
				}
				pieces.add(np);
			} else {
				for (Point pt : p.points) {
					pt.setElement("s");
					newPlateau.add(pt);
				}
				this.setPlateau(newPlateau);
				return Pair.of(canMove, p);
			}
			this.setPlateau(newPlateau);
			return Pair.of(canMove, np);
		}

		private Piece jetIfPossible(Piece p, String w) {
			Piece np = new Piece();
			np.setPoints(new ArrayList<>());
			np.setType(p.type);
			boolean canMove = true;
			Set<Point> newPlateau = new HashSet<>();
			for (Point pt : plateau) {
				if (!p.points.contains(pt)) {
					newPlateau.add(new Point(pt.x, pt.y, pt.element));
				}
			}
			if (w.equals("<")) {
				for (Point pt : p.points) {
					Point ptg = Point.getPoint(plateau, pieces, pt.x - 1, pt.y).get();
					if (!(ptg.element.equals("r") || ptg.element.equals("."))) {
						canMove = false;
					}
				}
				if (canMove) {
					plateau.removeAll(p.points);
					for (Point pt : p.points) {
						newPlateau.remove(new Point(pt.x - 1, pt.y, pt.element));
						newPlateau.add(new Point(pt.x - 1, pt.y, pt.element));
						np.points.add(new Point(pt.x - 1, pt.y, pt.element));
					}
				}
			} else {
				for (Point pt : p.points) {
					Point ptd = Point.getPoint(plateau, pieces, pt.x + 1, pt.y).get();
					if (!(ptd.element.equals("r") || ptd.element.equals("."))) {
						canMove = false;
					}
				}
				if (canMove) {
					plateau.removeAll(p.points);
					for (Point pt : p.points) {
						newPlateau.remove(new Point(pt.x + 1, pt.y, pt.element));
						newPlateau.add(new Point(pt.x + 1, pt.y, pt.element));
						np.points.add(new Point(pt.x + 1, pt.y, pt.element));
					}
				}
			}
			if (!canMove) {
				for (Point pt : p.points) {
					newPlateau.remove(new Point(pt.x, pt.y, pt.element));
					newPlateau.add(new Point(pt.x, pt.y, pt.element));
					np.points.add(new Point(pt.x, pt.y, pt.element));
				}
			}
			this.setPlateau(newPlateau);
			return np;
		}


		public Game(Set<Point> plateau, String winds, int wp) {
			super();
			this.plateau = plateau;
			this.winds = winds;
			this.wp = wp;
		}

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			int amax = MesOutils.getMaxIntegerFromList(plateau.stream().map(Point::getX).collect(Collectors.toList()))
					+ 2;
			int amin = MesOutils.getMinIntegerFromList(plateau.stream().map(Point::getX).collect(Collectors.toList()))
					- 2;
			int omax = MesOutils.getMaxIntegerFromList(plateau.stream().map(Point::getY).collect(Collectors.toList()))
					+ 2;

			for (int j = omax; j >= 0; j--) {
				for (int i = amin; i <= amax; i++) {
					Point.getPoint(plateau, pieces, i, j).ifPresentOrElse(pt -> res.append(pt.toString()),
							() -> res.append("."));
				}
				res.append("\n");
			}
			return res.toString();
		}

	}

	private class Piece {
		String type;
		List<Point> points;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public List<Point> getPoints() {
			return points;
		}

		public void setPoints(List<Point> points) {
			this.points = points;
		}

		public Piece(String type, Point pointEnHautAGauche) {
			super();
			this.type = type;
			this.points = getPointsFromType(pointEnHautAGauche);
		}

		public Piece(String type2, ArrayList<Point> arrayList) {
			// TODO Auto-generated constructor stub
		}

		public Piece() {
			// TODO Auto-generated constructor stub
		}

		private List<Point> getPointsFromType(Point pointEnBasAGauche) {
			List<Point> pts = new ArrayList<>();
			if (type.equals("0")) {
				return getPointsFromType0(pointEnBasAGauche);
			}
			if (type.equals("1")) {
				return getPointsFromType1(pointEnBasAGauche);
			}
			if (type.equals("2")) {
				return getPointsFromType2(pointEnBasAGauche);
			}
			if (type.equals("3")) {
				return getPointsFromType3(pointEnBasAGauche);
			}
			if (type.equals("4")) {
				return getPointsFromType4(pointEnBasAGauche);
			}
			return null;
		}

		private List<Point> getPointsFromType0(Point p) {
			List<Point> res = new ArrayList<>();
			res.add(new Point(p.x, p.y, "r"));
			res.add(new Point(p.x + 1, p.y, "r"));
			res.add(new Point(p.x + 2, p.y, "r"));
			res.add(new Point(p.x + 3, p.y, "r"));
			return res;
		}

		private List<Point> getPointsFromType1(Point p) {
			List<Point> res = new ArrayList<>();
			res.add(new Point(p.x, p.y, "r"));
			res.add(new Point(p.x + 1, p.y, "r"));
			res.add(new Point(p.x + 2, p.y, "r"));
			res.add(new Point(p.x + 1, p.y + 1, "r"));
			res.add(new Point(p.x + 1, p.y - 1, "r"));
			return res;
		}

		private List<Point> getPointsFromType2(Point p) {
			List<Point> res = new ArrayList<>();
			res.add(new Point(p.x, p.y, "r"));
			res.add(new Point(p.x + 1, p.y, "r"));
			res.add(new Point(p.x + 2, p.y, "r"));
			res.add(new Point(p.x + 2, p.y + 1, "r"));
			res.add(new Point(p.x + 2, p.y + 2, "r"));
			return res;
		}

		private List<Point> getPointsFromType3(Point p) {
			List<Point> res = new ArrayList<>();
			res.add(new Point(p.x, p.y, "r"));
			res.add(new Point(p.x, p.y + 1, "r"));
			res.add(new Point(p.x, p.y + 2, "r"));
			res.add(new Point(p.x, p.y + 3, "r"));
			return res;
		}

		private List<Point> getPointsFromType4(Point p) {
			List<Point> res = new ArrayList<>();
			res.add(new Point(p.x, p.y, "r"));
			res.add(new Point(p.x, p.y + 1, "r"));
			res.add(new Point(p.x + 1, p.y, "r"));
			res.add(new Point(p.x + 1, p.y + 1, "r"));
			return res;
		}

	}

	private static class Point {
		int x;
		int y;
		String element;

		public String getElement() {
			return element;
		}

		public void setElement(String element) {
			this.element = element;
		}

		public Point(int x, int y, String element) {
			super();
			this.x = x;
			this.y = y;
			this.element = element;
		}

		public Point(Point pt) {
			super();
			this.x = pt.x;
			this.y = pt.y;
			this.element = pt.element;
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

			if (element.equals("b")) {
				return "*";
			}
			if (element.equals("r")) {
				return "@";
			}
			if (element.equals("s")) {
				return "#";
			}
			return ".";
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

		public static Optional<Point> getPoint(Set<Point> pts, Set<Piece> pieces, int x, int y) {
			Point p = null;
			for (Point pt : pts) {
				if (x == pt.x && y == pt.y) {
					return Optional.ofNullable(pt);
				}
			}
			Point a = new Point(x, y, ".");
			return Optional.ofNullable(a);
		}

	}

	public static List<Long> getDuration() {
		A2022Day17 d = new A2022Day17(17);
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

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

public class A2022Day17 extends A2022 {

	public A2022Day17(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day17 d = new A2022Day17(17);

		long startTime = System.currentTimeMillis();
		System.out.println(d.s1(false));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		// System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public String s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		return null;
	}

	public String s1(boolean b) {
		String winds = getInput(b);
		List<String> typesPiece = List.of("0", "1", "2", "3", "4");
		int tmax=10;
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
		Game g = new Game(bord, winds, 0);
		int nbRocks = 0;
		while (nbRocks < 2023) {
			String type = String.valueOf(nbRocks % 5);
			int ymax = MesOutils.getMaxIntegerFromList(
					g.plateau.stream().filter(p -> p.x > 0 && p.x < 8 && !p.element.equals(".")).map(Point::getY).toList());
			Piece p = new Piece(type, new Point(3, ymax + 4, "r"));
			g.faireTomberPiece(p);
			System.out.println(g);

		}
		System.out.println(g);
		return null;
	}

	private class Game {
		Set<Point> plateau;
		String winds;
		int wp;

		public Set<Point> getPlateau() {
			return plateau;
		}
		private Set<Point> changeElement(int x, int y, String e, Set<Point> newPlateau) {
			newPlateau.remove(new Point(x,y,""));
			newPlateau.add(new Point(x,y,e));
			return newPlateau;
		}
		private Point getPts(int x, int y) {
			for(Point pt:plateau) {
				if (x == pt.x && y == pt.y) {
					return pt;
				}
			}
			return null;
			
		}


		public void faireTomberPiece(Piece p) {
			boolean descend = true;
			while (descend) {
				plateau.removeAll(p.points);
				for (Point pt : p.points) {
					plateau.add(pt);
				}
				System.out.println(this);
				Piece pj = jetIfPossible(p, winds.substring(wp, wp + 1));
				Point pq=getPts(4, 4);
				System.out.println(this);
				descend = fallIfPossible(pj);
			}
		}

		private boolean fallIfPossible(Piece pj) {
			// TODO Auto-generated method stub
			return false;
		}

		private Piece jetIfPossible(Piece p, String w) {
			Piece np=new Piece();
			np.setPoints(new ArrayList<>());
			np.setType(p.type);
			Set<Point> newPlateau=new HashSet<>();
			for(Point pt:plateau) {
				newPlateau.add(new Point(pt.x,pt.y,pt.element));
			}
		//	Piece oldP=new Piece();
			//oldP.setPoints(new ArrayList<>());
			//oldP.setType(p.type);
			boolean canMove=true;
			if(w.equals("<")) {
				for(Point pt:p.points) {
					Point ptg=new Point(pt.x-1,pt.y,".");
					if(plateau.contains(ptg) && Point.getPoint(plateau, pt.x-1, pt.y).get().element.equals(".")) {
						canMove=false;
					}
				}
				if(canMove) {
					for(Point pt:p.points) {
						for(Point q:plateau) {
						
							if(pt.x==q.x && pt.y ==q.y) {
								newPlateau=changeElement(q.x, q.y, ".",newPlateau);
								
							}
							if(pt.x-1==q.x && pt.y ==q.y) {
								System.out.println(pt.x);
								System.out.println(q.x);
								System.out.println(q.x);
								System.out.println(q.y);
								np.points.add(new Point(q.x,q.y,"r"));
								newPlateau=changeElement(q.x, q.y, "r",newPlateau);
							}
						}
					}
				}
			} else {
				for(Point pt:p.points) {
					Point ptd=Point.getPoint(plateau, pt.x+1, pt.y).get();
					if(!(ptd.element.equals("r") || ptd.element.equals("."))) {
						canMove=false;
					}
				}
				if(canMove) {
					for(Point pt:p.points) {
						for(Point q:plateau) {
							if(pt.x==q.x && pt.y ==q.y) {
								newPlateau=changeElement(q.x, q.y, ".",newPlateau);
								
							}
							if(pt.x+1==q.x && pt.y ==q.y) {
								np.points.add(new Point(q.x,q.y,"r"));
								newPlateau=changeElement(q.x, q.y, "r",newPlateau);
							}
						}
					}
				}
			}	
			this.setPlateau(newPlateau);
			if(canMove) {
			//	plateau.removeAll(oldP.points);
			//	plateau.addAll(np.points);
				return np;
			}
			return p;
		}

		public String getWinds() {
			return winds;
		}

		public void setWinds(String winds) {
			this.winds = winds;
		}

		public int getWp() {
			return wp;
		}

		public void setWp(int wp) {
			this.wp = wp;
		}

		public void setPlateau(Set<Point> plateau) {
			this.plateau = plateau;
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
					Point.getPoint(plateau, i, j).ifPresentOrElse(pt -> res.append(pt.toString()),
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

		public static Optional<Point> getPoint(Set<Point> pts, int x, int y) {
			Point p = null;
			for (Point pt : pts) {
				if (x == pt.x && y == pt.y) {
					return Optional.ofNullable(pt);
				}
			}
			return Optional.ofNullable(p);
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

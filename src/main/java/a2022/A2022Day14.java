package a2022;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2022Day14 extends A2022 {

	public A2022Day14(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day14 d = new A2022Day14(14);
		//System.out.println(d.s1(true));
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

	public long s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		Set<Point> points = getPoints(input);
		Caverne cav = new Caverne(points);
		cav.carte.add(new Point(500, 0, "s"));
		int amax = MesOutils.getMaxIntegerFromList(cav.carte.stream().map(Point::getX).collect(Collectors.toList()))
				+ 2;
		int amin = MesOutils.getMinIntegerFromList(cav.carte.stream().map(Point::getX).collect(Collectors.toList()))
				- 2;
		int omax = MesOutils.getMaxIntegerFromList(cav.carte.stream().map(Point::getY).collect(Collectors.toList()));

		for (int j = 0; j <= omax+1; j++) {
			for (int i = amin; i <= amax; i++) {
				if(Point.getPoint(cav.carte, i, j).isEmpty()) {
					cav.carte.add(new Point(i,j,"a"));
				}	
			}
		}
	
			for (int i = amin-200; i <= amax+200; i++) {
				if(Point.getPoint(cav.carte, i, omax+2).isEmpty()) {
					cav.carte.add(new Point(i,omax+2,"r"));
				}	
			}
		
		System.out.println(cav);
		cav.remplir(2);
		System.out.println(cav);
		return cav.carte.stream().filter(p -> p.element.equals("s")).count();
	}

	public long s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		Set<Point> points = getPoints(input);
		Caverne cav = new Caverne(points);
		cav.carte.add(new Point(500, 0, "s"));
		int amax = MesOutils.getMaxIntegerFromList(cav.carte.stream().map(Point::getX).collect(Collectors.toList()))
				+ 2;
		int amin = MesOutils.getMinIntegerFromList(cav.carte.stream().map(Point::getX).collect(Collectors.toList()))
				- 2;
		int omax = MesOutils.getMaxIntegerFromList(cav.carte.stream().map(Point::getY).collect(Collectors.toList()))
				+ 5;

		for (int j = 0; j <= omax; j++) {
			for (int i = amin; i <= amax; i++) {
				if(Point.getPoint(cav.carte, i, j).isEmpty()) {
					cav.carte.add(new Point(i,j,"a"));
				}	
			}
		}
		
		cav.remplir(1);
		//System.out.println(cav);
		return cav.carte.stream().filter(p -> p.element.equals("s")).count()-2;
	}

	private Set<Point> getPoints(List<String> input) {
		Set<Point> points = new HashSet<>();
		for (String s : input) {
			String[] sp = s.split(" -> ");
			for (int k = 0; k < sp.length - 1; k++) {
			//	System.out.println(sp[k] + "  " + sp[k + 1]);
				int absG = Integer.parseInt(sp[k].trim().split(",")[0].trim());
				int ordG = Integer.parseInt(sp[k].trim().split(",")[1].trim());
				int absD = Integer.parseInt(sp[k + 1].trim().split(",")[0].trim());
				int ordD = Integer.parseInt(sp[k + 1].trim().split(",")[1].trim());
				int amin = Math.min(absG, absD);
				int amax = Math.max(absG, absD);
				int omin = Math.min(ordG, ordD);
				int omax = Math.max(ordG, ordD);
				for (int a = amin; a <= amax; a++) {
					for (int o = omin; o <= omax; o++) {
						points.add(new Point(a, o, "r"));
					}
				}
			}
		}
		return points;
	}

	public class Caverne {
		Set<Point> carte;
		int csX;
		int csY;
		int csYmax;
		public Set<Point> getCarte() {
			return carte;
		}

		public void remplir(int partie) {
			csX=500;
			csY=0;
			csYmax=MesOutils.getMaxIntegerFromList(carte.stream().map(Point::getY).collect(Collectors.toList()))+ 5;
			if(partie==2) {
				csYmax=1000000;
			}
		
			boolean fini = false;
			while (!fini) {
				boolean currentSandCanMove = true;
				while (currentSandCanMove) {
					currentSandCanMove = descendre();
				}
				//System.out.println(this);
				long cpt=carte.stream().filter(p -> p.element.equals("s")).count();
				if(cpt%1000==0) {
					System.out.println(carte);
					System.out.println(cpt);
				}
			
				//System.out.println(carte.stream().filter(p -> p.y > csY).map(Point::getY).filter(p -> p > csY).collect(Collectors.toList()));
				if (carte.stream().filter(p -> p.y > csY).map(Point::getY).filter(p -> p > csY).count()<1L) {
					fini = true;
				}
				if(csX==500 && csY==0) {
					fini=true;
				}
				csX=500;
				csY=0;
				changeElement(csX,csY,"s");
			}

		}

		private boolean descendre() {
			if(csYmax<csY) {
				return false;
			}
			Optional<Point> enBas = Point.getPoint(carte, csX, csY + 1);
			if(enBas.isEmpty()){
				return false;
			}
			if (enBas.get().element.equals("a")) {
				deplacer(csX, csY + 1,csX,csY);
				return true;
			} else if (List.of("s", "r").contains(enBas.get().element)) {
				boolean diagG = descendreDiagG();
				if (diagG) {
					return true;
				}
				boolean diagD = descendreDiagD();
				if (diagD) {
					return true;
				}
			}
			return false;
		}

		private boolean descendreDiagD() {
			Optional<Point> enBasG = Point.getPoint(carte, csX+1, csY +1);
			if (enBasG.isEmpty()) {
				deplacer(csX+1, csY+1,csX,csY );
				return true;
			} else if (List.of("s", "r").contains(enBasG.get().element)) {
				return false;
			} else {
				deplacer(csX+1, csY+1,csX,csY );
				return true;
			}
			
		}

		private boolean descendreDiagG() {
			Optional<Point> enBasG = Point.getPoint(carte, csX-1, csY+1 );
			if (enBasG.isEmpty()) {
				deplacer(csX-1, csY+1 ,csX,csY );
				return true;
			} else if (List.of("s", "r").contains(enBasG.get().element)) {
				return false;
			} else {
				deplacer(csX-1, csY+1 ,csX,csY );
				return true;
			}
		}

		private void deplacer(int x, int y,int xeff,int yeff) {
			Optional<Point> dest = Point.getPoint(carte, x, y);
			if (dest.isEmpty()) {
				Point ptDest = new Point(x, y, "s");
				carte.add(ptDest);
				changeElement(xeff, yeff,"a");
				csX = ptDest.x;
				csY = ptDest.y;
			} else {
				changeElement(xeff, yeff,"a");
				changeElement(x,y,"s");
				csX = x;
				csY = y;
			}
			
		}

		private void changeElement(int x, int y, String e) {
			for(Point pt:carte) {
				if (x == pt.x && y == pt.y) {
					pt.setElement(e);
				}
			}
			
		}

		public void setCarte(Set<Point> carte) {
			this.carte = carte;
		}

		public Caverne(Set<Point> carte) {
			super();
			this.carte = carte;
		}

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			int amax = MesOutils.getMaxIntegerFromList(carte.stream().map(Point::getX).collect(Collectors.toList()))
					+ 2;
			int amin = MesOutils.getMinIntegerFromList(carte.stream().map(Point::getX).collect(Collectors.toList()))
					- 2;
			int omax = MesOutils.getMaxIntegerFromList(carte.stream().map(Point::getY).collect(Collectors.toList()))
					+ 2;

			for (int j = 0; j <= omax; j++) {
				for (int i = amin; i <= amax; i++) {
					Point.getPoint(carte, i, j).ifPresentOrElse(pt -> res.append(pt.toString()), () -> res.append("."));
				}
				res.append("\n");
			}
			return res.toString();
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
			if (element.equals("s")) {
				return "+";
			}
			if (element.equals("r")) {
				return "#";
			}
			if (element.equals("a")) {
				return ".";
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
		A2022Day14 d = new A2022Day14(14);
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

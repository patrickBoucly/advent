package aocmaven.a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.checkerframework.checker.units.qual.C;

import outils.GraphWeighted;
import outils.MesOutils;
import outils.NodeWeighted;

public class A2021Day15 extends A2021 {

	public A2021Day15(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day15 d = new A2021Day15(15);
		// d.s1(true);
		long startTime = System.currentTimeMillis();
		// d.s1(false);
		//System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();

		 System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}
private String s2(boolean b) {
		
		List<Point> p1 = getPoints(b);
		List<Point> points=getAllPoints(p1);
		System.out.println(points.size());
		int imax = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getX).collect(Collectors.toList()));
		List<NodeWeighted> nodes = new ArrayList<>();
		for (Point p : points) {
			String name = String.valueOf(p.x) + "_" + String.valueOf(p.y);
			NodeWeighted n = new NodeWeighted(p.value, name, p.x, p.y, name);
			nodes.add(n);
		}

		GraphWeighted graphWeighted = new GraphWeighted(true);

		for (int j = 0; j <= imax; j++) {
			for (int i = 0; i < imax; i++) {
				NodeWeighted A = getNode(i, j, nodes);
				NodeWeighted B = getNode(i + 1, j, nodes);
				graphWeighted.addEdge(A, B, B.n);
				
			}
		}
		for (int i = 0; i <= imax; i++) {
			for (int j = 0; j < imax; j++) {

				NodeWeighted A = getNode(i, j, nodes);
				NodeWeighted B = getNode(i, j + 1, nodes);
				graphWeighted.addEdge(A, B, B.n);
		//		System.out.println("lien : " + A.name + " " + B.name+ " "+B.n);
			}
		}
		String[] res = graphWeighted.DijkstraShortestPath(getNode(0, 0, nodes), getNode(imax, imax, nodes));
		System.out.println(res[0]);
		System.out.println(res[1]);
		return res[0];
	}

	private List<Point> getAllPoints(List<Point> p1) {
		int size = MesOutils.getMaxIntegerFromList(p1.stream().map(Point::getX).collect(Collectors.toList()))+1;
		List<Point> allPoints= new ArrayList<>();
		for(int i=0;i<5;i++) {
			for(int j=0;j<5;j++) {
				for(Point p:p1) {
					allPoints.add(new Point(p.x+i*size,p.y+j*size,newRisk(i,j,p.value)));
				}
			}
		}
	return allPoints;
}

	private int newRisk(int i, int j, int value) {
		int res= value;
		int dec=i+j;
		if(dec==0) {
			return res;
		}
		for(int k=1;k<=dec;k++) {
			if(res == 9) {
				res=1;
			} else {
				res++;
			}
		}
		return res;
	}

	private String s1(boolean b) {
		
		List<Point> points = getPoints(b);
		int imax = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getX).collect(Collectors.toList()));
		List<NodeWeighted> nodes = new ArrayList<>();
		for (Point p : points) {
			String name = String.valueOf(p.x) + "_" + String.valueOf(p.y);
			NodeWeighted n = new NodeWeighted(p.value, name, p.x, p.y, name);
			nodes.add(n);
		}

		GraphWeighted graphWeighted = new GraphWeighted(true);

		for (int j = 0; j <= imax; j++) {
			for (int i = 0; i < imax; i++) {
				NodeWeighted A = getNode(i, j, nodes);
				NodeWeighted B = getNode(i + 1, j, nodes);
				graphWeighted.addEdge(A, B, B.n);
			//	System.out.println("lien : " + A.name + " " + B.name+ " "+B.n);
			}
		}
		for (int i = 0; i <= imax; i++) {
			for (int j = 0; j < imax; j++) {

				NodeWeighted A = getNode(i, j, nodes);
				NodeWeighted B = getNode(i, j + 1, nodes);
				graphWeighted.addEdge(A, B, B.n);
		//		System.out.println("lien : " + A.name + " " + B.name+ " "+B.n);
			}
		}
		String[] res = graphWeighted.DijkstraShortestPath(getNode(0, 0, nodes), getNode(imax, imax, nodes));
		System.out.println(res[0]);
		System.out.println(res[1]);
		return res[0];
	}

	private NodeWeighted getNode(int i, int j, List<NodeWeighted> nodes) {
		for (NodeWeighted n : nodes) {
			if (n.name.equals(String.valueOf(i) + "_" + String.valueOf(j))) {
				return n;
			}
		}
		return null;
	}

	private long s1old(boolean b) {
		List<Point> points = getPoints(b);
		List<Chemin> chemins = getChemins(points);
		return MesOutils
				.getMinLongFromList(chemins.stream().mapToLong(Chemin::getCout).boxed().collect(Collectors.toList()));
	}

	private List<Chemin> getChemins(List<Point> points) {
		int imax = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getX).collect(Collectors.toList()));
		int coutCheminSimple = getCoutCheminSimple(points);
		System.out.println(coutCheminSimple);
		Chemin ch1 = new Chemin(new ArrayList<>());
		Chemin ch2 = new Chemin(new ArrayList<>());
		ch1.add(Point.getPoint(1, 0, points));
		ch2.add(Point.getPoint(0, 1, points));
		List<Chemin> chemins = new ArrayList<>();

		chemins.add(ch1);
		chemins.add(ch2);
		boolean poursuivre = true;

		do {

			poursuivre = false;
			List<Chemin> newChemins = new ArrayList<>();
			int nbDetour = 0;
			for (Chemin c : chemins) {
				if (c.getCout() < coutCheminSimple && !c.isEnd(imax)) {
					poursuivre = true;
					int posx = c.getLastPoint().x;
					int posy = c.getLastPoint().y;
					if (posx < imax) {
						Chemin newC = new Chemin(c.getItineraire());
						newC.add(Point.getPoint(posx + 1, posy, points));
						newChemins.add(newC);
					}
					if (posy < imax) {
						Chemin newC = new Chemin(c.getItineraire());
						newC.add(Point.getPoint(posx, posy + 1, points));
						newChemins.add(newC);
					}
					if (nbDetour > 50) {

						if (posy > 0) {
							nbDetour++;
							Chemin newC = new Chemin(c.getItineraire());
							newC.add(Point.getPoint(posx, posy - 1, points));
							newChemins.add(newC);
						}
						if (posx > 0) {
							nbDetour++;
							Chemin newC = new Chemin(c.getItineraire());
							newC.add(Point.getPoint(posx - 1, posy, points));
							newChemins.add(newC);
						}
					}

				}
			}
			if (poursuivre) {
				chemins = newChemins;
				for (Chemin c : chemins) {
					// System.out.println(c);
				}
			}

		} while (poursuivre);

		return chemins;
	}

	private int getCoutCheminSimple(List<Point> points) {
		int imax = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getX).collect(Collectors.toList()));
		List<Point> simples = new ArrayList<>();
		for (int i = 1; i < imax; i++) {
			simples.add(Point.getPoint(i, 0, points));
			simples.add(Point.getPoint(imax, i - 1, points));
		}
		simples.add(Point.getPoint(imax, imax, points));
		return simples.stream().map(Point::getValue).reduce(0, (a, b) -> a + b);
	}

	private List<Point> getPoints(boolean c) {
		List<Point> points = new ArrayList<>();
		List<String> lignes = Arrays.asList(getInput(c).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		for (int i = 0; i < lignes.size(); i++) {
			for (int j = 0; j < lignes.size(); j++) {
				points.add(new Point(i, j, Integer.parseInt(lignes.get(j).substring(i, i + 1))));
			}
		}
		return points;
	}

	public static class Chemin {
		private List<Point> itineraire;

		@Override
		public String toString() {
			return "Chemin [itineraire=" + itineraire + "]";
		}

		public List<Point> getItineraire() {
			return itineraire;
		}

		public boolean isEnd(int imax) {
			Point last = itineraire.get(itineraire.size() - 1);
			Point end = new Point(imax, imax, 0);
			return last.equals(end);
		}

		public void add(Point point) {
			List<Point> nwIt = new ArrayList<>(itineraire);
			nwIt.add(point);
			setItineraire(nwIt);
		}

		public Point getLastPoint() {
			return itineraire.get(itineraire.size() - 1);
		}

		public void setItineraire(List<Point> itineraire) {
			this.itineraire = itineraire;
		}

		public int getCout() {
			return itineraire.stream().map(Point::getValue).reduce(0, (a, b) -> a + b);
		}

		public Chemin(List<Point> itineraire) {
			super();
			this.itineraire = itineraire;
		}

	}

	public static class Point {
		int x;
		int y;
		int value;

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + ", value=" + value + "]";
		}

		public Point(int x, int y, int value) {
			super();
			this.x = x;
			this.y = y;
			this.value = value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
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
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
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

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public static Point getPoint(int i, int j, List<Point> pts) {
			Point p = new Point(i, j, 0);
			for (Point pt : pts) {
				if (pt.equals(p)) {
					return pt;
				}
			}
			return null;
		}

	}

	public static List<Long> getDuration() {
		A2021Day15 d = new A2021Day15(15);
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

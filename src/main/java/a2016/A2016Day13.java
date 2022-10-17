package a2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import outils.UniformCostSearch;
import outils.UniformCostSearch.Graph;

public class A2016Day13 extends A2016 {
	public A2016Day13(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day13 d = new A2016Day13(13);
		long startTime = System.currentTimeMillis();
		System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public Integer s1(boolean b) {
		UniformCostSearch.Graph graph = new UniformCostSearch.Graph();
		Integer input = 10;
		int reachX = 7;
		int reachY = 4;
		int imax = 9;
		if(b) {
		reachX = 31;
		reachY = 39;
		imax = 100;
		input=1352;
		}
		init(graph,b,imax,input);
		return graph.uniformSearch(imax+2, reachY*(imax+1)+reachX);
	}
	public Integer s2(boolean b) {
		UniformCostSearch.Graph graph = new UniformCostSearch.Graph();
		Integer input = 10;
		int imax = 9;
		if(b) {
		imax = 50;
		input=1352;
		}
		init(graph,b,imax,input);
		int res=1;//cost =0 pour position initiale
		for(int x=0;x<imax;x++) {
			for(int y=0;y<imax;y++) {
				int cost=graph.uniformSearch(imax+2, y*(imax+1)+x);
				if(cost<=50  && cost>0) {
					res++;
				}
			}
		}
		return res;
	}


	private void init(Graph graph, boolean b, int imax, Integer input) {

		List<Point> points = new ArrayList<>();
		for (int i = 0; i <= imax; i++) {
			for (int j = 0; j <= imax; j++) {
				points.add(new Point(i, j, isOpen(i, j, input)));
			}
		}
		points.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
		for (int i = 0; i < points.size(); i++) {
			graph.addNode(i);
		}
		for (int j = 0; j < imax; j++) {
			for (int i = 0; i < imax; i++) {
				Point mpt = Point.getPoint(i, j, points);
				Point mptd = Point.getPoint(i + 1, j, points);
				Point mptb = Point.getPoint(i, j + 1, points);
				if (mpt.open && mptd.open) {
					graph.addEdge(i+j*(imax+1),1+i+j*(imax+1),1);
					graph.addEdge(1+i+j*(imax+1),i+j*(imax+1),1);
				}
				if (mpt.open && mptb.open) {
					graph.addEdge(i+j*(imax+1),i+(j+1)*(imax+1),1);
					graph.addEdge(i+(j+1)*(imax+1),i+j*(imax+1),1);
				}
				
			}
		}
		
	}

	private boolean isOpen(int x, int y, Integer input) {
		return nombrePairdeUn(Integer.toBinaryString((calculate(x, y, input))));
	}

	private boolean nombrePairdeUn(String s) {
		Integer nbUn = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.substring(i, i + 1).equals("1")) {
				nbUn++;
			}
		}
		return nbUn % 2 == 0;
	}

	private int calculate(int x, int y, Integer input) {
		return x * x + 3 * x + 2 * x * y + y + y * y + input;
	}

	
	public static class Point {
		int x;
		int y;
		boolean open;

		@Override
		public String toString() {
			String symbol = open ? "O" : "#";
			return "[" + symbol + "]";
		}

		public Point(int x, int y, boolean open) {
			super();
			this.x = x;
			this.y = y;
			this.open = open;
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

		public boolean isOpen() {
			return open;
		}

		public void setOpen(boolean open) {
			this.open = open;
		}

		public static Point getPoint(int i, int j, List<Point> pts) {
			Point p = new Point(i, j, true);
			for (Point pt : pts) {
				if (pt.equals(p)) {
					return pt;
				}
			}
			return null;
		}
	}

	public static List<Long> getDuration() {
		A2016Day13 d = new A2016Day13(13);
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

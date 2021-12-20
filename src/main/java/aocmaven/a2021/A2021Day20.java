package aocmaven.a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import aocmaven.a2021.A2021Day9.Point;
import outils.MesOutils;

public class A2021Day20 extends A2021 {

	public A2021Day20(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day20 d = new A2021Day20(20);
		long startTime = System.currentTimeMillis();
	//     System.out.println(d.s1(false));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(false));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}
	private long s2(boolean b) {
		Maps map=getMap(b);
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		String decode = lignes.get(0);
		Set<Point> nextEnhance= new HashSet<A2021Day20.Point>();
		for(int i=0;i<50;i++) {
		//	System.out.println(map);
			nextEnhance=enhance(map.pts,decode);
		//	nextEnhance=clean(nextEnhance);
			
			System.out.println("i :"+i);
	//	    System.out.println(new Maps(nextEnhance));
            System.out.println(nextEnhance.stream().filter(p->p.pixel.equals("#")).count());
		    map.setPts(nextEnhance);
		}
		System.out.println(map);
		return nextEnhance.stream().filter(p->p.pixel.equals("#")).count();
	}
	

	private long s1(boolean b) {
		Maps map=getMap(b);
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		String decode = lignes.get(0);
		System.out.println(map);
		Set<Point> firstEnhance=enhance(map.pts,decode);
		Maps mf1=new Maps(firstEnhance);
		System.out.println(mf1);
		Set<Point> secondEnhance=enhance(firstEnhance,decode);
		Maps mf2=new Maps(secondEnhance);
		System.out.println(mf2);
		return secondEnhance.stream().filter(p->p.pixel.equals("#")).count();
	}

	private Maps getMap(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		Maps map = new Maps(new HashSet<A2021Day20.Point>());
		for(int j=2;j<lignes.size();j++) {
			for(int i=0;i<lignes.get(2).length();i++) {
				Point np=new Point(i,j-2,lignes.get(j).substring(i, i+1));
				map.add(np);
			}
		}
		Set<Point> nextEnhance= new HashSet<A2021Day20.Point>();
		int ymin = MesOutils.getMinIntegerFromList(map.pts.stream().map(Point::getY).collect(Collectors.toList()));
		int xmin = MesOutils.getMinIntegerFromList(map.pts.stream().map(Point::getX).collect(Collectors.toList()));
		int ymax = MesOutils.getMaxIntegerFromList(map.pts.stream().map(Point::getY).collect(Collectors.toList()));
		int xmax = MesOutils.getMaxIntegerFromList(map.pts.stream().map(Point::getX).collect(Collectors.toList()));
		for(int i=xmin-3;i<=xmax+3;i++) {
			for(int j=ymin-3;j<=ymax+3;j++) {
				Optional<Point> p=Point.getPoint(map.pts, i, j);
				Point np;
				if(p.isPresent()) {
					np=p.get();
				} else {
					np=new Point(i,j,".");
				}
				nextEnhance.add(new Point(np.x,np.y,np.pixel));
				
			}
		}	
		return new Maps(nextEnhance);
	}

	

	private Set<Point> enhance(Set<Point> pts, String decode) {
		Set<Point> firstEnhance=new HashSet<A2021Day20.Point>();
		String code="";
		int valCode;
		int ymin = MesOutils.getMinIntegerFromList(pts.stream().map(Point::getY).collect(Collectors.toList()));
		int xmin = MesOutils.getMinIntegerFromList(pts.stream().map(Point::getX).collect(Collectors.toList()));
		int ymax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getY).collect(Collectors.toList()));
		int xmax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getX).collect(Collectors.toList()));
		for(int i=xmin-2;i<=xmax+2;i++) {
			for(int j=ymin-2;j<=ymax+2;j++) {
				Optional<Point> p=Point.getPoint(pts, i, j);
				Point np;
				if(p.isPresent()) {
					np=p.get();
				} else {
					np=new Point(i,j,".");
				}
					code=getStringFromVoisins(pts,np);
					valCode=getValueFromCode(code);
					firstEnhance.add(new Point(np.x,np.y,decoder(valCode,decode)));
				
			}
		}	
		return firstEnhance;
	}

	private String decoder(int valCode, String decode) {
		return decode.substring(valCode, valCode+1);
	}

	private int getValueFromCode(String code) {
		String bin="";
		for(int pos=0;pos<code.length();pos++) {
			bin+=code.substring(pos, pos+1).equals(".") ? "0":"1";
		}
		return Integer.parseInt(bin, 2);
	}

	private String getStringFromVoisins(Set<Point> pts, Point p) {
		List<Point> v=getAdj(pts,p);
		v.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
		String res="";
		for(Point pt:v) {
			res+=pt.getPixel();
		}
		return res;
	}
	public List<Point> getAdj(Set<Point> pts, Point p) {
		List<Point> adj = new ArrayList<>();
		Point.getPoint(pts, p.x - 1, p.y - 1).ifPresentOrElse(pt -> adj.add(pt),
				() -> adj.add(new Point(p.x, p.y - 1, ".")));
		Point.getPoint(pts, p.x, p.y - 1).ifPresentOrElse(pt -> adj.add(pt),
				() -> adj.add(new Point(p.x, p.y - 1, ".")));
		Point.getPoint(pts, p.x + 1, p.y - 1).ifPresentOrElse(pt -> adj.add(pt),
				() -> adj.add(new Point(p.x + 1, p.y - 1, ".")));
		Point.getPoint(pts, p.x - 1, p.y).ifPresentOrElse(pt -> adj.add(pt),
				() -> adj.add(new Point(p.x - 1, p.y, ".")));
		Point.getPoint(pts, p.x, p.y).ifPresentOrElse(pt -> adj.add(pt),
				() -> adj.add(new Point(p.x, p.y, ".")));
		Point.getPoint(pts, p.x + 1, p.y).ifPresentOrElse(pt -> adj.add(pt),
				() -> adj.add(new Point(p.x + 1, p.y, ".")));
		Point.getPoint(pts, p.x - 1, p.y + 1).ifPresentOrElse(pt -> adj.add(pt),
				() -> adj.add(new Point(p.x - 1, p.y + 1, ".")));
		Point.getPoint(pts, p.x, p.y + 1).ifPresentOrElse(pt -> adj.add(pt),
				() -> adj.add(new Point(p.x, p.y + 1, ".")));
		Point.getPoint(pts, p.x + 1, p.y + 1).ifPresentOrElse(pt -> adj.add(pt),
				() -> adj.add(new Point(p.x + 1, p.y + 1, ".")));
		return adj;
	}
	
	public static class Maps {
		Set<Point> pts;

		public Maps(Set<Point> pts) {
			super();
			this.pts = pts;
		}

		public Set<Point> getPts() {
			return pts;
		}

		public void setPts(Set<Point> pts) {
			this.pts = pts;
		}
		public void add(Point p) {
			Set<Point> pt=getPts();
			pt.add(p);
			setPts(pt);
		}

		@Override
		public String toString() {
			StringBuilder res=new StringBuilder();
			//pts.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
			int imax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getX).collect(Collectors.toList()));
			int imin = MesOutils.getMinIntegerFromList(pts.stream().map(Point::getX).collect(Collectors.toList()));
			for(int j=imin;j<imax;j++) {
				for(int i=imin;i<imax;i++) {
				Point.getPoint(pts, i, j).ifPresentOrElse(pt -> res.append(pt.pixel),() -> res.append("."));
				
				}
				res.append("\n");
			}
			return res.toString();
		}
		
	}
	public static class Point {
		int x;
		int y;
		String pixel;

		public Point(int x, int y, String pixel) {
			super();
			this.x = x;
			this.y = y;
			this.pixel = pixel;
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
			return pixel;
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

		public String getPixel() {
			return pixel;
		}

		public void setPixel(String pixel) {
			this.pixel = pixel;
		}

		public static Optional<Point> getPoint(Set<Point> pts, int x, int y) {
			Point p = null;
			for (Point i : pts) {
				if (x == i.x && y == i.y) {
					p = new Point(x, y, i.pixel);
				}
			}
			return Optional.ofNullable(p);
		}
	}

	public static List<Long> getDuration() {
		A2021Day20 d = new A2021Day20(20);
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

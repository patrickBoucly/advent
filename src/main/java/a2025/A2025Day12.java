package a2025;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class A2025Day12 extends A2025 {
	public A2025Day12(int day) {
		super(day);
	}
	public static void main(String[] args0) {
		A2025Day12 d = new A2025Day12(12);
		System.out.println(d.s1(false));
		//System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		//d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		//System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
	//	System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
		//		+ (endTime - startTime) + " milliseconds");
	}

	public Integer s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Game g = new Game();
		g.init(inputL);
		return g.solve1();
	}

	public Long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Game g = new Game();
		g.init(inputL);
		return g.solve2();
	}
	
	@Data
	@NoArgsConstructor
	public static class Game {
		List<String> inputL;
		List<Region> regions;
		Set<Shape> shapes;
		public Integer solve1() {
			int res=0;
			for(Region r:regions) {		
				res+=testFit(r);
			}
			return res;
		}
		public Integer testFit(Region r) {
			Set<Shape> shapeToPlace=new HashSet<>();
			for(int k=0;k<r.qtt.get(0);k++) {
				shapeToPlace.add(shapes.stream().filter(sh->sh.index.equals(0)).findAny().get());
			}
			for(int k=0;k<r.qtt.get(1);k++) {
				shapeToPlace.add(shapes.stream().filter(sh->sh.index.equals(1)).findAny().get());
			}
			for(int k=0;k<r.qtt.get(2);k++) {
				shapeToPlace.add(shapes.stream().filter(sh->sh.index.equals(2)).findAny().get());
			}
			for(int k=0;k<r.qtt.get(3);k++) {
				shapeToPlace.add(shapes.stream().filter(sh->sh.index.equals(3)).findAny().get());
			}
			for(int k=0;k<r.qtt.get(4);k++) {
				shapeToPlace.add(shapes.stream().filter(sh->sh.index.equals(4)).findAny().get());
			}
			for(Shape sh:shapeToPlace) {
				for(int rot=0;rot<3;rot++) {
					sh.rotate();
					for(int lc=1;lc<r.l-1;lc++) {
						for(int hc=1;hc<r.h-1;hc++) {
						//	(suite)
						}
					}
				}
				
			}
			return null;
			
		}
		public void init(List<String> inputL2) {
			inputL=inputL2;
			regions=new ArrayList<>();
			shapes=new HashSet<>();
			int i=0;
			while(i<30) {
				Shape sh=new Shape();
				Set<Point> pts=new HashSet<>();
				String li1=inputL.get(i+1);
				String li2=inputL.get(i+2);
				String li3=inputL.get(i+3);
				for(int j=0;j<3;j++) {
					if(li1.substring(j, j+1).equals("#")) {
						pts.add(new Point(j,0));
					}
					if(li2.substring(j, j+1).equals("#")) {
						pts.add(new Point(j,1));
					}
					if(li3.substring(j, j+1).equals("#")) {
						pts.add(new Point(j,2));
					}
				}
				sh.setIndex(Integer.parseInt(inputL.get(i).substring(0, 1)));
				sh.setPts(pts);
				shapes.add(sh);
				i+=5;
			}
			for(int k=31;k<inputL.size();k++) {
				Region r=new Region();
				String lk=inputL.get(k);
				r.fit=false;
				r.l=Integer.parseInt(lk.substring(0, lk.indexOf("x")));
				r.h= Integer.parseInt(lk.substring(lk.indexOf("x")+1,lk.indexOf(":")));
				String[] flk=lk.substring(lk.indexOf(" ")+1).split(" ");
				List<Integer> qtt=new ArrayList<>();
				for(String m:flk) {
					qtt.add(Integer.parseInt(m));
				}
				r.setQtt(qtt);
				regions.add(r);
			}
			System.out.println(this);
			System.out.println(" ");
			
		}
		public Long solve2() {
			Long r = 0L;
			return r;
		}
	}
	@Data
	@NoArgsConstructor
	public static class Region {
		int h;
		int l;
		List<Integer> qtt;
		Boolean fit;
		
	}
	
	@Data
	@NoArgsConstructor
	public static class Shape {
		public void rotate() {
			// TODO Auto-generated method stub
			
		}
		Integer index;
		Set<Point> pts;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	private static class Point {
		int x;
		int y;
	}

	public static Optional<Point> getPoint(Set<Point> pts, int x, int y) {
		Point p = null;
		for (Point i : pts) {
			if (x == i.x && y == i.y) {
				p = new Point(x, y);
			}
		}
		return Optional.ofNullable(p);
	}

	public static List<Long> getDuration() {
		A2025Day12 d = new A2025Day12(12);
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
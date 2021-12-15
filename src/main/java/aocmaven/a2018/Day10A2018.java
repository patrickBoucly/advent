package aocmaven.a2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day10A2018 {

	public static void main(String[] args0) {
		s1();

	}

	private static void s1() {
		String inputAll = read();
		String[] messages = inputAll.split("\n");
		List<Point> pts = new ArrayList<>();
		HashMap<Integer, List<Point>> situation = new HashMap<>();
		int x;
		int y;
		int vx;
		int vy;
		for (String m : messages) {
			x=Integer.parseInt(m.substring(10, 16).trim());
			y= Integer.parseInt(m.substring(18, 24).trim());
			vx=Integer.parseInt(m.substring(36, 38).trim());
			vy=Integer.parseInt(m.substring(40, 42).trim());
			pts.add(new Point(x,y,vx,vy));
		}
		
		situation.put(0, pts);
		for(int i=0;i<13000;i++) {
			if(i % 1000 ==0) {
				//System.out.println(i);
			}
			pts=nextPosition(pts);
			situation.put(i,pts );
		}
		for(int i=10900;i<12000;i++) {
			quandAfficher(situation.get(i), i);
		}
	}

	private static void quandAfficher(List<Point> list, int i) {
		boolean a =true;
		for(Point p:list) {
			if(!(p.x+p.y<400 && p.x+p.y > -400)) {
				a=false;
			}
		}
		if(i==10941) {
		afficher(list,i);
		}
	}

	private static void afficher(List<Point> list, int i) {
		System.out.println("seconde "+i);
		String line= "";
		for(int y=-300;y<300;y++) {
			for(int x=-300;x<300;x++) {
				if(list.contains(new Point(x,y,0,0))) {
					line+="#";
				}else {
					line+="0";
				}
				
			}
			line+="\n";
		}
		 Path filePath = Paths.get("C:/", "tmp/aoc/t", "test"+i+".txt");
		 
	        try
	        {
	            //Write content to file
	            Files.writeString(filePath, line, StandardOpenOption.CREATE);
	        } 
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	        }
		
	}

	private static List<Point> nextPosition(List<Point> pts) {
		List<Point> newPosition = new ArrayList<>();
		for(Point p:pts) {
			newPosition.add(new Point(p.x+p.vx,p.y+p.vy,p.vx,p.vy));
		}
		return newPosition;
	}

	private static String read() {
		Path path = Paths.get("C:\\Users\\z11r88\\eclipse-workspace2\\aocmaven\\src\\main\\resources\\src\\advent_of_code\\main\\resources\\a2018\\input10");
		String content = "";
		try {
			content = Files.readString(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;

	}

	public static class Point {
		int x;
		int y;
		int vx;
		int vy;
		public Point(int x, int y, int vx, int vy) {
			super();
			this.x = x;
			this.y = y;
			this.vx = vx;
			this.vy = vy;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + vx;
			result = prime * result + vy;
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
		
	}
}

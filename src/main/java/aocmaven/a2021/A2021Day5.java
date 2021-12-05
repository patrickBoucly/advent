package aocmaven.a2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class A2021Day5 {

	public static void main(String[] args0) {
		s1();
		
		
		
	}

	
	private static void s1() {
		List<String> input;
		input = Arrays.asList(read().split("\n"));
		for(String s:input) {
			List<String> pointS =Arrays.asList(s.split("->"));
			for(String ps:pointS) {
				List<String> pointS =Arrays.asList( ps.trim().split(",");
			}
		}
		
	}


	public static class Wind {
		Point oneEnd;
		Point otherEnd;
		public Wind(Point oneEnd, Point otherEnd) {
			super();
			this.oneEnd = oneEnd;
			this.otherEnd = otherEnd;
		}
		public Point getOneEnd() {
			return oneEnd;
		}
		public void setOneEnd(Point oneEnd) {
			this.oneEnd = oneEnd;
		}
		public Point getOtherEnd() {
			return otherEnd;
		}
		public void setOtherEnd(Point otherEnd) {
			this.otherEnd = otherEnd;
		}
		
	}
	
	public static class Point {
		int x;
		int y;
		int nbNuage;

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

		public int getNbNuage() {
			return nbNuage;
		}

		public void setNbNuage(int nbNuage) {
			this.nbNuage = nbNuage;
		}

		public Point(int x, int y, int nbNuage) {
			super();
			this.x = x;
			this.y = y;
			this.nbNuage = nbNuage;
		}

	}

	private static String read() {
		Path path = Paths.get(
				"C:\\git_repositories\\advent\\src\\main\\resources\\src\\advent_of_code\\main\\resources\\a2021\\input5");
		String content = "";
		try {
			content = Files.readString(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;

	}
}

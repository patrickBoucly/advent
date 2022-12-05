package a2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class A2022Day4 extends A2022 {

	public A2022Day4(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day4 d = new A2022Day4(4);
		System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1=endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day "+ d.day+" run 1 took "+timeS1+" milliseconds, run 2 took " + (endTime - startTime) + " milliseconds");
		
	}

	public long s2(boolean b) {
		List<String> input=Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		List<Line> lines = getLines(input);
		return lines.stream().filter(Line::overlap).count();
	}
	
	

	public long s1(boolean b) {
		List<String> input=Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		List<Line> lines = getLines(input);
		return lines.stream().filter(Line::inclusion).count();
	}

	private List<Line> getLines(List<String> input) {
		List<Line> lines=new ArrayList<>();
		for(String s:input) {
			int min1=Integer.valueOf(s.split(",")[0].trim().split("-")[0].trim());
			int min2=Integer.valueOf(s.split(",")[1].trim().split("-")[0].trim());
			int max1=Integer.valueOf(s.split(",")[0].trim().split("-")[1].trim());
			int max2=Integer.valueOf(s.split(",")[1].trim().split("-")[1].trim());
			lines.add(new Line(min1,min2,max1,max2));
		}
		return lines;
	}

	private class Line{
		int min1;
		int min2;
		int max1;
		int max2;
		public int getMin1() {
			return min1;
		}
		public void setMin1(int min1) {
			this.min1 = min1;
		}
		public int getMin2() {
			return min2;
		}
		public void setMin2(int min2) {
			this.min2 = min2;
		}
		public int getMax1() {
			return max1;
		}
		public void setMax1(int max1) {
			this.max1 = max1;
		}
		public int getMax2() {
			return max2;
		}
		public void setMax2(int max2) {
			this.max2 = max2;
		}
		public Line(int min1, int min2, int max1, int max2) {
			super();
			this.min1 = min1;
			this.min2 = min2;
			this.max1 = max1;
			this.max2 = max2;
		}
		public boolean inclusion() {
			if(min1<=min2 && max1>=max2) {
				return true;
			}
			if(min2<=min1 && max2>=max1) {
				return true;
			}
			return false;
		}
		public boolean overlap() {
			if(min1<=max2 && min1>=min2) {
				return true;
			}
			if(max1<=max2 && max1>=min2) {
				return true;
			}
			if(min2<=max1 && min2>=min1) {
				return true;
			}
			if(max2<=max1 && max2>=min1) {
				return true;
			}
			return false;
		}
	}
	
	public static List<Long> getDuration() {
		A2022Day4 d = new A2022Day4(4);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1,endTime - startTime);
	}

	
}

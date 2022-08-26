package a2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2016Day3 extends A2016 {

	public A2016Day3(int day) {
		super(day);
	}
	public static void main(String[] args0) {
		A2016Day3 d = new A2016Day3(3);
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
	
	private int s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim())
				.collect(Collectors.toList());
		List<Triangle> trgs= new ArrayList<>();
		for(String s:input) {
			List<Integer> lgs = Arrays.asList(s.trim().split(",")).stream().map(k ->Integer.valueOf(k.trim())).collect(Collectors.toList());
			trgs.add(new Triangle(lgs.get(0),lgs.get(1),lgs.get(2)));
		}
		List<Triangle> trgsV= new ArrayList<>();
		for(int i =0;i<trgs.size();i=i+3) {
			trgsV.add(new Triangle(trgs.get(i).l1,trgs.get(i+1).l1,trgs.get(i+2).l1));
			trgsV.add(new Triangle(trgs.get(i).l2,trgs.get(i+1).l2,trgs.get(i+2).l2));
			trgsV.add(new Triangle(trgs.get(i).l3,trgs.get(i+1).l3,trgs.get(i+2).l3));
		}
		int res=0;
		for(Triangle t:trgsV) {
			if(t.isValid()) {
				res++;
			}
		}
		return res;
	}
	private int s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim())
				.collect(Collectors.toList());
		List<Triangle> trgs= new ArrayList<>();
		for(String s:input) {
			List<Integer> lgs = Arrays.asList(s.trim().split(",")).stream().map(k ->Integer.valueOf(k.trim())).collect(Collectors.toList());
			trgs.add(new Triangle(lgs.get(0),lgs.get(1),lgs.get(2)));
		}
		
		int res=0;
		for(Triangle t:trgs) {
			if(t.isValid()) {
				res++;
			}
		}
		return res;
	}
	private class Triangle{
		int l1;
		int l2;
		int l3;
		public int getL1() {
			return l1;
		}
		public void setL1(int l1) {
			this.l1 = l1;
		}
		public int getL2() {
			return l2;
		}
		public void setL2(int l2) {
			this.l2 = l2;
		}
		public int getL3() {
			return l3;
		}
		public void setL3(int l3) {
			this.l3 = l3;
		}
		public Triangle(int l1, int l2, int l3) {
			super();
			this.l1 = l1;
			this.l2 = l2;
			this.l3 = l3;
		}
		public boolean isValid() {
			List<Integer> lg=Arrays.asList(l1,l2,l3);
			
			
			int max = outils.MesOutils.getMaxIntegerFromList(lg);
			return 2*max<lg.stream().mapToLong(e -> e).sum();
		}
	}
	
	public static List<Long> getDuration() {
		A2016Day3 d = new A2016Day3(3);
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

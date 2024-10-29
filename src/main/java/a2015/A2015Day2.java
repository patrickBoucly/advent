package a2015;

import java.util.Arrays;
import java.util.List;

public class A2015Day2 extends A2015 {
	public A2015Day2(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2015Day2 d = new A2015Day2(2);
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

	public long s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		int res=0;
		for(String s:inputL) { 
			int lo=Integer.parseInt(s.substring(0,s.indexOf("x")));
			int la=Integer.parseInt(s.substring(s.indexOf("x")+1,s.lastIndexOf("x")));
			int ha=Integer.parseInt(s.substring(s.lastIndexOf("x")+1));
			res+=2*lo*la+2*lo*ha+2*la*ha+reste(lo,la,ha);
		}
		return res;
	
	}
	

	private int reste(int lo, int la, int ha) {
		if(lo<=la && ha<=la) {
			return lo*ha;
		}
		if(lo<=ha && la<=ha) {
			return lo*la;
		}
		if(la<=lo && ha<=lo) {
			return la*ha;
		}
		return 0;
	}

	public long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		int res=0;
		for(String s:inputL) { 
			int lo=Integer.parseInt(s.substring(0,s.indexOf("x")));
			int la=Integer.parseInt(s.substring(s.indexOf("x")+1,s.lastIndexOf("x")));
			int ha=Integer.parseInt(s.substring(s.lastIndexOf("x")+1));
			res+=lo*la*ha+reste2(lo,la,ha);
		}
		return res;

	}
	

	private int reste2(int lo, int la, int ha) {
		if(lo<=la && ha<=la) {
			return 2*lo+2*ha;
		}
		if(lo<=ha && la<=ha) {
			return 2*lo+2*la;
		}
		if(la<=lo && ha<=lo) {
			return 2*la+2*ha;
		}
		return 0;
	}

	public static List<Long> getDuration() {
		A2015Day2 d = new A2015Day2(2);
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

package a2017;

import java.util.Arrays;
import java.util.List;


public class A2017Day1 extends A2017 {

	public A2017Day1(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day1 d = new A2017Day1(1);
		long startTime = System.currentTimeMillis();
		//System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1=endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day "+ d.day+" run 1 took "+timeS1+" milliseconds, run 2 took " + (endTime - startTime) + " milliseconds");
		
	}

	public long s1(boolean b) {
		String input=getInput(b).trim();
		long res=0L;
		for(int pos=0;pos<input.length()-1;pos=pos+1) {
			if(input.substring(pos, pos+1).equals(input.substring(pos+1, pos+2))) {
				res+=Long.parseLong(input.substring(pos, pos+1));
			}
		}
		if(input.substring(0, 1).equals(input.substring(input.length()-1, input.length()))) {
			res+=Long.parseLong(input.substring(0, 1));
		}
		return res;
	}
	public long s2(boolean b) {
		String input=getInput(b).trim();
		long res=0L;
		int pas=input.length()/2;
		for(int pos=0;pos<input.length();pos=pos+1) {
			int np=getNp(pos,pas,input.length());
			if(input.substring(pos, pos+1).equals(input.substring(np,np+1))) {
				res+=Long.parseLong(input.substring(pos, pos+1));
			}
		}
		return res;
	}



	private int getNp(int pos, int pas, int size) {
		return (pos+pas) % size;
	}

	public static List<Long> getDuration() {
		A2017Day1 d = new A2017Day1(1);
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

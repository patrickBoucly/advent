package aocmaven.a2021;

import java.util.Arrays;
import java.util.List;

public class A2021Day18 extends A2021 {

	public A2021Day18(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day18 d = new A2021Day18(18);
		long startTime = System.currentTimeMillis();
	//	System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		 System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	private int s1(boolean b) {
		String s1="[[[[[9,8],1],2],3],4]";
		String s2=explose(s1,4);
		System.out.println(s2);
		return 0;
	}

	private String explose(String s1, int i) {
		String res="";
		int posfin=-1;
		int posvirg=-1;
		boolean trouve=false;
		for(int pos=i;pos<s1.length();pos++) {
			if(!trouve && s1.substring(pos, pos+1).equals(",")) {
				posvirg=pos;
			}
			if(s1.substring(pos, pos+1).equals("]") && !trouve) {
				posfin=pos;
				trouve=true;
			}
		}
		if(s1.substring(i-1, i).equals(",")) {
			int posbefore=-2;
			boolean posbeforeT=false;
			for(int j=i-2;j>0;j--) {
				if(!posbeforeT && s1.substring(j,j+1).equals("[")) {
					posbeforeT=true;
					posbefore=j;
				}
			}
			String regular=s1.substring(posbefore, i-1);
			String nbGauche=s1.substring(i,posvirg);
			String newRegular=String.valueOf(Integer.parseInt(regular)+Integer.parseInt(nbGauche));
			res=s1.substring(0, posbefore)+newRegular+",";
		} else {
			res=s1.substring(0,i)+"0,";
		} 
		if(s1.substring(posfin+1, posfin+2).equals(",")) {
			
		}
		return null;
	}

	private int s2(boolean b) {
		return 0;
	}

	public static List<Long> getDuration() {
		A2021Day18 d = new A2021Day18(18);
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

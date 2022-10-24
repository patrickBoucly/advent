package a2016;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class A2016Day16 extends A2016 {
	String input = "10001001100000001";

	public A2016Day16(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day16 d = new A2016Day16(16);
		long startTime = System.currentTimeMillis();
		///System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}


	public static String getNext(String next, String l, String lb) {
		String res = next + "0";

		for (int i = 0; i < next.length(); i++) {
			if (next.substring(i, i + 1).equals(l)) {
				res += lb;
			} else if (next.substring(i, i + 1).equals("0")) {
				res += "0";
			} else {
				res += l;
			}
		}
		return res;

	}

	public String s1(boolean b) {
		int size=272;
		String dragonCurve=input;
		if (!b) {
			size=20;
			dragonCurve="10000";
		}
		int tour=0;
		while(dragonCurve.length()<size) {
			dragonCurve=aDragonCurved(dragonCurve);
			tour++;
			System.out.println(tour +" "+dragonCurve.length());
		}
		return checksum(dragonCurve.substring(0,size));
	}

	private String checksum(String dragonCurved) {
		System.out.println("checksum "+dragonCurved.length());
		if (dragonCurved.length() % 2 != 0) {
			return dragonCurved;
		} else {
			dragonCurved = aChecksum(dragonCurved);
			return checksum(dragonCurved);
		}
	}

	private String aChecksum(String dragonCurved) {
		String[] split=dragonCurved.split("(?<=\\G.{2})");
		Map<String,String> mapObj =new HashMap<>(); 
		mapObj.put("00","1");
		mapObj.put("01","0");
		mapObj.put("10","0");
		mapObj.put("11","1");
		String res="";
		for (int i = 0; i < split.length;i++) {
			res+=mapObj.get(split[i]);
		}
		return res;
	}

	

	private String aDragonCurved(String a) {
		String b = a;
		String c = StringUtils.reverse(b);
		b = c.replace("0", "x");
		b = b.replace("1", "0");
		b = b.replace("x", "1");
		return a+"0"+b;
	}

	public String s2(boolean b) {
		int size=35651584;
		String dragonCurve=input;
		if (!b) {
			size=20;
			dragonCurve="10000";
		}
		int tour=0;
		while(dragonCurve.length()<size) {
			dragonCurve=aDragonCurved(dragonCurve);
			tour++;
			System.out.println(tour +" "+dragonCurve.length());
		}
		return checksum(dragonCurve.substring(0,size));
	}

	public static List<Long> getDuration() {
		A2016Day16 d = new A2016Day16(16);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		// d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1, 3755797L);
	}

}

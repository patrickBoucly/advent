package a2015;

import java.util.Arrays;
import java.util.List;

public class A2015Day1 extends A2015 {
	public A2015Day1(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2015Day1 d = new A2015Day1(1);
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
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().toList();
		return inputL.get(0).chars()
                .mapToObj(c -> String.valueOf((char) c)).filter(x->x.equals("(")).count()-inputL.get(0).chars()
                .mapToObj(c -> String.valueOf((char) c)).filter(x->x.equals(")")).count();
	
	}
	

	public long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().toList();
		int pos=0;
		int cible=-1;
		List<String> caracteres=inputL.get(0).chars()
                .mapToObj(c -> String.valueOf((char) c)).toList();
		for(int i=0;i<caracteres.size();i++) {
			if(caracteres.get(i).equals("(")){
				pos++;
			}else {
				pos--;
			}
			if(pos==cible) {
				return i+1;
			}
		}
		return day;

	}
	

	public static List<Long> getDuration() {
		A2015Day1 d = new A2015Day1(1);
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

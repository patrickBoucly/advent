package a2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A2022Day01 extends A2022 {

	public A2022Day01(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day01 d = new A2022Day01(1);
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

	public int s1(boolean b) {
		List<String> inputL=Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		int max=0;
		int cur=0;
		for(int i=0;i<inputL.size();i++) {
			if(inputL.get(i).equals("\r")) {
				if(cur>max) {
					max=cur;
				}
				cur=0;
			} else {
				cur+=Integer.valueOf((inputL.get(i).trim()));
			}
		}
		
		return max;
	}

	
	
	public int s2(boolean b) {
		List<String> inputL=Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		int max=0;
		int cur=0;
		List<Integer> totParElf=new ArrayList<>();
		for(int i=0;i<inputL.size();i++) {
			if(inputL.get(i).equals("\r")) {
				totParElf.add(cur);
				cur=0;
			} else {
				cur+=Integer.valueOf((inputL.get(i).trim()));
			}
		}
		List<Integer> sorted = totParElf.stream()
                .sorted()
                .collect(Collectors.toList());
		return sorted.get(sorted.size()-1)+sorted.get(sorted.size()-2)+sorted.get(sorted.size()-3);
		
	}
	public static List<Long> getDuration() {
		A2022Day01 d = new A2022Day01(1);
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

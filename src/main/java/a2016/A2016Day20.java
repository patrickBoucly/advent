package a2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class A2016Day20 extends A2016 {

	public A2016Day20(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day20 d = new A2016Day20(20);
		long startTime = System.currentTimeMillis();
		//System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		 System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	private long s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<IPRule> ipr=new ArrayList<>();
		for(String s:input) {
			ipr.add(new IPRule(Long.parseLong(s.split("-")[0]),Long.parseLong(s.split("-")[1])));
		}
		Long max=4294967295L;
		Set<Long> bornes = new HashSet<Long>();
		int cpt=0;
		for(IPRule r:ipr) {
			bornes.add(r.min);
			bornes.add(r.max);
		}
		List<Long> bornesL=new ArrayList<Long>(bornes);
		Collections.sort(bornesL);
		System.out.println(bornesL);
		Long allowed=0L;
		for(int k=0;k<bornesL.size()-1;k++) {
			System.out.println(bornesL.get(k));
			if(bornesL.get(k)!=bornesL.get(k+1)+1) {
				if(respectAllRules(bornesL.get(k)+1,ipr)) {
					Long alloewedSize=bornesL.get(k+1)-bornesL.get(k)-1;
					System.out.println("allowed!"+alloewedSize);
					allowed+=alloewedSize;
				}
			}
		}
		return allowed;
		
	}

	public long s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<IPRule> ipr=new ArrayList<>();
		for(String s:input) {
			ipr.add(new IPRule(Long.parseLong(s.split("-")[0]),Long.parseLong(s.split("-")[1])));
		}
		Long max=4294967295L;
		for(long i=0;i<=max;i++) {
			if(respectAllRules(i,ipr)) {
				return i;
			}
		}
		return 0L;
	}
	
	private boolean respectAllRules(long i, List<IPRule> ipr) {
		for(IPRule r:ipr) {
			if(r.respectRule(i)) {
				return false;
			}
		}
		return true;
	}

	private class IPRule{
		long min;
		long max;
		public long getMin() {
			return min;
		}
		public void setMin(long min) {
			this.min = min;
		}
		public long getMax() {
			return max;
		}
		public void setMax(long max) {
			this.max = max;
		}
		public IPRule(long min, long max) {
			super();
			this.min = min;
			this.max = max;
		}
		public boolean respectRule(long i) {
			return i>=min && i<=max;
		}
		@Override
		public String toString() {
			return "IPRule [min=" + min + ", max=" + max + "]";
		}
		
	}
	

	public static List<Long> getDuration() {
		A2016Day20 d = new A2016Day20(20);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(3755797L,10028548L);
	}

}

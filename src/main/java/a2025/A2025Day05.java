package a2025;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class A2025Day05 extends A2025 {
	public A2025Day05(int day) {
		super(day);
	}
	public static void main(String[] args0) {
		A2025Day05 d = new A2025Day05(5);
		//System.out.println(d.s1(true));
		//System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	public Long s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Game g = new Game();
		g.setValues(inputL);
		return g.solve1();
	}

	public Long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Game g = new Game();
		g.setValues(inputL);
		return g.solve2();
	}
	@Data
	@NoArgsConstructor
	public static class Game {
		List<Range> range;
		List<Long> produit;
		public Long solve1() {
			return produit.stream().filter(l->range.stream().anyMatch(r->r.between(l))).count();
		}
		public void setValues(List<String> inputL) {
			List<Range> range = new ArrayList<>();
			List<Long> produit = new ArrayList<>();
			boolean p=false;
			for(int i=0;i<inputL.size();i++) {
				if(inputL.get(i).isEmpty()) {
					p=true;
				}
				else if(p) {
					produit.add(Long.parseLong(inputL.get(i)));
				}else {
					String[] s=inputL.get(i).split("-");
					range.add(new Range(Long.parseLong(s[0]),Long.parseLong(s[1])));
				}
			}
			this.range=range;
			this.produit=produit;
		}
		public Long solve2() {
			
			Set<Long> border=new HashSet<>();
			
			for(Range r:range) {
				border.add(r.max);
				border.add(r.min);
			}	
			Long freshCpt=0L+border.size();
			List<Long> orderList= new ArrayList<>(new TreeSet<>(border));

			for(int i=0;i<orderList.size()-1;i++) {
				if(orderList.get(i+1)-orderList.get(i)>1) {
					if(iIsOk(orderList.get(i)+1)) {
						
						freshCpt+=orderList.get(i+1)-orderList.get(i)-1;
					}
				}
				
			}
			return freshCpt;	
		}
		private boolean iIsOk(long l) {
			return range.stream().anyMatch(r->r.between(l));
		}
	}
	
	@Data
	@AllArgsConstructor
	public static class Range {
		Long min;
		Long max;
		public Boolean between(Long l) {
			return min<=l && l<=max;
		}
	}

	public static List<Long> getDuration() {
		A2025Day05 d = new A2025Day05(5);
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
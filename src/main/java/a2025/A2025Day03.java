package a2025;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import lombok.Data;

public class A2025Day03 extends A2025 {

	public A2025Day03(int day) {

		super(day);

	}

	public static void main(String[] args0) {

		A2025Day03 d = new A2025Day03(3);
		//System.out.println(d.s1(false));
		//System.out.println(d.s1(true));

		long startTime = System.currentTimeMillis();

		//d.s1(true);

		long endTime = System.currentTimeMillis();

		long timeS1 = endTime - startTime;

		startTime = System.currentTimeMillis();

		System.out.println(d.s2(true));

		endTime = System.currentTimeMillis();

	//	System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "

		//		+ (endTime - startTime) + " milliseconds");

	}

	public Integer s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Game g = new Game(inputL);
		return g.solve1();
	}

	public Long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Game g = new Game(inputL);
		return g.solve2();
	}
	@Data
	@AllArgsConstructor
	public static class Game {
		List<String> inputL;


		public Integer solve1() {
			int r = 0;
			for (String s : inputL) {
				r += getJoltage(s);
			}
			return r;
		}
		public Long solve2() {
			Long r = 0L;
			for (String s : inputL) {
				r += getJoltage2(s);
			}
			return r;
		}

		private Integer getJoltage(String s) {
			Pair<String,Integer> first=getFirst(s);
			String	t=s.substring(first.getRight()+1);
			
			String last = getLast(t);
			return Integer.parseInt(first.getKey()+last);
		}
		private Long getJoltage2(String s) {

			String res="";
			for(int i=1;i<=12;i++) {
				Pair<String,Integer> next=getFirst2(s,12-i);
				res=res+next.getLeft();
				s=s.substring(next.getRight()+1);
			}
			
			return Long.parseLong(res);
		}




		private String getLast(String t) {
			for(int k=9;k>0;k--) {
				if(t.contains(String.valueOf(k))) {
					return String.valueOf(k);
				}
			}
			return "-1";
		}

		private Pair<String, Integer> getFirst2(String s,int v) {
			String t=s.substring(0,s.length()-v);
			for(int k=9;k>0;k--) {
				if(t.contains(String.valueOf(k))) {
					return Pair.of(String.valueOf(k), t.indexOf(String.valueOf(k)));
				}
			}
			return Pair.of("-1",-1);
		}


		private Pair<String, Integer> getFirst(String s) {
			String t=s.substring(0,s.length()-1);
			for(int k=9;k>0;k--) {
				if(t.contains(String.valueOf(k))) {
					return Pair.of(String.valueOf(k), t.indexOf(String.valueOf(k)));
				}
			}
			return Pair.of("-1",-1);
		}

	}

	public static List<Long> getDuration() {
		A2025Day03 d = new A2025Day03(3);
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
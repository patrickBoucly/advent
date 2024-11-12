package a2015;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Data;

public class A2015Day6 extends A2015 {
	public A2015Day6(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2015Day6 d = new A2015Day6(6);
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

	public static List<String> voyelles = List.of("a", "e", "i", "o", "u");
	public static List<String> interdites = List.of("ab", "cd", "pq", "xy");

	public long s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().toList();
		List<Inst> insts=getInst(inputL);
		return 0L;
	}

	private List<Inst> getInst(List<String> inputL) {
		List<Inst> ins=new ArrayList<>();
		for(String i:inputL) {
			ins.add(getInstFromLine(i));
		}
		return ins;
	}

	

	private Inst getInstFromLine(String i) {
		Action a=null;
		if(i.contains("on")) {
			a=Action.of("on");
		}
		if(i.contains("off")) {
			a=Action.of("off");
		}
		if(i.contains("toggle")) {
			a=Action.of("toggle");
		}
		return null;
	}
	
	

	public long s2(boolean b) {
		return 0L;
	}

	@Data
	@AllArgsConstructor
	private static class Inst{
		public static Inst of(Action a, Ampoule debut,Ampoule fin) {
			return new Inst(a,debut,fin);
		}
		Action a;
		Ampoule debut;
		Ampoule fin;
		
	}
	@Data
	@AllArgsConstructor
	private static class Ampoule{
		public static Ampoule of(int x,int y,boolean allumee) {
			return new Ampoule(x,y,allumee);
		}
		int x;int y;boolean allumee;
		
	}
	@Data
	@AllArgsConstructor
	private static class Action{
		public static Action of(String verbe) {
			return new Action(verbe);
		}
		String verbe;
	}

	public static List<Long> getDuration() {
		A2015Day6 d = new A2015Day6(6);
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

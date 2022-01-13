package a2017;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class A2017Day10 extends A2017 {

	public A2017Day10(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day10 d = new A2017Day10(1);
		// d.s1(true);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1=endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		System.out.println("Day "+ d.day+" run 1 took "+timeS1+" milliseconds, run 2 took " + (endTime - startTime) + " milliseconds");
		
	}

	public int s1(boolean b) {
		List<Integer> splitPos = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).map(Integer::parseInt).collect(Collectors.toList());
		List<Integer> ln = IntStream.range(0, 255).boxed().collect(Collectors.toList());
		int skipSize=0;
		int curPos=0;
		G
		for(Integer i:splitPos) {
			g.transf(i);
		}
		return 0;
	}
	public int s2(boolean b) {
		return 0;
	}

public static class Game{
	List<Integer> ln;
	int skipSize;
	int curPos;
	public List<Integer> getLn() {
		return ln;
	}
	public void setLn(List<Integer> ln) {
		this.ln = ln;
	}
	public int getSkipSize() {
		return skipSize;
	}
	public void setSkipSize(int skipSize) {
		this.skipSize = skipSize;
	}
	public int getCurPos() {
		return curPos;
	}
	public void setCurPos(int curPos) {
		this.curPos = curPos;
	}
	public Game(List<Integer> ln, int skipSize, int curPos) {
		super();
		this.ln = ln;
		this.skipSize = skipSize;
		this.curPos = curPos;
	}
	
}

	public static List<Long> getDuration() {
		A2017Day10 d = new A2017Day10(1);
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

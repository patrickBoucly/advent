package a2015;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

public class A2015Day7 extends A2015 {
	public A2015Day7(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2015Day7 d = new A2015Day7(7);
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
		int res=0;
		List<String> letters = getInput(b).chars().mapToObj(c -> String.valueOf((char) c)).toList(); 
		Set<Pos> visited=new HashSet<>();
		visited.add(Pos.of(0,0));
		Pos curPos=Pos.of(0,0);
		for(String l:letters) {
			curPos = getNextPos(curPos, l);
			visited.add(curPos);
		}
		return visited.size();
	
	}
	@Data
	@AllArgsConstructor
	private static class Pos{
		public static Pos of(int i, int j) {
			return new Pos(i,j);
		}
		int x;
		int y;
		
	}


	public long s2(boolean b) {
		int res=0;
		List<String> letters = getInput(b).chars().mapToObj(c -> String.valueOf((char) c)).toList(); 
		Set<Pos> visited=new HashSet<>();
		visited.add(Pos.of(0,0));
		Pos curPos=Pos.of(0,0);
		Pos curPosRobot=Pos.of(0,0);

		for(int i=0;i<letters.size();i++) {
			if(i%2==0) {
			curPos = getNextPos(curPos,letters.get(i));
			visited.add(curPos);
			}else {
				curPosRobot = getNextPos(curPosRobot,letters.get(i));
				visited.add(curPosRobot);
			}
			
		}
		return visited.size();
	
	}

	private Pos getNextPos(Pos curPos, String l) {
		if(l.equals("^")) {
			curPos=Pos.of(curPos.x, curPos.y+1);
		}
		if(l.equals("v")) {
			curPos=Pos.of(curPos.x, curPos.y-1);
		}
		if(l.equals("<")) {
			curPos=Pos.of(curPos.x-1, curPos.y);
		}
		if(l.equals(">")) {
			curPos=Pos.of(curPos.x+1, curPos.y);
		}
		return curPos;
	}
	
	

	private int reste2(int lo, int la, int ha) {
		if(lo<=la && ha<=la) {
			return 2*lo+2*ha;
		}
		if(lo<=ha && la<=ha) {
			return 2*lo+2*la;
		}
		if(la<=lo && ha<=lo) {
			return 2*la+2*ha;
		}
		return 0;
	}

	public static List<Long> getDuration() {
		A2015Day7 d = new A2015Day7(7);
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

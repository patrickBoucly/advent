package a2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import a2024.A2024Day16.Game;
import a2024.A2024Day16.Maps;
import a2024.A2024Day16.Position;
import a2024.A2024Day16.State;

public class A2024Day18 extends A2024 {

	public A2024Day18(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day18 d = new A2024Day18(18);
		//System.out.println(d.s1(true));
		// too high 104512
		long startTime = System.currentTimeMillis();
		// d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	public long s1(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		int nbByte = 1024;
		if (!b) {
			nbByte = 12;
		}
		Game g = getGame(input, b,nbByte);
		System.out.println(g);
		return g.calculerRes181();
	}

	private Game getGame(List<String> input, boolean b, int l) {
		Set<Position> mapPos = new HashSet<>();
		Set<State> states = new HashSet<>();
		State initialState = null;
		int max = 70;
		int nbByte = l;
		if (!b) {
			max = 6;
		}
		for (int i = 0; i <= max; i++) {
			for (int j = 0; j <= max; j++) {
				Position p = null;
				if (i == 0 && j == 0) {
					p = new Position(i, j, "E");
					List<Position> it = new ArrayList<>();
					it.add(p);
					initialState = new State(p, null, 0L, it);
					states.add(initialState);
				} else if (i +j == 2* max) {
					p = new Position(i, j, "S");
				} else {
					p = new Position(i, j, ".");
				}
				mapPos.add(p);		
			}
			
		}
		Maps map=new Maps(mapPos);
		for(int k=0;k<nbByte;k++) {
			map.getPosition(Integer.parseInt(input.get(k).substring(0,input.get(k).indexOf(","))) ,
					Integer.parseInt(input.get(k).substring(input.get(k).indexOf(",")+1))).get().setContenu("#");
		}
		return new Game(map, states, initialState, 0L);
	}

	/*
	 * private Game getGame(List<String> input, boolean b) { int max=70; int
	 * nbByte=1024; if(!b) { max=6; nbByte=12; } Set<Position> pos =new HashSet<>();
	 * for(int i=0;i<=max;i++) { for(int j=0;j<=max;j++) { pos.add(new Position(i,
	 * j, false)); } } for(int i=0;i<nbByte;i++) {
	 * getPos(pos,Integer.parseInt(input.get(i).substring(0,input.get(i).indexOf(","
	 * ))) ,Integer.parseInt(input.get(i).substring(input.get(i).indexOf(",")+1))).
	 * setByte(true);; } return new Game(pos,max,0,0,max,max,0,0); }
	 */
	public String s2(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		boolean cont=true;
		int nbByte = 3030;
		if (!b) {
			nbByte = 12;
		}
		while(cont) {
			Game g=getGame(input, b, nbByte);
			//System.out.println(g);
			Long res=g.calculerRes181();
			if(res>Long.MAX_VALUE-1) {
				System.out.println(nbByte);
				nbByte-=1;
			}else {
				cont=false;
			}
		}
		return input.get(nbByte);
	}

	public static List<Long> getDuration() {
		A2024Day18 d = new A2024Day18(16);
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

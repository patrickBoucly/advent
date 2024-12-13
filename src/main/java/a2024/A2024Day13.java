package a2024;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import outils.MesOutils;

public class A2024Day13 extends A2024 {

	public A2024Day13(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day13 d = new A2024Day13(13);
		//System.out.println(d.s1(true));
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
		Game g=getGame(input);
		return g.getTotalToken();
	}

	

	private Game getGame(List<String> input) {
		Set<Machine> machines=new HashSet<>();
		for(int i=0;i<input.size();i=i+4) {
			Long decAx=Long.parseLong(input.get(i).substring(12, 14));
			Long decAy=Long.parseLong(input.get(i).substring(18));
			Long decBx=Long.parseLong(input.get(i+1).substring(12, 14));
			Long decBy=Long.parseLong(input.get(i+1).substring(18));
			int posEg1=input.get(i+2).indexOf("=");
			int posEg2=input.get(i+2).lastIndexOf("=");
			int posVir=input.get(i+2).indexOf(",");
			Long priceX=Long.parseLong(input.get(i+2).substring(posEg1+1, posVir));
			Long priceY=Long.parseLong(input.get(i+2).substring(posEg2+1));
			machines.add(new Machine(decAx,decAy,decBx,decBy,priceX,priceY,0L));
		}
		return new Game(machines);
	}

	public Long s2(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		Game g=getGame(input);
		for(Machine m:g.machines) {
			m.setPriceX(m.getPriceX()+10000000000000L);
			m.setPriceY(m.getPriceY()+10000000000000L);
		}
		return g.getTotalToken();
	}

	@Data
	@AllArgsConstructor
	private static class Game {
		public Long getTotalToken() {
			Long res=0L;
			for(Machine m:machines) {
				m.countToken();
				System.out.println(m.neededToken);
			}
			return machines.stream().map(Machine::getNeededToken).reduce(0L, Long::sum);
		}

		Set<Machine> machines;
	}
	@Data
	@AllArgsConstructor
	private static class Machine {
		public void countToken() {
			List<Long> systemSol=getSystemSol();
			if(systemSol !=null) {
				neededToken=systemSol.get(0)*3+systemSol.get(1);
			}
		}
		private List<Long> getSystemSol() {
			List<Long> res=MesOutils.solveLinearEquations(Arrays.asList(
					Arrays.asList(decAx,decBx),Arrays.asList(decAy,decBy)),Arrays.asList(priceX,priceY));
			if(res.get(0)<0 || res.get(1)<0) {
				return null;
			}
			Long ctrlX=priceX-decAx*res.get(0)-decBx*res.get(1);
			Long ctrlY=priceY-decAy*res.get(0)-decBy*res.get(1);
			if(ctrlX.equals(0L) && ctrlY.equals(0L) ) {
				return res;
			}
			return null;
		}
		Long decAx;
		Long decAy;
		Long decBx;
		Long decBy;
		Long priceX;
		Long priceY;
		Long neededToken;
	}

	public static List<Long> getDuration() {
		A2024Day13 d = new A2024Day13(13);
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

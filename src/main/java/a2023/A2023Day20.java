package a2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class A2023Day20 extends A2023 {

	public A2023Day20(int day) {
		super(day);
	}

	public static int id = 0;

	public static void main(String[] args0) {
		A2023Day20 d = new A2023Day20(20);
		System.out.println(d.s1(false));
		long startTime = System.currentTimeMillis();
		// d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		// System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public int s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		TheGame tg = getTheGame(inputL);
		System.out.println(tg);
		return 0;
	}

	private TheGame getTheGame(List<String> inputL) {
		TheGame tg = new TheGame();
		Map<String,Module> modules= new HashMap<>();
		
		Pulse curPulse = new Pulse(id++, "low", true);
		for (String s : inputL) {
			if(s.substring(0, 1).equals("b")) {
				String[] sp=s.split(">")[1].trim().split(",");
				List<String> dest=new ArrayList<>();
				for(String f:sp) {
					dest.add(f.trim());
				}
				Module m=new Module();
				m.setDestinations(dest);
				m.setType("broadcaster");
				modules.put("broadcaster",m);
			}else if(s.substring(0, 1).equals("%")) {
				String name=s.substring(1, s.indexOf(" "));
				String[] sp=s.split(">")[1].trim().split(",");
				List<String> dest=new ArrayList<>();
				for(String f:sp) {
					dest.add(f.trim());
				}
				Module m=new Module();
				m.setDestinations(dest);
				m.setType("flip");
				modules.put(name,m);
			}else {
				String name=s.substring(1, s.indexOf(" "));
				String[] sp=s.split(">")[1].trim().split(",");
				List<String> dest=new ArrayList<>();
				for(String f:sp) {
					dest.add(f.trim());
				}
				Module m=new Module();
				m.setDestinations(dest);
				m.setType("conjunction");
				modules.put(name,m);
			}
		}
		tg.setModules(modules);
		tg.setCurPulse(curPulse);
		return tg;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class TheGame {
		Map<String,Module> modules;
		Pulse curPulse;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Pulse {
		int id;
		String level;
		boolean actif;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Module {
		String type;
		boolean state;
		Pulse lastReceved;
		List<String> destinations;
	}

	public int s2(boolean b) {
		return 0;

	}

	public static List<Long> getDuration() {
		A2023Day20 d = new A2023Day20(1);
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

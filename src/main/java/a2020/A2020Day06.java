package a2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class A2020Day06 extends A2020 {

	public A2020Day06(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2020Day06 d = new A2020Day06(6);
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

	public int s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		List<Orbit> orbites = new ArrayList<>();
		Set<String> astres = new HashSet<>();
		for (String l : inputL) {
			String[] splitted = l.split("\\)");
			orbites.add(new Orbit(splitted[0].trim().replace("\r",""), splitted[1].trim().replace("\r","")));
			astres.add(splitted[0].trim().replace("\r",""));
			astres.add(splitted[1].trim().replace("\r",""));
		}
		List<String> you=new ArrayList<>();
		you.add("YOU");
		List<String> san=new ArrayList<>();
		san.add("SAN");
		while(!you.get(you.size()-1).equals("COM")) {
			String last=you.get(you.size()-1);
			you.add(orbites.stream().filter(o->o.satellite.equals(last)).map(Orbit::getCentre).findFirst().get());
		}
		while(!san.get(san.size()-1).equals("COM")) {
			String last=san.get(san.size()-1);
			san.add(orbites.stream().filter(o->o.satellite.equals(last)).map(Orbit::getCentre).findFirst().get());
		}
		System.out.println(you);
		System.out.println(san);
		int min=100000;
		for(String s:you) {
			if(san.contains(s)) {
				int dist=you.indexOf(s)+san.indexOf(s);
				min=Math.min(dist, min);
			}
		}
		return min-2;
	}

	public int s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		List<Orbit> orbites = new ArrayList<>();
		Set<String> astres = new HashSet<>();
		for (String l : inputL) {
			String[] splitted = l.split("\\)");
			orbites.add(new Orbit(splitted[0].trim().replace("\r",""), splitted[1].trim().replace("\r","")));
			astres.add(splitted[0].trim().replace("\r",""));
			astres.add(splitted[1].trim().replace("\r",""));
		}
		int res = compterLiens(astres, orbites);

		return res;
	}

	private int compterLiens(Set<String> astres, List<Orbit> orbites) {
		int res = 0;
		for (String astre : astres) {
			res += compterLiensAstre(astre, orbites);
		}
		return res;
	}

	private int compterLiensAstre(String astre, List<Orbit> orbites) {
		int res = 0;
		boolean continuer = true;
		while (continuer) {
			String centre="";
			for(Orbit o:orbites) {
				//System.out.println(o.getSatellite());
				if(o.getSatellite().equals(astre)) {
					centre=o.centre;
				}
			}
			if (centre.isBlank()) {
				return res;
			}
			res++;
			astre=centre;
		}
		return res;
	}

	@ToString
	@AllArgsConstructor
	@Setter
	@Getter
	public class Orbit {
		String centre;
		String satellite;
	}

	public static List<Long> getDuration() {
		A2020Day06 d = new A2020Day06(6);
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

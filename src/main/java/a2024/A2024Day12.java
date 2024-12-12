package a2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

public class A2024Day12 extends A2024 {

	public A2024Day12(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day12 d = new A2024Day12(12);
		System.out.println(d.s1(true));
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
		Jardin j = getJardin(input);
		return j.getTotalCost();
	}

	private Jardin getJardin(List<String> input) {
		Map<String, Parcelle> parcelles = new HashMap<>();
		Map<String, Parcelle> parcellesSep = new HashMap<>();
		int j = 0;
		for (String s : input) {
			for (int i = 0; i < s.length(); i++) {
				String a = s.substring(i, i + 1);
				Plant p = new Plant(a, i, j);
				if (parcelles.containsKey(a)) {
					parcelles.get(a).plants.add(p);
				} else {
					Set<Plant> plants = new HashSet<>();
					Parcelle par = new Parcelle(plants);
					plants.add(p);
					parcelles.put(a, par);
				}
			}
			j++;
		}
		for (Entry<String, Parcelle> s : parcelles.entrySet()) {
			List<Plant> plants = new ArrayList<>(s.getValue().plants);
			int i = 0;
			int added = 0;
			List<Plant> addedPlant=new ArrayList<A2024Day12.Plant>();
			while (s.getValue().plants.size() > added) {
				i++;
				Set<Plant> pi = new HashSet<>();
				for(Plant p:plants) {
					if(!addedPlant.contains(p)) {
						pi.add(p);
						break;
					}
				}
				
				boolean add = true;
				while (add) {
					for (Plant p : plants) {
						if (estContigu(p, pi)) {
							add = pi.add(p);
							if (add) {
								break;
							}
						}
					}
					if(pi.size()==1) {
						add=false;
					}
				}
				parcellesSep.put(s.getKey() + i, new Parcelle(pi));
				added += pi.size();
				addedPlant.addAll(pi);
			}

		}
		return new Jardin(parcellesSep);
	}

	private boolean estContigu(Plant p, Set<Plant> pi) {
		for(Plant q:pi) {
			if(manDist(q, p)==1) {
				return true;
			}
		}
		return false;
	}

	public Long s2(boolean b) {
		List<Long> input = null;

		return 0L;
	}

	@Data
	@AllArgsConstructor
	private static class Jardin {
		public long getTotalCost() {
			Long res = 0L;
			for (Entry<String, Parcelle> s : parcelles.entrySet()) {
				Long add = s.getValue().getCost();
				System.out.println(s.getKey() + " " + add);
				res += add;
			}
			return res;
		}

		Map<String, Parcelle> parcelles;
	}

	@Data
	@AllArgsConstructor
	private static class Parcelle {
		Set<Plant> plants;

		public Long getCost() {
			return getPerimeter() * plants.size();
		}

		private Long getPerimeter() {
			Long res = 0L;
			for (Plant p : plants) {
				res += 4 - getVoisinsNb(p, plants);
			}
			return res;
		}

		private Integer getVoisinsNb(Plant p, Set<Plant> pl) {
			Integer res = 0;
			for (Plant plant : pl) {
				if (manDist(plant, p) == 1) {
					res++;
				}
			}
			return res;
		}

		
	}
	public static int manDist(Plant p1, Plant p2) {
		return (Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y));
	}
	@Data
	@AllArgsConstructor
	private static class Plant {
		String type;
		int x;
		int y;
	}

	public static List<Long> getDuration() {
		A2024Day12 d = new A2024Day12(12);
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

package a2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import outils.MesOutils;

public class A2024Day5 extends A2024 {

	public A2024Day5(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day5 d = new A2024Day5(5);
		// System.out.println(d.s1(true));
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
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim()).toList();
		Game g = getGame(input);
		g.resolve1();
		return g.res1;
	}

	public Long s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim()).toList();
		Game g = getGame(input);
		g.resolve2();
		return g.res2;

	}

	private Game getGame(List<String> input) {
		int i = 0;
		List<Rule> rules = new ArrayList<>();
		List<Integer> listUpdate = new ArrayList<>();
		List<PageToProduce> pagesToProduce = new ArrayList<>();
		while (input.get(i).length() > 1) {
			String s = input.get(i);
			rules.add(new Rule(Integer.parseInt(s.substring(0, s.indexOf("|"))),
					Integer.parseInt(s.substring(s.indexOf("|") + 1))));
			i++;
		}
		i++;
		while (i < input.size()) {
			String[] sp = input.get(i).split(",");
			listUpdate = new ArrayList<>();
			for (int j = 0; j < sp.length; j++) {
				listUpdate.add(Integer.parseInt(sp[j]));
			}
			pagesToProduce.add(new PageToProduce(listUpdate, true));
			i++;
		}
		Game g = new Game(pagesToProduce, rules, 0L, 0L);
		return g;
	}

	@Data
	@AllArgsConstructor
	private static class Rule {
		Integer before;
		Integer after;
	}

	@Data
	@AllArgsConstructor
	private static class PageToProduce {
		List<Integer> updateList;
		boolean respected = true;
	}

	@Data
	@AllArgsConstructor
	private static class Game {
		public void resolve1() {
			for (PageToProduce ptp : pagesToProduce) {
				respectRule(ptp);
				ajouterRes1(ptp);
				// System.out.println(ptp);
			}
		}

		public void resolve2() {
			for (PageToProduce ptp : pagesToProduce) {
				respectRule(ptp);
			}
			ajouterRes2();

		}

		private void ajouterRes2() {
			PageToProduce orderedPtp = orderPtp();
			respectRule(orderedPtp);
			//System.out.println(orderedPtp.respected);
			for (PageToProduce ptp : pagesToProduce) {
				
					if (!ptp.respected) {

						PageToProduce ptpO = orderPtp(ptp);
						//System.out.println("ptp :" + ptp);
						//System.out.println("ptp size :" + ptp.updateList.size());
						//System.out.println("ptp0 " + ptpO);
						//System.out.println((ptpO.updateList.size() + 1) / 2 - 1);
						int add = ptpO.updateList.get((ptpO.updateList.size() + 1) / 2 - 1);
						//System.out.println("add :" + add);
						res2 += add;
					}
				
			}

		}

		private PageToProduce orderPtp(PageToProduce ptp) {
			System.out.println("avt ptp :"+ptp);
			int nbModif=0;
			Map<Integer, List<Integer>> befores = new HashMap<>();
			for (Rule r : rules) {
				if (befores.keySet().contains(r.before)) {
					befores.get(r.before).add(r.after);
				} else {
					List<Integer> ne=new ArrayList<>();
					ne.add(r.after);
					befores.put(r.before, ne);
				}
			}
			while(!ptp.respected) {
				respectRule(ptp);
				for(int i=0;i<ptp.updateList.size();i++) {
					int nbToConsider=ptp.updateList.get(i);
					List<Integer> avant=new ArrayList<>();
					avant.addAll(ptp.updateList.subList(0, i));
					
					if(befores.get(nbToConsider)!=null && !befores.get(nbToConsider).isEmpty()) {
						boolean decaler=false;
						int indexMin=10000;
						int indexNbToConsider=ptp.updateList.indexOf(nbToConsider);
						int nbToSwitch=1000000;
						for(Integer avt:avant) {
							if(befores.get(nbToConsider).contains(avt)) {
								decaler=true;
								int index=avant.indexOf(avt);
								if(index<indexMin) {
									indexMin=index;
									nbToSwitch=avant.get(index);
								}
							}
						}
						if(decaler) {
							List<Integer> l = new ArrayList<>();
							l.addAll(ptp.updateList.subList(0, indexMin));
							l.add(nbToConsider);
							l.addAll(ptp.updateList.subList(indexMin + 1,indexNbToConsider));
							l.add(nbToSwitch);
							l.addAll(ptp.updateList.subList(indexNbToConsider+ 1, ptp.updateList.size()));
							ptp.updateList =new ArrayList<>(l);
							respectRule(ptp);
							nbModif++;
							System.out.println("nbmodif "+nbModif+" ptp : "+ptp);
						}
						
					}
				}
			}
			return ptp;
		
		}

		private PageToProduce orderPtp() {
			List<Integer> globalOrder = findGlobalOrder(rules);
			return new PageToProduce(globalOrder, true);

		}

		private List<Integer> findGlobalOrder(List<Rule> rules) {
			List<Integer> notOrder = new ArrayList<>();
			List<Integer> order = new ArrayList<>();
			Set<Integer> allNumbers = new HashSet<>();
			for (Rule r : rules) {
				allNumbers.add(r.before);
				allNumbers.add(r.after);
			}
			notOrder.addAll(allNumbers);
			Map<Integer, List<Integer>> befores = new HashMap<>();
			for (Rule r : rules) {
				if (befores.keySet().contains(r.before)) {
					befores.get(r.before).add(r.after);
				} else {
					befores.put(r.before, new ArrayList<>(r.after));
				}
			}
			List<Integer> l = new ArrayList<>();
			for (List<Integer> m : befores.values()) {
				l.add(m.size());
			}
			Collections.sort(l);
			int jmax = MesOutils.getMaxIntegerFromList(l);
			for (int k = jmax; k >= 0; k--) {
				for (Entry<Integer, List<Integer>> entry : befores.entrySet()) {
					System.out.println(entry);
					if (entry.getValue().size() - k == 0) {
						order.add(entry.getKey());
					}
				}
			}

			PageToProduce t = new PageToProduce(order, true);
			respectRule(t);
			System.out.println(order + " " + t.respected);
			return order;
		}

		private void ajouterRes1(PageToProduce ptp) {
			if (ptp.respected) {
				res1 += ptp.updateList.get((ptp.updateList.size() + 1) / 2 - 1);

			}
		}

		private void respectRule(PageToProduce ptp) {
			boolean respect = true;
			for (int i = 0; i < ptp.updateList.size() - 1; i++) {
				Integer nbToConsider = ptp.updateList.get(i);
				List<Integer> nbToCompare = ptp.updateList.subList(i + 1, ptp.updateList.size());
				respect = respected(nbToConsider, nbToCompare);
				if (!respect) {
					ptp.respected = false;
					respect=false;
					break;
				}
			}
			if(respect) {
				ptp.respected=true;
			}

		}

		private Boolean respected(Integer nbToConsider, List<Integer> nbToCompare) {
			for (Rule r : rules) {
				if (r.after.equals(nbToConsider) && nbToCompare.contains(r.before)) {
					return false;
				}
			}
			return true;
		}

		List<PageToProduce> pagesToProduce;
		List<Rule> rules;
		Long res1;
		Long res2;
	}

	public static List<Long> getDuration() {
		A2024Day5 d = new A2024Day5(5);
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

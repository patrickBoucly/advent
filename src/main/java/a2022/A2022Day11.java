package a2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A2022Day11 extends A2022 {

	public A2022Day11(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day11 d = new A2022Day11(11);
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

	public long s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		List<Monkey> monkeys = getMonkeys(input);
		for (int round = 1; round <= 10000; round++) {
			monkeys = playRound(monkeys,2);
		}
		List<Integer> nbChecks = monkeys.stream().map(Monkey::getNombreDItemControlees).collect(Collectors.toList());
		nbChecks.sort(null);
		Long l1 = Long.parseLong(String.valueOf(nbChecks.get(nbChecks.size() - 2)));
		Long l2 = Long.parseLong(String.valueOf(nbChecks.get(nbChecks.size() - 1)));
		return l1 * l2;
	}

	public int s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		List<Monkey> monkeys = getMonkeys(input);
		for (int round = 1; round <= 20; round++) {
			monkeys = playRound(monkeys,1);
		}
		List<Integer> nbChecks = monkeys.stream().map(Monkey::getNombreDItemControlees).collect(Collectors.toList());
		nbChecks.sort(null);
		return nbChecks.get(nbChecks.size() - 2) * nbChecks.get(nbChecks.size() - 1);
	}

	private List<Monkey> playRound(List<Monkey> monkeys, int i) {
		for (Monkey m : monkeys) {
			for (int itemPos = 0; itemPos < m.getItems().size(); itemPos++) {
				Long item = m.getItems().get(itemPos);
				Long newItemValue = 0L;
				if (m.operator.equals("*")) {
					if (m.numberOperation.equals("old")) {
						newItemValue = item*item;
					} else {
						newItemValue = item*Long.valueOf(m.numberOperation);
					}
				} else {
					if (m.numberOperation.equals("old")) {
						newItemValue = item+item;
					} else {
						newItemValue = item+Long.valueOf(m.numberOperation);
					}
				}
				Long newItemValueReduce=0L;
				if(i==1) {
					newItemValueReduce= Math.floorDiv(newItemValue,3L);
				}else {
					Long produit=1L;
					for(Monkey mk:monkeys) {
						produit*=mk.testDivisibilityValue;
					}
					newItemValueReduce=Math.floorMod(newItemValue, produit);
				}
				if (newItemValueReduce%m.testDivisibilityValue==0) {
					for (Monkey mk : monkeys) {
						if (mk.id == m.idMonkeyToGiveIfTrue) {
							mk.items.add(newItemValueReduce);
						}
					}
				} else {
					for (Monkey mk : monkeys) {
						if (mk.id == m.idMonkeyToGiveIfFalse) {
							mk.items.add(newItemValueReduce);
						}
					}
				}
				m.nombreDItemControlees++;
			}
			m.setItems(new ArrayList<>());
		}
		return monkeys;
	}
	private List<Monkey> getMonkeys(List<String> input) {
		List<Monkey> monkeys = new ArrayList<>();
		int i = 0;
		while (i < input.size()) {
			String s = input.get(i);
			Integer id = Integer.valueOf(s.split(" ")[1].replace(":", "").trim());
			s = input.get(i + 1).substring(18);
			String[] split = s.split(",");
			List<Long> items = new ArrayList<>();
			for (String it : split) {
				items.add(Long.valueOf(it.trim()));
			}
			String operator = input.get(i + 2).substring(23, 24);
			String numberOperation = input.get(i + 2).substring(25).trim();
			Long testDivisibilityValue = Long.valueOf(input.get(i + 3).substring(21).trim());
			int idMonkeyToGiveIfTrue = Integer.valueOf(input.get(i + 4).substring(28).trim());
			int idMonkeyToGiveIfFalse = Integer.valueOf(input.get(i + 5).substring(29).trim());
			i = i + 7;
			monkeys.add(new Monkey(id, items, operator, numberOperation, testDivisibilityValue, idMonkeyToGiveIfTrue,
					idMonkeyToGiveIfFalse));
		}
		return monkeys;
	}

	private class Monkey {
		Integer id;
		List<Long> items;
		String operator;
		String numberOperation;
		Long testDivisibilityValue;
		int idMonkeyToGiveIfTrue;
		int idMonkeyToGiveIfFalse;
		int nombreDItemControlees;

		public Monkey(Integer id, List<Long> items, String operator, String numberOperation,
				Long testDivisibilityValue, int idMonkeyToGiveIfTrue, int idMonkeyToGiveIfFalse) {
			super();
			this.id = id;
			this.items = items;
			this.operator = operator;
			this.numberOperation = numberOperation;
			this.testDivisibilityValue = testDivisibilityValue;
			this.idMonkeyToGiveIfTrue = idMonkeyToGiveIfTrue;
			this.idMonkeyToGiveIfFalse = idMonkeyToGiveIfFalse;
			this.nombreDItemControlees = 0;
		}
		public List<Long> getItems() {return items;		}
		public void setItems(List<Long> items) {this.items = items;	}
		public int getNombreDItemControlees() {return nombreDItemControlees;}
	}
	public static List<Long> getDuration() {
		A2022Day11 d = new A2022Day11(11);
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

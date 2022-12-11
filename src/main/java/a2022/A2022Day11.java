package a2022;

import java.math.BigInteger;
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
		//System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(false));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public long s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		List<Monkey> monkeys = getMonkeys(input);
		System.out.println(monkeys);
		for (int round = 1; round <= 10000; round++) {
			monkeys = playRoundV2(monkeys);
			if (round % 100 == 0) {
				System.out.println("Round " + round);
			}
			if (round % 1000 == 0) {
				for (Monkey m : monkeys) {
					System.out.println(m.id + " " + m.nombreDItemControlees);
				}
			}
		}
		List<Integer> nbChecks = monkeys.stream().map(Monkey::getNombreDItemControlees).collect(Collectors.toList());
		nbChecks.sort(null);
		System.out.println(nbChecks);
		Long l1 = Long.parseLong(String.valueOf(nbChecks.get(nbChecks.size() - 2)));
		Long l2 = Long.parseLong(String.valueOf(nbChecks.get(nbChecks.size() - 1)));
		return l1 * l2;
	}

	public int s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		List<Monkey> monkeys = getMonkeys(input);
		System.out.println(monkeys);
		for (int round = 1; round <= 20; round++) {
			monkeys = playRound(monkeys);
		}
		List<Integer> nbChecks = monkeys.stream().map(Monkey::getNombreDItemControlees).collect(Collectors.toList());
		nbChecks.sort(null);
		System.out.println(nbChecks);
		return nbChecks.get(nbChecks.size() - 2) * nbChecks.get(nbChecks.size() - 1);
	}

	private List<Monkey> playRound(List<Monkey> monkeys) {
		for (Monkey m : monkeys) {
			for (int itemPos = 0; itemPos < m.getItems().size(); itemPos++) {
				BigInteger item = m.getItems().get(itemPos);
				BigInteger newItemValue = BigInteger.ZERO;
				if (m.operator.equals("*")) {
					if (m.numberOperation.equals("old")) {
						newItemValue = item.multiply(item);
					} else {
						newItemValue = item.multiply(BigInteger.valueOf(Long.valueOf(m.numberOperation)));
					}
				} else {
					if (m.numberOperation.equals("old")) {
						newItemValue = item.add(item);
					} else {
						newItemValue = item.add(BigInteger.valueOf(Long.valueOf(m.numberOperation)));
					}
				}
				BigInteger newItemValueDividedBy3=newItemValue.divideAndRemainder(BigInteger.valueOf(3L))[0];
				if (newItemValueDividedBy3.divideAndRemainder(m.testDivisibilityValue)[1].equals(BigInteger.ZERO)) {
					for (Monkey mk : monkeys) {
						if (mk.id == m.idMonkeyToGiveIfTrue) {
							// System.out.println(m.id + " donne " + newItemValue + " à " + mk.id);
							mk.items.add(newItemValueDividedBy3);
						}
					}
				} else {
					for (Monkey mk : monkeys) {
						if (mk.id == m.idMonkeyToGiveIfFalse) {
							//System.out.println(m.id + " donne " + newItemValue + " à " + mk.id);
							mk.items.add(newItemValueDividedBy3);
						}
					}
				}
				m.nombreDItemControlees++;
			}
			m.setItems(new ArrayList<>());
			for (Monkey mk : monkeys) {
				// System.out.println(mk);
			}

		}
		return monkeys;
	}

	private List<Monkey> playRoundV2(List<Monkey> monkeys) {
		for (Monkey m : monkeys) {
			for (int itemPos = 0; itemPos < m.getItems().size(); itemPos++) {
				BigInteger item = m.getItems().get(itemPos);
				BigInteger newItemValue = BigInteger.ZERO;
				if (m.operator.equals("*")) {
					if (m.numberOperation.equals("old")) {
						newItemValue = item.multiply(item);
					} else {
						newItemValue = item.multiply(BigInteger.valueOf(Long.valueOf(m.numberOperation)));
					}
				} else {
					if (m.numberOperation.equals("old")) {
						newItemValue = item.add(item);
					} else {
						newItemValue = item.add(BigInteger.valueOf(Long.valueOf(m.numberOperation)));
					}
				}
				if (newItemValue.divideAndRemainder(m.testDivisibilityValue)[1].equals(BigInteger.ZERO)) {
					for (Monkey mk : monkeys) {
						if (mk.id == m.idMonkeyToGiveIfTrue) {
							// System.out.println(m.id + " donne " + newItemValue + " à " + mk.id);
							mk.items.add(newItemValue);
						}
					}
				} else {
					for (Monkey mk : monkeys) {
						if (mk.id == m.idMonkeyToGiveIfFalse) {
							// System.out.println(m.id + " donne " + newItemValue + " à " + mk.id);
							mk.items.add(newItemValue);
						}
					}
				}
				m.nombreDItemControlees++;
			}
			m.setItems(new ArrayList<>());
			for (Monkey mk : monkeys) {
				// System.out.println(mk);
			}

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
			List<BigInteger> items = new ArrayList<>();
			for (String it : split) {
				items.add(BigInteger.valueOf(Long.valueOf(it.trim())));
			}
			String operator = input.get(i + 2).substring(23, 24);
			String numberOperation = input.get(i + 2).substring(25).trim();
			BigInteger testDivisibilityValue = BigInteger.valueOf(Long.valueOf(input.get(i + 3).substring(21).trim()));
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
		List<BigInteger> items;
		String operator;
		String numberOperation;
		BigInteger testDivisibilityValue;
		int idMonkeyToGiveIfTrue;
		int idMonkeyToGiveIfFalse;
		int nombreDItemControlees;

		public Monkey(Integer id, List<BigInteger> items, String operator, String numberOperation,
				BigInteger testDivisibilityValue, int idMonkeyToGiveIfTrue, int idMonkeyToGiveIfFalse) {
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

		public List<BigInteger> getItems() {
			return items;
		}

		public void setItems(List<BigInteger> items) {
			this.items = items;
		}

		public BigInteger getTestDivisibilityValue() {
			return testDivisibilityValue;
		}

		public void setTestDivisibilityValue(BigInteger testDivisibilityValue) {
			this.testDivisibilityValue = testDivisibilityValue;
		}

		public int getIdMonkeyToGiveIfFalse() {
			return idMonkeyToGiveIfFalse;
		}

		public void setIdMonkeyToGiveIfFalse(int idMonkeyToGiveIfFalse) {
			this.idMonkeyToGiveIfFalse = idMonkeyToGiveIfFalse;
		}

		public int getNombreDItemControlees() {
			return nombreDItemControlees;
		}

		public void setNombreDItemControlees(int nombreDItemControlees) {
			this.nombreDItemControlees = nombreDItemControlees;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public String getNumberOperation() {
			return numberOperation;
		}

		public void setNumberOperation(String numberOperation) {
			this.numberOperation = numberOperation;
		}

		public int getIdMonkeyToGiveIfTrue() {
			return idMonkeyToGiveIfTrue;
		}

		public void setIdMonkeyToGiveIfTrue(int idMonkeyToGiveIfTrue) {
			this.idMonkeyToGiveIfTrue = idMonkeyToGiveIfTrue;
		}

		public int getIdMonkeyToGiveIffalse() {
			return idMonkeyToGiveIfFalse;
		}

		public void setIdMonkeyToGiveIffalse(int idMonkeyToGiveIfFalse) {
			this.idMonkeyToGiveIfFalse = idMonkeyToGiveIfFalse;
		}

		@Override
		public String toString() {
			return "Monkey [id=" + id + ", items=" + items + ", operator=" + operator + ", numberOperation="
					+ numberOperation + ", testDivisibilityValue=" + testDivisibilityValue + ", idMonkeyToGiveIfTrue="
					+ idMonkeyToGiveIfTrue + ", idMonkeyToGiveIfFalse=" + idMonkeyToGiveIfFalse + "]";
		}

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

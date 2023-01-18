package a2022;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

public class A2022Day21 extends A2022 {
	Long solutionS2 = 0L;

	public A2022Day21(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day21 d = new A2022Day21(21);

		long startTime = System.currentTimeMillis();
		// System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public BigInteger s2(boolean b) {
		Set<Monkey> monkeys = getMonkeys(b);
		Pair<String, Long> cible = getCible(monkeys);
		String equation = cible.getLeft() + "=" + cible.getRight();
		System.out.println(cible.getRight().equals(72950437237500L));
		while (contientLettre(equation)) {
			equation = nextReplace(equation, monkeys);
		}
		// too high 10007319913897
		// 10007319913897
		// 10007319913897
		BigInteger res = calculer(equation);
		// System.out.println(s2b(b));

		return res;
	}

	private BigInteger calculer(String equation) {
		BigInteger res = BigInteger.valueOf(Long.valueOf(equation.split("=")[1]));
		String reste = equation.split("=")[0].replace(" ", "");
		reste = reste.substring(1);
		reste = reste.substring(0, reste.length() - 1);
		boolean simplifiable = true;
		System.out.println(reste);
		String simplifie = simplifier(reste);
		System.out.println("1 " + simplifie);
		String oldReste = reste;
		int cpt = 0;
		while (!oldReste.equals(simplifie)) {
			cpt++;
			oldReste = reste;
			simplifie = simplifier(reste);
			reste = simplifie;
			System.out.println(cpt + " " + reste);

		}
		cpt = 0;
		oldReste = reste;
		simplifie = "";
		while (!oldReste.equals(simplifie)) {
			cpt++;
			oldReste = reste;
			simplifie = simplifierD(reste);
			reste = simplifie;
			System.out.println(cpt + " " + reste);

		}
		boolean fini = false;
		System.out.println(reste);
		while (!fini) {
			if (reste.substring(0, 1).equals("h")) {
				Pair<String, BigInteger> operateurAndValue = getLastOperatorAndValue(reste);
				return applyOpAndValue(res, operateurAndValue);
			} else if (reste.substring(0, 1).equals("n")) {
				Pair<String, BigInteger> operateurAndValue = getFirstOperatorAndValue(reste);
				return applyOpAndValue(res, operateurAndValue);
			} else if (reste.substring(0, 1).equals("(")
					&& reste.substring(reste.length() - 1, reste.length()).equals(")")) {
				reste = reste.substring(1);
				reste = reste.substring(0, reste.length() - 1);
				System.out.println(reste + " " + res);
			} else if (reste.substring(0, 1).equals("(")) {
				Pair<String, BigInteger> operateurAndValue = getLastOperatorAndValue(reste);
				res = applyOpAndValue(res, operateurAndValue);
				reste = reste.substring(1);
				reste = reste.substring(0, reste.length() - 2 - operateurAndValue.getValue().toString().length());
				System.out.println(reste + " " + res);
			} else if (reste.substring(reste.length() - 1, reste.length()).equals(")")) {
				Pair<String, BigInteger> operateurAndValue = getFirstOperatorAndValue(reste);
				res = applyOpAndValue3(res, operateurAndValue);
				reste = reste.substring(0, reste.length() - 1);
				reste = reste.substring(2 + operateurAndValue.getValue().toString().length());
				System.out.println(reste + " " + res);
			}
		}
		return res;
	}

	private BigInteger applyOpAndValue3(BigInteger res, Pair<String, BigInteger> operateurAndValue) {
			if (operateurAndValue.getLeft().equals("+")) {
				return res.add(operateurAndValue.getRight().negate());
			}
			if (operateurAndValue.getLeft().equals("-")) {
				return operateurAndValue.getRight().add(res.negate());
			}
			if (operateurAndValue.getLeft().equals("*")) {
				return res.divide(operateurAndValue.getRight());
			}
			return operateurAndValue.getRight().divide(res);
		}
	

	private String simplifierD(String reste) {
		for (int i = reste.length() - 1; i > 0; i--) {
			if (reste.substring(i, i + 1).equals(")")) {
				for (int j = i - 1; j > 0; j--) {
					if (reste.substring(j, j + 1).equals(")")) {
						break;
					} else if (reste.substring(j, j + 1).equals("(")) {
						if (!(reste.substring(j + 1, i).contains("(") || reste.substring(j + 1, i).contains(")"))) {
							if (reste.substring(j, i + 1).contains("humn")) {
								return reste;
							}

							int cptOp = cptOP(reste.substring(j + 1, i));
							String bloc=reste.substring(j + 1, i);
							if (cptOp == 1) {
								String retour=reste.substring(0, j) + calculSimple(bloc)+ reste.substring(i + 1);
								System.out.println(retour+" "+bloc);
								return retour;

							}
							String retour=reste.substring(0, j) + calculComplexe(bloc)	+ reste.substring(i + 1);
							System.out.println(retour+" "+bloc);
							return retour;
						} else {
							break;
						}
					}
				}
			}
		}
		return reste;
	}

	private BigInteger calculComplexe(String s) {
		BigInteger res = BigInteger.ZERO;
		while (cptOP(s) > 0) {
			while (cptOPPrio(s) > 0) {
				s = applyFirstPrio(s);
			}
			s = applyLessPrio(s);
		}
		return new BigInteger(s);
	}

	private String applyLessPrio(String s) {
		BigInteger res = BigInteger.ZERO;
		while (cptOP(s) > 0) {
			for (int i = 0; i < s.length(); i++) {
				if (List.of("+", "-").contains(s.substring(i, i + 1))) {
					res = new BigInteger(s.substring(0, i));
					if (cptOP(s.substring(i + 1)) > 0) {
						for (int j = i + 1; i < s.length(); j++) {
							if (List.of("+", "-").contains(s.substring(j, j + 1))) {
								String debut = applyOpAndValue2(res, getLastOperatorAndValue(s.substring(0, j)))
										.toString();
								return debut + s.substring(j);
							}
						}
					} else {
						return applyOpAndValue2(res, getLastOperatorAndValue(s)).toString();
					}
				}
			}
		}
		return s;

	}

	private String applyFirstPrio(String s) {
		BigInteger res = BigInteger.ZERO;
		int start = 0;
		for (int i = 0; i < s.length(); i++) {
			if (List.of("+", "-").contains(s.substring(i, i + 1))) {
				start = i + 1;
			}
			if (List.of("*", "/").contains(s.substring(i, i + 1))) {
				int end = 0;
				for (int j = i + 1; j < s.length() - 1; j++) {
					if (List.of("+", "-", "*", "/").contains(s.substring(j, j + 1))) {
						end = j;
					}
					if (end != 0) {
						if (start == 0) {
							return applyOpAndValue2(new BigInteger(s.substring(start, i)),
									getLastOperatorAndValue(s.substring(start, end))) + s.substring(end);

						} else {
							return s.substring(0, start) + applyOpAndValue2(new BigInteger(s.substring(start, i)),
									getLastOperatorAndValue(s.substring(start, end))) + s.substring(end);
						}

					}

				}
				return s.substring(0, start) + applyOpAndValue2(new BigInteger(s.substring(start, i)),
						getLastOperatorAndValue(s.substring(start)));

			}
		}
		return s;
	}

	private int cptOP(String s) {
		int res = 0;
		for (int i = 0; i < s.length(); i++) {
			if (List.of("+", "-", "*", "/").contains(s.substring(i, i + 1))) {
				res++;
			}
		}
		return res;
	}

	private int cptOPPrio(String s) {
		int res = 0;
		for (int i = 0; i < s.length(); i++) {
			if (List.of("*", "/").contains(s.substring(i, i + 1))) {
				res++;
			}
		}
		return res;
	}

	private String simplifier(String reste) {
		for (int i = 0; i < reste.length(); i++) {
			if (reste.substring(i, i + 1).equals("(")) {
				for (int j = i + 1; j < reste.length(); j++) {
					if (reste.substring(j, j + 1).equals("(")) {
						break;
					} else if (reste.substring(j, j + 1).equals(")")) {
						if (reste.substring(i + 1, j).contains("humn")) {
							return reste;
						}
						if (!(reste.substring(i + 1, j).contains("(") || reste.substring(i + 1, j).contains(")"))) {
							String bloc=reste.substring(i + 1, j);
							int cptOp = cptOP(reste.substring(i + 1, j));
							if (cptOp == 1) {
								String retour=reste.substring(0, i) + calculSimple(bloc)+ reste.substring(j + 1);
								System.out.println(bloc+" "+retour);
								return retour;

							}
							String retour= reste.substring(0, i) + calculComplexe(bloc)	+ reste.substring(j + 1);
							System.out.println(bloc+" "+retour);
							return retour;
						} else {
							break;
						}
					}
				}
			}
		}
		return reste;
	}

	private BigInteger calculSimple(String substring) {
		return applyOpAndValue2(getFirstOperatorAndValue(substring).getRight(), getLastOperatorAndValue(substring));
	}

	private Pair<String, BigInteger> getFirstOperatorAndValue(String reste) {
		for (int i = 0; i < reste.length(); i++) {
			if (List.of("+", "-", "/", "*").contains(reste.substring(i, i + 1))) {
				return Pair.of(reste.substring(i, i + 1), new BigInteger(reste.substring(0, i)));
			}
		}
		return null;
	}

	private BigInteger applyOpAndValue(BigInteger res, Pair<String, BigInteger> operateurAndValue) {
		if (operateurAndValue.getLeft().equals("+")) {
			return res.add(operateurAndValue.getRight().negate());
		}
		if (operateurAndValue.getLeft().equals("-")) {
			return res.add(operateurAndValue.getRight());
		}
		if (operateurAndValue.getLeft().equals("*")) {
			return res.divide(operateurAndValue.getRight());
		}
		return res.multiply(operateurAndValue.getRight());
	}

	private BigInteger applyOpAndValue2(BigInteger res, Pair<String, BigInteger> operateurAndValue) {
		if (operateurAndValue.getLeft().equals("+")) {
			return res.add(operateurAndValue.getRight());
		}
		if (operateurAndValue.getLeft().equals("-")) {
			return res.add(operateurAndValue.getRight().negate());
		}
		if (operateurAndValue.getLeft().equals("*")) {
			return res.multiply(operateurAndValue.getRight());
		}
		return res.divide(operateurAndValue.getRight());
	}

	private Pair<String, BigInteger> getLastOperatorAndValue(String reste) {
		for (int i = reste.length() - 1; i > 0; i--) {
			if (List.of("+", "-", "/", "*").contains(reste.substring(i, i + 1))) {
				return Pair.of(reste.substring(i, i + 1), new BigInteger(reste.substring(i + 1)));
			}
		}
		return null;
	}

	private String nextReplace(String equation, Set<Monkey> monkeys) {
		for (Monkey m : monkeys) {
			if (equation.contains(m.name) && !m.name.equals("humn")) {
				if (m.nameMonkeyLeft != null) {

					return equation.replace(m.name,
							"( " + m.nameMonkeyLeft + " " + m.operator + " " + m.nameMonkeyRight + " ) ");
				}
				return equation.replace(m.name, "" + m.yell);
			}
		}
		return equation;
	}

	private boolean contientLettre(String equation) {
		equation = equation.replace("humn", "");
		String lettres = "abcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < lettres.length(); i++) {
			if (equation.contains(lettres.substring(i, i + 1))) {
				return true;
			}
		}
		return false;
	}

	private Pair<String, Long> getCible(Set<Monkey> monkeys) {
		Long g1 = -1L;
		Long d1 = -1L;
		Long d2 = -1L;
		Long g2 = -1L;
		Set<Monkey> monkeysTest = getMonkeysCopy(monkeys);
		monkeysTest.stream().filter(m -> m.name.equals("humn")).findFirst().get().setYell(0L);
		boolean fini = false;
		while (!fini) {
			nextYell(monkeysTest);
			fini = rootLeftAndRigthYell(monkeysTest);
			if (fini) {
				Monkey root = getMonkeyFromName(monkeysTest, "root");
				g1 = getMonkeyFromName(monkeysTest, root.nameMonkeyLeft).yell;
				d1 = getMonkeyFromName(monkeysTest, root.nameMonkeyRight).yell;

			}
		}
		monkeysTest = getMonkeysCopy(monkeys);
		monkeysTest.stream().filter(m -> m.name.equals("humn")).findFirst().get().setYell(10L);
		fini = false;
		while (!fini) {
			nextYell(monkeysTest);
			fini = rootLeftAndRigthYell(monkeysTest);
			if (fini) {
				Monkey root = getMonkeyFromName(monkeysTest, "root");
				g2 = getMonkeyFromName(monkeysTest, root.nameMonkeyLeft).yell;
				d2 = getMonkeyFromName(monkeysTest, root.nameMonkeyRight).yell;
			}
		}
		Monkey root = getMonkeyFromName(monkeysTest, "root");
		if (g1.equals(g2)) {
			return Pair.of(getMonkeyFromName(monkeysTest, root.nameMonkeyRight).name, g1);
		}
		return Pair.of(getMonkeyFromName(monkeysTest, root.nameMonkeyLeft).name, d1);
	}

	public Long s2b(boolean b) {
		Set<Monkey> monkeys = getMonkeys(b);
		Long test = 2002849640L;
		monkeys.stream().filter(m -> m.name.equals("root")).findFirst().get().setOperator("=");
		monkeys.stream().filter(m -> m.name.equals("humn")).findFirst().get().setYell(test);
		Set<Monkey> monkeysTest = getMonkeysCopy(monkeys);
		boolean good = false;
		while (!good) {
			test = (long) Math.random();
			monkeysTest = getMonkeysCopy(monkeys);
			monkeysTest.stream().filter(m -> m.name.equals("humn")).findFirst().get().setYell(test);
			boolean fini = false;
			while (!fini) {
				nextYell(monkeysTest);
				fini = rootLeftAndRigthYell(monkeysTest);
				if (fini) {
					Monkey root = getMonkeyFromName(monkeysTest, "root");
					Monkey mg = getMonkeyFromName(monkeysTest, root.nameMonkeyLeft);
					Monkey md = getMonkeyFromName(monkeysTest, root.nameMonkeyRight);
					System.out.println(test + " " + md);
					// System.out.println(mg.yell - md.yell);
					good = equalityRoot(monkeysTest);
				}
			}

			if (good) {
				solutionS2 = monkeysTest.stream().filter(m -> m.name.equals("humn")).findFirst().get().getYell();
			}
		}

		return solutionS2;
	}

	private boolean equalityRoot(Set<Monkey> monkeys) {
		Monkey root = monkeys.stream().filter(m -> m.name.equals("root")).findFirst().get();
		Monkey mg = getMonkeyFromName(monkeys, root.nameMonkeyLeft);
		Monkey md = getMonkeyFromName(monkeys, root.nameMonkeyRight);
		if (mg.yell != null && md.yell != null) {
			return mg.yell.equals(md.yell);
		}
		return false;
	}

	private boolean rootLeftAndRigthYell(Set<Monkey> monkeys) {
		Monkey root = monkeys.stream().filter(m -> m.name.equals("root")).findFirst().get();
		Monkey mg = getMonkeyFromName(monkeys, root.nameMonkeyLeft);
		Monkey md = getMonkeyFromName(monkeys, root.nameMonkeyRight);
		return mg.yell != null && md.yell != null;
	}

	private Set<Monkey> getMonkeysCopy(Set<Monkey> monkeys) {
		Set<Monkey> copy = new HashSet<>();
		for (Monkey m : monkeys) {
			copy.add(new Monkey(m.name, m.yell, m.nameMonkeyLeft, m.nameMonkeyRight, m.operator));
		}
		return copy;
	}

	private Set<Monkey> getMonkeys(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Set<Monkey> monkeys = new HashSet<>();
		for (String s : input) {
			Monkey m = new Monkey();
			String[] sp = s.split(" ");
			if (sp.length == 2) {
				m.setName(sp[0].replace(":", ""));
				m.setYell(Long.parseLong(sp[1]));
			} else {
				m.setName(sp[0].replace(":", ""));
				m.setYell(null);
				m.setNameMonkeyLeft(sp[1]);
				m.setNameMonkeyRight(sp[3]);
				m.setOperator(sp[2]);
			}
			monkeys.add(m);
		}
		return monkeys;
	}

	public Long s1(boolean b) {
		Set<Monkey> monkeys = getMonkeys(b);
		for (Monkey m : monkeys) {
			System.out.println(m);
		}
		boolean fini = false;
		while (!fini) {
			nextYell(monkeys);
			fini = rootYell(monkeys);
		}
		return monkeys.stream().filter(m -> m.name.equals("root")).findFirst().map(Monkey::getYell).get();
	}

	private boolean rootYell(Set<Monkey> monkeys) {
		return monkeys.stream().filter(m -> m.name.equals("root") && m.yell != null).findAny().isPresent();
	}

	private void nextYell(Set<Monkey> monkeys) {
		boolean majOk = false;
		while (!majOk) {
			for (Monkey m : monkeys) {
				if (m.yell == null) {
					Monkey mg = getMonkeyFromName(monkeys, m.nameMonkeyLeft);
					Monkey md = getMonkeyFromName(monkeys, m.nameMonkeyRight);
					if (mg.yell != null && md.yell != null) {
						majOk = true;
						m.apply(mg.yell, m.operator, md.yell);
					}
				}
			}
		}

	}

	private Monkey getMonkeyFromName(Set<Monkey> monkeys, String name) {
		return monkeys.stream().filter(m -> m.name.equals(name)).findFirst().orElse(null);
	}

	public class Monkey {
		String name;
		Long yell;
		String nameMonkeyLeft;
		String nameMonkeyRight;
		String operator;

		public String getName() {
			return name;
		}

		public void apply(Long yellg, String op, Long yelld) {
			if (op.equals("+")) {
				yell = yellg + yelld;
			} else if (op.equals("-")) {
				yell = yellg - yelld;
			} else if (op.equals("*")) {
				yell = yellg * yelld;
			} else if (op.equals("/")) {
				yell = yellg / yelld;
			}

		}

		public void setName(String name) {
			this.name = name;
		}

		public Long getYell() {
			return yell;
		}

		public void setYell(Long yell) {
			this.yell = yell;
		}

		public String getNameMonkeyLeft() {
			return nameMonkeyLeft;
		}

		public void setNameMonkeyLeft(String nameMonkeyLeft) {
			this.nameMonkeyLeft = nameMonkeyLeft;
		}

		public String getNameMonkeyRight() {
			return nameMonkeyRight;
		}

		public void setNameMonkeyRight(String nameMonkeyRight) {
			this.nameMonkeyRight = nameMonkeyRight;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public Monkey(String name, Long yell, String nameMonkeyLeft, String nameMonkeyRight, String operator) {
			super();
			this.name = name;
			this.yell = yell;
			this.nameMonkeyLeft = nameMonkeyLeft;
			this.nameMonkeyRight = nameMonkeyRight;
			this.operator = operator;
		}

		public Monkey() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(name);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Monkey other = (Monkey) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Objects.equals(name, other.name);
		}

		private A2022Day21 getEnclosingInstance() {
			return A2022Day21.this;
		}

		@Override
		public String toString() {
			if (yell == null) {
				return name + ": " + nameMonkeyLeft + " " + operator + " " + nameMonkeyRight;
			}
			return name + ": yell=" + yell;
		}

	}

	public static List<Long> getDuration() {
		A2022Day21 d = new A2022Day21(21);
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

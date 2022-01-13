package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2017Day8 extends A2017 {

	public A2017Day8(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day8 d = new A2017Day8(8);
		// d.s1(true);
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

	public int s1(boolean b) {
		List<Instruction> inst = getInst(b);
		System.out.println(inst.size());
		HashMap<String, Integer> reg = new HashMap<>();
		Set<String> setReg = inst.stream().map(Instruction::getRegistre).collect(Collectors.toSet());
		for (String s : setReg) {
			reg.put(s, 0);
		}
		for (Instruction i : inst) {
			reg = apply(reg, i);
		}
		System.out.println(reg);
		int res = MesOutils.getMaxIntegerFromList(reg.values().stream().collect(Collectors.toList()));

		return res;
	}

	private HashMap<String, Integer> apply(HashMap<String, Integer> reg, Instruction i) {
		if (condVerif(i.regC, i.opC, i.valCond, reg)) {
			if (i.op.equals("inc")) {
				reg.put(i.registre, reg.get(i.registre) + i.val);
			} else {
				reg.put(i.registre, reg.get(i.registre) - i.val);
			}
		}
		return reg;
	}

	private boolean condVerif(String regC, String opC, Integer v, HashMap<String, Integer> reg) {
		switch (opC) {
		case "==":
			return reg.get(regC).equals(v);
		case ">":
			return reg.get(regC) > v;
		case ">=":
			return reg.get(regC) >= v;
		case "<":
			return reg.get(regC) < v;
		case "<=":
			return reg.get(regC) <= v;
		case "!=":
			return !reg.get(regC).equals(v);
		default:
			System.out.println(opC);
		}
		return false;
	}

	private List<Instruction> getInst(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Instruction> inst = new ArrayList<>();
		for (String l : lignes) {
			String[] sp = l.split("if");
			String[] spG = sp[0].trim().split(" ");
			String[] spD = sp[1].trim().split(" ");
			String registre = spG[0].trim();
			String op = spG[1].trim();
			Integer val = Integer.parseInt(spG[2].trim());
			String regC = spD[0].trim();
			String opC = spD[1].trim();
			Integer valCond = Integer.parseInt(spD[2].trim());
			inst.add(new Instruction(registre, op, val, regC, opC, valCond));
		}
		return inst;
	}

	public int s2(boolean b) {
		List<Instruction> inst = getInst(b);
		System.out.println(inst.size());
		HashMap<String, Integer> reg = new HashMap<>();
		Set<String> setReg = inst.stream().map(Instruction::getRegistre).collect(Collectors.toSet());
		for (String s : setReg) {
			reg.put(s, 0);
		}
		int res = 0;
		for (Instruction i : inst) {
			reg = apply(reg, i);
			int nm = MesOutils.getMaxIntegerFromList(reg.values().stream().collect(Collectors.toList()));
			if (nm > res) {
				res = nm;
			}
		}

		return res;

	}

	public static class Instruction {
		String registre;
		String op;
		Integer val;
		String regC;
		String opC;
		Integer valCond;

		public String getRegistre() {
			return registre;
		}

		public void setRegistre(String registre) {
			this.registre = registre;
		}

		public String getOp() {
			return op;
		}

		public void setOp(String op) {
			this.op = op;
		}

		public String getRegC() {
			return regC;
		}

		public void setRegC(String regC) {
			this.regC = regC;
		}

		public String getOpC() {
			return opC;
		}

		public void setOpC(String opC) {
			this.opC = opC;
		}

		public Integer getValCond() {
			return valCond;
		}

		public void setValCond(Integer valCond) {
			this.valCond = valCond;
		}

		public Integer getVal() {
			return val;
		}

		public void setVal(Integer val) {
			this.val = val;
		}

		public Instruction(String registre, String op, Integer val, String regC, String opC, Integer valCond) {
			super();
			this.registre = registre;
			this.op = op;
			this.val = val;
			this.regC = regC;
			this.opC = opC;
			this.valCond = valCond;
		}

		@Override
		public String toString() {
			return "Instruction [registre=" + registre + ", op=" + op + ", val=" + val + ", regC=" + regC + ", opC="
					+ opC + ", valCond=" + valCond + "]";
		}

	}

	public static List<Long> getDuration() {
		A2017Day8 d = new A2017Day8(1);
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

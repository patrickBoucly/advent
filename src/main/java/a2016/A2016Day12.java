package a2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A2016Day12 extends A2016 {
	public A2016Day12(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day12 d = new A2016Day12(12);
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

	public Integer s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Instruction> insts = new ArrayList<>();
		for (String s : input) {
			String[] split = s.split(" ");
			insts.add(new Instruction(split[0], split[1], split.length == 3 ? split[2] : null));
		}
		Assembunny assembunny = new Assembunny(0, 0, 0, 0, 0);
		while (assembunny.pos < insts.size()) {
			assembunny.apply(insts.get(assembunny.pos));
			//System.out.println(assembunny);
		}
		return assembunny.a;
	}

	public Integer s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Instruction> insts = new ArrayList<>();
		for (String s : input) {
			String[] split = s.split(" ");
			insts.add(new Instruction(split[0], split[1], split.length == 3 ? split[2] : null));
		}
		Assembunny assembunny = new Assembunny(0, 0, 1, 0, 0);
		while (assembunny.pos < insts.size()) {
			assembunny.apply(insts.get(assembunny.pos));
			//System.out.println(assembunny);
		}
		return assembunny.a;
	}

	private class Assembunny {
		Integer a;
		Integer b;
		Integer c;
		Integer d;
		Integer pos;

		public void apply(Instruction inst) {
			if (inst.type.equals("cpy")) {
				applyCpy(inst.p1, inst.p2);
				pos++;
			}
			if (inst.type.equals("inc")) {
				applyInc(inst.p1, inst.p2);
				pos++;
			}
			if (inst.type.equals("dec")) {
				applyDec(inst.p1, inst.p2);
				pos++;
			}
			if (inst.type.equals("jnz")) {
				applyJnz(inst.p1, inst.p2);
			}
		}

		private void applyJnz(String x, String y) {
			if(x.equals("0")) {
				pos++;
			} else if(x.equals("a") && a.equals(0)) {
				pos++;
			}else if(x.equals("b") && b.equals(0)) {
				pos++;
			}else if(x.equals("c") && c.equals(0)) {
				pos++;
			}else if(x.equals("d") && d.equals(0)) {
				pos++;
			} else {
				pos += Integer.valueOf(y);
			} 
		}

		private void applyDec(String p1, String p2) {
			updateRegistre(p1, -1);
		}

		private void updateRegistre(String registre, int i) {
			if (registre.equals("a")) {
				a += i;
			}
			if (registre.equals("b")) {
				b += i;
			}
			if (registre.equals("c")) {
				c += i;
			}
			if (registre.equals("d")) {
				d += i;
			}
		}

		private void applyInc(String p1, String p2) {
			updateRegistre(p1, 1);
		}

		private void applyCpy(String p1, String registre) {
			if(p1.equals("a")) {
				applyCpyValue(a,registre);
			} else if(p1.equals("b")) {
				applyCpyValue(b,registre);
			} else if(p1.equals("c")) {
				applyCpyValue(c,registre);
			} else if(p1.equals("d")) {
				applyCpyValue(d,registre);
			} else {
				applyCpyValue(Integer.valueOf(p1),registre);
			}
		}

		private void applyCpyValue(Integer i, String registre) {
			if (registre.equals("a")) {
				a = i;
			}
			if (registre.equals("b")) {
				b = i;
			}
			if (registre.equals("c")) {
				c = i;
			}
			if (registre.equals("d")) {
				d = i;
			}
			
		}

		public Assembunny(Integer a, Integer b, Integer c, Integer d, Integer pos) {
			super();
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
			this.pos = pos;
		}


		@Override
		public String toString() {
			return "Assembunny [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", pos=" + pos + "]";
		}
		

	}

	private class Instruction {
		String type;
		String p1;
		String p2;


		public Instruction(String type, String p1, String p2) {
			super();
			this.type = type;
			this.p1 = p1;
			this.p2 = p2;
		}

		@Override
		public String toString() {
			return "Instruction [type=" + type + ", p1=" + p1 + ", p2=" + p2 + "]";
		}

	}

	public static List<Long> getDuration() {
		A2016Day12 d = new A2016Day12(12);
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

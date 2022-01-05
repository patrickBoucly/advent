package aocmaven.a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class A2021Day24 extends A2021 {

	public A2021Day24(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day24 d = new A2021Day24(24);
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
	
	private Long s1(boolean b) {
		return s(b,1);
	}
	private Long s2(boolean b) {
		return s(b,2);
	}
	
	private Long s(boolean b,int partie) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<BlocInst> blocs = new ArrayList<A2021Day24.BlocInst>();
		List<Instruction> inst = new ArrayList<A2021Day24.Instruction>();
		for (String l : lignes) {
			if (l.isEmpty()) {
				blocs.add(new BlocInst(inst));
				inst = new ArrayList<A2021Day24.Instruction>();
			} else {
				String[] sp = l.split(" ");
				if (sp.length == 2) {
					inst.add(new Instruction(sp[0], sp[1]));
				} else {
					inst.add(new Instruction(sp[0], sp[1], sp[2]));
				}
			}
		}
		blocs.add(new BlocInst(inst));
		List<Long> input_space = new ArrayList<>();
		Long max = 9999999L;
		while (max > -1) {
			input_space.add(max);
			max--;
		}
		
		input_space = input_space.stream().filter(l -> String.valueOf(l).length() == 7)
				.filter(l -> !String.valueOf(l).contains("0")).collect(Collectors.toList());
		if(partie==1) {
		input_space.sort(Comparator.reverseOrder());
		} else {
			input_space.sort(Comparator.naturalOrder());
		}
		Optional<Long> reponse = Optional.of(0L);
			for (Long lg : input_space) {
				reponse = trouverMax(lg, blocs);
				if (reponse != null) {
					break;
				}
			}

		return reponse.get();
	}

	private Optional<Long> trouverMax(Long lg, List<BlocInst> blocs) {
		String res = "";
		int z = 0;
		String lgs = String.valueOf(lg);
		int pos = 0;
		for (BlocInst bi : blocs) {
			if (bi.type == 2) {
				int digit = (z % 26) + bi.decrease;
				res += digit;
				z = Math.floorDiv(z, 26);
				if (digit < 1 || digit > 9) {
					return null;
				}
			} else {
				res += lgs.substring(pos, pos + 1);
				z = 26 * z + bi.increment + Integer.parseInt(lgs.substring(pos, pos + 1));
				pos++;
			}
		}
		if (z == 0) {
			return Optional.of(Long.parseLong(res));
		}
		return null;
	}

	public static boolean isLong(String s) {
		try {
			Long.parseLong(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	public static class BlocInst {
		List<Instruction> instr;
		int type;
		int increment;
		int decrease;

		@Override
		public String toString() {
			return "BlocInst [instr=" + instr + ", type=" + type + ", increment=" + increment + ", decrease=" + decrease
					+ "]";
		}

		public BlocInst(List<Instruction> instr) {
			super();
			this.instr = instr;
			this.type = (Integer.parseInt(instr.get(5).p2) > 0) ? 1 : 2;
			this.decrease = (type == 2) ? Integer.parseInt(instr.get(5).p2) : 0;
			this.increment = (Integer.parseInt(instr.get(15).p2));
		}

		public List<Instruction> getInstr() {
			return instr;
		}

		public void setInstr(List<Instruction> instr) {
			this.instr = instr;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public int getIncrement() {
			return increment;
		}

		public void setIncrement(int increment) {
			this.increment = increment;
		}

	}

	public static class Instruction {
		String operator;
		String p1;
		String p2;

		public Instruction(String operator, String p1, String p2) {
			super();
			this.operator = operator;
			this.p1 = p1;
			this.p2 = p2;
		}

		public Instruction(String operator, String p1) {
			super();
			this.operator = operator;
			this.p1 = p1;
			this.p2 = "";
		}

		@Override
		public String toString() {
			return operator + " " + p1 + " " + p2;
		}

	}

	public static class ALU {
		Long w;
		Long x;
		Long y;
		Long z;
		List<Instruction> inst;

		public List<Instruction> getInst() {
			return inst;
		}

		public void setInst(List<Instruction> inst) {
			this.inst = inst;
		}

		public Long getW() {
			return w;
		}

		public void setW(Long w) {
			this.w = w;
		}

		public Long getX() {
			return x;
		}

		public void setX(Long x) {
			this.x = x;
		}

		public Long getY() {
			return y;
		}

		public void setY(Long y) {
			this.y = y;
		}

		public Long getZ() {
			return z;
		}

		public void setZ(Long z) {
			this.z = z;
		}

		public ALU(List<Instruction> inst) {
			super();
			this.w = 0L;
			this.x = 0L;
			this.y = 0L;
			this.z = 0L;
			this.inst = inst;
		}

		public Long analyse(String input) {
			this.w = 0L;
			this.x = 0L;
			this.y = 0L;
			this.z = 0L;
			int pos = 0;
			for (Instruction i : inst) {
				if (i.operator.equals("inp")) {
					inp(Long.parseLong(input.substring(pos, pos + 1)), i.p1);
					pos++;
				} else if (i.operator.equals("add")) {
					add(i.p1, i.p2);
				} else if (i.operator.equals("mul")) {
					mul(i.p1, i.p2);
				} else if (i.operator.equals("mod")) {
					mod(i.p1, i.p2);
				} else if (i.operator.equals("eql")) {
					eql(i.p1, i.p2);
				} else if (i.operator.equals("div")) {
					div(i.p1, i.p2);
				}
			}
			// System.out.println(z);
			return z;
		}

		public void inp(long l, String var) {
			if (var.equals("w")) {
				w = l;
			} else if (var.equals("x")) {
				x = l;
			} else if (var.equals("y")) {
				y = l;
			} else {
				z = l;
			}
		}

		public void add(String a, String b) {
			Long bi = getBi(b);
			if (a.equals("w")) {
				w = w + bi;
			} else if (a.equals("x")) {
				x = x + bi;
			} else if (a.equals("y")) {
				y = y + bi;
			} else {
				z = z + bi;
			}
		}

		private Long getBi(String b) {
			Long bi = 0L;
			if (isLong(b)) {
				bi = Long.parseLong(b);
			} else {
				if (b.equals("w")) {
					bi = w;
				} else if (b.equals("x")) {
					bi = x;
				} else if (b.equals("y")) {
					bi = y;
				} else {
					bi = z;
				}
			}
			return bi;
		}

		public void mul(String a, String b) {
			Long bi = getBi(b);
			if (a.equals("w")) {
				w = w * bi;
			} else if (a.equals("x")) {
				x = x * bi;
			} else if (a.equals("y")) {
				y = y * bi;
			} else {
				z = z * bi;
			}
		}

		public void div(String a, String b) {
			Long bi = getBi(b);
			if (a.equals("w")) {
				w = Math.floorDiv(w, bi);
			} else if (a.equals("x")) {
				x = Math.floorDiv(x, bi);
			} else if (a.equals("y")) {
				y = Math.floorDiv(y, bi);
			} else {
				z = Math.floorDiv(z, bi);
			}
		}

		public void mod(String a, String b) {
			Long bi = getBi(b);
			if (a.equals("w")) {
				w = w % bi;
			} else if (a.equals("x")) {
				x = x % bi;
			} else if (a.equals("y")) {
				y = y % bi;
			} else {
				z = z % bi;
			}
		}

		public void eql(String a, String b) {
			Long bi = getBi(b);
			if (a.equals("w")) {
				w = (w == bi) ? 1L : 0L;
			} else if (a.equals("x")) {
				x = (x == bi) ? 1L : 0L;
			} else if (a.equals("y")) {
				y = (y == bi) ? 1L : 0L;
			} else {
				z = (z == bi) ? 1L : 0L;
			}
		}
	}

	public static List<Long> getDuration() {
		A2021Day24 d = new A2021Day24(24);
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

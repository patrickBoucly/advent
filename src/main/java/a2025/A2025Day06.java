package a2025;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import outils.MesOutils;

public class A2025Day06 extends A2025 {
	public A2025Day06(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2025Day06 d = new A2025Day06(6);
		//System.out.println(d.s1(true));
		// System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		// d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		// System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds,
		// run 2 took "
		// + (endTime - startTime) + " milliseconds");
	}

	public Long s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(s -> s.replaceAll("\\s+", " "))
				.map(String::trim).toList();
		Game g = new Game();
		g.initFromInput(inputL);
		return g.solve1();
	}

	public Long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().toList();
		Game g = new Game();
		g.initFromInput2(inputL);
		return g.solve2();
	}
	@Data
	@NoArgsConstructor
	public static class Operation {
		List<Long> nbs;
		String op;
	}

	@Data
	@NoArgsConstructor
	public static class Game {
		List<String> op;
		List<List<Long>> in;
		List<Operation> ops;
		public void initFromInput2(List<String> inputL) {
			ops= new ArrayList<>();
			int jmax=MesOutils.getMaxIntegerFromList(inputL.stream().map(l->l.length()).toList());
			int lastL=inputL.size()-1;
			int j = 0;
			Operation ope=new Operation();
			List<Long> nbs=new ArrayList<>();
			int jmaxInt=0;
			while(j<jmax) {
				String carOp=inputL.get(lastL).substring(j, j+1);
				if(!carOp.equals(" ")) {
					if(carOp.equals("@")) {
						ope.nbs=nbs;
						ops.add(ope);
						break;
					}
					ope=new Operation();
					nbs=new ArrayList<>();
					ope.op=carOp;
					jmaxInt=getJmaxInt(inputL,lastL,j,jmax);
				}
				String buildNb="";
				for(int i=0;i<lastL;i++) {
					buildNb+=inputL.get(i).substring(j, j+1);
				}
				if(!buildNb.equals(" ") && !buildNb.isBlank()) {
					nbs.add(Long.parseLong(buildNb.strip().trim()));
				}
				
				if(j==jmaxInt) {
					ope.nbs=nbs;
					ops.add(ope);
				}
				j++;
			}
			System.out.println(ops);
		}

		private int getJmaxInt(List<String> inputL, int lastL, int j, int jmax) {
			if(j==jmax) {
				return jmax;
			}
			int posNextP=inputL.get(lastL).substring(j+1).indexOf("+");
			int posNextF=inputL.get(lastL).substring(j+1).indexOf("*");
			if(posNextP*posNextF==1) {
				return jmax;
			}
			if(posNextP == -1) {
				return posNextF+j;
			}
			if(posNextF == -1) {
				return posNextP+j;
			}
			if(posNextF < posNextP) {
				return posNextF+j;
			}
			return posNextP+j;
		}

		public void initFromInput(List<String> inputL) {
			in = new ArrayList<>();
			for (int i = 0; i < inputL.size() - 1; i++) {
				List<Long> ligne = Arrays.stream(inputL.get(i).split(" ")).map(Long::valueOf).toList();
				in.add(ligne);
			}
			op = Arrays.stream(inputL.get(inputL.size() - 1).split(" ")).toList();
		}

		public Long solve1() {
			Long r = 0L;
			for (int j = 0; j < in.get(0).size(); j++) {
				Long tmp = 0L;
				if (op.get(j).equals("*")) {
					tmp = 1L;
				}
				for (int i = 0; i < in.size(); i++) {
					tmp = apply(tmp, in.get(i).get(j), op.get(j));
				}
				r += tmp;
			}
			return r;
		}

		public Long solve2() {
			Long r = 0L;
			for(Operation op:ops) {
				Long tmp = 0L;
				if (op.op.equals("*")) {
					tmp = 1L;
				}
				for (int i = 0; i < op.nbs.size(); i++) {
					tmp = apply(tmp, op.nbs.get(i), op.op);
				}
				r += tmp;
			}
			return r;
		}

		public Long apply(Long a, Long b, String op) {
			if (op.equals("*")) {
				return a * b;
			}
			return a + b;
		}
	}

	public static List<Long> getDuration() {
		A2025Day06 d = new A2025Day06(6);
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
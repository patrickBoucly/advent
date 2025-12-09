package a2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import a2019.A2019Day02.Game;
import lombok.Data;
import lombok.NoArgsConstructor;

public class A2019Day05 extends A2019 {

	public A2019Day05(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2019Day05 d = new A2019Day05(5);
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
		List<String> inputL = Arrays.asList(getInput(b).split(",")).stream().map(String::trim).toList();
		Game g = new Game();
		try {
			return g.solve1(inputL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Integer s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split(",")).stream().map(String::trim).toList();
		Game g = new Game();
		try {
			return g.solve2(inputL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Data
	@NoArgsConstructor
	public static class Game {
		public void initFromInput(List<String> inputL) {

		}

		public Integer solve2(List<String> inputL) throws Exception {
			List<Integer> a = new ArrayList<>(inputL.stream().map(Integer::parseInt).toList());
			List<Integer> output=new ArrayList<Integer>();
			int i = 0;
			while (i < a.size()) {
				String opcode = String.format("%05d", a.get(i));
				if (opcode.substring(opcode.length()-2).equals("01")) {
					applyAdd(a, i,opcode);
					i += 4;
				} else if (opcode.substring(opcode.length()-2).equals("02")) {
					applyMult(a, i,opcode);
					i += 4;
				} else if (opcode.substring(opcode.length()-2).equals("03")) {
					applyInput(a, i,5);
					i += 2;
				} else if (opcode.substring(opcode.length()-2).equals("04")) {
					applyOutput(a, i,output,opcode);
					i += 2;
				}else if (opcode.substring(opcode.length()-2).equals("05")) {
					i=applyJumpIfTrue(a, i,output,opcode);
				}else if (opcode.substring(opcode.length()-2).equals("06")) {
					i=applyJumpIfFalse(a, i,output,opcode);
				}else if (opcode.substring(opcode.length()-2).equals("07")) {
					applyLessThan(a, i,output,opcode);
					i += 4;
				}else if (opcode.substring(opcode.length()-2).equals("08")) {
					applyEquals(a, i,output,opcode);
					i += 4;
				}else if (opcode.substring(opcode.length()-2).equals("99")) {
					break;
				} else {
					throw new Exception("opcode incorrect "+opcode);
				}
			}
			return output.getLast();
		}

		private void applyEquals(List<Integer> li, int i, List<Integer> output, String opcode) {
			int valParm1 =opcode.substring(2, 3).equals("0")? li.get(li.get(i + 1)):li.get(i + 1);
			int valParm2 =opcode.substring(1, 2).equals("0")? li.get(li.get(i + 2)):li.get(i + 2);
			int dest = li.get(i + 3);
			if(valParm1==valParm2) {
				li.set(dest,1);
			}else {
				li.set(dest,0);
			}
		}

		private void applyLessThan(List<Integer> li, int i, List<Integer> output, String opcode) {
			int valParm1 =opcode.substring(2, 3).equals("0")? li.get(li.get(i + 1)):li.get(i + 1);
			int valParm2 =opcode.substring(1, 2).equals("0")? li.get(li.get(i + 2)):li.get(i + 2);
			int dest = li.get(i + 3);
			if(valParm1<valParm2) {
				li.set(dest,1);
			}else {
				li.set(dest,0);
			}
			
		}

		private Integer applyJumpIfFalse(List<Integer> li, int i, List<Integer> output, String opcode) {
			int valParm1 =opcode.substring(2, 3).equals("0")? li.get(li.get(i + 1)):li.get(i + 1);
			int valParm2 =opcode.substring(1, 2).equals("0")? li.get(li.get(i + 2)):li.get(i + 2);
			if(valParm1==0) {
				return valParm2;
			}else {
				return i+3;
			}
			
		}

		private Integer applyJumpIfTrue(List<Integer> li, int i, List<Integer> output, String opcode) {
			int valParm1 =opcode.substring(2, 3).equals("0")? li.get(li.get(i + 1)):li.get(i + 1);
			int valParm2 =opcode.substring(1, 2).equals("0")? li.get(li.get(i + 2)):li.get(i + 2);
			if(valParm1!=0) {
				return valParm2;
			}else {
				return i+3;
			}
		}

		public Integer solve1(List<String> inputL) throws Exception {
			List<Integer> a = new ArrayList<>(inputL.stream().map(Integer::parseInt).toList());
			List<Integer> output=new ArrayList<Integer>();
			int i = 0;
			while (i < a.size()) {
				String opcode = String.format("%05d", a.get(i));
				if (opcode.substring(opcode.length()-2).equals("01")) {
					applyAdd(a, i,opcode);
					i += 4;
				} else if (opcode.substring(opcode.length()-2).equals("02")) {
					applyMult(a, i,opcode);
					i += 4;
				} else if (opcode.substring(opcode.length()-2).equals("03")) {
					applyInput(a, i,1);
					i += 2;
				} else if (opcode.substring(opcode.length()-2).equals("04")) {
					applyOutput(a, i,output,opcode);
					i += 2;
				}else if (opcode.substring(opcode.length()-2).equals("99")) {
					break;
				} else {
					throw new Exception("opcode incorrect "+opcode);
				}
			}
			return output.getLast();
		}
		private void applyOutput(List<Integer> li, int i, List<Integer> output, String opcode) {
			int valParm1 =opcode.substring(2, 3).equals("0")? li.get(li.get(i + 1)):li.get(i + 1);
			output.add(valParm1);
		}

		private void applyInput(List<Integer> li, int i, int j) {
			li.set(li.get(i + 1), j);
		}

		private void applyMult(List<Integer> li, int i, String opcode) {
			int valParm1 =opcode.substring(2, 3).equals("0")? li.get(li.get(i + 1)):li.get(i + 1);
			int valParm2 =opcode.substring(1, 2).equals("0")? li.get(li.get(i + 2)):li.get(i + 2);
			int dest = li.get(i + 3);
			li.set(dest, valParm1 * valParm2);
		}


		private void applyAdd(List<Integer> li, int i, String opcode) {
			int valParm1 =opcode.substring(2, 3).equals("0")? li.get(li.get(i + 1)):li.get(i + 1);
			int valParm2 =opcode.substring(1, 2).equals("0")? li.get(li.get(i + 2)):li.get(i + 2);
			int dest = li.get(i + 3);
			li.set(dest, valParm1 + valParm2);
		}

	}


	public static List<Long> getDuration() {
		A2019Day05 d = new A2019Day05(5);
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

package a2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import outils.MesOutils;

public class A2023Day18 extends A2023 {

	public A2023Day18(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day18 d = new A2023Day18(18);
		// System.out.println(d.s1(true));
		// too low
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

	public long s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		TheGame tg = getTheGame(inputL);
		tg.creuserCoin();
		return tg.shoelace() + 1 +tg.compterCubeBord() / 2 ;
	}

	public long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		TheGame tg = getTheGame2(inputL);
		tg.creuserCoin();
	return tg.shoelace() + 1 + tg.compterCubeBord() / 2 ;
	}

	private TheGame getTheGame2(List<String> inputL) {
		TheGame tg = new TheGame();
		List<Instruction> insts = new ArrayList<>();
		List<Cube> cubes = new ArrayList<>();
		cubes.add(new Cube(0L, 0L, "0"));
		tg.setCubes(cubes);
		for (String l : inputL) {
			String[] sp = l.trim().split(" ");
			Instruction ins = new Instruction();
			sp[2] = sp[2].replace("(#", "").replace(")", "");
			int dirNum = Integer.parseInt(sp[2].trim().substring(sp[2].trim().length() - 1));
			if (dirNum == 3) {
				ins.setDir(0);
			}
			if (dirNum == 0) {
				ins.setDir(1);
			}
			if (dirNum == 1) {
				ins.setDir(2);
			}
			if (dirNum == 2) {
				ins.setDir(3);
			}
			ins.setQtt(Integer.parseInt(sp[2].trim().substring(0, sp[2].trim().length() - 1).trim(), 16));
			ins.setRgb("");
			insts.add(ins);
		}
		tg.setInsts(insts);
		return tg;
	}

	private TheGame getTheGame(List<String> inputL) {
		TheGame tg = new TheGame();
		List<Instruction> insts = new ArrayList<>();
		List<Cube> cubes = new ArrayList<>();
		cubes.add(new Cube(0L, 0L, "0"));
		tg.setCubes(cubes);
		for (String l : inputL) {
			String[] sp = l.trim().split(" ");
			Instruction ins = new Instruction();
			if (sp[0].equals("U")) {
				ins.setDir(0);
			}
			if (sp[0].equals("R")) {
				ins.setDir(1);
			}
			if (sp[0].equals("D")) {
				ins.setDir(2);
			}
			if (sp[0].equals("L")) {
				ins.setDir(3);
			}
			ins.setQtt(Integer.parseInt(sp[1].trim()));
			ins.setRgb("");
			insts.add(ins);
		}
		tg.setInsts(insts);
		return tg;
	}

	public static Optional<Cube> getCube(List<Cube> cubes, Long x, Long l) {
		Cube p = null;
		for (Cube i : cubes) {
			if (x == i.x && l == i.y) {
				return Optional.ofNullable(i);
			}
		}
		return Optional.ofNullable(p);
	}

	@Getter
	@Setter
	@AllArgsConstructor

	@NoArgsConstructor
	public static class TheGame {
		public void creuser() {
			Cube curCube = getCube(cubes, 0L, 0L).get();
			for (Instruction ins : insts) {
				curCube.setColor(ins.rgb);
				if (ins.dir == 0) {
					for (int k = 0; k < ins.qtt; k++) {
						Optional<Cube> nextCurCube = getCube(cubes, curCube.x, curCube.y - 1);
						if (nextCurCube.isPresent()) {
							nextCurCube.get().color = ins.rgb;
						} else {
							curCube = new Cube(curCube.x, curCube.y - 1, ins.rgb);
							cubes.add(curCube);
						}
					}
				}
				if (ins.dir == 1) {
					for (int k = 0; k < ins.qtt; k++) {
						Optional<Cube> nextCurCube = getCube(cubes, curCube.x + 1, curCube.y);
						if (nextCurCube.isPresent()) {
							nextCurCube.get().color = ins.rgb;
						} else {
							curCube = new Cube(curCube.x + 1, curCube.y, ins.rgb);
							cubes.add(curCube);
						}
					}
				}
				if (ins.dir == 2) {
					for (int k = 0; k < ins.qtt; k++) {
						Optional<Cube> nextCurCube = getCube(cubes, curCube.x, curCube.y + 1);
						if (nextCurCube.isPresent()) {
							nextCurCube.get().color = ins.rgb;
						} else {
							curCube = new Cube(curCube.x, curCube.y + 1, ins.rgb);
							cubes.add(curCube);
						}
					}
				}
				if (ins.dir == 3) {
					for (int k = 0; k < ins.qtt; k++) {
						Optional<Cube> nextCurCube = getCube(cubes, curCube.x - 1, curCube.y);
						if (nextCurCube.isPresent()) {
							nextCurCube.get().color = ins.rgb;
						} else {
							curCube = new Cube(curCube.x - 1, curCube.y, ins.rgb);
							cubes.add(curCube);
						}
					}
				}

			}

		}

		public Long compterCubeBord() {
			Long res=0L;
		
			for(Instruction in:insts) {
				res+=in.qtt;
				
			}
			return res;
		}

		public void creuserCoin() {
			Cube curCube = getCube(cubes, 0L, 0L).get();
			for (Instruction ins : insts) {
				curCube.setColor(ins.rgb);
				if (ins.dir == 0) {
					curCube=new Cube(curCube.x, curCube.y - ins.qtt, ins.rgb);
					cubes.add(curCube);
				}
				if (ins.dir == 1) {
					curCube=new Cube(curCube.x + ins.qtt, curCube.y, ins.rgb);
					cubes.add(curCube);
				}
				if (ins.dir == 2) {
					curCube=new Cube(curCube.x, curCube.y + ins.qtt, ins.rgb);
					cubes.add(curCube);
				}
				if (ins.dir == 3) {
					curCube=new Cube(curCube.x - ins.qtt, curCube.y, ins.rgb);
					cubes.add(curCube);
				}
			}
		}

		public long shoelace() {
			Long sum = 0L;
			for (int i = 0; i < cubes.size() - 1; i++) {
				sum += (cubes.get(i + 1).x + cubes.get(i).x) * (cubes.get(i + 1).y - cubes.get(i).y);
			}
			return sum / 2;
		}

		public Long compterS1() {
			Long res = 0L;
			Long jmax = MesOutils.getMaxLongFromList(cubes.stream().map(Cube::getY).collect(Collectors.toList()));
			Long jmin = MesOutils.getMinLongFromList(cubes.stream().map(Cube::getY).collect(Collectors.toList()));

			for (Long j = jmin; j <= jmax; j++) {
				Long imax = maxAbsLigneJ(j);
				Long imin = minAbsLigneJ(j);
				res += imax - imin + 1;
			}
			return res;
		}

		public void creuserInside() {
			Long jmax = MesOutils.getMaxLongFromList(cubes.stream().map(Cube::getY).collect(Collectors.toList()));
			Long jmin = MesOutils.getMinLongFromList(cubes.stream().map(Cube::getY).collect(Collectors.toList()));

			for (Long j = jmin; j <= jmax; j++) {
				Long imin = minAbsLigneJ(j);
				Long imax = maxAbsLigneJ(j);
				for (Long i = imin; i <= imax; i++) {
					Optional<Cube> c = getCube(cubes, i, j);
					if (c.isEmpty()) {
						cubes.add(new Cube(i, j, "int"));
					}
				}
			}
		}

		private Long minAbsLigneJ(Long j) {
			return MesOutils.getMinLongFromList(
					cubes.stream().filter(cu -> cu.y == j).map(Cube::getX).collect(Collectors.toList()));

		}

		private Long maxAbsLigneJ(Long j) {
			return MesOutils.getMaxLongFromList(
					cubes.stream().filter(cu -> cu.y == j).map(Cube::getX).collect(Collectors.toList()));
		}

		List<Instruction> insts;
		List<Cube> cubes;

		@Override

		public String toString() {
			StringBuilder res = new StringBuilder();
			Long imax = MesOutils.getMaxLongFromList(cubes.stream().map(Cube::getX).collect(Collectors.toList()));
			Long jmax = MesOutils.getMaxLongFromList(cubes.stream().map(Cube::getY).collect(Collectors.toList()));
			for (Long j = 0L; j <= jmax; j++) {
				for (Long i = 0L; i <= imax; i++) {
					getCube(cubes, i, j).ifPresentOrElse(pt -> res.append("#"), () -> res.append("."));
				}
				res.append("\n");
			}
			return res.toString();
		}

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Instruction {
		int dir;
		int qtt;
		String rgb;

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Cube {
		Long x;
		Long y;
		String color;

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Cube other = (Cube) obj;
			return x == other.x && y == other.y;
		}

	}

	public static List<Long> getDuration() {
		A2023Day18 d = new A2023Day18(18);
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

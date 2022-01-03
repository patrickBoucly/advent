package aocmaven.a2021;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class A2021Day22 extends A2021 {

	public A2021Day22(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day22 d = new A2021Day22(22);
		long startTime = System.currentTimeMillis();
		// System.out.println(d.s1(false));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	private long s1(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Instruction> instructions = new ArrayList<>();
		for (String l : lignes) {
			String[] sp1 = l.split(" ");
			String[] sp2 = sp1[1].split(",");
			boolean ins;
			if (sp1[0].equals("on")) {
				ins = true;
			} else {
				ins = false;
			}
			int xmin = 0;
			int xmax = 0;
			int ymin = 0;
			int ymax = 0;
			int zmin = 0;
			int zmax = 0;
			String v = sp2[0];
			int pos = 2;
			while (!v.substring(pos, pos + 1).equals(".")) {
				xmin = Integer.parseInt(v.substring(pos, pos + 1));
				pos++;
			}
			pos += 2;
			for (int j = pos; j < v.length(); j++) {
				xmax = Integer.parseInt(v.substring(pos, pos + 1));
				pos++;
			}
			v = sp2[1];
			pos = 2;
			while (!v.substring(pos, pos + 1).equals(".")) {
				ymin = Integer.parseInt(v.substring(pos, pos + 1));
				pos++;
			}
			pos += 2;
			for (int j = pos; j < v.length(); j++) {
				ymax = Integer.parseInt(v.substring(pos, pos + 1));
				pos++;
			}
			v = sp2[2];
			pos = 2;
			while (!v.substring(pos, pos + 1).equals(".")) {
				zmin = Integer.parseInt(v.substring(pos, pos + 1));
				pos++;
			}
			pos += 2;
			for (int j = pos; j < v.length(); j++) {
				zmax = Integer.parseInt(v.substring(pos, pos + 1));
				pos++;
			}
			instructions.add(new Instruction(ins, xmin, xmax, ymin, ymax, zmin, zmax));
		}
		Set<Point> cube = new HashSet<>();
		for (int i = -50; i <= 50; i++) {
			for (int j = -50; j <= 50; j++) {
				for (int k = -50; k <= 50; k++) {
					cube.add(new Point(i, j, k, false));
				}
			}
		}
		int cpt = 0;
		System.out.println(instructions.size());
		for (Instruction ins : instructions) {
			System.out.println(cpt);
			cube = applyIns1(cube, ins);
			cpt++;
		}
		System.out.println("s1 res : " + cube.stream().filter(Point::isOn).count());
		return cube.stream().filter(Point::isOn).count();
	}

	private Set<Point> applyIns1(Set<Point> cube, Instruction ins) {
		if (!ins.isOn) {
			cube.removeIf(p -> ins.contient(p));
		} else {
			cube.removeIf(p -> ins.contient(p) && !p.isOn);
			cube.addAll(ins.pointsConcernes1());
		}
		return cube;
	}

	private Set<Point> applyIns2(Set<Point> cube, Instruction ins) {
		if (!ins.isOn) {
			cube.removeIf(p -> ins.contient(p));
		} else {
			cube.removeIf(p -> ins.contient(p) && !p.isOn);
			cube.addAll(ins.pointsConcernes2());
		}
		return cube;
	}

	private BigInteger s2(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Instruction> instructions = new ArrayList<>();
		Set<Integer> lx = new HashSet<>();
		Set<Integer> ly = new HashSet<>();
		Set<Integer> lz = new HashSet<>();
		for (String l : lignes) {
			String[] sp1 = l.split(" ");
			String[] sp2 = sp1[1].split(",");
			boolean ins;
			if (sp1[0].equals("on")) {
				ins = true;
			} else {
				ins = false;
			}
			String xmin = "";
			String xmax = "";
			String ymin = "";
			String ymax = "";
			String zmin = "";
			String zmax = "";
			String v = sp2[0];
			int pos = 2;
			while (!v.substring(pos, pos + 1).equals(".")) {
				xmin += v.substring(pos, pos + 1);
				pos++;
			}
			lx.add(Integer.parseInt(xmin));
			pos += 2;
			for (int j = pos; j < v.length(); j++) {
				xmax += v.substring(pos, pos + 1);
				pos++;
			}

			lx.add(Integer.parseInt(xmax));
			v = sp2[1];
			pos = 2;
			while (!v.substring(pos, pos + 1).equals(".")) {
				ymin += v.substring(pos, pos + 1);
				pos++;
			}
			ly.add(Integer.parseInt(ymin));
			pos += 2;
			for (int j = pos; j < v.length(); j++) {
				ymax += v.substring(pos, pos + 1);
				pos++;
			}
			ly.add(Integer.parseInt(ymax));
			v = sp2[2];
			pos = 2;
			while (!v.substring(pos, pos + 1).equals(".")) {
				zmin += v.substring(pos, pos + 1);
				pos++;
			}
			lz.add(Integer.parseInt(zmin));
			pos += 2;
			for (int j = pos; j < v.length(); j++) {
				zmax += v.substring(pos, pos + 1);
				pos++;
			}
			lz.add(Integer.parseInt(zmax));
			instructions.add(new Instruction(ins, Integer.parseInt(xmin), Integer.parseInt(xmax),
					Integer.parseInt(ymin), Integer.parseInt(ymax), Integer.parseInt(zmin), Integer.parseInt(zmax)));
		}
		List<Integer> llx = new ArrayList<>(lx);
		List<Integer> lly = new ArrayList<>(ly);
		List<Integer> llz = new ArrayList<>(lz);
		// llx.addAll(new ArrayList<>(lx));
		// lly.addAll(new ArrayList<>(ly));
		// llz.addAll(new ArrayList<>(lz));
		Collections.sort(llx);
		Collections.sort(lly);
		Collections.sort(llz);
		llx.add(Integer.MAX_VALUE);
		lly.add(Integer.MAX_VALUE);
		llz.add(Integer.MAX_VALUE);
		llx.add(Integer.MIN_VALUE);
		lly.add(Integer.MIN_VALUE);
		llz.add(Integer.MIN_VALUE);
		lx = null;
		ly = null;
		lz = null;
		Collections.sort(llx);
		Collections.sort(lly);
		Collections.sort(llz);
		List<Integer> lcubes = new ArrayList<>();

		for (int i = 0; i < llx.size() - 1; i++) {
			for (int j = 0; j < lly.size() - 1; j++) {
				for (int k = 0; k < llz.size() - 1; k++) {
					System.out.println(lcubes.size());
					Cube c = new Cube(llx.get(i) + 1, llx.get(i + 1) - 1, lly.get(j) + 1, lly.get(j + 1) - 1,
							llz.get(k) + 1, llz.get(k + 1) - 1);
					if (c.bienForme()) {
						lcubes.addAll(Arrays.asList(c.xmin,c.xmax,c.ymin,c.ymax,c.zmin,c.zmax));
					}
					//cubes.add(new Cube(llx.get(i), llx.get(i), lly.get(j), lly.get(j), llz.get(k), llz.get(k)));
					c=new Cube(llx.get(i), llx.get(i), lly.get(j), lly.get(j), llz.get(k), llz.get(k));
						lcubes.addAll(Arrays.asList(c.xmin,c.xmax,c.ymin,c.ymax,c.zmin,c.zmax));
					c = new Cube(llx.get(i) + 1, llx.get(i + 1) - 1, lly.get(j), lly.get(j), llz.get(k), llz.get(k));
					if (c.bienForme()) {
						lcubes.addAll(Arrays.asList(c.xmin,c.xmax,c.ymin,c.ymax,c.zmin,c.zmax));
					}
					c = new Cube(llx.get(i), llx.get(i), lly.get(j) + 1, lly.get(j + 1) - 1, llz.get(k), llz.get(k));

					if (c.bienForme()) {
						lcubes.addAll(Arrays.asList(c.xmin,c.xmax,c.ymin,c.ymax,c.zmin,c.zmax));
					}
					c = new Cube(llx.get(i), llx.get(i), lly.get(j), lly.get(j), llz.get(k) + 1, llz.get(k + 1) - 1);

					if (c.bienForme()) {
						lcubes.addAll(Arrays.asList(c.xmin,c.xmax,c.ymin,c.ymax,c.zmin,c.zmax));
					}

					c = new Cube(llx.get(i) + 1, llx.get(i + 1) - 1, lly.get(j) + 1, lly.get(j + 1) - 1, llz.get(k),
							llz.get(k));
					if (c.bienForme()) {
						lcubes.addAll(Arrays.asList(c.xmin,c.xmax,c.ymin,c.ymax,c.zmin,c.zmax));
					}
					c = new Cube(llx.get(i) + 1, llx.get(i + 1) - 1, lly.get(j), lly.get(j), llz.get(k) + 1,
							llz.get(k + 1) - 1);
					if (c.bienForme()) {
						lcubes.addAll(Arrays.asList(c.xmin,c.xmax,c.ymin,c.ymax,c.zmin,c.zmax));
					}
					c = new Cube(llx.get(i), llx.get(i), lly.get(j) + 1, lly.get(j + 1) - 1, llz.get(k) + 1,
							llz.get(k + 1) - 1);
					if (c.bienForme()) {
						lcubes.addAll(Arrays.asList(c.xmin,c.xmax,c.ymin,c.ymax,c.zmin,c.zmax));
					}
				}
			}
		}
		List<Cube> cubes = new ArrayList<>();
		for(int i=0;i<lcubes.size();i=i+6) {
			cubes.add(new Cube(lcubes.get(i),lcubes.get(i+1),lcubes.get(i+2),lcubes.get(i+3),lcubes.get(i+4),lcubes.get(i+5)));
		}
		int cpt = 0;
		//List<Cube> cubes = new ArrayList<>(cubes);
		cubes.sort(Comparator.comparingInt(Cube::getXmin).thenComparing(Comparator.comparing(Cube::getXmax))
				.thenComparing(Comparator.comparing(Cube::getYmin)).thenComparing(Comparator.comparing(Cube::getYmax))
				.thenComparing(Comparator.comparing(Cube::getZmin)).thenComparing(Comparator.comparing(Cube::getZmax)));

		/*
		 * for (int i = -20; i <= 26; i++) { for (int j = -36; j <= 17; j++) { for (int
		 * k = -47; k <= 7; k++) { Point p = new Point(i, j, k, false); if
		 * (cubes.stream().allMatch(cu -> !cu.contient(p))) { System.out.println(p); }
		 * 
		 * } } }
		 */

		for (Instruction ins : instructions) {

			for (Cube cu : cubes) {
				// if (!(cu.xmin < -50 || cu.xmax > 50 || cu.ymin < -50 || cu.ymax > 50 ||
				// cu.zmin < -50 || cu.zmax > 50)) {
				// System.out.println("ins : " + ins);
				if (ins.contient(cu)) {
					cu.setOn(ins.isOn);
					// System.out.println("oui : " + cu);
				} else {
					// System.out.println("non : " + cu);
				}
			}
			// }
			cpt++;
			// System.out.println(cpt + " : " + ins);
			// System.out.println("nb on : " + cubes.stream().filter(cu ->
			// cu.isOn).mapToLong(Cube::getNbCube).reduce(0L, (c1, c2) -> c1 + c2));
			System.out.println(cpt + " : " + ins);
		}

		BigInteger res = BigInteger.ZERO;
		for (Cube cu : cubes.stream().filter(cu -> cu.isOn).collect(Collectors.toList())) {
			res = res.add(cu.getnbCube());
		}
		return res;
	}

	public static class Cube {
		int xmin;
		int xmax;
		int ymin;
		int ymax;
		int zmin;
		int zmax;
		BigInteger nbCube;
		boolean isOn;

		public BigInteger getNbCube() {
			return nbCube;
		}

		public boolean bienForme() {
			if (xmax < xmin || ymax < ymin || zmax < zmin) {
				return false;
			}
			return true;
		}

		public BigInteger getnbCube() {
			BigInteger a = new BigInteger(String.valueOf(xmax - xmin + 1));
			BigInteger b = new BigInteger(String.valueOf(ymax - ymin + 1));
			BigInteger c = new BigInteger(String.valueOf(zmax - zmin + 1));
			return a.multiply(b).multiply(c);

		}

		public boolean contient(Point p) {
			if (p.x <= xmax && p.y <= ymax && p.z <= zmax && p.x >= xmin && p.y >= ymin && p.z >= zmin) {
				return true;
			}
			return false;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (isOn ? 1231 : 1237);
			result = prime * result + xmax;
			result = prime * result + xmin;
			result = prime * result + ymax;
			result = prime * result + ymin;
			result = prime * result + zmax;
			result = prime * result + zmin;
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
			Cube other = (Cube) obj;
			if (isOn != other.isOn)
				return false;
			if (xmax != other.xmax)
				return false;
			if (xmin != other.xmin)
				return false;
			if (ymax != other.ymax)
				return false;
			if (ymin != other.ymin)
				return false;
			if (zmax != other.zmax)
				return false;
			if (zmin != other.zmin)
				return false;
			return true;
		}

		public void setNbCube(BigInteger nbCube) {
			this.nbCube = nbCube;
		}

		public boolean isOn() {
			return isOn;
		}

		public void setOn(boolean isOn) {
			this.isOn = isOn;
		}

		public Cube(int xmin, int xmax, int ymin, int ymax, int zmin, int zmax) {
			super();
			this.xmin = xmin;
			this.xmax = xmax;
			this.ymin = ymin;
			this.ymax = ymax;
			this.zmin = zmin;
			this.zmax = zmax;
			this.nbCube = BigInteger.ZERO;
			this.isOn = false;
		}

		@Override
		public String toString() {
			return "Cube [xmin=" + xmin + ", xmax=" + xmax + ", ymin=" + ymin + ", ymax=" + ymax + ", zmin=" + zmin
					+ ", zmax=" + zmax + ", nbCube=" + nbCube + ", isOn=" + isOn + "]";
		}

		public int getXmin() {
			return xmin;
		}

		public void setXmin(int xmin) {
			this.xmin = xmin;
		}

		public int getXmax() {
			return xmax;
		}

		public void setXmax(int xmax) {
			this.xmax = xmax;
		}

		public int getYmin() {
			return ymin;
		}

		public void setYmin(int ymin) {
			this.ymin = ymin;
		}

		public int getYmax() {
			return ymax;
		}

		public void setYmax(int ymax) {
			this.ymax = ymax;
		}

		public int getZmin() {
			return zmin;
		}

		public void setZmin(int zmin) {
			this.zmin = zmin;
		}

		public int getZmax() {
			return zmax;
		}

		public void setZmax(int zmax) {
			this.zmax = zmax;
		}

	}

	public static class Point {
		int x;
		int y;
		int z;
		boolean isOn;

		public Point(int x, int y, int z, boolean isOn) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
			this.isOn = isOn;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y, z);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			return x == other.x && y == other.y && z == other.z;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getZ() {
			return z;
		}

		public void setZ(int z) {
			this.z = z;
		}

		public boolean isOn() {
			return isOn;
		}

		public void setOn(boolean isOn) {
			this.isOn = isOn;
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + ", z=" + z + ", isOn=" + isOn + "]";
		}

	}

	public static class Instruction {
		boolean isOn;
		int xmin;
		int xmax;
		int ymin;
		int ymax;
		int zmin;
		int zmax;

		public boolean getIsOn() {
			return isOn;
		}

		public boolean contient(Cube cu) {
			if (cu.xmax <= xmax && cu.ymax <= ymax && cu.zmax <= zmax && cu.xmin >= xmin && cu.ymin >= ymin
					&& cu.zmin >= zmin) {
				return true;
			}
			return false;
		}

		public Set<Point> pointsConcernes1() {
			Set<Point> cube = new HashSet<>();
			if (xmin < -50 || xmax > 50 || ymin < -50 || ymax > 50 || zmin < -50 || zmax > 50) {

			} else {
				for (int i = xmin; i <= xmax; i++) {
					for (int j = ymin; j <= ymax; j++) {
						for (int k = zmin; k <= zmax; k++) {
							cube.add(new Point(i, j, k, true));
						}
					}
				}
			}
			return cube;
		}

		public Set<Point> pointsConcernes2() {
			Set<Point> cube = new HashSet<>();

			for (int i = xmin; i <= xmax; i++) {
				for (int j = ymin; j <= ymax; j++) {
					for (int k = zmin; k <= zmax; k++) {
						cube.add(new Point(i, j, k, true));
					}
				}
			}

			return cube;
		}

		public boolean contient(Point p) {
			if (p.x >= xmin && p.x <= xmax && p.y >= ymin && p.y <= ymax && p.z >= zmin && p.z <= zmax) {
				return true;
			}
			return false;
		}

		@Override
		public String toString() {
			return isOn + ", x=" + xmin + ".." + xmax + ", y=" + ymin + ".." + ymax + ", z=" + zmin + ".." + zmax;
		}

		public void setIsOn(boolean isOn) {
			this.isOn = isOn;
		}

		public int getXmin() {
			return xmin;
		}

		public void setXmin(int xmin) {
			this.xmin = xmin;
		}

		public int getXmax() {
			return xmax;
		}

		public void setXmax(int xmax) {
			this.xmax = xmax;
		}

		public int getYmin() {
			return ymin;
		}

		public void setYmin(int ymin) {
			this.ymin = ymin;
		}

		public int getYmax() {
			return ymax;
		}

		public void setYmax(int ymax) {
			this.ymax = ymax;
		}

		public int getZmin() {
			return zmin;
		}

		public void setZmin(int zmin) {
			this.zmin = zmin;
		}

		public int getZmax() {
			return zmax;
		}

		public void setZmax(int zmax) {
			this.zmax = zmax;
		}

		public Instruction(boolean isOn, int xmin, int xmax, int ymin, int ymax, int zmin, int zmax) {
			super();
			this.isOn = isOn;
			this.xmin = xmin;
			this.xmax = xmax;
			this.ymin = ymin;
			this.ymax = ymax;
			this.zmin = zmin;
			this.zmax = zmax;
		}

	}

	public static List<Long> getDuration() {
		A2021Day22 d = new A2021Day22(22);
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

package aocmaven.a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
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
		//System.out.println(d.s1(true));
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
			pos += 2;
			for (int j = pos; j < v.length(); j++) {
				xmax += v.substring(pos, pos + 1);
				pos++;
			}
			v = sp2[1];
			pos = 2;
			while (!v.substring(pos, pos + 1).equals(".")) {
				ymin += v.substring(pos, pos + 1);
				pos++;
			}
			pos += 2;
			for (int j = pos; j < v.length(); j++) {
				ymax += v.substring(pos, pos + 1);
				pos++;
			}
			v = sp2[2];
			pos = 2;
			while (!v.substring(pos, pos + 1).equals(".")) {
				zmin += v.substring(pos, pos + 1);
				pos++;
			}
			pos += 2;
			for (int j = pos; j < v.length(); j++) {
				zmax += v.substring(pos, pos + 1);
				pos++;
			}
			instructions.add(new Instruction(ins, Integer.parseInt(xmin), Integer.parseInt(xmax),
					Integer.parseInt(ymin), Integer.parseInt(ymax), Integer.parseInt(zmin), Integer.parseInt(zmax)));
		}
		Set<Point> cube = new HashSet<A2021Day22.Point>();
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


	private long s2(boolean b) {
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
			pos += 2;
			for (int j = pos; j < v.length(); j++) {
				xmax += v.substring(pos, pos + 1);
				pos++;
			}
			v = sp2[1];
			pos = 2;
			while (!v.substring(pos, pos + 1).equals(".")) {
				ymin += v.substring(pos, pos + 1);
				pos++;
			}
			pos += 2;
			for (int j = pos; j < v.length(); j++) {
				ymax += v.substring(pos, pos + 1);
				pos++;
			}
			v = sp2[2];
			pos = 2;
			while (!v.substring(pos, pos + 1).equals(".")) {
				zmin += v.substring(pos, pos + 1);
				pos++;
			}
			pos += 2;
			for (int j = pos; j < v.length(); j++) {
				zmax += v.substring(pos, pos + 1);
				pos++;
			}
			instructions.add(new Instruction(ins, Integer.parseInt(xmin), Integer.parseInt(xmax),
					Integer.parseInt(ymin), Integer.parseInt(ymax), Integer.parseInt(zmin), Integer.parseInt(zmax)));
		}
		Set<Point> cube = new HashSet<A2021Day22.Point>();
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
			cube = applyIns2(cube, ins);
			cpt++;
		}
		System.out.println("s1 res : " + cube.stream().filter(Point::isOn).count());
		return cube.stream().filter(Point::isOn).count();
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

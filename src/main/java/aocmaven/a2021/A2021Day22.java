package aocmaven.a2021;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
		System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		//System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	private BigInteger s1(boolean b) {
		return s(b, 1);
	}

	private BigInteger s2(boolean b) {
		return s(b, 2);
	}

	private BigInteger s(boolean b, Integer partie) {
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
		Collections.sort(llx);
		Collections.sort(lly);
		Collections.sort(llz);
		llx.add(llx.get(llx.size() - 1) + 2);
		lly.add(lly.get(lly.size() - 1) + 2);
		llz.add(llz.get(llz.size() - 1) + 2);
		llx.add(llx.get(0) - 2);
		lly.add(lly.get(0) - 2);
		llz.add(llz.get(0) - 2);
		Collections.sort(llx);
		Collections.sort(lly);
		Collections.sort(llz);
		Cube c1 = new Cube(-50, 50, -50, 50, -50, 50);
		Cube cmax = new Cube(llx.get(0), llx.get(llx.size() - 1), lly.get(0), lly.get(lly.size() - 1), llz.get(0),
				llz.get(llz.size() - 1));
		List<Cube> zones = getZones(cmax);
		BigInteger res = BigInteger.ZERO;
		for (Cube zone : zones) {
			res = res.add(calculerOn(zone, instructions, llx, lly, llz, c1, partie));
			System.out.println("sous-total : " + res);
		}
		res = res.add(calculerOnHZ(zones, instructions, llx, lly, llz, c1, partie));

		return res;
	}

	private BigInteger calculerOnHZ(List<Cube> zones, List<Instruction> instructions, List<Integer> llx,
			List<Integer> lly, List<Integer> llz, Cube c1, Integer partie) {
		if(partie ==1) {
			llx=llx.stream().filter(i->i>=-1000 && i<=1000).collect(Collectors.toList());
			lly=lly.stream().filter(i->i>=-1000 && i<=1000).collect(Collectors.toList());
			llz=llz.stream().filter(i->i>=-1000 && i<=1000).collect(Collectors.toList());
		}
		List<Cube> cubes = new ArrayList<>();
		for (int i = 0; i < llx.size() - 1; i++) {
			for (int j = 0; j < lly.size() - 1; j++) {
				for (int k = 0; k < llz.size() - 1; k++) {
					if (inclusDsAucuneZone(llx.get(i), llx.get(i + 1), lly.get(j), lly.get(j + 1), llz.get(k),
							llz.get(k + 1), zones)) {
						Cube c = new Cube(llx.get(i) + 1, llx.get(i + 1) - 1, lly.get(j) + 1, lly.get(j + 1) - 1,
								llz.get(k) + 1, llz.get(k + 1) - 1);
						if (c.bienForme()) {
							cubes.add(c);
						}
						cubes.add(new Cube(llx.get(i), llx.get(i), lly.get(j), lly.get(j), llz.get(k), llz.get(k)));

						c = new Cube(llx.get(i) + 1, llx.get(i + 1) - 1, lly.get(j), lly.get(j), llz.get(k),
								llz.get(k));
						if (c.bienForme()) {
							cubes.add(c);
						}
						c = new Cube(llx.get(i), llx.get(i), lly.get(j) + 1, lly.get(j + 1) - 1, llz.get(k),
								llz.get(k));

						if (c.bienForme()) {
							cubes.add(c);
						}
						c = new Cube(llx.get(i), llx.get(i), lly.get(j), lly.get(j), llz.get(k) + 1,
								llz.get(k + 1) - 1);

						if (c.bienForme()) {
							cubes.add(c);
						}

						c = new Cube(llx.get(i) + 1, llx.get(i + 1) - 1, lly.get(j) + 1, lly.get(j + 1) - 1, llz.get(k),
								llz.get(k));
						if (c.bienForme()) {
							cubes.add(c);
						}
						c = new Cube(llx.get(i) + 1, llx.get(i + 1) - 1, lly.get(j), lly.get(j), llz.get(k) + 1,
								llz.get(k + 1) - 1);
						if (c.bienForme()) {
							cubes.add(c);
						}
						c = new Cube(llx.get(i), llx.get(i), lly.get(j) + 1, lly.get(j + 1) - 1, llz.get(k) + 1,
								llz.get(k + 1) - 1);
						if (c.bienForme()) {
							cubes.add(c);
						}
					}
				}
			}
		}
		for (Instruction ins : instructions) {
			for (Cube cu : cubes) {
				if (ins.contient(cu)) {
					if (partie ==1 && c1.contient(cu)) {
						cu.setOn(ins.isOn);
					} else {
						cu.setOn(ins.isOn);
					}
				}
			}
		}
		BigInteger res = BigInteger.ZERO;
		for (Cube cu : cubes.stream().filter(cu -> cu.isOn).collect(Collectors.toList())) {
			res = res.add(cu.getnbCube());
		}
		return res;
	}

	private boolean inclusDsAucuneZone(Integer xi, Integer xa, Integer yi, Integer ya, Integer zi, Integer za,
			List<Cube> zones) {
		for (Cube zone : zones) {
			if (xi >= zone.xmin && xa <= zone.xmax && yi >= zone.ymin && ya <= zone.ymax && zi >= zone.zmin
					&& za <= zone.zmax) {
				return false;
			}
		}
		return true;
	}

	private List<Cube> getZones(Cube c) {
		List<Cube> zones = new ArrayList<A2021Day22.Cube>();
		zones.add(new Cube(100001, c.xmax, c.ymin, c.ymax, c.zmin, c.zmax, ">100000"));
		int pas = 10000;
		int k = 0;
		int lastValue = 0;
		while (100001 - k * pas > c.xmin + pas) {
			k++;
			Cube zone = new Cube(100001 - k * pas, 100000 - (k - 1) * pas, c.ymin, c.ymax, c.zmin, c.zmax,
					String.valueOf(100000 - k * pas) + ":" + String.valueOf(100000 - (k - 1) * pas));
			zones.add(zone);
			lastValue = 100001 - k * pas;
		}
		zones.add(new Cube(c.xmin, lastValue - 1, c.ymin, c.ymax, c.zmin, c.zmax, "<" + lastValue));
		return splitZ(splitY(zones));
	}

	private List<Cube> splitZ(List<Cube> zones) {
		List<Cube> zonesSpZ = new ArrayList<A2021Day22.Cube>();
		for (Cube zone : zones) {
			zonesSpZ.add(new Cube(zone.xmin, zone.xmax, zone.ymin, zone.ymax, zone.zmin, -1, zone.nom + ",y<0"));
			zonesSpZ.add(new Cube(zone.xmin, zone.xmax, zone.ymin, zone.ymax, 0, zone.zmax, zone.nom + ",y>=0"));
		}
		return zonesSpZ;
	}

	private List<Cube> splitY(List<Cube> zones) {
		List<Cube> zonesSpY = new ArrayList<A2021Day22.Cube>();
		for (Cube zone : zones) {
			zonesSpY.add(new Cube(zone.xmin, zone.xmax, zone.ymin, -1, zone.zmin, zone.zmax, zone.nom + ",z<0"));
			zonesSpY.add(new Cube(zone.xmin, zone.xmax, 0, zone.ymax, zone.zmin, zone.zmax, zone.nom + ",z>=0"));
		}
		return zonesSpY;
	}

	private BigInteger calculerOn(Cube zone, List<Instruction> instructions, List<Integer> llx, List<Integer> lly,
			List<Integer> llz, Cube c1, Integer partie) {
		List<Cube> cubes = new ArrayList<>();
		if(partie ==1) {
			llx=llx.stream().filter(i->i>=-1000 && i<=1000).collect(Collectors.toList());
			lly=lly.stream().filter(i->i>=-1000 && i<=1000).collect(Collectors.toList());
			llz=llz.stream().filter(i->i>=-1000 && i<=1000).collect(Collectors.toList());
		}
		for (int i = 0; i < llx.size() - 1; i++) {
			for (int j = 0; j < lly.size() - 1; j++) {
				for (int k = 0; k < llz.size() - 1; k++) {
					if (llx.get(i) >= zone.xmin && llx.get(i + 1) <= zone.xmax && lly.get(j) >= zone.ymin
							&& lly.get(j + 1) <= zone.ymax && llz.get(k) >= zone.zmin && llz.get(k + 1) <= zone.zmax) {
						Cube c = new Cube(llx.get(i) + 1, llx.get(i + 1) - 1, lly.get(j) + 1, lly.get(j + 1) - 1,
								llz.get(k) + 1, llz.get(k + 1) - 1);
						if (c.bienForme()) {
							cubes.add(c);
						}
						cubes.add(new Cube(llx.get(i), llx.get(i), lly.get(j), lly.get(j), llz.get(k), llz.get(k)));

						c = new Cube(llx.get(i) + 1, llx.get(i + 1) - 1, lly.get(j), lly.get(j), llz.get(k),
								llz.get(k));
						if (c.bienForme()) {
							cubes.add(c);
						}
						c = new Cube(llx.get(i), llx.get(i), lly.get(j) + 1, lly.get(j + 1) - 1, llz.get(k),
								llz.get(k));

						if (c.bienForme()) {
							cubes.add(c);
						}
						c = new Cube(llx.get(i), llx.get(i), lly.get(j), lly.get(j), llz.get(k) + 1,
								llz.get(k + 1) - 1);

						if (c.bienForme()) {
							cubes.add(c);
						}

						c = new Cube(llx.get(i) + 1, llx.get(i + 1) - 1, lly.get(j) + 1, lly.get(j + 1) - 1, llz.get(k),
								llz.get(k));
						if (c.bienForme()) {
							cubes.add(c);
						}
						c = new Cube(llx.get(i) + 1, llx.get(i + 1) - 1, lly.get(j), lly.get(j), llz.get(k) + 1,
								llz.get(k + 1) - 1);
						if (c.bienForme()) {
							cubes.add(c);
						}
						c = new Cube(llx.get(i), llx.get(i), lly.get(j) + 1, lly.get(j + 1) - 1, llz.get(k) + 1,
								llz.get(k + 1) - 1);
						if (c.bienForme()) {
							cubes.add(c);
						}
					}
				}
			}
		}
		System.out.println("nb cubes dans la zone " + zone.nom + " : " + cubes.size());
		if (cubes.size() > 0) {
			for (Instruction ins : instructions) {
				for (Cube cu : cubes) {
					if (ins.contient(cu)) {
						if (partie ==1 && c1.contient(cu)) {
							cu.setOn(ins.isOn);
						} else {
							cu.setOn(ins.isOn);
						}
					}
				}
			}
			BigInteger res = BigInteger.ZERO;
			for (Cube cu : cubes.stream().filter(cu -> cu.isOn).collect(Collectors.toList())) {
				res = res.add(cu.getnbCube());
			}
			return res;
		} else {
			return BigInteger.ZERO;
		}
	}

	public static class Cube {
		int xmin;
		int xmax;
		int ymin;
		int ymax;
		int zmin;
		int zmax;
		BigInteger nbCubeDeduce;
		String nom;
		BigInteger nbCube;
		boolean isOn;

		public String getNom() {
			return nom;
		}

		public boolean contient(Cube cu) {
			if(xmin<=cu.xmin && xmax>=cu.xmax && ymin<=cu.ymin && ymax>=cu.ymax && zmin<=cu.zmin && zmax>=cu.zmax ) {
				return true;
			}
			return false;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public BigInteger getNbCubeDeduce() {
			return nbCubeDeduce;
		}

		public void setNbCubeDeduce(BigInteger nbCubeDeduce) {
			this.nbCubeDeduce = nbCubeDeduce;
		}

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

		public Cube(Instruction ins) {
			super();
			this.xmin = ins.xmin;
			this.xmax = ins.xmax;
			this.ymin = ins.ymin;
			this.ymax = ins.ymax;
			this.zmin = ins.zmin;
			this.zmax = ins.zmax;
			this.nbCube = BigInteger.ZERO;
			this.isOn = false;
		}

		public Cube(int xmin, int xmax, int ymin, int ymax, int zmin, int zmax, String nom) {
			super();
			this.xmin = xmin;
			this.xmax = xmax;
			this.ymin = ymin;
			this.ymax = ymax;
			this.zmin = zmin;
			this.zmax = zmax;
			this.nbCube = BigInteger.ZERO;
			this.nom = nom;
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
		int type;

		public Point(int x, int y, int z, int type) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
			this.type = type;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
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

		public boolean contient2(Cube cu) {
			if (cu.xmax < xmax && cu.ymax < ymax && cu.zmax < zmax && cu.xmin >= xmin && cu.ymin >= ymin
					&& cu.zmin >= zmin) {
				return true;
			}
			return false;
		}

		public boolean contientBase(Cube cu) {
			return contient(new Point(cu.xmin, cu.ymin, cu.zmin, 0));
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

package aocmaven.a2021;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
		System.out.println(d.s2(false));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	private long s1(boolean b) {
		return 0L;
	}

	private BigInteger s2s3(boolean b) {
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
		Cube bigCube = new Cube(llx.get(0), llx.get(llx.size() - 1), lly.get(0), lly.get(lly.size() - 1), llz.get(0),
				llz.get(llz.size() - 1));

		Set<Cube> cubes = new HashSet<>();
		cubes.add(bigCube);
		for (Instruction ins : instructions) {
			Cube cubeIns = new Cube(ins);
			cubes.add(cubeIns);
			boolean splitrestant = false;
			boolean start = true;
			while (start || splitrestant) {
				Set<Cube> newCubes = new HashSet<>();
				splitrestant = false;
				start = false;
				for (Cube c1 : cubes) {
					for (Cube c2 : cubes) {
						if (!c1.equals(c2)) {
							if (c1.seTouche(c2)) {
								newCubes.addAll(c1.split(c2));
								splitrestant = true;
							}
						}
					}
				}
				if (splitrestant) {
					cubes = new HashSet<>(newCubes);
				}

			}
			cubes = new HashSet<>(cubes);
		}
		int cpt = 0;
		List<Cube> lcubes = new ArrayList<>(cubes);
		lcubes.sort(Comparator.comparingInt(Cube::getXmin).thenComparing(Comparator.comparing(Cube::getXmax))
				.thenComparing(Comparator.comparing(Cube::getYmin)).thenComparing(Comparator.comparing(Cube::getYmax))
				.thenComparing(Comparator.comparing(Cube::getZmin)).thenComparing(Comparator.comparing(Cube::getZmax)));

		for (Instruction ins : instructions) {
			for (Cube cu : lcubes) {
				if (ins.contient(cu)) {
					cu.setOn(ins.isOn);
				}
			}
			cpt++;
		}

		BigInteger res = BigInteger.ZERO;
		for (Cube cu : cubes.stream().filter(cu -> cu.isOn).collect(Collectors.toList())) {
			res = res.add(cu.getnbCube());
		}
		return res;
	}

	private BigInteger s2s1(boolean b) {
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
		List<Cube> cubes = new ArrayList<>();

		for (int i = 0; i < llx.size() - 1; i++) {
			System.out.println(i);
			for (int j = 0; j < lly.size() - 1; j++) {
				for (int k = 0; k < llz.size() - 1; k++) {

					Cube c = new Cube(llx.get(i) + 1, llx.get(i + 1) - 1, lly.get(j) + 1, lly.get(j + 1) - 1,
							llz.get(k) + 1, llz.get(k + 1) - 1);
					if (c.bienForme()) {
						cubes.add(c);
					}
					cubes.add(new Cube(llx.get(i), llx.get(i), lly.get(j), lly.get(j), llz.get(k), llz.get(k)));

					c = new Cube(llx.get(i) + 1, llx.get(i + 1) - 1, lly.get(j), lly.get(j), llz.get(k), llz.get(k));
					if (c.bienForme()) {
						cubes.add(c);
					}
					c = new Cube(llx.get(i), llx.get(i), lly.get(j) + 1, lly.get(j + 1) - 1, llz.get(k), llz.get(k));

					if (c.bienForme()) {
						cubes.add(c);
					}
					c = new Cube(llx.get(i), llx.get(i), lly.get(j), lly.get(j), llz.get(k) + 1, llz.get(k + 1) - 1);

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

		int cpt = 0;
		// List<Cube> cubes = new ArrayList<>(cubes);
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
		llx.add(llx.get(llx.size() - 1) + 2);
		lly.add(lly.get(lly.size() - 1) + 2);
		llz.add(llz.get(llz.size() - 1) + 2);
		/*
		 * llx.add(0); llx.add(0); llz.add(0); llx.add(-1); lly.add(-1); llz.add(-1);
		 */
		llx.add(llx.get(0) - 2);
		lly.add(lly.get(0) - 2);
		llz.add(llz.get(0) - 2);
		lx = null;
		ly = null;
		lz = null;
		/*
		 * List<Integer> splitValues = Arrays.asList(0, -10000, 10001); for (Integer spv
		 * : splitValues) { llx.add(spv); llx.add(spv - 1); }
		 */
		Collections.sort(llx);
		Collections.sort(lly);
		Collections.sort(llz);
		Cube cmax = new Cube(llx.get(0), llx.get(llx.size() - 1), lly.get(0), lly.get(lly.size() - 1), llz.get(0),
				llz.get(llz.size() - 1));
		List<Cube> zones = getZones(cmax);
		// List<Instruction> newInstructions = getNewInst(instructions, splitValues);
		BigInteger res = BigInteger.ZERO;
		for (Cube zone : zones) {
			 res = res.add(calculerOn(zone, instructions, llx, lly, llz));
			System.out.println("sous-total : "+res);
		}
		res = res.add(calculerOnHZ(zones, instructions, llx, lly, llz));

		return res;
	}

	private List<Cube> get2Zones(Cube c) {
		List<Cube> zones = new ArrayList<A2021Day22.Cube>();
		zones.add(new Cube(50, c.xmax, c.ymin, c.ymax, c.zmin, c.zmax, ">100000"));
		zones.add(new Cube(-10, 49, c.ymin, c.ymax, c.zmin, c.zmax, "<100000"));
		zones.add(new Cube(c.xmin, -11, c.ymin, c.ymax, c.zmin, c.zmax, "<100000"));
		return splitZ(splitY(zones));
	}

	private BigInteger calculerOnHZ(List<Cube> zones, List<Instruction> instructions, List<Integer> llx,
			List<Integer> lly, List<Integer> llz) {
		System.out.println("HZ");
		List<Cube> cubes = new ArrayList<>();
		for (int i = 0; i < llx.size() - 1; i++) {
			System.out.println(llx.get(i)+" "+ llx.get(i+1));
			//System.out.println(i);
			for (int j = 0; j < lly.size() - 1; j++) {
				for (int k = 0; k < llz.size() - 1; k++) {
					if (inclusDsAucuneZone(llx.get(i), llx.get(i + 1),lly.get(j), lly.get(j + 1),llz.get(k), llz.get(k + 1), zones)) {
				//		System.out.println("HZ!");
				//		System.out.println(new Cube(llx.get(i), llx.get(i+1), lly.get(j), lly.get(j+1),
				//		llz.get(k), llz.get(k+1)));
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
/*
		cubes.sort(Comparator.comparingInt(Cube::getXmin).thenComparing(Comparator.comparing(Cube::getXmax))
				.thenComparing(Comparator.comparing(Cube::getYmin)).thenComparing(Comparator.comparing(Cube::getYmax))
				.thenComparing(Comparator.comparing(Cube::getZmin)).thenComparing(Comparator.comparing(Cube::getZmax)));
*/
		System.out.println("nb cubes en HZ : " + cubes.size());
		/*
		 * List<Instruction> instructionsZone = new ArrayList<A2021Day22.Instruction>();
		 * System.out.println(zone.nom); for (Instruction ins : instructions) { if
		 * (convInsZone(ins, zone).xmin != Integer.MIN_VALUE) {
		 * System.out.println("ins :" + ins); System.out.println(convInsZone(ins,
		 * zone)); instructionsZone.add(convInsZone(ins, zone)); } }
		 */
		for (Instruction ins : instructions) {
			for (Cube cu : cubes) {
				if (ins.contient(cu)) {
					cu.setOn(ins.isOn);
				}
			}
		}
		System.out.println("fin attribution on/off, debut du calcule en HZ");
		BigInteger res = BigInteger.ZERO;
		for (Cube cu : cubes.stream().filter(cu -> cu.isOn).collect(Collectors.toList())) {
			res = res.add(cu.getnbCube());
		}
		return res;
	}

	private boolean inclusDsAucuneZone(Integer xi, Integer xa, Integer yi, Integer ya,Integer zi, Integer za, List<Cube> zones) {
		for (Cube zone : zones) {
			if (xi >= zone.xmin && xa <= zone.xmax && yi >= zone.ymin && ya <= zone.ymax && zi >= zone.zmin && za <= zone.zmax) {
				return false;
			}
		}
		return true;
	}

	private List<Instruction> getNewInst(List<Instruction> instructions, List<Integer> splitValues) {
		List<Instruction> newInstructions = new ArrayList<A2021Day22.Instruction>();
		for (Integer spv : splitValues) {
			for (Instruction ins : instructions) {
				if (spv < ins.xmax && spv > ins.xmin) {
					newInstructions
							.add(new Instruction(ins.isOn, ins.xmin, spv - 1, ins.ymin, ins.ymax, ins.zmin, ins.zmax));
					newInstructions
							.add(new Instruction(ins.isOn, spv, ins.xmax, ins.ymin, ins.ymax, ins.zmin, ins.zmax));
				}
			}
			instructions = new ArrayList<A2021Day22.Instruction>(newInstructions);
		}
		return instructions;
	}

	private List<Cube> getZones(Cube c) {
		List<Cube> zones = new ArrayList<A2021Day22.Cube>();
		zones.add(new Cube(100001, c.xmax, c.ymin, c.ymax, c.zmin, c.zmax, ">100000"));
		int pas =10000;
		int k = 0;
		int lastValue=0;
		while (100001 - k * pas > -80001) {
			k++;
			Cube zone = new Cube(100001 - k * pas, 100000 - (k - 1) * pas, c.ymin, c.ymax, c.zmin, c.zmax, String.valueOf(100000 - k * pas) + ":" + String.valueOf(100000 - (k-1 ) * pas));
			zones.add(zone);
			lastValue=100001 - k * pas;
		}
		zones.add(new Cube(c.xmin, lastValue-1, c.ymin, c.ymax, c.zmin, c.zmax, "<"+lastValue));
		return splitZ(splitY(zones));
	}

	private List<Cube> splitZ(List<Cube> zones) {
		List<Cube> zonesSpZ = new ArrayList<A2021Day22.Cube>();
		for(Cube zone:zones) {
			zonesSpZ.add(new Cube(zone.xmin,zone.xmax,zone.ymin,zone.ymax,zone.zmin,-1,zone.nom+",y<0"));
			zonesSpZ.add(new Cube(zone.xmin,zone.xmax,zone.ymin,zone.ymax,0,zone.zmax,zone.nom+",y>=0"));
		}
		return zonesSpZ;
	}

	private List<Cube> splitY(List<Cube> zones) {
		List<Cube> zonesSpY = new ArrayList<A2021Day22.Cube>();
		for(Cube zone:zones) {
			zonesSpY.add(new Cube(zone.xmin,zone.xmax,zone.ymin,-1,zone.zmin,zone.zmax,zone.nom+",z<0"));
			zonesSpY.add(new Cube(zone.xmin,zone.xmax,0,zone.ymax,zone.zmin,zone.zmax,zone.nom+",z>=0"));
		}
		return zonesSpY;
	}

	private BigInteger calculerOn(Cube zone, List<Instruction> instructions, List<Integer> llx, List<Integer> lly,
			List<Integer> llz) {
		List<Cube> cubes = new ArrayList<>();
		System.out.println(zone.nom);
		for (int i = 0; i < llx.size() - 1; i++) {
		//	System.out.println(zone.nom+llx.get(i)+" "+ llx.get(i+1));
			for (int j = 0; j < lly.size() - 1; j++) {
				for (int k = 0; k < llz.size() - 1; k++) {
					if (llx.get(i) >= zone.xmin && llx.get(i + 1) <= zone.xmax && lly.get(j) >= zone.ymin && lly.get(j + 1) <= zone.ymax && llz.get(k) >= zone.zmin && llz.get(k + 1) <= zone.zmax) {
						// System.out.println("Dans la zone "+zone.nom);
						// System.out.println(new Cube(llx.get(i), llx.get(i), lly.get(j), lly.get(j),
						// llz.get(k), llz.get(k)));
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
/*
		cubes.sort(Comparator.comparingInt(Cube::getXmin).thenComparing(Comparator.comparing(Cube::getXmax))
				.thenComparing(Comparator.comparing(Cube::getYmin)).thenComparing(Comparator.comparing(Cube::getYmax))
				.thenComparing(Comparator.comparing(Cube::getZmin)).thenComparing(Comparator.comparing(Cube::getZmax)));
*/
		System.out.println("nb cubes dans la zone " + zone.nom + " : " + cubes.size());
		if(cubes.size()>0) {
		/*
		 * List<Instruction> instructionsZone = new ArrayList<A2021Day22.Instruction>();
		 * System.out.println(zone.nom); for (Instruction ins : instructions) { if
		 * (convInsZone(ins, zone).xmin != Integer.MIN_VALUE) {
		 * System.out.println("ins :" + ins); System.out.println(convInsZone(ins,
		 * zone)); instructionsZone.add(convInsZone(ins, zone)); } }
		 */
		for (Instruction ins : instructions) {
			for (Cube cu : cubes) {
				if (ins.contient(cu)) {
					cu.setOn(ins.isOn);
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

	private Instruction convInsZone(Instruction ins, Cube zone) {
		Instruction instructionZone = new Instruction(false, Integer.MIN_VALUE, 0, 1, 0, 1, 0);
		if (zone.getNom().equals("0:10000")) {
			if (ins.xmin > 10000 || ins.xmax < 0) {
				/// System.out.println("a" +);
				return instructionZone;
			}
			if (ins.xmax <= 10000 && ins.xmin >= 0) {
				System.out.println("full ins :" + ins);
				return ins;
			}
			if (ins.xmin <= 10000 && ins.xmin >= 0) {
				System.out.println(ins);
				System.out.println(
						"ni " + new Instruction(ins.isOn, ins.xmin, 10000, ins.ymin, ins.ymax, ins.zmin, ins.zmax));
				return new Instruction(ins.isOn, ins.xmin, 10000, ins.ymin, ins.ymax, ins.zmin, ins.zmax);
			}
			if (ins.xmax <= 10000 && ins.xmax >= 0) {
				System.out.println(ins);
				System.out.println(
						"ni " + new Instruction(ins.isOn, 0, ins.xmax, ins.ymin, ins.ymax, ins.zmin, ins.zmax));
				return new Instruction(ins.isOn, 0, ins.xmax, ins.ymin, ins.ymax, ins.zmin, ins.zmax);
			}
			ins.setXmin(zone.xmin);
			ins.setXmax(zone.xmax);
			return ins;

		} else if (zone.getNom().equals(">10000")) {
			if (ins.xmax <= 10000) {
				return instructionZone;
			}
			if (ins.xmin > 10000) {
				return ins;
			}
			if (ins.xmax > 10000) {
				return new Instruction(ins.isOn, 10001, ins.xmax, ins.ymin, ins.ymax, ins.zmin, ins.zmax);
			}
			ins.setXmin(zone.xmin);
			ins.setXmax(zone.xmax);
			return ins;
		} else if (zone.getNom().equals("-10000:-1")) {
			if (ins.xmin > -1 || ins.xmax < -10000) {
				return instructionZone;
			}
			if (ins.xmax <= -1 && ins.xmin >= -10000) {
				return ins;
			}
			if (ins.xmin <= -1 && ins.xmin >= -10000) {
				return new Instruction(ins.isOn, ins.xmin, -1, ins.ymin, ins.ymax, ins.zmin, ins.zmax);
			}
			if (ins.xmax <= -1 && ins.xmax >= -10000) {
				return new Instruction(ins.isOn, -10000, ins.xmax, ins.ymin, ins.ymax, ins.zmin, ins.zmax);
			}
			ins.setXmin(zone.xmin);
			ins.setXmax(zone.xmax);
			return ins;
		} else if (zone.getNom().equals("<-10000")) {
			if (ins.xmin > -10000) {
				return instructionZone;
			}
			if (ins.xmax < -10000) {
				return ins;
			}
			if (ins.xmin <= -10000) {
				return new Instruction(ins.isOn, ins.xmin, -10001, ins.ymin, ins.ymax, ins.zmin, ins.zmax);
			}
			ins.setXmin(zone.xmin);
			ins.setXmax(zone.xmax);
			return ins;
		} else {
			System.out.println("zone inconnue");
		}

		return instructionZone;
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

		public Collection<? extends Cube> split(Cube c2) {
			List<Cube> splitted = new ArrayList<A2021Day22.Cube>();
			RepInclusion repinc = inclusion(this, c2);
			if (repinc.res) {
				return spliIncl(repinc);
			}
			List<Point> s1 = getSommets();
			List<Point> s2 = c2.getSommets();
			if (s1.stream().filter(p -> c2.contient(p)).count() == 2) {
				splitted = splitDeuxCoins(this, c2);
			}
			if (s2.stream().filter(p -> contient(p)).count() == 2) {
				splitted = splitDeuxCoins(c2, this);
			}
			if (s1.stream().filter(p -> c2.contient(p)).count() == 1) {
				splitted = splitUnCoin(this, c2);
			}
			if (s2.stream().filter(p -> contient(p)).count() == 1) {
				splitted = splitUnCoin(c2, this);
			}
			if (s1.stream().filter(p -> c2.contient(p)).count() == 4) {
				splitted = split4Coin(this, c2);
			}
			if (s2.stream().filter(p -> contient(p)).count() == 4) {
				splitted = split4Coin(c2, this);
			}
			return splitted;
		}

		private List<Cube> split4Coin(Cube c1, Cube c2) {
			List<Cube> splitted = new ArrayList<>();
			List<Point> s1 = c1.getSommets();
			List<Point> s2 = c2.getSommets();
			List<Point> pts = s2.stream().filter(p -> c2.contient(p)).collect(Collectors.toList());
			Point p1DeC2DansC1 = pts.get(0);
			Point p2DeC2DansC1 = pts.get(1);
			return splitted;

		}

		private List<Cube> splitUnCoin(Cube c1, Cube c2) {
			List<Cube> splitted = new ArrayList<>();
			List<Point> s1 = c1.getSommets();
			List<Point> s2 = c2.getSommets();
			Point pDeC2DansC1 = s2.stream().filter(p -> c2.contient(p)).findFirst().get();
			if (pDeC2DansC1.type == 1) {
				splitted = splitT1(c1, c2, 1);
			} else if (pDeC2DansC1.type == 7) {
				splitted = splitT1(c1, c2, 7);
			} else if (pDeC2DansC1.type == 2) {
				splitted = splitT1(c1, c2, 2);
			} else if (pDeC2DansC1.type == 8) {
				splitted = splitT1(c1, c2, 8);
			} else if (pDeC2DansC1.type == 3) {
				splitted = splitT1(c1, c2, 3);
			} else if (pDeC2DansC1.type == 5) {
				splitted = splitT1(c1, c2, 5);
			} else if (pDeC2DansC1.type == 4) {
				splitted = splitT1(c1, c2, 4);
			} else if (pDeC2DansC1.type == 6) {
				splitted = splitT1(c1, c2, 6);
			}
			return splitted;
		}

		private List<Cube> splitT4(Cube c1, Cube c2, int i) {
			List<Cube> splitted = new ArrayList<>();
			splitted.add(new Cube(c2.xmin, c1.xmax, c2.ymin, c1.ymax, c2.zmin, c1.zmax, "inter_t" + i));
			splitted.add(new Cube(c1.xmin, c2.xmin - 1, c1.ymin, c1.ymax, c1.zmin, c1.zmax, "bas_c1_t" + i));
			splitted.add(new Cube(c2.xmin, c1.xmax, c1.ymin, c1.ymax, c1.zmin, c2.zmin - 1, "gauche_c1_t" + i));
			splitted.add(new Cube(c2.xmin, c1.xmax, c1.ymin, c2.ymin - 1, c1.zmin, c1.zmax, "devant_c1_t" + i));
			splitted.add(new Cube(c2.xmin + 1, c2.xmax, c2.ymin, c2.ymax, c2.zmin, c2.zmax, "haut_c2_t" + i));
			splitted.add(new Cube(c2.xmin, c1.xmax, c2.ymin, c2.ymax, c1.zmax + 1, c2.zmax, "droite_c2_t" + i));
			splitted.add(new Cube(c2.xmin, c1.xmax, c1.ymax + 1, c2.ymax, c2.zmin, c2.zmax, "der_c2_t" + i));
			return splitted;
		}

		private List<Cube> splitT3(Cube c1, Cube c2, int i) {
			List<Cube> splitted = new ArrayList<>();
			splitted.add(new Cube(c2.xmin, c1.xmax, c2.ymin, c1.ymax, c2.zmin, c1.zmax, "inter_t" + i));
			splitted.add(new Cube(c1.xmin, c2.xmin - 1, c1.ymin, c1.ymax, c1.zmin, c1.zmax, "bas_c1_t" + i));
			splitted.add(new Cube(c2.xmin, c1.xmax, c1.ymin, c1.ymax, c1.zmin, c2.zmin - 1, "gauche_c1_t" + i));
			splitted.add(new Cube(c2.xmin, c1.xmax, c1.ymin, c2.ymin - 1, c1.zmin, c1.zmax, "devant_c1_t" + i));
			splitted.add(new Cube(c2.xmin + 1, c2.xmax, c2.ymin, c2.ymax, c2.zmin, c2.zmax, "haut_c2_t" + i));
			splitted.add(new Cube(c2.xmin, c1.xmax, c2.ymin, c2.ymax, c1.zmax + 1, c2.zmax, "droite_c2_t" + i));
			splitted.add(new Cube(c2.xmin, c1.xmax, c1.ymax + 1, c2.ymax, c2.zmin, c2.zmax, "der_c2_t" + i));
			return splitted;
		}

		private List<Cube> splitT2(Cube c1, Cube c2, int i) {
			List<Cube> splitted = new ArrayList<>();
			splitted.add(new Cube(c2.xmin, c1.xmax, c2.ymin, c1.ymax, c2.zmin, c1.zmax, "inter_t" + i));
			splitted.add(new Cube(c1.xmin, c2.xmin - 1, c1.ymin, c1.ymax, c1.zmin, c1.zmax, "bas_c1_t" + i));
			splitted.add(new Cube(c2.xmin, c1.xmax, c1.ymin, c1.ymax, c1.zmin, c2.zmin - 1, "gauche_c1_t" + i));
			splitted.add(new Cube(c2.xmin, c1.xmax, c1.ymin, c2.ymin - 1, c1.zmin, c1.zmax, "devant_c1_t" + i));
			splitted.add(new Cube(c2.xmin + 1, c2.xmax, c2.ymin, c2.ymax, c2.zmin, c2.zmax, "haut_c2_t" + i));
			splitted.add(new Cube(c2.xmin, c1.xmax, c2.ymin, c2.ymax, c1.zmax + 1, c2.zmax, "droite_c2_t" + i));
			splitted.add(new Cube(c2.xmin, c1.xmax, c1.ymax + 1, c2.ymax, c2.zmin, c2.zmax, "der_c2_t" + i));
			return splitted;
		}

		private List<Cube> splitT1(Cube c1, Cube c2, int i) {
			List<Cube> splitted = new ArrayList<>();
			splitted.add(new Cube(c2.xmin, c1.xmax, c2.ymin, c1.ymax, c2.zmin, c1.zmax, "inter_t" + i));
			splitted.add(new Cube(c1.xmin, c2.xmin - 1, c1.ymin, c1.ymax, c1.zmin, c1.zmax, "bas_c1_t" + i));
			splitted.add(new Cube(c2.xmin, c1.xmax, c1.ymin, c1.ymax, c1.zmin, c2.zmin - 1, "gauche_c1_t" + i));
			splitted.add(new Cube(c2.xmin, c1.xmax, c1.ymin, c2.ymin - 1, c1.zmin, c1.zmax, "devant_c1_t" + i));
			splitted.add(new Cube(c2.xmin + 1, c2.xmax, c2.ymin, c2.ymax, c2.zmin, c2.zmax, "haut_c2_t" + i));
			splitted.add(new Cube(c2.xmin, c1.xmax, c2.ymin, c2.ymax, c1.zmax + 1, c2.zmax, "droite_c2_t" + i));
			splitted.add(new Cube(c2.xmin, c1.xmax, c1.ymax + 1, c2.ymax, c2.zmin, c2.zmax, "der_c2_t" + i));
			return splitted;
		}

		private List<Cube> splitDeuxCoins(Cube c2, Cube c1) {
			List<Cube> splitted = new ArrayList<>();
			List<Point> s1 = c1.getSommets();
			List<Point> s2 = c2.getSommets();
			List<Point> pts = s2.stream().filter(p -> c2.contient(p)).collect(Collectors.toList());
			Point p1DeC2DansC1 = pts.get(0);
			Point p2DeC2DansC1 = pts.get(1);
			return splitted;
		}

		private List<Cube> spliIncl(RepInclusion repinc) {
			List<Cube> splitted = new ArrayList<A2021Day22.Cube>();
			repinc.petit.setNom("centre");
			splitted.add(repinc.petit);
			splitted.add(new Cube(repinc.grand.xmin, repinc.petit.xmin - 1, repinc.grand.ymin, repinc.petit.ymin - 1,
					repinc.grand.zmin, repinc.grand.zmax, "dvt"));
			splitted.add(new Cube(repinc.petit.xmax + 1, repinc.grand.xmax, repinc.petit.ymax + 1, repinc.grand.ymax,
					repinc.grand.zmin, repinc.grand.zmax, "der"));
			splitted.add(new Cube(repinc.petit.xmin, repinc.petit.xmax, repinc.petit.ymin, repinc.petit.ymax,
					repinc.petit.zmax + 1, repinc.grand.zmax, "dr"));
			splitted.add(new Cube(repinc.petit.xmin, repinc.petit.xmax, repinc.petit.ymin, repinc.petit.ymax,
					repinc.grand.zmin, repinc.petit.zmin - 1, "ga"));
			splitted.add(new Cube(repinc.petit.xmin, repinc.petit.xmax, repinc.grand.ymin, repinc.petit.ymin - 1,
					repinc.grand.zmin, repinc.grand.zmax, "bas"));
			splitted.add(new Cube(repinc.petit.xmin, repinc.petit.xmax, repinc.petit.ymax + 1, repinc.grand.ymax,
					repinc.grand.zmin, repinc.grand.zmax, "ht"));
			return splitted;
		}

		private RepInclusion inclusion(Cube c1, Cube c2) {
			if (c1.xmax < c2.xmax && c1.xmin > c2.xmin && c1.ymax < c2.ymax && c1.ymin > c2.ymin && c1.zmax < c2.zmax
					&& c1.zmin > c2.zmin) {
				return new RepInclusion(true, c1, c2);
			} else if (c2.xmax < c1.xmax && c2.xmin > c1.xmin && c2.ymax < c1.ymax && c2.ymin > c1.ymin
					&& c2.zmax < c1.zmax && c2.zmin > c1.zmin) {
				return new RepInclusion(true, c2, c1);
			}
			return new RepInclusion(false, null, null);
		}

		public boolean seTouche(Cube c2) {
			List<Point> s1 = getSommets();
			List<Point> s2 = c2.getSommets();
			return s1.stream().anyMatch(p -> c2.contient(p)) || s2.stream().anyMatch(p -> contient(p));
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

		public List<Point> getSommets() {
			List<Point> sommets = new ArrayList<A2021Day22.Point>();
			sommets.add(new Point(xmin, ymin, zmin, 1));
			sommets.add(new Point(xmin, ymin, zmax, 2));
			sommets.add(new Point(xmin, ymax, zmax, 3));
			sommets.add(new Point(xmin, ymax, zmin, 4));
			sommets.add(new Point(xmax, ymin, zmin, 5));
			sommets.add(new Point(xmax, ymin, zmax, 6));
			sommets.add(new Point(xmax, ymax, zmax, 7));
			sommets.add(new Point(xmax, ymax, zmin, 8));
			return sommets;
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

	public static class RepInclusion {
		boolean res;
		Cube petit;
		Cube grand;

		public boolean isRes() {
			return res;
		}

		public void setRes(boolean res) {
			this.res = res;
		}

		public Cube getPetit() {
			return petit;
		}

		public void setPetit(Cube petit) {
			this.petit = petit;
		}

		public Cube getGrand() {
			return grand;
		}

		public void setGrand(Cube grand) {
			this.grand = grand;
		}

		public RepInclusion(boolean res, Cube petit, Cube grand) {
			super();
			this.res = res;
			this.petit = petit;
			this.grand = grand;
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

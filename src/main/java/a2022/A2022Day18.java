package a2022;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import outils.MesOutils;

public class A2022Day18 extends A2022 {
	int cpt = 0;

	public A2022Day18(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day18 d = new A2022Day18(18);
		long startTime = System.currentTimeMillis();
		// System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public int s2(boolean b) {
		Set<Cube> cubes = getCubes(b);
		// 2420 too low
		// 15414 too high
		int res = 0;
		int imax = MesOutils.getMaxIntegerFromList(cubes.stream().map(Cube::getX).toList()) + 2;
		int jmax = MesOutils.getMaxIntegerFromList(cubes.stream().map(Cube::getY).toList()) + 2;
		int kmax = MesOutils.getMaxIntegerFromList(cubes.stream().map(Cube::getZ).toList()) + 2;
		Set<Cube> externes = new HashSet<>();
		externes.add(new Cube(0, 0, 0, "externe"));
		externes.add(new Cube(imax, 0, 0, "externe"));
		externes.add(new Cube(0, jmax, 0, "externe"));
		externes.add(new Cube(0, 0, kmax, "externe"));
		externes.add(new Cube(0, jmax, kmax, "externe"));
		externes.add(new Cube(imax, 0, kmax, "externe"));
		externes.add(new Cube(imax, jmax, 0, "externe"));
		externes.add(new Cube(imax, jmax, kmax, "externe"));
		int externeSize = 0;
		while (externeSize < externes.size()) {
			externeSize = externes.size();
			Set<Cube> nextExternes = new HashSet<>();
			Set<Cube> voisins = new HashSet<>();
			for (Cube c : externes) {
				nextExternes.add(c);
				voisins = getVoisinsHorsDiag(c, imax, jmax, kmax);
				for (Cube v : voisins) {
					if (cubes.contains(v)) {
						for (Cube cb : cubes) {
							if (cb.equals(v)) {
								cb.position = "bord";
							}
						}
					} else {
						v.position = "externe";
						nextExternes.add(v);
					}
				}
			}
			externes = nextExternes;
		}
		Set<Cube> all = new HashSet<>();
		all.addAll(cubes);
		all.addAll(externes);
		for (int i = 0; i < imax; i++) {
			for (int j = 0; j < jmax; j++) {
				for (int k = 0; k < kmax; k++) {
					Cube nc = new Cube(i, j, k, "interne");
					if (!all.contains(nc)) {
						all.add(nc);
					}
				}
			}
		}
		return nbExposeFaceExterne(all, imax, jmax, kmax);
	}

	private int nbExposeFaceExterne(Set<Cube> all, int imax, int jmax, int kmax) {
		int res = 0;
		List<Cube> bord = all.stream().filter(cu -> cu.position.equals("bord")).toList();
		List<Cube> interne = all.stream().filter(cu -> cu.position.equals("interne")).toList();
		for (Cube c : bord) {
			Set<Cube> voisinDeC = getVoisinsHorsDiagFromCubes(c, all, imax, jmax, kmax);
			for (Cube v : voisinDeC) {
				if (v.position.equals("externe")) {
					res++;
				}
			}
		}
		return res;
	}

	private int nbExposeFaceExterne2(Set<Cube> all, int imax, int jmax, int kmax) {
		int res = 0;
		List<Cube> bord = all.stream().filter(cu -> cu.position.equals("bord")).toList();
		for (Cube cub : bord) {
			System.out.println(cub);
		}
		for (Cube c : all) {
			if (c.position.equals("bord")) {
				Set<Cube> voisinDeC = getVoisinsHorsDiagFromCubes(c, all, imax, jmax, kmax);
				for (Cube v : voisinDeC) {
					if (v != null) {
						if (v.position.equals("externe")) {
							res++;
						}
					}
				}
			}
		}
		return res;
	}

	private Set<Cube> getVoisinsHorsDiagFromCubes(Cube c, Set<Cube> cubes, int imax, int jmax, int kmax) {
		Set<Cube> voisins = new HashSet<>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				for (int k = -1; k <= 1; k++) {
					int ni = c.x + i;
					int nj = c.y + j;
					int nk = c.z + k;
					if (!(ni == 0 && nj == 0 && nk == 0)) {
						if (ni <= imax && nj <= jmax && nk <= kmax) {
							if (ni > -2 && nk > -2 && nj > -2) {
								if (Math.abs(c.x - ni) + Math.abs(c.y - nj) + Math.abs(c.z - nk) == 1) {
									voisins.add(getCube(ni, nj, nk, cubes));
								}
							}
						}
					}
				}
			}
		}
		return voisins;
	}

	private Cube getCube(int ni, int nj, int nk, Set<Cube> cubes) {
		for (Cube c : cubes) {
			if (c.x == ni && c.y == nj && c.z == nk) {
				return c;
			}
		}
		return null;
	}

	private Set<Cube> getVoisinsHorsDiag(Cube c, int imax, int jmax, int kmax) {
		Set<Cube> voisins = new HashSet<>();
		if (c.position.isBlank()) {
			return voisins;
		}
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				for (int k = -1; k <= 1; k++) {
					int ni = c.x + i;
					int nj = c.y + j;
					int nk = c.z + k;
					if (!(ni == 0 && nj == 0 && nk == 0)) {
						if (ni <= imax && nj <= jmax && nk <= kmax) {
							if (ni > -2 && nk > -2 && nj > -2) {
								if (Math.abs(c.x - ni) + Math.abs(c.y - nj) + Math.abs(c.z - nk) == 1) {
									voisins.add(new Cube(ni, nj, nk));
								}
							}
						}
					}
				}
			}
		}
		return voisins;
	}

	private int compteFaceInaccessible(Set<Cube> cubes) {
		int res = 0;
		for (Cube c : cubes) {
			if (estInaccessible(cubes, c)) {
				res += nbExposeFace(cubes, c);
			}
		}
		for (Cube c : cubes) {
			System.out.println(c.nbVoisins9);
		}
		return res;
	}

	private boolean estInaccessible(Set<Cube> cubes, Cube c) {
		// c.isExterneCube(cubes);
		c.setV9(cubes);
		return true;
	}

	public int s1(boolean b) {
		Set<Cube> cubes = getCubes(b);
		int res = 0;
		for (Cube c : cubes) {
			res += nbExposeFace(cubes, c);
		}
		return res;
	}

	private Set<Cube> getCubes(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().toList();
		Set<Cube> cubes = new HashSet<>();
		for (String s : input) {
			String[] sp = s.split(",");
			cubes.add(new Cube(Integer.parseInt(sp[0].trim()), Integer.parseInt(sp[1].trim()),
					Integer.parseInt(sp[2].trim()), ""));
		}
		return cubes;
	}

	public boolean sontVoisins(Cube c1, Cube c2) {
		if (c1.equals(c2)) {
			return false;
		}
		if (Math.abs(c1.x - c2.x) + Math.abs(c1.y - c2.y) + Math.abs(c1.z - c2.z) == 1) {
			return true;
		}
		return false;
	}

	public boolean sontVoisins9(Cube c1, Cube c2) {
		if (c1.equals(c2)) {
			return false;
		}

		if (Math.abs(c1.x - c2.x) < 2 && Math.abs(c1.y - c2.y) < 2 && Math.abs(c1.z - c2.z) < 2) {
			System.out.println(c2);
			return true;
		}
		return false;
	}

	public Integer nbExposeFace(Collection<Cube> cubes, Cube c) {
		int res = 6;
		for (Cube c2 : cubes) {
			if (sontVoisins(c, c2)) {
				res--;
			}
		}
		return res;
	}

	private class Cube {
		int x;
		int y;
		int z;
		int nbVoisins9;
		String position;

		public int getNbVoisins9() {
			return nbVoisins9;
		}

		public void setV9(Set<Cube> cubes) {
			int res = 0;
			System.out.println(this);
			for (Cube c : cubes) {
				if (sontVoisins9(this, c)) {
					res++;
				}
			}
			setNbVoisins9(res);

		}

		public void setNbVoisins9(int nbVoisins9) {
			this.nbVoisins9 = nbVoisins9;
		}

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
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

		public Cube(int x, int y, int z) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
			this.position = "";
		}

		public Cube(int x, int y, int z, String position) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
			this.position = position;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(x, y, z);
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
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return x == other.x && y == other.y && z == other.z;
		}

		private A2022Day18 getEnclosingInstance() {
			return A2022Day18.this;
		}

		@Override
		public String toString() {
			return "[" + x + "," + y + "," + z + "] " + position;
		}

	}

	public static List<Long> getDuration() {
		A2022Day18 d = new A2022Day18(18);
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

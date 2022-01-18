package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2017Day20 extends A2017 {

	public A2017Day20(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day20 d = new A2017Day20(20);
		// d.s1(true);
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

	public long s1(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Particule> ps = new ArrayList<>();
		int id = 0;
		for (String l : lignes) {
			String[] sp = l.split(",");
			ps.add(new Particule(id, Long.parseLong(sp[0]), Long.parseLong(sp[1]), Long.parseLong(sp[2]),
					Long.parseLong(sp[3]), Long.parseLong(sp[4]), Long.parseLong(sp[5]), Long.parseLong(sp[6]),
					Long.parseLong(sp[7]), Long.parseLong(sp[8])));
			id++;
		}
		for (long j = 0; j < 1000000; j++) {
			for (Particule p : ps) {
				p.move();
			}
		}
		long min = 10000000000000L;
		Particule pmin = null;
		for (Particule p : ps) {
			if (p.dist() < min) {
				pmin = p;
				min = p.dist();
			}
		}

		return pmin.id;
	}

	public long s2(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Particule> ps = new ArrayList<>();
		int id = 0;
		for (String l : lignes) {
			String[] sp = l.split(",");
			ps.add(new Particule(id, Long.parseLong(sp[0]), Long.parseLong(sp[1]), Long.parseLong(sp[2]),
					Long.parseLong(sp[3]), Long.parseLong(sp[4]), Long.parseLong(sp[5]), Long.parseLong(sp[6]),
					Long.parseLong(sp[7]), Long.parseLong(sp[8])));
			id++;
		}
		for (long j = 0; j < 1000000; j++) {
			for (Particule p : ps) {
				p.move();
			}
			ps = collisions(ps);
			System.out.println(ps.size());
		}
		return ps.size();
	}

	private List<Particule> collisions(List<Particule> ps) {
		HashMap<Position, List<Integer>> poz = new HashMap<>();
		for (Particule p : ps) {
			if (poz.containsKey(p.gp())) {
				List<Integer> l=new ArrayList<>(poz.get(p.gp()));
				l.add(p.id);
				poz.put(p.gp(),l);
			} else {
				poz.put(p.gp(),Arrays.asList(p.id));
			}
		}
		List<Particule> res=new ArrayList<>(ps);
		for(Position pz:poz.keySet()) {
			if(poz.get(pz).size()>1) {
				for(Integer id:poz.get(pz)) {
					res=res.stream().filter(p->p.id != id).collect(Collectors.toList());
				}
			}
		}
		return res;
	}

	public static class Position {
		long x;
		long y;
		long z;

		public long getX() {
			return x;
		}

		public void setX(long x) {
			this.x = x;
		}

		public long getY() {
			return y;
		}

		public void setY(long y) {
			this.y = y;
		}

		public long getZ() {
			return z;
		}

		public void setZ(long z) {
			this.z = z;
		}

		public Position(long x, long y, long z) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
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
			Position other = (Position) obj;
			return x == other.x && y == other.y && z == other.z;
		}

	}

	public static class Particule {
		int id;
		long x;
		long y;
		long z;
		long vx;
		long vy;
		long vz;
		long ax;
		long ay;
		long az;

		public Position gp() {
			return new Position(x, y, z);
		}

		public long getX() {
			return x;
		}

		public void setX(long x) {
			this.x = x;
		}

		public long getY() {
			return y;
		}

		public void setY(long y) {
			this.y = y;
		}

		public long getZ() {
			return z;
		}

		public void setZ(long z) {
			this.z = z;
		}

		public long getVx() {
			return vx;
		}

		public void setVx(long vx) {
			this.vx = vx;
		}

		public long getVy() {
			return vy;
		}

		public void setVy(long vy) {
			this.vy = vy;
		}

		public long getVz() {
			return vz;
		}

		public void setVz(long vz) {
			this.vz = vz;
		}

		public long getAx() {
			return ax;
		}

		public void setAx(long ax) {
			this.ax = ax;
		}

		public long getAy() {
			return ay;
		}

		public void setAy(long ay) {
			this.ay = ay;
		}

		public long getAz() {
			return az;
		}

		public void setAz(long az) {
			this.az = az;
		}

		public Particule(int id, long x, long y, long z, long vx, long vy, long vz, long ax, long ay, long az) {
			super();
			this.id = id;
			this.x = x;
			this.y = y;
			this.z = z;
			this.vx = vx;
			this.vy = vy;
			this.vz = vz;
			this.ax = ax;
			this.ay = ay;
			this.az = az;
		}

		@Override
		public String toString() {
			return "Particule [x=" + x + ", y=" + y + ", z=" + z + ", vx=" + vx + ", vy=" + vy + ", vz=" + vz + ", ax="
					+ ax + ", ay=" + ay + ", az=" + az + "]";
		}

		public void move() {
			vx += ax;
			vy += ay;
			vz += az;
			x += vx;
			y += vy;
			z += vz;
		}

		public long dist() {
			return Math.abs(x) + Math.abs(y) + Math.abs(z);
		}
	}

	public static List<Long> getDuration() {
		A2017Day20 d = new A2017Day20(1);
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

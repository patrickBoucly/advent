package a2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2022Day09 extends A2022 {

	public A2022Day09(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day09 d = new A2022Day09(9);
		// System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public int s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		List<Instructions> inst = input.stream().map(Instructions::new).collect(Collectors.toList());
		Extremite head = new Extremite("h", new Position(0L, 0L), new Position(0L, 0L));
		Extremite un = new Extremite("1", new Position(0L, 0L), new Position(0L, 0L));
		Extremite deux = new Extremite("2", new Position(0L, 0L), new Position(0L, 0L));
		Extremite trois = new Extremite("3", new Position(0L, 0L), new Position(0L, 0L));
		Extremite quatre = new Extremite("4", new Position(0L, 0L), new Position(0L, 0L));
		Extremite cinq = new Extremite("5", new Position(0L, 0L), new Position(0L, 0L));
		Extremite six = new Extremite("6", new Position(0L, 0L), new Position(0L, 0L));
		Extremite sept = new Extremite("7", new Position(0L, 0L), new Position(0L, 0L));
		Extremite huit = new Extremite("8", new Position(0L, 0L), new Position(0L, 0L));
		Extremite neuf = new Extremite("9", new Position(0L, 0L), new Position(0L, 0L));
		List<Extremite> l = new ArrayList<>();
		l.add(head);
		l.add(un);
		l.add(deux);
		l.add(trois);
		l.add(quatre);
		l.add(cinq);
		l.add(six);
		l.add(sept);
		l.add(huit);
		l.add(neuf);
		Corde c = new Corde(l);

		System.out.println(c);
		for (Instructions i : inst) {
			/*
			 * System.out.println(head);
			 * 
			 * System.out.println(un); System.out.println(deux); System.out.println(trois);
			 * System.out.println(quatre); System.out.println(cinq);
			 * System.out.println(six); System.out.println(sept); System.out.println(huit);
			 * System.out.println(neuf);
			 */
			System.out.println(i);

			apply2(i, head, un, deux, trois, quatre, cinq, six, sept, huit, neuf, c);
			System.out.println(c);

			System.out.println("nbv viste :" + neuf.getVisite().size());

		}
		System.out.println(neuf.visite);
		System.out.println(c);
		List<Extremite> r = new ArrayList<>();
		for (Position e : neuf.visite) {
			r.add(new Extremite("#", e, e));
		}
		System.out.println(new Corde(r));
		// 2486
		return neuf.getVisite().size();
	}

	private void apply2(Instructions i, Extremite head, Extremite un, Extremite deux, Extremite trois, Extremite quatre,
			Extremite cinq, Extremite six, Extremite sept, Extremite huit, Extremite neuf, Corde c) {
		for (int q = 1; q <= i.qtt; q++) {
			avancerHead(i.dir, head, c);
			avancerTail(i.dir, un, head, c);
			avancerTail(i.dir, deux, un, c);
			avancerTail(i.dir, trois, deux, c);
			avancerTail(i.dir, quatre, trois, c);
			avancerTail(i.dir, cinq, quatre, c);
			avancerTail(i.dir, six, cinq, c);
			avancerTail(i.dir, sept, six, c);
			avancerTail(i.dir, huit, sept, c);
			avancerTail(i.dir, neuf, huit, c);
		}
	}

	private void avancerTail(String dir, Extremite tail, Extremite head, Corde c) {
		System.out.println("head");
		System.out.println(head);
		System.out.println("tail");
		System.out.println(tail);
		if (head.estLoin(tail)) {
			/*
			 * System.out.println("loin!"); tail.lastPos = new Position(tail.curPos); //
			 * Long deltaA=head.curPos.a-head.lastPos.a; // Long
			 * deltaO=head.curPos.o-head.lastPos.o; // tail.setCurPos(new
			 * Position(tail.curPos.a+deltaA,tail.curPos.o+deltaO ));
			 * 
			 * if (head.lastMove.equals("U")) { tail.monter(); if (head.curPos.a >
			 * tail.curPos.a) { tail.droite(); } else if (head.curPos.a < tail.curPos.a) {
			 * tail.gauche(); } } if (head.lastMove.equals("D")) { tail.descendre(); if
			 * (head.curPos.a > tail.curPos.a) { tail.droite(); } else if (head.curPos.a <
			 * tail.curPos.a) { tail.gauche(); } } if (head.lastMove.equals("R")) {
			 * tail.droite(); if (head.curPos.o > tail.curPos.o) { tail.monter(); } else if
			 * (head.curPos.o < tail.curPos.o) { tail.descendre(); } } if
			 * (head.lastMove.equals("L")) { tail.gauche(); if (head.curPos.o >
			 * tail.curPos.o) { tail.monter(); } else if (head.curPos.o < tail.curPos.o) {
			 * tail.descendre(); } } if(head.curPos.equals(tail.curPos)) {
			 * System.out.println(c); }
			 */
			if (head.curPos.o.equals(tail.curPos.o)) {
				if (head.curPos.a > tail.curPos.a) {
					tail.droite();
				} else {
					tail.gauche();
				}
			} else if (head.curPos.a.equals(tail.curPos.a)) {
				if (head.curPos.o > tail.curPos.o) {
					tail.monter();
				} else {
					tail.descendre();
				}
			} else {
				tail.diag(head);
			}
			// System.out.println(c);
			tail.visite.add(new Position(tail.curPos));

		}
	}

	private void avancerHead(String dir, Extremite head, Corde c) {
		head.lastPos = new Position(head.curPos);
		if (dir.equals("U")) {
			head.monter();
		}
		if (dir.equals("D")) {
			head.descendre();
		}
		if (dir.equals("L")) {
			head.gauche();
		}
		if (dir.equals("R")) {
			head.droite();
		}
		head.visite.add(new Position(head.curPos));
	}

	public int s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		List<Instructions> inst = input.stream().map(Instructions::new).collect(Collectors.toList());
		Extremite head = new Extremite("h", new Position(0L, 0L), new Position(0L, 0L));
		Extremite tail = new Extremite("t", new Position(0L, 0L), new Position(0L, 0L));
		// System.out.println(head);
		// System.out.println(tail);
		for (Instructions i : inst) {
			// System.out.println(i);

			apply(i, head, tail);
		}
		return tail.getVisite().size();
	}

	private void apply(Instructions i, Extremite head, Extremite tail) {
		for (int q = 1; q <= i.qtt; q++) {
			avancerHead(i.dir, head, null);
			// System.out.println(head);
			avancerTail(i.dir, tail, head, null);
			// System.out.println(tail);
		}
	}

	private class Corde {
		List<Extremite> extremites;

		public List<Extremite> getExtremites() {
			return extremites;
		}

		public void setExtremites(List<Extremite> extremites) {
			this.extremites = extremites;
		}

		public Corde(List<Extremite> extremites) {
			super();
			this.extremites = extremites;
		}

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			Extremite ex = new Extremite();
			// pts.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
			long amax = Math.max(2,
					MesOutils.getMaxLongFromList(extremites.stream().map(e -> e.curPos.a).collect(Collectors.toList()))
							+ 2);
			long amin = Math.min(-2,
					MesOutils.getMinLongFromList(extremites.stream().map(e -> e.curPos.a).collect(Collectors.toList()))
							- 2);
			long omax = Math.max(2,
					MesOutils.getMaxLongFromList(extremites.stream().map(e -> e.curPos.o).collect(Collectors.toList()))
							+ 2);
			long omin = Math.min(-2,
					MesOutils.getMinLongFromList(extremites.stream().map(e -> e.curPos.o).collect(Collectors.toList()))
							- 2);
			for (long j = omax; j >= omin; j--) {
				for (long i = amin; i <= amax; i++) {
					if (i == 0L && j == 0L) {
						res.append("s");
					} else {
						ex.getExtremite(extremites, i, j).ifPresentOrElse(pt -> res.append(pt.type),
								() -> res.append("."));
					}
				}
				res.append("\n");
			}
			return res.toString();
		}

	}

	private class Instructions {
		String dir;
		Integer qtt;

		public Instructions(String l) {
			this.dir = l.split(" ")[0];
			this.qtt = Integer.valueOf(l.split(" ")[1].trim());
		}

		@Override
		public String toString() {
			return dir + " " + qtt;
		}

	}

	private class Extremite {
		String type;
		Position curPos;
		Position lastPos;
		Set<Position> visite;
		String lastMove;

		public String getLastMove() {
			return lastMove;
		}

		public void diag(Extremite head) {
			if (head.curPos.a > curPos.a) {
				if (head.curPos.o > curPos.o) {
					curPos.setO(curPos.getO() + 1);
					curPos.setA(curPos.getA() + 1);
				} else {
					curPos.setO(curPos.getO() - 1);
					curPos.setA(curPos.getA() + 1);
				}
			} else {
				if (head.curPos.o > curPos.o) {
					curPos.setO(curPos.getO() + 1);
					curPos.setA(curPos.getA() - 1);
				} else {
					curPos.setO(curPos.getO()- 1);
					curPos.setA(curPos.getA() - 1);
				}
			}
		}

		public Optional<Extremite> getExtremite(List<Extremite> extremites, long i, long j) {
			for (Extremite e : extremites) {
				if (e.curPos.a == i && e.curPos.o == j) {
					return Optional.ofNullable(e);
				}
			}
			return Optional.empty();
		}

		public boolean estLoin(Extremite tail) {
			return (Math.abs(curPos.a - tail.curPos.a) > 1 || Math.abs(curPos.o - tail.curPos.o) > 1);

		}

		public void setLastMove(String lastMove) {
			this.lastMove = lastMove;
		}

		public String getType() {
			return type;
		}

		public void monter() {
			lastMove = "U";
			curPos.setO(curPos.getO() + 1);
		}

		public void descendre() {
			lastMove = "D";
			curPos.setO(curPos.getO() - 1);
		}

		public void droite() {
			lastMove = "R";
			curPos.setA(curPos.getA() + 1);
		}

		public void gauche() {
			lastMove = "L";
			curPos.setA(curPos.getA() - 1);
		}

		public void setType(String type) {
			this.type = type;
		}

		public Position getCurPos() {
			return curPos;
		}

		public void setCurPos(Position curPos) {
			this.curPos = curPos;
		}

		public Position getLastPos() {
			return lastPos;
		}

		public void setLastPos(Position lastPos) {
			this.lastPos = lastPos;
		}

		public Set<Position> getVisite() {
			return visite;
		}

		public void setVisite(Set<Position> visite) {
			this.visite = visite;
		}

		public Extremite(String type, Position curPos, Position lastPos) {
			super();
			this.type = type;
			this.curPos = curPos;
			this.lastPos = lastPos;
			this.visite = new HashSet<>();
			visite.add(new Position(0L, 0L));
		}

		public Extremite() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return type + " [" + curPos + "]";
		}

	}

	private class Position {
		Long a;
		Long o;

		public Long getA() {
			return a;
		}

		public void setA(Long a) {
			this.a = a;
		}

		public Long getO() {
			return o;
		}

		public void setO(Long o) {
			this.o = o;
		}

		public Position(Long a, Long o) {
			super();
			this.a = a;
			this.o = o;
		}

		public Position(Position curPos) {
			this.a = curPos.a;
			this.o = curPos.o;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(a, o);
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
			Position other = (Position) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Objects.equals(a, other.a) && Objects.equals(o, other.o);
		}

		@Override
		public String toString() {
			return a + "," + o;
		}

		private A2022Day09 getEnclosingInstance() {
			return A2022Day09.this;
		}

	}

	public static List<Long> getDuration() {
		A2022Day09 d = new A2022Day09(9);
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

package a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2021Day25 extends A2021 {

	public A2021Day25(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day25 d = new A2021Day25(25);
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

	private int s1(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<SeaC> seacs = new ArrayList<>();
		int j = 0;
		for (String l : lignes) {
			int max = l.length();
			for (int pos = 0; pos < max; pos++) {
				if (l.substring(pos, pos + 1).equals(">")) {
					seacs.add(new SeaC(pos, j, ">", false));
				} else if (l.substring(pos, pos + 1).equals("v")) {
					seacs.add(new SeaC(pos, j, "v", false));
				} else if (l.substring(pos, pos + 1).equals(".")) {
					seacs.add(new SeaC(pos, j, ".", false));
				}
			}
			j++;
		}
		Situation s = new Situation(seacs, true, 0);
		while (s.abouge) {
			s.move();
			//System.out.println(SeaC.listString(s.seacs));
			System.out.println(s.nbStep);
		}

		return s.nbStep;
	}

	private int s2(boolean b) {
		return 0;
	}

	public static class Situation {

		public int nbStep;
		int xmax;
		int ymax;

		public Situation(List<SeaC> seacs, boolean abouge, int i) {
			super();
			this.seacs = seacs;
			this.abouge = abouge;
			this.nbStep = i;
			this.xmax = MesOutils.getMaxIntegerFromList(seacs.stream().map(SeaC::getX).collect(Collectors.toList()));
			this.ymax = MesOutils.getMaxIntegerFromList(seacs.stream().map(SeaC::getY).collect(Collectors.toList()));

		}

		public void move() {
			List<SeaC> newSeacs = new ArrayList<>();
			List<SeaC> newSeacsTMP = new ArrayList<>();

			for (SeaC sc : seacs.stream().filter(d -> d.type.equals(">")).collect(Collectors.toList())) {

				if (sc.x == xmax) {
					if (!(seacs.contains(new SeaC(0, sc.y, ">", false))
							|| seacs.contains(new SeaC(0, sc.y, "v", false)))) {
						newSeacsTMP.add(new SeaC(0, sc.y, sc.type, true));
					} else {
						sc.moved = false;
						newSeacsTMP.add(sc);
					}
				} else {
					if (!(seacs.contains(new SeaC(sc.x + 1, sc.y, ">", false))
							|| seacs.contains(new SeaC(sc.x + 1, sc.y, "v", false)))) {
						newSeacsTMP.add(new SeaC(sc.x + 1, sc.y, sc.type, true));
					} else {
						sc.moved = false;
						newSeacsTMP.add(sc);
					}

				}
			}
			newSeacsTMP.addAll(seacs.stream().filter(d -> d.type.equals("v")).collect(Collectors.toList()));
			newSeacs.addAll(newSeacsTMP.stream().filter(d -> d.type.equals(">")).collect(Collectors.toList()));
			
			for (SeaC sc : newSeacsTMP.stream().filter(d -> d.type.equals("v")).collect(Collectors.toList())) {

				if (sc.y == ymax) {
					if (!(newSeacsTMP.contains(new SeaC(sc.x, 0, ">", false))
							|| newSeacsTMP.contains(new SeaC(sc.x, 0, "v", false)))) {
						newSeacs.add(new SeaC(sc.x, 0, sc.type, true));
					} else {
						sc.moved = false;
						newSeacs.add(sc);
					}
				} else {
					if (!(newSeacsTMP.contains(new SeaC(sc.x, sc.y + 1, ">", false))
							|| newSeacsTMP.contains(new SeaC(sc.x, sc.y + 1, "v", false)))) {
						newSeacs.add(new SeaC(sc.x, sc.y + 1, sc.type, true));
					} else {
						sc.moved = false;
						newSeacs.add(sc);
					}
				}
			}

			this.seacs = newSeacs;
			this.nbStep++;
			this.abouge = seacs.stream().anyMatch(x -> x.moved);
		}

		List<SeaC> seacs;
		boolean abouge;
	}

	public static class SeaC {
		int x;
		int y;
		boolean moved;

		public boolean isMove() {
			return moved;
		}

		public void setMove(boolean moved) {
			this.moved = moved;
		}

		@Override
		public String toString() {
			return "SeaC [x=" + x + ", y=" + y + ", type=" + type + "]";
		}

		String type;

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

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public SeaC(int x, int y, String type, boolean b) {
			super();
			this.x = x;
			this.y = y;
			this.type = type;
			this.moved = b;
		}

		@Override
		public int hashCode() {
			return Objects.hash(type, x, y);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SeaC other = (SeaC) obj;
			return Objects.equals(type, other.type) && x == other.x && y == other.y;
		}

		public static String listString(List<SeaC> sc) {
			StringBuilder res = new StringBuilder();
			// pts.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
			int imax = MesOutils.getMaxIntegerFromList(sc.stream().map(SeaC::getX).collect(Collectors.toList()));
			int imin = MesOutils.getMinIntegerFromList(sc.stream().map(SeaC::getX).collect(Collectors.toList()));
			int jmax = MesOutils.getMaxIntegerFromList(sc.stream().map(SeaC::getY).collect(Collectors.toList()));
			int jmin = MesOutils.getMinIntegerFromList(sc.stream().map(SeaC::getY).collect(Collectors.toList()));
			for (int j = jmin; j <= jmax; j++) {
				for (int i = imin; i <= imax; i++) {
					SeaC.getSeaC(sc, i, j).ifPresentOrElse(pt -> res.append(pt.type), () -> res.append("."));

				}
				res.append("\n");
			}
			return res.toString();
		}

		public static Optional<SeaC> getSeaC(List<SeaC> sc, int x, int y) {
			SeaC p = null;
			for (SeaC i : sc) {
				if (x == i.x && y == i.y) {
					p = new SeaC(x, y, i.type, false);
				}
			}
			return Optional.ofNullable(p);
		}

	}

	public static List<Long> getDuration() {
		A2021Day25 d = new A2021Day25(25);
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

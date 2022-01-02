package aocmaven.a2021;

import java.util.ArrayList;
import java.util.Arrays;
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
		System.out.println(d.s1(false));
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
					seacs.add(new SeaC(pos, j, ">"));
				} else if (l.substring(pos, pos + 1).equals("v")) {
					seacs.add(new SeaC(pos, j, "v"));
				} else if (l.substring(pos, pos + 1).equals(".")) {
					seacs.add(new SeaC(pos, j, "."));
				}
			}
			j++;
		}
		Situation s = new Situation(seacs, true, 0);
		System.out.println(SeaC.listString(seacs));
		int xmax = MesOutils.getMaxIntegerFromList(seacs.stream().map(SeaC::getX).collect(Collectors.toList()));
		int ymax = MesOutils.getMaxIntegerFromList(seacs.stream().map(SeaC::getY).collect(Collectors.toList()));
		while (s.abouge) {
			s.nextStepE(xmax, ymax);
			s.nextStepS(xmax, ymax);
			System.out.println(s.nbStep);
			System.out.println(SeaC.listString(seacs));
		}

		return s.nbStep;
	}

	private int s2(boolean b) {
		return 0;
	}

	public static class Situation {

		public int nbStep;

		public Situation(List<SeaC> seacs, boolean abouge, int i) {
			super();
			this.seacs = seacs;
			this.abouge = abouge;
			this.nbStep = i;
		}

		public void nextStepE(int xmax, int ymax) {
			System.out.println(SeaC.listString(seacs));
			List<SeaC> newSeacs = seacs.stream().filter(y->y.type.equals("v")).collect(Collectors.toList());
			System.out.println(SeaC.listString(newSeacs));
			List<SeaC> canMove = canMove(">", seacs, xmax, ymax);
			List<SeaC> canTMove = canTMove(">", seacs, xmax, ymax);
			newSeacs.addAll(canTMove);
			System.out.println(SeaC.listString(newSeacs));

			for (SeaC sc : canMove) {
				newSeacs.add(sc.move(xmax, ymax));
			}
			System.out.println(SeaC.listString(newSeacs));
			if (canMove.isEmpty()) {
				abouge = false;
			}
			this.seacs=newSeacs;
			System.out.println(SeaC.listString(seacs));
			}

		public void nextStepS(int xmax, int ymax) {
			System.out.println(SeaC.listString(seacs));
			List<SeaC> newSeacs = seacs.stream().filter(y->y.type.equals(">")).collect(Collectors.toList());
			List<SeaC> canMove = canMove("v", seacs, xmax, ymax);
			List<SeaC> canTMove = canTMove("v", seacs, xmax, ymax);
			newSeacs.addAll(canTMove);
			for (SeaC sc : canMove) {
				newSeacs.add(sc.move(xmax, ymax));
			}
			System.out.println(SeaC.listString(newSeacs));
			if (canMove.isEmpty() && !abouge) {
				abouge = false;
			}
			this.seacs = newSeacs;
			this.nbStep++;
		}

		private List<SeaC> canTMove(String type, List<SeaC> cc, int xmax, int ymax) {
			List<SeaC> canTMove = new ArrayList<>();
			for (SeaC sc : cc) {
				if (sc.type.equals(type)) {
					if (sc.type.equals(">")) {
						if (sc.x == xmax) {
							if ((cc.contains(new SeaC(0, sc.y, ">")) || cc.contains(new SeaC(0, sc.y, "v")))) {
								canTMove.add(sc);
							}
						} else {
							if ((cc.contains(new SeaC(sc.x + 1, sc.y, ">"))|| cc.contains(new SeaC(sc.x + 1, sc.y, "v")))) {
								canTMove.add(sc);
							}
						}
					} else if (sc.type.equals("v")) {
						if (sc.y == ymax) {
							if ((cc.contains(new SeaC(sc.x, 0, ">"))||cc.contains(new SeaC(sc.x, 0, "v")))) {
								canTMove.add(sc);
							}
						} else {
							if ((cc.contains(new SeaC(sc.x, sc.y + 1, ">"))|| cc.contains(new SeaC(sc.x, sc.y + 1, "v")))) {
								canTMove.add(sc);
							}
						}
					}
				}
			}
			return canTMove;
		}

		private List<SeaC> canMove(String type, List<SeaC> cc, int xmax, int ymax) {
			List<SeaC> canMove = new ArrayList<>();
			for (SeaC sc : cc) {
				if (sc.type.equals(type)) {
					if (sc.type.equals(">")) {
						if (sc.x == xmax) {
							if (!(cc.contains(new SeaC(0, sc.y, ">")) || cc.contains(new SeaC(0, sc.y, "v")))) {
								canMove.add(sc);
							}
						} else {
							if (!(cc.contains(new SeaC(sc.x + 1, sc.y, ">"))|| cc.contains(new SeaC(sc.x + 1, sc.y, "v")))) {
								canMove.add(sc);
							}
						}
					} else if (sc.type.equals("v")) {
						if (sc.y == ymax) {
							if (!(cc.contains(new SeaC(sc.x, 0, ">"))||cc.contains(new SeaC(sc.x, 0, "v")))) {
								canMove.add(sc);
							}
						} else {
							if (!(cc.contains(new SeaC(sc.x, sc.y + 1, ">"))|| cc.contains(new SeaC(sc.x, sc.y + 1, "v")))) {
								canMove.add(sc);
							}
						}
					}
				}
			}
			return canMove;
		}

		List<SeaC> seacs;
		boolean abouge;
	}

	public static class SeaC {
		int x;
		int y;

		@Override
		public String toString() {
			return "SeaC [x=" + x + ", y=" + y + ", type=" + type + "]";
		}

		String type;

		public int getX() {
			return x;
		}

		public SeaC move(int xmax, int ymax) {
			if (type.equals(">")) {
				if (x == xmax) {
					return new SeaC(0, y, type);
				} else {
					return new SeaC(x + 1, y, type);
				}
			} else if (type.equals("v")) {
				if (y == ymax) {
					return new SeaC(0, y, type);
				} else {
					return new SeaC(x, y + 1, type);
				}
			}
			return null;
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

		public SeaC(int x, int y, String type) {
			super();
			this.x = x;
			this.y = y;
			this.type = type;
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
					p = new SeaC(x, y, i.type);
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

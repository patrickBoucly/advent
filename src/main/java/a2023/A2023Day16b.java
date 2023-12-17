package a2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import outils.MesOutils;

public class A2023Day16b extends A2023 {

	public A2023Day16b(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day16b d = new A2023Day16b(16);
		System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		// d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public int s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		TheGame tg = getTheGame(inputL);
		tg.deplacerBeams();
		Set<Point> vu=new HashSet<>();
		for(Point p:tg.beams.stream().map(k->k.getPos()).toList()) {
			vu.add(p);
		}
		return vu.size();
	}

	private TheGame getTheGame(List<String> inputL) {
		TheGame tg = new TheGame();
		Grille gr = new Grille();
		Set<Point> points = new HashSet<>();
		for (int l = 0; l < inputL.size(); l++) {
			for (int c = 0; c < inputL.get(0).length() - 1; c++) {
				Point p = new Point();
				p.setX(c);
				p.setY(l);
				p.setInfo(inputL.get(l).substring(c, c + 1));

				points.add(p);
			}
		}
		gr.setPoints(points);
		tg.setGr(gr);
		Set<Beam> beams = new HashSet<>();
		Beam b = new Beam();
		Point p = getPoint(points, 0, 0).get();
		b.setPos(new Point(p.x, p.y, ">"));
		beams.add(b);
		Map<Beam, Integer> dejaVu = new HashMap<>();
		Beam b2 = new Beam();
		b2.setPos(new Point(0, 0, ">"));
		dejaVu.put(b2, 1);
		tg.setDejaVu(dejaVu);
		tg.setBeams(beams);
		tg.cmax = inputL.get(0).length() - 1;
		tg.lmax = inputL.size() - 1;
		return tg;
	}

	public Long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		TheGame tg = getTheGame(inputL);
		Long res = 0L;
		int nbPbord = 0;
		for (Point p : tg.gr.points) {
			TheGame tg2 = null;
			if (p.x == 0) {
				nbPbord++;
				System.out.println("départ du point: " + p + " direction >, indice " + nbPbord);
				Beam depart = new Beam();
				Point np = new Point();
				np.setX(p.x);
				np.setY(p.y);
				np.setInfo(">");
				depart.setPos(np);
				tg2 = getTheGame(inputL);
				tg2.beams = new HashSet<>();
				tg2.beams.add(depart);
				tg2.deplacerBeams();
				Set<Point> vu=new HashSet<>();
				for(Point q:tg2.beams.stream().map(k->k.getPos()).toList()) {
					vu.add(q);
				}
				System.out.println(p + " " + vu.size());
				if (vu.size() > res) {
					res = (long) vu.size();
					System.out.println("noueau max : " + res);
				}

			}
			if (p.y == 0) {
				nbPbord++;
				System.out.println("départ du point: " + p + " direction v, indice " + nbPbord);
				Beam depart = new Beam();
				Point np = new Point();
				np.setX(p.x);
				np.setY(p.y);
				np.setInfo("v");
				depart.setPos(np);
				tg2 = getTheGame(inputL);
				tg2.beams = new HashSet<>();
				tg2.beams.add(depart);
				tg2.deplacerBeams();
				Set<Point> vu=new HashSet<>();
				for(Point q:tg2.beams.stream().map(k->k.getPos()).toList()) {
					vu.add(q);
				}
				System.out.println(p + " " + vu.size());
				if (vu.size() > res) {
					res = (long) vu.size();
					System.out.println("noueau max : " + res);
				}
			}
			if (p.x == tg.cmax - 1) {
				nbPbord++;
				System.out.println("départ du point: " + p + " direction <, indice " + nbPbord);
				Beam depart = new Beam();
				Point np = new Point();
				np.setX(p.x);
				np.setY(p.y);
				np.setInfo("<");
				depart.setPos(np);
				tg2 = getTheGame(inputL);
				tg2.beams = new HashSet<>();
				tg2.beams.add(depart);
				tg2.deplacerBeams();
				Set<Point> vu=new HashSet<>();
				for(Point q:tg2.beams.stream().map(k->k.getPos()).toList()) {
					vu.add(q);
				}
				System.out.println(p + " " + vu.size());
				if (vu.size() > res) {
					res = (long) vu.size();
					System.out.println("noueau max : " + res);
				}
			}
			if (p.y == tg.lmax ) {
				nbPbord++;
				System.out.println("départ du point: " + p + " direction ^, indice " + nbPbord);
				Beam depart = new Beam();
				Point np = new Point();
				np.setX(p.x);
				np.setY(p.y);
				np.setInfo("^");
				depart.setPos(np);
				tg2 = getTheGame(inputL);
				tg2.beams = new HashSet<>();
				tg2.beams.add(depart);

				tg2.deplacerBeams();
				Set<Point> vu=new HashSet<>();
				for(Point q:tg2.beams.stream().map(k->k.getPos()).toList()) {
					vu.add(q);
				}
				System.out.println(p + " " + vu.size());
				if (vu.size() > res) {
					res = (long) vu.size();
					System.out.println("noueau max : " + res);
				}
			}

		}

		return res;

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	private static class TheGame {
		int cmax;
		int lmax;
		public void deplacerBeams() {
			while (!beams.stream().filter(Beam::isJustAdded).toList().isEmpty()) {
				for (Beam r : beams.stream().filter(Beam::isJustAdded).toList()) {
					beams.addAll(getNextBeams(r));
					r.justAdded=false;
				}
			}

		}

		private Set<Beam> getNextBeams(Beam b) {
			String dir = b.pos.info;
			if (dir.equals(">")) {
				return getNextBeamGoEast(b);
			}
			if (dir.equals("<")) {
				return getNextBeamGoWest(b);
			}
			if (dir.equals("^")) {
				return getNextBeamGoNorth(b);
			}
			if (dir.equals("v")) {
				return getNextBeamGoSouth(b);
			}
			return null;
		}

		private Set<Beam> getNextBeamGoSouth(Beam b) {
			Set<Beam> nextBeams = new HashSet<>();
			Beam b1 = new Beam();
			Beam b2 = new Beam();
			Point ptG = getPoint(gr.points, b.pos.x, b.pos.y).get();
			String mir = ptG.info;
			if (mir.equals(".") || mir.equals("|")) {
				Optional<Point> pt1 = getPoint(gr.points, b.pos.x, b.pos.y + 1);
				if (pt1.isEmpty()) {
					return nextBeams;
				} else {
					Point pt1b = new Point(pt1.get().x, pt1.get().y, "v");
					b1.pos = pt1b;
					nextBeams.add(b1);
					return nextBeams;
				}
			} else if (mir.equals("\\")) {
				Optional<Point> pt1 = getPoint(gr.points, b.pos.x + 1, b.pos.y);
				if (pt1.isEmpty()) {
					return nextBeams;
				} else {
					Point pt1b = new Point(pt1.get().x, pt1.get().y, ">");
					b1.pos = pt1b;
					nextBeams.add(b1);
					return nextBeams;
				}
			} else if (mir.equals("/")) {
				Optional<Point> pt1 = getPoint(gr.points, b.pos.x - 1, b.pos.y);
				if (pt1.isEmpty()) {
					return nextBeams;
				} else {
					Point pt1b = new Point(pt1.get().x, pt1.get().y, "<");
					b1.pos = pt1b;
					nextBeams.add(b1);
					return nextBeams;
				}
			} else if (mir.equals("-")) {
				Optional<Point> pt1 = getPoint(gr.points, b.pos.x + 1, b.pos.y);
				if (!pt1.isEmpty()) {
					Point pt1b = new Point(pt1.get().x, pt1.get().y, ">");
					b1.pos = pt1b;
					nextBeams.add(b1);
				}
				Optional<Point> pt2 = getPoint(gr.points, b.pos.x - 1, b.pos.y);
				if (!pt2.isEmpty()) {
					Point pt2b = new Point(pt2.get().x, pt2.get().y, "<");
					b2.pos = pt2b;
					nextBeams.add(b2);
				}
				return nextBeams;
			}
			return nextBeams;
		}

		private Set<Beam> getNextBeamGoNorth(Beam b) {
			Set<Beam> nextBeams = new HashSet<>();
			Beam b1 = new Beam();
			Beam b2 = new Beam();
			Point ptG = getPoint(gr.points, b.pos.x, b.pos.y).get();
			String mir = ptG.info;
			if (mir.equals(".") || mir.equals("|")) {
				Optional<Point> pt1 = getPoint(gr.points, b.pos.x, b.pos.y - 1);
				if (pt1.isEmpty()) {
					return nextBeams;
				} else {
					Point pt1b = new Point(pt1.get().x, pt1.get().y, "^");
					b1.pos = pt1b;
					nextBeams.add(b1);
					return nextBeams;
				}
			} else if (mir.equals("\\")) {
				Optional<Point> pt1 = getPoint(gr.points, b.pos.x - 1, b.pos.y);
				if (pt1.isEmpty()) {
					return nextBeams;
				} else {
					Point pt1b = new Point(pt1.get().x, pt1.get().y, "<");
					b1.pos = pt1b;
					nextBeams.add(b1);
					return nextBeams;
				}
			} else if (mir.equals("/")) {
				Optional<Point> pt1 = getPoint(gr.points, b.pos.x + 1, b.pos.y);
				if (pt1.isEmpty()) {
					return nextBeams;
				} else {
					Point pt1b = new Point(pt1.get().x, pt1.get().y, ">");
					b1.pos = pt1b;
					nextBeams.add(b1);
					return nextBeams;
				}
			} else if (mir.equals("-")) {
				Optional<Point> pt1 = getPoint(gr.points, b.pos.x + 1, b.pos.y);
				if (!pt1.isEmpty()) {
					Point pt1b = new Point(pt1.get().x, pt1.get().y, ">");
					b1.pos = pt1b;
					nextBeams.add(b1);
				}
				Optional<Point> pt2 = getPoint(gr.points, b.pos.x - 1, b.pos.y);
				if (!pt2.isEmpty()) {
					Point pt2b = new Point(pt2.get().x, pt2.get().y, "<");
					b2.pos = pt2b;
					nextBeams.add(b2);
				}
				return nextBeams;
			}
			return nextBeams;
		}

		private Set<Beam> getNextBeamGoWest(Beam b) {
			Set<Beam> nextBeams = new HashSet<>();
			Beam b1 = new Beam();
			Beam b2 = new Beam();
			Point ptG = getPoint(gr.points, b.pos.x, b.pos.y).get();
			String mir = ptG.info;
			if (mir.equals(".") || mir.equals("-")) {
				Optional<Point> pt1 = getPoint(gr.points, b.pos.x - 1, b.pos.y);
				if (pt1.isEmpty()) {
					return nextBeams;
				} else {
					Point pt1b = new Point(pt1.get().x, pt1.get().y, "<");
					b1.pos = pt1b;
					nextBeams.add(b1);
					return nextBeams;
				}
			} else if (mir.equals("\\")) {
				Optional<Point> pt1 = getPoint(gr.points, b.pos.x, b.pos.y - 1);
				if (pt1.isEmpty()) {
					return nextBeams;
				} else {
					Point pt1b = new Point(pt1.get().x, pt1.get().y, "^");
					b1.pos = pt1b;
					nextBeams.add(b1);
					return nextBeams;
				}
			} else if (mir.equals("/")) {
				Optional<Point> pt1 = getPoint(gr.points, b.pos.x, b.pos.y + 1);
				if (pt1.isEmpty()) {
					return nextBeams;
				} else {
					Point pt1b = new Point(pt1.get().x, pt1.get().y, "v");
					b1.pos = pt1b;
					nextBeams.add(b1);
					return nextBeams;
				}
			} else if (mir.equals("|")) {
				Optional<Point> pt1 = getPoint(gr.points, b.pos.x, b.pos.y + 1);
				if (!pt1.isEmpty()) {
					Point pt1b = new Point(pt1.get().x, pt1.get().y, "v");
					b1.pos = pt1b;
					nextBeams.add(b1);
				}
				Optional<Point> pt2 = getPoint(gr.points, b.pos.x, b.pos.y - 1);
				if (!pt2.isEmpty()) {
					Point pt2b = new Point(pt2.get().x, pt2.get().y, "^");
					b2.pos = pt2b;
					nextBeams.add(b2);
				}
				return nextBeams;
			}
			return nextBeams;
		}

		private Set<Beam> getNextBeamGoEast(Beam b) {
			Set<Beam> nextBeams = new HashSet<>();
			Beam b1 = new Beam();
			Beam b2 = new Beam();
			Point ptG = getPoint(gr.points, b.pos.x, b.pos.y).get();
			String mir = ptG.info;
			if (mir.equals(".") || mir.equals("-")) {
				Optional<Point> pt1 = getPoint(gr.points, b.pos.x + 1, b.pos.y);
				if (pt1.isEmpty()) {
					return nextBeams;
				} else {
					Point pt1b = new Point(pt1.get().x, pt1.get().y, ">");
					b1.pos = pt1b;
					nextBeams.add(b1);
					return nextBeams;
				}
			} else if (mir.equals("\\")) {
				Optional<Point> pt1 = getPoint(gr.points, b.pos.x, b.pos.y + 1);
				if (pt1.isEmpty()) {
					return nextBeams;
				} else {
					Point pt1b = new Point(pt1.get().x, pt1.get().y, "v");
					b1.pos = pt1b;
					nextBeams.add(b1);
					return nextBeams;
				}
			} else if (mir.equals("/")) {
				Optional<Point> pt1 = getPoint(gr.points, b.pos.x, b.pos.y - 1);
				if (pt1.isEmpty()) {
					return nextBeams;
				} else {
					Point pt1b = new Point(pt1.get().x, pt1.get().y, "^");
					b1.pos = pt1b;
					nextBeams.add(b1);
					return nextBeams;
				}
			} else if (mir.equals("|")) {
				Optional<Point> pt1 = getPoint(gr.points, b.pos.x, b.pos.y + 1);
				if (!pt1.isEmpty()) {
					Point pt1b = new Point(pt1.get().x, pt1.get().y, "v");
					b1.pos = pt1b;
					nextBeams.add(b1);
				}
				Optional<Point> pt2 = getPoint(gr.points, b.pos.x, b.pos.y - 1);
				if (!pt2.isEmpty()) {
					Point pt2b = new Point(pt2.get().x, pt2.get().y, "^");
					b2.pos = pt2b;
					nextBeams.add(b2);
				}
				return nextBeams;
			}
			return nextBeams;
		}

	

		Grille gr;
		Set<Beam> beams;
		Map<Beam, Integer> dejaVu;

		@Override

		public String toString() {
			StringBuilder res = new StringBuilder();
			int imax = MesOutils
					.getMaxIntegerFromList(gr.points.stream().map(Point::getX).collect(Collectors.toList()));
			int jmax = MesOutils
					.getMaxIntegerFromList(gr.points.stream().map(Point::getY).collect(Collectors.toList()));
			for (int j = 0; j <= jmax; j++) {
				for (int i = 0; i <= imax; i++) {
					Beam isCurBeam = new Beam(new Point(i, j, ""),true);
					if (beams.stream().map(b -> b.pos).toList().contains(isCurBeam.pos)) {

						for (Beam be : beams) {
							if (be.equals(isCurBeam)) {
								String afficher = "";
								int cpt = 0;
								if (be.pos.equals(isCurBeam.pos)) {
									if (afficher.equals("")) {
										afficher = be.pos.info;
										cpt++;
									} else {
										cpt++;
									}
								}
								if (cpt == 1) {
									res.append(afficher);
								} else {
									res.append("" + cpt);
								}
							}
						}
					} else if (!getPoint(gr.points, i, j).get().info.equals(".")) {
						res.append(getPoint(gr.points, i, j).get().info);
					} else if (dejaVu.keySet().stream().map(k->k.pos).toList().contains(isCurBeam.pos)) {
						for (Beam be : dejaVu.keySet()) {
							if (be.pos.equals(isCurBeam.pos)) {
								if (dejaVu.get(be) > 1) {
									res.append(be.pos.info);
									// res.append(""+dejaVu.get(be));
								} else {
									res.append(be.pos.info);
								}
							}
						}
					} else {
						if (getPoint(gr.points, i, j).isPresent()) {
							res.append(getPoint(gr.points, i, j).get().info);

						}

					}
				}
				res.append("\n");
			}
			return res.toString();
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	private static class Grille {
		Set<Point> points;

		@Override

		public String toString() {
			StringBuilder res = new StringBuilder();
			int imax = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getX).collect(Collectors.toList()));
			int jmax = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getY).collect(Collectors.toList()));
			for (int j = 0; j <= jmax; j++) {
				for (int i = 0; i <= imax; i++) {
					getPoint(points, i, j).ifPresentOrElse(pt -> res.append(pt.info), () -> res.append("."));
				}
				res.append("\n");
			}
			return res.toString();
		}

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	private static class Point {
		int x;
		int y;
		String info;

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
			Point other = (Point) obj;
			return x == other.x && y == other.y;
		}

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	private static class Beam {
		Point pos;
		boolean justAdded=true;
		@Override
		public int hashCode() {
			return Objects.hash(pos);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Beam other = (Beam) obj;
			return Objects.equals(pos, other.pos) && pos.info.equals(other.pos.info);
		}
		
	}

	public static Optional<Point> getPoint(Set<Point> pts, int x, int y) {
		Point p = null;
		for (Point i : pts) {
			if (x == i.x && y == i.y) {
				return Optional.ofNullable(i);
			}
		}
		return Optional.ofNullable(p);
	}

	public static List<Long> getDuration() {
		A2023Day16b d = new A2023Day16b(1);
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

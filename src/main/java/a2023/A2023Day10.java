package a2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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

public class A2023Day10 extends A2023 {

	public A2023Day10(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day10 d = new A2023Day10(10);
	//	System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		//System.out.println(d.s2(true));
		System.out.println(d.s3(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public double s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		TheGame tg = getGameFromInput(inputL);
		tg.findLoop();
		return Math.floor((tg.getLoop().size()) / 2);
	}

	private TheGame getGameFromInput(List<String> inputL) {
		Set<Point> grille = new HashSet<>();
		List<Point> loop = new ArrayList<>();
		TheGame tg = new TheGame();
		int lmax = inputL.size();
		int cmax = inputL.get(0).length();
		for (int l = 0; l < lmax; l++) {
			for (int c = 0; c < cmax - 1; c++) {
				Point p = new Point(c, l, inputL.get(l).substring(c, c + 1), 0, 0, 0, 0);
				if (p.info.equals("S")) {
					loop.add(p);
					tg.setCurPoint(p);
				}
				grille.add(p);
			}
		}
		tg.setGrille(grille);
		tg.setLoop(loop);
		return tg;
	}

	public long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		TheGame tg = getGameFromInput(inputL);
		tg.findLoop();
		tg.replaceFakePipe();
		tg.findInOut();
		return tg.grille.stream().filter(p -> p.info.equals("I") || p.info.equals("K")).count();

	}
	public long s3(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		TheGame tg = getGameFromInput(inputL);
		tg.findLoop();
		return tg.shoelace()+1-tg.loop.size()/2;

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class TheGame {
		public void findLoop() {
			while (loop.stream().filter(p -> p.info.equals("S")).count() < 2L) {
				findNext();
			}
		}

		public Long shoelace() {
			Long sum=0L;
			for(int i=0;i<loop.size()-1;i++) {
				sum+=(loop.get(i+1).x+loop.get(i).x)*(loop.get(i+1).y-loop.get(i).y);
			}
			return sum/2;
		}

		public void replaceFakePipe() {
			for (Point p : grille) {
				if (Arrays.asList("F", "J", "-", "|", "7", "L").contains(p.info) && !loop.contains(p)) {
					p.info = ".";
				}
			}
		}

		public void findInOut() {
			mettreLesOBordure();
			mettreLesK2();
			mettreLeursVoisinsEnK();
		}

		private void mettreLesK2() {
			List<Point> tour = new ArrayList<>(loop);
			String inside = "down";
			int debut = 0;
			tour.addAll(loop);
			for (int j = 0; j < tour.size(); j++) {
				if (getPoint(grille, tour.get(j).x, tour.get(j).y - 1).isPresent() && tour.get(j).info.equals("-")
						&& getPoint(grille, tour.get(j).x, tour.get(j).y - 1).get().info.equals("O")) {
					debut = j;
					curPoint = tour.get(j);
					break;
				}
			}
			for (int l = debut; l < debut + loop.size(); l++) {
				curPoint = tour.get(l);
				if (l == debut) {
					inside = "down";
				} else {
					inside = setInsideValue(inside);
				}

				String curL = curPoint.info;
				if (curL.equals("-")) {
					if (inside.equals("up")) {
						Optional<Point> pointToCheck = getPoint(grille, curPoint.x, curPoint.y - 1);
						if (pointToCheck.isPresent() && pointToCheck.get().info.equals(".")) {
							pointToCheck.get().info = "K";
						}
					}
					if (inside.equals("down")) {
						Optional<Point> pointToCheck = getPoint(grille, curPoint.x, curPoint.y + 1);
						if (pointToCheck.isPresent() && pointToCheck.get().info.equals(".")) {
							pointToCheck.get().info = "K";
						}
					}
				}
				if (curL.equals("|")) {
					if (inside.equals("rigth")) {
						Optional<Point> pointToCheck = getPoint(grille, curPoint.x + 1, curPoint.y);
						if (pointToCheck.isPresent() && pointToCheck.get().info.equals(".")) {
							pointToCheck.get().info = "K";
						}
					}
					if (inside.equals("left")) {
						Optional<Point> pointToCheck = getPoint(grille, curPoint.x - 1, curPoint.y);
						if (pointToCheck.isPresent() && pointToCheck.get().info.equals(".")) {
							pointToCheck.get().info = "K";
						}
					}
				}
				if (curL.equals("F")) {
					if (inside.equals("rigth") || inside.equals("down")) {
						Optional<Point> pointToCheck = getPoint(grille, curPoint.x + 1, curPoint.y + 1);
						if (pointToCheck.isPresent() && pointToCheck.get().info.equals(".")) {
							pointToCheck.get().info = "K";
						}
					}
					if (inside.equals("left") || inside.equals("up")) {
						Optional<Point> pointToCheck = getPoint(grille, curPoint.x - 1, curPoint.y);
						if (pointToCheck.isPresent() && pointToCheck.get().info.equals(".")) {
							pointToCheck.get().info = "K";
						}
						pointToCheck = getPoint(grille, curPoint.x, curPoint.y - 1);
						if (pointToCheck.isPresent() && pointToCheck.get().info.equals(".")) {
							pointToCheck.get().info = "K";
						}
					}
				}
				if (curL.equals("7")) {
					if (inside.equals("left") || inside.equals("down")) {
						Optional<Point> pointToCheck = getPoint(grille, curPoint.x - 1, curPoint.y + 1);
						if (pointToCheck.isPresent() && pointToCheck.get().info.equals(".")) {
							pointToCheck.get().info = "K";
						}
					}
					if (inside.equals("rigth") || inside.equals("up")) {
						Optional<Point> pointToCheck = getPoint(grille, curPoint.x + 1, curPoint.y);
						if (pointToCheck.isPresent() && pointToCheck.get().info.equals(".")) {
							pointToCheck.get().info = "K";
						}
						pointToCheck = getPoint(grille, curPoint.x, curPoint.y - 1);
						if (pointToCheck.isPresent() && pointToCheck.get().info.equals(".")) {
							pointToCheck.get().info = "K";
						}
					}
				}
				if (curL.equals("L")) {
					if (inside.equals("left") || inside.equals("down")) {
						Optional<Point> pointToCheck = getPoint(grille, curPoint.x - 1, curPoint.y);
						if (pointToCheck.isPresent() && pointToCheck.get().info.equals(".")) {
							pointToCheck.get().info = "K";
						}
						pointToCheck = getPoint(grille, curPoint.x, curPoint.y + 1);
						if (pointToCheck.isPresent() && pointToCheck.get().info.equals(".")) {
							pointToCheck.get().info = "K";
						}
					}
					if (inside.equals("rigth") || inside.equals("up")) {
						Optional<Point> pointToCheck = getPoint(grille, curPoint.x + 1, curPoint.y - 1);
						if (pointToCheck.isPresent() && pointToCheck.get().info.equals(".")) {
							pointToCheck.get().info = "K";

						}

					}
				}
				if (curL.equals("J")) {
					if (inside.equals("rigth") || inside.equals("down")) {
						Optional<Point> pointToCheck = getPoint(grille, curPoint.x + 1, curPoint.y);
						if (pointToCheck.isPresent() && pointToCheck.get().info.equals(".")) {
							pointToCheck.get().info = "K";
						}
						pointToCheck = getPoint(grille, curPoint.x, curPoint.y + 1);
						if (pointToCheck.isPresent() && pointToCheck.get().info.equals(".")) {
							pointToCheck.get().info = "K";

						}
					}
					if (inside.equals("left") || inside.equals("up")) {
						Optional<Point> pointToCheck = getPoint(grille, curPoint.x - 1, curPoint.y - 1);
						if (pointToCheck.isPresent() && pointToCheck.get().info.equals(".")) {
							pointToCheck.get().info = "K";
						}

					}
				}
			}

		}

		private String setInsideValue(String inside) {
			if (inside.equals("rigth") && curPoint.info.equals("F")) {
				return "down";
			}
			if (inside.equals("rigth") && curPoint.info.equals("7")) {
				return "up";
			}

			if (inside.equals("rigth") && curPoint.info.equals("L")) {
				return "up";
			}
			if (inside.equals("rigth") && curPoint.info.equals("J")) {
				return "down";
			}

			if (inside.equals("left") && curPoint.info.equals("L")) {
				return "down";
			}

			if (inside.equals("left") && curPoint.info.equals("J")) {
				return "up";
			}
			if (inside.equals("left") && curPoint.info.equals("7")) {
				return "down";
			}
			if (inside.equals("left") && curPoint.info.equals("F")) {
				return "up";
			}

			if (inside.equals("up") && curPoint.info.equals("L")) {
				return "rigth";
			}
			if (inside.equals("up") && curPoint.info.equals("J")) {
				return "left";
			}
			if (inside.equals("up") && curPoint.info.equals("7")) {
				return "rigth";
			}
			if (inside.equals("up") && curPoint.info.equals("F")) {
				return "left";
			}

			if (inside.equals("down") && curPoint.info.equals("L")) {
				return "left";
			}
			if (inside.equals("down") && curPoint.info.equals("J")) {
				return "rigth";
			}
			if (inside.equals("down") && curPoint.info.equals("7")) {
				return "left";
			}
			if (inside.equals("down") && curPoint.info.equals("F")) {
				return "rigth";
			}
			return inside;
		}

		private void mettreLeursVoisinsEnK() {
			int nbK = grille.stream().filter(pt -> pt.info.equals("K")).toList().size();
			boolean continuer = true;
			while (continuer) {
				for (Point pt : grille.stream().filter(pt -> pt.info.equals("K")).toList()) {
					Optional<Point> p1 = getPoint(grille, pt.x, pt.y - 1);
					Optional<Point> p2 = getPoint(grille, pt.x, pt.y + 1);
					Optional<Point> p3 = getPoint(grille, pt.x + 1, pt.y);
					Optional<Point> p4 = getPoint(grille, pt.x - 1, pt.y);
					if (p1.isPresent() && !loop.contains(p1.get()) && p1.get().info.equals(".")) {
						p1.get().info = "K";
					}
					if (p2.isPresent() && !loop.contains(p2.get()) && p2.get().info.equals(".")) {
						p2.get().info = "K";
					}
					if (p3.isPresent() && !loop.contains(p3.get()) && p3.get().info.equals(".")) {
						p3.get().info = "K";
					}
					if (p4.isPresent() && !loop.contains(p4.get()) && p4.get().info.equals(".")) {
						p4.get().info = "K";
					}

				}
				int newnbK = grille.stream().filter(d -> d.info.equals("K")).toList().size();
				if (newnbK == nbK) {
					continuer = false;
				}
				nbK = newnbK;
			}

		}

		private void mettreLesOBordure() {
			for (Point p : grille.stream().filter(pt -> pt.info.equals(".")).toList()) {
				putNbLoopPartCol(p);
				putNbLoopPartLine(p);
				if (p.nbLoopPartOnColUp * p.nbLoopPartOnColDown * p.nbLoopPartOnLineLeft
						* p.nbLoopPartOnLineRigth == 0) {
					p.info = "O";
				}

			}
			int nbO = grille.stream().filter(pt -> pt.info.equals("O")).toList().size();
			boolean continuer = true;
			while (continuer) {
				for (Point pt : grille.stream().filter(pt -> pt.info.equals("O")).toList()) {
					Optional<Point> p1 = getPoint(grille, pt.x, pt.y - 1);
					Optional<Point> p2 = getPoint(grille, pt.x, pt.y + 1);
					Optional<Point> p3 = getPoint(grille, pt.x + 1, pt.y);
					Optional<Point> p4 = getPoint(grille, pt.x - 1, pt.y);
					if (p1.isPresent() && !loop.contains(p1.get())) {
						p1.get().info = "O";
					}
					if (p2.isPresent() && !loop.contains(p2.get())) {
						p2.get().info = "O";
					}
					if (p3.isPresent() && !loop.contains(p3.get())) {
						p3.get().info = "O";
					}
					if (p4.isPresent() && !loop.contains(p4.get())) {
						p4.get().info = "O";
					}

				}
				int newnbO = grille.stream().filter(d -> d.info.equals("O")).toList().size();
				if (newnbO == nbO) {
					continuer = false;
				}
				nbO = newnbO;
			}
		}

		private void putNbLoopPartLine(Point p) {
			p.nbLoopPartOnLineRigth = 0;
			p.nbLoopPartOnLineLeft = 0;
			for (Point q : grille.stream().filter(pt -> pt.y == p.y).toList()) {
				if (loop.contains(q)) {
					if (q.x < p.x) {
						p.nbLoopPartOnLineLeft++;
					} else {
						p.nbLoopPartOnLineRigth++;
					}
				}
			}
		}

		private void putNbLoopPartCol(Point p) {
			p.nbLoopPartOnColUp = 0;
			p.nbLoopPartOnColDown = 0;
			for (Point q : grille.stream().filter(pt -> pt.x == p.x).toList()) {
				if (loop.contains(q)) {
					if (q.y < p.y) {
						p.nbLoopPartOnColUp++;
					} else {
						p.nbLoopPartOnColDown++;
					}
				}
			}

		}

		private void findNext() {
			Point next = getNext();
			loop.add(next);
			predPoint = curPoint;
			curPoint = next;
		}

		private Point getNext() {
			if (curPoint.info.equals("-")) {
				Optional<Point> p1 = getPoint(grille, curPoint.x + 1, curPoint.y);
				Optional<Point> p2 = getPoint(grille, curPoint.x - 1, curPoint.y);
				if (p1.isPresent() && !p1.get().equals(predPoint) && deplacementADroiteOK(p1)) {
					return p1.get();
				}
				if (p2.isPresent() && !p2.get().equals(predPoint) && deplacementAGaucheOk(p2)) {
					return p2.get();
				}
			}
			if (curPoint.info.equals("|")) {
				Optional<Point> p1 = getPoint(grille, curPoint.x, curPoint.y - 1);
				Optional<Point> p2 = getPoint(grille, curPoint.x, curPoint.y + 1);
				if (p1.isPresent() && !p1.get().equals(predPoint) && deplacementEnHautOk(p1)) {
					return p1.get();
				}
				if (p2.isPresent() && !p2.get().equals(predPoint) && deplacementEnBasOk(p2)) {
					return p2.get();
				}
			}
			if (curPoint.info.equals("F")) {
				Optional<Point> p1 = getPoint(grille, curPoint.x + 1, curPoint.y);
				Optional<Point> p2 = getPoint(grille, curPoint.x, curPoint.y + 1);
				if (p1.isPresent() && !p1.get().equals(predPoint) && deplacementADroiteOK(p1)) {
					return p1.get();
				}
				if (p2.isPresent() && !p2.get().equals(predPoint) && deplacementEnBasOk(p2)) {
					return p2.get();
				}
			}
			if (curPoint.info.equals("J")) {
				Optional<Point> p1 = getPoint(grille, curPoint.x, curPoint.y - 1);
				Optional<Point> p2 = getPoint(grille, curPoint.x - 1, curPoint.y);
				if (p1.isPresent() && !p1.get().equals(predPoint) && deplacementEnHautOk(p1)) {
					return p1.get();
				}
				if (p2.isPresent() && !p2.get().equals(predPoint) && deplacementAGaucheOk(p2)) {
					return p2.get();
				}
			}
			if (curPoint.info.equals("7")) {
				Optional<Point> p1 = getPoint(grille, curPoint.x - 1, curPoint.y);
				Optional<Point> p2 = getPoint(grille, curPoint.x, curPoint.y + 1);
				if (p1.isPresent() && !p1.get().equals(predPoint) && deplacementAGaucheOk(p1)) {
					return p1.get();
				}
				if (p2.isPresent() && !p2.get().equals(predPoint) && deplacementEnBasOk(p2)) {
					return p2.get();
				}
			}
			if (curPoint.info.equals("L")) {
				Optional<Point> p1 = getPoint(grille, curPoint.x, curPoint.y - 1);
				Optional<Point> p2 = getPoint(grille, curPoint.x + 1, curPoint.y);
				if (p1.isPresent() && !p1.get().equals(predPoint) && deplacementEnHautOk(p1)) {
					return p1.get();
				}
				if (p2.isPresent() && !p2.get().equals(predPoint) && deplacementADroiteOK(p2)) {
					return p2.get();
				}

			}
			if (curPoint.info.equals("S")) {
				Optional<Point> p1 = getPoint(grille, curPoint.x, curPoint.y - 1);
				Optional<Point> p2 = getPoint(grille, curPoint.x, curPoint.y + 1);
				Optional<Point> p3 = getPoint(grille, curPoint.x + 1, curPoint.y);
				Optional<Point> p4 = getPoint(grille, curPoint.x - 1, curPoint.y);
				if (p1.isPresent() && deplacementEnHautOk(p1)) {
					return p1.get();
				}
				if (p2.isPresent() && deplacementEnBasOk(p2)) {
					return p2.get();
				}
				if (p3.isPresent() && deplacementADroiteOK(p3)) {
					return p3.get();
				}
				if (p4.isPresent() && deplacementAGaucheOk(p4)) {
					return p4.get();
				}
			}
			return null;
		}

		private boolean deplacementEnBasOk(Optional<Point> p1) {
			return Arrays.asList("|", "L", "J", "S").contains(p1.get().info);
		}

		private boolean deplacementEnHautOk(Optional<Point> p1) {
			return Arrays.asList("|", "7", "F", "S").contains(p1.get().info);
		}

		private boolean deplacementAGaucheOk(Optional<Point> p2) {
			return Arrays.asList("-", "F", "L", "S").contains(p2.get().info);
		}

		private boolean deplacementADroiteOK(Optional<Point> p1) {
			return Arrays.asList("-", "7", "J", "S").contains(p1.get().info);
		}

		Set<Point> grille;
		List<Point> loop;
		Point curPoint;
		Point predPoint;

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			res.append("CurrentPoint : " + curPoint).append("\n").append("Loop : " + loop).append("\n");
			// pts.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
			int imax = MesOutils.getMaxIntegerFromList(grille.stream().map(Point::getX).collect(Collectors.toList()));
			int jmax = MesOutils.getMaxIntegerFromList(grille.stream().map(Point::getY).collect(Collectors.toList()));
			for (int j = 0; j <= jmax; j++) {
				for (int i = 0; i <= imax; i++) {
					Optional<Point> p = getPoint(grille, i, j);
					if (p.isPresent() && curPoint.equals(p.get())) {
						getPoint(grille, i, j).ifPresentOrElse(pt -> res.append("X"), () -> res.append("."));
					} else {
						getPoint(grille, i, j).ifPresentOrElse(pt -> res.append(toAscci(pt.info)),
								() -> res.append("."));
					}
				}
				res.append("\n");
			}
			return res.toString();
		}

		private char toAscci(String info) {
			
			if(info.equals("F")) {
				return '┌';
			}
			if(info.equals("7")) {
				return '┐';
			}
			if(info.equals("L")) {
				return '└';
			}
			if(info.equals("J")) {
				return '┘';
			}
			if(info.equals("-")) {
				return '─';
			}
			if(info.equals("|")) {
				return '│';
			}
			if(info.equals("S")) {
				return 'S';
			}
			if(info.equals("O")) {
				return 'O';
			}
			if(info.equals("I")) {
				return 'I';
			}
			if(info.equals(".")) {
				return '.';
			}

			return 'O';
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

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	private static class Point {
		int x;
		int y;
		String info;
		int nbLoopPartOnLineLeft = 0;
		int nbLoopPartOnLineRigth = 0;
		int nbLoopPartOnColUp = 0;
		int nbLoopPartOnColDown = 0;

		@Override
		public int hashCode() {
			return Objects.hash(info, x, y);
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
			return Objects.equals(info, other.info) && x == other.x && y == other.y;
		}
	}

	public static List<Long> getDuration() {
		A2023Day10 d = new A2023Day10(1);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s3(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1, endTime - startTime);
	}

}

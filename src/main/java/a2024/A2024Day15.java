package a2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import outils.MesOutils;

public class A2024Day15 extends A2024 {

	public A2024Day15(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day15 d = new A2024Day15(15);
		// System.out.println(d.s1(true));
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

	public long s1(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		Warhouse w = getW(input);
		w.allMove();
		return w.gpsDist();
	}

	private Warhouse getW(List<String> input) {
		int j = 0;
		boolean map = true;
		Set<Position> pos = new HashSet<>();
		List<String> mvt = new ArrayList<>();
		int rx = -1;
		int ry = -1;
		while (j < input.size()) {
			if (input.get(j).isEmpty()) {
				map = false;
			} else if (map) {
				for (int i = 0; i < input.get(j).length(); i++) {
					String s = input.get(j).substring(i, i + 1);
					if (s.equals("@")) {
						rx = i;
						ry = j;
						pos.add(new Position(i, j, "."));
					} else {
						pos.add(new Position(i, j, s));
					}
				}
			} else {
				for (int i = 0; i < input.get(j).length(); i++) {
					mvt.add(input.get(j).substring(i, i + 1));
				}
			}
			j++;
		}
		return new Warhouse(pos, mvt, rx, ry);
	}

	private Warhouse2 getW2(List<String> input) {
		int j = 0;
		boolean map = true;
		Set<Position> pos = new HashSet<>();
		List<String> mvt = new ArrayList<>();
		Set<Box> boxes = new HashSet<>();
		int rx = -1;
		int ry = -1;
		int id = 0;
		while (j < input.size()) {
			if (input.get(j).isEmpty()) {
				map = false;
			} else if (map) {
				for (int i = 0; i < input.get(j).length(); i++) {
					int k = 2 * i;
					String s = input.get(j).substring(i, i + 1);
					if (s.equals("@")) {
						rx = 2 * i;
						ry = j;
						pos.add(new Position(k, j, "."));
						pos.add(new Position(k + 1, j, "."));
					} else if (s.equals("O")) {
						pos.add(new Position(k, j, "."));
						pos.add(new Position(k + 1, j, "."));
						id++;
						boxes.add(new Box(id, k, k + 1, j));
					} else {
						pos.add(new Position(k, j, s));
						pos.add(new Position(k + 1, j, s));
					}
				}
			} else {
				for (int i = 0; i < input.get(j).length(); i++) {
					mvt.add(input.get(j).substring(i, i + 1));
				}
			}
			j++;
		}
		return new Warhouse2(pos, mvt, rx, ry, boxes);
	}

	public long s2(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		Warhouse2 w = getW2(input);

		w.allMove();

		return w.gpsDist();
	}

	@Data
	@AllArgsConstructor
	private static class Warhouse2 {
		Set<Position> pos;
		List<String> mvt;
		int rx;
		int ry;
		Set<Box> boxes;

		private Position getPosition(int i, int j) {
			for (Position p : pos) {
				if (p.x == i && p.y == j) {
					return p;
				}
			}
			return null;
		}

		public long gpsDist() {
			Long res = 0l;
			for (Box b : boxes) {
				res += (b.bxl) + 100 * b.by;
			}
			return res;
		}

		public void allMove() {
			for (String s : mvt) {
			
				if (s.equals("^")) {
					moveUp();
				}
				if (s.equals("v")) {
					moveDown();
				}
				if (s.equals("<")) {
					moveL();
				}
				if (s.equals(">")) {
					moveR();
				}
			}
		}

		private void moveUp() {
			Optional<Box> boxD = getBox(rx, ry - 1);
			if (!getPosition(rx, ry - 1).contenu.equals("#")) {
				if (boxD.isEmpty()) {
					ry -= 1;
				} else {
					Set<Box> boxToMove = new HashSet<>();
					int k = 1;
					boxToMove.add(boxD.get());
					int oldSize = 0;
					while (oldSize < k) {
						oldSize = k;
						Set<Box> boxToMoveC = new HashSet<>(boxToMove);
						for (Box j : boxToMove) {
							boxToMoveC.addAll(getAllBoxeUp(j));
						}
						k = boxToMoveC.size();
						boxToMove=new HashSet<>(boxToMoveC);
					}
					if (allCanMoveUp(boxToMove)) {
						for (Box b : boxToMove) {
							b.moveU();
						}
						ry -= 1;
					}
				}
			}

		}

		private Collection<? extends Box> getAllBoxeUp(Box j) {
			Set<Box> res = new HashSet<>();
			for (Box b : boxes) {
				if (b.by == j.by - 1 && (b.bxl == j.bxl || b.bxl == j.bxr || b.bxr == j.bxl)) {
					res.add(b);
				}
			}
			return res;
		}

		private boolean allCanMoveUp(Set<Box> boxToMove) {
			for (Box b : boxToMove) {
				if (getPosition(b.bxl, b.by - 1).contenu.equals("#")) {
					return false;
				}
				if (getPosition(b.bxr, b.by - 1).contenu.equals("#")) {
					return false;
				}
			}
			return true;
		}

		private void moveR() {
			Optional<Box> boxR = getBox(rx + 1, ry);
			if (!getPosition(rx + 1, ry).contenu.equals("#")) {
				if (boxR.isEmpty()) {
					rx += 1;
				} else {
					Set<Box> boxToMove = new HashSet<>();
					int k = 1;
					boxToMove.add(boxR.get());
					int oldSize = 0;
					while (oldSize < k) {
						oldSize = k;
						Set<Box> boxToMoveC = new HashSet<>(boxToMove);
						for (Box j : boxToMove) {
							boxToMoveC.addAll(getAllBoxeR(j));
						}
						k = boxToMoveC.size();
						boxToMove=new HashSet<>(boxToMoveC);
					}
					if (allCanMoveR(boxToMove)) {
						for (Box b : boxToMove) {
							b.moveR();
						}
						rx += 1;
					}

				}
			}
		}

		private boolean allCanMoveR(Set<Box> boxToMove) {
			for (Box b : boxToMove) {
				if (getPosition(b.bxr + 1, b.by).contenu.equals("#")) {
					return false;
				}
			}
			return true;
		}

		private Collection<? extends Box> getAllBoxeR(Box j) {
			Set<Box> res = new HashSet<>();
			for (Box b : boxes) {
				if (b.by == j.by && b.bxl - 1 == j.bxr) {
					res.add(b);
				}
			}
			return res;
		}

		private void moveL() {
			Optional<Box> boxR = getBox(rx - 1, ry);
			if (!getPosition(rx - 1, ry).contenu.equals("#")) {
				if (boxR.isEmpty()) {
					rx -= 1;
				} else {
					Set<Box> boxToMove = new HashSet<>();
					int k = 1;
					boxToMove.add(boxR.get());
					int oldSize = 0;
					while (oldSize < k) {
						oldSize = k;
						Set<Box> boxToMoveC = new HashSet<>(boxToMove);
						for (Box j : boxToMove) {
							boxToMoveC.addAll(getAllBoxeL(j));
						}
						k = boxToMoveC.size();
						boxToMove=new HashSet<>(boxToMoveC);
					}
					if (allCanMoveL(boxToMove)) {
						for (Box b : boxToMove) {
							b.moveL();
						}
						rx -= 1;
					}

				}
			}

		}

		private boolean allCanMoveL(Set<Box> boxToMove) {
			for (Box b : boxToMove) {
				if (getPosition(b.bxl - 1, b.by).contenu.equals("#")) {
					return false;
				}
			}
			return true;
		}

		private Collection<? extends Box> getAllBoxeL(Box j) {
			Set<Box> res = new HashSet<>();
			for (Box b : boxes) {
				if (b.by == j.by && b.bxr + 1 == j.bxl) {
					res.add(b);
				}
			}
			return res;
		}

		private void moveDown() {
			Optional<Box> boxD = getBox(rx, ry + 1);

			if (!getPosition(rx, ry + 1).contenu.equals("#")) {
				if (boxD.isEmpty()) {
					ry += 1;
				} else {
					Set<Box> boxToMove = new HashSet<>();
					int k = 1;
					boxToMove.add(boxD.get());
					int oldSize = 0;
					while (oldSize < k) {
						oldSize = k;
						Set<Box> boxToMoveC = new HashSet<>(boxToMove);
						for (Box j : boxToMove) {
							boxToMoveC.addAll(getAllBoxeDown(j));
						}
						k = boxToMoveC.size();
						boxToMove=new HashSet<>(boxToMoveC);
					}
					if (allCanMoveDown(boxToMove)) {
						for (Box b : boxToMove) {
							b.moveD();
						}
						ry += 1;
					}

				}
			}
		}

		private boolean allCanMoveDown(Set<Box> boxToMove) {
			for (Box b : boxToMove) {
				if (getPosition(b.bxl, b.by + 1).contenu.equals("#")) {
					return false;
				}
				if (getPosition(b.bxr, b.by + 1).contenu.equals("#")) {
					return false;
				}
			}
			return true;
		}

		private Collection<? extends Box> getAllBoxeDown(Box j) {
			Set<Box> res = new HashSet<>();
			for (Box b : boxes) {
				if (b.by == j.by + 1 && (b.bxl == j.bxl || b.bxl == j.bxr || b.bxr == j.bxl)) {
					res.add(b);
				}
			}
			return res;
		}

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			// pts.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
			int imax = MesOutils.getMaxIntegerFromList(pos.stream().map(Position::getX).collect(Collectors.toList()));
			int jmax = MesOutils.getMaxIntegerFromList(pos.stream().map(Position::getY).collect(Collectors.toList()));

			for (int j = 0; j <= jmax; j++) {
				for (int i = 0; i <= imax; i++) {
					Optional<Box> b = getBox(i, j);
					if (rx == i && ry == j) {
						res.append("@");
					} else if (getPosition(i, j).contenu.equals("#")) {
						res.append("#");
					} else if (b.isPresent()) {
						if (b.get().bxl == i) {
							res.append("[");
						} else {
							res.append("]");
						}
					} else {
						res.append(".");
					}

				}
				res.append("\n");

			}
			return res.toString();
		}

		private Optional<Box> getBox(int i, int j) {
			for (Box b : boxes) {
				if (b.by == j && (b.bxl == i || b.bxr == i)) {
					return Optional.ofNullable(b);
				}
			}
			return Optional.empty();
		}
	}

	@Data
	@AllArgsConstructor
	private static class Box {
		public void moveR() {
			setBxl(bxl+1);
			setBxr(bxr+1);
		}

		public void moveD() {
			setBy(by+1);
		}

		public void moveU() {
			setBy(by-1);
		}

		public void moveL() {
			setBxl(bxl-1);
			setBxr(bxr-1);
		}

		int id;
		int bxl;
		int bxr;
		int by;
	}

	@Data
	@AllArgsConstructor
	private static class Warhouse {
		Set<Position> pos;
		List<String> mvt;
		int rx;
		int ry;

		public void allMove() {
			// System.out.println(this);
			for (String s : mvt) {
				// System.out.println(s);

				if (s.equals("^")) {
					moveUp();
				}
				if (s.equals("v")) {
					moveDown();
				}
				if (s.equals("<")) {
					moveL();
				}
				if (s.equals(">")) {
					moveR();
				}
				// System.out.println(this);
				// System.out.println("");
			}

		}

		private void moveR() {
			Position r1 = getPosition(rx + 1, ry);
			if (r1.contenu.equals(".")) {
				rx += 1;
			} else if (r1.contenu.equals("O")) {
				int j = 2;
				Position next = getPosition(rx + j, ry);
				while (next.contenu.equals("O")) {
					j++;
					next = getPosition(rx + j, ry);
				}
				if (next.contenu.equals(".")) {
					r1.setContenu(".");
					rx += 1;
					next.setContenu("O");
				}
			}

		}

		private Position getPosition(int i, int j) {
			for (Position p : pos) {
				if (p.x == i && p.y == j) {
					return p;
				}
			}
			return null;
		}

		private void moveL() {
			Position r1 = getPosition(rx - 1, ry);
			if (r1.contenu.equals(".")) {
				rx -= 1;
			} else if (r1.contenu.equals("O")) {
				int j = 2;
				Position next = getPosition(rx - j, ry);
				while (next.contenu.equals("O")) {
					j++;
					next = getPosition(rx - j, ry);
				}
				if (next.contenu.equals(".")) {
					r1.setContenu(".");
					rx -= 1;
					next.setContenu("O");
				}
			}
		}

		private void moveUp() {
			Position r1 = getPosition(rx, ry - 1);
			if (r1.contenu.equals(".")) {
				ry -= 1;
			} else if (r1.contenu.equals("O")) {
				int j = 2;
				Position next = getPosition(rx, ry - j);
				while (next.contenu.equals("O")) {
					j++;
					next = getPosition(rx, ry - j);
				}
				if (next.contenu.equals(".")) {
					r1.setContenu(".");
					ry -= 1;
					next.setContenu("O");
				}
			}

		}

		private void moveDown() {
			Position r1 = getPosition(rx, ry + 1);
			if (r1.contenu.equals(".")) {
				ry += 1;
			} else if (r1.contenu.equals("O")) {
				int j = 2;
				Position next = getPosition(rx, ry + j);
				while (next.contenu.equals("O")) {
					j++;
					next = getPosition(rx, ry + j);
				}
				if (next.contenu.equals(".")) {
					r1.setContenu(".");
					ry += 1;
					next.setContenu("O");
				}
			}

		}

		public Long gpsDist() {
			Long res = 0l;
			for (Position p : pos) {
				if (p.contenu.equals("O")) {
					res += p.getDist();
				}
			}
			return res;
		}

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			// pts.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
			int imax = MesOutils.getMaxIntegerFromList(pos.stream().map(Position::getX).collect(Collectors.toList()));
			int jmax = MesOutils.getMaxIntegerFromList(pos.stream().map(Position::getY).collect(Collectors.toList()));

			for (int j = 0; j <= jmax; j++) {
				for (int i = 0; i <= imax; i++) {
					if (rx == i && ry == j) {
						res.append("@");
					} else {
						res.append(getPosition(i, j).contenu);
					}

				}
				res.append("\n");

			}
			return res.toString();
		}
	}

	@Data
	@AllArgsConstructor
	private static class Position {
		int x;
		int y;
		String contenu;

		public int getDist() {
			return y * 100 + x;
		}
	}

	public static List<Long> getDuration() {
		A2024Day15 d = new A2024Day15(15);
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

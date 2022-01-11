package a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class A2021Day23 extends A2021 {

	public A2021Day23(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day23 d = new A2021Day23(23);
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

	private int s1(boolean b) {
		Game g = initGame(b);
		for(Amphipods amp:g.amph) {
			System.out.println(amp);
			System.out.println(g.posAccess(amp));
		}
		return 0;
	}

	private Game initGame(boolean b) {
		List<Position> map = new ArrayList<>();
		for (int i = 1; i <= 11; i++) {
			map.add(new Position(i, 1));
		}
		for (int i = 3; i <= 9; i = i + 2) {
			for (int j = 2; j <= 3; j++) {
				map.add(new Position(i, j));
			}
		}
		List<Amphipods> amph = initAmph(b);
		return new Game(amph, map);
	}

	private List<Amphipods> initAmph(boolean b) {
		List<Amphipods> amph = new ArrayList<>();
		if (!b) {
			amph.add(new Amphipods("B", new Position(3, 2)));
			amph.add(new Amphipods("A", new Position(3, 3)));
			amph.add(new Amphipods("C", new Position(5, 2)));
			amph.add(new Amphipods("D", new Position(5, 3)));
			amph.add(new Amphipods("B", new Position(7, 2)));
			amph.add(new Amphipods("C", new Position(7, 3)));
			amph.add(new Amphipods("D", new Position(9, 2)));
			amph.add(new Amphipods("A", new Position(9, 3)));
		} else {
			amph.add(new Amphipods("D", new Position(3, 2)));
			amph.add(new Amphipods("C", new Position(3, 3)));
			amph.add(new Amphipods("B", new Position(5, 2)));
			amph.add(new Amphipods("C", new Position(5, 3)));
			amph.add(new Amphipods("D", new Position(7, 2)));
			amph.add(new Amphipods("A", new Position(7, 3)));
			amph.add(new Amphipods("A", new Position(9, 2)));
			amph.add(new Amphipods("B", new Position(9, 3)));
		}
		return amph;
	}

	private int s2(boolean b) {
		return 0;
	}

	public static class Game {
		List<Amphipods> amph;
		List<Position> map;
		int costTot;

		public List<Amphipods> getAmph() {
			return amph;
		}

		public void setAmph(List<Amphipods> amph) {
			this.amph = amph;
		}

		public List<Position> getMap() {
			return map;
		}

		public void setMap(List<Position> map) {
			this.map = map;
		}

		public int getCostTot() {
			return costTot;
		}

		public void setCostTot(int costTot) {
			this.costTot = costTot;
		}

		public Game(List<Amphipods> amph, List<Position> map) {
			super();
			this.amph = amph;
			this.map = map;
			this.costTot = 0;
		}

		public boolean isOver() {
			return amph.stream().allMatch(Amphipods::isOk);
		}

		public List<Position> posDisp() {
			List<Position> posDisp = new ArrayList<>();
			for (Position p : map) {
				if (!amph.stream().anyMatch(a -> a.p.equals(p))) {
					posDisp.add(p);
				}
			}
			return posDisp;
		}

		public List<Position> posAccess(Amphipods z) {
			List<Position> posAccess = new ArrayList<>();
			List<Position> nps = Arrays.asList(new Position(z.p.x + 1, z.p.y), new Position(z.p.x - 1, z.p.y),
					new Position(z.p.x, z.p.y + 1), new Position(z.p.x + 1, z.p.y - 1));
			for (Position np : nps) {
				if (posDisp().contains(np)) {
					if (np.isO()) {
						List<Position> nps2 = Arrays.asList(new Position(np.x + 1, np.y), new Position(np.x - 1, z.p.y),
								new Position(np.x, np.y - 1));
						boolean ok = false;
						for (Position np2 : nps2) {
							if (posDisp().contains(np2) && !ok) {
								posAccess.add(np);
								ok = true;
							}
						}
					} else {
						posAccess.add(np);
					}
				}
			}
			return posAccess;
		}

		public void move(Amphipods z, Position dest) {
			int i = z.p.x;
			int j = z.p.y;
			int cptMvt = 0;
			if (j != dest.y) {
				if (j > dest.y) {
					j--;
					cptMvt++;
					if (j > dest.y) {
						j--;
						cptMvt++;
					}
				} else {
					j++;
					cptMvt++;
					if (j < dest.y) {
						j++;
						cptMvt++;
					}
				}
			}
			if (i != dest.x) {
				if (i < dest.x) {
					while (i != dest.x) {
						i++;
						cptMvt++;
					}
				} else {
					while (i != dest.x) {
						i--;
						cptMvt++;
					}
				}
			}
			costTot += cptMvt * z.moveCost;
			z.setP(dest);
		}

		@Override
		public String toString() {
			return "Game [amph=" + amph + ", map=" + map + ", costTot=" + costTot + "]";
		}

	}

	public static class Position {
		int x;
		int y;
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
			Position other = (Position) obj;
			return x == other.x && y == other.y;
		}

		public Position(int x, int y) {
			super();
			this.x = x;
			this.y = y;
			if (y == 1) {
				type = "h";
			}
			if (y == 1 && (x == 3 || x == 5 || x == 7 || x == 9)) {
				type = "o";
			}
			if (y > 1 && (x == 3)) {
				type = "sa";
			}
			if (y > 1 && (x == 5)) {
				type = "sb";
			}
			if (y > 1 && (x == 7)) {
				type = "sc";
			}
			if (y > 1 && (x == 9)) {
				type = "sd";
			}
		}

		public boolean isH() {
			return type.equals("h");
		}

		public boolean isO() {
			return type.equals("o");
		}

		public boolean isSa() {
			return type.equals("sa");
		}

		public boolean isSb() {
			return type.equals("sb");
		}

		public boolean isSc() {
			return type.equals("sc");
		}

		public boolean isSd() {
			return type.equals("sd");
		}

		@Override
		public String toString() {
			return "Position [x=" + x + ", y=" + y + ", type=" + type + "]";
		}

	}

	public static class Amphipods {
		String type;
		int moveCost;
		Position p;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getMoveCost() {
			return moveCost;
		}

		public void setMoveCost(int moveCost) {
			this.moveCost = moveCost;
		}

		public Position getP() {
			return p;
		}

		public void setP(Position p) {
			this.p = p;
		}

		public Amphipods(String type, Position p) {
			super();
			this.type = type;
			int cost = 1;
			if (type.equals("B")) {
				cost = 10;
			} else if (type.equals("C")) {
				cost = 100;
			} else if (type.equals("D")) {
				cost = 1000;
			}
			this.moveCost = cost;
			this.p = p;
		}

		public boolean isOk() {
			if (type.equals("A")) {
				return p.isSa();
			}
			if (type.equals("B")) {
				return p.isSb();
			} else if (type.equals("C")) {
				return p.isSc();
			}
			return p.isSd();

		}

		@Override
		public String toString() {
			return "amphipods [Type=" + type + ", moveCost=" + moveCost + ", p=" + p + "]";
		}

	}

	public static List<Long> getDuration() {
		A2021Day23 d = new A2021Day23(23);
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

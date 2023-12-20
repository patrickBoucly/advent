package a2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class A2023Day17b extends A2023 {

	public static final int MIN = 3;
	public static final int MAX = 10;
	public static final int EXTRA = 15;
	public static List<Boolean> doneMap;

	public A2023Day17b(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day17b d = new A2023Day17b(17);
		// System.out.println(d.s1(false));
		long startTime = System.currentTimeMillis();
		// d.s1(true);
		// too low s2 1213 pas 1222
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public long s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		int taille=input.size() * input.get(0).length();
		List<List<Crucible>> map = new ArrayList<>();
		doneMap = new ArrayList<>();
		for (int i = 0; i < taille; i++) {
			doneMap.add(false);
			map.add(new ArrayList<A2023Day17b.Crucible>());
		}
		map.get(0).add(new Crucible(0, 0, Crucible.SOUTH, 0, 0));
		map.get(0).add(new Crucible(0, 0, Crucible.EAST, 0, 0));
		boolean allprocess = false;
		while (!allprocess) {
			allprocess = true;
			outerloop: for (int i = 0; i < taille; i++) {
				if (doneMap.get(i))
					continue;
				List<Crucible> list = map.get(i);
				for (Crucible c : list) {
					if (!c.isProcessed) {
						c.calculNeighbours2(input, map, input.size());
						allprocess = false;
						break outerloop;
					}
				}
				if (!list.isEmpty())
					doneMap.set(i,true);
			}
		}
		long res = Long.MAX_VALUE;
		for (Crucible c : map.get(map.size() - 1)) {
			if (c.getMoves() < res) {
				res = c.getMoves();
			}
		}
		return res;
	}

	public long s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		int taille=input.size() * input.get(0).length();
		List<List<Crucible>> map = new ArrayList<>();
		doneMap = new ArrayList<>();
		for (int i = 0; i < taille; i++) {
			doneMap.add(false);
			map.add(new ArrayList<A2023Day17b.Crucible>());
		}
		map.get(0).add(new Crucible(0, 0, Crucible.SOUTH, 0, 0));
		map.get(0).add(new Crucible(0, 0, Crucible.EAST, 0, 0));
		boolean allprocess = false;
		while (!allprocess) {
			allprocess = true;
			outerloop: for (int i = 0; i < taille; i++) {
				if (doneMap.get(i))
					continue;
				List<Crucible> list = map.get(i);
				for (Crucible c : list) {
					if (!c.isProcessed) {
						c.calculNeighbours(input, map, input.size());
						allprocess = false;
						break outerloop;
					}
				}
				if (!list.isEmpty())
					doneMap.set(i,true);
			}
		}
		long res = Long.MAX_VALUE;
		for (Crucible c : map.get(map.size() - 1)) {
			if (c.getMoves() < res) {
				res = c.getMoves();
			}
		}
		return res;

	}

	@Getter
	@Setter
	@ToString
	@NoArgsConstructor
	public static class Crucible {
		static int NORTH = 0;
		static int SOUTH = 1;
		static int WEST = 2;
		static int EAST = 3;
		int x;
		int y;
		int dir;
		int consecutifmoves;
		long moves;
		boolean isProcessed;

		public void calculNeighbours(List<String> heatMap, List<List<Crucible>> map, int width) {
			if (dir == NORTH) {
				doMove(heatMap, map, width, x + 1, y, EAST, 1);
				if (consecutifmoves < 3) {
					doMove(heatMap, map, width, x, y - 1, NORTH, consecutifmoves + 1);
				}
				doMove(heatMap, map, width, x - 1, y, WEST, 1);
			}
			if (dir == SOUTH) {
				doMove(heatMap, map, width, x + 1, y, EAST, 1);
				if (consecutifmoves < 3) {
					doMove(heatMap, map, width, x, y + 1, SOUTH, consecutifmoves + 1);
				}
				doMove(heatMap, map, width, x - 1, y, WEST, 1);
			}
			if (dir == EAST) {
				doMove(heatMap, map, width, x, y - 1, NORTH, 1);
				if (consecutifmoves < 3) {
					doMove(heatMap, map, width, x + 1, y, EAST, consecutifmoves + 1);
				}
				doMove(heatMap, map, width, x, y + 1, SOUTH, 1);
			}
			if (dir == WEST) {
				doMove(heatMap, map, width, x, y - 1, NORTH, 1);
				if (consecutifmoves < 3) {
					doMove(heatMap, map, width, x - 1, y, WEST, consecutifmoves + 1);
				}
				doMove(heatMap, map, width, x, y + 1, SOUTH, 1);
			}
			isProcessed = true;
		}

		public void doMove(List<String> heatMap, List<List<Crucible>> map, int size, int newX, int newY, int newDir,
				int co) {
			if (newX < 0 || newY < 0 || newX > size - 1 || newY > size - 1) {
				return;
			}

			int nextHeat = Integer.parseInt(heatMap.get(newY).substring(newX, newX + 1));
			for (Crucible c : map.get(newY * size + newX)) {
				if (c.consecutifmoves == co && c.getMoves() == moves + nextHeat && c.getDir() == newDir) {
					return;
				}
			}

			if (map.get(newY * size + newX).isEmpty()
					|| map.get(newY * size + newX).get(0).getMoves() + EXTRA > moves + nextHeat) {
				map.get(newY * size + newX).add(new Crucible(newX, newY, newDir, moves + nextHeat, co));
				A2023Day17b.doneMap.set(newY * size + newX,false);
			}
		}

		public void calculNeighbours2(List<String> heatMap, List<List<Crucible>> map, int width) {
			if (dir == NORTH) {
				if (consecutifmoves > MIN) {
					if (x + MIN < width) {
						doMove(heatMap, map, width, x + 1, y, EAST, 1);
					}
				}
				if (consecutifmoves < MAX) {
					doMove(heatMap, map, width, x, y - 1, NORTH, consecutifmoves + 1);
				}
				if (consecutifmoves > MIN) {
					if (x - MIN >= 0) {
						doMove(heatMap, map, width, x - 1, y, WEST, 1);
					}
				}
			}
			if (dir == SOUTH) {
				if (consecutifmoves > MIN) {
					if (x + MIN < width) {
						doMove(heatMap, map, width, x + 1, y, EAST, 1);
					}
				}
				if (consecutifmoves < MAX) {
					doMove(heatMap, map, width, x, y + 1, SOUTH, consecutifmoves + 1);
				}
				if (consecutifmoves > MIN) {
					if (x - MIN >= 0) {
						doMove(heatMap, map, width, x - 1, y, WEST, 1);
					}
				}
			}
			if (dir == EAST) {
				if (consecutifmoves > MIN) {
					if (y - MIN >= 0) {
						doMove(heatMap, map, width, x, y - 1, NORTH, 1);
					}
				}
				if (consecutifmoves < MAX) {
					doMove(heatMap, map, width, x + 1, y, EAST, consecutifmoves + 1);
				}
				if (consecutifmoves > MIN) {
					if (y + MIN < width) {
						doMove(heatMap, map, width, x, y + 1, SOUTH, 1);
					}
				}
			}
			if (dir == WEST) {
				if (consecutifmoves > MIN) {
					if (y - MIN >= 0) {
						doMove(heatMap, map, width, x, y - 1, NORTH, 1);
					}
				}
				if (consecutifmoves < MAX) {
					doMove(heatMap, map, width, x - 1, y, WEST, consecutifmoves + 1);
				}
				if (consecutifmoves > MIN) {
					if (y + MIN < width) {
					doMove(heatMap, map, width, x, y + 1, SOUTH, 1);
					}
				}
			}
			isProcessed = true;
		}

		public Crucible(int x, int y, int dir, long moves, int co) {
			super();
			this.x = x;
			this.y = y;
			this.dir = dir;
			this.moves = moves;
			this.consecutifmoves = co;
		}
	}

	public static List<Long> getDuration() {
		A2023Day17b d = new A2023Day17b(17);
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

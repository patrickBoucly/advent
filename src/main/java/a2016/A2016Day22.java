package a2016;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2016Day22 extends A2016 {

	public A2016Day22(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day22 d = new A2016Day22(22);
		long startTime = System.currentTimeMillis();
		// System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(false));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	private String s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Node> nodes = input.stream().map(Node::new).collect(Collectors.toList());
		Game g=new Game(nodes);
		System.out.println(g);
		return "";
	}

	public int s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Node> nodes = input.stream().map(Node::new).collect(Collectors.toList());
		int fit = 0;
		for (Node n1 : nodes) {
			for (Node n2 : nodes) {
				if (n1.viablePair(n2)) {
					fit++;
				}
			}
		}
		return fit;
	}

	private class Node {
		int x;
		int y;
		int size;
		int used;
		int avail;
		int useRatio;

		public Node(String line) {
			String[] s = line.trim().split(";");
			String[] pos = s[0].split("-");
			x = Integer.parseInt(pos[1].substring(1));
			y = Integer.parseInt(pos[2].split(";")[0].substring(1));
			size = Integer.parseInt(s[1].substring(0, s[1].length() - 1));
			used = Integer.parseInt(s[2].substring(0, s[2].length() - 1));
			avail = Integer.parseInt(s[3].substring(0, s[3].length() - 1));
			useRatio = Integer.parseInt(s[4].substring(0, s[4].length() - 1));
		}

		public Node(int x, int y, int size, int used, int avail, int useRatio) {
			super();
			this.x = x;
			this.y = y;
			this.size = size;
			this.used = used;
			this.avail = avail;
			this.useRatio = useRatio;
		}

		public boolean viablePair(Node b) {
			return !((x == b.x) && (y == b.y)) && used != 0 && b.avail >= used;
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

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public int getUsed() {
			return used;
		}

		public void setUsed(int used) {
			this.used = used;
		}

		public int getAvail() {
			return avail;
		}

		public void setAvail(int avail) {
			this.avail = avail;
		}

		public int getUseRatio() {
			return useRatio;
		}

		public void setUseRatio(int useRatio) {
			this.useRatio = useRatio;
		}

		public Optional<Node> getNode(List<Node> nodes, int x, int y) {
			Node p = null;
			for (Node i : nodes) {
				if (x == i.x && y == i.y) {
					p = new Node(x, y, i.size, i.used, i.avail, i.useRatio);
				}
			}
			return Optional.ofNullable(p);
		}

	}

	private class Game {
		List<Node> nodes;
		Integer finalDataSize;
		int xg;
		int yg;

		public Game(List<Node> nodes) {
			super();
			this.nodes = nodes;
			this.xg = MesOutils.getMaxIntegerFromList(
					nodes.stream().filter(n -> n.y == 0).map(n -> n.x).collect(Collectors.toList()));
			this.yg = 0;
			this.finalDataSize = getFinalDataSize();

		}

		public Integer getFinalDataSize() {
			return nodes.stream().filter(n -> n.y == 0 && n.x == xg).findFirst().get().used;
		}

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			// pts.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
			int imax = MesOutils.getMaxIntegerFromList(nodes.stream().map(Node::getX).collect(Collectors.toList()));
			int jmax = MesOutils.getMaxIntegerFromList(nodes.stream().map(Node::getY).collect(Collectors.toList()));
			for (int j = 0; j <= jmax; j++) {
				for (int i = 0; i <= imax; i++) {
					if (j == yg && i == xg) {
						res.append("G");
					} else {
						Node n = nodes.get(0).getNode(nodes, i, j).get();
						if (n.avail < finalDataSize) {
							res.append(".");
						} else if (n.used == 0) {
							res.append("_");
						} else {
							res.append("@");
						}
					}
				}
				res.append("\n");
			}
			return res.toString();
		}
	}

	public static List<Long> getDuration() {
		A2016Day22 d = new A2016Day22(22);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(3755797L, 10028548L);
	}

}

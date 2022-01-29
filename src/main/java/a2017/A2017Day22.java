package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class A2017Day22 extends A2017 {

	public A2017Day22(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day22 d = new A2017Day22(22);
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
		Game g = getGame(b);
		for (int k = 0; k < 10000; k++) {
			System.out.println(g);
			g.burst();

		}
		return g.nbBurstCauseInf;
	}

	private Game getGame(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Node> nd = new ArrayList<>();
		int i = 0;
		for (String l : lignes) {
			for (int j = 0; j < l.length(); j++) {
				if (l.substring(j, j + 1).equals("0")) {
					
						nd.add(new Node(i, j, false));
					
				} else {
					
						nd.add(new Node(i, j, true));
				
				}
			}
			i++;
		}
		
			return new Game(nd, b);
		

	}
	private Game2 getGame2(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		
		List<Node2> nd2 = new ArrayList<>();
		int i = 0;
		for (String l : lignes) {
			for (int j = 0; j < l.length(); j++) {
				if (l.substring(j, j + 1).equals("0")) {
					
						nd2.add(new Node2(i, j, "c"));
					
				} else {
					
						nd2.add(new Node2(i, j, "i"));
				
				}
			}
			i++;
		}
		
			return new Game2(nd2, b);
		

	}

	public int s2(boolean b) {
		Game2 g = getGame2(b);
		for (int k = 0; k < 10000000; k++) {
			if(k%10000==0) {
				System.out.println(k+" "+g.nbBurstCauseInf);
			}
			g.burst();

		}
		return g.nbBurstCauseInf;
	}

	public static class Node {
		int i;
		int j;
		boolean isInfected;

		public Node(int i, int j, boolean isInfected) {
			super();
			this.i = i;
			this.j = j;
			this.isInfected = isInfected;
		}

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

		public int getJ() {
			return j;
		}

		public void setJ(int j) {
			this.j = j;
		}

		public boolean isInfected() {
			return isInfected;
		}

		public void setInfected(boolean isInfected) {
			this.isInfected = isInfected;
		}

		@Override
		public int hashCode() {
			return Objects.hash(i, j);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			return i == other.i && j == other.j;
		}

		@Override
		public String toString() {
			return "Node [i=" + i + ", j=" + j + ", isInfected=" + isInfected + "]";
		}

	}

	public static class Game {
		List<Node> nodes;
		int virI;
		int virJ;
		String dir;
		boolean b;
		int nbBurstCauseInf;

		public Node getNode(int i, int j) {
			return nodes.stream().filter(n -> n.i == i && n.j == j).findFirst().get();
		}

		public List<Node> getNodes() {
			return nodes;
		}

		public void burst() {
			Node curNode = getNode(virI, virJ);
			//System.out.println(curNode);
			if (curNode.isInfected) {
				tr(dir);
			} else {
				tl(dir);
			}
			if (!curNode.isInfected) {
				nbBurstCauseInf++;
			}
			curNode.setInfected(!curNode.isInfected);
			moveFoward();
		}

		private void moveFoward() {
			if (dir.equals("N")) {
				virI--;
			} else if (dir.equals("E")) {
				virJ++;
			} else if (dir.equals("S")) {
				virI++;
			} else {
				virJ--;
			}
			if (!nodes.stream().anyMatch(n -> n.i == virI && n.j == virJ)) {
				nodes.add(new Node(virI, virJ, false));
			}

		}

		private void tr(String dir) {
			if (dir.equals("N")) {
				setDir("E");
			} else if (dir.equals("E")) {
				setDir("S");
			} else if (dir.equals("S")) {
				setDir("W");
			} else {
				setDir("N");
			}
		}

		private void tl(String dir) {
			if (dir.equals("N")) {
				setDir("W");
			} else if (dir.equals("W")) {
				setDir("S");
			} else if (dir.equals("S")) {
				setDir("E");
			} else {
				setDir("N");
			}
		}

		public void setNodes(List<Node> nodes) {
			this.nodes = nodes;
		}

		public int getVirI() {
			return virI;
		}

		public void setVirI(int virI) {
			this.virI = virI;
		}

		public int getVirJ() {
			return virJ;
		}

		public void setVirJ(int virJ) {
			this.virJ = virJ;
		}

		public String getDir() {
			return dir;
		}

		public void setDir(String dir) {
			this.dir = dir;
		}

		public Game(List<Node> nodes, boolean b) {
			super();
			this.nodes = nodes;
			this.dir = "N";
			if (b) {
				this.virI = 12;
				this.virJ = 12;
			} else {
				this.virI = 1;
				this.virJ = 1;
			}
			this.nbBurstCauseInf = 0;
		}

		@Override
		public String toString() {
			return "Game [virI=" + virI + ", virJ=" + virJ + ", dir=" + dir + "]";
		}

	}

	public static class Node2 {
		int i;
		int j;
		String status;

		public Node2(int i, int j, String status) {
			super();
			this.i = i;
			this.j = j;
			this.status = status;
		}

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

		public int getJ() {
			return j;
		}

		public void setJ(int j) {
			this.j = j;
		}

		@Override
		public int hashCode() {
			return Objects.hash(i, j);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			return i == other.i && j == other.j;
		}

		@Override
		public String toString() {
			return "Node [i=" + i + ", j=" + j + ", isInfected=" + status + "]";
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

	}

	public static class Game2 {
		List<Node2> nodes;
		int virI;
		int virJ;
		String dir;
		boolean b;
		int nbBurstCauseInf;

		public Node2 getNode(int i, int j) {
			return nodes.stream().filter(n -> n.i == i && n.j == j).findFirst().get();
		}

		public List<Node2> getNodes() {
			return nodes;
		}
		/*If it is clean, it turns left.
If it is weakened, it does not turn, and will continue moving in the same direction.
If it is infected, it turns right.
If it is flagged, it reverses direction, and will go back the way*/

		public void burst() {
			Node2 curNode = getNode(virI, virJ);
			//System.out.println(curNode);
			if (curNode.status.equals("c")) {
				tl(dir);
			} else if (curNode.status.equals("i")) {
				tr(dir);
			}else if (curNode.status.equals("f")) {
				tr(dir);
				tr(dir);
			}
			if (curNode.status.equals("i")) {
				curNode.status="f";
			} else if(curNode.status.equals("c")) {
				curNode.status="w";
			} else if(curNode.status.equals("f")) {
				curNode.status="c";
			} else if(curNode.status.equals("w")) {
				curNode.status="i";
				nbBurstCauseInf++;
			} 
			moveFoward();
		}
			
		

		private void moveFoward() {
			if (dir.equals("N")) {
				virI--;
			} else if (dir.equals("E")) {
				virJ++;
			} else if (dir.equals("S")) {
				virI++;
			} else {
				virJ--;
			}
			if (!nodes.stream().anyMatch(n -> n.i == virI && n.j == virJ)) {
				nodes.add(new Node2(virI, virJ, "c"));
			}

		}

		private void tr(String dir) {
			if (dir.equals("N")) {
				setDir("E");
			} else if (dir.equals("E")) {
				setDir("S");
			} else if (dir.equals("S")) {
				setDir("W");
			} else {
				setDir("N");
			}
		}

		private void tl(String dir) {
			if (dir.equals("N")) {
				setDir("W");
			} else if (dir.equals("W")) {
				setDir("S");
			} else if (dir.equals("S")) {
				setDir("E");
			} else {
				setDir("N");
			}
		}

		public void setNodes(List<Node2> nodes) {
			this.nodes = nodes;
		}

		public int getVirI() {
			return virI;
		}

		public void setVirI(int virI) {
			this.virI = virI;
		}

		public int getVirJ() {
			return virJ;
		}

		public void setVirJ(int virJ) {
			this.virJ = virJ;
		}

		public String getDir() {
			return dir;
		}

		public void setDir(String dir) {
			this.dir = dir;
		}

		public Game2(List<Node2> nd2, boolean b) {
			super();
			this.nodes = nd2;
			this.dir = "N";
			if (b) {
				this.virI = 12;
				this.virJ = 12;
			} else {
				this.virI = 1;
				this.virJ = 1;
			}
			this.nbBurstCauseInf = 0;
		}

		@Override
		public String toString() {
			return "Game [virI=" + virI + ", virJ=" + virJ + ", dir=" + dir + "]";
		}

	}

	public static List<Long> getDuration() {
		A2017Day22 d = new A2017Day22(1);
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

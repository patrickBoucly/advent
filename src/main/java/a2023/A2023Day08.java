package a2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import outils.MesOutils;
import outils.UniformCostSearch.Graph;

public class A2023Day08 extends A2023 {

	public A2023Day08(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day08 d = new A2023Day08(8);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}



	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class TheGame {
		List<String> instructions;
		Graph graph;
		List<Node> nodes;
		List<Node> currentNodes;
		Long nbStep;

		public void solve1() {
			while (!currentNodes.get(0).name.equals("ZZZ")) {
				for (String ins : instructions) {
					currentNodes = Arrays.asList(getNextNode(ins));
					nbStep++;
					if (currentNodes.get(0).name.equals("ZZZ")) {
						break;
					}
				}
			}
		}

		private Node getNextNode(String ins) {
			if (ins.equals("L")) {
				return getNextLeftNode();
			}
			return getNextRigthNode();
		}

		private Node getNextRigthNode() {
			Optional<Node> n = getNodeFromNodes(currentNodes.get(0).rightNodeName, nodes);
			if (n.isPresent()) {
				return n.get();
			}
			return null;
		}

		private Node getNextLeftNode() {
			Optional<Node> n = getNodeFromNodes(currentNodes.get(0).leftNodeName, nodes);
			if (n.isPresent()) {
				return n.get();
			}
			return null;
		}

		public Long solve2() {

			while (!currentNodes.stream().allMatch(no -> no.name.substring(2).equals("Z"))) {
				for (String ins : instructions) {
					currentNodes = getAllNextNode(ins);
					nbStep++;
					for (Node nod : currentNodes) {
						if (nod.name.substring(2).equals("Z") && nod.minStepToGoOut == -1L) {
							nod.setMinStepToGoOut(nbStep);

						}
					}
					if (currentNodes.stream().allMatch(no -> no.name.substring(2).equals("Z"))) {
						return nbStep;
					}
					if (currentNodes.stream().allMatch(no -> no.minStepToGoOut != -1L)) {
						return MesOutils.ppcmListe(currentNodes.stream().map(n->n.getMinStepToGoOut()).toList());
					}

				}
			}
			return nbStep;

		}

		private List<Node> getAllNextNode(String ins) {
			if (ins.equals("L")) {
				return getNextAllLeftNode();
			}
			return getNextAllRigthNode();
		}

		private List<Node> getNextAllRigthNode() {
			List<Node> nextCurNodes = new ArrayList<>();
			for (Node no : currentNodes) {
				Optional<Node> n = getNodeFromNodesWithOut(no.rightNodeName, nodes,no.minStepToGoOut);
				if (n.isPresent()) {
					nextCurNodes.add(n.get());
				}
			}
			return nextCurNodes;
		}

		private List<Node> getNextAllLeftNode() {
			List<Node> nextCurNodes = new ArrayList<>();
			for (Node no : currentNodes) {
				Optional<Node> n = getNodeFromNodesWithOut(no.leftNodeName, nodes,no.minStepToGoOut);
				if (n.isPresent()) {
					nextCurNodes.add(n.get());
				}
			}
			return nextCurNodes;
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Node {
		String name;
		String leftNodeName;
		String rightNodeName;
		boolean IsCurrentNode;
		Long minStepToGoOut = -1L;
		int id;

	}

	public Long s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().toList();
		TheGame tg = getTheGameFromInput(inputL);
		tg.solve1();

		return tg.getNbStep();
	}

	static Optional<Node> getNodeFromNodes(String nodeGame, List<Node> nodes) {
		for (Node n : nodes) {
			if (n.name.equals(nodeGame)) {
				return Optional.ofNullable(n);
			}
		}
		return Optional.empty();
	}
	static Optional<Node> getNodeFromNodesWithOut(String nodeGame, List<Node> nodes,Long out) {
		for (Node n : nodes) {
			if (n.name.equals(nodeGame)) {
				n.setMinStepToGoOut(out);
				return Optional.ofNullable(n);
			}
		}
		return Optional.empty();
	}

	private TheGame getTheGameFromInput(List<String> inputL) {
		TheGame tg = new TheGame();
		Graph graph = new Graph();
		Node currentNode = null;
		List<String> instructions = new ArrayList<>();
		String ins = inputL.get(0).trim();
		for (int i = 0; i < ins.length(); i++) {
			instructions.add(ins.substring(i, i + 1));
		}
		int cpt = 0;
		List<Node> nodes = new ArrayList<>();
		for (int i = 2; i < inputL.size(); i++) {
			String ori = inputL.get(i).substring(0, 3);
			String left = inputL.get(i).substring(7, 10);
			String rigth = inputL.get(i).substring(12, 15);
			Node nodeOri = new Node();
			nodeOri.setId(cpt);
			cpt++;
			nodeOri.setName(ori);
			nodeOri.setLeftNodeName(left);
			nodeOri.setRightNodeName(rigth);
			nodes.add(nodeOri);
			if (ori.equals("AAA")) {
				currentNode = nodeOri;
			}
		}

		for (int i = 0; i < nodes.size(); i++) {
			graph.addNode(i);
		}
		for (Node n : nodes) {
			graph.addEdge(n.id, getNodeIdFromName(n.leftNodeName, nodes), 1);
			graph.addEdge(n.id, getNodeIdFromName(n.getRightNodeName(), nodes), 1);
		}
		tg.setNodes(nodes);
		tg.setCurrentNodes(Arrays.asList(currentNode));
		tg.setGraph(graph);
		tg.setNbStep(0L);
		tg.setInstructions(instructions);
		return tg;
	}

	private Integer getNodeIdFromName(String name, List<Node> nodes) {
		for (Node n : nodes) {
			if (n.name.equals(name)) {
				return n.id;
			}
		}
		return null;
	}

	public Long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().toList();
		TheGame tg = getTheGame2FromInput(inputL);
		return tg.solve2();


	}

	private TheGame getTheGame2FromInput(List<String> inputL) {
		TheGame tg = new TheGame();
		Graph graph = new Graph();
		List<Node> currentNodes = new ArrayList<>();
		List<String> instructions = new ArrayList<>();
		String ins = inputL.get(0).trim();
		for (int i = 0; i < ins.length(); i++) {
			instructions.add(ins.substring(i, i + 1));
		}
		int cpt = 0;
		List<Node> nodes = new ArrayList<>();
		for (int i = 2; i < inputL.size(); i++) {
			String ori = inputL.get(i).substring(0, 3);
			String left = inputL.get(i).substring(7, 10);
			String rigth = inputL.get(i).substring(12, 15);
			Node nodeOri = new Node();
			nodeOri.setId(cpt);
			cpt++;
			nodeOri.setName(ori);
			nodeOri.setLeftNodeName(left);
			nodeOri.setRightNodeName(rigth);
			nodes.add(nodeOri);
			if (ori.substring(2).equals("A")) {
				currentNodes.add(nodeOri);
			}
		}

		for (int i = 0; i < nodes.size(); i++) {
			graph.addNode(i);
		}
		for (Node n : nodes) {
			graph.addEdge(n.id, getNodeIdFromName(n.leftNodeName, nodes), 1);
			graph.addEdge(n.id, getNodeIdFromName(n.getRightNodeName(), nodes), 1);
		}
		tg.setNodes(nodes);
		tg.setCurrentNodes(currentNodes);
		tg.setGraph(graph);
		tg.setNbStep(0L);
		tg.setInstructions(instructions);
		return tg;

	}

	public static List<Long> getDuration() {
		A2023Day08 d = new A2023Day08(8);
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

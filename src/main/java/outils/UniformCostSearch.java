package outils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import lombok.ToString;

public class UniformCostSearch {
	@ToString
	public static class Graph {

		private HashMap<Integer, Node> nodeLookup = new HashMap<>();

		@ToString
		public class Node {
			private int id;
			private int cost;
			LinkedList<Edge> adj = new LinkedList<>();

			private Node(int id) {
				this.id = id;
			}
		}

		public class Edge {
			Node toNode;
			int cost;

			public Edge(Node toNode, int cost) {
				this.toNode = toNode;
				this.cost = cost;
			}

			@Override
			public String toString() {
				return "Edge [cost=" + cost + "]";
			}

		}

		public class Pair {
			Node node;
			int cost;
			LinkedList<Node> lastAddedNodes;

			public Pair(Node node, int cost) {
				this.node = node;
				this.cost = cost;
			}

			// Ajouter un constructeur prenant une liste de nœuds comme troisième paramètre
			public Pair(Node node, int cost, LinkedList<Node> lastAddedNodes) {
				this.node = node;
				this.cost = cost;
				this.lastAddedNodes = lastAddedNodes;
			}
		}

		public class NodeComparator implements Comparator<Pair> {
			@Override
			public int compare(Pair o1, Pair o2) {
				return Integer.compare(o1.cost, o2.cost);
			}
		}

		public void addNode(int id) {
			nodeLookup.put(id, new Node(id));
		}

		public void addNode(int id, Node node) {
			nodeLookup.put(id, node);
		}

		private Node getNode(int id) {
			return nodeLookup.get(id);
		}

		public void addEdge(int source, int destination, int cost) {
			Node s = getNode(source);
			Node d = getNode(destination);
			s.adj.add(new Edge(d, cost));
		}

		public int uniformSearch(int start, int end) {
			return uniformSearch(getNode(start), getNode(end));
		}

		private int uniformSearch(Node start, Node end) {
			HashSet<Node> visited = new HashSet<>();
			PriorityQueue<Pair> queue = new PriorityQueue<>(new NodeComparator());
			queue.add(new Pair(start, 0));

			while (!queue.isEmpty()) {
				Pair pair = queue.poll();
				Node node = pair.node;
				if (visited.contains(node))
					continue;
				visited.add(node);
				node.cost = pair.cost;

				for (Edge edge : node.adj) {
					Node child = edge.toNode;
					int cost = (pair.cost + edge.cost);
					if (visited.contains(child) && child.cost < cost)
						continue;
					queue.add(new Pair(child, cost));
				}

			}

			return end.cost;
		}

		public int uniformSearchC(int start, int end, int indice) {
			return uniformSearchC(getNode(start), getNode(end), indice);
		}

		private int uniformSearchC(Node start, Node end, int indice) {
			HashSet<LinkedList<Node>> visited = new HashSet<>();
			PriorityQueue<Pair> queue = new PriorityQueue<>(new NodeComparator());
			queue.add(new Pair(start, 0, new LinkedList<>()));

			while (!queue.isEmpty()) {
				Pair pair = queue.poll();
				Node node = pair.node;
				LinkedList<Node> lastAddedNodes = new LinkedList<>(pair.lastAddedNodes);


				if (lastAddedNodes.size() >= 4) {					
					if (areNodesAligned(lastAddedNodes.get(lastAddedNodes.size()-4), lastAddedNodes.get(lastAddedNodes.size()-1-3), lastAddedNodes.get(lastAddedNodes.size()-2),
							lastAddedNodes.get(lastAddedNodes.size()-1), indice)) {
						// Si les trois derniers nœuds sont alignés, ne pas ajouter le nœud actuel
						continue;
					} 
					if(getDetour(lastAddedNodes,indice)>1) {
						continue;
					}
					if(demitour(lastAddedNodes)) {
						continue;
					}
				}	
				node.cost = pair.cost;

				for (Edge edge : node.adj) {
					Node child = edge.toNode;
					int cost = pair.cost + edge.cost;
					lastAddedNodes.add(child);
					if (visited.contains(lastAddedNodes) && child.cost < cost)
						continue;
					queue.add(new Pair(child, cost, new LinkedList<>(lastAddedNodes)));

				}
			}

			return end.cost;
		}

		private boolean demitour(LinkedList<Node> lastAddedNodes) {
			if(lastAddedNodes.get(lastAddedNodes.size()-1).id-lastAddedNodes.get(lastAddedNodes.size()-2).id==
					-1*lastAddedNodes.get(lastAddedNodes.size()-2).id-lastAddedNodes.get(lastAddedNodes.size()-3).id) {
				return true;
			}
			return false;
		}

		private int getDetour(LinkedList<Node> lastAddedNodes, int indice) {
			int detour=0;
			for(int i=1;i<lastAddedNodes.size();i++) {
				if (lastAddedNodes.get(i).id - lastAddedNodes.get(i-1).id == -(indice)) {
					detour ++;
				} else if (lastAddedNodes.get(i).id - lastAddedNodes.get(i-1).id == -1) {
					detour ++;
				}
			}
			return detour;
		}

		private boolean areNodesAligned(Node node1, Node node2, Node node3, Node node4, int indice) {
			if (node1 == null || node2 == null || node3 == null || node4 == null) {
				// Si l'un des nœuds est null, retourner false pour éviter la comparaison
				return false;
			}
			

			if (node1.id - node2.id == 1 && node2.id - node3.id == 1 && node3.id - node4.id == 1) {
				return true;
			}
			if (node1.id - node2.id == indice  && node2.id - node3.id == indice 
					&& node3.id - node4.id == indice ) {
				return true;
			}
			return false;
		}
	}
}
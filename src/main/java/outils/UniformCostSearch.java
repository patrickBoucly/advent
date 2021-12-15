package outils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import aocmaven.a2021.A2021Day15;
import aocmaven.a2021.A2021Day15.Point;
import aocmaven.a2021.A2021Day15.Cave;

public class UniformCostSearch {
	static class Graph {

		private HashMap<Integer, Node> nodeLookup = new HashMap<>();

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
		}

		public class Pair {
			Node node;
			int cost;

			public Pair(Node node, int cost) {
				this.node = node;
				this.cost = cost;
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

	}

	public static void main(String[] args) {
		Graph graph = new Graph();
		// s
		A2021Day15 d = new A2021Day15(15);
		List<Point> p1 = d.getPoints(false);
		System.out.println(p1);
		List<Point> points = d.getAllPoints(p1, 5);
		
		points.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
		Cave c=new Cave(points);
		 System.out.println(c);
		for (int i = 0; i < points.size(); i++) {
			graph.addNode(i);
		}
		int imax = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getX).collect(Collectors.toList()));
		points.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
		for (int j = 0; j <= imax; j++) {
			for (int i = 0; i < imax; i++) {
				Point mpt=Point.getPoint(i + 1, j, points);
				graph.addEdge((imax+1) * j + i, (imax+1) * j + i + 1, mpt.getValue());
			//	graph.addEdge((imax+1) * j + i, (imax+1) * j + i + 1, points.get((imax+1) * j + i + 1).getValue());
			}
		}
		points.sort(Comparator.comparing(Point::getX).thenComparing(Comparator.comparing(Point::getY)));
		for (int i = 0; i <= imax; i++) {
			for (int j = 0; j < imax; j++) {
				Point mpt=Point.getPoint(i, j + 1, points);
				graph.addEdge((imax+1) * j + i, (imax+1) * (j + 1) + i, mpt.getValue());
			//	graph.addEdge((imax+1) * j + i, (imax+1) * j + i + 1, points.get((imax+1) * i + j + 1).getValue());
			}
		}
		System.out.println("Cost calc");
		int cost = graph.uniformSearch(0, points.size()-1);
		System.out.println("Cost : " + cost);
	}

}
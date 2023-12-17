package outils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

import a2023.A2023Day17b.Point;
import a2023.A2023Day17b.State;
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
		public void addNode(int id,Node node) {
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
		
		public int uniformSearchC(int start, int end,int imax) {
			return uniformSearchC(getNode(start), getNode(end),imax);
		}
		private int uniformSearchC(Node start, Node end,int imax) {
			HashSet<Node> visited = new HashSet<>();
			LinkedList<Node> visitedL = new LinkedList<>();
			PriorityQueue<Pair> queue = new PriorityQueue<>(new NodeComparator());
			queue.add(new Pair(start, 0));

			while (!queue.isEmpty()) {
				Pair pair = queue.poll();
				Node node = pair.node;
				if (respectePasLaCondition(node.id,visitedL,imax))
					continue;				
				visited.add(node);
				visitedL.add(node);
				node.cost = pair.cost;

				for (Edge edge : node.adj) {
					Node child = edge.toNode;
					int cost = (pair.cost + edge.cost);
					if(visitedL.size()>2 && visitedL.get(1).id==1) {
						for(Node n:visitedL) {
							System.out.println(n);
						}
					}
					
					if ( child.cost < cost && respectePasLaCondition(edge.toNode.id,visitedL,imax))
						continue;
					queue.add(new Pair(child, cost));
				}

			}

			return end.cost;
		}
		private boolean respectePasLaCondition(int vid, LinkedList<Node> visitedL,int imax) {

			if (visitedL.size() < 4) {
				return false;
			}
			
		
			int t = visitedL.get(visitedL.size() - 4).id;
			int x = visitedL.get(visitedL.size() - 3).id;
			int y = visitedL.get(visitedL.size() - 2).id;
			int z = visitedL.get(visitedL.size() - 1).id;
			if (t - x == 1 && x - y == 1 && y - z == 1 && z - vid == 1) {
				return true;
			}
			if (t - x == -1 && x - y == -1 && y - z == -1 && z - vid == -1) {
				return true;
			}
			if (t - x == (imax + 1) && x - y == (imax + 1) && y - z == (imax + 1) && z - vid == (imax + 1)) {
				return true;
			}
			if (t - x == -(imax + 1) && x - y == -(imax + 1) && y - z == -(imax + 1) && z - vid == -(imax + 1)) {
				return true;
			}
			return false;
		}
	}

	public static void main(String[] args) {
		/*
		Graph graph = new Graph();
		// s
		A2021Day15 d = new A2021Day15(15);
		List<Point> p1 = d.getPoints(true);
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
				mpt=Point.getPoint(i, j , points);
				graph.addEdge( (imax+1) * j + i + 1,(imax+1) * j + i, mpt.getValue());
			//	graph.addEdge((imax+1) * j + i, (imax+1) * j + i + 1, points.get((imax+1) * j + i + 1).getValue());
			}
		}
		points.sort(Comparator.comparing(Point::getX).thenComparing(Comparator.comparing(Point::getY)));
		for (int i = 0; i <= imax; i++) {
			for (int j = 0; j < imax; j++) {
				Point mpt=Point.getPoint(i, j + 1, points);
				graph.addEdge((imax+1) * j + i, (imax+1) * (j + 1) + i, mpt.getValue());
				mpt=Point.getPoint(i, j , points);
				graph.addEdge((imax+1) * (j + 1) + i,(imax+1) * j + i, mpt.getValue());
			//	graph.addEdge((imax+1) * j + i, (imax+1) * j + i + 1, points.get((imax+1) * i + j + 1).getValue());
			}
		}
		System.out.println("Cost calc");
		int cost = graph.uniformSearch(0, points.size()-1);
		System.out.println("Cost : " + cost);
		*/
	}

}
package a2018;

import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Day8A2018 {

	public static void main(String[] args) {
		s1();
		// s2();

	}

	private static void s2() {

	}

	private static void s1() {
		getTree();
	}

	private static void getTree() {
		String message = getMessage(
				"/aoc/src/advent_of_code/main/resources/a2018/input8");
		List<Integer> input = new ArrayList<>();
		List<Integer> inputG = new ArrayList<>();
		List<Integer> inputD = new ArrayList<>();
		List<Node> tree = new ArrayList<>();
		LinkedHashSet<Integer> posConnue = new LinkedHashSet<>();
		List<Integer> index = new ArrayList<>();

		for (String s : message.split("\n")) {
			for (String i : s.split(" ")) {
				input.add(Integer.valueOf(i));
			}
		}
		for (int i = 0; i <= input.size(); i++) {
			index.add(i);
		}
		// input.add(-1);
		while (input.size() > 0) {
			for (int i = 0; i < input.size(); i++) {
				if (input.get(i) == 0) {
					Node n = new Node(i, input.get(i), input.get(i + 1));
					int debutMetatN = i + 2;
					int finMetatN = debutMetatN + input.get(i + 1);
					if(i==0) {
						input.remove(0);
						input.remove(0);
						n.setMetadata(input);
						tree.add(n);
						input=new ArrayList<>();
						
					} else {
					n.setMetadata(new ArrayList<>(input.subList(debutMetatN, finMetatN)));
					tree.add(n);
					
					input.set(i-2, input.get(i-2)-1);
				
					inputG =input.subList(0, i);
					inputD=input.subList(finMetatN, input.size());
					input=new ArrayList<>();
					input.addAll(inputG);
					input.addAll(inputD);
					if(tree.size() % 1 ==0) {
					System.out.println(tree.size());
					System.out.println(input.size());
					}
					}
				}
			}
		}

		int tot = 0;
		for (Node n : tree) {
			for (Integer i : n.getMetadata()) {
				System.out.println(i);
				tot += i;
			}
		}

		System.out.println(tot);

	}

	private static int decalageEnfants(Node nj) {
		int decalageTotal = 0;
		if (nj.nbChilds > 0) {
			for (Node c : nj.childs) {
				decalageTotal += decalageEnfants(c);
			}
		} else {
			decalageTotal = nj.getNbMetadata();
		}
		return decalageTotal;
	}


	private static List<Integer> nettoyerInput(List<Integer> input) {
		List<Integer> inputRestant = new ArrayList<>(input);
		System.out.println(inputRestant.size());
		int i = input.size() - input.get(1);
		for (int k = 0; k < input.get(1); k++) {
			inputRestant.remove(i);
		}
		inputRestant.remove(0);
		inputRestant.remove(0);
		return inputRestant;
	}

	public static class Node {
		int id;
		Integer nbChilds;
		Integer nbMetadata;
		List<Node> childs;
		Node parent;
		List<Integer> metadata;
		List<Integer> info;
		boolean determine = false;

		public Node(int id, Integer nbChilds, Integer nbMetadata) {
			super();
			this.id = id;
			this.nbChilds = nbChilds;
			this.nbMetadata = nbMetadata;
			this.metadata = new ArrayList<>();
			this.childs = new ArrayList<>();
		}

		public void addChild(Node p, Node n) {
			List<Node> childs = p.getChilds();
			childs.add(n);
			p.setChilds(childs);
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Integer getNbChilds() {
			return nbChilds;
		}

		public void setNbChilds(Integer nbChilds) {
			this.nbChilds = nbChilds;
		}

		public Integer getNbMetadata() {
			return nbMetadata;
		}

		public void setNbMetadata(Integer nbMetadata) {
			this.nbMetadata = nbMetadata;
		}

		public List<Node> getChilds() {
			return childs;
		}

		public void setChilds(List<Node> childs) {
			this.childs = childs;
		}

		public Node getParent() {
			return parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
		}

		public List<Integer> getMetadata() {
			return metadata;
		}

		public void setMetadata(List<Integer> metadata) {
			this.metadata = metadata;
			this.nbMetadata = metadata.size();
		}

		public List<Integer> getInfo() {
			return info;
		}

		public void setInfo(List<Integer> info) {
			this.info = info;
		}

		public boolean isDetermine() {
			return determine;
		}

		public void setDetermine(boolean determine) {
			this.determine = determine;
		}

		@Override
		public String toString() {
			return "Node [id=" + id + ", nbChilds=" + nbChilds + ", nbMetadata=" + nbMetadata + ", childs=" + childs
					+ ", parent=" + parent + ", metadata=" + metadata + ", info=" + info + ", determine=" + determine
					+ "]";
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + id;
			return result;
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
			if (id != other.id)
				return false;
			return true;
		}

	}

	private static String getMessage(String monFic) {
		String line = "";
		try {
			line = readFileToString(new File(monFic));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return line;
	}

}

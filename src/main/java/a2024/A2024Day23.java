package a2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

public class A2024Day23 extends A2024 {

	public A2024Day23(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day23 d = new A2024Day23(23);
		System.out.println(d.s1(true));
		// too high 104512
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

	public long s1(boolean z) {
		List<String> input = Arrays.stream(getInput(z).trim().split("\n")).map(String::trim).toList();

		// Construction de l'adjacence du graphe
		Map<String, Set<String>> adjacency = new HashMap<>();
		for (String connection : input) {
			String[] parts = connection.split("-");
			String a = parts[0];
			String b = parts[1];

			adjacency.putIfAbsent(a, new HashSet<>());
			adjacency.putIfAbsent(b, new HashSet<>());
			adjacency.get(a).add(b);
			adjacency.get(b).add(a);
		}

		// Obtention de la liste des nœuds uniques
		List<String> nodes = new ArrayList<>(adjacency.keySet());
		int n = nodes.size();
		int count = 0;

		// Itération sur tous les triplets possibles
		for (int i = 0; i < n; i++) {
			String a = nodes.get(i);
			for (int j = i + 1; j < n; j++) {
				String b = nodes.get(j);
				// Vérifier si a et b sont connectés
				if (!adjacency.get(a).contains(b))
					continue;
				for (int k = j + 1; k < n; k++) {
					String c = nodes.get(k);
					// Vérifier si a-c et b-c sont connectés
					if (adjacency.get(a).contains(c) && adjacency.get(b).contains(c)) {
						// Vérifier si au moins un des noms commence par 't'
						if (a.startsWith("t") || b.startsWith("t") || c.startsWith("t")) {
							count++;
							// Optionnel: Afficher le triplet
							// System.out.println(a + "," + b + "," + c);
						}
					}
				}
			}
		}

		return count;

	}

	public String s2(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();

		Map<String, Set<String>> graph = buildGraph(input);

		List<String> nodes = new ArrayList<>(graph.keySet());
		Collections.sort(nodes);

		Set<String> maxClique = findMaximumClique(graph, nodes);

		List<String> sortedClique = new ArrayList<>(maxClique);
		Collections.sort(sortedClique);

		// Construire le mot de passe
		String password = String.join(",", sortedClique);

		return password;
	}

	/**
	 * Construit un graphe à partir de la liste des connexions.
	 *
	 * @param input Tableau de chaînes représentant les connexions (e.g., "a-b")
	 * @return Un graphe représenté par une Map où chaque clé est un nœud et la
	 *         valeur est un Set de nœuds connectés
	 */
	private static Map<String, Set<String>> buildGraph(List<String> input) {
		Map<String, Set<String>> graph = new HashMap<>();

		for (String connection : input) {
			String[] parts = connection.split("-");
			if (parts.length != 2) {
				continue; // Ignorer les connexions invalides
			}
			String a = parts[0];
			String b = parts[1];

			// Ajouter la connexion pour a
			graph.putIfAbsent(a, new HashSet<>());
			graph.get(a).add(b);

			// Ajouter la connexion pour b (graphe non orienté)
			graph.putIfAbsent(b, new HashSet<>());
			graph.get(b).add(a);
		}

		return graph;
	}

	/**
	 * Trouve la plus grande clique dans le graphe en utilisant une approche
	 * récursive et backtracking.
	 *
	 * @param graph Représentation du graphe
	 * @param nodes Liste des nœuds à explorer
	 * @return La plus grande clique trouvée
	 */
	private static Set<String> findMaximumClique(Map<String, Set<String>> graph, List<String> nodes) {
		Set<String> maxClique = new HashSet<>();
		findCliqueRecursive(graph, nodes, new HashSet<>(), maxClique);
		return maxClique;
	}

	/**
	 * Méthode récursive pour trouver les cliques maximales.
	 *
	 * @param graph         Le graphe
	 * @param nodes         Les nœuds restants à explorer
	 * @param currentClique La clique actuelle en construction
	 * @param maxClique     La plus grande clique trouvée jusqu'à présent
	 */
	private static void findCliqueRecursive(Map<String, Set<String>> graph, List<String> nodes,
			Set<String> currentClique, Set<String> maxClique) {
		if (nodes.isEmpty()) {
			if (currentClique.size() > maxClique.size()) {
				maxClique.clear();
				maxClique.addAll(currentClique);
			}
			return;
		}

		for (int i = 0; i < nodes.size(); i++) {
			String node = nodes.get(i);
			Set<String> newClique = new HashSet<>(currentClique);
			newClique.add(node);

			// Générer la liste des nœuds qui sont connectés à 'node' et à tous les nœuds de
			// la clique actuelle
			List<String> newCandidates = new ArrayList<>();
			for (int j = i + 1; j < nodes.size(); j++) {
				String candidate = nodes.get(j);
				if (graph.get(node).contains(candidate)) {
					boolean connectedToAll = true;
					for (String member : newClique) {
						if (!graph.get(candidate).contains(member)) {
							connectedToAll = false;
							break;
						}
					}
					if (connectedToAll) {
						newCandidates.add(candidate);
					}
				}
			}

			// Appel récursif avec la nouvelle clique et les nouveaux candidats
			findCliqueRecursive(graph, newCandidates, newClique, maxClique);
		}

		// Vérifier si la clique actuelle est la plus grande
		if (currentClique.size() > maxClique.size()) {
			maxClique.clear();
			maxClique.addAll(currentClique);
		}
	}

	public static List<Long> getDuration() {
		A2024Day23 d = new A2024Day23(23);
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

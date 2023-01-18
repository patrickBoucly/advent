package a2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

import outils.GraphWeighted;
import outils.NodeWeighted;

public class A2022Day16BFS extends A2022 {
	private int max = 0;

	public A2022Day16BFS(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day16BFS d = new A2022Day16BFS(16);

		long startTime = System.currentTimeMillis();
		// System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public int s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		List<Valve> valves = creationValves(input);
		List<Valve> valvesFluxPositif = trouverValvesDebitPositif(valves);
		// déterminer le plus court chemin entre ces valves à flux positif
		Map<String, Integer> distances = new HashMap<>();
		distances = calculeDistancesEntreValves(valves, distances);
		Valve valveDepart = getValveByName(valves, "AA");
		int maxTime = 26;
		int maxPressure = 0;
		return bfs2(valveDepart, maxTime, distances, maxPressure, valvesFluxPositif);
	}

	public int s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		List<Valve> valves = creationValves(input);
		List<Valve> valvesFluxPositif = trouverValvesDebitPositif(valves);
		// déterminer le plus court chemin entre ces valves à flux positif
		Map<String, Integer> distances = new HashMap<>();
		distances = calculeDistancesEntreValves(valves, distances);
		Valve valveDepart = getValveByName(valves, "AA");
		int maxTime = 30;
		int maxPressure = 0;
		return bfs(valveDepart, maxTime, distances, maxPressure, valvesFluxPositif);
	}

	private Valve getValveByName(Collection<Valve> valves, String nom) {
		return valves.stream().filter(v -> v.getNom().equals(nom)).findAny().orElse(null);
	}

	private Map<String, Integer> calculeDistancesEntreValves(List<Valve> valves, Map<String, Integer> distances) {
		GraphWeighted graphe = new GraphWeighted(true);

		// creation des nodes
		List<NodeWeighted> nodes = new ArrayList<>();
		int identifiant = 0;
		for (Valve valve : valves) {
			identifiant++;
			NodeWeighted node = new NodeWeighted(identifiant, valve.getNom());
			nodes.add(node);
		}

		// creation des edges
		for (Valve valve : valves) {
			List<Valve> voisins = valve.getVoisins();
			for (Valve voisin : voisins) {
				NodeWeighted node1 = getNodeByName(valve.getNom(), nodes);
				NodeWeighted node2 = getNodeByName(voisin.getNom(), nodes);
				graphe.addEdge(node1, node2, 1);
			}
		}

		// calcul plus court chemin entre valves à flux positif ou AA
		for (Valve valve1 : valves) {
			for (Valve valve2 : valves)
				if (valve1 != valve2 && ((valve1.getFlowRate() > 0 && valve2.getFlowRate() > 0)
						|| (valve1.getNom().equals("AA") | valve2.getNom().equals("AA")))) {
					graphe.resetNodesVisited();
					NodeWeighted node1 = getNodeByName(valve1.getNom(), nodes);
					NodeWeighted node2 = getNodeByName(valve2.getNom(), nodes);
					String[] res = graphe.DijkstraShortestPath(node1, node2);

					distances.put(valve1.getNom() + ":" + valve2.getNom(),
							Integer.valueOf(StringUtils.split(res[1], '.')[0]));

				}
		}

		return distances;
	}

	private NodeWeighted getNodeByName(String nom, List<NodeWeighted> nodes) {
		return nodes.stream().filter(v -> v.getName().equals(nom)).findAny().orElse(null);
	}

	private List<Valve> trouverValvesDebitPositif(List<Valve> valves) {
		return valves.stream().filter(v -> v.getFlowRate() > 0).toList();
	}

	private List<Valve> creationValves(List<String> res) {

		List<Valve> valves = new ArrayList<>();

		// creation des Valves sans info
		for (String ligne : res) {
			String debutLigne = StringUtils.split(ligne, ";")[0];
			String nomValve = StringUtils.split(debutLigne, ' ')[1];
			int flowValve = Integer.valueOf(
					StringUtils.split(StringUtils.split(StringUtils.split(debutLigne, ' ')[4], '=')[1], '=')[0]);

			Valve valve = new Valve();
			valve.setFlowRate(flowValve);
			valve.setNom(nomValve);
			valves.add(valve);
		}

		// Ajout des infos des voisins dans les valves
		for (int j = 0; j < res.size(); j++) {
			String ligne = res.get(j);
			String finLigne = StringUtils.split(ligne, ";")[1];

			String[] nomVoisins = StringUtils.split(finLigne, ' ');
			String nomsVoisins = "";
			for (int i = 4; i < nomVoisins.length; i++) {
				nomsVoisins = nomsVoisins + nomVoisins[i];
			}
			String[] voisins = StringUtils.split(nomsVoisins, ',');

			Valve valve = valves.get(j);

			for (int k = 0; k < voisins.length; k++) {
				Valve valveVoisine = getValveByName(valves, voisins[k]);
				valve.getVoisins().add(valveVoisine);

			}

		}

		return valves;
	}

	private class Valve {

		private int flowRate;
		private List<Valve> voisins;
		private String nom;

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public Valve() {
			super();
			this.flowRate = 0;
			this.voisins = new ArrayList<Valve>();
		}

		public int getFlowRate() {
			return flowRate;
		}

		public void setFlowRate(int flowRate) {
			this.flowRate = flowRate;
		}

		public List<Valve> getVoisins() {
			return voisins;
		}

		@Override
		public String toString() {
			return "Valve [flowRate=" + flowRate + ", nom=" + nom + "]";
		}

	}

	private class State {
		private Set<Valve> valvesOuvertes;
		private int pressure;
		private int time;
		private Valve valveActuelle;
		private Valve valveActuelleElephant;
		private int timeElephant;

		public State() {
			super();
			this.valvesOuvertes = new HashSet<>();
		}

		public Valve getValveActuelle() {
			return valveActuelle;
		}

		public void setValveActuelle(Valve valveActuelle) {
			this.valveActuelle = valveActuelle;
		}

		public int getPressure() {
			return pressure;
		}

		public void setPressure(int pressure) {
			this.pressure = pressure;
		}

		public Set<Valve> getValvesOuvertes() {
			return valvesOuvertes;
		}

		public int getTime() {
			return time;
		}

		public void setTime(int time) {
			this.time = time;
		}

		@Override
		public int hashCode() {
			return Objects.hash(pressure, valvesOuvertes);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			State other = (State) obj;
			return pressure == other.pressure && Objects.equals(valvesOuvertes, other.valvesOuvertes);
		}

		public Valve getValveActuelleElephant() {
			return valveActuelleElephant;
		}

		public void setValveActuelleElephant(Valve valveActuelleElephant) {
			this.valveActuelleElephant = valveActuelleElephant;
		}

		public int getTimeElephant() {
			return timeElephant;
		}

		public void setTimeElephant(int timeElephant) {
			this.timeElephant = timeElephant;
		}

	}

	private int bfs(Valve valveDepart, int maxTime, Map<String, Integer> distances, int maxPressure,
			List<Valve> valvesFluxPositif) {

		State stateInitial = new State();
		stateInitial.setTime(0);
		stateInitial.setValveActuelle(valveDepart);

		Queue<State> queue = new LinkedList<>();
		queue.add(stateInitial);

		Set<State> visitedStates = new HashSet<>();
		visitedStates.add(stateInitial);

		while (!queue.isEmpty()) {
			State stateActuel = queue.poll();
			if (stateActuel.getTime() >= maxTime
					|| stateActuel.getValvesOuvertes().size() == valvesFluxPositif.size()) {
				if (stateActuel.getPressure() > maxPressure) {
					maxPressure = stateActuel.getPressure();
					System.err.println(stateActuel.getValvesOuvertes()); // pour connaitre le chemin
				}
			} else {

				// s'il reste des valves à ouvrir non visitées, on s'y rend
				if (stateActuel.getValvesOuvertes().size() < valvesFluxPositif.size()) {
					List<Valve> nextValves = choisirProchaineValve(stateActuel.getValvesOuvertes(), valvesFluxPositif);

					for (Valve nextValve : nextValves) {
						State nextState = new State();
						Set<Valve> valvesOuvertesActuellement = stateActuel.getValvesOuvertes();
						for (Valve v : valvesOuvertesActuellement) {
							String nom = v.getNom();
							Valve valve = getValveByName(valvesFluxPositif, nom);
							nextState.getValvesOuvertes().add(valve);
						}
						nextState.getValvesOuvertes().add(nextValve);
						String trajet = stateActuel.getValveActuelle().getNom() + ":" + nextValve.getNom();
						Integer distanceDuTrajet = distances.get(trajet);
						nextState.setTime(stateActuel.getTime() + distanceDuTrajet + 1);
						nextState.setValveActuelle(nextValve);
						int dureeFonctionnement = (maxTime - stateActuel.getTime()) - distanceDuTrajet - 1;
						if (dureeFonctionnement > 0) {
							nextState.setPressure(
									stateActuel.getPressure() + dureeFonctionnement * nextValve.getFlowRate());
						} else {
							nextState.setPressure(stateActuel.getPressure());
						}

						if (!visitedStates.contains(nextState)) {
							queue.add(nextState);
							visitedStates.add(nextState);
						}
					}
				}

			} // fin else
		} // fin while

		return maxPressure;
	}

	private int bfs2(Valve valveDepart, int maxTime, Map<String, Integer> distances, int maxPressure,
			List<Valve> valvesFluxPositif) {

		State stateInitial = new State();
		stateInitial.setTime(0);
		stateInitial.setValveActuelle(valveDepart);
		stateInitial.setTimeElephant(0);
		stateInitial.setValveActuelleElephant(valveDepart);

		Queue<State> queue = new LinkedList<>();
		queue.add(stateInitial);

		Set<State> visitedStates = new HashSet<>();
		visitedStates.add(stateInitial);

		while (!queue.isEmpty()) {
			State stateActuel = queue.poll();
			if ((stateActuel.getTime() >= maxTime && stateActuel.getTimeElephant() >= maxTime)
					|| stateActuel.getValvesOuvertes().size() == valvesFluxPositif.size()) {
				if (stateActuel.getPressure() > maxPressure) {
					maxPressure = stateActuel.getPressure();
					System.err.println(stateActuel.getValvesOuvertes()); // pour connaitre le chemin
				}
			} else {
				// test optimisation
				if (stateActuel.getTime() > maxTime / 4 && stateActuel.getPressure() < 800
						|| stateActuel.getTime() > maxTime / 2 && stateActuel.getPressure() < 1400) {
					// hypothese: ce chemin est moisi: on ne l'emprunte pas
				} else {

					// s'il reste des valves à ouvrir non visitées, on s'y rend
					if (stateActuel.getValvesOuvertes().size() < valvesFluxPositif.size()) {
						if (stateActuel.getTime() < maxTime && stateActuel.getTimeElephant() < maxTime) {

							List<Valve> nextValves = choisirProchaineValve(stateActuel.getValvesOuvertes(),
									valvesFluxPositif);

							for (Valve nextValve : nextValves) {

								State nextState = new State();
								Set<Valve> valvesOuvertesActuellement = stateActuel.getValvesOuvertes();
								for (Valve v : valvesOuvertesActuellement) {
									String nom = v.getNom();
									Valve valve = getValveByName(valvesFluxPositif, nom);
									nextState.getValvesOuvertes().add(valve);
								}
								nextState.getValvesOuvertes().add(nextValve);
								nextState.setTimeElephant(stateActuel.getTimeElephant());
								nextState.setValveActuelleElephant(stateActuel.getValveActuelleElephant());
								String trajet = stateActuel.getValveActuelle().getNom() + ":" + nextValve.getNom();
								Integer distanceDuTrajet = distances.get(trajet);
								nextState.setTime(stateActuel.getTime() + distanceDuTrajet + 1);
								nextState.setValveActuelle(nextValve);
								int dureeFonctionnement = (maxTime - stateActuel.getTime()) - distanceDuTrajet - 1;
								if (dureeFonctionnement > 0) {
									nextState.setPressure(
											stateActuel.getPressure() + dureeFonctionnement * nextValve.getFlowRate());
								} else {
									nextState.setPressure(stateActuel.getPressure());
								}
								for (Valve nextValveE : nextValves) {
									if (!nextValve.nom.equals(nextValveE.nom)) {
										State nextState2 = new State();
										nextState2.setPressure(nextState.getPressure());
										nextState2.setTime(nextState.getTime());
										nextState2.setValveActuelle(nextState.getValveActuelle());
										valvesOuvertesActuellement = nextState.getValvesOuvertes();
										for (Valve v : valvesOuvertesActuellement) {
											String nom = v.getNom();
											Valve valve = getValveByName(valvesFluxPositif, nom);
											nextState2.getValvesOuvertes().add(valve);
										}
										nextState2.getValvesOuvertes().add(nextValveE);
										trajet = stateActuel.getValveActuelleElephant().getNom() + ":"
												+ nextValveE.getNom();
										distanceDuTrajet = distances.get(trajet);
										nextState2
												.setTimeElephant(stateActuel.getTimeElephant() + distanceDuTrajet + 1);
										nextState2.setValveActuelleElephant(nextValveE);
										dureeFonctionnement = (maxTime - stateActuel.getTimeElephant())
												- distanceDuTrajet - 1;
										if (dureeFonctionnement > 0) {
											nextState2.setPressure(nextState.getPressure()
													+ dureeFonctionnement * nextValveE.getFlowRate());
										} else {
											nextState2.setPressure(nextState.getPressure());
										}
										if (!visitedStates.contains(nextState2)) {
											queue.add(nextState2);
											visitedStates.add(nextState2);
										}

									}
								}
							}
						} else if (stateActuel.getTime() < maxTime && stateActuel.getTimeElephant() >= maxTime) {
							// que H
							List<Valve> nextValves = choisirProchaineValve(stateActuel.getValvesOuvertes(),
									valvesFluxPositif);

							for (Valve nextValve : nextValves) {

								State nextState = new State();
								Set<Valve> valvesOuvertesActuellement = stateActuel.getValvesOuvertes();
								for (Valve v : valvesOuvertesActuellement) {
									String nom = v.getNom();
									Valve valve = getValveByName(valvesFluxPositif, nom);
									nextState.getValvesOuvertes().add(valve);
								}
								nextState.getValvesOuvertes().add(nextValve);
								nextState.setTimeElephant(stateActuel.getTimeElephant());
								nextState.setValveActuelleElephant(stateActuel.getValveActuelleElephant());
								String trajet = stateActuel.getValveActuelle().getNom() + ":" + nextValve.getNom();
								Integer distanceDuTrajet = distances.get(trajet);
								nextState.setTime(stateActuel.getTime() + distanceDuTrajet + 1);
								nextState.setValveActuelle(nextValve);
								int dureeFonctionnement = (maxTime - stateActuel.getTime()) - distanceDuTrajet - 1;
								if (dureeFonctionnement > 0) {
									nextState.setPressure(
											stateActuel.getPressure() + dureeFonctionnement * nextValve.getFlowRate());
								} else {
									nextState.setPressure(stateActuel.getPressure());
								}
								if (!visitedStates.contains(nextState)) {
									queue.add(nextState);
									visitedStates.add(nextState);
								}
							}
						} else {
							List<Valve> nextValves = choisirProchaineValve(stateActuel.getValvesOuvertes(),
									valvesFluxPositif);

							for (Valve nextValve : nextValves) {

								State nextState = new State();
								Set<Valve> valvesOuvertesActuellement = stateActuel.getValvesOuvertes();
								for (Valve v : valvesOuvertesActuellement) {
									String nom = v.getNom();
									Valve valve = getValveByName(valvesFluxPositif, nom);
									nextState.getValvesOuvertes().add(valve);
								}
								nextState.getValvesOuvertes().add(nextValve);
								nextState.setTime(stateActuel.getTime());
								nextState.setValveActuelle(stateActuel.getValveActuelle());
								String trajet = stateActuel.getValveActuelleElephant().getNom() + ":"
										+ nextValve.getNom();
								Integer distanceDuTrajet = distances.get(trajet);
								nextState.setTimeElephant(stateActuel.getTimeElephant() + distanceDuTrajet + 1);
								nextState.setValveActuelleElephant(nextValve);
								int dureeFonctionnement = (maxTime - stateActuel.getTimeElephant()) - distanceDuTrajet
										- 1;
								if (dureeFonctionnement > 0) {
									nextState.setPressure(
											stateActuel.getPressure() + dureeFonctionnement * nextValve.getFlowRate());
								} else {
									nextState.setPressure(stateActuel.getPressure());
								}
								if (!visitedStates.contains(nextState)) {
									queue.add(nextState);
									visitedStates.add(nextState);
								}
							}
						}
					}
				}

			} // fin else
		} // fin while

		return maxPressure;
	}

	private List<Valve> choisirProchaineValve(Set<Valve> valvesOuvertes, List<Valve> valvesFluxPositif) {
		List<Valve> nextValves = new ArrayList<>();
		for (Valve valve : valvesFluxPositif) {
			if (getValveByName(valvesOuvertes, valve.getNom()) == null) {
				nextValves.add(valve);
			}
		}
		return nextValves;
	}

	public static List<Long> getDuration() {
		A2022Day16BFS d = new A2022Day16BFS(16);
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

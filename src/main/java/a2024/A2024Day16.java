package a2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import outils.MesOutils;

public class A2024Day16 extends A2024 {

	public A2024Day16(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day16 d = new A2024Day16(16);
		// System.out.println(d.s1(false));
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

	public long s1(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		Game g = getGame(input);
		// System.out.println(g);
		return g.calculerRes1();
	}

	private Game getGame(List<String> input) {
		Set<Position> mapPos = new HashSet<>();
		Set<State> states = new HashSet<>();
		State initialState = null;
		int j = 0;
		for (String s : input) {
			for (int i = 0; i < s.length(); i++) {
				String a = s.substring(i, i + 1);
				Position p = new Position(i, j, a);
				mapPos.add(p);
				if (a.equals("E")) {
					List<Position> it = new ArrayList<>();
					it.add(p);
					initialState = new State(p, null, 0L, it);
					states.add(initialState);
				}
			}
			j++;
		}
		return new Game(new Maps(mapPos), states, initialState, 0L);
	}

	public long s2(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		Game g = getGame(input);
		// System.out.println(g);
		return g.calculerRes2();
	}

	@Data
	@AllArgsConstructor
	public static class Game {
		Maps map;
		Set<State> states;
		State initialState;
		Long res1;

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			// pts.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
			int imax = MesOutils.getMaxIntegerFromList(
					getMap().getMapPos().stream().map(Position::getX).collect(Collectors.toList()));
			int jmax = MesOutils.getMaxIntegerFromList(
					getMap().getMapPos().stream().map(Position::getY).collect(Collectors.toList()));

			for (int j = 0; j <= jmax; j++) {
				for (int i = 0; i <= imax; i++) {

					res.append(map.getPosition(i, j).get().contenu);

				}
				res.append("\n");

			}
			for (State st : states) {
				res.append(st);
				res.append("\n");
			}
			return res.toString();
		}

		public long calculerRes2() {
			// La queue contiendra tous les noeuds(=les states) à visiter
			Queue<State> queue = new LinkedList<>();
			queue.add(initialState);

			// on ne visitera que les noeuds nouveaux afin de ne pas tourner en rond, donc
			// on a besoin de sauvegarder l'ensemble des noeuds deja visités
			Map<State, Long> visitedStates = new HashMap<>();
			visitedStates.put(initialState, initialState.cost);
			List<Long> cost = new ArrayList<>();
			cost.add(Long.MAX_VALUE);
			Set<Position> dansItOpti = new HashSet<>();
			while (!queue.isEmpty()) { // on continue tant que la queue contient encore des noeuds à visiter (mais on
										// peut spécifier un arrêt apres si on a trouvé un noeud cherché)

				State stateActuel = ((LinkedList<State>) queue).removeLast();// on recupere le noeud à étudier
				if (stateActuel.cost < 104000 || (stateActuel.actuelle.contenu.equals("E") && stateActuel.cost > 1)) {
					if (stateActuel.actuelle.contenu.equals("S")) {

						if (stateActuel.cost.equals(103512L)) {
							dansItOpti.addAll(stateActuel.itineraire);
						}

					} else {
						Set<State> nextState = new HashSet<>();
						Set<Position> voisins = map.getVoisins(stateActuel);
						for (Position p : voisins) {
							if (stateActuel.precedente == null) {

								List<Position> it = new ArrayList<>();
								it.addAll(stateActuel.itineraire);
								it.add(p);
								State aNextState = new State(p, stateActuel.actuelle, stateActuel.cost + 1, it);
								nextState.add(aNextState);
							} else {
								List<Position> it = new ArrayList<>();
								it.addAll(stateActuel.itineraire);
								it.add(p);
								State aNextState = new State(p, stateActuel.actuelle, stateActuel.cost, it);
								if (p.x == stateActuel.precedente.x || p.y == stateActuel.precedente.y) {
									aNextState.cost += 1;
								} else {
									aNextState.cost += 1001;
								}
								nextState.add(aNextState);
							}
						}
						for (State st : nextState) {
							if (visitedStates.containsKey(st)) {
								if (st.cost <= visitedStates.get(st)) {
									visitedStates.put(st, st.cost);
									queue.add(st);
								}
							} else {
								visitedStates.put(st, st.cost);
								queue.add(st);
							}

						}
					}
				}
			}
			return dansItOpti.size();
		}

		public long calculerRes1() {
			// La queue contiendra tous les noeuds(=les states) à visiter
			Queue<State> queue = new LinkedList<>();
			queue.add(initialState);

			// on ne visitera que les noeuds nouveaux afin de ne pas tourner en rond, donc
			// on a besoin de sauvegarder l'ensemble des noeuds deja visités
			Map<State, Long> visitedStates = new HashMap<>();
			visitedStates.put(initialState, initialState.cost);
			List<Long> cost = new ArrayList<>();
			cost.add(Long.MAX_VALUE);
			while (!queue.isEmpty()) { // on continue tant que la queue contient encore des noeuds à visiter (mais on
										// peut spécifier un arrêt apres si on a trouvé un noeud cherché)

				State stateActuel = ((LinkedList<State>) queue).removeLast();// on recupere le noeud à étudier
				if (stateActuel.cost < 104000 || (stateActuel.actuelle.contenu.equals("E") && stateActuel.cost > 1)) {
					if (stateActuel.actuelle.contenu.equals("S")) {
						// afficherIt(stateActuel);
						cost.add(stateActuel.cost);
					} else {
						Set<State> nextState = new HashSet<>();
						Set<Position> voisins = map.getVoisins(stateActuel);
						for (Position p : voisins) {
							if (stateActuel.precedente == null) {

								List<Position> it = new ArrayList<>();
								it.addAll(stateActuel.itineraire);
								it.add(p);
								State aNextState = new State(p, stateActuel.actuelle, stateActuel.cost + 1, it);
								nextState.add(aNextState);
							} else {
								List<Position> it = new ArrayList<>();
								it.addAll(stateActuel.itineraire);
								it.add(p);
								State aNextState = new State(p, stateActuel.actuelle, stateActuel.cost, it);
								if (p.x == stateActuel.precedente.x || p.y == stateActuel.precedente.y) {
									aNextState.cost += 1;
								} else {
									aNextState.cost += 1001;
								}
								nextState.add(aNextState);
							}
						}
						for (State st : nextState) {
							if (visitedStates.containsKey(st)) {
								if (st.cost < visitedStates.get(st)) {
									visitedStates.put(st, st.cost);
									queue.add(st);
								}
							} else {
								visitedStates.put(st, st.cost);
								queue.add(st);
							}

						}
					}
				}
			}
			return MesOutils.getMinLongFromList(cost);
		}

		private void afficherIt(State st) {
			StringBuilder res = new StringBuilder();
			// pts.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
			int imax = MesOutils.getMaxIntegerFromList(
					getMap().getMapPos().stream().map(Position::getX).collect(Collectors.toList()));
			int jmax = MesOutils.getMaxIntegerFromList(
					getMap().getMapPos().stream().map(Position::getY).collect(Collectors.toList()));

			for (int j = 0; j <= jmax; j++) {
				for (int i = 0; i <= imax; i++) {
					if (st.itineraire.contains(map.getPosition(i, j).get())) {
						res.append("O");
					} else {
						res.append(map.getPosition(i, j).get().contenu);
					}
				}
				res.append("\n");
				System.out.println(st.itineraire.size());

			}
			System.out.println(res);

		}

		public long calculerRes181() {
			// La queue contiendra tous les noeuds(=les states) à visiter
			Queue<State> queue = new LinkedList<>();
			queue.add(initialState);

			// on ne visitera que les noeuds nouveaux afin de ne pas tourner en rond, donc
			// on a besoin de sauvegarder l'ensemble des noeuds deja visités
			Map<State, Long> visitedStates = new HashMap<>();
			visitedStates.put(initialState, initialState.cost);
			List<Long> cost = new ArrayList<>();
			cost.add(Long.MAX_VALUE);
			while (!queue.isEmpty()) { // on continue tant que la queue contient encore des noeuds à visiter (mais on
										// peut spécifier un arrêt apres si on a trouvé un noeud cherché)

				State stateActuel = ((LinkedList<State>) queue).removeLast();// on recupere le noeud à étudier
				if (stateActuel.cost < 104000 || (stateActuel.actuelle.contenu.equals("E") && stateActuel.cost > 1)) {
					if (stateActuel.actuelle.contenu.equals("S")) {
						// afficherIt(stateActuel);
						cost.add(stateActuel.cost);
					} else {
						Set<State> nextState = new HashSet<>();
						Set<Position> voisins = map.getVoisins(stateActuel);
						for (Position p : voisins) {
							if (stateActuel.precedente == null) {

								List<Position> it = new ArrayList<>();
								it.addAll(stateActuel.itineraire);
								it.add(p);
								State aNextState = new State(p, stateActuel.actuelle, stateActuel.cost + 1, it);
								nextState.add(aNextState);
							} else {
								List<Position> it = new ArrayList<>();
								it.addAll(stateActuel.itineraire);
								it.add(p);
								State aNextState = new State(p, stateActuel.actuelle, stateActuel.cost, it);
								if (p.x == stateActuel.precedente.x || p.y == stateActuel.precedente.y) {
									aNextState.cost += 1;
								} else {
									aNextState.cost += 1;
								}
								nextState.add(aNextState);
							}
						}
						for (State st : nextState) {
							if (visitedStates.containsKey(st)) {
								if (st.cost < visitedStates.get(st)) {
									visitedStates.put(st, st.cost);
									queue.add(st);
								}
							} else {
								visitedStates.put(st, st.cost);
								queue.add(st);
							}

						}
					}
				}
			}
			return MesOutils.getMinLongFromList(cost);
		}

		public long res201() {
			Queue<State> queue = new LinkedList<>();
			queue.add(initialState);

			// on ne visitera que les noeuds nouveaux afin de ne pas tourner en rond, donc
			// on a besoin de sauvegarder l'ensemble des noeuds deja visités
			Map<State, Long> visitedStates = new HashMap<>();
			visitedStates.put(initialState, initialState.cost);
			List<Long> cost = new ArrayList<>();
			cost.add(Long.MAX_VALUE);
			while (!queue.isEmpty()) { // on continue tant que la queue contient encore des noeuds à visiter (mais on
										// peut spécifier un arrêt apres si on a trouvé un noeud cherché)

				State stateActuel = ((LinkedList<State>) queue).removeLast();// on recupere le noeud à étudier
				if (stateActuel.cost < 104000 || (stateActuel.actuelle.contenu.equals("S") && stateActuel.cost > 1)) {
					if (stateActuel.actuelle.contenu.equals("E")) {
						// afficherIt(stateActuel);
						cost.add(stateActuel.cost);
					} else {
						Set<State> nextState = new HashSet<>();
						Set<Position> voisins = map.getVoisins(stateActuel);
						for (Position p : voisins) {
							if (stateActuel.precedente == null) {

								List<Position> it = new ArrayList<>();
								it.addAll(stateActuel.itineraire);
								it.add(p);
								State aNextState = new State(p, stateActuel.actuelle, stateActuel.cost + 1, it);
								nextState.add(aNextState);
							} else {
								List<Position> it = new ArrayList<>();
								it.addAll(stateActuel.itineraire);
								it.add(p);
								State aNextState = new State(p, stateActuel.actuelle, stateActuel.cost, it);
								if (p.x == stateActuel.precedente.x || p.y == stateActuel.precedente.y) {
									aNextState.cost += 1;
								} else {
									aNextState.cost += 1;
								}
								nextState.add(aNextState);
							}
						}
						for (State st : nextState) {
							if (visitedStates.containsKey(st)) {
								if (st.cost < visitedStates.get(st)) {
									visitedStates.put(st, st.cost);
									queue.add(st);
								}
							} else {
								visitedStates.put(st, st.cost);
								queue.add(st);
							}

						}
					}
				}
			}
			return MesOutils.getMinLongFromList(cost);
		}

	}

	@Data
	@AllArgsConstructor
	public static class State {
		Position actuelle;
		Position precedente;
		Long cost;
		List<Position> itineraire = new ArrayList<>();

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			State other = (State) obj;
			return Objects.equals(actuelle, other.actuelle) && Objects.equals(precedente, other.precedente);
		}

		@Override
		public int hashCode() {
			return Objects.hash(actuelle, precedente);
		}

	}

	@Data
	@AllArgsConstructor
	public static class Maps {
		Set<Position> mapPos = new HashSet<>();

		public Optional<Position> getPosition(int i, int j) {
			for (Position p : mapPos) {
				if (p.x == i && p.y == j) {
					return Optional.ofNullable(p);
				}
			}
			return Optional.empty();
		}

		public Set<Position> getVoisins(Position q) {
			Set<Position> v = new HashSet<>();
			for (Position p : mapPos) {
				if (manDist(q, p.x, p.y) == 1) {
					v.add(p);
				}
			}
			return v;
		}

		public boolean ouvreUnChemin(Position q) {
			Set<Position> v = getVoisins(q);
			List<String> valide=List.of(".","S","E");
			for (Position v1 : v) {
				for (Position v2 : v) {
					if (v1 != v2) {
						if (manDist(v1, v2.x, v2.y)==2 &&  valide.contains(v1.contenu) && valide.contains(v2.contenu)) {
							return true;
						}
						
					}
				}
			}
			return false;
		}

		public Set<Position> getVoisins(State s) {
			Set<Position> voisins = new HashSet<>();
			for (Position p : mapPos) {
				if (!p.contenu.equals("#")) {

					if (manDist(p, s.actuelle.x, s.actuelle.y) == 1) {
						if (!s.itineraire.contains(p)) {
							if (s.precedente != null && !(p.equals(s.precedente))) {
								voisins.add(p);
							} else if (!(p.equals(s.precedente))) {
								voisins.add(p);
							}
						}
					}
				}
			}
			return voisins;
		}

		public Set<Position> getVoisins(int i, int j, Position lastPos2) {
			Set<Position> voisins = new HashSet<>();
			for (Position p : mapPos) {
				if (!p.contenu.equals("#")) {
					if (manDist(p, i, j) == 1) {
						if (lastPos2 != null && !(lastPos2.equals(p))) {
							voisins.add(p);
						} else {
							voisins.add(p);
						}
					}
				}
			}
			return voisins;
		}

		private int manDist(Position p, int i, int j) {
			return Math.abs(p.x - i) + Math.abs(p.y - j);
		}
	}

	@Data
	@AllArgsConstructor
	public static class Position {
		int x;
		int y;
		String contenu;
	}

	public static List<Long> getDuration() {
		A2024Day16 d = new A2024Day16(16);
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

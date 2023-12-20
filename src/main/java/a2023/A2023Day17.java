package a2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import a2023.A2023Day17.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import outils.MesOutils;
import outils.UniformCostSearch;
import outils.UniformCostSearch.Graph;

public class A2023Day17 extends A2023 {

	public A2023Day17(int day) {
		super(day);
	}

	public static final int MAX_DETOUR = 3;

	public static void main(String[] args0) {
		A2023Day17 d = new A2023Day17(17);
		System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		// d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		// System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public int s0(boolean b) {
		UniformCostSearch.Graph graph = new UniformCostSearch.Graph();
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		int imax = input.get(0).length();
		int jmax = input.size();
		List<Point> points = new ArrayList<>();
		for (int i = 0; i < imax; i++) {
			for (int j = 0; j < jmax; j++) {
				points.add(new Point(i, j, Integer.parseInt(input.get(j).substring(i, i + 1)), 0));
			}
		}
		points.sort(Comparator.comparing(Point::getY).thenComparing(Comparator.comparing(Point::getX)));
		List<Integer> startAndStop = init(graph, b, points, jmax, imax);
		return graph.uniformSearchC(startAndStop.get(0), startAndStop.get(1), imax);
	}

	public Long s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		TheGame tg = getTheGame(input);

		State initial = new State();
		initial.setCost(0L);
		initial.setNbDown(0);
		initial.setNbRigth(0);
		initial.setDetour(0);
		initial.setPosition(getPoint(tg.points, 0, 0).get());
		initial.setChemin(Arrays.asList(getPoint(tg.points, 0, 0).get()));
		initial.setId(tg.idState++);
		return tg.bfs(initial);
	}

	private TheGame getTheGame(List<String> input) {
		TheGame tg = new TheGame();
		int imax = input.get(0).length();
		int jmax = input.size();
		Set<Point> points = new HashSet<>();
		int k = 0;
		for (int j = 0; j < jmax; j++) {
			for (int i = 0; i < imax; i++) {

				points.add(new Point(i, j, Integer.parseInt(input.get(j).substring(i, i + 1)), k));
				k++;
			}
		}
		tg.setPoints(points);
		tg.imax = MesOutils.getMaxIntegerFromList(points.stream().map(Point::getX).collect(Collectors.toList()));
		return tg;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class TheGame {
		public Long bfs(State stateInitial) {

			LinkedList<State> queue = new LinkedList<>();
			queue.add(stateInitial);
			Set<State> visitedStates = new HashSet<>();
			visitedStates.add(stateInitial);
			Long minCost = 1100L;
			int k = 0;
			while (!queue.isEmpty()) {

				State stateActuel = queue.poll();// on recupere le noeud à étudier
				if (k % 2000 == 0) {
					System.out.println("queue size : " + queue.size() + " cost stateActuel " + stateActuel.cost);
				}
				k++;
				// queue = reduceSize(queue);
				if (stateActuel.cost < minCost) {
					if (stateActuel.position.indice == (imax + 1) * (imax + 1) - 1) {
						if (stateActuel.cost < minCost) {
							afficherChemin(stateActuel.chemin, stateActuel.id);
							minCost = stateActuel.cost;
							System.out.println("nouveau min! :" + minCost);
							System.out.println(stateActuel.actions);
						}
					} else {

						List<Point> voisins = getVoisins(stateActuel.position);
						List<Point> nextCandidats = new ArrayList<>();
						for (Point v : voisins) {
							nextCandidats.add(v);
						}
						for (Point next : nextCandidats) {
							Long nextStateId = idState++;
							int nbDown = 0;
							int nbRigth = 0;
							int detour = 0;
							State nextState = new State();
							List<String> nextActions = new ArrayList<>(stateActuel.actions);
							if (next.indice - stateActuel.position.indice == 1) {
								nbRigth = stateActuel.nbRigth + 1;
								nextActions.add(">");
							} else if (next.indice - stateActuel.position.indice == (imax + 1)) {
								nbDown = stateActuel.nbDown + 1;
								nextActions.add("v");
							} else if (next.indice - stateActuel.position.indice == -(imax + 1)) {
								detour = stateActuel.detour + 1;
								nextActions.add("^");
							} else if (next.indice - stateActuel.position.indice == -1) {
								detour = stateActuel.detour + 1;
								nextActions.add("<");
							}

							nextState.setActions(nextActions);
							nextState.cost = stateActuel.cost + next.cost;
							nextState.setNbDown(nbDown);
							nextState.setNbRigth(nbRigth);
							nextState.setPosition(next);
							nextState.setDetour(detour);
							List<Point> chemin = new ArrayList<>(stateActuel.chemin);
							chemin.add(nextState.position);
							nextState.setChemin(chemin);
							if (nbDown < 4 && nbRigth < 4 && detour < MAX_DETOUR
									&& ajoutable(nextState, queue)) {

								nextState.setId(nextStateId);
								queue.add(nextState);
								visitedStates.add(nextState);

							}
						}
					}
				}
			}
			return minCost;
		}

		private LinkedList<State> reduceSize(LinkedList<State> queue) {
			LinkedList<State> q = new LinkedList<>();
			Long mediumC = 0L;
			if (queue.size() > 5000) {
				for (State s : queue) {
					mediumC += s.cost;
				}
				mediumC = mediumC / queue.size();
				for (State s : queue) {
					if (s.cost < mediumC) {
						q.add(s);
					}
				}
				return q;
			}
			return queue;
		}

		public void afficherChemin(List<Point> chemin, Long id) {
			StringBuilder res = new StringBuilder();
			res.append("State id " + id).append("\n");
			Integer imax = MesOutils
					.getMaxIntegerFromList(points.stream().map(Point::getX).collect(Collectors.toList()));
			Integer jmax = MesOutils
					.getMaxIntegerFromList(points.stream().map(Point::getY).collect(Collectors.toList()));
			for (int j = 0; j <= jmax; j++) {
				for (int i = 0; i <= imax; i++) {
					if (!(i == 0 && j == 0)) {
						if (chemin.contains(getPoint(points, i, j).get())) {
							res.append("#");
						} else {
							getPoint(points, i, j).ifPresentOrElse(pt -> res.append(pt.cost), () -> res.append("."));
						}

					} else {
						getPoint(points, i, j).ifPresentOrElse(pt -> res.append(pt.cost), () -> res.append("."));
					}

				}
				res.append("\n");
			}
			System.out.println(res.toString());
		}

		private boolean ajoutable(State nextState, LinkedList<State> queue) {
			if (nextState.actions.size() < 4) {
				if (nextState.actions.size() == 3) {
					if (nextState.actions.get(2).equals(">") && nextState.actions.get(1).equals("<"))
						return false;
					if (nextState.actions.get(2).equals("<") && nextState.actions.get(1).equals(">"))
						return false;
					if (nextState.actions.get(2).equals("^") && nextState.actions.get(1).equals("v"))
						return false;
					if (nextState.actions.get(2).equals("v") && nextState.actions.get(1).equals("^"))
						return false;
				}
				if (nextState.actions.size() == 2) {
					if (nextState.actions.get(0).equals(">") && nextState.actions.get(1).equals("<"))
						return false;
					if (nextState.actions.get(0).equals("<") && nextState.actions.get(1).equals(">"))
						return false;
					if (nextState.actions.get(0).equals("^") && nextState.actions.get(1).equals("v"))
						return false;
					if (nextState.actions.get(0).equals("v") && nextState.actions.get(1).equals("^"))
						return false;
				}
				return true;
			}
			int h = nextState.actions.size();
			List<String> last4 = nextState.actions.subList(h - 4, h);
			if (last4.stream().filter(s -> s.equals(">")).collect(Collectors.toList()).size() == 4) {
				return false;
			}
			if (last4.stream().filter(s -> s.equals("v")).collect(Collectors.toList()).size() == 4) {
				return false;
			}
		
				if (last4.get(3).equals(">") && last4.get(2).equals("<"))
					return false;
				if (last4.get(3).equals("<") && last4.get(2).equals(">"))
					return false;
				if (last4.get(3).equals("^") && last4.get(2).equals("v"))
					return false;
				if (last4.get(3).equals("v") && last4.get(2).equals("^"))
					return false;
			

			for (State v : queue.stream().filter(v -> v.position.equals(nextState.position))
					.collect(Collectors.toList())) {
				if (v.nbDown == nextState.nbDown) {
					if (v.nbRigth == nextState.nbRigth) {
						if (v.cost < nextState.cost) {
							return false;
						}
					}
				}

				if (v.nbDown == nextState.nbDown) {
					if (v.cost == nextState.cost) {
						if (v.nbRigth < nextState.nbRigth) {
							return false;
						}
					}
				}
				if (v.nbRigth == nextState.nbRigth) {
					if (v.cost == nextState.cost) {
						if (v.nbDown < nextState.nbDown) {
							return false;
						}
					}
				}
			}
			return true;
		}

		private List<Point> getVoisins(Point p) {
			return points.stream().filter(q -> !q.equals(p) && (Math.abs(p.x - q.x) + Math.abs(p.y - q.y) == 1))
					.collect(Collectors.toList());
		}

		Set<Point> points;
		int imax;
		Long idState = 0L;

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	public static class State {
		Point position;
		List<String> actions = new ArrayList<>();
		int nbDown;
		int nbRigth;
		Long cost;
		int detour;
		Long id;
		List<Point> chemin;

		@Override
		public int hashCode() {
			return Objects.hash(cost, nbDown, nbRigth, position);
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
			return Objects.equals(cost, other.cost) && nbDown == other.nbDown && nbRigth == other.nbRigth
					&& Objects.equals(position, other.position);
		}

	}

	private List<Integer> init(Graph graph, boolean b, List<Point> pts, int jmax, int imax) {
		Set<Point> points = new HashSet<>(pts);
		List<Integer> startAndEnd = new ArrayList<>();
		for (int i = 0; i < points.size(); i++) {
			graph.addNode(i);
		}
		startAndEnd.sort(null);
		for (int i = 0; i <= imax; i++) {
			for (int j = 0; j <= jmax; j++) {
				Optional<Point> pSource = getPoint(points, i, j);
				Optional<Point> pH = getPoint(points, i, j - 1);
				Optional<Point> pB = getPoint(points, i, j + 1);
				Optional<Point> pG = getPoint(points, i - 1, j);
				Optional<Point> pD = getPoint(points, i + 1, j);
				if (pSource.isPresent()) {
					for (Optional<Point> o : Arrays.asList(pH, pB, pD, pG))
						if (o.isPresent()) {
							graph.addEdge(getRang(pSource.get(), pts), getRang(o.get(), pts), o.get().cost);
						}
				}
			}

		}
		startAndEnd.add(0);
		startAndEnd.add(pts.size() - 1);
		return List.of(Math.abs(startAndEnd.get(0)), startAndEnd.get(1));
	}

	public static int getRang(Point p, List<Point> pts) {
		int imax = MesOutils.getMaxIntegerFromList(pts.stream().map(Point::getX).collect(Collectors.toList()));
		return p.x + p.y * (imax + 1);
	}

	public int s2(boolean b) {
		return 0;

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	public static class Point {
		int x;
		int y;
		int cost;
		int indice;

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			return x == other.x && y == other.y;
		}

	}

	public static Optional<Point> getPoint(Set<Point> points, int x, int y) {
		Point p = null;
		for (Point i : points) {
			if (x == i.x && y == i.y) {
				return Optional.ofNullable(i);
			}
		}
		return Optional.ofNullable(p);
	}

	public static List<Long> getDuration() {
		A2023Day17 d = new A2023Day17(17);
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

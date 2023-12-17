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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import outils.MesOutils;
import outils.UniformCostSearch;
import outils.UniformCostSearch.Graph;

public class A2023Day17b extends A2023 {

	public A2023Day17b(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day17b d = new A2023Day17b(17);
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
		return graph.uniformSearchC(startAndStop.get(0), startAndStop.get(1),imax);
	}

	public Long s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		TheGame tg = getTheGame(input);

		State initial = new State();
		initial.setCost(0L);
		initial.setChemin(Arrays.asList(getPoint(tg.points, 0, 0).get()));
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
			int sumDiag = 0;
			for (int j = 1; j < 2 * (imax + 1) - 1; j++) {
				if (j % 2 == 0) {
					sumDiag += getPoint(points, j / 2, j / 2).get().cost;
				} else {
					sumDiag += getPoint(points, (j + 1) / 2, (j + 1) / 2 - 1).get().cost;
				}
			}
			Set<State> visitedStates = new HashSet<>();
			visitedStates.add(stateInitial);
			Long minCost = (long) sumDiag;
			int k = 0;
			while (!queue.isEmpty()) {
				if (k % 2000 == 0) {
					System.out.println("queue size : " + queue.size()+" "+queue.get(0).chemin.size());
				}
				k++;

				State stateActuel = queue.poll();// on recupere le noeud à étudier
				if (stateActuel.cost < minCost && interessant(stateActuel)) {
					// if (minCost >1000000000L && stateActuel.cost < minCost) {
					if (stateActuel.chemin.get(stateActuel.chemin.size() - 1).indice == (imax + 1) * (imax + 1) - 1) {
						if (stateActuel.cost < minCost) {
							 afficherChemin(stateActuel.chemin);
							minCost = stateActuel.cost;
							System.out.println("nouveau min! :" + minCost);
						}
					} else {

						List<Point> voisins = getVoisins(stateActuel.chemin.get(stateActuel.chemin.size() - 1));
						List<Point> nextCandidats = new ArrayList<A2023Day17b.Point>();
						for (Point v : voisins) {
							if (respecteLaCondition(v, stateActuel)) {
								if (!stateActuel.chemin.contains(v)) {
									nextCandidats.add(v);
								}
							}
						}
						for (Point next : nextCandidats) {
							State nextState = new State();
							nextState.cost = stateActuel.cost + next.cost;
							List<Point> chemin = new ArrayList<A2023Day17b.Point>(stateActuel.chemin);
							chemin.add(next);
							nextState.setChemin(chemin);
							if (chemin.size() < 4) {
								queue.add(nextState);
								visitedStates.add(nextState);
							} else {

								Optional<State> dejaLa = visitedStates.stream()
										.filter(s -> s.chemin.size() > 3 && s.chemin
												.subList(s.chemin.size() - 4, s.chemin.size()).equals(nextState.chemin
														.subList(nextState.chemin.size() - 4, nextState.chemin.size())))
										.findFirst();
								if (!dejaLa.isPresent()) {

									queue.add(nextState);
									visitedStates.add(nextState);
								} else if (dejaLa.get().cost > nextState.cost) {
									int removeI = -1;
									for (int i = 0; i < queue.size(); i++) {
										if (queue.get(i).chemin
												.subList(queue.get(i).chemin.size() - 4, queue.get(i).chemin.size())
												.equals(dejaLa.get().chemin.subList(dejaLa.get().chemin.size() - 4,
														dejaLa.get().chemin.size()))) {
											removeI = i;
										}
									}
									if (removeI != -1) {
										queue.remove(removeI);
									}
									int removeIv = -1;
									List<State> visitedStatesL = new ArrayList<A2023Day17b.State>(visitedStates);
									for (int i = 0; i < visitedStates.size(); i++) {
										if (visitedStatesL.get(i).chemin.size()>4 && visitedStatesL.get(i).chemin
												.subList(visitedStatesL.get(i).chemin.size() - 4, visitedStatesL.get(i).chemin.size())
												.equals(dejaLa.get().chemin.subList(dejaLa.get().chemin.size() - 4,
														dejaLa.get().chemin.size()))) {
											removeIv = i;
										}
									}
									if (removeIv != -1) {
										visitedStatesL.remove(removeIv);
									}
									queue.add(nextState);
									visitedStates=new HashSet<A2023Day17b.State>(visitedStatesL);
									visitedStates.add(nextState);
								}
							}

						}
					}
				}
			}
			return minCost;
		}

		private boolean interessant(State stateActuel) {
			int cptDetour=0;
			if(stateActuel.chemin.size()>20) {
				for(int i=1;i<stateActuel.chemin.size();i++) {
					if(stateActuel.chemin.get(i).indice-stateActuel.chemin.get(i-1).indice==-1
							||stateActuel.chemin.get(i).indice-stateActuel.chemin.get(i-1).indice==-13) {
						cptDetour++;
					}
				}
				//System.out.println(cptDetour+" "+stateActuel.chemin.size());
				if(cptDetour>1) {
					return false;
				}
			}
			
			return true;
		}

		private boolean respecteLaCondition(Point v, State stateActuel) {

			if (stateActuel.chemin.size() < 4) {
				return true;
			}
			int t = stateActuel.chemin.get(stateActuel.chemin.size() - 4).indice;
			int x = stateActuel.chemin.get(stateActuel.chemin.size() - 3).indice;
			int y = stateActuel.chemin.get(stateActuel.chemin.size() - 2).indice;
			int z = stateActuel.chemin.get(stateActuel.chemin.size() - 1).indice;
			if (t - x == 1 && x - y == 1 && y - z == 1 && z - v.indice == 1) {
				return false;
			}
			if (t - x == -1 && x - y == -1 && y - z == -1 && z - v.indice == -1) {
				return false;
			}
			if (t - x == (imax + 1) && x - y == (imax + 1) && y - z == (imax + 1) && z - v.indice == (imax + 1)) {
				return false;
			}
			if (t - x == -(imax + 1) && x - y == -(imax + 1) && y - z == -(imax + 1) && z - v.indice == -(imax + 1)) {
				return false;
			}
			return true;
		}

		private List<Point> getVoisins(Point p) {
			return points.stream().filter(q -> !q.equals(p) && (Math.abs(p.x - q.x) + Math.abs(p.y - q.y) == 1))
					.toList();
		}

		Set<Point> points;
		int imax;

		public void afficherChemin(List<Point> chemin) {
			StringBuilder res = new StringBuilder();
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
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	public static class State {
		List<Point> chemin;
		Long cost;

		@Override
		public int hashCode() {
			return Objects.hash(chemin);
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
			return Objects.equals(chemin.get(chemin.size() - 1), other.chemin.get(other.chemin.size() - 1));
		}

	}

	private List<Integer> init(Graph graph, boolean b, List<Point> pts, int jmax, int imax) {
		Set<Point> points = new HashSet<>(pts);
		List<Integer> startAndEnd = new ArrayList<>();
		for (int i = 0; i < points.size(); i++) {
			graph.addNode(i);
		}
		startAndEnd.sort(null);
		for (Point p : points) {
			for (Point p2 : points) {
				if (sontVoisins(p, p2)) {
					graph.addEdge(getRang(p, pts), getRang(p2, pts), p2.cost);
					graph.addEdge(getRang(p2, pts), getRang(p, pts), p.cost);
				}
			}
		}
		startAndEnd.add(0);
		startAndEnd.add(pts.size() - 1);
		return List.of(Math.abs(startAndEnd.get(0)), startAndEnd.get(1));
	}

	private boolean sontVoisins(Point p, Point p2) {
		if (p.equals(p2)) {
			return false;
		}
		if (Math.abs(p.x - p2.x) + Math.abs(p.y - p2.y) == 1) {
			return true;
		}
		return false;
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
		A2023Day17b d = new A2023Day17b(17);
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

package a2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2022Day19 extends A2022 {

	public A2022Day19(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day19 d = new A2022Day19(19);
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
		Set<Blueprint> prints = getPrints(b);
		int res = 1;
		int timeMax = 32;
		Map<Integer, Integer> reponses = new HashMap<>();
		for (Blueprint print : prints) {
			if (print.id <=3) {
				int r = bfs(print, timeMax);
				System.out.println(print.id + " : " + r + " geodes");
				reponses.put(print.id, r);
			}
		}
		for (Entry<Integer, Integer> i : reponses.entrySet()) {
			res *= i.getValue();
		}
		return res;
	}

	public Integer s1(boolean b) {
		Set<Blueprint> prints = getPrints(b);
		int res = 0;
		int maxTime = 24;
		Map<Integer, Integer> reponses = new HashMap<>();
		for (Blueprint print : prints) {
			int r = bfs(print, maxTime);
			System.out.println(print.id + " : " + r + " geodes");
			reponses.put(print.id, r);
		}
		for (Entry<Integer, Integer> i : reponses.entrySet()) {
			res += i.getKey() * i.getValue();
		}
		return res;
	}

	private int bfs(Blueprint print, int maxTime) {
		int maxGeode = 0;
		State stateInitial = new State(print, 0, 1, 0, 0, 0, 0, 0, 0, 0);
		Queue<State> queue = new LinkedList<>();
		stateInitial.setActions(new ArrayList<>());
		queue.add(stateInitial);
		Set<State> visitedStates = new HashSet<>();
		visitedStates.add(stateInitial);
		int mi = 0;
		while (!queue.isEmpty()) {
			State stateActuel = queue.poll();
			if (mi != stateActuel.getMinute()) {
				if (mi > maxTime-7)
					System.out.println(stateActuel.getMinute());
				mi = stateActuel.getMinute();
			}
			if (stateActuel.getMinute() >= maxTime) {
				if (stateActuel.getGeode() > maxGeode) {
					System.out.println(stateActuel);
					maxGeode = stateActuel.getGeode();
				}
			} else {
				if (print.id==2 && maxTime == 32 && stateActuel.getMinute() >= 29 && stateActuel.getGeode() < 2) {

				}else if (print.id==2 && maxTime == 32 && stateActuel.getMinute() >= 28 && stateActuel.getGeode() < 1) {

				} else if (print.id==1 && maxTime == 32 && stateActuel.getMinute() >= 29 && stateActuel.getGeode() < 3) {

				}else if (print.id==3 && maxTime == 32 && stateActuel.getMinute() >= 26 && stateActuel.getGeode() < 3) {

				}else if (print.id==3 && maxTime == 32 && stateActuel.getMinute() >= 27 && stateActuel.getGeode() < 5) {

				}
				else if (print.id==3 && maxTime == 32 && stateActuel.getMinute() >= 28 && stateActuel.getGeode() < 9) {

				}else if (print.id==3 && maxTime == 32 && stateActuel.getMinute() >= 30 && stateActuel.getGeode() < 13) {

				}else if (print.id==3 && maxTime == 32 && stateActuel.getMinute() >= 31 && stateActuel.getGeode() < 16) {

				}
				else {
					if (stateActuel.canBuildRobot("geode", print)) {
						State nextState = new State();
						nextState = getNextStateBuildGeodeRobot(stateActuel);
						if (nextState.print != null && !visitedStates.contains(nextState)) {
							queue.add(nextState);
							visitedStates.add(nextState);
						}
					} else {
						if (stateActuel.canBuildRobot("obsidian", print)) {
							State nextState = new State();
							nextState = getNextStateBuildObsidianRobot(stateActuel);
							if (nextState.print != null && !visitedStates.contains(nextState)) {
								queue.add(nextState);
								visitedStates.add(nextState);
							}
						}
						if (stateActuel.canBuildRobot("clay", print)) {
							State nextState = new State();
							nextState = getNextStateBuildClayRobot(stateActuel);
							if (nextState.print != null && !visitedStates.contains(nextState)) {
								queue.add(nextState);
								visitedStates.add(nextState);
							}

						}
						if (stateActuel.canBuildRobot("ore", print)) {
							State nextState = new State();
							nextState = getNextStateBuildOreRobot(stateActuel);
							if (nextState.print != null && !visitedStates.contains(nextState)) {
								queue.add(nextState);
								visitedStates.add(nextState);
							}
						}

						State nextState = getNextStateWait(stateActuel);
						if (!visitedStates.contains(nextState)) {
							queue.add(nextState);
							visitedStates.add(nextState);
						}

					}
				} // fin else
			}
		} // fin while
		System.out.println(maxGeode);
		return maxGeode;

	}

	private boolean queueContainsBetter(Queue<State> queue, State s) {
		int o = s.nbOre;
		int ob = s.nbObsidian;
		int c = s.nbClay;
		int g = s.getGeode();
		int ro = s.nbOreRobot;
		int rob = s.nbObsidianRobot;
		int rc = s.nbClayRobot;
		int rg = s.nbGeodeRobot;
		int m = s.getMinute();
		for (State st : queue) {
			int ot = st.nbOre;
			int obt = st.nbObsidian;
			int ct = st.nbClay;
			int gt = st.getGeode();
			int rot = st.nbOreRobot;
			int robt = st.nbObsidianRobot;
			int rct = st.nbClayRobot;
			int rgt = st.nbGeodeRobot;
			int mt = st.getMinute();
			if (m == mt) {
				if (ot > o && obt >= ob && ct >= c && gt >= g && rot >= ro && robt >= rob && rct >= rc && rgt >= rg) {
					return true;
				}
				if (ot >= o && obt > ob && ct >= c && gt >= g && rot >= ro && robt >= rob && rct >= rc && rgt >= rg) {
					return true;
				}
				if (ot >= o && obt >= ob && ct > c && gt >= g && rot >= ro && robt >= rob && rct >= rc && rgt >= rg) {
					return true;
				}
				if (ot >= o && obt >= ob && ct >= c && gt > g && rot >= ro && robt >= rob && rct >= rc && rgt >= rg) {
					return true;
				}
				if (ot >= o && obt >= ob && ct >= c && gt >= g && rot > ro && robt >= rob && rct >= rc && rgt >= rg) {
					return true;
				}
				if (ot >= o && obt >= ob && ct >= c && gt >= g && rot >= ro && robt > rob && rct >= rc && rgt >= rg) {
					return true;
				}
				if (ot >= o && obt >= ob && ct >= c && gt >= g && rot >= ro && robt >= rob && rct > rc && rgt >= rg) {
					return true;
				}
				if (ot >= o && obt >= ob && ct >= c && gt >= g && rot >= ro && robt >= rob && rct >= rc && rgt > rg) {
					return true;
				}
			}
		}
		return false;
	}

	private int getMinuteMinFirstElement(Queue<State> queue, String element) {
		List<List<String>> actionsListe = queue.stream().map(State::getActions).toList();
		List<Integer> index = actionsListe.stream().map(l -> l.indexOf(element)).toList();
		List<Integer> indexP = index.stream().filter(i -> i >= 0).toList();
		if (indexP.isEmpty()) {
			return -1;
		}
		Integer min = MesOutils.getMinIntegerFromList(indexP);
		return min != null ? min : null;
	}

	private State getNextStateWait(State stateActuel) {
		State nextState = new State();
		List<String> actions = new ArrayList<>(stateActuel.getActions());
		actions.add("wait");
		nextState.setActions(actions);
		nextState.setPrint(stateActuel.getPrint());
		nextState.setMinute(stateActuel.getMinute() + 1);
		nextState.setNbOreRobot(stateActuel.getNbOreRobot());
		nextState.setNbClayRobot(stateActuel.getNbClayRobot());
		nextState.setNbObsidianRobot(stateActuel.getNbObsidianRobot());
		nextState.setNbGeodeRobot(stateActuel.getNbGeodeRobot());
		nextState.setClay(stateActuel.getClay() + stateActuel.nbClayRobot);
		nextState.setObsidian(stateActuel.getObsidian() + stateActuel.nbObsidianRobot);
		nextState.setGeode(stateActuel.getGeode() + stateActuel.nbGeodeRobot);
		nextState.setOre(stateActuel.getOre() + stateActuel.nbOreRobot);
		return nextState;
	}

	private State getNextStateBuildOreRobot(State stateActuel) {

		int coutClay = 0;
		int coutOre = 0;
		int coutObsidian = 0;
		List<Cost> couts = new ArrayList<>();
		for (Robot r : stateActuel.print.costs.keySet()) {
			if (r.name.equals("ore")) {
				couts = stateActuel.print.costs.get(r);
			}
		}
		for (Cost c : couts) {
			if (c.type.equals("ore")) {
				coutOre = c.qtt;
			}
			if (c.type.equals("clay")) {
				coutClay = c.qtt;
			}
			if (c.type.equals("obsidian")) {
				coutObsidian = c.qtt;
			}
		}
		State nextState = new State();
		List<String> actions = new ArrayList<>(stateActuel.getActions());
		actions.add("ore");
		nextState.setActions(actions);
		nextState.setPrint(stateActuel.getPrint());
		nextState.setMinute(stateActuel.getMinute() + 1);
		nextState.setNbOreRobot(stateActuel.getNbOreRobot() + 1);
		nextState.setNbClayRobot(stateActuel.getNbClayRobot());
		nextState.setNbObsidianRobot(stateActuel.getNbObsidianRobot());
		nextState.setNbGeodeRobot(stateActuel.getNbGeodeRobot());
		nextState.setClay(stateActuel.getClay() + stateActuel.nbClayRobot - coutClay);
		nextState.setObsidian(stateActuel.getObsidian() + stateActuel.nbObsidianRobot - coutObsidian);
		nextState.setGeode(stateActuel.getGeode() + stateActuel.nbGeodeRobot);
		nextState.setOre(stateActuel.getOre() + stateActuel.nbOreRobot - coutOre);
		return nextState;
	}

	private State getNextStateBuildClayRobot(State stateActuel) {

		int coutClay = 0;
		int coutOre = 0;
		int coutObsidian = 0;
		List<Cost> couts = new ArrayList<>();
		for (Robot r : stateActuel.print.costs.keySet()) {
			if (r.name.equals("clay")) {
				couts = stateActuel.print.costs.get(r);
			}
		}
		for (Cost c : couts) {
			if (c.type.equals("ore")) {
				coutOre = c.qtt;
			}
			if (c.type.equals("clay")) {
				coutClay = c.qtt;
			}
			if (c.type.equals("obsidian")) {
				coutObsidian = c.qtt;
			}
		}
		State nextState = new State();
		List<String> actions = new ArrayList<>(stateActuel.getActions());
		actions.add("clay");
		nextState.setActions(actions);
		nextState.setPrint(stateActuel.getPrint());
		nextState.setMinute(stateActuel.getMinute() + 1);
		nextState.setNbOreRobot(stateActuel.getNbOreRobot());
		nextState.setNbClayRobot(stateActuel.getNbClayRobot() + 1);
		nextState.setNbObsidianRobot(stateActuel.getNbObsidianRobot());
		nextState.setNbGeodeRobot(stateActuel.getNbGeodeRobot());
		nextState.setClay(stateActuel.getClay() + stateActuel.nbClayRobot - coutClay);
		nextState.setObsidian(stateActuel.getObsidian() + stateActuel.nbObsidianRobot - coutObsidian);
		nextState.setGeode(stateActuel.getGeode() + stateActuel.nbGeodeRobot);
		nextState.setOre(stateActuel.getOre() + stateActuel.nbOreRobot - coutOre);
		return nextState;
	}

	private State getNextStateBuildObsidianRobot(State stateActuel) {

		int coutClay = 0;
		int coutOre = 0;
		int coutObsidian = 0;
		List<Cost> couts = new ArrayList<>();
		for (Robot r : stateActuel.print.costs.keySet()) {
			if (r.name.equals("obsidian")) {
				couts = stateActuel.print.costs.get(r);
			}
		}
		for (Cost c : couts) {
			if (c.type.equals("ore")) {
				coutOre = c.qtt;
			}
			if (c.type.equals("clay")) {
				coutClay = c.qtt;
			}
			if (c.type.equals("obsidian")) {
				coutObsidian = c.qtt;
			}
		}
		State nextState = new State();
		List<String> actions = new ArrayList<>(stateActuel.getActions());
		actions.add("obsidian");
		nextState.setActions(actions);
		nextState.setPrint(stateActuel.getPrint());
		nextState.setMinute(stateActuel.getMinute() + 1);
		nextState.setNbOreRobot(stateActuel.getNbOreRobot());
		nextState.setNbClayRobot(stateActuel.getNbClayRobot());
		nextState.setNbObsidianRobot(stateActuel.getNbObsidianRobot() + 1);
		nextState.setNbGeodeRobot(stateActuel.getNbGeodeRobot());
		nextState.setClay(stateActuel.getClay() + stateActuel.nbClayRobot - coutClay);
		nextState.setObsidian(stateActuel.getObsidian() + stateActuel.nbObsidianRobot - coutObsidian);
		nextState.setGeode(stateActuel.getGeode() + stateActuel.nbGeodeRobot);
		nextState.setOre(stateActuel.getOre() + stateActuel.nbOreRobot - coutOre);
		return nextState;
	}

	private State getNextStateBuildGeodeRobot(State stateActuel) {
		int coutClay = 0;
		int coutOre = 0;
		int coutObsidian = 0;
		List<Cost> couts = new ArrayList<>();
		for (Robot r : stateActuel.print.costs.keySet()) {
			if (r.name.equals("geode")) {
				couts = stateActuel.print.costs.get(r);
			}
		}
		for (Cost c : couts) {
			if (c.type.equals("ore")) {
				coutOre = c.qtt;
			}
			if (c.type.equals("clay")) {
				coutClay = c.qtt;
			}
			if (c.type.equals("obsidian")) {
				coutObsidian = c.qtt;
			}
		}
		State nextState = new State();
		List<String> actions = new ArrayList<>(stateActuel.getActions());
		actions.add("geode");
		nextState.setActions(actions);
		nextState.setPrint(stateActuel.getPrint());
		nextState.setMinute(stateActuel.getMinute() + 1);
		nextState.setNbOreRobot(stateActuel.getNbOreRobot());
		nextState.setNbClayRobot(stateActuel.getNbClayRobot());
		nextState.setNbObsidianRobot(stateActuel.getNbObsidianRobot());
		nextState.setNbGeodeRobot(stateActuel.getNbGeodeRobot() + 1);
		nextState.setClay(stateActuel.getClay() + stateActuel.nbClayRobot - coutClay);
		nextState.setObsidian(stateActuel.getObsidian() + stateActuel.nbObsidianRobot - coutObsidian);
		nextState.setGeode(stateActuel.getGeode() + stateActuel.nbGeodeRobot);
		nextState.setOre(stateActuel.getOre() + stateActuel.nbOreRobot - coutOre);
		return nextState;
	}

	private Set<Blueprint> getPrints(boolean b) {
		Set<Blueprint> prints = new HashSet<>();

		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		for (String s : input) {
			Map<Robot, List<Cost>> costs = new HashMap<>();
			String[] spDeuxPoints = s.split(":");
			int numPrint = Integer.parseInt(spDeuxPoints[0].split(" ")[1].trim());
			String[] spEachs = spDeuxPoints[1].trim().split(" Each");
			for (String each : spEachs) {
				each = each.replace("Each ", "").trim();
				String[] eachSpace = each.split(" ");
				Robot r = new Robot(eachSpace[0].trim());
				if (each.contains("and")) {
					List<Cost> lcost = new ArrayList<>();
					String lesCouts = each.split("costs")[1];
					String[] couts = lesCouts.split("and");
					for (String cout : couts) {
						String[] coutSp = cout.trim().split(" ");
						lcost.add(new Cost(coutSp[1].replace(".", "").trim(), Integer.parseInt(coutSp[0].trim())));
					}
					costs.put(r, lcost);
				} else {
					costs.put(r, List
							.of(new Cost(eachSpace[4].replace(".", "").trim(), Integer.parseInt(eachSpace[3].trim()))));
				}
			}
			prints.add(new Blueprint(numPrint, costs));
		}

		return prints;
	}

	private class Robot {
		String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Robot(String name) {
			super();
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

	}

	private class Cost {
		String type;
		Integer qtt;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Integer getQtt() {
			return qtt;
		}

		public void setQtt(Integer qtt) {
			this.qtt = qtt;
		}

		public Cost(String type, Integer qtt) {
			super();
			this.type = type;
			this.qtt = qtt;
		}

		@Override
		public String toString() {
			return "Cost [type=" + type + ", qtt=" + qtt + "]";
		}

	}

	private class State {
		public int nbClay;
		public int nbObsidian;
		public int nbOre;
		Blueprint print;
		int minute;
		int nbOreRobot;
		int nbClayRobot;
		int nbObsidianRobot;
		int nbGeodeRobot;
		int ore;
		int clay;
		int obsidian;
		int geode;
		int score;
		private List<String> actions;

		public Blueprint getPrint() {
			return print;
		}

		private boolean besoinPlusRessource(String ressource, Blueprint print) {
			if (ressource.equals("geode")) {
				return true;
			}
			int besoinMax = MesOutils
					.getMaxIntegerFromList(List.of(print.getCout("clay", ressource), print.getCout("ore", ressource),
							print.getCout("obsidian", ressource), print.getCout("geode", ressource)));
			// besoinMax = besoinMax + 3;
			if (ressource.equals("ore")) {
				return besoinMax > nbOreRobot;
			}
			if (ressource.equals("clay")) {
				return besoinMax > nbClayRobot;
			}
			if (ressource.equals("obsidian")) {
				return besoinMax > nbObsidianRobot;
			}

			return true;

		}

		private boolean coutsSupportable(String nomRobot) {
			List<Cost> couts = new ArrayList<>();
			for (Robot r : print.costs.keySet()) {
				if (r.name.equals(nomRobot)) {
					couts = print.costs.get(r);
				}
			}
			for (Cost c : couts) {
				if (c.type.equals("ore")) {
					if (c.qtt > ore) {
						return false;
					}
				}
				if (c.type.equals("clay")) {
					if (c.qtt > clay) {
						return false;
					}
				}
				if (c.type.equals("obsidian")) {
					if (c.qtt > obsidian) {
						return false;
					}
				}
				if (c.type.equals("geode")) {
					if (c.qtt > geode) {
						return false;
					}
				}
			}
			return true;
		}

		public boolean canBuildRobot(String ressource, Blueprint print) {
			return coutsSupportable(ressource) && besoinPlusRessource(ressource, print);
		}

		public void setPrint(Blueprint print) {
			this.print = print;
		}

		public int getMinute() {
			return minute;
		}

		public void setMinute(int minute) {
			this.minute = minute;
		}

		public int getNbOreRobot() {
			return nbOreRobot;
		}

		public void setNbOreRobot(int nbOreRobot) {
			this.nbOreRobot = nbOreRobot;
		}

		public int getNbClayRobot() {
			return nbClayRobot;
		}

		public void setNbClayRobot(int nbClayRobot) {
			this.nbClayRobot = nbClayRobot;
		}

		public int getNbObsidianRobot() {
			return nbObsidianRobot;
		}

		public void setNbObsidianRobot(int nbObsidianRobot) {
			this.nbObsidianRobot = nbObsidianRobot;
		}

		public int getNbGeodeRobot() {
			return nbGeodeRobot;
		}

		public void setNbGeodeRobot(int nbGeodeRobot) {
			this.nbGeodeRobot = nbGeodeRobot;
		}

		public int getOre() {
			return ore;
		}

		public void setOre(int ore) {
			this.ore = ore;
		}

		public int getClay() {
			return clay;
		}

		public void setClay(int clay) {
			this.clay = clay;
		}

		public int getObsidian() {
			return obsidian;
		}

		public void setObsidian(int obsidian) {
			this.obsidian = obsidian;
		}

		public int getGeode() {
			return geode;
		}

		public void setGeode(int geode) {
			this.geode = geode;
		}

		public State(Blueprint print, int minute, int nbOreRobot, int nbClayRobot, int nbObsidianRobot,
				int nbGeodeRobot, int ore, int clay, int obsidian, int geode) {
			super();
			this.print = print;
			this.minute = minute;
			this.nbOreRobot = nbOreRobot;
			this.nbClayRobot = nbClayRobot;
			this.nbObsidianRobot = nbObsidianRobot;
			this.nbGeodeRobot = nbGeodeRobot;
			this.ore = ore;
			this.clay = clay;
			this.obsidian = obsidian;
			this.geode = geode;
		}

		public State() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "State [minute=" + minute + ",actions=" + actions + " ,nbOreRobot=" + nbOreRobot + ", nbClayRobot="
					+ nbClayRobot + ", nbObsidianRobot=" + nbObsidianRobot + ", nbGeodeRobot=" + nbGeodeRobot + ", ore="
					+ ore + ", clay=" + clay + ", obsidian=" + obsidian + ", geode=" + geode + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(clay, geode, minute, nbClayRobot, nbGeodeRobot, nbObsidianRobot,
					nbOreRobot, obsidian, ore);
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
			State other = (State) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return clay == other.clay && geode == other.geode && minute == other.minute
					&& nbClayRobot == other.nbClayRobot && nbGeodeRobot == other.nbGeodeRobot
					&& nbObsidianRobot == other.nbObsidianRobot && nbOreRobot == other.nbOreRobot
					&& obsidian == other.obsidian && ore == other.ore;
		}

		public List<String> getActions() {
			return actions;
		}

		public void setActions(List<String> actions) {
			this.actions = actions;
		}

		private A2022Day19 getEnclosingInstance() {
			return A2022Day19.this;
		}

	}

	private class Blueprint {
		int id;
		Map<Robot, List<Cost>> costs;

		public Blueprint(int id, Map<Robot, List<Cost>> costs) {
			super();
			this.id = id;
			this.costs = costs;
		}

		public int getCout(String nomRobot, String ressource) {
			List<Cost> couts = new ArrayList<>();
			for (Robot r : costs.keySet()) {
				if (r.name.equals(nomRobot)) {
					couts = costs.get(r);
				}
			}
			for (Cost c : couts) {
				if (c.type.equals(ressource)) {
					return c.qtt;
				}
			}
			return 0;
		}

		@Override
		public String toString() {
			StringBuilder st = new StringBuilder();
			st.append("Blueprint " + id + " :");
			st.append("\n");
			for (Entry<Robot, List<Cost>> e : costs.entrySet()) {
				st.append("\n");
				st.append(e.getKey().name + ":");
				st.append("\n");
				for (Cost c : e.getValue()) {
					st.append(c + " ");
				}
			}
			return st.toString();
		}

	}

	public static List<Long> getDuration() {
		A2022Day19 d = new A2022Day19(19);
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

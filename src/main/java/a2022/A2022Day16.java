package a2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import outils.MesOutils;
import outils.UniformCostSearch;

public class A2022Day16 extends A2022 {

	public A2022Day16(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day16 d = new A2022Day16(16);

		long startTime = System.currentTimeMillis();
		System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		// System.out.println(d.s2(false));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public class CustomPair {
		private String v1;
		private String v2;

		public CustomPair(String v1, String v2) {
			super();
			this.v1 = v1;
			this.v2 = v2;
		}

		// standard getters and setters
	}

	public int s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		Situation situationInitiale = getSituationInitiale(b, input);
		Map<CustomPair, Integer> distanceEntreValve = getDist(situationInitiale.valves);
		for (Entry<CustomPair, Integer> cp : distanceEntreValve.entrySet()) {

			// System.out.println("la distance de "+cp.getKey().v1+" à "+cp.getKey().v2+"
			// est de "+cp.getValue());
		}
		Set<Situation> situations = new HashSet<>();
		situations.addAll(situationInitiale.nextStep2(distanceEntreValve));
		int max = getMax2(situations, distanceEntreValve);

		return max;
	}

	private int getMax2(Set<Situation> situations, Map<CustomPair, Integer> distanceEntreValve) {

		int max = 0;

		for (Situation situ : situations) {
			int unMAx = getUnMax2(Set.of(situ), distanceEntreValve);
			if (unMAx > max) {
				max = unMAx;
			}
		}

		return max;

	}

	private int getUnMax2(Set<Situation> situations, Map<CustomPair, Integer> distanceEntreValve) {
		boolean continuer = true;
		int max = 0;
		while (continuer) {
			Set<Situation> situationsSuivantes = new HashSet<>();
			for (Situation situ : situations) {

				situationsSuivantes.addAll(situ.nextStep2(distanceEntreValve));

			}
			Set<Situation> situationsSuivantesClean = clean(situationsSuivantes);
			situations = new HashSet<>(situationsSuivantesClean);
			continuer = situations.stream().anyMatch(s -> s.minutes < 30);
			Situation smax = null;
			for (Situation situ : situations) {
				int totPress = situ.totalPression;
				if (totPress > max) {
					max = totPress;
					smax = new Situation(situ);
				}
			}
//			System.out.println(smax);
			// System.out.println("MAx :" + max);
		}
		situations = situations.stream().filter(s -> s.minutes == 30).collect(Collectors.toSet());

		for (Situation situ : situations) {
			int totPress = situ.totalPression;
			if (totPress > max) {
				max = totPress;
				System.out.println(situ);
				System.out.println(situ.itineraire);
			}
		}

		return max;
	}

	public int s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		Situation situationInitiale = getSituationInitiale(b, input);
		Map<CustomPair, Integer> distanceEntreValve = getDist(situationInitiale.valves);
		Set<Situation> situations = new HashSet<>();
		situations.add(situationInitiale);
		int max = getMaxRec(situations, distanceEntreValve);

		return max;
	}

	private int getMaxRec(Set<Situation> situations, Map<CustomPair, Integer> distanceEntreValve) {
		Set<Integer> pressures = new HashSet<>();
		pressures.add(0);
		for (Situation situ : situations) {
			if (situ.minutes == 30) {
				if (situ.totalPression > 1700) {
					System.out.println(situ);
				}
				pressures.add(situ.totalPression);
			} else {
				if (situ.minutes < 30) {
					pressures.add(getMaxRec(situ.nextStepAvecRec(distanceEntreValve), distanceEntreValve));
				} else {
					pressures.add(0);
				}
			}
		}
		return MesOutils.getMaxIntegerFromList(pressures.stream().toList());
	}

	private Set<Situation> getNextSituations(Situation situ, Map<CustomPair, Integer> distanceEntreValve) {
		// TODO Auto-generated method stub
		return null;
	}

	private int getMax(Set<Situation> situations, Map<CustomPair, Integer> distanceEntreValve) {

		int max = 0;

		for (Situation situ : situations) {
			int unMAx = getUnMAx(Set.of(situ), distanceEntreValve);
			if (unMAx > max) {
				max = unMAx;
			}
		}

		return max;
	}

	private int getUnMAx(Set<Situation> situations, Map<CustomPair, Integer> distanceEntreValve) {
		boolean continuer = true;
		int max = 0;
		while (continuer) {
			Set<Situation> situationsSuivantes = new HashSet<>();
			for (Situation situ : situations) {

				situationsSuivantes.addAll(situ.nextStep(distanceEntreValve));

			}
			Set<Situation> situationsSuivantesClean = clean(situationsSuivantes);
			situations = new HashSet<>(situationsSuivantesClean);
			continuer = situations.stream().anyMatch(s -> s.minutes < 30);

			Situation smax = null;
			for (Situation situ : situations) {
				int totPress = situ.totalPression;
				if (totPress > max) {
					max = totPress;
					smax = new Situation(situ);
				}
			}
		}
		situations = situations.stream().filter(s -> s.minutes == 30).collect(Collectors.toSet());

		for (Situation situ : situations) {
			int totPress = situ.totalPression;
			if (totPress > max) {
				max = totPress;
				System.out.println(situ);
				System.out.println(situ.itineraire);
			}
		}

		return max;
	}

	private Set<String> getOuverte(Set<Valve> valves) {
		HashSet<String> o = new HashSet<>();
		for (Valve v : valves) {
			if (v.isOpen()) {
				o.add(v.name);
			}
		}
		return o;
	}

	private Map<CustomPair, Integer> getDist(Set<Valve> valves) {
		Map<CustomPair, Integer> res = new HashMap<>();
		UniformCostSearch.Graph graph = new UniformCostSearch.Graph();
		List<Valve> lv = new ArrayList<>(valves);
		for (int i = 0; i < lv.size(); i++) {
			graph.addNode(i);
		}
		for (int i = 0; i < lv.size(); i++) {
			for (int j = 0; j < lv.size(); j++) {
				if (i != j) {
					if (lv.get(i).nomsVoisines.contains(lv.get(j).name)) {
						graph.addEdge(i, j, 1);
					}
				}
			}
		}
		for (int i = 0; i < lv.size(); i++) {
			for (int j = 0; j < lv.size(); j++) {
				if (i != j) {
					res.put(new CustomPair(lv.get(i).name, lv.get(j).name), graph.uniformSearch(i, j));
				}
			}
		}
		return res;
	}

	private Set<Situation> clean(Set<Situation> situationsSuivantes) {
		Set<Situation> situationsSuivantesClean = new HashSet<>();
		/*
		 * for (Situation situ : situationsSuivantes) { Set<Situation> memeValve =
		 * situationsSuivantes.stream() .filter(s -> s.valves.equals(situ.valves) &&
		 * s.curPos.equals(situ.curPos)) .collect(Collectors.toSet()); int maxPress =
		 * MesOutils.getMaxIntegerFromList(memeValve.stream().map(s ->
		 * s.totalPression).toList());
		 * situationsSuivantesClean.add(memeValve.stream().filter(s -> s.totalPression
		 * == maxPress).findFirst().get()); }
		 */
		situationsSuivantesClean = situationsSuivantes.stream().filter(s -> s.minutes <= 30)
				.collect(Collectors.toSet());
		return situationsSuivantesClean;
	}

	private Situation getSituationInitiale(boolean b, List<String> input) {
		Set<Valve> valves = new HashSet<>();
		for (String s : input) {
			String name = s.substring(6, 8);
			int flowRate = Integer.parseInt(s.split(";")[0].split("=")[1].trim());
			String[] nameV = s.split("valve")[1].substring(1).trim().split(",");
			Set<String> nameVoisine = new HashSet<>();
			for (String v : nameV) {
				nameVoisine.add(v.trim());
			}
			valves.add(new Valve(name, flowRate, nameVoisine));
		}
		for (Valve v : valves) {
			v.setVoisineFromName(valves);
		}
		return new Situation(valves, 0, 0);
	}

	public static class Itineraire {
		List<String> itineraire;

		public List<String> getItineraire() {
			return itineraire;
		}

		public void setItineraire(List<String> itineraire) {
			this.itineraire = itineraire;
		}

		public Itineraire(List<String> itineraire) {
			super();
			this.itineraire = itineraire;
		}

		@Override
		public int hashCode() {
			return Objects.hash(itineraire);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Itineraire other = (Itineraire) obj;
			return Objects.equals(itineraire, other.itineraire);
		}

	}

	public static class Situation {
		Set<Valve> valves;
		int minutes;
		int totalPression;
		String quoiFaire;
		String curPos;
		String curPosE;
		List<String> itineraire;

		public List<String> getItineraire() {
			return itineraire;
		}


		public String getCurPosE() {
			return curPosE;
		}

		public void setCurPosE(String curPosE) {
			this.curPosE = curPosE;
		}

		public Set<Situation> nextStepAvecRec(Map<CustomPair, Integer> distanceEntreValve) {
			Set<Situation> nextSituations = new HashSet<>();
			Valve curValve = getCurValve(curPos, valves);
			List<Valve> destPossible = valves.stream().filter(v ->curValve.nomsVoisines.contains(v.name)).toList();
			if (!curValve.open && curValve.flowRate > 0) {
				Situation nextSituation = new Situation(this);
				for (Valve v : nextSituation.valves) {
					if (v.name.equals(curPos)) {
						v.setOpen(true);
					}
				}
				nextSituation.setMinutes(nextSituation.getMinutes() + 1);
				nextSituation.totalPression += (30 - nextSituation.getMinutes()) * curValve.getFlowRate();
				nextSituation.setMinutes(this.minutes + 1);
				nextSituations.add(nextSituation);
				return nextSituations;
			}
			for (Valve dest : destPossible) {
				Situation nextSituation = new Situation(this);
				nextSituation.setCurPos(dest.name);
				nextSituation.itineraire.add(dest.name);
				int tempsDeTrajet = getDist(distanceEntreValve, curPos, dest.name);
				nextSituation.setMinutes(this.minutes + tempsDeTrajet);
				nextSituations.add(nextSituation);

			}
			if (destPossible.size() == 0) {
				Situation nextSituation = new Situation(this);
				nextSituation.setMinutes(30);
				nextSituations.add(nextSituation);
			}
			return nextSituations;
		}

		public Collection<? extends Situation> nextStep2(Map<CustomPair, Integer> distanceEntreValve) {
			Set<Situation> nextSituations = new HashSet<>();
			Valve curValve = getCurValve(curPos, valves);
			Valve curValveE = getCurValve(curPosE, valves);

			boolean jeFerme = !curValve.open && curValve.flowRate > 0;
			boolean eleFerme = !curValveE.open && curValveE.flowRate > 0;

			if (jeFerme && eleFerme) {
				Situation nextSituation = new Situation(this);
				for (Valve v : nextSituation.valves) {
					if (v.name.equals(curPos) || v.name.equals(curPosE)) {
						v.setOpen(true);
					}
				}
				nextSituation.setMinutes(nextSituation.getMinutes() + 1);
				nextSituation.totalPression += (26 - nextSituation.getMinutes())
						* (curValve.getFlowRate() + curValve.getFlowRate());
				nextSituation.setMinutes(this.minutes + 1);
				nextSituations.add(nextSituation);
				return nextSituations;
			}
			if (jeFerme && !eleFerme) {
				Situation nextSituation = new Situation(this);
				for (Valve v : nextSituation.valves) {
					if (v.name.equals(curPos)) {
						v.setOpen(true);
					}
				}
				nextSituation.setMinutes(nextSituation.getMinutes() + 1);
				nextSituation.totalPression += (26 - nextSituation.getMinutes()) * (curValve.getFlowRate());
				nextSituation.setMinutes(this.minutes + 1);
				List<Valve> destProchePossible = valves.stream().filter(v -> curValveE.nomsVoisines.contains(v.name))
						.toList();
				for (Valve dest : destProchePossible) {
					Situation nextSituationA2 = new Situation(nextSituation);
					nextSituationA2.setCurPosE(dest.name);
					nextSituationA2.itineraire.add(dest.name);
					nextSituations.add(nextSituationA2);

				}
				nextSituations.add(nextSituation);
				return nextSituations;
			}
			if (!jeFerme && eleFerme) {
				Situation nextSituation = new Situation(this);
				for (Valve v : nextSituation.valves) {
					if (v.name.equals(curPosE)) {
						v.setOpen(true);
					}
				}
				nextSituation.setMinutes(nextSituation.getMinutes() + 1);
				nextSituation.totalPression += (26 - nextSituation.getMinutes()) * (curValveE.getFlowRate());
				nextSituation.setMinutes(this.minutes + 1);
				List<Valve> destProchePossible = valves.stream().filter(v -> curValve.nomsVoisines.contains(v.name))
						.toList();
				for (Valve dest : destProchePossible) {
					Situation nextSituationA2 = new Situation(nextSituation);
					nextSituationA2.setCurPos(dest.name);
					nextSituationA2.itineraire.add(dest.name);
					nextSituations.add(nextSituationA2);
				}
				nextSituations.add(nextSituation);
				return nextSituations;
			}
			List<Valve> destProchePossible = valves.stream().filter(v -> curValve.nomsVoisines.contains(v.name))
					.toList();
			List<Valve> destProchePossibleE = valves.stream().filter(v -> curValveE.nomsVoisines.contains(v.name))
					.toList();

			for (Valve dest : destProchePossible) {
				Situation nextSituation = new Situation(this);
				nextSituation.setCurPos(dest.name);
				nextSituation.itineraire.add(dest.name);
				nextSituation.setMinutes(this.minutes + 1);
				for (Valve destE : destProchePossibleE) {
					Situation nextSituationA2 = new Situation(nextSituation);
					nextSituationA2.setCurPosE(destE.name);
					nextSituation.itineraire.add(destE.name);
					nextSituations.add(nextSituationA2);
				}

			}
			if (destProchePossible.size() == 0) {
				Situation nextSituation = new Situation(this);
				nextSituation.setMinutes(26);
				nextSituations.add(nextSituation);
			}
			if (destProchePossibleE.size() == 0) {
				Situation nextSituation = new Situation(this);
				nextSituation.setMinutes(26);
				nextSituations.add(nextSituation);
			}
			return nextSituations;
		}

		public int getNbOpenValveValves() {
			int cpt = 0;
			for (Valve v : valves) {
				if (v.open) {
					cpt++;
				}
			}
			return cpt;
		}

		public void setItineraire(List<String> itineraire) {
			this.itineraire = itineraire;
		}

		public String getCurPos() {
			return curPos;
		}

		public StringBuilder getMessageRelease() {
			StringBuilder st = new StringBuilder();
			st.append("Valves ");
			int cpt = 0;
			for (Valve v : valves) {
				if (v.isOpen()) {
					cpt += v.flowRate;
					st.append(" " + v.name + " ");
				}
			}
			st.append("are open, releasing " + cpt + " pressure.");
			return st;
		}

		public void setCurPos(String curPos) {
			this.curPos = curPos;
		}

		public String getQuoiFaire() {
			return quoiFaire;
		}

		public Set<Situation> nextStep(Map<CustomPair, Integer> distanceEntreValve) {
			Set<Situation> nextSituations = new HashSet<>();
			Valve curValve = getCurValve(curPos, valves);
			List<Valve> destPossible = valves.stream().filter(v -> v.flowRate > 0 && !v.open).toList();
			if (!curValve.open && curValve.flowRate > 0) {
				Situation nextSituation = new Situation(this);
				for (Valve v : nextSituation.valves) {
					if (v.name.equals(curPos)) {
						v.setOpen(true);
					}
				}
				nextSituation.setMinutes(nextSituation.getMinutes() + 1);
				nextSituation.totalPression += (30 - nextSituation.getMinutes()) * curValve.getFlowRate();
				nextSituation.setMinutes(this.minutes + 1);
				nextSituations.add(nextSituation);
				return nextSituations;
			}
			for (Valve dest : destPossible) {
				Situation nextSituation = new Situation(this);
				nextSituation.setCurPos(dest.name);
				nextSituation.itineraire.add(dest.name);
				int tempsDeTrajet = getDist(distanceEntreValve, curPos, dest.name);
				nextSituation.setMinutes(this.minutes + tempsDeTrajet);
				nextSituations.add(nextSituation);

			}
			if (destPossible.size() == 0) {
				Situation nextSituation = new Situation(this);
				nextSituation.setMinutes(30);
				nextSituations.add(nextSituation);
			}
			return nextSituations;
		}

		private int getDist(Map<CustomPair, Integer> distanceEntreValve, String curPos2, String name) {
			for (Entry<CustomPair, Integer> cp : distanceEntreValve.entrySet()) {
				if (cp.getKey().v1.equals(curPos2) && cp.getKey().v2.equals(name)) {
					// System.out.println("la distance de "+cp.getKey().v1+" à "+cp.getKey().v2+"
					// est de "+cp.getValue());
					return cp.getValue();
				}
			}
			return 0;
		}

		private Valve getCurValve(String curPos2, Set<Valve> valves2) {

			for (Valve v : valves) {
				if (v.name.equals(curPos2)) {
					return v;
				}
			}

			return null;
		}

		public void setQuoiFaire(String quoiFaire) {
			this.quoiFaire = quoiFaire;
		}

		public Set<Valve> getValves() {
			return valves;
		}

		public void setValves(Set<Valve> valves) {
			this.valves = valves;
		}

		public int getMinutes() {
			return minutes;
		}

		public void setMinutes(int minutes) {
			this.minutes = minutes;
		}

		public int getTotalPression() {
			return totalPression;
		}

		public void setTotalPression(int totalPression) {
			this.totalPression = totalPression;
		}

		public Situation(Set<Valve> valves, int minutes, int totalPression) {
			super();
			this.valves = valves;
			this.minutes = minutes;
			this.totalPression = totalPression;
			this.quoiFaire = "move";
			this.curPos = "AA";
			this.curPosE = "AA";
			this.itineraire = List.of("AA");
		}

		@Override
		public String toString() {
			return itineraire + ", minutes=" + minutes + ", totalPression=" + totalPression + ", " + getMessageRelease()
					+ "]";
		}

		public Situation(Set<Valve> valves, int minutes, int totalPression, String quoiFaire, String curPos) {
			super();
			this.valves = valves;
			this.minutes = minutes;
			this.totalPression = totalPression;
			this.quoiFaire = quoiFaire;
			this.curPos = curPos;
		}

		public Situation(Situation s) {
			super();
			Set<Valve> nv = new HashSet<>();
			for (Valve v : s.valves) {
				Valve aNewV = new Valve(v.name, v.flowRate, v.nomsVoisines, v.open);
				aNewV.setVoisineFromName(s.valves);
				nv.add(aNewV);
			}

			this.itineraire = new ArrayList<>(s.itineraire);
			this.valves = nv;
			this.minutes = s.minutes;
			this.totalPression = s.totalPression;
			this.quoiFaire = s.quoiFaire;
			this.curPos = s.curPos;
		}

		@Override
		public int hashCode() {
			return Objects.hash(curPos, minutes, quoiFaire, totalPression, valves);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Situation other = (Situation) obj;
			return Objects.equals(curPos, other.curPos) && minutes == other.minutes
					&& Objects.equals(quoiFaire, other.quoiFaire) && totalPression == other.totalPression
					&& Objects.equals(valves, other.valves);
		}

	}

	private static class Valve {
		String name;
		int flowRate;
		Set<Valve> voisines;
		Set<String> nomsVoisines;
		boolean open;

		public boolean isOpen() {
			return open;
		}

		public void setOpen(boolean open) {
			this.open = open;
		}

		public Set<String> getNomsVoisines() {
			return nomsVoisines;
		}

		public void setVoisineFromName(Set<Valve> valves) {
			Set<Valve> lesVoisines = new HashSet<>();
			for (String name : nomsVoisines) {
				for (Valve v : valves) {
					if (v.name.equals(name)) {
						lesVoisines.add(v);
					}
				}
			}
			setVoisines(lesVoisines);

		}

		public void setNomsVoisines(Set<String> nomsVoisines) {
			this.nomsVoisines = nomsVoisines;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getFlowRate() {
			return flowRate;
		}

		public void setFlowRate(int flowRate) {
			this.flowRate = flowRate;
		}

		public Set<Valve> getVoisines() {
			return voisines;
		}

		public void setVoisines(Set<Valve> voisines) {
			this.voisines = voisines;
		}

		public Valve(String name, int flowRate, Set<String> nomsVoisines) {
			super();
			this.name = name;
			this.flowRate = flowRate;
			this.nomsVoisines = nomsVoisines;
			this.open = false;
		}

		public Valve(String name, int flowRate, Set<String> nomsVoisines, boolean open) {
			super();
			this.name = name;
			this.flowRate = flowRate;
			this.nomsVoisines = nomsVoisines;
			this.open = open;
		}

		@Override
		public String toString() {
			return "Valve [name=" + name + ", flowRate=" + flowRate + ", nomsVoisines=" + nomsVoisines + ", open="
					+ open + "]";
		}

		@Override
		public int hashCode() {
			return Objects.hash(name, open);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Valve other = (Valve) obj;
			return Objects.equals(name, other.name) && open == other.open;
		}

	}

	public static List<Long> getDuration() {
		A2022Day16 d = new A2022Day16(16);
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

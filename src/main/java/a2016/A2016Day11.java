package a2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2016Day11 extends A2016 {
	public A2016Day11(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day11 d = new A2016Day11(11);
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

	public Integer s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		Set<Machine> f1 = listerMachine(input.get(0), b);
		Set<Machine> f2 = listerMachine(input.get(1), b);
		Set<Machine> f3 = listerMachine(input.get(2), b);
		Set<Machine> f4 = listerMachine(input.get(3), b);
		Situation s = new Situation(new Elevator(), f1, f2, f3, f4, 0);
		Game g = new Game(Set.of(s));
		Boolean fini = false;
		while (!fini) {
			fini = nextStep(g);
		}
		return 1;
	}
	public Integer s2(boolean b) {
		List<String> input = Arrays.asList(getInput(false).trim().split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		Set<Machine> f1 = listerMachine(input.get(0), b);
		Set<Machine> f2 = listerMachine(input.get(1), b);
		Set<Machine> f3 = listerMachine(input.get(2), b);
		Set<Machine> f4 = listerMachine(input.get(3), b);
		Situation s = new Situation(new Elevator(), f1, f2, f3, f4, 0);
		Game g = new Game(Set.of(s));
		Boolean fini = false;
		while (!fini) {
			fini = nextStep(g);
		}
		return 1;
	}

	private Boolean nextStep(Game g) {
		Set<Situation> nextStepSituations = new HashSet<>();
		for (Situation s : g.getSituations()) {
			nextStepSituations.addAll(g.getNextSituations(s));
		}
		for (Situation s : nextStepSituations) {
			if (s.isSolve()) {
				System.out.println("fini! " + s.nbMvt);
				return true;
			}
		}
		g.setSituations(nextStepSituations);
		System.out.println("nbmvt " +g.situations.stream().findFirst().get().nbMvt+" size "+ g.getSituations().size());
		return false;
	}

	private Set<Machine> listerMachine(String l, Boolean b) {
		if (b) {
			Set<Machine> res = new HashSet<>();
			String s1 = "";
			if (!l.contains(",")) {
				if (l.contains("nothing")) {
					return res;
				} else {
					if (l.contains("compatible")) {
						s1 = l.substring(l.indexOf("a", 23) + 2);
						res.add(new Machine("m", s1.split("-")[0].trim()));
					} else {
						res.add(new Machine("g", s1.split(" ")[0].trim()));
					}
					return res;
				}

			}
			String[] spl = l.split(",");
			s1 = spl[0];
			s1 = s1.substring(s1.indexOf("a", 23) + 2);
			if (s1.contains("compatible")) {
				res.add(new Machine("m", s1.split("-")[0].trim()));
			} else {
				res.add(new Machine("g", s1.split(" ")[0].trim()));
			}
			for (int i = 1; i < spl.length - 1; i++) {
				s1 = spl[i].substring(3);
				if (s1.contains("compatible")) {
					res.add(new Machine("m", s1.split("-")[0].trim()));
				} else {
					res.add(new Machine("g", s1.split(" ")[0].trim()));
				}
			}
			s1 = spl[spl.length - 1].substring(7);
			if (s1.contains("compatible")) {
				res.add(new Machine("m", s1.split("-")[0].trim()));
			} else {
				res.add(new Machine("g", s1.split(" ")[0].trim()));
			}
			return res;
		}
		Set<Machine> res = new HashSet<>();
		if (l.contains("nothing")) {
			return res;
		} else if (l.contains("and")) {
			res.add(new Machine("m", "hydrogen"));
			res.add(new Machine("m", "lithium"));
		} else if (l.contains("hydrogen generator")) {
			res.add(new Machine("g", "hydrogen"));
		} else {
			res.add(new Machine("g", "lithium"));
		}
		return res;
	}

	private class Game {
		Set<Situation> situations;

		public Game(Set<Situation> situations) {
			super();
			this.situations = situations;
		}


		public Set<Situation> getSituations() {
			return situations;
		}

		public void setSituations(Set<Situation> situations) {
			this.situations = situations;
		}

		public Set<Move> moveEnvisageable(Situation s) {
			Set<Move> moveEnvisageables = new HashSet<>();
			List<Integer> deplacements;
			if (s.elevator.position == 1) {
				for (Machine m1 : s.f1) {
					moveEnvisageables.add(new Move(1, Set.of(m1)));
					for (Machine m2 : s.f1) {
						if (!m1.equals(m2)) {
							moveEnvisageables.add(new Move(1, Set.of(m1, m2)));
						}
					}
				}
			} else if (s.elevator.position == 4) {
				for (Machine m1 : s.f4) {
					moveEnvisageables.add(new Move(-1, Set.of(m1)));
					for (Machine m2 : s.f4) {
						if (!m1.equals(m2)) {
							moveEnvisageables.add(new Move(-1, Set.of(m1, m2)));
						}
					}
				}
			} else if (s.elevator.position == 2) {
				deplacements = List.of(-1, 1);
				for (Integer d : deplacements) {
					for (Machine m1 : s.f2) {
						moveEnvisageables.add(new Move(d, Set.of(m1)));
						for (Machine m2 : s.f2) {
							if (!m1.equals(m2)) {
								moveEnvisageables.add(new Move(d, Set.of(m1, m2)));
							}
						}
					}
				}
			} else {
				deplacements = List.of(-1, 1);
				for (Integer d : deplacements) {
					for (Machine m1 : s.f3) {
						moveEnvisageables.add(new Move(d, Set.of(m1)));
						for (Machine m2 : s.f3) {
							if (!m1.equals(m2)) {
								moveEnvisageables.add(new Move(d, Set.of(m1, m2)));
							}
						}
					}
				}
			}
			return moveEnvisageables;
		}

		public List<Situation> getNextSituations(Situation s) {
			List<Situation> nextSituations = new ArrayList<>();
			Set<Move> moveEnvisageable = moveEnvisageable(s);
			for (Move m : moveEnvisageable) {
				Situation ns = getNextSituationAfterMove(s, m);
				if (allFloorOk(ns)) {
					nextSituations =majNextSituation(nextSituations,ns);
				}
			}
			return nextSituations;
		}

		private List<Situation> majNextSituation(List<Situation> nextSituations, Situation ns) {
			if(ns.f1.contains(new Machine("g","thulium")) || ns.f1.contains(new Machine("m","thulium"))) {
				//return nextSituations;
			}
			if(nextSituations.contains(ns)) {
				int nbMvtDansListe=MesOutils.getMinIntegerFromList(nextSituations.stream().filter(ns::identique)
						.map(Situation::getNbMvt).collect(Collectors.toList()));
				if(ns.nbMvt<nbMvtDansListe) {
					nextSituations.remove(ns);
					nextSituations.add(ns);
				}
				return nextSituations;
			}
			else {
				nextSituations.add(ns);
				return nextSituations;
			}
		}

		private Situation getNextSituationAfterMove(Situation s, Move m) {
			Set<Machine> nf1 = new HashSet<>(s.f1);
			Set<Machine> nf2 = new HashSet<>(s.f2);
			Set<Machine> nf3 = new HashSet<>(s.f3);
			Set<Machine> nf4 = new HashSet<>(s.f4);
			Elevator el = new Elevator();
			el.position = s.elevator.position + m.deplacement;
			el.contenu = new ArrayList<>();
			nf1.removeAll(m.chargement);
			nf2.removeAll(m.chargement);
			nf3.removeAll(m.chargement);
			nf4.removeAll(m.chargement);
			if (el.position == 1) {
				nf1.addAll(m.chargement);
			} else if (el.position == 2) {
				nf2.addAll(m.chargement);
			} else if (el.position == 3) {
				nf3.addAll(m.chargement);
			} else {
				nf4.addAll(m.chargement);
			}
			return new Situation(el, nf1, nf2, nf3, nf4, s.nbMvt + 1);
		}

		private boolean allFloorOk(Situation ns) {
			return floorOk(ns.f1) && floorOk(ns.f2) && floorOk(ns.f3) && floorOk(ns.f4);
		}
	

		boolean floorOk(Set<Machine> f1) {
			if (f1.isEmpty()) {
				return true;
			}
			for (Machine m : f1) {
				if (m.type.equals("m") && !f1.contains(new Machine("g", m.element))
						&& f1.stream().filter(k -> !k.element.equals(m.element) && k.type.equals("g")).count() > 0) {
					return false;
				}
			}
			return true;
		}
	}

	private class Situation {
		private Integer nbMvt;
		Set<Machine> f1;
		Set<Machine> f2;
		Set<Machine> f3;
		Set<Machine> f4;
		private Elevator elevator;

		public Boolean identique(Situation s2) {
			return f1.equals(s2.f1) && f2.equals(s2.f2) && f3.equals(s2.f3) & f4.equals(s2.f4)
					&& elevator.equals(s2.elevator);
		}

		public Boolean isSolve() {
			return f1.stream().filter(m -> m.type.equals("m")).count() == 0L
					&& f2.stream().filter(m -> m.type.equals("m")).count() == 0L
					&& f3.stream().filter(m -> m.type.equals("m")).count() == 0L 
					&& f1.stream().filter(m -> m.type.equals("g")).count() == 0L
							&& f2.stream().filter(m -> m.type.equals("g")).count() == 0L
							&& f3.stream().filter(m -> m.type.equals("g")).count() == 0L;
		}


		public Situation(Elevator elevator, Set<Machine> f1, Set<Machine> f2, Set<Machine> f3, Set<Machine> f4,
				Integer nbMvt) {
			super();
			this.elevator = elevator;
			this.f1 = f1;
			this.f2 = f2;
			this.f3 = f3;
			this.f4 = f4;
			this.nbMvt = nbMvt;
		}

		public Integer getNbMvt() {
			return nbMvt;
		}

		@Override
		public String toString() {
			Set<String> elements = f1.stream().map(Machine::getElement).collect(Collectors.toSet());
			elements.addAll(f2.stream().map(Machine::getElement).collect(Collectors.toSet()));
			elements.addAll(f3.stream().map(Machine::getElement).collect(Collectors.toSet()));
			elements.addAll(f4.stream().map(Machine::getElement).collect(Collectors.toSet()));
			List<String> elementsL = new ArrayList<>(elements);
			Collections.sort(elementsL, (d1,d2) -> d1.compareTo(d2));
			StringBuilder res = new StringBuilder();
			res.append("F4 ");
			if (elevator.position == 4) {
				res.append("E ");
			} else {
				res.append(". ");
			}
			for (int i = 0; i < elementsL.size(); i++) {
				if (f4.contains(new Machine("g", elementsL.get(i)))) {
					res.append(elementsL.get(i).substring(0, 1).toUpperCase()).append("G ");
				} else {
					res.append(".  ");
				}
				if (f4.contains(new Machine("m", elementsL.get(i)))) {
					res.append(elementsL.get(i).substring(0, 1).toUpperCase()).append("M ");
				} else {
					res.append(".  ");
				}
			}
			res.append("\n");
			
			res.append("F3 ");
			if (elevator.position == 3) {
				res.append("E ");
			} else {
				res.append(". ");
			}
			for (int i = 0; i < elementsL.size(); i++) {
				if (f3.contains(new Machine("g", elementsL.get(i)))) {
					res.append(elementsL.get(i).substring(0, 1).toUpperCase()).append("G ");
				} else {
					res.append(".  ");
				}
				if (f3.contains(new Machine("m", elementsL.get(i)))) {
					res.append(elementsL.get(i).substring(0, 1).toUpperCase()).append("M ");
				} else {
					res.append(".  ");
				}
			}
			res.append("\n");
			
			res.append("F2 ");
			if (elevator.position == 2) {
				res.append("E ");
			} else {
				res.append(". ");
			}
			for (int i = 0; i < elementsL.size(); i++) {
				if (f2.contains(new Machine("g", elementsL.get(i)))) {
					res.append(elementsL.get(i).substring(0, 1).toUpperCase()).append("G ");
				} else {
					res.append(".  ");
				}
				if (f2.contains(new Machine("m", elementsL.get(i)))) {
					res.append(elementsL.get(i).substring(0, 1).toUpperCase()).append("M ");
				} else {
					res.append(".  ");
				}
			}
			res.append("\n");
			
			res.append("F1 ");
			if (elevator.position == 1) {
				res.append("E ");
			} else {
				res.append(". ");
			}
			for (int i = 0; i < elementsL.size(); i++) {
				if (f1.contains(new Machine("g", elementsL.get(i)))) {
					res.append(elementsL.get(i).substring(0, 1).toUpperCase()).append("G ");
				} else {
					res.append(".  ");
				}
				if (f1.contains(new Machine("m", elementsL.get(i)))) {
					res.append(elementsL.get(i).substring(0, 1).toUpperCase()).append("M ");
				} else {
					res.append(".  ");
				}
			}
			res.append("\n");
			return res.toString();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(elevator, f1, f2, f3, f4);
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
			Situation other = (Situation) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Objects.equals(elevator, other.elevator) && Objects.equals(f1, other.f1)
					&& Objects.equals(f2, other.f2) && Objects.equals(f3, other.f3) && Objects.equals(f4, other.f4);
		}

		private A2016Day11 getEnclosingInstance() {
			return A2016Day11.this;
		}

		
	}

	private class Move {
		Integer deplacement;
		Set<Machine> chargement;
		public Move(Integer deplacement, Set<Machine> chargement) {
			super();
			this.deplacement = deplacement;
			this.chargement = chargement;
		}

		@Override
		public String toString() {
			return "Move [deplacement=" + deplacement + ", chargement=" + chargement + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(chargement, deplacement);
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
			Move other = (Move) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Objects.equals(chargement, other.chargement) && Objects.equals(deplacement, other.deplacement);
		}

		private A2016Day11 getEnclosingInstance() {
			return A2016Day11.this;
		}

	}

	private class Elevator {
		Integer position;
		List<Machine> contenu;

		public Elevator() {
			super();
			this.position = 1;
			this.contenu = new ArrayList<>();
		}

	
		@Override
		public String toString() {
			return "Elevator [position=" + position + ", contenu=" + contenu + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(contenu, position);
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
			Elevator other = (Elevator) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Objects.equals(contenu, other.contenu) && Objects.equals(position, other.position);
		}

		private A2016Day11 getEnclosingInstance() {
			return A2016Day11.this;
		}

	}

	private class Machine {
		String type;
		String element;

		public Machine(String type, String element) {
			super();
			this.type = type;
			this.element = element;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(element, type);
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
			Machine other = (Machine) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Objects.equals(element, other.element) && Objects.equals(type, other.type);
		}

		private A2016Day11 getEnclosingInstance() {
			return A2016Day11.this;
		}
		

		public String getElement() {
			return element;
		}

		@Override
		public String toString() {
			return "Machine [type=" + type + ", element=" + element + "]";
		}

	}

	

	public static List<Long> getDuration() {
		A2016Day11 d = new A2016Day11(11);
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

package a2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


public class Day7A2018 {

	public static void main(String[] args) {
		// s1();
		s2();

	}

	private static void s2() {
		List<Rule> rules = getRules();
		HashSet<String> lettres = new HashSet<>();
		for (Rule r : rules) {
			lettres.add(r.after);
			lettres.add(r.before);
		}
		List<String> lettresList = new ArrayList<>(lettres);
		Collections.sort(lettresList);
		HashMap<String, Integer> letterDuration = new HashMap<>();
		int i = 1;
		for (String l : lettresList) {
			letterDuration.put(l, 60 + i);
			i++;
		}

		int conteurSecond = 0;
		List<Worker> workers = new ArrayList<>();
		for (int num = 1; num < 6; num++) {
			workers.add(new Worker(num));
		}
		LinkedList<String> done = new LinkedList<>();
		ArrayList<String> available = new ArrayList<>();
		LinkedList<String> prerequisites = new LinkedList<>();

		boolean workerDispo = workerDispo(workers);
		System.out.println(workerDispo);
		while (done.size() < lettresList.size()) {
			// definir les taches possibles

			for (String lettre : lettres) {
				if (!done.contains(lettre)) {
					if (!lettreEnCours(lettre,workers)) {
						boolean add = true;
						prerequisites = getPrerequisites(lettre);
						if (!done.containsAll(prerequisites)) {
							add = false;
						}
						if (add && !available.contains(lettre)) {
							available.add(lettre);
						}
					}
				}
			}
			for (Worker w : workers) {
				// nouvelle tache pour inactif si dispo
				Collections.sort(available);
				if (!w.isOqp() && !available.isEmpty()) {
					w.setOqp(true);
					w.setStep(available.get(0));
					available.remove(0);
					w.setNbSecSurStep(0);
				}
			}

			// au travail!
			for (Worker w : workers) {
				if (w.isOqp()) {
					w.setNbSecSurStep(w.getNbSecSurStep() + 1);
				}
			}
			conteurSecond++;

			for (Worker w : workers) {
				// Un worker a fini son travail?
				if (w.isOqp() && (w.nbSecSurStep == letterDuration.get(w.Step))) {
					w.setOqp(false);
					done.add(w.Step);
				}
			}
			System.out.println("temps : " + conteurSecond + "  " + done);
		}

		System.out.println(conteurSecond);

	}

	private static boolean lettreEnCours(String lettre, List<Worker> workers) {
		boolean res=false;
		for(Worker w:workers) {
			if(lettre.equals(w.Step)) {
				return true;
			}
		}
		return res;
	}

	private static boolean workerDispo(List<Worker> workers) {
		boolean res = false;
		for (Worker w : workers) {
			if (!w.oqp) {
				return true;
			}
		}
		return res;
	}

	private static LinkedList<String> solve(List<Rule> rules, String lastLetter, HashSet<String> lettres) {
		LinkedList<String> order = new LinkedList<>();
		ArrayList<String> available = new ArrayList<>();
		LinkedList<String> prerequisites = new LinkedList<>();
		String lastOrdered = "";
		while (order.size() < lettres.size() - 1) {
			for (String lettre : lettres) {
				if (!order.contains(lettre)) {
					boolean add = true;
					prerequisites = getPrerequisites(lettre);
					if (!order.containsAll(prerequisites)) {
						add = false;
					}
					if (add && !available.contains(lettre)) {
						available.add(lettre);
					}

				}
			}
			Collections.sort(available);
			lastOrdered = available.get(0);
			available.remove(0);
			order.add(lastOrdered);
			System.out.println(order);
		}

		order.add(lastLetter);
		System.out.println(order.size());
		return order;
	}

	private static void s1() {
		List<Rule> rules = getRules();
		HashSet<String> lettres = new HashSet<>();
		for (Rule r : rules) {
			lettres.add(r.after);
			lettres.add(r.before);
		}
		System.out.println(lettres.size());
		LinkedList<String> before = new LinkedList<>();
		LinkedList<String> after = new LinkedList<>();
		LinkedList<String> order = new LinkedList<>();
		for (Rule r : rules) {
			System.out.println(r);
			before.add(r.before);
			after.add(r.after);
		}
		boolean first = false;
		boolean last = false;
		for (int i = 0; i < before.size(); i++) {
			if (!after.contains(before.get(i)) && !first) {
				order.add(0, before.get(i));
				first = true;
			}
		}
		String lastLetter = "";
		for (int i = 0; i < after.size(); i++) {
			if (!before.contains(after.get(i)) && !last) {
				lastLetter = after.get(i);
				last = true;
			}
		}
		order = solve(rules, lastLetter, lettres);
		StringBuilder res = new StringBuilder();
		for (String s : order) {
			res.append(s);
		}
		for (Rule r : rules) {
			boolean erreur = false;
			erreur = controle(r, order);
			if (erreur) {
				System.out.println(r);
			}
		}
		for (String l : lettres) {
			System.out.println(l + " " + getPrerequisites(l));
		}
		System.out.println(res);
	}

	private static boolean controle(Rule r, LinkedList<String> order) {
		int indexB = -1;
		int indexA = -1;
		int cpt = 0;
		for (String s : order) {
			if (order.get(cpt).equals(r.before)) {
				indexB = cpt;
			}
			if (order.get(cpt).equals(r.after)) {
				indexA = cpt;
			}
			cpt++;
		}

		return indexB < indexA;
	}

	private static LinkedList<String> getPrerequisites(String after) {
		LinkedList<String> prerequisites = new LinkedList<>();
		List<Rule> rules = getRules();
		for (Rule r : rules) {
			if (r.after.equals(after)) {
				prerequisites.add(r.before);
			}
		}
		return prerequisites;
	}

	private static List<Rule> getRules() {
		String message = getMessage(
				"C:\\Users\\z11r88\\eclipse-workspace2\\advent_of_code\\src\\advent_of_code\\main\\resources\\a2018\\input7");
		List<Rule> rules = new ArrayList<>();

		for (String s : message.split("\n")) {
			rules.add(new Rule(s.substring(5, 6), s.substring(36, 37)));
		}
		return rules;
	}

	
	public static class Rule {
		String before;
		String after;

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + ((after == null) ? 0 : after.hashCode());
			result = prime * result + ((before == null) ? 0 : before.hashCode());
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
			Rule other = (Rule) obj;
			if (after == null) {
				if (other.after != null)
					return false;
			} else if (!after.equals(other.after))
				return false;
			if (before == null) {
				if (other.before != null)
					return false;
			} else if (!before.equals(other.before))
				return false;
			return true;
		}

		public Rule(String before, String after) {
			super();
			this.before = before;
			this.after = after;
		}

		@Override
		public String toString() {
			return "Step " + before + " must be finished before step " + after + " can begin.";
		}

	}

	public static class Worker {
		int numero;

		public int getNumero() {
			return numero;
		}

		public void setNumero(int numero) {
			this.numero = numero;
		}

		public String getStep() {
			return Step;
		}

		public void setStep(String step) {
			Step = step;
		}

		public Integer getNbSecSurStep() {
			return nbSecSurStep;
		}

		public void setNbSecSurStep(Integer nbSecSurStep) {
			this.nbSecSurStep = nbSecSurStep;
		}

		public boolean isOqp() {
			return oqp;
		}

		public void setOqp(boolean oqp) {
			this.oqp = oqp;
		}

		@Override
		public String toString() {
			return "Worker [numero=" + numero + ", Step=" + Step + ", nbSecSurStep=" + nbSecSurStep + ", oqp=" + oqp
					+ "]";
		}

		String Step = "0";
		Integer nbSecSurStep = 0;
		boolean oqp = false;

		public Worker(int numero) {
			super();
			this.numero = numero;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + numero;
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
			Worker other = (Worker) obj;
			if (numero != other.numero)
				return false;
			return true;
		}

	}

	private static String getMessage(String monFic) {
		String line = "";
		try {
			Files.readString(Paths.get(monFic));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return line;
	}

}

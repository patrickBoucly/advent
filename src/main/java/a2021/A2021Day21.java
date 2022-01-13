package a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class A2021Day21 extends A2021 {
	final static int nbVic = 21;

	public A2021Day21(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day21 d = new A2021Day21(21);
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

	private long s2(boolean b) {
		HashMap<Integer, Integer> joueurPose = new HashMap<>();
		if (b) {
			joueurPose.put(1, 4);
			joueurPose.put(2, 7);
		} else {
			joueurPose.put(1, 4);
			joueurPose.put(2, 8);
		}
		HashMap<Integer, Long> scores = new HashMap<>();
		scores.put(1, 0L);
		scores.put(2, 0L);
		Jeu2 j = new Jeu2(scores, joueurPose);
		HashMap<Jeu2, Long> c = j.jouer();
		List<Situation> ls = new ArrayList<>();
		for (Jeu2 g : c.keySet()) {
			ls.add(new Situation(g.scores.get(1), g.scores.get(2), c.get(g), c.get(g),g.joueurPose.get(1),g.joueurPose.get(2)));
		}
		Long v1 = 0L;
		Long v2 = 0L;
		for (Situation s : ls) {
			if (s.s1 >= nbVic) {
				v1 += s.cpt1;
			} else if (s.s2 >= nbVic) {
				v2 += s.cpt2;
			}
		}
		//System.out.println(ls);
		ls = ls.stream().filter(s -> s.s1 < nbVic && s.s2 < nbVic).collect(Collectors.toList());
		//System.out.println(ls);
		int cur = 2;
		while (!ls.isEmpty()) {
			ls = jouerLS(ls, cur);
			for (Situation s : ls) {
				if (s.s1 >= nbVic) {
					v1 += s.cpt1;
				} else if (s.s2 >= nbVic) {
					v2 += s.cpt2;
				}
			}
			ls = ls.stream().filter(s -> s.s1 < nbVic && s.s2 < nbVic).collect(Collectors.toList());
			cur = (cur == 1) ? 2 : 1;
		}
		Long w = v1;
		if (v2 > v1) {
			w = v2;
		}
		//System.out.println(v1);
		//System.out.println(v2);
		return w;
	}

	private List<Situation> jouerLS(List<Situation> ls, int cur) {
		List<Situation> nls = new ArrayList<>();
		for (Situation s : ls) {
			for (Integer sum : Jeu2.sumFreq.keySet()) {
				
				if (cur == 2) {
					int np=-1;
					String newPose = String.valueOf(s.p2 + sum);
					if (newPose.substring(newPose.length() - 1).equals("0")) {
						np=10;
					} else {
						np=Integer.parseInt(newPose.substring(newPose.length() - 1));
					}
					nls.add(new Situation(s.s1, s.s2 + np, s.cpt1 * Jeu2.sumFreq.get(sum),
							s.cpt2 * Jeu2.sumFreq.get(sum),s.p1,np));
				} else {
					int np=-1;
					String newPose = String.valueOf(s.p1 + sum);
					if (newPose.substring(newPose.length() - 1).equals("0")) {
						np=10;
					} else {
						np=Integer.parseInt(newPose.substring(newPose.length() - 1));
					}
					nls.add(new Situation(s.s1 + np, s.s2, s.cpt1 * Jeu2.sumFreq.get(sum),
							s.cpt2 * Jeu2.sumFreq.get(sum),np,s.p2));

				}
			}
		}
		return nls;
	}

	private long s1(boolean b) {
		HashMap<Integer, Integer> joueurPose = new HashMap<>();
		if (b) {
			joueurPose.put(1, 4);
			joueurPose.put(2, 7);
		} else {
			joueurPose.put(1, 4);
			joueurPose.put(2, 8);
		}
		HashMap<Integer, Long> scores = new HashMap<>();
		scores.put(1, 0L);
		scores.put(2, 0L);
		Jeu1 j = new Jeu1(scores, joueurPose, 0L, 0L);
		while (!j.fini()) {
			j.jouer();
		}
		Long scoreMin = Long.MAX_VALUE;
		for (Long sc : scores.values()) {
			if (sc < scoreMin) {
				scoreMin = sc;
			}
		}
		return scoreMin * j.de;
	}

	private Long s2b(boolean b) {
		HashMap<Integer, Integer> joueurPose = new HashMap<>();
		if (b) {
			joueurPose.put(1, 4);
			joueurPose.put(2, 7);
		} else {
			joueurPose.put(1, 4);
			joueurPose.put(2, 8);
		}
		HashMap<Integer, Long> scores = new HashMap<>();
		scores.put(1, 0L);
		scores.put(2, 0L);
		Jeu2 j = new Jeu2(scores, joueurPose);
		int nbTour = 0;
		HashMap<Jeu2, HashMap<Jeu2, Long>> corres = new HashMap<>();
		HashMap<Jeu2, Long> res = new HashMap<>();
		HashMap<Jeu2, Long> newRes = new HashMap<>();
		HashMap<Jeu2, Long> unRes = new HashMap<>();
		Set<Jeu2> allGames = initAllGame(j);
		for (Jeu2 jeu : allGames) {
			corres.put(jeu, jeu.jouer());
		}
		Long v1 = 0L;
		Long v2 = 0L;

		Long w = v2;
		if (v1 > v2) {
			w = v1;
		}
		//System.out.println("nombre de victoire du grand gagant : " + w);
		//System.out.println(allGames.size());
		HashMap<Jeu2, Long> nbChemins = new HashMap<>();

		for (Jeu2 g : corres.keySet()) {

			//System.out.println(corres.get(g));
		}

		for (Jeu2 g : corres.keySet()) {
			Long nb = cheminsVers(g, corres, nbChemins);
			nbChemins.put(g, nb);
			if (nbChemins.size() % 10 == 0) {
			//	System.out.println(nbChemins.size());
			}
		}
		for (Jeu2 g : nbChemins.keySet()) {
			if (g.winner > 0) {
			//	System.out.println(g.winner + " " + nbChemins.get(g));
			}
		}
		return w;
	}

	private Long cheminsVers(Jeu2 j, HashMap<Jeu2, HashMap<Jeu2, Long>> corres, HashMap<Jeu2, Long> nbChemins) {
		Long res = 0L;
		if (j.winner < 1) {
			if (j.scores.get(1) == 0 && j.scores.get(2) == 0) {
				return 1L;
			}
		}
		if (nbChemins.containsKey(j)) {
			return nbChemins.get(j);
		}
		for (Jeu2 g : corres.keySet()) {
			for (Jeu2 gg : corres.get(g).keySet()) {
				if (gg.equals(j)) {
					try {
						if (nbChemins.isEmpty()) {
							res += corres.get(g).get(gg) * cheminsVers(g, corres);
						} else {
							res += corres.get(g).get(gg) * cheminsVers(g, corres, nbChemins);
						}
					} catch (NullPointerException e) {
						e.getMessage();
						e.getCause();
						e.printStackTrace();
					}
				}
			}
		}
		return res;
	}

	private Long cheminsVers(Jeu2 j, HashMap<Jeu2, HashMap<Jeu2, Long>> corres) {
		Long res = 0L;
		if (j.scores.get(1) == 0 && j.scores.get(2) == 0) {
			return 1L;
		}
		for (Jeu2 g : corres.keySet()) {
			for (Jeu2 gg : corres.get(g).keySet()) {
				if (gg.equals(j)) {
					res += corres.get(g).get(gg) * cheminsVers(g, corres);
				}
			}
		}
		return res;
	}

	private Set<Jeu2> initAllGame(Jeu2 j) {
		Set<Jeu2> res = new HashSet<>();
		Set<Jeu2> newRes = new HashSet<>();
		res.add(j);
		int nrs = -1;
		while (nrs < res.size()) {
			nrs = res.size();
			for (Jeu2 jeu : res) {
				newRes.addAll(jeu.SetRetourne());
			}
			res = new HashSet(newRes);
		}
		return res;
	}

	private Set<Jeu2> initAllGame2(Jeu2 j) {
		Set<Jeu2> res = new HashSet<>();
		for (Long s1 = 0L; s1 < nbVic; s1++) {
			for (Long s2 = 0L; s2 < nbVic; s2++) {
				for (int pos1 = 0; pos1 < 11; pos1++) {
					for (int pos2 = 0; pos2 < 11; pos2++) {
						for (int cur = 1; cur <= 2; cur++) {
							HashMap<Integer, Long> scores = new HashMap<>();
							scores.put(1, s1);
							scores.put(2, s2);
							HashMap<Integer, Integer> joueurPose = new HashMap<>();
							joueurPose.put(1, pos1);
							joueurPose.put(2, pos2);
							res.add(new Jeu2(scores, joueurPose, cur));
						}
					}
				}

			}
		}
		return res;
	}

	public static class Situation {
		Long s1;
		Long s2;
		Long cpt1;
		Long cpt2;
		int p1;
		int p2;

		public Situation(Long s1, Long s2, Long cpt1, Long cpt2,int p1,int p2) {
			super();
			this.s1 = s1;
			this.s2 = s2;
			this.cpt1 = cpt1;
			this.cpt2 = cpt2;
			this.p1=p1;
			this.p2=p2;
		}

		@Override
		public String toString() {
			return "Situation [s1=" + s1 + ", s2=" + s2 + ", cpt1=" + cpt1 + ", cpt2=" + cpt2 + "]";
		}

	}

	public static class Jeu1 {
		HashMap<Integer, Long> scores;
		Long de;
		Long nbLance;
		HashMap<Integer, Integer> joueurPose;
		int currentP;
		int nbtour;

		public HashMap<Integer, Long> getScores() {
			return scores;
		}

		public void jouer() {
			Long deplacement = de + 1 + de + 2 + de + 3;
			String newPose = String.valueOf(joueurPose.get(currentP) + deplacement);
			if (newPose.substring(newPose.length() - 1).equals("0")) {
				joueurPose.put(currentP, 10);
			} else {
				joueurPose.put(currentP, Integer.parseInt(newPose.substring(newPose.length() - 1)));
			}
			scores.put(currentP, scores.get(currentP) + joueurPose.get(currentP));
			de += 3;
			nbLance += 3;
			if (currentP == scores.keySet().size()) {
				currentP = 1;
			} else {
				currentP++;
			}

			nbtour++;

		}

		public boolean fini() {
			return scores.values().stream().anyMatch(p -> p >= 1000);
		}

		public void setScores(HashMap<Integer, Long> scores) {
			this.scores = scores;
		}

		public Long getDe() {
			return de;
		}

		public void setDe(Long de) {
			this.de = de;
		}

		public Long getNbLance() {
			return nbLance;
		}

		public void setNbLance(Long nbLance) {
			this.nbLance = nbLance;
		}

		public HashMap<Integer, Integer> getJoueurPose() {
			return joueurPose;
		}

		public void setJoueurPose(HashMap<Integer, Integer> joueurPose) {
			this.joueurPose = joueurPose;
		}

		public Jeu1(HashMap<Integer, Long> scores, HashMap<Integer, Integer> joueurPose, Long de, Long nbLance) {
			super();
			this.scores = scores;
			this.joueurPose = joueurPose;
			this.de = de;
			this.nbLance = nbLance;
			this.currentP = 1;
			this.nbtour = 0;
		}

	}

	public static class Jeu2 {
		HashMap<Integer, Long> scores;
		HashMap<Integer, Integer> joueurPose;
		int currentP;
		private int winner;
		public static final HashMap<Integer, Long> sumFreq = setSumFreq();

		public Map<Integer, Long> getScores() {
			return scores;
		}

		public Set<Jeu2> SetRetourne() {
			Set<Jeu2> res = new HashSet<>();
			res.add(this);
			if (winner == 0) {
				Jeu2 j0 = new Jeu2(this);
				for (Integer sum : sumFreq.keySet()) {
					Jeu2 j = new Jeu2(j0);
					String newPose = String.valueOf(j.joueurPose.get(j.currentP) + sum);
					if (newPose.substring(newPose.length() - 1).equals("0")) {
						j.joueurPose.put(j.currentP, 10);
					} else {
						j.joueurPose.put(j.currentP, Integer.parseInt(newPose.substring(newPose.length() - 1)));
					}
					j.scores.put(j.currentP, j.scores.get(j.currentP) + j.joueurPose.get(j.currentP));
					j.currentP = (j.currentP == 1) ? 2 : 1;
					if (j.fini()) {
						if (j.j1w()) {
							j.winner = 1;
						} else {
							j.winner = 2;
						}
						j.joueurPose = null;
						j.scores = null;
						j.currentP = 0;

					}
					res.add(j);
				}
			} else {
				res.add(this);
			}
			return res;
		}

		private static HashMap<Integer, Long> setSumFreq() {
			HashMap<Integer, Long> sumFreq = new HashMap<>();
			sumFreq.put(3, 1L);
			sumFreq.put(4, 3L);
			sumFreq.put(5, 6L);
			sumFreq.put(6, 7L);
			sumFreq.put(7, 6L);
			sumFreq.put(8, 3L);
			sumFreq.put(9, 1L);
			return sumFreq;
		}

		public HashMap<Jeu2, Long> jouer() {

			HashMap<Jeu2, Long> res = new HashMap<>();
			if (winner == 0) {
				Jeu2 j0 = new Jeu2(this);
				for (Integer sum : sumFreq.keySet()) {
					Jeu2 j = new Jeu2(j0);
					String newPose = String.valueOf(j.joueurPose.get(j.currentP) + sum);
					if (newPose.substring(newPose.length() - 1).equals("0")) {
						j.joueurPose.put(j.currentP, 10);
					} else {
						j.joueurPose.put(j.currentP, Integer.parseInt(newPose.substring(newPose.length() - 1)));
					}
					j.scores.put(j.currentP, j.scores.get(j.currentP) + j.joueurPose.get(j.currentP));
					j.currentP = (j.currentP == 1) ? 2 : 1;

					if (res.containsKey(j)) {
						res.put(j, res.get(j) + sumFreq.get(sum));
					} else {
						res.put(j, sumFreq.get(sum));
					}
				}
			} else {
				res.put(this, 1L);
			}
			return res;
		}

		public boolean fini() {
			if (scores.values().stream().anyMatch(p -> p >= nbVic)) {
				return true;
			}
			return false;
		}

		public boolean actif() {
			return !(winner == 0);
		}

		public void setScores(HashMap<Integer, Long> scores) {
			this.scores = scores;
		}

		public HashMap<Integer, Integer> getJoueurPose() {
			return joueurPose;
		}

		public void setJoueurPose(HashMap<Integer, Integer> joueurPose) {
			this.joueurPose = joueurPose;
		}

		public Jeu2(HashMap<Integer, Long> scores, HashMap<Integer, Integer> joueurPose) {
			super();
			this.scores = scores;
			this.joueurPose = joueurPose;
			this.currentP = 1;
		}

		public Jeu2(Jeu2 j) {
			this.scores = new HashMap(j.scores);
			this.currentP = j.currentP;
			this.joueurPose = new HashMap(j.joueurPose);
			this.winner = 0;
		}

		public Long tot() {
			return scores.get(1) + scores.get(2);
		}

		public Jeu2(HashMap<Integer, Long> scores, HashMap<Integer, Integer> joueurPose, int cur) {
			super();
			this.scores = scores;
			this.joueurPose = joueurPose;
			this.currentP = cur;
		}

		public boolean j1w() {
			if (scores.get(1) > scores.get(2)) {
				return true;
			}
			return false;
		}

		public boolean j2w() {
			return !j1w();
		}

		@Override
		public String toString() {
			return "Jeu2 [scores=" + scores + ", joueurPose=" + joueurPose + ", currentP=" + currentP + ", winner="
					+ winner + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + currentP;
			result = prime * result + ((joueurPose == null) ? 0 : joueurPose.hashCode());
			result = prime * result + ((scores == null) ? 0 : scores.hashCode());
			result = prime * result + winner;
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
			Jeu2 other = (Jeu2) obj;
			if (currentP != other.currentP)
				return false;
			if (joueurPose == null) {
				if (other.joueurPose != null)
					return false;
			} else if (!joueurPose.equals(other.joueurPose))
				return false;
			if (scores == null) {
				if (other.scores != null)
					return false;
			} else if (!scores.equals(other.scores))
				return false;
			if (winner != other.winner)
				return false;
			return true;
		}

	}

	public static List<Long> getDuration() {
		A2021Day21 d = new A2021Day21(21);
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

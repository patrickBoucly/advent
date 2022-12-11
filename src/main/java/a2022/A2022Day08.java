package a2022;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2022Day08 extends A2022 {

	public A2022Day08(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day08 d = new A2022Day08(8);
		// System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(false));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public Integer s2(boolean b) {
		Foret f = new Foret(getArbres(b));
		f.setScores();
		return MesOutils
				.getMaxIntegerFromList(f.arbres.stream().map(Arbre::getScorePanoramique).collect(Collectors.toList()));
	}

	public int s1(boolean b) {
		Foret f = new Foret(getArbres(b));
		int res = 0;
		for (Arbre a : f.arbres) {
			if (f.isVisible(a)) {
				res++;
			}
		}
		return res;
	}

	public Set<Arbre> getArbres(boolean c) {
		Set<Arbre> arbres = new HashSet<>();
		List<String> lignes = Arrays.asList(getInput(c).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		for (int i = 0; i < lignes.size(); i++) {
			for (int j = 0; j < lignes.size(); j++) {
				arbres.add(new Arbre(i, j, Integer.parseInt(lignes.get(j).substring(i, i + 1))));
			}
		}
		return arbres;
	}

	public static class Foret {
		public Set<Arbre> arbres;

		public Set<Arbre> getArbres() {
			return arbres;
		}

		public void setScores() {
			for (Arbre a : arbres) {
				setScore(a);
			}
		}

		private void setScore(Arbre a) {
			a.setScorePanoramique(scoreH(a) * scoreB(a) * scoreG(a) * scoreD(a));
		}

		private int scoreD(Arbre a) {
			int xmax = MesOutils.getMaxIntegerFromList(arbres.stream().map(Arbre::getX).collect(Collectors.toList()));
			int res = 0;
			for (int j = a.x+1; j <= xmax; j++) {
				if (a.hauteur > getArbre(j, a.y).get().hauteur) {
					res++;
				} else {
					res++;
					return res;
				}
			}
			return res;
		}

		private int scoreG(Arbre a) {
			int res = 0;
			for (int j = a.x-1; j>= 0; j--) {
				if (a.hauteur > getArbre(j, a.y).get().hauteur) {
					res++;
				} else {
					res++;
					return res;
				}
			}
			return res;
		}

		private int scoreB(Arbre a) {
			int ymax = MesOutils.getMaxIntegerFromList(arbres.stream().map(Arbre::getY).collect(Collectors.toList()));
			int res = 0;
			for (int j = a.y+1; j <= ymax; j++) {
				if (a.hauteur > getArbre(a.x, j).get().hauteur) {
					res++;
				} else {
					res++;
					return res;
				}
			}
			return res;
		}

		private int scoreH(Arbre a) {
			int res = 0;
			for (int j = a.y-1; j >= 0; j--) {
				if (a.hauteur > getArbre(a.x, j).get().hauteur) {
					res++;
				} else {
					res++;
					return res;
				}
			}
			return res;
		}

		public void setArbres(Set<Arbre> arbres) {
			this.arbres = arbres;
		}

		public Foret(Set<Arbre> arbres) {
			super();
			this.arbres = arbres;
		}

		public Optional<Arbre> getArbre(int x, int y) {
			Arbre p = null;
			for (Arbre i : arbres) {
				if (x == i.x && y == i.y) {
					p = new Arbre(x, y, i.hauteur);
				}
			}
			return Optional.ofNullable(p);
		}

		public boolean isVisible(Arbre a) {
			return isBord(a) || isVisibleHaut(a) || isVisibleBas(a) || isVisibleGauche(a) || isVisibleDroite(a);
		}

		private boolean isVisibleDroite(Arbre a) {
			return arbres.stream().filter(b -> a.x < b.x && b.y == a.y && a.hauteur <= b.hauteur).findAny().isEmpty();
		}

		private boolean isVisibleGauche(Arbre a) {
			return arbres.stream().filter(b -> a.x > b.x && b.y == a.y && a.hauteur <= b.hauteur).findAny().isEmpty();
		}

		private boolean isVisibleBas(Arbre a) {
			return arbres.stream().filter(b -> a.x == b.x && b.y > a.y && a.hauteur <= b.hauteur).findAny().isEmpty();
		}

		private boolean isVisibleHaut(Arbre a) {
			return arbres.stream().filter(b -> a.x == b.x && b.y < a.y && a.hauteur <= b.hauteur).findAny().isEmpty();
		}

		private boolean isBord(Arbre a) {
			int xmax = MesOutils.getMaxIntegerFromList(arbres.stream().map(Arbre::getX).collect(Collectors.toList()));
			int ymax = MesOutils.getMaxIntegerFromList(arbres.stream().map(Arbre::getY).collect(Collectors.toList()));
			return a.x == 0 || a.y == 0 || a.x == xmax || a.y == ymax;
		}

	}

	public static class Arbre {
		int x;
		int y;
		Integer hauteur;
		Integer scorePanoramique = 0;

		public Arbre(int x, int y, Integer hauteur) {
			super();
			this.x = x;
			this.y = y;
			this.hauteur = hauteur;
		}

		public Integer getScorePanoramique() {
			return scorePanoramique;
		}

		public void setScorePanoramique(Integer scorePanoramique) {
			this.scorePanoramique = scorePanoramique;
		}

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
			Arbre other = (Arbre) obj;
			return x == other.x && y == other.y;
		}

		

		@Override
		public String toString() {
			return "Arbre [x=" + x + ", y=" + y + ", hauteur=" + hauteur + ", scorePanoramique=" + scorePanoramique
					+ "]";
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public Integer getHauteur() {
			return hauteur;
		}

		public void setHauteur(Integer hauteur) {
			this.hauteur = hauteur;
		}

	}

	public static List<Long> getDuration() {
		A2022Day08 d = new A2022Day08(8);
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

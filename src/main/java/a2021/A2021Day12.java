package a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class A2021Day12 extends A2021 {

	public A2021Day12(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day12 d = new A2021Day12(12);
	//	System.out.println(d.s1(true));
	//	System.out.println(d.s2(true));
		System.out.println(d.getDuration());
		
	}

	public long s2(boolean b) {
		Caverne c = getCave(b);
		List<Chemin> chemins = getChemins(c);
		while (chemins.stream().filter(ch -> !ch.termine).findFirst().isPresent()) {
			chemins = c.poursuivre2(chemins);
		}
		
		return (chemins.stream().filter(ch -> ch.itineraire.contains(new Grotte("end"))).distinct().count());
	
	}

	public long s1(boolean b) {
		Caverne c = getCave(b);
		List<Chemin> chemins = getChemins(c);
		while (chemins.stream().filter(ch -> !ch.termine).findFirst().isPresent()) {
			chemins = c.poursuivre1(chemins);
		}
		return (chemins.stream().filter(ch -> ch.itineraire.contains(new Grotte("end"))).count());
	}

	private List<Chemin> getChemins(Caverne c) {
		List<Chemin> chemins = new ArrayList<>();
		for (Gallerie g : c.galleries) {
			if (g.g1.nom.equals("start")) {
				Chemin ch = new Chemin(false);
				ch.setItineraire(ch.addGrotte(new Grotte("start")));
				ch.setItineraire(ch.addGrotte(g.g2));
				chemins.add(ch);
			} else if (g.g2.nom.equals("start")) {
				Chemin ch = new Chemin(false);
				ch.setItineraire(ch.addGrotte(new Grotte("start")));
				ch.setItineraire(ch.addGrotte(g.g1));
				chemins.add(ch);
			}
		}
		return chemins;
	}

	private Caverne getCave(boolean b) {
		List<String> lignes = getLignes(b);

		Set<Gallerie> galleries = new HashSet<>();
		for (String l : lignes) {
			String[] sp = l.split("-");
			galleries.add(new Gallerie(new Grotte(sp[0]), new Grotte(sp[1])));
		}
		Caverne c = new Caverne(galleries);
		
		return c;
	}

	public static class Caverne {
		Set<Gallerie> galleries;
		Set<Grotte> grottes;

		public Set<Grotte> getGrottes() {
			return grottes;
		}

		public List<Chemin> poursuivre2(List<Chemin> chemins) {
			List<Chemin> retour = new ArrayList<>();
			for (int i = 0; i < chemins.size(); i++) {
				Chemin ceChemin = new Chemin(chemins.get(i).itineraire, chemins.get(i).termine);
				if (!ceChemin.termine) {

					List<Grotte> voisins = getVoisins(ceChemin.getLastVisited());
					int nbAjoutable = 0;
					for (Grotte v : voisins) {

						if (ajoutable2(ceChemin, v)) {
							nbAjoutable++;
							Chemin nc = new Chemin(new ArrayList<>(ceChemin.itineraire), v.nom.equals("end"));
							nc.setItineraire(nc.addGrotte(v));
							retour.add(nc);
						}
						if (nbAjoutable == 0) {
							retour.add(new Chemin(ceChemin.itineraire, true));
						}
					}

				} else {
					retour.add(ceChemin);
				}
			}
			return retour;
		}

		private boolean ajoutable2(Chemin ceChemin, Grotte v) {
			if (v.nom.equals("start")) {
				return false;
			} else if (v.isGrande()) {
				return true;
			} else {
				List<Grotte> cptLowDistinct= ceChemin.itineraire.stream().filter(g->!g.grande).distinct().collect(Collectors.toList());
				List<Grotte> cptLow= ceChemin.itineraire.stream().filter(g->!g.grande).collect(Collectors.toList());
		
				return cptLow.size()-cptLowDistinct.size()<=1;
			}

		}

		public List<Chemin> poursuivre1(List<Chemin> chemins) {
			List<Chemin> retour = new ArrayList<>();
			for (int i = 0; i < chemins.size(); i++) {
				Chemin ceChemin = new Chemin(chemins.get(i).itineraire, chemins.get(i).termine);
				if (!ceChemin.termine) {

					List<Grotte> voisins = getVoisins(ceChemin.getLastVisited());
					int nbAjoutable = 0;
					for (Grotte v : voisins) {

						if (ajoutable(ceChemin, v)) {
							nbAjoutable++;
							Chemin nc = new Chemin(new ArrayList<>(ceChemin.itineraire), v.nom.equals("end"));
							nc.setItineraire(nc.addGrotte(v));
							retour.add(nc);
						}
						if (nbAjoutable == 0) {
							retour.add(new Chemin(ceChemin.itineraire, true));
						}
					}

				} else {
					retour.add(ceChemin);
				}
			}
			return retour;
		}

		private boolean ajoutable(Chemin ch, Grotte v) {
			if (v.nom.equals("start")) {
				return false;
			} else if (v.isGrande()) {
				return true;
			} else {
				return !ch.itineraire.contains(v);
			}

		}

		private List<Grotte> getVoisins(Grotte uneGrotte) {
			List<Grotte> voisins = new ArrayList<>();
			for (Gallerie g : galleries) {
				if (g.g1.equals(uneGrotte)) {
					voisins.add(g.g2);
				} else if (g.g2.equals(uneGrotte)) {
					voisins.add(g.g1);
				}
			}
			return voisins;
		}

		public void setGrottes(Set<Grotte> grottes) {
			this.grottes = grottes;
		}

		public Caverne(Set<Gallerie> galleries) {
			super();
			this.galleries = galleries;
			Set<Grotte> gts = new HashSet<>();
			for (Gallerie g : galleries) {
				gts.add(g.g1);
				gts.add(g.g2);
			}
			setGrottes(gts);
		}

		public Set<Gallerie> getGalleries() {
			return galleries;
		}

		public void setGalleries(Set<Gallerie> galleries) {
			this.galleries = galleries;
		}

	}

	public static class Chemin {
		List<Grotte> itineraire;
		boolean termine;

		public Chemin(boolean b) {
			this.itineraire = new ArrayList<>();
			this.termine = b;
		}

		public Chemin(List<Grotte> itineraire, boolean b) {
			this.itineraire = itineraire;
			this.termine = b;
		}

		public Grotte getLastVisited() {
			return getItineraire().get(getItineraire().size() - 1);
		}

		public List<Grotte> getItineraire() {
			return itineraire;
		}

		public void setItineraire(List<Grotte> itineraire) {
			this.itineraire = itineraire;
		}

		public List<Grotte> addGrotte(Grotte g) {
			List<Grotte> newItineraire = new ArrayList<>(getItineraire());
			newItineraire.add(g);
			return newItineraire;
		}
		public Integer taille() {
			return itineraire.size();
		}
		public int compare(Chemin ch1, Chemin ch2) {
            return ch1.taille().compareTo(ch2.taille());
        }

		public boolean isTermine() {
			return termine;
		}

		public void setTermine(boolean termine) {
			this.termine = termine;
		}

		@Override
		public String toString() {
			return "Chemin [itineraire=" + itineraire + ", termine=" + termine + "]";
		}

	}

	public static class Gallerie {
		Grotte g1;
		Grotte g2;

		public Grotte getG1() {
			return g1;
		}

		public void setG1(Grotte g1) {
			this.g1 = g1;
		}

		public Grotte getG2() {
			return g2;
		}

		public void setG2(Grotte g2) {
			this.g2 = g2;
		}

		public Gallerie(Grotte g1, Grotte g2) {
			super();
			this.g1 = g1;
			this.g2 = g2;
		}

		public Grotte autreExremite(Grotte g) {
			if (g.equals(g1)) {
				return g2;
			} else {
				return g1;
			}
		}

	}

	public static class Grotte {
		String nom;
		boolean grande;

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public boolean isGrande() {
			return grande;
		}

		public void setGrande(boolean grande) {
			this.grande = grande;
		}

		public Grotte(String nom) {
			super();
			this.nom = nom;
			this.grande = nom.substring(0, 1).toUpperCase().equals(nom.substring(0, 1));
		}

		@Override
		public String toString() {
			return nom;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((nom == null) ? 0 : nom.hashCode());
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
			Grotte other = (Grotte) obj;
			if (nom == null) {
				if (other.nom != null)
					return false;
			} else if (!nom.equals(other.nom))
				return false;
			return true;
		}

	}

	private List<String> getLignes(boolean c) {
		List<String> lignes = Arrays.asList(getInput(c).split("\n")).stream().collect(Collectors.toList());
		return lignes;
	}

	public static List<Long> getDuration() {
		A2021Day12 d = new A2021Day12(12);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1,endTime - startTime);
	}
}

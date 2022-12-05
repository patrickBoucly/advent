package a2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class A2022Day3 extends A2022 {

	public A2022Day3(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day3 d = new A2022Day3(3);
		System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1=endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day "+ d.day+" run 1 took "+timeS1+" milliseconds, run 2 took " + (endTime - startTime) + " milliseconds");
		
	}

	public long s2(boolean b) {
		List<String> listeDesMotsBizzares=Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		List<Integer> listeDesPriorites=new ArrayList<>();
		for(int i=0;i<listeDesMotsBizzares.size();i=i+3) {
			String un=listeDesMotsBizzares.get(i);
			String deux=listeDesMotsBizzares.get(i+1);
			String trois=listeDesMotsBizzares.get(i+2);
			String lettreCommune=recupererLettreCommuneTrois(un,deux,trois);
			listeDesPriorites.add(recupererPriorite(lettreCommune));
		}
		return listeDesPriorites.stream().mapToLong(e -> e).sum();
	}
	
	
	private String recupererLettreCommuneTrois(String un, String deux, String trois) {
		String ordre="0abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for(int i=0;i<ordre.length();i++) {
			String lettre=ordre.substring(i, i+1);
			if(un.contains(lettre) && deux.contains(lettre) && trois.contains(lettre)) {
				return lettre;
			}
			
		}
		return null;
	}

	public long s1(boolean b) {
		List<String> listeDesMotsBizzares=Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		List<Integer> listeDesPriorites=new ArrayList<>();
		for(String unMotBizarre:listeDesMotsBizzares) {
			String partieDeGauche=recupererPartieDeGauche(unMotBizarre);
			String partieDeDroite=recupererPartieDeDroite(unMotBizarre);
			String lettreCommune=recupererLettreCommune(partieDeGauche,partieDeDroite);
			Integer priorite=recupererPriorite(lettreCommune);
			listeDesPriorites.add(priorite);
		}
		Long reponse=0L;
		for(Integer priorite:listeDesPriorites) {
			reponse=reponse+priorite;
		}
		return reponse;
	}
	private Integer recupererPriorite(String lettreCommune) {
		String ordre="0abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for(int i=0;i<ordre.length();i++) {
			if(ordre.substring(i, i+1).equals(lettreCommune)) {
				return i;
			}
			
		}
		return null;
	}

	private String recupererLettreCommune(String partieDeGauche, String partieDeDroite) {
		for(int i=0;i<partieDeGauche.length();i++) {
			String lettreARegarderDansLeMotDeGauche=partieDeGauche.substring(i, i+1);
			if(partieDeDroite.contains(lettreARegarderDansLeMotDeGauche)) {
				return lettreARegarderDansLeMotDeGauche;
			}
			
		}
		return null;
	}

	private String recupererPartieDeDroite(String unMotBizarre) {
		Integer taille=unMotBizarre.length();
		return unMotBizarre.substring(taille/2);
	}

	private String recupererPartieDeGauche(String unMotBizarre) {
		Integer taille=unMotBizarre.length();
		return unMotBizarre.substring(0, taille/2);
	}

	public static List<Long> getDuration() {
		A2022Day3 d = new A2022Day3(3);
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

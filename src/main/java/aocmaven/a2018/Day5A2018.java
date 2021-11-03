package aocmaven.a2018;

import static org.apache.commons.io.FileUtils.readFileToString;
import java.util.stream.*;

import lombok.Data;
import lombok.Value;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class Day5A2018 {

	public static void main(String[] args) {
		//s1();
		s2();

	}

	private static void s2() {
		String message = getMessage(
				"C:\\Users\\z11r88\\eclipse-workspace2\\advent_of_code\\src\\advent_of_code\\main\\resources\\a2018\\input5");
		message+="$$";
		String messageSansUneLettre="";
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		HashMap<Character,Integer> longeurParLettreSup = new HashMap<>();
		int taille=0;
		int cpt=0;
		for(char letter:alphabet) {
			messageSansUneLettre=message;
			messageSansUneLettre=messageSansUneLettre.replace(String.valueOf(letter),"");
			messageSansUneLettre=messageSansUneLettre.replace(String.valueOf(Character.toUpperCase(letter)),"");
			taille=nettoyage(messageSansUneLettre);
			longeurParLettreSup.put(letter, taille);
			cpt++;
			System.out.println(cpt);
		}
		int minPolymere=trouverLongueurMin(longeurParLettreSup);
		System.out.println(minPolymere);
	}

	private static int trouverLongueurMin(HashMap<Character, Integer> longeurParLettreSup) {
		int min=1000000;
		for(Character c:longeurParLettreSup.keySet()) {
			if(longeurParLettreSup.get(c) < min) {
				min=longeurParLettreSup.get(c);
			}
		}
		return min;
	}

	private static Integer nettoyage(String message) {
		boolean fini = false;
		HashMap<Integer, String> chaineAnettoyer = new HashMap<>();
		HashMap<Integer, String> chaineNettoyee = new HashMap<>();
		chaineAnettoyer.put(-1, message);
		while (!fini) {
			chaineNettoyee = nettoyer(chaineAnettoyer);
			if (chaineNettoyee.keySet().contains(-2)) {
				fini = true;
			} else {
				chaineAnettoyer = chaineNettoyee;
			}
		}
		return chaineNettoyee.get(-2).length();
	}

	private static void s1() {
		String message = getMessage(
				"C:\\Users\\z11r88\\eclipse-workspace2\\advent_of_code\\src\\advent_of_code\\main\\resources\\a2018\\input5");
		message+="$$";
		boolean fini = false;
		HashMap<Integer, String> chaineAnettoyer = new HashMap<>();
		HashMap<Integer, String> chaineNettoyee = new HashMap<>();
		chaineAnettoyer.put(-1, message);
		int cpt = 0;
		int dernierNbNettoyage = -1;
		
		while (!fini) {
			chaineNettoyee = nettoyer(chaineAnettoyer);
			if (chaineNettoyee.keySet().contains(-2)) {
				fini = true;
			} else {
				chaineAnettoyer = chaineNettoyee;
			}
			cpt++;
		}
		System.out.println(chaineNettoyee.get(-2).length());
	}

	private static HashMap<Integer, String> nettoyer(HashMap<Integer, String> chaineAnettoyer) {
		int posAsupp = -2;
		char nextLetter;
		char letter = 0;
		HashMap<Integer, String> res = new HashMap<>();
		int dernierNbNettoyage = -1;
		for (Integer i : chaineAnettoyer.keySet()) {
			dernierNbNettoyage = i;
		}
		String chaine = chaineAnettoyer.get(dernierNbNettoyage);
		int pos = 0;
		boolean fini = false;
		while (pos < chaine.length() && !fini) {
			letter = chaine.charAt(pos);
			if (pos == chaine.length() - 1) {
				fini = true;
			} else {
				nextLetter = chaine.charAt(pos + 1);
				if (((Character.toUpperCase(letter) == letter) && (Character.toLowerCase(letter) == nextLetter))
						|| ((Character.toLowerCase(letter) == letter)
								&& (Character.toUpperCase(letter) == nextLetter))) {
					fini = true;
					posAsupp = pos;
				} else {
					pos++;
				}
			}
		}
		String newChaine="";
		if (posAsupp >= 0) {
			if (posAsupp == 0) {
				newChaine=chaine.substring(2);
				res.put(posAsupp,newChaine );
			} else if (posAsupp <= chaine.length() - 1) {
				newChaine=chaine.substring(0, posAsupp) + chaine.substring(posAsupp + 2);	
				res.put(posAsupp,newChaine );		
			}
		} else {
			res.put(-2, chaine);
		}
		return res;
	}

	private static String getMessage(String monFic) {
		String line = "";
		try {
			line = readFileToString(new File(monFic));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return line;
	}

}

package outils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class MesOutils {
	public static Integer getMaxIntegerFromList(List<Integer> listOfIntegers) {
		return listOfIntegers.stream().mapToInt(v -> v).max().getAsInt();
	}

	public static Integer getMinIntegerFromList(List<Integer> listOfIntegers) {
		return listOfIntegers.stream().mapToInt(v -> v).min().getAsInt();
	}

	public static long getMinLongFromList(List<Long> listOfLongs) {
		return listOfLongs.stream().mapToLong(v -> v).min().getAsLong();
	}

	public static long getMaxLongFromList(List<Long> listOfLongs) {
		return listOfLongs.stream().mapToLong(v -> v).max().getAsLong();
	}

	public static long pgcd(long a, long b) {
		while (b != 0) {
			long temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}

	// Fonction pour calculer le PPCM (Plus Petit Commun Multiple)
	public static long ppcm(long a, long b) {
		return a * (b / pgcd(a, b));
	}

	public static long pgcdListe(List<Long> liste) {
		if (liste == null || liste.isEmpty()) {
			throw new IllegalArgumentException("La liste ne peut pas être nulle ou vide.");
		}

		long resultat = liste.get(0);
		for (int i = 1; i < liste.size(); i++) {
			resultat = pgcd(resultat, liste.get(i));
		}
		return resultat;
	}

	// Fonction pour calculer le PPCM d'une liste de nombres
	public static long ppcmListe(List<Long> liste) {
		if (liste == null || liste.isEmpty()) {
			throw new IllegalArgumentException("La liste ne peut pas être nulle ou vide.");
		}

		long resultat = liste.get(0);
		for (int i = 1; i < liste.size(); i++) {
			resultat = ppcm(resultat, liste.get(i));
		}
		return resultat;
	}
	
	public static <T> List<List<T>> generateCombinations(List<T> list) {
        List<List<T>> result = new ArrayList<>();
        int n = list.size();

        // Générer toutes les combinaisons en utilisant des masques binaires
        for (int i = 0; i < (1 << n); i++) {
            List<T> combination = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    combination.add(list.get(j));
                }
            }
            result.add(combination);
        }
        return result;
    }
	public static <T> List<List<T>> cartesianProduct(List<T> choices, int length) {
	    List<List<T>> result = new ArrayList<>();
	    generateCartesianProduct(new ArrayList<>(), choices, length, result);
	    return result;
	}

	private static <T> void generateCartesianProduct(List<T> current, List<T> choices, int remaining, List<List<T>> result) {
	    if (remaining == 0) {
	        result.add(new ArrayList<>(current)); // Ajouter une copie de la combinaison actuelle
	        return;
	    }

	    for (T choice : choices) {
	        current.add(choice); // Ajouter un élément à la combinaison actuelle
	        generateCartesianProduct(current, choices, remaining - 1, result); // Générer pour les positions restantes
	        current.remove(current.size() - 1); // Retirer l'élément pour revenir à l'état précédent
	    }
	}
	public static <T> void generatePermutations(List<T> list, Consumer<List<T>> consumer, AtomicBoolean stopSignal) {
        permute(new ArrayList<>(), list, new boolean[list.size()], consumer, stopSignal);
    }

    private static <T> void permute(List<T> current, List<T> list, boolean[] used, Consumer<List<T>> consumer, AtomicBoolean stopSignal) {
        if (stopSignal.get()) return; // Arrêter si le signal est actif

        if (current.size() == list.size()) {
            consumer.accept(new ArrayList<>(current)); // Traiter la permutation
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            if (used[i] || stopSignal.get()) continue; // Vérifier le signal avant de continuer

            used[i] = true;
            current.add(list.get(i));
            permute(current, list, used, consumer, stopSignal);
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
	/*
	 * public static long getMinOccuredLetter(String line) { InputStream
	 * targetStream = new ByteArrayInputStream(line.getBytes()); return
	 * targetStream. mapToLong(v ->
	 * v).min().orElseThrow(NoSuchElementException::new); } public static long
	 * getMaxOccuredLetter(String line) { return listOfLongs.stream().mapToLong(v ->
	 * v).min().orElseThrow(NoSuchElementException::new); }
	 * 
	 * 
	 * 
	 * public static Stream GenerateStreamFromString(string s) { var stream = new
	 * MemoryStream(); var writer = new StreamWriter(stream); writer.Write(s);
	 * writer.Flush(); stream.Position = 0; return stream; }
	 */
}

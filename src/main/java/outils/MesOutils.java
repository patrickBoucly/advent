package outils;

import java.util.List;

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

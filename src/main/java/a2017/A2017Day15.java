package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A2017Day15 extends A2017 {

	public A2017Day15(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day15 d = new A2017Day15(1);
		// d.s1(true);
		long startTime = System.currentTimeMillis();
		//System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	/*
	 * Les g�n�rateurs fonctionnent tous deux sur le m�me principe. Pour cr�er sa
	 * prochaine valeur, un g�n�rateur prend la valeur pr�c�dente qu'il a produite,
	 * la multiplie par un facteur (le g�n�rateur A utilise 16807 ; le g�n�rateur B
	 * utilise 48271), puis conserve le reste de la division du produit obtenu par
	 * 2147483647. Ce reste final est la valeur qu'il produit ensuite.
	 * 
	 * Pour calculer la premi�re valeur de chaque g�n�rateur, il utilise une valeur
	 * de d�part sp�cifique comme "valeur pr�c�dente" (comme indiqu� dans votre
	 * entr�e de puzzle).
	 * 
	 * Par exemple, supposons que pour les valeurs de d�part, le g�n�rateur A
	 * utilise 65, tandis que le g�n�rateur B utilise 8921. Les cinq premi�res
	 * paires de valeurs g�n�r�es sont alors les suivantes
	 */
	public int s1(boolean b) {
		List<Long> la = new ArrayList<>();
		la.add(65L);
		List<Long> lb = new ArrayList<>();
		lb.add(8921L);
		int pos = 0;
		Long lastA = 783L;
		Long lastB = 325L;
		int tot = 0;
		while (pos < 40000000) {
			lastA = (lastA * 16807) % 2147483647;
			lastB = (lastB * 48271) % 2147483647;
			if (get16LAstBin(lastA).equals(get16LAstBin(lastB))) {
				tot++;
			}
			pos++;
		}

		return tot;
	}

	private Object get16LAstBin(Long l) {
		String s = "0000000000000000000000" + Long.toBinaryString(l);
		return s.substring(s.length() - 16);
	}

	public int s2(boolean b) {
		List<Long> la = new ArrayList<>();
		List<Long> lb = new ArrayList<>();
		
		Long lastA = 783L;
		Long lastB = 325L;
		int tot = 0;
		while (la.size() <= 5000000) {
			lastA = (lastA * 16807) % 2147483647;
			if (lastA % 4 == 0) {
				//System.out.println(lastA);
				la.add(lastA);
			}
		}
		while (lb.size() <= 5000000) {
			lastB = (lastB * 48271) % 2147483647;
			if (lastB % 8 == 0) {
				lb.add(lastB);
			}
		}
		for (int i = 0; i < la.size(); i++) {
			if (get16LAstBin(la.get(i)).equals(get16LAstBin(lb.get(i)))) {
				tot++;
			}
		}
		return tot;
	}

	public static List<Long> getDuration() {
		A2017Day15 d = new A2017Day15(1);
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

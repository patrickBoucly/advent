package aocmaven.a2021;

import java.util.Arrays;
import java.util.List;

public class A2021Day18 extends A2021 {
	public static final List<String> nb = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

	public A2021Day18(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day18 d = new A2021Day18(18);
		long startTime = System.currentTimeMillis();
		 System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		//System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}
	/*
	 * 
	 * [[[[[9,8],1],2],3],4] becomes [[[[0,9],2],3],4] (the 9 has no regular number
	 * to its left, so it is not added to any regular number). 
	 * [7,[6,[5,[4,[3,2]]]]] becomes [7,[6,[5,[7,0]]]] (the 2 has no regular number to its right, and so
	 * it is not added to any regular number). 
	 * [[6,[5,[4,[3,2]]]],1] becomes [[6,[5,[7,0]]],3].
	 * 
	 * [[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]] becomes
	 * [[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]] (the pair [3,2] is unaffected because the
	 * pair [7,3] is further to the left; [3,2] would explode on the next action).
	 * [[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]] becomes [[3,[2,[8,0]]],[9,[5,[7,0]]]].
	 * 
	 */

	private int s1(boolean b) {
		
		String s1 = "[[[[[9,8],1],2],3],4]";
		String s1r = explose(s1, 4);
		System.out.println(s1r);
		
		String s2 = "[7,[6,[5,[4,[3,2]]]]]";
		String s2r = explose(s2, 13);
		System.out.println(s2r);
		String s3="[[6,[5,[4,[3,2]]]],1]";
		String s3r = explose(s3, 11);
		System.out.println(s3r);
		
		String s4="[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]";
		String s4r = explose(s4, 25);
		System.out.println(s4r);
		return 0;
	}

	private String explose(String s1, int i) {
		String res = "";
		int posfin = -1;
		int posvirg = -1;
		boolean trouve = false;
		for (int pos = i; pos < s1.length(); pos++) {
			if (!trouve && s1.substring(pos, pos + 1).equals(",")) {
				posvirg = pos;
			}
			if (s1.substring(pos, pos + 1).equals("]") && !trouve) {
				posfin = pos;
				trouve = true;
			}
		}
		Reponse ag = chercheAG(s1, i);
		Reponse ad = chercheAD(s1, posfin);
		String newRegularG="";
		String newRegularD="";
		String gauche=s1.substring(0, i);
		String droite=s1.substring(posfin+1);
		if (ag.resp) {
			String regular = s1.substring(ag.deb, ag.fin + 1);
			String nbGauche = s1.substring(i, posvirg);
			newRegularG = String.valueOf(Integer.parseInt(regular) + Integer.parseInt(nbGauche));
			res=gauche.substring(0,ag.deb)+newRegularG+gauche.substring(ag.fin+1,gauche.length()-1)+"0";
		} else {
			res=gauche+"0";
		}
		if (ad.resp) {
			String regular = s1.substring(ad.deb, ad.fin + 1);
			String nbDroite = s1.substring(posvirg + 1, posfin);
			newRegularD = String.valueOf(Integer.parseInt(regular) + Integer.parseInt(nbDroite));
			res+=droite.substring(0,ad.deb-posfin-1)+newRegularD+droite.substring(ad.fin-posfin);
		}else {
			res+=droite;
		}
		return res;
	}

	private Reponse chercheAD(String s1, int i) {
		int deb = -1;
		int fin = -1;
		for (int j = i + 1; j < s1.length(); j++) {
			if (nb.contains(s1.substring(j, j + 1)) && deb == -1) {
				fin = j;
				deb = j;
			} else if (!nb.contains(s1.substring(j, j + 1)) && deb != -1) {
				fin = j-1;
				return new Reponse(deb != -1, deb, fin);
			}
		}
		return new Reponse(deb != -1, deb, fin);
	}

	private Reponse chercheAG(String s1, int i) {
		int deb = -1;
		int fin = -1;
		for (int j = i - 1; j >= 0; j--) {
			if (nb.contains(s1.substring(j, j + 1)) && fin == -1) {
				fin = j;
				deb = j;
			} else if (!nb.contains(s1.substring(j, j + 1)) && fin != -1) {
				deb = j+1;
				return new Reponse(fin != -1, deb, fin);
			}
		}
		return new Reponse(fin != -1, deb, fin);
	}

	private int s2(boolean b) {
		return 0;
	}

	public static class Reponse {
		boolean resp;
		int deb;
		int fin;

		public Reponse(boolean resp, int deb, int fin) {
			super();
			this.resp = resp;
			this.deb = deb;
			this.fin = fin;
		}

	}

	public static List<Long> getDuration() {
		A2021Day18 d = new A2021Day18(18);
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

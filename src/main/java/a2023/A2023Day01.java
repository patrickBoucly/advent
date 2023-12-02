package a2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A2023Day01 extends A2023 {

	public A2023Day01(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day01 d = new A2023Day01(1);
		System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");				
	}

	public int s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().toList();
		return inputL.stream().map(this::getResFromLigne).reduce(0, Integer::sum);
	}

	public int s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().toList();
		return inputL.stream().map(this::getResFromLigne2).reduce(0, Integer::sum);

	}
	
	public int getResFromLigne2(String ligne) {
		List<String> chiffres = List.of("1","2","3","4","5","6","7","8","9",
				"one","two","three","four","five","six","seven","eight","nine");
		List<Integer> firstOcc=new ArrayList<>();
		List<Integer> lastOcc=new ArrayList<>();
		for(String c:chiffres) {
			firstOcc.add(ligne.indexOf(c));
			lastOcc.add(ligne.lastIndexOf(c));
		}
		int pos1erApparu=10000000;
		int pos1erApparruDansFirstOcc=10000000;
		for(int j=0;j<chiffres.size();j++) {
			if(firstOcc.get(j) != -1) {
				if(firstOcc.get(j)<pos1erApparu) {
					pos1erApparu=firstOcc.get(j);
					pos1erApparruDansFirstOcc=j;
				}
			}
		}
		int posDerApparu=-10000000;
		int poDerApparruDansLastOcc=10000000;
		for(int j=0;j<chiffres.size();j++) {
			if(lastOcc.get(j) != -1) {
				if(lastOcc.get(j)>posDerApparu) {
					posDerApparu=lastOcc.get(j);
					poDerApparruDansLastOcc=j;
				}
			}
		}
		String chiffre1=chiffres.get(pos1erApparruDansFirstOcc);
		String chiffre2=chiffres.get(poDerApparruDansLastOcc);
		return calculFrom2Chiffres(chiffre1,chiffre2);
	}

	private int calculFrom2Chiffres(String chiffre1, String chiffre2) {
		List<String> chiffres = List.of("0","1","2","3","4","5","6","7","8","9");
		int res=0;
		if(chiffres.contains(chiffre1)) {
			res+=10*Integer.valueOf(chiffre1);
		}else {
			res+=10*convert(chiffre1);
		}
		if(chiffres.contains(chiffre2)) {
			res+=Integer.valueOf(chiffre2);
		}else {
			res+=convert(chiffre2);
		}
		
		return res;
	}

	private int convert(String chiffre1) {
		if(chiffre1.equals("one")) {
			return 1;
		}
		if(chiffre1.equals("two")) {
			return 2;
		}
		if(chiffre1.equals("three")) {
			return 3;
		}
		if(chiffre1.equals("four")) {
			return 4;
		}
		if(chiffre1.equals("five")) {
			return 5;
		}
		if(chiffre1.equals("six")) {
			return 6;
		}
		if(chiffre1.equals("seven")) {
			return 7;
		}
		if(chiffre1.equals("eight")) {
			return 8;
		}
		if(chiffre1.equals("nine")) {
			return 9;
		}
		return 0;
	}

	public int getResFromLigne(String ligne) {
		List<String> chiffres = List.of("0","1","2","3","4","5","6","7","8","9");
		int cpt=0;
		int seul=-1;
		for(int i=0;i<ligne.length();i++) {
			String lettre=ligne.substring(i, i+1);
			if(chiffres.contains(lettre)){
				seul= Integer.valueOf(lettre);
				cpt++;
			}
		}
		if(cpt==0) {
			return 0;
		}
		if(cpt==1) {
			return Integer.valueOf(seul)*11;
		}
		String deb="";
		String fin="";
		for(int i=0;i<ligne.length();i++) {
			if(chiffres.contains(ligne.substring(i, i+1))){
				deb= ligne.substring(i, i+1);
				break;
			}
		}
		for(int i=ligne.length()-1;i>-1;i--) {
			if(chiffres.contains(ligne.substring(i, i+1))){
				fin= ligne.substring(i, i+1);
				break;
			}
		}
		
		return Integer.valueOf(""+deb+fin);
	}
	public static List<Long> getDuration() {
		A2023Day01 d = new A2023Day01(1);
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

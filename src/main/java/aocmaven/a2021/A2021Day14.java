package aocmaven.a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import aocmaven.a2018.Day16A2018.Instruction;
import outils.MesOutils;

public class A2021Day14 extends A2021 {

	public A2021Day14(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day14 d = new A2021Day14(14);
		// d.s1(true);
		long startTime = System.currentTimeMillis();
		// d.s1(false);
		// System.out.println( d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();

		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	private long s2(boolean b) {
		return s(b,41);
	}
	private long s1(boolean b) {
		return s(b,11);
	}

	private long s(boolean b,int nbstep) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Insertion> inserts = new ArrayList<>();
		for (int i = 2; i < lignes.size(); i++) {
			String[] sp = lignes.get(i).split("->");
			inserts.add(new Insertion(sp[0].trim(), sp[1].trim()));
		}
		HashMap<String, Long> initialize = initialize(lignes.get(0));
		HashMap<String, Long> sequences = getSequences(initialize, inserts);
		for (int step = 2; step < nbstep; step++) {
			sequences = getSequences(sequences, inserts);
		}
		HashMap<String, Long> cptChar = getCount(sequences);
		String lastLetter=lignes.get(0).substring(lignes.get(0).length()-1);
		cptChar.put(lastLetter,cptChar.get(lastLetter)+1);
		List<Long> occuredNb = cptChar.values().stream().collect(Collectors.toList());
		return MesOutils.getMaxLongFromList(occuredNb) - MesOutils.getMinLongFromList(occuredNb);

	}

	private HashMap<String, Long> initialize(String polymere) {
		HashMap<String, Long> newSeq = new HashMap<>();
		for(int pos=0;pos<polymere.length()-1;pos++) {
			String chaine=polymere.substring(pos, pos+2);
			if (newSeq.containsKey(chaine)) {
				newSeq.put(chaine, newSeq.get(chaine)+1L);
			} else {
				newSeq.put(chaine, 1L);
			}
		}
		return newSeq;
	}

	private HashMap<String, Long> getSequences(HashMap<String, Long> oldSeq, List<Insertion> inserts) {
		HashMap<String, Long> newSeq = new HashMap<>();
		for (String s : oldSeq.keySet()) {
			Insertion ins = Insertion.getInsert(s, inserts);
			List<String> newChaine = getNewChaine(ins);
			for (String chaine : newChaine) {
				if (newSeq.containsKey(chaine)) {
					newSeq.put(chaine, newSeq.get(chaine) + oldSeq.get(s));
				} else {
					newSeq.put(chaine, oldSeq.get(s));
				}
			}
		}
		return newSeq;
	}

	private HashMap<String, Long> getCount(HashMap<String, Long> sequences) {
		HashMap<String, Long> cptChar = new HashMap<>();
		for (String s : sequences.keySet()) {
			if (cptChar.containsKey(s.substring(0, 1))) {
				cptChar.put(s.substring(0, 1), cptChar.get(s.substring(0, 1)) + sequences.get(s));
			} else {
				cptChar.put(s.substring(0, 1), sequences.get(s));
			}
		}
		return cptChar;
	}

	private List<String> getNewChaine(Insertion insertion) {
		return Arrays.asList(insertion.template.substring(0, 1) + insertion.letterToInsert,
				insertion.letterToInsert + insertion.template.substring(1, 2));
	}



	public static class Insertion {
		String template;
		String letterToInsert;

		public String getTemplate() {
			return template;
		}

		public static Insertion getInsert(String match, List<Insertion> inserts) {
			Insertion res = null;
			for (Insertion ins : inserts) {
				if (ins.template.equals(match)) {
					return ins;
				}
			}
			return res;
		}

		public void setTemplate(String template) {
			this.template = template;
		}

		public String getLetterToInsert() {
			return letterToInsert;
		}

		public void setLetterToInsert(String letterToInsert) {
			this.letterToInsert = letterToInsert;
		}

		public Insertion(String template, String letterToInsert) {
			super();
			this.template = template;
			this.letterToInsert = letterToInsert;
		}

		public String getChainePostInsert() {
			return letterToInsert + template.substring(1, 2);
		}

		@Override
		public String toString() {
			return "Insertion [template=" + template + ", letterToInsert=" + letterToInsert + "]";
		}

	}

	public static List<Long> getDuration() {
		A2021Day14 d = new A2021Day14(14);
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

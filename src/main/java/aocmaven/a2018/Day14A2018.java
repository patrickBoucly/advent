package aocmaven.a2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Day14A2018 {

	public static void main(String[] args0) {
		//s1();
		 s2();
	}

	private static void s2() {
		String input = read();
		for (String s : input.split("\n")) {
			List<Integer> notesRecettes = new ArrayList<>();
			notesRecettes.add(3);
			notesRecettes.add(7);
			boolean fini=false;
			Jeux j = new Jeux(notesRecettes, 0, 1);
			while (!fini) {
				j.cuisiner();
				//System.out.println(j);
				fini=patternTrouve(j,s.trim());
			}
			System.out.println(j);
			System.out.println(j.notesRecettes.size()-s.length()+1);
		}
	}

	private static boolean patternTrouve(Jeux j, String s) {
		String finNotesRecettesString=enString(j.notesRecettes,s.length()+2);
		return finNotesRecettesString.contains(s);
	}

	private static String enString(List<Integer> notesRecettes, int j) {
		String res="";
		int debut=0;
		if(notesRecettes.size()>j-1) {
		debut=notesRecettes.size()-j;
		} 
		for(int k=debut;k<notesRecettes.size();k++) {
			res+=String.valueOf(notesRecettes.get(k));
		}
		return res;
	}

	private static void s1() {
		String input = read();
		for (String s : input.split("\n")) {
			List<Integer> notesRecettes = new ArrayList<>();
			notesRecettes.add(3);
			notesRecettes.add(7);
			Jeux j = new Jeux(notesRecettes, 0, 1);
			while (j.getNotesRecettes().size() <= Integer.parseInt(s.trim())+10) {
				j.cuisiner();
				//System.out.println(j);
			}
			System.out.println(j.notesRecettes.subList(Integer.parseInt(s.trim()), Integer.parseInt(s.trim())+10));
		}
	}

	public static class Jeux {
		List<Integer> notesRecettes;
		int posRelf1;
		int posRelf2;

		public Jeux(List<Integer> notesRecettes, int posRelf1, int posRelf2) {
			super();
			this.notesRecettes = notesRecettes;
			this.posRelf1 = posRelf1;
			this.posRelf2 = posRelf2;
		}

		@Override
		public String toString() {
			int min = posRelf2;
			int max = posRelf1;
			if (posRelf1 <= posRelf2) {
				min = posRelf1;
				max = posRelf2;
			}
			String res = "";
			int pos = 0;
			while (pos < notesRecettes.size()) {
				if (min == pos) {
					res += " (" + notesRecettes.get(pos) + ") ";
				} else if (max == pos) {
					res += " [" + notesRecettes.get(pos) + "] ";
				} else {
					res += " " + notesRecettes.get(pos) + " ";
				}
				pos++;
			}
			return res;
		}

		public void cuisiner() {
			int nouvelleRecette = notesRecettes.get(posRelf1) + notesRecettes.get(posRelf2);
			if (nouvelleRecette > 9) {
				notesRecettes.add(1);
				notesRecettes.add(nouvelleRecette - 10);
			} else {
				notesRecettes.add(nouvelleRecette);
			}
			avancer();

		}

		private void avancer() {
			if (posRelf1 + 1 + notesRecettes.get(posRelf1) < notesRecettes.size()) {
				posRelf1 = posRelf1 + 1 + notesRecettes.get(posRelf1);
			} else {
				int k = 1;
				while (posRelf1 + 1 + notesRecettes.get(posRelf1) - k * notesRecettes.size() > notesRecettes.size()) {
					k++;
				}
				posRelf1 = posRelf1 + 1 + notesRecettes.get(posRelf1) - k * notesRecettes.size();
			}

			if (posRelf2 + 1 + notesRecettes.get(posRelf2) < notesRecettes.size()) {
				posRelf2 = posRelf2 + 1 + notesRecettes.get(posRelf2);
			} else {
				int k = 1;
				while (posRelf2 + 1 + notesRecettes.get(posRelf2) - k * notesRecettes.size() > notesRecettes.size()) {
					k++;
				}
				posRelf2 = posRelf2 + 1 + notesRecettes.get(posRelf2) - k * notesRecettes.size();
			}

		}

		public List<Integer> getNotesRecettes() {
			return notesRecettes;
		}

		public void setNotesRecettes(List<Integer> notesRecettes) {
			this.notesRecettes = notesRecettes;
		}

		public int getPosRelf1() {
			return posRelf1;
		}

		public void setPosRelf1(int posRelf1) {
			this.posRelf1 = posRelf1;
		}

		public int getPosRelf2() {
			return posRelf2;
		}

		public void setPosRelf2(int posRelf2) {
			this.posRelf2 = posRelf2;
		}

	}

	private static String read() {
		Path path = Paths.get(
				"C:\\git_repositories\\advent\\src\\main\\resources\\src\\advent_of_code\\main\\resources\\a2018\\input14_2");
		String content = "";
		try {
			content = Files.readString(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;

	}
}

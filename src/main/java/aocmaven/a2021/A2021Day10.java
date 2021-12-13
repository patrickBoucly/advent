package aocmaven.a2021;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class A2021Day10 extends A2021 {

	public A2021Day10(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day10 d = new A2021Day10(10);
		System.out.println(d.s1(true));
		System.out.println(d.s2(true));
		}

	public Long s2(boolean c) {
		List<String> lignes = getLignes(c);
		List<String> incomplete = lignes.stream().filter(l -> this.getValue(l) == 0).collect(Collectors.toList());
		List<String> clean = incomplete.stream().map(this::clean).collect(Collectors.toList());
		List<String> complementaire = clean.stream().map(this::complementaire).collect(Collectors.toList());
		List<Long> points = complementaire.stream().map(this::getScore).collect(Collectors.toList());

		List<Long> pointsTries = points.stream().sorted().collect(Collectors.toList());
		Long median = pointsTries.stream().skip(points.size() / 2).findFirst().get();
		return (median);

	}

	public Long getScore(String s) {
		long res = 0;

		for (int i = 0; i < s.length(); i++) {
			int scoreDeLElement=scoreElement(s.substring(i, i + 1));
			res = res * 5 + scoreDeLElement;
		}
		return res;
	}

	private int scoreElement(String s) {
		int res = 0;
		if (s.equals(")")) {
			return 1;
		} else if (s.equals("]")) {
			return 2;
		} else if (s.equals("}")) {
			return 3;
		}
		return 4;
	}

	public String clean(String s) {
		boolean continuer = true;
		String avtClean = s;
		String apresClean = "";
		while (continuer) {
			apresClean = getCleanest(avtClean);
			if (avtClean.equals(apresClean)) {
				continuer = false;
			}
			avtClean=apresClean;
		}
		return apresClean;
	}

	private String getCleanest(String cleanest) {
		int remove = -1;
		List<String> lus = new ArrayList<>();
		for (int pos = 0; pos < cleanest.length(); pos++) {
			lus.add(cleanest.substring(pos, pos + 1));
		}
		for (int i = 0; i < cleanest.length() - 1; i++) {
			if (remove < 0) {
				Symbol s1 = new Symbol(cleanest.substring(i, i + 1));
				Symbol s2 = new Symbol(cleanest.substring(i + 1, i + 2));
				if (s1.ouvrant && s1.correspond(s2)) {
					remove = i;
				}
			}
		}
		if (remove != -1) {
			String acleanest = "";
			if(remove == cleanest.length()-2) {
				lus.remove(lus.size()-1);
				lus.remove(lus.size()-1);
			} else {
				lus.remove(remove);
				lus.remove(remove);
			}
			
			for (String l : lus) {
				acleanest += l;
			}
			return acleanest;
		} else {
			return cleanest;
		}
	}

	public String complementaire(String s) {
		String retour = "";
		for (int pos = s.length()-1; pos >=0; pos--) {
			Symbol symbole=new Symbol(s.substring(pos, pos+1));
			retour += symbole.getCorrespond();
		}
		return retour;
	}

	public int s1(boolean c) {
		List<String> lignes = getLignes(c);
		return (lignes.stream().mapToInt(this::getValue).reduce(0, (a, b) -> a + b));
	}

	private Integer getValue(String l) {
		// l="{([(<{}[<>[]}>{[]{[(<()>";
		ArrayDeque<Symbol> lus = new ArrayDeque<>();
		Symbol s0 = new Symbol(l.substring(0, 1));
		if (!s0.isOuvrant()) {
			return s0.value;
		} else {
			lus.add(s0);
		}
		for (int i = 1; i < l.length(); i++) {
			String s = l.substring(i, i + 1);
			Symbol sym = new Symbol(s);
			if (sym.isOuvrant()) {
				lus.add(sym);
			} else {
				Symbol s2 = lus.getLast();
				if (sym.correspond(s2)) {
					lus.removeLast();
				} else {
					return sym.value;
				}
			}
		}

		return 0;
	}

//	
//	lus.add(line)

	private List<String> getLignes(boolean c) {
		List<String> lignes = Arrays.asList(getInput(c).split("\n")).stream().collect(Collectors.toList());
		return lignes;
	}

	public static class Symbol {
		String element;
		boolean ouvrant;
		int value = 0;

		public Symbol(String string) {
			super();
			this.element = string;
			if (Arrays.asList("(", "{", "<", "[").contains(string)) {
				this.ouvrant = true;
			} else {
				this.ouvrant = false;
			}
			if (!this.isOuvrant()) {
				if (string.equals(")")) {
					this.value = 3;
				} else if (string.equals("]")) {
					this.value = 57;
				} else if (string.equals("}")) {
					this.value = 1197;
				} else {
					this.value = 25137;
				}

			}
		}

		public boolean correspond(Symbol s) {
			Map<String, String> corres = new HashMap<>();
			corres.put("{", "}");
			corres.put("<", ">");
			corres.put("[", "]");
			corres.put("(", ")");
			corres.put("}", "{");
			corres.put("]", "[");
			corres.put(">", "<");
			corres.put(")", "(");
			return corres.get(s.element).equals(this.element);

		}

		public String getCorrespond() {
			Map<String, String> corres = new HashMap<>();
			corres.put("{", "}");
			corres.put("<", ">");
			corres.put("[", "]");
			corres.put("(", ")");
			corres.put("}", "{");
			corres.put("]", "[");
			corres.put(">", "<");
			corres.put(")", "(");
			return corres.get(this.element);

		}

		@Override
		public String toString() {
			return element;
		}

		public String getElement() {
			return element;
		}

		public void setElement(String element) {
			this.element = element;
		}

		public boolean isOuvrant() {
			return ouvrant;
		}

		public void setOuvrant(boolean ouvrant) {
			this.ouvrant = ouvrant;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

	}
	public static List<Long> getDuration() {
		A2021Day10 d = new A2021Day10(10);
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

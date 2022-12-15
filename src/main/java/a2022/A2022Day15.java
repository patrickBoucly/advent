package a2022;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import outils.MesOutils;

public class A2022Day15 extends A2022 {

	public A2022Day15(int day) {
		super(day);
	}
	public static void main(String[] args0) {
		A2022Day15 d = new A2022Day15(15);
		long startTime = System.currentTimeMillis();
		System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}
	public BigInteger s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().toList();
		Set<Element> elements = getElement(input);
		Carte c = new Carte(elements);
		Element baliseD = c.getBaliseD(b);
		return BigInteger.valueOf(baliseD.y).multiply(BigInteger.valueOf(4000000L)).add(BigInteger.valueOf(baliseD.x));
	}
	public long s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().toList();
		int numLine = 10;
		int numLineB = 2000000;
		Set<Element> elements = getElement(input);
		Carte c = new Carte(elements);
		Long res = 0L;
		if (b) {
			res = c.compteDiese(numLineB);
		} else {
			res = c.compteDiese(numLine);
		}
		return res;
	}
	private Set<Element> getElement(List<String> input) {
		Set<Element> pts = new HashSet<>();
		for (String s : input) {
			String[] sp = s.split(": closest beacon is at ");
			String[] spg = sp[0].replace("Sensor at ", "").split(",");
			String[] spd = sp[1].split(",");
			Element balise = new Element(Integer.valueOf(spd[0].split("=")[1].trim()),
					Integer.valueOf(spd[1].split("=")[1].trim()), "B");
			Element sensor = new Element(Integer.valueOf(spg[0].split("=")[1].trim()),
					Integer.valueOf(spg[1].split("=")[1].trim()), "S", balise);
			sensor.setClosest(balise);
			pts.add(sensor);
			pts.add(balise);
		}
		return pts;
	}
	public class Carte {
		Set<Element> elements;

		public Carte(Set<Element> elements) {
			super();
			this.elements = elements;
		}
		public Element getBaliseD(boolean b) {
			int x = getBonneLigne(b);
			int y = getBonneColonne(x, b);
			return new Element(x, y, "");
		}
		private int getBonneColonne(int bonneligne, boolean b) {
			int max = 20;
			if (b) {
				max = 4000000;
			}
			List<Element> sensors = elements.stream().filter(p -> p.type.equals("S")).toList();
			for (int i = 0; i < max; i++) {
				boolean vu = false;
				for (Element s : sensors) {
					Element xj = new Element(i, bonneligne, ".");
					if (manat(s, xj) <= manat(s, s.closest)) {
						vu = true;
					}
				}
				if (!vu) {
					return i;
				}
			}
			return 0;
		}
		private Integer getBonneLigne(boolean b) {
			int j = 0;
			int max = 20;
			if (b) {
				max = 4000000;
			}
			List<Element> sensors = elements.stream().filter(p -> p.type.equals("S")).toList();
			while (j < max) {
				List<Segment> segVisible = getSegVisible(j, sensors);
				int k = 0;
				int nbS = segVisible.size();
				for (int rep = 0; rep < segVisible.size(); rep++) {
					for (int w = 0; w < nbS; w++) {
						if (segVisible.get(w).contient(k)) {
							k = segVisible.get(w).fin;
							if (k > max) {
								break;
							}
						}
					}
				}
				if (k < max) {
					return j;
				}
				j++;
			}
			return null;
		}
		private List<Segment> getSegVisible(int j, List<Element> sensors) {
			List<Segment> segVisible = new ArrayList<>();
			for (Element s : sensors) {
				Element xj = new Element(s.x, j, ".");
				if (manat(s, xj) <= manat(s, s.closest)) {
					Long dm = manat(s, s.closest);
					Long xamin = s.x - dm + Math.abs(j - s.y);
					Long xamax = dm - Math.abs(j - s.y) + s.x;
					segVisible.add(new Segment(xamin.intValue(), xamax.intValue()));
				}
			}
			return segVisible;
		}
		public Long compteDiese(int numLine) {
			Long cpt=0L;
			List<Element> sensors = elements.stream().filter(p -> p.type.equals("S")).toList();
			List<Segment> segVisible = getSegVisible(numLine, sensors);
			Integer k =MesOutils.getMinIntegerFromList(segVisible.stream().map(Segment::getDeb).toList())-2;
			Integer kmax =MesOutils.getMaxIntegerFromList(segVisible.stream().map(Segment::getFin).toList())+2;
			while(k<kmax){
				Integer lastVu= unSegContientK(segVisible,k);
				if(lastVu==Integer.MIN_VALUE || lastVu.equals(k)) {
					k++;
				} else {
					cpt+=lastVu-k;
					k=lastVu;	
				}
			}
			return cpt;
		}
		private Integer unSegContientK(List<Segment> segVisible, Integer k) {
			int max=Integer.MIN_VALUE;
			for(Segment s:segVisible) {
				if(s.contient(k) && s.fin>max) {
						max=s.fin;
				}
			}
			return max;
		}
		private long manat(Element s, Element b) {
			return (Math.abs(s.x - b.x) + Math.abs(s.y - b.y));

		}
		public Set<Element> getElements() {
			return elements;
		}
		public void setElements(Set<Element> elements) {
			this.elements = elements;
		}
	}
	private static class Segment {
		int deb;
		int fin;
		public Segment(int deb, int fin) {
			super();
			this.deb = deb;
			this.fin = fin;
		}
		public boolean contient(int i) {
			return i <= fin && i >= deb;
		}

		@Override
		public String toString() {
			return "[" + deb + "," + fin + "]";
		}

		public int getDeb() {
			return deb;
		}


		public int getFin() {
			return fin;
		}

	}
	private static class Element {
		int x;
		int y;
		String type;
		Element closest;
		public void setClosest(Element closest) {
			this.closest = closest;
		}
		public Element(int x, int y, String type, Element closest) {
			super();
			this.x = x;
			this.y = y;
			this.type = type;
			this.closest = closest;
		}
		public Element(int x, int y, String type) {
			super();
			this.x = x;
			this.y = y;
			this.type = type;
			
		}
		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Element other = (Element) obj;
			return x == other.x && y == other.y;
		}
	}

	public static List<Long> getDuration() {
		A2022Day15 d = new A2022Day15(15);
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

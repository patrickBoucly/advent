package aocmaven.a2021;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	
	
	private long s2(boolean b) {
		List<String> ln = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());	
		long max=0;
		for(int i=0;i<ln.size();i++) {
			for(int j=0;j<ln.size();j++) {
				if(magnitude(resoudre(Arrays.asList(ln.get(i),ln.get(j))))>max) {
					max=magnitude(resoudre(Arrays.asList(ln.get(i),ln.get(j))));
				}
				if(magnitude(resoudre(Arrays.asList(ln.get(j),ln.get(i))))>max) {
					max=magnitude(resoudre(Arrays.asList(ln.get(j),ln.get(i))));
				}
			}
		}
		
		
		return max;
	}
	
	private String s1(boolean b) {
		
		List<String> ln = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());	
		return resoudre(ln);
	}
	
	public Integer magnitude(String s) {
		Reponse pair=null;
		while(s.contains(",")) {
				pair=getFirstPair(s);
			s=s.substring(0,pair.deb)+magnitudeS(s.substring(pair.deb,pair.fin+1))+s.substring(pair.fin+1);
		}
		
		return Integer.parseInt(s);
		
	}
	
	private Reponse getFirstPair(String s) {
		int posD=-1;
		int posF=-1;
		for(int pos=0;pos<s.length();pos++) {
			if(s.substring(pos, pos+1).equals("[")) {
				posD=pos;
			} else if(s.substring(pos, pos+1).equals("]")) {
				if(posD != -1) {
					return new Reponse(true,posD,pos);
				}
			}
		}
		return null;
	}

	public String magnitudeS(String s1) {
		int virgpos=-1;
		for (int pos = 1; pos < s1.length(); pos++) {
			if ( s1.substring(pos, pos + 1).equals(",")) {
				virgpos = pos;
			}
		}
		int g=Integer.parseInt(s1.substring(1, virgpos));
		int d=Integer.parseInt(s1.substring(virgpos+1, s1.length()-1));
		
		return String.valueOf(3*g+d*2);
	}

	private String resoudre(List<String> ln) {
		String sum = ln.get(0);
		for (int i = 1; i < ln.size(); i++) {
			sum = snailAdd(sum, ln.get(i));
			while (atraiter(sum)) {
				sum = traiter(sum);
			}
		}
		return sum;
	}

	private boolean atraiter(String s) {
		return aspliter(s).resp || explosion(s).resp;
	}

	private String snailAdd(String s1, String s2) {
		return "[" + s1 + "," + s2 + "]";
	}

	private String traiter(String s) {
		Reponse exp = explosion(s);
		Reponse spl = aspliter(s);
		
		if (exp.resp) {
			return explose(s, exp.deb);
		} else if (spl.resp) {
			return spliter(s, spl.deb);
		}
		return s;
	}
	
	

	private Reponse explosion(String s) {
		int cpt = 0;
		for (int pos = 0; pos < s.length(); pos++) {
			if (s.substring(pos, pos + 1).equals("[")) {
				cpt++;
				if (cpt == 5) {
					int dec=0;
					while(s.substring(pos+1+dec, pos +1+dec+1).equals("[")) {
						dec++;
					}
					return new Reponse(true, pos + 1+dec, -1);
				}
			} else if (s.substring(pos, pos + 1).equals("]")) {
				cpt--;
			}
		}
		return new Reponse(false, -1, -1);
	}

	private Reponse aspliter(String s) {
		for (int pos = 0; pos < s.length() - 1; pos++) {
			if (nb.contains(s.substring(pos, pos + 1)) && nb.contains(s.substring(pos + 1, pos + 2))) {
				return new Reponse(true, pos, pos + 1);
			}
		}
		return new Reponse(false, -1, -1);
	}

	private String spliter(String s1, int i) {
		int number = Integer.parseInt(s1.substring(i, i + 2));
		int g;
		int d;
		if (number % 2 == 0) {
			g = number / 2;
			d = g;
		} else {
			g = Math.round(number / 2);
			d = g + 1;
		}
		return s1.substring(0, i) + "[" + g + "," + d + "]" + s1.substring(i + 2);
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
		String newRegularG = "";
		String newRegularD = "";
		String gauche = s1.substring(0, i);
		String droite = s1.substring(posfin + 1);
		if (ag.resp) {
			String regular = s1.substring(ag.deb, ag.fin + 1);
			String nbGauche = s1.substring(i, posvirg);
			newRegularG = String.valueOf(Integer.parseInt(regular) + Integer.parseInt(nbGauche));
			res = gauche.substring(0, ag.deb) + newRegularG + gauche.substring(ag.fin + 1, gauche.length() - 1) + "0";
		} else {
			res = gauche.substring(0, gauche.length() - 1) + "0";
		}
		if (ad.resp) {
			String regular = s1.substring(ad.deb, ad.fin + 1);
			String nbDroite = s1.substring(posvirg + 1, posfin);
			newRegularD = String.valueOf(Integer.parseInt(regular) + Integer.parseInt(nbDroite));
			res += droite.substring(0, ad.deb - posfin - 1) + newRegularD + droite.substring(ad.fin - posfin);
		} else {
			res += droite;
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
				fin = j - 1;
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
				deb = j + 1;
				return new Reponse(fin != -1, deb, fin);
			}
		}
		return new Reponse(fin != -1, deb, fin);
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

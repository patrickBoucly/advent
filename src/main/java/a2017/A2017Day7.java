package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2017Day7 extends A2017 {

	public A2017Day7(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day7 d = new A2017Day7(7);
		// d.s1(true);
		long startTime = System.currentTimeMillis();
		// System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public String s1(boolean b) {
		List<Prog> pgrs = getProgFromInput(b);
		Set<String> pg = new HashSet<>();
		Set<String> spg = new HashSet<>();
		pg = pgrs.stream().map(Prog::getNom).collect(Collectors.toSet());
		for (Prog p : pgrs) {
			if (p.subProg != null) {
				spg.addAll(p.subProg.stream().map(Prog::getNom).collect(Collectors.toList()));
			}
		}
		String nomBase = "";
		for (String s : pg) {
			if (!spg.contains(s)) {
				nomBase = s;
			}
		}

		return nomBase;
	}

	private List<Prog> getProgFromInput(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Prog> pgrs = new ArrayList<>();
		for (String l : lignes) {
			if (l.contains(">")) {
				String[] spi = l.split("->");
				String[] sp = spi[0].split(" ");
				String nom = sp[0].trim();
				int pds = Integer.parseInt(sp[1].replace("(", "").replace(")", ""));
				String[] spj = spi[1].split(",");
				List<String> sousPgNAme = new ArrayList<>();
				for (String s : spj) {
					sousPgNAme.add(s.trim());
				}
				pgrs.add(new Prog(nom, pds, sousPgNAme, false, 10000));
			} else {
				String[] sp = l.split(" ");
				String nom = sp[0].trim();
				int pds = Integer.parseInt(sp[1].replace("(", "").replace(")", ""));
				pgrs.add(new Prog(nom, pds, Arrays.asList("none"), true, 1));
			}
		}
		return pgrs;
	}
	public int s2(boolean b) {
		List<Prog> pgrs = getProgFromInput(b);
		HashMap<String,Integer> np=new HashMap<>();
	//	np.put("rqwgj", getPdsTot("rqwgj",pgrs));
		
		np.put("nhrla", getPdsTot("nhrla",pgrs));
		np.put("titychs", getPdsTot("titychs",pgrs));
		np.put("tgrfyrx", getPdsTot("tgrfyrx",pgrs));
		np.put("aoxky", getPdsTot("aoxky",pgrs));
		np.put("iezlg", getPdsTot("iezlg",pgrs));
		np.put("yraktfn", getPdsTot("yraktfn",pgrs));	
		np.put("idfyy", getPdsTot("idfyy",pgrs));
		np.put("nwpmqu", getPdsTot("nwpmqu",pgrs));
		np.put("owayyaw", getPdsTot("owayyaw",pgrs));
		
		// tajeklj, rkczq, ohyhw, zceovs, gudpriq
		np.put("tajeklj", getPdsTot("tajeklj",pgrs));
		np.put("rkczq", getPdsTot("rkczq",pgrs));
		np.put("ohyhw", getPdsTot("ohyhw",pgrs));
		np.put("zceovs", getPdsTot("zceovs",pgrs));
		np.put("gudpriq", getPdsTot("gudpriq",pgrs));
		
		List<Integer> pds=np.values().stream().collect(Collectors.toList());
		System.out.println(np);
		int dif=MesOutils.getMaxIntegerFromList(pds)-MesOutils.getMinIntegerFromList(pds);
		Prog p=getProg("aobgmc",pgrs);
		return p.pds-8;
	}

	private Integer getPdsTot(String n, List<Prog> pgrs) {
		int pdsTot=0;
		Prog p=getProg(n,pgrs);
		if(p.isFinal) {
			return p.pds;
		} else {
			pdsTot+=p.pds;
			for(String q:p.nomsSubProg) {
				pdsTot+=getPdsTot(q,pgrs);
			}
			return pdsTot;
		}
	}

	private Prog getProg(String n, List<Prog> pgrs) {
		for(Prog p:pgrs) {
			if(p.nom.equals(n)) {
				return p;
			}
		}
		return null;
	}

	public int s2b(boolean b) {
		List<Prog> pgrs = getProgFromInput(b);
		for (Prog p : pgrs) {
			if (p.nom.equals("pxzzw")) {
				System.out.println(p.nom + " " + p.nomsSubProg);
			}
		}
		int deepNValue = 1;
		List<Prog> ident = getIdent(pgrs);
		List<Prog> pasIdent = getPasIdent(pgrs);
		System.out.println("");
		while (pasIdent.size() > 2) {
			for (Prog x : pasIdent) {
				for (String spXs : x.nomsSubProg) {
					for (Prog i : ident) {
						if (i.nom.equals(spXs)) {
							x.subProg.add(x);
							x.pdsTot = x.subProg.stream().map(Prog::getPds).reduce(0, (a, c) -> a + c) + x.pds;
							

						}
					}
				}
			}
			/*
			if (pasIdent.size() == 2) {
				for (Prog x : pasIdent) {
					for (String spXs : x.nomsSubProg) {
						for (Prog i : pgrs) {
							if (i.nom.equals(spXs)) {
								x.subProg.add(x);
								if (x.subProg.size() == x.nomsSubProg.size()) {
									x.pdsTot = x.subProg.stream().map(Prog::getPds).reduce(0, (a, c) -> a + c) + x.pds;
								}

							}
						}
					}
				}
			}
			*/
			ident = getIdent(pgrs);
			pasIdent = getPasIdent(pgrs);
		}

		
		for (Prog p : pgrs) {
			for(Prog q:pasIdent) {
			//	System.out.println(q.nom+ " "+q.nomsSubProg);
				if(q.nomsSubProg.contains(p.nom)) {
					System.out.println(p.nom+" "+p.pdsTot);
				}
				
			}
		}
		
		return 0;

	}

	private List<Prog> getPasIdent(List<Prog> pgrs) {
		return pgrs.stream().filter(p -> p.getSubProg().isEmpty() && !p.isFinal).collect(Collectors.toList());
	}

	private List<Prog> getIdent(List<Prog> pgrs) {
		return pgrs.stream().filter(p -> p.getSubProg().size() == p.getNomsSubProg().size() || p.isFinal)
				.collect(Collectors.toList());
	}

	private List<Prog> getDeepNSup(int deepNValue, List<Prog> pgrs) {
		return pgrs.stream().filter(p -> p.deep > deepNValue).collect(Collectors.toList());
	}

	private List<Prog> getDeepN(int deepNValue, List<Prog> pgrs) {
		return pgrs.stream().filter(p -> p.deep == deepNValue).collect(Collectors.toList());
	}

	public Prog getFN(Prog p, List<Prog> l) {
		if (!l.contains(p)) {
			return null;
		} else {
			for (Prog li : l) {
				if (li.nom.equals(p.nom)) {
					return li;
				}
			}
		}
		return null;
	}

	private void complete(Prog p, List<Prog> pgrs) {
		for (Prog sub : p.subProg) {
			for (Prog pg : pgrs) {
				if (pg.nom.equals(sub.nom)) {
					if (!pg.isFinal) {
						complete(sub, pgrs);
					} else {
						sub.isFinal = true;
						sub.pds = pg.pds;
						sub.subProg = null;
					}
				}
			}

		}

	}

	public static class Prog {
		String nom;
		Integer pds;
		List<Prog> subProg;
		boolean isFinal;
		boolean added = false;
		int deep;
		int pdsTot;
		List<String> nomsSubProg;

		public List<String> getNomsSubProg() {
			return nomsSubProg;
		}

		public void setNomsSubProg(List<String> nomsSubProg) {
			this.nomsSubProg = nomsSubProg;
		}

		public int getDeep() {
			return deep;
		}

		public void setDeep(int deep) {
			this.deep = deep;
		}

		public int getPdsTot() {
			return pdsTot;
		}

		public void setPdsTot(int pdsTot) {
			this.pdsTot = pdsTot;
		}

		public boolean isAdded() {
			return added;
		}

		public boolean isComplete() {
			if (isFinal) {
				return true;
			}
			for (Prog p : subProg) {
				if (!p.isFinal && p.subProg.size() == 0) {
					return false;
				}
			}
			return true;
		}

		public void setAdded(boolean added) {
			this.added = added;
		}

		public Prog(String nom, Integer pds, List<String> nomsSubProg, boolean isFinal, int d) {
			super();
			this.nom = nom;
			this.pds = pds;
			this.nomsSubProg = nomsSubProg;
			this.isFinal = isFinal;
			this.added = false;
			this.deep = d;
			this.subProg = new ArrayList<>();

		}

		public boolean incomplet() {
			if (isFinal) {
				return false;
			}
			for (Prog p : subProg) {
				if (p.subProg == null && !p.isFinal) {
					return true;
				}
			}
			return false;
		}

		public boolean isFinal() {
			return isFinal;
		}

		public void setFinal(boolean isFinal) {
			this.isFinal = isFinal;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public Integer getPds() {
			return pds;
		}

		public void setPds(Integer pds) {
			this.pds = pds;
		}

		public List<Prog> getSubProg() {
			return subProg;
		}

		public void setSubProg(List<Prog> subProg) {
			this.subProg = subProg;
		}

		public Prog(String nom, Integer pds, List<String> nomsSubProg) {
			super();
			this.nom = nom;
			this.pds = pds;
			this.subProg = subProg;
		}

		@Override
		public String toString() {
			return "Prog [nom=" + nom + ", pds=" + pds + ", subProg=" + subProg + ", isFinal=" + isFinal + ", added="
					+ added + ", deep=" + deep + ", pdsTot=" + pdsTot + "]";
		}

		@Override
		public int hashCode() {
			return Objects.hash(nom);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Prog other = (Prog) obj;
			return Objects.equals(nom, other.nom);
		}

	}

	public static List<Long> getDuration() {
		A2017Day7 d = new A2017Day7(1);
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

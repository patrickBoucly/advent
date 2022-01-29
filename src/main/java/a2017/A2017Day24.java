package a2017;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class A2017Day24 extends A2017 {
	private static int autoid = 0;

	public A2017Day24(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day24 d = new A2017Day24(24);
		// d.s1(true);
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

	public int s1(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<ElementPont> l = new ArrayList<>();

		for (String li : lignes) {
			String[] sp = li.split("/");
			l.add(new ElementPont(Integer.parseInt(sp[0]), Integer.parseInt(sp[1])));

		}
		int max=0;
		System.out.println(l);
		int thisMax = -1;
		List<ElementPont> fp = l.stream().filter(e -> e.vg == 0 || e.vd == 0).collect(Collectors.toList());
		for (ElementPont fpi : fp) {
		
			List<ElementPont> els = new ArrayList<>(
					l.stream().filter(e -> !(e.id==fpi.id)).collect(Collectors.toList()));
			Entry<Integer, Pont> ceRes = getPontsFromElementBase(fpi, els);
			thisMax = ceRes.getKey();
			if (thisMax > max) {
				System.out.println(ceRes.getValue());
				max = thisMax;
			}

		}
	
	return max;
		
	}
	
	public int s1b(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<ElementPont> elementsPont = new ArrayList<>();

		for (String l : lignes) {
			String[] sp = l.split("/");
			elementsPont.add(new ElementPont(Integer.parseInt(sp[0]), Integer.parseInt(sp[1]), 0));

		}

		HashMap<Integer, Integer> cpt = getCpt(elementsPont);
		int max = -1;
		boolean methodSimple = true;
		if (!methodSimple) {
			System.out.println("init cpt :" + cpt);
			System.out.println(elementsPont);
			List<ElementPont> elementsPontF2 = f2(elementsPont);
			System.out.println(f2(elementsPont));
			System.out.println(getCpt(f2(elementsPont)));
			List<List<ElementPont>> elementsPontF4 = f4(elementsPontF2);
			System.out.println(elementsPontF4.size());
			System.out.println(elementsPontF4);
			for (List<ElementPont> l : elementsPontF4) {
				// System.out.println(getCpt(l));
			}
			List<List<ElementPont>> listeElementF5 = f5(elementsPontF4);
			List<List<ElementPont>> listeElementF6 = f6(listeElementF5);

			/*
			 * List<ElementPont> elementsPontF6 = f6(elementsPontF4);
			 * System.out.println("sansP" +elementsPontF6);
			 * System.out.println(getCpt(elementsPontF6)); List<List<ElementPont>>
			 * listeElementF5 = f5(elementsPontF6); System.out.println(listeElementF5); for
			 * (List<ElementPont> l : listeElementF5) { System.out.println(getCpt(l)); }
			 */

			/*
			 * elementsPontF2=Pont.sortedSet(elementsPontF2); for(ElementPont
			 * e:elementsPontF2) { System.out.println((e)); } List<List<ElementPont>>
			 * listeElementF3 = f3(elementsPontF2); for (List<ElementPont> l3i :
			 * listeElementF3) { // System.out.println(l3i); //
			 * System.out.println(getCpt(l3i)); } List<List<ElementPont>> listeElementF3LV2
			 * = new ArrayList<>(); for (List<ElementPont> l : listeElementF3) {
			 * listeElementF3LV2.addAll(f3(l)); } List<List<ElementPont>> listeElementF3LV3
			 * = new ArrayList<>(); for (List<ElementPont> l : listeElementF3LV2) {
			 * listeElementF3LV3.addAll(f3(l)); }
			 * 
			 * List<List<ElementPont>> listeElementF3LV4 = new ArrayList<>(); for
			 * (List<ElementPont> l : listeElementF3LV3) { listeElementF3LV4.addAll(f3(l));
			 * } List<List<ElementPont>> listeElementF3LV5 = new ArrayList<>(); for
			 * (List<ElementPont> l : listeElementF3LV4) { listeElementF3LV5.addAll(f3(l));
			 * }
			 * 
			 * List<List<ElementPont>> listeElementF3LV6 = new ArrayList<>(); for
			 * (List<ElementPont> l : listeElementF3LV5) { listeElementF3LV6.addAll(f3(l));
			 * } Set<Set<ElementPont>> listeElementF3LV6Set = new HashSet<>(); for
			 * (List<ElementPont> l : listeElementF3LV6) { listeElementF3LV6Set.add(new
			 * HashSet<>(l)); }
			 */
			max = 0;
			int cptr = 0;
			// System.out.println(listeElementF3LV6Set.size());
			// System.out.println(getSize(listeElementF3LV6));
			List<Pont> ponts = new ArrayList<>();
			long startTime = System.currentTimeMillis();
			System.out.println(listeElementF6.size() + "!!!!");
			for (List<ElementPont> l : listeElementF6) {
				// List<ElementPont> l = elementsPontF4;
				// new ArrayList<>(ls);
				cptr++;
				/*
				 * System.out.println("cptr "+cptr); System.out.println(getCpt(l));
				 * System.out.println(l.size()); System.out.println(l);
				 */
				int thisMax = -1;
				ponts = new ArrayList<>();
				List<ElementPont> fp = l.stream().filter(e -> e.vg == 0 || e.vd == 0).collect(Collectors.toList());
				for (ElementPont fpi : fp) {
					// System.out.println(fpi);
					List<ElementPont> els = new ArrayList<>(
							l.stream().filter(e -> e.id != fpi.id).collect(Collectors.toList()));
					Entry<Integer, Pont> ceRes = getPontsFromElementBase(fpi, els);
					thisMax = ceRes.getKey();
					// if(thisMax>1500)

					if (thisMax > max) {
						System.out.println("traité :" + cptr + " max actuel: " + max);
						System.out.println(ceRes.getValue());
						max = thisMax;
					}
					if (cptr % 2000 == 0) {
						System.out.println("traité :" + cptr);
					}

				}

			}

		} else {
			List<ElementPont> l = new ArrayList<>(elementsPont);

			int thisMax = -1;
			List<ElementPont> fp = l.stream().filter(e -> e.vg == 0 || e.vd == 0).collect(Collectors.toList());
			for (ElementPont fpi : fp) {
				// System.out.println(fpi);
				List<ElementPont> els = new ArrayList<>(
						l.stream().filter(e -> !(e.vg == 0 || e.vd == 0)).collect(Collectors.toList()));
				Entry<Integer, Pont> ceRes = getPontsFromElementBase(fpi, els);
				thisMax = ceRes.getKey();
				// if(thisMax>1500)

				if (thisMax > max) {
					System.out.println(ceRes.getValue());
					max = thisMax;
				}

			}
		}
		return max;
	}

	private List<List<ElementPont>> f5(List<List<ElementPont>> elementsPontF4) {
		List<List<ElementPont>> fusion5 = new ArrayList<>();
		for (List<ElementPont> e : elementsPontF4) {
			fusion5.addAll(unfusion5(e, 45));
		}
		return fusion5;
	}

	private Collection<? extends List<ElementPont>> unfusion5(List<ElementPont> elementsPont, Integer lintAF) {
		List<List<ElementPont>> cetFusion5 = new ArrayList<>();
		List<ElementPont> f5 = new ArrayList<>();
		for (ElementPont e : elementsPont) {
			if (!(lintAF == e.vd || lintAF == e.vg)) {
				f5.add(e);
			}
		}
		List<ElementPont> af = new ArrayList<>();

		for (ElementPont e : elementsPont) {
			if (lintAF == e.vd || lintAF == e.vg) {
				af.add(e);
			}
		}
		cetFusion5 = cetF5(lintAF, af, f5);
		return cetFusion5;
	}

	private List<List<ElementPont>> cetF5(Integer lintAF, List<ElementPont> af, List<ElementPont> f5) {
		List<List<ElementPont>> res = new ArrayList<>();
		List<ElementPont> f3c = new ArrayList<>(f5);
		// System.out.println(af);
		f3c.add(af.get(4));
		res.addAll(merge5(lintAF, af.get(0), af.get(1), af.get(2), af.get(3), f3c));
		f3c = new ArrayList<>(f5);
		f3c.add(af.get(3));
		res.addAll(merge5(lintAF, af.get(0), af.get(1), af.get(2), af.get(4), f3c));
		f3c = new ArrayList<>(f5);
		f3c.add(af.get(2));
		res.addAll(merge5(lintAF, af.get(0), af.get(1), af.get(4), af.get(3), f3c));
		f3c = new ArrayList<>(f5);
		f3c.add(af.get(1));
		res.addAll(merge5(lintAF, af.get(0), af.get(4), af.get(2), af.get(3), f3c));
		f3c = new ArrayList<>(f5);
		f3c.add(af.get(0));
		res.addAll(merge5(lintAF, af.get(4), af.get(1), af.get(2), af.get(3), f3c));
		return res;
	}

	private List<List<ElementPont>> merge5(Integer lintAF, ElementPont a, ElementPont b, ElementPont c, ElementPont d,
			List<ElementPont> f4) {
		List<ElementPont> f = new ArrayList<>(f4);
		List<List<ElementPont>> res = new ArrayList<>();
		List<ElementPont> af = new ArrayList<>();
		af.add(a);
		af.add(b);
		af.add(c);
		af.add(d);

		a = af.get(0);
		b = af.get(1);
		c = af.get(2);
		d = af.get(3);
		f.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		res.add(f);
		a = af.get(0);
		b = af.get(2);
		c = af.get(1);
		d = af.get(3);
		f = new ArrayList<>(f4);
		// System.out.println(f);
		f.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		res.add(f);
		a = af.get(0);
		b = af.get(3);
		c = af.get(1);
		d = af.get(2);
		f = new ArrayList<>(f4);
		// System.out.println(f);
		f.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		res.add(f);
		return res;
	}

	private List<List<ElementPont>> f6(List<List<ElementPont>> listeElementF5) {
		List<List<ElementPont>> fusion6 = new ArrayList<>();
		for (List<ElementPont> e : listeElementF5) {
			fusion6.addAll(unfusion6(e, 44));
		}
		return fusion6;
	}

	private List<List<ElementPont>> unfusion6(List<ElementPont> elementsPont, int lintAF) {
		List<List<ElementPont>> cetFusion6 = new ArrayList<>();
		List<ElementPont> f6 = new ArrayList<>();
		for (ElementPont e : elementsPont) {
			if (!(lintAF == e.vd || lintAF == e.vg)) {
				f6.add(e);
			}
		}
		List<ElementPont> af = new ArrayList<>();

		for (ElementPont e : elementsPont) {
			if (lintAF == e.vd || lintAF == e.vg) {
				af.add(e);
			}
		}
		cetFusion6 = cetF6(lintAF, af, f6);
		return cetFusion6;
	}

	private List<List<ElementPont>> cetF6(int lintAF, List<ElementPont> af, List<ElementPont> f6) {
		List<List<ElementPont>> res = new ArrayList<>();
		ElementPont a = null;
		ElementPont c = null;
		ElementPont d = null;
		ElementPont b = null;
		ElementPont e = null;
		ElementPont f = null;
		List<ElementPont> f3c = new ArrayList<>();

		f3c = new ArrayList<>(f6);
		a = af.get(0);
		b = af.get(1);
		c = af.get(2);
		d = af.get(3);
		e = af.get(4);
		f = af.get(5);

		// System.out.println(af);
		f3c.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f3c.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		f3c.add(new ElementPont((e.vg == lintAF) ? e.vd : c.vg, (f.vg == lintAF) ? f.vd : f.vg,
				2 * lintAF + e.fusionValeur + f.fusionValeur));
		res.add(f3c);

		a = af.get(0);
		b = af.get(1);
		c = af.get(2);
		d = af.get(4);
		e = af.get(3);
		f = af.get(5);

		f3c = new ArrayList<>(f6);
		// System.out.println(af);
		f3c.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f3c.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		f3c.add(new ElementPont((e.vg == lintAF) ? e.vd : c.vg, (f.vg == lintAF) ? f.vd : f.vg,
				2 * lintAF + e.fusionValeur + f.fusionValeur));
		res.add(f3c);

		a = af.get(0);
		b = af.get(1);
		c = af.get(2);
		d = af.get(4);
		e = af.get(5);
		f = af.get(3);

		f3c = new ArrayList<>(f6);
		// System.out.println(af);
		f3c.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f3c.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		f3c.add(new ElementPont((e.vg == lintAF) ? e.vd : c.vg, (f.vg == lintAF) ? f.vd : f.vg,
				2 * lintAF + e.fusionValeur + f.fusionValeur));
		res.add(f3c);
		// AC

		a = af.get(0);
		b = af.get(2);
		c = af.get(1);
		d = af.get(3);
		e = af.get(4);
		f = af.get(5);

		f3c = new ArrayList<>(f6);
		// System.out.println(af);
		f3c.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f3c.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		f3c.add(new ElementPont((e.vg == lintAF) ? e.vd : c.vg, (f.vg == lintAF) ? f.vd : f.vg,
				2 * lintAF + e.fusionValeur + f.fusionValeur));
		res.add(f3c);

		a = af.get(0);
		b = af.get(2);
		c = af.get(1);
		d = af.get(4);
		e = af.get(3);
		f = af.get(5);

		f3c = new ArrayList<>(f6);
		// System.out.println(af);
		f3c.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f3c.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		f3c.add(new ElementPont((e.vg == lintAF) ? e.vd : c.vg, (f.vg == lintAF) ? f.vd : f.vg,
				2 * lintAF + e.fusionValeur + f.fusionValeur));
		res.add(f3c);

		a = af.get(0);
		b = af.get(2);
		c = af.get(1);
		d = af.get(4);
		e = af.get(5);
		f = af.get(3);

		f3c = new ArrayList<>(f6);
		// System.out.println(af);
		f3c.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f3c.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		f3c.add(new ElementPont((e.vg == lintAF) ? e.vd : c.vg, (f.vg == lintAF) ? f.vd : f.vg,
				2 * lintAF + e.fusionValeur + f.fusionValeur));
		res.add(f3c);

		// AD

		f3c = new ArrayList<>(f6);
		a = af.get(0);
		b = af.get(3);
		c = af.get(2);
		d = af.get(1);
		e = af.get(4);
		f = af.get(5);

		// System.out.println(af);
		f3c.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f3c.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		f3c.add(new ElementPont((e.vg == lintAF) ? e.vd : c.vg, (f.vg == lintAF) ? f.vd : f.vg,
				2 * lintAF + e.fusionValeur + f.fusionValeur));
		res.add(f3c);

		a = af.get(0);
		b = af.get(4);
		c = af.get(2);
		d = af.get(1);
		e = af.get(3);
		f = af.get(5);

		f3c = new ArrayList<>(f6);
		// System.out.println(af);
		f3c.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f3c.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		f3c.add(new ElementPont((e.vg == lintAF) ? e.vd : c.vg, (f.vg == lintAF) ? f.vd : f.vg,
				2 * lintAF + e.fusionValeur + f.fusionValeur));
		res.add(f3c);

		a = af.get(0);
		b = af.get(4);
		c = af.get(2);
		d = af.get(1);
		e = af.get(5);
		f = af.get(3);

		f3c = new ArrayList<>(f6);
		// System.out.println(af);
		f3c.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f3c.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		f3c.add(new ElementPont((e.vg == lintAF) ? e.vd : c.vg, (f.vg == lintAF) ? f.vd : f.vg,
				2 * lintAF + e.fusionValeur + f.fusionValeur));
		res.add(f3c);

		// AE

		f3c = new ArrayList<>(f6);
		a = af.get(0);
		b = af.get(4);
		c = af.get(2);
		d = af.get(3);
		e = af.get(1);
		f = af.get(5);

		// System.out.println(af);
		f3c.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f3c.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		f3c.add(new ElementPont((e.vg == lintAF) ? e.vd : c.vg, (f.vg == lintAF) ? f.vd : f.vg,
				2 * lintAF + e.fusionValeur + f.fusionValeur));
		res.add(f3c);

		a = af.get(0);
		b = af.get(3);
		c = af.get(2);
		d = af.get(4);
		e = af.get(1);
		f = af.get(5);

		f3c = new ArrayList<>(f6);
		// System.out.println(af);
		f3c.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f3c.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		f3c.add(new ElementPont((e.vg == lintAF) ? e.vd : c.vg, (f.vg == lintAF) ? f.vd : f.vg,
				2 * lintAF + e.fusionValeur + f.fusionValeur));
		res.add(f3c);

		a = af.get(0);
		b = af.get(5);
		c = af.get(2);
		d = af.get(4);
		e = af.get(1);
		f = af.get(3);

		f3c = new ArrayList<>(f6);
		// System.out.println(af);
		f3c.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f3c.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		f3c.add(new ElementPont((e.vg == lintAF) ? e.vd : c.vg, (f.vg == lintAF) ? f.vd : f.vg,
				2 * lintAF + e.fusionValeur + f.fusionValeur));
		res.add(f3c);

		// AF

		f3c = new ArrayList<>(f6);
		a = af.get(0);
		b = af.get(5);
		c = af.get(2);
		d = af.get(3);
		e = af.get(4);
		f = af.get(1);

		// System.out.println(af);
		f3c.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f3c.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		f3c.add(new ElementPont((e.vg == lintAF) ? e.vd : c.vg, (f.vg == lintAF) ? f.vd : f.vg,
				2 * lintAF + e.fusionValeur + f.fusionValeur));
		res.add(f3c);

		a = af.get(0);
		b = af.get(5);
		c = af.get(2);
		d = af.get(4);
		e = af.get(3);
		f = af.get(1);

		f3c = new ArrayList<>(f6);
		// System.out.println(af);
		f3c.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f3c.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		f3c.add(new ElementPont((e.vg == lintAF) ? e.vd : c.vg, (f.vg == lintAF) ? f.vd : f.vg,
				2 * lintAF + e.fusionValeur + f.fusionValeur));
		res.add(f3c);

		a = af.get(0);
		b = af.get(3);
		c = af.get(2);
		d = af.get(4);
		e = af.get(5);
		f = af.get(1);

		f3c = new ArrayList<>(f6);
		// System.out.println(af);
		f3c.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		f3c.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
				2 * lintAF + c.fusionValeur + d.fusionValeur));
		f3c.add(new ElementPont((e.vg == lintAF) ? e.vd : c.vg, (f.vg == lintAF) ? f.vd : f.vg,
				2 * lintAF + e.fusionValeur + f.fusionValeur));
		res.add(f3c);

		return res;
	}

	private List<ElementPont> uneF6(List<ElementPont> elementsPont) {
		List<ElementPont> f2 = new ArrayList<>();

		HashMap<Integer, Integer> cpt = getCpt(elementsPont);
		List<Integer> intAF4 = new ArrayList<>();
		for (Entry<Integer, Integer> e : cpt.entrySet()) {
			if (e.getValue() == 6) {
				intAF4.add(e.getKey());
			}
		}

		int lintAF = intAF4.get(0);
		// System.out.println(lintAF);
		for (ElementPont e : elementsPont) {
			if (!(lintAF == e.vd || lintAF == e.vg)) {
				f2.add(e);
			}
		}
		List<ElementPont> af = new ArrayList<>();
		for (Integer i : intAF4) {

			for (ElementPont e : elementsPont) {
				if (i == e.vd || i == e.vg) {
					af.add(e);
				}
			}
		}
		/*
		 * System.out.println(new ElementPont(id++, (af.get(0).vg == lintAF) ?
		 * af.get(0).vd : af.get(0).vg, (af.get(1).vg == lintAF) ? af.get(1).vd :
		 * af.get(1).vg, 2 * lintAF + af.get(0).fusionValeur + af.get(1).fusionValeur));
		 */
		f2.add(new ElementPont((af.get(0).vg == lintAF) ? af.get(0).vd : af.get(0).vg,
				(af.get(1).vg == lintAF) ? af.get(1).vd : af.get(1).vg,
				2 * lintAF + af.get(0).fusionValeur + af.get(1).fusionValeur));
		f2.add(new ElementPont((af.get(2).vg == lintAF) ? af.get(2).vd : af.get(2).vg,
				(af.get(3).vg == lintAF) ? af.get(3).vd : af.get(3).vg,
				2 * lintAF + af.get(2).fusionValeur + af.get(3).fusionValeur));
		f2.add(new ElementPont((af.get(4).vg == lintAF) ? af.get(4).vd : af.get(2).vg,
				(af.get(5).vg == lintAF) ? af.get(5).vd : af.get(5).vg,
				2 * lintAF + af.get(4).fusionValeur + af.get(5).fusionValeur));

		return f2;
	}

	private List<List<ElementPont>> f4(List<ElementPont> elementsPontF2) {
		HashMap<Integer, Integer> cpt = getCpt(elementsPontF2);

		List<List<ElementPont>> fusion4 = new ArrayList<>();
		List<List<ElementPont>> mfusion4 = new ArrayList<>();
		List<Integer> intsAF = new ArrayList<>();
		for (Entry<Integer, Integer> e : cpt.entrySet()) {
			if (e.getValue() == 4) {
				intsAF.add(e.getKey());
			}
		}
		fusion4 = Arrays.asList(elementsPontF2);
		for (Integer i : intsAF) {
			mfusion4 = new ArrayList<>();
			for (List<ElementPont> le : fusion4) {
				mfusion4.addAll(unfusion4(le, i));
			}
			fusion4 = new ArrayList<>(mfusion4);
		}

		return fusion4;
	}

	private List<List<ElementPont>> unfusion4(List<ElementPont> elementsPontF2, Integer lintAF) {
		List<List<ElementPont>> cetFusion4 = new ArrayList<>();
		List<ElementPont> f4 = new ArrayList<>();
		for (ElementPont e : elementsPontF2) {
			if (!(lintAF == e.vd || lintAF == e.vg)) {
				f4.add(e);
			}
		}
		List<ElementPont> af = new ArrayList<>();

		for (ElementPont e : elementsPontF2) {
			if (lintAF == e.vd || lintAF == e.vg) {
				af.add(e);
			}
		}

		cetFusion4 = cetF4(lintAF, af, f4);

		return cetFusion4;
	}

	private List<List<ElementPont>> cetF4(Integer lintAF, List<ElementPont> af, List<ElementPont> f4) {
		List<List<ElementPont>> res = new ArrayList<>();
		ElementPont a = null;
		ElementPont c = null;
		ElementPont d = null;
		ElementPont b = null;

		// System.out.println(af);
		List<ElementPont> f = new ArrayList<>(f4);

		if (lintAF != 50) {
			a = af.get(0);
			b = af.get(1);
			c = af.get(2);
			d = af.get(3);
			f.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
					2 * lintAF + a.fusionValeur + b.fusionValeur));
			f.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
					2 * lintAF + c.fusionValeur + d.fusionValeur));
			res.add(f);
			a = af.get(0);
			b = af.get(2);
			c = af.get(1);
			d = af.get(3);
			f = new ArrayList<>(f4);
			// System.out.println(f);
			f.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
					2 * lintAF + a.fusionValeur + b.fusionValeur));
			f.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
					2 * lintAF + c.fusionValeur + d.fusionValeur));
			res.add(f);
			a = af.get(0);
			b = af.get(3);
			c = af.get(1);
			d = af.get(2);
			f = new ArrayList<>(f4);
			// System.out.println(f);
			f.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
					2 * lintAF + a.fusionValeur + b.fusionValeur));
			f.add(new ElementPont((c.vg == lintAF) ? c.vd : c.vg, (d.vg == lintAF) ? d.vd : d.vg,
					2 * lintAF + c.fusionValeur + d.fusionValeur));
			res.add(f);
		} else {
			a = af.get(0);
			b = af.get(1);
			c = af.get(2);
			f = new ArrayList<>(f4);
			f.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
					2 * lintAF + a.fusionValeur + b.fusionValeur + c.fusionValeur + c.vd + c.vg));
			res.add(f);

		}
		return res;
	}

	private List<ElementPont> uneF4(List<ElementPont> elementsPont) {
		List<ElementPont> f2 = new ArrayList<>();

		HashMap<Integer, Integer> cpt = getCpt(elementsPont);
		List<Integer> intAF4 = new ArrayList<>();
		for (Entry<Integer, Integer> e : cpt.entrySet()) {
			if (e.getValue() == 4) {
				intAF4.add(e.getKey());
			}
		}

		int lintAF = intAF4.get(0);
		// System.out.println(lintAF);
		for (ElementPont e : elementsPont) {
			if (!(lintAF == e.vd || lintAF == e.vg)) {
				f2.add(e);
			}
		}
		List<ElementPont> af = new ArrayList<>();
		for (Integer i : intAF4) {

			for (ElementPont e : elementsPont) {
				if (i == e.vd || i == e.vg) {
					af.add(e);
				}
			}
		}
		/*
		 * System.out.println(new ElementPont(id++, (af.get(0).vg == lintAF) ?
		 * af.get(0).vd : af.get(0).vg, (af.get(1).vg == lintAF) ? af.get(1).vd :
		 * af.get(1).vg, 2 * lintAF + af.get(0).fusionValeur + af.get(1).fusionValeur));
		 */
		f2.add(new ElementPont((af.get(0).vg == lintAF) ? af.get(0).vd : af.get(0).vg,
				(af.get(1).vg == lintAF) ? af.get(1).vd : af.get(1).vg,
				2 * lintAF + af.get(0).fusionValeur + af.get(1).fusionValeur));
		f2.add(new ElementPont((af.get(2).vg == lintAF) ? af.get(2).vd : af.get(2).vg,
				(af.get(3).vg == lintAF) ? af.get(3).vd : af.get(3).vg,
				2 * lintAF + af.get(2).fusionValeur + af.get(3).fusionValeur));

		return f2;
	}

	private List<List<ElementPont>> f3(List<ElementPont> elementsPontF2) {
		HashMap<Integer, Integer> cpt = getCpt(elementsPontF2);
		List<List<ElementPont>> fusion3 = new ArrayList<>();
		for (Entry<Integer, Integer> e : cpt.entrySet()) {
			if (e.getValue() == 3 && e.getKey() != 0) {
				fusion3.addAll(unfusion3(elementsPontF2, e.getKey()));
			}

		}
		return fusion3;
	}

	private Collection<? extends List<ElementPont>> unfusion3(List<ElementPont> elementsPontF2, Integer lintAF) {
		List<List<ElementPont>> cetFusion3 = new ArrayList<>();
		List<ElementPont> f3 = new ArrayList<>();
		for (ElementPont e : elementsPontF2) {
			if (!(lintAF == e.vd || lintAF == e.vg)) {
				f3.add(e);
			}
		}
		List<ElementPont> af = new ArrayList<>();

		for (ElementPont e : elementsPontF2) {
			if (lintAF == e.vd || lintAF == e.vg) {
				af.add(e);
			}
		}
		cetFusion3 = cetF3(lintAF, af, f3);
		return cetFusion3;
	}

	private List<List<ElementPont>> cetF3(Integer lintAF, List<ElementPont> af, List<ElementPont> f3) {
		List<List<ElementPont>> res = new ArrayList<>();
		List<ElementPont> f3c = new ArrayList<>(f3);
		// System.out.println(af);
		f3c.add(af.get(2));
		res.add(merge(lintAF, af.get(0), af.get(1), f3c));
		f3c = new ArrayList<>(f3);
		f3c.add(af.get(1));
		res.add(merge(lintAF, af.get(0), af.get(2), f3c));
		f3c = new ArrayList<>(f3);
		f3c.add(af.get(0));
		res.add(merge(lintAF, af.get(2), af.get(1), f3c));
		return res;
	}

	private List<ElementPont> merge(Integer lintAF, ElementPont a, ElementPont b, List<ElementPont> f3) {
		List<ElementPont> f = new ArrayList<>(f3);
		// System.out.println(f);
		f.add(new ElementPont((a.vg == lintAF) ? a.vd : a.vg, (b.vg == lintAF) ? b.vd : b.vg,
				2 * lintAF + a.fusionValeur + b.fusionValeur));
		// System.out.println(f);
		return f;
	}

	private List<ElementPont> f2(List<ElementPont> elementsPont) {
		HashMap<Integer, Integer> cpt = getCpt(elementsPont);
		List<ElementPont> uneFDePlus = removeD(elementsPont);
		while (cpt.containsValue(2)) {
			uneFDePlus = new ArrayList<>(uneF(uneFDePlus));
			cpt = getCpt(uneFDePlus);
		}
		return uneFDePlus;
	}

	private List<ElementPont> removeD(List<ElementPont> elementsPont) {
		List<ElementPont> removeD = new ArrayList<>();
		for (ElementPont e : elementsPont) {
			if (e.vg != e.vd) {
				removeD.add(e);
			}
		}
		return removeD;
	}

	private HashMap<Integer, Integer> getCpt(List<ElementPont> elementsPont) {
		HashMap<Integer, Integer> cpt = new HashMap<>();
		for (ElementPont e : elementsPont) {
			if (e.vd != e.vg) {
				if (cpt.containsKey(e.vd)) {
					cpt.put(e.vd, cpt.get(e.vd) + 1);
				} else {
					cpt.put(e.vd, 1);
				}
				if (cpt.containsKey(e.vg)) {
					cpt.put(e.vg, cpt.get(e.vg) + 1);
				} else {
					cpt.put(e.vg, 1);
				}
			}
		}
		return cpt;
	}

	private HashMap<Integer, Integer> getSize(List<List<ElementPont>> lelementsPont) {
		HashMap<Integer, Integer> cpt = new HashMap<>();
		for (List<ElementPont> le : lelementsPont) {

			if (cpt.containsKey(le.size())) {
				cpt.put(le.size(), cpt.get(le.size()) + 1);
			} else {
				cpt.put(le.size(), 1);
			}

		}

		return cpt;
	}

	private List<ElementPont> uneF(List<ElementPont> elementsPont) {
		List<ElementPont> f2 = new ArrayList<>();

		HashMap<Integer, Integer> cpt = getCpt(elementsPont);
		List<Integer> intAF2 = new ArrayList<>();
		for (Entry<Integer, Integer> e : cpt.entrySet()) {
			if (e.getValue() == 2) {
				intAF2.add(e.getKey());
			}
		}

		int lintAF = intAF2.get(0);
		// System.out.println(lintAF);
		for (ElementPont e : elementsPont) {
			if (!(lintAF == e.vd || lintAF == e.vg)) {
				f2.add(e);
			}
		}
		List<ElementPont> af = new ArrayList<>();
		for (Integer i : intAF2) {

			for (ElementPont e : elementsPont) {
				if (i == e.vd || i == e.vg) {
					af.add(e);
				}
			}
		}
		/*
		 * System.out.println(new ElementPont(id++, (af.get(0).vg == lintAF) ?
		 * af.get(0).vd : af.get(0).vg, (af.get(1).vg == lintAF) ? af.get(1).vd :
		 * af.get(1).vg, 2 * lintAF + af.get(0).fusionValeur + af.get(1).fusionValeur));
		 */
		f2.add(new ElementPont((af.get(0).vg == lintAF) ? af.get(0).vd : af.get(0).vg,
				(af.get(1).vg == lintAF) ? af.get(1).vd : af.get(1).vg,
				2 * lintAF + af.get(0).fusionValeur + af.get(1).fusionValeur));

		return f2;
	}

	/*
	 * private List<Pont> getPontsFromElement(List<ElementPont> elementsPont) {
	 * List<Pont> allPont = new ArrayList<>(); List<ElementPont> fp =
	 * elementsPont.stream().filter(e -> e.vg == 0).collect(Collectors.toList());
	 * for (ElementPont fpi : fp) { List<ElementPont> els = new ArrayList<>(
	 * elementsPont.stream().filter(e -> e.id !=
	 * fpi.id).collect(Collectors.toList())); allPont.addAll(new
	 * ArrayList<>(getPontsFromElementBase(fpi, els))); }
	 * 
	 * return allPont; }
	 */
	private Entry<Integer, Pont> getPontsFromElementBase(ElementPont fpi, List<ElementPont> els) {
		if (fpi.vd == 0) {
			fpi.flip();
		}
		Set<Pont> pont = new HashSet<>();
		// System.out.println(els);
		List<ElementPont> ajoutable = new ArrayList<>();
		for (ElementPont e : els) {
			ElementPont ajouter=match(fpi,e);
			if(ajouter != null) {
				ajoutable.add(ajouter);
			}
		}
		for (ElementPont nextE : ajoutable) {
			Pont p = new Pont();
			p.addElement(fpi);
			p.addElement(nextE);
			pont.add(p);
		}
		for(Pont p:pont) {
			System.out.println(p);
		}
		int max = 0;
		Pont ppmax = null;
		HashMap<Boolean, Set<Pont>> npont = new HashMap<>();
		npont.put(true, pont);
		//npont = addNewElement(npont, els);
		while (npont.containsKey(true) ) {
			npont = addNewElement(npont, els);
		}
		for(Pont p:npont.get(false)) {
			if(p.getValeur()>max) {
				max=p.getValeur();
				ppmax=p;
			}
		}
		Map.Entry<Integer, Pont> res;
		res = new AbstractMap.SimpleEntry<>(max, ppmax);
		return res;
	}

	private ElementPont match(ElementPont lastE, ElementPont next) {
		if(!(lastE.vd == next.vd || lastE.vd == next.vg)) {
			return null;
		} else {
			if(lastE.vd==next.vd) {
				return new ElementPont(next.vd,next.vg,next.id);
			}	
			return new ElementPont(next.vg,next.vd,next.id);
		}
	}

	

	private HashMap<Boolean, Set<Pont>> addNewElement(HashMap<Boolean, Set<Pont>> npont, List<ElementPont> els) {
		HashMap<Boolean, Set<Pont>> res = new HashMap<>();
		boolean continuer = false;
		Set<Pont> nset = new HashSet<>();

		for (Pont p : npont.get(true)) {

			List<ElementPont> ajoutable = new ArrayList<>();

			for (ElementPont e : els) {
				if (!p.elements.contains(e)) {
					ElementPont ajouter=match(p.lastE(),e);
					if(ajouter != null) {
						ajoutable.add(ajouter);
					}
				}
			}

			if (!ajoutable.isEmpty() && !continuer) {
				continuer = true;
			}

			for (ElementPont nextE : ajoutable) {
				Pont nnp = new Pont(p);
				nnp.addElement(nextE);
				nset.add(nnp);
			}

		}
		if (continuer) {
			res.put(continuer, nset);
			System.out.println(nset.stream().collect(Collectors.toList()).get(0));
			System.out.println(nset.size());
		} else {
			res.put(false, npont.get(true));
		}

		return res;
	}

	private Set<Pont> clean(Set<Pont> pont, int maxSize) {
		Set<Pont> res = pont.stream().filter(p -> p.getElSize() > maxSize - 2).collect(Collectors.toSet());
		return res;
	}

	public int s2(boolean b) {
		return 0;
	}

	public static class Pont {
		List<ElementPont> elements;

		public List<ElementPont> getElements() {
			return elements;
		}

		public static List<ElementPont> sortedSet(List<ElementPont> elements) {
			List<ElementPont> t = new ArrayList<>();
			for (ElementPont e : elements) {
				if (e.vg < e.vd) {
					t.add(e);
				} else {
					int vt = e.vg;
					e.vg = e.vd;
					e.vd = vt;
					t.add(e);
				}
			}
			List<ElementPont> ne = new ArrayList<>(t);
			ne.sort(Comparator.comparing(ElementPont::getVg).thenComparing(ElementPont::getVd));

			return ne;

		}

		public ElementPont lastE() {
			return elements.get(elements.size() - 1);
		}

		public void setElements(List<ElementPont> elements) {
			this.elements = elements;
		}

		public int getElSize() {
			return elements.size();
		}

		public Pont() {
			super();
			this.elements = new ArrayList<>();
		}

		public Pont(Pont p) {
			super();
			this.elements = new ArrayList<>(p.elements);
		}

		public void addElement(ElementPont e) {
			List<ElementPont> nelements = new ArrayList<>(elements);
			if (!nelements.contains(e)) {
				nelements.add(e);
				setElements(nelements);
			}
		}

		@Override
		public int hashCode() {
			return Objects.hash(elements);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pont other = (Pont) obj;
			return Objects.equals(elements, other.elements);
		}

		public int getValeur() {
			int res = 0;
			for (ElementPont e : elements) {
				res += e.getVT();
			}
			return res;
		}

		@Override
		public String toString() {
			return "Pont [elements=" + elements + "]";
		}

	}

	public static class ElementPont {
		int vg;
		int vd;
		int id;
		int fusionValeur;

		public void flip() {
		int tmp=vg;
		this.setVg(vd);
		this.setVd(tmp);
		}


		public void setId(int id) {
			this.id = id;
		}

		public int getFusionValeur() {
			return fusionValeur;
		}

		public void setFusionValeur(int fusionValeur) {
			this.fusionValeur = fusionValeur;
		}

		public int getId() {
			return id;
		}



		public int getVg() {
			return vg;
		}

		public void setVg(int vg) {
			this.vg = vg;
		}

		public int getVd() {
			return vd;
		}

		public void setVd(int vd) {
			this.vd = vd;
		}

		public int getVT() {
			return getFusionValeur() + vg + vd;
		}

		public ElementPont(int vg, int vd) {
			super();
			this.id = autoid++;
			this.vg = vg;
			this.vd = vd;
		}

		public ElementPont(int vg, int vd, int id) {
			super();
			this.id = id;
			this.vg = vg;
			this.vd = vd;

		}

		@Override
		public int hashCode() {
			return Objects.hash(id);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ElementPont other = (ElementPont) obj;
			return id == other.id;
		}

		@Override
		public String toString() {
			return vg + "/" + vd + "  " + id;
		}

	}

	public static List<Long> getDuration() {
		A2017Day24 d = new A2017Day24(1);
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

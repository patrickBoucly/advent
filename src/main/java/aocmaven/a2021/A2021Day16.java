package aocmaven.a2021;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class A2021Day16 extends A2021 {

	public A2021Day16(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day16 d = new A2021Day16(16);
		long startTime = System.currentTimeMillis();
		System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		// System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	private int s1(boolean b) {

		Transmission t1 = decode(new Transmission(hexToBinary("D2FE28")));
		System.out.println("t1.version :" + t1.version);
		System.out.println("t1.typeID :" + t1.typeID);
		System.out.println("t1.sousPaquet :" + t1.sousPaquet);

		Transmission t2 = decode(new Transmission(hexToBinary("38006F45291200")));
		System.out.println("t2.version :" + t2.version);
		System.out.println("t2.typeID :" + t2.typeID);
		System.out.println("t2.sousPaquet :" + t2.sousPaquet);

		Transmission t3 = new Transmission(hexToBinary("EE00D40C823060"));
		System.out.println("t3.version :" + t3.version);
		System.out.println("t3.typeID :" + t3.typeID);
		System.out.println("t3.sousPaquet :" + t3.sousPaquet);

		// Transmission t4 = new Transmission(hexToBinary("8A004A801A8002F478"));
		// System.out.println("sumVT4 : "+sumVersionSousP(t4));

		// Transmission t5 = new
		// Transmission(hexToBinary("620080001611562C8802118E34"));
		// System.out.println(sumVersionSousP(t5));
		// Transmission t6 = new
		// Transmission(hexToBinary("C0015000016115A2E0802F182340"));
		// System.out.println(sumVersionSousP(t6));
		Transmission t7 = new Transmission(hexToBinary("A0016C880162017C3686B18A3D4780"));

		System.out.println(sumVersionSousP(t7));

		return 0;
	}

	public Transmission decode(Transmission t) {
		if (t.typeID == 4) {
			int nbP = -1;
			String SousPEnd = t.binT.substring(6);
			t.sousPaquet = new ArrayList<>();
			do {
				nbP++;
				Transmission nt = new Transmission(SousPEnd.substring(nbP * 5, (nbP + 1) * 5),"litteral");
				t.sousPaquet.add(nt);

			} while (SousPEnd.substring(nbP * 5, nbP * 5 + 1).equals("1"));
			t.endOfT = "" + SousPEnd.substring((nbP + 1) * 5);
		} else {
			String reste = t.binT.substring(6);
			t.idTypeLongueur = Integer.parseInt(reste.substring(0, 1), 2);
			reste = reste.substring(1);
			int lgSousPaquet;
			if (reste.contains("1")) {
				if (t.idTypeLongueur == 0) {
					lgSousPaquet = Integer.parseInt(reste.substring(0, 15), 2);
					reste = reste.substring(15);
					t.sousPaquet = new ArrayList<>();
					int nbSP = 0;
					while (t.sousPaquet.stream().map(tx -> tx.binT).map(String::length).reduce(0,
							(a, b) -> a + b) < lgSousPaquet) {
						t.sousPaquet.add(decode(new Transmission(reste)));
					}

				} else {
					lgSousPaquet = Integer.parseInt(reste.substring(0, 11), 2);
					t.sousPaquet = new ArrayList<>();
					reste = reste.substring(11);
					int lengthSP = reste.length() / lgSousPaquet;
					for (int j = 0; j < lgSousPaquet; j++) {
						t.sousPaquet.add(new Transmission(reste.substring(0, lengthSP)));
						reste = reste.substring(lengthSP);
					}

				}
			} else {
				t.sousPaquet = null;
				t.endOfT = t.binT.substring(6);
			}
		}
		return t;
	}

	private int sumVersionSousP(Transmission t) {
		int tot = t.version;
		if (t.sousPaquet == null) {
			return 0;
		} else {
			for (Transmission sp : t.sousPaquet) {
				tot += sp.version;
				if (sp.sousPaquet != null) {
					for (Transmission nnt : sp.sousPaquet) {
						tot += sumVersionSousP(nnt);
					}
				}
			}
		}
		return tot;
	}

	private int s2(boolean b) {
		return s(b, 5);
	}

	private int s(boolean b, int rep) {
		return 0;

	}

	public static String hexToBinary(String hexValue) {

		HashMap<String, String> mapping = new HashMap<String, String>();
		mapping.put("0", "0000");
		mapping.put("1", "0001");
		mapping.put("2", "0010");
		mapping.put("3", "0011");
		mapping.put("4", "0100");
		mapping.put("5", "0101");
		mapping.put("6", "0110");
		mapping.put("7", "0111");
		mapping.put("8", "1000");
		mapping.put("9", "1001");
		mapping.put("a", "1010");
		mapping.put("b", "1011");
		mapping.put("c", "1100");
		mapping.put("d", "1101");
		mapping.put("e", "1110");
		mapping.put("f", "1111");

		StringBuilder result = new StringBuilder();
		for (int pos = 0; pos < hexValue.length(); pos++) {
			// need to do some error checking here.
			result.append(mapping.get(hexValue.substring(pos, pos + 1).toLowerCase()));
		}
		return result.toString();
	}

	public static class Transmission {
		String binT;
		Integer version;
		Integer typeID;
		Integer idTypeLongueur;
		String endOfT;
		List<Transmission> sousPaquet;
		String litteralOrOperator;

		@Override
		public String toString() {
			return binT;
		}

		public Transmission(String binTv1) {
			super();
			this.binT = binTv1;
			this.version = Integer.parseInt(binT.substring(0, 3), 2);
			this.typeID = Integer.parseInt(binT.substring(3, 6), 2);
			sousPaquet = new ArrayList<>();
			if (typeID == 4) {
				litteralOrOperator = "litteral";
			} else {
				litteralOrOperator = "operator";
			}
		}

		public Transmission(String binTv1, String p) {
			super();
			this.binT = binTv1;
			this.version = Integer.parseInt(binT.substring(0, 3), 2);
			if (p.equals("litteral")) {
				litteralOrOperator = "litteral";
				typeID=null;
				sousPaquet=null;
			} else {
				this.typeID = Integer.parseInt(binT.substring(3, 6), 2);
				sousPaquet = new ArrayList<>();
				if (typeID == 4) {
					litteralOrOperator = "litteral";
				} else {
					litteralOrOperator = "operator";
				}
			}
		}
	}

	public static List<Long> getDuration() {
		A2021Day16 d = new A2021Day16(16);
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

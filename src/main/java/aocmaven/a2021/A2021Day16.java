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
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	private int s1(boolean b) {
		String s = getInput(b);
		Transmission t = new Transmission(hexToBinary(s));
		t = decode(t);
		return sumVersionSousP(t);
	}

	private Long s2(boolean b) {
		String s = getInput(true);
		return findSum2(s);
	}
	private long findSum2(String hex) {
	    var in = hexToBin(hex.trim());
	    return findPackets2(in, Integer.MAX_VALUE).get(0);
	  }

	  int prevI = 0;
	  private List<Long> findPackets2(String in, int toParse) {
	    List<Long> res = new ArrayList<>();
	    long sum = 0;
	    for(int i = 0, parsed = 0; i< in.length();parsed++){
	      if(parsed >= toParse){
	        break;
	      }
	      if(in.substring(i).chars().allMatch(e -> e == '0')) break;
	      int id = binToDec(in.substring(i+3, i+6));
	      i+=6;
	      if(id == 4){
	        String s = "";
	        for(;;i+=5){
	          s+=in.substring(i+1, i+5);
	          if(in.charAt(i) == '0'){
	            i+=5;
	            break;
	          }
	        }
	        res.add(binToLongDec(s));
	      } else {
	        int lengthLength = 15;
	        boolean b = in.charAt(i) == '1';
	        if(b){
	          lengthLength = 11;
	        }
	        i++;
	        int length = binToDec(in.substring(i, i+lengthLength));
	        i+=lengthLength;
	        List<Long> op = findPackets2(in.substring(i, b ? in.length() : (i + length)), b ? length : Integer.MAX_VALUE);
	        res.add(performOp(op, id));
	        i += b ? prevI : length;
	      }
	      prevI = i;
	    }
	    return res;
	  }
	
	String hexToBin(String s) {
	    return String.format("%"+(s.length()*4)+"s", new BigInteger(s, 16).toString(2)).replace(" ", "0");
	  }

	  int binToDec(String s) {
	    return Integer.parseInt(new BigInteger(s, 2).toString(10));
	  }

	  long binToLongDec(String s) {
	    return Long.parseLong(new BigInteger(s, 2).toString(10));
	  }


	public Transmission decode(Transmission t) {
			if (t.typeID == 4) {
				int nbP = -1;
				String SousPEnd = t.binT.substring(6);
				t.sousPaquet = new ArrayList<>();
				String paquets="";
				do {
					nbP++;
					paquets+=SousPEnd.substring(nbP * 5, (nbP + 1) * 5);
				} while (SousPEnd.substring(nbP * 5, nbP * 5 + 1).equals("1"));
				t.lg = 11 + nbP * 5;
				t.binT = t.binT.substring(0, t.lg);
				t.value=Long.parseLong(paquets, 2);
				return t;
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
						while (t.sousPaquet.stream().map(tx -> tx.binT).map(String::length).reduce(0,
								(a, b) -> a + b) < lgSousPaquet) {
							Transmission nt = decode(new Transmission(reste));
							t.sousPaquet.add(nt);
							reste = reste.substring(nt.lg);
						}

						t.lg = 22 + lgSousPaquet;
						t.binT = t.binT.substring(0, t.lg);
						t.value=calculer(t,t.typeID);
						return t;
					} else {
						lgSousPaquet = Integer.parseInt(reste.substring(0, 11), 2);
						t.sousPaquet = new ArrayList<>();
						reste = reste.substring(11);
						while (t.sousPaquet.size() < lgSousPaquet) {
							Transmission nt = new Transmission(reste);
							nt = decode(nt);
							t.sousPaquet.add(nt);
							reste = reste.substring(nt.lg);
						}
						t.lg = 18 + t.sousPaquet.stream().map(tx -> tx.binT).map(String::length).reduce(0,
								(a, b) -> a + b);
						t.binT = t.binT.substring(0, t.lg);
						t.value=calculer(t,t.typeID);
						return t;
					}
				}
		}
		return t;
	}
	public Long calculer(Transmission t,int idt) {
		return performOp(t.sousPaquet.stream().map(Transmission::getValue).collect(Collectors.toList()) ,idt);
	}

	public Long performOp(List<Long> op,int idt) {
		if (idt == 0) {
			return op.stream().mapToLong(e -> e).sum();
		} else if (idt == 1) {
			return op.stream().mapToLong(e -> e).reduce((a,b) -> a*b).getAsLong();
		} else if (idt == 2) {
			return op.stream().mapToLong(e -> e).min().getAsLong();
		} else if (idt == 3) {
			return op.stream().mapToLong(e -> e).max().getAsLong();
		} else if (idt == 5) {
				return op.get(0) > op.get(1) ? 1L : 0L;
		} else if (idt == 6) {
				return op.get(0) < op.get(1) ? 1L : 0L;
		} else if (idt == 7) {
				return op.get(0).equals(op.get(1)) ? 1L : 0L;
		}
		return null;
	}
	

	private int sumVersionSousP(Transmission t) {
		int tot = t.version;
		if (t.litteralOrOperator.equals("operator")) {
			for (Transmission sp : t.sousPaquet) {
				tot += getSum(sp);
			}
		} else {
			tot += t.version;
		}
		return tot;
	}

	private int getSum(Transmission t) {
		int tot = 0;
		if (t.litteralOrOperator.equals("operator")) {
			for (Transmission sp : t.sousPaquet) {
				tot += getSum(sp);
			}
		}
		tot += t.version;

		return tot;
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
		int lg;
		long value;

		public long getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			String res="";
			res+=binT;
			res+="\n";
			res+=value;
			return res;
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
			lg = binTv1.length();

		}

		public Transmission(String binTv1, String p) {
			super();
			this.binT = binTv1;
			this.version = Integer.parseInt(binT.substring(0, 3), 2);
			lg = binTv1.length();
			if (p.equals("litteral")) {
				litteralOrOperator = "litteral";
				typeID = null;
				sousPaquet = null;
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

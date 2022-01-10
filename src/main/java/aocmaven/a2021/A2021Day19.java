package aocmaven.a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class A2021Day19 extends A2021 {

	public A2021Day19(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day19 d = new A2021Day19(19);
		long startTime = System.currentTimeMillis();
		//System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	private int s1(boolean b) {
		return s0(b).detected.size();
	}

	private long s2(boolean b) {
		Game g = s0(b);
		long maxmanat = 0;
		for (Scanner s1 : g.scans) {

			for (Scanner s2 : g.scans) {
				long dist= manat(s1,s2);
				if(dist>maxmanat) {
					maxmanat=dist;
				}
			}
		}
		return maxmanat;
	}

	private long manat(Scanner s1, Scanner s2) {
		return (Math.abs(s1.poseScan.x-s2.poseScan.x)+Math.abs(s1.poseScan.y-s2.poseScan.y)+Math.abs(s1.poseScan.z-s2.poseScan.z));
		
	}

	private Game s0(boolean b) {
		Game g = getGame(b);
		g.detected.addAll(g.scans.get(0).balisesDetectee);
		System.out.println(g.detected);
		while (g.scans.stream().anyMatch(sc -> sc.poseScan == null)) {
			g.findNextScan();
			System.out.println(g.scans.stream().filter(s -> s.poseScan != null).count());
			System.out.println("nb balise detectees :" + g.detected.size());
		}
		return g;
	}

	private Game getGame(boolean b) {
		Game g = new Game();
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Scanner> scans = new ArrayList<>();
		List<Scanner> scansClean = new ArrayList<>();
		Set<Balise> detected = new HashSet<>();
		Scanner sc = null;
		List<Balise> lb = null;
		for (String l : lignes) {
			if (!l.isEmpty() && l.substring(0, 2).equals("--")) {
				String[] sp = l.split(" ");
				sc = new Scanner(Integer.parseInt(sp[2].trim()));
				if (Integer.parseInt(sp[2].trim()) == 0) {
					sc.setPoseScan(new Position(0, 0, 0));
				}
				lb = new ArrayList<>();
			} else if (!l.isEmpty() && !l.substring(0, 1).equals("")) {
				String[] sp = l.split(",");
				lb.add(new Balise(new Position(Integer.parseInt(sp[0].trim()), Integer.parseInt(sp[1].trim()),
						Integer.parseInt(sp[2].trim()))));
			} else {
				sc.setBalisesDetectee(lb);
				scans.add(sc);
			}
		}
		sc.setBalisesDetectee(lb);
		scans.add(sc);
		scansClean.add(scans.get(0));
		g.setDetected(detected);
		g.setScans(scans);
		g.setScansClean(scansClean);
		return g;
	}

	private static Scanner clean(Scanner scanP) {
		return changementBase(scanP);
	}

	private static Scanner reorientation(Scanner scanP) {
		List<Position> newPos = new ArrayList<>();
		List<Balise> newBal = new ArrayList<>();
		System.out.println(scanP);
		System.out.println(scanP.balisesDetectee.stream().map(Balise::t13).collect(Collectors.toList()));
		switch (scanP.permutation) {
		case "0":
			return scanP;
		case "1":
			newBal = scanP.balisesDetectee.stream().map(Balise::t1).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "2":
			newBal = scanP.balisesDetectee.stream().map(Balise::t2).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "3":
			newBal = scanP.balisesDetectee.stream().map(Balise::t3).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "4":
			newBal = scanP.balisesDetectee.stream().map(Balise::t4).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "5":
			newBal = scanP.balisesDetectee.stream().map(Balise::t7).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "6":
			newBal = scanP.balisesDetectee.stream().map(Balise::t6).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "7":
			newBal = scanP.balisesDetectee.stream().map(Balise::t5).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "8":
			newBal = scanP.balisesDetectee.stream().map(Balise::t12).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "9":
			newBal = scanP.balisesDetectee.stream().map(Balise::t11).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "10":
			newBal = scanP.balisesDetectee.stream().map(Balise::t10).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "11":
			newBal = scanP.balisesDetectee.stream().map(Balise::t9).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "12":
			newBal = scanP.balisesDetectee.stream().map(Balise::t8).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "13":
			newBal = scanP.balisesDetectee.stream().map(Balise::t13).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "14":
			newBal = scanP.balisesDetectee.stream().map(Balise::t15).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "15":
			newBal = scanP.balisesDetectee.stream().map(Balise::t14).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "16":
			newBal = scanP.balisesDetectee.stream().map(Balise::t16).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "17":
			newBal = scanP.balisesDetectee.stream().map(Balise::t19).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "18":
			newBal = scanP.balisesDetectee.stream().map(Balise::t18).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "19":
			newBal = scanP.balisesDetectee.stream().map(Balise::t17).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "20":
			newBal = scanP.balisesDetectee.stream().map(Balise::t20).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "21":
			newBal = scanP.balisesDetectee.stream().map(Balise::t24).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "22":
			newBal = scanP.balisesDetectee.stream().map(Balise::t22).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "23":
			newBal = scanP.balisesDetectee.stream().map(Balise::t23).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "24":
			newBal = scanP.balisesDetectee.stream().map(Balise::t21).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "25":
			newBal = scanP.balisesDetectee.stream().map(Balise::t26).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "26":
			newBal = scanP.balisesDetectee.stream().map(Balise::t25).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "27":
			newBal = scanP.balisesDetectee.stream().map(Balise::t28).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "28":
			newBal = scanP.balisesDetectee.stream().map(Balise::t27).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "29":
			newBal = scanP.balisesDetectee.stream().map(Balise::t29).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");
		case "30":
			newBal = scanP.balisesDetectee.stream().map(Balise::t30).collect(Collectors.toList());
			return new Scanner(scanP.num, scanP.poseScan, newBal, "0");

		}

		return null;
	}

	private static Scanner changementBase(Scanner scanP) {
		List<Position> newPos = new ArrayList<>();
		List<Balise> newBal = new ArrayList<>();
		newPos = scanP.balisesDetectee.stream().map(b -> b.poseBal.plus(scanP.poseScan)).collect(Collectors.toList());
		for (Position p : newPos) {
			newBal.add(new Balise(p));
		}
		return new Scanner(scanP.num, scanP.poseScan, newBal);
	}

	private static HashMap<Position, Set<Balise>> getGoodPos(Scanner scanner, Scanner scanP,
			List<Position> possiblePosDep) {
		HashMap<Position, Set<Balise>> res = new HashMap<>();
		Set<Balise> lb = new HashSet<>();
		for (Position posinit : possiblePosDep) {
			lb = new HashSet<>();
			for (Balise b0 : scanner.balisesDetectee) {
				for (Balise bx : scanP.balisesDetectee) {
					if (bx.poseBal.equals(b0.poseBal.moins(posinit))) {
						lb.add(bx);
					}
				}
				if (lb.size() == 12) {
					res.put(posinit, lb);
					return res;

				}
			}
		}
		return res;
	}

	

	public static class Bilan {
		Scanner sc1;
		Scanner sc2;
		int nbMatch;
		String transfMax;

		public Bilan(Scanner sc1, Scanner sc2) {
			super();
			this.sc1 = sc1;
			this.sc2 = sc2;
		}

		public Scanner getSc1() {
			return sc1;
		}

		public void setSc1(Scanner sc1) {
			this.sc1 = sc1;
		}

		public Scanner getSc2() {
			return sc2;
		}

		public void setSc2(Scanner sc2) {
			this.sc2 = sc2;
		}

		public int getNbMatch() {
			return nbMatch;
		}

		public void setNbMatch(int nbMatch) {
			this.nbMatch = nbMatch;
		}

		public String getTransfMax() {
			return transfMax;
		}

		public void setTransfMax(String transfMax) {
			this.transfMax = transfMax;
		}

	}

	public static class Game {
		List<Scanner> scans;
		List<Scanner> scansClean = new ArrayList<>();
		Set<Balise> detected = new HashSet<>();

		public List<Scanner> getScans() {
			return scans;
		}

		public void findNextScan() {
			boolean stop = false;
			for (Scanner scan : scans) {
				if (scan.poseScan != null) {
					for (Scanner scan2 : scans) {
						if (scan2.poseScan == null && scan.num != scan2.num && !stop) {
							// System.out.println("source :" + scan.num + " scanne recherché :" +
							// scan2.num);
							for (Scanner scanP : scan2.allPermutation()) {
								if (!stop) {
									List<Position> possiblePosDep = new ArrayList<>();
									for (Balise bal2 : scanP.balisesDetectee) {
										for (Balise bal1 : scan.balisesDetectee) {
											possiblePosDep.add(bal1.poseBal.moins(bal2.poseBal));
										}
									}
									HashMap<Position, Set<Balise>> goodPos = getGoodPos(scan, scanP, possiblePosDep);
									if (!goodPos.keySet().isEmpty()) {
										Position gp = goodPos.keySet().stream().findFirst().get();
										scanP.setPoseScan(gp);
										for (Scanner s : scans) {
											if (s.num == scanP.num) {
												s.setPoseScan(gp);
												scanP.setPoseScan(gp);
												// Scanner sReor=reorientation(scanP);
												s.setBalisesDetectee(changementBase(scanP).balisesDetectee);
												s.setPermutation(scanP.permutation);
												detected.addAll(s.getBalisesDetectee());
												stop = true;
												break;
											}
										}
									}
								}
							}
						}
					}
				}
			}

		}

		private Set<Balise> cleanBalise(Set<Balise> b, Scanner s) {
			Scanner sc = new Scanner(s.num, s.poseScan, new ArrayList<>(b), s.permutation);
			// sc=reorientation(sc);
			sc = changementBase(sc);
			return sc.balisesDetectee.stream().collect(Collectors.toSet());
		}

		public void setScans(List<Scanner> scans) {
			this.scans = scans;
		}

		public List<Scanner> getScansClean() {
			return scansClean;
		}

		public void setScansClean(List<Scanner> scansClean) {
			this.scansClean = scansClean;
		}

		public Set<Balise> getDetected() {
			return detected;
		}

		public void setDetected(Set<Balise> detected) {
			this.detected = detected;
		}

		public Game(List<Scanner> scans, List<Scanner> scansClean, Set<Balise> detected) {
			super();
			this.scans = scans;
			this.scansClean = scansClean;
			this.detected = detected;
		}

		public Game() {
			super();
		}

	}

	public static class Scanner {
		int num;
		Position poseScan;
		List<Balise> balisesDetectee;
		String permutation;

		public Scanner(int num, Position poseScan, List<Balise> balisesDetectee, String permutation) {
			super();
			this.num = num;
			this.poseScan = poseScan;
			this.balisesDetectee = balisesDetectee;
			this.permutation = permutation;
		}

		public String getPermutation() {
			return permutation;
		}

		public void setPermutation(String permutation) {
			this.permutation = permutation;
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public Position getPoseScan() {
			return poseScan;
		}

		public void setPoseScan(Position poseScan) {
			this.poseScan = poseScan;
		}

		public List<Balise> getBalisesDetectee() {
			return balisesDetectee;
		}

		public void setBalisesDetectee(List<Balise> balisesDetectee) {
			this.balisesDetectee = balisesDetectee;
		}

		public Scanner(int num, Position poseScan, List<Balise> balisesDetectee) {
			super();
			this.num = num;
			this.poseScan = poseScan;
			this.balisesDetectee = balisesDetectee;
		}

		public Scanner(int num) {
			this.num = num;
		}

		public List<Scanner> allPermutation() {
			List<Scanner> allPermut = new ArrayList<>();
			List<Scanner> allPermutMax = new ArrayList<>();
			setPermutation("0");
			allPermut.add(this);
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t1).collect(Collectors.toList()), "1"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t2).collect(Collectors.toList()), "2"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t3).collect(Collectors.toList()), "3"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t4).collect(Collectors.toList()), "4"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t5).collect(Collectors.toList()), "5"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t6).collect(Collectors.toList()), "6"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t7).collect(Collectors.toList()), "7"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t8).collect(Collectors.toList()), "8"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t9).collect(Collectors.toList()), "9"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t10).collect(Collectors.toList()), "10"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t11).collect(Collectors.toList()), "11"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t12).collect(Collectors.toList()), "12"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t13).collect(Collectors.toList()), "13"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t14).collect(Collectors.toList()), "14"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t15).collect(Collectors.toList()), "15"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t16).collect(Collectors.toList()), "16"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t17).collect(Collectors.toList()), "17"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t18).collect(Collectors.toList()), "18"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t19).collect(Collectors.toList()), "19"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t20).collect(Collectors.toList()), "20"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t21).collect(Collectors.toList()), "21"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t22).collect(Collectors.toList()), "22"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t23).collect(Collectors.toList()), "23"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t24).collect(Collectors.toList()), "24"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t25).collect(Collectors.toList()), "25"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t26).collect(Collectors.toList()), "26"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t27).collect(Collectors.toList()), "27"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t28).collect(Collectors.toList()), "28"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t29).collect(Collectors.toList()), "29"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t30).collect(Collectors.toList()), "30"));
			allPermut.add(new Scanner(num, poseScan,
					balisesDetectee.stream().map(Balise::t31).collect(Collectors.toList()), "31"));
			for (Scanner s : allPermut) {
				allPermutMax.add(s);
				allPermutMax.add(intervXY(s));
				allPermutMax.add(intervXZ(s));
				allPermutMax.add(intervYZ(s));
			}
			return allPermutMax;
		}

		private Scanner intervYZ(Scanner s) {
			return new Scanner(s.num, s.poseScan,
					s.balisesDetectee.stream().map(Balise::intervYZ).collect(Collectors.toList()));
		}

		private Scanner intervXZ(Scanner s) {
			return new Scanner(s.num, s.poseScan,
					s.balisesDetectee.stream().map(Balise::intervXZ).collect(Collectors.toList()));
		}

		private Scanner intervXY(Scanner s) {
			return new Scanner(s.num, s.poseScan,
					s.balisesDetectee.stream().map(Balise::intervXY).collect(Collectors.toList()));
		}

		@Override
		public String toString() {
			return "Scanner [num=" + num + ", poseScan=" + poseScan + ", balisesDetectee=" + balisesDetectee
					+ ", permutation=" + permutation + "]";
		}

	}

	public static class Balise {
		Position poseBal;

		public Position getPoseBal() {
			return poseBal;
		}

		@Override
		public String toString() {
			return "Balise [poseBal=" + poseBal + "]";
		}

		public void setPoseBal(Position poseBal) {
			this.poseBal = poseBal;
		}

		public Balise t1() {
			return new Balise(new Position(poseBal.x, poseBal.y, -poseBal.z));
		}

		public Balise t2() {
			return new Balise(new Position(poseBal.x, -poseBal.y, poseBal.z));
		}

		public Balise t3() {
			return new Balise(new Position(-poseBal.x, poseBal.y, poseBal.z));
		}

		public Balise t4() {
			return new Balise(new Position(poseBal.y, poseBal.x, poseBal.z));
		}

		public Balise t5() {
			return new Balise(new Position(poseBal.x, -poseBal.z, poseBal.y));
		}

		public Balise t6() {
			return new Balise(new Position(poseBal.x, -poseBal.z, -poseBal.y));
		}

		public Balise t7() {
			return new Balise(new Position(poseBal.x, poseBal.z, -poseBal.y));
		}

		public Balise t8() {
			return new Balise(new Position(-poseBal.z, poseBal.y, poseBal.x));
		}

		public Balise t9() {
			return new Balise(new Position(-poseBal.x, -poseBal.z, poseBal.y));
		}

		public Balise t10() {
			return new Balise(new Position(-poseBal.x, -poseBal.z, -poseBal.y));
		}

		public Balise t11() {
			return new Balise(new Position(-poseBal.x, poseBal.z, -poseBal.y));
		}

		public Balise t12() {
			return new Balise(new Position(poseBal.z, poseBal.y, -poseBal.x));
		}

		public Balise t13() {
			return new Balise(new Position(-poseBal.x, poseBal.y, -poseBal.z));
		}

		public Balise t14() {
			return new Balise(new Position(-poseBal.z, -poseBal.y, poseBal.x));
		}

		public Balise t15() {
			return new Balise(new Position(poseBal.z, -poseBal.y, -poseBal.x));
		}

		public Balise t16() {
			return new Balise(new Position(-poseBal.x, -poseBal.y, -poseBal.z));
		}

		public Balise t17() {
			return new Balise(new Position(-poseBal.y, poseBal.x, poseBal.z));
		}

		public Balise t18() {
			return new Balise(new Position(-poseBal.x, -poseBal.y, poseBal.z));
		}

		public Balise t19() {
			return new Balise(new Position(poseBal.y, -poseBal.x, poseBal.z));
		}

		public Balise t20() {
			return new Balise(new Position(-poseBal.y, -poseBal.x, poseBal.z));
		}

		public Balise t21() {
			return new Balise(new Position(-poseBal.y, -poseBal.z, poseBal.x));
		}

		public Balise t22() {
			return new Balise(new Position(-poseBal.z, poseBal.y, -poseBal.x));
		}

		public Balise t23() {
			return new Balise(new Position(poseBal.z, poseBal.y, poseBal.x));
		}

		public Balise t24() {
			return new Balise(new Position(poseBal.z, -poseBal.x, -poseBal.y));
		}

		public Balise t25() {
			return new Balise(new Position(poseBal.z, poseBal.x, poseBal.y));
		}

		public Balise t26() {
			return new Balise(new Position(poseBal.y, poseBal.z, poseBal.x));
		}

		public Balise t27() {
			return new Balise(new Position(-poseBal.z, poseBal.x, poseBal.y));
		}

		public Balise t28() {
			return new Balise(new Position(poseBal.y, poseBal.z, -poseBal.x));
		}

		public Balise t29() {
			return new Balise(new Position(poseBal.y, -poseBal.z, -poseBal.x));
		}

		public Balise t30() {
			return new Balise(new Position(-poseBal.y, -poseBal.z, -poseBal.x));
		}

		public Balise t31() {
			return new Balise(new Position(-poseBal.y, poseBal.z, poseBal.x));
		}

		public static Balise intervYZ(Balise b) {
			return new Balise(new Position(b.poseBal.x, b.poseBal.z, b.poseBal.y));
		}

		public static Balise intervXZ(Balise b) {
			return new Balise(new Position(b.poseBal.z, b.poseBal.y, b.poseBal.x));
		}

		public static Balise intervXY(Balise b) {
			return new Balise(new Position(b.poseBal.z, b.poseBal.y, b.poseBal.x));
		}

		public Balise(Position poseBal) {
			super();
			this.poseBal = poseBal;
		}

		public Balise(int i, int j, int k) {
			super();
			this.poseBal = new Position(i, j, k);
		}

		@Override
		public int hashCode() {
			return Objects.hash(poseBal);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Balise other = (Balise) obj;
			return Objects.equals(poseBal, other.poseBal);
		}

	}

	public static class Position {
		int x;
		int y;
		int z;

		public Position(int x, int y, int z) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public Position plus(Position p) {
			return new Position(x + p.x, y + p.y, z + p.z);
		}

		public Position moins(Position p) {
			return new Position(x - p.x, y - p.y, z - p.z);
		}

		@Override
		public String toString() {
			return "[" + x + "," + y + "," + z + "]";
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y, z);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
			return x == other.x && y == other.y && z == other.z;
		}

	}

	public static List<Long> getDuration() {
		A2021Day19 d = new A2021Day19(19);
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

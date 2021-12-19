package aocmaven.a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A2021Day19 extends A2021 {

	public A2021Day19(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day19 d = new A2021Day19(19);
		long startTime = System.currentTimeMillis();
		System.out.println(d.s1(false));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		// System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	private int s1(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Scanner> scans = new ArrayList<>();
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
		List<Integer> sumabsSc0 = new ArrayList<Integer>();
		for (Scanner scan : scans) {
			if (scan.num == 0) {
				sumabsSc0 = scan.balisesDetectee.stream().map(Balise::getPoseBal).map(Position::sumabs)
						.collect(Collectors.toList());
			}
		}
		for (Scanner scan : scans) {
			if (scan.num != 0) {
				int pos=0;
				for (Scanner scanP : scan.allPermutation()) {
				List<Integer> common = new ArrayList<Integer>(sumabsSc0);
				List<Integer> sumabsSc = scanP.balisesDetectee.stream().map(Balise::getPoseBal).map(Position::sumabs)
						.collect(Collectors.toList());
				common.retainAll(sumabsSc);
				System.out.println(scanP.num + ","+"pos  "+pos+" common size  :" + common.size());
				pos++;
				}
			}
		}
		System.out.println(sumabsSc0);

		List<Bilan> bilans = new ArrayList<>();
		for (Scanner scan1 : scans) {
			for (Scanner scan2 : scans) {
				if (scan1.num != scan2.num) {

				}
				// bilans.add(new Bilan(scan,scan2));

			}
		}

		return 0;

	}

	private int s2(boolean b) {
		return 0;
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
			this.nbMatch = getnbMatch(sc1, sc2);
			this.transfMax = gettransfMax(sc1, sc2);
		}

		private String gettransfMax(Scanner sc12, Scanner sc22) {
			// TODO Auto-generated method stub
			return null;
		}

		private int getnbMatch(Scanner sc12, Scanner sc22) {
			// TODO Auto-generated method stub
			return 0;
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

	public static class Scanner {
		int num;
		Position poseScan;
		List<Balise> balisesDetectee;

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
			allPermut.add(this);
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t1).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t2).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t3).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t4).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t5).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t6).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t7).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t8).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t9).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t10).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t11).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t12).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t13).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t14).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t15).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t16).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t17).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t18).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t19).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t20).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t21).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t22).collect(Collectors.toList())));
			allPermut.add(
					new Scanner(num, poseScan, balisesDetectee.stream().map(Balise::t23).collect(Collectors.toList())));
			return allPermut;
		}

		@Override
		public String toString() {
			return "Scanner [num=" + num + ", poseScan=" + poseScan + ", balisesDetectee=" + balisesDetectee + "]";
		}

	}

	public static class Balise {
		Position poseBal;

		public Position getPoseBal() {
			return poseBal;
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
			return new Balise(new Position(poseBal.x, -poseBal.y, -poseBal.z));
		}

		public Balise t5() {
			return new Balise(new Position(-poseBal.x, poseBal.y, -poseBal.z));
		}

		public Balise t6() {
			return new Balise(new Position(-poseBal.x, -poseBal.y, poseBal.z));
		}

		public Balise t7() {
			return new Balise(new Position(-poseBal.x, -poseBal.y, -poseBal.z));
		}

		public Balise t8() {
			return new Balise(intervYZ(this.poseBal));
		}

		public Balise t9() {
			return new Balise(intervYZ(t1().poseBal));
		}

		public Balise t10() {
			return new Balise(intervYZ(t2().poseBal));
		}

		public Balise t11() {
			return new Balise(intervYZ(t3().poseBal));
		}

		public Balise t12() {
			return new Balise(intervYZ(t4().poseBal));
		}

		public Balise t13() {
			return new Balise(intervYZ(t5().poseBal));
		}

		public Balise t14() {
			return new Balise(intervYZ(t6().poseBal));
		}

		public Balise t15() {
			return new Balise(intervYZ(t7().poseBal));
		}

		public Balise t16() {
			return new Balise(intervXZ(this.poseBal));
		}

		public Balise t17() {
			return new Balise(intervXZ(t1().poseBal));
		}

		public Balise t18() {
			return new Balise(intervXZ(t2().poseBal));
		}

		public Balise t19() {
			return new Balise(intervXZ(t3().poseBal));
		}

		public Balise t20() {
			return new Balise(intervXZ(t4().poseBal));
		}

		public Balise t21() {
			return new Balise(intervXZ(t5().poseBal));
		}

		public Balise t22() {
			return new Balise(intervXZ(t6().poseBal));
		}

		public Balise t23() {
			return new Balise(intervXZ(t7().poseBal));
		}

		public Position intervYZ(Position p) {
			return new Position(p.x, p.z, p.y);
		}

		public Position intervXZ(Position p) {
			return new Position(p.z, p.y, p.x);
		}

		public Balise(Position poseBal) {
			super();
			this.poseBal = poseBal;
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

		public int sumabs() {
			return Math.abs(x) + Math.abs(y) + Math.abs(z);
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

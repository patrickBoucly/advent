package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A2017Day17 extends A2017 {

	public A2017Day17(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day17 d = new A2017Day17(1);
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

	public Long s1(boolean b) {
		int input = 371;
		int inpu = 3;
		List<Long> l = new ArrayList<>();
		l.add(0L);
		int pos = 0;
		Sit s = new Sit(l, pos, input);
		for (int i = 0; i < 10000; i++) {
			s.spinLock();
			System.out.println(s.l);
		}
		System.out.println(s.l);
		return s.l.get(s.pos + 1);
	}

	public static class Sit {
		List<Long> l;
		int pos;
		int step;
		long cpt;
		int ls;

		@Override
		public String toString() {
			String res = "Sit [pos=" + pos + ", step=" + step + "]";
			for (int j = 0; j < l.size(); j++) {
				if (j == pos) {
					res += " (" + l.get(j) + ") ";
				} else {
					res += " " + l.get(j) + " ";
				}
			}
			return res;
		}

		public int getStep() {
			return step;
		}

		public void setStep(int step) {
			this.step = step;
		}

		public List<Long> getL() {
			return l;
		}

		public void spinLock() {
			cpt++;
			pos = (pos + step) % l.size();
			l.add(pos + 1, cpt);
			pos++;

		}

		public void spinLock2() {

			cpt++;
			if((pos + step) % ls==0) {
				l.add(cpt);
			}
			pos = (pos + step) % ls;
			ls++;
			pos++;

		}

		public void setL(List<Long> l) {
			this.l = l;
		}

		public int getPos() {
			return pos;
		}

		public void setPos(int pos) {
			this.pos = pos;
		}

		public Sit(List<Long> l, int pos, int step) {
			super();
			this.l = l;
			this.pos = pos;
			this.step = step;
			this.cpt = 0;
			this.ls = 1;
		}

	}
//1222153 low
	public Long s2(boolean b) {
		int input = 371;
		int inpu = 3;
		List<Long> l = new ArrayList<>();
		l.add(0L);
		int pos = 0;
		Sit s = new Sit(l, pos, input);
		for (int i = 0; i < 50000000; i++) {
			s.spinLock2();
		}
		return s.l.get(s.l.size()-1);
	}

	public static List<Long> getDuration() {
		A2017Day17 d = new A2017Day17(1);
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

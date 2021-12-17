package aocmaven.a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class A2021Day17 extends A2021 {

	public A2021Day17(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day17 d = new A2021Day17(17);
		long startTime = System.currentTimeMillis();
	//	System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		 System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	private int s1(boolean b) {
		Area a = new Area(b);
		List<Probe> probes = new ArrayList<>();
		for (int x = -50; x < 300; x++) {
			for (int y = a.y1; y < 300; y++) {
				Stack<Position> nps = new Stack<>();
				nps.add(new Position(0, 0));
				probes.add(new Probe(x, y, nps));
			}
		}
		for (Probe p : probes) {
			while (a.analyze(p) == 0) {
				p=p.nextStep();
			}
			if (a.analyze(p) == 1) {
				System.out.println(p.position.get(1));
			}
		}
		
		Stack<Position> nps = new Stack<>();
		nps.add(new Position(0, 0));
		Probe t= new Probe(6,9,nps);
		while (a.analyze(t) == 0) {
			t=t.nextStep();
		}
		if (a.analyze(t) == 1) {
			System.out.println(t.position.get(1));
		}

		List<Probe> winingsProbe = probes.stream().filter(p -> a.analyze(p) == 1).collect(Collectors.toList());
		int res=0;
		Probe wp=null;
		System.out.println(t.position);
		for(Probe p:winingsProbe) {
			for(Position pos:p.position) {
				if(pos.y>res) {
					res=pos.y;
					wp=p;
				}
			}
		}
		System.out.println(res);
		System.out.println(wp.position.get(1));

		return res;
	}

	private int s2(boolean b) {
		Area a = new Area(b);
		List<Probe> probes = new ArrayList<>();
		for (int x = -50; x < 300; x++) {
			for (int y = a.y1; y < 300; y++) {
				Stack<Position> nps = new Stack<>();
				nps.add(new Position(0, 0));
				probes.add(new Probe(x, y, nps));
			}
		}
		for (Probe p : probes) {
			while (a.analyze(p) == 0) {
				p=p.nextStep();
			}
			if (a.analyze(p) == 1) {
				System.out.println(p.position.get(1));
			}
		}
		
		Stack<Position> nps = new Stack<>();
		nps.add(new Position(0, 0));
		Probe t= new Probe(6,9,nps);
		while (a.analyze(t) == 0) {
			t=t.nextStep();
		}
		if (a.analyze(t) == 1) {
			System.out.println(t.position.get(1));
		}

		List<Probe> winingsProbe = probes.stream().filter(p -> a.analyze(p) == 1).collect(Collectors.toList());
		
		return winingsProbe.size();
	}

	public static class Probe {
		int vx;
		int vy;
		Stack<Position> position = new Stack<>();

		@Override
		public String toString() {
			return "Probe [vx=" + vx + ", vy=" + vy + ", Lastposition=" + position.peek() + "]";
		}

		public Probe(int vx, int vy, Stack<Position> p) {
			super();
			this.vx = vx;
			this.vy = vy;
			Position p0 = new Position(0, 0);
			this.position = p;
		}

		public int getVx() {
			return vx;
		}

		public void setVx(int vx) {
			this.vx = vx;
		}

		public int getVy() {
			return vy;
		}

		public void setVy(int vy) {
			this.vy = vy;
		}

		public Stack<Position> getPosition() {
			return position;
		}

		public void setPosition(Stack<Position> position) {
			this.position = position;
		}

		public Position getNextPosition(Probe p) {
			Position lastP = p.position.peek();
			return new Position(lastP.x + p.vx, lastP.y + vy);
		}

		public Probe nextStep() {
			int drag=0;
			this.position.add(getNextPosition(this));
			if (vx > 0) {
				drag = -1;
			} else if(vx<0) {
				drag = +1;
			}
			setVx(vx + drag);
			setVy(vy - 1);
			return this;
		}

	}

	public static class Position {
		int x;
		int y;

		public Position(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		@Override
		public String toString() {
			return "Position [x=" + x + ", y=" + y + "]";
		}

	}

	public static class Area {
		private int x1;
		private int x2;
		private int y1;
		private int y2;

		public int getX1() {
			return x1;
		}

		public void setX1(int x1) {
			this.x1 = x1;
		}

		public int getX2() {
			return x2;
		}

		public void setX2(int x2) {
			this.x2 = x2;
		}

		public int getY1() {
			return y1;
		}

		public void setY1(int y1) {
			this.y1 = y1;
		}

		public int getY2() {
			return y2;
		}

		public void setY2(int y2) {
			this.y2 = y2;
		}

		public int analyze(Probe p) {
			Position lastP = p.position.peek();
			int res = 0;
			if (getX1() <= lastP.x && getX2() >= lastP.x && getY1() <= lastP.y && getY2() >= lastP.y) {
				res = 1;
			} else if (lastP.x > getX2() || lastP.y < getY1()) {
				res = -1;
			}
			return res;
		}

		public Area(boolean b) {
			super();
			if (!b) {
				this.x1 = 20;
				this.x2 = 30;
				this.y1 = -10;
				this.y2 = -5;
			} else {
				this.x1 = 60;
				this.x2 = 94;
				this.y1 = -171;
				this.y2 = -136;
			}
		}

	}

	public static List<Long> getDuration() {
		A2021Day17 d = new A2021Day17(17);
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

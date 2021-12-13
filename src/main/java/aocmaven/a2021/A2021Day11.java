package aocmaven.a2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class A2021Day11 extends A2021 {

	public A2021Day11(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day11 d = new A2021Day11(11);
		System.out.println(d.s1(true));
		System.out.println(d.s2(true));
	}

	private int s2(boolean c) {
		List<String> lignes = getLignes(c);
		List<Poulpe> poulpes = new ArrayList<>();
		int y = 0;
		int id = 0;
		for (String l : lignes) {
			for (int pos = 0; pos < l.length(); pos++) {
				poulpes.add(new Poulpe(id++, pos, y, Integer.parseInt(l.substring(pos, pos + 1))));
			}
			y++;
		}
		Jeu j = new Jeu(poulpes);
		for (int g = 0; g < 1000; g++) {
			j.nextStep();
			if (j.poulpes.stream().allMatch(p -> p.nrj == 0)) {
				return j.step;
			}
		}
		return 0;
	}

	private long s1(boolean c) {
		List<String> lignes = getLignes(c);
		List<Poulpe> poulpes = new ArrayList<>();
		int y = 0;
		int id = 0;
		for (String l : lignes) {
			for (int pos = 0; pos < l.length(); pos++) {
				poulpes.add(new Poulpe(id++, pos, y, Integer.parseInt(l.substring(pos, pos + 1))));
			}
			y++;
		}
		Jeu j = new Jeu(poulpes);
		for (int g = 0; g < 100; g++) {
			j.nextStep();
		}
		return (j.nbFlash);
	}

	public static class Jeu {
		List<Poulpe> poulpes;
		int step;
		long nbFlash;

		public Jeu(List<Poulpe> poulpes) {
			super();
			this.poulpes = poulpes;
			this.step = 0;
			this.nbFlash = 0;
		}

		public void nextStep() {
			setStep(getStep() + 1);
			poulpes = poulpes.stream().map(Poulpe::nrjUp).collect(Collectors.toList());
			while (!poulpes.stream().filter(p -> p.nrj > 9 && !p.asFlashed).findAny().isEmpty()) {
				Poulpe poulpe = poulpes.stream().filter(p -> p.nrj > 9 && !p.asFlashed).findFirst().get();
				poulpes = poulpe.flash(poulpes);
				nbFlash++;
			}
			poulpes = poulpes.stream().map(Poulpe::notFlashed).collect(Collectors.toList());
		}

		public List<Poulpe> getPoulpes() {
			return poulpes;
		}

		public void setPoulpes(List<Poulpe> poulpes) {
			this.poulpes = poulpes;
		}

		public int getStep() {
			return step;
		}

		public void setStep(int step) {
			this.step = step;
		}

		public long getNbFlash() {
			return nbFlash;
		}

		public void setNbFlash(long nbFlash) {
			this.nbFlash = nbFlash;
		}

		@Override
		public String toString() {
			String res = "Step " + step;
			res += "\n";
			for (int i = 1; i <= 100; i++) {
				if (i % 10 == 0) {
					res += String.valueOf(poulpes.get(i - 1).nrj) + " \n";
				} else {
					res += String.valueOf(poulpes.get(i - 1).nrj) + " ";
				}
			}
			return res;
		}

	}

	public static class Poulpe {
		int id;
		int x;
		int y;
		int nrj;
		boolean asFlashed;

		@Override
		public String toString() {
			return "Poulpe [x=" + x + ", y=" + y + ", nrj=" + nrj + ", asFlashed=" + asFlashed + "]";
		}

		public List<Poulpe> flash(List<Poulpe> poulpes) {
			for (int i = x - 1; i <= x + 1; i++) {
				for (int j = y - 1; j <= y + 1; j++) {
					if (i != x || j != y) {
						Optional<Poulpe> poulpe = getPoulpe(i, j, poulpes);
						if (poulpe.isPresent()) {
							if (poulpe.get().nrj <= 9 && !poulpe.get().asFlashed) {
								poulpe.get().nrjUp();
							}
						}
					} else {
						Optional<Poulpe> poulpe = getPoulpe(i, j, poulpes);
						if (poulpe.isPresent()) {
							poulpe.get().setAsFlashed(true);
							poulpe.get().setNrj(0);

						}
					}
				}
			}
			return poulpes;
		}

		public Poulpe notFlashed() {
			setAsFlashed(false);
			return this;

		}

		private Optional<Poulpe> getPoulpe(int i, int j, List<Poulpe> poulpes) {
			Poulpe lePoulpe = null;
			for (Poulpe p : poulpes) {
				if (p.x == i && p.y == j) {
					return Optional.ofNullable(p);
				}
			}
			return Optional.ofNullable(lePoulpe);
		}

		public Poulpe nrjUp() {
			nrj++;
			return this;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
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

		public int getNrj() {
			return nrj;
		}

		public void setNrj(int nrj) {
			this.nrj = nrj;
		}

		public boolean isAsFlashed() {
			return asFlashed;
		}

		public void setAsFlashed(boolean asFlashed) {
			this.asFlashed = asFlashed;
		}

		public Poulpe(int id, int x, int y, int nrj) {
			super();
			this.id = id;
			this.x = x;
			this.y = y;
			this.nrj = nrj;
			this.asFlashed = false;
		}

	}

	private List<String> getLignes(boolean c) {
		List<String> lignes = Arrays.asList(getInput(c).split("\n")).stream().collect(Collectors.toList());
		return lignes;
	}

	
	public static List<Long> getDuration() {
		A2021Day11 d = new A2021Day11(11);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1,endTime - startTime);
	}
}

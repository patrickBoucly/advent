package a2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class A2016Day10 extends A2016 {
	public A2016Day10(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day10 d = new A2016Day10(10);
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

	public long s1(boolean b) {
		Game g = new Game(Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim()).sorted()
				.collect(Collectors.toList()));
		try {
			return g.resolve(b, 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0L;
	}
	public long s2(boolean b) {
		Game g = new Game(Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim()).sorted()
				.collect(Collectors.toList()));
		try {
			return g.resolve(b, 2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0L;
	}

	private class Game {
		List<Inst> insts;
		Set<Robot> robots;
		HashMap<Integer, Integer> output = new HashMap<>();

		public Optional<Robot> getRobot(Integer i, Set<Robot> rs) {
			if (rs == null) {
				return Optional.empty();
			}
			Optional<Robot> res = Optional.empty();
			for (Robot r : rs) {
				if (r.id.equals(i)) {
					return Optional.of(r);
				}
			}
			return res;
		}

		public Game(List<String> input) {
			super();
			List<Inst> i = input.stream().map(Inst::new).collect(Collectors.toList());
			Set<Robot> r = new HashSet<>();
			for (Inst a : i) {
				if (a.type.equals("allocation")) {
					if (!r.contains(new Robot(a.destValue, 0))) {
						r.add(new Robot(a.destValue, a.goValue));
					} else {
						r.stream().filter(s -> s.id == a.destValue).forEach(k -> k.setV(a.goValue));
					}
				}
			}
			for (Inst a : i) {
				if (!a.type.equals("allocation")) {
					if (!a.destMinR.substring(0, 1).equals("o")) {
						if (this.getRobot(Integer.valueOf(a.destMinR), r).isEmpty()) {
							r.add(new Robot(Integer.valueOf(a.destMinR), null));
						}
					}
					if (!a.destMaxR.substring(0, 1).equals("o")) {
						if (this.getRobot(Integer.valueOf(a.destMaxR), r).isEmpty()) {
							r.add(new Robot(Integer.valueOf(a.destMaxR), null));
						}
					}
				}
			}
			HashMap<Integer, Integer> o = new HashMap<>();
			this.insts = i;
			this.robots = r;
			this.output = o;
		}

		public Integer resolve(Boolean b, Integer partie) throws Exception {
			List<Inst> give = insts.stream().filter(i -> !i.type.equals("allocation")).collect(Collectors.toList());
			List<Inst> giveNext = new ArrayList<>(give);
			Robot rsource = null;
			Optional<Robot> rDestMin = null;
			Optional<Robot> rDestMax = null;
			while (!give.isEmpty()) {

				Boolean done = false;
				for (Inst in : give) {
					try {
						rsource = this.getRobot(in.robotSource, robots).get();
						rDestMin = Optional.empty();
						rDestMax = Optional.empty();
						if (!in.destMinR.substring(0, 1).equals("o")) {
							rDestMin = this.getRobot(Integer.valueOf(in.destMinR), robots);
						}
						if (!in.destMaxR.substring(0, 1).equals("o")) {
							rDestMax = this.getRobot(Integer.valueOf(in.destMaxR), robots);
						}
						if (rsource.oqpHand()) {
							if (rDestMin.isEmpty() && rDestMax.isEmpty()) {
								done = true;
								giveNext = new ArrayList<>(
										give.stream().filter(ins -> !ins.equals(in)).collect(Collectors.toList()));
								output.put(Integer.valueOf(in.destMinR.substring(1)),this.getRobot(in.robotSource, robots).get().min);
								output.put(Integer.valueOf(in.destMaxR.substring(1)),this.getRobot(in.robotSource, robots).get().max);
							} else if (rDestMin.isEmpty() && !rDestMax.isEmpty()) {
								if (rDestMax.get().onEmptyHand()) {
									done = true;
									giveNext = new ArrayList<>(
											give.stream().filter(ins -> !ins.equals(in)).collect(Collectors.toList()));
									output.put(Integer.valueOf(in.destMinR.substring(1)),this.getRobot(in.robotSource, robots).get().min);
									this.getRobot(Integer.valueOf(in.destMaxR), robots).get().setV(
											this.getRobot(Integer.valueOf(in.robotSource), robots).get().getMax());
								}

							} else if (!rDestMin.isEmpty() && rDestMax.isEmpty()) {
								if (rDestMin.get().onEmptyHand()) {
									done = true;
									giveNext = new ArrayList<>(
											give.stream().filter(ins -> !ins.equals(in)).collect(Collectors.toList()));
									output.put(
											Integer.valueOf(in.destMaxR.substring(1)),this.getRobot(in.robotSource, robots).get().max);
									this.getRobot(Integer.valueOf(in.destMinR), robots).get().setV(
											this.getRobot(Integer.valueOf(in.robotSource), robots).get().getMin());

								}
							} else if (!rDestMin.isEmpty() && !rDestMax.isEmpty()) {
								if (rDestMin.get().onEmptyHand() && rDestMax.get().onEmptyHand()) {
									done = true;
									giveNext = new ArrayList<>(
											give.stream().filter(ins -> !ins.equals(in)).collect(Collectors.toList()));
									this.getRobot(Integer.valueOf(in.destMaxR), robots).get().setV(
											this.getRobot(Integer.valueOf(in.robotSource), robots).get().getMax());
									this.getRobot(Integer.valueOf(in.destMinR), robots).get().setV(
											this.getRobot(Integer.valueOf(in.robotSource), robots).get().getMin());
								}
							}
							if (done) {
								give = new ArrayList<>(giveNext);
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("error inst " + in);
						System.out.println("rsource " + rsource);
						System.out.println("///////");
					}
				}
			}
			if (partie.equals(1)) {
				return robots.stream().filter(r -> r.min == 17 && r.max == 61).findFirst().get().id;
			} else {
				return output.get(0) * output.get(2) * output.get(1);
			}

		}

	}

	private class Inst {
		String type = "give";
		Integer goValue = -1;
		Integer destValue = -1;
		Integer robotSource = -1;
		String destMinR = "";
		String destMaxR = "";
		String line;

		public Inst(String line) {
			super();
			this.line = line;
			String[] spl = line.split(" ");
			if (line.substring(0, 1).equals("v")) {
				type = "allocation";
				goValue = Integer.valueOf(spl[1]);
				destValue = Integer.valueOf(spl[5]);
			} else {
				robotSource = Integer.valueOf(spl[1]);
				if (spl[5].equals("bot")) {
					destMinR = spl[6];
				} else {
					destMinR = "o" + spl[6];
				}
				if (spl[10].equals("bot")) {
					destMaxR = spl[11];
				} else {
					destMaxR = "o" + spl[11];
				}
			}
		}

		@Override
		public String toString() {
			String res = this.line + " => " + "Inst [type=" + type + ", goValue=" + goValue + ", destValue=" + destValue
					+ ", robotSource=" + robotSource + ", destMinR=" + destMinR + ", destMaxR=" + destMaxR + "]";
			return res;
		}

	}

	private class Robot {
		Integer id;
		Integer min;
		Integer max;

		public Robot(int id, Integer min) {
			super();
			this.id = id;
			this.min = min;
			this.max = null;
		}

		public boolean emptyHand() {
			return min == null && max == null;
		}

		public boolean onEmptyHand() {
			return min == null || max == null;
		}

		public boolean oqpHand() {
			return min != null && max != null;
		}

		public int getMin() {
			return min;
		}

		public void setMin(Integer val) {
			this.min = val;
		}

		public int getMax() {
			return max;
		}

		public void setMax(Integer max) {
			this.max = max;
		}

		public void setV(int x) {
			if (min == null) {
				setMin(x);
			} else {
				if (x > min) {
					setMax(x);
				} else {
					setMax(min);
					setMin(x);
				}

			}
		}

		@Override
		public String toString() {
			return "Robot [id=" + id + ", min=" + min + ", max=" + max + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(id);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Robot other = (Robot) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Objects.equals(id, other.id);
		}

		private A2016Day10 getEnclosingInstance() {
			return A2016Day10.this;
		}
	}

	

	public static List<Long> getDuration() {
		A2016Day10 d = new A2016Day10(10);
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

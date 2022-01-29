package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class A2017Day23 extends A2017 {

	public A2017Day23(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day23 d = new A2017Day23(23);
		// d.s1(true);
		long startTime = System.currentTimeMillis();
		//System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1=endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day "+ d.day+" run 1 took "+timeS1+" milliseconds, run 2 took " + (endTime - startTime) + " milliseconds");
		
	}

	public int s1(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Inst> insts = new ArrayList<>();
		for (String s : lignes) {
			String[] sp = s.split(" ");
			if (sp.length == 3) {
				insts.add(new Inst(sp[0].trim(), sp[1].trim(), sp[2].trim()));
			} else {
				insts.add(new Inst(sp[0].trim(), sp[1].trim(), null));
			}
		}
		Game g = new Game(insts, "", 0L);
		g.play();
		return g.cptMul;
	}
	public Long s2(boolean b) {
		List<String> lignes = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		List<Inst> insts = new ArrayList<>();
		for (String s : lignes) {
			String[] sp = s.split(" ");
			if (sp.length == 3) {
				insts.add(new Inst(sp[0].trim(), sp[1].trim(), sp[2].trim()));
			} else {
				insts.add(new Inst(sp[0].trim(), sp[1].trim(), null));
			}
		}
		Game g = new Game(insts, "", 0L);
		g.record.put("a",1L);
		g.play();
		return g.record.get("h");
	}
	public static class Game {
		List<Inst> insts;
		String lastPlayed;
		Long LastPlayedValue;
		int cip;
		int cptMul;
		HashMap<String, Long> record;
		boolean finish;

		public List<Inst> getInsts() {
			return insts;
		}

		public void play() {
			cip=11;
			record.put("e", 106699L);//passer jnz g -8
			record.put("b", 106700L);//debut
			record.put("c", 123700L);//debut
			record.put("d", 106699L);//passer jnz g -13
			record.put("g", 106700L);//passer jnz g -13
			//record.put("f", 0L);
			while (!finish) {
				
				if(cip<insts.size()) {
					
				System.out.println(cip);
				System.out.println(insts.get(cip));
				System.out.println(record);
			//	System.out.println("h "+record.get("h"));
				apply(insts.get(cip));
				} else {
					finish=true;
					System.out.println("h "+record.get("h"));
				}
			}

		}

		private void apply(Inst i) {
			if (i.op.equals("sub")) {
					if (i.p2.matches("-?\\d+")) {
					record.put(i.p1, record.get(i.p1) - Long.parseLong(i.p2));
					} else {
						if (record.containsKey(i.p2)) {
							record.put(i.p1, record.get(i.p1) - record.get(i.p2));
						}
					}
					cip++;
			} else if (i.op.equals("set")) {
				if (i.p2.matches("-?\\d+")) {
					record.put(i.p1, Long.parseLong(i.p2));
				} else {
					if (record.containsKey(i.p2)) {
						record.put(i.p1, record.get(i.p2));
					}
				}
				cip++;
			} else if (i.op.equals("mul")) {
					cptMul++;
					if (i.p2.matches("-?\\d+")) {
					record.put(i.p1, record.get(i.p1) * Long.parseLong(i.p2));
					} else {
						if (record.containsKey(i.p2)) {
							record.put(i.p1, record.get(i.p1) * record.get(i.p2));
						}
					}
				
				cip++;
			} else if (i.op.equals("jnz")) {
				if (i.p1.matches("-?\\d+")) {
					if (Long.parseLong(i.p1) != 0) {
						cip += Long.parseLong(i.p2);
					} else {
						cip++;
					}
					
				} else {
					
						if (record.get(i.p1) != 0) {
							cip += Long.parseLong(i.p2);
						} else {
							cip++;
						}
					
				}

			} 

		}

		public void setInsts(List<Inst> insts) {
			this.insts = insts;
		}

		public String getLastPlayed() {
			return lastPlayed;
		}

		public void setLastPlayed(String lastPlayed) {
			this.lastPlayed = lastPlayed;
		}

		public Long getLastPlayedValue() {
			return LastPlayedValue;
		}

		public void setLastPlayedValue(Long lastPlayedValue) {
			LastPlayedValue = lastPlayedValue;
		}

		public Game(List<Inst> insts, String lastPlayed, Long lastPlayedValue) {
			super();
			this.insts = insts;
			this.lastPlayed = lastPlayed;
			LastPlayedValue = lastPlayedValue;
			this.cip = 0;
			this.finish = false;
			this.record = new HashMap<String, Long>();
			this.record.put("a", 0L);
			this.record.put("b", 0L);
			this.record.put("c", 0L);
			this.record.put("d", 0L);
			this.record.put("e", 0L);
			this.record.put("f", 0L);
			this.record.put("g", 0L);
			this.record.put("h", 0L);
			
			this.cptMul=0;
		}

	}

	public static class Inst {
		String op;
		String p1;
		String p2;

		public String getOp() {
			return op;
		}

		public void setOp(String op) {
			this.op = op;
		}

		public String getP1() {
			return p1;
		}

		public void setP1(String p1) {
			this.p1 = p1;
		}

		public String getP2() {
			return p2;
		}

		public void setP2(String p2) {
			this.p2 = p2;
		}

		public Inst(String op, String p1, String p2) {
			super();
			this.op = op;
			this.p1 = p1;
			this.p2 = p2;
		}

		@Override
		public String toString() {
			return op + " " + p1 + " " + p2;
		}

	}

	public static List<Long> getDuration() {
		A2017Day23 d = new A2017Day23(1);
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

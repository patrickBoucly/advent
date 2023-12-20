package a2023;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class A2023Day19 extends A2023 {

	public A2023Day19(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day19 d = new A2023Day19(19);
		// System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		// d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public BigDecimal s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		TheGame2 tg = getTheGame2(inputL);
		tg.resoudre();
	//	System.out.println(tg.accepted);
		return tg.compterAccepted();

	}

	private TheGame2 getTheGame2(List<String> inputL) {
		Map<String, Rule> insts = new HashMap<>();
		String startInst = "in";
		TheGame2 tg = new TheGame2();
		for (String s : inputL) {
			if (s.isBlank()) {
				break;
			}
			String name = s.substring(0, s.indexOf("{"));
			String[] rules = s.substring(s.indexOf("{") + 1, s.indexOf("}")).trim().split(",");
			ArrayList<String> lr = new ArrayList<>();
			for (String r : rules) {
				lr.add(r.trim());
			}
			Rule rule = new Rule();
			rule.ruleElement = lr;
			insts.put(name, rule);

		}

		tg.setInsts(insts);
		tg.setRejected(new ArrayList<A2023Day19.BigWorkflow>());
		tg.setAccepted(new ArrayList<A2023Day19.BigWorkflow>());
		tg.setStartInst(startInst);
		BigWorkflow bwf = new BigWorkflow(1, 4000, 1, 4000, 1, 4000, 1, 4000, false, "in");
		tg.setBwf((Arrays.asList(bwf)));
		return tg;
	}

	public Long s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		TheGame tg = getTheGame(inputL);
		// System.out.println(tg);
		tg.resoudre();
		Long res = 0L;
		for (Workflow e : tg.getAccepted()) {
			res += e.a + e.x + e.m + e.s;
		}
		return res;
	}

	private TheGame getTheGame(List<String> inputL) {
		Map<String, Rule> insts = new HashMap<>();
		List<Workflow> workflows = new ArrayList<>();
		List<Workflow> accepted = new ArrayList<>();
		List<Workflow> rejected = new ArrayList<>();
		String startInst = "in";
		List<Float> lesX = new ArrayList<>();
		List<Float> lesM = new ArrayList<>();
		List<Float> lesA = new ArrayList<>();
		List<Float> lesS = new ArrayList<>();
		boolean p2 = false;
		TheGame tg = new TheGame();
		for (String s : inputL) {
			if (!p2 && !s.isBlank()) {

				String name = s.substring(0, s.indexOf("{"));

				String[] rules = s.substring(s.indexOf("{") + 1, s.indexOf("}")).trim().split(",");
				ArrayList<String> lr = new ArrayList<>();
				for (String r : rules) {
					lr.add(r.trim());
				}
				Rule rule = new Rule();
				rule.ruleElement = lr;
				insts.put(name, rule);
			}
			if (s.isBlank()) {
				p2 = true;
			}
			if (p2 && !s.isBlank()) {
				Workflow w = new Workflow();
				String[] cont = s.substring(s.indexOf("{") + 1, s.indexOf("}")).trim().split(",");
				w.x = Integer.parseInt(cont[0].trim().substring(cont[0].indexOf("=") + 1));
				w.m = Integer.parseInt(cont[1].trim().substring(cont[1].indexOf("=") + 1));
				w.a = Integer.parseInt(cont[2].trim().substring(cont[2].indexOf("=") + 1));
				w.s = Integer.parseInt(cont[3].trim().substring(cont[3].indexOf("=") + 1));
				workflows.add(w);
			}
		}
		tg.setLesX(lesX);
		tg.setLesM(lesM);
		tg.setLesA(lesA);
		tg.setLesS(lesS);
		tg.setAccepted(accepted);
		tg.setInsts(insts);
		tg.setRejected(rejected);
		tg.setStartInst(startInst);
		tg.setWorkflows(workflows);

		return tg;
	}

	@Getter
	@Setter
	@ToString
	@NoArgsConstructor
	public static class Workflow {
		int x;
		int m;
		int a;
		int s;

		public Workflow(int x, int m, int a, int s) {
			super();
			this.x = x;
			this.m = m;
			this.a = a;
			this.s = s;
		}

	}

	@Getter
	@Setter
	@ToString
	@NoArgsConstructor
	@AllArgsConstructor
	public static class BigWorkflow {
		public BigWorkflow(BigWorkflow w) {
			xg = w.xg;
			xd = w.xd;
			mg = w.mg;
			md = w.md;
			ag = w.ag;
			ad = w.ad;
			sg = w.sg;
			sd = w.sd;
			solved = w.solved;
			curInst = w.curInst;
		}

		int xg;
		int xd;
		int mg;
		int md;
		int ag;
		int ad;
		int sg;
		int sd;
		boolean solved;
		String curInst;

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Instruction {
		String name;
		ArrayList<Rule> rules;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Rule {
		List<String> ruleElement;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class TheGame {
		private ArrayList<Integer> allList;

		public void resoudre() {
			for (Workflow e : workflows) {
				String reponse = getReponse(e);
				if (reponse.equals("A")) {
					accepted.add(e);
				}
				if (reponse.equals("R")) {
					rejected.add(e);
				}
			}
		}

		public Long compter() {
			Long res = 0L;
			for (Workflow w : accepted) {
				res += compter(w);
			}
			return res;
		}

		public Long compter(Workflow w) {
			Long res = 1L;
			// System.out.println(w);
			int indx = -1;
			for (Integer i = 0; i < lesX.size() - 1; i++) {
				if (w.x < lesX.get(i + 1) && w.x > lesX.get(i)) {
					indx = i;
				}
			}
			int indm = -1;
			for (Integer i = 0; i < lesM.size() - 1; i++) {
				if (w.m < lesM.get(i + 1) && w.m > lesM.get(i)) {
					indm = i;
				}
			}
			int inda = -1;
			for (Integer i = 0; i < lesA.size() - 1; i++) {
				if (w.a < lesA.get(i + 1) && w.a > lesA.get(i)) {
					inda = i;
				}
			}
			int inds = -1;
			for (Integer i = 0; i < lesS.size() - 1; i++) {
				if (w.s < lesS.get(i + 1) && w.s > lesS.get(i)) {
					inds = i;
				}
			}

			res = (long) (Math.floor(lesX.get(indx + 1)) - Math.floor(lesX.get(indx)))
					* (long) (Math.floor(lesM.get(indm + 1)) - Math.floor(lesM.get(indm)))
					* (long) (Math.floor(lesA.get(inda + 1)) - Math.floor(lesA.get(inda)))
					* (long) (Math.floor(lesS.get(inds + 1)) - Math.floor(lesS.get(inds)));

			return res;
		}

		public void alimenterListe() {
			for (Rule rule : insts.values()) {
				for (String r : rule.ruleElement) {
					if (r.contains("<") || r.contains(">")) {
						if (r.contains("x")) {
							if (r.contains("<")) {
								int v = Integer.parseInt(r.substring(r.indexOf("<") + 1, r.indexOf(":")));
								// lesX.add(v - 1);
								lesX.add((float) (v + 0.5));
							} else {
								int v = Integer.parseInt(r.substring(r.indexOf(">") + 1, r.indexOf(":")));
								// lesX.add(v + 1);
								lesX.add((float) (v - 0.5));
							}
						} else if (r.contains("m")) {
							if (r.contains("<")) {
								int v = Integer.parseInt(r.substring(r.indexOf("<") + 1, r.indexOf(":")));
								// lesM.add(v - 1);
								lesM.add((float) (v + 0.5));
							} else {
								int v = Integer.parseInt(r.substring(r.indexOf(">") + 1, r.indexOf(":")));
								// lesM.add(v + 1);
								lesM.add((float) (v - 0.5));
							}
						} else if (r.contains("a")) {
							if (r.contains("<")) {
								int v = Integer.parseInt(r.substring(r.indexOf("<") + 1, r.indexOf(":")));
								// lesA.add(v- 1);
								lesA.add((float) (v + 0.5));
							} else {
								int v = Integer.parseInt(r.substring(r.indexOf(">") + 1, r.indexOf(":")));
								// lesA.add(v+ 1);
								lesA.add((float) (v - 0.5));
							}
						} else if (r.contains("s")) {
							if (r.contains("<")) {

								int v = Integer.parseInt(r.substring(r.indexOf("<") + 1, r.indexOf(":")));
								// lesS.add(v- 1);
								lesS.add((float) (v + 0.5));
							} else {
								int v = Integer.parseInt(r.substring(r.indexOf(">") + 1, r.indexOf(":")));
								// lesS.add(v+ 1);
								lesS.add((float) (v - 0.5));
							}
						}
					}
				}
			}
			lesX.add(0.5f);
			lesM.add(0.5f);
			lesA.add(0.5f);
			lesS.add(0.5f);
			lesX.add(4000.5f);
			lesM.add(4000.5f);
			lesA.add(4000.5f);
			lesS.add(4000.5f);

			lesX.sort(Comparator.naturalOrder());
			lesM.sort(Comparator.naturalOrder());
			lesA.sort(Comparator.naturalOrder());
			lesS.sort(Comparator.naturalOrder());
			for (int i = 0; i < lesX.size() - 1; i++) {
				lesXctrl.add((int) Math.floor((lesX.get(i + 1) + lesX.get(i)) / 2));
			}

			for (int i = 0; i < lesM.size() - 1; i++) {
				lesMctrl.add((int) Math.floor((lesM.get(i + 1) + lesM.get(i)) / 2));
			}

			for (int i = 0; i < lesA.size() - 1; i++) {
				lesActrl.add((int) Math.floor((lesA.get(i + 1) + lesA.get(i)) / 2));
			}

			for (int i = 0; i < lesS.size() - 1; i++) {
				lesSctrl.add((int) Math.floor((lesS.get(i + 1) + lesS.get(i)) / 2));
			}
			allList = new ArrayList<Integer>();
			allList.addAll(lesXctrl);
			allList.addAll(lesMctrl);
			allList.addAll(lesActrl);
			allList.addAll(lesSctrl);

		}

		public String getReponse(Workflow e) {
			Rule curRule = insts.get(startInst);
			// System.out.println(curRule);
			while (true) {
				String reponse = analyser(curRule, e);
				if (reponse.equals("A")) {
					return "A";
				}
				if (reponse.equals("R")) {
					return "R";
				}
				curRule = insts.get(reponse);
			}
		}

		private String analyser(Rule curRule, Workflow e) {
			for (int i = 0; i < curRule.ruleElement.size(); i++) {
				String rul = curRule.ruleElement.get(i);
				if (rul.equals("A")) {
					return "A";
				} else if (rul.equals("R")) {
					return "R";
				} else if (rul.contains("<")) {
					String g = rul.substring(0, rul.indexOf("<"));
					String dir = rul.substring(rul.indexOf(":") + 1);
					Integer comp = Integer.parseInt(rul.substring(rul.indexOf("<") + 1, rul.indexOf(":")));
					if (g.equals("x") && e.x < comp) {
						return dir;
					}
					if (g.equals("m") && e.m < comp) {
						return dir;
					}
					if (g.equals("a") && e.a < comp) {
						return dir;
					}
					if (g.equals("s") && e.s < comp) {
						return dir;
					}
				} else if (rul.contains(">")) {
					String g = rul.substring(0, rul.indexOf(">"));
					String dir = rul.substring(rul.indexOf(":") + 1);
					Integer comp = Integer.parseInt(rul.substring(rul.indexOf(">") + 1, rul.indexOf(":")));
					if (g.equals("x") && e.x > comp) {
						return dir;
					}
					if (g.equals("m") && e.m > comp) {
						return dir;
					}
					if (g.equals("a") && e.a > comp) {
						return dir;
					}
					if (g.equals("s") && e.s > comp) {
						return dir;
					}

				} else {
					return rul;
				}

			}
			return "R";

		}

		Map<String, Rule> insts;
		List<Workflow> workflows;
		List<Workflow> accepted;
		List<Workflow> rejected;
		String startInst;
		List<Float> lesX = new ArrayList<>();
		List<Float> lesM = new ArrayList<>();
		List<Float> lesA = new ArrayList<>();
		List<Float> lesS = new ArrayList<>();
		List<Integer> lesXctrl = new ArrayList<>();
		List<Integer> lesMctrl = new ArrayList<>();
		List<Integer> lesActrl = new ArrayList<>();
		List<Integer> lesSctrl = new ArrayList<>();
	}

	@Getter
	@Setter
	@ToString
	@NoArgsConstructor
	public static class Reponse {
		String fini = "";
		List<BigWorkflow> next = new ArrayList<>();
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class TheGame2 {
		public void resoudre() {
			while (bwf.stream().anyMatch(w -> !w.solved)) {
				bwf = nextMove();
			}

		}

		private List<BigWorkflow> nextMove() {
			List<BigWorkflow> nbwf = new ArrayList<>();
			nbwf.addAll(bwf.stream().filter(w -> w.solved).toList());
			for (BigWorkflow w : bwf.stream().filter(w -> !w.solved).toList()) {
				nbwf.addAll(doNextStep(w));
			}
			return nbwf;
		}

		private List<BigWorkflow> doNextStep(BigWorkflow w) {
			Rule curRule = insts.get(w.curInst);
			Reponse reponse = analyser2(curRule, w);
			if (reponse.fini.equals("A")) {
				w.solved=true;
				accepted.add(w);
			}
			if (reponse.fini.equals("R")) {
				w.solved=true;
				rejected.add(w);
			}
			return reponse.next;
		}

		private Reponse analyser2(Rule r, BigWorkflow w) {
			Reponse rep = new Reponse();
			ArrayList<BigWorkflow> next = new ArrayList<>();
			for(int i = 0; i < r.ruleElement.size(); i++) {
				String rul = r.ruleElement.get(i);
				if (rul.equals("A")) {
					rep.fini = "A";
					return rep;
				} else if (rul.equals("R")) {
					rep.fini = "R";
					return rep;
				} else if (rul.contains("<")) {
					String g = rul.substring(0, rul.indexOf("<"));
					String dir = rul.substring(rul.indexOf(":") + 1);
					Integer comp = Integer.parseInt(rul.substring(rul.indexOf("<") + 1, rul.indexOf(":")));
					if (g.equals("x")) {
						if (w.xd < comp) {
							if (dir.equals("A")) {
								rep.fini = "A";
								return rep;
							} else if (dir.equals("R")) {
								rep.fini = "R";
								return rep;
							} else {
								w.curInst = dir;
								next.add(w);
								rep.setNext(next);
								return rep;
							}

						} else if (w.xg >= comp) {
							continue;
						} else {
							BigWorkflow w1 = new BigWorkflow(w);
							BigWorkflow w2 = new BigWorkflow(w);
							w1.xd = comp - 1;
							w2.xg = comp;
							next.add(w2);
							next.add(w1);
							rep.next = next;
							return rep;
						}
					}
					if (g.equals("a")) {
						if (w.ad < comp) {
							if (dir.equals("A")) {
								rep.fini = "A";
								return rep;
							} else if (dir.equals("R")) {
								rep.fini = "R";
								return rep;
							} else {
								w.curInst = dir;
								next.add(w);
								rep.setNext(next);
								return rep;
							}

						} else if (w.ag >= comp) {
							continue;
						} else {
							BigWorkflow w1 = new BigWorkflow(w);
							BigWorkflow w2 = new BigWorkflow(w);
							w1.ad = comp - 1;
							w2.ag = comp;
							next.add(w2);
							next.add(w1);
							rep.next = next;
							return rep;
						}
					}
					if (g.equals("m")) {
						if (w.md < comp) {
							if (dir.equals("A")) {
								rep.fini = "A";
								return rep;
							} else if (dir.equals("R")) {
								rep.fini = "R";
								return rep;
							} else {
								w.curInst = dir;
								next.add(w);
								rep.setNext(next);
								return rep;
							}

						} else if (w.mg >= comp) {
							//
						} else {
							BigWorkflow w1 = new BigWorkflow(w);
							BigWorkflow w2 = new BigWorkflow(w);
							w1.md = comp - 1;
							w2.mg = comp;
							next.add(w2);
							next.add(w1);
							rep.next = next;
							return rep;
						}
					}
					if (g.equals("s")) {
						if (w.sd < comp) {
							if (dir.equals("A")) {
								rep.fini = "A";
								return rep;
							} else if (dir.equals("R")) {
								rep.fini = "R";
								return rep;
							} else {
								w.curInst = dir;
								next.add(w);
								rep.setNext(next);
								return rep;
							}

						} else if (w.sg >= comp) {
							//continue;
						} else {
							BigWorkflow w1 = new BigWorkflow(w);
							BigWorkflow w2 = new BigWorkflow(w);
							w1.sd = comp - 1;
							w2.sg = comp;
							next.add(w2);
							next.add(w1);
							rep.next = next;
							return rep;
						}
					}
				} else if (rul.contains(">")){
					String g = rul.substring(0, rul.indexOf(">"));
					String dir = rul.substring(rul.indexOf(":") + 1);
					Integer comp = Integer.parseInt(rul.substring(rul.indexOf(">") + 1, rul.indexOf(":")));
					if (g.equals("x")) {
						if (w.xg > comp) {
							if (dir.equals("A")) {
								rep.fini = "A";
								return rep;
							} else if (dir.equals("R")) {
								rep.fini = "R";
								return rep;
							} else {
								w.curInst = dir;
								next.add(w);
								rep.setNext(next);
								return rep;
							}

						} else if (w.xd <= comp) {
							continue;
						} else {
							BigWorkflow w1 = new BigWorkflow(w);
							BigWorkflow w2 = new BigWorkflow(w);
							w1.xd = comp ;
							w2.xg = comp+1;
							next.add(w2);
							next.add(w1);
							rep.next = next;
							return rep;
						}
					}
					if (g.equals("a")) {
						if (w.ag > comp) {
							if (dir.equals("A")) {
								rep.fini = "A";
								return rep;
							} else if (dir.equals("R")) {
								rep.fini = "R";
								return rep;
							} else {
								w.curInst = dir;
								next.add(w);
								rep.setNext(next);
								return rep;
							}

						} else if (w.ad <= comp) {
							continue;
						} else {
							BigWorkflow w1 = new BigWorkflow(w);
							BigWorkflow w2 = new BigWorkflow(w);
							w1.ad = comp ;
							w2.ag = comp+1;
							next.add(w2);
							next.add(w1);
							rep.next = next;
							return rep;
						}
					}
					if (g.equals("m")) {
						if (w.mg > comp) {
							if (dir.equals("A")) {
								rep.fini = "A";
								return rep;
							} else if (dir.equals("R")) {
								rep.fini = "R";
								return rep;
							} else {
								w.curInst = dir;
								next.add(w);
								rep.setNext(next);
								return rep;
							}

						} else if (w.md <= comp) {
							continue;
						} else {
							BigWorkflow w1 = new BigWorkflow(w);
							BigWorkflow w2 = new BigWorkflow(w);
							w1.md = comp ;
							w2.mg = comp+1;
							next.add(w2);
							next.add(w1);
							rep.next = next;
							return rep;
						}
					}
					if (g.equals("s")) {
						if (w.sg > comp) {
							if (dir.equals("A")) {
								rep.fini = "A";
								return rep;
							} else if (dir.equals("R")) {
								rep.fini = "R";
								return rep;
							} else {
								w.curInst = dir;
								next.add(w);
								rep.setNext(next);
								return rep;
							}

						} else if (w.sd <= comp) {
							
						} else {
							BigWorkflow w1 = new BigWorkflow(w);
							BigWorkflow w2 = new BigWorkflow(w);
							w1.sd = comp ;
							w2.sg = comp+1;
							next.add(w2);
							next.add(w1);
							rep.next = next;
							return rep;
						}
					}

				} else {
					w.curInst=rul;
					next.add(w);
					rep.setNext(next);
					return rep;
				}

			}
			
			return null;
		}

		public BigDecimal compterAccepted() {
			BigDecimal res=BigDecimal.ZERO;
			for(BigWorkflow w:accepted) {
				//System.out.println(w);
				res=res.add(BigDecimal.valueOf(w.xd-w.xg+1L).multiply(BigDecimal.valueOf(w.md-w.mg+1L)).multiply(BigDecimal.valueOf(w.ad-w.ag+1L)).multiply(BigDecimal.valueOf(w.sd-w.sg+1L)));
			//	System.out.println(res);
			}
			return res;
		}

		Map<String, Rule> insts;
		List<BigWorkflow> bwf;
		List<BigWorkflow> accepted;
		List<BigWorkflow> rejected;
		String startInst;
	}

	public static List<Long> getDuration() {
		A2023Day19 d = new A2023Day19(1);
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

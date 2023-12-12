package a2023;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class A2023Day12 extends A2023 {

	public A2023Day12(int day) {
		super(day);
	}

	public static long cpt = 0L;

	public static void main(String[] args0) {
		A2023Day12 d = new A2023Day12(12);
		// System.out.println(d.s1(false));
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public Long s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		TheGame tg = getGameFromInput(inputL);

		return tg.compterArrangements();
	}

	
	

	private TheGame getGameFromInput(List<String> inputL) {
		TheGame tg = new TheGame();
		List<Instruction> insts = new ArrayList<>();
		for (String s : inputL) {
			List<String> lavaSpot = new ArrayList<>();
			List<Integer> contiguousDamaged = new ArrayList<>();
			String[] sp = s.trim().split(" ");
			for (int i = 0; i < sp[0].length(); i++) {
				lavaSpot.add(sp[0].substring(i, i + 1));
			}
			String[] sp2 = sp[1].trim().split(",");
			for (int i = 0; i < sp2.length; i++) {
				contiguousDamaged.add(Integer.parseInt(sp2[i].trim()));
			}
			Instruction ins = new Instruction();
			ins.setContiguousDamaged(contiguousDamaged);
			ins.setLavaSpot(lavaSpot);
			insts.add(ins);
		}
		tg.setInsts(insts);
		return tg;
	}

	public Long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		TheGame tg = getGame2FromInput(inputL);
		return tg.compterArrangements();

	}

	private TheGame getGame2FromInput(List<String> inputL) {
		TheGame tg = new TheGame();
		List<Instruction> insts = new ArrayList<>();
		for (String s : inputL) {
			List<String> lavaSpot = new ArrayList<>();
			List<Integer> contiguousDamaged = new ArrayList<>();
			String[] sp = s.trim().split(" ");
			for (int i = 0; i < sp[0].length(); i++) {
				lavaSpot.add(sp[0].substring(i, i + 1));
			}
			String[] sp2 = sp[1].trim().split(",");
			for (int i = 0; i < sp2.length; i++) {
				contiguousDamaged.add(Integer.parseInt(sp2[i].trim()));
			}
			Instruction ins = new Instruction();
			List<Integer> fiveContiguousDamaged = new ArrayList<>();
			List<String> fiveLavaSpot = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				fiveContiguousDamaged.addAll(contiguousDamaged);
				fiveLavaSpot.addAll(lavaSpot);
				if (i != 4) {
					fiveLavaSpot.add("?");
				}

			}
			ins.setContiguousDamaged(fiveContiguousDamaged);
			ins.setLavaSpot(fiveLavaSpot);
			insts.add(ins);
		}
		tg.setInsts(insts);
		return tg;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class TheGame {
		List<Instruction> insts;

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			for (Instruction ins : insts) {
				res.append(ins).append("\n");
			}
			return res.toString();
		}

		public Long compterArrangements() {
			Long res = 0L;
			for (Instruction ins : insts) {
				cpt++;
				System.out.println("ligne "+cpt);
				System.out.println(ins);
				Long arrNb = compterArrangements(ins);
				System.out.println("ArrangementCount :" + arrNb);
				res += arrNb;
			}
			return res;
		}

		private Long compterArrangements(Instruction ins) {
			Map<Instruction, Long> chaines = new HashMap<>();
			chaines.put(ins, 1L);
			while (chaines.keySet().stream().anyMatch(k -> k.lavaSpot.contains("?"))) {
				chaines = resoudreUnMystere(chaines);
			}

			Long res = 0L;
			for (Entry<Instruction, Long> entry : chaines.entrySet()) {
				if (entry.getKey().isValide()) {
					res += entry.getValue();
				}
			}
			return res;
		}

		private Map<Instruction, Long> resoudreUnMystere(Map<Instruction, Long> chaines) {
			Map<Instruction, Long> newChaines = new HashMap<>();
			for (Entry<Instruction, Long> entry : chaines.entrySet()) {
				Map<Instruction, Long> newChainesInst = new HashMap<>();
				newChainesInst = resoudreUnMystereInst(entry);

				for (Instruction k : newChainesInst.keySet()) {
					if (newChaines.containsKey(k)) {
						newChaines.put(k, newChaines.get(k) + newChainesInst.get(k));
					} else {
						if(newChaines.keySet().size() !=0 && newChaines.keySet().size() % 1000000==0) {
							System.out.println(k.lavaSpot.stream().filter(j->j.equals("?")).count());
							System.out.println(newChaines.keySet().size());
						}
						newChaines.put(k, newChainesInst.get(k));
					}
				}
			}

			return newChaines;
		}

		private Map<Instruction, Long> resoudreUnMystereInst(Entry<Instruction, Long> entry) {
			// System.out.println(entry);
			Map<Instruction, Long> newInst = new HashMap<>();

			// System.out.println(entry);
			int posMystere = entry.getKey().lavaSpot.indexOf("?");

			List<String> l1 = new ArrayList<String>(entry.getKey().lavaSpot);
			l1.set(posMystere, "#");
			List<String> l2 = new ArrayList<String>(entry.getKey().lavaSpot);
			l2.set(posMystere, ".");
			Instruction inst1 = new Instruction();
			Instruction inst2 = new Instruction();
			inst1.setContiguousDamaged(entry.getKey().contiguousDamaged);
			inst2.setContiguousDamaged(entry.getKey().contiguousDamaged);
			inst1.setLavaSpot(l1);
			inst2.setLavaSpot(l2);
			
			if (okPourLeMoment(inst1)) {
				//System.out.println("post ctrl "+cpt +" " + inst1);
				Entry<Instruction, Long> newEntry = getReste(inst1, entry.getValue());
				newInst.put(newEntry.getKey(), newEntry.getValue());
			}
			if (okPourLeMoment(inst2)) {
			//	System.out.println("post ctrl "+cpt +" " + inst2);
				Entry<Instruction, Long> newEntry = getReste(inst2, entry.getValue());
				newInst.put(newEntry.getKey(), newEntry.getValue());
			}

			return newInst;
		}

		private Entry<Instruction, Long> getReste(Instruction inst1, Long nb) {

			if (!inst1.lavaSpot.contains("?")) {
				return new AbstractMap.SimpleEntry<>(inst1, nb);
			}
			int safe = inst1.lavaSpot.subList(0, inst1.lavaSpot.indexOf("?")).lastIndexOf(".");
			if (!inst1.lavaSpot.contains("?") || safe < 1) {
				return new AbstractMap.SimpleEntry<>(inst1, nb);
			}
			List<String> seq = inst1.lavaSpot.subList(0, safe + 1);
			Long nbLava = seq.stream().filter(s -> s.equals("#")).count();
			if (nbLava == 0L) {
				return new AbstractMap.SimpleEntry<>(inst1, nb);
			}
			Long countCond = 0L;
			int nbConnu = 0;
			/*
			System.out.println("inst  complete");
			System.out.println(inst1);
			System.out.println("safe : " + safe);
			System.out.println("seq " + seq);
			System.out.println(inst1.contiguousDamaged);
			System.out.println(nbLava);
			*/
			while (countCond != nbLava) {
				/*
				System.out.println("nb connu :" + nbConnu);
				System.out.println("countCond " + countCond);
				System.out.println(inst1.contiguousDamaged.get(nbConnu));
				*/
				countCond += inst1.contiguousDamaged.get(nbConnu);
				nbConnu++;
			}
			Instruction inst = new Instruction();
			List<Integer> condConservees = inst1.contiguousDamaged.subList(nbConnu, inst1.contiguousDamaged.size());
			inst.setContiguousDamaged(new ArrayList<Integer>(condConservees));
			List<String> seqConservee = inst1.lavaSpot.subList(safe + 1, inst1.lavaSpot.size());
			inst.setLavaSpot(seqConservee);
			Entry<Instruction, Long> reste = new AbstractMap.SimpleEntry<>(inst, nb);
			//System.out.println("reste : " + reste);
			return reste;
		}

		private boolean okPourLeMoment(Instruction inst) {
			List<String> seq = new ArrayList<>(inst.lavaSpot);
			// debug=Arrays.asList("#",".","#",".","#","#","#",".",".","#",".","#",".","#","#","#",".",".",".","#",".","#",".","#","#","#",".",".",".","#",".","#",".","#","#","#",".",".","#","#",".","#",".","#","#","#");

			List<String> l = new ArrayList<String>();
			int posMystere=seq.indexOf("?");
			if (posMystere != -1) {
				l = seq.subList(0, posMystere);
				if(l.size()<2) {
					return true;
				}
				if(!l.contains(".")) {

					if(inst.contiguousDamaged.size()==0 || l.stream().filter(k->k.equals("#")).count()>inst.contiguousDamaged.get(0)) {
						return false;
					}
				}
				int lastP=l.lastIndexOf(".");
				if(lastP==-1) {
					return true;
				}
				l = l.subList(0, lastP );
				if(l.size()==0) {
					return true;
				}
				
			} else {
				l = seq;
			}
			//l.add(".");
		
			List<Integer> contiguousDamaged = new ArrayList<>();
			String curSymb = l.get(0);
			int curSeq = 1;
			for (int i = 1; i < l.size(); i++) {
				if (l.get(i).equals("#") && curSymb.equals("#")) {
					curSeq++;
				} else if (curSymb.equals(".") && l.get(i).equals("#")) {
					curSeq = 1;
					curSymb = l.get(i);
				} else if (curSymb.equals("#") && l.get(i).equals(".")) {
					contiguousDamaged.add(curSeq);
					curSymb = l.get(i);
					curSeq = 0;
				} else {
					curSymb = l.get(i);
					curSeq = 0;

				}
				
			}
			if(curSymb.equals("#")) {
				contiguousDamaged.add(curSeq);
			}
			if (contiguousDamaged.size() > inst.getContiguousDamaged().size()) {
				return false;
			}
			for (int k = 0; k < contiguousDamaged.size() ; k++) {

				if (inst.getContiguousDamaged().get(k) != contiguousDamaged.get(k)) {
					return false;
				}
			}

			return true;
		}

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Instruction {
		List<String> lavaSpot;
		List<Integer> contiguousDamaged;

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			res.append(lavaSpot + " " + contiguousDamaged);

			return res.toString();
		}
		

		public boolean isValide() {
			List<String> l = new ArrayList<>(lavaSpot);
			List<Integer> contiguousDamaged = new ArrayList<>();
			String curSymb = lavaSpot.get(0);
			l.add(".");
			int curSeq = 1;
			for (int i = 1; i < l.size(); i++) {
				if (l.get(i).equals("#") && curSymb.equals("#")) {
					curSeq++;
				} else if (curSymb.equals(".") && l.get(i).equals("#")) {
					curSeq = 1;
					curSymb = l.get(i);
				} else if (curSymb.equals("#") && l.get(i).equals(".")) {
					contiguousDamaged.add(curSeq);
					curSymb = l.get(i);
					curSeq = 0;
				} else {
					curSymb = l.get(i);
					curSeq = 0;

				}
			}

			if (contiguousDamaged.size() != getContiguousDamaged().size()) {

				return false;
			}

			for (int k = 0; k < contiguousDamaged.size(); k++) {
				if (getContiguousDamaged().get(k) != contiguousDamaged.get(k)) {

					return false;
				}
			}

			return true;
		}


		@Override
		public int hashCode() {
			return Objects.hash(contiguousDamaged, lavaSpot);
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Instruction other = (Instruction) obj;
			return Objects.equals(contiguousDamaged, other.contiguousDamaged)
					&& Objects.equals(lavaSpot, other.lavaSpot);
		}
	}

	public static List<Long> getDuration() {
		A2023Day12 d = new A2023Day12(1);
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

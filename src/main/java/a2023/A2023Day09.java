package a2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class A2023Day09 extends A2023 {

	public A2023Day09(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day09 d = new A2023Day09(9);
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

	public Long s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		TheGame tg = getTheGame(inputL);
		tg.solve1();
		Long res=0L;
		for(Sequence seq:tg.seqs) {
			res+= seq.seq.get(0).get(seq.seq.get(0).size()-1);
		}
		return res;
		
	}

	public Long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		TheGame tg = getTheGame(inputL);
		tg.solve2();
		Long res=0L;
		for(Sequence seq:tg.seqs) {
			res+= seq.seq.get(0).get(0);
		}
		return res;

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class TheGame {
		public void solve1() {
			for (Sequence sequence : seqs) {
				goDeep(sequence);
				goUp(sequence);
			}
		}

		public void solve2() {
			for (Sequence sequence : seqs) {
				goDeep(sequence);
				goUp2(sequence);
			}
			
		}

		private void goUp2(Sequence sequence) {
			for (int j = sequence.seq.size() - 1; j >0; j--) {
				Long firstJ = sequence.seq.get(j).get(0);
				Long firstBeforeJ = sequence.seq.get(j-1).get(0);
				sequence.seq.get(j-1).add(0,firstBeforeJ-firstJ);
			}
			
		}

		private void goUp(Sequence sequence) {
			for (int j = sequence.seq.size() - 1; j >0; j--) {
				Long lastJ = sequence.seq.get(j).get(sequence.seq.get(j).size()-1);
				Long lastBeforeJ = sequence.seq.get(j-1).get(sequence.seq.get(j-1).size()-1);
				sequence.seq.get(j-1).add(lastJ+lastBeforeJ);
			}
		}

		private void goDeep(Sequence sequence) {
			List<Long> lastList = sequence.getLastList();
			if (lastList.stream().allMatch(i -> i == 0L)) {
				lastList.add(0L);
			} else {
				sequence.ajouterSub();
				goDeep(sequence);
			}
		}

		List<Sequence> seqs;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Sequence {

		public List<Long> getLastList() {
			int m = seq.size();
			return seq.get(m - 1);
		}

		public List<Long> getBeforeLastList() {
			int m = seq.size();
			return seq.get(m - 2);
		}

		public void ajouterSub() {
			List<Long> lastSeq = getLastList();
			List<Long> valueSeqSubseq = new ArrayList<Long>();
			for (int i = 0; i < lastSeq.size() - 1; i++) {
				valueSeqSubseq.add(lastSeq.get(i + 1) - lastSeq.get(i));
			}
			List<List<Long>> ll = new ArrayList<List<Long>>();
			ll.addAll(seq);
			ll.add(valueSeqSubseq);
			seq = new ArrayList<List<Long>>();
			seq.addAll(ll);
		}

		List<List<Long>> seq;

	}

	private TheGame getTheGame(List<String> inputL) {
		TheGame tg = new TheGame();
		List<Sequence> seqs = new ArrayList<>();
		for (String s : inputL) {
			List<Long> seq = new ArrayList<Long>();
			String[] sp = s.split(" ");
			for (int i = 0; i < sp.length; i++) {
				seq.add(Long.valueOf(sp[i].trim()));
			}
			Sequence sequ = new Sequence();
			sequ.setSeq(Arrays.asList(seq));
			seqs.add(sequ);
		}
		tg.setSeqs(seqs);
		return tg;
	}

	public static List<Long> getDuration() {
		A2023Day09 d = new A2023Day09(9);
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

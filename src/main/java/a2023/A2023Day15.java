package a2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class A2023Day15 extends A2023 {

	public A2023Day15(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day15 d = new A2023Day15(15);
		
		long startTime = System.currentTimeMillis();
		d.s1(true);
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
		List<String> mots = Arrays.asList(inputL.get(0).split(",")).stream().map(String::trim).toList();
		Long sum = 0L;
		for (String mot : mots) {
			sum += returnHashValue(mot);
		}
		return sum;
	}

	public static int returnHashValue(String mot) {
		int cur = 0;
		for (int i = 0; i < mot.length(); i++) {
			cur += toAscii(mot.substring(i, i + 1));
			cur *= 17;
			cur = cur % 256;
		}
		return cur;
	}

	private static int toAscii(String letter) {
		return letter.charAt(0);
	}

	public Long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		TheGame tg=getTheGame(inputL);
		tg.ranger();
		return tg.calculer();
	}
	
	private TheGame getTheGame(List<String> inputL) {
		List<String> mots = Arrays.asList(inputL.get(0).split(",")).stream().map(String::trim).toList();
		TheGame tg=new TheGame();
		Map<Integer,LinkedList<Lens>> lensSlots=new HashMap<>();
		List<Lens> lenses=new ArrayList<>();
		for(String mot:mots) {
			Lens l=new Lens();
			String[] sp=mot.split("=");{
				if(sp.length==1) {
					l.setLabel(sp[0].substring(0, sp[0].length()-1));
					l.setOp("-");
				}else {
				
					l.setLabel(sp[0]);
					l.setOp("=");
					l.setFocal(Integer.parseInt(sp[sp.length-1]));
				}
			}
			lenses.add(l);
		}
		for(int i=0;i<256;i++) {
			lensSlots.put(i, new LinkedList<>());
		}
		tg.setLenses(lenses);
		tg.setLensSlots(lensSlots);
		return tg;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class TheGame{
		Map<Integer,LinkedList<Lens>> lensSlots;
		List<Lens> lenses;
		@Override
		public String toString() {
			StringBuilder res=new StringBuilder();

				res.append(lenses).append("\n");
			
			for(Integer boxNum:lensSlots.keySet()) {
				if(lensSlots.get(boxNum).size()>0) {
					res.append(boxNum).append(lensSlots.get(boxNum))	;
					res.append("\n");
				}
				
			}
			return res.toString();
		}
		public Long calculer() {
			Long sum=0L;
			for(Integer boxNum:lensSlots.keySet()) {
				for(int i=0;i<lensSlots.get(boxNum).size();i++) {
					sum+=(boxNum+1)*(i+1)*lensSlots.get(boxNum).get(i).focal;
				}
			}
			return sum;
		}
		public void ranger() {
			for(int i=0;i<lenses.size();i++) {
				ranger(i);
			}	
		}
		private void ranger(int i) {
			Lens l=lenses.get(i);
			int numbox=returnHashValue(l.label);
			if(l.op.equals("-")) {
				lensSlots.get(numbox).remove(l);
			}else {
				if(lensSlots.get(numbox).contains(l)) {
					int index=lensSlots.get(numbox).indexOf(l);
					lensSlots.get(numbox).remove(l);
					lensSlots.get(numbox).add(index, l);
				}else {
					lensSlots.get(numbox).add(l);
				}		
			}
		}
		
	}
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Lens{
		String label;
		String op;
		Integer focal;
		@Override
		public String toString() {
			String val =focal == null? "" : String.valueOf(focal);
			return label +op +val;
		}
		@Override
		public int hashCode() {
			return Objects.hash(label);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Lens other = (Lens) obj;
			return Objects.equals(label, other.label);
		}
		
		
	}

	public static List<Long> getDuration() {
		A2023Day15 d = new A2023Day15(1);
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

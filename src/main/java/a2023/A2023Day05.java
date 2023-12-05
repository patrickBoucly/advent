package a2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import a2023.A2023Day05.Seed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class A2023Day05 extends A2023 {

	public A2023Day05(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day05 d = new A2023Day05(5);
		System.out.println(d.s1(false));
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

	public int s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		System.out.println(inputL);
		TheGame tg = getTheGame(inputL);
		tg.evaluerSeeds();
		for (Seed sd : tg.seeds) {
			System.out.println(sd);
		}
		return 0;
	}

	private TheGame getTheGame(List<String> inputL) {
		TheGame tg = new TheGame();
		List<String> cate=new ArrayList<String>();
		cate.addAll(List.of("seed-to-soil","soil-to-fertilizer","fertilizer-to-water","water-to-light","light-to-temperature","temperature-to-humidity","humidity-to-location"));
		tg.setCate(cate);
		List<Seed> seeds = new ArrayList<>();
		Map<String, List<Instruction>> instructionsByCategory = new HashMap<>();
		String cat = "";
		int numCat = -1;
		int numRow = 0;
		for (String s : inputL) {

			if (s.contains("seeds:")) {
				String[] sp = s.substring(s.trim().indexOf(":") + 1).trim().split(" ");
				for (String st : sp) {
					Seed sd = new Seed();
					sd.setSeedNum(Long.valueOf(st.trim()));
					sd.setCorrespondingValues(new HashMap<>());
					sd.setCurVal(sd.getSeedNum());
					seeds.add(sd);

				}
			} else {

				if (s.contains(":")) {
					cat = s.split(":")[0].trim().split(" ")[0].trim();
					numRow = 0;
					numCat++;
					instructionsByCategory.put(cat, new ArrayList<A2023Day05.Instruction>());
				} else if (s.trim().length() > 0) {
					String[] sp = s.split(" ");
					Instruction ins = new Instruction();
					ins.setCategoryName(cat);
					ins.setId(numCat);
					ins.setNumRow(numRow);
					ins.setDestinationRangeStart(Long.valueOf(sp[0].trim()));
					ins.setSourceRangeStart(Long.valueOf(sp[1].trim()));
					ins.setRangeLength(Long.valueOf(sp[2].trim()));
					List<Instruction> l = new ArrayList<>(instructionsByCategory.get(cat));
					l.add(ins);
					instructionsByCategory.put(cat, l);
				}
			}
		}
		tg.setSeeds(seeds);
		tg.setInstructionsByCategory(instructionsByCategory);
		return tg;
	}

	public int s2(boolean b) {
		return 0;

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Instruction {
		int id;
		int numRow;
		String categoryName;
		Long destinationRangeStart;
		Long sourceRangeStart;
		Long rangeLength;

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Seed {
		Long seedNum;
		Long curVal;
		Map<String, Long> correspondingValues;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class TheGame {
		List<String> cate=new ArrayList<>();
		
		public void evaluerSeeds() {
			seeds.stream().forEach(see -> evaluerSeed(see));
		}

		private void evaluerSeed(Seed sd) {
			for (int i=0;i<instructionsByCategory.size();i++) {
				 List<Instruction> cat = instructionsByCategory.get(i);
				 instructionsByCategory.g
				for (Instruction ins : instructionsByCategory.get(cat)) {
					if (sd.curVal >= ins.getSourceRangeStart()
							&& sd.curVal <= ins.getSourceRangeStart() + ins.getRangeLength()) {
						sd.correspondingValues.put(cat,
								sd.curVal - ins.getSourceRangeStart() + ins.getDestinationRangeStart());
						sd.curVal=sd.correspondingValues.get(cat);
					}
				}
			}
			for (String cat : instructionsByCategory.keySet()) {
				if (!sd.getCorrespondingValues().containsKey(cat)) {
					sd.correspondingValues.put(cat, sd.seedNum);
				}
			}

		}

		List<Seed> seeds;
		Map<String, List<Instruction>> instructionsByCategory;
		Map<String, Long> correspondingValues;
	}

	public static List<Long> getDuration() {
		A2023Day05 d = new A2023Day05(5);
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

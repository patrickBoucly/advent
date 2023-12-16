package a2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import outils.MesOutils;

public class A2023Day05 extends A2023 {

	public A2023Day05(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day05 d = new A2023Day05(5);
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
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().toList();
		TheGame tg = getTheGame(inputL);
		tg.evaluerSeeds();
		Long res = Long.MAX_VALUE;
		for (Seed sd : tg.seeds) {
			/// System.out.println(sd);
			if (sd.getCorrespondingValues().get("humidity-to-location") < res) {
				res = sd.getCorrespondingValues().get("humidity-to-location");
			}
		}
		return res;
	}

	private TheGame getTheGame(List<String> inputL) {
		TheGame tg = new TheGame();
		List<String> cate = new ArrayList<>();
		cate.addAll(List.of("seed-to-soil", "soil-to-fertilizer", "fertilizer-to-water", "water-to-light",
				"light-to-temperature", "temperature-to-humidity", "humidity-to-location"));
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
					instructionsByCategory.put(cat, new ArrayList<>());
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

	public Long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().toList();
		TheGame tg = getTheGame2(inputL);
		List<Seed> seedsToControl = getSeedsToControl(tg);
		Long res = Long.MAX_VALUE;
		Long from = MesOutils.getMinLongFromList(tg.seedRanges.stream().map(SeedRange::getMinRange).toList()); // 
		Long to = MesOutils.getMaxLongFromList(tg.seedRanges.stream().map(SeedRange::getMaxRange).toList());
		System.out.println(from + " " + to);
		for (Long lg = from; lg < to; lg++) {
			if (lg % 1000000 == 0) {
				System.out.println((lg - from) / (to - from) * 100 + " " + lg + " " + res);
			}
			for (SeedRange sdr : tg.getSeedRanges()) {
				if (sdr.isInRange(lg)) {
					Seed s = new Seed();
					s.setCurVal(lg);
					s.setCorrespondingValues(new HashMap<>());
					tg.evaluerSeed(s);
					Long v = s.getCorrespondingValues().get("humidity-to-location");
					if (v < res) {
						res = v;
						System.out.println("res " + res + " " + lg);
					}
				}
			}

		}

		// réponse 60568880, trouvée en 21' seed n°920707452

		return MesOutils.getMinLongFromList(seedsToControl.stream().filter(se -> tg.isInOneRange(se))
				.map(se -> se.getCorrespondingValues().get("humidity-to-location")).toList());

	}

	private List<Seed> getSeedsToControl(TheGame tg) {
		Set<InterestingPoint> interestingPoints = new HashSet<>();
		for (int i = 0; i < tg.cate.size(); i++) {
			for (Instruction ins : tg.instructionsByCategory.get(tg.cate.get(i))) {
				interestingPoints.add(new InterestingPoint(ins.sourceRangeStart, tg.cate.get(i)));
				interestingPoints.add(new InterestingPoint(ins.destinationRangeStart, tg.cate.get(i)));
			}
		}
		interestingPoints.add(new InterestingPoint(80L, "seed-to-soil"));
		interestingPoints.add(new InterestingPoint(82L, "seed-to-soil"));
		for (InterestingPoint pt : interestingPoints) {
			pt.findLocationValue(tg);
			pt.findSeedOrigin(tg);
		}
		List<Seed> seedsToControl = new ArrayList<>();
		for (InterestingPoint pt : interestingPoints) {
			Seed sd = new Seed();
			sd.setSeedNum(pt.initValue);
			Map<String, Long> correspondingValues = new HashMap<String, Long>();
			correspondingValues.put("humidity-to-location", pt.getLocationValue());
			sd.setCorrespondingValues(correspondingValues);
			seedsToControl.add(sd);
		}
		return seedsToControl;
	}

	private TheGame getTheGame2(List<String> inputL) {
		TheGame tg = new TheGame();
		List<String> cate = new ArrayList<>();
		List<SeedRange> seedRanges = new ArrayList<>();
		cate.addAll(List.of("seed-to-soil", "soil-to-fertilizer", "fertilizer-to-water", "water-to-light",
				"light-to-temperature", "temperature-to-humidity", "humidity-to-location"));
		tg.setCate(cate);
		List<Seed> seeds = new ArrayList<>();
		Map<String, List<Instruction>> instructionsByCategory = new HashMap<>();
		String cat = "";
		int numCat = -1;
		int numRow = 0;
		for (String s : inputL) {
			if (s.contains("seeds:")) {
				String[] sp = s.substring(s.trim().indexOf(":") + 1).trim().split(" ");
				Long deb = 0L;
				for (int i = 0; i < sp.length; i++) {

					if (i % 2 == 0) {
						deb = Long.valueOf(sp[i].trim());
					} else {
						SeedRange sr = new SeedRange();
						sr.setMinRange(deb);
						sr.setMaxRange(deb + Long.valueOf(sp[i].trim()));
						seedRanges.add(sr);
					}

				}
				tg.seedRanges = seedRanges;
			} else {
				if (s.contains(":")) {
					cat = s.split(":")[0].trim().split(" ")[0].trim();
					numRow = 0;
					numCat++;
					instructionsByCategory.put(cat, new ArrayList<>());
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
	@ToString
	public static class InterestingPoint {
		Long initValue;
		Long value;
		Long nextValue;
		String cat;
		Long seedNumOrigin;
		Long locationValue;
		List<String> catVus;

		public InterestingPoint(Long value, String cat) {
			super();
			this.value = value;
			this.nextValue = value;
			this.cat = cat;
			this.initValue = value;
		}

		public void findSeedOrigin(TheGame tg) {
			value = initValue;
			boolean debute = false;
			catVus = new ArrayList<>();
			for (int i = tg.cate.size() - 1; i >= 0; i--) {
				for (Instruction ins : tg.instructionsByCategory.get(tg.cate.get(i))) {
					String categorie = tg.cate.get(i);
					if (!debute && categorie.equals(cat)) {
						debute = true;
					} else {
						if (debute && !categorie.equals(cat)) {
							if (value >= ins.getSourceRangeStart()
									&& value < ins.getSourceRangeStart() + ins.getRangeLength()) {
								nextValue = value - ins.getSourceRangeStart() + ins.getDestinationRangeStart();
								catVus.add(categorie);
							}

						}
						// System.out.println(sd.getCorrespondingValues());
						if (!catVus.contains(categorie)) {
							nextValue = value;
							catVus.add(categorie);
						}
						value = nextValue;
					}
				}

			}
			seedNumOrigin = value;
		}

		public void findLocationValue(TheGame tg) {
			boolean debute = false;
			catVus = new ArrayList<>();
			for (int i = 0; i < tg.cate.size(); i++) {
				for (Instruction ins : tg.instructionsByCategory.get(tg.cate.get(i))) {
					String categorie = tg.cate.get(i);
					if (!debute && categorie.equals(cat)) {
						debute = true;
					} else {
						if (debute && !categorie.equals(cat)) {
							if (value >= ins.getSourceRangeStart()
									&& value < ins.getSourceRangeStart() + ins.getRangeLength()) {
								nextValue = value - ins.getSourceRangeStart() + ins.getDestinationRangeStart();
								catVus.add(categorie);
							}

						}
						// System.out.println(sd.getCorrespondingValues());
						if (!catVus.contains(categorie)) {
							nextValue = value;
							catVus.add(categorie);
						}
						value = nextValue;
					}
				}

			}
			locationValue = value;
		}

		@Override
		public int hashCode() {
			return Objects.hash(cat, value);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			InterestingPoint other = (InterestingPoint) obj;
			return Objects.equals(cat, other.cat) && Objects.equals(value, other.value);
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Seed {
		Long seedNum;
		Long curVal;
		Long nextCurVal;
		Map<String, Long> correspondingValues;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class SeedRange {
		Long minRange;
		Long maxRange;

		public boolean isInRange(Long l) {
			return l >= minRange && l <= maxRange;
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class TheGame {
		List<String> cate = new ArrayList<>();

		public void evaluerSeeds() {
			seeds.stream().forEach(see -> evaluerSeed(see));
		}

		public boolean isInOneRange(Seed se) {
			return seedRanges.stream().anyMatch(sr -> sr.isInRange(se.seedNum));
		}

		private void evaluerSeed(Seed sd) {
			for (int i = 0; i < cate.size(); i++) {
				for (Instruction ins : instructionsByCategory.get(cate.get(i))) {
					if (sd.curVal >= ins.getSourceRangeStart()
							&& sd.curVal < ins.getSourceRangeStart() + ins.getRangeLength()) {
						// System.out.println(cate.get(i));
						// System.out.println(sd);
						// System.out.println(ins);
						sd.nextCurVal = sd.curVal - ins.getSourceRangeStart() + ins.getDestinationRangeStart();
						sd.correspondingValues.put(cate.get(i), sd.nextCurVal);
					}

				}
				// System.out.println(sd.getCorrespondingValues());
				if (!sd.getCorrespondingValues().containsKey(cate.get(i))) {
					sd.nextCurVal = sd.curVal;
					sd.correspondingValues.put(cate.get(i), sd.curVal);
				}
				sd.curVal = sd.nextCurVal;
			}

		}

		List<SeedRange> seedRanges;
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

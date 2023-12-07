package a2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import a2023.A2023Day06.RaceRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class A2023Day06 extends A2023 {

	public A2023Day06(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day06 d = new A2023Day06(6);
		//System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
	//	d.s1(true);
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
		Races races=getRacesFromInput(inputL);
		races.findNbPossibleVictory();
		return races.getS1();
	}

	private Races getRacesFromInput(List<String> inputL) {
		Races theRaces=new Races();
		String[] sp1=inputL.get(0).split(":")[1].trim().split(",");
		String[] sp2=inputL.get(1).split(":")[1].trim().split(",");
		List<RaceRecord> racesRecords=new ArrayList<>();
		for(int i=0;i<sp1.length;i++) {
			RaceRecord r=new RaceRecord();
			r.setIdRaceRecord(i);
			r.setRaceDuration(Long.valueOf(sp1[i].trim()));
			r.setDistanceToBeat(Long.valueOf(sp2[i].trim()));
			racesRecords.add(r);
		}
		theRaces.setRacesRecords(racesRecords);
		return theRaces;
	}

	public int s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		System.out.println(inputL);
		Races races=getRaces2FromInput(inputL);
		races.findNbPossibleVictory();
		return races.getS1();

	}
	
	private Races getRaces2FromInput(List<String> inputL) {
		Races theRaces=new Races();
		String[] sp1=inputL.get(0).split(":")[1].trim().replace(",","").split(",");
		String[] sp2=inputL.get(1).split(":")[1].trim().replace(",","").split(",");
		List<RaceRecord> racesRecords=new ArrayList<>();
		for(int i=0;i<sp1.length;i++) {
			RaceRecord r=new RaceRecord();
			r.setIdRaceRecord(i);
			r.setRaceDuration(Long.valueOf(sp1[i].trim()));
			r.setDistanceToBeat(Long.valueOf(sp2[i].trim()));
			racesRecords.add(r);
		}
		theRaces.setRacesRecords(racesRecords);
		return theRaces;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Races {
		public void findNbPossibleVictory() {
			Map<Integer,Long> nbPossibleVictory=new HashMap<>();
			
			for(RaceRecord rr:racesRecords) {
				nbPossibleVictory.put(rr.idRaceRecord, getNbPossibleVictoryThisRace(rr));
			}
			this.nbPossibleVictory=nbPossibleVictory;
		}
		private Long getNbPossibleVictoryThisRace(RaceRecord rr) {
			Long firstVicIndice=-1L;
			Long lastVicIndice=-1L;
			Long firstLookIndice=0L;
			Long lastLookIndice=rr.getRaceDuration();
			boolean firstFound=false;
			boolean lastFound=false;
			while(!firstFound) {
				Race race=new Race();
				race.buttonPushDuration=firstLookIndice;
				race.setRaceRecord(rr);
				race.CalculerDistance();
				if(race.distanceParcouru>rr.distanceToBeat) {
					firstFound=true;
					firstVicIndice=firstLookIndice;
				}
				firstLookIndice++;
			}
			while(!lastFound) {
				Race race=new Race();
				race.buttonPushDuration=lastLookIndice;
				race.setRaceRecord(rr);
				race.CalculerDistance();
				if(race.distanceParcouru>rr.distanceToBeat) {
					lastFound=true;
					lastVicIndice=lastLookIndice;
				}
				lastLookIndice--;
			}
			return lastVicIndice-firstVicIndice+1;
		}
		public int getS1() {
			int res=1;
			for(Integer j:nbPossibleVictory.keySet()) {
				res*=nbPossibleVictory.get(j);
			}
			return res;
		}
		List<Race> races;
		List<RaceRecord> racesRecords;
		Map<Integer, Long> nbPossibleVictory;
	}
	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class RaceRecord {
		int idRaceRecord;
		Long raceDuration;
		Long distanceToBeat;
	
	}
	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Race {
		public void CalculerDistance() {
			distanceParcouru=buttonPushDuration*(raceRecord.getRaceDuration()-buttonPushDuration);
			
		}
		int id;
		RaceRecord raceRecord;
		Long distanceParcouru;
		Long buttonPushDuration;
	}

	public static List<Long> getDuration() {
		A2023Day06 d = new A2023Day06(6);
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

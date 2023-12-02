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

@Getter
@Setter

public class A2023Day02 extends A2023 {

	public A2023Day02(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day02 d = new A2023Day02(2);
		System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		d.s1(true);
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
		TheGame tg = getTheGame(inputL);
		tg.countValidGame();
		return tg.getSumIdGameValid();
	}

	public int s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		TheGame tg = getTheGame(inputL);
		for (Game g : tg.games) {
			g.calculerPuissance();
		}
		tg.countPuissance();
		return tg.getSumPuissance();
	}

	private TheGame getTheGame(List<String> inputL) {
		TheGame tg = new TheGame(new ArrayList<>(), 0, 13, 14, 12, 0);
		List<Game> games = new ArrayList<>();
		for (String l : inputL) {
			String[] split1 = l.split(":");
			String[] split12 = split1[0].split(" ");
			int id = Integer.valueOf(split12[1]);
			String[] split3 = l.substring(l.indexOf(":") + 1).trim().split(";");
			Game g = new Game();
			g.setId(id);
			List<Tirage> tirages = new ArrayList<>();
			for (String t : split3) {
				String[] cubesTire = t.trim().split(",");
				Tirage tir = new Tirage();
				int nbRed = 0;
				int nbGreen = 0;
				int nbBlue = 0;
				for (String st : cubesTire) {
					String[] split4 = st.trim().split(" ");
					if (split4[1].equals("green")) {
						nbGreen += Integer.valueOf(split4[0]);
					}
					if (split4[1].equals("blue")) {
						nbBlue += Integer.valueOf(split4[0]);
					}
					if (split4[1].equals("red")) {
						nbRed += Integer.valueOf(split4[0]);
					}
				}
				tir.setNbBlue(nbBlue);
				tir.setNbGreen(nbGreen);
				tir.setNbRed(nbRed);
				tirages.add(tir);
			}
			g.setTirages(tirages);
			games.add(g);
		}
		tg.setGames(games);
		return tg;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	public static class TheGame {
		public void countValidGame() {
			sumIdGameValid = 0;
			for (Game g : games) {
				boolean allTirageOK = true;
				for (Tirage t : g.getTirages()) {
					if (t.nbBlue > nbBlueMax || t.nbRed > nbRedMax || t.nbGreen > nbGreenMax) {
						allTirageOK = false;
					}
				}
				if (allTirageOK) {
					sumIdGameValid += g.getId();
				}
			}

		}

		public void countPuissance() {
			sumPuissance = 0;
			for (Game g : games) {
				sumPuissance += g.getPuissance();
			}

		}

		List<Game> games;
		int sumIdGameValid;
		int nbGreenMax;
		int nbBlueMax;
		int nbRedMax;
		int sumPuissance;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Game {
		public void calculerPuissance() {
			int reqB = 0;
			int reqG = 0;
			int reqR = 0;
			for (Tirage t : tirages) {
				if (t.nbBlue > reqB) {
					reqB = t.nbBlue;
				}
				if (t.nbRed > reqR) {
					reqR = t.nbRed;
				}
				if (t.nbGreen > reqG) {
					reqG = t.nbGreen;
				}
			}
			puissance = reqG * reqB * reqR;
		}

		int id;
		List<Tirage> tirages;
		int puissance;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Tirage {
		int nbRed;
		int nbGreen;
		int nbBlue;
	}

	public static List<Long> getDuration() {
		A2023Day02 d = new A2023Day02(2);
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

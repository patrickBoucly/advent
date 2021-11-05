package aocmaven.a2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import aocmaven.a2018.Day13A2018.Plan;
import aocmaven.a2018.Day13A2018.Train;

public class Day15A2018 {

	public static void main(String[] args0) {
		s1();
		// s2();
	}

	private static void s2() {
		String input = read();

	}

	private static void s1() {
		String input = read();
		Board board = getBoard(input);
		board.initialiser();
		board.passerUnTour();

		System.out.println(board);
		System.out.println(board.combattants.stream().filter(c -> c.type.equals("E")).count());
		System.out.println(board.combattants.stream().filter(c -> c.type.equals("G")).count());

	}

	private static Board getBoard(String input) {

		String[] lignes = input.split("\n");
		int nbLignes = lignes.length;
		int longeurLigne = lignes[0].length();
		String[][] board = new String[longeurLigne][nbLignes];
		for (int j = 0; j < nbLignes; j++) {
			for (int i = 0; i < longeurLigne; i++) {
				board[i][j] = lignes[j].substring(i, i + 1);
			}
		}
		return new Board(board, longeurLigne, nbLignes);
	}

	public static class Board {
		String[][] board;
		int longeurLigne;
		int nbLignes;
		List<Combattant> combattants = new ArrayList<>();
		int round = 0;

		public Board(String[][] board, int longeurLigne, int nbLignes) {
			this.board = board;
			this.longeurLigne = longeurLigne;
			this.nbLignes = nbLignes;
		}

		public void passerUnTour() {
			combattants = ordreDePassage(combattants);
			boolean first = true;
			boolean fini = false;
			for (Combattant c : combattants) {
				if (first) {
					first = false;
					fini = seuleRace(combattants);
					if (fini) {
						break;
					}
				}
				action(c);
			}
			if (!fini) {
				round++;
			}

		}

		private void action(Combattant c) {
			String nord = board[c.x][c.y - 1];
			String sud = board[c.x][c.y + 1];
			String est = board[c.x + 1][c.y];
			String ouest = board[c.x - 1][c.y];
			c.voisinage.put("N", nord);
			c.voisinage.put("S", sud);
			c.voisinage.put("E", est);
			c.voisinage.put("O", ouest);
			if (c.voisinage.containsValue(c.autreRace())) {
				attaquer(c);
			} else {
				move(c);
			}

		}

		private void move(Combattant c) {
			List<String> directionPosssibles = new ArrayList<>();
			for (String caseDir : c.voisinage.keySet()) {
				if (c.voisinage.get(caseDir).equals(".")) {
					directionPosssibles.add(caseDir);
				}
			}
			if (!directionPosssibles.isEmpty()) {
				c.distMinEnnemi = calculerCoutDeplacement(c, directionPosssibles);
				String ouBouger = choisirDestination(c);
				if (ouBouger.equals("N")) {
					c.setY(c.getY() - 1);
				} else if (ouBouger.equals("S")) {
					c.setY(c.getY() + 1);
				} else if (ouBouger.equals("E")) {
					c.setX(c.getX() + 1);
				} else {
					c.setX(c.getX() - 1);
				}

			}

		}

		private HashMap<String, Integer> calculerCoutDeplacement(Combattant c, List<String> directionPosssibles) {
			// TODO Auto-generated method stub
			return null;
		}

		private String choisirDestination(Combattant c) {
			int distMin = 100;
			String bonneDir = "";
			for (String caseDir : c.distMinEnnemi.keySet()) {
				if (c.distMinEnnemi.get(caseDir) < distMin) {
					distMin = c.distMinEnnemi.get(caseDir);
					bonneDir = caseDir;
				} else if (c.distMinEnnemi.get(caseDir) == distMin) {
					if(!bonneDir.equals("N") && (bonneDir.equals("S") || (bonneDir.equals("E") && caseDir.equals("O")))) {		
							bonneDir = caseDir;
					}
				}

			}
			return bonneDir;

		}

		private void attaquer(Combattant c) {
			List<Combattant> opponents = new ArrayList<>();
			for (String dir : c.getVoisinage().keySet()) {
				if (c.getVoisinage().get(dir).equals(c.autreRace())) {
					opponents.add(getOpponnent(c, dir));
				}
			}
			Combattant opponent = selectOpponnent(opponents);
			opponent.setHp(opponent.hp - 3);
			if (opponent.getHp() <= 0) {
				combattants.remove(opponentFromId(opponent.getId()));
			}
		}

		private Object opponentFromId(int id) {
			Combattant res = null;
			for (Combattant c : combattants) {
				if (c.id == id) {
					res = c;
				}
			}
			return res;
		}

		private Combattant selectOpponnent(List<Combattant> opponents) {
			opponents.sort(Comparator.comparing(Combattant::getHp).thenComparing(Combattant::getY)
					.thenComparing(Combattant::getX));
			return opponents.get(0);
		}

		private Combattant getOpponnent(Combattant c, String dir) {
			Combattant opponent = null;
			int xo = c.x;
			int yo = c.y;
			if (dir.equals("N")) {
				yo = c.y - 1;
			} else if (dir.equals("S")) {
				yo = c.y + 1;
			} else if (dir.equals("E")) {
				xo = c.x + 1;
			} else {
				xo = c.x - 1;
			}
			for (Combattant c2 : combattants) {
				if (c2.x == xo && c2.y == yo) {
					opponent = c2;
				}
			}

			return opponent;
		}

		private boolean seuleRace(List<Combattant> combattants) {
			boolean res = true;
			for (int j = 1; j < combattants.size(); j++) {
				if (!combattants.get(j).type.equals(combattants.get(0).type)) {
					res = false;
				}
			}
			return res;
		}

		private List<Combattant> ordreDePassage(List<Combattant> combattants) {
			combattants.sort(Comparator.comparing(Combattant::getY).thenComparing(Combattant::getX));
			return combattants;
		}

		public void initialiser() {
			int id = 1;
			for (int j = 0; j < nbLignes; j++) {
				for (int i = 0; i < longeurLigne; i++) {
					String s = board[i][j];
					if (s.equals("G")) {
						combattants.add(new Combattant(id, 200, "G", i, j));
						id++;
					} else if (s.equals("E")) {
						combattants.add(new Combattant(id, 200, "E", i, j));
						id++;
					}
				}
			}
		}

		public void set(int x, int y, String string) {
			board[x][y] = string;

		}

		public String getCarac(int i, int j) {
			return board[i][j];
		}

		@Override
		public String toString() {
			String res = "";
			for (int j = 0; j < nbLignes; j++) {
				StringBuilder ligne = new StringBuilder();
				for (int i = 0; i < longeurLigne; i++) {
					ligne.append(board[i][j]);
				}
				res += ligne.toString();
				res += "\n";
			}
			return res;

		}

	}

	public static class Combattant {
		int id;
		int hp;
		String type;
		int x;
		int y;
		HashMap<String, String> voisinage = new HashMap<>();
		HashMap<String, Integer> distMinEnnemi = new HashMap<>();

		public int getHp() {
			return hp;
		}

		public String autreRace() {
			if (type.equals("E")) {
				return "G";
			}
			return "E";
		}

		public HashMap<String, String> getVoisinage() {
			return voisinage;
		}

		public HashMap<String, Integer> getDistMinEnnemi() {
			return distMinEnnemi;
		}

		public void setDistMinEnnemi(HashMap<String, Integer> distMinEnnemi) {
			this.distMinEnnemi = distMinEnnemi;
		}

		public void setVoisinage(HashMap<String, String> voisinage) {
			this.voisinage = voisinage;
		}

		public void setHp(int hp) {
			this.hp = hp;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public Combattant(int id, int hp, String type, int x, int y) {
			super();
			this.id = id;
			this.hp = hp;
			this.type = type;
			this.x = x;
			this.y = y;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	}

	private static String read() {
		Path path = Paths.get(
				"C:\\git_repositories\\advent\\src\\main\\resources\\src\\advent_of_code\\main\\resources\\a2018\\input15");
		String content = "";
		try {
			content = Files.readString(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;

	}
}

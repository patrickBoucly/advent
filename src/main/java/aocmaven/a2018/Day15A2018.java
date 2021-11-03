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
		//s2();
	}

	private static void s2() {
		String input = read();
	
	}

	
	private static void s1() {
		String input = read();
		Board board=getBoard(input);
		board.initialiser();
		board.passerUnTour();
		
		System.out.println(board);
		
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
		List<Combattant> combattants=new ArrayList<>();
		int round=0;
		public Board(String[][] board, int longeurLigne, int nbLignes) {
			this.board = board;
			this.longeurLigne = longeurLigne;
			this.nbLignes = nbLignes;
		}
		public void passerUnTour() {
			combattants=ordreDePassage(combattants);
			boolean first=true;
			boolean fini=false;
			for(Combattant c:combattants) {
				if(first) {
					first=false;
					fini=seuleRace(combattants);
					if(fini) {
						break;
					}
				}
				c.action();	
			}
			if(!fini) {
				round++;
			}
			
			
		}
		private boolean seuleRace(List<Combattant> combattants) {
			boolean res=true;
			for(int j=1;j<combattants.size();j++) {
				if(!combattants.get(j).type.equals(combattants.get(0).type)) {
					res=false;
				}
			}
			return res;
		}
		private List<Combattant> ordreDePassage(List<Combattant> combattants) {
			combattants.sort( Comparator.comparing(Combattant::getY).thenComparing(Combattant::getX));
			return combattants;
		}

		public void initialiser() {
			for (int j = 0; j < nbLignes; j++) {
				for (int i = 0; i < longeurLigne; i++) {
					String s=board[i][j];
					if(s.equals("G") ) {
						combattants.add(new Combattant(200,"G", i, j));
					} else if (s.equals("E")) {
						combattants.add(new Combattant(200,"E", i, j));
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
		int hp;
		String type;
		int x;
		int y;
		public int getHp() {
			return hp;
		}
		public void action() {
			// TODO Auto-generated method stub
			
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
		public Combattant(int hp, String type, int x, int y) {
			super();
			this.hp = hp;
			this.type = type;
			this.x = x;
			this.y = y;
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

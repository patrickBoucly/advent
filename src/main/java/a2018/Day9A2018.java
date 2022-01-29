package a2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Day9A2018 {

	  public static void main(String[] args0) {
		  s1();
	  }

	private static void s1() {
		String input=read();
		Partie p=new Partie(Integer.parseInt(input.split(" ")[6]),0,0,0,new ArrayList<Integer>(),Integer.parseInt(input.split(" ")[0]));
		
		p.addBille(0,p.getValeurBilleActuelle());
			
		while(p.valeurBilleActuelle != p.getValeurDerniereBille() ) {
			p.passerUnTour();
			if(p.valeurBilleActuelle % 1000==0) {
			System.out.println(p.valeurBilleActuelle);
			}
		}
		System.out.println(p.scores);
		int max=p.scores.values().stream().mapToInt(v -> v).max().getAsInt();
		System.out.println(max);
		
	}

	private static int getNewIndice(int positionBilleActuelle, List<Integer> listeBille) {
		
		if(positionBilleActuelle==listeBille.size()) {
			return 1;
		}
		return positionBilleActuelle+2;
	}

	private static String read() {
		 Path path = Paths.get("C:\\git_repositories\\aoc\\src\\advent_of_code\\main\\resources\\a2018\\input9");
		 String content="";
	     try {
	         content = Files.readString(path);
	     }catch (IOException e){
	         e.printStackTrace();
	     }
		return content;

	}
	
	public static class Partie {
		int valeurDerniereBille;
		Integer nbPlayer;
		int tour;
		int valeurBilleActuelle;
		int positionBilleActuelle;
		int currentPlayer=1;
		List<Integer> listeBille= new ArrayList<Integer>();
		HashMap<Integer,Integer> scores = new HashMap<>();
		public Partie(int valeurDerniereBille, int tour, int valeurBilleActuelle, int positionBilleActuelle,
				List<Integer> listeBille, Integer nbPlayer) {
			super();
			this.valeurDerniereBille = valeurDerniereBille;
			this.tour = tour;
			this.valeurBilleActuelle = valeurBilleActuelle;
			this.positionBilleActuelle = positionBilleActuelle;
			this.listeBille = listeBille;
			this.nbPlayer=nbPlayer;
			for(int i=1;i<=nbPlayer;i++) {
				scores.put(i,0);
			}
		}
		public void addBille(int i, int valeurBilleActuelle) {
			listeBille.add(i,valeurBilleActuelle);
			
		}
		public int getValeurDerniereBille() {
			return valeurDerniereBille;
		}
		public void setValeurDerniereBille(int valeurDerniereBille) {
			this.valeurDerniereBille = valeurDerniereBille;
		}
		public int getTour() {
			return tour;
		}
		public void setTour(int tour) {
			this.tour = tour;
		}
		public int getValeurBilleActuelle() {
			return valeurBilleActuelle;
		}
		public void setValeurBilleActuelle(int valeurBilleActuelle) {
			this.valeurBilleActuelle = valeurBilleActuelle;
		}
		public int getPositionBilleActuelle() {
			return positionBilleActuelle;
		}
		public void setPositionBilleActuelle(int positionBilleActuelle) {
			this.positionBilleActuelle = positionBilleActuelle;
		}
		public List<Integer> getListeBille() {
			return listeBille;
		}
		public void setListeBille(List<Integer> listeBille) {
			this.listeBille = listeBille;
		}
		public Integer getNbPlayer() {
			return nbPlayer;
		}
		public void setNbPlayer(Integer nbPlayer) {
			this.nbPlayer = nbPlayer;
		}
		public HashMap<Integer, Integer> getScores() {
			return scores;
		}
		public void setScores(HashMap<Integer, Integer> scores) {
			this.scores = scores;
		}
		
		public int getCurrentPlayer() {
			return currentPlayer;
		}
		public void setCurrentPlayer(int currentPlayer) {
			this.currentPlayer = currentPlayer;
		}
		public void passerUnTour() {
			this.tour++;
			this.valeurBilleActuelle++;
			if(currentPlayer==nbPlayer) {
				currentPlayer=1;
			} else {
				currentPlayer++;
			}
			if(valeurBilleActuelle % 23 != 0) {
			positionBilleActuelle=getNewPos();
			addBille(positionBilleActuelle,valeurBilleActuelle);
			} else {
				scores.put(currentPlayer,scores.get(currentPlayer)+valeurBilleActuelle);
				int indiceMoins7=getIndiceMoins7();
				int valBilleRetiree=listeBille.remove(indiceMoins7);
				scores.put(currentPlayer,scores.get(currentPlayer)+valBilleRetiree);
				positionBilleActuelle=getNowPosePlus1(indiceMoins7);
			}
		}
		private int getNowPosePlus1(int indiceMoins7) {
			if(indiceMoins7 != listeBille.size()-1) {
				return indiceMoins7;
			}
			return 0;
		}
		private int getIndiceMoins7() {
			if(positionBilleActuelle>=7) {
				return positionBilleActuelle-7;
			} else {
				return listeBille.size()-7+positionBilleActuelle;
			}

		}
		private int getNewPos() {
			if(tour < 3) {
				return 1;
			} else if(positionBilleActuelle == listeBille.size()-1){
				return 1;
			} else {
				return positionBilleActuelle+2;
			}
			
		}
	}
	}
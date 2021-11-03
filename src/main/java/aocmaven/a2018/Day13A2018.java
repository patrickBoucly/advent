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

public class Day13A2018 {

	public static void main(String[] args0) {
		//s1();
		s2();
	}

	private static void s2() {
		String input = read();
		Plan plan = getPlan(input);
		// System.out.println(plan);
		List<Train> trains = getTrains(plan);
		ReseauFerre sncf = new ReseauFerre(plan, trains);
		while (sncf.trains.size()>1) {
			sncf.avancerTrains();
		}
		System.out.println("Last train : " + sncf.trains.get(0) );
	}

	private static void s1() {
		String input = read();
		Plan plan = getPlan(input);
		// System.out.println(plan);
		List<Train> trains = getTrains(plan);
		ReseauFerre sncf = new ReseauFerre(plan, trains);
		while (!sncf.crash) {
			sncf.avancerTrains();
		}
		System.out.println("X : " + sncf.crashX + " Y : " + sncf.crashY);
	}

	private static Plan getPlan(String input) {

		String[] lignes = input.split("\n");
		int nbLignes = lignes.length;
		int longeurLigne = lignes[0].length();
		String[][] plan = new String[longeurLigne][nbLignes];
		for (int j = 0; j < nbLignes; j++) {
			for (int i = 0; i < longeurLigne; i++) {
				plan[i][j] = lignes[j].substring(i, i + 1);
			}
		}
		return new Plan(plan, longeurLigne, nbLignes);
	}

	private static List<Train> getTrains(Plan plan) {
		List<Train> trains = new ArrayList<>();
		List<String> trainCarac = new ArrayList<>();
		trainCarac.addAll(Arrays.asList(">", "<", "v", "^"));
		int id = 1;
		for (int j = 0; j < plan.nbLignes; j++) {
			for (int i = 0; i < plan.longeurLigne; i++) {
				String carac = plan.getCarac(i, j);
				if (trainCarac.contains(carac)) {
					trains.add(new Train(id, "r", carac, i, j));
					id++;
				}
			}

		}
		return trains;
	}

	public static class ReseauFerre {
		boolean crash = false;
		Train crashT1;
		Train crashT2;
		int crashX = -1;
		int crashY = -1;
		Plan plan;
		List<Train> trains;

		public ReseauFerre(Plan plan, List<Train> trains) {
			super();
			this.trains = trains;
			this.plan = removeTrains(plan);

		}

		public ArrayList<Train> clean() {
			ArrayList<Train> res=new ArrayList<>();
			res.add(crashT1);
			res.add(crashT2);
			return res;
			
		}

		public Train getTrain(int i) {
			Train ti = null;
			for (Train t : trains) {
				if (t.id == i) {
					ti = t;
				}
			}
			return ti;
		}

		public void avancerTrains() {
			System.out.println(trains);
			trains=ordreDePassage(trains);
			System.out.println(trains);
			List<Train> crashed = new ArrayList<>();
			for (Train t : trains) {
				if (t.direction.equals(">")) {
					if (plan.getCarac(t.x + 1, t.y).equals("-")) {
						t.x++;
					} else if (plan.getCarac(t.x + 1, t.y).equals("\\")) {
						t.x++;
						t.setDirection("v");
					} else if (plan.getCarac(t.x + 1, t.y).equals("/")) {
						t.x++;
						t.setDirection("^");
					} else if (plan.getCarac(t.x + 1, t.y).equals("+")) {
						t.x++;
						t.setDirection(t.getNextDirection());
					} else {
						System.out.println("erreur : " + plan.getCarac(t.x + 1, t.y));
					}
				} else if (t.direction.equals("<")) {
					if (plan.getCarac(t.x - 1, t.y).equals("-")) {
						t.x--;
					} else if (plan.getCarac(t.x - 1, t.y).equals("\\")) {
						t.x--;
						t.setDirection("^");
					} else if (plan.getCarac(t.x - 1, t.y).equals("/")) {
						t.x--;
						t.setDirection("v");
					} else if (plan.getCarac(t.x - 1, t.y).equals("+")) {
						t.x--;
						t.setDirection(t.getNextDirection());
					} else {
						System.out.println("erreur : " + plan.getCarac(t.x - 1, t.y));
					}
				} else if (t.direction.equals("^")) {
					if (plan.getCarac(t.x, t.y - 1).equals("|")) {
						t.y--;
					} else if (plan.getCarac(t.x, t.y - 1).equals("\\")) {
						t.y--;
						t.setDirection("<");
					} else if (plan.getCarac(t.x, t.y - 1).equals("/")) {
						t.y--;
						t.setDirection(">");
					} else if (plan.getCarac(t.x, t.y - 1).equals("+")) {
						t.y--;
						t.setDirection(t.getNextDirection());
					} else {
						System.out.println("erreur : " + plan.getCarac(t.x, t.y - 1));
					}
				} else if (t.direction.equals("v")) {
					if (plan.getCarac(t.x, t.y + 1).equals("|")) {
						t.y++;
					} else if (plan.getCarac(t.x, t.y + 1).equals("\\")) {
						t.y++;
						t.setDirection(">");
					} else if (plan.getCarac(t.x, t.y + 1).equals("/")) {
						t.y++;
						t.setDirection("<");
					} else if (plan.getCarac(t.x, t.y + 1).equals("+")) {
						t.y++;
						t.setDirection(t.getNextDirection());
					} else {
						System.out.println("erreur : " + plan.getCarac(t.x, t.y + 1));
					}
				}
				crashControl(crashed);
				if(crash) {
					crash=false;
					crashed.addAll(clean());
				}
			}
			trains.removeAll(crashed);
			
		}

		private List<Train> ordreDePassage(List<Train> trains2) {
			trains2.sort( Comparator.comparing(Train::getY).thenComparing(Train::getX));
			return trains2;
		}

		private void crashControl(List<Train> crashed) {
			for (Train t1 : trains) {
				for (Train t2 : trains) {
					if (!crashed.contains(t1) && !crashed.contains(t2) && (t1.id != t2.id) && (t1.x == t2.x) && (t1.y == t2.y)) {
						crash = true;
						crashX = t1.x;
						crashY = t1.y;
						crashT1=t1;
						crashT2=t2;
					}

				}
			}

		}

		private Plan removeTrains(Plan plan) {
			List<String> trainCarac = new ArrayList<>();
			trainCarac.addAll(Arrays.asList(">", "<", "v", "^"));
			for (Train t : trains) {
				if (t.direction.equals(">") || t.direction.equals("<")) {
					plan.set(t.x, t.y, "-");
				}
				if (t.direction.equals("^") || t.direction.equals("v")) {
					plan.set(t.x, t.y, "|");
				}
			}
			return plan;
		}

		@Override
		public String toString() {
			String res = "ReseauFerre [plan=" + plan;
			res += "\n" + "trains :" + "\n";
			for (Train t : trains) {
				res += t.toString();
				res += "\n";
			}
			return res;
		}

	}

	public static class Plan {
		String[][] plan;
		int longeurLigne;
		int nbLignes;

		public Plan(String[][] plan2, int longeurLigne, int nbLignes) {
			this.plan = plan2;
			this.longeurLigne = longeurLigne;
			this.nbLignes = nbLignes;
		}

		public void set(int x, int y, String string) {
			plan[x][y] = string;

		}

		public String getCarac(int i, int j) {
			return plan[i][j];
		}

		@Override
		public String toString() {
			String res = "";
			for (int j = 0; j < nbLignes; j++) {
				StringBuilder ligne = new StringBuilder();
				ligne.append(j);
				ligne.append("         ");
				ligne.substring(0, 4);
				for (int i = 0; i < longeurLigne; i++) {
					ligne.append(plan[i][j]);
				}
				res += ligne.toString();
				res += "\n";
			}
			return res;

		}

	}

	public static class Train {
		int id;
		String lastChoice;
		String direction;
		int x;
		int y;

		public Train(int id, String lastChoice, String direction, int x, int y) {
			super();
			this.id = id;
			this.lastChoice = lastChoice;
			this.direction = direction;
			this.x = x;
			this.y = y;
		}

		public String getNextDirection() {
			String nextAction = getNextAction();
			if (direction.equals(">") && nextAction.equals("r")) {
				setDirection("v");
			} else if (direction.equals(">") && nextAction.equals("l")) {
				setDirection("^");
			} else if (direction.equals("<") && nextAction.equals("r")) {
				setDirection("^");
			} else if (direction.equals("<") && nextAction.equals("l")) {
				setDirection("v");
			} else if (direction.equals("^") && nextAction.equals("r")) {
				setDirection(">");
			} else if (direction.equals("^") && nextAction.equals("l")) {
				setDirection("<");
			} else if (direction.equals("v") && nextAction.equals("r")) {
				setDirection("<");
			} else if (direction.equals("v") && nextAction.equals("l")) {
				setDirection(">");
			}

			return getDirection();
		}

		private String getNextAction() {
			String nextAction = "r";
			if (lastChoice.equals("r")) {
				nextAction = "l";
			} else if (lastChoice.equals("l")) {
				nextAction = "s";
			}
			setLastChoice(nextAction);
			return nextAction;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getLastChoice() {
			return lastChoice;
		}

		public void setLastChoice(String lastChoice) {
			this.lastChoice = lastChoice;
		}

		public String getDirection() {
			return direction;
		}

		public void setDirection(String direction) {
			this.direction = direction;
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

		@Override
		public String toString() {
			return "Train [id=" + id + ", lastChoice=" + lastChoice + ", direction=" + direction + ", x=" + x + ", y="
					+ y + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Train other = (Train) obj;
			if (id != other.id)
				return false;
			return true;
		}

	}

	private static String read() {
		Path path = Paths.get(
				"C:\\git_repositories\\advent\\src\\main\\resources\\src\\advent_of_code\\main\\resources\\a2018\\input13");
		String content = "";
		try {
			content = Files.readString(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;

	}
}

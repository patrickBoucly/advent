package a2024;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import outils.MesOutils;

public class A2024Day6 extends A2024 {

	public A2024Day6(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day6 d = new A2024Day6(6);
		//System.out.println(d.s1(true));
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

	public long s1(boolean b) {
	    // Récupération simplifiée de l'input
	    List<String> input = Arrays.stream(getInput(b).trim().split("\n"))
	            .map(String::trim)
	            .toList();

	    Game g = getGame(input);
	    g.solve1();
	    return g.guard.visited.size();
	}

	private Game getGame(List<String> input) {
	    Set<Point> pts = new HashSet<>();
	    Guard g = null;

	    for (int j = 0; j < input.size(); j++) {
	        String s = input.get(j);
	        for (int i = 0; i < s.length(); i++) {
	            char c = s.charAt(i);
	            boolean obstacle = (c == '#');
	            // Si on trouve le garde
	            if (c == '^') {
	                Set<Point> v = new HashSet<>();
	                Point start = new Point(i, j, false);
	                v.add(start);
	                Map<PointWithDir, Integer> vp = new HashMap<>();
	                vp.put(new PointWithDir(start, "^"), 1);
	                g = new Guard(i, j, "^", v, vp);
	            }
	            pts.add(new Point(i, j, obstacle));
	        }
	    }
	    return new Game(g, pts, 0);
	}

	public int s2(boolean b) {
	    List<String> input = Arrays.stream(getInput(b).trim().split("\n"))
	            .map(String::trim)
	            .toList();
	    return solve2(input);
	}

	private int solve2(List<String> input) {
	    // Au lieu de recalculer getGame(input) pour chaque point, ce qui est très coûteux,
	    // envisager une approche différente. Par exemple, vous pourriez:
	    // 1. Créer le jeu une seule fois.
	    // 2. Modifier directement l'obstacle sur un clone du set ou sur une copie.
	    // Cependant, voici une simple réécriture conservant la logique actuelle.

		Game g = getGame(input);
		int startX=g.guard.posX;
		int startY=g.guard.posY;
	    int res2 = 0;
	    // System.out.println(pts.size()); // Eviter les prints dans les boucles si possible.

	    // Cette boucle crée un nouveau Game pour chaque point dans pts => O(n^2) potentiel.
	    // Idéalement, repenser la logique. Ici, on se contente d'un refacto superficiel.
	    int j = 0;
	    for (Point q : g.pts) {
	        j++;
	        if(j%50==0)
	        System.out.println(j);
	        Point obstaclePoint = findPoint(g.pts, q.x, q.y);
	        if (obstaclePoint != null && !obstaclePoint.isObstacle ) {
	            obstaclePoint.setObstacle(true);
	            res2 += g.isCercle();
	            Set<Point> v = new HashSet<>();
                Point start = new Point(startX, startY, false);
                v.add(start);
                Map<PointWithDir, Integer> vp = new HashMap<>();
                vp.put(new PointWithDir(start, "^"), 1);
                g.setGuard(new Guard(startX, startY, "^", v, vp));
		        if (obstaclePoint != null) {
		            obstaclePoint.setObstacle(false);
		        }
	        }
	    }

	    return res2;
	}

	private Point findPoint(Set<Point> pts, int x, int y) {
	    // Recherche plus efficace: comme Point implémente equals/hashCode, 
	    // on peut directement vérifier s'il existe.
	    // On peut simplement faire :
	    Point p = new Point(x, y, false);
	    if (pts.contains(p)) {
	        // On veut le point exact, pas juste savoir s'il existe.
	        // Puisqu'il existe, on peut le retrouver en itérant. Idéalement, changer pts en Map.
	        for (Point candidate : pts) {
	            if (candidate.x == x && candidate.y == y) {
	                return candidate;
	            }
	        }
	    }
	    return null;
	}

	private Set<Point> getPointsInput(List<String> input) {
	    Set<Point> pts = new HashSet<>();
	    for (int j = 0; j < input.size(); j++) {
	        String s = input.get(j);
	        for (int i = 0; i < s.length(); i++) {
	            char c = s.charAt(i);
	            boolean obstacle = (c == '#');
	            pts.add(new Point(i, j, obstacle));
	        }
	    }
	    return pts;
	}

	@Data
	@AllArgsConstructor
	public static class Game {
	    Guard guard;
	    Set<Point> pts;
	    int res2 = 0;

	    // ...
	    // toString() est correct, mais on pourrait optimiser le calcul de imax/jmax
	    // en le stockant lors de la création du Game. Ici, on laisse tel quel.
	    // ...

	    public int isCercle() {
	        boolean circle = false;
	        // Tant qu'on a un point (sans obstacle) à la position du garde
	        while (!circle && pts.contains(new Point(guard.posX, guard.posY, false))) {
	            moveGuard();
	            for (Integer i : guard.pointWithDir.values()) {
	                if (i > 2) {
	                    circle = true;
	                    break;
	                }
	            }
	        }
	        return circle ? 1 : 0;
	    }

	    public void solve1() {
	        while (pts.contains(new Point(guard.posX, guard.posY, false))) {
	            moveGuard();
	        }
	    }

	    private void moveGuard() {
	        // Simplification: utilisation d'une méthode générique
	        int dx = 0, dy = 0;
	        switch (guard.dir) {
	            case "^" -> dy = -1;
	            case "v" -> dy = 1;
	            case "<" -> dx = -1;
	            case ">" -> dx = 1;
	        }

	        moveInDirection(dx, dy);
	        guard.setDir(nextDir(guard.dir));
	    }

	    private void moveInDirection(int dx, int dy) {
	        boolean continuer = true;
	        while (continuer) {
	            int curX = guard.posX;
	            int curY = guard.posY;
	            Point nextPoint = findPointFast(curX + dx, curY + dy);
	            if (nextPoint != null) {
	                if (!nextPoint.isObstacle) {
	                    guard.posX += dx;
	                    guard.posY += dy;
	                    guard.addVisitedOriented(nextPoint, guard.dir);
	                } else {
	                    continuer = false;
	                }
	            } else {
	                // On sort de la grille
	                guard.posX += dx;
	                guard.posY += dy;
	                continuer = false;
	            }
	        }
	    }

	    private Point findPointFast(int x, int y) {
	        // Pour éviter la boucle, on peut directement faire :
	        // On ne sait pas si Point est toujours présent avec ou sans obstacle.
	        // Mais comme equals/hashCode sont correctement implémentés, 
	        // pts.contains(new Point(x,y,false)) est possible.
	        // Cependant, cela ne nous donne pas l'objet Point original. 
	        // Comme auparavant, on devrait utiliser une Map pour un accès direct.
	        // On va au moins éviter la boucle de recherche en modifiant equals/hashcode appropriés.
	        
	        // Ici on retourne simplement le point s'il existe et n'importe quel 'isObstacle' 
	        // en se basant sur le fait que `pts` est un HashSet:
	        Point probe = new Point(x, y, false);
	        if (pts.contains(probe)) {
	            // Pour récupérer l'objet réel (avec son isObstacle correct),
	            // on doit refaire une itération. Idéalement, stocker pts dans une map.
	            for (Point p : pts) {
	                if (p.x == x && p.y == y) {
	                    return p;
	                }
	            }
	        }
	        return null;
	    }

	    private String nextDir(String dir) {
	        return switch (dir) {
	            case "^" -> ">";
	            case ">" -> "v";
	            case "v" -> "<";
	            default -> "^";
	        };
	    }
	}

	@Data
	@AllArgsConstructor
	private static class Point {
	    int x;
	    int y;
	    boolean isObstacle;

	    @Override
	    public String toString() {
	        return isObstacle ? "#" : ".";
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        Point other = (Point) obj;
	        return x == other.x && y == other.y;
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(x, y);
	    }
	}

	@Data
	@AllArgsConstructor
	public class Guard {
	    int posX;
	    int posY;
	    String dir;
	    Set<Point> visited;
	    Map<PointWithDir, Integer> pointWithDir;

	    public void addVisitedOriented(Point point, String dir2) {
	        PointWithDir pv = new PointWithDir(point, dir2);
	        pointWithDir.merge(pv, 1, Integer::sum);
	    }
	}

	@Data
	@AllArgsConstructor
	public class PointWithDir {
	    Point p;
	    String dir;
	}


	public static List<Long> getDuration() {
		A2024Day6 d = new A2024Day6(6);
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

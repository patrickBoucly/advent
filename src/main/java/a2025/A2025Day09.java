package a2025;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class A2025Day09 extends A2025 {
	public A2025Day09(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2025Day09 d = new A2025Day09(9);
		long startTime = System.currentTimeMillis();
		System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	public Long s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Game g = new Game();
		g.getPointsFromInput(inputL);
		return g.solve1();
	}

	public Long s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Game g = new Game();
		g.getPointsFromInput(inputL);
		return g.solve2();
	}

	@Data
	@NoArgsConstructor
	public static class Game {
	    List<Point> init;          
	    Set<Point> pts;           
	    Set<Cellule> toutesCellules;  
	    Set<Cellule> murs;          
	    Cellule[][] grille;           
	    List<Long> lesX;              
	    List<Long> lesY; 

	    private long manhattan(Point p1, Point p2) {
	        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
	    }

	    private void getPointsFromInput(List<String> lignes) {
	        pts = new HashSet<>();
	        init = new ArrayList<>();
	        for (String l : lignes) {
	            String[] s = l.split(",");
	            long x = Long.parseLong(s[0]);
	            long y = Long.parseLong(s[1]);
	            Point p = new Point(x, y, "#", false);
	            pts.add(p);
	            init.add(p);
	        }
	    }

	    public Long solve1() {
	        long maxManhattan = 0L;
	        Point meilleur1 = null;
	        Point meilleur2 = null;

	        for (Point p1 : pts) {
	            for (Point p2 : pts) {
	                long d = manhattan(p1, p2);
	                if (d > maxManhattan) {
	                    maxManhattan = d;
	                    meilleur1 = p1;
	                    meilleur2 = p2;
	                }
	            }
	        }
	        return aire(meilleur1, meilleur2);
	    }

	    private Long aire(Point a, Point b) {
	        long largeur  = Math.abs(a.x - b.x) + 1; 
	        long hauteur  = Math.abs(a.y - b.y) + 1;
	        return largeur * hauteur;
	    }


	    public Long solve2() {
	        toutesCellules = new HashSet<>();
	        murs           = new HashSet<>();
	        construireMurs();
	        construireGrilleCompressee();
	        marquerExterieurEtInterieur();
	        return aireMaximaleRectanglesRouges();
	    }

	    private void construireMurs() {
	        for (int i = 0; i < init.size() - 1; i++) {
	            ajouterSegmentMur(init.get(i), init.get(i + 1));
	        }
	        ajouterSegmentMur(init.get(init.size() - 1), init.get(0));
	    }

	    private void ajouterSegmentMur(Point p1, Point p2) {
	        if (p1.x.equals(p2.x)) {
	            long x  = p1.x;
	            long y1 = Math.min(p1.y, p2.y);
	            long y2 = Math.max(p1.y, p2.y);
	            murs.add(new Cellule(x, x, y1, y2, -1, -1, TypeCellule.MUR));
	        } else if (p1.y.equals(p2.y)) {
	            long y  = p1.y;
	            long x1 = Math.min(p1.x, p2.x);
	            long x2 = Math.max(p1.x, p2.x);
	            murs.add(new Cellule(x1, x2, y, y, -1, -1, TypeCellule.MUR));
	        } else {
	            throw new IllegalArgumentException("Segment non aligné : " + p1 + " -> " + p2);
	        }
	    }

	    private void construireGrilleCompressee() {
	    	TreeSet<Long> xBruts = new TreeSet<>();
	    	TreeSet<Long> yBruts = new TreeSet<>();
	    	for (Point p : init) {
	    	    xBruts.add(p.x);
	    	    yBruts.add(p.y);
	    	}
	    	long minX = xBruts.first();
	    	long maxX = xBruts.last();
	    	long minY = yBruts.first();
	    	long maxY = yBruts.last();
	    	xBruts.add(minX - 1);
	    	xBruts.add(maxX + 1);
	    	yBruts.add(minY - 1);
	    	yBruts.add(maxY + 1);
	        lesX = new ArrayList<>(xBruts);
	        lesY = new ArrayList<>(yBruts);
	        int nbColonnes = lesX.size() - 1; 
	        int nbLignes   = lesY.size() - 1; 
	        grille = new Cellule[nbColonnes][nbLignes];

	        // On crée une cellule pour chaque "rectangle compressé"
	        for (int ix = 0; ix < nbColonnes; ix++) {
	            long x1 = lesX.get(ix);
	            long x2 = lesX.get(ix + 1);

	            for (int iy = 0; iy < nbLignes; iy++) {
	                long y1 = lesY.get(iy);
	                long y2 = lesY.get(iy + 1);

	                Cellule c = nouvelleCellule(x1, x2, y1, y2, ix, iy);
	                grille[ix][iy] = c;
	            }
	        }
	    }

	    private Cellule nouvelleCellule(long x1, long x2, long y1, long y2, int ix, int iy) {
	        Cellule c = new Cellule(x1, x2, y1, y2, ix, iy, TypeCellule.INCONNUE);
	        toutesCellules.add(c);
	        return c;
	    }

	    private void marquerExterieurEtInterieur() {
	        if (grille == null || grille.length == 0 || grille[0].length == 0) {
	            return;
	        }

	        // cellule [0][0] qui forcément dehors avec la marge
	        Cellule depart = grille[0][0];
	        Queue<Cellule> file = new ArrayDeque<>();
	        depart.setType(TypeCellule.EXTERIEUR);
	        file.add(depart);

	        // BFS : on remplit toutes les cellules accessibles sans traverser de mur
	        while (!file.isEmpty()) {
	            Cellule actuelle = file.poll();
	            for (Cellule voisine : voisins4(actuelle)) {
	                if (voisine != null && voisine.type == TypeCellule.INCONNUE  && !ilExisteUnMurEntre(actuelle, voisine)) {
	                    voisine.type = TypeCellule.EXTERIEUR;
	                    file.add(voisine);
	                }
	            }
	        }
	        for (Cellule c : toutesCellules) {
	            if (c.type == TypeCellule.INCONNUE) {
	                c.type = TypeCellule.INTERIEUR;
	            }
	        }
	    }
	    
	    private Cellule[] voisins4(Cellule cur) {
	        List<Cellule> voisins = new ArrayList<>();
	        int ix = cur.ix;
	        int iy = cur.iy;
	        if (ix > 0)                   voisins.add(grille[ix - 1][iy]);           
	        if (ix < grille.length - 1)   voisins.add(grille[ix + 1][iy]);         
	        if (iy > 0)                   voisins.add(grille[ix][iy - 1]);         
	        if (iy < grille[0].length - 1) voisins.add(grille[ix][iy + 1]);         
	        return voisins.toArray(new Cellule[0]);
	    }

	    /**
	     * Indique s'il existe un mur entre deux cellules voisines.
	     * On regarde si un segment du contour (vertical ou horizontal) 
	     * coupe la frontière commune des deux cellules.
	     */
	    private boolean ilExisteUnMurEntre(Cellule a, Cellule b) {
	        // Cas 1 : voisins horizontaux (même Y, intervalles X qui se touchent)
	        if (a.ymin == b.ymin && a.ymax == b.ymax) {
	            Cellule gauche = a.xmin < b.xmin ? a : b;
	            Cellule droite = (gauche == a) ? b : a;

	            if (gauche.xmax == droite.xmin) {
	                long xMur  = gauche.xmax;
	                long y1    = gauche.ymin;
	                long y2    = gauche.ymax;

	                for (Cellule m : murs) {
	                    if (m.type == TypeCellule.MUR
	                            && m.xmin == m.xmax
	                            && m.xmin == xMur) {
	                        // recouvrement vertical des intervalles [y1,y2] et [m.ymin,m.ymax]
	                        if (!(m.ymax <= y1 || m.ymin >= y2)) {
	                            return true;
	                        }
	                    }
	                }
	            }
	            return false;
	        }

	        // Cas 2 : voisins verticaux (même X, intervalles Y qui se touchent)
	        if (a.xmin == b.xmin && a.xmax == b.xmax) {
	            Cellule bas  = a.ymin < b.ymin ? a : b;
	            Cellule haut = (bas == a) ? b : a;

	            if (bas.ymax == haut.ymin) {
	                long yMur = bas.ymax;
	                long x1   = bas.xmin;
	                long x2   = bas.xmax;

	                for (Cellule m : murs) {
	                    if (m.type == TypeCellule.MUR
	                            && m.ymin == m.ymax
	                            && m.ymin == yMur) {
	                        // recouvrement horizontal des intervalles [x1,x2] et [m.xmin,m.xmax]
	                        if (!(m.xmax <= x1 || m.xmin >= x2)) {
	                            return true;
	                        }
	                    }
	                }
	            }
	            return false;
	        }

	        // Normalement on ne vient jamais ici avec deux cellules non voisines
	        return false;
	    }


	    private Long aireMaximaleRectanglesRouges() {
	        long aireMax = 0L;
	        for (int i = 0; i < init.size(); i++) {
	            Point a = init.get(i);
	            for (int j = i + 1; j < init.size(); j++) {
	                Point b = init.get(j);
	                long x1 = Math.min(a.x, b.x);
	                long x2 = Math.max(a.x, b.x);
	                long y1 = Math.min(a.y, b.y);
	                long y2 = Math.max(a.y, b.y);
	                
	                if (x1 == x2 || y1 == y2) {
	                    continue;
	                }

	                // retrouver les indices compressés correspondant à ces coordonnées
	                int ix1 = lesX.indexOf(x1);
	                int ix2 = lesX.indexOf(x2);
	                int iy1 = lesY.indexOf(y1);
	                int iy2 = lesY.indexOf(y2);


	                // Vérifier qu'aucune cellule EXTERIEUR n'est dans ce rectangle
	                boolean rectangleValide = true;
	                for (int ix = ix1; ix < ix2 && rectangleValide; ix++) {
	                    for (int iy = iy1; iy < iy2; iy++) {
	                        Cellule c = grille[ix][iy];
	                        if (c.type == TypeCellule.EXTERIEUR) {
	                            rectangleValide = false;
	                            break;
	                        }
	                    }
	                }

	                if (!rectangleValide) {
	                    continue;
	                }

	                // Aire en nombre de tuiles (bornes incluses)
	                long aireRectangle = (x2 - x1 + 1L) * (y2 - y1 + 1L);
	                if (aireRectangle > aireMax) {
	                    aireMax = aireRectangle;
	                }
	            }
	        }

	        return aireMax;
	    }
	}

	enum TypeCellule { MUR, INCONNUE, INTERIEUR, EXTERIEUR }

	@Data
	@AllArgsConstructor
	public static class Cellule {
	    long xmin, xmax, ymin, ymax;
	    int ix, iy;              // indices dans la grille pour les cellules et -1  -1 pour les murs
	    TypeCellule type;
	}
	@Data
	@AllArgsConstructor
	public static class Point {
		Long x;
		Long y;
		String cont;
		Boolean traite;

		public static Optional<Point> getPoint(Set<Point> pts, Long i, Long j) {
			Point p = new Point(i, j, "?", false);
			for (Point pt : pts) {
				if (pt.equals(p)) {
					return Optional.ofNullable(pt);
				}
			}
			return Optional.empty();
		}

		public static Optional<Point> getPoint(Set<Point> pts, int i, int j) {
			Point p = new Point(Long.parseLong(String.valueOf(i)), Long.parseLong(String.valueOf(j)), "?", false);
			for (Point pt : pts) {
				if (pt.equals(p)) {
					return Optional.ofNullable(pt);
				}
			}
			return Optional.empty();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			return Objects.equals(x, other.x) && Objects.equals(y, other.y);
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

	}

	public static List<Long> getDuration() {
		A2025Day09 d = new A2025Day09(9);
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
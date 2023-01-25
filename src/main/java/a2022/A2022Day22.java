package a2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Getter;
import lombok.Setter;
import outils.MesOutils;

@Getter
@Setter
public class A2022Day22 extends A2022 {

	public A2022Day22(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day22 d = new A2022Day22(22);
		long startTime = System.currentTimeMillis();
		// System.out.println(d.s1(false));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public Integer s2(boolean b) {
		for(int i=2550;i<2550;i++) {
			Game2 g = getGame2(b);
			g.setSequence(g.sequence.subList(0, i));
			System.out.println(i+1+" instruction :");
			System.out.println("Résultat : "+solveRun2(b, g));
		}
		return solveRun2(b,getGame2(b));
	}

	private Integer solveRun2(boolean b, Game2 g) {
		if (b) {
			for (String inst : g.sequence) {
				g = g.apply2(inst);
			}
		} else {
			for (String inst : g.sequence) {
				g = g.apply(inst);
			}
		}

		Face f = g.faces.stream().filter(fa -> fa.actif).findFirst().get();
		int facing = 0;
		if (f.curDir.equals("v")) {
			facing = 1;
		}
		if (f.curDir.equals("<")) {
			facing = 2;
		}
		if (f.curDir.equals("^")) {
			facing = 3;
		}
		if (b) {
			Pair<Integer, Integer> coef = getCoef(f.num);
			System.out.println("Arrivée sur la face :"+f.num + " "+ "aux coordonnées : "  + " " +String.valueOf(f.curPos.x + coef.getLeft() * g.taille)+" "+String.valueOf(f.curPos.y + coef.getRight() * g.taille)+ ", orientation :"+f.curDir);
			
			return 1000 * (f.curPos.y + coef.getRight() * g.taille) + 4 * (f.curPos.x + coef.getLeft() * g.taille)
					+ facing;
		}
		return 1000 * (f.curPos.y + g.taille) + 4 * (f.curPos.x + g.taille) + facing;
	}

	private Pair<Integer, Integer> getCoef(int num) {
		if (num == 1) {
			return Pair.of(1, 0);
		}
		if (num == 2) {
			return Pair.of(1, 1);
		}
		if (num == 3) {
			return Pair.of(0, 2);
		}
		if (num == 4) {
			return Pair.of(2, 0);
		}
		if (num == 5) {
			return Pair.of(0, 3);
		}
		if (num == 6) {
			return Pair.of(1, 2);
		}
		return null;
	}

	public int s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().toList();
		Game g = getGame(input);
		System.out.println(g);
		System.out.println(g.sequence.size());
		// int cpt=0;
		for (String inst : g.sequence) {
			g = g.apply(inst);
			// cpt++;
			System.out.println(g);
		}
		System.out.println(g.curPos);

		return g.calculate();
	}

	private Game2 getGame2(boolean b) {
		List<String> seq = Arrays.asList(getExtraInput(b, 72).split(";")).stream().toList();
		Face f1 = getFace(Arrays.asList(getExtraInput(b, 12).split("\n")).stream().toList(), 1);
		f1.actif = true;
		f1.setCurPos(new Position(1, 1));
		f1.curDir = ">";
		Face f2 = getFace(Arrays.asList(getExtraInput(b, 22).split("\n")).stream().toList(), 2);
		Face f3 = getFace(Arrays.asList(getExtraInput(b, 32).split("\n")).stream().toList(), 3);
		Face f4 = getFace(Arrays.asList(getExtraInput(b, 42).split("\n")).stream().toList(), 4);
		Face f5 = getFace(Arrays.asList(getExtraInput(b, 52).split("\n")).stream().toList(), 5);
		Face f6 = getFace(Arrays.asList(getExtraInput(b, 62).split("\n")).stream().toList(), 6);

		Game2 g = new Game2();
		g.taille = Arrays.asList(getExtraInput(b, 2).split("\n")).stream().toList().size();
		Set<Face> faces = new HashSet<>();
		faces.addAll(List.of(f1, f2, f3, f4, f5, f6));
		g.setFaces(faces);
		g.setSequence(seq);
		g.sample = b;
		return g;
	}

	private Face getFace(List<String> input, int k) {
		Set<Point> pts = new HashSet<>();
		Face f = new Face();
		int j = 1;
		for (String ss : input) {
			String s = ss.replaceAll("(\\r|\\n|\\t)", "");
			for (int i = 0; i < s.length(); i++) {
				pts.add(new Point(i + 1, j, s.substring(i, i + 1)));
			}
			j++;
		}
		f.setNum(k);
		f.setPts(pts);
		f.setActif(false);
		f.setCurDir("");
		f.setCurPos(null);
		return f;
	}

	private Game getGame(List<String> input) {
		boolean ligneVide = false;
		int j = 1;
		List<String> seq = new ArrayList<>();
		Game g = new Game();
		Set<Point> pts = new HashSet<>();
		for (String ss : input) {
			String s = ss.replaceAll("(\\r|\\n|\\t)", "");
			if (ligneVide && seq.size() > 1) {
				break;
			}
			if (s.isBlank()) {
				ligneVide = true;
			} else if (ligneVide) {
				String[] sp = s.split(";");
				for (String m : sp) {
					seq.add(m);
				}
				g.setSequence(seq);
			} else {
				for (int i = 0; i < s.length(); i++) {
					pts.add(new Point(i + 1, j, s.substring(i, i + 1)));
				}
			}
			j++;
		}
		g.setPts(pts);
		g.setCurDir(">");
		g.setCurPos(new Position(MesOutils.getMinIntegerFromList(
				pts.stream().filter(pt -> pt.p.y == 1 && !pt.info.equals(" ")).map(pt -> pt.p.x).toList()), 1));
		return g;
	}

	public Optional<Point> getPoint(Set<Point> pts, int x, int y) {
		Point p = null;
		for (Point i : pts) {
			if (x == i.p.x && y == i.p.y) {
				p = new Point(new Position(x, y), i.info);
				return Optional.ofNullable(p);
			}
		}
		return Optional.ofNullable(p);
	}

	private class Face {
		int num;
		Set<Point> pts;
		boolean actif;
		Position curPos;
		String curDir;

		public Face(int num, Set<Point> pts, boolean actif, Position curPos, String curDir) {
			super();
			this.num = num;
			Set<Point> npts = new HashSet<>();
			for (Point p : pts) {
				npts.add(new Point(new Position(p.p.x, p.p.y), p.info));
			}
			this.pts = npts;
			this.actif = actif;
			if (curPos != null) {
				this.curPos = new Position(curPos.x, curPos.y);
			} else {
				this.curPos = null;
			}
			this.curDir = curDir;
		}

		public Face() {
			// TODO Auto-generated constructor stub
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public void setPts(Set<Point> pts) {
			this.pts = pts;
		}

		public void setActif(boolean actif) {
			this.actif = actif;
		}

		public void setCurPos(Position curPos) {
			this.curPos = curPos;
		}

		public void setCurDir(String curDir) {
			this.curDir = curDir;
		}

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			res.append("Face " + num);
			res.append("\n");
			int imax = MesOutils.getMaxIntegerFromList(pts.stream().map(p -> p.getP().x).toList());
			int imin = MesOutils.getMinIntegerFromList(pts.stream().map(p -> p.getP().x).toList());
			int jmax = MesOutils.getMaxIntegerFromList(pts.stream().map(p -> p.getP().y).toList());
			int jmin = MesOutils.getMinIntegerFromList(pts.stream().map(p -> p.getP().y).toList());
			for (int j = jmin; j <= jmax; j++) {
				for (int i = imin; i <= imax; i++) {
					if (curPos != null && i == curPos.x && j == curPos.y) {
						res.append(curDir);
					} else {
						getPoint(pts, i, j).ifPresentOrElse(pt -> res.append(pt.info), () -> res.append(" "));
					}
				}
				res.append("\n");
			}
			return res.toString();
		}

	}

	private class Game2 {
		Set<Face> faces;
		List<String> sequence;
		int taille;
		boolean sample;

		public Game2(Set<Face> faces, List<String> sequence) {
			super();
			this.faces = faces;
			this.sequence = sequence;
		}

		public Game2 apply2(String inst) {
			Face f = faces.stream().filter(fa -> fa.actif).findFirst().get();
			String nextDir = f.curDir;
			Game2 ng = new Game2();
			if (inst.equals("R")) {
				if (f.curDir.equals(">")) {
					nextDir = "v";
				}
				if (f.curDir.equals("v")) {
					nextDir = "<";
				}
				if (f.curDir.equals("<")) {
					nextDir = "^";
				}
				if (f.curDir.equals("^")) {
					nextDir = ">";
				}
			} else if (inst.equals("L")) {
				if (f.curDir.equals(">")) {
					nextDir = "^";
				}
				if (f.curDir.equals("v")) {
					nextDir = ">";
				}
				if (f.curDir.equals("<")) {
					nextDir = "v";
				}
				if (f.curDir.equals("^")) {
					nextDir = "<";
				}
			} else {
				int dep = Integer.parseInt(inst);
				boolean stop = false;
				for (int i = 0; i < dep; i++) {
					if (!stop) {
						f = faces.stream().filter(fa -> fa.actif).findFirst().get();
						if (f.curDir.equals(">")) {
							if (f.curPos.x == taille) {
								if (f.num == 3) {
									Face f6 = getFace(6);
									Optional<Point> nextP = getPoint(f6.pts, 1, f.curPos.y);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f6.setCurPos(new Position(nextP.get().p));
										f6.actif = true;
										f6.setCurDir(">");
										nextDir = ">";
									} else {
										stop = true;
									}
								} else if (f.num == 2) {
									Face f4 = getFace(4);
									Optional<Point> nextP = getPoint(f4.pts, f.curPos.y, taille);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f4.setCurPos(new Position(nextP.get().p));
										f4.actif = true;
										f4.setCurDir("^");
										nextDir = "^";
									} else {
										stop = true;
									}
								} else if (f.num == 4) {
									Face f6 = getFace(6);
									Optional<Point> nextP = getPoint(f6.pts, taille, taille - f.curPos.y + 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f6.setCurPos(new Position(nextP.get().p));
										f6.actif = true;
										f6.setCurDir("<");
										nextDir = "<";
									} else {
										stop = true;
									}
								} else if (f.num == 1) {
									Face f4 = getFace(4);
									Optional<Point> nextP = getPoint(f4.pts, 1, f.curPos.y);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f4.setCurPos(new Position(nextP.get().p));
										f4.actif = true;
										f4.setCurDir(">");
										nextDir = ">";
									} else {
										stop = true;
									}
								} else if (f.num == 5) {
									Face f6 = getFace(6);
									Optional<Point> nextP = getPoint(f6.pts, f.curPos.y, taille);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f6.setCurPos(new Position(nextP.get().p));
										f6.actif = true;
										f6.setCurDir("^");
										nextDir = "^";
									} else {
										stop = true;
									}
								} else if (f.num == 6) {
									Face f4 = getFace(4);
									Optional<Point> nextP = getPoint(f4.pts, taille, taille - f.curPos.y + 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f4.setCurPos(new Position(nextP.get().p));
										f4.actif = true;
										f4.setCurDir("<");
										nextDir = "<";
									} else {
										stop = true;
									}
								}

							} else {
								Optional<Point> nextP = getPoint(f.pts, f.curPos.x + 1, f.curPos.y);
								if (nextP.get().info.equals(".")) {
									f.setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}
							}
						} else if (f.curDir.equals("v")) {
							if (f.curPos.y == taille) {
								if (f.num == 3) {
									Face f5 = getFace(5);
									Optional<Point> nextP = getPoint(f5.pts, f.curPos.x, 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f5.setCurPos(new Position(nextP.get().p));
										f5.actif = true;
										f5.setCurDir("v");
										nextDir = "v";
									} else {
										stop = true;
									}
								} else if (f.num == 2) {
									Face f6 = getFace(6);
									Optional<Point> nextP = getPoint(f6.pts, f.curPos.x, 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f6.setCurPos(new Position(nextP.get().p));
										f6.actif = true;
										f6.setCurDir("v");
										nextDir = "v";
									} else {
										stop = true;
									}
								} else if (f.num == 4) {
									Face f2 = getFace(2);
									Optional<Point> nextP = getPoint(f2.pts, taille, f.curPos.x);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f2.setCurPos(new Position(nextP.get().p));
										f2.actif = true;
										f2.setCurDir("<");
										nextDir = "<";
									} else {
										stop = true;
									}
								} else if (f.num == 1) {
									Face f2 = getFace(2);
									Optional<Point> nextP = getPoint(f2.pts, f.curPos.x, 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f2.setCurPos(new Position(nextP.get().p));
										f2.actif = true;
										f2.setCurDir("v");
										nextDir = "v";
									} else {
										stop = true;
									}
								} else if (f.num == 5) {
									Face f4 = getFace(4);
									Optional<Point> nextP = getPoint(f4.pts, f.curPos.x, 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f4.setCurPos(new Position(nextP.get().p));
										f4.actif = true;
										f4.setCurDir("v");
										nextDir = "v";
									} else {
										stop = true;
									}
								} else if (f.num == 6) {
									Face f5 = getFace(5);
									Optional<Point> nextP = getPoint(f5.pts, taille, f.curPos.x);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f5.setCurPos(new Position(nextP.get().p));
										f5.actif = true;
										f5.setCurDir("<");
										nextDir = "<";
									} else {
										stop = true;
									}
								}

							} else {
								Optional<Point> nextP = getPoint(f.pts, f.curPos.x, f.curPos.y + 1);
								if (nextP.get().info.equals(".")) {
									f.setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}
							}
						} else if (f.curDir.equals("<")) {
							if (f.curPos.x == 1) {
								if (f.num == 3) {
									Face f1 = getFace(1);
									Optional<Point> nextP = getPoint(f1.pts, 1, taille - f.curPos.y + 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f1.setCurPos(new Position(nextP.get().p));
										f1.actif = true;
										f1.setCurDir(">");
										nextDir = ">";
									} else {
										stop = true;
									}
								} else if (f.num == 2) {
									Face f3 = getFace(3);
									Optional<Point> nextP = getPoint(f3.pts, f.curPos.y, 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f3.setCurPos(new Position(nextP.get().p));
										f3.actif = true;
										f3.setCurDir("v");
										nextDir = "v";
									} else {
										stop = true;
									}
								} else if (f.num == 4) {
									Face f1 = getFace(1);
									Optional<Point> nextP = getPoint(f1.pts, taille, f.curPos.y);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f1.setCurPos(new Position(nextP.get().p));
										f1.actif = true;
										f1.setCurDir("<");
										nextDir = "<";
									} else {
										stop = true;
									}
								} else if (f.num == 1) {
									Face f3 = getFace(3);
									Optional<Point> nextP = getPoint(f3.pts, 1, taille - f.curPos.y + 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f3.setCurPos(new Position(nextP.get().p));
										f3.actif = true;
										f3.setCurDir(">");
										nextDir = ">";
									} else {
										stop = true;
									}
								} else if (f.num == 5) {
									Face f1 = getFace(1);
									Optional<Point> nextP = getPoint(f1.pts, f.curPos.y, 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f1.setCurPos(new Position(nextP.get().p));
										f1.actif = true;
										f1.setCurDir("v");
										nextDir = "v";
									} else {
										stop = true;
									}
								} else if (f.num == 6) {
									Face f3 = getFace(3);
									Optional<Point> nextP = getPoint(f3.pts, taille, f.curPos.y);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f3.setCurPos(new Position(nextP.get().p));
										f3.actif = true;
										f3.setCurDir("<");
										nextDir = "<";
									} else {
										stop = true;
									}
								}

							} else {
								Optional<Point> nextP = getPoint(f.pts, f.curPos.x - 1, f.curPos.y);
								if (nextP.get().info.equals(".")) {
									f.setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}

							}
						} else if (f.curDir.equals("^")) {
							if (f.curPos.y == 1) {
								if (f.num == 3) {
									Face f2 = getFace(2);
									Optional<Point> nextP = getPoint(f2.pts, 1, f.curPos.x);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f2.setCurPos(new Position(nextP.get().p));
										f2.actif = true;
										f2.setCurDir(">");
										nextDir = ">";
									} else {
										stop = true;
									}
								} else if (f.num == 2) {
									Face f1 = getFace(1);
									Optional<Point> nextP = getPoint(f1.pts, f.curPos.x, taille);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f1.setCurPos(new Position(nextP.get().p));
										f1.actif = true;
										f1.setCurDir("^");
										nextDir = "^";
									} else {
										stop = true;
									}
								} else if (f.num == 4) {
									Face f5 = getFace(5);
									Optional<Point> nextP = getPoint(f5.pts, f.curPos.x, taille);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f5.setCurPos(new Position(nextP.get().p));
										f5.actif = true;
										f5.setCurDir("^");
										nextDir = "^";
									} else {
										stop = true;
									}
								} else if (f.num == 1) {
									Face f5 = getFace(5);
									Optional<Point> nextP = getPoint(f5.pts, 1, f.curPos.x);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f5.setCurPos(new Position(nextP.get().p));
										f5.actif = true;
										f5.setCurDir(">");
										nextDir = ">";
									} else {
										stop = true;
									}
								} else if (f.num == 5) {
									Face f3 = getFace(3);
									Optional<Point> nextP = getPoint(f3.pts, f.curPos.x, taille);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f3.setCurPos(new Position(nextP.get().p));
										f3.actif = true;
										f3.setCurDir("^");
										nextDir = "^";
									} else {
										stop = true;
									}
								} else if (f.num == 6) {
									Face f2 = getFace(2);
									Optional<Point> nextP = getPoint(f2.pts, f.curPos.x, taille);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f2.setCurPos(new Position(nextP.get().p));
										f2.actif = true;
										f2.setCurDir("^");
										nextDir = "^";
									} else {
										stop = true;
									}
								}

							} else {
								Optional<Point> nextP = getPoint(f.pts, f.curPos.x, f.curPos.y - 1);
								if (nextP.get().info.equals(".")) {
									f.setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}
							}
						}

					}
				}
			}
			// ng.setCurDir(nextDir);
			// ng.setCurPos(new Position(curPos));
			ng.setSequence(sequence);

			Set<Face> nfs = new HashSet<>();
			Face f1 = faces.stream().filter(fa -> fa.num == 1).findFirst().get();
			nfs.add(new Face(f1.num, f1.pts, f1.actif, f1.curPos, f1.curDir));
			Face f2 = faces.stream().filter(fa -> fa.num == 2).findFirst().get();
			nfs.add(new Face(f2.num, f2.pts, f2.actif, f2.curPos, f2.curDir));
			Face f3 = faces.stream().filter(fa -> fa.num == 3).findFirst().get();
			nfs.add(new Face(f3.num, f3.pts, f3.actif, f3.curPos, f3.curDir));
			Face f4 = faces.stream().filter(fa -> fa.num == 4).findFirst().get();
			nfs.add(new Face(f4.num, f4.pts, f4.actif, f4.curPos, f4.curDir));
			Face f5 = faces.stream().filter(fa -> fa.num == 5).findFirst().get();
			nfs.add(new Face(f5.num, f5.pts, f5.actif, f5.curPos, f5.curDir));
			Face f6 = faces.stream().filter(fa -> fa.num == 6).findFirst().get();
			nfs.add(new Face(f6.num, f6.pts, f6.actif, f6.curPos, f6.curDir));
			nfs.stream().filter(fa -> fa.actif).findFirst().get().setCurDir(nextDir);
			ng.setFaces(nfs);
			ng.taille = taille;
			ng.sample = sample;

			return ng;
		}

		public Game2() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			if (!sample) {
				int imax = taille * 4;
				int jmax = taille * 3;
				Face f1 = faces.stream().filter(fa -> fa.num == 1).findFirst().get();
				Face f2 = faces.stream().filter(fa -> fa.num == 2).findFirst().get();
				Face f3 = faces.stream().filter(fa -> fa.num == 3).findFirst().get();
				Face f4 = faces.stream().filter(fa -> fa.num == 4).findFirst().get();
				Face f5 = faces.stream().filter(fa -> fa.num == 5).findFirst().get();
				Face f6 = faces.stream().filter(fa -> fa.num == 6).findFirst().get();
				for (int j = 1; j <= jmax; j++) {

					for (int i = 1; i <= imax; i++) {
						if (j <= taille) {
							if (i <= taille) {
								res.append(" ");
							} else if (i <= 2 * taille) {
								res.append(" ");
							} else if (i <= 3 * taille) {
								if (f1.actif && f1.curPos.x == i - 2 * taille && f1.curPos.y == j) {
									res.append(f1.curDir);
								} else {
									res.append(getInfoPoint(f1.pts, i - 2 * taille, j));
								}
							} else {
								res.append(" ");
							}
						} else if (j <= 2 * taille) {
							if (i <= taille) {
								if (f5.actif && f5.curPos.x == i && f5.curPos.y == j - taille) {
									res.append(f5.curDir);
								} else {
									res.append(getInfoPoint(f5.pts, i, j - taille));
								}
							} else if (i <= 2 * taille) {
								if (f3.actif && f3.curPos.x == i - taille && f3.curPos.y == j - taille) {
									res.append(f3.curDir);
								} else {
									res.append(getInfoPoint(f3.pts, i - taille, j - taille));
								}
							} else if (i <= 3 * taille) {
								if (f2.actif && f2.curPos.x == i - 2 * taille && f2.curPos.y == j - taille) {
									res.append(f2.curDir);
								} else {
									res.append(getInfoPoint(f2.pts, i - 2 * taille, j - taille));
								}
							} else {
								res.append(" ");
							}
						} else {
							if (i <= 2 * taille) {
								res.append(" ");
							} else if (i <= 3 * taille) {
								if (f6.actif && f6.curPos.x == i - 2 * taille && f6.curPos.y == j - 2 * taille) {
									res.append(f6.curDir);
								} else {
									res.append(getInfoPoint(f6.pts, i - 2 * taille, j - 2 * taille));
								}
							} else {
								if (f4.actif && f4.curPos.x == i - 3 * taille && f4.curPos.y == j - 2 * taille) {
									res.append(f4.curDir);
								} else {
									res.append(getInfoPoint(f4.pts, i - 3 * taille, j - 2 * taille));
								}
							}
						}
					}
					res.append("\n");
				}
				return res.toString();
			}
			int imax = taille * 3;
			int jmax = taille * 4;
			Face f1 = faces.stream().filter(fa -> fa.num == 1).findFirst().get();
			Face f2 = faces.stream().filter(fa -> fa.num == 2).findFirst().get();
			Face f3 = faces.stream().filter(fa -> fa.num == 3).findFirst().get();
			Face f4 = faces.stream().filter(fa -> fa.num == 4).findFirst().get();
			Face f5 = faces.stream().filter(fa -> fa.num == 5).findFirst().get();
			Face f6 = faces.stream().filter(fa -> fa.num == 6).findFirst().get();
			for (int j = 1; j <= jmax; j++) {

				for (int i = 1; i <= imax; i++) {
					if (j <= taille) {
						if (i <= taille) {
							res.append(" ");
						} else if (i <= 2 * taille) {
							if (f1.actif && f1.curPos.x == i - taille && f1.curPos.y == j) {
								res.append(f1.curDir);
							} else {
								res.append(getInfoPoint(f1.pts, i - taille, j));
							}
						} else {
							if (f4.actif && f4.curPos.x == i - 2 * taille && f4.curPos.y == j) {
								res.append(f4.curDir);
							} else {
								res.append(getInfoPoint(f4.pts, i - 2 * taille, j));
							}
						}
					} else if (j <= 2 * taille) {
						if (i <= taille) {
							res.append(" ");
						} else if (i <= 2 * taille) {
							if (f2.actif && f2.curPos.x == i - taille && f2.curPos.y == j - taille) {
								res.append(f2.curDir);
							} else {
								res.append(getInfoPoint(f2.pts, i - taille, j - taille));
							}
						} else {
							res.append(" ");
						}
					} else if (j <= 3 * taille) {
						if (i <= taille) {
							if (f3.actif && f3.curPos.x == i && f3.curPos.y == j - 2 * taille) {
								res.append(f3.curDir);
							} else {
								res.append(getInfoPoint(f2.pts, i, j - 2 * taille));
							}
						} else if (i <= 2 * taille) {
							if (f6.actif && f6.curPos.x == i - taille && f6.curPos.y == j - 2 * taille) {
								res.append(f6.curDir);
							} else {
								res.append(getInfoPoint(f6.pts, i - taille, j - 2 * taille));
							}
						} else {
							res.append(" ");
						}
					} else {
						if (i <= taille) {
							if (f5.actif && f5.curPos.x == i && f5.curPos.y == j - 3 * taille) {
								res.append(f5.curDir);
							} else {
								res.append(getInfoPoint(f5.pts, i, j - 3 * taille));
							}
						} else {
							res.append(" ");
						}
					}
				}
				res.append("\n");
			}

			return res.toString();
		}

		private Object getInfoPoint(Set<Point> pts, int i, int j) {
			return pts.stream().filter(pt -> pt.p.x == i && pt.p.y == j).findFirst().get().info;
		}

		public Game2 apply(String inst) {
			Face f = faces.stream().filter(fa -> fa.actif).findFirst().get();
			int activeFace = f.num;
			String nextDir = f.curDir;
			Game2 ng = new Game2();
			if (inst.equals("R")) {
				if (f.curDir.equals(">")) {
					nextDir = "v";
				}
				if (f.curDir.equals("v")) {
					nextDir = "<";
				}
				if (f.curDir.equals("<")) {
					nextDir = "^";
				}
				if (f.curDir.equals("^")) {
					nextDir = ">";
				}
			} else if (inst.equals("L")) {
				if (f.curDir.equals(">")) {
					nextDir = "^";
				}
				if (f.curDir.equals("v")) {
					nextDir = ">";
				}
				if (f.curDir.equals("<")) {
					nextDir = "v";
				}
				if (f.curDir.equals("^")) {
					nextDir = "<";
				}
			} else {
				int dep = Integer.parseInt(inst);
				boolean stop = false;
				for (int i = 0; i < dep; i++) {
					if (!stop) {
						f = faces.stream().filter(fa -> fa.actif).findFirst().get();
						if (f.curDir.equals(">")) {
							if (f.curPos.x == taille) {
								if (f.num == 3) {
									Face f2 = getFace(2);
									Optional<Point> nextP = getPoint(f2.pts, 1, f.curPos.y);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f2.setCurPos(new Position(nextP.get().p));
										f2.actif = true;
										f2.setCurDir(">");
										nextDir = ">";
									} else {
										stop = true;
									}
								} else if (f.num == 2) {
									Face f4 = getFace(4);
									Optional<Point> nextP = getPoint(f4.pts, taille - f.curPos.y + 1, 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f4.setCurPos(new Position(nextP.get().p));
										f4.actif = true;
										f4.setCurDir("v");
										nextDir = "v";
									} else {
										stop = true;
									}
								} else if (f.num == 4) {
									Face f3 = getFace(3);
									Optional<Point> nextP = getPoint(f3.pts, 1, f.curPos.y);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f3.setCurPos(new Position(nextP.get().p));
										f3.actif = true;
										f3.setCurDir(">");
										nextDir = ">";
									} else {
										stop = true;
									}
								} else if (f.num == 1) {
									Face f4 = getFace(4);
									Optional<Point> nextP = getPoint(f4.pts, f.curPos.y, 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f4.setCurPos(new Position(nextP.get().p));
										f4.actif = true;
										f4.setCurDir("v");
										nextDir = "v";
									} else {
										stop = true;
									}
								} else if (f.num == 5) {
									Face f3 = getFace(3);
									Optional<Point> nextP = getPoint(f3.pts, 1, f.curPos.y);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f3.setCurPos(new Position(nextP.get().p));
										f3.actif = true;
										f3.setCurDir(">");
										nextDir = ">";
									} else {
										stop = true;
									}
								} else if (f.num == 6) {
									Face f4 = getFace(4);
									Optional<Point> nextP = getPoint(f4.pts, f.curPos.y, taille);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f4.setCurPos(new Position(nextP.get().p));
										f4.actif = true;
										f4.setCurDir("^");
										nextDir = "^";
									} else {
										stop = true;
									}
								}

							} else {
								Optional<Point> nextP = getPoint(f.pts, f.curPos.x + 1, f.curPos.y);
								if (nextP.get().info.equals(".")) {
									f.setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}
							}
						} else if (f.curDir.equals("v")) {
							if (f.curPos.y == taille) {
								if (f.num == 3) {
									Face f6 = getFace(6);
									Optional<Point> nextP = getPoint(f6.pts, 1, taille - f.curPos.x + 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f6.setCurPos(new Position(nextP.get().p));
										f6.actif = true;
										f6.setCurDir(">");
										nextDir = ">";
									} else {
										stop = true;
									}
								} else if (f.num == 2) {
									Face f6 = getFace(6);
									Optional<Point> nextP = getPoint(f6.pts, f.curPos.x, 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f6.setCurPos(new Position(nextP.get().p));
										f6.actif = true;
										f6.setCurDir("v");
										nextDir = "v";
									} else {
										stop = true;
									}
								} else if (f.num == 4) {
									Face f6 = getFace(6);
									Optional<Point> nextP = getPoint(f6.pts, taille, f.curPos.x);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f6.setCurPos(new Position(nextP.get().p));
										f6.actif = true;
										f6.setCurDir("<");
										nextDir = "<";
									} else {
										stop = true;
									}
								} else if (f.num == 1) {
									Face f2 = getFace(2);
									Optional<Point> nextP = getPoint(f2.pts, f.curPos.x, 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f2.setCurPos(new Position(nextP.get().p));
										f2.actif = true;
										f2.setCurDir("v");
										nextDir = "v";
									} else {
										stop = true;
									}
								} else if (f.num == 5) {
									Face f1 = getFace(1);
									Optional<Point> nextP = getPoint(f1.pts, f.curPos.x, 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f1.setCurPos(new Position(nextP.get().p));
										f1.actif = true;
										f1.setCurDir("v");
										nextDir = "v";
									} else {
										stop = true;
									}
								} else if (f.num == 6) {
									Face f5 = getFace(5);
									Optional<Point> nextP = getPoint(f5.pts, taille - f.curPos.x + 1, taille);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f5.setCurPos(new Position(nextP.get().p));
										f5.actif = true;
										f5.setCurDir("^");
										nextDir = "^";
									} else {
										stop = true;
									}
								}

							} else {
								Optional<Point> nextP = getPoint(f.pts, f.curPos.x, f.curPos.y + 1);
								if (nextP.get().info.equals(".")) {
									f.setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}
							}
						} else if (f.curDir.equals("<")) {
							if (f.curPos.x == 1) {
								if (f.num == 3) {
									Face f4 = getFace(4);
									Optional<Point> nextP = getPoint(f4.pts, taille, f.curPos.y);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f4.setCurPos(new Position(nextP.get().p));
										f4.actif = true;
										f4.setCurDir("<");
										nextDir = "<";
									} else {
										stop = true;
									}
								} else if (f.num == 2) {
									Face f3 = getFace(3);
									Optional<Point> nextP = getPoint(f3.pts, taille, f.curPos.y);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f3.setCurPos(new Position(nextP.get().p));
										f3.actif = true;
										f3.setCurDir("<");
										nextDir = "<";
									} else {
										stop = true;
									}
								} else if (f.num == 4) {
									Face f6 = getFace(6);
									Optional<Point> nextP = getPoint(f6.pts, taille, f.curPos.y);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f6.setCurPos(new Position(nextP.get().p));
										f6.actif = true;
										f6.setCurDir("<");
										nextDir = "<";
									} else {
										stop = true;
									}
								} else if (f.num == 1) {
									Face f3 = getFace(3);
									Optional<Point> nextP = getPoint(f3.pts, f.curPos.y, 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f3.setCurPos(new Position(nextP.get().p));
										f3.actif = true;
										f3.setCurDir("v");
										nextDir = "v";
									} else {
										stop = true;
									}
								} else if (f.num == 5) {
									Face f4 = getFace(4);
									Optional<Point> nextP = getPoint(f4.pts, taille, taille - f.curPos.y + 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f4.setCurPos(new Position(nextP.get().p));
										f4.actif = true;
										f4.setCurDir(">");
										nextDir = ">";
									} else {
										stop = true;
									}
								} else if (f.num == 6) {
									Face f3 = getFace(3);
									Optional<Point> nextP = getPoint(f3.pts, taille - f.curPos.y + 1, taille);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f3.setCurPos(new Position(nextP.get().p));
										f3.actif = true;
										f3.setCurDir("^");
										nextDir = "^";
									} else {
										stop = true;
									}
								}

							} else {
								Optional<Point> nextP = getPoint(f.pts, f.curPos.x - 1, f.curPos.y);
								if (nextP.get().info.equals(".")) {
									f.setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}

							}
						} else if (f.curDir.equals("^")) {
							if (f.curPos.y == 1) {
								if (f.num == 3) {
									Face f1 = getFace(1);
									Optional<Point> nextP = getPoint(f1.pts, 1, f.curPos.x);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f1.setCurPos(new Position(nextP.get().p));
										f1.actif = true;
										f1.setCurDir(">");
										nextDir = ">";
									} else {
										stop = true;
									}
								} else if (f.num == 2) {
									Face f1 = getFace(1);
									Optional<Point> nextP = getPoint(f1.pts, f.curPos.x, taille);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f1.setCurPos(new Position(nextP.get().p));
										f1.actif = true;
										f1.setCurDir("^");
										nextDir = "^";
									} else {
										stop = true;
									}
								} else if (f.num == 4) {
									Face f4 = getFace(4);
									Optional<Point> nextP = getPoint(f4.pts, taille, taille - f.curPos.x + 1);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f4.setCurPos(new Position(nextP.get().p));
										f4.actif = true;
										f4.setCurDir("<");
										nextDir = "<";
									} else {
										stop = true;
									}
								} else if (f.num == 1) {
									Face f5 = getFace(5);
									Optional<Point> nextP = getPoint(f5.pts, f.curPos.x, taille);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f5.setCurPos(new Position(nextP.get().p));
										f5.actif = true;
										f5.setCurDir("^");
										nextDir = "^";
									} else {
										stop = true;
									}
								} else if (f.num == 5) {
									Face f6 = getFace(6);
									Optional<Point> nextP = getPoint(f6.pts, f.curPos.x, taille);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f6.setCurPos(new Position(nextP.get().p));
										f6.actif = true;
										f6.setCurDir("^");
										nextDir = "^";
									} else {
										stop = true;
									}
								} else if (f.num == 6) {
									Face f2 = getFace(2);
									Optional<Point> nextP = getPoint(f2.pts, f.curPos.x, taille);
									if (nextP.get().info.equals(".")) {
										f.actif = false;
										f.curPos = null;
										f.curDir = "";
										f2.setCurPos(new Position(nextP.get().p));
										f2.actif = true;
										f2.setCurDir("^");
										nextDir = "^";
									} else {
										stop = true;
									}
								}

							} else {
								Optional<Point> nextP = getPoint(f.pts, f.curPos.x, f.curPos.y - 1);
								if (nextP.get().info.equals(".")) {
									f.setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}
							}
						}

					}
				}
			}
			// ng.setCurDir(nextDir);
			// ng.setCurPos(new Position(curPos));
			ng.setSequence(sequence);
			Set<Face> nfs = new HashSet<>();
			Face f1 = faces.stream().filter(fa -> fa.num == 1).findFirst().get();
			nfs.add(new Face(f1.num, f1.pts, f1.actif, f1.curPos, f1.curDir));
			Face f2 = faces.stream().filter(fa -> fa.num == 2).findFirst().get();
			nfs.add(new Face(f2.num, f2.pts, f2.actif, f2.curPos, f2.curDir));
			Face f3 = faces.stream().filter(fa -> fa.num == 3).findFirst().get();
			nfs.add(new Face(f3.num, f3.pts, f3.actif, f3.curPos, f3.curDir));
			Face f4 = faces.stream().filter(fa -> fa.num == 4).findFirst().get();
			nfs.add(new Face(f4.num, f4.pts, f4.actif, f4.curPos, f4.curDir));
			Face f5 = faces.stream().filter(fa -> fa.num == 5).findFirst().get();
			nfs.add(new Face(f5.num, f5.pts, f5.actif, f5.curPos, f5.curDir));
			Face f6 = faces.stream().filter(fa -> fa.num == 6).findFirst().get();
			nfs.add(new Face(f6.num, f6.pts, f6.actif, f6.curPos, f6.curDir));
			nfs.stream().filter(fa -> fa.actif).findFirst().get().setCurDir(nextDir);
			ng.setFaces(nfs);
			ng.taille = taille;
			ng.sample = sample;
			return ng;

		}

		private Face getFace(int i) {
			return faces.stream().filter(fa -> fa.num == i).findFirst().get();
		}

		public void setSequence(List<String> sequence) {
			this.sequence = sequence;
		}

		public void setFaces(Set<Face> faces) {
			this.faces = faces;
		}

	}

	private class Game {
		List<String> sequence;
		Set<Point> pts;
		Position curPos;
		String curDir;

		public List<String> getSequence() {
			return sequence;
		}

		public Integer calculate() {
			int facing = 0;
			if (curDir.equals("v")) {
				facing = 1;
			}
			if (curDir.equals("<")) {
				facing = 2;
			}
			if (curDir.equals("^")) {
				facing = 3;
			}
			return 1000 * curPos.y + 4 * curPos.x + facing;
		}

		public Game apply(String inst) {
			String nextDir = curDir;
			Game ng = new Game();
			if (inst.equals("R")) {
				if (curDir.equals(">")) {
					nextDir = "v";
				}
				if (curDir.equals("v")) {
					nextDir = "<";
				}
				if (curDir.equals("<")) {
					nextDir = "^";
				}
				if (curDir.equals("^")) {
					nextDir = ">";
				}
			} else if (inst.equals("L")) {
				if (curDir.equals(">")) {
					nextDir = "^";
				}
				if (curDir.equals("v")) {
					nextDir = ">";
				}
				if (curDir.equals("<")) {
					nextDir = "v";
				}
				if (curDir.equals("^")) {
					nextDir = "<";
				}
			} else {
				int dep = Integer.valueOf(inst);
				boolean stop = false;
				for (int i = 0; i < dep; i++) {
					if (!stop) {
						if (curDir.equals(">")) {
							Optional<Point> nextP = getPoint(pts, curPos.x + 1, curPos.y);
							if (nextP.isPresent()) {
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								} else if (nextP.get().info.equals(" ")) {
									int debut = MesOutils.getMinIntegerFromList(
											pts.stream().filter(pt -> pt.p.y == curPos.y && !pt.info.equals(" "))
													.map(pt -> pt.p.x).toList());
									nextP = getPoint(pts, debut, curPos.y);
									if (nextP.get().info.equals(".")) {
										setCurPos(new Position(nextP.get().p));
									} else if (nextP.get().info.equals("#")) {
										stop = true;
									}
								} else {
									System.out.println(nextP.get().info);
								}
							} else {
								int debut = MesOutils.getMinIntegerFromList(
										pts.stream().filter(pt -> pt.p.y == curPos.y && !pt.info.equals(" "))
												.map(pt -> pt.p.x).toList());
								nextP = getPoint(pts, debut, curPos.y);
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}
							}
						}
						if (curDir.equals("v")) {
							Optional<Point> nextP = getPoint(pts, curPos.x, curPos.y + 1);
							if (nextP.isPresent()) {
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								} else if (nextP.get().info.equals(" ")) {
									int debut = MesOutils.getMinIntegerFromList(
											pts.stream().filter(pt -> pt.p.x == curPos.x && !pt.info.equals(" "))
													.map(pt -> pt.p.y).toList());
									nextP = getPoint(pts, curPos.x, debut);
									if (nextP.get().info.equals(".")) {
										setCurPos(new Position(nextP.get().p));
									} else if (nextP.get().info.equals("#")) {
										stop = true;
									}
								}
							} else {
								int debut = MesOutils.getMinIntegerFromList(
										pts.stream().filter(pt -> pt.p.x == curPos.x && !pt.info.equals(" "))
												.map(pt -> pt.p.y).toList());
								nextP = getPoint(pts, curPos.x, debut);
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}
							}
						}
						if (curDir.equals("<")) {
							Optional<Point> nextP = getPoint(pts, curPos.x - 1, curPos.y);
							if (nextP.isPresent()) {
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								} else if (nextP.get().info.equals(" ")) {
									int fin = MesOutils.getMaxIntegerFromList(
											pts.stream().filter(pt -> pt.p.y == curPos.y && !pt.info.equals(" "))
													.map(pt -> pt.p.x).toList());
									nextP = getPoint(pts, fin, curPos.y);
									if (nextP.get().info.equals(".")) {
										setCurPos(new Position(nextP.get().p));
									} else if (nextP.get().info.equals("#")) {
										stop = true;
									}
								}
							} else {
								int fin = MesOutils.getMaxIntegerFromList(
										pts.stream().filter(pt -> pt.p.y == curPos.y && !pt.info.equals(" "))
												.map(pt -> pt.p.x).toList());
								nextP = getPoint(pts, fin, curPos.y);
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}
							}
						}
						if (curDir.equals("^")) {
							Optional<Point> nextP = getPoint(pts, curPos.x, curPos.y - 1);
							if (nextP.isPresent()) {
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								} else if (nextP.get().info.equals(" ")) {
									int fin = MesOutils.getMaxIntegerFromList(
											pts.stream().filter(pt -> pt.p.x == curPos.x && !pt.info.equals(" "))
													.map(pt -> pt.p.y).toList());
									nextP = getPoint(pts, curPos.x, fin);
									if (nextP.get().info.equals(".")) {
										setCurPos(new Position(nextP.get().p));
									} else if (nextP.get().info.equals("#")) {
										stop = true;
									}
								}
							} else {
								int fin = MesOutils.getMaxIntegerFromList(
										pts.stream().filter(pt -> pt.p.x == curPos.x && !pt.info.equals(" "))
												.map(pt -> pt.p.y).toList());
								nextP = getPoint(pts, curPos.x, fin);
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}
							}
						}
					}
				}
			}
			ng.setCurDir(nextDir);
			ng.setCurPos(new Position(curPos));
			ng.setSequence(sequence);
			Set<Point> npts = new HashSet<>();
			for (Point pt : pts) {
				npts.add(new Point(pt.p, pt.info));
			}
			ng.setPts(npts);
			return ng;

		}

		public void setSequence(List<String> sequence) {
			this.sequence = sequence;
		}

		public void setCurDir(String curDir) {
			this.curDir = curDir;
		}

		public void setPts(Set<Point> pts) {
			this.pts = pts;
		}

		public Position getCurPos() {
			return curPos;
		}

		public void setCurPos(Position curPos) {
			this.curPos = curPos;
		}

		public Game(List<String> sequence, Set<Point> pts, Position curPos) {
			super();
			this.sequence = sequence;
			this.pts = pts;
			this.curPos = curPos;
		}

		public Game() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			int imax = MesOutils.getMaxIntegerFromList(pts.stream().map(p -> p.getP().x).toList());
			int imin = MesOutils.getMinIntegerFromList(pts.stream().map(p -> p.getP().x).toList());
			int jmax = MesOutils.getMaxIntegerFromList(pts.stream().map(p -> p.getP().y).toList());
			int jmin = MesOutils.getMinIntegerFromList(pts.stream().map(p -> p.getP().y).toList());
			for (int j = jmin; j <= jmax; j++) {
				for (int i = imin; i <= imax; i++) {
					if (i == curPos.x && j == curPos.y) {
						res.append(curDir);
					} else {
						getPoint(pts, i, j).ifPresentOrElse(pt -> res.append(pt.info), () -> res.append(" "));
					}
				}
				res.append("\n");
			}
			return res.toString();
		}

	}

	private class Point {
		Position p;
		String info;

		public Point(Position position, String info) {
			this.p = position;
			this.info = info;
		}

		@Override
		public String toString() {
			return "[" + p.x + "," + p.y + "," + info + "]";
		}

		public Point(int i, int j, String info) {
			this.p = new Position(i, j);
			this.info = info;
		}

		public Position getP() {
			return p;
		}
	}

	private class Position {
		int x;
		int y;

		@Override
		public String toString() {
			return "Position [x=" + x + ", y=" + y + "]";
		}

		public Position(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public Position(Position nextWP) {
			super();
			this.x = nextWP.x;
			this.y = nextWP.y;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(x, y);
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
			Position other = (Position) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return x == other.x && y == other.y;
		}

		private A2022Day22 getEnclosingInstance() {
			return A2022Day22.this;
		}

	}

	public static List<Long> getDuration() {
		A2022Day22 d = new A2022Day22(22);
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

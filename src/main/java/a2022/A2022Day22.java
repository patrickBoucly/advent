package a2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import outils.MesOutils;

public class A2022Day22 extends A2022 {

	public A2022Day22(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day22 d = new A2022Day22(22);
		long startTime = System.currentTimeMillis();
		System.out.println(d.s1(false));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		// System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public Integer s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		Game g = getGame(input);
		System.out.println(g);
		System.out.println(g.sequence.size());
		//int cpt=0;
		if(b) {
			for (String inst : g.sequence) {
				g=g.apply2B(inst);
				//cpt++;
				System.out.println(g);
			}
		}else {
			for (String inst : g.sequence) {
				g=g.apply2A(inst);
				//cpt++;
				System.out.println(g);
			}
		}
		
		System.out.println(g.curPos);
		return g.calculate();
	}

	public int s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		Game g = getGame(input);
		System.out.println(g);
		System.out.println(g.sequence.size());
		//int cpt=0;
		for (String inst : g.sequence) {
			g=g.apply(inst);
			//cpt++;
			System.out.println(g);
		}
		System.out.println(g.curPos);
		return g.calculate();
	}

	private Game getGame(List<String> input) {
		boolean ligneVide = false;
		int j = 1;
		Game g = new Game();
		Set<Point> pts = new HashSet<>();
		for (String ss : input) {
			String s=ss.replaceAll("(\\r|\\n|\\t)", "");
			if (s.isBlank()) {
				ligneVide = true;
			} else if (ligneVide) {
				String[] sp = s.split(";");
				List<String> seq = new ArrayList<>();
				for (String m : sp) {
					seq.add(m);
				}
				g.setSequence(seq);
			} else {
				for (int i = 0; i < s.length(); i++) {
					pts.add(new Point(i+1, j, s.substring(i, i + 1)));
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
		public Game apply2A(String inst) {
			int c=4;
			String nextDir=curDir;
			String nextDirPot="";
			Game ng=new Game();
			if (inst.equals("R")) {
				if (curDir.equals(">")) {
					nextDir="v";
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
								}else if (nextP.get().info.equals(" ")) {
									int numBloc=Math.floorDiv(curPos.y, c);
									int dec=Math.floorMod(curPos.y, c);;
									if(numBloc==1) {
										nextDirPot="<";
										nextP=getPoint(pts, curPos.x+c, curPos.y+c+2*(c-dec)-1);
									}else if(numBloc==2) {
										nextDirPot="v";
										nextP=getPoint(pts, curPos.x+c-dec, curPos.y+c-dec);
									}else {
										
									}
									int debut=MesOutils.getMinIntegerFromList(
											pts.stream().filter(pt -> pt.p.y == curPos.y && !pt.info.equals(" ")).map(pt -> pt.p.x).toList());
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
								int debut=MesOutils.getMinIntegerFromList(
										pts.stream().filter(pt -> pt.p.y == curPos.y && !pt.info.equals(" ")).map(pt -> pt.p.x).toList());
								nextP = getPoint(pts, debut, curPos.y);
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}
							}
						}
						if (curDir.equals("v")) {
							Optional<Point> nextP = getPoint(pts, curPos.x , curPos.y+1);
							if (nextP.isPresent()) {
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}else if (nextP.get().info.equals(" ")) {
									int debut=MesOutils.getMinIntegerFromList(
											pts.stream().filter(pt -> pt.p.x == curPos.x && !pt.info.equals(" ")).map(pt -> pt.p.y).toList());
									nextP = getPoint(pts, curPos.x, debut);
									if (nextP.get().info.equals(".")) {
										setCurPos(new Position(nextP.get().p));
									} else if (nextP.get().info.equals("#")) {
										stop = true;
									}
								}
							} else {
								int debut=MesOutils.getMinIntegerFromList(
										pts.stream().filter(pt -> pt.p.x == curPos.x && !pt.info.equals(" ")).map(pt -> pt.p.y).toList());
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
								}else if (nextP.get().info.equals(" ")) {
									int fin=MesOutils.getMaxIntegerFromList(
											pts.stream().filter(pt -> pt.p.y == curPos.y && !pt.info.equals(" ")).map(pt -> pt.p.x).toList());
									nextP = getPoint(pts, fin, curPos.y);
									if (nextP.get().info.equals(".")) {
										setCurPos(new Position(nextP.get().p));
									} else if (nextP.get().info.equals("#")) {
										stop = true;
									}
								}
							} else {
								int fin=MesOutils.getMaxIntegerFromList(
										pts.stream().filter(pt -> pt.p.y == curPos.y && !pt.info.equals(" ")).map(pt -> pt.p.x).toList());
								nextP = getPoint(pts, fin, curPos.y);
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}
							}
						}
						if (curDir.equals("^")) {
							Optional<Point> nextP = getPoint(pts, curPos.x , curPos.y-1);
							if (nextP.isPresent()) {
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}else if (nextP.get().info.equals(" ")) {
									int fin=MesOutils.getMaxIntegerFromList(
											pts.stream().filter(pt -> pt.p.x == curPos.x && !pt.info.equals(" ")).map(pt -> pt.p.y).toList());
									nextP = getPoint(pts, curPos.x, fin);
									if (nextP.get().info.equals(".")) {
										setCurPos(new Position(nextP.get().p));
									} else if (nextP.get().info.equals("#")) {
										stop = true;
									}
								}
							} else {
								int fin=MesOutils.getMaxIntegerFromList(
										pts.stream().filter(pt -> pt.p.x == curPos.x && !pt.info.equals(" ")).map(pt -> pt.p.y).toList());
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
			Set<Point> npts=new HashSet<>();
			for(Point pt:pts) {
				npts.add(new Point(pt.p,pt.info));
			}
			ng.setPts(npts);
			return ng;

		}
		public Game apply2B(String inst) {
			String nextDir=curDir;
			Game ng=new Game();
			if (inst.equals("R")) {
				if (curDir.equals(">")) {
					nextDir="v";
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
								}else if (nextP.get().info.equals(" ")) {
									int debut=MesOutils.getMinIntegerFromList(
											pts.stream().filter(pt -> pt.p.y == curPos.y && !pt.info.equals(" ")).map(pt -> pt.p.x).toList());
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
								int debut=MesOutils.getMinIntegerFromList(
										pts.stream().filter(pt -> pt.p.y == curPos.y && !pt.info.equals(" ")).map(pt -> pt.p.x).toList());
								nextP = getPoint(pts, debut, curPos.y);
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}
							}
						}
						if (curDir.equals("v")) {
							Optional<Point> nextP = getPoint(pts, curPos.x , curPos.y+1);
							if (nextP.isPresent()) {
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}else if (nextP.get().info.equals(" ")) {
									int debut=MesOutils.getMinIntegerFromList(
											pts.stream().filter(pt -> pt.p.x == curPos.x && !pt.info.equals(" ")).map(pt -> pt.p.y).toList());
									nextP = getPoint(pts, curPos.x, debut);
									if (nextP.get().info.equals(".")) {
										setCurPos(new Position(nextP.get().p));
									} else if (nextP.get().info.equals("#")) {
										stop = true;
									}
								}
							} else {
								int debut=MesOutils.getMinIntegerFromList(
										pts.stream().filter(pt -> pt.p.x == curPos.x && !pt.info.equals(" ")).map(pt -> pt.p.y).toList());
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
								}else if (nextP.get().info.equals(" ")) {
									int fin=MesOutils.getMaxIntegerFromList(
											pts.stream().filter(pt -> pt.p.y == curPos.y && !pt.info.equals(" ")).map(pt -> pt.p.x).toList());
									nextP = getPoint(pts, fin, curPos.y);
									if (nextP.get().info.equals(".")) {
										setCurPos(new Position(nextP.get().p));
									} else if (nextP.get().info.equals("#")) {
										stop = true;
									}
								}
							} else {
								int fin=MesOutils.getMaxIntegerFromList(
										pts.stream().filter(pt -> pt.p.y == curPos.y && !pt.info.equals(" ")).map(pt -> pt.p.x).toList());
								nextP = getPoint(pts, fin, curPos.y);
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}
							}
						}
						if (curDir.equals("^")) {
							Optional<Point> nextP = getPoint(pts, curPos.x , curPos.y-1);
							if (nextP.isPresent()) {
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}else if (nextP.get().info.equals(" ")) {
									int fin=MesOutils.getMaxIntegerFromList(
											pts.stream().filter(pt -> pt.p.x == curPos.x && !pt.info.equals(" ")).map(pt -> pt.p.y).toList());
									nextP = getPoint(pts, curPos.x, fin);
									if (nextP.get().info.equals(".")) {
										setCurPos(new Position(nextP.get().p));
									} else if (nextP.get().info.equals("#")) {
										stop = true;
									}
								}
							} else {
								int fin=MesOutils.getMaxIntegerFromList(
										pts.stream().filter(pt -> pt.p.x == curPos.x && !pt.info.equals(" ")).map(pt -> pt.p.y).toList());
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
			Set<Point> npts=new HashSet<>();
			for(Point pt:pts) {
				npts.add(new Point(pt.p,pt.info));
			}
			ng.setPts(npts);
			return ng;

		}

		public Game apply(String inst) {
			String nextDir=curDir;
			Game ng=new Game();
			if (inst.equals("R")) {
				if (curDir.equals(">")) {
					nextDir="v";
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
								}else if (nextP.get().info.equals(" ")) {
									int debut=MesOutils.getMinIntegerFromList(
											pts.stream().filter(pt -> pt.p.y == curPos.y && !pt.info.equals(" ")).map(pt -> pt.p.x).toList());
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
								int debut=MesOutils.getMinIntegerFromList(
										pts.stream().filter(pt -> pt.p.y == curPos.y && !pt.info.equals(" ")).map(pt -> pt.p.x).toList());
								nextP = getPoint(pts, debut, curPos.y);
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}
							}
						}
						if (curDir.equals("v")) {
							Optional<Point> nextP = getPoint(pts, curPos.x , curPos.y+1);
							if (nextP.isPresent()) {
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}else if (nextP.get().info.equals(" ")) {
									int debut=MesOutils.getMinIntegerFromList(
											pts.stream().filter(pt -> pt.p.x == curPos.x && !pt.info.equals(" ")).map(pt -> pt.p.y).toList());
									nextP = getPoint(pts, curPos.x, debut);
									if (nextP.get().info.equals(".")) {
										setCurPos(new Position(nextP.get().p));
									} else if (nextP.get().info.equals("#")) {
										stop = true;
									}
								}
							} else {
								int debut=MesOutils.getMinIntegerFromList(
										pts.stream().filter(pt -> pt.p.x == curPos.x && !pt.info.equals(" ")).map(pt -> pt.p.y).toList());
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
								}else if (nextP.get().info.equals(" ")) {
									int fin=MesOutils.getMaxIntegerFromList(
											pts.stream().filter(pt -> pt.p.y == curPos.y && !pt.info.equals(" ")).map(pt -> pt.p.x).toList());
									nextP = getPoint(pts, fin, curPos.y);
									if (nextP.get().info.equals(".")) {
										setCurPos(new Position(nextP.get().p));
									} else if (nextP.get().info.equals("#")) {
										stop = true;
									}
								}
							} else {
								int fin=MesOutils.getMaxIntegerFromList(
										pts.stream().filter(pt -> pt.p.y == curPos.y && !pt.info.equals(" ")).map(pt -> pt.p.x).toList());
								nextP = getPoint(pts, fin, curPos.y);
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}
							}
						}
						if (curDir.equals("^")) {
							Optional<Point> nextP = getPoint(pts, curPos.x , curPos.y-1);
							if (nextP.isPresent()) {
								if (nextP.get().info.equals(".")) {
									setCurPos(new Position(nextP.get().p));
								} else if (nextP.get().info.equals("#")) {
									stop = true;
								}else if (nextP.get().info.equals(" ")) {
									int fin=MesOutils.getMaxIntegerFromList(
											pts.stream().filter(pt -> pt.p.x == curPos.x && !pt.info.equals(" ")).map(pt -> pt.p.y).toList());
									nextP = getPoint(pts, curPos.x, fin);
									if (nextP.get().info.equals(".")) {
										setCurPos(new Position(nextP.get().p));
									} else if (nextP.get().info.equals("#")) {
										stop = true;
									}
								}
							} else {
								int fin=MesOutils.getMaxIntegerFromList(
										pts.stream().filter(pt -> pt.p.x == curPos.x && !pt.info.equals(" ")).map(pt -> pt.p.y).toList());
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
			Set<Point> npts=new HashSet<>();
			for(Point pt:pts) {
				npts.add(new Point(pt.p,pt.info));
			}
			ng.setPts(npts);
			return ng;

		}

		public void setSequence(List<String> sequence) {
			this.sequence = sequence;
		}

		public Set<Point> getPts() {
			return pts;
		}

		public String getCurDir() {
			return curDir;
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

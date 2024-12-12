package a2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import outils.MesOutils;

public class A2024Day10 extends A2024 {

	public A2024Day10(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day10 d = new A2024Day10(10);
		System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		//d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	public long s1(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		Game g=getGame(input);
		return g.solve1();
	}

	

	public int s2(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		Game g=getGame(input);
		return g.solve2();
	}
	private Game getGame(List<String> input) {
		Set<Position> pos=new HashSet<>();
		Set<Itineraire> iti=new HashSet<>();
		Set<Chemin> chemins=new HashSet<>();
		int j=-1;
		for(String s:input) {
			j++;
			for(int i=0;i<s.length();i++) {
				pos.add(new Position(i,j,Integer.parseInt(s.substring(i, i+1))));
			}
		}
		return new Game(pos,iti,new HashMap<>(),chemins);
	}
	@Data
	@AllArgsConstructor
	public static class Game {
		Set<Position> pos;
		Set<Itineraire> iti;
		Map<Itineraire,Integer> itiMap;
		Set<Chemin> chemins;

		public int solve1() {
			int xmax=MesOutils.getMaxIntegerFromList(pos.stream().map(p->p.x).toList());
			int ymax=MesOutils.getMaxIntegerFromList(pos.stream().map(p->p.y).toList());
			for (Position p : pos) {
				if (auBordEtHauteur0(p,xmax,ymax)) {
					findAllPath(p);
				}

			}
			return iti.size();
		}

		public int solve2() {
			int xmax=MesOutils.getMaxIntegerFromList(pos.stream().map(p->p.x).toList());
			int ymax=MesOutils.getMaxIntegerFromList(pos.stream().map(p->p.y).toList());
			for (Position p : pos) {
				if (auBordEtHauteur0(p,xmax,ymax)) {
					findAllPath2(p);
				}

			}
			return itiMap.values().stream().reduce(0, Integer::sum);
		}

		private void findAllPath2(Position p) {
			List<Position> voisinsValide = getVoisins(p).stream().filter(q -> q.hauteur - p.hauteur == 1).toList();
			for (Position q : voisinsValide) {
				List<Position> posi = new ArrayList<>();
				posi.add(p);
				posi.add(q);
				chemins.add(new Chemin(posi,false));
			}
			while(chemins.stream().anyMatch(ch->!ch.ended)) {
				getNext2();
			}
			
		}

		private void getNext2() {
			Set<Chemin> newChemins=new HashSet<>();
			for(Chemin ch:chemins.stream().filter(c->!c.ended).toList()) {
				Position p=ch.pos.get(ch.pos.size()-1);
				List<Position> voisinsValide = getVoisins(p).stream().filter(q -> q.hauteur - p.hauteur == 1).toList();
				for(Position v:voisinsValide) {
					Chemin c=ch.copy(ch);
					c.pos.add(v);
					if(v.hauteur==9) {
						c.ended=true;
						Itineraire i=new Itineraire(c.getPos().get(0), c.getPos().get(c.getPos().size()-1));
						if(itiMap.containsKey(i)) {
							itiMap.put(i,itiMap.get(i)+ 1);
						}else {
							itiMap.put(i, 1);
						}
					}else {
						newChemins.add(c);
					}
					
				}
			}
			chemins=new HashSet<>();
			for(Chemin ch:newChemins) {
				chemins.add(ch);
			}
			
		}

		private boolean auBordEtHauteur0(Position p, int xmax, int ymax) {
			return p.hauteur==0 ;
		}

		public void findAllPath(Position p) {
			List<Position> voisinsValide = getVoisins(p).stream().filter(q -> q.hauteur - p.hauteur == 1).toList();
			for (Position q : voisinsValide) {
				List<Position> posi = new ArrayList<>();
				posi.add(p);
				posi.add(q);
				chemins.add(new Chemin(posi,false));
			}
			while(chemins.stream().anyMatch(ch->!ch.ended)) {
				getNext();
			}
		}

		private void getNext() {
			Set<Chemin> newChemins=new HashSet<>();
			for(Chemin ch:chemins.stream().filter(c->!c.ended).toList()) {
				Position p=ch.pos.get(ch.pos.size()-1);
				List<Position> voisinsValide = getVoisins(p).stream().filter(q -> q.hauteur - p.hauteur == 1).toList();
				for(Position v:voisinsValide) {
					Chemin c=ch.copy(ch);
					c.pos.add(v);
					if(v.hauteur==9) {
						c.ended=true;
						iti.add(new Itineraire(c.getPos().get(0), c.getPos().get(c.getPos().size()-1)));
					}else {
						newChemins.add(c);
					}
					
				}
			}
			chemins=new HashSet<>();
			for(Chemin ch:newChemins) {
				chemins.add(ch);
			}
			
		}

		private Set<Position> getVoisins(Position p) {
			Set<Position> voisins=new HashSet<>();
			Optional<Position> q= getPosition(p.x+1,p.y,pos);
			if(q.isPresent()) {
				voisins.add(q.get());
			}
			q= getPosition(p.x+1,p.y,pos);
			if(q.isPresent()) {
				voisins.add(q.get());
			}
			q= getPosition(p.x,p.y+1,pos);
			if(q.isPresent()) {
				voisins.add(q.get());
			}
			q= getPosition(p.x-1,p.y,pos);
			if(q.isPresent()) {
				voisins.add(q.get());
			}
			q= getPosition(p.x,p.y-1,pos);
			if(q.isPresent()) {
				voisins.add(q.get());
			}
			return voisins;
		}
		public static Optional<Position> getPosition(int i, int j, Set<Position> posi) {
			for (Position po : posi) {
				if (po.x==i && po.y==j) {
					return Optional.of(po);
				}
			}
			return Optional.empty();
		}
	}

	@Data
	@AllArgsConstructor
	public static class Position {
		int x;
		int y;
		int hauteur;
	}

	@Data
	@AllArgsConstructor
	public static class Chemin {
		List<Position> pos;
		boolean ended;

		public Chemin copy(Chemin c) {
			List<Position> np = new ArrayList<>();
			for (Position p : c.pos) {
				np.add(new Position(p.x, p.y, p.hauteur));
			}
			return new Chemin(np, c.ended);
		}
		
	}
	@Data
	@AllArgsConstructor
	public static class Itineraire {
		Position deb;
		Position fin;	
	}
	public static List<Long> getDuration() {
		A2024Day10 d = new A2024Day10(10);
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

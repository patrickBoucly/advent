package a2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.AllArgsConstructor;
import lombok.Data;
import outils.MesOutils;

public class A2024Day7 extends A2024 {
    private static List<String> operators= Arrays.asList("+","*");
	public A2024Day7(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day7 d = new A2024Day7(7);
		System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		// d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		//System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	public long s1(boolean b) {
	    // Récupération simplifiée de l'input
	    List<String> input = Arrays.stream(getInput(b).trim().split("\n"))
	            .map(String::trim)
	            .toList();
	    Game g=getGame(input);
	    System.out.println(g);
	    g.solve1();
	    return g.res1;
	}

	

	private Game getGame(List<String> input) {
		List<Equation> eqs=new ArrayList<>();
		for(String s:input) {
			String[] elements=s.split(":")[1].trim().split(" ");
			List<Long> longElement=new ArrayList<Long>();
			for(String e:elements) {
				longElement.add(Long.parseLong(e.trim()));
			}
			Equation eq=new Equation(Long.parseLong(s.split(":")[0].trim()), longElement, false);
			eqs.add(eq);
			
		}
		return new Game(eqs,0L);
	}

	public int s2(boolean b) {
	    List<String> input = Arrays.stream(getInput(b).trim().split("\n"))
	            .map(String::trim)
	            .toList();
	    return 0;
	}

	@Data
	@AllArgsConstructor
	private static class Game {
		List<Equation> equations;
	    Long res1=0L;
	    public void solve1() {
			for(Equation eq:equations) {
				System.out.println(eq);
				solve1Eq(eq);
				if(eq.solvable) {
					
					res1+=eq.result;
					System.out.println(res1);
				}
			}
			
		}
		private void solve1Eq(Equation eq) {
			if(!(eq.result<eq.calculerPlus() || eq.result>eq.calculerFois())) {
				 AtomicBoolean stopSignal = new AtomicBoolean(false);
			        MesOutils.generatePermutations(eq.element, permutation -> {
			            eq.testList(permutation);
			            if (eq.isSolvable()) {
			                stopSignal.set(true); 
			            }
			        }, stopSignal);
			}
			
		}
		
	}


	@Data
	@AllArgsConstructor
	private static class Equation {
	    public void testList(List<Long> l) {
	    	List<List<String>> choixOperators=MesOutils.cartesianProduct(operators,l.size()-1);
			for(List<String> ops:choixOperators) {
				Long cal=calculer(l,ops);
				if(cal.equals(result)) {
					solvable=true;
					break;
				}
			}
			
		}
		public Long calculerFois() {
			Long cf=element.get(0);
			for(int i=1;i<element.size();i++) {
				cf=cf*element.get(i);
			}
			return cf;
		}
		public Long calculerPlus() {
			Long cf=element.get(0);
			for(int i=1;i<element.size();i++) {
				cf=cf+element.get(i);
			}
			return cf;
		}
		private static Long calculer(List<Long> l, List<String> ops) {
			Long res=l.get(0);
			for(int i=0;i<ops.size();i++) {
				if(ops.get(i).equals("+")) {
					res=res+l.get(i+1);
				}else {
					res=res*l.get(i+1);
				}
				
			}
			return res;
		}
		Long result;
	    List<Long> element;
	    boolean solvable;
	}

	

	public static List<Long> getDuration() {
		A2024Day7 d = new A2024Day7(7);
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

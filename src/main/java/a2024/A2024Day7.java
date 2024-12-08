package a2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
	    List<String> input = Arrays.stream(getInput(b).trim().split("\n"))
	            .map(String::trim)
	            .toList();
	    Game g=getGame(input);
	    System.out.println(g);
	    g.solve1();
	    return g.res1;
	}

	

	private Game getGame(List<String> input) {
		List<Equation> eqs = new ArrayList<>();
	    for (String s : input) {
	        String[] parts = s.split(":");
	        long result = Long.parseLong(parts[0].trim());
	        List<Long> longElements = Arrays.stream(parts[1].trim().split(" "))
	                                        .map(String::trim)
	                                        .map(Long::parseLong)
	                                        .toList();
	        eqs.add(new Equation(result, longElements, false));
	    }
	    return new Game(eqs, 0L);
	}

	public Long s2(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n"))
	            .map(String::trim)
	            .toList();
	    Game g=getGame(input);
	    System.out.println(g);
	    g.solve2();
	    return g.res1;
	}

	@Data
	@AllArgsConstructor
	private static class Game {
		List<Equation> equations;
	    Long res1=0L;
	    public void solve1() {
	    	int cpt=0;
	    	equations.sort(Comparator.comparingInt(e -> ((Equation) e).getElement().size()));
			for(Equation eq:equations) {
				cpt++;
				System.out.println("liste "+cpt);
				solve1Eq(eq);
				if(eq.solvable) {
					
					res1+=eq.result;
					System.out.println(res1);
				}
			}
			
		}
		public void solve2() {
			int cpt=0;
	    	equations.sort(Comparator.comparingInt(e -> ((Equation) e).getElement().size()));
			for(Equation eq:equations) {
				cpt++;
				System.out.println("liste "+cpt);
				solve2Eq(eq);
				if(eq.solvable) {
					
					res1+=eq.result;
					System.out.println(res1);
				}
			}
			
		}
		private void solve2Eq(Equation eq) {
			AtomicBoolean stopSignal = new AtomicBoolean(false);
			solveEquationWithOperators2(eq.element, eq.result, solution -> {
		        System.out.println("Solution trouvée : " + solution);
		        eq.setSolvable(true);
		    }, stopSignal);
			
		}
		private void solve1Eq(Equation eq) {
			AtomicBoolean stopSignal = new AtomicBoolean(false);
			solveEquationWithOperators(eq.element, eq.result, solution -> {
		        System.out.println("Solution trouvée : " + solution);
		        eq.setSolvable(true);
		    }, stopSignal);
			
		}
		public static void solveEquationWithOperators(
		        List<Long> numbers, Long target, Consumer<String> consumer, AtomicBoolean stopSignal) {
		    explore(0, numbers, target, 0L, "", consumer, stopSignal);
		}public static void solveEquationWithOperators2(
		        List<Long> numbers, Long target, Consumer<String> consumer, AtomicBoolean stopSignal) {
		    explore2(0, numbers, target, 0L, "", consumer, stopSignal);
		}

		private static void explore(
		        int index,
		        List<Long> numbers,
		        Long target,
		        Long currentResult,
		        String currentExpression,
		        Consumer<String> consumer,
		        AtomicBoolean stopSignal) {

		    // Arrêter si une solution est déjà trouvée
		    if (stopSignal.get()) return;

		    // Si tous les nombres ont été utilisés, vérifier si le résultat correspond à la cible
		    if (index == numbers.size()) {
		        if (currentResult.equals(target)) {
		            consumer.accept(currentExpression); // Envoyer l'expression valide
		            stopSignal.set(true); // Arrêter si une solution suffit
		        }
		        return;
		    }

		    // Récupérer le nombre courant
		    Long currentNumber = numbers.get(index);

		    // Appliquer l'opérateur "+"
		    explore(
		            index + 1,
		            numbers,
		            target,
		            currentResult + currentNumber,
		            currentExpression.isEmpty() ? "" + currentNumber : currentExpression + " + " + currentNumber,
		            consumer,
		            stopSignal
		    );

		    // Appliquer l'opérateur "*"
		    explore(
		            index + 1,
		            numbers,
		            target,
		            currentResult * (index == 0 ? 1 : currentNumber), // Multiplication spéciale pour le 1er élément
		            currentExpression.isEmpty() ? "" + currentNumber : currentExpression + " * " + currentNumber,
		            consumer,
		            stopSignal
		    );
		}
		private static void explore2(
		        int index,
		        List<Long> numbers,
		        Long target,
		        Long currentResult,
		        String currentExpression,
		        Consumer<String> consumer,
		        AtomicBoolean stopSignal) {

		    // Debug : Afficher les paramètres actuels
		    //System.out.println("Index: " + index + ", Result: " + currentResult + ", Expression: " + currentExpression);

		    if (stopSignal.get()) return;

		    if (index == numbers.size()) {
		        if (currentResult.equals(target)) {
		            System.out.println("Expression valide trouvée : " + currentExpression);
		            consumer.accept(currentExpression);
		            stopSignal.set(true);
		        }
		        return;
		    }

		    Long currentNumber = numbers.get(index);

		    explore2(
		            index + 1,
		            numbers,
		            target,
		            currentResult + currentNumber,
		            currentExpression.isEmpty() ? "" + currentNumber : currentExpression + " + " + currentNumber,
		            consumer,
		            stopSignal
		    );

		    explore2(
		            index + 1,
		            numbers,
		            target,
		            currentResult * currentNumber,
		            currentExpression.isEmpty() ? "" + currentNumber : currentExpression + " * " + currentNumber,
		            consumer,
		            stopSignal
		    );

		    if (!currentExpression.isEmpty()) {
		        Long concatenatedResult = Long.parseLong(currentResult + "" + currentNumber);
		       // System.out.println("Concaténation : " + currentResult + " || " + currentNumber + " = " + concatenatedResult);
		        explore2(
		                index + 1,
		                numbers,
		                target,
		                concatenatedResult,
		                currentExpression + " || " + currentNumber,
		                consumer,
		                stopSignal
		        );
		    }
		}
	}


	@Data
	@AllArgsConstructor
	private static class Equation {
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

package a2021;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import outils.MesOutils;

public class A2021Day7 extends A2021 {

	public A2021Day7(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day7 d = new A2021Day7(7);
		System.out.println(d.s1(true));
		System.out.println(d.s2(true));

	}
	private Long s1(boolean c) {
		List<Integer> listOfIntegers = Arrays.asList(getInput(c).split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
		List<Long> couts=IntStream.rangeClosed(MesOutils.getMinIntegerFromList(listOfIntegers),MesOutils.getMaxIntegerFromList(listOfIntegers)).mapToLong(i->coutDeplacement(listOfIntegers,i)).boxed().collect(Collectors.toList());
		return MesOutils.getMinLongFromList(couts);

	}
	private Long s2(boolean c) {
		List<Integer> listOfIntegers = Arrays.asList(getInput(c).split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
		List<Long> couts=IntStream.rangeClosed(MesOutils.getMinIntegerFromList(listOfIntegers),MesOutils.getMaxIntegerFromList(listOfIntegers)).mapToLong(i->coutDeplacement2(listOfIntegers,i)).boxed().collect(Collectors.toList());
		return MesOutils.getMinLongFromList(couts);
	}
	private Long coutDeplacement(List<Integer> listOfIntegers, int j) {
		return listOfIntegers.stream().mapToLong(l->Math.abs(l-j)).reduce((long) 0, (a, b) -> a + b);
	}
	private Long coutDeplacement2(List<Integer> listOfIntegers, int j) {
		return listOfIntegers.stream().mapToLong(l->((Math.abs(l-j))*(Math.abs(l-j)+1))/2).reduce((long) 0, (a, b) -> a + b);
	}
	public static List<Long> getDuration() {
		A2021Day7 d = new A2021Day7(7);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1,endTime - startTime);
	}
	
}

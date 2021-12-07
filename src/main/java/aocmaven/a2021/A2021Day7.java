package aocmaven.a2021;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class A2021Day7 extends A2021 {

	public A2021Day7(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2021Day7 d = new A2021Day7(7);
		//d.s1(true);
		d.s2(true);

	}

	private void s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split(","));
		List<Integer> listOfIntegers = input.stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
		Integer max = listOfIntegers.stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new);
		Integer min = listOfIntegers.stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new);

		int coutMin = 10000000;
		int jmin = -1;
		for (int j = min; j <= max; j++) {
			int cout = 0;
			for (Integer i : listOfIntegers) {
				cout += Math.abs(j - i);
			}
			
			if(cout<coutMin) {
				jmin=j;
				coutMin=cout;
			}
		}
		System.out.println(jmin);
		System.out.println(coutMin);

	}
	private void s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split(","));
		List<Integer> listOfIntegers = input.stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
		Integer max = listOfIntegers.stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new);
		Integer min = listOfIntegers.stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new);

		BigInteger coutMin =new BigInteger("1000000000000");
		int jmin = -1;
		for (int j = min; j <= max; j++) {
			BigInteger cout = BigInteger.ZERO;
			for (Integer i : listOfIntegers) {
				cout=cout.add(coutV2(Math.abs(j-i)));
			}
			
			if(cout.compareTo(coutMin)<0) {
				jmin=j;
				coutMin=cout;
			}
		}
		System.out.println(jmin);
		System.out.println(coutMin);

	}

	private BigInteger coutV2(int i) {
		int res=0;
		if(i % 2 ==0) {
			 res=(i/2)*(i+1);
			
		} else {
			 res=((i+1)/2)*(i);
		}
		
		return new BigInteger(String.valueOf(res));
	}

}

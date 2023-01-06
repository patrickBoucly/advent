package a2022;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A2022Day25 extends A2022 {

	public A2022Day25(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day25 d = new A2022Day25(25);
		long startTime = System.currentTimeMillis();
		System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		// System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public String s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		return null;
	}

	public String s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		Long decimalRes = input.stream().map(A2022Day25::getDEcFromSNAFU).reduce(0L, Long::sum);
		return getSnafuFromDec(decimalRes);
	}

	private String getSnafuFromDec(Long decimalRes) {
		int n = 0;
		String res = "";
		while (decimalRes /(2*Math.pow(5, n))>1) {
			n++;
		}
		
		Long m1=0L;
		Long m2=0L;
		Long pas=0L;
		while(n>-1) {
			pas=(long) Math.pow(5, n);
			if(decimalRes==0) {
				res += "0";
			} else if(decimalRes>0) {
				m1=decimalRes-pas;
				m2=decimalRes-2*pas;
				if(decimalRes<Math.abs(m1)) {
					res += "0";
				} else if(Math.abs(m1)<Math.abs(m2)) {
					res += "1";
					decimalRes = m1;
				} else {
					res += "2";
					decimalRes = m2;
				}
			} else {
				m1=decimalRes+pas;
				m2=decimalRes+2*pas;
				if(-decimalRes<Math.abs(m1)) {
					res += "0";
				} else if(Math.abs(m1)<Math.abs(m2)) {
					res += "-";
					decimalRes = m1;
				} else {
					res += "=";
					decimalRes = m2;
				}
			}
			n--;
		}
		return res;
	}

	public static Long getDEcFromSNAFU(String snafu) {
	BigInteger cinq = BigInteger.valueOf(5L);
		BigInteger res = BigInteger.valueOf(getValue(snafu.substring(snafu.length() - 1)));
		int n = snafu.length();
		for (int i = 1; i < n; i++) {
			BigInteger add = cinq.pow(i).multiply(BigInteger.valueOf(getValue(snafu.substring(n - i - 1, n - i))));
			res = res.add(add);
		}
		return res.longValue();
	}

	public static Long getValue(String s) {
		if (s.equals("=")) {
			return -2L;
		}
		if (s.equals("-")) {
			return -1L;
		}
		return Long.valueOf(s);
	}

	public static List<Long> getDuration() {
		A2022Day25 d = new A2022Day25(25);
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

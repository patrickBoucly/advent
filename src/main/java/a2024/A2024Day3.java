package a2024;

import java.util.Arrays;
import java.util.List;

public class A2024Day3 extends A2024 {

	public A2024Day3(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day3 d = new A2024Day3(3);
		//System.out.println(d.s1(true));
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
		String inputL = getInput(b);
		System.out.println(inputL);
		String[] sp = inputL.split("mul\\(");
		int res=0;
		for (String s : sp) {
			System.out.println(s);
			if (s.contains(")")) {
				String sub = s.substring(0,s.indexOf(")"));
				
				if (sub.contains(",")) {
					int posVir=sub.indexOf(",");
					String g=sub.substring(0,posVir);
					String d=sub.substring(posVir+1);
					if(g.matches("-?\\d+") && d.matches("-?\\d+")) {
						res+=Integer.parseInt(g)*Integer.parseInt(d);
					}
				}

			}
		}


		return res;
	}

	public int s2(boolean b) {
		String inputL = getInput(b);
		System.out.println(inputL);
		String[] sp = inputL.split("mul\\(");
		int res=0;
		boolean faire=true;
		for (String s : sp) {
			System.out.println(s);
			if (s.contains(")")) {
				String sub = s.substring(0,s.indexOf(")"));
				
				if (sub.contains(",")) {
					int posVir=sub.indexOf(",");
					String g=sub.substring(0,posVir);
					String d=sub.substring(posVir+1);
					if(g.matches("-?\\d+") && d.matches("-?\\d+")) {
						if(faire)
						res+=Integer.parseInt(g)*Integer.parseInt(d);
					}
				}

			}
			boolean doo=s.contains("do()");
			boolean dont=s.contains("don't()");
			if(doo && !dont) {
				faire=true;
			}
			if(!doo && dont) {
				faire=false;
			}
			if(doo && dont) {
				faire=s.lastIndexOf("do()")>s.lastIndexOf("do()");
			}
		}


		return res;

	}

	public static List<Long> getDuration() {
		A2024Day3 d = new A2024Day3(3);
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

package a2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.experimental.var;

public class A2024Day2 extends A2024 {

	public A2024Day2(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day2 d = new A2024Day2(2);
		System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	public long s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(s -> s.trim()).toList();
		List<Report> reports=new ArrayList<>();
		for(String l:inputL) {
			Report report=new Report();
			List<Integer> myNumbers=new ArrayList<>();
			String[] sp=l.split(" ");
			for(int j=0;j<sp.length;j++) {
				myNumbers.add(Integer.parseInt(sp[j]));			
			}
			report.setMyNumbers(myNumbers);
			reports.add(report);
			
		}
		for(Report r:reports) {
			r.evaluerReport();
		}
		int cpt=0;
		for(Report r:reports) {
			if(r.safe) {
				cpt=cpt+1;
			}
		}
		return cpt;
	}

	public int s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(s -> s.trim()).toList();
		List<Report> reports=new ArrayList<>();
		for(String l:inputL) {
			Report report=new Report();
			List<Integer> myNumbers=new ArrayList<>();
			String[] sp=l.split(" ");
			for(int j=0;j<sp.length;j++) {
				myNumbers.add(Integer.parseInt(sp[j]));			
			}
			report.setMyNumbers(myNumbers);
			reports.add(report);
			
		}
		for(Report r:reports) {
			r.evaluerReport2();
		}
		int cpt=0;
		for(Report r:reports) {
			if(r.safe) {
				cpt=cpt+1;
			}
		}
		return cpt;

	}

	@Data
	public static class Report {
		List<Integer> myNumbers=new ArrayList<>();
		List<Integer> variation=new ArrayList<>();
		boolean safe=false;

		public boolean isSafe() {
			List<Integer> inc = List.of(1, 2, 3);
			List<Integer> dec = List.of(-1, -2, -3);
			
			return variation.stream().allMatch(inc::contains) || variation.stream().allMatch(dec::contains);
		}
		public boolean isSafe2() {
			for(int j=0;j<myNumbers.size();j++) {
				Report np=new Report();
				List<Integer> npNumbers=new ArrayList<>();
				for(int k=0;k<myNumbers.size();k++) {
					if(k != j) {
						npNumbers.add(myNumbers.get(k));
					}
				}
				np.setMyNumbers(npNumbers);
				np.evaluerReport();
				if(np.isSafe()) {
					return true;
				}
			}
			return false;
		}

		public void evaluerReport() {
			for (int i = 0; i < myNumbers.size()-1; i++) {
				variation.add(myNumbers.get(i + 1) - myNumbers.get(i));
			}
			setSafe(isSafe());
		}
		public void evaluerReport2() {
			for (int i = 0; i < myNumbers.size()-1; i++) {
				variation.add(myNumbers.get(i + 1) - myNumbers.get(i));
			}
			setSafe(isSafe2());
		}
		
	}

	public static List<Long> getDuration() {
		A2024Day2 d = new A2024Day2(2);
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

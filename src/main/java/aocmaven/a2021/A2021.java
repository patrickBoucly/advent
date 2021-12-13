package aocmaven.a2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class A2021  {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	int day;
	public A2021(int day) {
		super();
		this.day=day;
	}
	public static A2021 getDay(int i) {
		return new A2021(i);
	}
	public String getInput(boolean donnesComplete ) {
		return read(donnesComplete,day);
	}
	
	public String read(boolean donnesComplete, int day) {
		String filePath = "src/main/resources/a2021/input" + day;
		String suffixe = (donnesComplete ? "" : "_s");
		String content = "";
		Path path = Paths.get(filePath + suffixe);
		try {
			content = Files.readString(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;

	}
	
	public static void printDuration() {
		String res="---------------------------------------------------------------";
		res+="\n";
		res+="-------------------Advent of Code 2021-------------------------";
		res+="\n";
		res+="--------------------------TIMINGS-----------------------------";
		res+="\n";
		res+="---------------------------------------------------------------";
		res+="\n";
		res+=getColoredLine(A2021Day1.getDuration(),1);
		res+=getColoredLine(A2021Day2.getDuration(),2);
		res+=getColoredLine(A2021Day3.getDuration(),3);
		res+=getColoredLine(A2021Day4.getDuration(),4);
		res+=getColoredLine(A2021Day5.getDuration(),5);
		res+=getColoredLine(A2021Day6.getDuration(),6);
		res+=getColoredLine(A2021Day7.getDuration(),7);
		res+=getColoredLine(A2021Day8.getDuration(),8);
		res+=getColoredLine(A2021Day9.getDuration(),9);
		res+=getColoredLine(A2021Day10.getDuration(),10);
		res+=getColoredLine(A2021Day11.getDuration(),11);
		res+=getColoredLine(A2021Day12.getDuration(),12);
		res+=getColoredLine(A2021Day13.getDuration(),13);
		System.out.println(res);
		
	}
	
	private static String getColoredLine(List<Long> time, int i) {
		String res ="Day "+ i+" run 1 took ";
		if(time.get(0)<100) {
			res+=ANSI_GREEN + time.get(0) + ANSI_RESET;
		}else if(time.get(0)<1000) {
			res+=ANSI_YELLOW + time.get(0) + ANSI_RESET;
		}else if(time.get(0)<10000) {
			res+=ANSI_RED + time.get(0) + ANSI_RESET;
		} else {
			res+=ANSI_PURPLE + time.get(0) + ANSI_RESET;
		}
		res+=" milliseconds, run 2 took ";
		
		
		if(time.get(1)<100) {
			res+=ANSI_GREEN + time.get(1) + ANSI_RESET;
		}else if(time.get(1)<1000) {
			res+=ANSI_YELLOW + time.get(1) + ANSI_RESET;
		}else if(time.get(1)<10000) {
			res+=ANSI_RED + time.get(1) + ANSI_RESET;
		} else {
			res+=ANSI_PURPLE + time.get(1) + ANSI_RESET;
		}
		res+=" milliseconds";
		res+="\n";
		return res;
	}
	public static void main(String[] args0) {
		printDuration();
		
	}
}
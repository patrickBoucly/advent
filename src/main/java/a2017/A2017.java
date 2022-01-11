package a2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class A2017  {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	int day;
	public A2017(int day) {
		super();
		this.day=day;
	}
	public static A2017 getDay(int i) {
		return new A2017(i);
	}
	public String getInput(boolean donnesComplete ) {
		return read(donnesComplete,day);
	}
	
	public String read(boolean donnesComplete, int day) {
		String filePath = "src/main/resources/a2017/input" + day;
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
		res+="-------------------Advent of Code 2017-------------------------";
		res+="\n";
		res+="--------------------------TIMINGS-----------------------------";
		res+="\n";
		res+="---------------------------------------------------------------";
		res+="\n";
		int cpt=0;
		res+=getColoredLine(A2017Day1.getDuration(),1);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day2.getDuration(),2);

		System.out.println(cpt++);
		res+=getColoredLine(A2017Day3.getDuration(),3);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day4.getDuration(),4);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day5.getDuration(),5);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day6.getDuration(),6);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day7.getDuration(),7);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day8.getDuration(),8);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day9.getDuration(),9);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day10.getDuration(),10);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day11.getDuration(),11);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day12.getDuration(),12);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day13.getDuration(),13);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day14.getDuration(),14);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day15.getDuration(),15);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day16.getDuration(),16);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day17.getDuration(),17);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day18.getDuration(),18);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day19.getDuration(),19);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day20.getDuration(),20);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day17.getDuration(),17);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day22.getDuration(),22);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day23.getDuration(),23);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day24.getDuration(),24);
		System.out.println(cpt++);
		res+=getColoredLine(A2017Day25.getDuration(),25);
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

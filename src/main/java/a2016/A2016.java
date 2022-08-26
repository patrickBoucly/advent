package a2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class A2016  {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	int day;
	public A2016(int day) {
		super();
		this.day=day;
	}
	public static A2016 getDay(int i) {
		return new A2016(i);
	}
	public String getInput(boolean donnesComplete ) {
		return read(donnesComplete,day);
	}
	
	public String read(boolean donnesComplete, int day) {
		String filePath = "src/main/resources/a2016/input" + day;
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
		res+="-------------------Advent of Code 2016-------------------------";
		res+="\n";
		res+="--------------------------TIMINGS-----------------------------";
		res+="\n";
		res+="---------------------------------------------------------------";
		res+="\n";
		int cpt=0;
		res+=getColoredLine(A2016Day1.getDuration(),1);
		
		
		res+=getColoredLine(A2016Day2.getDuration(),2);

		
		res+=getColoredLine(A2016Day3.getDuration(),3);
		
		res+=getColoredLine(A2016Day4.getDuration(),4);
		
		res+=getColoredLine(A2016Day5.getDuration(),5);
		
		/*
		res+=getColoredLine(A2016Day6.getDuration(),6);
		
		
		res+=getColoredLine(A2016Day7.getDuration(),7);
		
		res+=getColoredLine(A2016Day8.getDuration(),8);
		
		res+=getColoredLine(A2016Day9.getDuration(),9);
		
		res+=getColoredLine(A2016Day10.getDuration(),10);
		
		res+=getColoredLine(A2016Day11.getDuration(),11);
		
		res+=getColoredLine(A2016Day12.getDuration(),12);
		
		res+=getColoredLine(A2016Day13.getDuration(),13);
		
		res+=getColoredLine(A2016Day14.getDuration(),14);
		
		res+=getColoredLine(A2016Day15.getDuration(),15);
		
		res+=getColoredLine(A2016Day16.getDuration(),16);
		
		res+=getColoredLine(A2016Day17.getDuration(),17);
		
		res+=getColoredLine(A2016Day18.getDuration(),18);
		
		res+=getColoredLine(A2016Day19.getDuration(),19);
		
		res+=getColoredLine(A2016Day20.getDuration(),20);
		
		res+=getColoredLine(A2016Day17.getDuration(),17);
		
		res+=getColoredLine(A2016Day22.getDuration(),22);
		
		res+=getColoredLine(A2016Day23.getDuration(),23);
		
		res+=getColoredLine(A2016Day24.getDuration(),24);
		
		res+=getColoredLine(A2016Day25.getDuration(),25);
		*/
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

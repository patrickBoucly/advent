package a2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import outils.MesOutils;

@Getter
@Setter
public class A2023  {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	int day;
	public A2023(int day) {
		super();
		this.day=day;
	}
	public static A2023 getDay(int i) {
		return new A2023(i);
	}
	public String getInput(boolean donnesComplete ) {
		return read(donnesComplete,day);
	}
	public String getExtraInput(boolean donnesComplete,int parametre ) {
		return readExtra(donnesComplete,day,parametre);
	}
	
	public String readExtra(boolean donnesComplete, int day,int parametre) {
		String filePath = "src/main/resources/A2023/input" + day+"_extra_"+parametre;
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
	
	public String read(boolean donnesComplete, int day) {
		String filePath = "src/main/resources/A2023/input" + day;
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
		int cpt=0;
		res+=getColoredLine(A2023Day01.getDuration(),1);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day02.getDuration(),2);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day03.getDuration(),3);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day04.getDuration(),4);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day05.getDuration(),5);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day06.getDuration(),6);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day07.getDuration(),7);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day08.getDuration(),8);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day09.getDuration(),9);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day10.getDuration(),10);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day11.getDuration(),11);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day12.getDuration(),12);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day13.getDuration(),13);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day14.getDuration(),14);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day15.getDuration(),15);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day16.getDuration(),16);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day17.getDuration(),17);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day18.getDuration(),18);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day19.getDuration(),19);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day20.getDuration(),20);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day21.getDuration(),21);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day22.getDuration(),22);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day23.getDuration(),23);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day24.getDuration(),24);
		System.out.println(cpt++);
		res+=getColoredLine(A2023Day25.getDuration(),25);
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

package a2022;

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
public class A2022  {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	int day;
	public A2022(int day) {
		super();
		this.day=day;
	}
	public static A2022 getDay(int i) {
		return new A2022(i);
	}
	public String getInput(boolean donnesComplete ) {
		return read(donnesComplete,day);
	}
	public String getExtraInput(boolean donnesComplete,int parametre ) {
		return readExtra(donnesComplete,day,parametre);
	}
	
	public String readExtra(boolean donnesComplete, int day,int parametre) {
		String filePath = "src/main/resources/A2022/input" + day+"_extra_"+parametre;
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
		String filePath = "src/main/resources/A2022/input" + day;
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
		res+=getColoredLine(A2022Day01.getDuration(),1);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day02.getDuration(),2);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day03.getDuration(),3);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day04.getDuration(),4);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day05.getDuration(),5);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day06.getDuration(),6);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day07.getDuration(),7);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day08.getDuration(),8);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day09.getDuration(),9);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day10.getDuration(),10);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day11.getDuration(),11);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day12.getDuration(),12);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day13.getDuration(),13);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day14.getDuration(),14);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day15.getDuration(),15);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day16.getDuration(),16);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day17.getDuration(),17);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day18.getDuration(),18);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day19.getDuration(),19);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day20.getDuration(),20);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day21.getDuration(),21);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day22.getDuration(),22);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day23.getDuration(),23);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day24.getDuration(),24);
		System.out.println(cpt++);
		res+=getColoredLine(A2022Day25.getDuration(),25);
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

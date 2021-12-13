package aocmaven.a2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class A2021  {
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
		res+=A2021Day1.getDuration();
		res+="\n";
		res+=A2021Day2.getDuration();
		res+="\n";
		res+=A2021Day3.getDuration();
		res+="\n";
		res+=A2021Day4.getDuration();
		res+="\n";
		res+=A2021Day5.getDuration();
		res+="\n";
		res+=A2021Day6.getDuration();
		res+="\n";
		res+=A2021Day7.getDuration();
		res+="\n";
		res+=A2021Day8.getDuration();
		res+="\n";
		res+=A2021Day9.getDuration();
		res+="\n";
		res+=A2021Day10.getDuration();
		res+="\n";
		res+=A2021Day11.getDuration();
		res+="\n";
		res+=A2021Day12.getDuration();
		res+="\n";
		res+=A2021Day13.getDuration();
	
		System.out.println(res);
		
	}
	
	public static void main(String[] args0) {
		printDuration();
		
	}
}

package aocmaven.a2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A2021Day1 {

	public static void main(String[] args0) {
		 s1();
		s2();
	}

	private static void s1() {
		String input=read();
		List<Integer> inputL=Arrays.asList(input.split("\n")).stream().map(Integer::parseInt).collect(Collectors.toList());
		System.out.println("s1 : "+getNbIncrease(inputL));
	}

	private static int getNbIncrease(List<Integer> inputL) {
		int res=0;
		for(int i=1;i<inputL.size();i++) {
			if(inputL.get(i-1)<inputL.get(i)){
				res++;
			}
		}
		return res;
	}
	
	private static void s2() {
		String input=read();
		List<Integer> inputL=Arrays.asList(input.split("\n")).stream().map(Integer::parseInt).collect(Collectors.toList());
		List<Integer> sommeDe3= new ArrayList<>();
		for(int i=0;i<inputL.size()-2;i++) {
			sommeDe3.add(inputL.get(i)+inputL.get(i+1)+inputL.get(i+2));
		}
		System.out.println("s2 : "+getNbIncrease(sommeDe3));
		
	}
	
	private static String read() {
		Path path = Paths.get(
				"C:\\git_repositories\\advent\\src\\main\\resources\\src\\advent_of_code\\main\\resources\\a2021\\input1");
		String content = "";
		try {
			content = Files.readString(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;

	}
}

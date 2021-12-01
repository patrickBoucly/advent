package aocmaven.a2021;

import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A2021Day1 {

	public static void main(String[] args0) {
		 s1();
		s2();
	}

	private static void s1() {
		String input=read();
		List<String> inputL=Arrays.asList(input.split("\n"));
		int res=0;
		for(int i=1;i<inputL.size();i++) {
			if(Integer.parseInt(inputL.get(i-1))<Integer.parseInt(inputL.get(i))){
				res++;
			}
		}
		System.out.println("s1 : "+res);
	}
	
	private static void s2() {
		String input=read();
		List<String> inputL=Arrays.asList(input.split("\n"));
		int res=0;
		List<Integer> sommeDe3= new ArrayList<>();
		for(int i=0;i<inputL.size()-2;i++) {
			sommeDe3.add(Integer.parseInt(inputL.get(i))+Integer.parseInt(inputL.get(i+1))+Integer.parseInt(inputL.get(i+2)));
		}
		for(int i=1;i<sommeDe3.size();i++) {
			if(sommeDe3.get(i-1)<sommeDe3.get(i)){
				res++;
			}
		}
		
		
		System.out.println("s2 : "+res);
		
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

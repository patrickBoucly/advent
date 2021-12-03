package aocmaven.a2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A2021Day3 {

	public static void main(String[] args0) {
		///s1();
		 s2();
	}

	private static void s1() {
		List<String> input;
		input = Arrays.asList(read().split("\n"));
		String gamma = getGamma(input);
		String epsilon = getEpsilon(input);

		System.out.println(Integer.parseInt(gamma, 2)*Integer.parseInt(epsilon, 2));
	}

	private static String getEpsilon(List<String> input) {
		String epsilon = "";
		for (int pos = 0; pos < input.get(0).length(); pos++) {
			int nb0 = 0;
			int nb1 = 0;
			for (String s : input) {
				if (s.substring(pos, pos + 1).equals("1")) {
					nb1++;
				} else {
					nb0++;
				}
			}
			if(nb1<nb0) {
				epsilon+="1";
			} else {
				epsilon+="0";
			}
		}
		return epsilon;
	}

	private static String getGamma(List<String> input) {
		String gamma = "";
		for (int pos = 0; pos < input.get(0).length(); pos++) {
			int nb0 = 0;
			int nb1 = 0;
			for (String s : input) {
				if (s.substring(pos, pos + 1).equals("1")) {
					nb1++;
				} else {
					nb0++;
				}
			}
			if(nb1>nb0) {
				gamma+="1";
			} else {
				gamma+="0";
			}
		}
		return gamma;
	}
	
	private static String getPlusCourante(List<String> input,Integer pos) {
		String plusCourante = "";
		
			int nb0 = 0;
			int nb1 = 0;
			for (String s : input) {
				if (s.substring(pos, pos + 1).equals("1")) {
					nb1++;
				} else {
					nb0++;
				}
			}
			if(nb1>=nb0) {
				plusCourante+="1";
			} else {
				plusCourante+="0";
			}
		
		return plusCourante;
	}
	
	private static String getMoinsCourante(List<String> input,Integer pos) {
		String moinsCourante = "";
		
			int nb0 = 0;
			int nb1 = 0;
			for (String s : input) {
				if (s.substring(pos, pos + 1).equals("1")) {
					nb1++;
				} else {
					nb0++;
				}
			}
			if(nb1<nb0) {
				moinsCourante+="1";
			} else {
				moinsCourante+="0";
			}
		
		return moinsCourante;
	}
	

	private static void s2() {
		List<String> input;
		input = Arrays.asList(read().split("\n"));
		String generateur=getGenerateur(input);
		String epurateur=getEpurateur(input);
		System.out.println(Integer.parseInt(generateur, 2));
		System.out.println(Integer.parseInt(epurateur, 2));
		System.out.println(Integer.parseInt(generateur, 2)*Integer.parseInt(epurateur, 2));
	}

	private static String getEpurateur(List<String> input) {
		List<String> codeConserves;
		int pos=0;
		while(input.size()>1) {
			codeConserves = new ArrayList<>();
			String conserver =getMoinsCourante(input, pos);
			for(String s:input) {
				if(s.substring(pos, pos+1).equals(conserver)) {
					codeConserves.add(s);
				}
			}
			input=new ArrayList<>(codeConserves);
			pos++;
		}
		return input.get(0);
	}

	private static String getGenerateur(List<String> input) {
		List<String> codeConserves;
		int pos=0;
		while(input.size()>1) {
			codeConserves = new ArrayList<>();
			String conserver = getPlusCourante(input, pos);
			for(String s:input) {
				if(s.substring(pos, pos+1).equals(conserver)) {
					codeConserves.add(s);
				}
			}
			input=new ArrayList<>(codeConserves);
			pos++;
		}
		return input.get(0);
	}

	private static String read() {
		Path path = Paths.get(
				"C:\\git_repositories\\advent\\src\\main\\resources\\src\\advent_of_code\\main\\resources\\a2021\\input3");
		String content = "";
		try {
			content = Files.readString(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;

	}
}

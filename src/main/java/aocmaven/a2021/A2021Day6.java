package aocmaven.a2021;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class A2021Day6 {

	public static void main(String[] args0) {
		//s1();
		//s2();
		s(false,18);
		s(false,80);
		s(false,256);
		s(true,18);
		s(true,80);
		s(true,256);
	}

	private static void s(boolean b, int nbDay) {
		String champs=(b ? "full input" : "sample data");
		List<String> input = Arrays.asList(read(b).split(","));
		List<Integer> inputNum = input.stream().map(s->Integer.parseInt(s.trim())).collect(Collectors.toList());
		Map<Integer,BigInteger> comptage= new HashMap<>();
		for(Integer i:inputNum) {
			if(comptage.containsKey(i)) {
				comptage.put(i, comptage.get(i).add(BigInteger.ONE));
			} else {
				comptage.put(i,BigInteger.ONE);
			}
		}
		for(int k=0;k<=8;k++) {
			if(!comptage.containsKey(k)) {
				comptage.put(k, BigInteger.ZERO);
			}
		}
		Map<Integer,BigInteger> nextComptage= new HashMap<>();
		int day =0;
		while(day<nbDay) {
			nextComptage= new HashMap<>();
			for(int k=0;k<=8;k++) {
				if(k==6) {
					nextComptage.put(6, comptage.get(7).add(comptage.get(0)));
				} else if(k==8) {
					nextComptage.put(8,comptage.get(0));
				}else {
					nextComptage.put(k,comptage.get(k+1));
				}
			}
			comptage= new HashMap<>(nextComptage);
			day++;
		}
		BigInteger tot=BigInteger.ZERO;
		for(Integer i:nextComptage.keySet()) {
			tot=tot.add( nextComptage.get(i));
		}
		System.out.println("number of fish after "+nbDay+" days on "+champs +" : "+tot);
	}
	private static String read(boolean donnesComplete) {
		String suffixe="";
		if(!donnesComplete) {
			suffixe+="_s";
		}
		Path path = Paths.get(
				"C:\\git_repositories\\advent\\src\\main\\resources\\src\\advent_of_code\\main\\resources\\a2021\\input6"+suffixe);
		String content = "";
		try {
			content = Files.readString(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
}

package a2016;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class A2016Day6 extends A2016 {
	public A2016Day6(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day6 d = new A2016Day6(6);
		long startTime = System.currentTimeMillis();
		System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	private String s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim())
				.collect(Collectors.toList());
		String res = "";
		for (int i = 0; i < input.get(0).length(); i++) {
			HashMap<String, Integer> letters = getLettres(getColumMessage(input, i));
			res += lessFreqLetter(letters);
		}
		return res;
	}

	private String lessFreqLetter(HashMap<String, Integer> lettres) {
		Set<Entry<String, Integer>> entries = lettres.entrySet();
		Comparator<Entry<String, Integer>> valueComparator = new Comparator<>() {

			@Override
			public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
				Integer v1 = e1.getValue();
				Integer v2 = e2.getValue();
				String l1 = e1.getKey();
				String l2 = e2.getKey();
				if (v1 == v2) {
					return l1.compareTo(l2);
				} else {
					return v1.compareTo(v2);
				}

			}
		};

		List<Entry<String, Integer>> listOfEntries = new ArrayList<Entry<String, Integer>>(entries);

		Collections.sort(listOfEntries, valueComparator);

		LinkedHashMap<String, Integer> sortedByValue = new LinkedHashMap<String, Integer>(listOfEntries.size());

		for (Entry<String, Integer> entry : listOfEntries) {
			sortedByValue.put(entry.getKey(), entry.getValue());
		}

		Set<Entry<String, Integer>> entrySetSortedByValue = sortedByValue.entrySet();
		
		return entrySetSortedByValue.stream().findFirst().get().getKey();
	}

	private String s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim())
				.collect(Collectors.toList());
		String res = "";
		for (int i = 0; i < input.get(0).length(); i++) {
			HashMap<String, Integer> letters = getLettres(getColumMessage(input, i));
			res += mostFreqLetter(letters);
		}
		return res;
	}

	private String mostFreqLetter(HashMap<String, Integer> lettres) {
		Set<Entry<String, Integer>> entries = lettres.entrySet();
		Comparator<Entry<String, Integer>> valueComparator = new Comparator<>() {

			@Override
			public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
				Integer v1 = e1.getValue();
				Integer v2 = e2.getValue();
				String l1 = e1.getKey();
				String l2 = e2.getKey();
				if (v1 == v2) {
					return l1.compareTo(l2);
				} else {
					return v2.compareTo(v1);
				}

			}
		};

		List<Entry<String, Integer>> listOfEntries = new ArrayList<Entry<String, Integer>>(entries);

		Collections.sort(listOfEntries, valueComparator);

		LinkedHashMap<String, Integer> sortedByValue = new LinkedHashMap<String, Integer>(listOfEntries.size());

		for (Entry<String, Integer> entry : listOfEntries) {
			sortedByValue.put(entry.getKey(), entry.getValue());
		}

		Set<Entry<String, Integer>> entrySetSortedByValue = sortedByValue.entrySet();
	
		return entrySetSortedByValue.stream().findFirst().get().getKey();
	}

	private String getColumMessage(List<String> input, int i) {
		String m = "";
		for (int j = 0; j < input.size(); j++) {
			m += input.get(j).substring(i, i + 1);
		}
		return m;
	}

	public String getLetterIndex(String s, int i) {
		return s.substring(i, i + 1);
	}

	private String hash(String password) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");

			md.update(password.getBytes());

			byte byteData[] = md.digest();

			// convertir le tableau de bits en une format hexadécimal - méthode 1
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	private HashMap<String, Integer> getLettres(String mot) {
		HashMap<String, Integer> lettreDuMot = new HashMap<>();
		for (int pos = 0; pos < mot.length(); pos++) {
			String l = mot.substring(pos, pos + 1);
			if (lettreDuMot.containsKey(l)) {
				lettreDuMot.put(l, lettreDuMot.get(l) + 1);
			} else {
				lettreDuMot.put(l, 1);
			}
		}
		return lettreDuMot;
	}

	public static List<Long> getDuration() {
		A2016Day6 d = new A2016Day6(6);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1, endTime - startTime);
	}

}

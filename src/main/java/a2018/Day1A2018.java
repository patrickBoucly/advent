package a2018;

import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day1A2018 {

	public static void main(String[] args) {
		String message = getMessage(
				"C:\\Users\\z11r88\\eclipse-workspace2\\advent_of_code\\src\\advent_of_code\\main\\resources\\a2018\\input1");
		int res = 0;
		for (String s : message.split("\n")) {
			if (s.subSequence(0, 1).equals("+")) {
				res += Integer.parseInt(s.substring(1));
			} else {
				res -= Integer.parseInt(s.substring(1));
			}
		}
		System.out.println(res);
		res = 0;
		List<Integer> step = new ArrayList<>();
		step.add(0);
		int rep = 99999999;
		boolean continuer = true;
		while (continuer) {
			for (String s : message.split("\n")) {
				if (continuer) {
					if (s.subSequence(0, 1).equals("+")) {
						res += Integer.parseInt(s.substring(1));
						if (!step.contains(res)) {
							step.add(res);
						} else {
							rep = res;
							continuer = false;
						}
						
					} else {
						res -= Integer.parseInt(s.substring(1));
						if (!step.contains(res)) {
							step.add(res);
						} else {
							rep = res;
							continuer = false;
						}
					}
				}
			}
		}
		System.out.println(rep);

	}

	private static String getMessage(String monFic) {
		String line = "";
		try {
			line = readFileToString(new File(monFic));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return line;
	}
}

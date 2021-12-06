package aocmaven.a2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class A2021 {
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
}

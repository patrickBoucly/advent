package a2020;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class A2020Day20 extends A2020 {

	public A2020Day20(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2020Day20 d = new A2020Day20(20);
		long startTime = System.currentTimeMillis();
		//System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public int s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		Set<Tile> tiles=new HashSet<>();
		Tile t=new Tile();
		int num=0;
		Set<Point> pts=new HashSet<>();
		boolean deb=true;
		int numLigne=0;
		for(String s:inputL) {
			if(s.isBlank()) {
				if(!deb) {
					t.setNum(num);
					t.setPts(pts);
					tiles.add(t);
					numLigne=0;
					pts=new HashSet<>();
					t=new Tile();
				}else {
					deb=false;
				}
			}else if(s.substring(0, 1).equals("T")) {
				num=Integer.parseInt(s.split(" ")[1].split(":")[0].trim());
			} else {
				for(int i=0;i<s.length();i++) {
						pts.add(new Point(i, numLigne, s.substring(i, i+1).equals("#")));
				}
				numLigne++;
			}
			
		}
		for(Tile tile:tiles) {
			System.out.println(tile);
		}
		return 2;
	}

	public int s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		return 2;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	public class Tile{
		int num;
		Set<Point> pts;
	}
	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	public class Point{
		int x;
		int y;
		Boolean diese;
	}
	public static List<Long> getDuration() {
		A2020Day20 d = new A2020Day20(20);
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

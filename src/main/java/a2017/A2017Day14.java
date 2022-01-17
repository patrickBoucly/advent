package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class A2017Day14 extends A2017 {

	public A2017Day14(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day14 d = new A2017Day14(14);
		// System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		//d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public int s1(boolean b) {
		A2017Day10 d = new A2017Day10(10);
		String s = "nbysizxe-";
		List<String> grid = new ArrayList<>();
		for (int i = 0; i < 128; i++) {
			grid.add(hexToBinary(d.getSol2(s + i)));
		}
		int tot = 0;
		for (String row : grid) {
			for (int p = 0; p < row.length(); p++) {
				if (row.substring(p, p + 1).equals("1")) {
					tot++;
				}
			}
		}
		return tot;
	}

	public int s2(boolean b) {
		A2017Day10 d = new A2017Day10(10);
		String s="nbysizxe-";
		//String s = "flqrgnkx-";
		List<String> grid = new ArrayList<>();
		for (int i = 0; i < 128; i++) {
			grid.add(hexToBinary(d.getSol2(s + i)));
		}
		List<Cell> cgrid = new ArrayList<>();
		int numRow = 0;
		for (String row : grid) {
			for (int p = 0; p < row.length(); p++) {
				if (row.substring(p, p + 1).equals("1")) {
					cgrid.add(new Cell(numRow, p));
				}
			}
			numRow++;
		}
		int numZ = 0;
		for (Cell cg : cgrid) {
			if (cg.z == 0) {
				numZ++;
				cg.z = numZ;
				cg.setZone(cg.getAllV(cgrid));
			} 
		}
		for (Cell cg : cgrid) {
			if(cg.i==4 && cg.j==4) {
				System.out.println(cg.getAllV(cgrid));
			}
		}
		return numZ;
	}

	String hexToBinary(String hex) {

		// variable to store the converted
		// Binary Sequence
		String binary = "";

		// converting the accepted Hexadecimal
		// string to upper case
		hex = hex.toUpperCase();

		// initializing the HashMap class
		HashMap<Character, String> hashMap = new HashMap<Character, String>();

		// storing the key value pairs
		hashMap.put('0', "0000");
		hashMap.put('1', "0001");
		hashMap.put('2', "0010");
		hashMap.put('3', "0011");
		hashMap.put('4', "0100");
		hashMap.put('5', "0101");
		hashMap.put('6', "0110");
		hashMap.put('7', "0111");
		hashMap.put('8', "1000");
		hashMap.put('9', "1001");
		hashMap.put('A', "1010");
		hashMap.put('B', "1011");
		hashMap.put('C', "1100");
		hashMap.put('D', "1101");
		hashMap.put('E', "1110");
		hashMap.put('F', "1111");

		int i;
		char ch;

		// loop to iterate through the length
		// of the Hexadecimal String
		for (i = 0; i < hex.length(); i++) {
			// extracting each character
			ch = hex.charAt(i);

			// checking if the character is
			// present in the keys
			if (hashMap.containsKey(ch))

				// adding to the Binary Sequence
				// the corresponding value of
				// the key
				binary += hashMap.get(ch);

			// returning Invalid Hexadecimal
			// String if the character is
			// not present in the keys
			else {
				binary = "Invalid Hexadecimal String";
				return binary;
			}
		}

		// returning the converted Binary
		return binary;
	}

	public static class Cell {
		int i;
		int j;
		int z;

		public int getI() {
			return i;
		}

		public List<Cell> getAllV(List<Cell> cgrid) {
			Set<Cell> voiz=new HashSet<>();
			voiz.addAll(getV(cgrid));
			Set<Cell> nvoiz=new HashSet<>(voiz);
			int k=0;
			while(k<100) {
				voiz=new HashSet<>(nvoiz);
				for(Cell v:voiz) {
					nvoiz.addAll(v.getV(cgrid));
				}
				k++;
			}
			return new ArrayList<>(nvoiz);
		}

		public void setI(int i) {
			this.i = i;
		}

		public int getJ() {
			return j;
		}

		public void setJ(int j) {
			this.j = j;
		}

		public int getZ() {
			return z;
		}

		public void setZ(int z) {
			this.z = z;
		}

		public Cell(int i, int j) {
			super();
			this.i = i;
			this.j = j;
			this.z = 0;
		}

		public List<Cell> getV(List<Cell> grid) {
			List<Cell> v = new ArrayList<>();
			for (Cell c : grid) {
				if (c.i == i - 1 && c.j == j || c.i == i + 1 && c.j == j || c.i == i && c.j == j - 1
						|| c.i == i && c.j == j + 1) {
					v.add(c);
				}
			}

			return v;
		}

		public void setZone(List<Cell> v) {
			for (Cell c : v) {
				c.z=this.z;
			}
		}

		@Override
		public String toString() {
			return "Cell [i=" + i + ", j=" + j + ", z=" + z + "]";
		}

	}

	public static List<Long> getDuration() {
		A2017Day14 d = new A2017Day14(1);
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

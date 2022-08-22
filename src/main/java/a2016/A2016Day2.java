package a2016;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class A2016Day2 extends A2016 {
	public A2016Day2(int day) {
		super(day);
	}
	public static void main(String[] args0) {
		A2016Day2 d = new A2016Day2(2);
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
		String res="";
		System.out.println(input);
		for(String s:input) {
			String lastNumber =res.equals("") ? "5": res.substring(res.length()-1,res.length());
			res+=this.getCodeFromLine2(s,lastNumber);
		}
		return res;
	}
	private String s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim())
				.collect(Collectors.toList());
		String res="";
		System.out.println(input);
		for(String s:input) {
			String lastNumber =res.equals("") ? "5": res.substring(res.length()-1,res.length());
			res+=this.getCodeFromLine(s,lastNumber);
		}
		return res;
	}
	public String getCodeFromLine(String line, String lastNumber) {
		Position nextPos=getPosFromDigit(lastNumber);
		for(int i=0;i<line.length();i++) {
			nextPos=getNextPos(nextPos,line.substring(i, i+1));
		}
		return getCodeFromPosition(nextPos);
	}
	public String getCodeFromLine2(String line, String lastNumber) {
		Position nextPos=getPosFromDigit2(lastNumber);
		for(int i=0;i<line.length();i++) {
			nextPos=getNextPos2(nextPos,line.substring(i, i+1));
		}
		return getCodeFromPosition2(nextPos);
	}
	
	private String getCodeFromPosition(Position nextPos) {
		String res="";
		if(nextPos.a==-1 && nextPos.o==-1) {
			return "1";
		}
	
		if(nextPos.a==0 && nextPos.o==-1) {
			return "2";
		}
		if(nextPos.a==1 && nextPos.o==-1) {
			return "3";
		}
		if(nextPos.a==-1 && nextPos.o==0) {
			return "4";
		}
		if(nextPos.a==0 && nextPos.o==0) {
			return "5";
		}
		if(nextPos.a==1 && nextPos.o==0) {
			return "6";
		}
		if(nextPos.a==-1 && nextPos.o==1) {
			return "7";
		}
		if(nextPos.a==0 && nextPos.o==1) {
			return "8";
		}
		if(nextPos.a==1 && nextPos.o==1) {
			return "9";
		}
		return "0";
	}
	
	private String getCodeFromPosition2(Position nextPos) {
		String res="";
		if(nextPos.a==0 && nextPos.o==-2) {
			return "1";
		}
	
		if(nextPos.a==-1 && nextPos.o==-1) {
			return "2";
		}
		if(nextPos.a==0 && nextPos.o==-1) {
			return "3";
		}
		if(nextPos.a==1 && nextPos.o==-1) {
			return "4";
		}
		if(nextPos.a==-2 && nextPos.o==0) {
			return "5";
		}
		if(nextPos.a==-1 && nextPos.o==0) {
			return "6";
		}
		if(nextPos.a==0 && nextPos.o==0) {
			return "7";
		}
		if(nextPos.a==1 && nextPos.o==0) {
			return "8";
		}
		if(nextPos.a==2 && nextPos.o==0) {
			return "9";
		}
		if(nextPos.a==-1 && nextPos.o==1) {
			return "A";
		}
		if(nextPos.a==0 && nextPos.o==1) {
			return "B";
		}
		if(nextPos.a==1 && nextPos.o==1) {
			return "C";
		}
		if(nextPos.a==0 && nextPos.o==2) {
			return "D";
		}
		return "0";
	}
	private Position getNextPos2(Position nextPos, String dir) {
		if(dir.equals("U")) {
			if(Arrays.asList("5","2","1","4","9").contains(getCodeFromPosition2(nextPos))) {
				return nextPos;
			} else {
				return new Position(nextPos.a, nextPos.o-1);
			}
		} else if(dir.equals("D")) {
			if(Arrays.asList("5","A","D","C","9").contains(getCodeFromPosition2(nextPos))) {
				return nextPos;
			} else {
				return new Position(nextPos.a, nextPos.o+1);
			}
		} else if(dir.equals("L")) {
			if(Arrays.asList("5","2","1","A","D").contains(getCodeFromPosition2(nextPos))) {
				return nextPos;
			} else {
				return new Position(nextPos.a-1, nextPos.o);
			}
		} else if(dir.equals("R")) {
			if(Arrays.asList("C","D","1","4","9").contains(getCodeFromPosition2(nextPos))) {
				return nextPos;
			} else {
				return new Position(nextPos.a+1, nextPos.o);
			}
		} 
		return nextPos;
	}
	private Position getNextPos(Position nextPos, String dir) {
		if(dir.equals("U")) {
			if(nextPos.o==-1) {
				return nextPos;
			} else {
				return new Position(nextPos.a, nextPos.o-1);
			}
		} else if(dir.equals("D")) {
			if(nextPos.o==1) {
				return nextPos;
			} else {
				return new Position(nextPos.a, nextPos.o+1);
			}
		} else if(dir.equals("L")) {
			if(nextPos.a==-1) {
				return nextPos;
			} else {
				return new Position(nextPos.a-1, nextPos.o);
			}
		} else if(dir.equals("R")) {
			if(nextPos.a==1) {
				return nextPos;
			} else {
				return new Position(nextPos.a+1, nextPos.o);
			}
		} 
		return nextPos;
	}
	private Position getPosFromDigit(String lastNumber) {
		Position nextPos=new Position(0,0);
		if(lastNumber.equals("1")) {
			return new Position(-1,-1);
		}
		if(lastNumber.equals("2")) {
			return new Position(0,-1);
		}
		if(lastNumber.equals("3")) {
			return new Position(1,-1);
		}
		if(lastNumber.equals("4")) {
			return new Position(-1,0);
		}
		if(lastNumber.equals("5")) {
			return new Position(0,0);
		}
		if(lastNumber.equals("6")) {
			return new Position(1,0);
		}
		if(lastNumber.equals("7")) {
			return new Position(-1,1);
		}
		if(lastNumber.equals("8")) {
			return new Position(0,1);
		}
		if(lastNumber.equals("9")) {
			return new Position(1,1);
		}
		return nextPos;
	}
	private Position getPosFromDigit2(String lastNumber) {
		Position nextPos=new Position(0,0);
		if(lastNumber.equals("1")) {
			return new Position(0,-2);
		}
		if(lastNumber.equals("2")) {
			return new Position(-1,-1);
		}
		if(lastNumber.equals("3")) {
			return new Position(-1,0);
		}
		if(lastNumber.equals("4")) {
			return new Position(-1,1);
		}
		if(lastNumber.equals("5")) {
			return new Position(-2,0);
		}
		if(lastNumber.equals("6")) {
			return new Position(-1,0);
		}
		if(lastNumber.equals("7")) {
			return new Position(0,0);
		}
		if(lastNumber.equals("8")) {
			return new Position(1,0);
		}
		if(lastNumber.equals("9")) {
			return new Position(2,0);
		}
		if(lastNumber.equals("A")) {
			return new Position(-1,1);
		}
		if(lastNumber.equals("B")) {
			return new Position(0,1);
		}
		if(lastNumber.equals("C")) {
			return new Position(1,1);
		}
		if(lastNumber.equals("D")) {
			return new Position(0,2);
		}
		return nextPos;
	}

	private class Position{
		int a;
		int o;
		public int getA() {
			return a;
		}
		public void setA(int a) {
			this.a = a;
		}
		public int getO() {
			return o;
		}
		public void setO(int o) {
			this.o = o;
		}
		public Position(int a, int o) {
			super();
			this.a = a;
			this.o = o;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(a, o);
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Objects.equals(a, other.a) && Objects.equals(o, other.o);
		}
		private A2016Day2 getEnclosingInstance() {
			return A2016Day2.this;
		}
		@Override
		public String toString() {
			return "Position [a=" + a + ", o=" + o + "]";
		}
		
	}
	
	public static List<Long> getDuration() {
		A2016Day2 d = new A2016Day2(2);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1,endTime - startTime);
	}
}

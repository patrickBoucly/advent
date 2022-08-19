package a2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

public class A2016Day1 extends A2016 {
	public A2016Day1(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day1 d = new A2016Day1(1);
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

	public long s1(boolean b) {
		Position pos=new Position(0L,0L);
		String dir = "N";

		List<String> input = Arrays.asList(getInput(b).trim().split(",")).stream().map(s -> s.trim())
				.collect(Collectors.toList());
		for (String s : input) {
			dir = tourner(dir,s.substring(0, 1));
			avancer(dir,pos,s);
		}
		System.out.println(input);
		return Math.abs(pos.a)+Math.abs(pos.o);
	}
	private class Position{
		Long a;
		Long o;
		public Long getA() {
			return a;
		}
		public void setA(Long a) {
			this.a = a;
		}
		public Long getO() {
			return o;
		}
		public void setO(Long o) {
			this.o = o;
		}
		public Position(Long a, Long o) {
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
		private A2016Day1 getEnclosingInstance() {
			return A2016Day1.this;
		}
		@Override
		public String toString() {
			return "Position [a=" + a + ", o=" + o + "]";
		}
		
	}

	private void avancer(String dir, Position pos, String s) {
		switch(dir){
	       case "N": 
	    	   pos.o+=Long.valueOf(s.substring(1));
	          break;
	       case "E":
	    	   pos.a+=Long.valueOf(s.substring(1));
	           break;
	   
	       case "W":
	    	   pos.a-=Long.valueOf(s.substring(1));
	           break;
	       case "S":
	    	   pos.o-=Long.valueOf(s.substring(1));
	           break;
	   }

	}

	private String tourner(String dir, String rot) {
		switch(dir){
	       case "N": 
	          if(rot.equals("R")) {
	        	  dir="E";
	          } else {
	        	  dir="W";
	          }
	          break;
	       case "E":
	    	   if(rot.equals("R")) {
		        	  dir="S";
		          } else {
		        	  dir="N";
		          }
	           break;
	   
	       case "W":
	    	   if(rot.equals("R")) {
		        	  dir="N";
		          } else {
		        	  dir="S";
		          }
	           break;
	       case "S":
	    	   if(rot.equals("R")) {
		        	  dir="W";
		          } else {
		        	  dir="E";
		          }
	           break;
	   }

		return dir;
	}

	public long s2(boolean b) {
		String dir="N";
		Map<Position,Integer> posCpt=new HashMap<>();
		Position lastP=new Position(0L, 0L);
		posCpt.put(new Position(0L, 0L), 1);
		List<String> input = Arrays.asList(getInput(b).trim().split(",")).stream().map(s -> s.trim())
				.collect(Collectors.toList());
		for (String s : input) {
			dir = tourner(dir,s.substring(0, 1));
			lastP=avancer2(dir,posCpt,s,lastP);
			if(posCpt.containsValue(2)) {
				for(Entry<Position, Integer> e:posCpt.entrySet() ) {
					if(e.getValue()==2) {
						return Math.abs(e.getKey().a)+Math.abs(e.getKey().o);
					}
				}
				
			}
		}
		return 0L;
	}
	private Position avancer2(String dir, Map<Position, Integer> posCpt, String s, Position lastP) {
		Position newPos=new Position(lastP.a,lastP.o);
		switch(dir){
	       case "N": 
	    	   newPos.o=lastP.o+Long.valueOf(s.substring(1));
	          break;
	       case "E":
	    	   newPos.a=lastP.a+Long.valueOf(s.substring(1));
	           break;
	   
	       case "W":
	    	   newPos.a=lastP.a-Long.valueOf(s.substring(1));
	           break;
	       case "S":
	    	   newPos.o=lastP.o-Long.valueOf(s.substring(1));
	           break;
	   }
		if(posCpt.containsKey(newPos)) {
			posCpt.put(newPos, 2);
		} else {
		posCpt.put(newPos, 1);
		}
		return new Position(newPos.a,newPos.o);
		
	}

	public static List<Long> getDuration() {
		// TODO Auto-generated method stub
		return null;
	}

}

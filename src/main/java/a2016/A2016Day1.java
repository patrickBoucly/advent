package a2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
		List<Position> posCpt=new ArrayList<>();
		Position lastP=new Position(0L, 0L);
		posCpt.add(new Position(0L, 0L));
		List<String> input = Arrays.asList(getInput(b).trim().split(",")).stream().map(s -> s.trim())
				.collect(Collectors.toList());
		Long rep=0L;
		HashMap<Long,Position> repLastPos=new HashMap<>();
		repLastPos.put(0L, lastP);
		for (String s : input) {
			dir = tourner(dir,s.substring(0, 1));
			repLastPos=avancer2(dir,posCpt,s,repLastPos);
			if(repLastPos.size()>1) {
				for(Long l:repLastPos.keySet()) {
					if(l!=0L) {
						return l;
					}
				}
				
			}
		}
		return rep;
	}
	private HashMap<Long, Position> avancer2(String dir, List<Position> posCpt, String s, HashMap<Long, Position> repLastPos) {
		Long rep=0L;
		Position newPos=new Position(repLastPos.get(0L).a,repLastPos.get(0L).o);
		switch(dir){
	       case "N": 
	    	   for(int i=1;i<=Long.valueOf(s.substring(1));i++) {
	    		   newPos=new Position(repLastPos.get(0L).a,repLastPos.get(0L).o+i);
	    		   if(posCpt.contains(newPos)) {
	    			   rep=Math.abs(newPos.a)+Math.abs(newPos.o);
	    		   } else {
	    			   posCpt.add(newPos);
	    		   }
	    	   }
	          break;
	       case "E":
	    	   for(int i=1;i<=Long.valueOf(s.substring(1));i++) {
	    		   newPos=new Position(repLastPos.get(0L).a+i,repLastPos.get(0L).o);
	    		   if(posCpt.contains(newPos)) {
	    			   rep=Math.abs(newPos.a)+Math.abs(newPos.o);
	    		   }else {
	    			   posCpt.add(newPos);
	    		   }
	    	   }
	
	           break;
	   
	       case "W":
	    	   for(int i=1;i<=Long.valueOf(s.substring(1));i++) {
	    		   newPos=new Position(repLastPos.get(0L).a-i,repLastPos.get(0L).o);
	    		   if(posCpt.contains(newPos)) {
	    			   rep=Math.abs(newPos.a)+Math.abs(newPos.o);
	    		   }else {
	    			   posCpt.add(newPos);
	    		   }
	    	   }
	    	   
	           break;
	       case "S":
	    	   for(int i=1;i<=Long.valueOf(s.substring(1));i++) {
	    		   newPos=new Position(repLastPos.get(0L).a,repLastPos.get(0L).o-i);

	    		   if(posCpt.contains(newPos)) {
	    			   rep=Math.abs(newPos.a)+Math.abs(newPos.o);
	    		   }else {
	    			   posCpt.add(newPos);
	    		   }
	    	   }
	           break;
	   }
		repLastPos.put(rep,newPos);
	return repLastPos;
		
	}

	public static List<Long> getDuration() {
		A2016Day1 d = new A2016Day1(1);
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

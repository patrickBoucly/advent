package a2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class A2016Day4 extends A2016 {
	public A2016Day4(int day) {
		super(day);
	}
	public static void main(String[] args0) {
		A2016Day4 d = new A2016Day4(4);
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
	private Long s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim())
				.collect(Collectors.toList());
		Long res=0L;
		for(String i:input) {
			Room r=  new Room(i);
			if(r.isReal()) {
				res+=r.getDash();
			}
		}
		
		return res;
		
	}
	private String s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).trim().split("\n")).stream().map(s -> s.trim())
				.collect(Collectors.toList());
		Long res=0L;
		for(String i:input) {
			Room r=  new Room(i);
			if(r.isReal()) {
				String dec=r.decode();
				if(dec.contains("ole"))		{
					return r.decode();
				}
			}
		}
		
		return "pas trouvé";
		
	}
	private class Room{
		String name;
		String checksum;
		Long dash;
		public String getName() {
			return name;
		}
		public String decode() {
			String decodeName="";
			for(int j=0;j<name.length();j++) {
				decodeName+=getDecLetter(name.substring(j, j+1),dash);
			}
			return decodeName;
		}
		private String getDecLetter(String letter, Long decalage) {
			if(letter.equals("-")) {
				return "-";
			}
			List<String> alpha =Arrays.asList("a","b","c","d","e","f","g","h","i","g","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
			int initPos=alpha.indexOf(letter);
			return alpha.get((initPos+decalage.intValue())%26);
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getChecksum() {
			return checksum;
		}
		public void setChecksum(String checksum) {
			this.checksum = checksum;
		}
		public Long getDash() {
			return dash;
		}
		public void setDash(Long dash) {
			this.dash = dash;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(checksum, dash, name);
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
			Room other = (Room) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Objects.equals(checksum, other.checksum) && Objects.equals(dash, other.dash)
					&& Objects.equals(name, other.name);
		}
		private A2016Day4 getEnclosingInstance() {
			return A2016Day4.this;
		}
		public Room(String name, String checksum, Long dash) {
			super();
			this.name = name;
			this.checksum = checksum;
			this.dash = dash;
		}
		public Room(String inst) {
			super();
			this.name =inst.substring(0,inst.lastIndexOf("-"));
			this.checksum = inst.substring(inst.indexOf("[")+1,inst.length()-1);
			this.dash =Long.valueOf(inst.substring(inst.lastIndexOf("-")+1,inst.indexOf("[")));
		}
		@Override
		public String toString() {
			return "Room [name=" + name + ", checksum=" + checksum + ", dash=" + dash + "]";
		}
		
		public boolean isReal() {
			HashMap<String, Integer> lettres= getLettres(this.name);
			lettres.remove("-");
			Set<Entry<String, Integer>> entries = lettres.entrySet();
	        TreeMap<String, Integer> sorted = new TreeMap<>(lettres);
	        Set<Entry<String, Integer>> mappings = sorted.entrySet();
	      	        Comparator<Entry<String, Integer>> valueComparator 
	               = new Comparator<>() {
	            
	            @Override
	            public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
	                Integer v1 = e1.getValue();
	                Integer v2 = e2.getValue();
	                String l1=e1.getKey();
	                String l2=e2.getKey();
	                if(v1==v2) {
	                	return l1.compareTo(l2);
	                } else {
	                	return v2.compareTo(v1);
	                }
	                
	            }
	        };
	        
	
	        List<Entry<String, Integer>> listOfEntries 
	                  = new ArrayList<Entry<String, Integer>>(entries);
	        
	        Collections.sort(listOfEntries, valueComparator);
	        
	        LinkedHashMap<String, Integer> sortedByValue 
	                    = new LinkedHashMap<String, Integer>(listOfEntries.size());
	        
	      
	        for(Entry<String, Integer> entry : listOfEntries){
	            sortedByValue.put(entry.getKey(), entry.getValue());
	        }
	 
	        Set<Entry<String, Integer>> entrySetSortedByValue = sortedByValue.entrySet();
			String check="";
			boolean ctrl=true;
			for(int i=0;i<checksum.length();i++) {
				String letter=entrySetSortedByValue.toArray()[i].toString().substring(0, 1);
				if(!checksum.substring(i, i+1).equals(letter)){
					ctrl=false;
				}
			}
			
			
			return ctrl;
		}
		private HashMap<String, Integer> getLettres(String mot) {
			HashMap<String, Integer> lettreDuMot = new HashMap<>();
			for (int pos = 0; pos < mot.length() ; pos++) {
				String l = mot.substring(pos, pos + 1);
				if (lettreDuMot.containsKey(l)) {
					lettreDuMot.put(l, lettreDuMot.get(l) + 1);
				} else {
					lettreDuMot.put(l, 1);
				}
			}
			return lettreDuMot;
		}
	}
	
	public static List<Long> getDuration() {
		A2016Day4 d = new A2016Day4(4);
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

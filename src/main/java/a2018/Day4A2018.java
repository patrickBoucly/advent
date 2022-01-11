package a2018;

import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day4A2018 {

	public static void main(String[] args) {
	//s1();
		s2();

	}
	private static void s2() {
		String message = getMessage(
				"C:\\Users\\z11r88\\eclipse-workspace2\\advent_of_code\\src\\advent_of_code\\main\\resources\\a2018\\input4");
		List<Info> infos=new ArrayList<>();
		for (String s : message.split("\n")) {
			String stringDateBrut=s.substring(1).split("]")[0];
			Date d=convertString2Date(stringDateBrut);
			String reste=s.split("]")[1];
			if(reste.contains("#")) {
			StringBuilder numGSB= new  StringBuilder();
			for(int i=0;i<reste.length();i++){
				if(reste.subSequence(i, i+1).equals("#")) {
					int j=i+1;
					while(!reste.subSequence(j, j+1).equals(" ")) {
						numGSB.append(reste.subSequence(j, j+1));
						j++;
					}
				}
			}
			String numGS=numGSB.toString();
			infos.add(new Info(d,Integer.parseInt(numGS),reste)); 
			
			} else {
				infos.add(new Info(d,reste));
			}
		}
		Collections.sort(infos);
		HashMap<Integer,List<Integer>> gardienMinutesSommeil= new HashMap<>();
		int gardeCourant=-1;
		int minDebutSommeil=-1;
		for(Info i:infos) {
			
			if(i.numGardien !=0) {
				gardeCourant=i.numGardien;
			} else {
				if(i.action.contains("asleep") ) {
					minDebutSommeil=i.date.getMinutes();	
				} else {
					List<Integer> minutesDormi= new ArrayList<>();
					for(int m=minDebutSommeil;m<i.date.getMinutes();m++) {
						minutesDormi.add(m);
					}
					if(gardienMinutesSommeil.get(gardeCourant) != null) {
					minutesDormi.addAll(gardienMinutesSommeil.get(gardeCourant));
					}
					gardienMinutesSommeil.put(gardeCourant, minutesDormi);
				}
			}
			}
			int max=0;
			int idMax=-1;
			for(Integer numG:gardienMinutesSommeil.keySet()) {
				if(gardienMinutesSommeil.get(numG).size()>= max) {
					max=gardienMinutesSommeil.get(numG).size();
					idMax=numG;
				}
			}
			System.out.println(idMax);
			long maxCount=-1;
			
			Map<Integer, Integer> gardeMinuteMax= new HashMap<>();
			List<Integer> all ;
			int identG=-1;
			int freqmax=0;
			for(Integer idG:gardienMinutesSommeil.keySet()) {
				Map<Integer, Long> frequencies = gardienMinutesSommeil.get(idG).stream()
				        .collect(Collectors.groupingBy(i -> i, Collectors.counting()));
				System.out.println(idG);
				System.out.println(frequencies);
				all = frequencies.entrySet().stream()
				    .filter(e -> e.getValue() == frequencies.values().stream().mapToLong(Long::longValue).max().orElse(0))
				    .map(Map.Entry::getKey)
				    .collect(Collectors.toList());
				if(frequencies.get(all.get(0))>freqmax){
					identG=idG;
				}
				gardeMinuteMax.put(idG, all.get(0));
			}
			
			
			System.out.println(identG*gardeMinuteMax.get(identG));
		
		
	}
	
    public static class Revendication{
        int id;
        int deportH;
        int deportL;
       
		
		int h;
        int l;
      
		public Revendication(int id,  int deportL,int deportH,  int l,int h) {
			super();
			this.id = id;
			this.deportH = deportH;
			this.deportL = deportL;
			this.h = h;
			this.l = l;
		
		}
       
	}
	
    public static class Info implements Comparable<Info> {
		Date date;
		int numGardien;
		String action;
		public Info(Date date, int numGardien, String action) {
			super();
			this.date = date;
			this.numGardien = numGardien;
			this.action = action;
		}
		public Info(Date date,  String action) {
			super();
			this.date = date;
			this.action = action;
		}
		 public Date getDateTime() {
			    return date;
			  }

		@Override
		public String toString() {
			if(numGardien != 0) {
			return "Info [date=" + date.getMinutes() + ", numGardien=" + numGardien + ", action=" + action + "]";
			} else {
				return "Info [date=" + date.getMinutes() + ", action=" + action + "]";
			}
		}
		 @Override
		  public int compareTo(Info o) {
		    return getDateTime().compareTo(o.getDateTime());
		  }
		
	
	
	}

	private static void s1() {
		String message = getMessage(
				"C:\\Users\\z11r88\\eclipse-workspace2\\advent_of_code\\src\\advent_of_code\\main\\resources\\a2018\\input4");
		List<Info> infos=new ArrayList<>();
		for (String s : message.split("\n")) {
			String stringDateBrut=s.substring(1).split("]")[0];
			Date d=convertString2Date(stringDateBrut);
			String reste=s.split("]")[1];
			if(reste.contains("#")) {
			StringBuilder numGSB= new  StringBuilder();
			for(int i=0;i<reste.length();i++){
				if(reste.subSequence(i, i+1).equals("#")) {
					int j=i+1;
					while(!reste.subSequence(j, j+1).equals(" ")) {
						numGSB.append(reste.subSequence(j, j+1));
						j++;
					}
				}
			}
			String numGS=numGSB.toString();
			infos.add(new Info(d,Integer.parseInt(numGS),reste)); 
			
			} else {
				infos.add(new Info(d,reste));
			}
		}
		Collections.sort(infos);
		HashMap<Integer,List<Integer>> gardienMinutesSommeil= new HashMap<>();
		int gardeCourant=-1;
		int minDebutSommeil=-1;
		for(Info i:infos) {
			
			if(i.numGardien !=0) {
				gardeCourant=i.numGardien;
			} else {
				if(i.action.contains("asleep") ) {
					minDebutSommeil=i.date.getMinutes();	
				} else {
					List<Integer> minutesDormi= new ArrayList<>();
					for(int m=minDebutSommeil;m<i.date.getMinutes();m++) {
						minutesDormi.add(m);
					}
					if(gardienMinutesSommeil.get(gardeCourant) != null) {
					minutesDormi.addAll(gardienMinutesSommeil.get(gardeCourant));
					}
					gardienMinutesSommeil.put(gardeCourant, minutesDormi);
				}
			}
			}
			int max=0;
			int idMax=-1;
			for(Integer numG:gardienMinutesSommeil.keySet()) {
				if(gardienMinutesSommeil.get(numG).size()>= max) {
					max=gardienMinutesSommeil.get(numG).size();
					idMax=numG;
				}
			}
			System.out.println(idMax);
			Map<Integer, Long> gardeMinuteMax= new HashMap<>();
			for(Integer idG:gardienMinutesSommeil.keySet()) {
				
			}
			Map<Integer, Long> frequencies = gardienMinutesSommeil.get(idMax).stream()
			        .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

			long maxCount = frequencies.values().stream().mapToLong(Long::longValue).max().orElse(0);

			List<Integer> all = frequencies.entrySet().stream()
			    .filter(e -> e.getValue() == maxCount)
			    .map(Map.Entry::getKey)
			    .collect(Collectors.toList());
			System.out.println(all.get(0)*idMax);
			int minuteLaPlusFrequente=0;
			int nboccurenceminutemax=0;
			int mincpt=0;
			for(Integer minute:gardienMinutesSommeil.get(idMax)) {
				mincpt=0;
				for(Integer i:gardienMinutesSommeil.get(idMax)) {
					if(i.equals(minute)) {
						mincpt++;
					}
				}
				if(mincpt>nboccurenceminutemax) {
					minuteLaPlusFrequente=minute;
				}
			}
			
			System.out.println(idMax*minuteLaPlusFrequente);
		
	
		
	}

	 public static Date convertString2Date(String dateString)
	  {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    Date date = null;
	    try {
	      date = sdf.parse(dateString);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return date;
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

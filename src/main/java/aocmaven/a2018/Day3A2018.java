package aocmaven.a2018;

import static org.apache.commons.io.FileUtils.readFileToString;
import java.util.stream.*;

import lombok.Data;
import lombok.Value;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class Day3A2018 {

	public static void main(String[] args) {
		//s1();
		s2();

	}
	@Data
    @Value
    public static class Revendication{
        int id;
        int deportH;
        int deportL;
        public List<CarreTissu> getDemandes() {
			return demandes;
		}
		public void setDemandes(List<CarreTissu> demandes) {
			this.demandes = demandes;
		}
		int h;
        int l;
        List<CarreTissu> demandes= new ArrayList<>();
		public Revendication(int id,  int deportL,int deportH,  int l,int h) {
			super();
			this.id = id;
			this.deportH = deportH;
			this.deportL = deportL;
			this.h = h;
			this.l = l;
			List<CarreTissu> demandes= new ArrayList<>();
				for(int j=1;j<=this.l;j++ ) {
					for(int k=1;k<=this.h;k++ ) {
						demandes.add(new CarreTissu(this.deportL+j, this.deportH+k));
					}
				}
			
			this.demandes=demandes;
		}
       
        }
	 public static class CarreTissu{
	        int x;
	        int y;
			public CarreTissu(int x, int y) {
				super();
				this.x = x;
				this.y = y;
			}
			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + x;
				result = prime * result + y;
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
				CarreTissu other = (CarreTissu) obj;
				if (x != other.x)
					return false;
				if (y != other.y)
					return false;
				return true;
			}
			
	       
	        }


	private static void s2() {
		String message = getMessage(
				"C:\\Users\\z11r88\\eclipse-workspace2\\advent_of_code\\src\\advent_of_code\\main\\resources\\a2018\\input3");
		List<Revendication> rev = new ArrayList<>();
		
		for (String s : message.split("\n")) {
			String[] split=s.trim().split("#|@|,|:|x|\" \"");
			rev.add(new Revendication(Integer.parseInt(split[1].trim()), Integer.parseInt(split[2].trim()), Integer.parseInt(split[3].trim()), Integer.parseInt(split[4].trim()), Integer.parseInt(split[5].trim())));
		}
		HashMap<CarreTissu,Integer> demandesComptage = new HashMap<>();
		List<CarreTissu> demandes= new ArrayList<>();
		for(Revendication r:rev) {
					demandes.addAll(r.getDemandes());
		}
		
		for(CarreTissu c:demandes) {
			if(demandesComptage.containsKey(c)) {
				demandesComptage.put(c, demandesComptage.get(c)+1);
			} else {
				demandesComptage.put(c,1);
			}
		}
		
		int idRes=-1;
		for(Revendication r:rev) {
			System.out.println(r.id);
			boolean candidat=true;
			for(CarreTissu c:r.getDemandes()) {
				if(demandesComptage.get(c)!=1) {
					candidat=false;
					break;
				}
			}
			if(candidat) {
				idRes=r.id;
				break;
			}
			
		}
		System.out.println(idRes);
		
	}

	private static void s1() {
		String message = getMessage(
				"C:\\Users\\z11r88\\eclipse-workspace2\\advent_of_code\\src\\advent_of_code\\main\\resources\\a2018\\input3");
		List<Revendication> rev = new ArrayList<>();
		
		for (String s : message.split("\n")) {
			String[] split=s.trim().split("#|@|,|:|x|\" \"");
			rev.add(new Revendication(Integer.parseInt(split[1].trim()), Integer.parseInt(split[2].trim()), Integer.parseInt(split[3].trim()), Integer.parseInt(split[4].trim()), Integer.parseInt(split[5].trim())));
		}
		List<CarreTissu> demandes= new ArrayList<>();
		HashMap<CarreTissu,Integer> demandesComptage = new HashMap<>();
		for(Revendication r:rev) {
					demandes.addAll(r.getDemandes());
		}
		
		for(CarreTissu c:demandes) {
			if(demandesComptage.containsKey(c)) {
				demandesComptage.put(c, demandesComptage.get(c)+1);
			} else {
				demandesComptage.put(c,1);
			}
		}
		int res=0;
		for(CarreTissu c: demandesComptage.keySet()) {
			if(demandesComptage.get(c)>1) {
				res++;
			}
		}
		
		System.out.println(demandes.size());
		System.out.println(demandes.stream().distinct().count());
		System.out.println(demandes.size()-demandes.stream().distinct().count());
		System.out.println(res);
		
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

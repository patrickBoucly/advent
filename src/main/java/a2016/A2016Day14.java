package a2016;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A2016Day14 extends A2016 {
	public A2016Day14(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day14 d = new A2016Day14(14);
		long startTime = System.currentTimeMillis();
		//System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(false));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public Integer s1(boolean b) {
		List<String> keys=new ArrayList<>();
		String input="abc";
		if(b) {
			input="ihaygndm";
		}
		int index=0;
		while(keys.size()<64) {
			String hash=hash(input+index);
			String pattern=getTripletPattern(hash);
			if(pattern5Fois1000HashSuivant(pattern,input,index)) {
				keys.add(input+index);
			}
			index++;
		}
		System.out.println(keys);
		return index-1;
	}
	private boolean pattern5Fois1000HashSuivant(String pattern, String input, int index) {
		if(pattern.equals("")) {			
			return false;
		}
		for(int i=index+1;i<index+1+1000;i++) {
			if(hash(input+i).contains(pattern+pattern+pattern+pattern+pattern)) {
				return true;
			}
		}
		return false;
	}
	private boolean pattern5Fois1000HashSuivant2017(String pattern, String input, int index) {
		if(pattern.equals("")) {			
			return false;
		}
		for(int i=index+1;i<index+1+1000;i++) {
			if(hash2017fois(input+i).contains(pattern+pattern+pattern+pattern+pattern)) {
				return true;
			}
		}
		return false;
	}

	private String getTripletPattern(String chaine) {
		String pattern="";
		for(int i=0;i<chaine.length()-2;i++) {
			String s=chaine.substring(i, i+1);
			String s1=chaine.substring(i+1, i+2);
			String s2=chaine.substring(i+2, i+3);
			if(s1.equals(s2) && s1.equals(s)) {
				return s;
			}
			
		}
		return pattern;
	}

	public Integer s2(boolean b) {
		List<String> keys=new ArrayList<>();
		String input="abc";
		if(b) {
			input="ihaygndm";
		}
		int index=0;
		while(keys.size()<64) {
			String hash=hash2017fois(input+index);
			String pattern=getTripletPattern(hash);
			if(pattern5Fois1000HashSuivant2017(pattern,input,index)) {
				keys.add(input+index);
			}
			index++;
		}
		System.out.println(keys);
		return index-1;
	}

	private String hash2017fois(String toHash) {
		String res=toHash;
		for(int i=0;i<2017;i++) {
			res=hash(res);
		}
		return res;
	}

	private String hash(String password) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");

			md.update(password.getBytes());

			byte byteData[] = md.digest();

			// convertir le tableau de bits en une format hexadécimal - méthode 1
			 StringBuffer hexString = new StringBuffer();
		     for (int i=0;i<byteData.length;i++) {
		      String hex=Integer.toHexString(0xff & byteData[i]);
		          if(hex.length()==1) hexString.append('0');
		          hexString.append(hex);
		     }
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}

	public static List<Long> getDuration() {
		A2016Day14 d = new A2016Day14(14);
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

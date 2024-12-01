package a2015;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class A2015Day4 extends A2015 {
	public A2015Day4(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2015Day4 d = new A2015Day4(4);
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
		String secret="yzbqklnj";
		String aHash="";
		int rang=0;
		while(true) {
			aHash=hash(secret+rang);
			if(aHash.substring(0, 5).equals("00000")) {
				return rang;
			}
			rang++;
		}
	}
	


	public long s2(boolean b) {
		String secret="yzbqklnj";
		String aHash="";
		int rang=0;
		while(true) {
			aHash=hash(secret+rang);
			if(aHash.substring(0, 6).equals("000000")) {
				return rang;
			}
			rang++;
		}
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
		A2015Day4 d = new A2015Day4(4);
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

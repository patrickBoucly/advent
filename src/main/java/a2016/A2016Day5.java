package a2016;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class A2016Day5 extends A2016 {
	public A2016Day5(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day5 d = new A2016Day5(5);
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
		String password = getInput(b);
		Map<String,String> res=new HashMap<>();
		Long i=-1L;
		while(res.keySet().size()<8) {
			i++;
			String hashed=hash(password+String.valueOf(i));
			if(hashed.substring(0,5).equals("00000")) {
				String p6=hashed.substring(5,6);
				String p7=hashed.substring(6,7);
				if(Arrays.asList("0","1","2","3","4","5","6","7").contains(p6)) {
					if(!res.containsKey(p6)) {
						res.put( p6, p7);
					}
				}
			}
		}
		String message="";
		for(String s:res.values()) {
			message+=s;
		}
		return message;
	}

	private String s1(boolean b) {
		String password = getInput(b);
		String res="";
		Long i=-1L;
		while(res.length()<8) {
			i++;
			String hashed=hash(password+String.valueOf(i));
			if(hashed.substring(0,5).equals("00000")) {
				res+=hashed.substring(5,6);
			}
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
		A2016Day5 d = new A2016Day5(5);
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

package a2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

public class A2024Day22 extends A2024 {

	public A2024Day22(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day22 d = new A2024Day22(22);
		//System.out.println(d.s1(true));
		// too high 104512
		long startTime = System.currentTimeMillis();
		// d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	public long s1(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		List<Long> buyers=new ArrayList<>();
		for(String s:input) {
			buyers.add(Long.parseLong(s));
		}
		long res = 0l;
		for (Long secret : buyers) {
			for (int i = 0; i < 2000; i++) {
				secret = getNextSecret(secret);
				
			}
			res+=secret;
		}
		return res;
	}

	public long s2(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		Game g=getGame(input);
		System.out.println("getinput ok");
		g.calculerGains();
		System.out.println("calculerGains ok");
		return g.getGainMax();
	}

	private Game getGame(List<String> input) {
		List<Long> buyersStart=new ArrayList<>();
		for(String s:input) {
			buyersStart.add(Long.parseLong(s));
		}
		Set<Sequence> seq=new HashSet<>();
		Set<Buyer> buyers=new HashSet<>();
		for(Long bs:buyersStart) {
			List<Long> secrets2k=new ArrayList<>();
			List<Long> variations=new ArrayList<>();
			Map<Sequence,Long> gains=new HashMap<>();
			secrets2k.add(bs);
			for (int i = 0; i < 2000; i++) {
				bs = getNextSecret(bs);
				variations.add(bs%10-secrets2k.get(secrets2k.size()-1)%10);
				secrets2k.add(bs);
			}
			buyers.add(new Buyer(secrets2k,variations,gains));
		}
		for(Buyer bu:buyers) {
			for(int i=0;i<1996;i+=4) {
				seq.add(new Sequence(Arrays.asList(bu.variations.get(i)
						,bu.variations.get(i+1),bu.variations.get(i+2)
						,bu.variations.get(i+3))));
			}
		}
		return new Game(seq,buyers);
	}

	public long getNextSecret(long secret) {
		secret = prune(mix(64L * secret, secret));
		secret = prune(mix(secret / 32, secret));
		return prune(mix(2048 * secret, secret));
	}

	private long prune(long mix) {
		return mix % 16777216L;
	}

	private long mix(long secret, long value) {
		secret = secret ^ value;
		return secret;
	}

	@Data
	@AllArgsConstructor
	private static class Sequence {
		List<Long> var;
	}
	@Data
	@AllArgsConstructor
	private static class Game {
		public void calculerGains() {
			int cpt=0;
			System.out.println(buyers.size());
			for(Buyer bu:buyers) {
				for(Sequence s:seq) {
					bu.gains.put(s, calculGain(bu,s));
					
				}
				cpt++;
				if(cpt%100==0)
				System.out.println(cpt);
			}
			
		}
		private Long calculGain(Buyer bu, Sequence seq1) {
			Long res=0l;
			for(int dec=0;dec<1996;dec++) {
				Sequence seq2=new Sequence(Arrays.asList(bu.variations.get(dec)
						,bu.variations.get(dec+1),bu.variations.get(dec+2)
						,bu.variations.get(dec+3)));
				if(seq1.equals(seq2)) {
					return bu.secrets2k.get(dec+4)%10;
				}
			}
			return res;
		}
		public long getGainMax() {
			Long res=0L;
			for(Sequence q:seq) {
				Long t=0l;
				for(Buyer bu:buyers) {
					t+=bu.gains.get(q);
				}
				if(t>res) {
					res=t;
				}
			}
			return res;
		}
		Set<Sequence> seq;
		Set<Buyer> buyers;
	}
	@Data
	@AllArgsConstructor
	private static class Buyer {
		List<Long> secrets2k;
		List<Long> variations;
		Map<Sequence,Long> gains;
	}

	public static List<Long> getDuration() {
		A2024Day22 d = new A2024Day22(22);
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

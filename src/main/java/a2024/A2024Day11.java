package a2024;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class A2024Day11 extends A2024 {

	public A2024Day11(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day11 d = new A2024Day11(11);
		System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		//d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	public long s1(boolean b) {
		List<Long> input = null;
		if(b) {
			input=Arrays.asList(2701L, 64945L, 0L, 9959979L, 93L, 781524L, 620L, 1L);
		}else {
			input=Arrays.asList(125L,17L);
		}
		int nbBlink=25;
		Deque<Long> stones=new ArrayDeque<>();
		for(Long l:input) {
			stones.add(l);
		}
		for(int i=0;i<nbBlink;i++) {
			stones=blink(stones);
		}
		return stones.size();
	}

	

	private Deque<Long> blink(Deque<Long> stones) {
		List<Long> nb=new ArrayList<>(stones.stream().toList());
		stones=new ArrayDeque<>();
		for(Long l:nb) {
			List<Long> nbTrans=trans(l);
			stones.addAll(nbTrans);
		}
		return stones;
	}

	private List<Long> trans(Long l) {
		if(l.equals(0L)) {
			return Arrays.asList(1L);
		}
		if(String.valueOf(l).length()%2==0) {
			int mil=String.valueOf(l).length()/2;
			return Arrays.asList(Long.parseLong(String.valueOf(l).substring(0, mil)),
					Long.parseLong(String.valueOf(l).substring( mil)));
		}
		Long res=l*2024;
		return Arrays.asList(res);
	}

	public Long s2(boolean b) {
		List<Long> input = null;
		Map<Long,Long> nbFreq=new HashMap<>();
		if(b) {
			nbFreq.put(2701L, 1L);
			nbFreq.put(64945L, 1L);
			nbFreq.put(0L, 1L);
			nbFreq.put(9959979L, 1L);
			nbFreq.put(93L, 1L);
			nbFreq.put(781524L, 1L);
			nbFreq.put(620L, 1L);
			nbFreq.put(1L, 1L);
		}else {
			nbFreq.put(125L, 1L);
			nbFreq.put(17L, 1L);
		}
		int nbBlink=75;
		
		for(int i=0;i<nbBlink;i++) {
			nbFreq=blink2(nbFreq);
		}
		return nbFreq.values().stream().reduce(0L, Long::sum);
	}

	private Map<Long, Long> blink2(Map<Long, Long> nbFreq) {
		Map<Long,Long> nbFreq2=new HashMap<>();
		for(Long l:nbFreq.keySet()) {
			List<Long> nbTrans=trans(l);
			for(Long k:nbTrans) {
				if(nbFreq2.containsKey(k)) {
					nbFreq2.put(k,nbFreq2.get(k)+nbFreq.get(l));
				}else {
					nbFreq2.put(k, nbFreq.get(l));
				}
			}
		}
		return nbFreq2;
	}

	public static List<Long> getDuration() {
		A2024Day11 d = new A2024Day11(11);
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

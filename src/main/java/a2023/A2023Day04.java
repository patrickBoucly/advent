package a2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class A2023Day04 extends A2023 {
	public static Integer numero;

	public A2023Day04(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2023Day04 d = new A2023Day04(4);
		//System.out.println(d.s1(true));
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

	public Long s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().toList();
		List<Card> cards = getCards(inputL);
		for (Card c : cards) {
			c.countWin();
			c.calcValue();
		}

		return cards.stream().map(Card::getValue).reduce(0L, Long::sum);
	}

	private List<Card> getCards(List<String> inputL) {
		List<Card> cards = new ArrayList<>();
		for (String s : inputL) {
			s = s.replace("   ", " ");
			s = s.replace("  ", " ");
			Card c = new Card();
			String[] split = s.split(":");
			c.setId(Integer.valueOf(split[0].trim().split(" ")[1].trim()));
			c.setNum(c.getId());
			int sep = split[1].trim().indexOf("|");
			String split2M = split[1].trim().substring(0, sep);
			String split2W = split[1].trim().substring(sep + 1);
			List<Integer> myNumbers = new ArrayList<>();
			List<Integer> winning = new ArrayList<>();
			String[] split3 = split2M.trim().split(" ");
			String[] split4 = split2W.trim().split(" ");
			for (String m : split3) {
				myNumbers.add(Integer.valueOf(m.trim()));
			}
			for (String m : split4) {
				winning.add(Integer.valueOf(m.trim()));
			}
			c.setMyNumbers(myNumbers);
			c.setWinning(winning);
			c.setPlayed(false);
			cards.add(c);
			numero = c.getNum();
		}
		return cards;
	}

	public int s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().toList();
		List<Card> cards = getCards(inputL);
		Map<Integer,Integer> comptages=new HashMap<>();
		for(Card c:cards) {
			c.countWin();
			comptages.put(c.getId(), 1);
		}
		compter(comptages,cards);
		System.out.println(comptages);
		return comptages.values().stream().reduce(0, Integer::sum);
	}

	private void compter(Map<Integer, Integer> comptages, List<Card> cards) {
		for(Integer i:comptages.keySet()) {
			Optional<Card> c=getCardJ(cards,i);
			if(c.isPresent()) {
				Card cc=c.get();
				for (int j =i+1; j <i+1+ cc.getNbWinning(); j++) {
					comptages.put(j, comptages.get(j)+comptages.get(i));
				}
				
			}
		}
	}

	private List<Card> playACard(List<Card> cards) {
		List<Card> newCards = new ArrayList<>();
		Optional<Card> c = cards.stream().filter(k -> !k.isPlayed()).findFirst();
		if (!c.isPresent()) {
			return cards;
		}
		for (Card k : cards) {
			if (k.num != c.get().num) {
				newCards.add(k);
			} else {
				k.setPlayed(true);
				k.countWin();
				newCards.add(k);
				for (int j =k.getId()+1; j <k.getId()+1+ k.getNbWinning(); j++) {
					Optional<Card> ad =getCardJ(cards,j);
					if (ad.isPresent()) {
						Card d = new Card();
						numero++;
						d.setId(ad.get().getId());
						d.setNum(numero);
						d.setMyNumbers(ad.get().getMyNumbers());
						d.setWinning(ad.get().getWinning());
						d.setPlayed(false);
						newCards.add(d);
					}
				}
			}
		}
		return newCards;
	}

	private Optional<Card> getCardJ(List<Card> cards, int j) {
		return cards.stream().filter(h -> h.id == j).findFirst();
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Card {
		int id;
		int num;
		List<Integer> myNumbers;
		List<Integer> winning;
		Long value;
		Integer nbWinning;
		boolean played;

		public void countWin() {
			int cpt = 0;
			for (Integer n : myNumbers) {
				if (winning.contains(n)) {
					cpt++;
				}
			}
			setNbWinning(cpt);
		}

		public void calcValue() {
			Double v = Math.pow(2, nbWinning - 1d);
			setValue(v.longValue());
		}
	}

	public static List<Long> getDuration() {
		A2023Day04 d = new A2023Day04(4);
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

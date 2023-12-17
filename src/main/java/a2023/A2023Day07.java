package a2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import outils.MesOutils;

public class A2023Day07 extends A2023 {

	public A2023Day07(int day) {
		super(day);
	}

	public static List<String> cardValueOrder = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q",
			"K", "A");
	public static List<String> cardValueOrder2 = Arrays.asList("J", "2", "3", "4", "5", "6", "7", "8", "9", "T", "Q",
			"K", "A");
	public static List<String> kindValueOrder = Arrays.asList("High card", "One pair", "Two pair", "Three of a kind",
			"Full house", "Four of a kind", "Five of a kind");

	public static void main(String[] args0) {
		A2023Day07 d = new A2023Day07(7);
		// System.out.println(d.s1(true));
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

	public int s1(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().toList();
		TheGame tg = getTheGame(inputL);
		tg.determinerKinds();
		tg.ranger();
		return tg.hands.stream().mapToInt(h -> h.bid * h.rank).sum();
	}

	public int s2(boolean b) {
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().toList();
		TheGame tg = getTheGame(inputL);
		tg.determinerKinds2();
		tg.ranger2();
		return tg.hands.stream().mapToInt(h -> h.bid * h.rank).sum();

	}

	private TheGame getTheGame(List<String> inputL) {
		TheGame tg = new TheGame();
		List<Hand> hands = new ArrayList<>();
		for (String s : inputL) {
			Hand h = new Hand();
			List<Card> cards = new ArrayList<>();
			String[] sp = s.split(" ");
			h.setBid(Integer.valueOf(sp[1].trim()));
			String main = sp[0].trim();
			for (Integer i = 0; i < main.length(); i++) {
				cards.add(new Card(main.substring(i, i + 1)));
			}
			h.setCards(cards);
			hands.add(h);
		}
		tg.setHands(hands);
		return tg;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class TheGame {
		public void determinerKinds() {
			for (Hand h : hands) {
				Map<String, Integer> cardValueCount = new HashMap<>();

				for (String value : h.cards.stream().map(Card::getSymbole).toList()) {
					if (cardValueCount.containsKey(value)) {
						cardValueCount.put(value, cardValueCount.get(value) + 1);
					} else {
						cardValueCount.put(value, 1);
					}
				}
				h.setKind(getKind(cardValueCount));
			}
		}

		public void ranger2() {
			trier2();
			for (int i = 0; i < hands.size(); i++) {
				hands.get(i).setRank(i + 1);
			}
		}

		private void trier2() {
			hands.sort(new ComparatorS2());

		}

		public void determinerKinds2() {
			for (Hand h : hands) {
				Map<String, Integer> cardValueCount = new HashMap<>();

				for (String value : h.cards.stream().map(Card::getSymbole).toList()) {
					if (cardValueCount.containsKey(value)) {
						cardValueCount.put(value, cardValueCount.get(value) + 1);
					} else {
						cardValueCount.put(value, 1);
					}
				}
				cardValueCount = getBest(cardValueCount);
				h.setKind(getKind(cardValueCount));
			}

		}

		private Map<String, Integer> getBest(Map<String, Integer> cardValueCount) {
			Map<String, Integer> newCardValueCount = new HashMap<>();
			if (cardValueCount.containsKey("J")) {
				int nbJ = cardValueCount.get("J");
				if (nbJ == 5) {
					newCardValueCount.put("A", 5);
					return newCardValueCount;

				}
				String bestKet = getBestKey(cardValueCount);
				newCardValueCount.put(bestKet, cardValueCount.get(bestKet) + nbJ);
				for (String key : cardValueCount.keySet()) {
					if (!key.equals("J") && !key.equals(bestKet)) {
						newCardValueCount.put(key, cardValueCount.get(key));
					}
				}
				return newCardValueCount;
			} else {
				for (String key : cardValueCount.keySet()) {

					newCardValueCount.put(key, cardValueCount.get(key));

				}
			}
			return newCardValueCount;
		}

		private String getBestKey(Map<String, Integer> cardValueCount) {
			
			if (cardValueCount.get("J") == 5) {
				return "A";
			}
			int maxFreq = 0;
			for(String key:cardValueCount.keySet()) {
				if(!key.equals("J") && cardValueCount.get(key)>maxFreq) {
					maxFreq=cardValueCount.get(key);
				}
			}
			for (int l = cardValueOrder2.size() - 1; l >= 0; l--) {
				
					if (cardValueCount.keySet().contains(cardValueOrder2.get(l)) && cardValueCount.get(cardValueOrder2.get(l)) == maxFreq) {
						return cardValueOrder2.get(l);
					}
				
			}
			return null;
		}

		private String getKind(Map<String, Integer> cardValueCount) {
			int maxFreq = MesOutils.getMaxIntegerFromList(cardValueCount.values().stream().toList());
			int nbPair = cardValueCount.values().stream().filter(v -> v == 2).toList().size();
			if (maxFreq == 5) {
				return kindValueOrder.get(6);
			} else if (maxFreq == 4) {
				return kindValueOrder.get(5);
			} else if (maxFreq == 3 && nbPair == 1) {
				return kindValueOrder.get(4);
			} else if (maxFreq == 3) {
				return kindValueOrder.get(3);
			} else if (nbPair == 2) {
				return kindValueOrder.get(2);
			} else if (nbPair == 1) {
				return kindValueOrder.get(1);
			} else {
				return kindValueOrder.get(0);
			}
		}

		public void ranger() {
			trier();
			for (int i = 0; i < hands.size(); i++) {
				hands.get(i).setRank(i + 1);
			}

		}

		private void trier() {
			hands.sort(new ComparatorS1());
		}

		List<Hand> hands;

	}

	public static class ComparatorS1 implements Comparator<Hand> {

		@Override
		public int compare(Hand o1, Hand o2) {
			int kindCompare = Integer.compare(kindValueOrder.indexOf(o1.kind), kindValueOrder.indexOf(o2.kind));
			if (kindCompare != 0) {
				return kindCompare;
			}
			for (int posCard = 0; posCard < 5; posCard++) {
				int cardCompare = Integer.compare(cardValueOrder.indexOf(o1.cards.get(posCard).symbole),
						cardValueOrder.indexOf(o2.cards.get(posCard).symbole));
				if (cardCompare != 0) {
					return cardCompare;
				}
			}
			return 0;
		}

	}

	public static class ComparatorS2 implements Comparator<Hand> {

		@Override
		public int compare(Hand o1, Hand o2) {
			int kindCompare = Integer.compare(kindValueOrder.indexOf(o1.kind), kindValueOrder.indexOf(o2.kind));
			if (kindCompare != 0) {
				return kindCompare;
			}
			for (int posCard = 0; posCard < 5; posCard++) {
				int cardCompare = Integer.compare(cardValueOrder2.indexOf(o1.cards.get(posCard).symbole),
						cardValueOrder2.indexOf(o2.cards.get(posCard).symbole));
				if (cardCompare != 0) {
					return cardCompare;
				}
			}
			return 0;
		}

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Hand {
		List<Card> cards;
		Integer bid;
		Integer rank;
		String kind;

		@Override
		public String toString() {
			return "Hand [cards=" + cards.stream().map(Card::getSymbole).collect(Collectors.joining()) + ", bid=" + bid
					+ ", rank=" + rank + ", kind=" + kind + "]";
		}

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@NoArgsConstructor
	public static class Card {
		String symbole;
	}

	public static List<Long> getDuration() {
		A2023Day07 d = new A2023Day07(7);
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

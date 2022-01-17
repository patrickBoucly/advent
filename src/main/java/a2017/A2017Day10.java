package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class A2017Day10 extends A2017 {

	public A2017Day10(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day10 d = new A2017Day10(10);
		// d.s1(true);
		long startTime = System.currentTimeMillis();
		//System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public int s1(boolean b) {
		List<Integer> splitPos = Arrays.asList(getInput(b).split(",")).stream().map(String::trim).map(Integer::parseInt)
				.collect(Collectors.toList());
		// List<Integer> splitPos=Arrays.asList(3, 4, 1, 5);
		List<Integer> ln = IntStream.range(0, 256).boxed().collect(Collectors.toList());
		int skipSize = 0;
		int curPos = 0;
		Game g = new Game(ln, skipSize, curPos);
		for (Integer i : splitPos) {
			g.transf(i);
		}
		return g.res1();
	}

	public String s2(boolean b) {
		// String input = getInput(b);
		List<String> inputs=Arrays.asList("1,2,3","1,2,4","","AoC 2017",getInput(b));
		String res="";
		for(String s:inputs) {
			res=getSol2(s);
		}
		
		return res;
	}

	public String getSol2(String input) {
		List<Integer> asciiFromInput = getAsciiFromInput(input);
		List<Integer> suffix = Arrays.asList(17, 31, 73, 47, 23);
		asciiFromInput.addAll(suffix);
		List<Integer> sparseHash = getSparseHash(asciiFromInput);
		List<Integer> denseHash = getDenseHash(sparseHash);
		String res = getKnotHash(denseHash);
		return res;
	}

	private List<Integer> getAsciiFromInput(String input) {
		List<Integer> ascii = new ArrayList<>();
		for (int i = 0; i < input.length(); i++) {
			int asciiChar = input.subSequence(i, i + 1).charAt(0);
			ascii.add(asciiChar);
		}
		return ascii;
	}

	private List<Integer> getSparseHash(List<Integer> asciiFromInput) {
		List<Integer> ln = IntStream.range(0, 256).boxed().collect(Collectors.toList());
		int skipSize = 0;
		int curPos = 0;
		Game g = new Game(ln, skipSize, curPos);
		for (int step = 1; step <= 64; step++)
			for (Integer i : asciiFromInput) {
				g.step=step;
				g.pl=i;
				g.transf(i);
			}
		g.finalDec();
		return g.getLn();
	}

	private String getKnotHash(List<Integer> denseHash) {
		String knotHash = "";
		for (Integer i : denseHash) {
			String hexint = Integer.toHexString(i);
			if (hexint.length() == 1) {
				hexint = "0" + hexint;
			}
			knotHash += hexint;
		}
		return knotHash;
	}

	private List<Integer> getDenseHash(List<Integer> l) {
		List<Integer> denseHash = new ArrayList<>();
		for (int step = 0; step < 16; step++) {
			List<Integer> sub = l.subList(step * 16, (step + 1) * 16);
			int res = sub.get(0) ^ sub.get(1);
			for (int j = 2; j < sub.size(); j++) {
				res = res ^ sub.get(j);
			}
			denseHash.add(res);
		}
		return denseHash;
	}

	public static class Game {
		List<Integer> ln;
		int skipSize;
		int curPos;
		int pos0;
		int step;
		int pl;

		public int getPos0() {
			return pos0;
		}

		public void setPos0(int pos0) {
			this.pos0 = pos0;
		}

		public List<Integer> getLn() {
			return ln;
		}

		public int res1() {
			finalDec();
			return getLn().get(0) * getLn().get(1);
		}
		public void finalDec() {
			int dec = pos0 % getLn().size();
			int poz0 = getLn().size() - dec;
			List<Integer> nln=new ArrayList<>();
			int i=0;
			while(nln.size()<getLn().size()) {
				nln.add((getLn().get((poz0+i)%getLn().size())));
				i++;
			}
			setLn(nln);
		}
		
		public void transf(Integer i) {
			List<Integer> avDec = new ArrayList<>();
			List<Integer> in = new ArrayList<>(ln.subList(curPos, curPos + i));
			List<Integer> ap = new ArrayList<>(ln.subList(curPos + i, ln.size()));
			avDec.addAll(rev(in));
			avDec.addAll(ap);
			ln = decaler(avDec, (i + skipSize) % ln.size());
			pos0 += i + skipSize;
			skipSize++;
		}

		private List<Integer> decaler(List<Integer> avDec, int i) {
			List<Integer> apDec = avDec.subList(i, avDec.size());
			apDec.addAll(avDec.subList(0, i));
			return apDec;
		}

		private Collection<? extends Integer> rev(List<Integer> in) {
			List<Integer> inR = new ArrayList<>();
			for (int pos = in.size() - 1; pos >= 0; pos--) {
				inR.add(in.get(pos));
			}
			return inR;
		}

		public void setLn(List<Integer> ln) {
			this.ln = ln;
		}

		public int getSkipSize() {
			return skipSize;
		}

		public void setSkipSize(int skipSize) {
			this.skipSize = skipSize;
		}

		public int getCurPos() {
			return curPos;
		}

		public void setCurPos(int curPos) {
			this.curPos = curPos;
		}

		public Game(List<Integer> ln, int skipSize, int curPos) {
			super();
			this.ln = ln;
			this.skipSize = skipSize;
			this.curPos = curPos;
			this.pos0 = 0;
		}

		@Override
		public String toString() {
			return "Game [skipSize=" + skipSize + ", curPos=" + curPos + ",ln=" + ln + "]";
		}

	}

	public static List<Long> getDuration() {
		A2017Day10 d = new A2017Day10(1);
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

package a2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class A2022Day13 extends A2022 {

	public A2022Day13(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day13 d = new A2022Day13(13);

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

	public int s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().toList();
		List<Pair> pairs = new ArrayList<>();
		int m = 0;
		while (m < input.size()) {
			pairs.add(new Pair(new Packet(input.get(m).trim()),new Packet(input.get(m + 1).trim())));
			m = m + 3;
		}
		
		var packets = new ArrayList<Packet>(
                pairs.stream().map(p -> List.of(p.left, p.right)).flatMap(Collection::stream).toList());
        packets.add(new Packet("[[6]]"));
        packets.add(new Packet("[[2]]"));
        Comparator<Packet> cp = new Comparator<>() {
            public int compare(Packet left, Packet right) {
                var p = new Pair(left, right);
                var b = p.isRight();
                if (b == null) {
                    return 0;
                } else if (b) {
                    return -1;
                } else {
                    return 1;
                }
            };
        };
        var sorted = packets.stream().sorted(cp).toList();
        return findPacket(sorted, "[[2]]") * findPacket(sorted, "[[6]]");
	}
	 private static final int findPacket(List<Packet> packets, String packet) {
	        for (int i = 0; i < packets.size(); ++i) {
	            if (packets.get(i).line.equals(packet)) {
	                return i + 1;
	            }
	        }
	        throw new NoSuchElementException();
	    }


	public int s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().toList();
		List<Pair> pairs = new ArrayList<>();
		int m = 0;
		while (m < input.size()) {
			pairs.add(new Pair(new Packet(input.get(m).trim()),new Packet(input.get(m + 1).trim())));
			m = m + 3;
		}
		 int sum = 0;
	        for (int k = 0; k < pairs.size(); ++k) {
	            if (pairs.get(k).isRight()) {
	                sum += (k + 1);
	            }
	        }

		return sum;
	}

	private static final record Pair(Packet left, Packet right) {
		Boolean isRight() {
			return compareLists(left.read, right.read);
		}

		private Boolean compareLists(List<Object> left, List<Object> right) {
			for (int i = 0; i < left.size() && i < right.size(); ++i) {
				var l = left.get(i);
				var r = right.get(i);
				if (l instanceof Integer li && r instanceof Integer ri) {
					if (ri < li) {
						return false;
					} else if (li < ri) {
						return true;
					}
				} else {
					var ll = toList(l);
					var rr = toList(r);
					var b = compareLists(ll, rr);
					if (b != null) {
						return b;
					}
				}
			}
			if (left.size() < right.size()) {
				return true;
			} else if (right.size() < left.size()) {
				return false;
			}
			return null;
		}

		@SuppressWarnings("unchecked")
		private List<Object> toList(Object v) {
			if (v instanceof List<?> lo) {
				return (List<Object>) lo;
			}
			return List.of(v);
		}
	}

	private static final class Packet {
		final String line;
		final List<Object> read;
		Packet(String line) {
			this.line = line;
			read = readList(line, 1, line.length() - 1).list;
		}
		private static Tuple readList(String line, int start, int end) {
			List<Object> read = new ArrayList<>();
			StringBuilder sb = new StringBuilder();
			int i = start;
			while (i < end) {
				if (line.charAt(i) == '[') {
					var t = readList(line, i + 1, end);
					i = t.idx;
					read.add(t.list);
				} else if (line.charAt(i) == ']') {
					if (sb.length() > 0) {
						read.add(Integer.parseInt(sb.toString()));
					}
					return new Tuple(i + 1, read);
				} else if (line.charAt(i) == ',') {
					if (sb.length() > 0) {
						read.add(Integer.parseInt(sb.toString()));
						sb = new StringBuilder();
					}
					++i;
				} else {
					sb.append(line.charAt(i));
					++i;
				}
			}
			if (sb.length() > 0) {
				read.add(Integer.parseInt(sb.toString()));
			}
			return new Tuple(i + 1, read);
		}
	}

	private static final record Tuple(int idx, List<Object> list) {

	}

	public static List<Long> getDuration() {
		A2022Day13 d = new A2022Day13(13);
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

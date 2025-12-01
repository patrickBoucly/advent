package a2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

public class A2024Day24 extends A2024 {

	public A2024Day24(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day24 d = new A2024Day24(24);
		System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		// d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		// System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	public long s1(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();
		Game g = parseInput(input);

		return g.solve();

	}

	public long s2(boolean b) {
		List<String> input = Arrays.stream(getInput(b).trim().split("\n")).map(String::trim).toList();

		return 0l;
	}
	 public static Game parseInput(List<String> input) {
	        Map<String, Wire> wireMap = new HashMap<>();
	        Set<Connection> connections = new HashSet<>();
	        boolean isInitialSection = true;

	        for (String line : input) {
	            if (line.isBlank()) {
	                isInitialSection = false;
	                continue;
	            }

	            if (isInitialSection) {
	                // Parse initial wire values
	                String[] parts = line.split(": ");
	                wireMap.put(parts[0], new Wire(parts[0], Integer.parseInt(parts[1])));
	            } else {
	                // Parse gate connections
	                String[] parts = line.split(" ");
	                Wire input1 = wireMap.computeIfAbsent(parts[0], k -> new Wire(k, null));
	                Wire input2 = wireMap.computeIfAbsent(parts[2], k -> new Wire(k, null));
	                Wire output = wireMap.computeIfAbsent(parts[4], k -> new Wire(k, null));
	                connections.add(new Connection(Set.of(input1, input2), output, parts[1]));
	            }
	        }

	        return new Game(new HashSet<>(wireMap.values()), connections);
	    }

	    @Data
	    @AllArgsConstructor
	    static class Game {
	        Set<Wire> wires;
	        Set<Connection> connections;

	        public long solve() {
	            propagateValues();
	            return calculateResult();
	        }

	        private void propagateValues() {
	            boolean updated;
	            do {
	                updated = false;
	                for (Connection connection : connections) {
	                    List<Wire> inputs = new ArrayList<>(connection.inputs);
	                    if (inputs.get(0).value != null && inputs.get(1).value != null && connection.output.value == null) {
	                        connection.output.setValue(evaluateGate(connection));
	                        updated = true;
	                    }
	                }
	            } while (updated);
	        }

	        private int evaluateGate(Connection connection) {
	            List<Wire> inputs = new ArrayList<>(connection.inputs);
	            int a = inputs.get(0).value;
	            int b = inputs.get(1).value;

	            return switch (connection.type) {
	                case "AND" -> (a == 1 && b == 1) ? 1 : 0;
	                case "OR" -> (a == 1 || b == 1) ? 1 : 0;
	                case "XOR" -> (a != b) ? 1 : 0;
	                default -> throw new IllegalArgumentException("Unknown gate type: " + connection.type);
	            };
	        }

	        private long calculateResult() {
	            long result = 0;
	            List<Wire> zWires = wires.stream()
	                    .filter(w -> w.name.startsWith("z"))
	                    .sorted(Comparator.comparing(Wire::getName))
	                    .toList();

	            for (int i = 0; i < zWires.size(); i++) {
	                result += (long) zWires.get(i).value * (1L << i);
	            }

	            return result;
	        }
	    }

	    @Data
	    @AllArgsConstructor
	    static class Wire {
	        String name;
	        Integer value;
	    }

	    @Data
	    @AllArgsConstructor
	    static class Connection {
	        Set<Wire> inputs;
	        Wire output;
	        String type;
	    }
	

	public static List<Long> getDuration() {
		A2024Day24 d = new A2024Day24(24);
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

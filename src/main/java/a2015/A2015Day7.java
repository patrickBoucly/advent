package a2015;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

public class A2015Day7 extends A2015 {
	public A2015Day7(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2015Day7 d = new A2015Day7(7);
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
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Set<Wire> wires = getWires(inputL);
		return solveWireA(wires);
	
	}

	private long solveWireA(Set<Wire> wires) {
		while(Wire.getByName(wires,"a").value==null) {
			for(Wire w:wires) {
				if(w.value == null) {
					if(canBeIntegerRegex(w.inst.left) && canBeIntegerRegex(w.inst.rigth)) {
						w.value=apply(w.inst);
					}
					if(w.inst.left==null && canBeIntegerRegex(w.inst.rigth)) {
						w.value=apply(w.inst);
					}
				}
				if(w.value != null) {
					for(Wire w2:wires) {
						if(w2.inst != null &&w2.inst.left != null && w2.inst.left.equals(w.name)) {
							w2.inst.left=String.valueOf(w.value);
						}
						if(w2.inst != null &&w2.inst.rigth != null && w2.inst.rigth.equals(w.name)) {
							w2.inst.rigth=String.valueOf(w.value);
						}
					}
				}
			}
			
		}
		
		return Wire.getByName(wires,"a").value;
	}

	private Integer apply(Inst inst) {
		if(inst.op.equals("OR")) {
			return Integer.parseInt(inst.left)|Integer.parseInt(inst.rigth);
		}
		if(inst.op.equals("AND")) {
			return Integer.parseInt(inst.left)&Integer.parseInt(inst.rigth);
		}
		if(inst.op.equals("RSHIFT")) {
			return Integer.parseInt(inst.left)>>Integer.parseInt(inst.rigth);
		}
		if(inst.op.equals("LSHIFT")) {
			return Integer.parseInt(inst.left)<<Integer.parseInt(inst.rigth);
		}
		if(inst.op.equals("NOT")) {
			return 65535-Integer.parseInt(inst.rigth);
		}
		if(inst.op.equals("EQUALS")) {
			return Integer.parseInt(inst.rigth);
		}
		
		return null;
	}

	private Set<Wire> getWires(List<String> inputL) {
		Set<Wire> wires=new HashSet<>();
		for(String l:inputL) {
			String[] ls=l.split("->");
			if(canBeIntegerRegex(ls[0].trim())) {
			wires.add(new Wire(ls[1].trim(),null,Integer.parseInt(ls[0].trim())));
			}else {
				String[] instru=ls[0].trim().split(" ");
				if(instru.length==3) {
				wires.add(new Wire(ls[1].trim(),Inst.of(instru[0].trim(), instru[1].trim(), instru[2].trim()),null));
				}else if(instru.length==2){
					wires.add(new Wire(ls[1].trim(),Inst.of(null, instru[0].trim(), instru[1].trim()),null));
				}else {
					wires.add(new Wire(ls[1].trim(),Inst.of(null, "EQUALS", instru[0].trim()),null));
				}
			}
		}
		return wires;
	}
	@Data
	@AllArgsConstructor
	private static class Wire{
		public static Wire of(String name,Inst inst, Integer value) {
			return new Wire(name,inst,value);
		}
		String name;
		Inst inst;
		Integer value;
		public static Wire getByName(Set<Wire> wires,String name) {
			Optional<Wire> wire=wires.stream().filter(w->w.getName().equals(name)).findAny();
			if(wire.isPresent()) {
				return wire.get();
			}
			return null;
			
		}
		
	}
	@Data
	@AllArgsConstructor
	private static class Inst{
		public static Inst of(String left,String op, String rigth) {
			return new Inst(left,op,rigth);
		}
		String left;
		String op;
		String rigth;
		
		
	}
	public long s2(boolean b) {
		int res=(int) s1(b);
		List<String> inputL = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim).toList();
		Set<Wire> wires = getWires(inputL);
		Wire.getByName(wires, "b").value=res;
		return solveWireA(wires);
	
	}
	public static boolean canBeIntegerRegex(String str) {
	    if (str == null || str.isEmpty()) {
	        return false;
	    }
	    return str.matches("-?\\d+"); // Vérifie si la chaîne est un entier (positif ou négatif)
	}



	public static List<Long> getDuration() {
		A2015Day7 d = new A2015Day7(7);
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

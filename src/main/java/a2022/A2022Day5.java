package a2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class A2022Day5 extends A2022 {

	public A2022Day5(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day5 d = new A2022Day5(5);
		//System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1=endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day "+ d.day+" run 1 took "+timeS1+" milliseconds, run 2 took " + (endTime - startTime) + " milliseconds");
		
	}

	public String s2(boolean b) {
		List<String> input=Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		List<Stack<String>> stacks=getStacks();
		List<Deplacement> deplacements = getDeplacements(input);
		for(Deplacement d:deplacements) {
			List<String> aDeplacer=new ArrayList<>();
			for(int q=1;q<=d.qtt;q++) {
				aDeplacer.add(0,stacks.get(d.origine-1).pop());
			}
			for(String st:aDeplacer) {
				stacks.get(d.getDestination()-1).add(st);
			}
		}
		String res="";
		for(Stack stack:stacks) {
			res+=stack.peek();
		}
		return res;
	}
	
	

	public String s1(boolean b) {
		List<String> input=Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		List<Stack<String>> stacks=getStacks();
		List<Deplacement> deplacements = getDeplacements(input);
		for(Deplacement d:deplacements) {
			for(int q=1;q<=d.qtt;q++) {
			String aDep=stacks.get(d.origine-1).pop();
			stacks.get(d.getDestination()-1).add(aDep);
			}
		}
		String res="";
		for(Stack stack:stacks) {
			res+=stack.peek();
		}
		return res;
	}

	private List<Stack<String>> getStacks() {
		List<Stack<String>> stacks=new ArrayList<>();
		Stack<String> stack0=new Stack<String>();
		stack0.add("B");
		stack0.add("S");
		stack0.add("V");
		stack0.add("Z");
		stack0.add("G");
		stack0.add("P");
		stack0.add("W");
		Stack<String> stack1=new Stack<String>();
		stack1.add("J");
		stack1.add("V");
		stack1.add("B");
		stack1.add("C");
		stack1.add("Z");
		stack1.add("F");
		Stack<String> stack2=new Stack<String>();
		stack2.add("V");
		stack2.add("L");
		stack2.add("M");
		stack2.add("H");
		stack2.add("N");
		stack2.add("Z");
		stack2.add("D");
		stack2.add("C");
		Stack<String> stack3=new Stack<String>();
		stack3.add("L");
		stack3.add("D");
		stack3.add("M");
		stack3.add("Z");
		stack3.add("P");
		stack3.add("F");
		stack3.add("J");
		stack3.add("B");
		Stack<String> stack4=new Stack<String>();
		stack4.add("V");
		stack4.add("F");
		stack4.add("C");
		stack4.add("G");
		stack4.add("J");
		stack4.add("B");
		stack4.add("Q");
		stack4.add("H");
		Stack<String> stack5=new Stack<String>();
		stack5.add("G");
		stack5.add("F");
		stack5.add("Q");
		stack5.add("T");
		stack5.add("S");
		stack5.add("L");
		stack5.add("B");
		Stack<String> stack6=new Stack<String>();
		stack6.add("L");
		stack6.add("G");
		stack6.add("C");
		stack6.add("Z");
		stack6.add("V");
		Stack<String> stack7=new Stack<String>();
		stack7.add("N");
		stack7.add("L");
		stack7.add("G");
		Stack<String> stack8=new Stack<String>();
		stack8.add("J");
		stack8.add("F");
		stack8.add("H");
		stack8.add("C");
		stacks.add(stack0);
		stacks.add(stack1);
		stacks.add(stack2);
		stacks.add(stack3);
		stacks.add(stack4);
		stacks.add(stack5);
		stacks.add(stack6);
		stacks.add(stack7);
		stacks.add(stack8);
		return stacks;
	}

	private List<Deplacement> getDeplacements(List<String> input) {
		List<Deplacement> deplacements=new ArrayList<>();
		for(String s:input) {
			s=s.substring(5);
			deplacements.add(new Deplacement(Integer.valueOf(s.split("from")[0].trim()),Integer.valueOf(s.split("from")[1].split("to")[0].trim()),Integer.valueOf(s.split("from")[1].split("to")[1].trim())));
		}
		return deplacements;
	}

	private class Deplacement{
		int qtt;
		int origine;
		int destination;
		public Deplacement(int qtt, int origine, int destination) {
			super();
			this.qtt = qtt;
			this.origine = origine;
			this.destination = destination;
		}
		public int getQtt() {
			return qtt;
		}
		public void setQtt(int qtt) {
			this.qtt = qtt;
		}
		public int getOrigine() {
			return origine;
		}
		public void setOrigine(int origine) {
			this.origine = origine;
		}
		public int getDestination() {
			return destination;
		}
		public void setDestination(int destination) {
			this.destination = destination;
		}
		@Override
		public String toString() {
			return "Deplacement [qtt=" + qtt + ", origine=" + origine + ", destination=" + destination + "]";
		}
		
	}
	
	public static List<Long> getDuration() {
		A2022Day5 d = new A2022Day5(5);
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

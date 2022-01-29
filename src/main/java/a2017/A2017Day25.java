package a2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class A2017Day25 extends A2017 {

	public A2017Day25(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2017Day25 d = new A2017Day25(25);
		// d.s1(true);
		long startTime = System.currentTimeMillis();
		System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1=endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		System.out.println("Day "+ d.day+" run 1 took "+timeS1+" milliseconds, run 2 took " + (endTime - startTime) + " milliseconds");
		
	}

	public long s1(boolean b) {
		HashMap<Integer,Integer> slotVal=new HashMap<>();
		int nbStep=12317297;
		slotVal.put(0,0);
		Game g=new Game(getStates(),slotVal,0,getState("A",getStates()));
		for(int i=0;i<nbStep;i++) {
			g.nextStep();
		}
		return g.slotVal.values().stream().filter(v->v==1).count();
	}
	private State getState(String string, List<State> states) {
		for(State s:states) {
			if(s.nom.equals(string)) {
				return s;
			}
		}
		return null;
	}

	private List<State> getStates() {
		List<State> states=new ArrayList<>();
		states.add(new State("A",1,1,"B",0,-1,"D"));
		states.add(new State("B",1,1,"C",0,1,"F"));
		states.add(new State("C",1,-1,"C",1,-1,"A"));
		states.add(new State("D",0,-1,"E",1,1,"A"));
		states.add(new State("E",1,-1,"A",0,1,"B"));
		states.add(new State("F",0,1,"C",0,1,"E"));
		return states;
	}

	public int s2(boolean b) {
		return 0;
	}
	public class Game{
		List<State> states;
		HashMap<Integer,Integer> slotVal;
		int cursor;
		State curState;
		public List<State> getStates() {
			return states;
		}
		public void nextStep() {
			if(!slotVal.containsKey(cursor)) {
				slotVal.put(cursor, 0);
			}
			if(slotVal.get(cursor)==0) {
				slotVal.put(cursor, curState.w0);
				cursor+=curState.mov0;
				curState=getState(curState.ns0,states);
			} else {
				slotVal.put(cursor, curState.w1);
				cursor+=curState.mov1;
				curState=getState(curState.ns1,states);
			}
			
		}
		
		@Override
		public String toString() {
			return "Game [slotVal=" + slotVal + "]";
		}
		public void setStates(List<State> states) {
			this.states = states;
		}
		public HashMap<Integer, Integer> getSlotVal() {
			return slotVal;
		}
		public void setSlotVal(HashMap<Integer, Integer> slotVal) {
			this.slotVal = slotVal;
		}
		public int getCursor() {
			return cursor;
		}
		public void setCursor(int cursor) {
			this.cursor = cursor;
		}
		public State getCurState() {
			return curState;
		}
		public void setCurState(State curState) {
			this.curState = curState;
		}
		public Game(List<State> states, HashMap<Integer, Integer> slotVal, int cursor, State curState) {
			super();
			this.states = states;
			this.slotVal = slotVal;
			this.cursor = cursor;
			this.curState = curState;
		}
		
	}
	
public class State{
	String nom;
	int w0;
	int mov0;
	String ns0;
	int w1;
	int mov1;
	String ns1;
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getW0() {
		return w0;
	}
	public void setW0(int w0) {
		this.w0 = w0;
	}
	public int getMov0() {
		return mov0;
	}
	public void setMov0(int mov0) {
		this.mov0 = mov0;
	}
	public String getNs0() {
		return ns0;
	}
	public void setNs0(String ns0) {
		this.ns0 = ns0;
	}
	public int getW1() {
		return w1;
	}
	public void setW1(int w1) {
		this.w1 = w1;
	}
	public int getMov1() {
		return mov1;
	}
	public void setMov1(int mov1) {
		this.mov1 = mov1;
	}
	public String getNs1() {
		return ns1;
	}
	public void setNs1(String ns1) {
		this.ns1 = ns1;
	}
	public State(String nom, int w0, int mov0, String ns0, int w1, int mov1, String ns1) {
		super();
		this.nom = nom;
		this.w0 = w0;
		this.mov0 = mov0;
		this.ns0 = ns0;
		this.w1 = w1;
		this.mov1 = mov1;
		this.ns1 = ns1;
	}
	
	
	
	
}


	public static List<Long> getDuration() {
		A2017Day25 d = new A2017Day25(1);
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

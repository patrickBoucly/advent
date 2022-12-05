package a2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class A2022Day02 extends A2022 {

	public A2022Day02(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day02 d = new A2022Day02(2);
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

	public long s1(boolean b) {
		List<String> inputL=Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		List<Round> r=new ArrayList<>();
		Game g=new Game();
		for(String s:inputL) {
			String[] sp=s.split(" ");
			r.add(new Round(sp[0],sp[1]));
		}
		
		return r.stream().mapToLong(t -> t.pointP2).sum();
	}
	
	private class Game{
		List<Round> rounds;
	}
	private class Round{
		String actionP1="";
		String actionP2="";
		Long pointP1=0L;
		Long pointP2=0L;
		
		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(actionP1, actionP2);
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Round other = (Round) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Objects.equals(actionP1, other.actionP1) && Objects.equals(actionP2, other.actionP2);
		}
		public String getActionP1() {
			return actionP1;
		}
		public void setActionP1(String actionP1) {
			this.actionP1 = actionP1;
		}
		public String getActionP2() {
			return actionP2;
		}
		public void setActionP2(String actionP2) {
			this.actionP2 = actionP2;
		}
		public Long getPointP1() {
			return pointP1;
		}
		public void setPointP1(Long pointP1) {
			this.pointP1 = pointP1;
		}
		public Long getPointP2() {
			return pointP2;
		}
		public void setPointP2(Long pointP2) {
			this.pointP2 = pointP2;
		}
		public Round(String actionP1, String actionP2) {
			super();
			this.actionP1 = actionP1.trim();
			this.actionP2 = actionP2.trim();
			this.pointP1=0L;
			this.pointP2=0L;
			evalRound();
		}
		public void evalRound() {
			pointP1=pointChoix(actionP1)+pointCompare1(actionP1,actionP2);
			pointP2+=pointChoix(actionP2)+pointCompare2(actionP1,actionP2);
		}
		private Long pointCompare1(String actionP12, String actionP22) {
			if(actionP12.equals("A")) {
				if(actionP22.equals("X")) {
					return 3L;
				}
				if(actionP22.equals("Y")) {
					return 0L;
				}
				if(actionP22.equals("Z")) {
					return 6L;
				}
			}
			if(actionP12.equals("B")) {
				if(actionP22.equals("X")) {
					return 6L;
				}
				if(actionP22.equals("Y")) {
					return 3L;
				}
				if(actionP22.equals("Z")) {
					return 0L;
				}
			}
			if(actionP12.equals("C")) {
				if(actionP22.equals("X")) {
					return 0L;
				}
				if(actionP22.equals("Y")) {
					return 6L;
				}
				if(actionP22.equals("Z")) {
					return 3L;
				}
			}
			return null;
		}
		private Long pointCompare2(String actionP12, String actionP22) {
			if(actionP12.equals("A")) {
				if(actionP22.equals("X")) {
					return 3L;
				}
				if(actionP22.equals("Y")) {
					return 6L;
				}
				if(actionP22.equals("Z")) {
					return 0L;
				}
			}
			if(actionP12.equals("B")) {
				if(actionP22.equals("X")) {
					return 0L;
				}
				if(actionP22.equals("Y")) {
					return 3L;
				}
				if(actionP22.equals("Z")) {
					return 6L;
				}
			}
			if(actionP12.equals("C")) {
				if(actionP22.equals("X")) {
					return 6L;
				}
				if(actionP22.equals("Y")) {
					return 0L;
				}
				if(actionP22.equals("Z")) {
					return 3L;
				}
			}
			return null;
		}
		private Long pointChoix(String action) {
			if(action.equals("A")||action.equals("X")) {
				return 1L;
			}
			if(action.equals("B")||action.equals("Y")) {
				return 2L;
			}
			if(action.equals("C")||action.equals("Z")) {
				return 3L;
			}
			return 0L;
			
		}
		@Override
		public String toString() {
			return "Round [actionP1=" + actionP1 + ", actionP2=" + actionP2 + ", pointP1=" + pointP1 + ", pointP2="
					+ pointP2 + "]";
		}
		private A2022Day02 getEnclosingInstance() {
			return A2022Day02.this;
		}
		public void evalRound2() {
			if(actionP1.equals("A")) {
				if(actionP2.equals("X")) {
					pointP1=4L;
					pointP2=3L;
				}
				if(actionP2.equals("Y")) {
					pointP1=4L;
					pointP2=4L;
				}
				if(actionP2.equals("Z")) {
					pointP1=1L;
					pointP2=8L;
				}
			}
			if(actionP1.equals("B")) {
				if(actionP2.equals("X")) {
					pointP1=8L;
					pointP2=1L;
				}
				if(actionP2.equals("Y")) {
					pointP1=5L;
					pointP2=5L;
				}
				if(actionP2.equals("Z")) {
					pointP1=2L;
					pointP2=9L;
				}
			}
			if(actionP1.equals("C")) {
				if(actionP2.equals("X")) {
					pointP1=9L;
					pointP2=2L;
				}
				if(actionP2.equals("Y")) {
					pointP1=6L;
					pointP2=6L;
				}
				if(actionP2.equals("Z")) {
					pointP1=3L;
					pointP2=7L;
				}
			}
			
		}
		
	}

	
	
	public long s2(boolean b) {
		List<String> inputL=Arrays.asList(getInput(b).split("\n")).stream().collect(Collectors.toList());
		List<Round> r=new ArrayList<>();
		Game g=new Game();
		for(String s:inputL) {
			String[] sp=s.split(" ");
			Round ro=new Round(sp[0],sp[1]);
			ro.evalRound2();
			System.out.println(ro);
			r.add(ro);
		}
		
		return r.stream().mapToLong(t -> t.pointP2).sum();
	}
	public static List<Long> getDuration() {
		A2022Day02 d = new A2022Day02(2);
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

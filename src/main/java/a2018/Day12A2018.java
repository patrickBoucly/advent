package a2018;

import java.util.HashMap;
import java.util.Map.Entry;

public class Day12A2018 {

	public static void main(String[] args0) {
		//s1();
		s2();
	}

	private static void s2() {
		int input=7315;
		HashMap<Cell,Integer> scoresCell = new HashMap<>();
		int max=0;
		int sMax=0;
		
		for(int s=1;s<20;s++) {
			for(int x=1;x<=300-s;x++) {
				for(int y=1;y<=300-s;y++) {
					if(score(new Cell(x,y,input),s)>max){
						System.out.println("s : "+s+" x : "+x+" y:"+y+ "score :"+score(new Cell(x,y,input),s));
						scoresCell.put(new Cell(x,y,input), score(new Cell(x,y,input),s));
						max=score(new Cell(x,y,input),s);
						sMax=s;
					}
					
				}
			}
		}
		
		for(Entry<Cell, Integer> c:scoresCell.entrySet()) {
			if(c.getValue() == max) {
				System.out.println(c.getKey() +" size "+sMax + " powerTotal : "+max);
			}
		}
		
	}

	private static void s1() {
		int input=7315;
		HashMap<Cell,Integer> scoresCell = new HashMap<>();
		int max=0;
		for(int x=1;x<=298;x++) {
			for(int y=1;y<=298;y++) {
				if(score(new Cell(x,y,input),3)>max){
					scoresCell.put(new Cell(x,y,input), score(new Cell(x,y,input),3));
					max=score(new Cell(x,y,input),3);
				}
				
			}
		}
		for(Entry<Cell, Integer> c:scoresCell.entrySet()) {
			if(c.getValue() == max) {
				System.out.println(c.getKey());
			}
		}
		
		
	}
	private static Integer score(Cell cell,int size) {
		int tot=0;
		for(int x=cell.x;x<cell.x+size;x++) {
			for(int y=cell.y;y<cell.y+size;y++) {
				tot+=(new Cell(x,y,cell.input)).power;
			}
		}
		return tot;
	}
	public static class Cell {
		int input;
		int x;
		int y;
		int power;
		public Cell(int x, int y,int input) {
			super();
			this.x = x;
			this.y = y;
			this.input=input;
			setPower(x,y);
		}
		public void setPower(int x,int y) {
			int rack=x+10;
			int beginPowerLevel=rack*y;
			int increase=(beginPowerLevel+this.input)*rack;
			if (increase<100 && increase > -100) {
				this.power =-5;
			} else {
				String tmp=String.valueOf(Math.abs(increase));
				this.power =Integer.parseInt(tmp.substring(tmp.length()-3,tmp.length()-2))-5;
			}
			
		}
		@Override
		public String toString() {
			return "Cell [x=" + x + ", y=" + y + ", power=" + power + "]";
		}
		
	}
}

package aocmaven.a2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day16A2018 {

	public static void main(String[] args0) {
		s1();
		// s2();
	}

	private static void s2() {
		String input = read();
	}

	private static void s1() {
		//Sample s1= new Sample(new Register(Arrays.asList(3, 2, 1, 1)),new Register(Arrays.asList(3, 2, 2, 1)),new Instruction(9, 2, 1, 2));
		List<Sample> inputSamples = new ArrayList<>();
		
		String input = read();
		
		Register before=null;
		Instruction instruction=null;
		Register afterRecherche=null;
		int pos=0;
		String[] inputS=input.split("\n");
		for (String s : inputS) {
	//	System.out.println(s);
		}
		
		for (String s : inputS) {
			if(s.equals("@")) {
				break;
			}
		///	System.out.println(s);
			if(pos % 4 ==0) {
			before =new Register(Arrays.asList(Integer.parseInt(s.substring(9, 10)),Integer.parseInt(s.substring(12, 13)),Integer.parseInt(s.substring(15, 16)),Integer.parseInt(s.substring(18, 19))));
			} else if(pos % 4 ==1) {
				List<Integer> instruc= new ArrayList<>();
				String[] instrucS=s.split(" ");
				for(String c:instrucS) {
					instruc.add(Integer.parseInt(c.trim()));
				}
				instruction=new Instruction(instruc);
			}else if(pos % 4 ==2) {
				afterRecherche=new Register(Arrays.asList(Integer.parseInt(s.substring(9, 10)),Integer.parseInt(s.substring(12, 13)),Integer.parseInt(s.substring(15, 16)),Integer.parseInt(s.substring(18, 19))));
			} else {
				inputSamples.add(new Sample(before, afterRecherche,instruction));
			}
			pos++;
		}
		
		
		int res=0;
		
		
		
		int cpt=0;
		for(Sample s:inputSamples) {
			if(cpt>4) {
		//		break;
			}
		//	res+=matchPlusDe3OpCode(s);
			cpt++;
		}
		Map<Integer,String> mapOpCode = new HashMap<>();
		Map<String,Integer> mapOpCodeNomNum = new HashMap<>();
		List<Sample> LM1=null;
		List<Sample> LM2=null;
		List<Sample> LM3=null;
		List<Sample> LM4=null;
		List<Sample> LM5=null;
		
		LM1=getMatch(inputSamples,1);
		
		System.out.println(LM1.get(0).instruction);
		System.out.println(getOPverifiees(LM1.get(0)));
		
		LM2=getMatch(inputSamples,2);
		System.out.println(LM2.get(0).instruction);
		System.out.println(getOPverifiees(LM2.get(0)));
		LM3=getMatch(inputSamples,3);
		System.out.println(LM3.get(0).instruction);
		System.out.println(getOPverifiees(LM3.get(0)));
		
		
		for(int i=1;i<17;i++) {
			List<Sample> LM=getLMI(inputSamples,i);
			for(int j=0;j<LM.size();j++) {
				//if(!opTrouves.contains(getOPverifiees(LM2.get(j)).instruction.opcode.toString()))) {
					
			//	}
			}
			
		}
		
	}
	
	 private static List<Sample> getLMI(List<Sample> inputSamples, int i) {
		return getMatch( inputSamples, i) ;
	}
	
	 private static List<Sample> getMatch(List<Sample> inputSamples, int i) {
		 List<Sample> LM= new ArrayList<>();
		 for(Sample s:inputSamples) {
			 if(getOPverifiees(s).size()== i) {
					LM.add(s);
			}
		 }
		
		return LM;
	}

	private static int matchPlusDe3OpCode(Sample s) {
		if(getOPverifiees(s).size()>=3) {
			return 1;
		}
		return 0;
	}

	
	private static List<String> getOPverifiees(Sample s) {
		return getOPverifiees(s.before,s.instruction,s.afterRecherche);
	}

	private static List<String> getOPverifiees(Register before, Instruction instruction, Register afterRecherche) {
		List<String> opVerifies = new ArrayList<>();
		// addr,addi,mulr,muli,seti,setr,bori,borr,bani,banr,gtir,gtri,gtrr,eqir,eqri,eqrr
		if (addr(before, instruction).equals(afterRecherche)) {
			opVerifies.add("addr");
		}
		//System.out.println("after addr:"+addr(before, instruction));
		
		
		if (addi(before, instruction).equals(afterRecherche)) {
			opVerifies.add("addi");
		}
	//	System.out.println("after addi:"+addi(before, instruction));
		
		
		if (mulr(before, instruction).equals(afterRecherche)) {
			opVerifies.add("mulr");
		}
	//	System.out.println("after mulr:"+mulr(before, instruction));
		

		if (muli(before, instruction).equals(afterRecherche)) {
			opVerifies.add("muli");
		}
	//	System.out.println("after muli:"+muli(before, instruction));
		
		if (eqrr(before, instruction).equals(afterRecherche)) {
			opVerifies.add("eqrr");
		}
		
	//	System.out.println("after eqrr:"+eqrr(before, instruction));
		
		
		if (eqri(before, instruction).equals(afterRecherche)) {
			opVerifies.add("eqri");
		}
		//System.out.println("after eqri:"+eqri(before, instruction));
		
		
		
		if (eqir(before, instruction).equals(afterRecherche)) {
			opVerifies.add("eqir");
		}
		//System.out.println("after eqir:"+eqir(before, instruction));
		
		
		if (gtrr(before, instruction).equals(afterRecherche)) {
			opVerifies.add("gtrr");
		}
	//	System.out.println("after gtrr:"+gtrr(before, instruction));
		
		
		if (gtri(before, instruction).equals(afterRecherche)) {
			opVerifies.add("gtri");
		}
	//	System.out.println("after gtri:"+gtri(before, instruction));
		
		
		if (gtir(before, instruction).equals(afterRecherche)) {
			opVerifies.add("gtir");
		}
	//	System.out.println("after gtir:"+gtir(before, instruction));
		if (banr(before, instruction).equals(afterRecherche)) {
			opVerifies.add("banr");
		}
	//	System.out.println("after banr:"+banr(before, instruction));
		
		
		if (bani(before, instruction).equals(afterRecherche)) {
			opVerifies.add("bani");
		}
	//	System.out.println("after bani:"+bani(before, instruction));
		
		if (seti(before, instruction).equals(afterRecherche)) {
			opVerifies.add("seti");
		}
	//	System.out.println("after seti:"+seti(before, instruction));
		
		
		if (setr(before, instruction).equals(afterRecherche)) {
			opVerifies.add("setr");
		}
	//	System.out.println("after setr"+setr(before, instruction));
		
		
		if (bori(before, instruction).equals(afterRecherche)) {
			opVerifies.add("bori");
		}
	//	System.out.println("after bori:"+bori(before, instruction));
		
		
		if (borr(before, instruction).equals(afterRecherche)) {
			opVerifies.add("borr");
		}
	//	System.out.println("after borr:"+borr(before, instruction));
		
		return opVerifies;
	}

	public static Register addr(Register before, Instruction instruction) {
		List<Integer> r= new ArrayList<>(before.getRegister()) ;
		Register after = new Register(r);
		return after.setRegisterP(after.getRegisterP(instruction.inputA)+after.getRegisterP(instruction.inputB),instruction.outputC);
	}

	public static Register addi(Register before, Instruction instruction) {

		List<Integer> r= new ArrayList<>(before.getRegister()) ;
		Register after = new Register(r);
		return after.setRegisterP(after.getRegisterP(instruction.inputA)+instruction.getInputB(),instruction.outputC);
	}

	public static Register mulr(Register before, Instruction instruction) {
		List<Integer> r= new ArrayList<>(before.getRegister()) ;
		Register after = new Register(r);
		return after.setRegisterP(after.getRegisterP(instruction.inputA)*after.getRegisterP(instruction.inputB),instruction.outputC);
	}

	public static Register muli(Register before, Instruction instruction) {
		List<Integer> r= new ArrayList<>(before.getRegister()) ;
		Register after = new Register(r);
		return after.setRegisterP(after.getRegisterP(instruction.inputA)*instruction.inputB,instruction.outputC);
	}

	public static Register banr(Register before, Instruction instruction) {
		List<Integer> r= new ArrayList<>(before.getRegister()) ;
		Register after = new Register(r);
		return after.setRegisterP(after.getRegisterP(instruction.inputA)&after.getRegisterP(instruction.inputB),instruction.outputC);
	}

	public static Register bani(Register before, Instruction instruction) {
		List<Integer> r= new ArrayList<>(before.getRegister()) ;
		Register after = new Register(r);
		return after.setRegisterP(after.getRegisterP(instruction.inputA)&instruction.getInputB(),instruction.outputC);
	}

	public static Register borr(Register before, Instruction instruction) {
		List<Integer> r= new ArrayList<>(before.getRegister()) ;
		Register after = new Register(r);
		return after.setRegisterP(after.getRegisterP(instruction.inputA)|after.getRegisterP(instruction.inputB),instruction.outputC);
	}

	public static Register bori(Register before, Instruction instruction) {
		List<Integer> r= new ArrayList<>(before.getRegister()) ;
		Register after = new Register(r);
		return after.setRegisterP(after.getRegisterP(instruction.inputA)|instruction.getInputB(),instruction.outputC);
	}

	public static Register setr(Register before, Instruction instruction) {
		List<Integer> r= new ArrayList<>(before.getRegister()) ;
		Register after = new Register(r);
		return after.setRegisterP(after.getRegisterP(instruction.inputA),instruction.outputC);
	}

	public static Register seti(Register before, Instruction instruction) {
		List<Integer> r= new ArrayList<>(before.getRegister()) ;
		Register after = new Register(r);
		return after.setRegisterP(instruction.inputA,instruction.outputC);
	}

	public static Register gtir(Register before, Instruction instruction) {
		List<Integer> r= new ArrayList<>(before.getRegister()) ;
		Register after = new Register(r);
		int d = 0;
		if (instruction.getInputA() > after.getRegisterP(instruction.inputB)) {
			d = 1;
		}
		return after.setRegisterP(d,instruction.outputC);
	}

	public static Register gtri(Register before, Instruction instruction) {
		List<Integer> r= new ArrayList<>(before.getRegister()) ;
		Register after = new Register(r);
		// r A vB
		int d = 0;
		if (after.getRegisterP(instruction.inputA) > instruction.inputB) {
			d = 1;
		}
		return after.setRegisterP(d,instruction.outputC);
	}

	public static Register gtrr(Register before, Instruction instruction) {
		List<Integer> r= new ArrayList<>(before.getRegister()) ;
		Register after = new Register(r);
		// ra rb
		int d = 0;
		if (after.getRegisterP(instruction.inputA) > after.getRegisterP(instruction.inputB)) {
			d = 1;
		}
		return after.setRegisterP(d,instruction.outputC);
	}

	public static Register eqir(Register before, Instruction instruction) {
		List<Integer> r= new ArrayList<>(before.getRegister()) ;
		Register after = new Register(r);
		int d = 0;
		if (instruction.getInputA().equals(after.getRegisterP(instruction.inputB))) {
			d = 1;
		}
		return after.setRegisterP(d,instruction.outputC);
	}

	public static Register eqri(Register before, Instruction instruction) {
		List<Integer> r= new ArrayList<>(before.getRegister()) ;
		Register after = new Register(r);
		// r A vB
		int d = 0;
		if (after.getRegisterP(instruction.inputA).equals(instruction.inputB)) {
			d = 1;
		}
		return after.setRegisterP(d,instruction.outputC);
	}

	public static Register eqrr(Register before, Instruction instruction) {
		List<Integer> r= new ArrayList<>(before.getRegister()) ;
		Register after = new Register(r);
		// ra rb
		int d = 0;
		if (after.getRegisterP(instruction.inputA).equals(after.getRegisterP(instruction.inputB))) {
			d = 1;
		}
		return after.setRegisterP(d,instruction.outputC);
	}
	
	public static class Sample {
		Register before;
		Register afterRecherche;
		Instruction instruction;
		public Register getBefore() {
			return before;
		}
		public void setBefore(Register before) {
			this.before = before;
		}
		public Register getAfterRecherche() {
			return afterRecherche;
		}
		public void setAfterRecherche(Register afterRecherche) {
			this.afterRecherche = afterRecherche;
		}
		public Instruction getInstruction() {
			return instruction;
		}
		public void setInstruction(Instruction instruction) {
			this.instruction = instruction;
		}
		public Sample(Register before, Register afterRecherche, Instruction instruction) {
			super();
			this.before = before;
			this.afterRecherche = afterRecherche;
			this.instruction = instruction;
		}
		@Override
		public String toString() {
			String res ="";
			res+="Before:"+before.register;
			res+="\n";
			res+=instruction.toString();
			res+="\n";
			res+="After:"+afterRecherche.register;
			res+="\n";
			
			return res;
		}
		
	
	}
	public static class Register {
		List<Integer> register;
		public Register(List<Integer> register) {
			super();
			this.register = register;
		}
		
		public List<Integer> getRegister() {
			return register;
		}

		public void setRegister(List<Integer> register) {
			this.register = register;
		}
		public Register setRegisterP(Integer value,Integer pos) {
			Register after = new Register(this.getRegister());
			after.register.set(pos, value);
			return after;
		}
		public Integer getRegisterP(Integer pos) {
			return register.get(pos);
		}

		@Override
		public String toString() {
			return "[" + register + "]";
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((register == null) ? 0 : register.hashCode());
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
			Register other = (Register) obj;
			if (register == null) {
				if (other.register != null)
					return false;
			} else if (!register.equals(other.register))
				return false;
			return true;
		}
		
	}
	public static class Instruction {
		Integer opcode;
		Integer inputA;
		Integer inputB;
		Integer outputC;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((inputA == null) ? 0 : inputA.hashCode());
			result = prime * result + ((inputB == null) ? 0 : inputB.hashCode());
			result = prime * result + ((opcode == null) ? 0 : opcode.hashCode());
			result = prime * result + ((outputC == null) ? 0 : outputC.hashCode());
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
			Instruction other = (Instruction) obj;
			if (inputA == null) {
				if (other.inputA != null)
					return false;
			} else if (!inputA.equals(other.inputA))
				return false;
			if (inputB == null) {
				if (other.inputB != null)
					return false;
			} else if (!inputB.equals(other.inputB))
				return false;
			if (opcode == null) {
				if (other.opcode != null)
					return false;
			} else if (!opcode.equals(other.opcode))
				return false;
			if (outputC == null) {
				if (other.outputC != null)
					return false;
			} else if (!outputC.equals(other.outputC))
				return false;
			return true;
		}

		public Integer getOpcode() {
			return opcode;
		}

		public void setOpcode(Integer opcode) {
			this.opcode = opcode;
		}

		public Integer getInputA() {
			return inputA;
		}

		public void setInputA(Integer inputA) {
			this.inputA = inputA;
		}

		public Integer getInputB() {
			return inputB;
		}

		public void setInputB(Integer inputB) {
			this.inputB = inputB;
		}

		public Integer getOutputC() {
			return outputC;
		}

		public void setOutputC(Integer outputC) {
			this.outputC = outputC;
		}

		@Override
		public String toString() {
			return "[" + opcode + ", " + inputA + ", " + inputB + ", "	+ outputC + "]";
		}
		
		public Instruction(List<Integer> instruc) {
			super();
			this.opcode = instruc.get(0);
			this.inputA = instruc.get(1);
			this.inputB = instruc.get(2);
			this.outputC = instruc.get(3);
		}
		
		public Instruction(Integer opcode, Integer inputA, Integer inputB, Integer outputC) {
			super();
			this.opcode = opcode;
			this.inputA = inputA;
			this.inputB = inputB;
			this.outputC = outputC;
		}
	}

	private static String read() {
		Path path = Paths.get(
				"C:\\git_repositories\\advent\\src\\main\\resources\\src\\advent_of_code\\main\\resources\\a2018\\input16_1");
		String content = "";
		try {
			content = Files.readString(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;

	}
}

package a2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A2024Day17 extends A2024 {

	public A2024Day17(int day) {
		super(day);
	}
	private static int[] program;
	public static Long registerA = 0L;
	public static Long registerB = 0L;
	public static Long registerC = 0L;

	public static void main(String[] args0) {
		A2024Day17 d = new A2024Day17(17);
		//System.out.println(d.s1(false));
		// too high 104512
		long startTime = System.currentTimeMillis();
		// d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	public String s1(boolean b) {
		int k=4;
		registerA = 59590048L;
		registerB = 0L;
		registerC = 0L;

		List<Integer> program = Arrays.asList(2,4,1,5,7,5,0,3,1,6,4,3,5,5,3,0);
		List<Long> output = executeProgram(registerA, registerB, registerC, program);

		return String.join(",", output.stream().map(String::valueOf).toArray(String[]::new));
	}

	public static List<Long> executeProgram(Long registerA, Long registerB, Long registerC, List<Integer> program) {
		// Liste pour stocker les valeurs de sortie
		List<Long> output = new ArrayList<>();
		// Instruction pointer (pointeur d'instruction)
		int instructionPointer = 0;

		// Boucle principale pour exécuter les instructions
		while (instructionPointer < program.size()) {
			int opcode = program.get(instructionPointer); // Opcode actuel
			int operand = program.get(instructionPointer + 1); // Opérande associé
			instructionPointer += 2; // Avance de 2 positions

			int denominator;
			if (operand < 4) {
				denominator = (int) Math.pow(2, operand);
			} else {
				if (operand == 4) {
					denominator = (int) Math.pow(2, registerA);
				} else if (operand == 5) {
					denominator = (int) Math.pow(2, registerB);
				} else {
					denominator = (int) Math.pow(2, registerC);
				}
			}

			switch (opcode) {
			case 0: // adv : Divise A par 2^operand et stocke dans A
				registerA /= denominator;
				break;

			case 1: // bxl : XOR entre B et operand, résultat dans B
				registerB ^= operand;
				break;

			case 2: // bst : Operand modulo 8, résultat dans B
				if (operand < 4) {
					registerB = (long) (operand % 8);
				} else if (operand == 4) {
					registerB = registerA % 8;
				} else if (operand == 5) {
					registerB = registerB % 8;
				} else {
					registerB = registerC % 8;
				}
				break;

			case 3: // jnz : Saut si A != 0
				if (registerA != 0) {
					instructionPointer = operand;
				}
				break;

			case 4: // bxc : XOR entre B et C, résultat dans B
				registerB ^= registerC;
				break;

			case 5: // out : Operand modulo 8, ajoute à la sortie
				long outValue;
				if (operand < 4) {
					outValue = operand % 8;
				} else if (operand == 4) {
					outValue = registerA % 8;
				} else if (operand == 5) {
					outValue = registerB % 8;
				} else {
					outValue = registerC % 8;
				}
				output.add(outValue);
				break;

			case 6: // bdv : Divise A par 2^operand et stocke dans B
				registerB = registerA / denominator;
				break;

			case 7: // cdv : Divise A par 2^operand et stocke dans C
				registerC = registerA / denominator;
				break;

			default: // Instruction invalide (ne devrait jamais arriver)
				throw new IllegalArgumentException("Opcode inconnu: " + opcode);
			}
		}

		// Retourne les valeurs de sortie sous forme de chaîne jointe par des virgules
		return output;
	}
	 public static boolean areListsIdentical(List<Integer> intList, List<Long> longList) {
	        // Vérification de la taille
	        if (intList.size() != longList.size()) {
	            return false;
	        }
	        
	        // Parcours et comparaison
	        for (int i = 0; i < intList.size(); i++) {
	            long intAsLong = intList.get(i).longValue();
	            long longValue = longList.get(i);
	            
	            if (intAsLong != longValue) {
	                return false;
	            }
	        }
	        
	        return true;
	    }

	public Long s2(boolean b) {
		registerA = 35300000000000L;
		//max 281500000000000L
		Long cible= registerA;
		registerB = 0L;
		registerC = 0L;		
		List<Integer> program = Arrays.asList(2,4,1,5,7,5,0,3,1,6,4,3,5,5,3,0);
		Long cp=0L;
		for(Long l=35300000000000L;l<281500000000000L;l++) {
			registerA = l;
			cible= registerA;
			registerB = 0L;
			registerC = 0L;
			if(cp%10000000000L==0) {
				System.out.println(cp);
			}
			
			List<Long> output = executeProgram(registerA, registerB, registerC, program);
			if(areListsIdentical(program,output) && cible.equals(registerA) && registerB.equals(0L) && registerC.equals(0L)) {
				return registerA;
			}
			cp++;
		}

		
		return 0L;
	}

	

	private boolean progEgaux(List<Long> output, List<Integer> program) {
		if (output.size() != program.size()) {
			return false;
		}
		for (int i = 0; i < output.size(); i++) {
			if (output.get(i) - program.get(i) != 0) {
				return false;
			}
		}
		return true;
	}

	

	public static List<Long> getDuration() {
		A2024Day17 d = new A2024Day17(16);
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

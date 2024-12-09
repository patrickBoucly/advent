package a2024;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import outils.MesOutils;

public class A2024Day9 extends A2024 {

	public A2024Day9(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2024Day9 d = new A2024Day9(9);
		// System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		// d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");
	}

	public long s1(boolean b) {
		Disque disque = getDisque(getInput(b));
		disque.moveBlock();
		return disque.calculateChecksum();
	}

	public Long s2(boolean b) {
		Disque disque = getDisque(getInput(b));
		disque.moveBlock2();
		return disque.calculateChecksum2();
	}

	private Disque getDisque(String input) {
		List<File> files = new ArrayList<>();
		int id = -1;
		int fileSize = -1;
		int leftSize = -1;
		for (int i = 0; i < input.length() - 1; i++) {
			if (i % 2 == 0) {
				id++;
				fileSize = Integer.parseInt(input.substring(i, i + 1));
			} else {
				leftSize = Integer.parseInt(input.substring(i, i + 1));
				files.add(new File(id, fileSize, leftSize, 0, new ArrayDeque<>()));
			}
		}
		id++;
		fileSize = Integer.parseInt(input.substring(input.length() - 1, input.length()));
		files.add(new File(id, fileSize, 0, 0, new ArrayDeque<>()));
		int maxId = MesOutils.getMaxIntegerFromList(files.stream().map(f -> f.id).toList());
		return new Disque(files, maxId);
	}

	@Data
	@AllArgsConstructor
	public static class File {
		int id;
		int fileSize;
		int leftSize;
		int deplace;
		Deque<Integer> leftSpace;
	}

	@Data
	@AllArgsConstructor
	public static class Disque {
		List<File> files;
		int maxId;

		public void moveBlock() {

			Integer nbToMove = 0;
			while (nbToMove != null) {
				nbToMove = getnbToMove();
				if (nbToMove != null) {
					ajouterNombre(nbToMove);
				}

			}
		}

		public void moveBlock2() {
			for (int i = maxId; i > 0; i--) {
				deplacerBloque(i);
			}

		}

		private void deplacerBloque(int i) {
			int sizeToMove = files.get(i).fileSize;
			for (int j = 0; j < i; j++) {
				File fDest = files.get(j);
				File fOri = files.get(i);
				if (sizeToMove + fDest.leftSpace.size() <= fDest.leftSize) {
					for (int k = 0; k < fOri.fileSize; k++) {
						fDest.leftSpace.addLast(fOri.id);
						fOri.deplace = fOri.fileSize;
					}
					break;
				}
			}

		}

		private Integer getnbToMove() {
			for (int i = maxId; i > 0; i--) {
				File f = files.get(i);
				if (f.fileSize > f.deplace) {
					if (remainPlace(f.id)) {
						f.deplace++;
						return f.id;
					}
				}
			}
			return null;
		}

		private boolean remainPlace(int id) {
			for (int i = 0; i < id; i++) {
				if (files.get(i).leftSpace.size() < files.get(i).leftSize) {
					return true;
				}
			}
			return false;
		}

		private void ajouterNombre(Integer nbToMove) {
			for (int i = 0; i < files.size(); i++) {
				if (files.get(i).leftSpace.size() < files.get(i).leftSize) {
					files.get(i).leftSpace.addLast(nbToMove);
					break;
				}
			}

		}

		public Long calculateChecksum() {
			Long res = 0L;
			String s = "";
			int k = 0;
			for (File f : files) {

				for (int j = 0; j < f.fileSize - f.deplace; j++) {
					res += k * f.id;
					s += f.id;
					k++;
				}
				for (int i = 0; i < f.leftSize; i++) {
					Integer add = f.leftSpace.pollFirst();
					if (add != null) {
						res += k * add;
						s += add;
						k++;
					}

				}
			}
			System.out.println(s);
			return res;
		}

		public Long calculateChecksum2() {
			Long res = 0L;
			String s = "";
			int k = 0;
			for (File f : files) {

				for (int j = 0; j < f.fileSize - f.deplace; j++) {
					res += k * f.id;
					s += f.id;
					k++;
				}
				for (int j = f.fileSize - f.deplace; j < f.fileSize; j++) {
					s += ".";
					k++;
				}
				int reste=f.leftSize - f.leftSpace.size();
				for (int i = 0; i < f.leftSize; i++) {
					
					Integer add = f.leftSpace.pollFirst();
					if (add != null) {
						res += k * add;
						s += add;
						k++;
					}
				}
				
				for(int w=0;w<reste;w++) {
					k++;
					s+=".";
				}
			}
			System.out.println(s);
			return res;
		}

	}

	public static List<Long> getDuration() {
		A2024Day9 d = new A2024Day9(9);
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

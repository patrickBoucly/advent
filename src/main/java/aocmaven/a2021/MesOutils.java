package aocmaven.a2021;

import java.util.List;
import java.util.NoSuchElementException;

public class MesOutils {
	public static Integer getMaxIntegerFromList(List<Integer> listOfIntegers) {
		return listOfIntegers.stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new);
	}

	public static Integer getMinIntegerFromList(List<Integer> listOfIntegers) {
		return listOfIntegers.stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new);
	}


	public static long getMinLongFromList(List<Long> listOfLongs) {
		return listOfLongs.stream().mapToLong(v -> v).min().orElseThrow(NoSuchElementException::new);
		}
	public static long getMaxLongFromList(List<Long> listOfLongs) {
		return listOfLongs.stream().mapToLong(v -> v).max().orElseThrow(NoSuchElementException::new);
		}

}

package outils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;

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
	/*
	public static long getMinOccuredLetter(String line) {
		InputStream targetStream = new ByteArrayInputStream(line.getBytes());
		return targetStream. mapToLong(v -> v).min().orElseThrow(NoSuchElementException::new);
		}
	public static long getMaxOccuredLetter(String line) {
		return listOfLongs.stream().mapToLong(v -> v).min().orElseThrow(NoSuchElementException::new);
		}
	
	
	
	public static Stream GenerateStreamFromString(string s)
	{
	    var stream = new MemoryStream();
	    var writer = new StreamWriter(stream);
	    writer.Write(s);
	    writer.Flush();
	    stream.Position = 0;
	    return stream;
	}
	*/
}

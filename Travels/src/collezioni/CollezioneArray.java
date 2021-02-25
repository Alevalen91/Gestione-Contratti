package collezioni;

import java.util.*;

public class CollezioneArray {

	public static void main(String[] args) {
		
		// Performance con l'uso di ensureCapacity()
		ArrayList<String> list = new ArrayList<String>(10000000);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
		list.add("nuovo elemento");
		}
		
		LinkedList <String> lklist = new LinkedList<String>();
		
		list.ensureCapacity(100000000);
		for (int i = 10000000; i < 100000000; i++) {
		list.add("nuovo elemento");
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Tempo = " + (endTime - startTime));
	}

}

package collezioni;
import java.io.Serializable;
import java.util.*;

public class CollezioneAuto {
	public static void main(String[] args) {

//		HashSet set = new HashSet();
//		set.add("Stringa");
//		set.add(new Date());
//		set.add(Math.random());
//		System.out.println(set);
		
		
		// Uso di HashMap: Map NON ordinato (elementi NON duplicati)
		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		map.put("1key", Math.random());
		map.put("2key", "Testo di una stringa");
		map.put("0key", new Date());
		System.out.println(map);
		System.out.println(map.get("2key"));

		// Uso di TreeMap: Map ordinato (elementi NON duplicati)
		TreeMap sortedMap = new TreeMap(map);
		System.out.println(sortedMap);

		
		// Set non ordinato con elementi NON duplicati
		HashSet<String> set = new HashSet<String>();
		set.add("Panda");
		set.add("Croma");
		set.add("Tipo");
		set.add("Panda");
		set.add("Cinquecento");
		System.out.println(set);

		//Uso di un iterator
		Iterator<String> iter = set.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}

		// Set ordinato con elementi NON duplicati
		TreeSet<String> sortedSet = new TreeSet<String>(set);
		System.out.println(sortedSet);

	}

}

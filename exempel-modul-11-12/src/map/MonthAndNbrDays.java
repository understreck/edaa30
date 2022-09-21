package map;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MonthAndNbrDays {
	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("Januari", 31);
		map.put("Februari", 28);
		map.put("Mars", 31);
		map.put("April", 30);
		map.put("Maj", 31);
		map.put("Juni", 30);
		map.put("Juli", 31);
		map.put("Augusti", 31);
		map.put("September", 30);
		map.put("Oktober", 31);
		map.put("November", 30);
		map.put("December", 31);
		
		System.out.println("Antal dagar i april: " + map.get("April"));

		Set<String> keys = map.keySet();
		System.out.println("Mängden med nycklar: " + keys);
		
		Collection<Integer> values = map.values();
		System.out.println("Samlingen med värden: " + values);
		
		Set<Map.Entry<String, Integer>> pairs = map.entrySet();
		System.out.println("Mängden nyckel-värde-par: " + pairs);
		
		System.out.print("Månader med 31 dagar:");
		for (Map.Entry<String, Integer> e : map.entrySet()) {
			if (e.getValue() == 31) {
				System.out.print(" " + e.getKey());
			}
		}
		
		// Metoden keySet ger en vy av nycklarna. Dvs. borttagning av element
		// i vyn påverkar mappen och tvärtom. 
		// Samma sak gäller metoderna values och entrySet.
		Set<String> myKeys = map.keySet();
		System.out.println(myKeys);
		myKeys.clear();
		System.out.println(map);  // Mappen är nu tom
	}
}

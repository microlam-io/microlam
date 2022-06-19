package io.microlam.dynamodb;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Utils {


	public static <K, V> K getFirstKey(Map<K, V> map, V value) {
	    for (Entry<K, V> entry : map.entrySet()) {
	        if (entry.getValue().equals(value)) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}

	public static <T> Set<T> arrayToSet(T... values) {
		return new HashSet<T>(Arrays.asList(values));
	}

}

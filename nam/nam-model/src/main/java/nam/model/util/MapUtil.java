package nam.model.util;

import java.util.Iterator;

import org.aries.common.Map;
import org.aries.common.MapEntry;
import org.eclipse.emf.common.util.EMap;


public class MapUtil {

	public static Map createMap(java.util.Map<Object, Object> details) {
		Map map = new Map();
		Iterator<Object> iterator = details.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			Object value = details.get(key);
			MapEntry entry = new MapEntry();
			entry.setKey(key);
			entry.setValue(value);
			map.getEntries().add(entry );
		}
		return map;
	}

	public static Map createMap(EMap<String, String> details) {
		Map map = new Map();
		Iterator<String> iterator = details.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			Object value = details.get(key);
			MapEntry entry = new MapEntry();
			entry.setKey(key);
			entry.setValue(value);
			map.getEntries().add(entry );
		}
		return map;
	}
	
}

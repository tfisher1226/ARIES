package org.aries.adapter;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.aries.common.Map;
import org.aries.common.MapEntry;


public class MapAdapter extends XmlAdapter<Map, java.util.Map<Object, Object>> {

	@Override
	public Map marshal(java.util.Map<Object, Object> map) throws Exception {
		Map mapType = new Map();
		mapType.getEntries();
		for (Entry<Object, Object> entry : map.entrySet()) {
			MapEntry mapEntry = new MapEntry();
			mapEntry.setKey(entry.getKey());
			mapEntry.setValue(entry.getValue());
			mapType.getEntries().add(mapEntry);
		}
		return mapType;
	}

	@Override
	public java.util.Map<Object, Object> unmarshal(Map mapType) throws Exception {
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		for (MapEntry mapEntry : mapType.getEntries()) {
			hashMap.put(mapEntry.getKey(), mapEntry.getValue());
		}
		return hashMap;
	}

}

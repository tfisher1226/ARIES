package org.aries.service;

import java.util.HashMap;
import java.util.Map;


class TypeMapRepository {

	static final TypeMapRepository INSTANCE = new TypeMapRepository();
	
	private Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();

	
	public Class<?> getTypeMapping(String schemaTypeName) {
		return typeMap.get(schemaTypeName);
	}

	public void addTypeMapping(String schemaTypeName, Class<?> classObject) {
		typeMap.put(schemaTypeName, classObject);
	}

	public void removeTypeMapping(String schemaTypeName) {
		typeMap.remove(schemaTypeName);
	}

}

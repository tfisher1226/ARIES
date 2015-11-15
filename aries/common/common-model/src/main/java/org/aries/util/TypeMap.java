package org.aries.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.ClassUtils;
import org.aries.util.NameUtil;
import org.eclipse.emf.codegen.util.CodeGenUtil;


public class TypeMap {

	public static final TypeMap INSTANCE = new TypeMap();


	private Map<String, TypeMapEntry> typesByTypeNameMap;

	private Map<String, TypeMapEntry> typesByClassNameMap;
	
	private Map<Class<?>, TypeMapEntry> typesByClassMap;

	private Object typeMutex = new Object();

	
	public TypeMap() {
		typesByTypeNameMap = new ConcurrentHashMap<String, TypeMapEntry>();
		typesByClassNameMap = new ConcurrentHashMap<String, TypeMapEntry>();
		typesByClassMap = new ConcurrentHashMap<Class<?>, TypeMapEntry>();
	}

	public void addType(String name, String namespace, Class<?> classObject) {
		TypeMapEntry entry = new TypeMapEntry();
		entry.setName(name);
		entry.setNamespace(namespace);
		entry.setClassObject(classObject);
		addType(entry);
	}

	public void addType(TypeMapEntry entry) {
		synchronized (typeMutex) {
			typesByTypeNameMap.put(entry.getName(), entry);
			typesByClassNameMap.put(entry.getClassObject().getCanonicalName(), entry);
			typesByClassMap.put(entry.getClassObject(), entry);
		}
	}
	
	public String getTypeName(Class<?> classObject) {
		TypeMapEntry entry = getEntry(classObject);
		//Assert.notNull(entry, "Type not found for: "+classObject.getName());
		if (entry != null)
			return entry.getName();
		return null;
	}

	public String getTypeName(String className) {
		TypeMapEntry entry = getEntryByClassName(className);
		//Assert.notNull(entry, "Type not found for: "+className);
		if (entry != null)
			return entry.getName();
		return null;
	}

	public Class<?> getTypeClassByTypeName(String name) {
		TypeMapEntry entry = getEntryByTypeName(name);
		//Assert.notNull(entry, "Class not found for: "+name);
		if (entry != null)
			return entry.getClassObject();
		return null;
	}

	public Class<?> getTypeClassByClassName(String name) {
		String className = NameUtil.getClassName(name);
		if (CodeGenUtil.isJavaDefaultType(className))
			return getClassForDefaultType(name);
		TypeMapEntry entry = getEntryByClassName(name);
		//Assert.notNull(entry, "Class not found for: "+name);
		if (entry != null)
			return entry.getClassObject();
		return null;
	}

	protected Class<?> getClassForDefaultType(String className) {
		try {
			return ClassUtils.getClass(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Class<?>> getClasses() {
		synchronized (typeMutex) {
			Set<Class<?>> set = typesByClassMap.keySet();
			ArrayList<Class<?>> list = new ArrayList<Class<?>>(set);
			return list;
		}
	}
	
//	public Class<?>[] getClasses() {
//		Set<Class<?>> set = typesByClassMap.keySet();
//		Class<?>[] array = set.toArray(new Class<?>[set.size()]);
//		return array;
//	}
	
	public Set<Class<?>> getClassesAsSet() {
		synchronized (typeMutex) {
			Set<Class<?>> set = typesByClassMap.keySet();
			return Collections.unmodifiableSet(set);
		}
	}
	
//	public Class<?>[] getTypeClasses() {
//		Set<Class<?>> set = typesByClassMap.keySet();
//		Class<?>[] array = set.toArray(new Class<?>[set.size()]);
//		return array;
//	}

	protected TypeMapEntry getEntryByTypeName(String typeName) {
		return (TypeMapEntry) typesByTypeNameMap.get(typeName);
	}

	protected TypeMapEntry getEntryByClassName(String className) {
		return (TypeMapEntry) typesByClassNameMap.get(className);
	}

	protected TypeMapEntry getEntry(Class<?> classObject) {
		return (TypeMapEntry) typesByClassMap.get(classObject);
	}

	

	private Map<String, Source> schemasByNameMap = new HashMap<String, Source>();

	private List<String> schemaNames = new ArrayList<String>();

	private Source[] schemaSources;

	private Object schemaMutex = new Object();


	public Source[] getSchemas() {
		synchronized (schemaMutex) {
			if (schemaSources != null)
				return schemaSources;
			Collection<Source> sources = schemasByNameMap.values();
			Source[] array = new Source[sources.size()]; 
			schemaSources = sources.toArray(array);
			return schemaSources;
		}
	}

	public void addSchema(String filename) {
		synchronized (schemaMutex) {
			Source source = getSourceSchema(filename);
			schemasByNameMap.put(filename, source);
			schemaSources = null;
		}
	}
	
	protected Source getSourceSchema(String filename) {
		InputStream stream = getClass().getResourceAsStream(filename);
		StreamSource source = new StreamSource(stream);
		return source;
	}
	
}

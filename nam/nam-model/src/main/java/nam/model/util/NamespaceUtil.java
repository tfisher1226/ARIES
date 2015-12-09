package nam.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Cache;
import nam.model.Element;
import nam.model.Enumeration;
import nam.model.Import;
import nam.model.Information;
import nam.model.Namespace;
import nam.model.Namespaces;
import nam.model.Persistence;
import nam.model.Properties;
import nam.model.Query;
import nam.model.Type;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class NamespaceUtil extends BaseUtil {
	
	public static Object getKey(Namespace namespace) {
		return namespace.getUri();
	}

	public static String getLabel(Namespace namespace) {
		return namespace.getUri();
	}
	
	public static boolean isEmpty(Namespace namespace) {
		if (namespace == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Namespace> namespaceList) {
		if (namespaceList == null  || namespaceList.size() == 0)
			return true;
		Iterator<Namespace> iterator = namespaceList.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			if (!isEmpty(namespace))
				return false;
		}
		return true;
	}
	
	public static String toString(Namespace namespace) {
		if (isEmpty(namespace))
			return "Namespace: [uninitialized] "+namespace.toString();
		String text = namespace.toString();
		return text;
	}
	
	public static String toString(Collection<Namespace> namespaceList) {
		if (isEmpty(namespaceList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Namespace> iterator = namespaceList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Namespace namespace = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(namespace);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Namespace create() {
		Namespace namespace = new Namespace();
		initialize(namespace);
		return namespace;
	}
	
	public static void initialize(Namespace namespace) {
		if (namespace.getExported() == null)
			namespace.setExported(false);
		if (namespace.getImported() == null)
			namespace.setImported(false);
		if (namespace.getIncluded() == null)
			namespace.setIncluded(false);
	}
	
	public static boolean validate(Namespace namespace) {
		if (namespace == null)
			return false;
		Validator validator = Validator.getValidator();
		NamespaceUtil.validate(namespace.getImports());
		ElementUtil.validate(NamespaceUtil.getElements(namespace));
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Namespace> namespaceList) {
		Validator validator = Validator.getValidator();
		Iterator<Namespace> iterator = namespaceList.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			//TODO break or accumulate?
			validate(namespace);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Namespace> namespaceList) {
		Collections.sort(namespaceList, createNamespaceComparator());
	}
	
	public static Collection<Namespace> sortRecords(Collection<Namespace> namespaceCollection) {
		List<Namespace> list = new ArrayList<Namespace>(namespaceCollection);
		Collections.sort(list, createNamespaceComparator());
		return list;
	}
	
	public static Comparator<Namespace> createNamespaceComparator() {
		return new Comparator<Namespace>() {
			public int compare(Namespace namespace1, Namespace namespace2) {
				Object key1 = getKey(namespace1);
				Object key2 = getKey(namespace2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Namespace clone(Namespace namespace) {
		if (namespace == null)
			return null;
		Namespace clone = create();
		clone.setUri(ObjectUtil.clone(namespace.getUri()));
		clone.setName(ObjectUtil.clone(namespace.getName()));
		clone.setLabel(ObjectUtil.clone(namespace.getLabel()));
		clone.setPrefix(ObjectUtil.clone(namespace.getPrefix()));
		clone.setSchema(ObjectUtil.clone(namespace.getSchema()));
		clone.setVersion(ObjectUtil.clone(namespace.getVersion()));
		clone.setFilePath(ObjectUtil.clone(namespace.getFilePath()));
		clone.setDescription(ObjectUtil.clone(namespace.getDescription()));
		clone.getImports().addAll(NamespaceUtil.clone(namespace.getImports()));
		addElements(clone, ElementUtil.clone(NamespaceUtil.getElements(namespace)));
		clone.setImported(ObjectUtil.clone(namespace.getImported()));
		clone.setIncluded(ObjectUtil.clone(namespace.getIncluded()));
		clone.setExported(ObjectUtil.clone(namespace.getExported()));
//		clone.setCreationDate(ObjectUtil.clone(namespace.getCreationDate()));
//		clone.setLastUpdate(ObjectUtil.clone(namespace.getLastUpdate()));
		return clone;
	}
	
	public static List<Namespace> clone(List<Namespace> namespaceList) {
		if (namespaceList == null)
			return null;
		List<Namespace> newList = new ArrayList<Namespace>();
		Iterator<Namespace> iterator = namespaceList.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			Namespace clone = clone(namespace);
			newList.add(clone);
		}
		return newList;
	}


	public static String getPackageName(Namespace namespace) {
		return getPackageName(namespace.getUri());
	}

	public static String getPackageName(String namespace) {
		return NameUtil.getPackageNameFromNamespace(namespace);
	}

	public static Properties getProperties(Namespace namespace) {
		Properties properties = namespace.getProperties();
		if (properties == null) {
			properties = new Properties();
			namespace.setProperties(properties);
		}
		return properties;
	}

	public static String getProperty(Namespace namespace, String name) {
		Properties properties = NamespaceUtil.getProperties(namespace);
		return PropertyUtil.getValue(properties, name);
	}

	public static void setProperty(Namespace namespace, String name) {
		setProperty(namespace, name, true);
	}

	public static void setProperty(List<Namespace> namespaces, String name) {
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			setProperty(namespace, name);
		}
	}
	
	public static void unsetProperty(Namespace namespace, String name) {
		setProperty(namespace, name, false);
	}
	
	public static void setProperty(Namespace namespace, String name, Object value) {
		Properties properties = NamespaceUtil.getProperties(namespace);
		PropertyUtil.setProperty(properties, name, value);
	}

	public static List<Namespace> getFiles(Namespaces namespaces) {
		return ListUtil.getObjectList(namespaces.getFilesAndNamespaces(), String.class);
	}

	public static List<Namespace> getNamespaces(Namespaces namespaces) {
		return ListUtil.getObjectList(namespaces.getFilesAndNamespaces(), Namespace.class);
	}

	public static List<Namespace> getImports(Namespace namespace) {
		List<Namespace> imports = namespace.getImports();
		return imports;
	}

	public static Collection<Namespace> getImportedNamespaces(Information model) {
		return getImportedNamespaces(model.getImports());
	}
	
	public static Collection<Namespace> getImportedNamespaces(Persistence model) {
		return getImportedNamespaces(model.getImports());
	}

	public static Collection<Namespace> getImportedNamespaces(List<Import> imports) {
		Set<Namespace> importedNamespaces = new HashSet<Namespace>();
		Iterator<Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import container = iterator.next();
			Object object = container.getObject();
			if (object instanceof Information) {
				Information block = (Information) object;
				List<Namespace> namespaces = block.getNamespaces();
				importedNamespaces.addAll(namespaces);
			}
		}
		return importedNamespaces;
	}
	
	public static Map<String, Namespace> getImportedNamespaces(Namespace namespace) {
		Map<String, Namespace> map = new HashMap<String, Namespace>();
		String uri = namespace.getUri();
		if (!map.containsKey(uri))
			map.put(uri, namespace);
		List<Namespace> imports = namespace.getImports();
		Iterator<Namespace> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Namespace importedNamespace = iterator.next();
			System.out.println(">>>"+importedNamespace.getUri());
			map.putAll(getImportedNamespaces(importedNamespace));
		}
		return map;
	}
	

//	public static List<Type> getTypes(Namespace namespace) {
//		List<Type> types = namespace.getTypesAndEnumerationsAndElements();
//		return types;
//	}
	
//	public static List<Type> getTypes(Namespace namespace) {
//		List<Type> types = new ArrayList<Type>();
//		List<Type> objects = namespace.getTypesAndEnumerationsAndElements();
//		Iterator<Type> iterator = objects.iterator();
//		while (iterator.hasNext()) {
//			Type type = iterator.next();
//			if (type instanceof Element)
//				continue;
//			types.add(type);
//		}
//		return types;
//	}
//	
//	public static List<Element> getElements(Namespace namespace) {
//		List<Element> elements = new ArrayList<Element>();
//		List<Type> objects = namespace.getTypesAndEnumerationsAndElements();
//		Iterator<Type> iterator = objects.iterator();
//		while (iterator.hasNext()) {
//			Type type = iterator.next();
//			if (type instanceof Element)
//				elements.add((Element) type);
//		}
//		return elements;
//	}
//
//	public static List<Enumeration> getEnumerations(Namespace namespace) {
//		List<Enumeration> enumerations = new ArrayList<Enumeration>();
//		List<Type> objects = namespace.getTypesAndEnumerationsAndElements();
//		Iterator<Type> iterator = objects.iterator();
//		while (iterator.hasNext()) {
//			Type type = iterator.next();
//			if (type instanceof Enumeration)
//				enumerations.add((Enumeration) type);
//		}
//		return enumerations;
//	}

	
//	public static List<Import> getImports(Namespace namespace) {
//		return getObjectList(namespace, Import.class);
//	}

	public static List<Type> getTypes(Namespace namespace) {
		return getObjectList(namespace, Type.class);
	}

	public static List<Type> getTypes(Collection<Namespace> namespaces) {
		List<Type> list = new ArrayList<Type>();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			list.addAll(getTypes(namespace));
		}
		return list;
	}

	public static List<Enumeration> getEnumerations(Namespace namespace) {
		return getObjectList(namespace, Enumeration.class);
	}
	
	public static List<Element> getElements(Namespace namespace) {
		return getObjectList(namespace, Element.class);
	}
	
	public static List<Element> getElements(Namespace namespace, boolean recurse) {
		List<Element> elements = new ArrayList<Element>();
		elements.addAll(getElements(namespace));
		if (recurse) {
			List<Namespace> imports = namespace.getImports();
			Iterator<Namespace> iterator = imports.iterator();
			while (iterator.hasNext()) {
				Namespace importedNamespace = iterator.next();
				elements.addAll(getElements(importedNamespace, false));
			}
		}
		return elements;
	}

	public static List<Element> getElements(Collection<Namespace> namespaces) {
		List<Element> elements = new ArrayList<Element>();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			elements.addAll(getElements(namespace));
		}
		return elements;
	}
	
	public static Map<String, Type> getTypeMap(Namespace namespace) {
		Map<String, Type> typesMap = new HashMap<String, Type>();
		List<Type> namespaceTypes = getTypes(namespace);
		Iterator<Type> iterator = namespaceTypes.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			typesMap.put(type.getType(), type);
		}
		return typesMap;
	}
	
	public static Map<String, Type> getTypeMap(Collection<Namespace> namespaces) {
		Map<String, Type> typesMap = new HashMap<String, Type>();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			typesMap.putAll(getTypeMap(namespace));
		}
		return typesMap;
	}
	
	public static void addType(Namespace owner, Type type) {
		List<Serializable> list = owner.getTypesAndEnumerationsAndElements();
		synchronized (list) {
			if (!list.contains(type)) {
				list.add(type);
			}
		}
	}

	public static boolean removeType(Namespace owner, Type type) {
		List<Serializable> list = owner.getTypesAndEnumerationsAndElements();
		synchronized (list) {
			if (list.contains(type)) {
				return list.remove(type);
			}
		}
		return false;
	}

	public static void addElement(Namespace namespace, Element element) {
		List<Element> elements = getObjectList(namespace, Element.class);
		if (!containsElement(elements, element))
			namespace.getTypesAndEnumerationsAndElements().add(element);
	}

	public static void addElements(Namespace namespace, Collection<Element> elements) {
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			addElement(namespace, element);
		}
	}
	
	public static boolean removeElement(Namespace namespace, Element element) {
		List<Element> elements = getObjectList(namespace, Element.class);
		if (containsElement(elements, element))
			return namespace.getTypesAndEnumerationsAndElements().remove(element);
		return false;
	}
	
	public static void addEnumeration(Namespace namespace, Enumeration enumeration) {
		List<Enumeration> enumerations = getObjectList(namespace, Enumeration.class);
		if (!containsEnumeration(enumerations, enumeration))
			namespace.getTypesAndEnumerationsAndElements().add(enumeration);
	}
	
	public static void addTypes(Namespace namespace, Collection<Type> types) {
		Iterator<Type> iterator = types.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			if (type instanceof Enumeration)
				addEnumeration(namespace, (Enumeration) type);
			if (type instanceof Element)
				addElement(namespace, (Element) type);
		}
	}

	public static void removeTypes(Namespace namespace, Collection<Type> types) {
	}

	public static boolean containsElement(List<Element> elements, Element element) {
		if (elements.contains(element))
			return true;
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element importedElement = iterator.next();
			if (importedElement.getType().equals(element.getType()))
				return true;
		}
		return false;
	}
	
	public static boolean containsEnumeration(List<Enumeration> enumerations, Enumeration enumeration) {
		if (enumerations.contains(enumeration))
			return true;
		Iterator<Enumeration> iterator = enumerations.iterator();
		while (iterator.hasNext()) {
			Enumeration importedEnumeration = iterator.next();
			if (importedEnumeration.getType().equals(enumeration.getType()))
				return true;
		}
		return false;
	}
	
	
	public static List<Cache> getCaches(Namespace namespace) {
		return getObjectList(namespace, Cache.class);
	}
	
	public static List<Query> getQueries(Namespace namespace) {
		return getObjectList(namespace, Query.class);
	}

	public static Collection<Query> getQueriesForElement(Namespace namespace, Element element) {
		List<Query> elementQueries = new ArrayList<Query>();
		List<Query> namespaceQueries = getQueries(namespace);
		Iterator<Query> iterator = namespaceQueries.iterator();
		while (iterator.hasNext()) {
			Query query = iterator.next();
			String fromType = query.getFrom();
			if (fromType.equals(element.getType()))
				elementQueries.add(query);
		}
		return elementQueries;
	}
	
	public static <T> T getObjectByName(Namespace namespace, Class<?> objectClass, String name) {
		List<Object> objectList = getObjectList(namespace, objectClass);
		return ListUtil.getObjectByValue(objectList, objectClass, "getName", name);
	}
	
	public static <T> List<T> getObjectList(Namespace namespace, Class<?> objectClass) {
		List<Serializable> objects = namespace.getTypesAndEnumerationsAndElements();
		return ListUtil.getObjectList(objects, objectClass);
	}
	
	public static <T> T getObject(Namespace namespace, Class<?> objectClass) {
		List<Serializable> objects = namespace.getTypesAndEnumerationsAndElements();
		return ListUtil.getObject(objects, objectClass);
	}

	public static void setFilePath(Collection<Namespace> namespaces, String filePath) {
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			namespace.setFilePath(filePath);
		}
	}
	
	public static boolean isImported(Namespace namespace) {
		Boolean imported = namespace.getImported();
		return imported != null && imported;
	}

	public static boolean isIncluded(Namespace namespace) {
		Boolean included = namespace.getIncluded();
		return included != null && included;
	}

	public static void setImported(Collection<Namespace> namespaces, boolean imported) {
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			namespace.setImported(imported);
		}
	}

	public static void setIncluded(Collection<Namespace> namespaces, boolean included) {
//		if (included)
//			System.out.println();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			if (included)
				namespace.setIncluded(included);
			//TODO for now ignore "unsetting" this
		}
	}
	
	public static void addImportedNamespaces(Namespace namespace, Collection<Namespace> importedNamespaces) {
		Iterator<Namespace> iterator = importedNamespaces.iterator();
		while (iterator.hasNext()) {
			Namespace importedNamespace = iterator.next();
			addImportedNamespace(namespace, importedNamespace);
		}
	}

	public static void addImportedNamespace(Namespace namespace, Namespace importedNamespace) {
		if (!equals(namespace, importedNamespace)) {
			
//			if (namespace.getUri().equals("http://admin") && importedNamespace.getUri().equals("http://bookshop2"))
//				System.out.println();
			
			List<Namespace> imports = namespace.getImports();
			if (!imports.contains(importedNamespace)) {
				imports.add(importedNamespace);
				//System.out.println("#### Added imported namespace: "+namespace.getUri()+": "+importedNamespace.getUri());
			}
		}
	}

	public static String getGroupIdFromNamespace(Namespace namespace) {
		return getGroupIdFromNamespace(namespace.getUri());
	}
	
	public static String getGroupIdFromNamespace(String namespace) {
		Assert.notNull(namespace, "Namespace must be specified");
		String packageName = NameUtil.getPackageNameFromNamespace(namespace);
		String localPart = NameUtil.getSimpleName(packageName);
		String groupId = packageName.replace("." + localPart, "");
		return groupId;
	}

	public static String getArtifactIdFromNamespace(Namespace namespace) {
		return getArtifactIdFromNamespace(namespace.getUri());
	}
	
	public static String getArtifactIdFromNamespace(String namespace) {
		Assert.notNull(namespace, "Namespace must be specified");
		String packageName = NameUtil.getPackageNameFromNamespace(namespace);
		String localPart = NameUtil.getSimpleName(packageName);
		String baseName = localPart.replace("_", "-");
		String artifaceId = baseName + "-model";
		return artifaceId;
	}

	public static String getVersionFromNamespace(Namespace namespace) {
		Assert.notNull(namespace, "Namespace must be specified");
		String version = namespace.getVersion();
		if (StringUtils.isEmpty(version))
			version = "0.0.1-SNAPSHOT";
		return version;
	}

	public static boolean equals(Namespace namespace1, Namespace namespace2) {
		Assert.notNull(namespace1, "First namespace must be specified");
		Assert.notNull(namespace2, "Second namespace must be specified");
		return namespace1.getUri().equals(namespace2.getUri());
	}

	public static Namespace getNamespace(Collection<Namespace> namespaces, String uri) {
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			if (namespace.getUri().equalsIgnoreCase(uri)) {
				return namespace;
			}
		}
		return null;
	}

	public static boolean isNamespaceExists(List<Namespace> namespaces, Namespace namespace) {
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace1 = iterator.next();
			if (equals(namespace1, namespace)) {
				return true;
			}
		}
		return false;
	}

	public static void addNamespace(List<Namespace> targetList, Namespace namespace) {
		if (!isNamespaceExists(targetList, namespace)) {
			targetList.add(namespace);
		}
	}

	public static void addNamespaces(List<Namespace> targetList, Collection<Namespace> namespaces) {
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			addNamespace(targetList, namespace);
		}
	}

	public static void addImportedNamespaces(List<Namespace> targetList, Namespace namespace) {
		addNamespaces(targetList, getImportedNamespaces(namespace).values());
	}

	public static String getXSDFileName(Namespace namespace) {
		return getFileName(namespace, "xsd");
	}
	
	public static String getARIESFileName(Namespace namespace) {
		return getFileName(namespace, "aries");
	}
	
	public static String getFileName(Namespace namespace, String extension) {
		String uri = namespace.getUri();
		String groupId = getGroupIdFromNamespace(uri);
		//String artifactId = getArtifactIdFromNamespace(uri);
		String version = getVersionFromNamespace(namespace);
		if (version.endsWith("-SNAPSHOT"))
			version = version.replace("-SNAPSHOT", "");
		if (groupId.startsWith("org.") || groupId.startsWith("com.") || groupId.startsWith("net.") || groupId.startsWith("edu."))
			groupId = groupId.substring(4);
		String packageName = NameUtil.getPackageNameFromNamespace(uri);
		String localPart = NameUtil.getSimpleName(packageName);
		String fileName = groupId + "-" + localPart + "-" + version + "." + extension;
		return fileName;
	}

	public static void sortNamespaces(List<Namespace> namespaces) {
		Collections.sort(namespaces, new Comparator<Namespace>() {
			public int compare(Namespace namespace1, Namespace namespace2) {
				return namespace1.getUri().compareTo(namespace2.getUri());
			}
		});
	}

	public static void mergeNamespaces(Namespace namespace1, Namespace namespace2) {
		mergeImports(namespace1.getImports(), namespace2.getImports());
		Properties properties1 = namespace1.getProperties();
		Properties properties2 = namespace2.getProperties();
		if (properties1 != null && properties2 != null)
			PropertyUtil.mergeProperties(properties1, properties2);
		mergeTypes(namespace1, namespace2);
	}

	public static void mergeTypes(Namespace namespace1, Namespace namespace2) {
		List<Serializable> types1 = namespace1.getTypesAndEnumerationsAndElements();
		List<Serializable> types2 = namespace2.getTypesAndEnumerationsAndElements();
		mergeTypes(types1, types2);
	}
	
	public static void mergeTypes(List<Serializable> types1, List<Serializable> types2) {
		Iterator<Serializable> iterator2 = types2.iterator();
		while (iterator2.hasNext()) {
			Type type2 = (Type) iterator2.next();
			if (!types1.contains(type2)) {
				types1.add(type2);
			}
		}
	}

	public static void mergeImports(List<Namespace> namespaces1, List<Namespace> namespaces2) {
		Iterator<Namespace> iterator2 = namespaces2.iterator();
		while (iterator2.hasNext()) {
			Namespace namespace2 = iterator2.next();
			if (!namespaces1.contains(namespace2)) {
				namespaces1.add(namespace2);
			}
		}
	}

	
}

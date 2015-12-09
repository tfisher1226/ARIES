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

import nam.model.Element;
import nam.model.Elements;
import nam.model.Import;
import nam.model.Include;
import nam.model.Namespace;
import nam.model.Properties;
import nam.model.Property;
import nam.model.Query;
import nam.model.Transacted;
import nam.model.TransactionScope;
import nam.model.TransactionUsage;
import nam.model.Unit;
import nam.model.Units;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.Assert;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.Validator;


public class UnitUtil extends BaseUtil {

	public static String getKey(Unit unit) {
		return unit.getNamespace() + "/" + unit.getName();
	}
	
	public static String getLabel(Unit unit) {
		return unit.getName();
	}
	
	public static boolean getLabel(Collection<Unit> unitList) {
		if (unitList == null  || unitList.size() == 0)
			return true;
		Iterator<Unit> iterator = unitList.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			if (!isEmpty(unit))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Unit unit) {
		if (unit == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Unit> unitList) {
		if (unitList == null  || unitList.size() == 0)
			return true;
		Iterator<Unit> iterator = unitList.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			if (!isEmpty(unit))
				return false;
		}
		return true;
	}
	
	public static String toString(Unit unit) {
		if (isEmpty(unit))
			return "Unit: [uninitialized] "+unit.toString();
		String text = unit.toString();
		return text;
	}
	
	public static String toString(Collection<Unit> unitList) {
		if (isEmpty(unitList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Unit> iterator = unitList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Unit unit = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(unit);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Unit create() {
		Unit unit = new Unit();
		initialize(unit);
		return unit;
	}
	
	public static void initialize(Unit unit) {
		//nothing for now
	}
	
	public static boolean validate(Unit unit) {
		if (unit == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Unit> unitList) {
		Validator validator = Validator.getValidator();
		Iterator<Unit> iterator = unitList.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			//TODO break or accumulate?
			validate(unit);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Unit> unitList) {
		Collections.sort(unitList, createUnitComparator());
	}
	
	public static Collection<Unit> sortRecords(Collection<Unit> unitCollection) {
		List<Unit> list = new ArrayList<Unit>(unitCollection);
		Collections.sort(list, createUnitComparator());
		return list;
	}
	
	public static Comparator<Unit> createUnitComparator() {
		return new Comparator<Unit>() {
			public int compare(Unit unit1, Unit unit2) {
				Object key1 = getKey(unit1);
				Object key2 = getKey(unit2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Unit clone(Unit unit) {
		if (unit == null)
			return null;
		Unit clone = create();
		return clone;
	}
	
	public static List<Unit> clone(List<Unit> unitList) {
		if (unitList == null)
			return null;
		List<Unit> newList = new ArrayList<Unit>();
		Iterator<Unit> iterator = unitList.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			Unit clone = clone(unit);
			newList.add(clone);
		}
		return newList;
	}


//	public static boolean isUnitExists(List<Unit> units, Unit unit) {
//		Iterator<Unit> iterator = units.iterator();
//		while (iterator.hasNext()) {
//			Unit unit1 = iterator.next();
//			if (equals(unit1, unit)) {
//				return true;
//			}
//		}
//		return false;
//	}

//	public static void addUnit(List<Unit> units, Unit unit) {
//		if (!isUnitExists(units, unit)) {
//			units.add(unit);
//		}
//	}

	public static String getUnitName(Unit unit) {
		String unitName = NameUtil.uncapName(unit.getName());
		return unitName;
	}

	public static String getPackageName(Unit unit) {
		String namespaceUri = unit.getNamespace().getUri();
		return NameUtil.getPackageNameFromNamespace(namespaceUri);
	}

	public static Set<String> getIncludedNamespaces(Unit unit) {
		Set<String> includedNamespaces = new HashSet<String>();
		List<Include> includeList = getObjectList(unit, Include.class);
		Iterator<Include> iterator = includeList.iterator();
		while (iterator.hasNext()) {
			Include include = (Include) iterator.next();
			includedNamespaces.add(include.getNamespace());
		}
		return includedNamespaces;
	}

	public static Set<String> getImportedNamespaces(Unit unit) {
		Set<String> importedNamespaces = new HashSet<String>();
		List<Import> importList = getObjectList(unit, Import.class);
		Iterator<Import> iterator = importList.iterator();
		while (iterator.hasNext()) {
			Import include = (Import) iterator.next();
			importedNamespaces.add(include.getNamespace());
		}
		return importedNamespaces;
	}

	public static <T> List<T> getObjectList(Unit unit, Class<?> objectClass) {
		List<Serializable> objects = unit.getIncludesAndImports();
		return ListUtil.getObjectList(objects, objectClass);
	}
	
	public static List<Element> getElements(Unit unit) {
//		Elements elements = unit.getElements();
//		if (elements == null) {
//			elements = new Elements();
//			unit.setElements(elements);
//		}
		List<Element> list = ElementUtil.getElements(unit.getElements());
		return list;
	}

	public static List<Element> getAllElements(Unit unit) {
		Set<Element> elements = new HashSet<Element>();
		elements.addAll(getElementsFromNamespace(unit, true));
		elements.addAll(ElementUtil.getElements(unit.getElements()));
		List<Element> elementsBasedOnType = filterElementsBasedOnType(elements);
		ElementUtil.sortTypesByQualifiedName(elementsBasedOnType);
		return elementsBasedOnType;
	}

	public static List<Element> getElementsFromNamespace(Unit unit) {
		List<Element> elements = getElementsFromNamespace(unit, false);
		return elements;
	}
	
	public static List<Element> getElementsFromNamespace(Unit unit, boolean recurse) {
		List<Element> elements = NamespaceUtil.getElements(unit.getNamespace(), recurse);
		return elements;
	}
	
	public static void addElement(Unit unit, Element element) {
		if (unit.getElements() == null)
			unit.setElements(new Elements());
		ElementUtil.addElement(unit.getElements(), element);
		//String unitNamespace = unit.getNamespace().getUri();
		//String elementNamespace = TypeUtil.getNamespace(element.getType());
	}

	public static void addElements(Unit unit, List<Element> elements) {
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			addElement(unit, element);
		}
	}

	public static void addElementsFromNamespace(Unit unit, Namespace namespace) {
		addElementsFromNamespace(unit, namespace, false);
	}
	
	public static void addElementsFromNamespace(Unit unit, Namespace namespace, boolean recurse) {
		List<Element> elements = NamespaceUtil.getElements(namespace, recurse);
		addElements(unit, elements);
	}

	public static boolean containsElement(Unit unit, Element element) {
		return containsElementByType(unit, element.getType());
	}

	public static boolean containsElementByType(Unit unit, String elementType) {
		Element element = getElementByType(unit, elementType);
		return element != null;
	}

	public static boolean containsElementByName(Unit unit, String elementName) {
		Element element = getElementByName(unit, elementName);
		return element != null;
	}

	public static Element getElementByType(Unit unit, String elementType) {
		List<Element> elements = ElementUtil.getElements(unit.getElements());
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (element.getType().equals(elementType))
				return element;
		}
		return null;
	}

	public static Element getElementByName(Unit unit, String elementName) {
		List<Element> elements = ElementUtil.getElements(unit.getElements());
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (element.getName().equalsIgnoreCase(elementName))
				return element;
		}
		return null;
	}

	
	public static void addQuery(Unit unit, Query query) {
		if (!containsQuery(unit, query))
			unit.getQueries().add(query);
	}

	public static void addQueries(Unit unit, List<Query> queries) {
		Iterator<Query> iterator = queries.iterator();
		while (iterator.hasNext()) {
			Query query = iterator.next();
			addQuery(unit, query);
		}
	}
	
	public static boolean containsQuery(Unit unit, Query query) {
		List<Query> queries = unit.getQueries();
		Iterator<Query> iterator = queries.iterator();
		while (iterator.hasNext()) {
			Query existingQuery = iterator.next();
			if (existingQuery.getName().equals(query.getName()))
				return true;
		}
		return false;
	}
	
	
	public static List<Property> getProperties(Unit unit) {
		Properties properties = unit.getProperties();
		if (properties == null) {
			properties = new Properties();
			unit.setProperties(properties);
		}
		List<Property> list = properties.getProperties();
		return list;
	}

//	public static List<Query> getQueries(Unit unit) {
//		return getObjectList(unit, Query.class);
//	}

//	public static <T> List<T> getObjectList(Unit unit, Class<?> objectClass) {
//		List<Serializable> objects = unit.getQueries();
//		return ListUtil.getObjectList(objects, objectClass);
//	}
//	
//	public static <T> T getObject(Unit unit, Class<?> objectClass) {
//		List<Serializable> objects = unit.getQueries();
//		return ListUtil.getObject(objects, objectClass);
//	}
	

//	public static Namespaces getNamespaces(Unit unit) {
//		Namespaces namespaces = unit.getNamespaces();
//		if (namespaces == null) {
//			namespaces = new Namespaces();
//			unit.setNamespaces(namespaces);
//		}
//		return namespaces;
//	}

//	public static List<Entity> getEntities(Unit persistence) {
//		if (persistence == null)
//			return new ArrayList<Entity>();
//		Entities entities = persistence.getEntities();
//		if (entities == null)
//			entities = new Entities();
//		return entities.getEntities();
//	}

	public static Set<Element> getFunctionalElements(Unit unit) {
		Set<Element> elements = new HashSet<Element>();
		elements.addAll(NamespaceUtil.getElements(unit.getNamespace()));
		elements.addAll(ElementUtil.getElements(unit.getElements()));
		return elements;
	}
	
	//gets distinct sorted functional element list according to type
	public static List<Element> getFunctionalElementsBasedOnType(Unit unit) {
		return filterElementsBasedOnType(getFunctionalElements(unit));
	}
	
	//gets distinct sorted functional element list according to name
	public static List<Element> getFunctionalElementsBasedOnName(Unit unit) {
		List<Element> elements = filterElementsBasedOnName(getFunctionalElements(unit));
		ElementUtil.sortTypesByQualifiedName(elements);
		return elements;
	}

	protected static List<Element> filterElementsBasedOnType(Set<Element> elements) {
		List<Element> filteredElements = new ArrayList<Element>();
		Set<String> elementsProcessedSoFar = new HashSet<String>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			String elementType = element.getType();
			if (!elementsProcessedSoFar.contains(elementType)) {
				if (ElementUtil.isAbstract(element))
					continue;
				if (ElementUtil.isTransient(element))
					continue;
				filteredElements.add(element);
				elementsProcessedSoFar.add(elementType);
			}
		}
		Collections.sort(filteredElements, new Comparator<Element>() {
			public int compare(Element element1, Element element2) {
				return element1.getType().compareTo(element2.getType());
			}
		});
		return filteredElements;
	}

	protected static List<Element> filterElementsBasedOnName(Set<Element> elements) {
		List<Element> filteredElements = new ArrayList<Element>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (ElementUtil.isAbstract(element))
				continue;
			if (ElementUtil.isTransient(element))
				continue;
			filteredElements.add(element);
		}
		Collections.sort(filteredElements, new Comparator<Element>() {
			public int compare(Element element1, Element element2) {
				return element1.getName().compareTo(element2.getName());
			}
		});
		return filteredElements;
	}

//	public static String getPackageName(Unit unit) {
//		List<Namespace> namespaceList = getNamespaces(unit);
//		Namespace namespace = namespaceList.get(namespaceList.size() - 1);
//		String packageName = NameUtil.getPackageFromNamespace(namespace.getUri());
//		return packageName;
//	}


	public static Map<String, Unit> createUnitMap(Units persistenceUnits) {
		if (persistenceUnits == null)
			return new HashMap<String, Unit>();
		return createUnitMap(persistenceUnits.getUnits());
	}

	public static Map<String, Unit> createUnitMap(Collection<Unit> persistenceUnits) {
		Map<String, Unit> map = new HashMap<String, Unit>();
		Iterator<Unit> iterator = persistenceUnits.iterator();
		while (iterator.hasNext()) {
			Unit persistenceUnit = (Unit) iterator.next();
			String name = persistenceUnit.getName();
			if (name != null)
				map.put(name, persistenceUnit);
		}
		return map;
	}
	
	
	public static void setProperty(Unit unit, String name, String value) {
		if (name.equals("transacted")) {
			Transacted transacted = getTransacted(unit);
			if (value.equals("mandatory")) {
				transacted.setUse(TransactionUsage.MANDATORY);
			} else if (value.equals("required")) {
				transacted.setUse(TransactionUsage.REQUIRED);
			} else if (value.equals("requiresNew")) {
				transacted.setUse(TransactionUsage.REQUIRES_NEW);
			} else if (value.equals("supported")) {
				transacted.setUse(TransactionUsage.SUPPORTED);
			}
		}
			
		if (name.equals("scope")) {
			Transacted transacted = getTransacted(unit);
			if (value.equals("event")) {
				transacted.setScope(TransactionScope.EVENT);
			} else if (value.equals("method")) {
				transacted.setScope(TransactionScope.METHOD);
			} else if (value.equals("thread")) {
				transacted.setScope(TransactionScope.THREAD);
			} else if (value.equals("process")) {
				transacted.setScope(TransactionScope.PROCESS);
			} else if (value.equals("workflow")) {
				transacted.setScope(TransactionScope.CONVERSATION);
			}
		}
	}
	
	public static Transacted getTransacted(Unit unit) {
		Transacted transacted = unit.getTransacted();
		if (transacted == null) {
			transacted = new Transacted();
			unit.setTransacted(transacted);
		}
		return transacted;
	}
	
	public static boolean equals(Unit unit1, Unit unit2) {
		Assert.notNull(unit1, "First unit must be specified");
		Assert.notNull(unit2, "Second unit must be specified");
		if (!unit1.getName().equals(unit2.getName()))
			return false;
		if (!NamespaceUtil.equals(unit1.getNamespace(), unit2.getNamespace()))
			return false;
		//if (!AdapterUtil.equals(unit1.getAdapter(), unit2.getAdapter()))
		//	return false;
		return true;
	}
		
}

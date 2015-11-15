package nam.model.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Annotation;
import nam.model.Attribute;
import nam.model.Element;
import nam.model.Elements;
import nam.model.Enumeration;
import nam.model.Field;
import nam.model.Grouping;
import nam.model.Id;
import nam.model.Include;
import nam.model.Information;
import nam.model.Item;
import nam.model.Literal;
import nam.model.Namespace;
import nam.model.Parameter;
import nam.model.Project;
import nam.model.Reference;
import nam.model.Type;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.Validator;


public class ElementUtil extends BaseUtil {

	public static Object getKey(Element element) {
		return element.getType()+":"+element.getName();
	}

	public static String getLabel(Element element) {
		return NameUtil.capName(element.getName());
	}
	
	public static boolean isEmpty(Element element) {
		if (element == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Element> elementList) {
		if (elementList == null  || elementList.size() == 0)
			return true;
		Iterator<Element> iterator = elementList.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!isEmpty(element))
				return false;
		}
		return true;
	}
	
	public static String toString(Element element) {
		if (isEmpty(element))
			return "Element: [uninitialized] "+element.toString();
		String text = element.toString();
		return text;
	}
	
	public static String toString(Collection<Element> elementList) {
		if (isEmpty(elementList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Element> iterator = elementList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Element element = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(element);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Element create() {
		Element element = new Element();
		initialize(element);
		return element;
	}
	
	public static void initialize(Element element) {
		if (element.getAbstract() == null)
			element.setAbstract(false);
		if (element.getRoot() == null)
			element.setRoot(false);
		if (element.getTransient() == null)
			element.setTransient(false);
	}
	
	public static boolean validate(Element element) {
		if (element == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Element> elementList) {
		Validator validator = Validator.getValidator();
		Iterator<Element> iterator = elementList.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			//TODO break or accumulate?
			validate(element);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Element> elementList) {
		Collections.sort(elementList, createElementComparator());
	}
	
	public static Collection<Element> sortRecords(Collection<Element> elementCollection) {
		List<Element> list = new ArrayList<Element>(elementCollection);
		Collections.sort(list, createElementComparator());
		return list;
	}
	
	public static Comparator<Element> createElementComparator() {
		return new Comparator<Element>() {
			public int compare(Element element1, Element element2) {
				String value1 = element1.getType() + element1.getName();
				String value2 = element2.getType() + element2.getName();
				int status = value1.compareTo(value2);
				return status;
			}
		};
	}
	
	public static Element clone(Element element) {
		if (element == null)
			return null;
		Element clone = new Element();
		clone.setName(element.getName());
		clone.setType(element.getType());
		clone.setLabel(element.getName());
		clone.setRoot(element.getRoot());
		clone.setPublic(element.getPublic());
		clone.setAbstract(element.getAbstract());
		clone.setTransient(element.getTransient());
		clone.setExtends(element.getExtends());
		clone.setCached(element.getCached());
//		clone.setCreationDate(ObjectUtil.clone(element.getCreationDate()));
//		clone.setLastUpdate(ObjectUtil.clone(element.getLastUpdate()));

		List<Field> fields = getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			Field newField = FieldUtil.cloneField(field);
			addField(clone, newField);
		}
		return clone;
	}

	public static Collection<Element> clone(Collection<Element> elements) {
		Collection<Element> clone = new ArrayList<Element>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			clone.add(clone(element));
		}
		return clone;
	}


	public static boolean isElement(Type type) {
		return type instanceof Element;
	}
	
	public static boolean isEnumeration(Type type) {
		return type instanceof Enumeration;
	}
	
	public static boolean isRoot(Type type) {
		if (!isElement(type))
			return false;
		Element element = (Element) type;
		Boolean value = element.getRoot();
		return value != null && value == true;
	}
	
	public static boolean isAbstract(Type type) {
		if (!isElement(type))
			return false;
		Element element = (Element) type;
		Boolean value = element.getAbstract();
		return value != null && value == true;
	}

	public static boolean isTransient(Type type) {
		if (!isElement(type))
			return false;
		Element element = (Element) type;
		Boolean value = element.getTransient();
		return value != null && value == true;
	}

	public static boolean isPersisted(Type type) {
		if (!isElement(type))
			return false;
		Element element = (Element) type;
		Boolean value = element.getPersisted();
		return value != null && value == true;
	}

	public static boolean isHierarchical(Type type) {
		if (!isElement(type))
			return false;
		Element element = (Element) type;
		Boolean value = element.getHierarchical();
		return value != null && value == true;
	}
	
	public static boolean isSortable(Type type) {
		if (!isElement(type))
			return false;
		Element element = (Element) type;
		Boolean value = element.getSortable();
		return value != null && value == true;
	}
	
	public static boolean isSelfReferencing(Element element) {
		List<Field> fields = getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (isSelfReferencing(element, field))
				return true;
		}
		return false;
	}

	public static boolean isSelfReferencing(Element element, Field field) {
		String elementType = element.getType();
		String fieldType = field.getType();
		return fieldType.equals(elementType);
	}
	
	public static boolean isTwoWaySelfReferencing(Element element) {
		List<Field> fields = getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (isTwoWaySelfReferencing(element, field))
				return true;
		}
		return false;
	}

	public static boolean isTwoWaySelfReferencing(Element element, Field field) {
		String elementType = element.getType();
		String fieldType = field.getType();
		if (!fieldType.equals(elementType))
			return false;
		if (!FieldUtil.isInverse(field))
			return false;
		return true;
	}
	
	//TODO base these differences off of a specific "type" field - not name
	public static boolean isUserDefined(Element element) {
		String elementName = element.getName();
		
		//special cases - (for now)
		if (elementName.endsWith("EmailMessage"))
			return true;
		
		if (elementName.endsWith("Key"))
			return false;
		if (elementName.endsWith("Message"))
			return false;
		//if (elementName.endsWith("Event"))
		//	return false;
		if (elementName.endsWith("Exception"))
			return false;
		if (elementName.endsWith("Criteria"))
			return false;
		return true;
	}
	
	//TODO make this come from type (not name)
	public static boolean isKeyElement(Type element) {
		return element.getName().endsWith("Key");
	}

	//TODO make this come from type (not name)
	public static boolean isCriteriaElement(Type element) {
		return element.getName().endsWith("Criteria");
	}

	//TODO make this come from type (not name)
	public static boolean isMessageElement(Type element) {
		return element.getName().endsWith("Message") && !element.getName().equalsIgnoreCase("EmailMessage");
	}

	//TODO make this come from type (not name)
	public static boolean isEventElement(Type element) {
		return element.getName().endsWith("Event") && false;
	}

	//TODO make this come from type (not name)
	public static boolean isExceptionElement(Type element) {
		return element.getName().endsWith("Exception");
	}

	public static boolean isComparableElement(Element element) {
//		if (element.getName().equals("Sequence"))
//			System.out.println();
		List<Attribute> attributes = getSingleValueAttributes(element);
		if (attributes.size() == 0)
			return false;
		Collection<Field> keyFields = ElementUtil.getKeyFields(element);
		if (keyFields.isEmpty())
			keyFields.addAll(getRequiredFields(element));
		if (keyFields.isEmpty())
			keyFields.addAll(getSingleValueAttributes(element));
		if (keyFields.size() == 0 && !hasIdField(element))
			return false;
		if (element.getName().startsWith("Abstract"))
			return true;
		return !isCriteriaElement(element) &&
			!isMessageElement(element) &&
			!isEventElement(element) && 
			!isExceptionElement(element);
	}

	
	
	public static List<Include> getIncludes(Elements elements) {
		return ListUtil.getObjectList(elements.getIncludesAndClazzsAndTypes(), Include.class);
	}
	
	public static List<Type> getTypes(Elements elements) {
		return ListUtil.getObjectList(elements.getIncludesAndClazzsAndTypes(), Type.class);
	}

	public static List<Class> getClasses(Elements elements) {
		return ListUtil.getObjectList(elements.getIncludesAndClazzsAndTypes(), Class.class);
	}

	
	public static Collection<Element> getElements(Project project) {
		Set<Element> set = new HashSet<Element>();
		Collection<Information> informationBlocks = ProjectUtil.getInformationBlocks(project);
		Collection<Namespace> namespaces = InformationUtil.getAllNamespaces(informationBlocks);
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			List<Element> elements = NamespaceUtil.getElements(namespace);
			set.addAll(elements);
		}
		return set; 
	}

	public static List<Element> getElements(Elements elements) {
		return ListUtil.getObjectList(elements.getIncludesAndClazzsAndTypes(), Element.class);
	}

	public static List<Element> getElements(Namespace namespace, Elements elements) {
		List<Element> filteredList = new ArrayList<Element>();
		List<Element> list = getElements(elements);
		Iterator<Element> iterator = list.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			String elementNamespace = TypeUtil.getNamespace(element.getType());
			if (elementNamespace.equals(namespace.getUri())) {
				filteredList.add(element);
			}
		}
		return filteredList;
	}
	
	public static void addElement(Elements elements, Element element) {
		if (!elements.getIncludesAndClazzsAndTypes().contains(element))
			elements.getIncludesAndClazzsAndTypes().add(element);
	}



	public static void addGroup(Element element, Grouping group) {
		if (!element.getAttributesAndReferencesAndGroups().contains(group))
			element.getAttributesAndReferencesAndGroups().add(group);
	}
	
	public static List<Grouping> getGroups(Element element) {
		List<Grouping> groups = new ArrayList<Grouping>();
		List<Serializable> objects = element.getAttributesAndReferencesAndGroups();
		Iterator<Serializable> iterator = objects.iterator();
		while (iterator.hasNext()) {
			Serializable object = (Serializable) iterator.next();
			if (object instanceof Grouping) {
				Grouping group = (Grouping) object;
				groups.add(group);
			}
		}
		return groups;
	}


	//TODO Generate a warning here if some labels are null while some are non-null
	public static boolean isEnumerationLabelsExist(Enumeration enumeration) {
		List<Literal> literals = enumeration.getLiterals();
		Iterator<Literal> iterator = literals.iterator();
		while (iterator.hasNext()) {
			Literal literal = iterator.next();
			if (StringUtils.isEmpty(literal.getLabel()))
				return false;
		}
		return true;
	}
	
	public static List<Literal> getLiterals(Enumeration enumeration) {
		List<Literal> literals = new ArrayList<Literal>();
		literals.addAll(enumeration.getLiterals());
		return literals;
	}
	
	public static Collection<Item> getItems(Element element) {
		List<Item> items = new ArrayList<Item>();
		if (element == null)
			return items;
		List<Serializable> contents = element.getIdsAndItemsAndSecrets();
		Iterator<Serializable> iterator = contents.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof Item)
				items.add((Item) object);
			else if (object instanceof Grouping) {
				Grouping group = (Grouping) object;
				items.addAll(getItems(group));
			}
		}
		return items;
	}
	
	public static <T extends Item> List<T> getItems(Grouping group) {
		List<T> items = new ArrayList<T>();
		List<Field> objects = group.getIdsAndItemsAndSecrets();
		Iterator<Field> iterator = objects.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (field instanceof Item) {
				@SuppressWarnings("unchecked")
				T item = (T) field;
				items.add(item);
			}
		}
		return items;
	}
	
	
	public static boolean hasIdField(Type type) {
		if (type instanceof Element == false)
			return false;
		Element element = (Element) type;
		List<Id> ids = getIds(element);
		return !ids.isEmpty();
	}

	public static boolean hasUniqueField(Element element) {
		List<Field> fields = getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (FieldUtil.isId(field))
				continue;
			if (FieldUtil.isUnique(field)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasCollectionFields(Element element) {
		Collection<Field> collectionFields = getFields(element, "collection");
		Collection<Field> listFields = getFields(element, "list");
		Collection<Field> setFields = getFields(element, "set");
		Collection<Field> mapFields = getFields(element, "map");
		int size = collectionFields.size() + 
			listFields.size() +
			setFields.size() +
			mapFields.size();
		return size > 0;
	}

	public static boolean hasListOrSetFields(Element element) {
		Collection<Field> listFields = getFields(element, "list");
		Collection<Field> setFields = getFields(element, "set");
		int size = listFields.size() + setFields.size();
		return size > 0;
	}

	public static boolean hasMapFields(Element element) {
		Collection<Field> mapFields = getFields(element, "map");
		int size = mapFields.size();
		return size > 0;
	}


	public static boolean isFieldExists(Element element, String fieldName) {
		if (isFieldExists(getAttributes(element), fieldName))
			return true;
		if (isFieldExists(getReferences(element), fieldName))
			return true;
		return false;
	}

	public static <T extends Field> boolean isFieldExists(List<T> fields, String fieldName) {
		Iterator<T> iterator = fields.iterator();
		while (iterator.hasNext()) {
			T field = (T) iterator.next();
			if (field.getName().equalsIgnoreCase(fieldName))
				return true;
		}
		return false;
	}

	public static boolean isFieldExists(Element element, Field field) {
		List<Field> fields = getFields(element);
		return isFieldExists(fields, field);
	}
	
	public static boolean isFieldExists(Grouping group, Field field) {
		List<Field> fields = group.getFields();
		return isFieldExists(fields, field);
	}

	public static boolean isFieldExists(List<Field> fields, Field field) {
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field existingField = iterator.next();
			if (FieldUtil.equals(existingField, field)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isGroupExists(Element element, Grouping group) {
		List<Grouping> groups = getGroups(element);
		Iterator<Grouping> iterator = groups.iterator();
		while (iterator.hasNext()) {
			Grouping existingGroup = iterator.next();
			if (existingGroup.getName().equals(group.getName()))
				return true;
		}
		return false;
	}
	
	public static void addField(Element element, Field field) {
		addField(element, field, -1);
	}
	
	public static void addField(Element element, Field field, int index) {
		if (!isFieldExists(element, field)) {
			List<Serializable> contents = element.getAttributesAndReferencesAndGroups();
			if (!contents.contains(field)) {
				if (index > -1)
					contents.add(index, field);
				else contents.add(field);
			}
		}
	}

	public static boolean removeField(Element element, Field field) {
		if (isFieldExists(element, field))
			return element.getAttributesAndReferencesAndGroups().remove(field);
		return false;
	}


	public static void sortFieldsByName(List<Field> fields) {
		Collections.sort(fields, new Comparator<Field>() {
			public int compare(Field field1, Field field2) {
				return field1.getName().compareToIgnoreCase(field2.getName());
			}
		});
	}

	public static void sortFieldsByType(List<Field> fields) {
		Collections.sort(fields, new Comparator<Field>() {
			public int compare(Field field1, Field field2) {
				return field1.getType().compareToIgnoreCase(field2.getType());
			}
		});
	}

	public static List<Field> getFieldsSortedByType(Element element) {
		List<Field> fields = getFields(element);
		sortFieldsByType(fields);
		return fields;
	}
	
	public static List<Field> getFields(Element element) {
		List<Field> fields = new ArrayList<Field>();
		if (element == null)
			return fields;
		List<Serializable> contents = element.getAttributesAndReferencesAndGroups();
		Iterator<Serializable> iterator = contents.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof Attribute)
				fields.add((Attribute) object);
			else if (object instanceof Reference)
				fields.add((Reference) object);
			else if (object instanceof Grouping) {
				Grouping group = (Grouping) object;
				fields.addAll(group.getFields());
			}
		}
		return fields;
	}
	
	public static Collection<Field> getKeyFields(Element element) {
//		if (element.getName().equalsIgnoreCase("Payment"))
//			System.out.println();
		boolean isKeyElement = isKeyElement(element);
		List<Field> keyFields = new ArrayList<Field>();
		List<Field> fields = getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
//			if (field.getName().equals("actions"))
//				System.out.println();
			String structure = field.getStructure();
			if (!structure.equals("item"))
				continue;
			if (isKeyElement || FieldUtil.isUseForEquals(field))
				keyFields.add(field);
		}
//		if (keyFields.isEmpty())
//			keyFields.addAll(getRequiredFields(element));
//		if (keyFields.isEmpty())
//			keyFields.addAll(getSingleValueAttributes(element));
		return keyFields;
	}
	
	public static Collection<Field> getFieldsForToString(Element element) {
//		if (element.getName().equalsIgnoreCase("Payment"))
//			System.out.println();
		Set<Field> toStringFields = new LinkedHashSet<Field>();
		toStringFields.addAll(getUniqueFields(element));
		Collection<Field> keyFields = getKeyFields(element);
		if (keyFields.isEmpty())
			keyFields.addAll(getRequiredFields(element));
		//if (keyFields.isEmpty())
		//	keyFields.addAll(getSingleValueAttributes(element));
		toStringFields.addAll(keyFields);
		if (toStringFields.size() == 0) {
			List<Attribute> attributes = getSingleValueAttributes(element);
			Iterator<Attribute> iterator = attributes.iterator();
			while (iterator.hasNext()) {
				Attribute attribute = iterator.next();
				//TODO if (FieldUtil.isLabel(field))
				toStringFields.add(attribute);
			}
		}
		return toStringFields;
	}
	
	public static Collection<Field> getFields(Element element, String structure) {
		List<Field> fieldsForStructure = new ArrayList<Field>();
		List<Field> fields = getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (field.getStructure().equals(structure))
				fieldsForStructure.add(field);
		}
		return fieldsForStructure;
	}
	
	public static List<Field> getUniqueFields(Element element) {
		List<Field> uniqueFields = new ArrayList<Field>();
		List<Field> fields = getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (FieldUtil.isId(field))
				continue;
			String structure = field.getStructure();
			if (!structure.equals("item"))
				continue;
			if (FieldUtil.isUnique(field)) {
				uniqueFields.add(field);
			}
		}
		return uniqueFields;
	}

	public static List<Field> getIndexedFields(Element element) {
		List<Field> indexedFields = new ArrayList<Field>();
		List<Field> fields = getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String structure = field.getStructure();
			if (!structure.equals("item"))
				continue;
			if (FieldUtil.isIndexed(field)) {
				indexedFields.add(field);
			}
		}
		return indexedFields;
	}

	public static List<Field> getRequiredFields(Element element) {
		List<Field> requiredFields = new ArrayList<Field>();
		List<Field> fields = getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (FieldUtil.isRequired(field)) {
				requiredFields.add(field);
			}
		}
		return requiredFields;
	}
	
	public static List<Field> getLabelFields(Element element) {
		List<Field> labelFields = new ArrayList<Field>();
		List<Field> fields = getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (FieldUtil.isUseForLabel(field)) {
				labelFields.add(field);
			}
		}
		return labelFields;
	}
	
	
//	public static List<Field> getFieldsByType(Element element, String fieldType) {
//		return getFieldsByType(element, fieldType, null);
//	}
	
	public static List<Field> getFieldsByType(Element element, String construct, String fieldType) {
		List<Field> fieldsByType = new ArrayList<Field>();
		List<Field> fields = getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (!field.getType().equals(fieldType))
				continue;
			if (construct != null && !field.getStructure().equalsIgnoreCase(construct))
				continue;
			fieldsByType.add(field);
		}
		return fieldsByType;
	}
	
	public static List<Field> getFieldsByClassName(Element element, String construct, String className) {
		List<Field> fieldsByClassName = new ArrayList<Field>();
		List<Field> fields = getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String fieldClassName = TypeUtil.getClassName(field.getType());
			if (!fieldClassName.equals(className))
				continue;
			if (construct != null && !field.getStructure().equalsIgnoreCase(construct))
				continue;
			fieldsByClassName.add(field);
		}
		return fieldsByClassName;
	}

	public static boolean hasField(Element element, String fieldName) {
		return hasField(element, fieldName, false);
	}
	
	public static boolean hasField(Element element, String fieldName, boolean recurse) {
		Field field = getField(element, fieldName, recurse);
		return field != null;
	}

	public static Field getField(Element element, String fieldName) {
		return getField(element, fieldName, false);
	}
	
	public static Field getField(Element element, String fieldName, boolean recurse) {
		List<Field> fields = getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (field.getName().equalsIgnoreCase(fieldName))
				return field;
		}
		if (recurse) {
			String superType = element.getExtends();
			if (superType != null) {
				//TODO
			}
		}
		return null;
	}
	
	public static Field getCorrespondingField(Element element, Parameter parameter) {
		String parameterName = parameter.getName();
		String parameterType = parameter.getType();
		String parameterConstruct = parameter.getConstruct();
		Field field = getField(element, parameterName);
		if (field != null)
			return field;
		//TODO make this "smarter" - don't just pick the first of field of matching type
		List<Field> fields = getFieldsByType(element, parameterConstruct, parameterType);
		if (fields.size() > 0)
			return fields.get(0);
		return null;
	}
	
	public static Field getTargetField(Element element, String fieldName) {
		Field targetField = getField(element, fieldName);
		if (targetField == null)
			targetField = getField(element, fieldName+"Id");
		Assert.notNull(targetField, "Target field not found for: "+fieldName);
		return targetField;
	}
	
	public static String getTargetFieldName(Element element, String fieldName) {
		Field targetField = getTargetField(element, fieldName);
		return targetField.getName();
	}
	
	public static List<Field> getSelfReferencingFields(Element element) {
		List<Field> selfReferencingFields = new ArrayList<Field>();
		List<Field> fields = getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (isSelfReferencing(element, field))
				selfReferencingFields.add(field);
		}
		return selfReferencingFields;
	}
	
	public static List<Id> getIds(Element element) {
		return getFields2(element, Id.class);
	}
	
	public static List<Attribute> getAttributes(Element element) {
		return getFields2(element, Attribute.class);
	}
	
	public static List<Attribute> getSingleValueAttributes(Element element) {
		List<Attribute> singleValueAttributes = new ArrayList<Attribute>();
		List<Attribute> attributes = getFields2(element, Attribute.class);
		Iterator<Attribute> iterator = attributes.iterator();
		while (iterator.hasNext()) {
			Attribute attribute = iterator.next();
			String structure = attribute.getStructure();
			//if (attribute.getMaxOccurs() == 1) {
			if (structure.equals("item")) {
				singleValueAttributes.add(attribute);
			}
		}
		return singleValueAttributes;
	}
	
	public static Attribute getFirstAttribute(Element element) {
		List<Attribute> attributes = getAttributes(element);
		if (attributes.size() > 0)
			return attributes.get(0);
		return null;
	}
	
	public static void addAttribute(Element element, Attribute attribute) {
		element.addToAttributesAndReferencesAndGroups(attribute);
	}

	public static boolean removeAttribute(Element element, Attribute attribute) {
		return element.removeFromAttributesAndReferencesAndGroups(attribute);
	}

	public static List<Reference> getReferences(Element element) {
		return getFields2(element, Reference.class);
	}
	
	public static void addReference(Element element, Reference reference) {
		element.addToAttributesAndReferencesAndGroups(reference);
	}

	public static boolean removeReference(Element element, Reference reference) {
		return element.removeFromAttributesAndReferencesAndGroups(reference);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Field> List<T> getFields2(Element element, java.lang.Class<T> classObject) {
		List<T> fields = new ArrayList<T>();
		List<Serializable> objects = element.getAttributesAndReferencesAndGroups();
		Iterator<Serializable> iterator = objects.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object.getClass().equals(classObject)) {
				fields.add((T) object);
			} else if (object instanceof Grouping) {
				Grouping group = (Grouping) object;
				fields.addAll(getFields2(group, classObject));
			}
		}
		return fields;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Field> List<T> getFields2(Grouping group, java.lang.Class<T> classObject) {
		List<T> fields = new ArrayList<T>();
		List<Field> objects = group.getFields();
		Iterator<Field> iterator = objects.iterator();
		while (iterator.hasNext()) {
			Field object = (Field) iterator.next();
			if (object.getClass().equals(classObject)) {
				fields.add((T) object);
			}
		}
		return fields;
	}

	public static <T extends Field> List<T> getFields2(Grouping group) {
		List<T> fields = new ArrayList<T>();
		List<Field> objects = group.getFields();
		Iterator<Field> iterator = objects.iterator();
		while (iterator.hasNext()) {
			@SuppressWarnings("unchecked")
			T field = (T) iterator.next();
			fields.add(field);
		}
		return fields;
	}

	public static List<String> getApplicationDefinedFieldNames(Class<?> classObject) {
		List<String> fieldNames = new ArrayList<String>();
		Map<String, Class<?>> fields = getApplicationDefinedFields(classObject);
		Iterator<String> iterator = fields.keySet().iterator();
		while (iterator.hasNext()) {
			String fieldName = iterator.next();
			fieldNames.add(fieldName);
		}
		return fieldNames;
	}
	
	public static Map<String, Class<?>> getApplicationDefinedFields(Class<?> classObject) {
		Map<String, Class<?>> fields = new HashMap<String, Class<?>>();
		Method[] methods = classObject.getMethods();
		for (Method method: methods) {
			String name = method.getName();
			if (name.equals("clone")) continue;
			if (name.equals("equals")) continue;
			if (name.equals("finalize")) continue;
			if (name.equals("getClass")) continue;
			if (name.equals("hashCode")) continue;
			if (name.equals("notify")) continue;
			if (name.equals("notifyAll")) continue;
			if (name.equals("toString")) continue;
			if (name.equals("wait")) continue;
			if (!name.startsWith("get")) continue;
			String fieldName = name.substring(3);
			if (fieldName.equals("Id")) continue;
			if (fieldName.equals("TransactionId")) continue;
			if (fieldName.equals("CorrelationId")) continue;
			if (fieldName.equals("ReplyToDestinations")) continue;
			java.lang.Class<?> returnType = method.getReturnType();
			fields.put(fieldName, returnType);
		}
		return fields;
	}
	
	public static List<String> getApplicationDefinedFieldNames(Element element) {
		List<String> fieldNames = new ArrayList<String>();
		List<Field> fields = getApplicationDefinedFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			fieldNames.add(field.getName());
		}
		return fieldNames;
	}
	
	public static List<Field> getApplicationDefinedFields(Element element) {
		List<Field> fields = new ArrayList<Field>();
		List<Field> allFields = getFields(element);
		Iterator<Field> iterator = allFields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String name = field.getName();
			if (name.equals("id"))
				continue;
			fields.add(field);
		}
		return fields;
	}
	

	
	
	public static List<Annotation> getAnnotations(Element element) {
		List<Annotation> annotations = element.getAnnotations();
		return annotations;
	}

	public static Element cloneElement(Element element) {
		Element newElement = new Element();
		newElement.setName(element.getName());
		newElement.setType(element.getType());
		newElement.setRoot(element.getRoot());
		newElement.setPublic(element.getPublic());
		newElement.setTransient(element.getTransient());
		newElement.setAbstract(element.getAbstract());
		newElement.setExtends(element.getExtends());
		newElement.setCached(element.getCached());
		List<Field> fields = getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			Field newField = FieldUtil.cloneField(field);
			addField(newElement, newField);
		}
		return newElement;
	}

	public static Reference getInverseReference(Element element) {
		List<Reference> references = getReferences(element);
		Iterator<Reference> iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			Boolean inverse = reference.getInverse();
			if (reference.getMappedBy() != null || (inverse != null && inverse == true)) {
				return reference;
			}
		}
		
		return null;
	}

	public static Set<Reference> getReferences(Element parent, Element element) {
		Set<Reference> references = new HashSet<Reference>();
		Assert.notNull(parent, "Parent must be specified");
		Assert.notNull(element, "Element must be specified");
		
		String elementType = element.getType();
		List<Reference> parentReferences = getReferences(parent);
		Iterator<Reference> iterator = parentReferences.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String referenceType = reference.getType();
			if (referenceType.equals(elementType)) {
				references.add(reference);
			}
		}
		
		return references;
	}
	
	public static Set<Reference> getContainedReferences(Element element) {
		Set<Reference> containedReferences = new HashSet<Reference>();
		List<Reference> references = ElementUtil.getReferences(element);
		Iterator<Reference> iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			if (reference.getContained()) {
				containedReferences.add(reference);
			}
		}
		return containedReferences;
	}
	
	public static Set<Reference> getContainingReferences(Collection<Element> sources, Element element) {
		Set<Reference> references = new HashSet<Reference>();
		Iterator<Element> iterator = sources.iterator();
		while (iterator.hasNext()) {
			Element parent = iterator.next();
//			if (parent.getName().equalsIgnoreCase("User"))
//				System.out.println();
			Set<Reference> parentReferences = getContainingReferences(parent, element);
			references.addAll(parentReferences);
		}
		return references;
	}
	
	public static Set<Reference> getContainingReferences(Element container, Element element) {
		Set<Reference> containingReferences = new HashSet<Reference>();
		Assert.notNull(container, "Container must be specified");
		Assert.notNull(element, "Element must be specified");
		//Reference inverseReference = getInverseReference(element);
		//Assert.notNull(inverseReference, "Inverse-reference not found");
		
		List<Reference> references = getReferences(container);
		Iterator<Reference> iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String elementType = element.getType();
			String referenceType = reference.getType();
			if (referenceType.equals(elementType)) {
				containingReferences.add(reference);
			}
		}
		
		return containingReferences;
	}

	public static Set<Reference> getNonContainmentReferences(Element element) {
		Set<Reference> nonContainmentReferences = new HashSet<Reference>();
		Assert.notNull(element, "Element must be specified");

		List<Reference> references = getReferences(element);
		Iterator<Reference> iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			if (!FieldUtil.isContained(reference)) {
				nonContainmentReferences.add(reference);
			}
		}
		
		return nonContainmentReferences;
	}

	public static Set<Reference> getNonTransientReferences(Collection<Element> sources, Element element) {
		Set<Reference> references = new HashSet<Reference>();
		Iterator<Element> iterator = sources.iterator();
		while (iterator.hasNext()) {
			Element parent = iterator.next();
			if (isTransient(parent))
				continue;
			Set<Reference> parentReferences = getReferences(parent, element);
			references.addAll(parentReferences);
		}
		return references;
	}
	
//	public static void sortElements(List<Element> elements) {
//		Collections.sort(elements, new Comparator<Element>() {
//			public int compare(Element element1, Element element2) {
//				String type1 = element1.getType();
//				String type2 = element2.getType();
//				String qualifiedName1 = TypeUtil.getPackageName(type1)+"."+TypeUtil.getClassName(type1);
//				String qualifiedName2 = TypeUtil.getPackageName(type2)+"."+TypeUtil.getClassName(type2);
//				return qualifiedName1.compareTo(qualifiedName2);
//			}
//		});
//	}
	
	public static <T extends Type> void sortTypesByName(List<T> elements) {
		Collections.sort(elements, new Comparator<T>() {
			public int compare(T type1, T type2) {
				String typeName1 = type1.getType();
				String typeName2 = type2.getType();
				String className1 = TypeUtil.getClassName(typeName1);
				String className2 = TypeUtil.getClassName(typeName2);
				return className1.compareTo(className2);
			}
		});
	}
	
	public static <T extends Type> void sortTypesByQualifiedName(List<T> elements) {
		Collections.sort(elements, new Comparator<T>() {
			public int compare(T type1, T type2) {
				String typeName1 = type1.getType();
				String typeName2 = type2.getType();
				String packageName1 = TypeUtil.getPackageName(typeName1);
				String packageName2 = TypeUtil.getPackageName(typeName2);
				String className1 = TypeUtil.getClassName(typeName1);
				String className2 = TypeUtil.getClassName(typeName2);
				String qualifiedName1 = packageName1+"."+className1;
				String qualifiedName2 = packageName2+"."+className2;
				return qualifiedName1.compareTo(qualifiedName2);
			}
		});
	}
	
	public static boolean equals(Element element1, Element element2) {
		if (!element1.getName().equals(element2.getName()))
			return false;
		if (!element1.getType().equals(element2.getType()))
			return false;
		return true;
	}

	public static Collection<Namespace> getNamespaces(Element element) {
		Collection<Namespace> list = new ArrayList<Namespace>();
		//TODO get imports from element
		//element.getNamespace();
		return list;
	}

}

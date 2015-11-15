package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Annotation;
import nam.model.Attribute;
import nam.model.Element;
import nam.model.Field;
import nam.model.Id;
import nam.model.Parameter;
import nam.model.Reference;
import nam.model.Secret;
import nam.model.Type;

import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.ClassUtil;
import org.aries.util.Validator;
import org.eclipse.emf.codegen.util.CodeGenUtil;


public class FieldUtil {

	public static boolean isEmpty(Field field) {
		if (field == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Field> fieldList) {
		if (fieldList == null  || fieldList.size() == 0)
			return true;
		Iterator<Field> iterator = fieldList.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (!isEmpty(field))
				return false;
		}
		return true;
	}
	
	public static String toString(Field field) {
		if (isEmpty(field))
			return "Field: [uninitialized] "+field.toString();
		String text = field.toString();
		return text;
	}
	
	public static String toString(Collection<Field> fieldList) {
		if (isEmpty(fieldList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Field> iterator = fieldList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Field field = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(field);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Field create() {
		Field field = new Field();
		initialize(field);
		return field;
	}
	
	public static void initialize(Field field) {
		//nothing for now
	}
	
	public static boolean validate(Field field) {
		if (field == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Field> fieldList) {
		Validator validator = Validator.getValidator();
		Iterator<Field> iterator = fieldList.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			//TODO break or accumulate?
			validate(field);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Field> fieldList) {
		Collections.sort(fieldList, createFieldComparator());
	}
	
	public static Collection<Field> sortRecords(Collection<Field> fieldCollection) {
		List<Field> list = new ArrayList<Field>(fieldCollection);
		Collections.sort(list, createFieldComparator());
		return list;
	}
	
	public static Comparator<Field> createFieldComparator() {
		return new Comparator<Field>() {
			public int compare(Field field1, Field field2) {
				String name1 = field1.getName();
				String name2 = field2.getName();
				int status = name1.compareTo(name2);
				return status;
			}
		};
	}
	
	public static Field clone(Field field) {
		if (field == null)
			return null;
		Field clone = cloneField(field);
		return clone;
	}
	
	
	public static List<Annotation> getAnnotations(Field field) {
		List<Annotation> annotations = field.getAnnotations();
		return annotations;
	}
	
	public static boolean isBaseType(Field field) {
		String className = TypeUtil.getClassName(field.getType());
		if (ClassUtil.isJavaPrimitiveType(className) && field.getMaxOccurs() == 1)
			return true;
		if (CodeGenUtil.isJavaDefaultType(className) && field.getMaxOccurs() == 1)
			return true;
		if (isSimple(field))
			return true;
		return false;
	}

	public static boolean isInteger(Field field) {
		return isEqualsClass(field, "int") || isEqualsClass(field, "Integer");
	}
	
	public static boolean isLong(Field field) {
		return isEqualsClass(field, "long");
	}
	
	public static boolean isShort(Field field) {
		return isEqualsClass(field, "short");
	}
	
	public static boolean isNumeric(Field field) {
		return isInteger(field) || isLong(field) || isShort(field);
	}

	public static boolean isBoolean(Field field) {
		return isEqualsClass(field, "boolean");
	}

	public static boolean isString(Field field) {
		return isEqualsClass(field, "string");
	}
	
	public static boolean isDate(Field field) {
		return isEqualsClass(field, "date");
	}
	
	public static boolean isEqualsClass(Field field, String className) {
		String fieldClassName = TypeUtil.getClassName(field.getType());
		return fieldClassName.equalsIgnoreCase(className);
	}

	public static boolean isItem(Type field) {
		return field.getStructure().equals("item");
	}

	public static boolean isList(Type field) {
		return field.getStructure().equals("list");
	}

	public static boolean isSet(Type field) {
		return field.getStructure().equals("set");
	}

	public static boolean isMap(Type field) {
		return field.getStructure().equals("map");
	}

	public static boolean isCollection(Type field) {
		return isList(field) || isSet(field) || isMap(field);
		//return field.getMaxOccurs() > 1 || field.getMaxOccurs() == -1;
	}

	public static boolean isCollection(String structure) {
		return structure.equals("list") || structure.equals("set");
	}

	public static boolean isSimple(Field field) {
		Boolean value = field.getSimple();
		return value != null && value == true;
	}
	
	public static boolean isIdExist(List<Attribute> fields) {
		Iterator<Attribute> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Attribute attribute = iterator.next();
			if (isId(attribute))
				return true;
		}
		return false;
	}
	
	public static boolean isUseForEquals(Field field) {
		Boolean value = field.getUseForEquals();
		return value != null && value == true;
	}
	
	public static boolean isId(Field field) {
		if (field instanceof Id) {
			return true;
		} else if (field instanceof Attribute) {
			Attribute attribute = (Attribute) field;
			Boolean value = attribute.getId();
			return value != null && value == true;
		}
		return false;
	}
	
	public static Id createId() {
		Id id = new Id();
		id.setId(true);
		id.setChangeable(false);
		id.setColumn("id");
		id.setIndexed(true);
		id.setName("id");
		id.setStructure("item");
		id.setMinOccurs(1);
		id.setMaxOccurs(1);
		id.setUnique(true);
		id.setType(TypeUtil.getDefaultIdType());
		return id;
	}

	public static boolean isSecret(Field field) {
		return field.getHash() != null;
	}
	
	public static boolean isUnique(Field field) {
		Boolean value = field.getUnique();
		return value != null && value == true;
	}

	public static boolean isIndexed(Field field) {
		Boolean value = field.getIndexed();
		return value != null && value == true;
	}

	public static boolean isRequired(Field field) {
		Boolean value = field.getRequired();
		return value != null && value == true;
	}
	
	public static boolean isNillable(Field field) {
		Boolean value = field.getNullable();
		return value != null && value == true;
	}
	
	public static boolean isTransient(Field field) {
		Boolean value = field.getTransient();
		return value != null && value == true;
	}
	
	public static boolean isDerived(Field field) {
		if (field instanceof Reference) {
			Reference reference = (Reference) field;
			Boolean value = reference.getDerived();
			return value != null && value == true;
		}
		return false;
	}
	
	public static boolean isOrdered(Field field) {
		if (field instanceof Reference) {
			Reference reference = (Reference) field;
			Boolean value = reference.getOrdered();
			return value != null && value == true;
		}
		return false;
	}
	
	public static boolean isChangeable(Field field) {
		Boolean value = field.getChangeable();
		return value != null && value == true;
	}

	public static boolean isUnsettable(Field field) {
		Boolean value = field.getUnsettable();
		return value != null && value == true;
	}
	
	public static boolean isTwoWay(Field field) {
		if (field instanceof Reference) {
			Reference reference = (Reference) field;
			Boolean value = reference.getTwoWay();
			boolean hasMapping = !StringUtils.isEmpty(reference.getMappedBy());
			return (value != null && value == true) || hasMapping;
		}
		return false;
	}

	public static boolean isInverse(Field field) {
		if (field instanceof Reference) {
			Reference reference = (Reference) field;
			Boolean value = reference.getInverse();
			return value != null && value == true;
		}
		return false;
	}
	
	public static boolean isMappedBy(Field field) {
		if (field instanceof Reference) {
			Reference reference = (Reference) field;
			String value = reference.getMappedBy();
			return !StringUtils.isEmpty(value);
		}
		return false;
	}
	
	public static boolean isContained(Field field) {
		if (field instanceof Reference) {
			Reference reference = (Reference) field;
			Boolean value = reference.getContained();
//			if (value != null && value == true)
//				System.out.println();
			return value != null && value == true;
		}
		return false;
	}
	
	public static boolean isManaged(Field field) {
		Boolean value = field.getManaged();
		return value != null && value == true;
	}
	
	public static boolean isVolatile(Field field) {
		Boolean value = field.getVolatile();
		return value != null && value == true;
	}
	
	public static boolean isUseForLabel(Field field) {
		Boolean value = field.getUseForLabel();
		return value != null && value == true;
	}
	
	public static boolean isOneToOne(Reference reference) {
		String value = reference.getRelation();
		return value != null && value.equalsIgnoreCase("oneToOne");
	}
	
	public static boolean isManyToOne(Reference reference) {
		String value = reference.getRelation();
		return value != null && value.equalsIgnoreCase("manyToOne");
	}
	
	public static boolean isOneToMany(Reference reference) {
		String value = reference.getRelation();
		return value != null && value.equalsIgnoreCase("oneToMany");
	}

	public static boolean isManyToMany(Reference reference) {
		String value = reference.getRelation();
		return value != null && value.equalsIgnoreCase("manyToMany");
	}

	public static boolean isItem(Field field) {
		return field.getStructure().equals("item");
	}
	
	public static boolean isList(Field field) {
		return field.getStructure().equals("list");
	}
	
	public static boolean isSet(Field field) {
		return field.getStructure().equals("set");
	}
	
	public static boolean isMap(Field field) {
		return field.getStructure().equals("map");
	}
	
	public static boolean isCollection(Field field) {
		return field.getMaxOccurs() > 1 || field.getMaxOccurs() == -1;
	}
	
	public static boolean isCascadeAll(Reference reference) {
		return isCascade(reference, "all");
	}

	public static boolean isCascadeNone(Reference reference) {
		return isCascade(reference, "none");
	}
	
	public static boolean isCascadeDelete(Reference reference) {
		return isCascade(reference, "delete");
	}

	public static boolean isCascade(Reference reference, String cascadeType) {
		String cascade = reference.getCascade();
		if (cascade == null)
			return false;
		if (cascade.equalsIgnoreCase("all"))
			return true;
		if (cascade.equalsIgnoreCase(cascadeType))
			return true;
		return false;
	}
	
	
	public static boolean hasDefaultValue(Field field) {
		return field.getDefault() != null;
	}

	public static Object getDefaultValue(Field field) {
		String defaultValue = field.getDefault();
		if (defaultValue == null)
			return null;
		String className = TypeUtil.getQualifiedName(field.getType());
		if (className.equals("java.util.Date")) {
			DateConverter converter = new DateConverter(); 
			java.util.Date date = (java.util.Date) converter.convert(java.util.Date.class, defaultValue);
			return date;
		} else if (className.equalsIgnoreCase("boolean")) {
			return new Boolean(defaultValue);
		}
		return defaultValue;
	}

	
	public static String getRelation(Field field, Element fieldElement) {
		String type = field.getType();
		String className = TypeUtil.getClassName(type);
		
		//none
		if (field instanceof Attribute && CodeGenUtil.isJavaDefaultType(className))
			return null;
		
		//OneToOne_Unidirectional item ???
		if (!isCollection(field) && !isTwoway(field, fieldElement) && !isMultiplicityMany(field, fieldElement))
			return "one-to-one";
		
		//OneToOne_Bidirectional item other-side=item (inverse=true)
		if (!isCollection(field) && isTwoway(field, fieldElement) && !isMultiplicityMany(field, fieldElement))
			return "one-to-one";

		//item other-side=item (inverse=true, in same class)
		//initializeFieldAnnotations_OneToOne_SelfReferencing(modelClass, modelField, item);
		
		//OneToMany_Unidirectional list ???
		if (isCollection(field) && !isTwoway(field, fieldElement) && !isMultiplicityMany(field, fieldElement))
			return "one-to-many";

		//OneToMany_Bidirectional list other-side=item (inverse=true)
		if (isCollection(field) && isTwoway(field, fieldElement) && !isMultiplicityMany(field, fieldElement))
			return "one-to-many";
		
		//ManyToOne_SelfReferencing list other-side=item (inverse=true, in same class)
		//initializeFieldAnnotations_ManyToOne_SelfReferencing(modelClass, modelField, item);

		//ManyToOne_Unidirectional item ???
		if (!isCollection(field) && !isTwoway(field, fieldElement) && isMultiplicityMany(field, fieldElement))
			return "many-to-one";
		
		//ManyToOne_Bidirectional item other-side=list (inverse=true)
		if (!isCollection(field) && isTwoway(field, fieldElement) && isMultiplicityMany(field, fieldElement))
			return "many-to-one";
		
		//ManyToOne_SelfReferencing item other-side=list (inverse=true, in same class)
		//initializeFieldAnnotations_ManyToOne_SelfReferencing(modelClass, modelField, item);

		//ManyToMany_Unidirectional list ???
		if (isCollection(field) && !isTwoway(field, fieldElement) && isMultiplicityMany(field, fieldElement))
			return "many-to-many";
		
		//ManyToMany_Bidirectional list other-side=list (inverse=true)
		if (isCollection(field) && isTwoway(field, fieldElement) && isMultiplicityMany(field, fieldElement))
			return "many-to-many";
		return null;
	}

	public static boolean isMultiplicityMany(Field field, Element fieldElement) {
		String fieldType = field.getType();
		
//		EEnum eEnum = GenerationContext.INSTANCE.getEnumerationCache().get(fieldType);
//		if (eEnum != null) {
//			return false;
//		}
//
//		Element element = GenerationContext.INSTANCE.getElementByType(fieldType);
		if (fieldElement != null) {
			fieldElement.getType();
			
			if (field instanceof Attribute) {
				String className = TypeUtil.getClassName(fieldType);
				if (CodeGenUtil.isJavaDefaultType(className))
					return false;

				Attribute attribute = (Attribute) field;
				String attributeName = attribute.getName();
				String attributeType = attribute.getType();

				List<Attribute> attributes = ElementUtil.getAttributes(fieldElement);
				Iterator<Attribute> iterator = attributes.iterator();
				while (iterator.hasNext()) {
					Attribute inverseAttribute = iterator.next();
					String inverseAttributeName = inverseAttribute.getName();
					String inverseAttributeType = inverseAttribute.getType();
					if (inverseAttributeName.equals(attributeName) && inverseAttributeType.equals(attributeType))
						return isCollection(inverseAttribute);
				}
			}
			
			if (field instanceof Reference) {
				Reference reference = (Reference) field;
				String referenceType = reference.getType();
				
				List<Reference> references = ElementUtil.getReferences(fieldElement);
				Iterator<Reference> iterator = references.iterator();
				while (iterator.hasNext()) {
					Reference inverseReference = iterator.next();
					String inverseReferenceType = inverseReference.getType();
					if (inverseReferenceType.equals(referenceType))
						return isCollection(inverseReference);
				}
			}
		}
		return false;
	}

	public static boolean isTwoway(Field field, Element fieldElement) {
		String fieldType = field.getType();
		
//		EEnum eEnum = GenerationContext.INSTANCE.getEnumerationCache().get(fieldType);
//		if (eEnum != null) {
//			return false;
//		}
//
//		Element element = GenerationContext.INSTANCE.getElementByType(fieldType);
		if (fieldElement != null) {
			fieldElement.getType();
			
			if (field instanceof Attribute) {
				String className = TypeUtil.getClassName(fieldType);
				if (CodeGenUtil.isJavaDefaultType(className))
					return false;

				Attribute attribute = (Attribute) field;
				String attributeName = attribute.getName();
				String attributeType = attribute.getType();

				List<Attribute> attributes = ElementUtil.getAttributes(fieldElement);
				Iterator<Attribute> iterator = attributes.iterator();
				while (iterator.hasNext()) {
					Attribute inverseAttribute = iterator.next();
					String inverseAttributeName = inverseAttribute.getName();
					String inverseAttributeType = inverseAttribute.getType();
					if (inverseAttributeName.equals(attributeName) && inverseAttributeType.equals(attributeType))
						return true; //inverseAttribute.getInverse();
				}
			}
			
			if (field instanceof Reference) {
				Reference reference = (Reference) field;
				String referenceName = reference.getName();
				String referenceType = reference.getType();
				
				List<Reference> references = ElementUtil.getReferences(fieldElement);
				Iterator<Reference> iterator = references.iterator();
				while (iterator.hasNext()) {
					Reference inverseReference = iterator.next();
					String inverseReferenceName = inverseReference.getName();
					String inverseReferenceType = inverseReference.getType();
					if (inverseReferenceName.equals(referenceName) && inverseReferenceType.equals(referenceType))
						return true; //inverseAttribute.getInverse();
				}
			}
		}
		return false;
	}
	
	public static boolean hasInverse(Element parent, Field field, Element fieldElement) {
		String fieldType = field.getType();
		
//		EEnum eEnum = GenerationContext.INSTANCE.getEnumerationCache().get(fieldType);
//		if (eEnum != null) {
//			return false;
//		}
//
//		Element fieldElement = GenerationContext.INSTANCE.getElementByType(fieldType);
		if (fieldElement != null) {
			
			if (field instanceof Attribute) {
				String className = TypeUtil.getClassName(fieldType);
				if (CodeGenUtil.isJavaDefaultType(className))
					return false;

				//Attribute attribute = (Attribute) field;
				List<Attribute> attributes = ElementUtil.getAttributes(fieldElement);
				Iterator<Attribute> iterator = attributes.iterator();
				while (iterator.hasNext()) {
					Attribute inverseAttribute = iterator.next();
					if (!FieldUtil.isInverse(inverseAttribute))
						continue;
					//String inverseAttributeName = inverseAttribute.getName();
					String inverseAttributeType = inverseAttribute.getType();
					if (inverseAttributeType.equals(parent.getType()))
						return true;
				}
			}
			
			if (field instanceof Reference) {
				//Reference reference = (Reference) field;
				List<Reference> references = ElementUtil.getReferences(fieldElement);
				Iterator<Reference> iterator = references.iterator();
				while (iterator.hasNext()) {
					Reference inverseReference = iterator.next();
					if (!FieldUtil.isInverse(inverseReference))
						continue;
					//String inverseReferenceName = inverseReference.getName();
					String inverseReferenceType = inverseReference.getType();
					if (inverseReferenceType.equals(parent.getType()))
						return true;
				}
			}
		}
		return false;
	}
	
	
	public static boolean isStructureCompatible(Field field, Parameter parameter) {
		String fieldStructure = field.getStructure();
		String parameterStructure = parameter.getConstruct();
		if (fieldStructure.equals(parameterStructure))
			return true;
		if (fieldStructure.equals("collection") && isCollection(parameterStructure))
			return true;
		return false;
	}

	public static boolean isStructuresCompatibleWithCollection(Field field, Parameter parameter) {
		String fieldStructure = field.getStructure();
		String parameterStructure = parameter.getConstruct();
		if (fieldStructure.equals(parameterStructure))
			return true;
		if (isCollection(fieldStructure) && isCollection(parameterStructure))
			return true;
		return false;
	}

	
	/*
	 * Cloning related
	 */
	
	public static Field cloneField(Field field) {
		if (field instanceof Id) {
			return cloneId((Id) field);
		} else if (field instanceof Secret) {
			return cloneSecret((Secret) field);
		} else if (field instanceof Attribute) {
			return cloneAttribute((Attribute) field);
		} else if (field instanceof Reference) {
			return cloneReference((Reference) field);
		}
		return null;
	}

	public static Id cloneId(Id id) {
		Id newId = new Id();
		copyAttribute(newId, id);
		return newId;
	}
	
	public static Secret cloneSecret(Secret secret) {
		Secret newSecret = new Secret();
		copyAttribute(newSecret, secret);
		return newSecret;
	}
	
	public static Attribute cloneAttribute(Attribute attribute) {
		Attribute newAttribute = new Attribute();
		copyAttribute(newAttribute, attribute);
		return newAttribute;
	}
	
	public static void copyAttribute(Attribute newAttribute, Attribute attribute) {
		newAttribute.setCascade(attribute.getCascade());
		newAttribute.setChangeable(attribute.getChangeable());
		newAttribute.setColumn(attribute.getColumn());
		newAttribute.setDefault(attribute.getDefault());
		newAttribute.setDerived(attribute.getDerived());
		newAttribute.setEnact(attribute.getEnact());
		newAttribute.setFetch(attribute.getFetch());
		newAttribute.setHash(attribute.getHash());
		newAttribute.setId(attribute.getId());
		newAttribute.setIndexed(attribute.getIndexed());
		newAttribute.setKey(attribute.getKey());
		newAttribute.setManaged(attribute.getManaged());
		newAttribute.setMany(attribute.getMany());
		newAttribute.setMaxOccurs(attribute.getMaxOccurs());
		newAttribute.setMinOccurs(attribute.getMinOccurs());
		newAttribute.setName(attribute.getName());
		newAttribute.setNullable(attribute.getNullable());
		newAttribute.setOrdered(attribute.getOrdered());
		newAttribute.setPlaceholder(attribute.getPlaceholder());
		newAttribute.setRef(attribute.getRef());
		newAttribute.setRequired(attribute.getRequired());
		newAttribute.setStructure(attribute.getStructure());
		newAttribute.setSimple(attribute.getSimple());
		newAttribute.setTransient(attribute.getTransient());
		newAttribute.setType(attribute.getType());
		newAttribute.setUnique(attribute.getUnique());
		newAttribute.setUnsettable(attribute.getUnsettable());
		newAttribute.setUseForEquals(attribute.getUseForEquals());
		newAttribute.setUseForLabel(attribute.getUseForLabel());
		newAttribute.setVolatile(attribute.getVolatile());
		newAttribute.getAnnotations().addAll(AnnotationUtil.cloneAnnotations(attribute.getAnnotations()));
	}

	public static Reference cloneReference(Reference reference) {
		Reference newReference = new Reference();
		copyReference(newReference, reference);
		return newReference;
	}
	
	public static void copyReference(Reference newReference, Reference reference) {
		newReference.setBinding(reference.getBinding());
		newReference.setCascade(reference.getCascade());
		newReference.setChangeable(reference.getChangeable());
		newReference.setColumn(reference.getColumn());
		newReference.setContained(reference.getContained());
		newReference.setDefault(reference.getDefault());
		newReference.setDerived(reference.getDerived());
		newReference.setEnact(reference.getEnact());
		newReference.setFetch(reference.getFetch());
		newReference.setHash(reference.getHash());
		newReference.setIndex(reference.getIndex());
		newReference.setIndexed(reference.getIndexed());
		newReference.setInverse(reference.getInverse());
		newReference.setKey(reference.getKey());
		newReference.setManaged(reference.getManaged());
		newReference.setMany(reference.getMany());
		newReference.setMappedBy(reference.getMappedBy());
		newReference.setMaxOccurs(reference.getMaxOccurs());
		newReference.setMinOccurs(reference.getMinOccurs());
		newReference.setName(reference.getName());
		newReference.setNullable(reference.getNullable());
		newReference.setOrdered(reference.getOrdered());
		newReference.setPlaceholder(reference.getPlaceholder());
		newReference.setRef(reference.getRef());
		newReference.setRelation(reference.getRelation());
		newReference.setRequired(reference.getRequired());
		newReference.setRestriction(reference.getRestriction());
		newReference.setSimple(reference.getSimple());
		newReference.setSource(reference.getSource());
		newReference.setStructure(reference.getStructure());
		newReference.setTransient(reference.getTransient());
		newReference.setTwoWay(reference.getTwoWay());
		newReference.setType(reference.getType());
		newReference.setUnique(reference.getUnique());
		newReference.setUnsettable(reference.getUnsettable());
		newReference.setUseForEquals(reference.getUseForEquals());
		newReference.setUseForLabel(reference.getUseForLabel());
		newReference.setVolatile(reference.getVolatile());
		newReference.getAnnotations().addAll(AnnotationUtil.cloneAnnotations(reference.getAnnotations()));
	}
	
	public static boolean equals(Field field1, Field field2) {
		Assert.notNull(field1, "Field1 must be specified");
		Assert.notNull(field2, "Field2 must be specified");
		Assert.notNull(field1.getName(), "Field1 name must be specified");
		Assert.notNull(field2.getName(), "Field2 name must be specified");
		Assert.notNull(field1.getType(), "Field1 type must be specified");
		Assert.notNull(field2.getType(), "Field2 type must be specified");
		Assert.notNull(field1.getStructure(), "Field1 structure must be specified");
		Assert.notNull(field2.getStructure(), "Field2 structure must be specified");
		if (!field1.getName().equals(field2.getName()))
			return false;
		if (!field1.getType().equals(field2.getType()))
			return false;
		if (!field1.getStructure().equals(field2.getStructure()))
			return false;
		return true;
	}

}

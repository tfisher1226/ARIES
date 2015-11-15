package nam.model;

import nam.ProjectLevelHelper;
import nam.model.Element;
import nam.model.Enumeration;
import nam.model.Field;
import nam.model.Namespace;
import nam.model.Reference;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.TypeUtil;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import aries.generation.engine.GenerationContext;


public class ModelLayerHelper {

	public static String getXSDFileName(Namespace namespace) {
		String packageName = ProjectLevelHelper.getPackageName(namespace);
		if (packageName.startsWith("com") || packageName.startsWith("org") || packageName.startsWith("net"))
			packageName = packageName.substring(4);
		String fileName = packageName.replace(".", "-");
		//String prefix = namespace.getPrefix();
		String version = namespace.getVersion();
		//if (!StringUtils.isEmpty(prefix))
		//	fileName += "-" + prefix;
		if (!StringUtils.isEmpty(version))
			fileName += "-" + version;
		fileName += ".xsd";
		return fileName;
	}

	
	/*
	 * Element properties
	 * ------------------
	 */
	
	public static String getElementType(Type element) {
		return element.getType();
	}

	public static String getElementName(Type element) {
		return element.getName();
	}

	public static String getElementName(Type element, boolean capped) {
		String name = element.getName();
		if (capped)
			return NameUtil.capName(name);
		return NameUtil.uncapName(name);
	}
	
	public static String getElementNameCapped(Type element) {
		return NameUtil.capName(getElementName(element));
	}

	public static String getElementNameUncapped(Type element) {
		return NameUtil.uncapName(getElementName(element));
	}

	public static String getElementPackageName(Type element) {
		return TypeUtil.getPackageName(element.getType());
	}

	public static String getElementClassName(Type element) {
		return TypeUtil.getClassName(element.getType());
	}

	public static String getElementQualifiedName(Type element) {
		return getElementPackageName(element) + "." + getElementClassName(element);
	}

	public static String getElementBeanName(Type element) {
		return NameUtil.uncapName(getElementClassName(element));
	}

	public static String getElementTypeLocalPart(Type element) {
		String elementType = getElementType(element);
		int indexOfBracket = elementType.indexOf("}");
		String localPart = elementType.substring(indexOfBracket+1);
		return localPart;
	}

	public static String getElementTypeLocalPartCapped(Type element) {
		return NameUtil.capName(getElementTypeLocalPart(element));
	}
	
	public static String getElementTypeLocalPartUncapped(Type element) {
		return NameUtil.uncapName(getElementTypeLocalPart(element));
	}
	
	public static String getElementTypeLocalPart(Type element, boolean capped) {
		String localPart = getElementTypeLocalPart(element);
		if (capped)
			return NameUtil.capName(localPart);
		return NameUtil.uncapName(localPart);
	}

	public static String getElementKey(Type element) {
		return element.getKey();
	}
	
	public static String getElementKeyPackageName(Type element) {
		return TypeUtil.getPackageName(element.getKey());
	}
	
	public static String getElementKeyClassName(Type element) {
		return TypeUtil.getClassName(element.getKey());
	}
	
	public static String getElementKeyQualifiedName(Type element) {
		return getElementKeyClassName(element) + "." + getElementKeyClassName(element);
	}

	public static String getElementKeyLocalPartCapped(Type element) {
		String elementKeyLocalPart = getElementKeyLocalPart(element);
		if (elementKeyLocalPart != null)
			return NameUtil.capName(elementKeyLocalPart);
		return null;
	}
	
	public static String getElementKeyLocalPartUncapped(Type element) {
		String elementKeyLocalPart = getElementKeyLocalPart(element);
		if (elementKeyLocalPart != null)
			return NameUtil.uncapName(elementKeyLocalPart);
		return null;
	}
	
	public static String getElementKeyLocalPart(Type element, boolean capped) {
		String localPart = getElementKeyLocalPart(element);
		if (localPart == null)
			return null;
		if (capped)
			return NameUtil.capName(localPart);
		return NameUtil.uncapName(localPart);
	}

	public static String getElementKeyLocalPart(Type element) {
		String elementKey = getElementKey(element);
		if (elementKey == null)
			return null;
		int indexOfBracket = elementKey.indexOf("}");
		String localPart = elementKey.substring(indexOfBracket+1);
		return localPart;
	}
	
	public static String getElementKeyTypeName(Namespace namespace, Element element) {
		return getElementKeyTypeName(namespace.getUri(), element);
	}
	
	public static String getElementKeyTypeName(String namespace, Element element) {
		String localPart = NameUtil.uncapName(element.getName()) + "Key";
		String entityType = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, localPart);
		return entityType;
	}

	public static String getElementKeyTypeName(Namespace namespace, String localPart) {
		String entityType = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace.getUri(), localPart);
		return entityType;
	}
	
	public static String getElementCriteriaTypeName(Namespace namespace, Element element) {
		return getElementCriteriaTypeName(namespace.getUri(), element);
	}
	
	public static String getElementCriteriaTypeName(String namespace, Element element) {
		String localPart = NameUtil.uncapName(element.getName()) + "Criteria";
		String entityType = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, localPart);
		return entityType;
	}

	public static String getElementCriteriaTypeName(Namespace namespace, String localPart) {
		String entityType = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace.getUri(), localPart);
		return entityType;
	}
	
	public static String getElementWebappFolder(Type type) {
		//String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		String elementFolder = elementPackageName.replace(".", "/");
		String folderName = elementFolder + "/" + elementNameUncapped;
		return folderName;
	}
	

	/*
	 * Enumeration properties
	 * ----------------------
	 */
	
	public static String getEnumerationType(Enumeration enumeration) {
		return enumeration.getType();
	}

	public static String getEnumerationName(Enumeration enumeration) {
		return enumeration.getName();
	}

	public static String getEnumerationNameCapped(Enumeration enumeration) {
		return NameUtil.capName(getEnumerationName(enumeration));
	}

	public static String getEnumerationNameUncapped(Enumeration enumeration) {
		return NameUtil.uncapName(getEnumerationName(enumeration));
	}

	public static String getEnumerationPackageName(Enumeration enumeration) {
		return TypeUtil.getPackageName(enumeration.getType());
	}

	public static String getEnumerationClassName(Enumeration enumeration) {
		return TypeUtil.getClassName(enumeration.getType());
	}

	public static String getEnumerationQualifiedName(Enumeration enumeration) {
		return getEnumerationPackageName(enumeration) + "." + getEnumerationClassName(enumeration);
	}
	
	
	/*
	 * Field properties
	 * ----------------
	 */
	
	public static String getFieldType(Field field) {
		return field.getType();
	}

	public static String getFieldName(Field field) {
		return field.getName();
	}

	public static String getFieldNameCapped(Type field) {
		return NameUtil.capName(field.getName());
	}

	public static String getFieldNameUncapped(Field field) {
		return NameUtil.uncapName(field.getName());
	}

	public static String getFieldPackageName(Field field) {
		return TypeUtil.getPackageName(field.getType());
	}

	public static String getFieldClassName(Field field) {
		return TypeUtil.getClassName(field.getType());
	}

	public static String getFieldClassNameUncapped(Field field) {
		return NameUtil.uncapName(TypeUtil.getClassName(field.getType()));
	}

	public static String getFieldQualifiedName(Field field) {
		return getFieldPackageName(field) + "." + getFieldClassName(field);
	}

	public static String getFieldTypeUri(Field field) {
		String fieldType = getFieldType(field);
		int indexOfBracket = fieldType.indexOf("}");
		String uri = fieldType.substring(1, indexOfBracket);
		return uri;
	}

	public static String getFieldTypeLocalPart(Field field) {
		String fieldType = getFieldType(field);
		int indexOfBracket = fieldType.indexOf("}");
		String localPart = fieldType.substring(indexOfBracket+1);
		return localPart;
	}

	public static String getFieldTypeLocalPartCapped(Field field) {
		return NameUtil.capName(getFieldTypeLocalPart(field));
	}
	
	public static String getFieldTypeLocalPartUncapped(Field field) {
		return NameUtil.uncapName(getFieldTypeLocalPart(field));
	}
	
	public static String getFieldTypeLocalPart(Field field, boolean capped) {
		String localPart = getFieldTypeLocalPart(field);
		if (capped)
			return NameUtil.capName(localPart);
		return NameUtil.uncapName(localPart);
	}
	
	
	/*
	 * Namespace properties
	 * --------------------
	 */
	
	
	/*
	 * Inferred Element properties
	 * (These are created as part of "cache" and "persist" structures in ARIEL)
	 * ------------------------------------------------------------------------
	 */

	public static String getInferredElementTypeName(Namespace namespace, Element element) {
		return getInferredElementTypeName(namespace.getUri(), element);
	}
	
	public static String getInferredElementTypeName(String namespace, Element element) {
		String localPart = NameUtil.uncapName(element.getName());
		String elementType = getInferredElementTypeName(namespace, localPart);
		return elementType;
	}

	public static String getInferredElementTypeName(Namespace namespace, String localPart) {
		return getInferredElementTypeName(namespace.getUri(), localPart);
	}
	
	public static String getInferredElementTypeName(String namespace, String localPart) {
		String elementType = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, localPart);
		return elementType;
	}

	public static String getInferredElementPackageName(Namespace namespace, Element element) {
		return getInferredElementPackageName(namespace.getUri(), element);
	}
	
	public static String getInferredElementPackageName(String namespace, Element element) {
		String elementType = getInferredElementTypeName(namespace, element);
		String packageName = TypeUtil.getPackageName(elementType);
		return packageName;
	}

	public static String getInferredElementClassName(Namespace namespace, Element element) {
		return getInferredElementClassName(namespace.getUri(), element);
	}
	
	public static String getInferredElementClassName(String namespace, Element element) {
		String elementType = getInferredElementTypeName(namespace, element);
		String className = TypeUtil.getClassName(elementType);
		return className;
	}

	public static String getInferredElementBeanName(Namespace namespace, Element element) {
		return getInferredElementBeanName(namespace.getUri(), element);
	}
	
	public static String getInferredElementBeanName(String namespace, Element element) {
		String className = getInferredElementClassName(namespace, element);
		String elementName = NameUtil.uncapName(className);
		return elementName;
	}
	
	
	/*
	 * Contained Field properties
	 * --------------------------
	 */

	public static String getContainedFieldPackageName(Namespace namespace) {
		return getContainedFieldPackageName(namespace.getUri());
	}
	
	public static String getContainedFieldPackageName(String namespace) {
		String packageName = ProjectLevelHelper.getPackageName(namespace);
		return packageName;
	}

	public static String getContainedFieldClassName(Element element, Reference reference) {
		String elementName = NameUtil.capName(element.getName());
		String referenceType = reference.getType();
		String referenceLocalPart = TypeUtil.getLocalPart(referenceType);
		String containedReferenceName = NameUtil.capName(referenceLocalPart);
		String className = elementName + containedReferenceName; 
		return className;
	}

	public static String getContainedFieldQualifiedName(Namespace namespace, Element element, Reference reference) {
		return getContainedFieldQualifiedName(namespace, element, reference);
	}
	
	public static String getContainedFieldQualifiedName(String namespace, Element element, Reference reference) {
		String packageName = getContainedFieldPackageName(namespace);
		String className = getContainedFieldClassName(element, reference);
		String qualifiedName = packageName + "." + className;
		return qualifiedName;
	}
	
	public static String getContainedFieldTypeName(Namespace namespace, Element element, Reference reference) {
		String packageName = getContainedFieldPackageName(namespace);
		String className = getContainedFieldClassName(element, reference);
		String typeName = TypeUtil.getTypeFromPackageAndClass(packageName, className);
		return typeName;
	}

	
	/*
	 * ModelFixture methods
	 * --------------------
	 */

	public static String getModelFixturePackageName(Namespace namespace) {
		return getModelFixturePackageName(namespace.getUri());
	}
	
	public static String getModelFixturePackageName(String namespace) {
		return ProjectLevelHelper.getPackageName(namespace) + ".util";
	}

	public static String getModelFixtureClassName(Element element) {
		String namespace = TypeUtil.getNamespace(element.getType());
		return getModelFixtureClassName(namespace);
	}
	
	public static String getModelFixtureClassName(Namespace namespace) {
		return getModelFixtureClassName(namespace.getUri());
	}
	
	public static String getModelFixtureClassName(String namespace) {
		//String packageName = ProjectLevelHelper.getPackageName(namespace);
		//String fixtureName = FixtureUtil.getFixtureNameFromPackageName(packageName);
		String lastSegment = NameUtil.getLastSegment(namespace, "/");
		lastSegment = NameUtil.toCamelCase(lastSegment);
		String className = NameUtil.capName(lastSegment) + "Fixture";
		//String className = NameUtil.capName(fixtureName);
		return className;
	}

	public static String getModelFixtureQualifiedName(Element element) {
		String namespace = TypeUtil.getNamespace(element.getType());
		return getModelFixturePackageName(namespace) + "." + getModelFixtureClassName(namespace); 
	}
	
	public static String getModelFixtureQualifiedName(Namespace namespace) {
		return getModelFixturePackageName(namespace.getUri()) + "." + getModelFixtureClassName(namespace.getUri()); 
	}

	public static String getModelFixtureQualifiedName(String type) {
		String namespace = TypeUtil.getNamespace(type);
		return getModelFixturePackageName(namespace) + "." + getModelFixtureClassName(namespace); 
	}
	
	public static String getModelFixtureBeanName(Type type) {
		String namespace = TypeUtil.getNamespace(type.getType());
		return getModelFixtureBeanName(namespace);
	}
	
	public static String getModelFixtureBeanName(Namespace namespace) {
		return getModelFixtureClassName(namespace);
	}
	
	public static String getModelFixtureBeanName(String type) {
		String namespace = TypeUtil.getNamespace(type);
		return getModelFixtureClassName(namespace);
	}
	
	
	
	/*
	 * ModelHelper methods
	 * --------------------
	 */

	public static String getModelHelperPackageName(Namespace namespace) {
		return getModelHelperPackageName(namespace.getUri());
	}
	
	public static String getModelHelperPackageName(String namespace) {
		return ProjectLevelHelper.getPackageName(namespace) + ".util";
	}

	public static String getModelHelperClassName(Type type) {
		String namespace = TypeUtil.getNamespace(type.getType());
		return getModelHelperClassName(namespace);
	}
	
	public static String getModelHelperClassName(Namespace namespace) {
		return getModelHelperClassName(namespace.getUri());
	}
	
	public static String getModelHelperClassName(String namespace) {
		//String packageName = ProjectLevelHelper.getPackageName(namespace);
		//String fixtureName = FixtureUtil.getFixtureNameFromPackageName(packageName);
		String lastSegment = NameUtil.getLastSegment(namespace, "/");
		lastSegment = NameUtil.toCamelCase(lastSegment);
		String className = NameUtil.capName(lastSegment) + "Helper";
		//String className = NameUtil.capName(fixtureName);
		return className;
	}

	public static String getModelHelperQualifiedName(Type type) {
		String namespace = TypeUtil.getNamespace(type.getType());
		return getModelHelperPackageName(namespace) + "." + getModelHelperClassName(namespace); 
	}
	
	public static String getModelHelperQualifiedName(Namespace namespace) {
		return getModelHelperPackageName(namespace.getUri()) + "." + getModelHelperClassName(namespace.getUri()); 
	}

	public static String getModelHelperQualifiedName(String type) {
		String namespace = TypeUtil.getNamespace(type);
		return getModelHelperPackageName(namespace) + "." + getModelHelperClassName(namespace); 
	}
	
	public static String getModelHelperBeanName(Type type) {
		return getModelHelperBeanName(type.getType());
	}
	
	public static String getModelHelperBeanName(Namespace namespace) {
		return getModelHelperClassName(namespace);
	}
	
	public static String getModelHelperBeanName(String type) {
		String namespace = TypeUtil.getNamespace(type);
		return getModelHelperClassName(namespace);
	}
	
	
	
	/*
	 * Fixture methods
	 * ---------------
	 */
	
	public static String getFixtureClassName(Type dataItem) {
		return getFixtureClassName(dataItem.getType());
	}
	
	public static String getFixtureClassName(String typeName) {
		String namespace = TypeUtil.getNamespace(typeName);
		//String dataItemPackageName = ProjectLevelHelper.getPackageName(dataItemNamespace);
		//String dataItemDomainName = NameUtil.mergeSegments(dataItemPackageName);
		String lastSegment = NameUtil.getLastSegment(namespace, "/");
		String fixtureName = NameUtil.capName(lastSegment) + "Fixture";
		return fixtureName;
	}

	public static String getFixturePackageName(Type dataItem) {
		return getFixturePackageName(dataItem.getType());
	}
	
	public static String getFixturePackageName(String typeName) {
		String dataItemNamespace = TypeUtil.getNamespace(typeName);
		String dataItemPackageName = ProjectLevelHelper.getPackageName(dataItemNamespace);
		String fixturePackageName = dataItemPackageName + ".util";
		return fixturePackageName;
	}
	
	public static String getFixtureQualifiedName(Type dataItem) {
		return getFixtureQualifiedName(dataItem.getType());
	}
	
	public static String getFixtureQualifiedName(String typeName) {
		String namespace = TypeUtil.getNamespace(typeName);
		return getFixtureQualifiedNameFromNamespace(namespace);
	}

	public static String getFixtureQualifiedName(Namespace namespace) {
		return getFixtureQualifiedNameFromNamespace(namespace.getUri());
	}
	
	public static String getFixtureQualifiedNameFromNamespace(String namespace) {
		String packageName = ProjectLevelHelper.getPackageName(namespace);
		//String domainName = NameUtil.mergeSegments(packageName);
		String domainName = NameUtil.getLastSegment(namespace, "/");
		String fixtureName = NameUtil.capName(domainName) + "Fixture";
		String fixtureQualifiedName = packageName + ".util." + fixtureName;
		return fixtureQualifiedName;
	}
	
	
//	/*
//	 * Helper methods
//	 * --------------
//	 */
//	
//	public static String getHelperName(Type dataItem) {
//		return getHelperName(dataItem.getType());
//	}
//	
//	public static String getHelperName(String typeName) {
//		String dataItemNamespace = TypeUtil.getNamespace(typeName);
//		String dataItemPackageName = ProjectLevelHelper.getPackageName(dataItemNamespace);
//		String dataItemDomainName = NameUtil.mergeSegments(dataItemPackageName);
//		String helperName = NameUtil.capName(dataItemDomainName) + "Helper";
//		return helperName;
//	}
//
//	public static String getHelperPackageName(Type dataItem) {
//		return getHelperPackageName(dataItem.getType());
//	}
//	
//	public static String getHelperPackageName(String typeName) {
//		String dataItemNamespace = TypeUtil.getNamespace(typeName);
//		String dataItemPackageName = ProjectLevelHelper.getPackageName(dataItemNamespace);
//		String helperPackageName = dataItemPackageName + ".util";
//		return helperPackageName;
//	}
//	
//	public static String getHelperQualifiedName(Type dataItem) {
//		return getHelperQualifiedName(dataItem.getType());
//	}
//	
//	public static String getHelperQualifiedName(String typeName) {
//		String dataItemNamespace = TypeUtil.getNamespace(typeName);
//		return getHelperQualifiedNameFromNamespace(dataItemNamespace);
//	}
//
//	public static String getHelperQualifiedName(Namespace namespace) {
//		return getHelperQualifiedNameFromNamespace(namespace.getUri());
//	}
//	
//	public static String getHelperQualifiedNameFromNamespace(String namespace) {
//		String packageName = ProjectLevelHelper.getPackageName(namespace);
//		String domainName = NameUtil.mergeSegments(packageName);
//		String helperName = NameUtil.capName(domainName) + "Helper";
//		String helperQualifiedName = packageName + ".util." + helperName;
//		return helperQualifiedName;
//	}
	
	
	
	/*
	 * Container related
	 */
	
	public static Element getContainer(GenerationContext context, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		if (inverseReference != null) {
			Element containingElement = context.getElementByType(inverseReference.getType());
			if (containingElement == null) {
				String namespace = inverseReference.getNamespace();
				String localPart = NameUtil.uncapName(inverseReference.getName());
				String originalType = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, localPart);
				containingElement = context.getElementByType(originalType);
			}
				
			//Field containingReference = ElementUtil.getField(containingElement, inverseReference.getMappedBy());
			//if (containingReference != null && FieldUtil.isContained(containingReference)) {
			return containingElement;
			//}
		}
		return null;
	}


	public static Reference createReferenceToContainingOwner(Namespace namespace, Element owner) {
		return createReferenceToContainingOwner(namespace.getUri(), owner);
	}
	
	public static Reference createReferenceToContainingOwner(String namespace, Element owner) {
		String ownerElementLocalPart = getElementTypeLocalPartUncapped(owner);
		String ownerElementNamespace = TypeUtil.getNamespace(owner.getType());
		String inferredElementTypeName = getInferredElementTypeName(namespace, owner);

		if (ownerElementLocalPart.equalsIgnoreCase("BookOrders"))
			System.out.println();
		
		Reference reference = new Reference();
		reference.setDerived(true);
		reference.setName(ownerElementLocalPart);
		reference.setNamespace(ownerElementNamespace);
		//attribute.setName(elementTypeLocalPart+"Key");
		reference.setType(inferredElementTypeName);
		reference.setRelation("manyToOne");
		reference.setUseForEquals(true);
		reference.setTwoWay(true);
		reference.setInverse(true);
		reference.setRequired(true);
		reference.setNullable(false);
		reference.setMinOccurs(1);
		reference.setMaxOccurs(1);
		reference.setStructure("item");
		return reference;
	}
	
}

package nam.model.src.main.java;

import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.data.src.test.java.EntityClassHelper;
import nam.model.Attribute;
import nam.model.Element;
import nam.model.Enumeration;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Reference;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.TypeUtil;

import org.aries.Assert;
import org.aries.util.NameUtil;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.jdt.core.dom.Modifier;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


public class DummyValueFactory extends AbstractBeanBuilder {

	private Namespace namespace;
	
	private boolean useForEntityFixtures;

	
	public DummyValueFactory(GenerationContext context) {
		super(context);
	}
	
	public Namespace getNamespace() {
		return namespace;
	}

	public void setNamespace(Namespace namespace) {
		this.namespace = namespace;
	}

	public boolean isUseForEntityFixtures() {
		return useForEntityFixtures;
	}

	public void setUseForEntityFixtures(boolean useForEntityFixtures) {
		this.useForEntityFixtures = useForEntityFixtures;
	}

	public boolean isGenerated(String javaName) {
		List<Namespace> namespaces = context.getNamespaces();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			if (isGenerated(namespace, javaName))
				return true;
			NamespaceUtil.getElements(namespace);
		}
		return false;
	}
	
	public boolean isGenerated(Namespace namespace, String javaName) {
		List<Element> elements = NamespaceUtil.getElements(namespace);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = (Element) iterator.next();
			String elementJavaName = TypeUtil.getClassName(element.getType());
			if (elementJavaName.equals(javaName))
				return true;
		}
		//elementCache
		return false;
	}

	public void createSampleFactoryMethod(ModelClass modelClass, Element element) {
		String elementType = element.getType();
		String packageName = TypeUtil.getPackageName(elementType);
		String className = TypeUtil.getClassName(elementType);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("create"+className);
		modelOperation.setResultType(className);
		modelClass.addInstanceOperation(modelOperation);
		modelClass.addImportedClass(packageName + "." + className);
		modelOperation.addInitialSource(createSampleFactoryMethod(element));
	}

	public String createSampleFactoryMethod(Element element) {
		String elementType = element.getType();
		String className = TypeUtil.getClassName(elementType);
		Buf buf = new Buf();
		String beanName = NameUtil.uncapName(className);
		buf.putLine2(className+" "+beanName+" = createEmpty_"+className+"();");
		buf.put(createSampleValuesForAttributes(element, ElementUtil.getAttributes(element)));
		buf.put(createSampleValuesForReferences(element, ElementUtil.getReferences(element)));
		buf.putLine2("return "+beanName+";");
		return buf.get();
	}

	public String createSampleValuesForAttributes(Element element, List<Attribute> attributes) {
		return createSampleValuesForAttributes(element, attributes, 0);
	}

	public String createSampleValuesForAttributes(Element element, List<Attribute> attributes, int indent) {
		Buf buf = new Buf();
		buf.setIndent(indent);
		Iterator<Attribute> iterator = attributes.iterator();
		while (iterator.hasNext()) {
			Attribute attribute = iterator.next();
			buf.put(createSampleValueForAttribute(element, attribute, 0, true));
		}
		return buf.get();
	}

	public String createSampleValueForAttribute(Element element, Attribute attribute, int indent) {
		return createSampleValueForAttribute(element, attribute, indent, false);
	}
	
	public String createSampleValueForAttribute(Element element, Attribute attribute, int indent, boolean includeInvocation) {
		//String packageName = TypeUtil.getPackageName(element.getType());
		String className = TypeUtil.getClassName(element.getType());
		String beanName = NameUtil.uncapName(className);
		
		if (useForEntityFixtures) {
			className = DataLayerHelper.getInferredEntityClassName(this.namespace, element);
			beanName = DataLayerHelper.getInferredEntityBeanName(this.namespace, element);
		}

		String structure = attribute.getStructure();
		String name = attribute.getName();
		String type = attribute.getType();
		String attributeClassName = TypeUtil.getClassName(type);
		String attributeDefaultValue = attribute.getDefault();
		
		//String javaName = TypeUtil.getJavaName(type);
		boolean isElement = context.getElementByType(type) != null;
		boolean isEnum = context.getEnumerationByType(type) != null;
		boolean isUnique = FieldUtil.isUnique(attribute);

		String itemKey = null;
		String itemValue = null;
		
		if (attributeDefaultValue != null) {
			if (isEnum)
				itemValue = attributeClassName+"."+attributeDefaultValue;
			else itemValue = attributeDefaultValue;
		}
		
//		if (isUnique)
//			System.out.println();
		
		if (itemValue == null) {
			if (structure.equals("item")) {
				itemValue = getDummyValue(name, type, true, false, isUnique);
			} else if (structure.equals("list") && isEnum) {
				itemValue = getDummyValue(name, type, true, true, false);
			} else if (structure.equals("list") && isElement) {
				itemValue = getDummyValue(name, type, true, true, false);
			} else if (structure.equals("set") && isEnum) {
				itemValue = getDummyValue(name, type, true, true, false);
			} else if (structure.equals("set") && isElement) {
				itemValue = getDummyValue(name, type, true, true, false);
			} else if (structure.equals("map")) {
				String key = attribute.getKey();
				Assert.notNull(key, "Key not found: "+name);
				itemKey = getDummyValue(name, key, true, true, false);
				itemValue = getDummyValue(name, type, true, true, false);
			}
		}
		
//		if (beanName.equals("bookOrdersBookEntity"))
//			System.out.println();

		Buf buf = new Buf();
		buf.setIndent(indent);
		if (includeInvocation) {
			if (structure.equals("item")) {
				buf.putLine2(beanName+".set"+NameUtil.capName(name)+"("+itemValue+");");
			} else if (structure.equals("list") && isEnum) {
				buf.putLine2(beanName+".get"+NameUtil.capName(name)+"().add("+itemValue+");");
			} else if (structure.equals("list") && isElement) {
				buf.putLine2(beanName+".get"+NameUtil.capName(name)+"().addAll("+itemValue+");");
			} else if (structure.equals("set") && isEnum) {
				buf.putLine2(beanName+".get"+NameUtil.capName(name)+"().add("+itemValue+");");
			} else if (structure.equals("set") && isElement) {
				buf.putLine2(beanName+".get"+NameUtil.capName(name)+"().addAll("+itemValue+");");
			} else if (structure.equals("map")) {
				buf.putLine2(beanName+".get"+NameUtil.capName(name)+"().put("+itemKey+", "+itemValue+");");
			}
		} else {
			buf.put(itemValue);
		}
		return buf.get();
	}
	
	public String createSampleValueForAttributeSAVE(Element element, Attribute attribute, int indent) {
		Buf buf = new Buf();
		buf.setIndent(indent);
		String structure = attribute.getStructure();
		String name = attribute.getName();
		String type = attribute.getType();
		//String javaName = TypeUtil.getJavaName(type);
		
		boolean isElement = context.getElementByType(type) != null;
		boolean isEnum = context.getEnumerationByType(type) != null;
		boolean isUnique = FieldUtil.isUnique(attribute);

		if (structure.equals("item")) {
			buf.putLine2(beanName+".set"+NameUtil.capName(name)+"("+getDummyValue(name, type, true, false, isUnique)+");");
		} else if (structure.equals("list") && isEnum) {
			buf.putLine2(beanName+".get"+NameUtil.capName(name)+"().add("+getDummyValue(name, type, true, true, false)+");");
		} else if (structure.equals("list") && isElement) {
			buf.putLine2(beanName+".get"+NameUtil.capName(name)+"().addAll("+getDummyValue(name, type, true, true, false)+");");
		} else if (structure.equals("set") && isEnum) {
			buf.putLine2(beanName+".get"+NameUtil.capName(name)+"().add("+getDummyValue(name, type, true, true, false)+");");
		} else if (structure.equals("set") && isElement) {
			buf.putLine2(beanName+".get"+NameUtil.capName(name)+"().addAll("+getDummyValue(name, type, true, true, false)+");");
		} else if (structure.equals("map")) {
			buf.putLine2(beanName+".get"+NameUtil.capName(name)+"().put("+getDummyValue(name, attribute.getKey(), true, true, false)+", "+getDummyValue(name, type, true, true, false)+");");
		}
		return buf.get();
	}

	public String createSampleValuesForReferences(Element element, List<Reference> references) {		
		return createSampleValuesForReferences(element, references, 0);
	}
	
	public String createSampleValuesForReferences(Element element, List<Reference> references, int indent) {
		Buf buf = new Buf();
		buf.setIndent(indent);
		Iterator<Reference> iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			if (FieldUtil.isInverse(reference))
				continue;
			buf.put(createSampleValueForReference(element, reference, 0, true));
		}
		return buf.get();
	}

	public String createSampleValueForReference(Element element, Reference reference, int indent) {
		return createSampleValueForReference(element, reference, indent, false);
	}

	public String createSampleValueForReference(Element element, Reference reference, int indent, boolean includeInvocation) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(this.namespace, element, reference);
		//String entityPackageName = entityClassHelper.getEntityPackageName();
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		
		String elementName = NameUtil.uncapName(element.getName());
		String elementNamespace = TypeUtil.getNamespace(element.getType());
		String referenceNamespace = TypeUtil.getNamespace(reference.getType());
		String packageName = TypeUtil.getPackageName(element.getType());
		String className = TypeUtil.getClassName(element.getType());
		String beanName = NameUtil.uncapName(className);
		
		if (useForEntityFixtures) {
			packageName = DataLayerHelper.getInferredEntityPackageName(this.namespace, element);
			className = DataLayerHelper.getInferredEntityClassName(this.namespace, element);
			beanName = DataLayerHelper.getInferredEntityBeanName(this.namespace, element);
		}
		
		String structure = reference.getStructure();
		String referenceName = reference.getName();
		String refrenceType = reference.getType();
		//String javaName = TypeUtil.getJavaName(type);
		boolean isElement = context.getElementByType(refrenceType) != null;
		boolean isEnum = context.getEnumerationByType(refrenceType) != null;
		
		String packagePrefix = "";
		Element referenceElement = context.getElementByType(refrenceType);
		String referencePackageName = TypeUtil.getPackageName(refrenceType);
		if (referenceElement != null && referencePackageName.equals(packageName) == false) {
			//String referenceFixtureName = FixtureUtil.getFixtureNameFromPackageName(referencePackageName);
			//String referenceFixtureClassName = NameUtil.capName(referenceFixtureName);
			
			//TODO any special consideration here for bi-directional relationships?
			if (reference.getContained() && useForEntityFixtures) {
				/*
				 * TODO
				 * We currently derive "containedReferenceName" from "referenceType".  
				 * THis results in: all fields that may happen to have this type being stored into the same table.
				 * In the future - we should have a setting for this (included as part of the containment relationship).
				 * We should also have a setting to make two fields of the same type end up getting stored into separate tables. 
				 */
				String containedFieldClassName = ModelLayerHelper.getContainedFieldClassName(element, reference);
				//String referenceFixtureClassName = DataLayerHelper.getEntityFixtureClassName(this.namespace);
				
				String newLocalPart = NameUtil.uncapName(containedFieldClassName);
				refrenceType = TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, newLocalPart);
				//packagePrefix = referenceFixtureClassName;
				packagePrefix = "";

			} else {
				String fixtureClassName = null;
				if (useForEntityFixtures)
					fixtureClassName = DataLayerHelper.getEntityFixtureClassName(referenceElement);
				else fixtureClassName = ModelLayerHelper.getFixtureClassName(referenceElement);
				//String referenceFixtureQualifiedName = DataLayerHelper.getEntityFixtureQualifiedName(referenceElement);
				//beanName = referenceFixtureClassName + "." + beanName;
				packagePrefix = fixtureClassName;
			}
		}
		
		boolean needsOwner = false;
		boolean needsEmpty = false;
		boolean isUnique = FieldUtil.isUnique(reference);
		boolean isInverse = FieldUtil.isInverse(reference);
		
		String ownerName = beanName;
		boolean hasExistingValue = isInverse == true;
		//TODO any special consideration here for bi-directional relationships?
		if (!reference.getDerived() && (reference.getContained() || reference.getMappedBy() != null || FieldUtil.hasInverse(element, reference, referenceElement)))
			needsOwner = true;
		
		//only proceed if Bi-directional OR for Entity
		if (!FieldUtil.hasInverse(element, reference, referenceElement) && !useForEntityFixtures)
			needsOwner = false;
		
		boolean selfReferencing = ElementUtil.isSelfReferencing(element, reference);
		if (selfReferencing) {
			needsEmpty = true;
		}
		
		String itemKey = null;
		String itemValue = null;
		if (hasExistingValue) {
			itemValue = referenceName;
		} else if (structure.equals("item")) {
			itemValue = getDummyValue(ownerName, referenceName, refrenceType, null, true, false, needsEmpty, needsOwner, isUnique);
		} else if (structure.equals("list") && isEnum) {
			itemValue = getDummyValue(ownerName, referenceName, refrenceType, null, true, true, needsEmpty, needsOwner, false);
		} else if (structure.equals("list") && isElement) {
			itemValue = getDummyValue(ownerName, referenceName, refrenceType, null, true, true, needsEmpty, needsOwner, false);
		} else if (structure.equals("set") && isEnum) {
			itemValue = getDummyValue(ownerName, referenceName, refrenceType, null, true, true, needsEmpty, needsOwner, false);
		} else if (structure.equals("set") && isElement) {
			itemValue = getDummyValue(ownerName, referenceName, refrenceType, null, true, true, needsEmpty, needsOwner, false);
		} else if (structure.equals("map")) {
			itemKey = getDummyValue(ownerName, referenceName, reference.getKey(), null, true, true, needsEmpty, needsOwner, false);
			itemValue = getDummyValue(ownerName, referenceName, refrenceType, null, true, true, needsEmpty, needsOwner, false);
		}
		
		if (!elementNamespace.equals(referenceNamespace)) {
			//if (useForEntityFixtures)
			//	fixtureClassName = DataLayerHelper.getEntityFixtureClassName(referenceElement);
			//else fixtureClassName = ModelLayerHelper.getFixtureClassName(referenceElement);
			//beanName = fixtureClassName + "." + beanName;
		}
		
		if (!packagePrefix.isEmpty())
			packagePrefix += ".";
		
		Buf buf = new Buf();
		buf.setIndent(indent);
		if (includeInvocation) {
			if (hasExistingValue) {
				buf.putLine2(beanName+".set"+NameUtil.capName(referenceName)+"("+referenceName+");");
			} else if (structure.equals("item")) {
				buf.putLine2(beanName+".set"+NameUtil.capName(referenceName)+"("+packagePrefix + itemValue+");");
			} else if (structure.equals("list") && isEnum) {
				buf.putLine2(beanName+".get"+NameUtil.capName(referenceName)+"().add("+packagePrefix + itemValue+");");
			} else if (structure.equals("list") && isElement) {
				buf.putLine2(beanName+".get"+NameUtil.capName(referenceName)+"().addAll("+packagePrefix + itemValue+");");
			} else if (structure.equals("set") && isEnum) {
				buf.putLine2(beanName+".get"+NameUtil.capName(referenceName)+"().add("+packagePrefix + itemValue+");");
			} else if (structure.equals("set") && isElement) {
				buf.putLine2(beanName+".get"+NameUtil.capName(referenceName)+"().addAll("+packagePrefix + itemValue+");");
			} else if (structure.equals("map")) {
				buf.putLine2(beanName+".get"+NameUtil.capName(referenceName)+"().put("+packagePrefix + itemKey+", "+packagePrefix + itemValue+");");
			}
		} else {
			buf.put(itemValue);
		}
		
		return buf.get();
	}

	public String getDummyValue(String name, String type, boolean addIndex, boolean isCollection, boolean isUnique) {
		return getDummyValue(null, name, type, null, addIndex, isCollection, false, false, isUnique);	
	}
	
	public String getDummyValue(String name, String type, String seedText, boolean addIndex, boolean isCollection, boolean isUnique) {
		return getDummyValue(null, name, type, seedText, addIndex, isCollection, false, false, isUnique);	
	}
	
	public String getDummyValue(String owner, String name, String type, String seedText, boolean addIndex, boolean isCollection, boolean needsEmpty, boolean needsOwner, boolean isUnique) {
		String javaName = TypeUtil.getJavaName(type);
		String simpleName = NameUtil.getSimpleName(javaName);
		
		if (!isUnique)
			seedText = null;
		if (javaName.equals("java.lang.String") || javaName.equals("java.lang.Object")) {
			String dummyValue = "\"dummy"+NameUtil.capName(name)+"\"";
			if (seedText != null)
				return addIndex ? dummyValue + " + value + "+seedText : dummyValue;
			return addIndex ? dummyValue + " + value" : dummyValue;
			
		} else if (javaName.equals("java.lang.Integer")) {
			if (seedText == null)
				return addIndex ? "(int) value" : "1";
			return addIndex ? "(int) value + "+seedText : seedText;
		
		} else if (javaName.equals("java.lang.Short")) {
			if (seedText == null)
				return addIndex ? "(short) value" : "1";
			return addIndex ? "(short) value + "+seedText : seedText;

		} else if (javaName.equals("java.lang.Long")) {
			if (seedText == null)
				return addIndex ? "(long) value" : "1L";
			return addIndex ? "(long) value + "+seedText : seedText;

		} else if (javaName.equals("java.lang.Double")) {
			if (seedText == null)
				return addIndex ? "(double) value" : "1.0D";
			return addIndex ? "(double) value + "+seedText : seedText+".0D";
		
		} else if (javaName.equals("java.lang.Float")) {
			if (seedText == null)
				return addIndex ? "(float) value" : "1.0F";
			return addIndex ? "(float) value + "+seedText : seedText+".0F";

		} else if (javaName.equals("java.lang.Boolean")) {
			if (seedText == null)
				return "false";
			return "true";
		
		} else if (javaName.equals("java.util.Date")) {
			if (seedText == null)
				return addIndex ? "new Date(value + 1000L)" : "new Date(1000L)";
			return addIndex ? "new Date(value + "+seedText+")" : "new Date((long) +"+seedText+")";
		
		} else if (simpleName.equals("byte[]")) {
			if (seedText != null)
				return "new String("+seedText+").getBytes()";
			String dummyValue = "\"dummy"+NameUtil.capName(name)+"\"";
			return addIndex ? "new String("+dummyValue+" + value).getBytes()" : "new String("+dummyValue+").getBytes()";

		} else if (context.getEnumerationByType(type) != null) {
			Enumeration enumeration = context.getEnumerationByType(type);
			EEnum eEnum = context.getEnumerationCache().get(javaName);
			EEnumLiteral literal = eEnum.getEEnumLiteral(0);
			String value = literal.getName().toUpperCase();
			return simpleName + "." + value;

		//} else if (context.getElementByType(type) != null) {
		} else {
			//Element element = context.getElementByType(type);
			//if (isGenerated(simpleName)) {
				StringBuffer buf = new StringBuffer();
				if (needsEmpty) {
					buf.append("createEmpty");
					if (isCollection)
						buf.append("List");
					buf.append("_"+simpleName);
					if (useForEntityFixtures)
						buf.append("Entity");
					buf.append("()");
					
				} else {
					buf.append("create");
					if (isCollection) {
						buf.append("List");
						buf.append("_"+simpleName);
						if (useForEntityFixtures)
							buf.append("Entity");
						if (needsOwner && owner != null)
							buf.append("("+owner+", 2)");
						else buf.append("(2)");
						
					} else {
						buf.append("_"+simpleName);
						if (useForEntityFixtures)
							buf.append("Entity");
						if (needsOwner && owner != null)
							buf.append("("+owner+", value)");
						else buf.append("(value)");
					}
				}
				return buf.toString();
			//}
			//return null;
		}
	}

	public void initializeImportedClasses(ModelClass modelClass) throws Exception {
		//modelClass.addImportedClass("java.io.Serializable");
	}

}

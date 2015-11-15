package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import nam.model.Namespace;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Type;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.Assert;
import org.aries.util.BaseUtil;
import org.aries.util.ClassUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.TypeMap;
import org.aries.util.Validator;
import org.eclipse.bpel.model.Variable;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;
import org.w3c.dom.Element;


public class TypeUtil extends BaseUtil {
	
	public static Object getKey(Type type) {
		return type.getType();
	}
	
	public static String getLabel(Type type) {
		return type.getName();
	}
	
	public static boolean isEmpty(Type type) {
		if (type == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Type> typeList) {
		if (typeList == null  || typeList.size() == 0)
			return true;
		Iterator<Type> iterator = typeList.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			if (!isEmpty(type))
				return false;
		}
		return true;
	}
	
	public static String toString(Type type) {
		if (isEmpty(type))
			return "Type: [uninitialized] "+type.toString();
		String text = type.toString();
		return text;
	}
	
	public static String toString(Collection<Type> typeList) {
		if (isEmpty(typeList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Type> iterator = typeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Type type = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(type);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Type create() {
		Type type = new Type();
		initialize(type);
		return type;
	}
	
	public static void initialize(Type type) {
		if (type.getCached() == null)
			type.setCached(false);
		if (type.getPublic() == null)
			type.setPublic(false);
	}
	
	public static boolean validate(Type type) {
		if (type == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Type> typeList) {
		Validator validator = Validator.getValidator();
		Iterator<Type> iterator = typeList.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			//TODO break or accumulate?
			validate(type);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Type> typeList) {
		Collections.sort(typeList, createTypeComparator());
	}
	
	public static Collection<Type> sortRecords(Collection<Type> typeCollection) {
		List<Type> list = new ArrayList<Type>(typeCollection);
		Collections.sort(list, createTypeComparator());
		return list;
	}
	
	public static Comparator<Type> createTypeComparator() {
		return new Comparator<Type>() {
			public int compare(Type type1, Type type2) {
				Object key1 = getKey(type1);
				Object key2 = getKey(type2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Type clone(Type type) {
		if (type == null)
			return null;
		Type clone = create();
		clone.setName(ObjectUtil.clone(type.getName()));
		clone.setType(ObjectUtil.clone(type.getType()));
		clone.setKey(ObjectUtil.clone(type.getKey()));
		clone.setStructure(ObjectUtil.clone(type.getStructure()));
		clone.setNamespace(ObjectUtil.clone(type.getNamespace()));
		clone.setCached(ObjectUtil.clone(type.getCached()));
		clone.setPublic(ObjectUtil.clone(type.getPublic()));
		return clone;
	}
	
	public static List<Type> clone(List<Type> typeList) {
		if (typeList == null)
			return null;
		List<Type> newList = new ArrayList<Type>();
		Iterator<Type> iterator = typeList.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			Type clone = clone(type);
			newList.add(clone);
		}
		return newList;
	}


	public static String getDefaultNamespace() {
		return "http://www.w3.org/2001/XMLSchema";
	}
	
	public static String getDefaultIdType() {
		return "{"+getDefaultNamespace()+"}long";
	}

	public static String getDefaultType(String primitiveType) {
		Assert.isTrue(CodeGenUtil.isJavaDefaultType(primitiveType), "Not a default type: "+primitiveType);
		return "{"+getDefaultNamespace()+"}" + NameUtil.uncapName(primitiveType);
	}
	
	public static boolean isDefaultType(String type) {
		String namespace = getNamespace(type);
		return namespace.equals(getDefaultNamespace());
	}
	
	public static boolean isValidType(String type) {
		try {
			NameUtil.validateType(type);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static void validateType(String type) {
		NameUtil.validateType(type);
	}

	public static String getClassName(String type) {
		if (type == null)
			return null;
		String className = NameUtil.getClassNameFromType(type);
		return className;
	}

	public static String getPackageName(String type) {
		if (type == null)
			return null;
		String packageName = NameUtil.getPackageNameFromType(type);
		return packageName;
	}

	public static String getQualifiedName(String type) {
		validateType(type);
		String className = getClassName(type);
		String packageName = getPackageName(type);
		return packageName+"."+className;
	}
	
	public static String getJavaName(String type) {
		return getQualifiedName(type);
	}

	public static String getNamespace(String type) {
		validateType(type);
		int openBracketIndex = type.indexOf("{");
		int closeBracketIndex = type.indexOf("}");
		String namespace = type.substring(openBracketIndex+1, closeBracketIndex);
		return namespace;
	}
	
	public static String getLocalPart(String type) {
		validateType(type);
		int closeBracketIndex = type.indexOf("}");
		String simpleName = type.substring(closeBracketIndex+1);
		return simpleName;
	}
	
	public static String getServiceNameFromText(String text) {
		String serviceName = text;
		boolean filterServiceName = false;
		//TODO pull this out into some filter
		if (filterServiceName) {
			if (serviceName.endsWith("_Service"))
				serviceName = serviceName.replace("_Service", "");
			if (serviceName.endsWith("Service"))
				serviceName = serviceName.replace("Service", "");
			if (serviceName.endsWith("_PortType"))
				serviceName = serviceName.replace("_PortType", "");
			if (serviceName.endsWith("PortType"))
				serviceName = serviceName.replace("PortType", "");
		}
		return serviceName; 
	}
	
	public static String getNsPrefixFromEPackage(EPackage ePackage) {
		String ePackageName = ePackage.getName();
		String[] sections = ePackageName.split("\\.");
		for (int i=sections.length-1; i >= 0; i--) {
			String nsPrefix = sections[i];
			if (!nsPrefix.equals("_0")) {
				return nsPrefix;
			}
		}
		return null;
	}

	public static String getJavaPackageFromEPackage(EPackage ePackage) {
		String ePackageName = ePackage.getName().toLowerCase();
		String[] sections = ePackageName.split("\\.");
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < sections.length; i++) {
			String section = sections[i];
			//section = section.replaceAll("_", "");
			section = section.replaceAll("[0-9]", "");
			if (section.length() == 0)
				break;
			if (buf.length() > 0)
				buf.append(".");
			buf.append(section);
		}
		return buf.toString();
	}

	
	public static String getJavaTypeFromEClassifer(EClassifier eClassifier) {
		String packageName = getJavaPackageFromEClassifer(eClassifier);
		String typeName = packageName+"."+eClassifier.getName();
		return typeName;
	}
	
	public static String getJavaPackageFromEClassifer(EClassifier eClassifier) {
//		System.out.println(">>>"+int.class.getCanonicalName());
//		System.out.println(">>>"+int.class.getName());
//		System.out.println(">>>"+int.class.getSimpleName());
//		System.out.println(">>>"+int.class.getComponentType());
		String name = eClassifier.getName();
		if (CodeGenUtil.isJavaPrimitiveType(name))
			return name;
		if (CodeGenUtil.isJavaLangType(name))
			return "java.lang";
		return name;
	}

	
	public static String getTypeFromClass(Class<?> classObject) {
		String className = classObject.getCanonicalName();
		return getTypeFromClass(className);
	}

	public static String getTypeFromClass(String className) {
		String simpleName = NameUtil.getSimpleName(className);
		if (ClassUtil.isJavaDefaultType(simpleName) || simpleName.equals("void"))
			return org.aries.util.TypeUtil.getTypeFromDefaultType(simpleName);
		String typeName = TypeMap.INSTANCE.getTypeName(className);
		return typeName;
	}

	public static String getTypeFromVariable(Variable variable) {
		Message messageType = variable.getMessageType();
		if (messageType != null) {
			//QName qName = messageType.getQName();
			//String typeName = getTypeFromQName(qName);
			@SuppressWarnings("unchecked") Collection<Part> parts = messageType.getParts().values();
			//TODO take out this assumption and resolve some other way...
			Assert.isTrue(parts.size() == 1, "Incoming message must have only one part");
			Part part = (Part) parts.iterator().next();
			String typeName = getTypeFromMessagePart(part);
			return typeName;
		}
		XSDTypeDefinition xsdType = variable.getType();
		//Assert.notNull(xsdType, "XSDType should not be null");
		if (xsdType != null) {
			XSDSimpleTypeDefinition simpleType = xsdType.getSimpleType();
			if (simpleType != null) {
				throw new RuntimeException("NOT SUPPORTED YET");
			} else {
				String namespace = xsdType.getTargetNamespace();
				String localPart = xsdType.getQName();
				Assert.notNull(xsdType, "Namespace should not be null");
				Assert.notNull(xsdType, "LocalPart should not be null");
				String typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, localPart);
				return typeName;
			}
		}
		return null;
	}

	public static String getTypeFromMessage(Message message) {
		String typeName = getTypeFromQName(message.getQName());
		return typeName;
	}
	
	public static String getTypeFromMessagePart(Part part) {
		QName elementQName = part.getElementName();
		if (elementQName != null) {
			String typeName = getTypeFromQName(elementQName);
			return typeName;
		}
		QName typeQName = part.getTypeName();
		if (typeQName != null) {
			String typeName = getTypeFromQName(typeQName);
			return typeName;
		}
		XSDElementDeclaration partElementDeclaration = part.getElementDeclaration();
		if (partElementDeclaration != null) {
			XSDTypeDefinition partTypeDefinition = partElementDeclaration.getTypeDefinition();
			if (partTypeDefinition != null) {
				String namespace = partTypeDefinition.getTargetNamespace();
				String name = partTypeDefinition.getName();
				String typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, name);
				//TODO may need translation here
				return typeName;
			}
			String typeName = partElementDeclaration.getQName();
			//TODO may need translation here
			return typeName;
		}
		XSDTypeDefinition partTypeDefinition = part.getTypeDefinition();
		if (partTypeDefinition != null) {
			String typeName = partTypeDefinition.getQName();
			//TODO may need translation here
			return typeName;
		}
		return null;
	}

	public static String getNameFromMessage(Message message) {
		String name = message.getQName().getLocalPart();
		name = NameUtil.uncapName(name);
		return name;
	}
	
	public static String getNameFromMessagePart(Part part) {
		String name = part.getElementName().getLocalPart();
		name = NameUtil.uncapName(name);
		return name;
	}
	
	
//	protected String getTypeFromPartOLD(Part part) {
//		QName elementName = part.getElementName();
//		String parameterType = "String";
//		if (part.getTypeName() != null)
//			parameterType = part.getTypeName().getLocalPart();
//		ArrayList<String> arrayList = new ArrayList<String>();
//		arrayList.addAll(CodeGenUtil.getJavaDefaultTypes());
//		Collections.sort(arrayList);
//		if (parameterType.equals("string"))
//			parameterType = NameUtil.capName(parameterType);
//		if (!CodeGenUtil.isJavaDefaultType(parameterType)) {
//			EClass eClass = getElementByJavaType(parameterType);
//			if (eClass != null)
//				return parameterType;
//		}
//		return parameterType;
//	}
	
	public static String getTypeFromNamespace(Namespace namespace) {
		String typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace.getUri(), namespace.getName());
		return typeName;
	}
	
	public static String getTypeFromElement(Element element) {
		String typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(element.getNamespaceURI(), element.getLocalName());
		return typeName;
	}

	public static String getTypeFromQName(QName qName) {
		String typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(qName.getNamespaceURI(), qName.getLocalPart());
		return typeName;
	}

	public static String getTypeFromEClass(EClass eClass) {
		String typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(eClass.getEPackage().getNsURI(), eClass.getName());
		return typeName;
	}

	public static String getTypeFromEClassifier(EClassifier eType) {
		String typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(eType.getEPackage().getNsURI(), eType.getName());
		return typeName;
	}

	public static String getTypeFromEPackageAndEClass(EPackage ePackage, EClass eClass) {
		String typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(ePackage.getNsURI(), eClass.getName());
		return typeName;
	}

	public static String getTypeFromPackageAndClass(String packageName, String className) {
		String namespaceURI = NameUtil.getNamespaceURIFromPackageName(packageName);
		String typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespaceURI, NameUtil.uncapName(className));
		return typeName;
	}

	public static String getTypeFromJavaType(String javaQualifiedName) {
		String className = NameUtil.getClassName(javaQualifiedName);
		String packageName = NameUtil.getPackageName(javaQualifiedName);
		return getTypeFromPackageAndClass(packageName, className);
	}

	public static String getTypeFromNamespaceAndLocalPart(String namespace, String localPart) {
		return org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, localPart);
	}
	
	public static String getTypeFromNamespaceAndLocalPart(Namespace namespace, String localPart) {
		return org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace.getUri(), localPart);
	}
	
	public static boolean doesResultExist(Result result) {
		if (result == null)
			return false;
		if (result.getType() == null)
			return false;
		String type = result.getType();
		String className = TypeUtil.getClassName(type);
		if (className.equals("void"))
			return false;
		return true;
	}

	public static boolean isImportedClassRequired(String type) {
//		if ((type.endsWith(".Message") || 
//			type.endsWith(".Request") || 
//			type.endsWith(".Response")) && 
//			!type.startsWith("org.aries"))
//			return false;
		if (!type.contains(".")) {
			//TODO need extra checking here:
			//TODO if not default type here then throw Exception?
			if (!CodeGenUtil.isJavaDefaultType(type))
				throw new RuntimeException("Unexpected type: "+type);
			return false;
		}
		String packageName = NameUtil.getPackageName(type);
		String className = NameUtil.getClassName(type);
		boolean value = isImportedClassRequired(packageName, className);
		return value;
	}
	
	public static boolean isImportedClassRequired(String packageName, String className) {
		if (packageName.equals("java.lang") || CodeGenUtil.isJavaDefaultType(className))
			return false;
		return true;
	}

	public static <T extends Type> void sortByInstanceName(List<T> types) {
		Collections.sort(types, new Comparator<T>() {
			public int compare(T element1, T element2) {
				String name1 = element1.getName();
				String name2 = element2.getName();
				return name1.compareTo(name2);
			}
		});
	}

	public static <T extends Type> void sortByPackageName(List<T> types) {
		Collections.sort(types, new Comparator<T>() {
			public int compare(T element1, T element2) {
				String type1 = element1.getType();
				String type2 = element2.getType();
				String packageName1 = TypeUtil.getPackageName(type1);
				String packageName2 = TypeUtil.getPackageName(type2);
				return packageName1.compareTo(packageName2);
			}
		});
	}

	public static <T extends Type> void sortByClassName(List<T> types) {
		Collections.sort(types, new Comparator<T>() {
			public int compare(T element1, T element2) {
				String type1 = element1.getType();
				String type2 = element2.getType();
				String className1 = TypeUtil.getClassName(type1);
				String className2 = TypeUtil.getClassName(type2);
				return className1.compareTo(className2);
			}
		});
	}
	
	public static <T extends Type> void sortByQualifiedName(List<T> types) {
		Collections.sort(types, new Comparator<T>() {
			public int compare(T element1, T element2) {
				String type1 = element1.getType();
				String type2 = element2.getType();
				String qualifiedName1 = TypeUtil.getPackageName(type1)+"."+TypeUtil.getClassName(type1);
				String qualifiedName2 = TypeUtil.getPackageName(type2)+"."+TypeUtil.getClassName(type2);
				return qualifiedName1.compareTo(qualifiedName2);
			}
		});
	}

	public static <T extends Type> void sortByLocalPart(List<T> types) {
		Collections.sort(types, new Comparator<T>() {
			public int compare(T type1, T type2) {
				String localPart1 = TypeUtil.getLocalPart(type1.getType());
				String localPart2 = TypeUtil.getLocalPart(type2.getType());
				return localPart1.compareTo(localPart2);
			}
		});
	}

	public static String getStructuredName(Type type) {
		return getStructuredName(type.getStructure(), false);
	}
	
	public static String getStructuredName(Type type, boolean overrideDefaultForMap) {
		return getStructuredName(type.getStructure(), overrideDefaultForMap);
	}
	
	public static String getStructuredName(Parameter parameter) {
		return getStructuredName(parameter.getConstruct(), false);
	}

	public static String getStructuredName(String structure, boolean overrideDefaultForMap) {
		if (structure.equals("map") && overrideDefaultForMap)
			return "List";
		return NameUtil.capName(structure);
	}
	
	public static String getStructuredType(Type type) {
		return getStructuredType(type.getStructure(), type.getType(), type.getKey(), false);
	}
	
	public static String getStructuredType(Type type, boolean overrideDefaultForMap) {
		return getStructuredType(type.getStructure(), type.getType(), type.getKey(), overrideDefaultForMap);
	}
	
	public static String getStructuredType(Parameter parameter) {
		return getStructuredType(parameter.getConstruct(), parameter.getType(), parameter.getKey(), false);
	}
	
	public static String getStructuredType(String structure, String type, String key, boolean overrideDefaultForMap) {
		String className = type;
		if (type.startsWith("{"))
			className = getClassName(type);
		if (structure.equals("list")) {
			return "List<"+className+">";
		} else if (structure.equals("set")) {
			return "Set<"+className+">";
		} else if (structure.equals("map")) {
			if (overrideDefaultForMap)
				return "List<"+className+">";
			String keyClassName = getClassName(key);
			return "Map<"+keyClassName+", "+className+">";
		} else if (structure.equals("collection")) {
			return "Collection"+className+">";
		}
		return className;
	}

	public static String getStructuredParam(Type type) {
		return getStructuredParam(type.getStructure(), type.getType(), false);
	}
	
	public static String getStructuredParam(Type type, boolean overrideDefaultForMap) {
		return getStructuredParam(type.getStructure(), type.getType(), overrideDefaultForMap);
	}
	
	public static String getStructuredParam(Parameter parameter) {
		return getStructuredParam(parameter.getConstruct(), parameter.getType(), false);
	}

	public static String getStructuredParam(String structure, String type, boolean overrideDefaultForMap) {
		String localPart = null;
		if (type.startsWith("{"))
			localPart = getLocalPart(type);
		else localPart = NameUtil.uncapName(type);
		if (structure.equals("list")) {
			localPart += "List";
		} else if (structure.equals("set")) {
			localPart += "Set";
		} else if (structure.equals("map")) {
			if (overrideDefaultForMap)
				localPart += "List";
			else localPart += "Map";
		} else if (structure.equals("collection")) {
			localPart += "Collection";
		}
		return localPart;
	}
	
}

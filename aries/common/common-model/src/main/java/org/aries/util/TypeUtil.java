package org.aries.util;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.Variable;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;
import org.aries.Assert;


public class TypeUtil {

	public static String getDefaultNamespace() {
		return "http://www.w3.org/2001/XMLSchema";
	}
	
	public static String getDefaultIdType() {
		return "{"+getDefaultNamespace()+"}long";
	}

	public static String getDefaultType(String primitiveType) {
		Assert.isTrue(ClassUtil.isJavaDefaultType(primitiveType), "Not a default type: "+primitiveType);
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
		if (ClassUtil.isJavaPrimitiveType(name))
			return name;
		if (ClassUtil.isJavaLangType(name))
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
			return TypeUtil.getTypeFromDefaultType(simpleName);
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
				String typeName = TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, localPart);
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
				String typeName = getTypeFromNamespaceAndLocalPart(namespace, name);
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

	
	public static String getTypeFromQName(QName qName) {
		String typeName = getTypeFromNamespaceAndLocalPart(qName.getNamespaceURI(), qName.getLocalPart());
		return typeName;
	}

	public static String getTypeFromNamespaceAndLocalPart(String namespace, String localPart) {
		String typeName = "{"+getFilteredNamespace(namespace)+"}"+getFilteredLocalPart(localPart);
		return typeName;
	}

	public static String getTypeFromDefaultType(String primitiveType) {
		Assert.isTrue(CodeGenUtil.isJavaDefaultType(primitiveType) || primitiveType.equals("void"), "Type must be primitive type");
		String typeName = "{http://www.w3.org/2001/XMLSchema}"+getFilteredLocalPart(primitiveType);
		return typeName;
	}

	public static String getDerivedType(String initialType, String newLocalPart) {
		int indexOfClosingBracket = initialType.indexOf("}");
		String namespaceBlock = initialType.substring(0, indexOfClosingBracket+1);
		String derivedType = namespaceBlock + newLocalPart;
		return derivedType;
	}
	
	public static String getFilteredNamespace(String namespace) {
		String name = new String(namespace); 
		//TODO handle with external property
		if (name.contains("emf"))
			name = getDefaultNamespace();
		return name;
	}

	public static String getFilteredLocalPart(String localPart) {
		String name = NameUtil.uncapName(localPart); 
		//String name = localPart; 
		//TODO handle with external property
		if (name.endsWith("Object"))
			name = name.replace("Object", "");
		return name;
	}

	public static String getFilteredElementName(String elementName) {
		elementName = new String(elementName);
		if (elementName.endsWith("Type"))
			elementName = elementName.substring(0, elementName.length()-4);
		return elementName;
	}


}

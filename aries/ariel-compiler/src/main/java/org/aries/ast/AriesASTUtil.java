package org.aries.ast;

import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Namespace;
import nam.model.util.TypeUtil;

import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.ast.node.AbstractNode;
import org.aries.ast.node.CommandNode;
import org.aries.ast.node.PropertyNode;
import org.aries.ast.node.NetworkNode;
import org.aries.ast.node.TypeNode;
import org.aries.util.ClassUtil;
import org.aries.util.NameUtil;
import org.eclipse.emf.codegen.util.CodeGenUtil;

import aries.generation.engine.GenerationContext;


public class AriesASTUtil {

	public static GenerationContext context;
	
	
	public static boolean isName(String text) {
		if (text.charAt(0) == '$')
			return true;
		if (Character.isLetter(text.charAt(0)))
			return true;
		return false;
	}
	
	public static boolean isCommand(String text) {
		if (text.equals("post"))
			return true;
		if (text.equals("reply"))
			return true;
		if (text.equals("send"))
			return true;
		if (text.equals("throw"))
			return true;
		return false;
	}

	public static boolean isEndpoint(String text) {
		if (text.equals("invoke"))
			return true;
		if (text.equals("listen"))
			return true;
		if (text.equals("publish"))
			return true;
		if (text.equals("receive"))
			return true;
		if (text.equals("send"))
			return true;
		if (text.equals("subscribe"))
			return true;
		return false;
	}

	public static boolean isStatement(String text) {
		if (text.equals("if"))
			return true;
		if (text.equals("while"))
			return true;
		if (text.equals("for"))
			return true;
		if (text.equals("do"))
			return true;
		if (text.equals("continue"))
			return true;
		if (text.equals("break"))
			return true;
		if (text.equals("throw"))
			return true;
		if (text.equals("return"))
			return true;
		return false;
	}

	public static boolean isRelationalOp(String text) {
		if (text.equals("="))
			return true;
		if (text.equals("=="))
			return true;
		if (text.equals("!="))
			return true;
		if (text.equals("<"))
			return true;
		if (text.equals(">"))
			return true;
		if (text.equals("<="))
			return true;
		if (text.equals(">="))
			return true;
		return false;
	}
	

	public static String getFieldTypeFromTypeNode(TypeNode typeNode) throws Exception {
		return getFieldTypeFromTypeNode(typeNode, "");
	}
	
	public static String getFieldTypeFromTypeNode(TypeNode typeNode, String suffix) throws Exception {
//		if (typeNode == null)
//			System.out.println();
		List<TypeNode> typeArguments = typeNode.getTypeArguments();
		String construct = typeNode.getConstruct();
		String type = typeNode.getType();
		
		if (construct.equals("item")) {
			if (typeArguments.size() == 0) {
				return getFieldTypeFromType(type, suffix);
			} else {
				TypeNode typeArgument = typeArguments.get(0);
				return getFieldTypeFromTypeNode(typeArgument);
			}
			
		} else if (construct.equals("list") || type.equals("set")) {
			if (typeArguments.size() == 0) {
				return getFieldTypeFromType(type, suffix);
			} else {
				TypeNode typeArgument = typeArguments.get(0);
				return getFieldTypeFromTypeNode(typeArgument);
			}
			
		} else if (construct.equals("map")) {
			if (typeArguments.size() == 0) {
				return getFieldTypeFromType(type, suffix);
			} else {
				TypeNode typeArgument = typeArguments.get(1);
				return getFieldTypeFromTypeNode(typeArgument);
			}
			
		} else if (CodeGenUtil.isJavaLangType(type)) {
			return "{"+TypeUtil.getDefaultNamespace()+"}"+type.toLowerCase() + suffix;
		}
			
		return getFieldType(type + suffix);
	}

	private static String getFieldTypeFromType(String type, String suffix) {
		Element element = context.getElementByName(type);
		//Assert.notNull(element, "Element not found: "+type);
		if (element == null) {
			type += suffix;
			element = context.getElementByName(type);
		}
		if (element != null) {
			return element.getType();
		}
		if (CodeGenUtil.isJavaLangType(type)) {
			return "{"+TypeUtil.getDefaultNamespace()+"}"+type.toLowerCase() + suffix;
		}
		return type;
	}

	public static String getFieldType(String type) throws Exception {
		if (type == null)
			return null;
		
		String typePrefix = null;
		String typeLocalPart = type;
		if (type.contains(":")) {
			int indexOf = type.indexOf(":");
			typePrefix = type.substring(0, indexOf);
			typeLocalPart = type.substring(indexOf+1);
		}

		if (typePrefix != null && typePrefix.equals("xs")) {
			String fieldType = "{" + TypeUtil.getDefaultNamespace() + "}" + NameUtil.uncapName(typeLocalPart);
			return fieldType;
			
		} else if (typePrefix != null) {
			Namespace namespace = context.getNamespaceByPrefix(typePrefix);
			Assert.notNull(namespace, "Namespace not found for prefix: "+typePrefix);
			String fieldType = "{" + namespace.getUri() + "}" + NameUtil.uncapName(typeLocalPart);
			Element fieldElement = context.getElementByType(fieldType);
			Assert.notNull(fieldElement, "Element not found for type: "+fieldType);
			return fieldType;
		
		} else if (ClassUtil.isJavaPrimitiveType(typeLocalPart) || ClassUtil.isJavaLangType(typeLocalPart)) {
			String fieldType = "{" + TypeUtil.getDefaultNamespace() + "}" + NameUtil.uncapName(typeLocalPart);
			return fieldType;

		} else {
			List<Namespace> namespaces = context.getNamespaces();
			Iterator<Namespace> iterator = namespaces.iterator();
			while (iterator.hasNext()) {
				Namespace namespace = iterator.next();
				String fieldType = "{" + namespace.getUri() + "}" + NameUtil.uncapName(typeLocalPart);
				Element fieldElement = context.getElementByType(fieldType);
				if (fieldElement != null) {
					return fieldType;
				}
			}
		}
		return null;
	}
	
	public static String getFieldKeyTypeFromTypeNodeType(TypeNode typeNode) throws Exception {
		String construct = typeNode.getConstruct();
		String type = typeNode.getType();
		if (construct.equals("map")) {
			TypeNode typeArgument = typeNode.getTypeArguments().get(0);
			return getFieldTypeFromTypeNode(typeArgument);
		} else {
			return null;
		}
	}
	
	public static String getPropertyValue(NetworkNode protocolNode, String name) {
		PropertyNode propertyNode = protocolNode.getPropertyNode(name);
		if (propertyNode != null)
			return propertyNode.getValue();
		return null;
	}
	
	
	public static void printCommandSource(CommandNode commandNode) {
		System.out.println(">>> "+getNodeSource(commandNode));
	}
	
	public static String getNodeSource(AbstractNode commandNode) {
		StringBuffer buf = new StringBuffer();
		Iterator<String> iterator = commandNode.getTokens().iterator();
		while (iterator.hasNext()) {
			String token = iterator.next();
			buf.append(token);
		}
		return buf.toString();
	}
	
	public static void trimCommandSource(List<String> commandSource) {
		while (!commandSource.isEmpty()) {
			int lastIndex = commandSource.size() - 1;
			String lastToken = commandSource.get(lastIndex);
			if (StringUtils.isEmpty(lastToken.trim())) {
				commandSource.remove(lastIndex);
			} else {
				return;
			}
		}
	}
	
	
}

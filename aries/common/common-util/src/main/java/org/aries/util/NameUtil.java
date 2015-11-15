package org.aries.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.eclipse.emf.ecore.EPackage;


/**
 * Utility class for formatting names of java model objects.
 * 
 */
public class NameUtil {

	public static String normalizeToAlpha(String input) {
		StringBuffer buf = new StringBuffer();
		for (int i=0; i < input.length(); i++) {
			char charAt = input.charAt(i);
			if (Character.isLetterOrDigit(charAt))
				buf.append(charAt);
		}
		return buf.toString();
	}

	public static String normalize(String text, String delimiter) {
		if (!delimiter.equals("/"))
			text = text.replace("/", delimiter);
		if (!delimiter.equals("."))
			text = text.replace(".", delimiter);
		if (!delimiter.equals("-"))
			text = text.replace("-", delimiter);
		if (!delimiter.equals("_"))
			text = text.replace("_", delimiter);
		if (!delimiter.equals(File.separator))
			text = text.replace(File.separator, delimiter);
		return text;
	}
	
	/**
	 * Returns the specified string with the first character converted to lower
	 * case.
	 * 
	 * @param name The string to convert.
	 * @return The specified string with the first character converted to lower case.
	 */
	public static String uncapName(String name) {
		if (name.length() > 0)
			return Character.toLowerCase(name.charAt(0)) + name.substring(1);
		return name;
	}

	/**
	 * Returns the specified string with the first character converted to upper
	 * case.
	 * 
	 * @param name The string to convert.
	 * @return The specified string with the first character converted to upper case.
	 */
	public static String capName(String name) {
		if (name.length() > 0)
			return Character.toUpperCase(name.charAt(0)) + name.substring(1);
		return name;
	}
	
	public static String capName(String[] text) {
		String cappedName = "";
		for (int i = 0; i < text.length; i++) {
			String segment = text[i];
			cappedName += capName(segment);
			if (i+1 < text.length)
				cappedName += " ";
		}
		return cappedName;
	}
	
	//TODO make this better
	public static String toPlural(String name) {
		if (name.equalsIgnoreCase("child"))
			return name + "ren";
		if (name.endsWith("s"))
			return name + "es";
		return name + "s";
	}
	
	public static String toSingular(String name) {
		if (name.endsWith("ies"))
			name = name.substring(0, name.length()-3) + "y";
		//else if (name.endsWith("es"))
		//	name = name.substring(0, name.length()-2);	
		else if (name.endsWith("s"))
			name = name.substring(0, name.length()-1);	
		return name;
	}
	
	public static String convertSpacesToHyphens(String text) {
		return convertSpaces(text, "-");
	}

	public static String removeSpaces(String text) {
		return convertSpaces(text, "");
	}

	public static String convertSpaces(String text, String delimiter) {
		String result = text.replaceAll(" ", delimiter);
		result = text.replaceAll("\t", delimiter);
		return result;
	}

	public static String convertSpacesToUnderscores(String text) {
		String result = text.replaceAll(" ", "_");
		result = text.replaceAll("\t", "_");
		return result;
	}

	//TODO fix this to avoid the uppercase conversion
	public static String toUnderscoredLowercase(String text) {
		String result = text.replaceAll("(?<=[a-z0-9])[A-Z]|(?<=[a-zA-Z])[0-9]|(?<=[A-Z])[A-Z](?=[a-z])", "_$0");
		result = result.toLowerCase();
		return result;
	}

	public static String toCamelCase(String text) {
		text = text.replace("-", "_");
		return toCamelCase(text, "_");
	}

	public static String toCamelCase(String text, String delimiter) {
		text = getFilteredName(text);
		String[] parts = text.split(delimiter);
		if (parts.length == 1)
			return capName(text);
		StringBuffer buf = new StringBuffer();
		for (String part : parts)
			buf.append(toProperCase(part));
		return buf.toString();
	}

	public static String toProperCase(String s) {
		return s.substring(0, 1).toUpperCase() +
			s.substring(1).toLowerCase();
	}

	
	public static String getPackageName(String name) {
		int indexOf = name.lastIndexOf(".");
		if (indexOf != -1) {
			name = name.substring(0, indexOf);
			return name;
		}
		return null;
	}

	public static String getQualifiedContextNamePrefix(String fullyQualifiedClassName) {
		return getPackageNameSegments(fullyQualifiedClassName, 1);
	}
	
	public static String getQualifiedContextNamePrefix(String fullyQualifiedClassName, int maxSegments) {
		return getPackageNameSegments(fullyQualifiedClassName, maxSegments);
	}
	
	public static String getPackageNameSegments(String fullyQualifiedClassName, int maxSegments) {
		StringTokenizer tokenizer = new StringTokenizer(fullyQualifiedClassName, ".");
		StringBuffer prefix = new StringBuffer(); 
		for (int i=0; tokenizer.hasMoreTokens() && i < maxSegments; i++) {
			String token = tokenizer.nextToken();
			if (i > 0)
				prefix.append(".");
			prefix.append(token);
		}
		return prefix.toString();
	}
	
	public static String getClassName(Object instance) {
		return getSimpleName(instance.getClass().getName());
	}

	public static String getClassName(String name) {
		return getSimpleName(name);
	}

	public static String getCompilationUnitName(String className) {
		int indexOf = className.indexOf("<");
		if (indexOf != -1)
			className = className.substring(0, indexOf);
		return className;
	}
	
	public static String getLocalPart(String name) {
		return getLocalPart(name, ".");
	}
	
	public static String getLocalPart(String text, String delimiter) {
		String normalizedText = normalize(text, delimiter);
		String lastSegment = getLastSegment(normalizedText, delimiter);
		String localPart = NameUtil.uncapName(lastSegment);
		return localPart;
	}
	
	public static String getSimpleName(String name) {
		return getSimpleName(name, ".");
	}

	public static String getSimpleNameFromPath(String path) {
		return getSimpleName(path, "/");
	}

	public static String getSimpleName(String name, String delimiter) {
		if (StringUtils.isEmpty(name))
			return "";
		int indexOf = name.lastIndexOf(delimiter);
		if (indexOf != -1 && indexOf < name.length()-1)
			name = name.substring(indexOf+1);
		return name;
	}
	
	public static String getBaseName(String name) {
		return getBaseName(name, ".");
	}

	public static String getBaseNameFromPath(String path) {
		return getBaseName(path, "/");
	}

	public static String getBaseName(String name, String delimiter) {
		if (StringUtils.isEmpty(name))
			return "";
		int indexOf = name.lastIndexOf(delimiter);
		if (indexOf != -1 && indexOf < name.length()-1)
			name = name.substring(0, indexOf);
		return name;
	}
	

	public static String getClassNameFromType(String type) {
		int indexOf = type.indexOf("}");
		if (indexOf == -1)
			return type;
		validateType(type);
		int closeBracketIndex = indexOf;
		String simpleName = type.substring(closeBracketIndex+1);
		//TODO handle these with external properties 
		if (simpleName.equals("int"))
			return "Integer";
		if (simpleName.equals("date"))
			return "Date";
		if (simpleName.equals("dateTime"))
			return "Date";
		if (simpleName.equals("time"))
			return "Date";
		if (simpleName.equals("anyType"))
			return "Object";
		//TODO specify this properly and run test
		if (simpleName.equals("base64Binary"))
			return "byte[]";
		//if (CodeGenUtil.isJavaPrimitiveType(simpleName))
		//	simpleName = NameUtil.capName(simpleName);
		simpleName = NameUtil.capName(simpleName);
		return simpleName;
	}

	public static String getPackageNameFromType(String type) {
		validateType(type);
		int openBracketIndex = type.indexOf("{");
		int closeBracketIndex = type.indexOf("}");
		String simpleName = type.substring(closeBracketIndex+1);
		//TODO - Externalize these properly 
		if (simpleName.equals("date"))
			return "java.util";
		if (simpleName.equals("dateTime"))
			return "java.util";
		if (simpleName.equals("time"))
			return "java.util";
		if (ClassUtil.isJavaPrimitiveType(simpleName))
			return "java.lang";
		String namespace = type.substring(openBracketIndex+1, closeBracketIndex);
		String packageName = NameUtil.getPackageNameFromNamespace(namespace);
//		if (!packageName.equals("java.lang"))
//			System.out.println();
		return packageName;
	}

	public static void validateType(String type) {
		Assert.notNull(type, "Type name must be specified");
		Assert.notEmpty(type, "Type name context must exist");
		int openBracketIndex = type.indexOf("{");
		int closeBracketIndex = type.indexOf("}");
		Assert.isTrue(openBracketIndex == 0, "Type name open bracket not found: "+type);
		Assert.isTrue(closeBracketIndex > 1, "Type name close bracket not found: "+type);
		Assert.isTrue(closeBracketIndex+2 < type.length(), "Type name local part not found: "+type);
	}
	
	public static String getLocalNameFromXSDType(String type) {
		int colonIndex = type.indexOf(":");
		if (colonIndex == -1)
			return type;
		validateXSDType(type);
		String localName = type.substring(colonIndex+1);
		return localName;
	}
	
	public static String getPrefixFromXSDType(String type) {
		int colonIndex = type.indexOf(":");
		if (colonIndex == -1)
			return "";
		validateXSDType(type);
		String prefix = type.substring(0, colonIndex);
		return prefix;
	}
	
	public static void validateXSDType(String type) {
		Assert.notNull(type, "Type name must be specified");
		Assert.notEmpty(type, "Type name context must exist");
		int colonIndex = type.indexOf(":");
		Assert.isTrue(colonIndex > 0, "Type name ':' not found");
		Assert.isTrue(colonIndex != 0, "Type name prefix not found");
		Assert.isTrue(colonIndex+1 < type.length(), "Type name local part not found");
	}

	
	/**
	 * Converts the specified constant name to a Java class name: the returned
	 * string will have an initial upper case character, followed by all lower
	 * case characters, except for characters following an underscore '_' or a
	 * whitespace character: characters following a character that will be removed
	 * from the identifier are converted to upper case.
	 * 
	 * @param code The name to convert to a Java class name.
	 * @return A Java class name for the specified name.
	 */
	public static String constantToJavaClassName(String code) {
		StringBuffer result = new StringBuffer();

		boolean upperNext = true;
		char[] chars = code.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];

			boolean valid = (i == 0) ? Character.isJavaIdentifierStart(c) : Character.isJavaIdentifierPart(c);

			if (!valid) {
				upperNext = true;
				continue;
			}
			if (c == '_') {
				upperNext = true;
				continue;
			}

			if (upperNext) {
				result.append(Character.toUpperCase(c));
				upperNext = false;
			} else {
				result.append(Character.toLowerCase(c));
			}
		}
		return result.toString();
	}

	/**
	 * Returns whether the specified string is a valid Java identifier.
	 * 
	 * @param name The string to check.
	 * @return Whether the specified string is a valid Java identifier.
	 */
	public static boolean isValidIdentifier(String name) {
		char[] chars = name.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];

			boolean valid = (i == 0) ? Character.isJavaIdentifierStart(c) : Character.isJavaIdentifierPart(c);

			if (!valid) {
				return false;
			}
		}
		return true;
	}

	public static String splitStringUsingSpaces(String name) {
		return splitStringUsingDelimiter(name, " ");
	}

	public static String splitStringUsingSpacesCapped(String name) {
		return splitStringUsingDelimiter(name, " ", true);
	}

	public static String splitStringUsingDashes(String name) {
		return splitStringUsingDelimiter(name, "-");
	}

	public static String splitStringUsingUnderscores(String name) {
		return splitStringUsingDelimiter(name, "_");
	}

	public static String splitStringUsingDelimiter(String name, String delimiter) {
		return splitStringUsingDelimiter(name, delimiter, false);
	}
	
	public static String splitStringUsingDelimiter(String name, String delimiter, boolean capped) {
		StringBuffer buf = new StringBuffer();
		for (int i=0; i < name.length(); i++) {
			char charAt = name.charAt(i);
			boolean delimiterAdded = false;
			if (i > 0 && Character.isUpperCase(charAt)) {
				buf.append(delimiter);
				delimiterAdded = true;
			}
			if (capped && (i == 0 || delimiterAdded))
				buf.append(Character.toUpperCase(charAt));
			else buf.append(Character.toLowerCase(charAt));
		}
		return buf.toString();
	}
	
	
	public static String toCommaSeparatedString(List<String> list) {
		return toString(list, ", ");
	}
	
	public static String toString(List<String> list, String delimiter) {
		String argumentString = "";
		Iterator<String> iterator2 = list.iterator();
		for (int i=0; iterator2.hasNext(); i++) {
			String argument = iterator2.next();
			if (i > 0)
				argumentString += delimiter;
			argumentString += argument;
		}
		return argumentString;
	}
	
	
	public static String getNameFromXSDPath(String location) {
		return getNameFromPath(location, "xsd");
	}

	public static String getNameFromWSDLPath(String location) {
		return getNameFromPath(location, "wsdl");
	}

	public static String getNameFromBPELPath(String location) {
		return getNameFromPath(location, "bpel");
	}

	public static String getNameFromPath(String location) {
		return getNameFromPath(location, null);
	}
	
	public static String getNameFromPath(String location, String fileExtension) {
		String[] split = location.split("/");
		String fileName = split[split.length-1];
		if (fileExtension != null) {
			String name = fileName.replace("."+fileExtension, "");
			return name;
		}
		int indexOf = fileName.indexOf(".");
		if (indexOf > -1) {
			String name = fileName.substring(0, indexOf);
			return name;
		}
		return fileName;
	}

	public static String getLocalPartFromNamespace(String namespaceUri) {
		return uncapName(getNameFromNamespace(namespaceUri));
	}
	
	public static String getNameFromNamespace(String namespaceUri) {
		int lastIndexOf = namespaceUri.lastIndexOf("/");
		if (lastIndexOf != -1)
			namespaceUri = namespaceUri.substring(lastIndexOf+1);
		lastIndexOf = namespaceUri.lastIndexOf(".");
		if (lastIndexOf != -1)
			namespaceUri = namespaceUri.substring(lastIndexOf+1);
		namespaceUri = capName(namespaceUri);
		return namespaceUri;
	}

	public static String getDomainFromNamespace(String namespace) {
		if (namespace == null)
			System.out.println();
		if (namespace.equals("http://www.w3.org/2001/XMLSchema"))
			return "org.w3";
		String name = namespace.replace("http://", "");
		name = name.replace("www.", "");
		if (name.contains("/")) {
			int indexOf = name.indexOf("/");
			name = name.substring(0, indexOf);
		} else {
			name = name.replace("/", ".");
			name = name.replace("-", "_");
			//name = name.replace(".xsd", "");
			//name = name.replace(".wsdl", "");
			//name = name.replace(".bpel", "");
		}
		name = getFilteredPackageName(name);
		return name;
	}
	
	public static String getPackageNameFromNamespace(String namespace) {
		if (namespace.equals("http://www.w3.org/2001/XMLSchema"))
			return "java.lang";
		String name = namespace.replace("http://", "");
		name = name.replace("/", ".");
		name = name.replace("-", "_");
		//name = name.replace(".xsd", "");
		//name = name.replace(".wsdl", "");
		//name = name.replace(".bpel", "");
		name = getFilteredPackageName(name, true);
		return name;
	}
	
	public static String getPackagePathFromNamespace(String namespace) {
		String name = getPackageNameFromNamespace(namespace);
		name = name.replace(".", "/");
		return name;
	}
	
	public static String getNamespaceURIFromPackageName(String packageName) {
		if (packageName.equals("java.lang"))
			return "http://www.w3.org/2001/XMLSchema";
		packageName = packageName.replace(".", "/");
		packageName = packageName.replace("_", "-");
		StringBuffer buf = new StringBuffer();
		StringTokenizer tokenizer = new StringTokenizer(packageName, ".");
		for (int i=0; tokenizer.hasMoreTokens(); i++) {
			String token = tokenizer.nextToken();
			if (i > 0)
				buf.insert(0, ".");
			buf.insert(0, token);
		}
		String uri = "http://"+buf.toString();
		return uri;
	}

	public static String getPackageNameFromEPackage(EPackage ePackage) {
		String packageName = getFilteredPackageName(ePackage.getName());
		return packageName;
	}

	public static String getFilteredPackageName(String packageName) {
		//TODO - make this option configurable - leave false by default (for now)
		return getFilteredPackageName(packageName, false);
	}
	
	public static String getFilteredPackageName(String packageName, boolean fullyQualified) {
		StringTokenizer tokenizer = new StringTokenizer(packageName, ".");
		StringBuffer buf = new StringBuffer();
		//for (int i=0; i < segments.length; i++) {
		for (int i=0; tokenizer.hasMoreTokens(); i++) {
			String segment = tokenizer.nextToken();
			//assuming here segments are always greater than 1 in length 
			if (segment.startsWith("_") && Character.isDigit(segment.charAt(1)))
				break;
			if (Character.isDigit(segment.charAt(0)))
				break;
			if (segment.equals("www"))
				continue;
			if (segment.equals("com") || segment.equals("org") || segment.equals("net")) {
				if (fullyQualified) {
					boolean needsDot = buf.length() > 0;
					if (needsDot)
						buf.insert(0, segment+".");
					else buf.insert(0, segment);
				}
				continue;
			}
			boolean needsDot = buf.length() > 0;
			if (needsDot)
				buf.append(".");
			buf.append(segment);
		}
		//packageName
		return buf.toString();
	}

	public static String getFilteredName(String text) {
		String name = text.replace("-", "_");
		name = name.replace("$", "");
		return name;
	}

	public static String getParentPath(String path, String delimiter) {
		int lastIndexOf = path.lastIndexOf(delimiter);
		if (lastIndexOf != -1)
			return path.substring(0, lastIndexOf);
		return ".";
	}

	public static String getParentDirectoryName(String fileName) {
		fileName = normalizePath(fileName);
		if (fileName.endsWith(File.separator)) {
			int endIndex = fileName.length()-1; 
			fileName = fileName.substring(0, endIndex-1);
		}
		int lastIndexOf = fileName.lastIndexOf(File.separator);
		if (lastIndexOf != -1)
			return fileName.substring(0, lastIndexOf);
		return ".";
	}

	public static String normalizePath(String path) {
		String fileSeparator = System.getProperty("file.separator");
		String newPath = null;
		if (fileSeparator.equals("\\"))
			newPath = normalizePath(path, "/", fileSeparator);
		else newPath = normalizePath(path, "\\", "/");
		if (newPath.contains(fileSeparator+fileSeparator))
			newPath = newPath.replace(fileSeparator+fileSeparator, fileSeparator);
		return newPath;
	}
	
	public static String normalizePathToUnix(String path) {
		return normalizePath(path, "\\", "/");
	}
	
	public static String normalizePath(String path, String oldFileSeperator, String newFileSeparator) {
		boolean containsDrive = path.contains(":");
		String newPath = path.replace(oldFileSeperator, newFileSeparator);
		if (containsDrive) {
			if (newPath.substring(0, 1).equals(newFileSeparator))
				newPath = newPath.substring(1);
		}
		return newPath;
	}

	public static String trimFromStart(String name, String substring) {
		if (name.startsWith(substring)) {
			name = name.substring(substring.length());
		}
		return name;
	}
	
	public static String trimFromEnd(String name, String substring) {
		if (name.endsWith(substring)) {
			int indexOf = name.indexOf(substring);
			name = name.substring(0, indexOf);
		}
		return name;
	}

	public static String assureEndsWith(String name, String substring) {
		if (!name.toLowerCase().endsWith(substring.toLowerCase()))
			return name + substring;
		return name;
	}

	public static int getSegmentCount(String name) {
		return getSegmentCount(name, ".");
	}
	
	public static int getSegmentCount(String name, String delim) {
		StringTokenizer tokenizer = new StringTokenizer(name, delim);
		return tokenizer.countTokens();
	}

	public static String getFirstSegment(String name) {
		return getFirstSegment(name, ",");
	}
	
	public static String getFirstSegment(String name, String delimiter) {
		String[] parts = name.split("\\"+delimiter);
		String firstSegment = parts[0];
		return firstSegment;
	}
	
	public static String getFirstSegmentFromPackageName(String packageName) {
		String firstSegment = getFirstSegment(packageName, ".");
		return NameUtil.capName(firstSegment);
	}

	public static String getLastSegment(String name) {
		return getLastSegment(name, ",");
	}
	
	public static String getLastSegment(String name, String delimiter) {
		//TODO add check for special character (that needs to be escaped) - we just assume it for now
		String[] parts = name.split("\\"+delimiter);
		int index = parts.length - 1;
		String lastSegment = parts[index];
		return lastSegment;
	}
	
	public static String getLastSegmentFromPackageName(String packageName) {
		String lastSegment = getLastSegment(packageName, ".");
		return NameUtil.capName(lastSegment);
	}
	
	public static String[] splitIntoSegments(String name, String delimiter) {
		//List<String> segments = new ArrayList<String>();
		String[] segments = name.split("\\"+delimiter);
		return segments;
	}
	
	//Returns an uncapped camelCased version of the input text
	public static String mergeSegments(String packageName) {
		StringBuffer buf = new StringBuffer();
		String[] segments = packageName.split("\\.");
		for (String segment : segments)
			buf.append(NameUtil.capName(segment));
		String result = NameUtil.uncapName(buf.toString());
		return result;
	}
	

	public static boolean startsWithCommonPackagePrefix(String inputText) {
		return startsWithPrefix(inputText, new String[] { "java.", "javax.", "com.", "net.", "org." });
	}
	
	public static boolean startsWithPrefix(String inputText, String[] prefixes) {
		for (int i = 0; i < prefixes.length; i++) {
			if (inputText.startsWith(prefixes[i])) {
				return true;
			}
		}
		return false;
	}

	
	public static List<String> sortStrings(Collection<String> strings) {
		List<String> keys = new ArrayList<String>(strings);
		Collections.sort(keys);
//		Iterator<String> iterator2 = keys.iterator();
//		while (iterator2.hasNext()) {
//			String string = iterator2.next();
//			System.out.println(">>>"+string);
//		}
		return keys;
	}

	public static List<String> sortPackageNames(Collection<String> packageNames) {
		Set<String> sortedPackageNames = new TreeSet<String>(packageNames);
		Comparator<String> packageComparator = new Comparator<String>() {
			public int compare(String s1, String s2) {
				boolean s1CommonPrefix = NameUtil.startsWithCommonPackagePrefix(s1);
				boolean s2CommonPrefix = NameUtil.startsWithCommonPackagePrefix(s2);
				if (s1CommonPrefix && !s2CommonPrefix)
					return -1;
				if (!s1CommonPrefix && s2CommonPrefix)
					return 1;
				if (s1CommonPrefix && s2CommonPrefix)
					return s1.compareTo(s2);
				return s1.compareTo(s2);
			}
		};
		
		List<String> list = new ArrayList<String>(sortedPackageNames);
		Collections.sort(list, packageComparator);
		return list;
	}

}
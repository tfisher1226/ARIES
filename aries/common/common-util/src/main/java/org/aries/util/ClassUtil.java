package org.aries.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class ClassUtil {

	private static Set<String> javaDefaultTypes;


	public static boolean classExists(String className) {
		try {
			Class<?> classObject = loadClass(className);
			return classObject != null;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public static boolean isInterface(String className) {
		try {
			Class<?> classObject = loadClass(className);
			return classObject != null && classObject.isInterface();
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public static Class<?> loadClass(String className) throws ClassNotFoundException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		return loadClass(className, classLoader);
	}

	public static Class<?> loadClass(String className, ClassLoader classLoader) throws ClassNotFoundException {
		if (isJavaPrimitiveType(className))
			return getClassForJavaPrimitiveType(className);
		Class<?> classObject = classLoader.loadClass(className);
		return classObject;
	}

	public static Object getValueForClass(String type, Object value) {
		if (isJavaPrimitiveType(type))
			return getValueForJavaPrimitiveType(type, (String) value);
		if (value != null && value instanceof String)
			return value;
		return null;
	}


	/**
	 * Tests whether the given string is the name of a java.lang type.
	 */
	public static boolean isJavaLangType(String name) {
		return isJavaDefaultType(name) && Character.isUpperCase(name.charAt(0));
	}

	/**
	 * Tests whether the given string is the name of a primitive type.
	 */
	public static boolean isJavaPrimitiveType(String name) {
		return isJavaDefaultType(name) && Character.isLowerCase(name.charAt(0));
	}

	/**
	 * Tests whether the given string is the name of a primitive or java.lang type.
	 */
	public static boolean isJavaDefaultType(String name) {
		if (name.contains("[]"))
			name = name.replace("[]", "");
		//name = NameUtil.capName(name);
		return getJavaDefaultTypes().contains(name);
	}
	
	/**
	 * Returns the short names of the primitives and types in java.lang (i.e. those
	 * that don't need qualification).
	 */
	//TODO KEEP THIS UP-TO-DATE
	public static Set<String> getJavaDefaultTypes() {
		if (javaDefaultTypes == null) {
			Set<String> result = new HashSet<String>(100);
			result.add("AbstractMethodError");
			result.add("ArithmeticException");
			result.add("ArrayIndexOutOfBoundsException");
			result.add("ArrayStoreException");
			result.add("Boolean");
			result.add("Byte");
			result.add("Character");
			result.add("Class");
			result.add("ClassCastException");
			result.add("ClassCircularityError");
			result.add("ClassFormatError");
			result.add("ClassLoader");
			result.add("ClassNotFoundException");
			result.add("CloneNotSupportedException");
			result.add("Cloneable");
			result.add("Comparable");
			result.add("Compiler");
			result.add("Double");
			result.add("Deprecated");
			result.add("Enum");
			result.add("Error");
			result.add("Exception");
			result.add("ExceptionInInitializerError");
			result.add("Float");
			result.add("FloatingDecimal");
			result.add("IllegalAccessError");
			result.add("IllegalAccessException");
			result.add("IllegalArgumentException");
			result.add("IllegalMonitorStateException");
			result.add("IllegalStateException");
			result.add("IllegalThreadStateException");
			result.add("IncompatibleClassChangeError");
			result.add("IndexOutOfBoundsException");
			result.add("InheritableThreadLocal");
			result.add("InstantiationError");
			result.add("InstantiationException");
			result.add("Integer");
			result.add("InternalError");
			result.add("InterruptedException");
			result.add("LinkageError");
			result.add("Long");
			result.add("Math");
			result.add("NegativeArraySizeException");
			result.add("NoClassDefFoundError");
			result.add("NoSuchFieldError");
			result.add("NoSuchFieldException");
			result.add("NoSuchMethodError");
			result.add("NoSuchMethodException");
			result.add("NullPointerException");
			result.add("Number");
			result.add("NumberFormatException");
			result.add("Object");
			result.add("Override");
			result.add("OutOfMemoryError");
			result.add("Package");
			result.add("Process");
			result.add("Runnable");
			result.add("Runtime");
			result.add("RuntimeException");
			result.add("RuntimePermission");
			result.add("SecurityException");
			result.add("SecurityManager");
			result.add("Short");
			result.add("StackOverflowError");
			result.add("String");
			result.add("StringBuffer");
			result.add("StringBuilder");
			result.add("StringIndexOutOfBoundsException");
			result.add("SuppressWarnings");
			result.add("System");
			result.add("Thread");
			result.add("ThreadDeath");
			result.add("ThreadGroup");
			result.add("ThreadLocal");
			result.add("Throwable");
			result.add("UnknownError");
			result.add("UnsatisfiedLinkError");
			result.add("UnsupportedClassVersionError");
			result.add("UnsupportedOperationException");
			result.add("VerifyError");
			result.add("VirtualMachineError");
			result.add("Void");
			result.add("boolean");
			result.add("byte");
			result.add("char");
			result.add("double");
			result.add("float");
			result.add("int");
			result.add("long");
			result.add("short");
			javaDefaultTypes = Collections.unmodifiableSet(result);
		}
		return javaDefaultTypes;
	}

	public static Object getValueForJavaPrimitiveType(String type, String value) {
		if (type.equals("byte"))
			return new Byte(value);
		if (type.equals("char"))
			return new Character(value.charAt(0));
		if (type.equals("short"))
			return new Short(value);
		if (type.equals("int"))
			return new Integer(value);
		if (type.equals("long"))
			return new Long(value);
		if (type.equals("float"))
			return new Float(value);
		if (type.equals("double"))
			return new Double(value);
		if (type.equals("boolean"))
			return new Boolean(value);
		return null;
	}

	public static Class<?> getClassForJavaPrimitiveType(String type) {
		if (type.equals("byte"))
			return byte.class;
		if (type.equals("byte[]"))
			return byte[].class;
		if (type.equals("char"))
			return char.class;
		if (type.equals("char[]"))
			return char[].class;
		if (type.equals("short"))
			return short.class;
		if (type.equals("short[]"))
			return short[].class;
		if (type.equals("int"))
			return int.class;
		if (type.equals("int[]"))
			return int[].class;
		if (type.equals("long"))
			return long.class;
		if (type.equals("long[]"))
			return long[].class;
		if (type.equals("float"))
			return float.class;
		if (type.equals("float[]"))
			return float[].class;
		if (type.equals("double"))
			return double.class;
		if (type.equals("double[]"))
			return double[].class;
		if (type.equals("boolean"))
			return boolean.class;
		if (type.equals("void"))
			return void.class;
		return null;
	}

	public static String convertPrimitiveTypeToObjectTypeName(String type) {
		Class<?> classObject = convertPrimitiveTypeToObjectType(type);
		return classObject.getName();
	}

	public static Class<?> convertPrimitiveTypeToObjectType(String type) {
		if (type.equals("byte"))
			return Byte.class;
		if (type.equals("byte[]"))
			return Byte[].class;
		if (type.equals("char"))
			return Character.class;
		if (type.equals("char[]"))
			return Character[].class;
		if (type.equals("short"))
			return Short.class;
		if (type.equals("short[]"))
			return Short[].class;
		if (type.equals("int"))
			return Integer.class;
		if (type.equals("int[]"))
			return Integer[].class;
		if (type.equals("long"))
			return Long.class;
		if (type.equals("long[]"))
			return Long[].class;
		if (type.equals("float"))
			return Float.class;
		if (type.equals("float[]"))
			return Float[].class;
		if (type.equals("double"))
			return Double.class;
		if (type.equals("double[]"))
			return Double[].class;
		if (type.equals("boolean"))
			return Boolean.class;
		if (type.equals("boolean[]"))
			return Boolean[].class;
		if (type.equals("void"))
			return void.class;
		return null;
	}

	public static String convertTypeToJavaClass(String type) {
		if (isJavaPrimitiveType(type))
			return convertPrimitiveTypeToObjectTypeName(type);

		if (type.startsWith("java.lang"))
			return type;
		if (type.startsWith("java.util"))
			return type;

		if (type.toLowerCase().startsWith("string"))
			return "java.lang.String";
		if (type.toLowerCase().equals("date"))
			return "java.util.Date";
		if (type.equals("Integer"))
			return "java.lang.Integer";
		if (type.equals("Long"))
			return "java.lang.Long";
		if (type.equals("Short"))
			return "java.lang.Short";
		if (type.equals("Double"))
			return "java.lang.Double";
		if (type.equals("Boolean"))
			return "java.lang.Boolean";

		if (type.toLowerCase().startsWith("string[]"))
			return "java.lang.String[]";
		if (type.toLowerCase().equals("date[]"))
			return "java.util.Date[]";
		if (type.equals("Integer[]"))
			return "java.lang.Integer[]";
		if (type.equals("Long[]"))
			return "java.lang.Long[]";
		if (type.equals("Short[]"))
			return "java.lang.Short[]";
		if (type.equals("Double[]"))
			return "java.lang.Double[]";
		if (type.equals("Boolean[]"))
			return "java.lang.Boolean[]";
		if (type.toLowerCase().equals("base64binary"))
			return "java.lang.Byte[]";
		return type;
	}

	//	public static String convertPrimitiveToObjectType(String type) {
	//		if (ClassUtil.isPrimitiveType(type)) {
	//			Class<?> classObject = getClassForPrimitiveType(type);
	//			return classObject.getName();
	//		}
	//		return type;
	//	}
	//
	//	public static Class<?> convertPrimitiveToObject(String type) {
	//		if (ClassUtil.isPrimitiveType(type)) {
	//			Class<?> classObject = getClassForPrimitiveType(type);
	//			return classObject;
	//		}
	//		return null;
	//	}

	
	/**
	 * Show the class loader hierarchy for this class.
	 * Uses default line break and tab text characters.
	 * @param obj object to analyze loader hierarchy for
	 * @param role a description of the role of this class in the application
	 * (e.g., "servlet" or "EJB reference")
	 * @return a String showing the class loader hierarchy for this class
	 */
	public static String showClassLoaderHierarchy(Object obj, String role) {
		return showClassLoaderHierarchy(obj, role, "\n", "\t");
	}

	/**
	 * Show the class loader hierarchy for this class.
	 * @param obj object to analyze loader hierarchy for
	 * @param role a description of the role of this class in the application
	 * (e.g., "servlet" or "EJB reference")
	 * @param lineBreak line break
	 * @param tabText text to use to set tabs
	 * @return a String showing the class loader hierarchy for this class
	 */
	public static String showClassLoaderHierarchy(Object obj, String role, String lineBreak, String tabText) {
		String s = "object of " + obj.getClass() + ": role is " + role + lineBreak;
		return s + showClassLoaderHierarchy(obj.getClass().getClassLoader(), lineBreak, tabText, 0);
	}

	/**
	 * Show the class loader hierarchy for the given class loader.
	 * Uses default line break and tab text characters.
	 * @param cl class loader to analyze hierarchy for
	 * @return a String showing the class loader hierarchy for this class
	 */
	public static String showClassLoaderHierarchy(ClassLoader cl) {
		return showClassLoaderHierarchy(cl, "\n", "\t");
	}



	/**
	 * Show the class loader hierarchy for the given class loader.
	 * @param cl class loader to analyze hierarchy for
	 * @param lineBreak line break
	 * @param tabText text to use to set tabs
	 * @return a String showing the class loader hierarchy for this class
	 */
	public static String showClassLoaderHierarchy(ClassLoader cl, String lineBreak, String tabText) {
		return showClassLoaderHierarchy(cl, lineBreak, tabText, 0);
	}

	/**
	 * Show the class loader hierarchy for the given class loader.
	 * @param cl class loader to analyze hierarchy for
	 * @param lineBreak line break
	 * @param tabText text to use to set tabs
	 * @param indent nesting level (from 0) of this loader; used in pretty printing
	 * @return a String showing the class loader hierarchy for this class
	 */
	private static String showClassLoaderHierarchy(ClassLoader cl, String lineBreak, String tabText, int indent) {
		if (cl == null) {
			ClassLoader ccl = Thread.currentThread().getContextClassLoader();
			return "context class loader=[" + ccl + "] hashCode=" + ccl.hashCode();
		}

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < indent; i++) {
			buf.append(tabText);
		}

		buf.append("[").append(cl).append("] hashCode=").append(cl.hashCode()).append(lineBreak);
		ClassLoader parent = cl.getParent();
		return buf.toString() + showClassLoaderHierarchy(parent, lineBreak, tabText, indent + 1);

	}


}

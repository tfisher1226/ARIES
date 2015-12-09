package org.aries;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.enterprise.inject.InjectionException;

import com.sun.faces.util.DebugObjectOutputStream;


/**
 * Provides library of common assertion methods.
 * Throws common {@link RuntimeException} exceptions when assertions fail. 
 */
public class Assert {

	protected static final double ZERO_TOLERANCE = 0.00;
	protected static final double SMALL_TOLERANCE = 1e-6d;
	protected static final double REALLY_SMALL_TOLERANCE = 1e-10d;
	protected static final double LENGTH_TOLERANCE = 0.0001;

	public static void isTrue(boolean value) {
		isTrue(value, "[Assertion failed] - boolean must be true");
	}

	public static void isTrue(boolean value, String message) {
		if (!value) {
			throw new AssertionFailure(message);
		}
	}

	public static void isTrue(AtomicBoolean value) {
		notNull(value);
		isTrue(value.get());
	}

	public static void isTrue(AtomicBoolean value, String message) {
		notNull(value, message);
		isTrue(value.get(), message);
	}

	public static void isFalse(boolean value) {
		isFalse(value, "[Assertion failed] - boolean must be false");
	}

	public static void isFalse(boolean value, String message) {
		if (value) {
			throw new AssertionFailure(message);
		}
	}

	public static void isFalse(AtomicBoolean value) {
		notNull(value);
		isFalse(value.get());
	}

	public static void isFalse(AtomicBoolean value, String message) {
		notNull(value, message);
		isFalse(value.get(), message);
	}

	public static void isNull(Object object) {
		isNull(object, "[Assertion failed] - object must be null");
	}

	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new AssertionFailure(message);
		}
	}

	public static void notNull(Object object) {
		notNull(object, "[Assertion failed] - object must not be null");
	}

	public static void notNull(Object object, String message) {
		if (object == null) {
			//Thread.currentThread().dumpStack();
			throw new AssertionFailure(message);
		}
	}

	public static void hasLength(String text) {
		hasLength(text, "[Assertion failed] - string must have length");
	}

	public static void hasLength(String text, String message) {
		if (text == null || text.length() == 0) {
			throw new AssertionFailure(message);
		}
	}

	public static void isLength(Object[] array, int length) {
		notNull(array, "[Assertion failed] - array null");
		isTrue(array.length == length, "[Assertion failed] - array length not correct, expected="+length+", actual="+array.length);
	}
	
	public static void isLength(Collection<?> collection, int length) {
		notNull(collection, "[Assertion failed] - collection null");
		isTrue(collection.size() == length, "[Assertion failed] - collection size not correct, expected="+length+", actual="+collection.size());
	}
	
	//TODO make this count only the non-null items
	public static void isSize(Object[] array, int length) {
		notNull(array, "[Assertion failed] - array null");
		isTrue(array.length == length, "[Assertion failed] - array length not correct, expected="+length+", actual="+array.length);
	}
	
	//TODO make this count only the non-null items
	public static void isSize(Collection<?> collection, int size) {
		notNull(collection, "[Assertion failed] - collection null");
		isTrue(collection.size() == size, "[Assertion failed] - collection size not correct, expected="+size+", actual="+collection.size());
	}
	
	public static void isEmpty(String string) {
		notNull(string, "[Assertion failed] - string null");
		isTrue(string.length() == 0, "[Assertion failed] - string not empty");
	}

	public static void isEmpty(Object[] array) {
		notNull(array, "[Assertion failed] - array null");
		isTrue(array.length == 0, "[Assertion failed] - array not empty");
	}

	public static void isEmpty(Collection<?> collection) {
		notNull(collection, "[Assertion failed] - collection null");
		isTrue(collection.size() == 0, "[Assertion failed] - collection not empty");
	}

	public static void isEmpty(Collection<?> collection, String message) {
		notNull(collection, "[Assertion failed] - "+message+" collection null");
		isTrue(collection.size() == 0, "[Assertion failed] - "+message+" - collection not empty");
	}

	public static void isEmpty(Map<?, ?> map) {
		notNull(map, "[Assertion failed] - map null");
		isTrue(map.size() == 0, "[Assertion failed] - map not empty");
	}

	public static void isEmpty(Map<?, ?> map, String message) {
		notNull(map, "[Assertion failed] - "+message+" map null");
		isTrue(map.size() == 0, "[Assertion failed] - "+message+" - map not empty");
	}

	public static void notEmpty(String string) {
		notNull(string, "[Assertion failed] - string null");
		notEmpty(string, "[Assertion failed] - string empty");
	}

	public static void notEmpty(String string, String message) {
		notNull(string, message+", string null");
		if (string.length() == 0) {
			throw new AssertionFailure(message);
		}
	}

	public static void notEmpty(Object[] array) {
		notNull(array, "[Assertion failed] - array null");
		notEmpty(array, "[Assertion failed] - array empty");
	}

	public static void notEmpty(Object[] array, String message) {
		notNull(array, "[Assertion failed] - array null");
		if (array.length == 0) {
			throw new AssertionFailure(message);
		}
	}

	public static void noNullElements(Object[] array) {
		noNullElements(array, "[Assertion failed] - array must not contain null elements");
	}

	public static void noNullElements(Object[] array, String message) {
		notNull(array, "Array must not be null");
		for (int i = 0; i < array.length; i++) {
			notNull(array[i], message);
		}
	}

	public static void notEmpty(Collection<?> collection) {
		notNull(collection, "[Assertion failed] - collection null");
		notEmpty(collection, "[Assertion failed] - collection empty");
	}

	public static void notEmpty(Collection<?> collection, String message) {
		notNull(collection, "[Assertion failed] - collection null");
		if (collection.isEmpty()) {
			throw new AssertionFailure(message);
		}
	}

	public static void notEmpty(Map map) {
		notNull(map, "[Assertion failed] - map null");
		notEmpty(map, "[Assertion failed] - map empty");
	}

	public static void notEmpty(Map map, String message) {
		notNull(map, "[Assertion failed] - map null");
		if (map.isEmpty()) {
			throw new AssertionFailure(message);
		}
	}

	public static void isInstanceOf(Class clazz, Object object) {
		isInstanceOf(clazz, object, "");
	}

	public static void isInstanceOf(Class type, Object object, String message) {
		notNull(type, "[Assertion failed] - type null");
		notNull(type, "[Assertion failed] - object null");
		if (!type.isInstance(object)) {
			throw new AssertionFailure(message + "[Assertion failed] - "+ 
					"Object of class ["+object.getClass().getCanonicalName()+"] is not an instance of "+type.getCanonicalName());
		}
	}

	public static void isAssignable(Class superType, Class subType) {
		isAssignable(superType, subType, "");
	}

	public static void isAssignable(Class superType, Class subType, String message) {
		notNull(superType, "[Assertion failed] - super-type null");
		notNull(subType, "[Assertion failed] - sub-type null");
		if (!superType.isAssignableFrom(subType)) {
			throw new AssertionFailure(message+subType+" is not assignable to "+superType);
		}
	}

	public static void isSerializable(Object expectedObject) {
		serializeObject(expectedObject);
	}

	public static void isValidEquals(Serializable object) {
		Serializable serializedObject = serializeObject(object);
		String message = "[Assertion failed] - object of type "+object.getClass()+" should implement equals() correctly";
		isTrue(object.equals(serializedObject), message);
	}

	private static Serializable serializeObject(Object expectedObject) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(expectedObject);
			objectOutputStream.close();

			ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			Serializable serializedObject = (Serializable) objectInputStream.readObject();
			return serializedObject;
		} catch (Exception e) {
			String message = "[Assertion failed] - class "+expectedObject.getClass().getName()+" should be serializable: "+e;
			throw new AssertionFailure(message);
		}
	}


	public static void equals(Object object1, Object object2) {
		equals(object1, object2, "[Assertion failed] - \""+object1+"\" should equal \""+object2+"\"");
	}

	public static void equals(Object object1, Object object2, String message) {
		notNull(object1);
		notNull(object2);
		if (!object1.equals(object2)) {
			throw new AssertionFailure(message);
		}
	}

	public static void equals(Serializable object1, Serializable object2, String message) {
		notNull(object1);
		notNull(object2);
		isSerializable(object1);
		isSerializable(object2);
		if (!object1.equals(object2)) {
			throw new AssertionFailure(message);
		}
	}

	//TODO make full set of equals() methods for Dates that have different levels of reduced precision 
	public static void equals(Date date1, Date date2, String message) {
		notNull(date1);
		notNull(date2);
		long time1 = date1.getTime() / 1000L;
		long time2 = date2.getTime() / 1000L;
		if (time1 != time2) {
			throw new AssertionFailure("Dates not equal: "+message);
		}
	}
	
	public static <T> void equals(Collection<T> objectList1, Collection<T> objectList2, String message) {
		notNull(objectList1);
		notNull(objectList2);
		equals(objectList1.size(), objectList2.size(), message);
		Iterator<T> iterator1 = objectList1.iterator();
		Iterator<T> iterator2 = objectList2.iterator();
		while (iterator1.hasNext() && iterator1.hasNext()) {
			T object1 = iterator1.next();
			T object2 = iterator2.next();
			if (!object1.equals(object2)) {
				throw new AssertionFailure(message);
			}
		}
	}

	public static void equals(String expected, String actual, String message) {
		if (expected == null && actual == null)
			return;
		if (expected == null || actual == null)
			throw new AssertionFailure(message);
		String newExpected = expected.replace("\r\n", "\n");
		String newActual = actual.replace("\r\n", "\n");
		if (!newExpected.equals(newActual)) {
			throw new AssertionFailure(message+": expected=\""+expected+"\", actual=\""+actual+"\"");
		}
	}

	public static void equals(int expected, Number actual, String message) {
		equals(expected, actual.intValue(), message);	
	}

	public static void equals(int expected, int actual, String message) {
		if (expected != actual) {
			throw new AssertionFailure(message+": expected="+expected+", actual="+actual);
		}
	}

	public static void equals(String message, long expected, long actual) {
		equals(expected, actual, ZERO_TOLERANCE, message);
	}

	public static void equals(String message, short expected, short actual) {
		equals(expected, actual, ZERO_TOLERANCE, message);
	}

	public static void equals(String message, double expected, double actual) {
		equals(expected, actual, ZERO_TOLERANCE, message);
	}

	public static void equals(int[] expected, int[] actual, String message) {
		equals(message+" arrays not same size", expected.length, actual.length);
		for (int count = 0; count < expected.length; count++) {
			equals(message+" item with index "+count+" not equal", expected[count], actual[count]);
		}
	}

	public static void equals(long[] expected, long[] actual, String message) {
		equals(expected.length, actual.length, message+" arrays not same size");
		for (int count = 0; count < expected.length; count++) {
			equals(expected[count], actual[count], message+" item with index "+count+" not equal");
		}
	}

	public static void equals(double[] expected, double[] actual, double tolerance, String message) {
		equals(expected.length, actual.length, message+" arrays not same size");
		for (int count = 0; count < expected.length; count++) {
			equals(expected[count], actual[count],tolerance, message+" item with index "+count+" not equal");
		}
	}

	public static void equals(short[] expected, short[] actual, String message) {
		equals(expected.length, actual.length, message+" arrays not same size");
		for (int count = 0; count < expected.length; count++) {
			equals(expected[count], actual[count], message+" item with index "+count+" not equal");
		}
	}

	public static void equals(Object[] expected, Object[] actual, String message) {
		equals(expected.length, actual.length, message+" arrays not same size");
		for (int count = 0; count < expected.length; count++) {
			equals(expected[count], actual[count], message+" item with index "+count+" not equal");
		}
	}

	public static void equals(double d, double e, double tolerance, String message) {

	}

	public static void startsWith(String string1, String string2) {
		String message = "[Assertion failed] string1 does not start with string2:\n";
		message += "string1: " + string1 + "\n";
		message += "string2: " + string2 + "\n";
		startsWith(string1, string2, message); 
	}
	
	public static void startsWith(String string1, String string2, String message) {
		notNull(string1);
		notNull(string2);
		if (!string1.startsWith(string2)) {
			throw new AssertionFailure(message);
		}
	}
	
	public static boolean isInteger(String value, String message) {
		notNull(value);
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isDouble(String value, String message) {
		notNull(value);
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static void exception(Throwable e, String message) {
		Assert.isTrue(e.getMessage().contains(message), "Exception message: \""+e.getMessage()+"\", does not contain: \""+message+"\"");
	}

	public static void exception(Throwable e, Class<? extends Throwable> classObject) {
		Assert.equals(e.getClass(), classObject);
	}

	public static void exception(Throwable e, Class<? extends Throwable> classObject, String message) {
		exception(e, classObject);
		exception(e, message);
	}




	/**
	 * Asserts method don't declare primitive parameters.
	 *
	 * @param method to validate
	 * @param annotation annotation to propagate in exception message
	 */
	public static void assertNoPrimitiveParameters(final Method method, Class<? extends Annotation> annotation)
	{
		for (Class<?> type : method.getParameterTypes())
		{
			if (type.isPrimitive())
			{
				throw new InjectionException("Method " + getAnnotationMessage(annotation) + "can't declare primitive parameters: " + method);
			}
		}
	}

	/**
	 * Asserts method don't declare primitive parameters.
	 *
	 * @param method to validate
	 */
	public static void assertNoPrimitiveParameters(final Method method)
	{
		assertNoPrimitiveParameters(method, null);
	}

	/**
	 * Asserts field is not of primitive type.
	 *
	 * @param method to validate
	 * @param annotation annotation to propagate in exception message
	 */
	public static void assertNotPrimitiveType(final Field field, Class<? extends Annotation> annotation)
	{
		if (field.getType().isPrimitive())
		{
			throw new InjectionException("Field " + getAnnotationMessage(annotation) + "can't be of primitive type: " + field);
		}
	}

	/**
	 * Asserts field is not of primitive type.
	 *
	 * @param method to validate
	 */
	public static void assertNotPrimitiveType(final Field field)
	{
		assertNotPrimitiveType(field, null);
	}

	/**
	 * Asserts method have no parameters.
	 *
	 * @param method to validate
	 * @param annotation annotation to propagate in exception message
	 */
	public static void assertNoParameters(final Method method, Class<? extends Annotation> annotation) 
	{
		if (method.getParameterTypes().length != 0)
		{
			throw new InjectionException("Method " + getAnnotationMessage(annotation) + "have to have no parameters: " + method);
		}
	}

	/**
	 * Asserts method have no parameters.
	 *
	 * @param method to validate
	 */
	public static void assertNoParameters(final Method method)
	{
		assertNoParameters(method, null);
	}

	/**
	 * Asserts method return void.
	 *
	 * @param method to validate
	 * @param annotation annotation to propagate in exception message
	 */
	public static void assertVoidReturnType(final Method method, Class<? extends Annotation> annotation) 
	{
		if ((!method.getReturnType().equals(Void.class)) && (!method.getReturnType().equals(Void.TYPE)))
		{
			throw new InjectionException("Method " + getAnnotationMessage(annotation) + "have to return void: " + method);
		}
	}

	/**
	 * Asserts method return void.
	 *
	 * @param method to validate
	 */
	public static void assertVoidReturnType(final Method method) 
	{
		assertVoidReturnType(method, null);
	}

	/**
	 * Asserts field isn't of void type.
	 *
	 * @param field to validate
	 * @param annotation annotation to propagate in exception message
	 */
	public static void assertNotVoidType(final Field field, Class<? extends Annotation> annotation)
	{
		if ((field.getClass().equals(Void.class)) && (field.getClass().equals(Void.TYPE)))
		{
			throw new InjectionException("Field " + getAnnotationMessage(annotation) + "cannot be of void type: " + field);
		}
	}

	/**
	 * Asserts field isn't of void type.
	 *
	 * @param field to validate
	 */
	public static void assertNotVoidType(final Field field)
	{
		assertNotVoidType(field, null);
	}

	/**
	 * Asserts method don't throw checked exceptions.
	 *
	 * @param method to validate
	 * @param annotation annotation to propagate in exception message
	 */
	public static void assertNoCheckedExceptionsAreThrown(final Method method, Class<? extends Annotation> annotation) 
	{
		Class<?>[] declaredExceptions = method.getExceptionTypes();
		for (int i = 0; i < declaredExceptions.length; i++)
		{
			Class<?> exception = declaredExceptions[i];
			if (!exception.isAssignableFrom(RuntimeException.class))
			{
				throw new InjectionException("Method " + getAnnotationMessage(annotation) + "cannot throw checked exceptions: " + method);
			}
		}
	}

	/**
	 * Asserts method don't throw checked exceptions.
	 *
	 * @param method to validate
	 */
	public static void assertNoCheckedExceptionsAreThrown(final Method method) 
	{
		assertNoCheckedExceptionsAreThrown(method, null);
	}

	/**
	 * Asserts method is not static.
	 *
	 * @param method to validate
	 * @param annotation annotation to propagate in exception message
	 */
	public static void assertNotStatic(final Method method, Class<? extends Annotation> annotation) 
	{
		if (Modifier.isStatic(method.getModifiers()))
		{
			throw new InjectionException("Method " + getAnnotationMessage(annotation) + "cannot be static: " + method);
		}
	}

	/**
	 * Asserts method is not static.
	 *
	 * @param method to validate
	 */
	public static void assertNotStatic(final Method method) 
	{
		assertNotStatic(method, null);
	}

	/**
	 * Asserts field is not static.
	 *
	 * @param field to validate
	 * @param annotation annotation to propagate in exception message
	 */
	public static void assertNotStatic(final Field field, Class<? extends Annotation> annotation) 
	{
		if (Modifier.isStatic(field.getModifiers()))
		{
			throw new InjectionException("Field " + getAnnotationMessage(annotation) + "cannot be static: " + field);
		}
	}

	/**
	 * Asserts field is not static.
	 *
	 * @param field to validate
	 */
	public static void assertNotStatic(final Field field) 
	{
		assertNotStatic(field, null);
	}

	/**
	 * Asserts field is not final.
	 *
	 * @param field to validate
	 * @param annotation annotation to propagate in exception message
	 */
	public static void assertNotFinal(final Field field, Class<? extends Annotation> annotation) 
	{
		if (Modifier.isFinal(field.getModifiers()))
		{
			throw new InjectionException("Field " + getAnnotationMessage(annotation) + "cannot be final: " + field);
		}
	}

	/**
	 * Asserts field is not final.
	 *
	 * @param field to validate
	 */
	public static void assertNotFinal(final Field field) 
	{
		assertNotFinal(field, null);
	}

	/**
	 * Asserts method have exactly one parameter.
	 *
	 * @param method to validate
	 * @param annotation annotation to propagate in exception message
	 */
	public static void assertOneParameter(final Method method, Class<? extends Annotation> annotation)
	{
		if (method.getParameterTypes().length != 1)
		{
			throw new InjectionException("Method " + getAnnotationMessage(annotation) + "have to declare exactly one parameter: " + method);
		}
	}

	/**
	 * Asserts method have exactly one parameter.
	 *
	 * @param method to validate
	 */
	public static void assertOneParameter(final Method method)
	{
		assertOneParameter(method, null);
	}

	/**
	 * Asserts valid Java Beans setter method name.
	 *
	 * @param method to validate
	 * @param annotation annotation to propagate in exception message
	 */
	public static void assertValidSetterName(final Method method, Class<? extends Annotation> annotation)
	{
		final String methodName = method.getName();
		final boolean correctMethodNameLength = methodName.length() > 3;
		final boolean isSetterMethodName = methodName.startsWith("set");
		final boolean isUpperCasedPropertyName = correctMethodNameLength ? Character.isUpperCase(methodName.charAt(3)) : false;

		if (!correctMethodNameLength || !isSetterMethodName || !isUpperCasedPropertyName)
		{
			throw new InjectionException("Method " + getAnnotationMessage(annotation) + "doesn't follow Java Beans setter method name: " + method);
		}
	}

	/**
	 * Asserts valid Java Beans setter method name.
	 *
	 * @param method to validate
	 */
	public static void assertValidSetterName(final Method method)
	{
		assertValidSetterName(method, null);
	}

	/**
	 * Asserts only one method is annotated with annotation.
	 *
	 * @param method collection of methods to validate
	 * @param annotation annotation to propagate in exception message
	 */
	public static void assertOnlyOneMethod(final Collection<Method> methods, Class<? extends Annotation> annotation)
	{
		if (methods.size() > 1)
		{
			throw new InjectionException("Only one method " + getAnnotationMessage(annotation) + "can exist");
		}
	}

	/**
	 * Asserts only one method is annotated with annotation.
	 *
	 * @param method collection of methods to validate
	 */
	public static void assertOnlyOneMethod(final Collection<Method> methods)
	{
		assertOnlyOneMethod(methods, null);
	}

	/**
	 * Constructs annotation message. If annotation class is null it returns empty string.
	 *
	 * @param annotation to construct message for
	 * @return annotation message or empty string
	 */
	private static String getAnnotationMessage(Class<? extends Annotation> annotation)
	{
		return annotation == null ? "" : "annotated with @" + annotation + " annotation "; 
	}

	
    private static void assertSerializability(StringBuilder builder, Object toPrint) {
        DebugObjectOutputStream doos = null;
        try {
            OutputStream base = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(base);
            doos = new DebugObjectOutputStream(oos);
            doos.writeObject(toPrint);
        }
        catch (IOException ioe) {
            List pathToBadObject = doos.getStack();
            builder.append("Path to non-Serializable Object: \n");
            for (Object cur : pathToBadObject) {
                builder.append(cur.toString()).append("\n");
            }
        }
    }
    
}

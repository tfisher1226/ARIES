package org.aries.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;


public class ObjectUtil {

	private static Log log = LogFactory.getLog(ObjectUtil.class);


	public static boolean isEmpty(Object object) {
		return object == null || object.toString().isEmpty();
	}

	public static boolean isEmpty(Collection<Object> list) {
		return list == null || list.size() == 0;
	}

	public static String getDefaultName(Object instance) {
		String qualifiedName = instance.getClass().getName();
		String className = NameUtil.getClassName(qualifiedName);
		return NameUtil.uncapName(className);
	}

	public static <T> T newInstance(String className) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		T object = ObjectUtil.loadObject(className, classLoader);
		return object;
	}

	public static <T> T newInstance(Class<T> classObject, Object... parameters) throws Exception {
		Class<?>[] parameterTypes = new Class<?>[parameters.length];
		for (int i = 0; i < parameters.length; i++)
			parameterTypes[i] = parameters[i].getClass();
		Constructor<?> constructor = classObject.getConstructor(parameterTypes);
		Assert.notNull(constructor, "Constructor not found for: "+classObject.getName());
		@SuppressWarnings("unchecked") T instance = (T) constructor.newInstance(parameters);
		return instance;
	}

	public static <T> T loadObject(String className) {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null)
				classLoader = ObjectUtil.class.getClassLoader();
			@SuppressWarnings("unchecked")
			Class<T> classObject = (Class<T>) Class.forName(className, true, classLoader);
			T object = loadObject(classObject);
			return object;
		} catch (ClassNotFoundException e) {
			//log.error("Cannot find class: "+className, e);
			throw new RuntimeException(e);
		}
	}

	public static <T> T loadObject(String className, ClassLoader classLoader) {
		try {
			@SuppressWarnings("unchecked")
			Class<T> classObject = (Class<T>) Class.forName(className, true, classLoader);
			T object = loadObject(classObject);
			return object;
		} catch (ClassNotFoundException e) {
			//log.error("Cannot find class: "+className, e);
			throw new RuntimeException(e);
		}
	}

	public static <T> T loadObject(Class<T> classObject) {
		try {
			assert classObject != null;
			T object = classObject.newInstance();
			return object;
		} catch (InstantiationException e) {
			//log.error("Cannot instantiate class: "+classObject.getName(), e);
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			//log.error("Cannot access class: "+classObject.getName(), e);
			throw new RuntimeException(e);
		}
	}

	public static <T> T loadObject(String className, Class<?>[] parameterTypes, Object[] parameters) {
		try {
			return loadObject(ClassUtil.loadClass(className), parameterTypes, parameters);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T loadObject(Class<?> classObject, Class<?>[] parameterTypes, Object[] parameters) {
		try {
			assert classObject != null;
			Constructor<?> constructor = classObject.getConstructor(parameterTypes);
			Assert.notNull(constructor, "Constructor not found for: "+classObject+"("+parameterTypes+")");
			@SuppressWarnings("unchecked") T intance = (T) constructor.newInstance(parameters);
			return intance;
		} catch (InstantiationException e) {
			//log.error("Cannot instantiate class: "+classObject.getName(), e);
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			//log.error("Cannot access class: "+classObject.getName(), e);
			throw new RuntimeException(e);
		} catch (Exception e) {
			//log.error("Error: "+classObject.getName(), e);
			throw new RuntimeException(e);
		}
	}

	public static final <T extends Serializable> T cloneObject(T object) { 
		try { 
			//Copy:
			ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
			objectOutputStream.close();
			byteOutputStream.close();
			byte[] byteData = byteOutputStream.toByteArray();

			//Restore:
			ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteData);
			ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
			@SuppressWarnings("unchecked") T newObject = (T) objectInputStream.readObject();
			return newObject;

		} catch (OptionalDataException e) { 
			throw new RuntimeException("Optional data found. " + e.getMessage());
		} catch (StreamCorruptedException e) { 
			throw new RuntimeException("Serialized object got corrupted. " + e.getMessage());
		} catch (ClassNotFoundException e) { 
			throw new RuntimeException("A class could not be found during deserialization. " + e.getMessage());
		} catch (NotSerializableException e) { 
			//log.error(e);
			throw new IllegalArgumentException("Object is not serializable: " + e.getMessage());
		} catch (IOException e) { 
			throw new RuntimeException("IO operation failed during serialization. " + e.getMessage());
		} 
	} 

	/**
	 * Return a hex string form of an object's identity hash code.
	 * @param obj the object
	 * @return the object's identity code in hex
	 */
	public static String getIdentityHexString(Object obj) {
		return Integer.toHexString(System.identityHashCode(obj));
	}

	/**
	 * Return whether the given throwable is a checked exception:
	 * that is, neither a RuntimeException nor an Error.
	 * @param ex the throwable to check
	 * @return whether the throwable is a checked exception
	 * @see java.lang.Exception
	 * @see java.lang.RuntimeException
	 * @see java.lang.Error
	 */
	public static boolean isCheckedException(Throwable e) {
		return !(e instanceof RuntimeException || e instanceof Error);
	}

	/**
	 * Append the given Object to the given array, returning a new array
	 * consisting of the input array contents plus the given Object.
	 * @param array the array to append to (can be <code>null</code>)
	 * @param obj the Object to append
	 * @return the new array (of the same component type; never <code>null</code>)
	 */
	public static Object[] addObjectToArray(Object[] array, Object obj) {
		Class<?> compType = Object.class;
		if (array != null) {
			compType = array.getClass().getComponentType();
		}
		else if (obj != null) {
			compType = obj.getClass();
		}
		int newArrLength = (array != null ? array.length + 1 : 1);
		Object[] newArr = (Object[]) Array.newInstance(compType, newArrLength);
		if (array != null) {
			System.arraycopy(array, 0, newArr, 0, array.length);
		}
		newArr[array.length] = obj;
		return newArr;
	}



	public static <T> void writeObjectToTempFile(T object, String name) throws Exception {
		File tempFile = getTempFile(name);
		FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
		try {
			objectOutputStream.writeObject(object);
		} catch (Exception e) {
			log.error("Cannot write object to temp-file: "+name, e);
			throw new RuntimeException(e);
		} finally {
			objectOutputStream.flush();
			objectOutputStream.close();
		}
	}

	public static <T> T readObjectFromTempFile(String name) throws Exception {
		File tempFile = getTempFile(name);
		FileInputStream fileInputStream = new FileInputStream(tempFile);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
		try {
			@SuppressWarnings("unchecked") T object = (T) objectInputStream.readObject();
			return object;
		} catch (Exception e) {
			log.error("Cannot read object from temp-file: "+name, e);
			throw new RuntimeException(e);
		} finally {
			objectInputStream.close();
		}
	}

	private static String getTempDir() throws Exception {
		String userDir = System.getProperty("user.dir");
		String tempDir = userDir+"/.tmp";
		FileUtil.assureDirectoryExists(tempDir);
		return tempDir;
	}

	private static File getTempFile(String name) throws Exception {
		String tempDir = getTempDir();
		File tempFile = new File(tempDir, "object-"+name);
		FileUtil.assureFileExists(tempFile);
		return tempFile;
	}


	/* 
	 * cloning methods
	 */

	public static String clone(String object) {
		return object != null ? new String(object) : null;
	}

	public static Boolean clone(Boolean object) {
		return object != null ? new Boolean(object) : null;
	}

	public static Integer clone(Integer object) {
		return object != null ? new Integer(object) : null;
	}

	public static Short clone(Short object) {
		return object != null ? new Short(object) : null;
	}

	public static Long clone(Long object) {
		return object != null ? new Long(object) : null;
	}

	public static Double clone(Double object) {
		return object != null ? new Double(object) : null;
	}

	public static BigInteger clone(BigInteger object) {
		return object != null ? new BigInteger(object.toByteArray()) : null;
	}

	public static BigDecimal clone(BigDecimal object) {
		return object != null ? new BigDecimal(object.longValue()) : null;
	}

	public static Date clone(Date object) {
		return object != null ? new Date(object.getTime()) : null;
	}

	public static byte[] clone(byte[] object) {
		return object != null ? Arrays.copyOf(object, object.length) : null;
	}

	public static char[] clone(char[] object) {
		return object != null ? Arrays.copyOf(object, object.length) : null;
	}

	public static <T> List<T> clone(List<T> list, Class<T> valueClass) {
		List<T> newList = new ArrayList<T>();
		Iterator<T> iterator = list.iterator();
		while (iterator.hasNext()) {
			try {
				T value = iterator.next();
				T newValue = getCloneValue(value, valueClass);
				newList.add(newValue);
			} catch (Exception e) {
				throw ExceptionUtil.rewrap(e);
			}
		}
		return newList;
	}

	public static <T> Set<T> clone(Set<T> list, Class<T> valueClass) {
		Set<T> newSet = new HashSet<T>();
		Iterator<T> iterator = list.iterator();
		while (iterator.hasNext()) {
			try {
				T value = iterator.next();
				T newValue = getCloneValue(value, valueClass);
				newSet.add(newValue);
			} catch (Exception e) {
				throw ExceptionUtil.rewrap(e);
			}
		}
		return newSet;
	}

	public static <K, V> Map<K, V> clone(Map<K, V> map, Class<K> keyClass, Class<V> valueClass) {
		Map<K, V> newMap = new HashMap<K, V>();
		Set<K> keySet = map.keySet();
		Iterator<K> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			try {
				K key = iterator.next();
				V value = map.get(key);
				K newKey = getCloneValue(key, keyClass);
				V newValue = getCloneValue(value, valueClass);
				newMap.put(newKey, newValue);
			} catch (Exception e) {
				throw ExceptionUtil.rewrap(e);
			}
		}
		return newMap;
	}

	public static <T> T getCloneValue(T value, Class<T> valueClass) {
		try {
			T cloneValue = null;
			if (valueClass.isEnum()) {
				cloneValue = value;
			} else if (valueClass.equals(Boolean.class)) { 
				cloneValue = value;
			} else {
				cloneValue = ObjectUtil.newInstance(valueClass, value);
			}
			return cloneValue;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Serializable clone(Serializable object) {
		return object != null ? (Serializable) getCloneValue(object, (Class<Serializable>) object.getClass()) : null;
	}

}

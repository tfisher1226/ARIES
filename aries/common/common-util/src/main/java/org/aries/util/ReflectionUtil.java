package org.aries.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;


public class ReflectionUtil {
	
	private static Log log = LogFactory.getLog(ReflectionUtil.class);
	

    public static <T> T newInstance(Class<T> objectType) {
		try {
	    	T object = objectType.newInstance();
	    	return object;
		} catch (SecurityException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		} catch (InstantiationException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		}
    }
    
    public static <T> T newInstance(Class<T> objectType, Class<?>[] parameterTypes, Object[] parameterValues) {
		try {
	    	Constructor<T> constructor = findConstructor(objectType, parameterTypes);
	    	if (constructor != null) {
	    		T instance = constructor.newInstance(parameterValues);
		    	return instance;
	    	}
		} catch (SecurityException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		} catch (InstantiationException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		}
    	return null;
    }

    public static <T> Constructor<T> findConstructor(Class<T> objectType, Class<?>[] parameterTypes) {
		try {
	    	Constructor<T> constructor = objectType.getConstructor(parameterTypes);
	    	return constructor;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
    	return null;
    }


    public static Method findMethodByName(Class<?> classObject, String methodName) {
    	Map<String, Method> methods = findMethods(classObject);
    	Method method = methods.get(methodName);
    	return method;
    }

    public static Map<String, Method> findMethods(Class<?> classObject) {
    	Map<String, Method> map = new HashMap<String, Method>();
    	Method[] methods = classObject.getMethods();
    	for (Method method : methods)
    		map.put(method.getName(), method);
    	return map;
    }

    /**
     * Locates the first method found within specified <code>objectType</code> 
     * named <code>methodName</code>.
     * @param objectType The class within which to search.
     * @param methodName The method name to look for.
     * @return The method if found, <code>null</code> otherwise
     */
    public static Method findMethod(Class<?> objectType, String methodName) {
    	return findMethod(objectType, methodName, -1);
    }
    
    /**
     * Locates the first method found within specified <code>objectType</code> 
     * named <code>methodName</code> and having the matching parameter count;
     * @param objectType The class within which to search.
     * @param methodName The method name to look for.
     * @param parameterCount The number of parameters to match.
     * @return The method if found, <code>null</code> otherwise
     */
    public static Method findMethod(Class<?> objectType, String methodName, int parameterCount) {
        Method[] methods = objectType.getMethods();
        for (int i=0; i < methods.length; i++) {
            try {
                Method method = methods[i];
                if (method.getName().equals(methodName)) {
                	if (parameterCount == -1)
                		return method;
                	if (parameterCount == method.getParameterTypes().length)
                		return method;
                }
            } catch (SecurityException e) {
            	log.info("Error", e);
                break;
            }
        }
        return null;
    }
    
    /**
     * Locates method within specified <code>objectType</code> named
     * <code>methodName</code> having a single parameter of specified
     * <code>parameterType</code>.  Returns <code>null</code> otherwise. 
     * @param objectType The class within which to search.
     * @param methodName The method name to look for.
     * @param parameterTypes The types of parameters to look for.
     * @return The method if found, <code>null</code> otherwise
     */
    public static Method findMethod(Class<?> objectType, String methodName, Class<?>[] parameterTypes) {
        Method[] methods = objectType.getMethods();
        for (int i=0; i < methods.length; i++) {
            try {
                Method target = objectType.getDeclaredMethod(methodName, parameterTypes);
                if (target != null)
                	return target;
            } catch (SecurityException e) {
            	log.info("Error", e);
                break;
            } catch (NoSuchMethodException e) {
            	log.info("Error", e);
                break;
            }
        }
        if (objectType.getSuperclass() != Object.class)
            return findMethod(objectType.getSuperclass(), methodName, parameterTypes);
        return null;
    }

    /**
     * Locates method within specified <code>objectType</code> named
     * that has the same parameter list and return type of the specified
     * argument set. Returns <code>null</code> otherwise. 
     * @param objectType The class within which to search.
     * @param parameterTypes The types of parameters to look for.
     * @param returnType The type of the return value to look for.
     * @param classLoader The classLoader to use for loading the types.
     * @return The method if found, <code>null</code> otherwise
     */
    public static Method findMethod(String objectType, String methodName, String[] parameterTypes, String returnType) {
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    	Method method = findMethod(objectType, methodName, parameterTypes, returnType, classLoader);
    	return method;
    }
    
    /**
     * Locates method within specified <code>objectType</code> named
     * that has the same parameter list and return type of the specified
     * argument set. Returns <code>null</code> otherwise. 
     * @param objectType The class within which to search.
     * @param parameterTypes The types of parameters to look for.
     * @param returnType The type of the return value to look for.
     * @return The method if found, <code>null</code> otherwise
     */
    public static Method findMethod(String objectType, String methodName, String[] parameterTypes, String returnType, ClassLoader classLoader) {
		Assert.notNull(classLoader, "Class-loader must be set");
    	try {
    		Class<?> objectClass = null;
			Class<?> returnClass = null;
			if (objectType != null)
				objectClass = ClassUtil.loadClass(objectType, classLoader);
			if (returnType != null)
				returnClass = ClassUtil.loadClass(returnType, classLoader);
        	Class<?>[] parameterClasses = new Class<?>[parameterTypes.length]; 
        	for (int i=0; i < parameterTypes.length; i++)
        		parameterClasses[i] = ClassUtil.loadClass(parameterTypes[i], classLoader);
			Method method = findMethod(objectClass, methodName, parameterClasses, returnClass);
			return method;
		} catch (ClassNotFoundException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		}
    }
    
    /**
     * Locates method within specified <code>objectType</code> named
     * that has the same parameter list and return type of the specified
     * argument set. Returns <code>null</code> otherwise. 
     * @param objectType The class within which to search.
     * @param parameterTypes The types of parameters to look for.
     * @param returnType The type of the return value to look for.
     * @return The method if found, <code>null</code> otherwise
     */
    public static Method findMethod(Class<?> objectClass, String methodName, String[] parameterTypes, String returnType, ClassLoader classLoader) {
    	try {
			Class<?> returnClass = null;
			if (returnType != null)
				returnClass = ClassUtil.loadClass(returnType, classLoader);
        	Class<?>[] parameterClasses = new Class<?>[parameterTypes.length]; 
        	for (int i=0; i < parameterTypes.length; i++)
        		parameterClasses[i] = ClassUtil.loadClass(parameterTypes[i], classLoader);
			Method method = findMethod(objectClass, methodName, parameterClasses, returnClass);
			return method;
		} catch (ClassNotFoundException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		}
    }

    /**
     * Locates method within specified <code>objectType</code> named
     * that has the same parameter list and return type of the specified
     * argument set. Returns <code>null</code> otherwise. 
     * @param objectType The class within which to search.
     * @param methodName The method name to look for.
     * @param parameterTypes The types of parameters to look for.
     * @return The method if found, <code>null</code> otherwise
     */
    public static Method findMethod(Class<?> objectType, String methodName, List<Class<?>> parameterTypes, Class<?> returnType) {
    	Class<?>[] parameterTypesArray = parameterTypes.toArray(new Class<?>[parameterTypes.size()]);
    	Method method = findMethod(objectType, methodName, parameterTypesArray, returnType);
    	return method;
    }
    
    /**
     * Locates method within specified <code>objectType</code> named
     * that has the same parameter list and return type of the specified
     * argument set. Returns <code>null</code> otherwise. 
     * @param objectType The class within which to search.
     * @param methodName The method name to look for.
     * @param parameterTypes The types of parameters to look for.
     * @return The method if found, <code>null</code> otherwise
     */
    public static Method findMethod(Class<?> objectType, String methodName, Class<?>[] parameterTypes, Class<?> returnType) {
    	Assert.notNull(methodName, "Method-name must be specified");
    	Assert.notNull(objectType, "Object-type must be specified");
        Method[] methods = objectType.getMethods();
        for (int i=0; i < methods.length; i++) {
            try {
            	Method method = methods[i];
            	String name = method.getName();
            	if (!name.equals(methodName))
            		continue;
                Method target = objectType.getDeclaredMethod(methodName, parameterTypes);
                if (target == null)
                    target = objectType.getMethod(methodName, parameterTypes);
                if (target == null)
                	continue;
                if (returnType == null || target.getReturnType() == null)
                	continue;
                if (returnType.equals(target.getReturnType()))
                	return target;
                if (returnType.isAssignableFrom(target.getReturnType()))
                	return target;
            } catch (NoSuchMethodException e) {
            	continue;
            } catch (SecurityException e) {
            	log.debug("Error", e);
                throw new RuntimeException(e);
            }
        }
        if (objectType.getSuperclass() != Object.class)
            return findMethod(objectType.getSuperclass(), methodName, parameterTypes, returnType);
        return null;
    }

    
	public static Method getCallingMethod() {
		return getMethod(5);
	}

	public static Method getCurrentMethod() {
		return getMethod(1);
	}
	
	public static String getCurrentMethodName() {
		return getMethodName(1);
	}

	/**
	 * Get the method for a depth in call stack.
	 * @param depth depth in the call stack (0 means current method, 1 means call method, ...)
	 * @return method 
	 */
	public static Method getMethod(int depth) {
		try {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			StackTraceElement stackTraceElement = stackTrace[depth];
			String className = stackTraceElement.getClassName();
			Class<?> classObject = ClassUtil.loadClass(className);
			String methodName = stackTraceElement.getMethodName();
			Method method = findMethod(classObject, methodName);
			return method;
		} catch (Exception e) {
        	log.debug("Error", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get the method name for a depth in call stack.
	 * @param depth depth in the call stack (0 means current method, 1 means call method, ...)
	 * @return method name
	 */
	public static String getMethodName(int depth) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement stackTraceElement = stackTrace[stackTrace.length - 1 - depth];
		return stackTraceElement.getMethodName();
	}
	
	
    /**
     * Invokes specified <code>method</code> using specified 
     * <code>arg</code> from specified <code>caller</code>. 
     * @param caller The object to invokes the method.
     * @param methodName The method to invoke.
     * @param args The set of args to use.
     */
    public static Object invokeMethod(Object caller, String methodName, Object arg) {
    	Method method = findMethod(caller.getClass(), methodName);
    	if (arg != null) {
	        Object[] args = new Object[] {arg};
			return invokeMethod(caller, method, args);
    	}
        Object[] args = new Object[] {};
		return invokeMethod(caller, method, args);
    }
    
    /**
     * Invokes specified <code>method</code> using specified 
     * <code>arg</code> from specified <code>caller</code>. 
     * @param caller The object to invokes the method.
     * @param method The method to invoke.
     * @param args The set of args to use.
     */
    public static Object invokeMethod(Object caller, Method method, Object arg) {
        return invokeMethod(caller, method, new Object[] {arg});
    }
    
    /**
     * Invokes specified <code>method</code> using specified set of 
     * <code>args</code> from specified <code>caller</code>.
     * All exception conditions thrown as @link{RuntimeException}s.
     * @param caller The object to invokes the method.
     * @param method The method to invoke.
     * @param args The set of args to use.
     */
    public static Object invokeMethod(Object caller, Method method, Object[] args) {
        try {
            method.setAccessible(true);
            Object result = method.invoke(caller, args);
            return result;
        } catch (IllegalArgumentException e) {
        	log.debug("Error", e);
            throw e;
        } catch (IllegalAccessException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
        }
    }
    
    
	public static Object getFieldValue(Object object, String fieldName) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);
			//TODO really change this value here?
			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			Object fieldValue = field.get(object);
			field.setAccessible(accessible);
			return fieldValue;
		} catch (SecurityException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		}
	}

	public static void setFieldValue(Object object, String fieldName, Object fieldValue) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);
			//TODO really change this value here?
			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			field.set(object, fieldValue);
			field.setAccessible(accessible);
		} catch (SecurityException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
        	log.debug("Error", e);
            throw new RuntimeException(e);
		}
	}


}

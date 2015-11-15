package org.aries.util;

import java.lang.reflect.Field;


public class FieldUtil {

	public static boolean isFieldExists(Object object, String fieldName) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);
			return field != null;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public static Object getFieldValue(Object object, String fieldName) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			Object fieldValue = field.get(object);
			return fieldValue;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}

	public static void setFieldValue(Object object, String fieldName, Object fieldValue) {
		try {
			Field field = getField(object, fieldName);
			field.setAccessible(true);
			field.set(object, fieldValue);

		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
		
//		try {
//			Field field = object.getClass().getDeclaredField(fieldName);
//			field.setAccessible(true);
//			field.set(object, fieldValue);
//			
//		} catch (Exception e) {
//			try {
//				Field field = object.getClass().getField(fieldName);
//				field.setAccessible(true);
//				field.set(object, fieldValue);
//				
//			} catch (Exception e2) {
//				try {
//					object.getClass().getDeclaredFields();
//					object.getClass().getFields();
//					object.getClass().getMethods();
//				} catch (Exception e3) {
//					throw ExceptionUtil.rewrap(e);
//				}
//			}
//		}
	}

	public static Field getField(Object object, String fieldName) {
		return getField(object.getClass(), object, fieldName);
	}
	
	public static Field getField(Class<?> classObject, Object object, String fieldName) {
		Field field = null;
		try {
			field = classObject.getDeclaredField(fieldName);
			
		} catch (Exception e) {
			Class<?> superclass = classObject.getSuperclass();
			if (superclass != null)
				field = getField(superclass, object, fieldName);
		}
		
		if (field == null)
			throw new RuntimeException("Field not found: "+fieldName);
		return field;
	}
	
}

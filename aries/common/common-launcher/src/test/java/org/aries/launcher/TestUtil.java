package org.aries.launcher;

import java.lang.reflect.Field;


public class TestUtil {

	public static Object getFieldValue(Object object, String fieldName) throws Exception {
		Field field = object.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		Object fieldValue = field.get(object);
		return fieldValue;
	}

	public static void setFieldValue(Object object, String fieldName, Object fieldValue) throws Exception {
		Field field = object.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(object, fieldValue);
	}

}

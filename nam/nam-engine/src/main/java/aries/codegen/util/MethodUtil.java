package aries.codegen.util;

import nam.model.Field;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.generation.model.ModelField;


public class MethodUtil {

	public static String toGetMethod(Field field) {
		return toGetMethod(field, true);
	}
	
	public static String toGetMethod(Field field, boolean enforceBooleanVersion) {
		String className = TypeUtil.getClassName(field.getType());
		String fieldName = NameUtil.capName(field.getName());
		return toGetMethod(field.getStructure(), className, fieldName);
	}
	
	public static String toGetMethod(ModelField modelField) {
		return toGetMethod(modelField, true);
	}

	public static String toGetMethod(ModelField modelField, boolean enforceBooleanVersion) {
		return toGetMethod(modelField.getStructure(), modelField.getClassName(), modelField.getCappedName(), enforceBooleanVersion);
	}

	public static String toGetMethod(String structure, String className, String fieldName) {
		return toGetMethod(structure, className, fieldName, true);
	}
	
	/**
	 * Returns the name of the get method for this attribute. The returned string
	 * does not contain the type of this attribute and does not end in brackets.
	 * For example <code>isInitialized</code> or <code>getAddress</code>.
	 * 
	 * @return the name of the get method for this attribute
	 */
	public static String toGetMethod(String structure, String className, String fieldName, boolean enforceBooleanVersion) {
		if (structure == null)
			throw new RuntimeException("CODE PROBLEM");
		if (structure.equals("map"))
			return "get" + fieldName;
		if (className == null)
			throw new RuntimeException("CODE PROBLEM");
		if (className.toLowerCase().equals("boolean") && enforceBooleanVersion)
			return "is" + fieldName;
		return "get" + fieldName;
	}
	
	public static String toSetMethod(ModelField modelField) {
		return "set" + modelField.getCappedName();
	}

	public static String toUnsetMethod(ModelField modelField) {
		return "unset" + modelField.getCappedName();
	}

	public static String toAddMethod(ModelField modelField) {
		return "addTo" + modelField.getCappedName();
	}

	public static String toPutMethod(ModelField modelField) {
		return "put" + modelField.getCappedName();
	}
	
	public static String toRemoveMethod(ModelField modelField) {
		return "removeFrom" + modelField.getCappedName();
	}

	public static String toClearMethod(ModelField modelField) {
		return "clear" + modelField.getCappedName();
	}

}

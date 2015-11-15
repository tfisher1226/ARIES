package aries.codegen.util;

import nam.model.util.TypeUtil;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;
import org.eclipse.emf.codegen.util.CodeGenUtil;

import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelField;
import aries.generation.model.ModelReference;


public class ModelFieldUtil {

	public static String getFieldName(ModelField modelField) {
		String name = modelField.getName();
		return getFieldName(name);
	}
	
	public static String getFieldName(String name) {
		if (CodeGenUtil.isJavaReservedWord(name) || CodeGenUtil.isJavaDefaultType(name))
			return name + "Object";
		return name;
	}

	public static String getFieldType(ModelField modelField) {
		return getFieldType(modelField, modelField.getStructure());
	}
	
	public static String getFieldType(ModelField modelField, String structure) {
		String className = getFieldClassName(modelField);
		String keyClassName = getFieldKeyClassName(modelField);
		return getFieldType(structure, className, keyClassName);
	}

	public static String getFieldClassName(ModelField modelField) {
		String className = NameUtil.getSimpleName(modelField.getClassName());
		if (modelField.isFullyQualified() && className != null)
			className = modelField.getPackageName() + "." + className;
		return className;
	}

	public static String getFieldKeyClassName(ModelField modelField) {
		String keyClassName = NameUtil.getSimpleName(modelField.getKeyClassName());
		if (modelField.isFullyQualified() && keyClassName != null)
			keyClassName = modelField.getKeyPackageName() + "." + keyClassName;
		return keyClassName;
	}
	
	public static String getFieldType(String structure, String className, String keyClassName) {
		if (StringUtils.isEmpty(structure))
			return className;
		if (structure.equals("list")) {
			className = "List<"+className+">";	
		} else if (structure.equals("set")) {
			className = "Set<"+className+">";	
		} else if (structure.equals("map")) {
			className = "Map<"+keyClassName+", "+className+">";
		} else if (structure.equals("collection")) {
			className = "Collection<"+className+">";	
		}
		return className;
	}

	public static String getParameterName(ModelField modelField) {
		if (modelField instanceof ModelReference) {
			String fieldName = NameUtil.uncapName(modelField.getClassName());
			return fieldName;
			
		} else if (modelField instanceof ModelAttribute) {
			String fieldName = NameUtil.uncapName(modelField.getName());
			if (CodeGenUtil.isJavaReservedWord(fieldName) || CodeGenUtil.isJavaDefaultType(fieldName))
				fieldName = fieldName+"Object";
			return modelField.getName();
		}
		
		return null;
	}

	public static String getStructuredName(ModelField modelField) {
		String parameterName = null;
		String structure = modelField.getStructure();
		if (modelField instanceof ModelAttribute)
			parameterName = getStructuredName(modelField.getName(), structure);
		if (modelField instanceof ModelReference) {
			String type = modelField.getType();
			String fieldName = TypeUtil.getClassName(type);
			if (fieldName.endsWith("Entity"))
				fieldName = NameUtil.uncapName(modelField.getName());
			parameterName = getStructuredName(fieldName, structure);
			//parameterName = getStructuredName(modelField.getClassName(), structure);
		}
		if (parameterName.startsWith("abstract")) {
			parameterName = parameterName.substring(8);
			parameterName = NameUtil.uncapName(parameterName);
		}
		return parameterName;
	}
	
	public static String getStructuredName(String className, String structure) {
		String fieldName = NameUtil.uncapName(getFieldName(className));
		if (structure.equals("item"))
			return fieldName;
		if (structure.equals("list")) {
			fieldName += "List";	
		} else if (structure.equals("set")) {
			fieldName += "Set";	
		} else if (structure.equals("map")) {
			fieldName += "Map";
		} else if (structure.equals("collection")) {
			fieldName += "Collection";	
		}
		return fieldName;
	}
	
	public static boolean isInverse(ModelField modelField) {
		if (modelField instanceof ModelReference) {
			ModelReference modelReference = (ModelReference) modelField;
			Boolean inverse = modelReference.isInverse();
			return inverse != null && inverse;
		}
		return false;
	}
	
	public static boolean isCollection(ModelField modelField) {
		String structure = modelField.getStructure();
		return structure.equals("list") ||
			structure.equals("set") ||
			structure.equals("map") ||
			structure.equals("collection");
	}

	public static boolean isList(ModelField modelField) {
		String structure = modelField.getStructure();
		return structure.equals("list");
	}
	
	public static boolean isSet(ModelField modelField) {
		String structure = modelField.getStructure();
		return structure.equals("set");
	}
	
	public static boolean isMap(ModelField modelField) {
		String structure = modelField.getStructure();
		return structure.equals("map");
	}
	
	
}

package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Operation;
import nam.model.Parameter;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.TypeMap;
import org.aries.util.Validator;


public class ParameterUtil extends BaseUtil {
	
	public static Object getKey(Parameter parameter) {
		return parameter.getType();
	}

	public static String getLabel(Parameter parameter) {
		return parameter.getName();
	}

	public static String getLabel(Collection<Parameter> parameterList) {
		return null;
	}
	
	public static boolean isEmpty(Parameter parameter) {
		if (parameter == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(parameter.getName());
		status |= StringUtils.isEmpty(parameter.getType());
		return status;
	}
	
	public static boolean isEmpty(Collection<Parameter> parameterList) {
		if (parameterList == null  || parameterList.size() == 0)
			return true;
		Iterator<Parameter> iterator = parameterList.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			if (!isEmpty(parameter))
				return false;
		}
		return true;
	}
	
	public static String toString(Parameter parameter) {
		if (isEmpty(parameter))
			return "Parameter: [uninitialized] "+parameter.toString();
		String text = parameter.toString();
		return text;
	}
	
	public static String toString(Collection<Parameter> parameterList) {
		if (isEmpty(parameterList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Parameter> iterator = parameterList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(parameter);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Parameter create() {
		Parameter parameter = new Parameter();
		initialize(parameter);
		return parameter;
	}
	
	public static void initialize(Parameter parameter) {
		if (parameter.getRequired() == null)
			parameter.setRequired(false);
	}
	
	public static boolean validate(Parameter parameter) {
		if (parameter == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(parameter.getName(), "\"Name\" must be specified");
		validator.notEmpty(parameter.getType(), "\"Type\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Parameter> parameterList) {
		Validator validator = Validator.getValidator();
		Iterator<Parameter> iterator = parameterList.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			//TODO break or accumulate?
			validate(parameter);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Parameter> parameterList) {
		Collections.sort(parameterList, createParameterComparator());
	}
	
	public static Collection<Parameter> sortRecords(Collection<Parameter> parameterCollection) {
		List<Parameter> list = new ArrayList<Parameter>(parameterCollection);
		Collections.sort(list, createParameterComparator());
		return list;
	}
	
	public static Comparator<Parameter> createParameterComparator() {
		return new Comparator<Parameter>() {
			public int compare(Parameter parameter1, Parameter parameter2) {
				Object key1 = getKey(parameter1);
				Object key2 = getKey(parameter2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Parameter clone(Parameter parameter) {
		if (parameter == null)
			return null;
		Parameter clone = create();
		clone.setName(ObjectUtil.clone(parameter.getName()));
		clone.setType(ObjectUtil.clone(parameter.getType()));
		clone.setKey(ObjectUtil.clone(parameter.getKey()));
		clone.setConstruct(ObjectUtil.clone(parameter.getConstruct()));
		clone.setRequired(ObjectUtil.clone(parameter.getRequired()));
		return clone;
	}
	
	public static List<Parameter> clone(List<Parameter> parameterList) {
		if (parameterList == null)
			return null;
		List<Parameter> newList = new ArrayList<Parameter>();
		Iterator<Parameter> iterator = parameterList.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			Parameter clone = clone(parameter);
			newList.add(clone);
		}
		return newList;
	}



	public static String getConstruct(Parameter parameter) {
		String construct = parameter.getConstruct();
		if (construct == null) {
			construct = "item";
			parameter.setConstruct(construct);
		}
		return construct;
	}
	
	public static String getTypeSignature(Parameter parameter) {
		String className = TypeUtil.getClassName(parameter.getType());
		String structure = parameter.getConstruct();
		if (structure.equals("item")) {
			return className;
		} else if (structure.equals("list")) {
			return "List<"+className+">";
		} else if (structure.equals("set")) {
			return "Set<"+className+">";
		} else if (structure.equals("map")) {
			String keyClassName = TypeUtil.getClassName(parameter.getKey());
			return "Map<"+keyClassName+", "+className+">";
		}
		return null;
	}

	public static Class<?> getParameterType(Parameter parameter) {
    	if (parameter != null && parameter.getType() != null)
    		return TypeMap.INSTANCE.getTypeClassByTypeName(parameter.getType());
    	//TODO get this outta here
		return null;
	}

	/*
	 * Parameter factory methods
	 * -------------------------
	 */
	
	public static Parameter createParameter(Class<?> parameterType) {
		Parameter parameter = new Parameter();
		String parameterName = parameterType.getCanonicalName();
		String simpleName = NameUtil.getSimpleName(parameterName);
		parameter.setName(NameUtil.uncapName(simpleName));
		String typeName = TypeUtil.getTypeFromClass(parameterType);
		//Assert.notNull(typeName, "ParameterType for method \""+methodName+"\" not found: "+parameterType.getCanonicalName());
		parameter.setType(typeName);
		return parameter;
	}
	
	public static Parameter createParameter(String packageName, String className, String name, String structure) {
		Parameter parameter = new Parameter();
		parameter.setConstruct(structure);
		parameter.setType(TypeUtil.getTypeFromPackageAndClass(packageName, className));
		parameter.setName(name);
		return parameter;
	}
	

	public static String getArgumentString(Operation operation) {
		return getParameterString(operation.getParameters(), false);
	}

	public static String getParameterString(Operation operation) {
		return getParameterString(operation.getParameters(), true);
	}

	public static String getParameterString(List<Parameter> parameters, boolean includeType) {
		StringBuffer buf = new StringBuffer();
		Iterator<Parameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			String parameterName = parameter.getName();
			String parameterType = parameter.getType();
			String parameterClassName = TypeUtil.getLocalPart(parameterType);
			if (parameterClassName.endsWith("Message") && !parameterName.endsWith("Message"))
				parameterName += "Message";
			if (i > 0)
				buf.append(", ");
			if (includeType) {
				buf.append(parameterClassName);
				buf.append(" ");
			}
			buf.append(parameterName);
		}
		return buf.toString();
	}

	
	public static boolean equals(Parameter parameter1, Parameter parameter2) {
		Assert.notNull(parameter1, "Parameter1 must be specified");
		Assert.notNull(parameter2, "Parameter2 must be specified");
		Assert.notNull(parameter1.getName(), "Parameter1 name must be specified");
		Assert.notNull(parameter2.getName(), "Parameter2 name must be specified");
		Assert.notNull(parameter1.getType(), "Parameter1 type must be specified");
		Assert.notNull(parameter2.getType(), "Parameter2 type must be specified");
		if (!parameter1.getName().equals(parameter2.getName()))
			return false;
		if (!parameter1.getType().equals(parameter2.getType()))
			return false;
		return true;
	}

	public static boolean equals(List<Parameter> parameters1, List<Parameter> parameters2) {
		Assert.notNull(parameters1, "Parameter1 list must be specified");
		Assert.notNull(parameters2, "Parameter2 list must be specified");
		if (parameters1.size() != parameters2.size())
			return false;
		for (int i=0; i < parameters1.size(); i++) {
			Parameter parameter1 = parameters1.get(i);
			Parameter parameter2 = parameters1.get(i);
			if (!equals(parameter1, parameter2))
				return false;
		}
		return true;
	}
	
}

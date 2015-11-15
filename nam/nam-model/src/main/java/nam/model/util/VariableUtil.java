package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Variable;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class VariableUtil extends BaseUtil {
	
	public static String getKey(Variable variable) {
		return variable.getType();
	}
	
	public static String getLabel(Variable variable) {
		return variable.getName();
	}
	
	public static boolean getLabel(Collection<Variable> variableList) {
		if (variableList == null  || variableList.size() == 0)
			return true;
		Iterator<Variable> iterator = variableList.iterator();
		while (iterator.hasNext()) {
			Variable variable = iterator.next();
			if (!isEmpty(variable))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Variable variable) {
		if (variable == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(variable.getName());
		status |= StringUtils.isEmpty(variable.getType());
		return status;
	}
	
	public static boolean isEmpty(Collection<Variable> variableList) {
		if (variableList == null  || variableList.size() == 0)
			return true;
		Iterator<Variable> iterator = variableList.iterator();
		while (iterator.hasNext()) {
			Variable variable = iterator.next();
			if (!isEmpty(variable))
				return false;
		}
		return true;
	}
	
	public static String toString(Variable variable) {
		if (isEmpty(variable))
			return "Variable: [uninitialized] "+variable.toString();
		String text = variable.toString();
		return text;
	}
	
	public static String toString(Collection<Variable> variableList) {
		if (isEmpty(variableList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Variable> iterator = variableList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Variable variable = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(variable);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Variable create() {
		Variable variable = new Variable();
		initialize(variable);
		return variable;
	}
	
	public static void initialize(Variable variable) {
		//nothing for now
	}
	
	public static boolean validate(Variable variable) {
		if (variable == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(variable.getName(), "\"Name\" must be specified");
		validator.notEmpty(variable.getType(), "\"Type\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Variable> variableList) {
		Validator validator = Validator.getValidator();
		Iterator<Variable> iterator = variableList.iterator();
		while (iterator.hasNext()) {
			Variable variable = iterator.next();
			//TODO break or accumulate?
			validate(variable);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Variable> variableList) {
		Collections.sort(variableList, createVariableComparator());
	}
	
	public static Collection<Variable> sortRecords(Collection<Variable> variableCollection) {
		List<Variable> list = new ArrayList<Variable>(variableCollection);
		Collections.sort(list, createVariableComparator());
		return list;
	}
	
	public static Comparator<Variable> createVariableComparator() {
		return new Comparator<Variable>() {
			public int compare(Variable variable1, Variable variable2) {
				Object key1 = getKey(variable1);
				Object key2 = getKey(variable2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Variable clone(Variable variable) {
		if (variable == null)
			return null;
		Variable clone = create();
		clone.setName(ObjectUtil.clone(variable.getName()));
		clone.setType(ObjectUtil.clone(variable.getType()));
		//TODO clone.setObject(ObjectUtil.clone(variable.getObject()));
		return clone;
	}
	
	public static List<Variable> clone(List<Variable> variableList) {
		if (variableList == null)
			return null;
		List<Variable> newList = new ArrayList<Variable>();
		Iterator<Variable> iterator = variableList.iterator();
		while (iterator.hasNext()) {
			Variable variable = iterator.next();
			Variable clone = clone(variable);
			newList.add(clone);
		}
		return newList;
	}
	
}

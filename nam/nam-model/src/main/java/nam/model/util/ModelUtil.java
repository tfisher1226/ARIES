package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import nam.model.Model;


public class ModelUtil extends BaseUtil {
	
	public static Object getKey(Model model) {
		return model.getName();
	}
	
	public static String getLabel(Model model) {
		return model.getName();
	}
	
	public static boolean isEmpty(Model model) {
		if (model == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Model> modelList) {
		if (modelList == null  || modelList.size() == 0)
			return true;
		Iterator<Model> iterator = modelList.iterator();
		while (iterator.hasNext()) {
			Model model = iterator.next();
			if (!isEmpty(model))
				return false;
		}
		return true;
	}
	
	public static String toString(Model model) {
		if (isEmpty(model))
			return "Model: [uninitialized] "+model.toString();
		String text = model.toString();
		return text;
	}
	
	public static String toString(Collection<Model> modelList) {
		if (isEmpty(modelList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Model> iterator = modelList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Model model = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(model);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Model create() {
		Model model = new Model();
		initialize(model);
		return model;
	}
	
	public static void initialize(Model model) {
		//nothing for now
	}
	
	public static boolean validate(Model model) {
		if (model == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Model> modelList) {
		Validator validator = Validator.getValidator();
		Iterator<Model> iterator = modelList.iterator();
		while (iterator.hasNext()) {
			Model model = iterator.next();
			//TODO break or accumulate?
			validate(model);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Model> modelList) {
		Collections.sort(modelList, createModelComparator());
	}
	
	public static Collection<Model> sortRecords(Collection<Model> modelCollection) {
		List<Model> list = new ArrayList<Model>(modelCollection);
		Collections.sort(list, createModelComparator());
		return list;
	}
	
	public static Comparator<Model> createModelComparator() {
		return new Comparator<Model>() {
			public int compare(Model model1, Model model2) {
				Object key1 = getKey(model1);
				Object key2 = getKey(model2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Model clone(Model model) {
		if (model == null)
			return null;
		Model clone = create();
		clone.setName(ObjectUtil.clone(model.getName()));
		clone.setPackage(ObjectUtil.clone(model.getPackage()));
		return clone;
	}
	
}

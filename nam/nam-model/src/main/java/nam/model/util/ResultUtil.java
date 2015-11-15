package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Fault;
import nam.model.Result;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.TypeMap;
import org.aries.util.Validator;


public class ResultUtil extends BaseUtil {
	
	public static Object getKey(Result result) {
		return result.getType();
	}

	public static String getLabel(Result result) {
		return result.getType();
	}

	public static String getLabel(Collection<Result> resultList) {
		return null;
	}

	public static boolean isEmpty(Result result) {
		if (result == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(result.getType());
		return status;
	}
	
	public static boolean isEmpty(Collection<Result> resultList) {
		if (resultList == null  || resultList.size() == 0)
			return true;
		Iterator<Result> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Result result = iterator.next();
			if (!isEmpty(result))
				return false;
		}
		return true;
	}
	
	public static String toString(Result result) {
		if (isEmpty(result))
			return "Result: [uninitialized] "+result.toString();
		String text = result.toString();
		return text;
	}
	
	public static String toString(Collection<Result> resultList) {
		if (isEmpty(resultList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Result> iterator = resultList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Result result = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(result);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Result create() {
		Result result = new Result();
		initialize(result);
		return result;
	}
	
	public static void initialize(Result result) {
		if (result.getRequired() == null)
			result.setRequired(false);
	}
	
	public static boolean validate(Result result) {
		if (result == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(result.getType(), "\"Type\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Result> resultList) {
		Validator validator = Validator.getValidator();
		Iterator<Result> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Result result = iterator.next();
			//TODO break or accumulate?
			validate(result);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Result> resultList) {
		Collections.sort(resultList, createResultComparator());
	}
	
	public static Collection<Result> sortRecords(Collection<Result> resultCollection) {
		List<Result> list = new ArrayList<Result>(resultCollection);
		Collections.sort(list, createResultComparator());
		return list;
	}
	
	public static Comparator<Result> createResultComparator() {
		return new Comparator<Result>() {
			public int compare(Result result1, Result result2) {
				Object key1 = getKey(result1);
				Object key2 = getKey(result2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Result clone(Result result) {
		if (result == null)
			return null;
		Result clone = create();
		clone.setType(ObjectUtil.clone(result.getType()));
		clone.setName(ObjectUtil.clone(result.getName()));
		clone.setKey(ObjectUtil.clone(result.getKey()));
		clone.setConstruct(ObjectUtil.clone(result.getConstruct()));
		clone.setRequired(ObjectUtil.clone(result.getRequired()));
		return clone;
	}
	
	public static List<Result> clone(List<Result> resultList) {
		if (resultList == null)
			return null;
		List<Result> newList = new ArrayList<Result>();
		Iterator<Result> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Result result = iterator.next();
			Result clone = clone(result);
			newList.add(clone);
		}
		return newList;
	}


	public static String getConstruct(Result result) {
		String construct = result.getConstruct();
		if (construct == null) {
			construct = "item";
			result.setConstruct(construct);
		}
		return construct;
	}
	
	public static Class<?> getResultType(Result result) {
    	if (result != null && result.getType() != null)
    		return TypeMap.INSTANCE.getTypeClassByTypeName(result.getType());
    	//TODO get this outta here
		return null;
	}

	public static Result createResult(Class<?> resultType) {
		Result result = new Result();
		String resultName = resultType.getCanonicalName();
		String simpleName = NameUtil.getSimpleName(resultName);
		result.setName(NameUtil.uncapName(simpleName));
		String typeName = TypeUtil.getTypeFromClass(resultType);
		//Assert.notNull(typeName, "TypeName not found for: "+resultName);
		if (typeName == null)
			typeName = resultName;
		result.setType(typeName);
		return result;
	}
	
	public static boolean equals(Result result1, Result result2) {
		Assert.notNull(result1, "Result1 must be specified");
		Assert.notNull(result2, "Result2 must be specified");
		Assert.notNull(result1.getName(), "Result1 name must be specified");
		Assert.notNull(result2.getName(), "Result2 name must be specified");
		Assert.notNull(result1.getType(), "Result1 type must be specified");
		Assert.notNull(result2.getType(), "Result2 type must be specified");
		if (!result1.getName().equals(result2.getName()))
			return false;
		if (!result1.getType().equals(result2.getType()))
			return false;
		return true;
	}
	
	public static boolean equals(List<Result> results1, List<Result> results2) {
		Assert.notNull(results1, "Result1 list must be specified");
		Assert.notNull(results2, "Result2 list must be specified");
		if (results1.size() != results2.size())
			return false;
		for (int i=0; i < results1.size(); i++) {
			Result result1 = results1.get(i);
			Result result2 = results2.get(i);
			if (!equals(result1, result2))
				return false;
		}
		return true;
	}

}

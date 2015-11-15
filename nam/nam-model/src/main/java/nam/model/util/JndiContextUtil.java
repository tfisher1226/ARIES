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

import nam.model.JndiContext;


public class JndiContextUtil extends BaseUtil {
	
	public static String getKey(JndiContext jndiContext) {
		return jndiContext.getConnectionUrl();
	}
	
	public static String getLabel(JndiContext jndiContext) {
		return jndiContext.getName();
	}
	
	public static boolean getLabel(Collection<JndiContext> jndiContextList) {
		if (jndiContextList == null  || jndiContextList.size() == 0)
			return true;
		Iterator<JndiContext> iterator = jndiContextList.iterator();
		while (iterator.hasNext()) {
			JndiContext jndiContext = iterator.next();
			if (!isEmpty(jndiContext))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(JndiContext jndiContext) {
		if (jndiContext == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<JndiContext> jndiContextList) {
		if (jndiContextList == null  || jndiContextList.size() == 0)
			return true;
		Iterator<JndiContext> iterator = jndiContextList.iterator();
		while (iterator.hasNext()) {
			JndiContext jndiContext = iterator.next();
			if (!isEmpty(jndiContext))
				return false;
		}
		return true;
	}
	
	public static String toString(JndiContext jndiContext) {
		if (isEmpty(jndiContext))
			return "JndiContext: [uninitialized] "+jndiContext.toString();
		String text = jndiContext.toString();
		return text;
	}
	
	public static String toString(Collection<JndiContext> jndiContextList) {
		if (isEmpty(jndiContextList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<JndiContext> iterator = jndiContextList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			JndiContext jndiContext = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(jndiContext);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static JndiContext create() {
		JndiContext jndiContext = new JndiContext();
		initialize(jndiContext);
		return jndiContext;
	}
	
	public static void initialize(JndiContext jndiContext) {
		//nothing for now
	}
	
	public static boolean validate(JndiContext jndiContext) {
		if (jndiContext == null)
			return false;
		Validator validator = Validator.getValidator();
		PropertiesUtil.validate(jndiContext.getProperties());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<JndiContext> jndiContextList) {
		Validator validator = Validator.getValidator();
		Iterator<JndiContext> iterator = jndiContextList.iterator();
		while (iterator.hasNext()) {
			JndiContext jndiContext = iterator.next();
			//TODO break or accumulate?
			validate(jndiContext);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<JndiContext> jndiContextList) {
		Collections.sort(jndiContextList, createJndiContextComparator());
	}
	
	public static Collection<JndiContext> sortRecords(Collection<JndiContext> jndiContextCollection) {
		List<JndiContext> list = new ArrayList<JndiContext>(jndiContextCollection);
		Collections.sort(list, createJndiContextComparator());
		return list;
	}
	
	public static Comparator<JndiContext> createJndiContextComparator() {
		return new Comparator<JndiContext>() {
			public int compare(JndiContext jndiContext1, JndiContext jndiContext2) {
				Object key1 = getKey(jndiContext1);
				Object key2 = getKey(jndiContext2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static JndiContext clone(JndiContext jndiContext) {
		if (jndiContext == null)
			return null;
		JndiContext clone = create();
		clone.setName(ObjectUtil.clone(jndiContext.getName()));
		clone.setUserName(ObjectUtil.clone(jndiContext.getUserName()));
		clone.setPassword(ObjectUtil.clone(jndiContext.getPassword()));
		clone.setConnectionUrl(ObjectUtil.clone(jndiContext.getConnectionUrl()));
		clone.setContextFactory(ObjectUtil.clone(jndiContext.getContextFactory()));
		clone.setContextPackages(ObjectUtil.clone(jndiContext.getContextPackages()));
		clone.setProperties(PropertiesUtil.clone(jndiContext.getProperties()));
		return clone;
	}
	
}

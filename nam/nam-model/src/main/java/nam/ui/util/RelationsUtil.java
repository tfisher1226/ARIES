package nam.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.ui.Relations;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.Validator;


public class RelationsUtil extends BaseUtil {

	public static boolean isEmpty(Relations relations) {
		if (relations == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Relations> relationsList) {
		if (relationsList == null  || relationsList.size() == 0)
			return true;
		Iterator<Relations> iterator = relationsList.iterator();
		while (iterator.hasNext()) {
			Relations relations = iterator.next();
			if (!isEmpty(relations))
				return false;
		}
		return true;
	}
	
	public static String toString(Relations relations) {
		if (isEmpty(relations))
			return "Relations: [uninitialized] "+relations.toString();
		String text = relations.toString();
		return text;
	}
	
	public static String toString(Collection<Relations> relationsList) {
		if (isEmpty(relationsList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Relations> iterator = relationsList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Relations relations = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(relations);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Relations create() {
		Relations relations = new Relations();
		initialize(relations);
		return relations;
	}
	
	public static void initialize(Relations relations) {
		//nothing for now
	}
	
	public static boolean validate(Relations relations) {
		if (relations == null)
			return false;
		Validator validator = Validator.getValidator();
		RelationUtil.validate(relations.getRelations());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Relations> relationsList) {
		Validator validator = Validator.getValidator();
		Iterator<Relations> iterator = relationsList.iterator();
		while (iterator.hasNext()) {
			Relations relations = iterator.next();
			//TODO break or accumulate?
			validate(relations);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}

	public static Relations clone(Relations relations) {
		if (relations == null)
			return null;
		Relations clone = create();
		clone.setRelations(RelationUtil.clone(relations.getRelations()));
		return clone;
	}
	
}

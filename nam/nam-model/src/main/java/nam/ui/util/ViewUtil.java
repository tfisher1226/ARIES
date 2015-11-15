package nam.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import nam.model.util.ImportUtil;
import nam.ui.View;


public class ViewUtil extends BaseUtil {
	
	public static Object getKey(View view) {
		return view.getDomain() + "." + view.getName();
	}
	
	public static String getLabel(View view) {
		return view.getDomain() + "." + view.getName();
	}
	
	public static boolean isEmpty(View view) {
		if (view == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(view.getName());
		status |= StringUtils.isEmpty(view.getDomain());
		return status;
	}
	
	public static boolean isEmpty(Collection<View> viewList) {
		if (viewList == null  || viewList.size() == 0)
			return true;
		Iterator<View> iterator = viewList.iterator();
		while (iterator.hasNext()) {
			View view = iterator.next();
			if (!isEmpty(view))
				return false;
		}
		return true;
	}
	
	public static String toString(View view) {
		if (isEmpty(view))
			return "View: [uninitialized] "+view.toString();
		String text = view.toString();
		return text;
	}
	
	public static String toString(Collection<View> viewList) {
		if (isEmpty(viewList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<View> iterator = viewList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			View view = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(view);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static View create() {
		View view = new View();
		initialize(view);
		return view;
	}
	
	public static void initialize(View view) {
		//nothing for now
	}
	
	public static boolean validate(View view) {
		if (view == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(view.getDomain(), "\"Domain\" must be specified");
		validator.notEmpty(view.getName(), "\"Name\" must be specified");
		ControlsUtil.validate(view.getControls());
		ImportUtil.validate(view.getImports());
		RelationsUtil.validate(view.getRelations());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<View> viewList) {
		Validator validator = Validator.getValidator();
		Iterator<View> iterator = viewList.iterator();
		while (iterator.hasNext()) {
			View view = iterator.next();
			//TODO break or accumulate?
			validate(view);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<View> viewList) {
		Collections.sort(viewList, createViewComparator());
	}
	
	public static Collection<View> sortRecords(Collection<View> viewCollection) {
		List<View> list = new ArrayList<View>(viewCollection);
		Collections.sort(list, createViewComparator());
		return list;
	}
	
	public static Comparator<View> createViewComparator() {
		return new Comparator<View>() {
			public int compare(View view1, View view2) {
				Object key1 = getKey(view1);
				Object key2 = getKey(view2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static View clone(View view) {
		if (view == null)
			return null;
		View clone = create();
		clone.setImports(ImportUtil.clone(view.getImports()));
		clone.setName(ObjectUtil.clone(view.getName()));
		clone.setDomain(ObjectUtil.clone(view.getDomain()));
		clone.setControls(ControlsUtil.clone(view.getControls()));
		clone.setRelations(RelationsUtil.clone(view.getRelations()));
		return clone;
	}
	
}

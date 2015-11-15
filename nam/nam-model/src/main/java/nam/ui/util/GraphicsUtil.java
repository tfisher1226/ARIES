package nam.ui.util;

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

import nam.ui.Graphics;


public class GraphicsUtil extends BaseUtil {
	
	public static Object getKey(Graphics graphics) {
		return graphics.getName();
	}
	
	public static String getLabel(Graphics graphics) {
		return graphics.getName();
	}
	
	public static boolean isEmpty(Graphics graphics) {
		if (graphics == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Graphics> graphicsList) {
		if (graphicsList == null  || graphicsList.size() == 0)
			return true;
		Iterator<Graphics> iterator = graphicsList.iterator();
		while (iterator.hasNext()) {
			Graphics graphics = iterator.next();
			if (!isEmpty(graphics))
				return false;
		}
		return true;
	}
	
	public static String toString(Graphics graphics) {
		if (isEmpty(graphics))
			return "Graphics: [uninitialized] "+graphics.toString();
		String text = graphics.toString();
		return text;
	}
	
	public static String toString(Collection<Graphics> graphicsList) {
		if (isEmpty(graphicsList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Graphics> iterator = graphicsList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Graphics graphics = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(graphics);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Graphics create() {
		Graphics graphics = new Graphics();
		initialize(graphics);
		return graphics;
	}
	
	public static void initialize(Graphics graphics) {
		//nothing for now
	}
	
	public static boolean validate(Graphics graphics) {
		if (graphics == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Graphics> graphicsList) {
		Validator validator = Validator.getValidator();
		Iterator<Graphics> iterator = graphicsList.iterator();
		while (iterator.hasNext()) {
			Graphics graphics = iterator.next();
			//TODO break or accumulate?
			validate(graphics);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Graphics> graphicsList) {
		Collections.sort(graphicsList, createGraphicsComparator());
	}
	
	public static Collection<Graphics> sortRecords(Collection<Graphics> graphicsCollection) {
		List<Graphics> list = new ArrayList<Graphics>(graphicsCollection);
		Collections.sort(list, createGraphicsComparator());
		return list;
	}
	
	public static Comparator<Graphics> createGraphicsComparator() {
		return new Comparator<Graphics>() {
			public int compare(Graphics graphics1, Graphics graphics2) {
				Object key1 = getKey(graphics1);
				Object key2 = getKey(graphics2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Graphics clone(Graphics graphics) {
		if (graphics == null)
			return null;
		Graphics clone = create();
		clone.setName(ObjectUtil.clone(graphics.getName()));
		clone.setValue(ObjectUtil.clone(graphics.getValue()));
		return clone;
	}
	
}

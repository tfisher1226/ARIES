package org.aries.util.text;

import java.util.Iterator;
import java.util.List;


public class TextUtil {

	public static String joinTextWithCommas(List<String> list) {
		StringBuffer buf = new StringBuffer();
		if (list.size() > 0) {
			Iterator<String> iterator = list.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				String field = iterator.next();
				buf.append(field);
				if (i < list.size())
					buf.append(", ");
			}
		}
		return buf.toString();
	}
	
}

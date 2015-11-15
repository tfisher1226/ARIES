package org.aries.util;

import java.util.Map;


public class Selector {

	private Map<String, String> criteria;
	
	public void addSelection(String field, String filter) {
		criteria.put(field, filter);
	}

}

package org.aries.cache;

import java.io.Serializable;


class CachedItem implements Serializable {
	
	private String elementName;
	
	private Object elementData;

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public Object getElementData() {
		return elementData;
	}

	public void setElementData(Object elementData) {
		this.elementData = elementData;
	}
	
}

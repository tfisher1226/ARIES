package org.aries.ui.manager;

import java.io.Serializable;
import java.util.Collection;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;


@SuppressWarnings("serial")
public abstract class AbstractEnumerationHelper<T> implements Serializable {

	public abstract String toString(T record);
	
//	public abstract String toString(Collection<T> records);
	
	
	protected Display getDisplay() {
		return BeanContext.getFromSession("display");
	}

	public String toString(Collection<T> itemList) {
		return "";
	}
	
}


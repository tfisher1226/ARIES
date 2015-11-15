package org.aries.ui.manager;

import java.io.Serializable;
import java.util.Collection;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;


@SuppressWarnings("serial")
public abstract class AbstractElementHelper<T> implements Serializable {

	public abstract boolean isEmpty(T record);
	
	public abstract String toString(T record);
	
	public abstract String toString(Collection<T> records);
	
	public abstract boolean validate(T record);
	
	public abstract boolean validate(Collection<T> records);
	
	
	protected Display getDisplay() {
		return BeanContext.getFromSession("display");
	}

}


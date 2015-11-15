package org.aries.runtime;

import org.aries.util.ObjectUtil;


public class BeanFactory {

	//TODO use ThreadLocal cache of classloaders here?
	
	public <T> T newInstance(String className) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		return ObjectUtil.loadObject(className, classLoader);
	}
	
	public <T> T newInstance(Class<T> classObject) {
		return ObjectUtil.loadObject(classObject);
	}
	
}

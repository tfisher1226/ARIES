package org.aries.validate.util;

import org.aries.AssertionFailure;
import org.aries.util.ObjectUtil;


public class Point {

	private boolean enabled = true;

	private boolean blocked = false;

	private ClassLoader classLoader;

	private Class<? extends RuntimeException> exceptionClass = AssertionFailure.class;

	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public void setExceptionClass(Class<? extends RuntimeException> exceptionClass) {
		this.exceptionClass = exceptionClass;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}
	
	public Class<? extends RuntimeException> getExceptionClass() {
		return exceptionClass;
	}
	
	
	public void notNull(Object object) {
		notNull(object, "[check point failure] - object must not be null");
	}

	public void notNull(Object object, String message) {
		if ((enabled && object == null) || blocked) {
			throwException(message);
		}
	}
	
	public void condition(Object object) {
		condition(object, null);
	}

	public void condition(Object object, String message) {
		if ((enabled && object == null) || blocked) {
			throwException(message);
		}
	}
	
	protected void throwException(String message) {
		Class<?>[] parameterTypes = new Class<?>[] {String.class};
		Object[] parameterValues = new Object[] {message};
		RuntimeException exceptionObject = ObjectUtil.loadObject(getExceptionClass(), parameterTypes, parameterValues);
		throw exceptionObject;
	}

}

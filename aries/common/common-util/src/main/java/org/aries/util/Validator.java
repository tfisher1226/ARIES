package org.aries.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;


public class Validator {

	private static Validator validator;
	
	private List<String> messages = new ArrayList<String>();
	
	
	public Collection<String> getMessages() {
		return messages;
	}

	public static Validator getValidator() {
		if (validator == null)
			validator = new Validator();
		return validator;
	}

	public static void setValidator(Validator validator) {
		Validator.validator = validator;
	}

	public boolean isValid() {
		return messages.size() == 0;
	}

	public void isTrue(boolean value, String message) {
		try {
			Assert.isTrue(value, message);
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void isFalse(boolean value, String message) {
		try {
			Assert.isFalse(value, message);
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void notNull(Object object, String message) {
		try {
			Assert.notNull(object, message);
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void notEmpty(String string, String message) {
		try {
			Assert.notEmpty(string, message);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public <T> void notEmpty(Collection<T> collection, String message) {
		try {
			Assert.notEmpty(collection, message);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void validateString(Object value) {
		if (value instanceof String == false)
			messages.add("Value must be of type text.");
		String textValue = (String) value;
		if (StringUtils.isEmpty(textValue))
			messages.add("Value must be specified.");
	}

	public void validateInteger(Object value) {
		if (value instanceof Integer == false)
			messages.add("Value must be of type Integer.");
	}

	public void validateLong(Object value) {
		if (value instanceof Long == false)
			messages.add("Value must be of type Long.");
	}

	public void validateDate(Object value) {
		if (value instanceof Date == false)
			messages.add("Value must be of type Date.");
	}
	
	protected void handleException(Exception e) {
		Throwable cause = ExceptionUtils.getRootCause(e);
		if (cause == null)
			cause = e;
		cause.printStackTrace();
		String message = cause.getMessage();
		messages.add(message);
		//Display display = BeanContext.getFromSession("display");
		//display.error("Error: "+message);
	}

}

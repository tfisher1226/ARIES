package org.aries.ui.validate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.validator.ValidatorException;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;


@SuppressWarnings("serial")
public abstract class AbstractValidator implements Serializable {

	private Collection<String> messages = new ArrayList<String>();
	
	
	protected void resetState() {
		messages.clear();
	}
	
	public Collection<String> getMessages() {
		return messages;
	}
	
	protected Display getDisplay() {
		return BeanContext.getFromSession("display");
	}

	protected org.aries.util.Validator getValidator() {
		//return org.aries.ui.validate.Validator.getValidator();
		return BeanContext.getFromEvent("validator");
	}
	
	protected void throwErrors(Collection<String> errors) {
	}
	
	protected void throwWarning(String detail) {
		throwException(FacesMessage.SEVERITY_WARN, "Validation Error", detail);
	}

	protected void throwError(String detail) {
		throwException(FacesMessage.SEVERITY_ERROR, "Validation Error", detail);
	}

	protected void throwException(Severity severity, String summary, String detail) {
		FacesMessage facesMessage = new FacesMessage(severity, summary, detail);
		//messages = BeanContext.get("messages");
		//messages.add(facesMessage);
		//TODO reimplement this: get rid of Display
		Display display = getDisplay();
		if (display != null)
			display.add(facesMessage);
		throw new ValidatorException(facesMessage);
	}

}

package org.aries.ui.convert;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.validator.ValidatorException;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;


@SuppressWarnings("serial")
public abstract class AbstractConverter implements Serializable {

	protected void throwWarning(String detail) {
		throwException(FacesMessage.SEVERITY_WARN, "Conversion Error", detail);
	}

	protected void throwError(String detail) {
		throwException(FacesMessage.SEVERITY_ERROR, "Conversion Error", detail);
	}

	protected void throwException(Severity severity, String summary, String detail) {
		FacesMessage facesMessage = new FacesMessage(severity, summary, detail);
		//TODO reimplement this: get rid of Display
		Display display = BeanContext.getFromSession("display");
		if (display != null)
			display.add(facesMessage);
		throw new ValidatorException(facesMessage);
	}
	
}


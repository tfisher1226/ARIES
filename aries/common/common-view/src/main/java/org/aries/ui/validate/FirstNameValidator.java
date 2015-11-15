package org.aries.ui.validate;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator(value="firstNameValidator")
public class FirstNameValidator extends AbstractValidator implements Validator, Serializable {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null)
			throwError("First Name must be specified");
		if (value instanceof String == false)
			throwError("Unexpected value: "+value);
		String text = (String) value;
		if (text.length() == 0)
			throwError("First Name must be specified");
	}
	
}

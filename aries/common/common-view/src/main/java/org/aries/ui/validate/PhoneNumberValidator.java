package org.aries.ui.validate;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.aries.common.PhoneNumber;
import org.aries.common.util.PhoneNumberUtil;


@FacesValidator(value="phoneNumberValidator")
public class PhoneNumberValidator extends AbstractValidator implements Validator, Serializable {

	public PhoneNumberValidator() {
		// nothing for now
	}
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value != null) {
			if (value instanceof PhoneNumber == false)
				throwError("Unexpected value: "+value);
			PhoneNumber phoneNumber = (PhoneNumber) value;
			PhoneNumberUtil.toString(phoneNumber);
		}
	}
	
}

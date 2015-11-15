package org.aries.ui.validate;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.aries.common.ZipCode;
import org.aries.common.util.ZipCodeUtil;


@FacesValidator(value="zipCodeValidator")
public class ZipCodeValidator extends AbstractValidator implements Validator, Serializable {

	public ZipCodeValidator() {
		// nothing for now
	}
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value != null) {
			if (value instanceof ZipCode == false)
				throwError("Unexpected value: "+value);
			ZipCode zipCode = (ZipCode) value;
			ZipCodeUtil.toString(zipCode);
		}
	}
	
}

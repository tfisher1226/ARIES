package org.aries.ui.validate;

import java.io.Serializable;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator(value="numberValidator")
public class NumberValidator extends AbstractValidator implements Validator, Serializable {
	
	private static final String DECIMAL_REGEX = "\\-?[0-9]+(\\.[0-9]+)?";

	public NumberValidator() {
		// nothing for now
	}
	
	@Override
	public void validate(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		if (value == null) {
			generateError(component, value);
		}
		
		String strValue = value.toString();
		if (!strValue.trim().matches(DECIMAL_REGEX)) {
			generateError(component, (String) value);
		}
	}
	
	private String generateError(UIComponent component, Object value) throws ValidatorException {
		//FacesMessages messages = FacesMessages.instance();
		
		Map<String, Object> attributes = component.getAttributes();
		Object validatorMessage = attributes.get("validatorMessage");
		
		String errorMessage = validatorMessage instanceof String ?
			(String) validatorMessage : String.format("Value \"%s\" from component %s is not a number.", value, component.getId());
		
		//messages.add(StatusMessage.Severity.ERROR, errorMessage);
        throw new ValidatorException(new FacesMessage(errorMessage));
	}

}

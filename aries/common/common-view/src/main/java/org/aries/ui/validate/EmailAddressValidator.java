package org.aries.ui.validate;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.aries.common.EmailAddress;


@FacesValidator(value="emailAddressValidator")
public class EmailAddressValidator extends AbstractValidator implements Validator, Serializable {

	public EmailAddressValidator() {
		// nothing for now
	}
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null)
			return;
		if (value instanceof EmailAddress == false)
			throwError("Unexpected value: "+value);
		EmailAddress email = (EmailAddress) value;
		String text = email.getUrl();
		if (text.length() == 0)
			return;
		if (!isValidEmailAddress(text))
			throwError("Invalid email address: "+text);
	}

	public static boolean isValidEmailAddress(String text) {
		if (StringUtils.isEmpty(text)) 
			return false;
		boolean result = true;
		try {
			new InternetAddress(text);
			if (!hasNameAndDomain(text))
				result = false;
		} catch (AddressException e) {
			result = false;
		}
		return result;
	}

	private static boolean hasNameAndDomain(String text){
		String[] tokens = text.split("@");
		return tokens.length == 2 && !StringUtils.isEmpty(tokens[0]) && !StringUtils.isEmpty(tokens[1]);
	}

}

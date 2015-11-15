package admin.ui.user;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.aries.ui.validate.AbstractValidator;

import admin.User;
import admin.util.UserUtil;


@ApplicationScoped
@Named("userValidator")
public class UserValidator extends AbstractValidator implements javax.faces.validator.Validator, Serializable {

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		resetState();
		if (value != null) {
			if (value instanceof User == false)
				throwError("Unexpected value: "+value);
			User user = (User) value;
			if (UserUtil.validate(user) == false) {
				Collection<String> messages = getValidator().getMessages();
				getDisplay().addErrors(messages);
				throwErrors(messages);
			}
		}
	}
	
}

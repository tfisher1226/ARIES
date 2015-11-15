package admin.ui.user;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.aries.ui.validate.AbstractValidator;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.faces.Validator;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.User;
import admin.util.UserUtil;


@Validator
@AutoCreate
@BypassInterceptors
@Name("userValidator")
@Scope(ScopeType.APPLICATION)
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

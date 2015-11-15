package admin.ui.role;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.aries.ui.validate.AbstractValidator;

import admin.Role;
import admin.util.RoleUtil;


@ApplicationScoped
@Named("roleValidator")
public class RoleValidator extends AbstractValidator implements javax.faces.validator.Validator, Serializable {
	
	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		resetState();
		if (value != null) {
			if (value instanceof Role == false)
				throwError("Unexpected value: "+value);
			Role role = (Role) value;
			if (RoleUtil.validate(role) == false) {
				Collection<String> messages = getValidator().getMessages();
				getDisplay().addErrors(messages);
				throwErrors(messages);
			}
		}
	}
	
}

package admin.ui.permission;

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

import admin.Permission;
import admin.util.PermissionUtil;


@Validator
@AutoCreate
@BypassInterceptors
@Name("permissionValidator")
@Scope(ScopeType.APPLICATION)
public class PermissionValidator extends AbstractValidator implements javax.faces.validator.Validator, Serializable {
	
	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		resetState();
		if (value != null) {
			if (value instanceof Permission == false)
				throwError("Unexpected value: "+value);
			Permission permission = (Permission) value;
			if (PermissionUtil.validate(permission) == false) {
				Collection<String> messages = getValidator().getMessages();
				getDisplay().addErrors(messages);
				throwErrors(messages);
			}
		}
	}
	
}

package nam.ui.userInterface;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.aries.ui.validate.AbstractValidator;

import nam.ui.UserInterface;
import nam.ui.util.UserInterfaceUtil;


@ApplicationScoped
@Named("userInterfaceValidator")
public class UserInterfaceValidator extends AbstractValidator implements Validator, Serializable {
	
	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		resetState();
		if (value != null) {
			if (value instanceof UserInterface == false)
				throwError("Unexpected value: "+value);
			UserInterface userInterface = (UserInterface) value;
			if (UserInterfaceUtil.validate(userInterface) == false) {
				Collection<String> messages = getValidator().getMessages();
				getDisplay().addErrors(messages);
				throwErrors(messages);
			}
		}
	}
	
}

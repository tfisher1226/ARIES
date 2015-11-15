package nam.ui.userInterfaceType;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.aries.ui.validate.AbstractValidator;

import nam.ui.UserInterfaceType;
import nam.ui.util.UserInterfaceTypeUtil;


@ApplicationScoped
@Named("userInterfaceTypeValidator")
public class UserInterfaceTypeValidator extends AbstractValidator implements Validator, Serializable {
	
	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		resetState();
		if (value != null) {
			if (value instanceof UserInterfaceType == false)
				throwError("Unexpected value: "+value);
		}
	}
	
}

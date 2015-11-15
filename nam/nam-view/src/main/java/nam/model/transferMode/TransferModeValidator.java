package nam.model.transferMode;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import nam.model.TransferMode;

import org.aries.ui.validate.AbstractValidator;


@ApplicationScoped
@Named("transferModeValidator")
public class TransferModeValidator extends AbstractValidator implements Validator, Serializable {
	
	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		resetState();
		if (value != null) {
			if (value instanceof TransferMode == false)
				throwError("Unexpected value: "+value);
		}
	}
	
}

package nam.model.literal;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.aries.ui.validate.AbstractValidator;

import nam.model.Literal;
import nam.model.util.LiteralUtil;


@ApplicationScoped
@Named("literalValidator")
public class LiteralValidator extends AbstractValidator implements Validator, Serializable {
	
	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		resetState();
		if (value != null) {
			if (value instanceof Literal == false)
				throwError("Unexpected value: "+value);
			Literal literal = (Literal) value;
			if (LiteralUtil.validate(literal) == false) {
				Collection<String> messages = getValidator().getMessages();
				getDisplay().addErrors(messages);
				throwErrors(messages);
			}
		}
	}
	
}

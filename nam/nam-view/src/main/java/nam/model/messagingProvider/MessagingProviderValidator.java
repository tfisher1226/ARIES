package nam.model.messagingProvider;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.aries.ui.validate.AbstractValidator;

import nam.model.MessagingProvider;
import nam.model.util.MessagingProviderUtil;


@ApplicationScoped
@Named("messagingProviderValidator")
public class MessagingProviderValidator extends AbstractValidator implements Validator, Serializable {
	
	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		resetState();
		if (value != null) {
			if (value instanceof MessagingProvider == false)
				throwError("Unexpected value: "+value);
			MessagingProvider messagingProvider = (MessagingProvider) value;
			if (MessagingProviderUtil.validate(messagingProvider) == false) {
				Collection<String> messages = getValidator().getMessages();
				getDisplay().addErrors(messages);
				throwErrors(messages);
			}
		}
	}
	
}

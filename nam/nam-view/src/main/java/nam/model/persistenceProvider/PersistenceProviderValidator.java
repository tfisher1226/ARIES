package nam.model.persistenceProvider;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.aries.ui.validate.AbstractValidator;

import nam.model.PersistenceProvider;
import nam.model.util.PersistenceProviderUtil;


@ApplicationScoped
@Named("persistenceProviderValidator")
public class PersistenceProviderValidator extends AbstractValidator implements Validator, Serializable {
	
	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		resetState();
		if (value != null) {
			if (value instanceof PersistenceProvider == false)
				throwError("Unexpected value: "+value);
			PersistenceProvider persistenceProvider = (PersistenceProvider) value;
			if (PersistenceProviderUtil.validate(persistenceProvider) == false) {
				Collection<String> messages = getValidator().getMessages();
				getDisplay().addErrors(messages);
				throwErrors(messages);
			}
		}
	}
	
}

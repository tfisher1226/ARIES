package nam.model.cacheProvider;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.aries.ui.validate.AbstractValidator;

import nam.model.CacheProvider;
import nam.model.util.CacheProviderUtil;


@ApplicationScoped
@Named("cacheProviderValidator")
public class CacheProviderValidator extends AbstractValidator implements Validator, Serializable {
	
	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		resetState();
		if (value != null) {
			if (value instanceof CacheProvider == false)
				throwError("Unexpected value: "+value);
			CacheProvider cacheProvider = (CacheProvider) value;
			if (CacheProviderUtil.validate(cacheProvider) == false) {
				Collection<String> messages = getValidator().getMessages();
				getDisplay().addErrors(messages);
				throwErrors(messages);
			}
		}
	}
	
}

package nam.model.dependencyScope;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.aries.ui.validate.AbstractValidator;

import nam.model.DependencyScope;
import nam.model.util.DependencyScopeUtil;


@ApplicationScoped
@Named("dependencyScopeValidator")
public class DependencyScopeValidator extends AbstractValidator implements Validator, Serializable {
	
	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		resetState();
		if (value != null) {
			if (value instanceof DependencyScope == false)
				throwError("Unexpected value: "+value);
		}
	}
	
}

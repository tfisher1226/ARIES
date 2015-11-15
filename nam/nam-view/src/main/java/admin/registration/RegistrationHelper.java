package admin.registration;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import admin.Registration;
import admin.util.RegistrationUtil;


@SessionScoped
@Named("registrationHelper")
public class RegistrationHelper extends AbstractElementHelper<Registration> implements Serializable {
	
	@Override
	public boolean isEmpty(Registration registration) {
		return RegistrationUtil.isEmpty(registration);
	}
	
	@Override
	public String toString(Registration registration) {
		return RegistrationUtil.toString(registration);
	}
	
	@Override
	public String toString(Collection<Registration> registrationList) {
		return RegistrationUtil.toString(registrationList);
	}
	
	@Override
	public boolean validate(Registration registration) {
		return RegistrationUtil.validate(registration);
	}
	
	@Override
	public boolean validate(Collection<Registration> registrationList) {
		return RegistrationUtil.validate(registrationList);
	}
	
}

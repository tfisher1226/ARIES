package admin.ui.registration;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.model.DataModel;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Registration;
import admin.util.RegistrationUtil;


@Startup
@AutoCreate
@BypassInterceptors
@Name("registrationHelper")
@Scope(ScopeType.SESSION)
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

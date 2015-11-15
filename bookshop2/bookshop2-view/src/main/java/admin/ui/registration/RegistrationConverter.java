package admin.ui.registration;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import admin.Registration;
import admin.util.RegistrationUtil;


@ApplicationScoped
@Named("registrationConverter")
public class RegistrationConverter extends AbstractConverter implements javax.faces.convert.Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		RegistrationListManager registrationListManager = BeanContext.getFromSession("registrationListManager");
		Registration registration = registrationListManager.getRecordByName(value);
		return registration;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Registration registration = (Registration) value;
		return RegistrationUtil.toString(registration);
	}
	
}

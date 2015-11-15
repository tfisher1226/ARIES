package admin.ui.registration;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Registration;
import admin.util.RegistrationUtil;


@Converter
@AutoCreate
@BypassInterceptors
@Name("registrationConverter")
@Scope(ScopeType.APPLICATION)
public class RegistrationConverter extends AbstractConverter implements javax.faces.convert.Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		RegistrationListManager registrationListManager = BeanContext.getFromSession("mainRegistrationListManager");
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

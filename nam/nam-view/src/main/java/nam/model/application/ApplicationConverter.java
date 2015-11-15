package nam.model.application;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Application;
import nam.model.util.ApplicationUtil;


@FacesConverter(value = "applicationConverter", forClass = Application.class)
public class ApplicationConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ApplicationListManager applicationListManager = BeanContext.getFromSession("applicationListManager");
		Application application = applicationListManager.getRecordByName(value);
		return application;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Application application = (Application) value;
		return ApplicationUtil.getLabel(application);
	}
	
}

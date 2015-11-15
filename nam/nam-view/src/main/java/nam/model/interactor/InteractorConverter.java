package nam.model.interactor;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Interactor;
import nam.model.util.InteractorUtil;


@FacesConverter(value = "interactorConverter", forClass = Interactor.class)
public class InteractorConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		InteractorListManager interactorListManager = BeanContext.getFromSession("interactorListManager");
		Interactor interactor = interactorListManager.getRecordByName(value);
		return interactor;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Interactor interactor = (Interactor) value;
		return InteractorUtil.toString(interactor);
	}
	
}

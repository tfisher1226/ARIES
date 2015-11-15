package nam.model.persistence;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Persistence;
import nam.model.util.PersistenceUtil;


@FacesConverter(value = "persistenceConverter", forClass = Persistence.class)
public class PersistenceConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		PersistenceListManager persistenceListManager = BeanContext.getFromSession("persistenceListManager");
		Persistence persistence = persistenceListManager.getRecordByName(value);
		return persistence;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Persistence persistence = (Persistence) value;
		return PersistenceUtil.getLabel(persistence);
	}
	
}

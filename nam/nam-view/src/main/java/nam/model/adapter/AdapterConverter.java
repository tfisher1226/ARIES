package nam.model.adapter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Adapter;
import nam.model.util.AdapterUtil;


@FacesConverter(value = "adapterConverter", forClass = Adapter.class)
public class AdapterConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		AdapterListManager adapterListManager = BeanContext.getFromSession("adapterListManager");
		Adapter adapter = adapterListManager.getRecordByName(value);
		return adapter;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Adapter adapter = (Adapter) value;
		return AdapterUtil.getLabel(adapter);
	}
	
}

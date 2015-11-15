package nam.model.master;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Master;
import nam.model.util.MasterUtil;


@FacesConverter(value = "masterConverter", forClass = Master.class)
public class MasterConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		MasterListManager masterListManager = BeanContext.getFromSession("masterListManager");
		Master master = masterListManager.getRecordByName(value);
		return master;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Master master = (Master) value;
		return MasterUtil.toString(master);
	}
	
}

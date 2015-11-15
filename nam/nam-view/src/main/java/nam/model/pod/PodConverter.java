package nam.model.pod;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Pod;
import nam.model.util.PodUtil;


@FacesConverter(value = "podConverter", forClass = Pod.class)
public class PodConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		PodListManager podListManager = BeanContext.getFromSession("podListManager");
		Pod pod = podListManager.getRecordByName(value);
		return pod;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Pod pod = (Pod) value;
		return PodUtil.toString(pod);
	}
	
}

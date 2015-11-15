package nam.model.container;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Container;
import nam.model.util.ContainerUtil;


@FacesConverter(value = "containerConverter", forClass = Container.class)
public class ContainerConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ContainerListManager containerListManager = BeanContext.getFromSession("containerListManager");
		Container container = containerListManager.getRecordByName(value);
		return container;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Container container = (Container) value;
		return ContainerUtil.toString(container);
	}
	
}

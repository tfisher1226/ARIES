package nam.ui.layout;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.ui.Layout;
import nam.ui.util.LayoutUtil;


@FacesConverter(value = "layoutConverter", forClass = Layout.class)
public class LayoutConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		LayoutListManager layoutListManager = BeanContext.getFromSession("layoutListManager");
		Layout layout = layoutListManager.getRecordByName(value);
		return layout;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Layout layout = (Layout) value;
		return LayoutUtil.toString(layout);
	}
	
}

package nam.ui.graphics;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.ui.Graphics;
import nam.ui.util.GraphicsUtil;


@FacesConverter(value = "graphicsConverter", forClass = Graphics.class)
public class GraphicsConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		GraphicsListManager graphicsListManager = BeanContext.getFromSession("graphicsListManager");
		Graphics graphics = graphicsListManager.getRecordByName(value);
		return graphics;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Graphics graphics = (Graphics) value;
		return GraphicsUtil.toString(graphics);
	}
	
}

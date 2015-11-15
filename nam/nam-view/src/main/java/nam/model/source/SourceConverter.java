package nam.model.source;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Source;
import nam.model.util.SourceUtil;


@FacesConverter(value = "sourceConverter", forClass = Source.class)
public class SourceConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		SourceListManager sourceListManager = BeanContext.getFromSession("sourceListManager");
		Source source = sourceListManager.getRecordByName(value);
		return source;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Source source = (Source) value;
		return SourceUtil.getLabel(source);
	}
	
}

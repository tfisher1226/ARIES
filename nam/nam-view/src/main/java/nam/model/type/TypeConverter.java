package nam.model.type;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Type;
import nam.model.util.TypeUtil;


@FacesConverter(value = "typeConverter", forClass = Type.class)
public class TypeConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		TypeListManager typeListManager = BeanContext.getFromSession("typeListManager");
		Type type = typeListManager.getRecordByName(value);
		return type;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Type type = (Type) value;
		return TypeUtil.getLabel(type);
	}
	
}

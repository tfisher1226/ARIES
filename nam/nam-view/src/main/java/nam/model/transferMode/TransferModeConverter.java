package nam.model.transferMode;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.convert.AbstractConverter;

import nam.model.TransferMode;


@FacesConverter(value = "transferModeConverter", forClass = TransferMode.class)
public class TransferModeConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		TransferMode transferMode = TransferMode.valueOf(value.toUpperCase());
		return transferMode;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		TransferMode transferMode = null;
		if (value instanceof String)
			transferMode = TransferMode.valueOf((String) value);
		else if (value instanceof TransferMode)
			transferMode = (TransferMode) value;
		return transferMode.value();
	}
	
}

package nam.model.packageType;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.convert.AbstractConverter;

import nam.model.PackageType;


@FacesConverter(value = "packageTypeConverter", forClass = PackageType.class)
public class PackageTypeConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		PackageType packageType = PackageType.valueOf(value.toUpperCase());
		return packageType;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		PackageType packageType = null;
		if (value instanceof String)
			packageType = PackageType.valueOf((String) value);
		else if (value instanceof PackageType)
			packageType = (PackageType) value;
		return packageType.value();
	}
	
}

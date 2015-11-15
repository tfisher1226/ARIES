package nam.model._import;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Import;
import nam.model.util.ImportUtil;


@FacesConverter(value = "importConverter", forClass = Import.class)
public class ImportConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ImportListManager importListManager = BeanContext.getFromSession("importListManager");
		Import _import = importListManager.getRecordByName(value);
		return _import;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Import _import = (Import) value;
		return ImportUtil.toString(_import);
	}
	
}

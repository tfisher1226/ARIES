package nam.model.reference;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Reference;
import nam.model.util.ReferenceUtil;


@FacesConverter(value = "referenceConverter", forClass = Reference.class)
public class ReferenceConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ReferenceListManager referenceListManager = BeanContext.getFromSession("referenceListManager");
		Reference reference = referenceListManager.getRecordByName(value);
		return reference;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Reference reference = (Reference) value;
		return ReferenceUtil.toString(reference);
	}
	
}

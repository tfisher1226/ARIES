package nam.model.model;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Model;
import nam.model.util.ModelUtil;


@FacesConverter(value = "modelConverter", forClass = Model.class)
public class ModelConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ModelListManager modelListManager = BeanContext.getFromSession("modelListManager");
		Model model = modelListManager.getRecordByName(value);
		return model;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Model model = (Model) value;
		return ModelUtil.toString(model);
	}
	
}

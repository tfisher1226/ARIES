package nam.model.unit;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Unit;
import nam.model.util.UnitUtil;


@FacesConverter(value = "unitConverter", forClass = Unit.class)
public class UnitConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		UnitListManager unitListManager = BeanContext.getFromSession("unitListManager");
		Unit unit = unitListManager.getRecordByName(value);
		return unit;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Unit unit = (Unit) value;
		return UnitUtil.getLabel(unit);
	}
	
}

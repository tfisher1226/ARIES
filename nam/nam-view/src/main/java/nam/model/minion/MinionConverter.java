package nam.model.minion;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Minion;
import nam.model.util.MinionUtil;


@FacesConverter(value = "minionConverter", forClass = Minion.class)
public class MinionConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		MinionListManager minionListManager = BeanContext.getFromSession("minionListManager");
		Minion minion = minionListManager.getRecordByName(value);
		return minion;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Minion minion = (Minion) value;
		return MinionUtil.toString(minion);
	}
	
}

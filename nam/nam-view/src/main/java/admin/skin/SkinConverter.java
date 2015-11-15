package admin.skin;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import admin.Skin;
import admin.util.SkinUtil;


@FacesConverter(value = "skinConverter", forClass = Skin.class)
public class SkinConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		SkinListManager skinListManager = BeanContext.getFromSession("skinListManager");
		Skin skin = skinListManager.getRecordByName(value);
		return skin;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Skin skin = (Skin) value;
		return SkinUtil.toString(skin);
	}
	
}

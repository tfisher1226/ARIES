package admin.ui.skin;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Skin;
import admin.util.SkinUtil;


@Converter
@AutoCreate
@BypassInterceptors
@Name("skinConverter")
@Scope(ScopeType.APPLICATION)
public class SkinConverter extends AbstractConverter implements javax.faces.convert.Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		SkinListManager skinListManager = BeanContext.getFromSession("mainSkinListManager");
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

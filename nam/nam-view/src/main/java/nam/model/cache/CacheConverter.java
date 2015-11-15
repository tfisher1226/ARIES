package nam.model.cache;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Cache;
import nam.model.util.CacheUtil;


@FacesConverter(value = "cacheConverter", forClass = Cache.class)
public class CacheConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		CacheListManager cacheListManager = BeanContext.getFromSession("cacheListManager");
		Cache cache = cacheListManager.getRecordByName(value);
		return cache;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Cache cache = (Cache) value;
		return CacheUtil.toString(cache);
	}
	
}

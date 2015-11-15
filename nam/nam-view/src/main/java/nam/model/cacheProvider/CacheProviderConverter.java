package nam.model.cacheProvider;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.CacheProvider;
import nam.model.util.CacheProviderUtil;


@FacesConverter(value = "cacheProviderConverter", forClass = CacheProvider.class)
public class CacheProviderConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		CacheProviderListManager cacheProviderListManager = BeanContext.getFromSession("cacheProviderListManager");
		CacheProvider cacheProvider = cacheProviderListManager.getRecordByName(value);
		return cacheProvider;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		CacheProvider cacheProvider = (CacheProvider) value;
		return CacheProviderUtil.toString(cacheProvider);
	}
	
}

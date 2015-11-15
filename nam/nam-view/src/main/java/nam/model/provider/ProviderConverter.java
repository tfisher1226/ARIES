package nam.model.provider;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Provider;
import nam.model.util.ProviderUtil;


@FacesConverter(value = "providerConverter", forClass = Provider.class)
public class ProviderConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ProviderListManager providerListManager = BeanContext.getFromSession("providerListManager");
		Provider provider = providerListManager.getRecordByName(value);
		return provider;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Provider provider = (Provider) value;
		return ProviderUtil.toString(provider);
	}
	
}

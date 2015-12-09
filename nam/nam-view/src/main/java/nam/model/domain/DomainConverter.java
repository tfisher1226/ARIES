package nam.model.domain;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Domain;
import nam.model.util.DomainUtil;


@FacesConverter(value = "domainConverter", forClass = Domain.class)
public class DomainConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		DomainListManager domainListManager = BeanContext.getFromSession("domainListManager");
		Domain domain = domainListManager.getRecordByName(value);
		return domain;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Domain domain = (Domain) value;
		return DomainUtil.getLabel(domain);
	}
	
}

package nam.model.persistenceProvider;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.PersistenceProvider;
import nam.model.util.PersistenceProviderUtil;


@FacesConverter(value = "persistenceProviderConverter", forClass = PersistenceProvider.class)
public class PersistenceProviderConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		PersistenceProviderListManager persistenceProviderListManager = BeanContext.getFromSession("persistenceProviderListManager");
		PersistenceProvider persistenceProvider = persistenceProviderListManager.getRecordByName(value);
		return persistenceProvider;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		PersistenceProvider persistenceProvider = (PersistenceProvider) value;
		return PersistenceProviderUtil.toString(persistenceProvider);
	}
	
}

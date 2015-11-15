package nam.model.repository;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Repository;
import nam.model.util.RepositoryUtil;


@FacesConverter(value = "repositoryConverter", forClass = Repository.class)
public class RepositoryConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		RepositoryListManager repositoryListManager = BeanContext.getFromSession("repositoryListManager");
		Repository repository = repositoryListManager.getRecordByName(value);
		return repository;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Repository repository = (Repository) value;
		return RepositoryUtil.getLabel(repository);
	}
	
}

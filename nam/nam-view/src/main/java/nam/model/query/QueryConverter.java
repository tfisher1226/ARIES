package nam.model.query;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Query;
import nam.model.util.QueryUtil;


@FacesConverter(value = "queryConverter", forClass = Query.class)
public class QueryConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		QueryListManager queryListManager = BeanContext.getFromSession("queryListManager");
		Query query = queryListManager.getRecordByName(value);
		return query;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Query query = (Query) value;
		return QueryUtil.toString(query);
	}
	
}

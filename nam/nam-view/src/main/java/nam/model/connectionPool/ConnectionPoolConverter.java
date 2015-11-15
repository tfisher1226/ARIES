package nam.model.connectionPool;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.ConnectionPool;
import nam.model.util.ConnectionPoolUtil;


@FacesConverter(value = "connectionPoolConverter", forClass = ConnectionPool.class)
public class ConnectionPoolConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ConnectionPoolListManager connectionPoolListManager = BeanContext.getFromSession("connectionPoolListManager");
		ConnectionPool connectionPool = connectionPoolListManager.getRecordByName(value);
		return connectionPool;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		ConnectionPool connectionPool = (ConnectionPool) value;
		return ConnectionPoolUtil.toString(connectionPool);
	}
	
}

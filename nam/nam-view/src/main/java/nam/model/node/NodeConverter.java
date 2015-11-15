package nam.model.node;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Node;
import nam.model.util.NodeUtil;


@FacesConverter(value = "nodeConverter", forClass = Node.class)
public class NodeConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		NodeListManager nodeListManager = BeanContext.getFromSession("nodeListManager");
		Node node = nodeListManager.getRecordByName(value);
		return node;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Node node = (Node) value;
		return NodeUtil.toString(node);
	}
	
}

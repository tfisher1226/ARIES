package nam.model.literal;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Literal;
import nam.model.util.LiteralUtil;


@FacesConverter(value = "literalConverter", forClass = Literal.class)
public class LiteralConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		LiteralListManager literalListManager = BeanContext.getFromSession("literalListManager");
		Literal literal = literalListManager.getRecordByName(value);
		return literal;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Literal literal = (Literal) value;
		return LiteralUtil.toString(literal);
	}
	
}

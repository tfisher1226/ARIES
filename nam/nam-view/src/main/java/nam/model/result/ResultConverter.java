package nam.model.result;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Result;
import nam.model.util.ResultUtil;


@FacesConverter(value = "resultConverter", forClass = Result.class)
public class ResultConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ResultListManager resultListManager = BeanContext.getFromSession("resultListManager");
		Result result = resultListManager.getRecordByName(value);
		return result;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Result result = (Result) value;
		return ResultUtil.getLabel(result);
	}
	
}

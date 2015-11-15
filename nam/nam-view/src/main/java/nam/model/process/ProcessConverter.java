package nam.model.process;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Process;
import nam.model.util.ProcessUtil;


@FacesConverter(value = "processConverter", forClass = Process.class)
public class ProcessConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ProcessListManager processListManager = BeanContext.getFromSession("processListManager");
		Process process = processListManager.getRecordByName(value);
		return process;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Process process = (Process) value;
		return ProcessUtil.getLabel(process);
	}
	
}

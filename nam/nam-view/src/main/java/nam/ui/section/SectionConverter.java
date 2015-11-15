package nam.ui.section;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.ui.Section;
import nam.ui.util.SectionUtil;


@FacesConverter(value = "sectionConverter", forClass = Section.class)
public class SectionConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		SectionListManager sectionListManager = BeanContext.getFromSession("sectionListManager");
		Section section = sectionListManager.getRecordByName(value);
		return section;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Section section = (Section) value;
		return SectionUtil.toString(section);
	}
	
}

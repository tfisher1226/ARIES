package nam.model.annotation;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import nam.model.Annotation;
import nam.model.util.AnnotationUtil;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;


@FacesConverter(value = "annotationConverter", forClass = Annotation.class)
public class AnnotationConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		AnnotationListManager annotationListManager = BeanContext.getFromSession("annotationListManager");
		Annotation annotation = annotationListManager.getRecordByName(value);
		return annotation;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Annotation annotation = (Annotation) value;
		return AnnotationUtil.getLabel(annotation);
	}
	
}

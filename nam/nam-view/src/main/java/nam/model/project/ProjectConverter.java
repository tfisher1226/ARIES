package nam.model.project;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Project;
import nam.model.util.ProjectUtil;


@FacesConverter(value = "projectConverter", forClass = Project.class)
public class ProjectConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ProjectListManager projectListManager = BeanContext.getFromSession("projectListManager");
		Project project = projectListManager.getRecordByName(value);
		return project;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Project project = (Project) value;
		return ProjectUtil.getLabel(project);
	}
	
}

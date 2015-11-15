package nam.model.deployment;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Deployment;
import nam.model.util.DeploymentUtil;


@FacesConverter(value = "deploymentConverter", forClass = Deployment.class)
public class DeploymentConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		DeploymentListManager deploymentListManager = BeanContext.getFromSession("deploymentListManager");
		Deployment deployment = deploymentListManager.getRecordByName(value);
		return deployment;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Deployment deployment = (Deployment) value;
		return DeploymentUtil.toString(deployment);
	}
	
}

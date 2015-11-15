package nam.model.deployment;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Deployment;
import nam.model.util.DeploymentUtil;


@SessionScoped
@Named("deploymentHelper")
public class DeploymentHelper extends AbstractElementHelper<Deployment> implements Serializable {
	
	@Override
	public boolean isEmpty(Deployment deployment) {
		return DeploymentUtil.isEmpty(deployment);
	}
	
	@Override
	public String toString(Deployment deployment) {
		return DeploymentUtil.toString(deployment);
	}
	
	@Override
	public String toString(Collection<Deployment> deploymentList) {
		return DeploymentUtil.toString(deploymentList);
	}
	
	@Override
	public boolean validate(Deployment deployment) {
		return DeploymentUtil.validate(deployment);
	}
	
	@Override
	public boolean validate(Collection<Deployment> deploymentList) {
		return DeploymentUtil.validate(deploymentList);
	}
	
}

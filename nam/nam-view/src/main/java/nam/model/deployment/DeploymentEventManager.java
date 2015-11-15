package nam.model.deployment;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Deployment;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("deploymentEventManager")
public class DeploymentEventManager extends AbstractEventManager<Deployment> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Deployment getInstance() {
		return selectionContext.getSelection("deployment");
	}
	
	public void removeDeployment() {
		Deployment deployment = getInstance();
		fireRemoveEvent(deployment);
	}
	
}

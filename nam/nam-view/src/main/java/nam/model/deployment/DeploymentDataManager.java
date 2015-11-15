package nam.model.deployment;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Deployment;
import nam.model.util.DeploymentUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("deploymentDataManager")
public class DeploymentDataManager implements Serializable {
	
	@Inject
	private DeploymentEventManager deploymentEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	public Collection<Deployment> getDeploymentList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Deployment> getDefaultList() {
		return null;
	}
	
	public void saveDeployment(Deployment deployment) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeDeployment(Deployment deployment) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}

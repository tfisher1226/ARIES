package nam.model.network;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Domain;
import nam.model.Network;
import nam.model.Project;
import nam.model.util.DomainUtil;
import nam.model.util.NetworkUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("networkDataManager")
public class NetworkDataManager implements Serializable {
	
	@Inject
	private NetworkEventManager networkEventManager;
	
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
	
	public Collection<Network> getNetworkList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("project")) {
			return getNetworkList((Project) owner);
		
		} else if (scope.equals("domain")) {
			return getNetworkList((Domain) owner);
		
		} else {
		return getDefaultList();
	}
	}
	
	protected Collection<Network> getNetworkList(Project project) {
		return ProjectUtil.getNetworks(project);
	}
	
	protected Collection<Network> getNetworkList(Domain domain) {
		return DomainUtil.getNetworks(domain);
	}
	
	public Collection<Network> getDefaultList() {
		return null;
	}
	
	public void saveNetwork(Network network) {
		if (scope != null) {
			Object owner = getOwner();
		
			if (scope.equals("project")) {
				ProjectUtil.addNetwork((Project) owner, network);
		
			} else if (scope.equals("domain")) {
				DomainUtil.addNetwork((Domain) owner, network);
			}
		}
	}
	
	public boolean removeNetwork(Network network) {
		if (scope != null) {
			Object owner = getOwner();
		
			if (scope.equals("project")) {
				return ProjectUtil.removeNetwork((Project) owner, network);
		
			} else if (scope.equals("domain")) {
				return DomainUtil.removeNetwork((Domain) owner, network);
			}
		}
		return false;
	}
	
}

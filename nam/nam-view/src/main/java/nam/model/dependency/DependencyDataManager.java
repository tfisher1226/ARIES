package nam.model.dependency;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Dependency;
import nam.model.util.DependencyUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("dependencyDataManager")
public class DependencyDataManager implements Serializable {
	
	@Inject
	private DependencyEventManager dependencyEventManager;
	
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
	
	public Collection<Dependency> getDependencyList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Dependency> getDefaultList() {
		return null;
	}
	
	public void saveDependency(Dependency dependency) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeDependency(Dependency dependency) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}

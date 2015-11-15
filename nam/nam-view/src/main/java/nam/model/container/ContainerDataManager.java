package nam.model.container;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Container;
import nam.model.util.ContainerUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("containerDataManager")
public class ContainerDataManager implements Serializable {
	
	@Inject
	private ContainerEventManager containerEventManager;
	
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
	
	public Collection<Container> getContainerList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Container> getDefaultList() {
		return null;
	}
	
	public void saveContainer(Container container) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeContainer(Container container) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}

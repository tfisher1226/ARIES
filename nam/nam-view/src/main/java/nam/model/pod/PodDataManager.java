package nam.model.pod;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Pod;
import nam.model.util.PodUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("podDataManager")
public class PodDataManager implements Serializable {
	
	@Inject
	private PodEventManager podEventManager;
	
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
	
	public Collection<Pod> getPodList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Pod> getDefaultList() {
		return null;
	}
	
	public void savePod(Pod pod) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removePod(Pod pod) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}

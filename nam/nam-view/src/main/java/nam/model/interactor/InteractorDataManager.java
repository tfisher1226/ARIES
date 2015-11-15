package nam.model.interactor;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Interactor;
import nam.model.util.InteractorUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("interactorDataManager")
public class InteractorDataManager implements Serializable {
	
	@Inject
	private InteractorEventManager interactorEventManager;
	
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
	
	public Collection<Interactor> getInteractorList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Interactor> getDefaultList() {
		return null;
	}
	
	public void saveInteractor(Interactor interactor) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeInteractor(Interactor interactor) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}

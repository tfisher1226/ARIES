package nam.model.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Persistence;
import nam.model.util.PersistenceUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("persistenceDataManager")
public class PersistenceDataManager implements Serializable {
	
	@Inject
	private PersistenceEventManager persistenceEventManager;
	
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
	
	public Collection<Persistence> getPersistenceList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Persistence> getDefaultList() {
		return null;
	}
	
	public void savePersistence(Persistence persistence) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removePersistence(Persistence persistence) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}

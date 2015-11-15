package nam.model.persistenceProvider;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.PersistenceProvider;
import nam.model.util.PersistenceProviderUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("persistenceProviderDataManager")
public class PersistenceProviderDataManager implements Serializable {
	
	@Inject
	private PersistenceProviderEventManager persistenceProviderEventManager;
	
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
	
	public Collection<PersistenceProvider> getPersistenceProviderList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<PersistenceProvider> getDefaultList() {
		return null;
	}
	
	public void savePersistenceProvider(PersistenceProvider persistenceProvider) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removePersistenceProvider(PersistenceProvider persistenceProvider) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}

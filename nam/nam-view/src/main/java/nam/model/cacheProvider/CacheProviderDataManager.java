package nam.model.cacheProvider;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.CacheProvider;
import nam.model.util.CacheProviderUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("cacheProviderDataManager")
public class CacheProviderDataManager implements Serializable {
	
	@Inject
	private CacheProviderEventManager cacheProviderEventManager;
	
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
	
	public Collection<CacheProvider> getCacheProviderList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<CacheProvider> getDefaultList() {
		return null;
	}
	
	public void saveCacheProvider(CacheProvider cacheProvider) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeCacheProvider(CacheProvider cacheProvider) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}

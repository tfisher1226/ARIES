package nam.model.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Cache;
import nam.model.util.CacheUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("cacheDataManager")
public class CacheDataManager implements Serializable {
	
	@Inject
	private CacheEventManager cacheEventManager;
	
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
	
	public Collection<Cache> getCacheList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Cache> getDefaultList() {
		return null;
	}
	
	public void saveCache(Cache cache) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeCache(Cache cache) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}

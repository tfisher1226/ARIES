package nam.model.cacheProvider;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.CacheProvider;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("cacheProviderEventManager")
public class CacheProviderEventManager extends AbstractEventManager<CacheProvider> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public CacheProvider getInstance() {
		return selectionContext.getSelection("cacheProvider");
	}
	
	public void removeCacheProvider() {
		CacheProvider cacheProvider = getInstance();
		fireRemoveEvent(cacheProvider);
	}
	
}

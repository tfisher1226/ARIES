package nam.model.cache;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Cache;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("cacheEventManager")
public class CacheEventManager extends AbstractEventManager<Cache> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Cache getInstance() {
		return selectionContext.getSelection("cache");
	}
	
	public void removeCache() {
		Cache cache = getInstance();
		fireRemoveEvent(cache);
	}
	
}

package nam.model.cache;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Cache;
import nam.model.Project;
import nam.model.util.CacheUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("cacheWizard")
@SuppressWarnings("serial")
public class CacheWizard extends AbstractDomainElementWizard<Cache> implements Serializable {
	
	@Inject
	private CacheDataManager cacheDataManager;
	
	@Inject
	private CachePageManager cachePageManager;
	
	@Inject
	private CacheEventManager cacheEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Cache";
	}
	
	@Override
	public String getUrlContext() {
		return cachePageManager.getCacheWizardPage();
	}
	
	@Override
	public void initialize(Cache cache) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(cachePageManager.getSections());
		super.initialize(cache);
	}
	
	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}
	
	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}
	
	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		cachePageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		cachePageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		cachePageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		cachePageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Cache cache = getInstance();
		cacheDataManager.saveCache(cache);
		cacheEventManager.fireSavedEvent(cache);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Cache cache = getInstance();
		//TODO take this out soon
		if (cache == null)
			cache = new Cache();
		cacheEventManager.fireCancelledEvent(cache);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Cache cache = selectionContext.getSelection("cache");
		String name = cache.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("cacheWizard");
			display.error("Cache name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}

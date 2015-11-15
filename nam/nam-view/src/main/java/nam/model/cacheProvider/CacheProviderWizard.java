package nam.model.cacheProvider;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.CacheProvider;
import nam.model.Project;
import nam.model.util.CacheProviderUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("cacheProviderWizard")
@SuppressWarnings("serial")
public class CacheProviderWizard extends AbstractDomainElementWizard<CacheProvider> implements Serializable {
	
	@Inject
	private CacheProviderDataManager cacheProviderDataManager;
	
	@Inject
	private CacheProviderPageManager cacheProviderPageManager;
	
	@Inject
	private CacheProviderEventManager cacheProviderEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "CacheProvider";
	}
	
	@Override
	public String getUrlContext() {
		return cacheProviderPageManager.getCacheProviderWizardPage();
	}
	
	@Override
	public void initialize(CacheProvider cacheProvider) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(cacheProviderPageManager.getSections());
		super.initialize(cacheProvider);
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
		cacheProviderPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		cacheProviderPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		cacheProviderPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		cacheProviderPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		CacheProvider cacheProvider = getInstance();
		cacheProviderDataManager.saveCacheProvider(cacheProvider);
		cacheProviderEventManager.fireSavedEvent(cacheProvider);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		CacheProvider cacheProvider = getInstance();
		//TODO take this out soon
		if (cacheProvider == null)
			cacheProvider = new CacheProvider();
		cacheProviderEventManager.fireCancelledEvent(cacheProvider);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		CacheProvider cacheProvider = selectionContext.getSelection("cacheProvider");
		String name = cacheProvider.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("cacheProviderWizard");
			display.error("CacheProvider name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}

package nam.model.provider;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Project;
import nam.model.Provider;
import nam.model.util.ProviderUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("providerWizard")
@SuppressWarnings("serial")
public class ProviderWizard extends AbstractDomainElementWizard<Provider> implements Serializable {
	
	@Inject
	private ProviderDataManager providerDataManager;
	
	@Inject
	private ProviderPageManager providerPageManager;
	
	@Inject
	private ProviderEventManager providerEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Provider";
	}
	
	@Override
	public String getUrlContext() {
		return providerPageManager.getProviderWizardPage();
	}
	
	@Override
	public void initialize(Provider provider) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(providerPageManager.getSections());
		super.initialize(provider);
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
		providerPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		providerPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		providerPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		providerPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Provider provider = getInstance();
		providerDataManager.saveProvider(provider);
		providerEventManager.fireSavedEvent(provider);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Provider provider = getInstance();
		//TODO take this out soon
		if (provider == null)
			provider = new Provider();
		providerEventManager.fireCancelledEvent(provider);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Provider provider = selectionContext.getSelection("provider");
		String name = provider.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("providerWizard");
			display.error("Provider name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}

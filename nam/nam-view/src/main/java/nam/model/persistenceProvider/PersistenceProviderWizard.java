package nam.model.persistenceProvider;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.PersistenceProvider;
import nam.model.Project;
import nam.model.util.PersistenceProviderUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("persistenceProviderWizard")
@SuppressWarnings("serial")
public class PersistenceProviderWizard extends AbstractDomainElementWizard<PersistenceProvider> implements Serializable {
	
	@Inject
	private PersistenceProviderDataManager persistenceProviderDataManager;
	
	@Inject
	private PersistenceProviderPageManager persistenceProviderPageManager;
	
	@Inject
	private PersistenceProviderEventManager persistenceProviderEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "PersistenceProvider";
	}
	
	@Override
	public String getUrlContext() {
		return persistenceProviderPageManager.getPersistenceProviderWizardPage();
	}
	
	@Override
	public void initialize(PersistenceProvider persistenceProvider) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(persistenceProviderPageManager.getSections());
		super.initialize(persistenceProvider);
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
		persistenceProviderPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		persistenceProviderPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		persistenceProviderPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		persistenceProviderPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		PersistenceProvider persistenceProvider = getInstance();
		persistenceProviderDataManager.savePersistenceProvider(persistenceProvider);
		persistenceProviderEventManager.fireSavedEvent(persistenceProvider);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		PersistenceProvider persistenceProvider = getInstance();
		//TODO take this out soon
		if (persistenceProvider == null)
			persistenceProvider = new PersistenceProvider();
		persistenceProviderEventManager.fireCancelledEvent(persistenceProvider);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		PersistenceProvider persistenceProvider = selectionContext.getSelection("persistenceProvider");
		String name = persistenceProvider.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("persistenceProviderWizard");
			display.error("PersistenceProvider name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}

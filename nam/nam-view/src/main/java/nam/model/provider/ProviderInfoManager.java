package nam.model.provider;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.util.Validator;

import nam.model.Project;
import nam.model.Provider;
import nam.model.util.ProjectUtil;
import nam.model.util.ProviderUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("providerInfoManager")
public class ProviderInfoManager extends AbstractNamRecordManager<Provider> implements Serializable {
	
	@Inject
	private ProviderWizard providerWizard;
	
	@Inject
	private ProviderDataManager providerDataManager;
	
	@Inject
	private ProviderPageManager providerPageManager;
	
	@Inject
	private ProviderEventManager providerEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ProviderHelper providerHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ProviderInfoManager() {
		setInstanceName("provider");
	}
	
	
	public Provider getProvider() {
		return getRecord();
	}
	
	public Provider getSelectedProvider() {
		return selectionContext.getSelection("provider");
	}
	
	@Override
	public Class<Provider> getRecordClass() {
		return Provider.class;
	}
	
	@Override
	public boolean isEmpty(Provider provider) {
		return providerHelper.isEmpty(provider);
	}
	
	@Override
	public String toString(Provider provider) {
		return providerHelper.toString(provider);
	}
	
	@Override
	public void initialize() {
		Provider provider = selectionContext.getSelection("provider");
		if (provider != null)
			initialize(provider);
	}
	
	protected void initialize(Provider provider) {
		providerWizard.initialize(provider);
		setContext("provider", provider);
	}
	
	@Override
	public String newRecord() {
		return newProvider();
	}
	
	public String newProvider() {
		try {
			Provider provider = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("provider",  provider);
			String url = providerPageManager.initializeProviderCreationPage(provider);
			providerPageManager.pushContext(providerWizard);
			initialize(provider);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Provider create() {
		Provider provider = ProviderUtil.create();
		return provider;
	}
	
	@Override
	public Provider clone(Provider provider) {
		provider = ProviderUtil.clone(provider);
		return provider;
	}
	
	@Override
	public String viewRecord() {
		return viewProvider();
	}
	
	public String viewProvider() {
		Provider provider = selectionContext.getSelection("provider");
		String url = viewProvider(provider);
		return url;
	}
	
	public String viewProvider(Provider provider) {
		try {
			String url = providerPageManager.initializeProviderSummaryView(provider);
			providerPageManager.pushContext(providerWizard);
			initialize(provider);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editProvider();
	}
	
	public String editProvider() {
		Provider provider = selectionContext.getSelection("provider");
		String url = editProvider(provider);
		return url;
	}
	
	public String editProvider(Provider provider) {
		try {
			//provider = clone(provider);
			selectionContext.resetOrigin();
			selectionContext.setSelection("provider",  provider);
			String url = providerPageManager.initializeProviderUpdatePage(provider);
			providerPageManager.pushContext(providerWizard);
			initialize(provider);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveProvider() {
		Provider provider = getProvider();
		if (validateProvider(provider)) {
			if (isImmediate())
				persistProvider(provider);
			outject("provider", provider);
		}
	}
	
	public void persistProvider(Provider provider) {
		saveProvider(provider);
	}
	
	public void saveProvider(Provider provider) {
		try {
			saveProviderToSystem(provider);
			providerEventManager.fireAddedEvent(provider);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveProviderToSystem(Provider provider) {
		providerDataManager.saveProvider(provider);
	}
	
	public void handleSaveProvider(@Observes @Add Provider provider) {
		saveProvider(provider);
	}
	
	public void addProvider(Provider provider) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichProvider(Provider provider) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Provider provider) {
		return validateProvider(provider);
	}
	
	public boolean validateProvider(Provider provider) {
		Validator validator = getValidator();
		boolean isValid = ProviderUtil.validate(provider);
		Display display = getFromSession("display");
		display.setModule("providerInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveProvider() {
		display = getFromSession("display");
		display.setModule("providerInfo");
		Provider provider = selectionContext.getSelection("provider");
		if (provider == null) {
			display.error("Provider record must be selected.");
		}
	}
	
	public String handleRemoveProvider(@Observes @Remove Provider provider) {
		display = getFromSession("display");
		display.setModule("providerInfo");
		try {
			display.info("Removing Provider "+ProviderUtil.getLabel(provider)+" from the system.");
			removeProviderFromSystem(provider);
			selectionContext.clearSelection("provider");
			providerEventManager.fireClearSelectionEvent();
			providerEventManager.fireRemovedEvent(provider);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeProviderFromSystem(Provider provider) {
		if (providerDataManager.removeProvider(provider))
			setRecord(null);
	}
	
	public void cancelProvider() {
		BeanContext.removeFromSession("provider");
		providerPageManager.removeContext(providerWizard);
	}
	
}

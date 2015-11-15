package nam.model.cacheProvider;

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
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.CacheProvider;
import nam.model.Project;
import nam.model.util.CacheProviderUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("cacheProviderInfoManager")
public class CacheProviderInfoManager extends AbstractNamRecordManager<CacheProvider> implements Serializable {
	
	@Inject
	private CacheProviderWizard cacheProviderWizard;
	
	@Inject
	private CacheProviderDataManager cacheProviderDataManager;
	
	@Inject
	private CacheProviderPageManager cacheProviderPageManager;
	
	@Inject
	private CacheProviderEventManager cacheProviderEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private CacheProviderHelper cacheProviderHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public CacheProviderInfoManager() {
		setInstanceName("cacheProvider");
	}
	
	
	public CacheProvider getCacheProvider() {
		return getRecord();
	}
	
	public CacheProvider getSelectedCacheProvider() {
		return selectionContext.getSelection("cacheProvider");
	}
	
	@Override
	public Class<CacheProvider> getRecordClass() {
		return CacheProvider.class;
	}
	
	@Override
	public boolean isEmpty(CacheProvider cacheProvider) {
		return cacheProviderHelper.isEmpty(cacheProvider);
	}
	
	@Override
	public String toString(CacheProvider cacheProvider) {
		return cacheProviderHelper.toString(cacheProvider);
	}
	
	@Override
	public void initialize() {
		CacheProvider cacheProvider = selectionContext.getSelection("cacheProvider");
		if (cacheProvider != null)
			initialize(cacheProvider);
	}
	
	protected void initialize(CacheProvider cacheProvider) {
		CacheProviderUtil.initialize(cacheProvider);
		cacheProviderWizard.initialize(cacheProvider);
		setContext("cacheProvider", cacheProvider);
	}
	
	public void handleCacheProviderSelected(@Observes @Selected CacheProvider cacheProvider) {
		selectionContext.setSelection("cacheProvider",  cacheProvider);
		cacheProviderPageManager.updateState(cacheProvider);
		cacheProviderPageManager.refreshMembers();
		setRecord(cacheProvider);
	}
	
	@Override
	public String newRecord() {
		return newCacheProvider();
	}
	
	public String newCacheProvider() {
		try {
			CacheProvider cacheProvider = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("cacheProvider",  cacheProvider);
			String url = cacheProviderPageManager.initializeCacheProviderCreationPage(cacheProvider);
			cacheProviderPageManager.pushContext(cacheProviderWizard);
			initialize(cacheProvider);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public CacheProvider create() {
		CacheProvider cacheProvider = CacheProviderUtil.create();
		return cacheProvider;
	}
	
	@Override
	public CacheProvider clone(CacheProvider cacheProvider) {
		cacheProvider = CacheProviderUtil.clone(cacheProvider);
		return cacheProvider;
	}
	
	@Override
	public String viewRecord() {
		return viewCacheProvider();
	}
	
	public String viewCacheProvider() {
		CacheProvider cacheProvider = selectionContext.getSelection("cacheProvider");
		String url = viewCacheProvider(cacheProvider);
		return url;
	}
	
	public String viewCacheProvider(CacheProvider cacheProvider) {
		try {
			String url = cacheProviderPageManager.initializeCacheProviderSummaryView(cacheProvider);
			cacheProviderPageManager.pushContext(cacheProviderWizard);
			initialize(cacheProvider);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editCacheProvider();
	}
	
	public String editCacheProvider() {
		CacheProvider cacheProvider = selectionContext.getSelection("cacheProvider");
		String url = editCacheProvider(cacheProvider);
		return url;
	}
	
	public String editCacheProvider(CacheProvider cacheProvider) {
		try {
			//cacheProvider = clone(cacheProvider);
			selectionContext.resetOrigin();
			selectionContext.setSelection("cacheProvider",  cacheProvider);
			String url = cacheProviderPageManager.initializeCacheProviderUpdatePage(cacheProvider);
			cacheProviderPageManager.pushContext(cacheProviderWizard);
			initialize(cacheProvider);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveCacheProvider() {
		CacheProvider cacheProvider = getCacheProvider();
		if (validateCacheProvider(cacheProvider)) {
			if (isImmediate())
				persistCacheProvider(cacheProvider);
			outject("cacheProvider", cacheProvider);
		}
	}
	
	public void persistCacheProvider(CacheProvider cacheProvider) {
		saveCacheProvider(cacheProvider);
	}
	
	public void saveCacheProvider(CacheProvider cacheProvider) {
		try {
			saveCacheProviderToSystem(cacheProvider);
			cacheProviderEventManager.fireAddedEvent(cacheProvider);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveCacheProviderToSystem(CacheProvider cacheProvider) {
		cacheProviderDataManager.saveCacheProvider(cacheProvider);
	}
	
	public void handleSaveCacheProvider(@Observes @Add CacheProvider cacheProvider) {
		saveCacheProvider(cacheProvider);
	}
	
	public void addCacheProvider(CacheProvider cacheProvider) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichCacheProvider(CacheProvider cacheProvider) {
		//nothing for now
	}
	
	@Override
	public boolean validate(CacheProvider cacheProvider) {
		return validateCacheProvider(cacheProvider);
	}
	
	public boolean validateCacheProvider(CacheProvider cacheProvider) {
		Validator validator = getValidator();
		boolean isValid = CacheProviderUtil.validate(cacheProvider);
		Display display = getFromSession("display");
		display.setModule("cacheProviderInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveCacheProvider() {
		display = getFromSession("display");
		display.setModule("cacheProviderInfo");
		CacheProvider cacheProvider = selectionContext.getSelection("cacheProvider");
		if (cacheProvider == null) {
			display.error("CacheProvider record must be selected.");
		}
	}
	
	public String handleRemoveCacheProvider(@Observes @Remove CacheProvider cacheProvider) {
		display = getFromSession("display");
		display.setModule("cacheProviderInfo");
		try {
			display.info("Removing CacheProvider "+CacheProviderUtil.getLabel(cacheProvider)+" from the system.");
			removeCacheProviderFromSystem(cacheProvider);
			selectionContext.clearSelection("cacheProvider");
			cacheProviderEventManager.fireClearSelectionEvent();
			cacheProviderEventManager.fireRemovedEvent(cacheProvider);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeCacheProviderFromSystem(CacheProvider cacheProvider) {
		if (cacheProviderDataManager.removeCacheProvider(cacheProvider))
			setRecord(null);
	}
	
	public void cancelCacheProvider() {
		BeanContext.removeFromSession("cacheProvider");
		cacheProviderPageManager.removeContext(cacheProviderWizard);
	}
	
}

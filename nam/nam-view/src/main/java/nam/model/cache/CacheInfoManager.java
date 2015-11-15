package nam.model.cache;

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

import nam.model.Cache;
import nam.model.Project;
import nam.model.util.CacheUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("cacheInfoManager")
public class CacheInfoManager extends AbstractNamRecordManager<Cache> implements Serializable {
	
	@Inject
	private CacheWizard cacheWizard;
	
	@Inject
	private CacheDataManager cacheDataManager;
	
	@Inject
	private CachePageManager cachePageManager;
	
	@Inject
	private CacheEventManager cacheEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private CacheHelper cacheHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public CacheInfoManager() {
		setInstanceName("cache");
	}
	
	
	public Cache getCache() {
		return getRecord();
	}
	
	public Cache getSelectedCache() {
		return selectionContext.getSelection("cache");
	}
	
	@Override
	public Class<Cache> getRecordClass() {
		return Cache.class;
	}
	
	@Override
	public boolean isEmpty(Cache cache) {
		return cacheHelper.isEmpty(cache);
	}
	
	@Override
	public String toString(Cache cache) {
		return cacheHelper.toString(cache);
	}
	
	@Override
	public void initialize() {
		Cache cache = selectionContext.getSelection("cache");
		if (cache != null)
			initialize(cache);
	}
	
	protected void initialize(Cache cache) {
		CacheUtil.initialize(cache);
		cacheWizard.initialize(cache);
		setContext("cache", cache);
	}
	
	public void handleCacheSelected(@Observes @Selected Cache cache) {
		selectionContext.setSelection("cache",  cache);
		cachePageManager.updateState(cache);
		cachePageManager.refreshMembers();
		setRecord(cache);
	}
	
	@Override
	public String newRecord() {
		return newCache();
	}
	
	public String newCache() {
		try {
			Cache cache = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("cache",  cache);
			String url = cachePageManager.initializeCacheCreationPage(cache);
			cachePageManager.pushContext(cacheWizard);
			initialize(cache);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Cache create() {
		Cache cache = CacheUtil.create();
		return cache;
	}
	
	@Override
	public Cache clone(Cache cache) {
		cache = CacheUtil.clone(cache);
		return cache;
	}
	
	@Override
	public String viewRecord() {
		return viewCache();
	}
	
	public String viewCache() {
		Cache cache = selectionContext.getSelection("cache");
		String url = viewCache(cache);
		return url;
	}
	
	public String viewCache(Cache cache) {
		try {
			String url = cachePageManager.initializeCacheSummaryView(cache);
			cachePageManager.pushContext(cacheWizard);
			initialize(cache);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editCache();
	}
	
	public String editCache() {
		Cache cache = selectionContext.getSelection("cache");
		String url = editCache(cache);
		return url;
	}
	
	public String editCache(Cache cache) {
		try {
			//cache = clone(cache);
			selectionContext.resetOrigin();
			selectionContext.setSelection("cache",  cache);
			String url = cachePageManager.initializeCacheUpdatePage(cache);
			cachePageManager.pushContext(cacheWizard);
			initialize(cache);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveCache() {
		Cache cache = getCache();
		if (validateCache(cache)) {
			saveCache(cache);
		}
	}
	
	public void persistCache(Cache cache) {
		saveCache(cache);
	}
	
	public void saveCache(Cache cache) {
		try {
			saveCacheToSystem(cache);
			cacheEventManager.fireAddedEvent(cache);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveCacheToSystem(Cache cache) {
		cacheDataManager.saveCache(cache);
	}
	
	public void handleSaveCache(@Observes @Add Cache cache) {
		saveCache(cache);
	}
	
	public void enrichCache(Cache cache) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Cache cache) {
		return validateCache(cache);
	}
	
	public boolean validateCache(Cache cache) {
		Validator validator = getValidator();
		boolean isValid = CacheUtil.validate(cache);
		Display display = getFromSession("display");
		display.setModule("cacheInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveCache() {
		display = getFromSession("display");
		display.setModule("cacheInfo");
		Cache cache = selectionContext.getSelection("cache");
		if (cache == null) {
			display.error("Cache record must be selected.");
		}
	}
	
	public String handleRemoveCache(@Observes @Remove Cache cache) {
		display = getFromSession("display");
		display.setModule("cacheInfo");
		try {
			display.info("Removing Cache "+CacheUtil.getLabel(cache)+" from the system.");
			removeCacheFromSystem(cache);
			selectionContext.clearSelection("cache");
			cacheEventManager.fireClearSelectionEvent();
			cacheEventManager.fireRemovedEvent(cache);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeCacheFromSystem(Cache cache) {
		if (cacheDataManager.removeCache(cache))
			setRecord(null);
	}
	
	public void cancelCache() {
		BeanContext.removeFromSession("cache");
		cachePageManager.removeContext(cacheWizard);
	}
	
}

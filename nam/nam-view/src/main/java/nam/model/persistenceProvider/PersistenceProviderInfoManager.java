package nam.model.persistenceProvider;

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

import nam.model.PersistenceProvider;
import nam.model.Project;
import nam.model.util.PersistenceProviderUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("persistenceProviderInfoManager")
public class PersistenceProviderInfoManager extends AbstractNamRecordManager<PersistenceProvider> implements Serializable {
	
	@Inject
	private PersistenceProviderWizard persistenceProviderWizard;
	
	@Inject
	private PersistenceProviderDataManager persistenceProviderDataManager;
	
	@Inject
	private PersistenceProviderPageManager persistenceProviderPageManager;
	
	@Inject
	private PersistenceProviderEventManager persistenceProviderEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private PersistenceProviderHelper persistenceProviderHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public PersistenceProviderInfoManager() {
		setInstanceName("persistenceProvider");
	}
	
	
	public PersistenceProvider getPersistenceProvider() {
		return getRecord();
	}
	
	public PersistenceProvider getSelectedPersistenceProvider() {
		return selectionContext.getSelection("persistenceProvider");
	}
	
	@Override
	public Class<PersistenceProvider> getRecordClass() {
		return PersistenceProvider.class;
	}
	
	@Override
	public boolean isEmpty(PersistenceProvider persistenceProvider) {
		return persistenceProviderHelper.isEmpty(persistenceProvider);
	}
	
	@Override
	public String toString(PersistenceProvider persistenceProvider) {
		return persistenceProviderHelper.toString(persistenceProvider);
	}
	
	@Override
	public void initialize() {
		PersistenceProvider persistenceProvider = selectionContext.getSelection("persistenceProvider");
		if (persistenceProvider != null)
			initialize(persistenceProvider);
	}
	
	protected void initialize(PersistenceProvider persistenceProvider) {
		PersistenceProviderUtil.initialize(persistenceProvider);
		persistenceProviderWizard.initialize(persistenceProvider);
		setContext("persistenceProvider", persistenceProvider);
	}
	
	public void handlePersistenceProviderSelected(@Observes @Selected PersistenceProvider persistenceProvider) {
		selectionContext.setSelection("persistenceProvider",  persistenceProvider);
		persistenceProviderPageManager.updateState(persistenceProvider);
		persistenceProviderPageManager.refreshMembers();
		setRecord(persistenceProvider);
	}
	
	@Override
	public String newRecord() {
		return newPersistenceProvider();
	}
	
	public String newPersistenceProvider() {
		try {
			PersistenceProvider persistenceProvider = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("persistenceProvider",  persistenceProvider);
			String url = persistenceProviderPageManager.initializePersistenceProviderCreationPage(persistenceProvider);
			persistenceProviderPageManager.pushContext(persistenceProviderWizard);
			initialize(persistenceProvider);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public PersistenceProvider create() {
		PersistenceProvider persistenceProvider = PersistenceProviderUtil.create();
		return persistenceProvider;
	}
	
	@Override
	public PersistenceProvider clone(PersistenceProvider persistenceProvider) {
		persistenceProvider = PersistenceProviderUtil.clone(persistenceProvider);
		return persistenceProvider;
	}
	
	@Override
	public String viewRecord() {
		return viewPersistenceProvider();
	}
	
	public String viewPersistenceProvider() {
		PersistenceProvider persistenceProvider = selectionContext.getSelection("persistenceProvider");
		String url = viewPersistenceProvider(persistenceProvider);
		return url;
	}
	
	public String viewPersistenceProvider(PersistenceProvider persistenceProvider) {
		try {
			String url = persistenceProviderPageManager.initializePersistenceProviderSummaryView(persistenceProvider);
			persistenceProviderPageManager.pushContext(persistenceProviderWizard);
			initialize(persistenceProvider);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editPersistenceProvider();
	}
	
	public String editPersistenceProvider() {
		PersistenceProvider persistenceProvider = selectionContext.getSelection("persistenceProvider");
		String url = editPersistenceProvider(persistenceProvider);
		return url;
	}
	
	public String editPersistenceProvider(PersistenceProvider persistenceProvider) {
		try {
			//persistenceProvider = clone(persistenceProvider);
			selectionContext.resetOrigin();
			selectionContext.setSelection("persistenceProvider",  persistenceProvider);
			String url = persistenceProviderPageManager.initializePersistenceProviderUpdatePage(persistenceProvider);
			persistenceProviderPageManager.pushContext(persistenceProviderWizard);
			initialize(persistenceProvider);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void savePersistenceProvider() {
		PersistenceProvider persistenceProvider = getPersistenceProvider();
		if (validatePersistenceProvider(persistenceProvider)) {
			if (isImmediate())
				persistPersistenceProvider(persistenceProvider);
			outject("persistenceProvider", persistenceProvider);
		}
	}
	
	public void persistPersistenceProvider(PersistenceProvider persistenceProvider) {
		savePersistenceProvider(persistenceProvider);
	}
	
	public void savePersistenceProvider(PersistenceProvider persistenceProvider) {
		try {
			savePersistenceProviderToSystem(persistenceProvider);
			persistenceProviderEventManager.fireAddedEvent(persistenceProvider);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void savePersistenceProviderToSystem(PersistenceProvider persistenceProvider) {
		persistenceProviderDataManager.savePersistenceProvider(persistenceProvider);
	}
	
	public void handleSavePersistenceProvider(@Observes @Add PersistenceProvider persistenceProvider) {
		savePersistenceProvider(persistenceProvider);
	}
	
	public void addPersistenceProvider(PersistenceProvider persistenceProvider) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichPersistenceProvider(PersistenceProvider persistenceProvider) {
		//nothing for now
	}
	
	@Override
	public boolean validate(PersistenceProvider persistenceProvider) {
		return validatePersistenceProvider(persistenceProvider);
	}
	
	public boolean validatePersistenceProvider(PersistenceProvider persistenceProvider) {
		Validator validator = getValidator();
		boolean isValid = PersistenceProviderUtil.validate(persistenceProvider);
		Display display = getFromSession("display");
		display.setModule("persistenceProviderInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemovePersistenceProvider() {
		display = getFromSession("display");
		display.setModule("persistenceProviderInfo");
		PersistenceProvider persistenceProvider = selectionContext.getSelection("persistenceProvider");
		if (persistenceProvider == null) {
			display.error("PersistenceProvider record must be selected.");
		}
	}
	
	public String handleRemovePersistenceProvider(@Observes @Remove PersistenceProvider persistenceProvider) {
		display = getFromSession("display");
		display.setModule("persistenceProviderInfo");
		try {
			display.info("Removing PersistenceProvider "+PersistenceProviderUtil.getLabel(persistenceProvider)+" from the system.");
			removePersistenceProviderFromSystem(persistenceProvider);
			selectionContext.clearSelection("persistenceProvider");
			persistenceProviderEventManager.fireClearSelectionEvent();
			persistenceProviderEventManager.fireRemovedEvent(persistenceProvider);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removePersistenceProviderFromSystem(PersistenceProvider persistenceProvider) {
		if (persistenceProviderDataManager.removePersistenceProvider(persistenceProvider))
			setRecord(null);
	}
	
	public void cancelPersistenceProvider() {
		BeanContext.removeFromSession("persistenceProvider");
		persistenceProviderPageManager.removeContext(persistenceProviderWizard);
	}
	
}

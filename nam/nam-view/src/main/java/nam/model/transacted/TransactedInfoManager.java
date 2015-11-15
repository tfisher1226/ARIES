package nam.model.transacted;

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

import nam.model.Project;
import nam.model.Transacted;
import nam.model.TransactionScope;
import nam.model.TransactionUsage;
import nam.model.transactionScope.TransactionScopeSelectManager;
import nam.model.transactionUsage.TransactionUsageSelectManager;
import nam.model.util.ProjectUtil;
import nam.model.util.TransactedUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("transactedInfoManager")
public class TransactedInfoManager extends AbstractNamRecordManager<Transacted> implements Serializable {
	
	@Inject
	private TransactedWizard transactedWizard;
	
	@Inject
	private TransactedDataManager transactedDataManager;
	
	@Inject
	private TransactedPageManager transactedPageManager;
	
	@Inject
	private TransactedEventManager transactedEventManager;
	
	@Inject
	private TransactionScopeSelectManager transactionScopeSelectManager;
	
	@Inject
	private TransactionUsageSelectManager transactionUsageSelectManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public TransactedInfoManager() {
		setInstanceName("transacted");
	}
	
	
	public Transacted getTransacted() {
		return getRecord();
	}
	
	public Transacted getSelectedTransacted() {
		return selectionContext.getSelection("transacted");
	}
	
	@Override
	public Class<Transacted> getRecordClass() {
		return Transacted.class;
	}
	
	@Override
	public boolean isEmpty(Transacted transacted) {
		return getTransactedHelper().isEmpty(transacted);
	}
	
	@Override
	public String toString(Transacted transacted) {
		return getTransactedHelper().toString(transacted);
	}
	
	protected TransactedHelper getTransactedHelper() {
		return BeanContext.getFromSession("transactedHelper");
	}
	
	@Override
	public void initialize() {
		Transacted transacted = selectionContext.getSelection("transacted");
		if (transacted != null)
			initialize(transacted);
	}
	
	protected void initialize(Transacted transacted) {
		TransactedUtil.initialize(transacted);
		transactedWizard.initialize(transacted);
		initializeTransactionScopeSelectManager(transacted);
		initializeTransactionUsageSelectManager(transacted);
		initializeOutjectedState(transacted);
		setContext("transacted", transacted);
	}
	
	protected void initializeOutjectedState(Transacted transacted) {
		outjectTo("transactedScope", transacted.getScope());
		outjectTo("transactedUse", transacted.getUse());
		outject("transacted", transacted);
	}
	
	protected void initializeTransactionScopeSelectManager(Transacted transacted) {
		transactionScopeSelectManager.setOwnerRecord(transacted);
		transactionScopeSelectManager.initialize();
	}
	
	protected void initializeTransactionUsageSelectManager(Transacted transacted) {
		transactionUsageSelectManager.setOwnerRecord(transacted);
		transactionUsageSelectManager.initialize();
	}
	
	public void handleTransactedScopeSelected(@Observes @Selected TransactionScope scope) {
		getTransacted().setScope(scope);
	}
	
	public void handleTransactedUseSelected(@Observes @Selected TransactionUsage use) {
		getTransacted().setUse(use);
	}
	
	public void handleTransactedSelected(@Observes @Selected Transacted transacted) {
		selectionContext.setSelection("transacted",  transacted);
		transactedPageManager.updateState(transacted);
		setRecord(transacted);
	}
	
	@Override
	public String newRecord() {
		return newTransacted();
	}
	
	public String newTransacted() {
		try {
			Transacted transacted = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("transacted",  transacted);
			String url = transactedPageManager.initializeTransactedCreationPage(transacted);
			transactedPageManager.pushContext(transactedWizard);
			initialize(transacted);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Transacted create() {
		Transacted transacted = TransactedUtil.create();
		return transacted;
	}
	
	@Override
	public Transacted clone(Transacted transacted) {
		transacted = TransactedUtil.clone(transacted);
		return transacted;
	}
	
	@Override
	public String viewRecord() {
		return viewTransacted();
	}
	
	public String viewTransacted() {
		Transacted transacted = selectionContext.getSelection("transacted");
		String url = viewTransacted(transacted);
		return url;
	}
	
	public String viewTransacted(Transacted transacted) {
		try {
			String url = transactedPageManager.initializeTransactedSummaryView(transacted);
			transactedPageManager.pushContext(transactedWizard);
			initialize(transacted);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editTransacted();
	}
	
	public String editTransacted() {
		Transacted transacted = selectionContext.getSelection("transacted");
		String url = editTransacted(transacted);
		return url;
	}
	
	public String editTransacted(Transacted transacted) {
		try {
			//transacted = clone(transacted);
			selectionContext.resetOrigin();
			selectionContext.setSelection("transacted",  transacted);
			String url = transactedPageManager.initializeTransactedUpdatePage(transacted);
			transactedPageManager.pushContext(transactedWizard);
			initialize(transacted);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveTransacted() {
		Transacted transacted = getTransacted();
		if (validateTransacted(transacted)) {
			saveTransacted(transacted);
		}
	}
	
	public void persistTransacted(Transacted transacted) {
		saveTransacted(transacted);
	}
	
	public void saveTransacted(Transacted transacted) {
		try {
			saveTransactedToSystem(transacted);
			transactedEventManager.fireAddedEvent(transacted);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveTransactedToSystem(Transacted transacted) {
		transactedDataManager.saveTransacted(transacted);
	}
	
	public void handleSaveTransacted(@Observes @Add Transacted transacted) {
		saveTransacted(transacted);
	}
	
	public void enrichTransacted(Transacted transacted) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Transacted transacted) {
		return validateTransacted(transacted);
	}
	
	public boolean validateTransacted(Transacted transacted) {
		Validator validator = getValidator();
		boolean isValid = TransactedUtil.validate(transacted);
		Display display = getFromSession("display");
		display.setModule("transactedInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveTransacted() {
		display = getFromSession("display");
		display.setModule("transactedInfo");
		Transacted transacted = selectionContext.getSelection("transacted");
		if (transacted == null) {
			display.error("Transacted record must be selected.");
		}
	}
	
	public String handleRemoveTransacted(@Observes @Remove Transacted transacted) {
		display = getFromSession("display");
		display.setModule("transactedInfo");
		try {
			display.info("Removing Transacted "+TransactedUtil.getLabel(transacted)+" from the system.");
			removeTransactedFromSystem(transacted);
			selectionContext.clearSelection("transacted");
			transactedEventManager.fireClearSelectionEvent();
			transactedEventManager.fireRemovedEvent(transacted);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeTransactedFromSystem(Transacted transacted) {
		if (transactedDataManager.removeTransacted(transacted))
			setRecord(null);
	}
	
	public void cancelTransacted() {
		BeanContext.removeFromSession("transacted");
		transactedPageManager.removeContext(transactedWizard);
	}
	
}

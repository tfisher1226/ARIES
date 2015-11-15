package nam.model.messagingProvider;

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

import nam.model.MessagingProvider;
import nam.model.Project;
import nam.model.util.MessagingProviderUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("messagingProviderInfoManager")
public class MessagingProviderInfoManager extends AbstractNamRecordManager<MessagingProvider> implements Serializable {
	
	@Inject
	private MessagingProviderWizard messagingProviderWizard;
	
	@Inject
	private MessagingProviderDataManager messagingProviderDataManager;
	
	@Inject
	private MessagingProviderPageManager messagingProviderPageManager;
	
	@Inject
	private MessagingProviderEventManager messagingProviderEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private MessagingProviderHelper messagingProviderHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public MessagingProviderInfoManager() {
		setInstanceName("messagingProvider");
	}
	
	
	public MessagingProvider getMessagingProvider() {
		return getRecord();
	}
	
	public MessagingProvider getSelectedMessagingProvider() {
		return selectionContext.getSelection("messagingProvider");
	}
	
	@Override
	public Class<MessagingProvider> getRecordClass() {
		return MessagingProvider.class;
	}
	
	@Override
	public boolean isEmpty(MessagingProvider messagingProvider) {
		return messagingProviderHelper.isEmpty(messagingProvider);
	}
	
	@Override
	public String toString(MessagingProvider messagingProvider) {
		return messagingProviderHelper.toString(messagingProvider);
	}
	
	@Override
	public void initialize() {
		MessagingProvider messagingProvider = selectionContext.getSelection("messagingProvider");
		if (messagingProvider != null)
			initialize(messagingProvider);
	}
	
	protected void initialize(MessagingProvider messagingProvider) {
		MessagingProviderUtil.initialize(messagingProvider);
		messagingProviderWizard.initialize(messagingProvider);
		setContext("messagingProvider", messagingProvider);
	}
	
	public void handleMessagingProviderSelected(@Observes @Selected MessagingProvider messagingProvider) {
		selectionContext.setSelection("messagingProvider",  messagingProvider);
		messagingProviderPageManager.updateState(messagingProvider);
		messagingProviderPageManager.refreshMembers();
		setRecord(messagingProvider);
	}
	
	@Override
	public String newRecord() {
		return newMessagingProvider();
	}
	
	public String newMessagingProvider() {
		try {
			MessagingProvider messagingProvider = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("messagingProvider",  messagingProvider);
			String url = messagingProviderPageManager.initializeMessagingProviderCreationPage(messagingProvider);
			messagingProviderPageManager.pushContext(messagingProviderWizard);
			initialize(messagingProvider);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public MessagingProvider create() {
		MessagingProvider messagingProvider = MessagingProviderUtil.create();
		return messagingProvider;
	}
	
	@Override
	public MessagingProvider clone(MessagingProvider messagingProvider) {
		messagingProvider = MessagingProviderUtil.clone(messagingProvider);
		return messagingProvider;
	}
	
	@Override
	public String viewRecord() {
		return viewMessagingProvider();
	}
	
	public String viewMessagingProvider() {
		MessagingProvider messagingProvider = selectionContext.getSelection("messagingProvider");
		String url = viewMessagingProvider(messagingProvider);
		return url;
	}
	
	public String viewMessagingProvider(MessagingProvider messagingProvider) {
		try {
			String url = messagingProviderPageManager.initializeMessagingProviderSummaryView(messagingProvider);
			messagingProviderPageManager.pushContext(messagingProviderWizard);
			initialize(messagingProvider);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editMessagingProvider();
	}
	
	public String editMessagingProvider() {
		MessagingProvider messagingProvider = selectionContext.getSelection("messagingProvider");
		String url = editMessagingProvider(messagingProvider);
		return url;
	}
	
	public String editMessagingProvider(MessagingProvider messagingProvider) {
		try {
			//messagingProvider = clone(messagingProvider);
			selectionContext.resetOrigin();
			selectionContext.setSelection("messagingProvider",  messagingProvider);
			String url = messagingProviderPageManager.initializeMessagingProviderUpdatePage(messagingProvider);
			messagingProviderPageManager.pushContext(messagingProviderWizard);
			initialize(messagingProvider);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveMessagingProvider() {
		MessagingProvider messagingProvider = getMessagingProvider();
		if (validateMessagingProvider(messagingProvider)) {
			if (isImmediate())
				persistMessagingProvider(messagingProvider);
			outject("messagingProvider", messagingProvider);
		}
	}
	
	public void persistMessagingProvider(MessagingProvider messagingProvider) {
		saveMessagingProvider(messagingProvider);
	}
	
	public void saveMessagingProvider(MessagingProvider messagingProvider) {
		try {
			saveMessagingProviderToSystem(messagingProvider);
			messagingProviderEventManager.fireAddedEvent(messagingProvider);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveMessagingProviderToSystem(MessagingProvider messagingProvider) {
		messagingProviderDataManager.saveMessagingProvider(messagingProvider);
	}
	
	public void handleSaveMessagingProvider(@Observes @Add MessagingProvider messagingProvider) {
		saveMessagingProvider(messagingProvider);
	}
	
	public void addMessagingProvider(MessagingProvider messagingProvider) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichMessagingProvider(MessagingProvider messagingProvider) {
		//nothing for now
	}
	
	@Override
	public boolean validate(MessagingProvider messagingProvider) {
		return validateMessagingProvider(messagingProvider);
	}
	
	public boolean validateMessagingProvider(MessagingProvider messagingProvider) {
		Validator validator = getValidator();
		boolean isValid = MessagingProviderUtil.validate(messagingProvider);
		Display display = getFromSession("display");
		display.setModule("messagingProviderInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveMessagingProvider() {
		display = getFromSession("display");
		display.setModule("messagingProviderInfo");
		MessagingProvider messagingProvider = selectionContext.getSelection("messagingProvider");
		if (messagingProvider == null) {
			display.error("MessagingProvider record must be selected.");
		}
	}
	
	public String handleRemoveMessagingProvider(@Observes @Remove MessagingProvider messagingProvider) {
		display = getFromSession("display");
		display.setModule("messagingProviderInfo");
		try {
			display.info("Removing MessagingProvider "+MessagingProviderUtil.getLabel(messagingProvider)+" from the system.");
			removeMessagingProviderFromSystem(messagingProvider);
			selectionContext.clearSelection("messagingProvider");
			messagingProviderEventManager.fireClearSelectionEvent();
			messagingProviderEventManager.fireRemovedEvent(messagingProvider);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeMessagingProviderFromSystem(MessagingProvider messagingProvider) {
		if (messagingProviderDataManager.removeMessagingProvider(messagingProvider))
			setRecord(null);
	}
	
	public void cancelMessagingProvider() {
		BeanContext.removeFromSession("messagingProvider");
		messagingProviderPageManager.removeContext(messagingProviderWizard);
	}
	
}

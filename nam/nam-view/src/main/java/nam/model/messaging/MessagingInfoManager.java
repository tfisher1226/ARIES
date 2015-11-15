package nam.model.messaging;

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

import nam.model.Messaging;
import nam.model.Project;
import nam.model.util.MessagingUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("messagingInfoManager")
public class MessagingInfoManager extends AbstractNamRecordManager<Messaging> implements Serializable {
	
	@Inject
	private MessagingWizard messagingWizard;
	
	@Inject
	private MessagingDataManager messagingDataManager;
	
	@Inject
	private MessagingPageManager messagingPageManager;
	
	@Inject
	private MessagingEventManager messagingEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private MessagingHelper messagingHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public MessagingInfoManager() {
		setInstanceName("messaging");
	}
	
	
	public Messaging getMessaging() {
		return getRecord();
	}
	
	public Messaging getSelectedMessaging() {
		return selectionContext.getSelection("messaging");
	}
	
	@Override
	public Class<Messaging> getRecordClass() {
		return Messaging.class;
	}
	
	@Override
	public boolean isEmpty(Messaging messaging) {
		return messagingHelper.isEmpty(messaging);
	}
	
	@Override
	public String toString(Messaging messaging) {
		return messagingHelper.toString(messaging);
	}
	
	@Override
	public void initialize() {
		Messaging messaging = selectionContext.getSelection("messaging");
		if (messaging != null)
			initialize(messaging);
	}
	
	protected void initialize(Messaging messaging) {
		MessagingUtil.initialize(messaging);
		messagingWizard.initialize(messaging);
		setContext("messaging", messaging);
	}
	
	public void handleMessagingSelected(@Observes @Selected Messaging messaging) {
		selectionContext.setSelection("messaging",  messaging);
		messagingPageManager.updateState(messaging);
		messagingPageManager.refreshMembers();
		setRecord(messaging);
	}
	
	@Override
	public String newRecord() {
		return newMessaging();
	}
	
	public String newMessaging() {
		try {
			Messaging messaging = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("messaging",  messaging);
			String url = messagingPageManager.initializeMessagingCreationPage(messaging);
			messagingPageManager.pushContext(messagingWizard);
			initialize(messaging);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Messaging create() {
		Messaging messaging = MessagingUtil.create();
		return messaging;
	}
	
	@Override
	public Messaging clone(Messaging messaging) {
		messaging = MessagingUtil.clone(messaging);
		return messaging;
	}
	
	@Override
	public String viewRecord() {
		return viewMessaging();
	}
	
	public String viewMessaging() {
		Messaging messaging = selectionContext.getSelection("messaging");
		String url = viewMessaging(messaging);
		return url;
	}
	
	public String viewMessaging(Messaging messaging) {
		try {
			String url = messagingPageManager.initializeMessagingSummaryView(messaging);
			messagingPageManager.pushContext(messagingWizard);
			initialize(messaging);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editMessaging();
	}
	
	public String editMessaging() {
		Messaging messaging = selectionContext.getSelection("messaging");
		String url = editMessaging(messaging);
		return url;
	}
	
	public String editMessaging(Messaging messaging) {
		try {
			//messaging = clone(messaging);
			selectionContext.resetOrigin();
			selectionContext.setSelection("messaging",  messaging);
			String url = messagingPageManager.initializeMessagingUpdatePage(messaging);
			messagingPageManager.pushContext(messagingWizard);
			initialize(messaging);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveMessaging() {
		Messaging messaging = getMessaging();
		if (validateMessaging(messaging)) {
			if (isImmediate())
				persistMessaging(messaging);
			outject("messaging", messaging);
		}
	}
	
	public void persistMessaging(Messaging messaging) {
		saveMessaging(messaging);
	}
	
	public void saveMessaging(Messaging messaging) {
		try {
			saveMessagingToSystem(messaging);
			messagingEventManager.fireAddedEvent(messaging);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveMessagingToSystem(Messaging messaging) {
		messagingDataManager.saveMessaging(messaging);
	}
	
	public void handleSaveMessaging(@Observes @Add Messaging messaging) {
		saveMessaging(messaging);
	}
	
	public void addMessaging(Messaging messaging) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichMessaging(Messaging messaging) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Messaging messaging) {
		return validateMessaging(messaging);
	}
	
	public boolean validateMessaging(Messaging messaging) {
		Validator validator = getValidator();
		boolean isValid = MessagingUtil.validate(messaging);
		Display display = getFromSession("display");
		display.setModule("messagingInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveMessaging() {
		display = getFromSession("display");
		display.setModule("messagingInfo");
		Messaging messaging = selectionContext.getSelection("messaging");
		if (messaging == null) {
			display.error("Messaging record must be selected.");
		}
	}
	
	public String handleRemoveMessaging(@Observes @Remove Messaging messaging) {
		display = getFromSession("display");
		display.setModule("messagingInfo");
		try {
			display.info("Removing Messaging "+MessagingUtil.getLabel(messaging)+" from the system.");
			removeMessagingFromSystem(messaging);
			selectionContext.clearSelection("messaging");
			messagingEventManager.fireClearSelectionEvent();
			messagingEventManager.fireRemovedEvent(messaging);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeMessagingFromSystem(Messaging messaging) {
		if (messagingDataManager.removeMessaging(messaging))
			setRecord(null);
	}
	
	public void cancelMessaging() {
		BeanContext.removeFromSession("messaging");
		messagingPageManager.removeContext(messagingWizard);
	}
	
}

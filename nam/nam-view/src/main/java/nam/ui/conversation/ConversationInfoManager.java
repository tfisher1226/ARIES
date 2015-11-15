package nam.ui.conversation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.Conversation;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;
import nam.ui.util.ConversationUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.util.Validator;


@SessionScoped
@Named("conversationInfoManager")
public class ConversationInfoManager extends AbstractNamRecordManager<Conversation> implements Serializable {
	
	@Inject
	private ConversationWizard conversationWizard;
	
	@Inject
	private ConversationDataManager conversationDataManager;
	
	@Inject
	private ConversationPageManager conversationPageManager;
	
	@Inject
	private ConversationEventManager conversationEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ConversationHelper conversationHelper;

	@Inject
	private SelectionContext selectionContext;

	
	public ConversationInfoManager() {
		setInstanceName("conversation");
	}
	
	
	public Conversation getConversation() {
		return getRecord();
	}
	
	public Conversation getSelectedConversation() {
		return selectionContext.getSelection("conversation");
	}
	
	@Override
	public Class<Conversation> getRecordClass() {
		return Conversation.class;
	}
	
	@Override
	public boolean isEmpty(Conversation conversation) {
		return conversationHelper.isEmpty(conversation);
	}
	
	@Override
	public String toString(Conversation conversation) {
		return conversationHelper.toString(conversation);
	}
	
	@Override
	public void initialize() {
		Conversation conversation = selectionContext.getSelection("conversation");
		if (conversation != null)
			initialize(conversation);
	}
	
	protected void initialize(Conversation conversation) {
		ConversationUtil.initialize(conversation);
		conversationWizard.initialize(conversation);
		setContext("conversation", conversation);
	}
	
	public void handleConversationSelected(@Observes @Selected Conversation conversation) {
		selectionContext.setSelection("conversation",  conversation);
		conversationPageManager.updateState(conversation);
		conversationPageManager.refreshMembers();
		setRecord(conversation);
	}
	
	@Override
	public String newRecord() {
		return newConversation();
	}
	
	public String newConversation() {
		try {
			Conversation conversation = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("conversation",  conversation);
			String url = conversationPageManager.initializeConversationCreationPage(conversation);
			conversationPageManager.pushContext(conversationWizard);
			initialize(conversation);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Conversation create() {
		Conversation conversation = ConversationUtil.create();
		return conversation;
	}
	
	@Override
	public Conversation clone(Conversation conversation) {
		conversation = ConversationUtil.clone(conversation);
		return conversation;
	}
	
	@Override
	public String viewRecord() {
		return viewConversation();
	}
	
	public String viewConversation() {
		Conversation conversation = selectionContext.getSelection("conversation");
		String url = viewConversation(conversation);
		return url;
	}
	
	public String viewConversation(Conversation conversation) {
		try {
			String url = conversationPageManager.initializeConversationSummaryView(conversation);
			conversationPageManager.pushContext(conversationWizard);
			initialize(conversation);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editConversation();
	}
	
	public String editConversation() {
		Conversation conversation = selectionContext.getSelection("conversation");
		String url = editConversation(conversation);
		return url;
	}
	
	public String editConversation(Conversation conversation) {
		try {
			//conversation = clone(conversation);
			selectionContext.resetOrigin();
			selectionContext.setSelection("conversation",  conversation);
			String url = conversationPageManager.initializeConversationUpdatePage(conversation);
			conversationPageManager.pushContext(conversationWizard);
			initialize(conversation);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveConversation() {
		Conversation conversation = getConversation();
		if (validateConversation(conversation)) {
			if (isImmediate())
				persistConversation(conversation);
			outject("conversation", conversation);
		}
	}
	
	public void persistConversation(Conversation conversation) {
		saveConversation(conversation);
	}
	
	public void saveConversation(Conversation conversation) {
		try {
			saveConversationToSystem(conversation);
			conversationEventManager.fireAddedEvent(conversation);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveConversationToSystem(Conversation conversation) {
		conversationDataManager.saveConversation(conversation);
	}
	
	public void handleSaveConversation(@Observes @Add Conversation conversation) {
		saveConversation(conversation);
	}
	
	public void addConversation(Conversation conversation) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichConversation(Conversation conversation) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Conversation conversation) {
		return validateConversation(conversation);
	}
	
	public boolean validateConversation(Conversation conversation) {
		Validator validator = getValidator();
		boolean isValid = ConversationUtil.validate(conversation);
		Display display = getFromSession("display");
		display.setModule("conversationInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveConversation() {
		display = getFromSession("display");
		display.setModule("conversationInfo");
		Conversation conversation = selectionContext.getSelection("conversation");
		if (conversation == null) {
			display.error("Conversation record must be selected.");
		}
	}
	
	public String handleRemoveConversation(@Observes @Remove Conversation conversation) {
		display = getFromSession("display");
		display.setModule("conversationInfo");
		try {
			display.info("Removing Conversation "+ConversationUtil.getLabel(conversation)+" from the system.");
			removeConversationFromSystem(conversation);
			selectionContext.clearSelection("conversation");
			conversationEventManager.fireClearSelectionEvent();
			conversationEventManager.fireRemovedEvent(conversation);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeConversationFromSystem(Conversation conversation) {
		if (conversationDataManager.removeConversation(conversation))
			setRecord(null);
	}
	
	public void cancelConversation() {
		BeanContext.removeFromSession("conversation");
		conversationPageManager.removeContext(conversationWizard);
	}
	
}

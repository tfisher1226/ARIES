package nam.ui.conversation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Project;
import nam.ui.Conversation;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;


@SessionScoped
@Named("conversationWizard")
@SuppressWarnings("serial")
public class ConversationWizard extends AbstractDomainElementWizard<Conversation> implements Serializable {
	
	@Inject
	private ConversationDataManager conversationDataManager;
	
	@Inject
	private ConversationPageManager conversationPageManager;
	
	@Inject
	private ConversationEventManager conversationEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Conversation";
	}
	
	@Override
	public String getUrlContext() {
		return conversationPageManager.getConversationWizardPage();
	}
	
	@Override
	public void initialize(Conversation conversation) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(conversationPageManager.getSections());
		super.initialize(conversation);
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
		conversationPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		conversationPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		conversationPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		conversationPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Conversation conversation = getInstance();
		conversationDataManager.saveConversation(conversation);
		conversationEventManager.fireSavedEvent(conversation);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Conversation conversation = getInstance();
		//TODO take this out soon
		if (conversation == null)
			conversation = new Conversation();
		conversationEventManager.fireCancelledEvent(conversation);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Conversation conversation = selectionContext.getSelection("conversation");
		String name = conversation.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("conversationWizard");
			display.error("Conversation name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}

package nam.ui.conversation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.ui.Conversation;
import nam.ui.util.ConversationUtil;


@SessionScoped
@Named("conversationConfigurationSection")
public class ConversationRecord_ConfigurationSection extends AbstractWizardPage<Conversation> implements Serializable {

	private Conversation conversation;

	
	public ConversationRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
	}
	
	
	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	@Override
	public void initialize(Conversation conversation) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setConversation(conversation);
	}
	
	@Override
	public void validate() {
		if (conversation == null) {
			validator.missing("Conversation");
		} else {
		}
	}
	
}

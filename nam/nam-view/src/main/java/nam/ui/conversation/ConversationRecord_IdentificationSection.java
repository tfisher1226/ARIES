package nam.ui.conversation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.ui.Conversation;
import nam.ui.util.ConversationUtil;


@SessionScoped
@Named("conversationIdentificationSection")
public class ConversationRecord_IdentificationSection extends AbstractWizardPage<Conversation> implements Serializable {

	private Conversation conversation;


	public ConversationRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
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

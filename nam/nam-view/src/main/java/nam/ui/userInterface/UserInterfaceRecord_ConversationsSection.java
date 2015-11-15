package nam.ui.userInterface;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.ui.UserInterface;

import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("userInterfaceConversationsSection")
public class UserInterfaceRecord_ConversationsSection extends AbstractWizardPage<UserInterface> {

	private UserInterface userInterface;

	
	public UserInterfaceRecord_ConversationsSection() {
		setName("Conversations");
		setUrl("conversations");
	}

	public UserInterface getUserInterface() {
		return userInterface;
	}

	public void setUserInterface(UserInterface UserInterface) {
		this.userInterface = UserInterface;
	}

	public void initialize(UserInterface UserInterface) {
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setUserInterface(UserInterface);
	}
	
	public void validate() {
		if (userInterface == null) {
			validator.missing("UserInterface");
		} else {
			
		}
	}

}

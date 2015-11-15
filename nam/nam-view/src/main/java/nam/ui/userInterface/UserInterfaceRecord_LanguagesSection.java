package nam.ui.userInterface;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.ui.UserInterface;

import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("userInterfaceLanguagesSection")
public class UserInterfaceRecord_LanguagesSection extends AbstractWizardPage<UserInterface> {

	private UserInterface userInterface;

	
	public UserInterfaceRecord_LanguagesSection() {
		setName("Languages");
		setUrl("languages");
	}

	public UserInterface getUserInterface() {
		return userInterface;
	}

	public void setUserInterface(UserInterface userInterface) {
		this.userInterface = userInterface;
	}

	public void initialize(UserInterface userInterface) {
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setUserInterface(userInterface);
	}
	
	public void validate() {
		if (userInterface == null) {
			validator.missing("UserInterface");
		} else {
			
		}
	}

}

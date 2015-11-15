package nam.ui.userInterface;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.ui.UserInterface;

import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("userInterfaceGraphicsSection")
public class UserInterfaceRecord_GraphicsSection extends AbstractWizardPage<UserInterface> {

	private UserInterface userInterface;

	
	public UserInterfaceRecord_GraphicsSection() {
		setName("Graphics");
		setUrl("graphics");
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

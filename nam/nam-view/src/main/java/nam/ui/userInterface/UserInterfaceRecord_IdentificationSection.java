package nam.ui.userInterface;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.ui.UserInterface;
import nam.ui.util.UserInterfaceUtil;


@SessionScoped
@Named("userInterfaceIdentificationSection")
public class UserInterfaceRecord_IdentificationSection extends AbstractWizardPage<UserInterface> implements Serializable {
	
	private UserInterface userInterface;
	
	
	public UserInterfaceRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}
	
	
	public UserInterface getUserInterface() {
		return userInterface;
	}
	
	public void setUserInterface(UserInterface userInterface) {
		this.userInterface = userInterface;
	}
	
	@Override
	public void initialize(UserInterface userInterface) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setUserInterface(userInterface);
	}
	
	@Override
	public void validate() {
		if (userInterface == null) {
			validator.missing("UserInterface");
		} else {
		}
	}
	
}

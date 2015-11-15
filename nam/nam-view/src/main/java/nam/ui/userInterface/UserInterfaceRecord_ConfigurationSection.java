package nam.ui.userInterface;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.ui.UserInterface;
import nam.ui.util.UserInterfaceUtil;


@SessionScoped
@Named("userInterfaceConfigurationSection")
public class UserInterfaceRecord_ConfigurationSection extends AbstractWizardPage<UserInterface> implements Serializable {
	
	private UserInterface userInterface;
	
	
	public UserInterfaceRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
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
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
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

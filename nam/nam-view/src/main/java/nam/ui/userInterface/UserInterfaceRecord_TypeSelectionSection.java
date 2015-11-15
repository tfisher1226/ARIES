package nam.ui.userInterface;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.ui.UserInterface;
import nam.ui.design.SelectionContext;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("UserInterfaceTypeSelectionSection")
public class UserInterfaceRecord_TypeSelectionSection extends AbstractWizardPage<UserInterface> {

	private UserInterface userInterface;

	
	public UserInterfaceRecord_TypeSelectionSection() {
		setName("Type Selection");
		setUrl("typeSelection");
	}
	
	public UserInterface getUserInterface() {
		return userInterface;
	}

	public void setUserInterface(UserInterface userInterface) {
		this.userInterface = userInterface;
	}

	public void initialize(UserInterface userInterface) {
		setBackVisible(false);
		setNextVisible(true);
		setNextEnabled(true);
		setFinishVisible(false);
		setUserInterface(userInterface);
	}
	
	@Override
	public String next() {
		UserInterfaceInfoManager UserInterfaceInfoManager = BeanContext.getFromSession("UserInterfaceInfoManager");
		return UserInterfaceInfoManager.newUserInterface();
	}
	
	public void validate() {
		SelectionContext selectionContext = BeanContext.getFromSession("selectionContext");
		String projectName = selectionContext.getSelectedName();
		if (projectName == null) {
			validator.missing("Project");
		} else {
			//if (UserInterface.get
			//if (UserInterface.getConfiguration() == null)
			//	validator.missing("Configuration");
		}
	}
	
}

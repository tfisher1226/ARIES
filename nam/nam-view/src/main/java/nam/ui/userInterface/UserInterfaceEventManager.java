package nam.ui.userInterface;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.UserInterface;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("userInterfaceEventManager")
public class UserInterfaceEventManager extends AbstractEventManager<UserInterface> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public UserInterface getInstance() {
		return selectionContext.getSelection("userInterface");
	}
	
	public void removeUserInterface() {
		UserInterface userInterface = getInstance();
		fireRemoveEvent(userInterface);
	}
	
}

package nam.model.command;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Command;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("commandEventManager")
public class CommandEventManager extends AbstractEventManager<Command> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Command getInstance() {
		return selectionContext.getSelection("command");
	}
	
	public void removeCommand() {
		Command command = getInstance();
		fireRemoveEvent(command);
	}
	
}

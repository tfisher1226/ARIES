package nam.model.command;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.Command;
import nam.model.Project;
import nam.model.util.CommandUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("commandInfoManager")
public class CommandInfoManager extends AbstractNamRecordManager<Command> implements Serializable {
	
	@Inject
	private CommandWizard commandWizard;
	
	@Inject
	private CommandDataManager commandDataManager;
	
	@Inject
	private CommandPageManager commandPageManager;
	
	@Inject
	private CommandEventManager commandEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private CommandHelper commandHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public CommandInfoManager() {
		setInstanceName("command");
	}
	
	
	public Command getCommand() {
		return getRecord();
	}
	
	public Command getSelectedCommand() {
		return selectionContext.getSelection("command");
	}
	
	@Override
	public Class<Command> getRecordClass() {
		return Command.class;
	}
	
	@Override
	public boolean isEmpty(Command command) {
		return commandHelper.isEmpty(command);
	}
	
	@Override
	public String toString(Command command) {
		return commandHelper.toString(command);
	}
	
	@Override
	public void initialize() {
		Command command = selectionContext.getSelection("command");
		if (command != null)
			initialize(command);
	}
	
	protected void initialize(Command command) {
		CommandUtil.initialize(command);
		commandWizard.initialize(command);
		setContext("command", command);
	}
	
	public void handleCommandSelected(@Observes @Selected Command command) {
		selectionContext.setSelection("command",  command);
		commandPageManager.updateState(command);
		commandPageManager.refreshMembers();
		setRecord(command);
	}
	
	@Override
	public String newRecord() {
		return newCommand();
	}
	
	public String newCommand() {
		try {
			Command command = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("command",  command);
			String url = commandPageManager.initializeCommandCreationPage(command);
			commandPageManager.pushContext(commandWizard);
			initialize(command);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Command create() {
		Command command = CommandUtil.create();
		return command;
	}
	
	@Override
	public Command clone(Command command) {
		command = CommandUtil.clone(command);
		return command;
	}
	
	@Override
	public String viewRecord() {
		return viewCommand();
	}
	
	public String viewCommand() {
		Command command = selectionContext.getSelection("command");
		String url = viewCommand(command);
		return url;
	}
	
	public String viewCommand(Command command) {
		try {
			String url = commandPageManager.initializeCommandSummaryView(command);
			commandPageManager.pushContext(commandWizard);
			initialize(command);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editCommand();
	}
	
	public String editCommand() {
		Command command = selectionContext.getSelection("command");
		String url = editCommand(command);
		return url;
	}
	
	public String editCommand(Command command) {
		try {
			//command = clone(command);
			selectionContext.resetOrigin();
			selectionContext.setSelection("command",  command);
			String url = commandPageManager.initializeCommandUpdatePage(command);
			commandPageManager.pushContext(commandWizard);
			initialize(command);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveCommand() {
		Command command = getCommand();
		if (validateCommand(command)) {
			saveCommand(command);
		}
	}
	
	public void persistCommand(Command command) {
		saveCommand(command);
	}
	
	public void saveCommand(Command command) {
		try {
			saveCommandToSystem(command);
			commandEventManager.fireAddedEvent(command);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveCommandToSystem(Command command) {
		commandDataManager.saveCommand(command);
	}
	
	public void handleSaveCommand(@Observes @Add Command command) {
		saveCommand(command);
	}
	
	public void enrichCommand(Command command) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Command command) {
		return validateCommand(command);
	}
	
	public boolean validateCommand(Command command) {
		Validator validator = getValidator();
		boolean isValid = CommandUtil.validate(command);
		Display display = getFromSession("display");
		display.setModule("commandInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveCommand() {
		display = getFromSession("display");
		display.setModule("commandInfo");
		Command command = selectionContext.getSelection("command");
		if (command == null) {
			display.error("Command record must be selected.");
		}
	}
	
	public String handleRemoveCommand(@Observes @Remove Command command) {
		display = getFromSession("display");
		display.setModule("commandInfo");
		try {
			display.info("Removing Command "+CommandUtil.getLabel(command)+" from the system.");
			removeCommandFromSystem(command);
			selectionContext.clearSelection("command");
			commandEventManager.fireClearSelectionEvent();
			commandEventManager.fireRemovedEvent(command);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeCommandFromSystem(Command command) {
		if (commandDataManager.removeCommand(command))
			setRecord(null);
	}
	
	public void cancelCommand() {
		BeanContext.removeFromSession("command");
		commandPageManager.removeContext(commandWizard);
	}
	
}

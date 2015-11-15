package nam.model.command;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import nam.model.Command;
import nam.model.util.CommandUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("commandListManager")
public class CommandListManager extends AbstractDomainListManager<Command, CommandListObject> implements Serializable {
	
	@Inject
	private CommandDataManager commandDataManager;
	
	@Inject
	private CommandEventManager commandEventManager;
	
	@Inject
	private CommandInfoManager commandInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "commandList";
	}
	
	@Override
	public String getTitle() {
		return "Command List";
	}
	
	@Override
	public Object getRecordKey(Command command) {
		return CommandUtil.getKey(command);
	}
	
	@Override
	public String getRecordName(Command command) {
		return CommandUtil.getLabel(command);
	}
	
	@Override
	protected Class<Command> getRecordClass() {
		return Command.class;
	}
	
	@Override
	protected Command getRecord(CommandListObject rowObject) {
		return rowObject.getCommand();
	}
	
	@Override
	public Command getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? CommandUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Command command) {
		super.setSelectedRecord(command);
		fireSelectedEvent(command);
	}
	
	protected void fireSelectedEvent(Command command) {
		commandEventManager.fireSelectedEvent(command);
	}
	
	public boolean isSelected(Command command) {
		Command selection = selectionContext.getSelection("command");
		boolean selected = selection != null && selection.equals(command);
		return selected;
	}
	
	@Override
	protected CommandListObject createRowObject(Command command) {
		CommandListObject listObject = new CommandListObject(command);
		listObject.setSelected(isSelected(command));
		return listObject;
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
		else refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Command> createRecordList() {
		try {
			Collection<Command> commandList = commandDataManager.getCommandList();
			if (commandList != null)
				return commandList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewCommand() {
		return viewCommand(selectedRecordKey);
	}
	
	public String viewCommand(Object recordKey) {
		Command command = recordByKeyMap.get(recordKey);
		return viewCommand(command);
	}
	
	public String viewCommand(Command command) {
		String url = commandInfoManager.viewCommand(command);
		return url;
	}
	
	public String editCommand() {
		return editCommand(selectedRecordKey);
	}
	
	public String editCommand(Object recordKey) {
		Command command = recordByKeyMap.get(recordKey);
		return editCommand(command);
	}
	
	public String editCommand(Command command) {
		String url = commandInfoManager.editCommand(command);
		return url;
	}
	
	public void removeCommand() {
		removeCommand(selectedRecordKey);
	}
	
	public void removeCommand(Object recordKey) {
		Command command = recordByKeyMap.get(recordKey);
		removeCommand(command);
	}
	
	public void removeCommand(Command command) {
		try {
			if (commandDataManager.removeCommand(command))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelCommand(@Observes @Cancelled Command command) {
		try {
			//Object key = CommandUtil.getKey(command);
			//recordByKeyMap.put(key, command);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("command");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateCommand(Collection<Command> commandList) {
		return CommandUtil.validate(commandList);
	}
	
	public void exportCommandList(@Observes @Export String tableId) {
		//String tableId = "pageForm:commandListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}

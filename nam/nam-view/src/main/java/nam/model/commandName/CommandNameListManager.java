package nam.model.commandName;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.ui.AbstractDomainListManager;

import nam.model.CommandName;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("commandNameListManager")
public class CommandNameListManager extends AbstractDomainListManager<CommandName, CommandNameListObject> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "commandNameList";
	}
	
	@Override
	public String getTitle() {
		return "CommandName List";
	}
	
	@Override
	public Object getRecordKey(CommandName commandName) {
		return commandName.name();
	}
	
	@Override
	public String getRecordName(CommandName commandName) {
		return commandName.name();
	}
	
	@Override
	protected Class<CommandName> getRecordClass() {
		return CommandName.class;
	}
	
	@Override
	protected CommandName getRecord(CommandNameListObject rowObject) {
		return rowObject.getCommandName();
	}
	
	@Override
	public CommandName getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? selectedRecord.name() : null;
	}
	
	@Override
	public void setSelectedRecord(CommandName commandName) {
		super.setSelectedRecord(commandName);
	}
	
	public boolean isSelected(CommandName commandName) {
		CommandName selection = selectionContext.getSelection("commandName");
		boolean selected = selection != null && selection.equals(commandName);
		return selected;
	}
	
	@Override
	protected CommandNameListObject createRowObject(CommandName commandName) {
		CommandNameListObject listObject = new CommandNameListObject(commandName);
		listObject.setSelected(isSelected(commandName));
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
	protected Collection<CommandName> createRecordList() {
		try {
			Collection<CommandName> commandNameList = Arrays.asList(CommandName.values());
			return commandNameList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
}

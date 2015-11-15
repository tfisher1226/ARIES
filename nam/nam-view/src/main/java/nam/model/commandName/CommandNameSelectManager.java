package nam.model.commandName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.CommandName;


@SessionScoped
@Named("commandNameSelectManager")
public class CommandNameSelectManager extends AbstractSelectManager<CommandName, CommandNameListObject> implements Serializable {
	
	@Inject
	private CommandNameHelper commandNameHelper;
	
	
	@Override
	public String getClientId() {
		return "commandNameSelect";
	}
	
	@Override
	public String getTitle() {
		return "CommandName Selection";
	}
	
	@Override
	protected Class<CommandName> getRecordClass() {
		return CommandName.class;
	}
	
	@Override
	public String toString(CommandName commandName) {
		return commandName.name();
	}
	
	protected CommandNameHelper getCommandNameHelper() {
		return BeanContext.getFromSession("commandNameHelper");
	}
	
	protected CommandNameListManager getCommandNameListManager() {
		return BeanContext.getFromSession("commandNameListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshCommandNameList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<CommandName> recordList) {
		CommandNameListManager commandNameListManager = getCommandNameListManager();
		DataModel<CommandNameListObject> dataModel = commandNameListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshCommandNameList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<CommandName> refreshRecords() {
		CommandName[] values = CommandName.values();
		List<CommandName> masterList = new ArrayList<CommandName>();
		for (CommandName capability : values) {
			masterList.add(capability);
		}
		return masterList;
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<CommandName> commandNameList) {
		Collections.sort(commandNameList);
	}
	
}

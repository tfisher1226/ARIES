package nam.model.command;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Command;
import nam.model.util.CommandUtil;


@SessionScoped
@Named("commandSelectManager")
public class CommandSelectManager extends AbstractSelectManager<Command, CommandListObject> implements Serializable {
	
	@Inject
	private CommandDataManager commandDataManager;
	
	@Inject
	private CommandHelper commandHelper;
	
	
	@Override
	public String getClientId() {
		return "commandSelect";
	}
	
	@Override
	public String getTitle() {
		return "Command Selection";
	}
	
	@Override
	protected Class<Command> getRecordClass() {
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
	
	protected CommandHelper getCommandHelper() {
		return BeanContext.getFromSession("commandHelper");
	}
	
	protected CommandListManager getCommandListManager() {
		return BeanContext.getFromSession("commandListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshCommandList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Command> recordList) {
		CommandListManager commandListManager = getCommandListManager();
		DataModel<CommandListObject> dataModel = commandListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshCommandList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Command> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Command> commandList = BeanContext.getFromConversation(instanceId);
		return commandList;
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
	public void sortRecords(List<Command> commandList) {
		Collections.sort(commandList, new Comparator<Command>() {
			public int compare(Command command1, Command command2) {
				String text1 = CommandUtil.toString(command1);
				String text2 = CommandUtil.toString(command2);
				return text1.compareTo(text2);
			}
		});
	}
	
}

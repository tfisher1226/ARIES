package nam.model.command;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Command;
import nam.model.Operation;
import nam.model.Service;
import nam.model.util.OperationUtil;
import nam.model.util.ServiceUtil;
import nam.ui.design.SelectionContext;

import org.aries.Assert;


@SessionScoped
@Named("commandDataManager")
public class CommandDataManager implements Serializable {
	
	@Inject
	private CommandEventManager commandEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	public Collection<Command> getCommandList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;

		if (owner instanceof Operation)
			return OperationUtil.getCommands((Operation) owner);
		
		return getDefaultList();
	}
	
	public Collection<Command> getDefaultList() {
		return null;
	}
	
	public void saveCommand(Command command) {
		if (scope != null) {
			Object owner = getOwner();
			if (owner instanceof Operation)
				OperationUtil.addCommand((Operation) owner, command);
		}
	}
	
	public boolean removeCommand(Command command) {
		if (scope != null) {
			Object owner = getOwner();
			if (owner instanceof Operation)
				return OperationUtil.removeCommand((Operation) owner, command);
		}
		return false;
	}
	
}

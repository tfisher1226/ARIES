package nam.model.commandName;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.aries.ui.manager.AbstractEnumerationHelper;

import nam.model.CommandName;
import nam.model.util.CommandNameUtil;


@SessionScoped
@Named("commandNameHelper")
public class CommandNameHelper extends AbstractEnumerationHelper<CommandName> implements Serializable {
	
	@Produces
	public CommandName[] getCommandNameArray() {
		return CommandName.values();
	}
	
	@Override
	public String toString(CommandName commandName) {
		return commandName.name();
	}
	
	@Override
	public String toString(Collection<CommandName> commandNameList) {
		return CommandNameUtil.toString(commandNameList);
	}
	
}

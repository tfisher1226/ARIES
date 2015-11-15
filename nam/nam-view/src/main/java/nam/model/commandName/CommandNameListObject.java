package nam.model.commandName;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.CommandName;


public class CommandNameListObject extends AbstractListObject<CommandName> implements Comparable<CommandNameListObject>, Serializable {
	
	private CommandName commandName;
	
	
	public CommandNameListObject(CommandName commandName) {
		this.commandName = commandName;
	}
	
	
	public CommandName getCommandName() {
		return commandName;
	}
	
	@Override
	public Object getKey() {
		return commandName.name();
	}
	
	@Override
	public String getLabel() {
		return commandName.name();
	}
	
	@Override
	public String toString() {
		return toString(commandName);
	}
	
	@Override
	public String toString(CommandName commandName) {
		return commandName.name();
	}
	
	@Override
	public int compareTo(CommandNameListObject other) {
		String thisText = this.commandName.name();
		String otherText = other.commandName.name();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		CommandNameListObject other = (CommandNameListObject) object;
		String thisText = toString(this.commandName);
		String otherText = toString(other.commandName);
		return thisText.equals(otherText);
	}
	
}

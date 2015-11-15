package nam.model.command;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Command;
import nam.model.util.CommandUtil;


public class CommandListObject extends AbstractListObject<Command> implements Comparable<CommandListObject>, Serializable {
	
	private Command command;
	
	
	public CommandListObject(Command command) {
		this.command = command;
	}
	
	
	public Command getCommand() {
		return command;
	}
	
	@Override
	public Object getKey() {
		return getKey(command);
	}
	
	public Object getKey(Command command) {
		return CommandUtil.getKey(command);
	}
	
	@Override
	public String getLabel() {
		return getLabel(command);
	}
	
	public String getLabel(Command command) {
		return CommandUtil.getLabel(command);
	}
	
	@Override
	public String toString() {
		return toString(command);
	}
	
	@Override
	public String toString(Command command) {
		return CommandUtil.toString(command);
	}
	
	@Override
	public int compareTo(CommandListObject other) {
		Object thisKey = getKey(this.command);
		Object otherKey = getKey(other.command);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		CommandListObject other = (CommandListObject) object;
		Object thisKey = CommandUtil.getKey(this.command);
		Object otherKey = CommandUtil.getKey(other.command);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}

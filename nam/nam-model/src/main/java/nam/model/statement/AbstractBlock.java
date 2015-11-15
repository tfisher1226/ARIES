package nam.model.statement;

import java.util.ArrayList;
import java.util.List;

import nam.model.Command;


public abstract class AbstractBlock {

	private String name;

	private List<Command> commands = new ArrayList<Command>();

	
	public AbstractBlock() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}

	public void addCommand(Command command) {
		commands.add(command);
	}

}

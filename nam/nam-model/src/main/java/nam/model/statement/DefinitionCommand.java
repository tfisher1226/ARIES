package nam.model.statement;

import nam.model.Command;
import nam.model.Definition;


public class DefinitionCommand extends Command {

	private Definition definition;

	
	public DefinitionCommand() {
	}

	public Definition getDefinition() {
		return definition;
	}

	public void setDefinition(Definition definition) {
		this.definition = definition;
	}
	
	public String toString() {
		return getText(); //getSources();
	}

}


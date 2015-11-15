package nam.model.statement;

import java.util.ArrayList;
import java.util.List;

import nam.model.Command;


public class IfStatement extends Statement {

	private String conditionText;

	private Branch successBranch;
	
	private Branch avoidanceBranch;
	
	private ConditionStatement conditionStatement;

	private List<Command> commands = new ArrayList<Command>();

	private List<Command> elseIfCommands = new ArrayList<Command>();

	private List<Command> elseCommands = new ArrayList<Command>();

	
	public IfStatement() {
	}
	
	public String getConditionText() {
		return conditionText;
	}

	public void setConditionText(String conditionText) {
		this.conditionText = conditionText;
	}

	public ConditionStatement getConditionStatement() {
		return conditionStatement;
	}

	public void setConditionStatement(ConditionStatement conditionStatement) {
		this.conditionStatement = conditionStatement;
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
	
	public List<Command> getElseIfCommands() {
		return elseIfCommands;
	}

	public void setElseIfCommands(List<Command> elseIfCommands) {
		this.elseIfCommands = elseIfCommands;
	}

	public void addElseIfCommand(Command elseIfCommand) {
		elseIfCommands.add(elseIfCommand);
	}

	public List<Command> getElseCommands() {
		return elseCommands;
	}

	public void setElseCommands(List<Command> elseCommands) {
		this.elseCommands = elseCommands;
	}
	
	public void addElseCommand(Command elseCommand) {
		elseCommands.add(elseCommand);
	}

}

package aries.codegen.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import nam.model.Command;
import nam.model.Definition;
import nam.model.Service;
import nam.model.statement.AbstractBlock;
import nam.model.statement.BlockStatement;
import nam.model.statement.Branch;
import nam.model.statement.ConditionStatement;
import nam.model.statement.DoneCommand;
import nam.model.util.CommandUtil;


public class Pathway {

	private String name;
	
	private String type;

	private String label;

	private Command rootCommand;

	private AbstractBlock commandBlock;
	
	private List<Command> commands = new ArrayList<Command>();
	
	private Map<String, Definition> availableDefinitions = new HashMap<String, Definition>();
	
	private Stack<ConditionStatement> conditionStatements = new Stack<ConditionStatement>();

	private Set<ConditionStatement> requiredConditions = new LinkedHashSet<ConditionStatement>();
	
	//TODO eventually make this a stack 
	private Service remoteService;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Command getRootCommand() {
		return rootCommand;
	}

	public void setRootCommand(Command rootCommand) {
		this.rootCommand = rootCommand;
	}

	public AbstractBlock getCommandBlock() {
		return commandBlock;
	}

	public void setCommandBlock(AbstractBlock commandBlock) {
		this.commandBlock = commandBlock;
	}

	public Collection<Command> getCommands() {
		return commands;
	}

	public void setCommands(Collection<Command> commands) {
		this.commands = new ArrayList<Command>(commands);
	}

	public void addToCommands(Command command) {
		this.commands.add(command);
	}

	public void addToCommands(Collection<Command> commands) {
		this.commands.addAll(commands);
	}

	public Map<String, Definition> getAvailableDefinitions() {
		return availableDefinitions;
	}

	public Definition getAvailableDefinition(String name) {
		return availableDefinitions.get(name);
	}
	
	public void setAvailableDefinitions(Map<String, Definition> availableDefinitions) {
		this.availableDefinitions = availableDefinitions;
	}
	
	public void addAvailableDefinition(Definition availableDefinition) {
		this.availableDefinitions.put(availableDefinition.getName(), availableDefinition);
	}
	
	public Stack<ConditionStatement> getConditionStatements() {
		return conditionStatements;
	}

	public void pushConditionStatement(ConditionStatement conditionStatement) {
		this.conditionStatements.push(conditionStatement);
	}	
	
	public ConditionStatement popConditionStatement() {
		return conditionStatements.pop();
	}

	public Set<ConditionStatement> getRequiredConditions() {
		return requiredConditions;
	}

	public void setRequiredConditions(Collection<ConditionStatement> requiredConditions) {
		this.requiredConditions = new LinkedHashSet<ConditionStatement>();
		addRequiredConditions(requiredConditions);
	}
	
	public void addRequiredConditions(Collection<ConditionStatement> requiredConditions) {
		this.requiredConditions.addAll(requiredConditions);
	}
	
	public void addRequiredConditions(ConditionStatement requiredCondition) {
		this.requiredConditions.add(requiredCondition);
	}
	
	public Collection<Branch> getRequiredBranches() {
		Collection<Branch> requiredBranches = new LinkedHashSet<Branch>();
		if (rootCommand == null)
			return requiredBranches;
		if (rootCommand instanceof BlockStatement == false)
			return requiredBranches;
		BlockStatement blockStatement = (BlockStatement) rootCommand;
		return blockStatement.getRequiredBranches();
	}
	
	public Service getRemoteService() {
		return remoteService;
	}

	public void setRemoteService(Service remoteService) {
		this.remoteService = remoteService;
	}

	public boolean hasDoneCommand() {
		Collection<DoneCommand> doneCommands = new ArrayList<DoneCommand>();
		if (commands != null)
			doneCommands.addAll(CommandUtil.getCommands(commands, DoneCommand.class));
		if (commandBlock != null)
			doneCommands.addAll(CommandUtil.getCommands(commandBlock.getCommands(), DoneCommand.class));
		return doneCommands.size() > 0;
	}
	
}

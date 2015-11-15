package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>BlockNode class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String text</pre>
 * 
 */
public class BlockNode extends CommandNode {

	private List<CommandNode> commandNodes = new ArrayList<CommandNode>();

	private List<TimeoutNode> timeoutNodes = new ArrayList<TimeoutNode>();
	
	private List<ExceptionNode> exceptionNodes = new ArrayList<ExceptionNode>();
	
	
	public BlockNode() {
	}
	
	public BlockNode(Object node) {
		super(node);
	}

	public List<CommandNode> getCommandNodes() {
		return commandNodes;
	}

	public void setCommandNodes(List<CommandNode> commandNodes) {
		this.commandNodes = commandNodes;
	}

	public void addCommandNode(CommandNode commandNode) {
		commandNodes.add(commandNode);
	}

	public List<TimeoutNode> getTimeoutNodes() {
		return timeoutNodes;
	}

	public void addTimeoutNode(TimeoutNode timeoutNode) {
		timeoutNodes.add(timeoutNode);
	}
	
	public List<ExceptionNode> getExceptionNodes() {
		return exceptionNodes;
	}

	public void addExceptionNode(ExceptionNode exceptionNode) {
		exceptionNodes.add(exceptionNode);
	}
	
	
}

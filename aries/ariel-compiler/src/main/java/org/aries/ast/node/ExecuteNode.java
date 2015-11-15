package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Execute node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String text</pre>
 * 
 */
public class ExecuteNode extends CommandNode {

	private List<CommandNode> commandNodes = new ArrayList<CommandNode>();

	private List<OptionNode> optionNodes = new ArrayList<OptionNode>();

	private ThrowsNode throwsNode;
	
	
	public ExecuteNode() {
	}
	
	public ExecuteNode(Object node) {
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

	public List<OptionNode> getOptionNodes() {
		return optionNodes;
	}

	public void addOptionNode(OptionNode optionNode) {
		optionNodes.add(optionNode);
	}

	public ThrowsNode getThrowsNode() {
		return throwsNode;
	}

	public void addThrowsNode(ThrowsNode throwsNode) {
		this.throwsNode = throwsNode;
	}

}

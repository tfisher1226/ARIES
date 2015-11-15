package org.aries.ast.node.control;

import java.util.ArrayList;
import java.util.List;

import org.aries.ast.node.CommandNode;
import org.aries.ast.node.ExpressionNode;
import org.aries.ast.node.StatementNode;


/**
 * <p>DoNode class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String text</pre>
 * 
 */
public class DoNode extends StatementNode {

	private ExpressionNode expressionNode;

	private List<CommandNode> commandNodes = new ArrayList<CommandNode>();

	
//	public DoNode() {
//	}
	
	public DoNode(Object node) {
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

}

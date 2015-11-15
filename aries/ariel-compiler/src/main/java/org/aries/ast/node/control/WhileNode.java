package org.aries.ast.node.control;

import java.util.ArrayList;
import java.util.List;

import org.aries.ast.node.CommandNode;
import org.aries.ast.node.ExpressionNode;
import org.aries.ast.node.StatementNode;


/**
 * <p>WhileNode class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String text</pre>
 * 
 */
public class WhileNode extends StatementNode {

	private ExpressionNode expressionNode;

	private List<CommandNode> commandNodes = new ArrayList<CommandNode>();


//	public WhileNode() {
//	}
	
	public WhileNode(Object node) {
		super(node);
	}

	public ExpressionNode getExpressionNode() {
		return expressionNode;
	}

	public void setExpressionNode(ExpressionNode expressionNode) {
		this.expressionNode = expressionNode;
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

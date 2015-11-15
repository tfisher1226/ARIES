package org.aries.ast.node.control;

import java.util.ArrayList;
import java.util.List;

import org.aries.ast.node.CommandNode;
import org.aries.ast.node.ExpressionNode;
import org.aries.ast.node.StatementNode;


/**
 * <p>ForNode class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String text</pre>
 * 
 */
public class ForNode extends StatementNode {

	private ExpressionNode startExpressionNode;

	private ExpressionNode conditionExpressionNode;

	private ExpressionNode updateExpressionNode;

	private List<ExpressionNode> iterationExpressionNodes = new ArrayList<ExpressionNode>();

	private List<CommandNode> commandNodes = new ArrayList<CommandNode>();

	
//	public ForNode() {
//	}
	
	public ForNode(Object node) {
		super(node);
	}

	public ExpressionNode getStartExpressionNode() {
		return startExpressionNode;
	}

	public void setStartExpressionNode(ExpressionNode startExpressionNode) {
		this.startExpressionNode = startExpressionNode;
	}
	
	public ExpressionNode getConditionExpressionNode() {
		return conditionExpressionNode;
	}

	public void setConditionExpressionNode(ExpressionNode conditionExpressionNode) {
		this.conditionExpressionNode = conditionExpressionNode;
	}
	
	public ExpressionNode getUpdateExpressionNode() {
		return updateExpressionNode;
	}

	public void setUpdateExpressionNode(ExpressionNode updateExpressionNode) {
		this.updateExpressionNode = updateExpressionNode;
	}
	
	public List<ExpressionNode> getIterationExpressionNodes() {
		return iterationExpressionNodes;
	}

	public void setIterationExpressionNode(List<ExpressionNode> iterationExpressionNodes) {
		this.iterationExpressionNodes = iterationExpressionNodes;
	}

	public void addIterationExpressionNode(ExpressionNode iterationExpressionNode) {
		iterationExpressionNodes.add(iterationExpressionNode);
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

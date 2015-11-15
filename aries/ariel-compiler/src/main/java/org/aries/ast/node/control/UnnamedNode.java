package org.aries.ast.node.control;

import java.util.ArrayList;
import java.util.List;

import org.aries.ast.node.CommandNode;
import org.aries.ast.node.StatementNode;


/**
 * <p>UnnamedNode class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String text</pre>
 * 
 */
public class UnnamedNode extends StatementNode {

	private List<CommandNode> statementNodes = new ArrayList<CommandNode>();


	public UnnamedNode(Object node) {
		super(node);
	}

	public List<CommandNode> getStatementNodes() {
		return statementNodes;
	}

	public void setStatementNodes(List<CommandNode> statementNodes) {
		this.statementNodes = statementNodes;
	}

	public void addStatementNode(CommandNode statementNode) {
		statementNodes.add(statementNode);
	}
	
}

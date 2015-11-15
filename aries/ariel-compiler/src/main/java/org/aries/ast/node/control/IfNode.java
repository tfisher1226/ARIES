package org.aries.ast.node.control;

import java.util.ArrayList;
import java.util.List;

import org.aries.ast.node.ConditionNode;
import org.aries.ast.node.ExpressionNode;
import org.aries.ast.node.StatementNode;


/**
 * <p>IfNode class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String text</pre>
 * 
 */
public class IfNode extends StatementNode {

	private ConditionNode conditionNode;

	private List<StatementNode> statementNodes = new ArrayList<StatementNode>();

	private List<IfNode> elseIfNodes = new ArrayList<IfNode>();

	private UnnamedNode elseNode;

	
//	public IfNode() {
//	}
	
	public IfNode(Object node) {
		super(node);
	}

	public ConditionNode getConditionNode() {
		return conditionNode;
	}

	public void setConditionNode(ConditionNode conditionNode) {
		this.conditionNode = conditionNode;
	}

	public List<StatementNode> getStatementNodes() {
		return statementNodes;
	}

	public void setStatementNodes(List<StatementNode> statementNodes) {
		this.statementNodes = statementNodes;
	}

	public void addStatementNode(StatementNode statementNode) {
		statementNodes.add(statementNode);
	}
	
	public List<IfNode> getElseIfNodes() {
		return elseIfNodes;
	}

	public void setElseIfNodes(List<IfNode> elseIfNodes) {
		this.elseIfNodes = elseIfNodes;
	}

	public void addElseIfNode(IfNode elseIfNode) {
		elseIfNodes.add(elseIfNode);
	}

	public UnnamedNode getElseNode() {
		return elseNode;
	}

	public void setElseNode(UnnamedNode elseNode) {
		this.elseNode = elseNode;
	}
	
}

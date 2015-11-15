package org.aries.ast.node;

import nam.model.Condition;


/**
 * <p>ConditionNode class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String text</pre>
 * 
 */
public class ConditionNode extends AbstractNode {

	private ExpressionNode firstOperandNode;

	private ExpressionNode secondOperandNode;

	private OperatorNode operatorNode;
	
	private Condition condition;
	
	
	public ConditionNode(Object node) {
		super(node);
	}

	public ExpressionNode getFirstOperandNode() {
		return firstOperandNode;
	}

	public void setFirstOperandNode(ExpressionNode firstOperandNode) {
		this.firstOperandNode = firstOperandNode;
	}

	public ExpressionNode getSecondOperandNode() {
		return secondOperandNode;
	}

	public void setSecondOperandNode(ExpressionNode secondOperandNode) {
		this.secondOperandNode = secondOperandNode;
	}

	public OperatorNode getOperatorNode() {
		return operatorNode;
	}

	public void setOperatorNode(OperatorNode operatorNode) {
		this.operatorNode = operatorNode;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

}

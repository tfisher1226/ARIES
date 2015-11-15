package org.aries.ast.node;


/**
 * <p>DefinitionNode class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String text</pre>
 * 
 */
public class DefinitionNode extends StatementNode {

	private PrimaryNode primaryNode;
	
	private ExpressionNode expressionNode;
	

//	public DefinitionNode() {
//	}
	
	public DefinitionNode(Object node) {
		super(node);
	}

	public PrimaryNode getPrimaryNode() {
		return primaryNode;
	}

	public void setPrimaryNode(PrimaryNode primaryNode) {
		this.primaryNode = primaryNode;
	}

	public ExpressionNode getExpressionNode() {
		return expressionNode;
	}

	public void setExpressionNode(ExpressionNode expressionNode) {
		this.expressionNode = expressionNode;
	}

}
